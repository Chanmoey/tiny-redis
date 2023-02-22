package com.moon.tinyredis.resp.codec;

import com.moon.tinyredis.resp.reply.RespConstant;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.ByteProcessor;

import java.nio.charset.StandardCharsets;

/**
 * @author Chanmoey
 * @date 2023年02月22日
 */
public abstract class AbstractDecode<T> extends Resp<T> implements RespCodec<T> {
    @Override
    public abstract Resp<T> decode(ByteBuf buffer);

    @Override
    public abstract void encode(ChannelHandlerContext channelHandlerContext, Resp<T> resp, ByteBuf byteBuf);

    protected Long readInteger(ByteBuf buffer) {
        String num = readLine(buffer);
        if (num == null) {
            return null;
        }

        return Long.parseLong(num);
    }

    protected String readLine(ByteBuf buffer) {
        int endIndex = getEndIndex(buffer);

        if (endIndex == -1) {
            return null;
        }

        int startIndex = buffer.readerIndex();
        int length = endIndex - startIndex - 1;
        byte[] bytes = new byte[length];
        buffer.readBytes(bytes);

        // 设置游标为\r\n之后的第一个字节
        buffer.readerIndex(endIndex + 1);
        buffer.markReaderIndex();
        return new String(bytes, StandardCharsets.UTF_8);
    }

    protected int getEndIndex(ByteBuf buffer) {
        // 找\n的未知
        int index = buffer.forEachByte(ByteProcessor.FIND_LF);
        return (index > 0 && buffer.getByte(index - 1) == RespConstant.CR) ? index : -1;
    }
}
