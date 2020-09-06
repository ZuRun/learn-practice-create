package cn.zull.lpc.practice.es.service;

import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.ElasticsearchStatusException;
import org.elasticsearch.action.admin.indices.alias.IndicesAliasesRequest;
import org.elasticsearch.action.admin.indices.alias.get.GetAliasesRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.GetAliasesResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.cluster.metadata.AliasMetaData;
import org.elasticsearch.rest.RestStatus;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;

/**
 * 定时创建索引任务
 *
 * @author jared.zu
 * @date 2020/9/6 17:25:47
 */
@Slf4j
@Service
public class EsIndexScheduleService implements CommandLineRunner {

    @Resource
    private RestHighLevelClient restHighLevelClient;

    String alias = "test_alias";
    String _currentIndex = "lpc_test3";
    String _nextIndex = "lpc_test2";

    @Override
    public void run(String... args) throws Exception {
        System.out.println("---------");
//        pre();
        schedule(_currentIndex, _nextIndex);
//        test();
    }


    /**
     * 服务启动时,需检查索引是否存在,如不存在则创建
     * <p>
     * 1. 检查是否存在模板
     * 2. 检查是否存在索引
     * - 2.1 不存在则创建索引
     * 3. 检查是否存在别名
     * - 3.1 检查别名是否存在
     * - 3.2 不存在则创建别名
     * - 3.3 存在则检查对应索引是否正确
     */
    public void pre() throws IOException {
        if (!checkTemplate()) {
            log.warn("[索引模板不存在]");
            throw new RuntimeException("[索引模板不存在]");
        }

        String index = _currentIndex;
        boolean checkAndCreateIndex = checkAndCreateIndex(index);
        if (!checkAndCreateIndex) {
            log.warn("[创建索引失败]");
            throw new RuntimeException("[创建索引失败]");
        }

        if (!refreshAlias(index)) {
            log.warn("[别名操作失败]");
            throw new RuntimeException("[别名操作失败]");
        }
    }

    /**
     * 当前索引肯定存在(启动时候保证的)
     * <p>
     * 1. 检查下一个索引是否就绪
     * - 1.1 未就绪则创建
     * 2. 判断是否要替换别名-索引对应关系
     *
     * @return
     * @throws IOException
     */
    public boolean schedule(String currentIndex, String nextIndex) throws IOException {

        boolean result = checkAndCreateIndex(nextIndex);
        if (!result) {
            log.warn("[下一个索引未就绪] curIndex:{} nextIndex:{}", currentIndex, nextIndex);
            return false;
        }
        log.info("[下一个索引已就绪] curIndex:{} nextIndex:{}", currentIndex, nextIndex);

        // 检查别名是否需要替换
        GetAliasesRequest getAliasesRequest = new GetAliasesRequest(alias).indices(currentIndex);
        GetAliasesResponse aliasesResponse = restHighLevelClient.indices().getAlias(getAliasesRequest, RequestOptions.DEFAULT);
        RestStatus status = aliasesResponse.status();
        if (RestStatus.OK.equals(status)) {
            // 别名正确,无需处理
            log.info("[别名无需替换] index:{}", currentIndex);
            return true;
        } else if (RestStatus.NOT_FOUND.equals(status)) {
            // 不存在别名,创建别名
            log.warn("[别名需要更换对应索引] index:{} alias:{}", currentIndex, alias);
            return refreshAlias(currentIndex);
        } else {
            log.warn("[获取别名失败] 状态:{}", status);
            return false;
        }
    }


    /**
     * 检查模板是否存在
     *
     * @return
     */
    private boolean checkTemplate() {
        //TODO
        return true;
    }

    /**
     * 检查es中是否存在此索引,不存在则创建
     *
     * @param index
     * @return
     * @throws IOException
     */
    private boolean checkAndCreateIndex(String index) throws IOException {
        GetIndexRequest getIndexRequest = new GetIndexRequest(index);

        boolean exists = restHighLevelClient.indices().exists(getIndexRequest, RequestOptions.DEFAULT);
        if (!exists) {
            log.info("[索引不存在] 开始创建 {}", index);
            return createIndex(index);
        }
        log.info("[索引已存在] index:{}", index);
        return true;
    }


    /**
     * 创建索引,如果重复创建则会报错
     *
     * @param newIndex
     * @throws IOException
     */
    private boolean createIndex(String newIndex) throws IOException {
        try {
            CreateIndexRequest createIndexRequest = new CreateIndexRequest(newIndex);
            CreateIndexResponse createIndexResponse = restHighLevelClient.indices().create(createIndexRequest, RequestOptions.DEFAULT);
            boolean acknowledged = createIndexResponse.isAcknowledged();
            if (!acknowledged) {
                log.warn("[创建索引状态false] index:{}", newIndex);
            }
            log.info("[创建索引成功] index:{}", newIndex);
            return acknowledged;
        } catch (ElasticsearchStatusException e) {
            log.warn("[ElasticsearchStatusException] msg:{}", e.getMessage());
            throw e;
        }
    }

