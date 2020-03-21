使用方法

1. 依赖
```xml
<dependency>
    <groupId>cn.zull.lpc</groupId>
    <artifactId>lpc-common-redis</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
<dependency>
    <groupId>cn.zull.lpc</groupId>
    <artifactId>lpc-common-redisson</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
```

2. 配置
```properties
# Redis 是否启用集群,自定义参数
spring.redis.iscluster=false
spring.redis.cluster.nodes = 172.31.131.128:6379,172.31.131.136:6379,172.31.131.135:6379,172.31.131.146:6379,172.31.131.131:6379,172.31.131.141:6379
spring.redis.host=172.31.129.144
spring.redis.port= 19371
spring.redis.password=
#spring.redis.password = znjj.IDLF#8

spring.redis.redisson. = 30
spring.redis.lettuce.pool.max-idle = 100
spring.redis.lettuce.pool.max-wait = 500ms
spring.redis.shutdown-timeout = 2000ms
```