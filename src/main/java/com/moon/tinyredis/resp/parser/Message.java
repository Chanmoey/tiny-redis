package com.moon.tinyredis.resp.parser;

import com.moon.tinyredis.resp.reply.Reply;

/**
 * @author Chanmoey
 * @date 2023年02月26日
 */
public class Message {

    public static final int BODY_LENGTH = 4;

    private int bodyLength;

    private Reply reply;
}
