package com.moon.tinyredis.resp.parser;

import com.moon.tinyredis.resp.config.SystemConfig;
import com.moon.tinyredis.resp.reply.IntegerReply;
import com.moon.tinyredis.resp.reply.Reply;
import com.moon.tinyredis.resp.reply.RespConstant;
import com.moon.tinyredis.resp.reply.StatusReply;
import com.moon.tinyredis.resp.reply.error.CommonErrorReply;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.util.ByteProcessor;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author Chanmoey
 * @date 2023年02月23日
 */
public class Parser extends ByteToMessageDecoder {

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

        // TODO: 解析RESP, 重置readState
    }

    private ReadState readState;

    /**
     * 主要解析方法
     */
    public Reply parser0(byte[] data) {
        return null;
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
                throw new DecodeException("protocol error: " + "no end byte '\n'");
            }
            msg = new byte[index + 1];
            data.readBytes(msg);
            // 如果\n前面不是\r，也是协议错误了
            if (msg.length < 2 || msg[msg.length - 2] != '\r') {
                throw new DecodeException("protocol error: " + new String(msg));
            }
        } else {
            // 按照特定长度去读
            if (data.readableBytes() < readState.getBulkLen() + 2) {
                throw new DecodeException("protocol error");
            }
            msg = new byte[readState.getBulkLen() + 2];
            // 结尾必须是\r\n
            if (msg[msg.length - 1] != '\r' || msg[msg.length - 2] != '\r') {
                throw new DecodeException("protocol error");
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
        System.arraycopy(msg, 0, numByte, 0, numByte.length);

        try {
            int expectedLine = Integer.parseInt(new String(numByte, SystemConfig.SYSTEM_CHARSET));
            readState.setBulkLen(expectedLine);
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
                throw new DecodeException("protocol error: " + new String(msg, SystemConfig.SYSTEM_CHARSET));
            }
        } catch (Exception e) {
            throw new DecodeException("protocol error: " + new String(msg, SystemConfig.SYSTEM_CHARSET));
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
                    throw new DecodeException("protocol error: " + new String(msg, SystemConfig.SYSTEM_CHARSET));
                }
            }
            default -> throw new DecodeException("protocol error: " + new String(msg, SystemConfig.SYSTEM_CHARSET));
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
            int strLen = Integer.parseInt(new String(numBytes, SystemConfig.SYSTEM_CHARSET));
            
        }
    }
}
