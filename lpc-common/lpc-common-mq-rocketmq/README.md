
## 配置

```properties
# 启用apache版本还是aliyun版本,默认apache
lpc.mq.rocketmq.client=apache
# aliyun版本需要填写key与密钥
lpc.mq.rocketmq.aliyun.accessKey=xxxx
lpc.mq.rocketmq.aliyun.secretKey=yyyy
# rocketmq nameserver地址
lpc.mq.rocketmq.namesrvAddr=127.0.0.1:9876
# 是否开启默认提供方服务,默认关闭
lpc.mq.rocketmq.producer.enable=false
# 提供方 组名,默认PG_DEFAULT
lpc.mq.rocketmq.producer.group=PG_DEFAULT
```