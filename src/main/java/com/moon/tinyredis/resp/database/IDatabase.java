package com.moon.tinyredis.resp.database;

import com.moon.tinyredis.resp.command.CommandLine;
import com.moon.tinyredis.resp.session.Connection;

/**
 * @author Chanmoey
 * @date 2023年02月27日
 */
public interface IDatabase {

    void exec(Connection connection, CommandLine commandLine);

    void close();

    void afterClientClose(Connection connection);
}
