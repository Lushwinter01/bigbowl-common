package com.bigbowl.common.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <h2>基于 poi 的 Excel 文件处理工具</h2>
 * @author likey
 * @author Chuck
 * @version 0.2
 */
public class ImportXlsUtil {
	private static Logger log = LoggerFactory.getLogger(ImportXlsUtil.class);

	/**
	 * 读取Excel表格表头的内容
	 *
	 * @param is
	 * @param sheetIndex
	 *            第几个sheet
	 * @return
	 */
	public static String[] readExcelTitle(InputStream is, int sheetIndex, int index) {
		if (sheetIndex <= 0) {
			sheetIndex = 1;
		}
		String[] title = null;
		try {
			Workbook wb = WorkbookFactory.create(is);
			Sheet sheet = wb.getSheetAt(sheetIndex - 1);
			Row row = sheet.getRow(index - 1);
			// 标题总列数
			int colNum = row.getPhysicalNumberOfCells();
			title = new String[colNum];
			for (int i = 0; i < colNum; i++) {
				title[i] = getStringCellValue(row.getCell(i));
			}
		} catch (IOException e) {
			log.error("read file error", e);
		} catch (InvalidFormatException e) {
			log.error("file format error", e);
		}
		return title;
	}

	/**
	 * 读取Excel数据内容
	 *
	 * @param is
	 * @param sheetIndex
	 *            第几个sheet
	 * @param index
	 *            从第几行读内容
	 * @return
	 */
	public static String[][] readExcelContent(InputStream is, int sheetIndex, int index, int colNum) {
		String[][] content = null;
		try {
			if (sheetIndex <= 0) {
				sheetIndex = 1;
			}
			Workbook wb = WorkbookFactory.create(is);
			Sheet sheet = wb.getSheetAt(sheetIndex - 1);
			// 得到总行数
			int rowNum = sheet.getLastRowNum();
			// 正文内容应该从第二行开始,第一行为表头的标题
			content = new String[rowNum - index + 1][colNum];
			FormulaEvaluator evaluator = wb.getCreationHelper().createFormulaEvaluator();
			for (int i = index; i <= rowNum; i++) {
				Row row = sheet.getRow(i);
				if (row != null) {
					int j = 0;
					while (j < colNum) {
						content[i - index][j] = getStringCellValue(row.getCell(j),evaluator);
						j++;
					}
				}
			}
		} catch (IOException e) {
			log.error("read file error", e);
		} catch (InvalidFormatException e) {
			log.error("corrupt file", e);
		}
		return content;
	}
	/**
	 * 获取单元格数据内容为字符串类型的数据
	 *
	 * @param cell
	 *            Excel单元格
	 * @return String 单元格数据内容
	 */
	private static String getStringCellValue(Cell cell) {
		String strCell = "";
		if (cell == null)
			return strCell;
		switch (cell.getCellType()) {
			case Cell.CELL_TYPE_STRING:
				strCell = cell.getStringCellValue();
				break;
			case Cell.CELL_TYPE_NUMERIC:
				double d = cell.getNumericCellValue();
				int i = (int) d;
				if (d == 0 || (d >= 1 && d / i == 1)) {
					DecimalFormat df = new DecimalFormat("0");
					strCell = String.valueOf(df.format(cell.getNumericCellValue()));
				} else {
					strCell = String.valueOf(cell.getNumericCellValue());
				}
				break;
			case Cell.CELL_TYPE_BOOLEAN:
				strCell = String.valueOf(cell.getBooleanCellValue());
				break;
			case Cell.CELL_TYPE_BLANK:
				strCell = "";
				break;
			case Cell.CELL_TYPE_FORMULA:
				try {
					strCell = String.valueOf(cell.getCellFormula());
				} catch (IllegalStateException e) {
					strCell = String.valueOf(cell.getRichStringCellValue());
				}
				break;
			default:
				strCell = "";
				break;
		}
		if (strCell.equals("") || strCell == null) {
			return "";
		}
		return strCell;
	}
	/**
	 * 获取单元格数据内容为字符串类型的数据
	 *
	 * @param cell
	 *            Excel单元格
	 * @return String 单元格数据内容
	 */
	private static String getStringCellValue(Cell cell,FormulaEvaluator evaluator) {
		String strCell = "";
		if (cell == null)
			return strCell;
		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_STRING:
			strCell = cell.getStringCellValue();
			break;
		case Cell.CELL_TYPE_NUMERIC:
			double d = cell.getNumericCellValue();
			int i = (int) d;
			if (d == 0 || (d >= 1 && d / i == 1)) {
				DecimalFormat df = new DecimalFormat("0");
				strCell = String.valueOf(df.format(cell.getNumericCellValue()));
			} else {
				strCell = String.valueOf(cell.getNumericCellValue());
			}
			break;
		case Cell.CELL_TYPE_BOOLEAN:
			strCell = String.valueOf(cell.getBooleanCellValue());
			break;
		case Cell.CELL_TYPE_BLANK:
			strCell = "";
			break;
		case Cell.CELL_TYPE_FORMULA:
			try {
				strCell = getFormulaCellValue(cell,evaluator);
			} catch (IllegalStateException e) {
				strCell = String.valueOf(cell.getRichStringCellValue());
			}
			break;
		default:
			strCell = "";
			break;
		}
		if (strCell.equals("") || strCell == null) {
			return "";
		}
		return strCell;
	}

	/**
	 * 含有公式的单元格获取值
	 *
	 * @param cell
	 *            Excel单元格
	 * @return String 单元格数据内容
	 */
	private static String getFormulaCellValue(Cell cell,FormulaEvaluator evaluator) {
		String strCell = "";
		if (cell == null)
		return strCell;
		CellValue cellValue = evaluator.evaluate(cell);
		int cellType =  cellValue.getCellType();
		switch (cellType) {
			case Cell.CELL_TYPE_STRING:
				strCell = cell.getStringCellValue();
				break;
			case Cell.CELL_TYPE_NUMERIC:
				double d = cell.getNumericCellValue();
				int i = (int) d;
				if (d == 0 || (d >= 1 && d / i == 1)) {
					DecimalFormat df = new DecimalFormat("0");
					strCell = String.valueOf(df.format(cell.getNumericCellValue()));
				} else {
					strCell = String.valueOf(cell.getNumericCellValue());
				}
				break;
			case Cell.CELL_TYPE_BOOLEAN:
				strCell = String.valueOf(cell.getBooleanCellValue());
				break;
			case Cell.CELL_TYPE_BLANK:
				strCell = "";
				break;
			default:
				strCell = "";
				break;
		}
		if (strCell.equals("") || strCell == null) {
			return "";
		}
		return strCell;
	}


	/**
	 * import excel all
	 *
	 * @author likey
	 * @param fileURL
	 * @return
	 * @throws Exception
	 */
	public static String[][] importExcelUtil(String fileURL){
		InputStream is = null;
		String[][] map = null;
		try {
			is = new FileInputStream(fileURL);
			map = readExcelContent(is, 1, 0, 40);
		} catch (FileNotFoundException e) {
			log.error("file not found", e);
		} finally {
			try {
				if (is != null)
				{
					is.close();
				}
			} catch (IOException e) {
			}
		}
		return map;
	}
	public static boolean isExistsData(String[] arr){
		for(String data:arr){
			return StringUtils.isNotBlank(data);
		}
		return false;
	}
}
