package br.com.cargaemlote.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
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
import br.com.cargaemlote.services.StorageService;
import br.com.cargaemlote.utils.WriteExcelUtils;

@RestController
@RequestMapping(value = "/api/cargaemlote")
public class CargaEmLoteController {
	
	
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
	
	@PostMapping("/lotefile/upload")
	public String handleFileUpload(@RequestParam("file") MultipartFile file,
			RedirectAttributes redirectAttributes) {

		storageService.store(file);
		redirectAttributes.addFlashAttribute("message",
				"ok" + file.getOriginalFilename());

		return "redirect:/";
	}
	
	
	

}
