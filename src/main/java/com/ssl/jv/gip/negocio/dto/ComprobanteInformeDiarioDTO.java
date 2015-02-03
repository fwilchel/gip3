package com.ssl.jv.gip.negocio.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Id;



/**
 * The Class ComprobanteInformeDiarioDTO.
 */
@Entity
public class ComprobanteInformeDiarioDTO implements Serializable {

	private static final long serialVersionUID = 6826470281110432159L;
	@Id
	private Long idCuentaContable;
	private String descripcionCuentaContable;
	private BigDecimal  valorTotal;
	
	
	public Long getIdCuentaContable() {
		return idCuentaContable;
	}
	public void setIdCuentaContable(Long idCuentaContable) {
		this.idCuentaContable = idCuentaContable;
	}
	public String getDescripcionCuentaContable() {
		return descripcionCuentaContable;
	}
	public void setDescripcionCuentaContable(String descripcionCuentaContable) {
		this.descripcionCuentaContable = descripcionCuentaContable;
	}
	public BigDecimal getValorTotal() {
		return valorTotal;
	}
	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}
	
	
	
	
	}
