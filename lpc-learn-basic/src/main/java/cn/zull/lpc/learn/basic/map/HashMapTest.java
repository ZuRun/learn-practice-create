package cn.zull.lpc.learn.basic.map;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author jared.zu
 * @date 2020/8/23 14:43:24
 */
public class HashMapTest {
    public static void main(String[] args) {
        HashMap<String, String> map = new HashMap<>(16);
        map.put("k", "v");
        map.get("k");
        ConcurrentHashMap concurrentHashMap=new ConcurrentHashMap();
        concurrentHashMap.put("a",1);
        concurrentHashMap.remove("a");
    }
}
