package com.ly.service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ly.service.context.ErrorCode;
import com.ly.service.context.HandleException;
import com.ly.service.entity.ManagerUser;
import com.ly.service.mapper.ManagerMapper;
import com.ly.service.utils.PasswordUtil;

import tk.mybatis.mapper.entity.Example;

@Service
public class ManagerService {

	@Autowired
	ManagerMapper managerMapper;
	
	public ManagerUser login(String phone, String password) {
		Example ex = new Example(ManagerUser.class);
		ex.createCriteria().andEqualTo("phone", phone);
		ManagerUser managerUser = managerMapper.selectOneByExample(ex);
		if(managerUser == null){
			throw new HandleException(ErrorCode.NORMAL_ERROR, "用户不存在");
		}
		if(PasswordUtil.isEqual(managerUser.fetchPassword(), password, managerUser.fetchPwdnonce())){
			return managerUser;
		}else{
			throw new HandleException(ErrorCode.NORMAL_ERROR, "密码错误");
		}
	}

	public void modifyPwd(Integer managerid, String oldPassword, String newPassword) {
		ManagerUser manager = managerMapper.selectByPrimaryKey(managerid);
		if(PasswordUtil.isEqual(manager.fetchPassword(), oldPassword, manager.fetchPwdnonce())){
			String newPwd = PasswordUtil.generatePwd(newPassword, manager.fetchPwdnonce());
			manager.setPassword(newPwd);
			managerMapper.updateByPrimaryKey(manager);
		}else{
			throw new HandleException(ErrorCode.NORMAL_ERROR, "旧密码错误");
		}
	}

	public ManagerUser getManagerById(Integer managerid) {
		return managerMapper.selectByPrimaryKey(managerid);
	}

}
