package com.moon.tinyredis.resp.datastructure.dick;

import com.moon.tinyredis.resp.datastructure.algo.Shuffle;
import com.moon.tinyredis.resp.datastructure.value.Value;

import java.util.HashMap;
import java.util.Random;
import java.util.function.BiConsumer;

/**
 * 非并发安全
 *
 * @author Chanmoey
 * @date 2023年02月21日
 */
public class HashDict implements Dict {

    /**
     * 因为保证只有单线程操作这个map，所以不需要用并发安全的Hashmap
     */
    private HashMap<String, Value> map;

    /**
     * 最多容纳多少个元素，为了以后缓存置换做准备
     */
    private final int limitSize;

    public HashDict() {
        this(10240000);
    }

    public HashDict(int limitSize) {
        this.limitSize = limitSize;
        this.map = new HashMap<>();
    }


    @Override
    public Value get(String key) {
        return map.get(key);
    }

    @Override
    public int len() {
        return map.size();
    }

    @Override
    public int put(String key, Value value) {

        if (map.size() >= limitSize) {
            // 缓存已满，插入失败
            return 0;
        }

        // 如果key存在，则这次put未修改，返回0，否则为新添加，返回1。
        int res = 1;
        if (map.containsKey(key)) {
            res = 0;
        }

        map.put(key, value);
        return res;
    }

    @Override
    public int putIfAbsent(String key, Value value) {
        if (map.size() >= limitSize) {
            return 0;
        }
        return map.putIfAbsent(key, value) == null ? 0 : 1;
    }

    @Override
    public int putIfExists(String key, Value value) {
        if (!map.containsKey(key)) {
            return 0;
        }
        map.put(key, value);
        return 1;
    }

    @Override
    public int remove(String key) {
        return map.remove(key) == null ? 0 : 1;
    }

    @Override
    public void forEach(BiConsumer<String, Value> action) {
        map.forEach(action);
    }

    @Override
    public String[] keys(String regex) {
        String[] keySet = map.keySet().toArray(new String[0]);
        if ("*".equals(regex)) {
            return keySet;
        }
        int idx = 0;
        for (int i = 0; i < keySet.length; i++) {
            if (keySet[i].matches(regex)) {
                keySet[idx++] = keySet[i];
            }
        }

        String[] res = new String[idx];
        System.arraycopy(keySet, 0, res, 0, idx);
        return res;
    }

    private static final Random RANDOM = new Random();

    /**
     * 可以重复
     */
    @Override
    public String[] randomKeys(int limit) {
        String[] res = new String[limit];
        String[] keySets = map.keySet().toArray(new String[0]);
        for (int i = 0; i < limit; i++) {
            res[0] = keySets[RANDOM.nextInt(map.size())];
        }

        return res;
    }

    @Override
    public String[] randomDistinctKeys(int limit) {

        String[] keySets = map.keySet().toArray(new String[0]);

        if (limit >= map.size()) {
            return keySets;
        }
        String[] res = new String[limit];
        Shuffle.shuffle(keySets);
        System.arraycopy(keySets, 0, res, 0, limit);
        return res;
    }

    @Override
    public void clear() {
        this.map = new HashMap<>();
    }
}
