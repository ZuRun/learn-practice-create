package cn.zull.lpc.practice.es.service;

import cn.zull.lpc.common.basis.utils.JsonUtils;
import cn.zull.lpc.common.basis.utils.UUIDUtils;
import cn.zull.lpc.practice.es.model.LogModel;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author jared.zu
 * @date 2020/8/22 21:22:50
 */
@Slf4j
@Service
public class TestWriteSpeedService implements CommandLineRunner {
    @Autowired
    RestHighLevelClient restHighLevelClient;
    private final int poolSize = 100;
    private final AtomicInteger sum = new AtomicInteger(0);
    ThreadPoolExecutor executorService = (ThreadPoolExecutor) Executors.newFixedThreadPool(poolSize);

    {
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> {
            System.out.println(System.currentTimeMillis() + " active: " + executorService.getActiveCount() + " 消费条数:" + sum.getAndSet(0));
        }, 0, 1, TimeUnit.SECONDS);
    }

    @Override
    public void run(String... args) throws Exception {

        for (int i = 0; i < 100; i++) {
            executorService.execute(() -> {
                while (true) {
                    String uuid = UUIDUtils.simpleUUID();
                    long now = System.currentTimeMillis();
                    List<Map<String, String>> list = new ArrayList<>(100);
                    for (int j = 0; j < 100; j++) {
                        LogModel logModel = new LogModel()
                                .setClassName("cn.zull.lpc.practice.log2kafka.controller.LogController")
                                .setLevel("info")
                                .setTName("http-nio-8080-exec-80")
                                .setTraceId(uuid)
                                .setTimestamp(String.valueOf(now))
                                .setMessage("[tt]_" + uuid + "_" + uuid);
                        String json = JsonUtils.toJSONString(logModel);
                        Map map = JsonUtils.json2Map(json);
                        list.add(map);
                    }
                    bulkInsertDocs("lpc_log", list);
                    sum.addAndGet(100);
                }
            });
        }

    }


    /**
     * 批量写入ES
     *
     * @param index     索引
     * @param batchDocs 日志内容
     */
    private void bulkInsertDocs(String index, List<Map<String, String>> batchDocs) {
        String time = String.valueOf(System.currentTimeMillis());
        if (CollectionUtils.isEmpty(batchDocs)) {
            return;
        }
        boolean hasFail;
        BulkRequest bulkRequest = new BulkRequest();
        for (Map<String, String> batchDoc : batchDocs) {
            // 写入es时间
            batchDoc.put("t_w2es", time);
//            log.info("写es数据_"+JsonUtils.toJSONString(batchDoc));

            bulkRequest.add(new IndexRequest(index).source(batchDoc));
        }
        bulkRequest.timeout(TimeValue.timeValueMillis(15000));

        BulkResponse response = null;
        try {
            response = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
//            if (response.hasFailures()) {
//                log.info("写入ES数据结果 response = {}", response);
//            }
            hasFail = response.hasFailures();
        } catch (IOException e) {
            e.printStackTrace();
            hasFail = true;
        }
        if (hasFail) {
            log.warn("bulk insert ELS fail " + response.buildFailureMessage());
        }
    }
}
