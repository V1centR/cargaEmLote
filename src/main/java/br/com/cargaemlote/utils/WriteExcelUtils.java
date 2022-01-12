package br.com.cargaemlote.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

//Class for work excel files
public class WriteExcelUtils {

	CellType cellType;

	public void readExcelFile(String file) throws IOException {

		FileInputStream fst = new FileInputStream(file);

		XSSFWorkbook wb = new XSSFWorkbook(fst);
		// 0 is line of xls file
		XSSFSheet sheet = wb.getSheetAt(0);

		Iterator<Row> rt = sheet.iterator();
		while (rt.hasNext()) {
			Row row = rt.next();
			// For each columns
			Iterator<Cell> ct = row.cellIterator();

			while (ct.hasNext()) {
				Cell cell = ct.next();

				switch (cell.getCellType()) {
				case NUMERIC:
					System.out.print(cell.getNumericCellValue());
					break;
				case STRING:
					System.out.print(cell.getStringCellValue());
					break;
				default:
					break;
				}

				System.out.println("");
			} // close while columns
			
			fst.close();
		} // close while

	}

	public void generateExcelFile() {

		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet sheet = wb.createSheet("lote1");

		Map<String, Object[]> data = new TreeMap<String, Object[]>();

		data.put("1", new Object[] { "NOME", "CPF/CNPJ", "DATA", "STATUS" });
		data.put("2", new Object[] { "Miguel Guimarães", "888.999.777-99", "11/01/2022", "OK" });
		data.put("3", new Object[] { "Arthur Carvalho", "111.222.333-44", "11/01/2022", "NOK" });
		data.put("4", new Object[] { "Bernardo Cardoso", "444.555.000-11", "11/01/2022", "NOK" });

		Set<String> keyset = data.keySet();

		int rownum = 0;
		for (String key : keyset) {
			Row row = sheet.createRow(rownum++);
			Object[] objArr = data.get(key);
			int cellnum = 0;
			for (Object obj : objArr) {
				Cell cell = row.createCell(cellnum++);
				if (obj instanceof String)
					cell.setCellValue((String) obj);
				else if (obj instanceof Integer)
					cell.setCellValue((Integer) obj);
			}
		} // close for

		try {
			// Write the workbook in file system
			FileOutputStream out = new FileOutputStream(new File("S3/lote_20220111_03h06.xlsx"));
			wb.write(out);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
