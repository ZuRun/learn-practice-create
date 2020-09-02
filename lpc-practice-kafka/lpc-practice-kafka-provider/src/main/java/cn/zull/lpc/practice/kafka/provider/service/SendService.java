package cn.zull.lpc.practice.kafka.provider.service;

import cn.zull.lpc.common.basis.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author zurun
 * @date 2020/8/10 14:49:31
 */
@Slf4j
@Service
public class SendService implements CommandLineRunner {
    @Autowired
    KafkaProducer<String, String> kafkaProducer;
    @Value("${thread.size:1}")
    private Integer threadSize;
    @Value("${test.future.enable:false}")
    private Boolean enableFuture;
    private final String topic = "TEST_ELK";

    AtomicInteger sum = new AtomicInteger(0);
    private final List<Header> headers = Collections.singletonList(
            new RecordHeader("_v", StringUtils.getBytesUtf8("1.1")));

    {
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> {
            int s = sum.getAndSet(0);
            System.out.println(s);
        }, 0, 1, TimeUnit.SECONDS);
    }

    @Override
    public void run(String... args) throws Exception {
        ExecutorService executorService = Executors.newFixedThreadPool(threadSize);

        for (int j = 0; j < threadSize; j++) {
            int z = j;
            executorService.execute(() -> {
                int i = 0;
                while (true) {
                    ProducerRecord producerRecord = new ProducerRecord(topic, null, null, null,
                            z + "_" + i + "_" + (LocalDateTime.now().toString()), headers
                    );
                    i++;
                    Future<RecordMetadata> future = kafkaProducer.send(producerRecord);
                    sum.getAndIncrement();
                    if (enableFuture) {
                        try {
                            RecordMetadata recordMetadata = future.get();
//                            log.info("[z] {}", JsonUtils.toJSONString(recordMetadata));

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }

    }


}
