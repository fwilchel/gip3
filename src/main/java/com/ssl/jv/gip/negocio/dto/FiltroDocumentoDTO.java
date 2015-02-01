package com.ssl.jv.gip.negocio.dto;

import java.io.Serializable;
import java.util.Date;

public class FiltroDocumentoDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5424938254687892066L;
	private Long idTipoDocumento;
	private Long idEstado;
	private Long idEstado2;
	private Date fechaInicio;
	private Date fechaFin;

	public Long getIdTipoDocumento() {
		return idTipoDocumento;
	}

	public void setIdTipoDocumento(Long idTipoDocumento) {
		this.idTipoDocumento = idTipoDocumento;
	}

	public Long getIdEstado() {
		return idEstado;
	}

	public void setIdEstado(Long idEstado) {
		this.idEstado = idEstado;
	}

	public Long getIdEstado2() {
		return idEstado2;
	}

	public void setIdEstado2(Long idEstado2) {
		this.idEstado2 = idEstado2;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}
	
	
	
	

}
