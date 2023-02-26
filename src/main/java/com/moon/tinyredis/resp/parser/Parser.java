package com.moon.tinyredis.resp.parser;

import com.moon.tinyredis.resp.reply.Reply;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.util.ByteProcessor;

import java.util.List;

/**
 * @author Chanmoey
 * @date 2023年02月23日
 */
public class Parser extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf byteBuf, List<Object> list) throws Exception {

    }

    private ReadState readState;

    /**
     * 主要解析方法
     *
     * @param buffer
     * @return
     */
    public Reply parser0(ByteBuf buffer) {
        return null;
    }

    /**
     * *3\r\n$3\r\nSET\r\n$3\r\nkey\r\n$5\r\nvalue\r\n
     * 读取一行（以\r\n结尾）
     */
    public byte[] readLine(ByteBuf buffer) {
        byte[] msg;
        // 如果读取到了$数字，则严格读取字符个数
        // 否则按照\r\n进行切分
        if (readState.getBulkLen() == 0) {
            // 找\n的位置
            int index = buffer.forEachByte(ByteProcessor.FIND_LF);
            // 找到末尾都没找到\n，可能出现了拆包，等待下一轮
            if (index == -1) {
                return null;
            }
            msg = new byte[index + 1];
            buffer.readBytes(msg);
            if (msg.length < 2 || msg[msg.length - 2] != '\r') {
                throw new DecodeException("protocol error: " + new String(msg));
            }
        } else {

        }
        return msg;
    }
}
