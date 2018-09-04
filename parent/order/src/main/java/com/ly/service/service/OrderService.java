package com.ly.service.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ly.service.context.TransactionDrug;
import com.ly.service.entity.Order;
import com.ly.service.feign.client.SalesRecordClient;
import com.ly.service.mapper.OrderMapper;
import com.ly.service.mq.IMessageProvider;
import com.ly.service.utils.JSONUtils;

@Service
public class OrderService {
  
	@Autowired
	OrderMapper orderMapper;
	@Autowired
	IMessageProvider messageProvide;
	@Autowired
	SalesRecordClient salesRecordClient;
	
	public Order create(int uid, int amount, List<TransactionDrug> transactionList){
		Order order = new Order();
		order.setInfo(JSONUtils.getJsonString(transactionList));
		order.setUserid(uid);
		order.setAmount(amount);
		order.setCreatetime(new Date());
		order.setState(Order.STATE_NEW);
		orderMapper.insert(order);
		return order;
	}
	
	public void getPayToken(int pay_way, Long orderid){
		Order order = orderMapper.selectByPrimaryKey(orderid);
		//int amount = order.getAmount();
		order.setState(Order.STATE_PAYING);
		orderMapper.updateByPrimaryKey(order);
		//TODO创建支付凭证
		
	}
	
	@Transactional
	public void payOver(Long orderid){
		Order order = orderMapper.selectByPrimaryKey(orderid);
		order.setCompletetime(new Date());
		order.setState(Order.STATE_PAYED);
		orderMapper.updateByPrimaryKey(order);
		
		messageProvide.send(order);
	}

	@Transactional
	public Order createByStore(int uid, int storeid, List<TransactionDrug> transactionList) {
		Order order = new Order();
		order.setInfo(JSONUtils.getJsonString(transactionList));
		order.setUserid(uid);
		order.setCreatetime(new Date());
		order.setCompletetime(new Date());
		order.setAmount(0);
		order.setPayway(Order.PAY_OFFLINESTORE);
		order.setState(Order.STATE_COMPLETE);
		orderMapper.insert(order);
		
		salesRecordClient.createByStore(storeid, order);
		
		return order;
	}
}
