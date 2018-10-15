package com.ly.service.mq;


import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface AccountChannel {
    //public static final String OUTPUT = "my_output"; // 输出通道名称
    public static final String ORDER_INPUT = "order_input"; // 输入通道名称
	public static final String SALESRECORD_INPUT = "salesreocrd_input";

    @Input(AccountChannel.ORDER_INPUT)
    public SubscribableChannel order_input();

    @Input(AccountChannel.SALESRECORD_INPUT)
    public SubscribableChannel salesrecord_input();
    //@Output(OrderChannel.OUTPUT)
    //public MessageChannel output();
}
