package com.moon.tinyredis.resp.client;

import com.moon.tinyredis.resp.parser.PayLoad;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author Chanmoey
 * @date 2023年02月27日
 */
public class TinyClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        if (msg instanceof PayLoad payLoad) {
            System.out.println(new String(payLoad.getData().toBytes()));
        }
    }
}
