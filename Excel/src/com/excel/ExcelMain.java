package com.excel;

import java.io.File;
import java.io.FileOutputStream;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelMain {
	public static void main(String[] args) {
		ExcelMethods excel = new ExcelMethods();
		
		excel.excelWriter("/Users/misonaru/Desktop");
	}
	
	static void excelWriter(String filePath) {
		XSSFWorkbook workbook = null;
		XSSFSheet sheet = null;
		XSSFRow row = null;
		XSSFCell cell = null;
		
		// 3행 3열
		
		try {
			workbook = new XSSFWorkbook();
			sheet = workbook.createSheet("sheet1");
			
			row = sheet.createRow(0);
			cell = row.createCell(0);
			cell.setCellValue("이름");
			cell = row.createCell(1);
			cell.setCellValue("번호");
			
			row = sheet.createRow(1);
			cell = row.createCell(1);
			cell.setCellValue("백종헌");
			cell = row.createCell(1);
			cell.setCellValue("01077107418");
			
			File file = new File(filePath);
			FileOutputStream fos = new FileOutputStream(file);
			workbook.write(fos);
			
			if (fos != null)	fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
