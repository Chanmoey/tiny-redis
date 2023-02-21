package com.moon.tinyredis.resp.reply.error;

import java.nio.charset.StandardCharsets;

/**
 * @author Chanmoey
 * @date 2023年02月20日
 */
public class ArgNumberErrorReply extends AbstractErrorReplay{

    private final String cmd;

    public ArgNumberErrorReply(String cmd) {
        super("Arg Number Error");
        this.cmd = cmd;
    }

    @Override
    public byte[] toBytes() {
        return ("-Err wrong number of arguments for '" + cmd + "' command\r\n")
                .getBytes(StandardCharsets.UTF_8);
    }

    public static ArgNumberErrorReply makeArgNumberErrorReply(String cmd) {
        return new ArgNumberErrorReply(cmd);
    }
}
