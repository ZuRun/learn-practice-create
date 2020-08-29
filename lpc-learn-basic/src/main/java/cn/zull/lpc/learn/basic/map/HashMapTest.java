package cn.zull.lpc.learn.basic.map;

import java.util.HashMap;

/**
 * @author jared.zu
 * @date 2020/8/23 14:43:24
 */
public class HashMapTest {
    public static void main(String[] args) {
        HashMap<String, String> map = new HashMap<>(16);
        map.put("k", "v");
        map.get("k");
    }
}
