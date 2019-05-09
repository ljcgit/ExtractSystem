package com.excelHelper; 
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.DVConstraint;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFDataValidation;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
public class PoiExpExcel {
	public static void main(String[] args) {
		// ��ȡ����Xml·��
		String path = "WebContent/student.xml";
		File file = new File(path);
		SAXBuilder builder = new SAXBuilder();
		// ����xml�ļ�
		try {
			Document document = builder.build(file);
			// ����Excel
			HSSFWorkbook workbook = new HSSFWorkbook();
			// �������
			HSSFSheet sheet = workbook.createSheet("sheet0");
			// ��ȡXml�ļ��ĸ��ڵ�
			Element root = document.getRootElement();
			// ��ȡģ������
			String tempName = root.getAttributeValue("name");
			// �����п�
			Element colgroup = root.getChild("colgroup");
			setColumnWidth(sheet, colgroup);
			// ���ñ���
			int rownum = 0;
			int column = 0;
			Element title = root.getChild("title");
			List<Element> trs = title.getChildren("tr");
			for (int i = 0; i < trs.size(); i++) {
				Element tr = trs.get(i);
				List<Element> tds = tr.getChildren("td");
				HSSFRow row = sheet.createRow(rownum);
				HSSFCellStyle cellStyle = workbook.createCellStyle();// ������Ԫ���ʽ
				cellStyle.setAlignment(HorizontalAlignment.CENTER);// �������
				for (int j = 0; j < tds.size(); j++) {
					Element td = tds.get(j);
					HSSFCell cell = row.createCell(j);
					Attribute rowspan = td.getAttribute("rowspan");
					Attribute colspan = td.getAttribute("colspan");
					Attribute value = td.getAttribute("value");
					if (value != null) {
						String content = value.getValue();

						cell.setCellValue(content);
						int rspan = rowspan.getIntValue() - 1;
						int cspan = colspan.getIntValue() - 1;
						// ��������
						HSSFFont font = workbook.createFont();
						font.setFontName("����_GB2312");
						//font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);// ����Ӵ�
						// font.setFontHeight((short)12);
						font.setFontHeightInPoints((short) 12);
						cellStyle.setFont(font);
						cell.setCellStyle(cellStyle);
						// �ϲ���Ԫ�����
						sheet.addMergedRegion(new CellRangeAddress(rspan, rspan, 0, cspan));
					}

				}
				rownum++;

			}
			// ���ñ�ͷ
			Element thead = root.getChild("thead");
			trs = thead.getChildren("tr");
			for (int i = 0; i < trs.size(); i++) {
				Element tr = trs.get(i);
				HSSFRow row = sheet.createRow(rownum);
				List<Element> ths = tr.getChildren("th");
				for (int j = 0; j < ths.size(); j++) {
					Element th = ths.get(j);
					HSSFCell cell = row.createCell(j);
					Attribute value = th.getAttribute("value");
					if (value != null) {
						String content = value.getValue();
						cell.setCellValue(content);

					}
				}
				rownum++;
			}

			// ��������������ʽ
			Element tbody = root.getChild("tbody");
			Element tr = tbody.getChild("tr");
			int repeat = tr.getAttribute("repeat").getIntValue();
			List<Element> tds = tr.getChildren("td");
			for (int i = 0; i < repeat; i++) {
				HSSFRow row = sheet.createRow(rownum);
				for (int j = 0; j < tds.size(); j++) {
					Element td = tds.get(j);
					HSSFCell cell = row.createCell(j);
					setType(workbook, cell, td);
				}
			}
			rownum++;
			// ����Excel����ģ��
			File tempFile = new File("e:/" + tempName + ".xls");
			tempFile.delete();
			tempFile.createNewFile();
			FileOutputStream fos = FileUtils.openOutputStream(tempFile);
			workbook.write(fos);
			fos.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void setType(HSSFWorkbook workbook, HSSFCell cell, Element td) {
		Attribute typeAttr = td.getAttribute("type");
		String type = typeAttr.getValue();
		HSSFDataFormat format = workbook.createDataFormat();
		HSSFCellStyle cellStyle = workbook.createCellStyle();
		if ("NUMERIC".equalsIgnoreCase(type)) {
			//cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
			Attribute formatAttr = td.getAttribute("format");
			String formatValue = formatAttr.getValue();
			formatValue = StringUtils.isNotBlank(formatValue) ? formatValue : "#,##0.00";
			cellStyle.setDataFormat(format.getFormat(formatValue));
		} else if ("STRING".equalsIgnoreCase(type)) {
			cell.setCellValue("");
			cell.setCellType(CellType.STRING);
			cellStyle.setDataFormat(format.getFormat("@"));
		} else if ("DATE".equalsIgnoreCase(type)) {
			cell.setCellType(CellType.STRING);
			cellStyle.setDataFormat(format.getFormat("yyyy-m-d"));
		} else if ("ENUM".equalsIgnoreCase(type)) {
			CellRangeAddressList regions = new CellRangeAddressList(cell.getRowIndex(), cell.getRowIndex(),
					cell.getColumnIndex(), cell.getColumnIndex());

			Attribute enumAttr = td.getAttribute("format");
			String enumValue = enumAttr.getValue();
			// ���������б�����
			DVConstraint constraint = DVConstraint.createExplicitListConstraint(enumValue.split(","));
			// ������Ч�Զ���
			HSSFDataValidation dataValidation = new HSSFDataValidation(regions, constraint);
			workbook.getSheetAt(0).addValidationData(dataValidation);
		}
		cell.setCellStyle(cellStyle);

	}

	private static void setColumnWidth(HSSFSheet sheet, Element colgroup) {
		List<Element> cols = colgroup.getChildren("col");// ��ȡcol�Ľڵ�
		for (int i = 0; i < cols.size(); i++) {
			Element col = cols.get(i);
			Attribute width = col.getAttribute("width");// ��ȡÿ���е�width����
			String unit = width.getValue().replaceAll("[0-9,\\.]", "");// ��λ
			String value = width.getValue().replaceAll(unit, "");// ��ֵ
			int v = 0;
			if (StringUtils.isBlank(unit) || "px".endsWith(unit)) {
				v = Math.round(Float.parseFloat(value) * 37F);
			} else if ("em".endsWith(unit)) {
				v = Math.round(Float.parseFloat(value) * 267.5F);
			} // �Ե�λ�����ж�
			sheet.setColumnWidth(i, v);
		}

	}
}