package cn.zull.lpc.learn.basic.jvm;

import com.google.common.collect.Maps;

import java.lang.ref.WeakReference;
import java.util.Map;

/**
 * @author zurun
 * @date 2022/8/1 14:44:37
 */
public class WeakReferenceTest {
    static Map<WeakReference<Integer>, WeakReference<Integer>> map = Maps.newHashMap();

    public static void main(String[] args) throws Exception {
        execute();
        Thread.sleep(3000);

    }

    public static void execute() throws InterruptedException {
        WeakReference<Integer> key = new WeakReference<>(666);
        WeakReference<Integer> value = new WeakReference<>(1111);
        map.put(key, value);
        System.gc();
        Thread.sleep(3000);
        System.out.println(map.get(key).get());
    }

}
