package com.moon.tinyredis.resp.client;

import com.moon.tinyredis.resp.parser.PayLoad;
import com.moon.tinyredis.resp.reply.BulkReply;
import com.moon.tinyredis.resp.reply.MultiBulkReply;
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
            if (payLoad.getData() instanceof BulkReply) {
                String s = new String(payLoad.getData().toBytes());
                System.out.println(s.split("\r\n")[1]);
            } else if (payLoad.getData() instanceof MultiBulkReply multibulkreply) {
                byte[][] args = multibulkreply.getArgs();
                for(byte[] arg : args) {
                    System.out.println(new String(arg));
                }
            }
            else {
                String s = new String(payLoad.getData().toBytes());
                System.out.println(s.substring(1, s.length() - 2));
            }
        }
    }
}
