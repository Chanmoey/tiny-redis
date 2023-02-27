package com.moon.tinyredis.resp.database;

import com.moon.tinyredis.resp.command.CommandLine;
import com.moon.tinyredis.resp.parser.Message;
import com.moon.tinyredis.resp.reply.Reply;
import com.moon.tinyredis.resp.reply.error.CommonErrorReply;
import com.moon.tinyredis.resp.session.Connection;
import io.netty.buffer.ByteBuf;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @author Chanmoey
 * @date 2023年02月27日
 */
public class Database implements IDatabase {

    private static final Database INSTANCE = new Database();

    private static final Executor EXECUTOR = Executors.newSingleThreadExecutor();

    private static final Executor RESPONSE = Executors.newSingleThreadExecutor();

    private static final int DB_COUNT = 16;

    private final DB[] dbs;

    private Database() {
        this.dbs = new DB[DB_COUNT];
        for (int i = 0; i < 16; i++) {
            dbs[i] = new DB(i);
        }
    }

    public static IDatabase getInstance() {
        return INSTANCE;
    }

    @Override
    public void exec(Connection connection, CommandLine commandLine) {
        EXECUTOR.execute(() -> {
            int idx = connection.getDBIndex();
            if (idx < 0 || idx >= DB_COUNT) {
                response(connection, CommonErrorReply.makeCommonErrorReply("illegal db"));
                return;
            }
            // TODO：处理select 0
            dbs[idx].execCommand(connection, commandLine);
        });
    }

    private void response(Connection connection, Reply reply) {
        RESPONSE.execute(() -> {
            ByteBuf response = Message.makeMessage(reply);
            connection.getChannel().writeAndFlush(response);
        });
    }

    @Override
    public void close() {
    }

    @Override
    public void afterClientClose(Connection connection) {
    }
}
