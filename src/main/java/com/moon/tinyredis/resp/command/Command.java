package com.moon.tinyredis.resp.command;

import com.moon.tinyredis.resp.database.DB;
import com.moon.tinyredis.resp.reply.Reply;

/**
 * @author Chanmoey
 * @date 2023年02月21日
 */
public abstract class Command {

    /**
     * 参数数量set k1 v1的参数数量为2
     * 0 表示不需要参数
     * -1 表示参数为变长参数，最小为1
     */
    protected int arity;

    protected Command(int arity) {
        this.arity = arity;
    }

    public abstract Reply exec(DB db, byte[][] args);

    public boolean validateArity(byte[][] args) {

        if (arity == 0) {
            return true;
        }

        if (args == null) {
            return false;
        }
        if (arity < 0) {
            return args.length >= Math.abs(arity);
        }

        return args.length == arity;
    }
}
