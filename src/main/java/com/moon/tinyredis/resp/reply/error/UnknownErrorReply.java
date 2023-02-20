package com.moon.tinyredis.resp.reply.error;

import java.nio.charset.StandardCharsets;

/**
 * @author Chanmoey
 * @date 2023年02月20日
 */
public class UnknownErrorReply extends AbstractErrorReplay{

    UnknownErrorReply() {
        super(" Unknown Err");
    }

    private static final byte[] UNKNOWN_ERROR_BYTES = "-Err unknown\r\n".getBytes(StandardCharsets.UTF_8);

    @Override
    public byte[] toBytes() {
        return UNKNOWN_ERROR_BYTES;
    }
}
