package com.ly.service.mq;


import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface OrderChannel {
    public static final String OUTPUT = "order_output"; // 输出通道名称
    
    @Output(OrderChannel.OUTPUT)
    public MessageChannel output();
}
