package com.moon.tinyredis.resp.database;

import com.moon.tinyredis.resp.command.Command;
import com.moon.tinyredis.resp.command.CommandLine;
import com.moon.tinyredis.resp.command.CommandTable;
import com.moon.tinyredis.resp.config.SystemConfig;
import com.moon.tinyredis.resp.datastructure.dick.Dict;
import com.moon.tinyredis.resp.datastructure.dick.HashDict;
import com.moon.tinyredis.resp.parser.Message;
import com.moon.tinyredis.resp.reply.Reply;
import com.moon.tinyredis.resp.reply.error.ArgNumberErrorReply;
import com.moon.tinyredis.resp.reply.error.CommonErrorReply;
import com.moon.tinyredis.resp.session.Connection;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @author Chanmoey
 * @date 2023年02月21日
 */
public class DB {

    private static final CommandTable COMMAND_TABLE = CommandTable.getCommandTable();

    /**
     * 指令处理线程池
     */
    private static final Executor EXECUTOR = Executors.newSingleThreadExecutor();

    /**
     * 指令回复线程池
     */
    private static final Executor RESPONSE_EXECUTOR = Executors.newSingleThreadExecutor();

    private final Integer index;

    private final Dict data;

    public Dict getDict() {
        return data;
    }

    public DB(Integer index) {
        this.index = index;
        this.data = new HashDict();
    }

    /**
     * 当前线程不是Netty的线程，所以调用channel的writeAndFlush会作为一个task插入到Netty的线程池中去
     * 所以不去要额外求开辟一个线程，单独提交write的任务
     * <p>
     * 如果不是这样，网络IO相关的事件和其他业务事件要分开，不要让业务阻塞了Netty的网络IO，造成延迟
     */
    public void execCommand(Connection connection, CommandLine commandLine) {
        EXECUTOR.execute(() -> {
            Channel channel = connection.getChannel();

            if (connection.getDBIndex() != this.index) {
                Reply errorIdx = CommonErrorReply.makeCommonErrorReply("error db index");
                response(channel, errorIdx);
                return;
            }

            String commandName = new String(commandLine.getCommand(), SystemConfig.SYSTEM_CHARSET);
            Command command = COMMAND_TABLE.getCommand(commandName);
            if (command == null) {
                response(channel, CommonErrorReply.makeCommonErrorReply("Err unknown command " + commandName));
                return;
            }
            if (!command.validateArity(commandLine.getArgs())) {
                response(channel, ArgNumberErrorReply.makeArgNumberErrorReply(commandName));
                return;
            }
            Reply res = command.exec(this, commandLine.getArgs());
            response(channel, res);

            // TODO: AOF刷盘
        });
    }

    private void response(Channel channel, Reply reply) {
        RESPONSE_EXECUTOR.execute(() -> {
            ByteBuf byteBuf = Message.makeMessage(reply);
            channel.writeAndFlush(byteBuf);
        });
    }
}
