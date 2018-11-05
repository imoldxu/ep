package com.ly.service.mq.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ly.service.entity.Order;
import com.ly.service.entity.SalesRecord;
import com.ly.service.mq.AccountChannel;
import com.ly.service.service.AccountService;

@Component
@EnableBinding(AccountChannel.class)
public class MessageListener {

	@Autowired
	AccountService accountService;
	
	@Transactional
	@StreamListener(AccountChannel.ORDER_INPUT)
    public void order_input(Message<Order> msg) {
        Order order = msg.getPayload();
        //处理order状态变化消息
        if(order.getState()==Order.STATE_PAYED){
        	accountService.addStoreAccount(order.getTargetid(), order.getAmount(), "充值");
        }
	}
	
	@Transactional
	@StreamListener(AccountChannel.SALESRECORD_INPUT)
    public void salesrecord_input(Message<List<SalesRecord>> msg) {
		List<SalesRecord> records = msg.getPayload();
        //处理order状态变化消息
		accountService.settleSalesRecords(records);
	}
}
