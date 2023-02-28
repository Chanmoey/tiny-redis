package com.moon.tinyredis.resp.command.string;

import com.moon.tinyredis.resp.command.Command;
import com.moon.tinyredis.resp.config.SystemConfig;
import com.moon.tinyredis.resp.database.DB;
import com.moon.tinyredis.resp.datastructure.value.BulkValue;
import com.moon.tinyredis.resp.datastructure.value.Value;
import com.moon.tinyredis.resp.reply.IntegerReply;
import com.moon.tinyredis.resp.reply.Reply;
import com.moon.tinyredis.resp.reply.constant.NullBulkReply;
import com.moon.tinyredis.resp.reply.error.CommonErrorReply;

/**
 * @author Chanmoey
 * @date 2023年02月28日
 */
public class StrLen extends Command {

    public StrLen(int arity) {
        super(arity);
    }

    @Override
    public Reply exec(DB db, byte[][] args) {
        String key = new String(args[0], SystemConfig.SYSTEM_CHARSET);
        Value value = db.getDict().get(key);
        if (value == null) {
            return NullBulkReply.makeNullBulkReply();
        }
        if (!(value instanceof BulkValue)) {
            return CommonErrorReply.makeCommonErrorReply("the value is not string");
        }
        return IntegerReply.makeStatusReply(((BulkValue) value).getBulk().length);
    }
}
