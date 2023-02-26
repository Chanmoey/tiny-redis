package com.moon.tinyredis.resp.handler;

import com.moon.tinyredis.resp.config.SystemConfig;
import com.moon.tinyredis.resp.parser.PayLoad;
import com.moon.tinyredis.resp.reply.error.CommonErrorReply;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * 对指令进行转发，如果是正常的指令，则交由Database进行执行处理
 * 如果是错误的指令，则回送错误信息
 *
 * @author Chanmoey
 * @date 2023年02月27日
 */
public class CommandHandler extends SimpleChannelInboundHandler<PayLoad> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, PayLoad msg) throws Exception {
        if (msg.getException() != null) {
            ctx.writeAndFlush(CommonErrorReply.makeCommonErrorReply(msg.getException().getMessage()));
            return;
        }

        System.out.println(new String(msg.getData().toBytes(), SystemConfig.SYSTEM_CHARSET));
    }
}
