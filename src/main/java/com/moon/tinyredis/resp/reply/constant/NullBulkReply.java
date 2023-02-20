package com.moon.tinyredis.resp.reply.constant;

import com.moon.tinyredis.resp.reply.Reply;

import java.nio.charset.StandardCharsets;

/**
 * @author Chanmoey
 * @date 2023年02月20日
 */
public class NullBulkReply implements Reply {

    private static final NullBulkReply INSTANCE = new NullBulkReply();

    private static final byte[] NULL_BULK_BYTES = "$-1\r\n".getBytes(StandardCharsets.UTF_8);

    @Override
    public byte[] toBytes() {
        return NULL_BULK_BYTES;
    }

    public static NullBulkReply makeOkReplay() {
        return INSTANCE;
    }
}
