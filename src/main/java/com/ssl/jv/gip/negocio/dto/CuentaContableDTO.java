package com.ssl.jv.gip.negocio.dto;

import java.io.Serializable;


/**
 * The Class CuentaContableDTO.
 */
public class CuentaContableDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6241117194932543359L;

	/** The int id documento. */
	private Long intIdDocumento;
	
	/** The lng id. */
	private Long lngId;

	/** The str descripcion. */
	private String strDescripcion;

	/** The dbl total. */
	private Double dblTotal;
	
	/** The dbl total iva10. */
	private Double dblTotalIva10;
	
	/** The dbl total iva16. */
	private Double dblTotalIva16;
	
	/** The dbl total descuentos. */
	private Double dblTotalDescuentos;
	
	/** The str consecutivo doc. */
	private String strConsecutivoDoc;
	
	/** The str fecha generacion. */
	private String strFechaGeneracion;
	
	/** The dbl total factura. */
	private Double dblTotalFactura;
	
	/** The str codigo sap. */
	private String strCodigoSAP;
	
	/** The str nom ubicacion. */
	private String strNomUbicacion;
	
	/** The str indicador iva. */
	private String strIndicadorIVA;
	
	/** The lng objeto co. */
	private Long lngObjetoCO;
	
	/** The dbl base iva. */
	private Double dblBaseIva;

	/** The str nom cliente. */
	private String strNomCliente;
	
	/** The bln solicitud cafe. */
	private Boolean blnSolicitudCafe;

	/**
	 * Gets the int id documento.
	 *
	 * @return the int id documento
	 */
	public Long getIntIdDocumento() {
		return intIdDocumento;
	}

	/**
	 * Sets the int id documento.
	 *
	 * @param intIdDocumento the new int id documento
	 */
	public void setIntIdDocumento(Long intIdDocumento) {
		this.intIdDocumento = intIdDocumento;
	}

	/**
	 * Gets the lng id.
	 *
	 * @return the lng id
	 */
	public Long getLngId() {
		return lngId;
	}

	/**
	 * Sets the lng id.
	 *
	 * @param lngId the new lng id
	 */
	public void setLngId(Long lngId) {
		this.lngId = lngId;
	}

	/**
	 * Gets the str descripcion.
	 *
	 * @return the str descripcion
	 */
	public String getStrDescripcion() {
		return strDescripcion;
	}

	/**
	 * Sets the str descripcion.
	 *
	 * @param strDescripcion the new str descripcion
	 */
	public void setStrDescripcion(String strDescripcion) {
		this.strDescripcion = strDescripcion;
	}

	/**
	 * Gets the dbl total.
	 *
	 * @return the dbl total
	 */
	public Double getDblTotal() {
		return dblTotal;
	}

	/**
	 * Sets the dbl total.
	 *
	 * @param dblTotal the new dbl total
	 */
	public void setDblTotal(Double dblTotal) {
		this.dblTotal = dblTotal;
	}

	/**
	 * Gets the dbl total iva10.
	 *
	 * @return the dbl total iva10
	 */
	public Double getDblTotalIva10() {
		return dblTotalIva10;
	}

	/**
	 * Sets the dbl total iva10.
	 *
	 * @param dblTotalIva10 the new dbl total iva10
	 */
	public void setDblTotalIva10(Double dblTotalIva10) {
		this.dblTotalIva10 = dblTotalIva10;
	}

	/**
	 * Gets the dbl total iva16.
	 *
	 * @return the dbl total iva16
	 */
	public Double getDblTotalIva16() {
		return dblTotalIva16;
	}

	/**
	 * Sets the dbl total iva16.
	 *
	 * @param dblTotalIva16 the new dbl total iva16
	 */
	public void setDblTotalIva16(Double dblTotalIva16) {
		this.dblTotalIva16 = dblTotalIva16;
	}

	/**
	 * Gets the dbl total descuentos.
	 *
	 * @return the dbl total descuentos
	 */
	public Double getDblTotalDescuentos() {
		return dblTotalDescuentos;
	}

	/**
	 * Sets the dbl total descuentos.
	 *
	 * @param dblTotalDescuentos the new dbl total descuentos
	 */
	public void setDblTotalDescuentos(Double dblTotalDescuentos) {
		this.dblTotalDescuentos = dblTotalDescuentos;
	}

	/**
	 * Gets the str consecutivo doc.
	 *
	 * @return the str consecutivo doc
	 */
	public String getStrConsecutivoDoc() {
		return strConsecutivoDoc;
	}

	/**
	 * Sets the str consecutivo doc.
	 *
	 * @param strConsecutivoDoc the new str consecutivo doc
	 */
	public void setStrConsecutivoDoc(String strConsecutivoDoc) {
		this.strConsecutivoDoc = strConsecutivoDoc;
	}

	/**
	 * Gets the str fecha generacion.
	 *
	 * @return the str fecha generacion
	 */
	public String getStrFechaGeneracion() {
		return strFechaGeneracion;
	}

	/**
	 * Sets the str fecha generacion.
	 *
	 * @param strFechaGeneracion the new str fecha generacion
	 */
	public void setStrFechaGeneracion(String strFechaGeneracion) {
		this.strFechaGeneracion = strFechaGeneracion;
	}

	/**
	 * Gets the dbl total factura.
	 *
	 * @return the dbl total factura
	 */
	public Double getDblTotalFactura() {
		return dblTotalFactura;
	}

	/**
	 * Sets the dbl total factura.
	 *
	 * @param dblTotalFactura the new dbl total factura
	 */
	public void setDblTotalFactura(Double dblTotalFactura) {
		this.dblTotalFactura = dblTotalFactura;
	}

	/**
	 * Gets the str codigo sap.
	 *
	 * @return the str codigo sap
	 */
	public String getStrCodigoSAP() {
		return strCodigoSAP;
	}

	/**
	 * Sets the str codigo sap.
	 *
	 * @param strCodigoSAP the new str codigo sap
	 */
	public void setStrCodigoSAP(String strCodigoSAP) {
		this.strCodigoSAP = strCodigoSAP;
	}

	/**
	 * Gets the str nom ubicacion.
	 *
	 * @return the str nom ubicacion
	 */
	public String getStrNomUbicacion() {
		return strNomUbicacion;
	}

	/**
	 * Sets the str nom ubicacion.
	 *
	 * @param strNomUbicacion the new str nom ubicacion
	 */
	public void setStrNomUbicacion(String strNomUbicacion) {
		this.strNomUbicacion = strNomUbicacion;
	}

	/**
	 * Gets the str indicador iva.
	 *
	 * @return the str indicador iva
	 */
	public String getStrIndicadorIVA() {
		return strIndicadorIVA;
	}

	/**
	 * Sets the str indicador iva.
	 *
	 * @param strIndicadorIVA the new str indicador iva
	 */
	public void setStrIndicadorIVA(String strIndicadorIVA) {
		this.strIndicadorIVA = strIndicadorIVA;
	}

	/**
	 * Gets the lng objeto co.
	 *
	 * @return the lng objeto co
	 */
	public Long getLngObjetoCO() {
		return lngObjetoCO;
	}

	/**
	 * Sets the lng objeto co.
	 *
	 * @param lngObjetoCO the new lng objeto co
	 */
	public void setLngObjetoCO(Long lngObjetoCO) {
		this.lngObjetoCO = lngObjetoCO;
	}

	/**
	 * Gets the dbl base iva.
	 *
	 * @return the dbl base iva
	 */
	public Double getDblBaseIva() {
		return dblBaseIva;
	}

	/**
	 * Sets the dbl base iva.
	 *
	 * @param dblBaseIva the new dbl base iva
	 */
	public void setDblBaseIva(Double dblBaseIva) {
		this.dblBaseIva = dblBaseIva;
	}

	/**
	 * Gets the str nom cliente.
	 *
	 * @return the str nom cliente
	 */
	public String getStrNomCliente() {
		return strNomCliente;
	}

	/**
	 * Sets the str nom cliente.
	 *
	 * @param strNomCliente the new str nom cliente
	 */
	public void setStrNomCliente(String strNomCliente) {
		this.strNomCliente = strNomCliente;
	}

	/**
	 * Gets the bln solicitud cafe.
	 *
	 * @return the bln solicitud cafe
	 */
	public Boolean getBlnSolicitudCafe() {
		return blnSolicitudCafe;
	}

	/**
	 * Sets the bln solicitud cafe.
	 *
	 * @param blnSolicitudCafe the new bln solicitud cafe
	 */
	public void setBlnSolicitudCafe(Boolean blnSolicitudCafe) {
		this.blnSolicitudCafe = blnSolicitudCafe;
	}
	
	
	
	
}
