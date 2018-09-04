package com.ly.service.mq;


import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface MyProcess {
    public static final String OUTPUT = "my_output"; // 输出通道名称
    public static final String INPUT = "my_input"; // 输入通道名称

    @Input(MyProcess.INPUT)
    public SubscribableChannel input();

    @Output(MyProcess.OUTPUT)
    public MessageChannel output();
}
