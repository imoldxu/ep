package com.ly.service.mq.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ly.service.entity.Order;
import com.ly.service.mq.MyProcess;
import com.ly.service.service.SalesRecordService;

@Component
@EnableBinding(MyProcess.class)
public class MessageListener {

	@Autowired
	SalesRecordService salesRecordService;
	
	@Transactional
	@StreamListener(MyProcess.INPUT)
    public void input(Message<Order> msg) {
        Order order = msg.getPayload();
        salesRecordService.createByOnlinePay(order);
	}
	
}
