package com.moon.tinyredis.resp.reply;

import com.moon.tinyredis.resp.config.SystemConfig;

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
        return ("+" + status + RespConstant.CRLF).getBytes(SystemConfig.SYSTEM_CHARSET);
    }

    public static StatusReply makeStatusReply(String status) {
        return new StatusReply(status);
    }
}
