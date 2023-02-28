package com.moon.tinyredis.resp.command;

import com.moon.tinyredis.resp.command.keys.*;
import com.moon.tinyredis.resp.command.ping.Ping;
import com.moon.tinyredis.resp.command.string.Get;
import com.moon.tinyredis.resp.command.string.GetSet;
import com.moon.tinyredis.resp.command.string.Set;
import com.moon.tinyredis.resp.command.string.SetNX;

import java.util.HashMap;

/**
 * @author Chanmoey
 * @date 2023年02月21日
 */
public class CommandTable {

    private static final CommandTable INSTANCE = new CommandTable();

    private CommandTable() {
    }

    public static CommandTable getCommandTable() {
        return INSTANCE;
    }

    private final HashMap<String, Command> map = new HashMap<>();

    public void register(String key, Command command) {
        // 统一转小写，避免大小写匹配失败
        key = key.toLowerCase();
        map.put(key, command);
    }

    public Command getCommand(String key) {
        key = key.toLowerCase();
        return map.get(key);
    }

    public static void initTable() {
        CommandTable commandTable = CommandTable.getCommandTable();
        commandTable.register("ping", new Ping(0));
        commandTable.register("del", new Del(-1));
        commandTable.register("exists", new Exists(-1));
        commandTable.register("flushdb", new FlushDB(0));
        commandTable.register("type", new Type(1));
        commandTable.register("rename", new Rename(2));
        commandTable.register("renamenx", new RenameNX(2));
        commandTable.register("keys", new Keys(1));

        // string类型的指令
        commandTable.register("get", new Get(1));
        commandTable.register("set", new Set(2));
        commandTable.register("setnx", new SetNX(2));
        commandTable.register("getset", new GetSet(2));
    }
}
