package com.moon.tinyredis.resp.reply.constant;

import com.moon.tinyredis.resp.config.SystemConfig;
import com.moon.tinyredis.resp.reply.Reply;

/**
 * @author Chanmoey
 * @date 2023年02月20日
 */
public class NullBulkReply implements Reply {

    private static final NullBulkReply INSTANCE = new NullBulkReply();

    private static final byte[] NULL_BULK_BYTES = "$-1\r\n".getBytes(SystemConfig.SYSTEM_CHARSET);

    @Override
    public byte[] toBytes() {
        return NULL_BULK_BYTES;
    }

    public static NullBulkReply makeNullBulkReply() {
        return INSTANCE;
    }
}
