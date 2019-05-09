package com.excelHelper;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;

import com.bean.Staff;

/**
 * ����excel
 * @author LJC
 *
 */

public class PoiRead {
	public static List<Staff> readExcel(String fileName) {
		// ��Ҫ������Excel�ļ�
		File file = new File(fileName);
		List<Staff> staffs = new ArrayList<>();
		try {
			// ��ȡ������
			FileInputStream fs = FileUtils.openInputStream(file);
			HSSFWorkbook workbook = new HSSFWorkbook(fs);
			// ��ȡ��һ��������
			HSSFSheet hs = workbook.getSheetAt(0);
			// ��ȡSheet�ĵ�һ���кź����һ���к�
			int last = hs.getLastRowNum();
			int first = hs.getFirstRowNum();
			// ������ȡ��Ԫ�������Ϣ
			for (int i = first+1; i <= last; i++) {
				HSSFRow row = hs.getRow(i);
				int firstCellNum = row.getFirstCellNum();// ��ȡ�����еĵ�һ���к�
				int lastCellNum = row.getLastCellNum();// ��ȡ�����е����һ���к�
//				for (int j = firstCellNum; j < lastCellNum; j++) {
//					HSSFCell cell = row.getCell(j);
//			         cell.setCellType(CellType.STRING);
//					String value = cell.getStringCellValue();
//				}
				Staff staff = new Staff();
				HSSFCell cell = row.getCell(firstCellNum);
				cell.setCellType(CellType.STRING);
				staff.setName(cell.getStringCellValue());
				cell = row.getCell(firstCellNum+1);
				staff.setSex(cell.getStringCellValue());
				cell = row.getCell(firstCellNum+2);
				staff.setPhone(cell.getStringCellValue());
				staffs.add(staff);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return staffs;
	}
	
	public static void main(String[] args) {
		String fileName = "e:/sheet2.xls";
		List<Staff> staffs = readExcel(fileName);
		for(Staff s : staffs) {
			System.out.println(s);
		}
	}
}