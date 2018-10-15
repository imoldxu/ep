package com.ly.service.mq;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface SalesRecordChannel {
	public static final String OUTPUT = "salesrecord_output"; // 输出通道名称
    
    @Output(SalesRecordChannel.OUTPUT)
    public MessageChannel output();
}
