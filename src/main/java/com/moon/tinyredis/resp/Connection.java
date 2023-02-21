package com.moon.tinyredis.resp;

/**
 * 表示一个Redis连接
 *
 * @author Chanmoey
 * @date 2023年02月20日
 */
public interface Connection {

    int getDBIndex();

    void selectDB(int index);
}
