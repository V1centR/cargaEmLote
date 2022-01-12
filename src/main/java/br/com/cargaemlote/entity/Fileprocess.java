package br.com.cargaemlote.entity;

import java.io.Serializable;
import javax.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


/**
 * The persistent class for the fileprocess database table.
 * 
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="fileprocess")
@NamedQuery(name="Fileprocess.findAll", query="SELECT f FROM Fileprocess f")
public class Fileprocess implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	private String filename;

	private String timestamp;

	private String urlfile;
	
	//private String fileprocess;

	//bi-directional many-to-one association to Cargaloteslote
//	@OneToMany(mappedBy="fileprocess")
//	private List<Cargaloteslote> cargaloteslotes;

}