package com.moon.tinyredis.resp.parser;

import com.moon.tinyredis.resp.config.SystemConfig;
import com.moon.tinyredis.resp.reply.*;
import com.moon.tinyredis.resp.reply.constant.EmptyMultiBulkReply;
import com.moon.tinyredis.resp.reply.constant.NullBulkReply;
import com.moon.tinyredis.resp.reply.error.CommonErrorReply;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.util.ByteProcessor;

import java.util.List;

/**
 * @author Chanmoey
 * @date 2023年02月23日
 */
public class Parser extends ByteToMessageDecoder {

    public Parser(ReadState readState) {
        this.readState = readState;
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf byteBuf, List<Object> list) throws Exception {

        if (byteBuf.readableBytes() < Message.BODY_LENGTH) {
            return;
        }
        int bodyLength = byteBuf.readInt();
        if (byteBuf.readableBytes() < bodyLength) {
            // 长度不够一个body的长度，重置读取位置，等待下次重读
            byteBuf.resetReaderIndex();
            return;
        }
        ByteBuf cache = ByteBufAllocator.DEFAULT.buffer(bodyLength);
        byteBuf.readBytes(cache, bodyLength);
        // 读取完成后，记录索引
        byteBuf.markReaderIndex();

        // 重置readState, 解析RESP
        readState.resetState();
        PayLoad payLoad = parser0(cache);

        list.add(payLoad);
    }

    private final ReadState readState;

    /**
     * 主要解析方法
     */
    public PayLoad parser0(ByteBuf data) {
        PayLoad payLoad = new PayLoad();
        while (data.readableBytes() > 0) {
            try {
                byte[] msg = readLine(data);
                /* 判断是不是多行解析模式 */
                if (!readState.isReadingMultiLine()) {
                    // 发送'*'，解析头部，开启多行解析 *3\r\n
                    if (msg[0] == '*') {
                        parseMultiBulkHeader(msg);
                        // 数组长度为0，解析完成，重置readState，并返回解析结果
                        if (readState.getExpectedArgsCount() == 0) {
                            payLoad.setData(EmptyMultiBulkReply.makeEmptyMultiBuckReply());
                            readState.resetState();
                            return payLoad;
                        }

                        /*  发送'$'，解析头部，开启字符串的多行解析 */
                    } else if (msg[0] == '$') {
                        // $4\r\nPING\r\n
                        parseBulkHeader(msg);
                        // $-1\r\n
                        if (readState.getExpectedArgsCount() == 0) {
                            payLoad.setData(NullBulkReply.makeNullBulkReply());
                            readState.resetState();
                            return payLoad;
                        }

                        /* 剩下的: + - 三种情况 */
                    } else {
                        Reply reply = parseSingleLineReply(msg);
                        payLoad.setData(reply);
                        readState.resetState();
                        return payLoad;
                    }

                    /* 多行模式 */
                } else {
                    readBody(msg);
                    if (readState.finished()) {
                        Reply result = null;
                        if (readState.getMsgType() == RespConstant.MULTI_BULK) {
                            result = MultiBulkReply.makeMultiBulkReply(readState.getArgs());
                        } else if (readState.getMsgType() == RespConstant.BULK) {
                            result = BulkReply.makeBulkReply(readState.getArgs()[0]);
                        }
                        payLoad.setData(result);
                    }
                }
            } catch (Exception e) {
                // 出错了，设置错误，等待下一步处理，并且初始化readState等待下一次IO
                e.printStackTrace();
                readState.resetState();
                payLoad.setException((DecodeException) e);
                return payLoad;
            }
        }
        readState.resetState();
        return payLoad;
    }

    /**
     * 由decode方法保证，传到这个方法的数据，一定不存在拆包粘包问题
     * *3\r\n$3\r\nSET\r\n$3\r\nkey\r\n$5\r\nvalue\r\n
     * 读取一行（以\r\n结尾）
     */
    private byte[] readLine(ByteBuf data) {
        byte[] msg;
        // 如果读取到了$数字，则严格读取字符个数，否则按照\r\n进行切分

        // 按\r\n读取
        if (readState.getBulkLen() == 0) {
            // 找\n的位置
            int index = data.forEachByte(ByteProcessor.FIND_LF);
            // 找到末尾都没找到\n，肯定是协议错误了
            if (index == -1) {
                throw new DecodeException(RespConstant.PROTOCOL_ERROR + "no end byte '\n'");
            }
            msg = new byte[index - data.readerIndex() + 1];
            data.readBytes(msg);
            data.readerIndex();
            // 如果\n前面不是\r，也是协议错误了
            if (msg.length < 2 || msg[msg.length - 2] != '\r') {
                throw new DecodeException(RespConstant.PROTOCOL_ERROR + new String(msg));
            }
        } else {
            // 按照特定长度去读
            if (data.readableBytes() < readState.getBulkLen() + 2) {
                throw new DecodeException(RespConstant.PROTOCOL_ERROR + "buck length out of readable bytes");
            }
            msg = new byte[readState.getBulkLen() + 2];
            data.readBytes(msg);
            data.markReaderIndex();
            // 结尾必须是\r\n
            if (msg[msg.length - 1] != '\n' || msg[msg.length - 2] != '\r') {
                throw new DecodeException(RespConstant.PROTOCOL_ERROR + "must end with \r\n");
            }
            readState.setBulkLen(0);
        }
        return msg;
    }

