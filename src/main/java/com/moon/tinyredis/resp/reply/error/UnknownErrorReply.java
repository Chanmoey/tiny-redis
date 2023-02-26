package com.moon.tinyredis.resp.reply.error;

import java.nio.charset.StandardCharsets;

/**
 * @author Chanmoey
 * @date 2023年02月20日
 */
public class UnknownErrorReply extends AbstractErrorReplay {

    private static final UnknownErrorReply INSTANCE = new UnknownErrorReply();

    UnknownErrorReply() {
        super(" Unknown Err");
    }

    private static final byte[] UNKNOWN_ERROR_BYTES = "-Err unknown\r\n".getBytes(SystemConfig.SYSTEM_CHARSET);

    @Override
    public byte[] toBytes() {
        return UNKNOWN_ERROR_BYTES;
    }

    public static UnknownErrorReply makeUnknownErrorReply() {
        return INSTANCE;
    }
}
