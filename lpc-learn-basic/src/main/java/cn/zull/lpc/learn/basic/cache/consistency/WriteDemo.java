package cn.zull.lpc.learn.basic.cache.consistency;

/**
 *
 * 写操作
 * @author ZuRun
 * @date 2020/3/29 18:27:13
 */
public class WriteDemo {

    /**
     * 采用延时双删策略
     * 建议借助队列来执行双删除策略
     */
    public void delayDoubleDelete(String key,Object data){
//        redis.delKey(key);
//        db.updateData(data);
//        Thread.sleep(1000L);
//        redis.delKey(key);
    }
}
