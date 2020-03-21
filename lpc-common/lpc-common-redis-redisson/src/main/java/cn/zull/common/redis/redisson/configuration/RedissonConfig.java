package cn.zull.common.redis.redisson.configuration;

import cn.zull.common.redis.core.IRedisCache;
import cn.zull.common.redis.redisson.RedissonCache;
import lombok.Getter;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.StringCodec;
import org.redisson.config.ClusterServersConfig;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @author zurun
 * @date 2018/10/16 09:21:02
 */
@Configuration
@Getter
@ComponentScan("cn.zull.common.redis.redisson")
public class RedissonConfig {
    @Value("${spring.redis.host}")
    private String host;
    @Value("${spring.redis.port}")
    private int port;
    @Value("${spring.redis.password}")
    private String password;

    /**
     * 是否启用集群
     */
    @Value("${spring.redis.iscluster}")
    private Boolean isluster;
    /**
     * Redis 集群的节点
     */
    @Value("${spring.redis.cluster.nodes:0}")
    private List<String> nodesList;

    /**
     * redisson
     *
     * @return
     */
    @Bean("redissonCache")
    public IRedisCache redissonCache() {
        return new RedissonCache(redissonClient());
    }

    private RedissonClient redissonClient() {
        Config config = new Config();
        config.setCodec(new StringCodec());
        if (isluster) {
            // redis集群模式
            ClusterServersConfig clusterServersConfig = config.useClusterServers().setPingConnectionInterval(1000);
            if (!StringUtils.isEmpty(password)) {
                clusterServersConfig.setPassword(password);
            }
            for (String nodes : nodesList) {
                String[] node = nodes.split(",");
                for (int i = 0, length = node.length; i < length; i++) {
                    clusterServersConfig.addNodeAddress("redis://".concat(node[i]));
                }

            }
        } else {
            // 单机模式
            SingleServerConfig singleServerConfig = config.useSingleServer().
                    setAddress("redis://".concat(host.concat(":") + port)).setPingConnectionInterval(1000);
            if (!StringUtils.isEmpty(password)) {
                singleServerConfig.setPassword(password);
            }
        }
        return Redisson.create(config);
    }

}
