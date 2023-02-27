package com.moon.tinyredis.resp.session;

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

    /**
     * 本次客户端连接所操作的数据库
     */
    private int dbIndex;

    /**
     * Channel
     */
    private final Channel channel;

    public void close() {
        this.channel.close();
    }

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
