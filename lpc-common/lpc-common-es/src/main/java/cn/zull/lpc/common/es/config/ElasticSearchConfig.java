package cn.zull.lpc.common.es.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zurun
 * @date 2020/4/24 14:00:47
 */
@Slf4j
@Configuration
public class ElasticSearchConfig {

    @Value("${lpc.es.url}")
    private String hostlist;


    @Bean(destroyMethod = "close")
    public RestHighLevelClient restHighLevelClient() {
        //解析hostlist配置信息
        String[] split = hostlist.split(",");
        //创建HttpHost数组，其中存放es主机和端口的配置信息
        HttpHost[] httpHostArray = new HttpHost[split.length];
        for (int i = 0; i < split.length; i++) {
            String item = split[i];
            httpHostArray[i] = new HttpHost(item.split(":")[0], Integer.parseInt(item.split(":")[1]), "http");
        }
        RestHighLevelClient restHighLevelClient = new RestHighLevelClient(RestClient.builder(httpHostArray));
        log.info("已连成功连接最新版es 集群地址 host = {}", hostlist);
        return restHighLevelClient;
    }
}
