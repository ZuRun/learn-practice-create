package cn.zull.lpc.learn.basic.allocatememory;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * @author zurun
 * @date 2020/5/28 14:56:52
 */
public class AllocateMemoryTest {

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
        theUnsafe.setAccessible(true);
        Unsafe unsafe = (Unsafe) theUnsafe.get(null);
        unsafe.allocateMemory(1024);
        System.out.println("----------");
    }
}
