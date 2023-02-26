package com.moon.tinyredis.resp.command.keys;

import com.moon.tinyredis.resp.command.Command;
import com.moon.tinyredis.resp.config.SystemConfig;
import com.moon.tinyredis.resp.database.DB;
import com.moon.tinyredis.resp.datastructure.value.Value;
import com.moon.tinyredis.resp.reply.Reply;
import com.moon.tinyredis.resp.reply.constant.OkReply;
import com.moon.tinyredis.resp.reply.error.CommonErrorReply;
import com.moon.tinyredis.resp.reply.error.ErrorReply;

import java.nio.charset.StandardCharsets;

/**
 * @author Chanmoey
 * @date 2023年02月22日
 */
public class Rename extends Command {

    public Rename(int arity) {
        super(arity);
    }

    /**
     * 先删，再插
     */
    @Override
    public Reply exec(DB db, byte[][] args) {
        String oldKey = new String(args[0], SystemConfig.SYSTEM_CHARSET);
        Value v = db.getDict().get(oldKey);
        if (v == null) {
            return CommonErrorReply.makeCommonErrorReply("no such key");
        }
        String newKey = new String(args[1], SystemConfig.SYSTEM_CHARSET);
        db.getDict().put(newKey, v);
        db.getDict().remove(oldKey);
        return OkReply.makeOkReplay();
    }
}
