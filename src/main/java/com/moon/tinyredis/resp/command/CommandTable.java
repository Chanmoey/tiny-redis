package com.moon.tinyredis.resp.command;

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

    private HashMap<String, Command> map = new HashMap<>();

    public void register(String key, Command command) {
        // 统一转小写，避免大小写匹配失败
        key = key.toLowerCase();
        map.put(key, command);
    }

    public Command getCommand(String key) {
        key = key.toLowerCase();
        return map.get(key);
    }
}