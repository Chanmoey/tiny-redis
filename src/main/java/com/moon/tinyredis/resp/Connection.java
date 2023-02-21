package com.moon.tinyredis.resp;

import io.netty.channel.Channel;

/**
 * 表示一个Redis连接
 *
 * @author Chanmoey
 * @date 2023年02月20日
 */
public class Connection {

    public Connection(int dbIndex, Channel channel) {
        this.dbIndex = dbIndex;
        this.channel = channel;
    }

    protected int dbIndex;

    protected Channel channel;

    public int getDBIndex() {
        return dbIndex;
    }

    public void selectDB(int index) {
        this.dbIndex = index;
    }

    public Channel getChannel() {
        return channel;
    }
}
