package com.moon.tinyredis.resp.command.keys;

import com.moon.tinyredis.resp.command.Command;
import com.moon.tinyredis.resp.config.SystemConfig;
import com.moon.tinyredis.resp.database.DB;
import com.moon.tinyredis.resp.reply.MultiBulkReply;
import com.moon.tinyredis.resp.reply.Reply;

import java.nio.charset.StandardCharsets;

/**
 * @author Chanmoey
 * @date 2023年02月22日
 */
public class Keys extends Command {

    public Keys(int arity) {
        super(arity);
    }

    @Override
    public Reply exec(DB db, byte[][] args) {
        String pattern = new String(args[0], SystemConfig.SYSTEM_CHARSET);
        String[] keys = db.getDict().keys(pattern);

        byte[][] res = new byte[keys.length][];
        for (int i = 0; i < keys.length; i++) {
            res[i] = keys[i].getBytes(SystemConfig.SYSTEM_CHARSET);
        }
        return MultiBulkReply.makeMultiBulkReply(res);
    }
}
