package com.ssl.jv.gip.jpa.pojo;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the tipo_conteo database table.
 * 
 */
@Entity
@Table(name="tipo_conteo")
@NamedQuery(name="TipoConteo.findAll", query="SELECT t FROM TipoConteo t")
public class TipoConteo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;

	private String nombre;

	//bi-directional many-to-one association to Conteo
	@OneToMany(mappedBy="tipoConteo")
	private List<Conteo> conteos;

	//bi-directional many-to-one association to EstandarConteo
	@OneToMany(mappedBy="tipoConteo")
	private List<EstandarConteo> estandarConteos;

	//bi-directional many-to-one association to Pais
	@ManyToOne
	@JoinColumn(name="id_pais")
	private Pais pais;

	public TipoConteo() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<Conteo> getConteos() {
		return this.conteos;
	}

	public void setConteos(List<Conteo> conteos) {
		this.conteos = conteos;
	}

	public Conteo addConteo(Conteo conteo) {
		getConteos().add(conteo);
		conteo.setTipoConteo(this);

		return conteo;
	}

	public Conteo removeConteo(Conteo conteo) {
		getConteos().remove(conteo);
		conteo.setTipoConteo(null);

		return conteo;
	}

	public List<EstandarConteo> getEstandarConteos() {
		return this.estandarConteos;
	}

	public void setEstandarConteos(List<EstandarConteo> estandarConteos) {
		this.estandarConteos = estandarConteos;
	}

	public EstandarConteo addEstandarConteo(EstandarConteo estandarConteo) {
		getEstandarConteos().add(estandarConteo);
		estandarConteo.setTipoConteo(this);

		return estandarConteo;
	}

	public EstandarConteo removeEstandarConteo(EstandarConteo estandarConteo) {
		getEstandarConteos().remove(estandarConteo);
		estandarConteo.setTipoConteo(null);

		return estandarConteo;
	}

	public Pais getPais() {
		return this.pais;
	}

	public void setPais(Pais pais) {
		this.pais = pais;
	}

}