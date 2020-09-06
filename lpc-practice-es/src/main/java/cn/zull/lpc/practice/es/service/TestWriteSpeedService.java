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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
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
//@Service
public class TestWriteSpeedService implements CommandLineRunner {
    @Value("${lpc.p.es.index}")
    private String index;
    @Value("${lpc.p.es.poolSize:100}")
    private int poolSize;
    @Value("${lpc.p.es.batchSize:200}")
    private int batchSize;

    @Autowired
    RestHighLevelClient restHighLevelClient;
    private final AtomicInteger sum = new AtomicInteger(0);


    @Override
    public void run(String... args) throws Exception {
        ThreadPoolExecutor executorService = (ThreadPoolExecutor) Executors.newFixedThreadPool(poolSize);
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> {
            System.out.println(System.currentTimeMillis() + " active: " + executorService.getActiveCount() + " 消费条数:" + sum.getAndSet(0));
        }, 0, 1, TimeUnit.SECONDS);

        for (int i = 0; i < poolSize; i++) {
            executorService.execute(() -> {
                while (true) {
                    String uuid = UUIDUtils.simpleUUID();
                    long now = System.currentTimeMillis();
                    int m = batchSize;
                    List<Map<String, String>> list = new ArrayList<>(m);
                    for (int j = 0; j < m; j++) {
                        LogModel logModel = new LogModel()
                                .setClassName("cn.zull.lpc.practice.log2kafka.controller.LogController")
                                .setLevel("info")
                                .setTName("http-nio-8080-exec-80")
                                .setTraceId(uuid)
                                .setTimestamp(String.valueOf(now))
//                                .setMessage("[tt]_" + uuid + "_" + uuid)
                                .setMessage("[根据平台账号查询手机号]: 根据 key=iot:a:ac:" + uuid + " 从 redis 中查询的手机号为：tyAccount=15107777176");
                        String json = JsonUtils.toJSONString(logModel);
                        Map map = JsonUtils.json2Map(json);
                        list.add(map);
                    }
                    bulkInsertDocs(index, list);
                    sum.addAndGet(m);
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
        long now = System.currentTimeMillis();
        String time = String.valueOf(now);
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
//            hasFail = response.hasFailures();
        } catch (IOException e) {
            e.printStackTrace();
//            hasFail = true;
        }
        log.debug("[耗时] {}", System.currentTimeMillis() - now);
//        if (hasFail) {
//            log.warn("bulk insert ELS fail " + response.buildFailureMessage());
//        }
    }
}
