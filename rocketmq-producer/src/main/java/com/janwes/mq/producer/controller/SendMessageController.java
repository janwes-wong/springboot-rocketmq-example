package com.janwes.mq.producer.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Janwes
 * @version 1.0
 * @package com.janwes.mq.producer.controller
 * @date 2021/8/9 11:06
 * @description
 */
@Slf4j
@RestController
@RequestMapping("/message")
public class SendMessageController {

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    public static final String MESSAGE_TOPIC = "baseTopic110";

    private static String successResult;

    @GetMapping("/send")
    public String sendMessage() {
        rocketMQTemplate.asyncSend(MESSAGE_TOPIC, "开始搬砖啦！！！", new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                // 处理消息发送成功逻辑
                log.info(">>> 消息发送成功......");
                successResult = "消息发送成功";
            }

            @Override
            public void onException(Throwable throwable) {
                // 处理消息发送失败逻辑
                log.info(">>> 消息发送失败......");
                successResult = "消息发送失败";
            }
        });
        return successResult;
    }

    @GetMapping("/delayMessage")
    public String sendDelayMessage() {
        // RocketMQ 不支持任意时间自定义的延迟消息，仅支持内置预设值的延迟时间间隔的延迟消息。
        // 预设值的延迟时间间隔为：1s、 5s、 10s、 30s、 1m、 2m、 3m、 4m、 5m、 6m、 7m、 8m、 9m、 10m、 20m、 30m、 1h、 2h
        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        // 构建消息内容
        Message<String> message = MessageBuilder.withPayload("发送延迟消息-" + time).build();
        // delayLevel:5 对应1m（1分钟）
        rocketMQTemplate.asyncSend("delayTopic-130", message, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                // 处理消息发送成功逻辑
                log.info(">>> 定时延时消息发送成功......");
                successResult = "delayMessage send success...";
            }

            @Override
            public void onException(Throwable throwable) {
                throwable.printStackTrace();
                log.error("exception message{}", throwable.getMessage());
                // 处理消息发送失败逻辑
                log.error(">>> 定时延时消息发送失败......");
                successResult = "delayMessage send failed...";
            }
        }, 5000, 3);
        return successResult;
    }
}
