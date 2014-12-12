package com.ssl.jv.gip.jpa.pojo;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;


/**
 * The persistent class for the consignaciones database table.
 * 
 */
@Entity
@Table(name="consignaciones")
@NamedQuery(name="Consignacion.findAll", query="SELECT c FROM Consignacion c")
public class Consignacion implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ConsignacionPK id;

	private String administradorturno;

	private BigDecimal consignacion;

	@Column(name="fecha_ingreso")
	private Timestamp fechaIngreso;

	private String numeroguia;

	//bi-directional many-to-one association to Ubicacion
	@ManyToOne(optional=false)
	@JoinColumn(name="id_ubicacion", referencedColumnName="id", insertable=false, updatable=false) 
	private Ubicacion ubicacione;

	public Consignacion() {
	}

	public ConsignacionPK getId() {
		return this.id;
	}

	public void setId(ConsignacionPK id) {
		this.id = id;
	}

	public String getAdministradorturno() {
		return this.administradorturno;
	}

	public void setAdministradorturno(String administradorturno) {
		this.administradorturno = administradorturno;
	}

	public BigDecimal getConsignacion() {
		return this.consignacion;
	}

	public void setConsignacion(BigDecimal consignacion) {
		this.consignacion = consignacion;
	}

	public Timestamp getFechaIngreso() {
		return this.fechaIngreso;
	}

	public void setFechaIngreso(Timestamp fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
	}

	public String getNumeroguia() {
		return this.numeroguia;
	}

	public void setNumeroguia(String numeroguia) {
		this.numeroguia = numeroguia;
	}

	public Ubicacion getUbicacione() {
		return this.ubicacione;
	}

	public void setUbicacione(Ubicacion ubicacione) {
		this.ubicacione = ubicacione;
	}

}