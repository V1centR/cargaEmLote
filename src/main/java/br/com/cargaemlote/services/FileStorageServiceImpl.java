package br.com.cargaemlote.services;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import br.com.cargaemlote.utils.FileChecksUtil;

@Service
public class FileStorageServiceImpl implements StorageService {
	
//	@Value("${s3.file.storage}")
//	private String pathStorage;
	
	private final Path pathTmp = Paths.get("S3/tmp");
	private final Path pathFileOk = Paths.get("S3");
	
	
	FileChecksUtil fileCheck = new FileChecksUtil();

	  @Override
	  public void init() {
	    try {
	      Files.createDirectory(pathTmp);
	    } catch (IOException e) {
	      throw new RuntimeException("Could not initialize folder for upload!");
	    }
	  }

	  @Override
	  public String save(MultipartFile file) {
		  
		  //Get File extension, only excel files xls and xlsx
		  String fileExt = file.getOriginalFilename().split("\\.")[1];
		  long timestamp = new Timestamp(System.currentTimeMillis()).getTime();

		  
		  String fileName  = "tmp_cargalote_" + timestamp +"."+fileExt;
		  String fixfileName  = "cargalote_" + timestamp +"."+fileExt;
		  
		  
		  if(fileExt.equals("xlsx") || fileExt.equals("xls")) {
			  
			  try {

				  System.out.println("AQUI");
				  
				 // fileCheck.checkColumns(file);
				  
			      //Files.copy(file.getInputStream(), this.pathTmp.resolve(file.getOriginalFilename()));
				  Files.copy(file.getInputStream(), this.pathTmp.resolve(fileName));
				  
				  if(fileCheck.checkColumns(fileName,fixfileName).equals("OK")) {
					  return "OK";
				  } else {
					  return "NOK";
				  }
				  
			      
			      
			    } catch (Exception e) {
			    	
			      throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
			      
			    }
		  } else {
			  
			  return "Arquivo não permitido! Operação foi cancelada.";
		  }
	  }

	  @Override
	  public Resource load(String filename) {
	    try {
	      Path file = pathTmp.resolve(filename);
	      Resource resource = new UrlResource(file.toUri());

	      if (resource.exists() || resource.isReadable()) {
	        return resource;
	      } else {
	        throw new RuntimeException("Could not read the file!");
	      }
	    } catch (MalformedURLException e) {
	      throw new RuntimeException("Error: " + e.getMessage());
	    }
	  }

	  @Override
	  public void deleteAll() {
	    FileSystemUtils.deleteRecursively(pathTmp.toFile());
	  }

	  @Override
	  public Stream<Path> loadAll() {
	    try {
	      return Files.walk(this.pathTmp, 1).filter(path -> !path.equals(this.pathTmp)).map(this.pathTmp::relativize);
	    } catch (IOException e) {
	      throw new RuntimeException("Could not load the files!");
	    }
	  }
	

}
