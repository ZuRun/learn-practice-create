package cn.zull.lpc.learn.basic.buffer;

import cn.zull.lpc.common.basis.utils.StringUtils;
import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zurun
 * @date 2020/5/28 15:24:52
 */
public class ByteBufferTest {
    public static void main(String args[]) throws FileNotFoundException {
        ByteBufferTest test = new ByteBufferTest();

        // byteBuffer对堆内存的影响
//        test.test1();
        // 一直申请新的堆外内存
//        test.incrementLoop();
        test.t();
    }

    /**
     * byteBuffer不占用jvm内存
     */
    public void test1() {
        System.out.println("----------Test allocate--------");
        System.out.println("before allocate:" + Runtime.getRuntime().freeMemory());

        // 如果分配的内存过小，调用Runtime.getRuntime().freeMemory()大小不会变化？
        // 要超过多少内存大小JVM才能感觉到？
        ByteBuffer buffer = ByteBuffer.allocateDirect(102400);
//        ByteBuffer buffer = ByteBuffer.allocate(102400);
        System.out.println("buffer = " + buffer.toString());

        System.out.println("after alocate:" + Runtime.getRuntime().freeMemory());

        // 这部分直接用的系统内存，所以对JVM的内存没有影响
        ByteBuffer directBuffer = ByteBuffer.allocateDirect(102400);
        System.out.println("directBuffer = " + directBuffer);
        System.out.println("after direct alocate:" + Runtime.getRuntime().freeMemory());

        System.out.println("----------Test wrap--------");
        byte[] bytes = new byte[32];
        buffer = ByteBuffer.wrap(bytes);
        System.out.println(buffer);

        buffer = ByteBuffer.wrap(bytes, 10, 10);
        System.out.println(buffer);
    }

    /**
     * 一直申请新的堆外内存
     */
    public void incrementLoop() {
        List<ByteBuffer> list = new ArrayList<>(2048);
        int i = 0;
        int step = 10;
        while (true) {
            ByteBuffer buffer = ByteBuffer.allocateDirect(10 * 1024 * 1024);
            buffer.get();
            // 添加到list列表中,此引用就不会被释放了,导致堆外内存溢出
//            list.add(buffer);
            i = i + step;
            System.out.println("增加堆外内存 共:" + i + "MB");
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
        }
    }


    public void t() {
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(10 * 1024 * 1024);
        Gson gson = new Gson();

        Map<String, String> map = new HashMap<>(16);
        map.put("name", "jared.zu");
        String json = gson.toJson(map);
        System.out.println("json=" + json);
        byteBuffer.put(json.getBytes());
        System.out.println("-");

        int length = json.length();
        byte[] get = new byte[length];
        byteBuffer.get(get, 0, length);
        System.out.println(":" + StringUtils.newStringUtf8(get));
        System.out.println("---");

    }
}
