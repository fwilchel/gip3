package com.ssl.jv.gip.negocio.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.sql.Timestamp;


/**
 * The Class ProductoPorClienteComExtDTO.
 */
public class ProductoPorClienteComExtDTO implements Serializable{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 6826470281110432159L;

	private Long intId;
	
	private Long intIdProducto;
	
	private Long intIdCliente;
	
	private BigDecimal dblPrecioUSD;
	
	private String strIdMoneda;
	
	private BigDecimal dblIVA;	
	
	private BigDecimal dblDescuentoProducto;
	
	private BigDecimal dblOtrosDescuentos;
	
	private Boolean blnActivo;
	
	private String strNomProducto;
	
	private String strNomCliente;
	
	private String strSku;
	
	//Producto x inventario
	
	private Long intIdProductoInventario;
	
	private String strSkuProductoInventario;
	
	private String strNombreProductoInventario;
	
	private Long intUnidadProductoInventario;
	
	//Producto x documento
	
	private BigDecimal dblCantidad1ProductoxDocumento;
	
	private BigDecimal dblValorTotalProductoxDocumento;
	
	//Producto x inventario CE
	
	private BigDecimal dblPesoBrutoProductoInventarioCE;
	
	private BigDecimal dblPesoNetoProductoInventarioCE;
	
	private BigDecimal dblCantidadXEmbalajeProductoInventarioCE;
	
	private BigDecimal dblPesoNetoEmbalajeProductoInventarioCE;
	
	private BigDecimal dblPesoBrutoEmbalajeProductoInventarioCE;
	
	private BigDecimal dblCantCajasXTendidoProductoInventarioCE;
	
	private BigDecimal dblTotalCajasXPalletProductoInventarioCE;
	
	private String posicionArancelariaProductoInventarioCE;
	
	private String descripcionProductoInventarioCE;
	
	private Boolean controlStockProductoInventarioCE;
	
	private String nombrePrdProveedorProductoInventarioCE;
	
	//Unidad
	
	private String nombreUnidad;

	private String nombreInglesUnidad;
	
//	private ProductoInventario objProductoInventario;
//	
//	private ProductoInventarioCE objProductoInventarioCE;
//
//	private ProductoPorDocumento objProductoxDocumento;
//	
//	private TipoLoteOIC objTipoLoteOIC;
//	
//	private DocumentoxLotesOic objDocumentoxLotesOic;
	
	private boolean blnIncluirBusqueda;
	
	private BigDecimal dblTotalPesoNeto ,dblTotalPesoBruto ,dblTotalCajasTendido  ,dblTotalCajasPallet, dblTotalCajas, dblTotalCantidad;
	
//	private Unidad objUnidad;
	
	private String strRegSanitario;
	
	private BigDecimal dblMuestraFito ,dblMuestraCalidad;
	
//	private DocumentoxNegociacion objDocumentoxNegociacion;

	public Long getIntId() {
		return intId;
	}

	public void setIntId(Long intId) {
		this.intId = intId;
	}

	public Long getIntIdProducto() {
		return intIdProducto;
	}

	public void setIntIdProducto(Long intIdProducto) {
		this.intIdProducto = intIdProducto;
	}

	public Long getIntIdCliente() {
		return intIdCliente;
	}

	public void setIntIdCliente(Long intIdCliente) {
		this.intIdCliente = intIdCliente;
	}

	public BigDecimal getDblPrecioUSD() {
		return dblPrecioUSD;
	}

	public void setDblPrecioUSD(BigDecimal dblPrecioUSD) {
		this.dblPrecioUSD = dblPrecioUSD;
	}

	public String getStrIdMoneda() {
		return strIdMoneda;
	}

	public void setStrIdMoneda(String strIdMoneda) {
		this.strIdMoneda = strIdMoneda;
	}

	public BigDecimal getDblIVA() {
		return dblIVA;
	}

	public void setDblIVA(BigDecimal dblIVA) {
		this.dblIVA = dblIVA;
	}

	public BigDecimal getDblDescuentoProducto() {
		return dblDescuentoProducto;
	}

	public void setDblDescuentoProducto(BigDecimal dblDescuentoProducto) {
		this.dblDescuentoProducto = dblDescuentoProducto;
	}

	public BigDecimal getDblOtrosDescuentos() {
		return dblOtrosDescuentos;
	}

	public void setDblOtrosDescuentos(BigDecimal dblOtrosDescuentos) {
		this.dblOtrosDescuentos = dblOtrosDescuentos;
	}

	public Boolean getBlnActivo() {
		return blnActivo;
	}

	public void setBlnActivo(Boolean blnActivo) {
		this.blnActivo = blnActivo;
	}

