package com.moon.tinyredis.resp.command.keys;

import com.moon.tinyredis.resp.command.Command;
import com.moon.tinyredis.resp.database.DB;
import com.moon.tinyredis.resp.reply.Reply;
import com.moon.tinyredis.resp.reply.constant.OkReply;

/**
 * @author Chanmoey
 * @date 2023年02月22日
 */
public class FlushDB extends Command {
    public FlushDB(int arity) {
        super(arity);
    }

    /**
     * 指令能直接调用Dick的方法吗？要不要在DB里面封装一层？
     */
    @Override
    public Reply exec(DB db, byte[][] args) {
        db.getDict().clear();
        return OkReply.makeOkReplay();
    }
}
