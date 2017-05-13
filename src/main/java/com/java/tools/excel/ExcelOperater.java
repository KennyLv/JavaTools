package com.java.tools.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.util.CellRangeAddress;
//import org.apache.poi.ss.formula.eval.ErrorEval;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import com.java.tools.files.FileExtention;

public class ExcelOperater {

	public static void main(String[] args) {
		String filePath = System.getProperty("user.dir");
		ExcelOperater eo = new ExcelOperater();
		eo.readExcel(filePath + "\\upload\\Delivery App Content Matrix 2.28.17.xlsx");
	}

	public ExcelOperater() {
	}

	public List<String> readExcel(String path) {
		try {
			if (path != null && !path.equals("")) {
				String postfix = FileExtention.getPostfix(path);
				if (!postfix.equals("")) {
					if (postfix.equals("xls")) {
						// return readXls(path);
					} else if (postfix.equals("xlsx")) {
						// return readXlsxRow(path);
						return readXlsxByColumn(path);
					}
				} else {
					System.out.println(path + " : Not the Excel file!");
				}
			}
		} catch (IOException ex) {
		}
		return null;
	}

	private List<String> readXlsxByColumn(String path) throws IOException {
		List<String> list = new ArrayList<String>();
		System.out.println("XLSX Processing..." + path);

		InputStream is = new FileInputStream(path);
		XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);
		// int totalSheets = xssfWorkbook.getNumberOfSheets();
		// System.out.println("totalSheets = " + totalSheets);

		XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);

		if (xssfSheet != null) {
			// Total Rows
			int totalRows = xssfSheet.getLastRowNum();
			// Total Columns : USE cell num of first row as total cell nums:
			int totalColumns = xssfSheet.getRow(0).getLastCellNum();

			// GET first cell of each row as the key:
			String checkItemGroup = "";
			List<String> checkItems = new ArrayList<String>();
			for (int index = 0; index < totalRows; index++) {
				XSSFRow xssfRow = xssfSheet.getRow(index);
				String key = getValue(xssfRow.getCell(0));
				// System.out.println(key);
				if (isGroup(key)){
					checkItemGroup = key;
				} else {
					if (!checkItemGroup.equals("")){
						key = checkItemGroup + ":" + key;
					}
				}
				checkItems.add(index, key);
			}

			List<Object> allPackages = new ArrayList<Object>();
			for (int packageId = 1; packageId < totalColumns; packageId++) {
				List<String> packageItems = new ArrayList<String>();
				System.out.println(" =========================================  " + packageId);
				for (int keyIndex = 0; keyIndex < totalRows; keyIndex++) {
					String key = checkItems.get(keyIndex);
					// XSSFRow xssfRow = xssfSheet.getRow(keyIndex);
					// String value = getValue(xssfRow.getCell(packageId));
					String value = getMergedRegionValue(xssfSheet, keyIndex, packageId);
					if (keyIndex < 8){
						packageItems.add(key + " ====  " + value);
						System.out.println(key + " ====  " + value);
					} else {
						if(value.startsWith("x") || value.startsWith("R")){
							packageItems.add(key + " ====  " + value);
							System.out.println(key + " ====  " + value);
						}
					}
					
				}
				allPackages.add(packageItems);
			}
		}
		is.close();
		return list;
	}

	private String getValue(XSSFCell xssfCell) {
		try {
			if (xssfCell == null) {
				return "";
			}
			switch (xssfCell.getCellType()) {
			case XSSFCell.CELL_TYPE_STRING:
				return xssfCell.getRichStringCellValue().toString();
			case XSSFCell.CELL_TYPE_BOOLEAN:
				return String.valueOf(xssfCell.getBooleanCellValue());
			case XSSFCell.CELL_TYPE_NUMERIC:
				return String.valueOf(xssfCell.getNumericCellValue());
			// case XSSFCell.CELL_TYPE_ERROR:
			// return ErrorEval.getText(xssfCell.getErrorCellValue());
			case XSSFCell.CELL_TYPE_FORMULA:
				return xssfCell.getCellFormula();
			case XSSFCell.CELL_TYPE_BLANK:
				return "";
			default:
				return String.valueOf(xssfCell.getStringCellValue());
			}
		} catch (Exception ex) {
			// System.out.println(ex.getMessage());
			return "";
		}
	}

	private String getMergedRegionValue(XSSFSheet sheet, int row, int column) {
		String value = "";
		boolean isMerged = false;
		int sheetMergeCount = sheet.getNumMergedRegions();
		for (int i = 0; i < sheetMergeCount; i++) {
			CellRangeAddress range = sheet.getMergedRegion(i);
			int firstColumn = range.getFirstColumn();
			int lastColumn = range.getLastColumn();
			int firstRow = range.getFirstRow();
			int lastRow = range.getLastRow();
			if (row >= firstRow && row <= lastRow) {
				if (column >= firstColumn && column <= lastColumn) {
					isMerged = true;
					XSSFRow fRow = sheet.getRow(firstRow);
					XSSFCell fCell = fRow.getCell(firstColumn);
					value = getValue(fCell);
					/*
					 * for (int i = 0; i < sheetMergeCount; i++) {
					 * CellRangeAddress ca = sheet.getMergedRegion(i); int
					 * firstColumn = ca.getFirstColumn(); int lastColumn =
					 * ca.getLastColumn(); int firstRow = ca.getFirstRow(); int
					 * lastRow = ca.getLastRow();
					 * 
					 * if (row >= firstRow && row <= lastRow) {
					 * 
					 * if (column >= firstColumn && column <= lastColumn) {
					 * XSSFRow fRow = sheet.getRow(firstRow); XSSFCell fCell =
					 * fRow.getCell(firstColumn); return getValue(fCell); } } }
					 */

					break;
				}
			}
		}
		if (!isMerged) {
			XSSFRow fRow = sheet.getRow(row);
			XSSFCell fCell = fRow.getCell(column);
			value = getValue(fCell);
		}
		return value;
	}

	public void modifyExcel(String realPath, String sheetname, int xLocaion, int yLocation, String value) {
		POIFSFileSystem fs = null;
		HSSFWorkbook wb = null;
		try {
			File file = new File(realPath);
			if (file.exists()) {
				fs = new POIFSFileSystem(new FileInputStream(realPath));
				wb = new HSSFWorkbook(fs);
				HSSFSheet s = wb.getSheetAt(0);
				// 函数处理时横纵坐标从索引0开始
				HSSFRow row = s.getRow(xLocaion - 1);
				HSSFCell cell = null;
				if (row != null) {
					cell = row.getCell(yLocation - 1);
					if (cell == null) {
						cell = row.createCell(yLocation - 1);
					}
				} else {
					row = s.createRow(xLocaion - 1);
					cell = row.createCell(yLocation - 1);
				}

				cell.setCellValue(value);

			} else {
				wb = new HSSFWorkbook();
				HSSFSheet s = wb.createSheet();
				wb.setSheetName(0, sheetname);
				HSSFRow row = s.createRow(xLocaion - 1);
				HSSFCell cell = row.createCell(yLocation - 1);
				cell.setCellValue(value);
			}
			FileOutputStream fos = new FileOutputStream(realPath);
			wb.write(fos);
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public boolean isGroup(String str) {
		Pattern pattern = Pattern.compile("^[A-Z\\s]+$");
		Matcher matcher = pattern.matcher(str);
		return matcher.matches();
	}
	// private List<String> readXlsxRow(String path) throws IOException {
	// System.out.println("XLSX Processing..." + path);
	// InputStream is = new FileInputStream(path);
	// XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);
	// List<String> list = new ArrayList<String>();
	// int totalSheets = xssfWorkbook.getNumberOfSheets();
	// int numSheet = 0;
	// System.out.println("totalSheets = " + totalSheets);
	// // for (int numSheet = 0; numSheet < totalSheets; numSheet++) {
	// XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(numSheet);
	// // if (xssfSheet == null) {
	// // continue;
	// // }
	// if (xssfSheet != null) {
	// // Read the Row
	// int totalRows = xssfSheet.getLastRowNum();
	// System.out.println("totalRows = " + totalRows);
	// for (int rowNum = 1; rowNum <= totalRows; rowNum++) {
	// System.out.println("Current Row = " + rowNum);
	// XSSFRow xssfRow = xssfSheet.getRow(rowNum);
	// if (xssfRow != null) {
	// try {
	// // XSSFCell no = xssfRow.getCell(0);
	// // XSSFCell name = xssfRow.getCell(1);
	// // XSSFCell age = xssfRow.getCell(2);
	// // XSSFCell score = xssfRow.getCell(3);
	// // student.setNo(getValue(no));
	// // student.setName(getValue(name));
	// // student.setAge(getValue(age));
	// // student.setScore(Float.valueOf(getValue(score)));
	// System.out.println("Cell 0: " + getValue(xssfRow.getCell(0)));
	// System.out.println("Cell 1: " + getValue(xssfRow.getCell(1)));
	// System.out.println("Cell 2: " + getValue(xssfRow.getCell(2)));
	// System.out.println("Cell 3: " + getValue(xssfRow.getCell(3)));
	// } catch (Exception ex) {
	// System.out.println("");
	// }
	// }
	// }
	// }
	// is.close();
	// // }
	// return list;
	// }

	// private List<String> readXls(String path) throws IOException {
	// System.out.println("Processing..." + path);
	// InputStream is = new FileInputStream(path);
	// XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);
	// List<String> list = new ArrayList<String>();
	// // Read the Sheet
	// for (int numSheet = 0; numSheet < xssfWorkbook.getNumberOfSheets();
	// numSheet++) {
	// XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(numSheet);
	// if (xssfSheet == null) {
	// continue;
	// }
	// // Read the Row
	// for (int rowNum = 1; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
	// XSSFRow xssfRow = xssfSheet.getRow(rowNum);
	// if (xssfRow != null) {
	// XSSFCell no = xssfRow.getCell(0);
	// XSSFCell name = xssfRow.getCell(1);
	// XSSFCell age = xssfRow.getCell(2);
	// XSSFCell score = xssfRow.getCell(3);
	// // student.setNo(getValue(no));
	// // student.setName(getValue(name));
	// // student.setAge(getValue(age));
	// // student.setScore(Float.valueOf(getValue(score)));
	// list.add(getValue(name));
	// }
	// }
	// }
	// return list;
	// }

	// private String getValue(HSSFCell hssfCell) {
	// if (hssfCell.getCellType() == hssfCell.CELL_TYPE_BOOLEAN) {
	// return String.valueOf(hssfCell.getBooleanCellValue());
	// } else if (hssfCell.getCellType() == hssfCell.CELL_TYPE_NUMERIC) {
	// return String.valueOf(hssfCell.getNumericCellValue());
	// } else {
	// return String.valueOf(hssfCell.getStringCellValue());
	// }
	// }
}
