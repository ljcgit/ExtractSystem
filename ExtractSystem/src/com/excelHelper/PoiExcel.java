package com.excelHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.bean.Staff;
import com.dao.StaffDao;

/**
 * ����excel
 * @author LJC
 *
 */
public class PoiExcel {
	public static void saveExcel(List<List<Staff>> staffs,String eName) throws SQLException {
		String title[] = { "����", "�Ա�","��ϵ��ʽ" };
		// 1.����Excel������
		HSSFWorkbook workbook = new HSSFWorkbook();
		
		for(int ii=0;ii<staffs.size();ii++) {
			
			// 2.����һ��������
			HSSFSheet sheet = workbook.createSheet("С��"+ii);
			// 3.������һ��
			HSSFRow row = sheet.createRow(0);
			HSSFCell cell = null;
			// 4.�����һ������
			for (int i = 0; i < title.length; i++) {
				cell = row.createCell(i);
				cell.setCellValue(title[i]);
			}
			List<Staff> st = staffs.get(ii);
			for(int i =0;i<st.size();i++){
				HSSFRow row2 = sheet.createRow(i+1);
				HSSFCell cell2 = row2.createCell(0);
				cell2.setCellValue(st.get(i).getName());
				cell2 = row2.createCell(1);
				cell2.setCellValue(st.get(i).getSex());
				cell2 = row2.createCell(2);
				cell2.setCellValue(st.get(i).getPhone());
			}
		
		}
		// ����һ���ļ�,��Excel���ݴ���
		File file = new File("e:/"+eName+".xls");
		try {
			file.createNewFile();
			FileOutputStream stream = FileUtils.openOutputStream(file);
			workbook.write(stream);
			stream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws SQLException {
		List<Staff> staffs = StaffDao.getAll();
	}
}