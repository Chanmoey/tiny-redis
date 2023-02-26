package com.moon.tinyredis.resp.reply.constant;

import com.moon.tinyredis.resp.reply.Reply;

import java.nio.charset.StandardCharsets;

/**
 * @author Chanmoey
 * @date 2023年02月20日
 */
public class EmptyBulkReply implements Reply {
    private static final EmptyBulkReply INSTANCE = new EmptyBulkReply();

    private static final byte[] EMPTY_BULK_BYTES = "$0\r\n\r\n".getBytes(SystemConfig.SYSTEM_CHARSET);

    @Override
    public byte[] toBytes() {
        return EMPTY_BULK_BYTES;
    }

    public static EmptyBulkReply makeEmptyMultiBuckReply() {
        return INSTANCE;
    }
}
