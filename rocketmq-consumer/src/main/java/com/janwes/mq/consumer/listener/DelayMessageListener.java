package com.janwes.mq.consumer.listener;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Janwes
 * @version 1.0
 * @package com.janwes.mq.consumer.listener
 * @date 2021/8/7 15:46
 * @description
 */
@Slf4j
@Component
@RocketMQMessageListener(topic = "delayTopic-130", consumerGroup = "delay-consumer")
public class DelayMessageListener implements RocketMQListener<String> {

    @Override
    public void onMessage(String message) {
        String receiveTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        log.info(">>> receive message content：{},receiveTime：{}", message, receiveTime);
    }
}
