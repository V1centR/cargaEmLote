package br.com.cargaemlote.entity;

import java.io.Serializable;
import javax.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * The persistent class for the cargaloteslotes database table.
 * 
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="cargaloteslotes")
@NamedQuery(name="Cargaloteslote.findAll", query="SELECT c FROM Cargaloteslote c")
public class Cargaloteslote implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	private String datelote;

	private String esteira;

	private String fileexec;

	private String solicitante;

	private String status;

	//bi-directional many-to-one association to Fileprocess
	@ManyToOne
	@JoinColumn(name="file")
	private Fileprocess fileprocess;

}