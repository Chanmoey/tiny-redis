package com.moon.tinyredis.resp.reply;

import com.moon.tinyredis.resp.config.SystemConfig;
import com.moon.tinyredis.resp.reply.constant.NullBulkReply;

import java.util.Arrays;

/**
 * @author Chanmoey
 * @date 2023年02月20日
 */
public class MultiBulkReply implements Reply {

    private final byte[][] args;

    public MultiBulkReply(byte[][] args) {
        this.args = args;
    }

    @Override
    public byte[] toBytes() {

        int argLen = args.length;

        StringBuilder sb = new StringBuilder();
        sb.append("*").append(argLen).append(RespConstant.CRLF);

        for (byte[] arg : args) {
            if (arg == null) {
                sb.append(Arrays.toString(NullBulkReply.makeNullBulkReply().toBytes())).append(RespConstant.CRLF);
            } else {
                sb.append(Arrays.toString(BulkReply.makeBulkReply(arg).toBytes()));
            }
        }

        return sb.toString().getBytes(SystemConfig.SYSTEM_CHARSET);
    }


    public static MultiBulkReply makeMultiBulkReply(byte[][] args) {
        return new MultiBulkReply(args);
    }
}
