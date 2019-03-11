package com.ly.service.service;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.JsonNode;
import com.ly.service.context.ErrorCode;
import com.ly.service.context.HandleException;
import com.ly.service.entity.Doctor;
import com.ly.service.mapper.DoctorMapper;
import com.ly.service.utils.PasswordUtil;
import com.ly.service.utils.RedissonUtil;
import com.ly.service.utils.ValidDataUtil;
import com.ly.service.utils.WxUtil;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Service
public class DoctorService {

	@Autowired
	DoctorMapper doctorMapper;
	@Autowired
	SMSService sms;
	@Autowired
	RedissonUtil redissonUtil;
	
	public Doctor loginByWx(String wxCode) throws IOException{
		JsonNode wxOauthInfo = WxUtil.getOauthInfo(wxCode);
		String accessToken = null;
		accessToken = (String)redissonUtil.get("wechat_access_token");
		
		JsonNode wxUserInfo = WxUtil.getUserInfo2(accessToken, wxOauthInfo);
		// 获得微信的数据
		String unionID = wxUserInfo.get("unionid").asText();
		String headerImgURL = wxUserInfo.get("headimgurl").asText();
		if(null != headerImgURL &&  !headerImgURL.isEmpty()){
			//去掉微信图片为0的标号
			headerImgURL = headerImgURL.substring(0, headerImgURL.length()-3);
			//设置微信图片为64的标号
			headerImgURL = headerImgURL+"64";
		}
		//String wxnick = wxUserInfo.get("nickname").asText();
		//String nick = WxUtil.converWxNick(wxnick);
		// 获取微信账号对应的账号
		Example ex = new Example(Doctor.class);
		ex.createCriteria().andEqualTo("wxunionid", unionID);
		ex.setOrderByClause("id asc");
		Doctor doctor = doctorMapper.selectOneByExample(ex);
		if (doctor ==  null) {
			// 微信用户未注册
			doctor = new Doctor();
			doctor.setWxunionid(unionID);
			doctor.setCreatetime(new Date());
			doctorMapper.insert(doctor);
		} else {
			// 微信用户已经注册
			// 更新用户的昵称和头像
			doctorMapper.updateByPrimaryKey(doctor);			
		}
		
//		String idcard = user.getIdCard();
//		if(idcard!=null && !idcard.isEmpty()){
//			try{
//				int age = IdCardUtil.getAge(idcard);
//				user.setAge(age);
//			}catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
		
		JsonNode subscribeNode = wxUserInfo.get("subscribe");
		if(subscribeNode!=null){
			int subscribe = subscribeNode.intValue();
			doctor.setSubscribe(subscribe);
		}else{
			doctor.setSubscribe(0);//视为没有关注
		}
		
		return doctor;
	}

	public Doctor login(String phone, String password) throws HandleException {
		Example ex = new Example(Doctor.class);
		ex.createCriteria().andEqualTo("phone", phone);
		Doctor doctor = doctorMapper.selectOneByExample(ex);
		if(doctor == null){
			throw new HandleException(ErrorCode.NORMAL_ERROR, "用户不存在");
		}else{
			if(PasswordUtil.isEqual(doctor.fetchPassword(), password, doctor.fetchPwdnonce())){
				doctorMapper.updateByPrimaryKey(doctor);
				return doctor;
			}else{
				throw new HandleException(ErrorCode.NORMAL_ERROR, "用户密码错误");
			}
		}
	}

	@Transactional
	public Doctor bind(Integer doctorid, String phone, String password) {
		Example ex = new Example(Doctor.class);
		ex.createCriteria().andEqualTo("phone", phone);
		Doctor doctor = doctorMapper.selectOneByExample(ex);
		if(doctor == null){//用户不存在
			doctor = doctorMapper.selectByPrimaryKey(doctorid);
			if(doctor == null){
				throw new HandleException(ErrorCode.ARG_ERROR, "参数错误");
			}else{
				doctor.setPhone(phone);
				String nonce = PasswordUtil.generateNonce();
				doctor.setPwdnonce(nonce);
				
				String newPwd = PasswordUtil.generatePwd(password, nonce);
				doctor.setPassword(newPwd);
				
				doctorMapper.updateByPrimaryKey(doctor);
			}
		}else{//用户存在
			Doctor wxDoctor = doctorMapper.selectByPrimaryKey(doctorid);
			if(doctorid == doctor.getId()){//若是要绑定的id就是用户现在的id，则什么都不做
				return doctor;
			}
			if(PasswordUtil.isEqual(doctor.fetchPassword(), password, doctor.fetchPwdnonce())){
				doctor.setWxunionid(wxDoctor.fetchWxunionid());
				doctorMapper.updateByPrimaryKey(doctor);
				doctorMapper.deleteByPrimaryKey(wxDoctor);
			}else{
				throw new HandleException(ErrorCode.NORMAL_ERROR, "用户密码错误");
			}
		}
		return doctor;
	}

