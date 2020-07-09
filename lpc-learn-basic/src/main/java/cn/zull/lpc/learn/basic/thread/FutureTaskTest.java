package cn.zull.lpc.learn.basic.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * @author zurun
 * @date 2020/7/6 20:04:59
 */
public class FutureTaskTest {
    public static void main(String[] args) throws Exception {
        Callable<String> callable = () -> {
            System.out.println("todo 1");
            Thread.sleep(2000L);
            System.out.println("todo 2");
            return "todo1";
        };

        FutureTask<String> ft = new FutureTask<>(callable);
        Thread t = new Thread(ft);
        t.start();
        String res = ft.get();
        System.out.println("res: " + res);


    }
}
