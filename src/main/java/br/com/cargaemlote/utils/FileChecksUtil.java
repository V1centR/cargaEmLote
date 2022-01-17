package br.com.cargaemlote.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;

@PropertySource("src/main/resources/application.properties")
public class FileChecksUtil {
	
	@Value("${cargalote.file.columns}")
	private String columns;
	//private String columns = "nome;cpf;data";
	
	private final Path pathTmp = Paths.get("S3/tmp");
	private final Path path = Paths.get("/S3");
	
	
	public String fileChecks(String fileExt) {
		return "OK";
		
	}
	
	public String checkColumns(String file, String fixFilename) throws IOException {
		
		
		System.out.println("Exec OK");
		
		ArrayList<String> arr1 = new ArrayList<String>();
		arr1.add("nome");
		arr1.add("cpf");
		arr1.add("data");
		
		ArrayList<String> arr2 = new ArrayList<String>();
		
		try {
			
			FileInputStream fst = new FileInputStream(pathTmp+"/"+file);

			XSSFWorkbook wb = new XSSFWorkbook(fst);
			XSSFSheet sheet = wb.getSheetAt(0);
			
			
			//Get num columns
			int lastcell= sheet.getRow(0).getLastCellNum();
			
			System.out.println("lastcell:: " + lastcell);
			
			XSSFRow row;
			
			for(int i=0;i < lastcell;i++) {
				//To get column line always 0
				row = sheet.getRow(0);
				Cell cell = row.getCell(i);
				arr2.add(cell.getStringCellValue());				
			}			
			
			Object[] array1 = {arr1};  // arr1 contains only one element
		    Object[] array2 = {arr2};
			
			if(Arrays.equals(array1,array2)){
				
				Files.copy(fst, path.resolve(fixFilename));
				return "OK";
				
			} else {
				
				System.out.println("ARRAY EQUAL ERROR");
				return "NOK";
			}
			
			
		} catch (Exception e) {
			// TODO: handle exception
			return "NOK";
		}
	}
	
//	public String setNamefile() {
//		
//		String fileName = "arquivo_cargalote_";
//		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
//		
//		
//		return fileName+timestamp.getTime();
//	}
	

}
