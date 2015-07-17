package com.ssl.jv.gip.negocio.dto;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;



@Entity
public class DocumentoRequerimientoExportacionDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -829696387002776183L;
	
	
	
	 /**
	   * The id documento.
	   */
	@Id
	  private Long idDocumento;

	  /**
	   * The consecutivo documento.
	   */
	  private String consecutivoDocumento;

	  /**
	   * The fecha generacion.
	   */
	  private Date fechaGeneracion;

	  private boolean seleccionado;

	  private String documentoCliente;
	  
	  
	  private String clienteNombre;
	  
	  private String estadoNombre;
	  
	  private Long idCliente;
	  
	  private Long idTipoPrecio;
	  
	  private Long idMetodoPago;
	  

	   private Long idEstado;

	/**
	 * @return the idDocumento
	 */
	public Long getIdDocumento() {
		return idDocumento;
	}

	/**
	 * @param idDocumento the idDocumento to set
	 */
	public void setIdDocumento(Long idDocumento) {
		this.idDocumento = idDocumento;
	}

	/**
	 * @return the consecutivoDocumento
	 */
	public String getConsecutivoDocumento() {
		return consecutivoDocumento;
	}

	/**
	 * @param consecutivoDocumento the consecutivoDocumento to set
	 */
	public void setConsecutivoDocumento(String consecutivoDocumento) {
		this.consecutivoDocumento = consecutivoDocumento;
	}

	/**
	 * @return the fechaGeneracion
	 */
	public Date getFechaGeneracion() {
		return fechaGeneracion;
	}

	/**
	 * @param fechaGeneracion the fechaGeneracion to set
	 */
	public void setFechaGeneracion(Date fechaGeneracion) {
		this.fechaGeneracion = fechaGeneracion;
	}

		
	
	
	
	 public boolean isSeleccionado() {
		    return seleccionado;
		  }

		  public void setSeleccionado(boolean seleccionado) {
		    this.seleccionado = seleccionado;
		  }
	

	/**
	 * @return the documentoCliente
	 */
	public String getDocumentoCliente() {
		return documentoCliente;
	}

	/**
	 * @param documentoCliente the documentoCliente to set
	 */
	public void setDocumentoCliente(String documentoCliente) {
		this.documentoCliente = documentoCliente;
	}

	/**
	 * @return the clienteNombre
	 */
	public String getClienteNombre() {
		return clienteNombre;
	}

	/**
	 * @param clienteNombre the clienteNombre to set
	 */
	public void setClienteNombre(String clienteNombre) {
		this.clienteNombre = clienteNombre;
	}

	/**
	 * @return the estadoNombre
	 */
	public String getEstadoNombre() {
		return estadoNombre;
	}

	/**
	 * @param estadoNombre the estadoNombre to set
	 */
	public void setEstadoNombre(String estadoNombre) {
		this.estadoNombre = estadoNombre;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * @return the idCliente
	 */
	public Long getIdCliente() {
		return idCliente;
	}

	/**
	 * @param idCliente the idCliente to set
	 */
	public void setIdCliente(Long idCliente) {
		this.idCliente = idCliente;
	}

	/**
	 * @return the idTipoPrecio
	 */
	public Long getIdTipoPrecio() {
		return idTipoPrecio;
	}

	/**
	 * @param idTipoPrecio the idTipoPrecio to set
	 */
	public void setIdTipoPrecio(Long idTipoPrecio) {
		this.idTipoPrecio = idTipoPrecio;
	}

	/**
	 * @return the idMetodoPago
	 */
	public Long getIdMetodoPago() {
		return idMetodoPago;
	}

	/**
	 * @param idMetodoPago the idMetodoPago to set
	 */
	public void setIdMetodoPago(Long idMetodoPago) {
		this.idMetodoPago = idMetodoPago;
	}

	/**
	 * @return the idEstado
	 */
	public Long getIdEstado() {
		return idEstado;
	}

	/**
	 * @param idEstado the idEstado to set
	 */
	public void setIdEstado(Long idEstado) {
		this.idEstado = idEstado;
	}

}
