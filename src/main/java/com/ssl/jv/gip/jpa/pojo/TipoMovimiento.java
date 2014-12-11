package com.ssl.jv.gip.jpa.pojo;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the tipo_movimiento database table.
 * 
 */
@Entity
@Table(name="tipo_movimiento")
@NamedQuery(name="TipoMovimiento.findAll", query="SELECT t FROM TipoMovimiento t")
public class TipoMovimiento implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;

	private String nombre;

	//bi-directional many-to-one association to MovimientosInventario
	@OneToMany(mappedBy="tipoMovimiento")
	private List<MovimientosInventario> movimientosInventarios;

	public TipoMovimiento() {
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

	public List<MovimientosInventario> getMovimientosInventarios() {
		return this.movimientosInventarios;
	}

	public void setMovimientosInventarios(List<MovimientosInventario> movimientosInventarios) {
		this.movimientosInventarios = movimientosInventarios;
	}

	public MovimientosInventario addMovimientosInventario(MovimientosInventario movimientosInventario) {
		getMovimientosInventarios().add(movimientosInventario);
		movimientosInventario.setTipoMovimiento(this);

		return movimientosInventario;
	}

	public MovimientosInventario removeMovimientosInventario(MovimientosInventario movimientosInventario) {
		getMovimientosInventarios().remove(movimientosInventario);
		movimientosInventario.setTipoMovimiento(null);

		return movimientosInventario;
	}

}