package cn.zull.lpc.learn.basic.io.nio;

import cn.zull.lpc.common.basis.utils.UUIDUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;

/**
 * @author jared.zu
 * @date 2020/4/14 22:07:14
 */
public class Test {
    private final static String filePath = "/Users/zurun/Downloads/nio_test.txt";

    public static void main(String[] args) throws IOException {
        bio();


    }

    private static void bio() throws IOException {
        File file = new File(filePath);
        StringBuffer sb = new StringBuffer(150)
                .append(LocalDateTime.now().toString())
                .append("\r\n")
                .append(UUIDUtils.simpleUUID())
                .append("\r\n")
                .append(UUIDUtils.simpleUUID())
                .append("\r\n")
                .append("----------------------")
                .append("\r\n");
        try (OutputStream outputStream = new FileOutputStream(file,true)) {
            outputStream.write(sb.toString().getBytes());
        }

    }
}
