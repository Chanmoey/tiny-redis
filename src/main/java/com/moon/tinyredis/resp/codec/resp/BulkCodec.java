package com.moon.tinyredis.resp.codec.resp;

import com.moon.tinyredis.resp.codec.AbstractCodec;
import com.moon.tinyredis.resp.codec.Resp;
import com.moon.tinyredis.resp.reply.RespConstant;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import java.nio.charset.StandardCharsets;

/**
 * @author Chanmoey
 * @date 2023年02月23日
 */
public class BulkCodec extends AbstractCodec<String> {

    @Override
    public Resp<String> decode(ByteBuf buffer) {
        int endIndex = getEndIndex(buffer);
        if (-1 == endIndex) {
            return null;
        }
        // 获取字符串长度
        Long len = readInteger(buffer);
        // null || Bulk Null String
        if (len == null || RespConstant.NEGATIVE_ONE.equals(len)) {
            return null;
        }

        // Bulk Empty String
        if (RespConstant.ZERO.equals(len)) {
            return this.setValue(RespConstant.EMPTY_STRING);
        }

        if (buffer.readableBytes() < len + 2) {
            return null;
        }

        if (getEndIndex(buffer) - endIndex - 2 != len) {
            throw new IllegalArgumentException("协议解析出错，字符串长度有误");
        }
        return this.setValue(readLine(buffer));
    }

    @Override
    public void encode(ChannelHandlerContext channelHandlerContext, Resp<String> resp, ByteBuf byteBuf) {
        byteBuf.writeByte(RespConstant.BULK);
        String value = resp.getValue();
        if (value == null) {
            byteBuf.writeBytes(RespConstant.NULL_BULK_REPLY_BYTES);
            byteBuf.writeBytes(RespConstant.CRLF_BYTE);
        } else if (value.getBytes(StandardCharsets.UTF_8).length == 0) {
            byteBuf.writeByte(RespConstant.ZERO.byteValue());
            byteBuf.writeBytes(RespConstant.CRLF_BYTE);
            byteBuf.writeBytes(RespConstant.CRLF_BYTE);
        } else {
            String length = String.valueOf(value.getBytes(StandardCharsets.UTF_8).length);
            char[] charArray = length.toCharArray();
            for (char each : charArray) {
                byteBuf.writeByte((byte) each);
            }
            byteBuf.writeBytes(RespConstant.CRLF_BYTE);
            byteBuf.writeBytes(value.getBytes(StandardCharsets.UTF_8));
            byteBuf.writeBytes(RespConstant.CRLF_BYTE);
        }
    }
}
