package com.moon.tinyredis.resp.database;

import com.moon.tinyredis.resp.Connection;
import com.moon.tinyredis.resp.codec.CommandLine;
import com.moon.tinyredis.resp.command.Command;
import com.moon.tinyredis.resp.command.CommandTable;
import com.moon.tinyredis.resp.datastructure.dick.Dict;
import com.moon.tinyredis.resp.reply.error.ErrorReply;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @author Chanmoey
 * @date 2023年02月21日
 */
public class DB {

    private static final CommandTable COMMAND_TABLE = CommandTable.getCommandTable();

    private static final Executor EXECUTOR = Executors.newSingleThreadExecutor();

    private int index;

    private Dict data;

    public DB(int index, Dict data) {
        this.index = index;
        this.data = data;
    }

    private void execCommand(Connection connection, CommandLine commandLine) {
        EXECUTOR.execute(() -> {
            String commandName = new String(commandLine.getCommand(), StandardCharsets.UTF_8);
            Command command = COMMAND_TABLE.getCommand(commandName);
            if (command == null) {
                connection.getChannel().writeAndFlush((ErrorReply.);
            }

        });
    }
}
