package cn.zull.lpc.learn.basic.thread.pool;

/**
 * @author zurun
 * @date 2020/7/6 19:49:10
 */
public class ThreadTest {
    public static void main(String[] args) throws InterruptedException {
        System.out.println(1<<30);
        System.out.println("--start--");
        Thread t = new Thread(() -> {
            System.out.println("[t1] !!!");
            if (1 == 1) {
                throw new RuntimeException("测试线程中断");
            }
        });
        System.out.println("[线程状态1] "+t.getState());

        t.start();
        System.out.println("--end--");
        Thread.sleep(2000L);
        System.out.println("[线程状态2] "+t.getState());
    }
}
