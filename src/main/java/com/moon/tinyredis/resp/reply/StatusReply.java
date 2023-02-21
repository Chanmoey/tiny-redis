package com.moon.tinyredis.resp.reply;

import java.nio.charset.StandardCharsets;

/**
 * @author Chanmoey
 * @date 2023年02月20日
 */
public class StatusReply implements Reply{

    private final String status;

    public StatusReply(String status) {
        this.status = status;
    }

    @Override
    public byte[] toBytes() {
        return ("+" + status + RespConstant.CRLF).getBytes(StandardCharsets.UTF_8);
    }

    public static StatusReply makeStatusReply(String status) {
        return new StatusReply(status);
    }
}
