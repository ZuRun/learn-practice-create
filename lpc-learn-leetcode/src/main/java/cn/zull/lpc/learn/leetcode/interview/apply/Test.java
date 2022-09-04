package cn.zull.lpc.learn.leetcode.interview.apply;

import java.util.Random;

/**
 * @author zurun
 * @date 2022/9/4 10:05:53
 */
public class Test {
    public static void main(String[] args) {
        Random random = new Random();
        // 任务生产者
        new Thread(() -> {
            while (true) {

                int r = random.nextInt(100000);
                Task task = new Task();
                task.setName("task_" + r);
                task.setPriority(random.nextInt(5));
                task.setResourceNum(random.nextInt(5));
                TaskQueue.offer(task);
                try {
                    Thread.sleep(random.nextInt(200));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();

        // 资源生产者
        new Thread(() -> {
            while (true) {

                int r = random.nextInt(100000);
                Resource resource = new Resource();
                ResourcePool.offer(resource);
                try {
                    Thread.sleep(random.nextInt(100));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();



    }
}
