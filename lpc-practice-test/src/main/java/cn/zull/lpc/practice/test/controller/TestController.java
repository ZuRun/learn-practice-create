package cn.zull.lpc.practice.test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Jared.Zu
 * @date 2020/11/18 10:54:00
 */
@RestController
public class TestController {

    //    @Autowired
    ThreadPoolExecutor executors = new ThreadPoolExecutor(10, 20, 1, TimeUnit.MINUTES,
            new LinkedBlockingQueue(10));
    Lock lock = new ReentrantLock();

    @RequestMapping("test1")
    public void test1() throws InterruptedException {
        Thread.sleep(10_000);
    }

    @RequestMapping("test3")
    public void test3() throws InterruptedException {
        lock.lock();
    }


    @RequestMapping("test4")
    public void test34() throws InterruptedException {
        lock.tryLock(10_000,TimeUnit.SECONDS);
    }

    @RequestMapping("test2")
    public void test2() throws InterruptedException {
        executors.execute(() -> {
            try {
                Thread.sleep(10_000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        });
    }
}
