package com.ssl.jv.gip.negocio.dto;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Transient;

@Entity
public class CostoLogisticoDTO {

	@EmbeddedId
	private CostoLogisticoDTOPK id;
	@Transient
	private boolean seleccionado = true;

	public CostoLogisticoDTOPK getId() {
		return id;
	}

	public void setId(CostoLogisticoDTOPK id) {
		this.id = id;
	}

	public boolean isSeleccionado() {
		return seleccionado;
	}

	public void setSeleccionado(boolean seleccionado) {
		this.seleccionado = seleccionado;
	}

}
