package br.com.cargaemlote.services;

import java.nio.file.Path;
import java.util.stream.Stream;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface StorageService {
	
	
	public void init();
	
	public String save(MultipartFile file);
	
	public Resource load(String filename);
	
	public void deleteAll();
	
	public Stream<Path> loadAll();


}