    private boolean refreshAlias(String newIndex) throws IOException {
        log.info("[刷新别名映射的索引] newIndex:{}", newIndex);
        GetAliasesRequest getAliasesRequest = new GetAliasesRequest(alias);
        GetAliasesResponse aliasesResponse = restHighLevelClient.indices().getAlias(getAliasesRequest, RequestOptions.DEFAULT);
        RestStatus status = aliasesResponse.status();
        if (RestStatus.OK.equals(status)) {
            // 已存在别名,修改别名
            Map<String, Set<AliasMetaData>> aliases = aliasesResponse.getAliases();
            if (aliases.size() > 1) {
                log.warn("[别名对应索引数大于1] {}", aliases.size());
            }
            List<String> indexes = new ArrayList<>(1);
            aliases.forEach((k, v) -> {
                indexes.add(k);
            });
            return aliasModify(newIndex, indexes);
        } else if (RestStatus.NOT_FOUND.equals(status)) {
            // 不存在别名,创建别名
            log.warn("[不存在别名,创建别名] alias:{} newIndex:{}", alias, newIndex);
            return aliasModify(newIndex, null);
        } else {
            log.warn("[获取别名失败] 状态:{}", status);
            return false;
        }
    }

    private boolean aliasModify(String newIndex, Collection<String> oldIndexes) throws IOException {
        // 替换索引别名(原子性)
        IndicesAliasesRequest indicesAliasesRequest = new IndicesAliasesRequest();
        if (oldIndexes != null) {
            oldIndexes.forEach(oldIndex -> {
                log.info("[删除别名映射的老索引] {}", oldIndex);
                indicesAliasesRequest.addAliasAction(IndicesAliasesRequest.AliasActions.remove().index(oldIndex).alias(alias));
            });
        }
        indicesAliasesRequest.addAliasAction(IndicesAliasesRequest.AliasActions.add().index(newIndex).alias(alias));
        AcknowledgedResponse acknowledgedResponse = restHighLevelClient.indices().updateAliases(indicesAliasesRequest, RequestOptions.DEFAULT);
        if (!acknowledgedResponse.isAcknowledged()) {
            log.warn("[替换索引别名失败]");
        } else {
            log.info("[别名替换索引成功] alias:{} newIndex:{}", alias, newIndex);
        }
        return acknowledgedResponse.isAcknowledged();
    }


    public void test() {
        IndexRequest indexRequest = new IndexRequest();
        GetIndexRequest getIndexRequest = new GetIndexRequest("lpc_test", "lpc_test2", "lpc_test3");

        try {
            boolean exists = restHighLevelClient.indices().exists(getIndexRequest, RequestOptions.DEFAULT);
            System.out.println(exists);

            String alias = "test_alias";
            GetAliasesRequest getAliasesRequest = new GetAliasesRequest(alias).indices("lpc_test1");
            GetAliasesResponse aliasesResponse = restHighLevelClient.indices().getAlias(getAliasesRequest, RequestOptions.DEFAULT);
            RestStatus status = aliasesResponse.status();
            if (RestStatus.OK.equals(status)) {
                Map<String, Set<AliasMetaData>> aliases = aliasesResponse.getAliases();
                if (aliases.size() > 1) {
                    log.warn("[别名对应索引数大于1] {}", aliases.size());
                } else {
                    aliases.forEach((k, v) -> {
                        System.out.println(k);
                    });
                }
            }

            // 替换索引别名(原子性)
            IndicesAliasesRequest indicesAliasesRequest = new IndicesAliasesRequest();
            indicesAliasesRequest.addAliasAction(IndicesAliasesRequest.AliasActions.remove().index("lpc_test2").alias(alias));
            indicesAliasesRequest.addAliasAction(IndicesAliasesRequest.AliasActions.add().index("lpc_test3").alias(alias));
            AcknowledgedResponse acknowledgedResponse = restHighLevelClient.indices().updateAliases(indicesAliasesRequest, RequestOptions.DEFAULT);
            if (!acknowledgedResponse.isAcknowledged()) {
                log.warn("[替换索引别名失败]");
            }

            // 创建索引
            String nexIndex = "lpc_test3";
            CreateIndexRequest createIndexRequest = new CreateIndexRequest(nexIndex);

            CreateIndexResponse createIndexResponse = restHighLevelClient.indices().create(createIndexRequest, RequestOptions.DEFAULT);
            boolean acknowledged = createIndexResponse.isAcknowledged();
            System.out.println(acknowledged);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ElasticsearchStatusException e) {
            log.warn("[ElasticsearchStatusException] msg:{}", e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
