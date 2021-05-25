package cn.zull.lpc.practice.test.tmp;

/**
 * @author Jared.Zu
 * @date 2020/11/10 11:31:14
 */
public class TempTest {
    public static void main(String[] args) {
        long timestamp=1393633000000L;
        timestamp = timestamp - (1393632000000L & 2199023255551L);
        System.out.println((1393632000000L & 2199023255551L));
        System.out.println(timestamp);
    }
}
