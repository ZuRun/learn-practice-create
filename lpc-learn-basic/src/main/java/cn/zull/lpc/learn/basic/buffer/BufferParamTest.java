package cn.zull.lpc.learn.basic.buffer;

import java.nio.ByteBuffer;

/**
 * 常见参数测试
 *
 * @author jared.zu
 * @date 2020/7/26 23:09:43
 */
public class BufferParamTest {
    public static void main(String[] args) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

        // 看一下初始时4个核心变量的值
        System.out.println("初始时-->limit--->" + byteBuffer.limit());
        System.out.println("初始时-->position--->" + byteBuffer.position());
        System.out.println("初始时-->capacity--->" + byteBuffer.capacity());
        System.out.println("初始时-->mark--->" + byteBuffer.mark());

        System.out.println("--------------------------------------");

        // 添加一些数据到缓冲区中
        String s = "Java3y";
        byteBuffer.put(s.getBytes());

        // 看一下初始时4个核心变量的值
        System.out.println("put完之后-->limit--->" + byteBuffer.limit());
        System.out.println("put完之后-->position--->" + byteBuffer.position());
        System.out.println("put完之后-->capacity--->" + byteBuffer.capacity());
        System.out.println("put完之后-->mark--->" + byteBuffer.mark());


        System.out.println("--------------------------------------");
        // 切换为read模式
        byteBuffer.flip();

        System.out.println("flip之后 --->" + byteBuffer);

        // 创建一个limit()大小的字节数组(因为就只有limit这么多个数据可读)
        int limit = byteBuffer.limit();
        byte[] bytes = new byte[limit];

        // 将读取的数据装进我们的字节数组中
        byteBuffer.get(bytes);

        // 输出数据
        System.out.println(new String(bytes, 0, bytes.length));

        System.out.println("--------------------------------------");
        // 切换为write模式
        byteBuffer.clear();
        byte[] bytes2 = new byte[limit];
        byteBuffer.get(bytes2);

        System.out.println("clear之后 --->" + byteBuffer + "   ; " + new String(bytes2, 0, bytes2.length));
        // 输出:  clear之后 --->java.nio.HeapByteBuffer[pos=6 lim=1024 cap=1024]   ; Java3y
        // 仅仅更改标记位,并未真的清空buffer
    }
}
