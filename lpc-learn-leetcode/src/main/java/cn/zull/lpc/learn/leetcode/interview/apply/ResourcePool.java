package cn.zull.lpc.learn.leetcode.interview.apply;

import java.util.Deque;
import java.util.LinkedList;

/**
 * @author zurun
 * @date 2022/9/4 09:41:03
 */
public class ResourcePool {
    private static final Deque<Resource> deque = new LinkedList<>();

    public static void offer(Resource resource) {
        deque.offerLast(resource);
        DispatchPool.signal();
    }

    public static Resource poll() {
        return deque.pollFirst();
    }

    public static int size() {
        return deque.size();
    }
}
