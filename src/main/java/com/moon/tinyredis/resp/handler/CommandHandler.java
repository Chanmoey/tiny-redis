package com.moon.tinyredis.resp.handler;

import com.moon.tinyredis.resp.config.SystemConfig;
import com.moon.tinyredis.resp.parser.Message;
import com.moon.tinyredis.resp.parser.PayLoad;
import com.moon.tinyredis.resp.reply.error.CommonErrorReply;
import com.moon.tinyredis.resp.reply.error.ErrorReply;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * 对指令进行转发，如果是正常的指令，则交由Database进行执行处理
 * 如果是错误的指令，则回送错误信息
 *
 * @author Chanmoey
 * @date 2023年02月27日
 */
public class CommandHandler extends SimpleChannelInboundHandler<PayLoad> {

    private static final Executor executor = Executors.newSingleThreadExecutor();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, PayLoad msg) throws Exception {
        if (msg.getException() != null) {
            ErrorReply errorReply = CommonErrorReply.makeCommonErrorReply(msg.getException().getMessage());
            ByteBuf errBuf = Message.makeMessage(errorReply);
            ctx.writeAndFlush(errBuf);
            return;
        }

        // 通过线程池将IO与业务处理进行解耦，并将指令转发给Database进行处理
        System.out.println(new String(msg.getData().toBytes(), SystemConfig.SYSTEM_CHARSET));
    }
}
