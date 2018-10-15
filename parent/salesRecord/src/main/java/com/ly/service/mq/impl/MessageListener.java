package com.ly.service.mq.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ly.service.entity.Order;
import com.ly.service.mq.OrderChannel;
import com.ly.service.service.SalesRecordService;

@Component
@EnableBinding(OrderChannel.class)
public class MessageListener {

	@Autowired
	SalesRecordService salesRecordService;
	
	@Transactional
	@StreamListener(OrderChannel.INPUT)
    public void input(Message<Order> msg) {
        Order order = msg.getPayload();
        //处理order状态变化消息
        if(order.getState()==Order.STATE_PAYED){
        	salesRecordService.createByOnlinePay(order);
        }
	}
	
}
