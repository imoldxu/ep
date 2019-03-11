package com.ly.service.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import com.ly.service.context.ErrorCode;
import com.ly.service.context.HandleException;
import com.ly.service.entity.Drug;


public class ExcelUtils {
	// 总行数
	private int totalRows = 0;
	// 总条数
	private int totalCells = 0;
	// 错误信息接收器
	private String errorMsg;

	// 构造方法
	public ExcelUtils() {
	}

	// 获取总行数
	public int getTotalRows() {
		return totalRows;
	}

	// 获取总列数
	public int getTotalCells() {
		return totalCells;
	}

	// 获取错误信息
	public String getErrorInfo() {
		return errorMsg;
	}

	/**
	 * 验证EXCEL文件
	 * 
	 * @param filePath
	 * @return
	 */
	public boolean validateExcel(String filePath) {
		if (filePath == null || !(WDWUtil.isExcel2003(filePath) || WDWUtil.isExcel2007(filePath))) {
			errorMsg = "文件名不是excel格式";
			return false;
		}
		return true;
	}

	/**
	 * 读EXCEL文件，获取客户信息集合
	 * 
	 * @param fielName
	 * @return
	 * @throws IOException 
	 */
	public List<Drug> getExcelInfo(String fileName, MultipartFile Mfile) throws IOException {

		// 初始化客户信息的集合
		List<Drug> drugList = new ArrayList<Drug>();
		// 初始化输入流
		InputStream is = null;
		try {
			// 验证文件名是否合格
			if (!validateExcel(fileName)) {
				throw new IOException("文件名不是excel格式");
			}
			// 根据文件名判断文件是2003版本还是2007版本
			boolean isExcel2003 = true;
			if (WDWUtil.isExcel2007(fileName)) {
				isExcel2003 = false;
			}
			// 根据新建的文件实例化输入流
			is = Mfile.getInputStream();//new FileInputStream(file1);
			// 根据excel里面的内容读取客户信息
			drugList = getExcelInfo(is, isExcel2003);
			is.close();
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					is = null;
					e.printStackTrace();
				}
			}
		}
		return drugList;
	}

	/**
	 * 根据excel里面的内容读取客户信息
	 * 
	 * @param is
	 *            输入流
	 * @param isExcel2003
	 *            excel是2003还是2007版本
	 * @return
	 * @throws IOException
	 */
	private List<Drug> getExcelInfo(InputStream is, boolean isExcel2003) throws IOException{
		List<Drug> drugList = null;
		try {
			/** 根据版本选择创建Workbook的方式 */
			
			// 当excel是2003时
			if (isExcel2003) {
				//Workbook wb = new HSSFWorkbook(is);
				throw new IOException("仅支持excel2007以上版本");
			} else {// 当excel是2007时
				XSSFWorkbook wb = new XSSFWorkbook(is);
				drugList = readExcelValue(wb);
			}
			// 读取Excel里面的信息		
		} catch (IOException e) {
			e.printStackTrace();
		}
		return drugList;
	}

  /**
   * @param wb
   * @return
   */
  private List<Drug> readExcelValue(XSSFWorkbook wb){ 
      //得到第一个shell  
	  XSSFSheet sheet=wb.getSheetAt(0);
       
      //得到Excel的行数
      this.totalRows=sheet.getPhysicalNumberOfRows();
       
      //得到Excel的列数(前提是有行数)
       if(totalRows>=1 && sheet.getRow(0) != null){
            this.totalCells=sheet.getRow(0).getPhysicalNumberOfCells();
       }
       
       List<Drug> drugList=new ArrayList<Drug>();
       Drug drug;
       for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++){
			XSSFRow row = sheet.getRow(rowIndex);
			if (row == null)
			    continue;
			
			drug = new Drug();
			XSSFCell nameCell = row.getCell(0); // 药品名列
			XSSFCell shortnameCell = row.getCell(1); // 药品简列
			XSSFCell standardCell = row.getCell(2); // 规格列
			XSSFCell formCell = row.getCell(3);//剂型
			//XSSFCell priceCell = row.getCell(4); // 价格列
			XSSFCell unitCell = row.getCell(4); // 计价单位
			XSSFCell categoryCell = row.getCell(5); // 分类
			//XSSFCell subCategoryCell = row.getCell(7);//子类
			XSSFCell singledoseCell = row.getCell(6);
			XSSFCell doseunitCell = row.getCell(7);
			XSSFCell usageCell = row.getCell(8);
			XSSFCell frequencyCell = row.getCell(9);
			XSSFCell companyCell = row.getCell(10);
			//XSSFCell fullkeysCell = row.getCell(11); // 拼音缩写
			//XSSFCell shortNameKeysCell = row.getCell(12); // 简称拼音缩写
			XSSFCell isZyCell = row.getCell(11);
			
			String drugname = nameCell.getStringCellValue();
			if(drugname!=null){
				drugname = drugname.trim();
			}
			if(drugname.isEmpty()){
				totalRows = rowIndex-1;
				break;
			}
			drug.setDrugname(drugname);
			String shortname = shortnameCell.getStringCellValue();
			if(shortname!=null){
				shortname = shortname.trim();
			}
			drug.setShortname(shortname);
			String standard = standardCell.getStringCellValue();
			if(standard!=null){
				standard = standard.trim();
			}
			drug.setStandard(standard);
			String category = categoryCell.getStringCellValue();
			if(category!=null){
				category = category.trim();
			}
			drug.setCategory(category);
			
//			String subcategory = subCategoryCell.getStringCellValue();
//			if(subcategory!=null){
//				subcategory = subcategory.trim();
//			}
//			drug.setSubcategory(subcategory);
			
//			if(priceCell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC){
//				drug.setPrice(priceCell.getNumericCellValue());
//			}else if(priceCell.getCellType() == XSSFCell.CELL_TYPE_STRING){
//				drug.setPrice(Double.valueOf(priceCell.getStringCellValue()));
//			}
			String unit = unitCell.getStringCellValue();
			if(unit!= null){
				unit = unit.trim();
			}
			drug.setUnit(unit);
			String form =formCell.getStringCellValue();
			if(form!=null){
				form = form.trim();
			}
			drug.setForm(form);
			if(singledoseCell.getCellType() == XSSFCell.CELL_TYPE_STRING){
				String singledose = singledoseCell.getStringCellValue();
				if(singledose!=null){
					singledose = singledose.trim();
				}
				drug.setSingledose(singledose);
			}else if(singledoseCell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC){
				drug.setSingledose(String.valueOf(singledoseCell.getNumericCellValue()));
			}
			drug.setDoseunit(doseunitCell.getStringCellValue());
			String frequency = frequencyCell.getStringCellValue();
			if(frequency!=null){
				frequency = frequency.trim();
			}
			drug.setFrequency(frequency);
			String defaultUsage = usageCell.getStringCellValue();
			if(defaultUsage!=null){
				defaultUsage = defaultUsage.trim();
			}
			drug.setDefaultusage(defaultUsage);
			//drug.setFullkeys(fullkeysCell.getStringCellValue());
			drug.setFullkeys(ChineseCharacterUtil.convertHanzi2Pinyin(drug.getDrugname(), false));
			//drug.setShortnamekeys(shortNameKeysCell.getStringCellValue());
			drug.setShortnamekeys(ChineseCharacterUtil.convertHanzi2Pinyin(drug.getShortname(), false));;
//			drug.setState(Drug.STATE_OK);//缺省上传之后药品可见
			String company = companyCell.getStringCellValue();
			if(company!=null){
				company = company.trim();
			}
			drug.setCompany(company);
			if(isZyCell.getCellType() == XSSFCell.CELL_TYPE_STRING){
				String isZyStr = isZyCell.getStringCellValue();
				if(isZyStr!=null){
					isZyStr = isZyStr.trim();
				}
				Integer isZy = Integer.parseInt(isZyStr);
				if(isZy != 1 && isZy !=2 ) {
					throw new HandleException(ErrorCode.NORMAL_ERROR, "中西药只能设置为1或2");
				}
				drug.setIszy(isZy);
			}else if(isZyCell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC){
				int isZy = (int) isZyCell.getNumericCellValue();
				if(isZy != 1 && isZy !=2 ) {
					throw new HandleException(ErrorCode.NORMAL_ERROR, "中西药只能设置为1或2");
				}
				drug.setIszy(isZy);
			}else {
				throw new HandleException(ErrorCode.NORMAL_ERROR, "中西药只能设置为1或2");
			}
			
			
			drugList.add(drug);
       }
           //添加
       return drugList;
  }
}
