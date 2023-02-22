package com.moon.tinyredis.resp.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author Chanmoey
 * @date 2023年02月22日
 */
public interface RespCodec<T> {

    Resp<T> decode(ByteBuf buffer);

    void encode(ChannelHandlerContext channelHandlerContext, Resp<T> resp, ByteBuf byteBuf);
}