	public Doctor register(String phone, String password) {
		Example ex = new Example(Doctor.class);
		ex.createCriteria().andEqualTo("phone", phone);
		Doctor doctor = doctorMapper.selectOneByExample(ex);
		if(doctor != null){
			throw new HandleException(ErrorCode.NORMAL_ERROR, "用户已存在");
		}else{
			doctor = new Doctor();
			doctor.setPhone(phone);
			
			String nonce = PasswordUtil.generateNonce();
			doctor.setPwdnonce(nonce);
			
			String newPwd = PasswordUtil.generatePwd(password, nonce);
			doctor.setPassword(newPwd);
			doctor.setCreatetime(new Date());
			doctorMapper.insertUseGeneratedKeys(doctor);
		}
		return doctor;
	}

	public Doctor updateInfo(Integer doctorID, Integer hid, String name, String department, String signatureurl) {
		Doctor doctor = doctorMapper.selectByPrimaryKey(doctorID);
		if(doctor == null){
			throw new HandleException(ErrorCode.ARG_ERROR,"参数错误");
		}
		if(hid != null){
			doctor.setHospitalid(hid);
		}
		if(name != null){
			doctor.setName(name);
		}
		if(department != null) {
			doctor.setDepartment(department);
		}
		if(signatureurl != null) {
			doctor.setSignatureurl(signatureurl);
		}
		doctorMapper.updateByPrimaryKeySelective(doctor);
		return doctor;
	}

	public Doctor getDoctor(Integer doctorid) {
		Doctor ret = doctorMapper.selectByPrimaryKey(doctorid);
		return ret;
	}

	public void modifyPwd(Integer doctorid, String oldPwd, String newPwd) {
		Doctor doctor = doctorMapper.selectByPrimaryKey(doctorid);
		if(PasswordUtil.isEqual(doctor.fetchPassword(), oldPwd, doctor.fetchPwdnonce())){
			String nPwd = PasswordUtil.generatePwd(newPwd, doctor.fetchPwdnonce());
			doctor.setPassword(nPwd);
			doctorMapper.updateByPrimaryKey(doctor);
		}else{
			throw new HandleException(ErrorCode.NORMAL_ERROR, "旧密码错误");
		}
	}
	
	/**
	 * 忘记密码
	 * @param doctorid
	 */
	public void getVerifyCode(String phone) {
		Example ex = new Example(Doctor.class);
		ex.createCriteria().andEqualTo("phone", phone);
		Doctor doctor = doctorMapper.selectOneByExample(ex);
		if(doctor == null) {
			throw new HandleException(ErrorCode.NORMAL_ERROR, "用户不存在");
		}
		
		Random r = new Random();
		int number = r.nextInt(999999);
		String code = String.format("%06d", number);
		//十分钟内有效，覆盖无效
		redissonUtil.set("DOCTOR_VERIFY_CODE_"+phone, code, 10*60*1000L);
		
		System.out.println(code);
		//sms.sendSMS(phone, code);
	}
	
	public String verifyCode(String phone, String code) {
		String rawcode = (String) redissonUtil.get("DOCTOR_VERIFY_CODE_"+phone);
		if(rawcode == null) {
			throw new HandleException(ErrorCode.NORMAL_ERROR, "验证码已失效");
		}
		if(rawcode.equals(code)) {
			Random r = new Random();
			int number = r.nextInt(999999999);
			String newcode = String.format("%09d", number);
			//十分钟内有效，覆盖无效
			redissonUtil.set("DOCTOR_AUTH_CODE_"+phone, newcode, 10*60*1000L);
			return newcode;			
		}else {
			throw new HandleException(ErrorCode.NORMAL_ERROR, "验证码错误，重复请求请输入最后一次的验证码");
			
		}
	}
	
