package br.com.cargaemlote.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.cargaemlote.entity.Cargaloteslote;
import br.com.cargaemlote.repo.CargaEmLoteRepo;
import br.com.cargaemlote.response.ResponseMessage;
import br.com.cargaemlote.services.StorageService;
import br.com.cargaemlote.utils.WriteExcelUtils;

@RestController
@RequestMapping(value = "/api/cargaemlote")
public class CargaEmLoteController {
	
	@Value("${s3.file.storage}")
	private String pathUpload;
	
	@Autowired
	CargaEmLoteRepo repo;
	
	@Autowired
	StorageService storageService;
	
	WriteExcelUtils genExcel = new WriteExcelUtils();
	
	
	@CrossOrigin
	@GetMapping
	public @ResponseBody List<Cargaloteslote> getlotes() throws IOException {
		
		//genExcel.generateExcelFile();
		genExcel.readExcelFile("S3/lote_20220111_03h06.xlsx");
		return repo.findAll();
	}
	
	
	@PostMapping("/uploadfile")
	  public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) {
	    String message = "";
	    
	    System.out.println("PATH::: " + pathUpload);
	    
	    try {
	      storageService.save(file);

	      message = "Arquivo carregado com sucesso: " + file.getOriginalFilename();
	      return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
	    } catch (Exception e) {
	      message = "Não foi possível executar o upload do arquivo: " + file.getOriginalFilename() + "!";
	      return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
	    }
	  }
	
	

}
