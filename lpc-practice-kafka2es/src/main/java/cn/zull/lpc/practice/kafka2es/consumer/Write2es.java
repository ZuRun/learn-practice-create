package cn.zull.lpc.practice.kafka2es.consumer;

import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author zurun
 * @date 2020/8/17 17:23:00
 */
@Slf4j
@Component
public class Write2es {

    @Resource
    private RestHighLevelClient client;

    @Value("${es.write.timeout:15000}")
    private Integer writeTimeOut;

    /**
     * es批量提交数量
     */
    @Value("${es.write.batchSize:20}")
    private Integer esBatchSubmitSize = 20;

    public void batchInsertEs(String index, List<Map<String, String>> batchDocs) {
        bulkInsertDocs(index, batchDocs);
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
        bulkRequest.timeout(TimeValue.timeValueMillis(writeTimeOut));

        BulkResponse response = null;
        try {
            response = client.bulk(bulkRequest, RequestOptions.DEFAULT);
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
