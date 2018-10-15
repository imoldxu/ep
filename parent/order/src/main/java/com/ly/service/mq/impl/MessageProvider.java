package com.ly.service.mq.impl;

import javax.annotation.Resource;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;

import com.ly.service.entity.Order;
import com.ly.service.mq.IMessageProvider;
import com.ly.service.mq.OrderChannel;

@EnableBinding(OrderChannel.class)
public class MessageProvider implements IMessageProvider {

	@Resource(name = OrderChannel.OUTPUT)
    private MessageChannel output; // 消息的发送管道
	
	@Override
	public void send(Order order) {
		output.send(MessageBuilder.withPayload(order).build());
	}

}
