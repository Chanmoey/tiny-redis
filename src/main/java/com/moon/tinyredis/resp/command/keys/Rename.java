package com.moon.tinyredis.resp.command.keys;

import com.moon.tinyredis.resp.command.Command;
import com.moon.tinyredis.resp.database.DB;
import com.moon.tinyredis.resp.reply.Reply;

/**
 * @author Chanmoey
 * @date 2023年02月22日
 */
public class Rename extends Command {

    public Rename(int arity) {
        super(arity);
    }

    @Override
    public Reply exec(DB db, byte[][] args) {
        return null;
    }
}
