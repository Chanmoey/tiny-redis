package com.moon.tinyredis.resp.command.string;

import com.moon.tinyredis.resp.command.Command;
import com.moon.tinyredis.resp.config.SystemConfig;
import com.moon.tinyredis.resp.database.DB;
import com.moon.tinyredis.resp.datastructure.value.BulkValue;
import com.moon.tinyredis.resp.datastructure.value.Value;
import com.moon.tinyredis.resp.reply.BulkReply;
import com.moon.tinyredis.resp.reply.Reply;
import com.moon.tinyredis.resp.reply.constant.NullBulkReply;
import com.moon.tinyredis.resp.reply.error.CommonErrorReply;

/**
 * @author Chanmoey
 * @date 2023年02月28日
 */
public class GetSet extends Command {

    public GetSet(int arity) {
        super(arity);
    }

    @Override
    public Reply exec(DB db, byte[][] args) {
        String key = new String(args[0], SystemConfig.SYSTEM_CHARSET);
        byte[] value = args[1];
        Value old = db.getDict().get(key);
        db.getDict().put(key, new BulkValue(value));
        if (old == null) {
            return NullBulkReply.makeNullBulkReply();
        }

        if (old instanceof BulkValue bulkValue) {
            return BulkReply.makeBulkReply(bulkValue.getBulk());
        }

        // TODO: 支持其他数据类型
        return CommonErrorReply.makeCommonErrorReply("value is not string");
    }
}
