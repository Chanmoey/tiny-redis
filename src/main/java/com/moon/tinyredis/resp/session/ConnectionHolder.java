package com.moon.tinyredis.resp.session;

import io.netty.channel.Channel;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Chanmoey
 * @date 2023年02月27日
 */
public class ConnectionHolder {

    private static final Map<Channel, Connection> connectionMap = new HashMap<>();

    public static void put(Connection connection) {
        connectionMap.put(connection.getChannel(), connection);
    }

    public static void close(Connection connection) {
        connection.close();
        connectionMap.remove(connection.getChannel());
    }
}
