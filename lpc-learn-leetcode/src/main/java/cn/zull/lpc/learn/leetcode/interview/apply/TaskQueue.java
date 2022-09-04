package cn.zull.lpc.learn.leetcode.interview.apply;

import java.util.Deque;
import java.util.LinkedList;

/**
 * @author zurun
 * @date 2022/9/4 09:21:13
 */
public class TaskQueue {

    private static final Deque<Task> deque = new LinkedList<>();

    public static void offer(Task task) {
        deque.offerLast(task);
        DispatchPool.signal();
    }

    public static Task poll() {
        return deque.pollFirst();
    }

    public static int size() {
        return deque.size();
    }
}
