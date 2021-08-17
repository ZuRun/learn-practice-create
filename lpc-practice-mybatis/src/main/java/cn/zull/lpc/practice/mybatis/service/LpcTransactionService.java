package cn.zull.lpc.practice.mybatis.service;

import cn.zull.lpc.common.basis.utils.UUIDUtils;
import cn.zull.lpc.common.threadpool.ParallelExecutor;
import cn.zull.lpc.practice.mybatis.entity.LpcDeviceInfo;
import cn.zull.lpc.practice.mybatis.mapper.LpcDeviceInfoMapper;
import com.google.common.base.Stopwatch;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.util.StopWatch;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author Jared.Zu
 * @date 2021/6/9 15:20:58
 */
@Slf4j
@Service
public class LpcTransactionService {
    @Autowired
    LpcDeviceInfoMapper lpcDeviceInfoMapper;
    @Autowired
    SqlSessionFactory sqlSessionFactory;


    ThreadPoolExecutor executor = new ThreadPoolExecutor(50, 50,
            1, TimeUnit.MINUTES,
            new LinkedBlockingQueue<Runnable>(2000));
    @Autowired
    ParallelExecutor parallelExecutor;

    @Transactional
    public void test() {
        int size = 99;
        LpcDeviceInfo lpcDeviceInfo1 = LpcDeviceInfo.builder().did(UUIDUtils.simpleUUID()).remark("~").sum(0).build();
//        lpcDeviceInfoMapper.insert(lpcDeviceInfo1);
        StopWatch stopwatch = new StopWatch();
        stopwatch.start();
        List<Runnable> runnableList = new ArrayList<>(size);
        sqlSessionFactory.openSession(ExecutorType.BATCH);
        for (int i = 0; i < size; i++) {
            final int m = i;
            LpcDeviceInfo lpcDeviceInfo = LpcDeviceInfo.builder().did(UUIDUtils.simpleUUID()).remark("~").sum(0).build();
//            runnableList.add(() -> {
                lpcDeviceInfoMapper.insert(lpcDeviceInfo);
//            });
        }
        try {
            parallelExecutor.batchExecute(runnableList).get();
        } catch (InterruptedException e) {
            log.warn("failed---------------");
            throw new RuntimeException("failed");
        } finally {

            stopwatch.stop();
            System.out.println("---------" + stopwatch.getTotalTimeMillis());
        }

    }

    public void test2() {
//        Object threadLocalMap = getThreadLocalMap();
        int size = 2000;
        CountDownLatch countDownLatch = new CountDownLatch(size);
        AtomicBoolean failed = new AtomicBoolean(false);
        LpcDeviceInfo lpcDeviceInfo1 = LpcDeviceInfo.builder().did(UUIDUtils.simpleUUID()).remark("~").sum(0).build();
        lpcDeviceInfoMapper.insert(lpcDeviceInfo1);
        Map<Object, Object> resourceMap = TransactionSynchronizationManager.getResourceMap();
        StopWatch stopwatch = new StopWatch();
        stopwatch.start();
        for (int i = 0; i < size; i++) {
            final int m = i;
            LpcDeviceInfo lpcDeviceInfo = LpcDeviceInfo.builder().did(UUIDUtils.simpleUUID()).remark("~").sum(0).build();
            executor.execute(() -> {
                try {
                    if (failed.get()) {
                        return;
                    }
//                    copyThreadLocals(threadLocalMap);
                    resourceMap.forEach(TransactionSynchronizationManager::bindResource);
//                    if (m == 51) {
//                        throw new RuntimeException("llllll");
//                    }
                    lpcDeviceInfoMapper.insert(lpcDeviceInfo);

                } catch (Exception e) {
                    e.printStackTrace();
                    failed.set(true);
                } finally {
                    countDownLatch.countDown();
                    TransactionSynchronizationManager.clear();
                    resourceMap.forEach((k, v) -> TransactionSynchronizationManager.unbindResourceIfPossible(k));
                }
            });
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            Thread.interrupted();
            e.printStackTrace();
        }
        if (failed.get()) {
            log.warn("failed---------------");
            throw new RuntimeException("failed");

        }
        stopwatch.stop();
        System.out.println("---------" + stopwatch.getTotalTimeMillis());

    }

    private void copyThreadLocals(Object threadLocalMap) {
//
        try {
            Thread thread = Thread.currentThread();
            Field threadLocalsField = Thread.class.getDeclaredField("threadLocals");
            threadLocalsField.setAccessible(true);
            threadLocalsField.set(thread, threadLocalMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Object getThreadLocalMap() {
        Map<ThreadLocal, Object> threadLocals = new HashMap<>();
        Thread thread = Thread.currentThread();
        Object threadLocalMap = null;
        try {
            Field threadLocalsField = Thread.class.getDeclaredField("threadLocals");
            threadLocalsField.setAccessible(true);
            threadLocalMap = threadLocalsField.get(thread);
            return threadLocalMap;
//            Field tableField = threadLocalMap.getClass().getDeclaredField("table");
//            tableField.setAccessible(true);
//            Object[] table = (Object[]) tableField.get(threadLocalMap);
//            for (int i = 0; i < table.length; i++) {
//                Object entry = table[i];
//                if (entry != null) {
//                    WeakReference<ThreadLocal> threadLocalRef = (WeakReference<ThreadLocal>) entry;
//                    ThreadLocal threadLocal = threadLocalRef.get();
//                    if (threadLocal != null) {
//                        Object threadLocalValue = threadLocal.get();
//                        threadLocals.put(threadLocal, threadLocalValue);
//                    }
//                }
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return threadLocalMap;
    }

    public static void resetThreadLocals(Map<ThreadLocal, Object> threadLocals) {
        if (threadLocals == null) {
            return;
        }
        for (Map.Entry<ThreadLocal, Object> entry : threadLocals.entrySet()) {
            ThreadLocal tl = entry.getKey();
            Object value = entry.getValue();
            tl.set(value);
        }
        threadLocals.clear();
    }

    public static void main(String[] args) {
        for (int i = 0; i < 300; i++) {
            System.out.println("INSERT INTO `points_detail` ( `points_code`, `points_pool_code`, `unique_id`, `points_id`, `points_type`, `scene_id`, `source_id`, `sub_source_id`, `source_name`, `biz_flow_id`, `total_points`, `withdrawing_points`, `withdrawn_points`, `given_points`, `remain_points`, `user_id`, `biz_line`, `expire_at`, `occur_at`, `status`, `remark`, `is_deleted`, `version`, `updated_at`, `created_at`, `sub_source_code`) VALUES (" + 102765047482089472L + i + ", 'EA1DF3', '574', 1802765047373037568, 2, 'LALA_FESTIVAL', '12820000501', '30', '红包雨', '574', 10, 0, 0, 0, 10, '12820000501', '', '2020-12-24 00:00:00', '2020-12-21 16:12:20', 2, '', 0, 26, '2020-12-24 18:16:51', '2020-12-21 16:12:21', 'code_30');\r\n");
        }
    }

}
