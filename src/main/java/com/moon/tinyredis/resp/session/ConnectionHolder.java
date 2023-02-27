package com.moon.tinyredis.resp.session;


import io.netty.channel.Channel;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Chanmoey
 * @date 2023年02月27日
 */
public class ConnectionHolder {

    private ConnectionHolder(){}

    private static final Map<Channel, Connection> CONNECTION_MAP = new HashMap<>();

    public static void put(Connection connection) {
        CONNECTION_MAP.put(connection.getChannel(), connection);
    }

    public static Connection get(Channel channel){
        return CONNECTION_MAP.get(channel);
    }

    public static void close(Connection connection) {
        connection.close();
        CONNECTION_MAP.remove(connection.getChannel());
    }
}
