package com.moon.tinyredis.resp.reply.constant;

import com.moon.tinyredis.resp.config.SystemConfig;
import com.moon.tinyredis.resp.reply.Reply;

import java.nio.charset.StandardCharsets;

/**
 * @author Chanmoey
 * @date 2023年02月20日
 */
public class EmptyMultiBulkReply implements Reply {

    private static final EmptyMultiBulkReply INSTANCE = new EmptyMultiBulkReply();

    private static final byte[] EMPTY_MULTI_BULK_BYTES = "*0\r\n".getBytes(SystemConfig.SYSTEM_CHARSET);

    @Override
    public byte[] toBytes() {
        return EMPTY_MULTI_BULK_BYTES;
    }

    public static EmptyMultiBulkReply makeEmptyMultiBuckReply() {
        return INSTANCE;
    }
}
