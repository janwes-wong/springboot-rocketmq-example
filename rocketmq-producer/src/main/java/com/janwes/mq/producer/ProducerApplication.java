package com.janwes.mq.producer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Janwes
 * @version 1.0
 * @package com.janwes.mq
 * @date 2021/7/23 16:52
 * @description rocketMQ消息生产者
 */
@SpringBootApplication
public class ProducerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProducerApplication.class, args);
    }
}
