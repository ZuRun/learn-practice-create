package cn.zull.lpc.learn.basic.concurrent;

/**
 * @author ZuRun
 * @date 2020/9/10 20:12:00
 */
public class CasTest {
    private static int i = 10;

    public static void main(String[] args) {
        for (int j = 0; j < 100; j++) {
            new Thread(runnable).start();

        }

    }

    private static Runnable runnable = () -> {
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        i = i + 1;
    };
}