	public String getStrNomProducto() {
		return strNomProducto;
	}

	public void setStrNomProducto(String strNomProducto) {
		this.strNomProducto = strNomProducto;
	}

	public String getStrNomCliente() {
		return strNomCliente;
	}

	public void setStrNomCliente(String strNomCliente) {
		this.strNomCliente = strNomCliente;
	}

	public String getStrSku() {
		return strSku;
	}

	public void setStrSku(String strSku) {
		this.strSku = strSku;
	}

	public boolean isBlnIncluirBusqueda() {
		return blnIncluirBusqueda;
	}

	public void setBlnIncluirBusqueda(boolean blnIncluirBusqueda) {
		this.blnIncluirBusqueda = blnIncluirBusqueda;
	}

	public BigDecimal getDblTotalPesoNeto() {
		return dblTotalPesoNeto;
	}

	public void setDblTotalPesoNeto(BigDecimal dblTotalPesoNeto) {
		this.dblTotalPesoNeto = dblTotalPesoNeto;
	}

	public BigDecimal getDblTotalPesoBruto() {
		return dblTotalPesoBruto;
	}

	public void setDblTotalPesoBruto(BigDecimal dblTotalPesoBruto) {
		this.dblTotalPesoBruto = dblTotalPesoBruto;
	}

	public BigDecimal getDblTotalCajasTendido() {
		return dblTotalCajasTendido;
	}

	public void setDblTotalCajasTendido(BigDecimal dblTotalCajasTendido) {
		this.dblTotalCajasTendido = dblTotalCajasTendido;
	}

	public BigDecimal getDblTotalCajasPallet() {
		return dblTotalCajasPallet;
	}

	public void setDblTotalCajasPallet(BigDecimal dblTotalCajasPallet) {
		this.dblTotalCajasPallet = dblTotalCajasPallet;
	}

	public BigDecimal getDblTotalCajas() {
		return dblTotalCajas;
	}

	public void setDblTotalCajas(BigDecimal dblTotalCajas) {
		this.dblTotalCajas = dblTotalCajas;
	}

	public BigDecimal getDblTotalCantidad() {
		return dblTotalCantidad;
	}

	public void setDblTotalCantidad(BigDecimal dblTotalCantidad) {
		this.dblTotalCantidad = dblTotalCantidad;
	}

	public String getStrRegSanitario() {
		return strRegSanitario;
	}

	public void setStrRegSanitario(String strRegSanitario) {
		this.strRegSanitario = strRegSanitario;
	}

	public BigDecimal getDblMuestraFito() {
		return dblMuestraFito;
	}

	public void setDblMuestraFito(BigDecimal dblMuestraFito) {
		this.dblMuestraFito = dblMuestraFito;
	}

	public BigDecimal getDblMuestraCalidad() {
		return dblMuestraCalidad;
	}

	public void setDblMuestraCalidad(BigDecimal dblMuestraCalidad) {
		this.dblMuestraCalidad = dblMuestraCalidad;
	}

	public String getStrSkuProductoInventario() {
		return strSkuProductoInventario;
	}

	public void setStrSkuProductoInventario(String strSkuProductoInventario) {
		this.strSkuProductoInventario = strSkuProductoInventario;
	}

	public String getStrNombreProductoInventario() {
		return strNombreProductoInventario;
	}

	public void setStrNombreProductoInventario(String strNombreProductoInventario) {
		this.strNombreProductoInventario = strNombreProductoInventario;
	}

	public BigDecimal getDblValorTotalProductoxDocumento() {
		return dblValorTotalProductoxDocumento;
	}

	public void setDblValorTotalProductoxDocumento(
			BigDecimal dblValorTotalProductoxDocumento) {
		this.dblValorTotalProductoxDocumento = dblValorTotalProductoxDocumento;
	}

	public BigDecimal getDblCantidad1ProductoxDocumento() {
		return dblCantidad1ProductoxDocumento;
	}

	public void setDblCantidad1ProductoxDocumento(
			BigDecimal dblCantidad1ProductoxDocumento) {
		this.dblCantidad1ProductoxDocumento = dblCantidad1ProductoxDocumento;
	}

	public BigDecimal getDblCantidadXEmbalajeProductoInventarioCE() {
		return dblCantidadXEmbalajeProductoInventarioCE;
	}

	public void setDblCantidadXEmbalajeProductoInventarioCE(
			BigDecimal dblCantidadXEmbalajeProductoInventarioCE) {
		this.dblCantidadXEmbalajeProductoInventarioCE = dblCantidadXEmbalajeProductoInventarioCE;
	}

	public BigDecimal getDblPesoNetoEmbalajeProductoInventarioCE() {
		return dblPesoNetoEmbalajeProductoInventarioCE;
	}

