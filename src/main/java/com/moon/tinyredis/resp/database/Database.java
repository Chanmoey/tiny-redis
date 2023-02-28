package com.moon.tinyredis.resp.database;

import com.moon.tinyredis.resp.command.CommandLine;
import com.moon.tinyredis.resp.command.CommandTable;
import com.moon.tinyredis.resp.config.SystemConfig;
import com.moon.tinyredis.resp.parser.Message;
import com.moon.tinyredis.resp.reply.Reply;
import com.moon.tinyredis.resp.reply.constant.OkReply;
import com.moon.tinyredis.resp.reply.error.ArgNumberErrorReply;
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

        // 初始化指令集
        CommandTable.initTable();
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

            // select指令由DataBase处理即可
            if ("select".equalsIgnoreCase(new String(commandLine.getCommand(), SystemConfig.SYSTEM_CHARSET))) {
                Reply select = select(connection, commandLine);
                response(connection, select);
                return;
            }

            // 其他指令交由具体的DB进行处理
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

    @Override
    public Reply select(Connection connection, CommandLine commandLine) {
        try {
            if (commandLine.getArgs().length != 1) {
                return ArgNumberErrorReply.makeArgNumberErrorReply("select");
            }
            int dbIndex = Integer.parseInt(new String(commandLine.getArgs()[0], SystemConfig.SYSTEM_CHARSET));
            if (dbIndex < 0 || dbIndex >= DB_COUNT) {
                return CommonErrorReply.makeCommonErrorReply("err db index is out of range");
            }
            connection.selectDB(dbIndex);
            return OkReply.makeOkReplay();
        } catch (Exception e) {
            return CommonErrorReply.makeCommonErrorReply("err invalid db index");
        }
    }
}
