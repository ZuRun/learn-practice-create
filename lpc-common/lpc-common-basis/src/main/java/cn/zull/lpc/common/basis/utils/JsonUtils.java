package cn.zull.lpc.common.basis.utils;

import com.google.gson.Gson;

/**
 * json工具类
 *
 * @author zurun
 * @date 2020/7/22 10:07:04
 */
public class JsonUtils {
    private static Gson gson = new Gson();

    public static String toJSONString(Object obj) {
        return gson.toJson(obj);
    }
}
