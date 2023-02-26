package com.moon.tinyredis.resp.parser;

import com.moon.tinyredis.resp.reply.Reply;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

/**
 * 用来构建网络传输消息数据
 *
 * @author Chanmoey
 * @date 2023年02月26日
 */
public class Message {

    public static final int BODY_LENGTH = 4;

    public static ByteBuf makeMessage(Reply reply) {
        ByteBuf buf = ByteBufAllocator.DEFAULT.buffer(reply.toBytes().length + BODY_LENGTH);
        buf.writeInt(reply.toBytes().length);
        buf.writeBytes(reply.toBytes());
        return buf;
    }
}
