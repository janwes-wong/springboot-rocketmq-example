package com.janwes.mq.producer.domain;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;

/**
 * @author Janwes
 * @version 1.0
 * @package com.janwes.mq.producer.domain
 * @date 2021/8/7 16:04
 * @description
 */
public class DelayMessage<T> implements Message<T> {

    private T message;

    public DelayMessage() {
    }

    public DelayMessage(T message) {
        this.message = message;
    }

    public T getMessage() {
        return message;
    }

    public void setMessage(T message) {
        this.message = message;
    }

    @Override
    public T getPayload() {
        return message;
    }

    @Override
    public MessageHeaders getHeaders() {
        return null;
    }
}
