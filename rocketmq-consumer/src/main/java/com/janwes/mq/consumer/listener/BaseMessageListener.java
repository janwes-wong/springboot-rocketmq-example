package com.janwes.mq.consumer.listener;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * @author Janwes
 * @version 1.0
 * @package com.janwes.mq.consumer.listener
 * @date 2021/7/23 17:17
 * @description
 */
@Slf4j
@Component
@RocketMQMessageListener(topic = "baseTopic110", consumerGroup = "baseGroup")
public class BaseMessageListener implements RocketMQListener<String> {

    /**
     * 通过rocketMQ-console可视化控制台对指定topic主题发送消息
     *
     * @param message
     */
    @Override
    public void onMessage(String message) {
        log.info(">>> receive message content：{}", message);
    }
}
