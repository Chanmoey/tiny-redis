package com.moon.tinyredis.resp.reply;

/**
 * 服务端对客户端的回复
 *
 * @author Chanmoey
 * @date 2023年02月20日
 */
public interface Reply {

    byte[] toBytes();
}
