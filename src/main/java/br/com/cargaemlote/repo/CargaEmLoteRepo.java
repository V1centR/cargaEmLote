package br.com.cargaemlote.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import br.com.cargaemlote.entity.Cargaloteslote;

public interface CargaEmLoteRepo extends CrudRepository<Cargaloteslote, Integer>  {
	
	//@Query("select c from Cargaloteslote c")
    List<Cargaloteslote> findAll();

}
