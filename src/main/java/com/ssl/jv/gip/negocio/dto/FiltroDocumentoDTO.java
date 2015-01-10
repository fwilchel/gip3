package com.ssl.jv.gip.negocio.dto;

import java.io.Serializable;

public class FiltroDocumentoDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5424938254687892066L;
	private Long idTipoDocumento;
	private Long idEstado;

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

}
