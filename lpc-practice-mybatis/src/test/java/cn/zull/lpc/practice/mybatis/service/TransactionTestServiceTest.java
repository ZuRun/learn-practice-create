package cn.zull.lpc.practice.mybatis.service;

import cn.zull.lpc.common.basis.utils.UUIDUtils;
import cn.zull.lpc.practice.mybatis.LpcMybatisPlusApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author zurun
 * @date 2020/4/13 16:24:53
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LpcMybatisPlusApplication.class)
public class TransactionTestServiceTest {

    @Autowired
    TransactionTestService transactionTestService;

    /**
     * 两个独立的事务,及时第二个回滚,不会影响第一个事务
     */
    @Test
    public void test1() {
        String uuid = UUIDUtils.simpleUUID();
        transactionTestService.insertTest1(uuid, false);
        System.out.println(uuid);
        String uuid2 = UUIDUtils.simpleUUID();
        transactionTestService.insertTest1(uuid2, true);
        System.out.println(uuid2);
    }

    /**
     * 并发操作数据 乐观锁 [不重复执行]
     * 注意将连接池改成和poolSize一致(包含最小连接数),减少连接数对结果的影响
     *
     * @throws InterruptedException
     */
    @Test
    public void test2() throws InterruptedException {
        int poolSize = 8;
        String uuid = "e2e7db94a3484a2e838642bdd2604e3d";
        CountDownLatch latch = new CountDownLatch(poolSize);

        ExecutorService executorService = Executors.newFixedThreadPool(poolSize);
        for (int i = 0; i < poolSize; i++) {
            executorService.execute(() -> {
                transactionTestService.updateWithVersion(uuid, "t____" + uuid);
                latch.countDown();
            });
        }
        latch.await();
    }

    /**
     * 并发操作数据 乐观锁 [重复执行]
     * 注意将连接池改成和poolSize一致(包含最小连接数),减少连接数对结果的影响
     *
     * @throws InterruptedException
     */
    @Test
    public void test3() throws InterruptedException {
        int poolSize = 8;
        String uuid = "e2e7db94a3484a2e838642bdd2604e3d";
        CountDownLatch latch = new CountDownLatch(poolSize);

        ExecutorService executorService = Executors.newFixedThreadPool(poolSize);
        for (int i = 0; i < poolSize; i++) {
            executorService.execute(() -> {
                log.info("[_] isInterrupted:{}", Thread.currentThread().isInterrupted());

                while (!Thread.currentThread().isInterrupted()) {
                    int updateCount = transactionTestService.updateWithVersion(uuid, "t____" + uuid);
                    if (updateCount > 0) {
                        break;
                    }
                    log.info("[获取乐观锁失败] isInterrupted:{}", Thread.currentThread().isInterrupted());
                }
                latch.countDown();
            });
        }
        latch.await();
    }
}