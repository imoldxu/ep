package com.ly.service.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ly.service.context.ErrorCode;
import com.ly.service.context.HandleException;
//import com.ly.service.context.Response;
import com.ly.service.context.TransactionDrug;
import com.ly.service.entity.Order;
//import com.ly.service.feign.client.AccountClient;
//import com.ly.service.feign.client.SalesRecordClient;
import com.ly.service.mapper.OrderMapper;
//import com.ly.service.mq.IMessageProvider;
import com.ly.service.utils.JSONUtils;
import com.ly.service.utils.MoneyUtil;

@Service
public class OrderService {
  
	@Autowired
	OrderMapper orderMapper;
	//@Autowired
	//IMessageProvider messageProvide;
	@Autowired
	SalesRecordService salesRecordService;
	//@Autowired
	//AccountClient accountClient;
	@Autowired
	AccountService accountService;
	
	public Order create(int uid, int amount, List<TransactionDrug> transactionList){
		Order order = new Order();
		order.setInfo(JSONUtils.getJsonString(transactionList));
		order.setTargettype(Order.TARGET_USER);
		order.setTranscode(Order.CODE_TRANS);
		order.setTargetid(uid);
		order.setAmount(amount);
		order.setCreatetime(new Date());
		//TODO:
		//order.setInvalidtime(invalidtime);
		order.setState(Order.STATE_NEW);
		orderMapper.insertUseGeneratedKeys(order);
		return order;
	}
	
	public void getPayToken(int targetid, int pay_way, Long orderid){
		Order order = orderMapper.selectByPrimaryKey(orderid);
		if(order.getTargetid() != targetid){
			throw new HandleException(ErrorCode.DOMAIN_ERROR, "你无权进行此操作");
		}
		
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
			salesRecordService.createByOnlinePay(order);
		}else if(order.getTranscode()==Order.CODE_CHARGE){
			if(order.getTargettype()==Order.TARGET_STORE){
				//FIXME:此处应该发消息更好,毕竟支付通知不会重复通知
				accountService.addStoreAccount(order.getTargetid(), order.getAmount(), order.getInfo());
			}
		}
	}

	@Transactional
	public Order createByStore(int uid, int storeid, List<TransactionDrug> transactionList) {
		Order order = new Order();
		order.setInfo(JSONUtils.getJsonString(transactionList));
		order.setTargettype(Order.TARGET_USER);
		order.setTargetid(uid);
		order.setTranscode(Order.CODE_TRANS);
		order.setCreatetime(new Date());
		order.setCompletetime(new Date());
		order.setAmount(0);
		order.setPayway(Order.PAY_OFFLINESTORE);
		order.setState(Order.STATE_COMPLETE);
		orderMapper.insertUseGeneratedKeys(order);
		
		//String orderStr = JSONUtils.getJsonString(order);
		
		salesRecordService.createByStore(storeid, order);//购药记录处理
		
		return order;
	}

	public Order createChargeOrder(int storeid, int amount) {
		Order order = new Order();
		order.setInfo("账户充值"+MoneyUtil.changeF2Y(amount));
		order.setTargettype(Order.TARGET_STORE);
		order.setTargetid(storeid);
		order.setCreatetime(new Date());
		order.setAmount(amount);
		order.setState(Order.STATE_NEW);
		orderMapper.insertUseGeneratedKeys(order);
		
		return order;
	}
}
