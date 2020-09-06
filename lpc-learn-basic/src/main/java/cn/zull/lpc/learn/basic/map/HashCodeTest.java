package cn.zull.lpc.learn.basic.map;

/**
 * @author jared.zu
 * @date 2020/8/30 15:17:07
 */
public class HashCodeTest {
    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            Object a1=new Object();


            System.out.println(a1.hashCode());
        }

    }
}
