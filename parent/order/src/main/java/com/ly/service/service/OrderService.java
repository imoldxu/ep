package com.ly.service.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ly.service.context.TransactionDrug;
import com.ly.service.entity.Order;
import com.ly.service.feign.client.AccountClient;
import com.ly.service.feign.client.SalesRecordClient;
import com.ly.service.mapper.OrderMapper;
import com.ly.service.mq.IMessageProvider;
import com.ly.service.utils.JSONUtils;
import com.ly.service.utils.MoneyUtil;

@Service
public class OrderService {
  
	@Autowired
	OrderMapper orderMapper;
	@Autowired
	IMessageProvider messageProvide;
	@Autowired
	SalesRecordClient salesRecordClient;
	@Autowired
	AccountClient accountClient;
	
	public Order create(int uid, int amount, List<TransactionDrug> transactionList){
		Order order = new Order();
		order.setInfo(JSONUtils.getJsonString(transactionList));
		order.setTagettype(Order.TAGET_USER);
		order.setTranscode(Order.CODE_TRANS);
		order.setTagetid(uid);
		order.setAmount(amount);
		order.setCreatetime(new Date());
		order.setState(Order.STATE_NEW);
		orderMapper.insertUseGeneratedKeys(order);
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
		if(order.getTranscode()==Order.CODE_TRANS){
			messageProvide.send(order);
		}else if(order.getTranscode()==Order.CODE_CHARGE){
			if(order.getTagettype()==Order.TAGET_STORE){
				//FIXME:此处应该发消息更好,毕竟支付通知不会重复通知
				accountClient.addStoreAccount(order.getTagetid(), order.getAmount(), order.getInfo());
			}
		}
	}

	@Transactional
	public Order createByStore(int uid, int storeid, List<TransactionDrug> transactionList) {
		Order order = new Order();
		order.setInfo(JSONUtils.getJsonString(transactionList));
		order.setTagettype(Order.TAGET_USER);
		order.setTagetid(uid);
		order.setTranscode(Order.CODE_TRANS);
		order.setCreatetime(new Date());
		order.setCompletetime(new Date());
		order.setAmount(0);
		order.setPayway(Order.PAY_OFFLINESTORE);
		order.setState(Order.STATE_COMPLETE);
		orderMapper.insertUseGeneratedKeys(order);
		
		salesRecordClient.createByStore(storeid, order);//购药记录处理
		
		return order;
	}

	public Order createChargeOrder(int storeid, int amount) {
		Order order = new Order();
		order.setInfo("账户充值"+MoneyUtil.changeF2Y(amount));
		order.setTagettype(Order.TAGET_STORE);
		order.setTagetid(storeid);
		order.setCreatetime(new Date());
		order.setAmount(amount);
		order.setState(Order.STATE_NEW);
		orderMapper.insertUseGeneratedKeys(order);
		
		return order;
	}
}
