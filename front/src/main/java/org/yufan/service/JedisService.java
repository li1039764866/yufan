package org.yufan.service;


public interface JedisService {

    /**
     * 获取string的值
     * @param key
     * @return
     */
    public  String get(String key);

    /**
     * 设置string的值
     * @param key
     * @param value
     * @return
     */
    public String set(String key, String value);

    /**
     * 获取hash的值
     * @param hkey
     * @param key
     */
    public String hget(String hkey, String key);

    /**
     * 设置hash的值
     * @param hkey 哈希表
     * @param key 该表中的key
     * @param value 设置该表中key对应的value值
     * @return
     */
    public long hset(String hkey, String key, String value);

    /**
     * 递增key
     */
    public long incr(String key);

    /**
     * 设置失效时间
     * @param key
     * @param time 单位为秒
     * @return
     */
    public long expire(String key, int time);

    /**
     * 查看失效时间
     * @param key
     * @return
     */
    public long ttl(String key);


    /**
     * 删除指定key的value
     * @param key
     * @return
     */
    public long del(String key);

    /**
     * 删除hash某个key的值
     * @param hkey
     * @param key
     * @return
     */
    public long hdel(String hkey, String key);
}
