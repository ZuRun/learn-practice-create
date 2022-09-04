package cn.zull.lpc.practice.test;

import cn.zull.lpc.common.basis.utils.Md5Utils;

/**
 * @author zurun
 * @date 2022/8/25 00:45:28
 */
public class NioSignal {
    public static void main(String[] args) {
        StringBuilder sb = new StringBuilder();
        sb.append("GET/c/award_cn/checkin".toUpperCase()).append("?");
        sb.append("app_id=10002&app_ver=5.5.0&device_id=7b27ca12975b4c9d94cc99dcf41b248a&lang=zh-cn&region=cn&timestamp=1658213260");
        sb.append("d9E1Bb7b98163ba9a96a390cdedbd02d");
        sb.append("Bearer 2.0SPVJwO2lHzdSL5kLTVXWUeptfgxVL2q/iZHuGcIwuqM=");
        String e = Md5Utils.encrypt(sb.toString());
        System.out.println(e);
    }
}