	public void setDblPesoNetoEmbalajeProductoInventarioCE(
			BigDecimal dblPesoNetoEmbalajeProductoInventarioCE) {
		this.dblPesoNetoEmbalajeProductoInventarioCE = dblPesoNetoEmbalajeProductoInventarioCE;
	}

	public BigDecimal getDblPesoBrutoEmbalajeProductoInventarioCE() {
		return dblPesoBrutoEmbalajeProductoInventarioCE;
	}

	public void setDblPesoBrutoEmbalajeProductoInventarioCE(
			BigDecimal dblPesoBrutoEmbalajeProductoInventarioCE) {
		this.dblPesoBrutoEmbalajeProductoInventarioCE = dblPesoBrutoEmbalajeProductoInventarioCE;
	}

	public BigDecimal getDblCantCajasXTendidoProductoInventarioCE() {
		return dblCantCajasXTendidoProductoInventarioCE;
	}

	public void setDblCantCajasXTendidoProductoInventarioCE(
			BigDecimal dblCantCajasXTendidoProductoInventarioCE) {
		this.dblCantCajasXTendidoProductoInventarioCE = dblCantCajasXTendidoProductoInventarioCE;
	}

	public BigDecimal getDblTotalCajasXPalletProductoInventarioCE() {
		return dblTotalCajasXPalletProductoInventarioCE;
	}

	public void setDblTotalCajasXPalletProductoInventarioCE(
			BigDecimal dblTotalCajasXPalletProductoInventarioCE) {
		this.dblTotalCajasXPalletProductoInventarioCE = dblTotalCajasXPalletProductoInventarioCE;
	}

	public Long getIntIdProductoInventario() {
		return intIdProductoInventario;
	}

	public void setIntIdProductoInventario(Long intIdProductoInventario) {
		this.intIdProductoInventario = intIdProductoInventario;
	}

	public Long getIntUnidadProductoInventario() {
		return intUnidadProductoInventario;
	}

	public void setIntUnidadProductoInventario(Long intUnidadProductoInventario) {
		this.intUnidadProductoInventario = intUnidadProductoInventario;
	}

	public BigDecimal getDblPesoBrutoProductoInventarioCE() {
		return dblPesoBrutoProductoInventarioCE;
	}

	public void setDblPesoBrutoProductoInventarioCE(
			BigDecimal dblPesoBrutoProductoInventarioCE) {
		this.dblPesoBrutoProductoInventarioCE = dblPesoBrutoProductoInventarioCE;
	}

	public BigDecimal getDblPesoNetoProductoInventarioCE() {
		return dblPesoNetoProductoInventarioCE;
	}

	public void setDblPesoNetoProductoInventarioCE(
			BigDecimal dblPesoNetoProductoInventarioCE) {
		this.dblPesoNetoProductoInventarioCE = dblPesoNetoProductoInventarioCE;
	}

	public String getPosicionArancelariaProductoInventarioCE() {
		return posicionArancelariaProductoInventarioCE;
	}

	public void setPosicionArancelariaProductoInventarioCE(
			String posicionArancelariaProductoInventarioCE) {
		this.posicionArancelariaProductoInventarioCE = posicionArancelariaProductoInventarioCE;
	}

	public String getNombreUnidad() {
		return nombreUnidad;
	}

	public void setNombreUnidad(String nombreUnidad) {
		this.nombreUnidad = nombreUnidad;
	}

	public String getNombreInglesUnidad() {
		return nombreInglesUnidad;
	}

	public void setNombreInglesUnidad(String nombreInglesUnidad) {
		this.nombreInglesUnidad = nombreInglesUnidad;
	}

	public String getDescripcionProductoInventarioCE() {
		return descripcionProductoInventarioCE;
	}

	public void setDescripcionProductoInventarioCE(
			String descripcionProductoInventarioCE) {
		this.descripcionProductoInventarioCE = descripcionProductoInventarioCE;
	}

	public Boolean getControlStockProductoInventarioCE() {
		return controlStockProductoInventarioCE;
	}

	public void setControlStockProductoInventarioCE(
			Boolean controlStockProductoInventarioCE) {
		this.controlStockProductoInventarioCE = controlStockProductoInventarioCE;
	}

	public String getNombrePrdProveedorProductoInventarioCE() {
		return nombrePrdProveedorProductoInventarioCE;
	}

	public void setNombrePrdProveedorProductoInventarioCE(
			String nombrePrdProveedorProductoInventarioCE) {
		this.nombrePrdProveedorProductoInventarioCE = nombrePrdProveedorProductoInventarioCE;
	}

	



}
