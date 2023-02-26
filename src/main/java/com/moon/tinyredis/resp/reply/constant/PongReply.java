package com.moon.tinyredis.resp.reply.constant;

import com.moon.tinyredis.resp.config.SystemConfig;
import com.moon.tinyredis.resp.reply.Reply;

import java.nio.charset.StandardCharsets;

/**
 * @author Chanmoey
 * @date 2023年02月20日
 */
public class PongReply implements Reply {

    private static final PongReply INSTANCE = new PongReply();

    private static final byte[] PONG_BYTES = "+PONG\r\n".getBytes(SystemConfig.SYSTEM_CHARSET);

    @Override
    public byte[] toBytes() {
        return PONG_BYTES;
    }

    public static PongReply makePonyReplay() {
        return INSTANCE;
    }
}
