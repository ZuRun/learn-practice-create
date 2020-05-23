package cn.zull.lpc.common.redis.jedis;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author zurun
 * @date 2020/4/8 18:49:01
 */
@Slf4j
@RunWith(SpringRunner.class)
public class NativeJedisCmdTest {

    @Test
    public void test() throws IOException {
        Set<HostAndPort> nodes = new HashSet<>();
//        172.31.131.128:6379,172.31.131.136:6379,172.31.131.135:6379,172.31.131.146:6379,172.31.131.131:6379,172.31.131.141:6379
        nodes.add(new HostAndPort("172.31.131.128", 6379));
        nodes.add(new HostAndPort("172.31.131.136", 6379));
        nodes.add(new HostAndPort("172.31.131.135", 6379));
        nodes.add(new HostAndPort("172.31.131.146", 6379));
        nodes.add(new HostAndPort("172.31.131.131", 6379));
        nodes.add(new HostAndPort("172.31.131.141", 6379));
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(4);

        JedisCluster cluster = new JedisCluster(nodes, 1000, 1000, 8, "znjj.IDLF#8", config);


        try {
            cluster.sadd("test:s:s", "");
            cluster.sadd("test:s:s", null);
            cluster.sadd("test:s:s", "-");

            Map<String, String> map = new HashMap<>(16);
            map.put("a", "aa");
            map.put("b", null);
            map.put("c", "empty");
            map.put("d", "");

            cluster.hmset("test:s", map);

            cluster.close();
        } catch (Exception e) {
            e.printStackTrace();
            cluster.close();
        }

    }
}
