package com.moon.tinyredis.resp.reply;

import com.moon.tinyredis.resp.config.SystemConfig;
import com.moon.tinyredis.resp.reply.constant.EmptyBulkReply;
import com.moon.tinyredis.resp.reply.constant.NullBulkReply;

import java.nio.charset.StandardCharsets;

/**
 * @author Chanmoey
 * @date 2023年02月20日
 */
public class BulkReply implements Reply {

    private final byte[] arg;

    public BulkReply(byte[] arg) {
        this.arg = arg;
    }

    @Override
    public byte[] toBytes() {

        if (arg == null) {
            return NullBulkReply.makeOkReplay().toBytes();
        }

        if (arg.length == 0) {
            return EmptyBulkReply.makeEmptyMultiBuckReply().toBytes();
        }

        return ("$" + arg.length + RespConstant.CRLF + new String(arg) + RespConstant.CRLF)
                .getBytes(SystemConfig.SYSTEM_CHARSET);
    }

    public static BulkReply makeBulkReply(byte[] arg) {
        return new BulkReply(arg);
    }
}
