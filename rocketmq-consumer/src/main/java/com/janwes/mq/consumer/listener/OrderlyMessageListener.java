package com.janwes.mq.consumer.listener;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * @author Janwes
 * @version 1.0
 * @package com.janwes.consumer.listener
 * @date 2021/3/4 23:33
 * @description RocketMQ监听器
 * consumeMode：顺序消息
 * 消息消费者端：通过多线程获取消息
 * 消费模式：
 * consumeMode = ConsumeMode.ORDERLY 表示：有序接收异步传递的消息。一队一线(源码默认消息没有顺序)
 */
@Slf4j
@Component
@RocketMQMessageListener(topic = "orderlyTopic-120", consumerGroup = "orderlyGroup", consumeMode = ConsumeMode.ORDERLY)
public class OrderlyMessageListener implements RocketMQListener<String> {

    /**
     * 接收消息
     *
     * @param message 消息内容
     */
    @Override
    public void onMessage(String message) {
        log.info("当前线程" + Thread.currentThread().getName() + "，接收到的消息=" + message);
    }
}