    /**
     * 根据readLine中获取到的一行字节，解析多行数组的行数
     * *300\r\n
     */
    private void parseMultiBulkHeader(byte[] msg) {
        byte[] numByte = new byte[msg.length - 3];
        System.arraycopy(msg, 1, numByte, 0, numByte.length);

        try {
            int expectedLine = Integer.parseInt(new String(numByte, SystemConfig.SYSTEM_CHARSET));
            if (expectedLine == 0) {
                readState.setExpectedArgsCount(expectedLine);
            } else if (expectedLine > 0) {
                // 设置解析器状态，标记正在读多行
                readState.setMsgType(msg[0]);
                readState.setExpectedArgsCount(expectedLine);
                readState.setReadingMultiLine(true);
                readState.setArgs(new byte[expectedLine][]);
            } else {
                // < 0 错误
                throw new DecodeException(RespConstant.PROTOCOL_ERROR + new String(msg, SystemConfig.SYSTEM_CHARSET));
            }
        } catch (Exception e) {
            throw new DecodeException(RespConstant.PROTOCOL_ERROR + new String(msg, SystemConfig.SYSTEM_CHARSET));
        }
    }

    private void parseBulkHeader(byte[] msg) {
        // 获取字符串长度，去掉$ \r \n
        byte[] strLenBytes = new byte[msg.length - 3];
        System.arraycopy(msg, 1, strLenBytes, 0, strLenBytes.length);
        try {
            int strLen = Integer.parseInt(new String(strLenBytes, SystemConfig.SYSTEM_CHARSET));
            readState.setBulkLen(strLen);
            if (readState.getBulkLen() == -1) {
                readState.setExpectedArgsCount(0);
            } else if (readState.getBulkLen() > 0) {
                readState.setMsgType(msg[0]);
                readState.setReadingMultiLine(true);
                readState.setExpectedArgsCount(1);
                readState.setArgs(new byte[1][]);
            } else {
                throw new DecodeException(RespConstant.PROTOCOL_ERROR + new String(msg, SystemConfig.SYSTEM_CHARSET));
            }
        } catch (Exception e) {
            throw new DecodeException(RespConstant.PROTOCOL_ERROR + new String(msg, SystemConfig.SYSTEM_CHARSET));
        }
    }

    /**
     * +OK\r\n -err\r\n :5\r\n
     */
    private Reply parseSingleLineReply(byte[] msg) {
        String str = new String(msg).replace("\r\n", "");
        Reply result;
        switch (msg[0]) {
            case '+' -> result = StatusReply.makeStatusReply(str.substring(1));
            case '-' -> result = CommonErrorReply.makeCommonErrorReply(str.substring(1));
            case ':' -> {
                try {
                    int val = Integer.parseInt(str.substring(1));
                    result = IntegerReply.makeStatusReply(val);
                } catch (Exception e) {
                    throw new DecodeException(RespConstant.PROTOCOL_ERROR + new String(msg, SystemConfig.SYSTEM_CHARSET));
                }
            }
            default ->
                    throw new DecodeException(RespConstant.PROTOCOL_ERROR + new String(msg, SystemConfig.SYSTEM_CHARSET));
        }
        return result;
    }

    /**
     * 1. $3\r\nSET\r\n$3\r\nkey\r\n$5\r\nvalue\r\n
     * 2. PING\r\n
     */
    private void readBody(byte[] msg) {
        // 去掉\r\n
        byte[] line = new byte[msg.length - 2];
        System.arraycopy(msg, 0, line, 0, line.length);
        // $3，读取字符串的长度
        if (line[0] == RespConstant.BULK) {
            byte[] numBytes = new byte[line.length - 1];
            System.arraycopy(line, 1, numBytes, 0, numBytes.length);
            try {
                int bulkLen = Integer.parseInt(new String(numBytes, SystemConfig.SYSTEM_CHARSET));

                // $0\r\n
                if (bulkLen <= 0) {
                    readState.appendArg(new byte[]{});
                    readState.setBulkLen(0);
                }
            } catch (Exception e) {
                throw new DecodeException(RespConstant.PROTOCOL_ERROR + new String(msg, SystemConfig.SYSTEM_CHARSET));
            }
        } else {
            readState.appendArg(line);
        }
    }
}
