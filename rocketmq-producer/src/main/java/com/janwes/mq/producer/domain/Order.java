package com.janwes.mq.producer.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Janwes
 * @version 1.0
 * @package com.janwes.producer.domain
 * @date 2021/3/4 23:50
 * @description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    private Long id;
    private String desc;

    /**
     * 模拟多个用户下订单 付款 完成
     *
     * @return
     */
    public static List<Order> buildOrders() {
        List<Order> list = new ArrayList<>();
        //每一个订单有一个唯一的订单id(可以通过此订单id 保证发送消息顺序)
        Order order1a = new Order(4L, "创建订单a");
        Order order2a = new Order(5L, "创建订单b");
        Order order3a = new Order(6L, "创建订单c");
        list.add(order1a);
        list.add(order2a);
        list.add(order3a);

        Order order1b = new Order(4L, "付款a");
        Order order2b = new Order(5L, "付款b");
        Order order3b = new Order(6L, "付款c");
        list.add(order1b);
        list.add(order2b);
        list.add(order3b);


        Order order1c = new Order(4L, "完成a");
        Order order2c = new Order(5L, "完成b");
        Order order3c = new Order(6L, "完成b");
        list.add(order1c);
        list.add(order2c);
        list.add(order3c);
        return list;
    }
}
