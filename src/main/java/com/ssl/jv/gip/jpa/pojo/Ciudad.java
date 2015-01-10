package com.ssl.jv.gip.jpa.pojo;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the ciudades database table.
 * 
 */
@Entity
@Table(name="ciudades")
@NamedQueries({
@NamedQuery(name="Ciudad.findAll", query="SELECT c FROM Ciudad c"),
@NamedQuery(name="Ciudad.findByPais", query="SELECT c FROM Ciudad c where c.idPais = :idPais")
})
public class Ciudad implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;

	@Column(name="id_departamento")
	private Long idDepartamento;

	@Column(name="id_pais")
	private String idPais;

	private String nombre;

	//bi-directional many-to-one association to PuntoVenta
	@OneToMany(mappedBy="ciudade")
	private List<PuntoVenta> puntoVentas;

	//bi-directional many-to-one association to TerminosTransporte
	@OneToMany(mappedBy="ciudade")
	private List<TerminosTransporte> terminosTransportes;

	public Ciudad() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdDepartamento() {
		return this.idDepartamento;
	}

	public void setIdDepartamento(Long idDepartamento) {
		this.idDepartamento = idDepartamento;
	}

	public String getIdPais() {
		return this.idPais;
	}

	public void setIdPais(String idPais) {
		this.idPais = idPais;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<PuntoVenta> getPuntoVentas() {
		return this.puntoVentas;
	}

	public void setPuntoVentas(List<PuntoVenta> puntoVentas) {
		this.puntoVentas = puntoVentas;
	}

	public PuntoVenta addPuntoVenta(PuntoVenta puntoVenta) {
		getPuntoVentas().add(puntoVenta);
		puntoVenta.setCiudade(this);

		return puntoVenta;
	}

	public PuntoVenta removePuntoVenta(PuntoVenta puntoVenta) {
		getPuntoVentas().remove(puntoVenta);
		puntoVenta.setCiudade(null);

		return puntoVenta;
	}

	public List<TerminosTransporte> getTerminosTransportes() {
		return this.terminosTransportes;
	}

	public void setTerminosTransportes(List<TerminosTransporte> terminosTransportes) {
		this.terminosTransportes = terminosTransportes;
	}

	public TerminosTransporte addTerminosTransporte(TerminosTransporte terminosTransporte) {
		getTerminosTransportes().add(terminosTransporte);
		terminosTransporte.setCiudade(this);

		return terminosTransporte;
	}

	public TerminosTransporte removeTerminosTransporte(TerminosTransporte terminosTransporte) {
		getTerminosTransportes().remove(terminosTransporte);
		terminosTransporte.setCiudade(null);

		return terminosTransporte;
	}

}