package cn.zull.lpc.common.basis.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;

import java.nio.charset.StandardCharsets;

/**
 * @author ZuRun
 * @date 2020/4/5 11:54:15
 */
@Slf4j
public class StringUtils {
    public static boolean isEmpty(@Nullable Object str) {
        return (str == null || "".equals(str));
    }

    public static String newStringUtf8(final byte[] bytes) {
        return new String(bytes, StandardCharsets.UTF_8);
    }

    public static byte[] getBytesUtf8(final String string) {
        return string.getBytes(StandardCharsets.UTF_8);
    }
}
