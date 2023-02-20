package com.moon.tinyredis.resp.reply.error;

import java.nio.charset.StandardCharsets;

/**
 * @author Chanmoey
 * @date 2023年02月20日
 */
public class WrongTypeErrorReply extends AbstractErrorReplay{

    WrongTypeErrorReply() {
        super("Wrong Type Err");
    }

    private static final byte[] UNKNOWN_ERROR_BYTES = "-WRONGTYPE Operation against a key holding the wrong kind of value\r\n".getBytes(StandardCharsets.UTF_8);

    @Override
    public byte[] toBytes() {
        return UNKNOWN_ERROR_BYTES;
    }
}
