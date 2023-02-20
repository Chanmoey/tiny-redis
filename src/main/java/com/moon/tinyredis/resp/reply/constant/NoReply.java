package com.moon.tinyredis.resp.reply.constant;

import com.moon.tinyredis.resp.reply.Reply;

/**
 * @author Chanmoey
 * @date 2023年02月20日
 */
public class NoReply implements Reply {

    private static final NoReply INSTANCE = new NoReply();

    private static final byte[] NO_BYTES = "".getBytes();

    @Override
    public byte[] toBytes() {
        return NO_BYTES;
    }

    public static NoReply makeNoReplay() {
        return INSTANCE;
    }
}
