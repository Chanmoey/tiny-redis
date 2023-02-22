package com.moon.tinyredis.resp.command;

import com.moon.tinyredis.resp.command.keys.Del;
import com.moon.tinyredis.resp.command.ping.Ping;

import java.util.HashMap;

/**
 * @author Chanmoey
 * @date 2023年02月21日
 */
public class CommandTable {

    private static final CommandTable INSTANCE = new CommandTable();

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
        commandTable.register("exists", new Del(-1));
        commandTable.register("flushdb", new Del(0));
        commandTable.register("type", new Del(1));
    }
}
