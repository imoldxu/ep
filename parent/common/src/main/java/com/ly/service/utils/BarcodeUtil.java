package com.ly.service.utils;

import com.ly.service.context.ErrorCode;
import com.ly.service.context.HandleException;

public class BarcodeUtil {

	public static String generateBarcode(int type, Long id) {
		long offset = 0L;
		if(type==TYPE_PRESCRIPTION) {
			offset = 150331L;//offset偏移值
		}else if(type==TYPE_PATIENT) {
			offset = 550925L;
		}
		Long rawCode = id+offset;
		String rawCodeStr = String.format("%09d", rawCode);
		String validNum = calcValidNum(rawCodeStr);
	
		String code = rawCodeStr+validNum;
		return code;
	}


	/**
	   *  将条码从后向前数（包含校验位），将奇数位的值相加，偶数位的值相加，偶数位的值和*3，再与奇数位值相加在一起，再用大于等于该数的整数去减，得到的结果就是校验值
	 * @param rawData
	 * @return
	 */
	private static String calcValidNum(String rawData) {
		final char[] cs = rawData.toUpperCase().toCharArray();
		int evenNums = 0;
		int oddNums = 0;
		for(int i=cs.length-1; i>=0; i--) {
			if(i%2 == 0) {
				evenNums += (cs[i] - '0');//偶数位
				
			}else {
				oddNums += (cs[i] - '0');//奇数位
			}
		}
		int nums = oddNums+evenNums*3;
		int validNum = nums % 10;
		if(validNum != 0) {
			validNum = 10 -validNum;
		}
		return String.valueOf(validNum);
	}
	
	public static final int TYPE_PRESCRIPTION = 1;
	public static final int TYPE_PATIENT = 2;
	
	public static Long barcode2ID(int type, String code) {
		long offset = 0L;
		if(type==TYPE_PRESCRIPTION) {
			offset = 150331L;//offset偏移值
		}else if(type==TYPE_PATIENT) {
			offset = 550925L;
		}
		
		String rawCode = code.substring(0, code.length()-1);
		String validNum = code.substring(code.length()-1);
		String calcValidNum = calcValidNum(rawCode);
		
		if(validNum.equals(calcValidNum)) {
			Long codenum = Long.valueOf(rawCode);
			Long id = codenum-offset;
			return id;
		}else {
			throw new HandleException(ErrorCode.NORMAL_ERROR, "无效的码");
		}
	}


}
