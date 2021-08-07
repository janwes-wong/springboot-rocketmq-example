package com.janwes.mq.producer;

import com.alibaba.fastjson.JSON;
import com.janwes.mq.producer.domain.DelayMessage;
import com.janwes.mq.producer.domain.Order;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author Janwes
 * @version 1.0
 * @package com.janwes.mq.producer
 * @date 2021/8/7 14:20
 * @description
 */
@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class SendMessageTest {

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    public static final String MESSAGE_TOPIC = "baseTopic110";

    @Test
    public void convertAndSend() {
        /**
         * 方法参数：
         * destination：目的地，主题topic
         * payload：荷载，消息内容数据
         */
        rocketMQTemplate.convertAndSend(MESSAGE_TOPIC, "你是谁？");
    }

    @Test
    public void syncSend() {
        // 发送普通消息 设置timeout消息发送超时时间
        rocketMQTemplate.syncSend(MESSAGE_TOPIC, "前进，向前进！！！", 4000);
    }

    @Test
    public void delayMessage() {
        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        rocketMQTemplate.syncSend(MESSAGE_TOPIC, "发送延迟消息 " + time, 5000);
    }

    @Test
    public void sendDelayMessage() {
        // RocketMQ 不支持任意时间自定义的延迟消息，仅支持内置预设值的延迟时间间隔的延迟消息。
        // 预设值的延迟时间间隔为：1s、 5s、 10s、 30s、 1m、 2m、 3m、 4m、 5m、 6m、 7m、 8m、 9m、 10m、 20m、 30m、 1h、 2h
        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        // delayLevel:5 对应1m（1分钟）
        rocketMQTemplate.syncSend("delayTopic-130", new DelayMessage<>("发送延迟消息-" + time), 5000, 3);
    }

    /**
     * 发送普通消息
     */
    @Test
    public void send() {
        rocketMQTemplate.syncSend(MESSAGE_TOPIC, "前进，向前进！！！");
    }

    /**
     * 发送异步消息
     */
    @Test
    public void asyncSend() {
        rocketMQTemplate.asyncSend(MESSAGE_TOPIC, "开始搬砖啦！！！", new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                // 处理消息发送成功逻辑
                log.info(">>> 消息发送成功......");
            }

            @Override
            public void onException(Throwable throwable) {
                // 处理消息发送失败逻辑
                log.info(">>> 消息发送失败......");
            }
        });
    }

    /**
     * 生产者发送普通消息
     * destination主题(未创建自动默认创建)
     * Object：消息内容(可以是任意类型的数据，如String、Map等)
     */
    @Test
    public void sendOrderlyMessage() {
        List<Order> orderList = Order.buildOrders();
        for (Order order : orderList) {
            /**
             * 根据订单id取模 队列数量 得到选择中的队列
             * 往队列中写入消息，只要订单id一样就会往同一个队列中写消息
             * 就可以保证消息消费顺序
             */
            // 1. 选择队列(设置消息队列选择器)
            rocketMQTemplate.setMessageQueueSelector(new MessageQueueSelector() {
                /**
                 * 匿名内部类方法
                 * @param list 选择目标队列  list：所有队列
                 * @param message 消息内容
                 * @param key 需要进行取模的参数（订单ID）  4(订单id)%4(队列数量 默认4个队列) == 0
                 * @return
                 */
                @Override
                public MessageQueue select(List<MessageQueue> list, Message message, Object key) {
                    Long id = Long.valueOf((String) key);
                    int index = (int) (id % list.size());
                    return list.get(index);
                }
            });
            // 2. 发送消息 参数1：顺序消息主题名称  参数2：消息内容 参数3:订单id
            rocketMQTemplate.syncSendOrderly("orderlyTopic-120", JSON.toJSONString(order), order.getId().toString());
        }
    }
}
