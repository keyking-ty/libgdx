package com.keyking.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.keyking.data.TelInfo;
import com.keyking.frame.LoginScreen;

public class DataOperateUtil implements Instances{
	
	public static String getExtends(String fileName){
		int index = fileName.lastIndexOf(".");
		return fileName.substring(index + 1,fileName.length());
	}
	
	/**
	 * 读取
	 * @param fileName
	 * @param ignoreRow 忽略前几行
	 * @return
	 */
	public static List<TelInfo> readExcel(String fileName,int ignoreRow){
		if (getExtends(fileName).equals("xls")){
			return readXls(fileName,ignoreRow);
		}else {
			return readXlxs(fileName,ignoreRow);
		}
	}
	
	public static List<TelInfo> readXls(String fileName , int ignoreRow){
		List<TelInfo> datas = new ArrayList<TelInfo>();
		try {
			InputStream is = new FileInputStream(fileName);
			HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
			for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
				 String name = hssfWorkbook.getSheetName(numSheet);
				 HSSFSheet hssfSheet = hssfWorkbook.getSheet(name);
				 if (hssfSheet == null) {
					 continue;
				 }
				 int rowNum = hssfSheet.getLastRowNum();
				 for (int row = ignoreRow ; row < rowNum ; row ++){
					 HSSFRow hssfRow = hssfSheet.getRow(row);
					 TelInfo info = new TelInfo();
					 info.setName(getValue(hssfRow.getCell(0)));
					 String tel = getValue(hssfRow.getCell(1));
					 tel = tel.substring(0,tel.length()-1);
					 info.setTelephone(tel);
					 datas.add(info);
				 }
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return datas;
	}
	
	public static List<TelInfo> readXlxs(String fileName,int ignoreRow){
		List<TelInfo> datas = new ArrayList<TelInfo>();
		try {
			InputStream is = new FileInputStream(fileName);
			XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);
			 for (int numSheet = 0; numSheet < xssfWorkbook.getNumberOfSheets(); numSheet++) {
				String name = xssfWorkbook.getSheetName(numSheet);
				XSSFSheet xssfSheet = xssfWorkbook.getSheet(name);
				if (xssfSheet == null){
					continue;
				}
				int rowNum = xssfSheet.getLastRowNum();
				for (int row = ignoreRow ; row < rowNum ; row ++){
					 XSSFRow xssfRow = xssfSheet.getRow(row);
					 TelInfo info = new TelInfo();
					 info.setName(getValue(xssfRow.getCell(0)));
					 String tel = getValue(xssfRow.getCell(1));
					 tel = tel.substring(0,tel.length()-1);
					 info.setTelephone(tel);
					 datas.add(info);
				}
			 }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return datas;
	}
	
     private static String getValue(XSSFCell xssfRow) {
         if (xssfRow.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
             return String.valueOf(xssfRow.getBooleanCellValue());
         } else if (xssfRow.getCellType() == Cell.CELL_TYPE_NUMERIC) {
             return String.valueOf(xssfRow.getNumericCellValue());
         } else {
             return String.valueOf(xssfRow.getStringCellValue());
         }
     }
 
     private static String getValue(HSSFCell hssfCell) {
         if (hssfCell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
             return String.valueOf(hssfCell.getBooleanCellValue());
         } else if (hssfCell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
             return String.valueOf(hssfCell.getNumericCellValue());
         } else {
             return String.valueOf(hssfCell.getStringCellValue());
         }
     }
     
     private static String randomName(){
    	StringBuffer sb = new StringBuffer();
 		Random random = new Random();
 		int num = MATH.random(8,16);
 		for (int i = 0 ; i < num ; i++){
 			int index = random.nextInt(LoginScreen.TOKENS.length);
 			sb.append(LoginScreen.TOKENS[index]);
 		}
    	return sb.toString();
     }
     
     public static List<TelInfo> readText(String fileName, boolean flag){
    	 List<TelInfo> tels = new ArrayList<TelInfo>();
    	 try {
			LineNumberReader reader = new LineNumberReader(new FileReader(fileName));
			String line = null;
			while ((line = reader.readLine()) != null){
				String[] ss = line.split(" ");
				String name = null,telephone = null;
				if (ss.length > 1){
					name       = ss[0];
					telephone  = ss[1];
				}else{
					name       = randomName();
					telephone  = ss[0];
				}
				check(telephone);
				if (telephone != null){
					TelInfo tel = new TelInfo();
					tel.setName(name);
					tel.setTelephone(telephone);
					tel.setUserId(flag ? 1 : 0);
					tels.add(tel);
				}
			}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return tels;
     }
     
     public static String check(String str) {
    	StringBuffer sb = new StringBuffer();
    	for (int i = 0 ; i < str.length() ; i ++){
    		char c = str.charAt(i);
    		if (c >= '0' && c <= '9'){
    			sb.append(c);
    		}
    	}
    	if (sb.length() < 11){
    		return null;
    	}
    	str = sb.toString();
		int index = str.indexOf("1");
		if (index > 0){
			str = str.substring(index,str.length());
		}
		return str;
	}
     
	public static void export(String fileName, List<TelInfo> tels){
    	try {
			FileWriter writer = new FileWriter(new File(fileName + "/" + "tels.txt"));
			for (TelInfo tel : tels){
				writer.write(tel.getTelephone() + "\n");
			}
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
}
 
 
