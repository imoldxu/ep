package com.ly.service.mq.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;

import com.ly.service.entity.SalesRecord;
import com.ly.service.mq.IMessageProvider;
import com.ly.service.mq.SalesRecordChannel;

@EnableBinding(SalesRecordChannel.class)
public class MessageProvider implements IMessageProvider {

	@Resource(name = SalesRecordChannel.OUTPUT)
    private MessageChannel output; // 消息的发送管道
	
	@Override
	public void send(List<SalesRecord> records) {
		output.send(MessageBuilder.withPayload(records).build());
	}

}
