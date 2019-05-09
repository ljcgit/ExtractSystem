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
		// 获取解析Xml路径
		String path = "WebContent/student.xml";
		File file = new File(path);
		SAXBuilder builder = new SAXBuilder();
		// 解析xml文件
		try {
			Document document = builder.build(file);
			// 创建Excel
			HSSFWorkbook workbook = new HSSFWorkbook();
			// 创建表格
			HSSFSheet sheet = workbook.createSheet("sheet0");
			// 获取Xml文件的根节点
			Element root = document.getRootElement();
			// 获取模板名称
			String tempName = root.getAttributeValue("name");
			// 设置列宽
			Element colgroup = root.getChild("colgroup");
			setColumnWidth(sheet, colgroup);
			// 设置标题
			int rownum = 0;
			int column = 0;
			Element title = root.getChild("title");
			List<Element> trs = title.getChildren("tr");
			for (int i = 0; i < trs.size(); i++) {
				Element tr = trs.get(i);
				List<Element> tds = tr.getChildren("td");
				HSSFRow row = sheet.createRow(rownum);
				HSSFCellStyle cellStyle = workbook.createCellStyle();// 创建单元格格式
				cellStyle.setAlignment(HorizontalAlignment.CENTER);// 标题居中
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
						// 设置字体
						HSSFFont font = workbook.createFont();
						font.setFontName("仿宋_GB2312");
						//font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);// 字体加粗
						// font.setFontHeight((short)12);
						font.setFontHeightInPoints((short) 12);
						cellStyle.setFont(font);
						cell.setCellStyle(cellStyle);
						// 合并单元格居中
						sheet.addMergedRegion(new CellRangeAddress(rspan, rspan, 0, cspan));
					}

				}
				rownum++;

			}
			// 设置表头
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

			// 设置数据区域样式
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
			// 生成Excel导入模板
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
			// 加载下拉列表内容
			DVConstraint constraint = DVConstraint.createExplicitListConstraint(enumValue.split(","));
			// 数据有效性对象
			HSSFDataValidation dataValidation = new HSSFDataValidation(regions, constraint);
			workbook.getSheetAt(0).addValidationData(dataValidation);
		}
		cell.setCellStyle(cellStyle);

	}

	private static void setColumnWidth(HSSFSheet sheet, Element colgroup) {
		List<Element> cols = colgroup.getChildren("col");// 获取col的节点
		for (int i = 0; i < cols.size(); i++) {
			Element col = cols.get(i);
			Attribute width = col.getAttribute("width");// 获取每列中的width属性
			String unit = width.getValue().replaceAll("[0-9,\\.]", "");// 单位
			String value = width.getValue().replaceAll(unit, "");// 数值
			int v = 0;
			if (StringUtils.isBlank(unit) || "px".endsWith(unit)) {
				v = Math.round(Float.parseFloat(value) * 37F);
			} else if ("em".endsWith(unit)) {
				v = Math.round(Float.parseFloat(value) * 267.5F);
			} // 对单位进行判断
			sheet.setColumnWidth(i, v);
		}

	}
}