package com.moon.tinyredis.resp.command.keys;

import com.moon.tinyredis.resp.command.Command;
import com.moon.tinyredis.resp.database.DB;
import com.moon.tinyredis.resp.reply.IntegerReply;
import com.moon.tinyredis.resp.reply.Reply;

import java.nio.charset.StandardCharsets;

/**
 * @author Chanmoey
 * @date 2023年02月22日
 */
public class Exists extends Command {

    public Exists(int arity) {
        super(arity);
    }

    @Override
    public Reply exec(DB db, byte[][] args) {
        int res = 0;
        for (byte[] arg : args) {
            String key = new String(arg, StandardCharsets.UTF_8);
            if (db.getDict().get(key) != null) {
                res++;
            }
        }
        return IntegerReply.makeStatusReply(res);
    }
}
