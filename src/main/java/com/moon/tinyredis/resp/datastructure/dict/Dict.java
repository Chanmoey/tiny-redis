package com.moon.tinyredis.resp.datastructure.dict;

import com.moon.tinyredis.resp.datastructure.value.Value;

import java.util.function.BiConsumer;

/**
 * @author Chanmoey
 * @date 2023年02月21日
 */
public interface Dict {

    Value get(String key);

    int len();

    int put(String key, Value value);

    int putIfAbsent(String key, Value value);

    int putIfExists(String key, Value value);

    int remove(String key);

    void forEach(BiConsumer<String, Value> action);

    String[] keys(String regex);

    String[] randomKeys(int limit);

    String[] randomDistinctKeys(int limit);

    void clear();
}
