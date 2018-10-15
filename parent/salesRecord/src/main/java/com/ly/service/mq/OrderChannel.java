package com.ly.service.mq;


import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface OrderChannel {
    //public static final String OUTPUT = "my_output"; // 输出通道名称
    public static final String INPUT = "order_input"; // 输入通道名称

    @Input(OrderChannel.INPUT)
    public SubscribableChannel input();

    //@Output(OrderChannel.OUTPUT)
    //public MessageChannel output();
}
