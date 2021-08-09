package com.janwes.mq.producer.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