	public void resetPwd(String phone, String code, String newPwd) {
		Example ex = new Example(Doctor.class);
		ex.createCriteria().andEqualTo("phone", phone);
		Doctor doctor = doctorMapper.selectOneByExample(ex);
		if(doctor == null) {
			throw new HandleException(ErrorCode.NORMAL_ERROR, "用户不存在");
		}
		
		String rawcode = (String) redissonUtil.get("DOCTOR_AUTH_CODE_"+phone);
		if(rawcode == null) {
			throw new HandleException(ErrorCode.NORMAL_ERROR, "授权已过期");
		}
		if(rawcode.equals(code)) {
			String nPwd = PasswordUtil.generatePwd(newPwd, doctor.fetchPwdnonce());
			doctor.setPassword(nPwd);
			doctorMapper.updateByPrimaryKey(doctor);
			return;			
		}else {
			throw new HandleException(ErrorCode.NORMAL_ERROR, "授权错误");
			
		}	
	}

	public Doctor modifyPhone(Integer doctorid, String phone, String pwd) {
		
		Doctor doctor = doctorMapper.selectByPrimaryKey(doctorid);
		if(doctor == null){
			throw new HandleException(ErrorCode.NORMAL_ERROR, "用户不存在");
		}else{
			if(PasswordUtil.isEqual(doctor.fetchPassword(), pwd, doctor.fetchPwdnonce())){
				doctor.setPhone(phone);
				doctorMapper.updateByPrimaryKey(doctor);
				return doctor;
			}else{
				throw new HandleException(ErrorCode.NORMAL_ERROR, "用户密码错误");
			}
		}
	}

	public List<Doctor> getDoctors(String phone, String name, Integer hospitalid, Integer pageIndex, Integer pageSize) {
		Example ex = new Example(Doctor.class);
		Criteria c = ex.createCriteria();
		if(!phone.trim().isEmpty()) {
			c.andEqualTo("phone", phone);
		}
		if(!name.trim().isEmpty()) {
			c.andLike("name", "%"+name+"%");
		}
		c.andEqualTo("hospitalid", hospitalid);
		
		RowBounds rowBounds = new RowBounds((pageIndex-1)*pageSize, pageSize);
		List<Doctor> doctor = doctorMapper.selectByExampleAndRowBounds(ex, rowBounds);
		return doctor;
	}

	public Doctor add(String phone, String password, String name, String department, Integer hospitalid) {
		Example ex = new Example(Doctor.class);
		ex.createCriteria().andEqualTo("phone", phone);
		Doctor doctor = doctorMapper.selectOneByExample(ex);
		if(doctor != null){
			throw new HandleException(ErrorCode.NORMAL_ERROR, "用户已存在");
		}else{
			doctor = new Doctor();
			doctor.setPhone(phone);
			
			String nonce = PasswordUtil.generateNonce();
			doctor.setPwdnonce(nonce);
			
			String newPwd = PasswordUtil.generatePwd(password, nonce);
			doctor.setPassword(newPwd);
			doctor.setCreatetime(new Date());
			doctor.setName(name);
			doctor.setDepartment(department);
			doctor.setHospitalid(hospitalid);
			doctorMapper.insertUseGeneratedKeys(doctor);
		}
		return doctor;
	}

	public Doctor modify(Doctor doctor) {
		if(doctor.getId() == null) {
			throw new HandleException(ErrorCode.ARG_ERROR, "参数错误");
		}
		doctor.setPassword(null);//不修改密码
		int ret = 0;
		try {
			ret = doctorMapper.updateByPrimaryKeySelective(doctor);
		}catch (Exception e) {
			throw new HandleException(ErrorCode.NORMAL_ERROR, "更新失败，该手机号可能已存在");
		}
		if(ret!=1) {
			throw new HandleException(ErrorCode.NORMAL_ERROR, "更新失败");
		}
		return doctor;
	}

	public void del(Integer doctorid) {
		int ret = doctorMapper.deleteByExample(doctorid);
		if(ret!=1) {
			throw new HandleException(ErrorCode.NORMAL_ERROR, "删除失败");
		}
	}
	
}
