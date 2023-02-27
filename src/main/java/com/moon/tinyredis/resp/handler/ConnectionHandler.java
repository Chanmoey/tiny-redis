package com.moon.tinyredis.resp.handler;

import com.moon.tinyredis.resp.session.Connection;
import com.moon.tinyredis.resp.session.ConnectionHolder;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author Chanmoey
 * @date 2023年02月27日
 */
public class ConnectionHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Connection connection = new Connection(0, ctx.channel());
        ConnectionHolder.put(connection);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        ConnectionHolder.close(ConnectionHolder.get(ctx.channel()));
    }
}
