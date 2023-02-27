package com.moon.tinyredis.resp.reply.constant;

import com.moon.tinyredis.resp.config.SystemConfig;
import com.moon.tinyredis.resp.reply.Reply;

/**
 * @author Chanmoey
 * @date 2023年02月20日
 */
public class OkReply implements Reply {
    private static final OkReply INSTANCE = new OkReply();

    private static final byte[] OK_BYTES = "+OK\r\n".getBytes(SystemConfig.SYSTEM_CHARSET);

    @Override
    public byte[] toBytes() {
        return OK_BYTES;
    }

    public static OkReply makeOkReplay() {
        return INSTANCE;
    }
}
