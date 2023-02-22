package com.moon.tinyredis.resp.command;

import com.moon.tinyredis.resp.database.DB;
import com.moon.tinyredis.resp.reply.Reply;
import com.moon.tinyredis.resp.reply.constant.PongReply;

/**
 * @author Chanmoey
 * @date 2023年02月22日
 */
public class Ping extends Command{

    protected Ping(int arity) {
        super(arity);
    }

    @Override
    public Reply exec(DB db, byte[][] args) {
        return PongReply.makePonyReplay();
    }

    /**
     * ping指令
     * @param args 可以为null
     */
    @Override
    public boolean validateArity(byte[][] args) {
        return args == null || args.length == arity;
    }
}
