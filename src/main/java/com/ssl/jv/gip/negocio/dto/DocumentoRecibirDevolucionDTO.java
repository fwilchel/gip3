package com.ssl.jv.gip.negocio.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

/**
 * The Class DocumentoRecibirDevolucionDTO.
 */
public class DocumentoRecibirDevolucionDTO implements Serializable{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 6826470281110432159L;

	/** The id documento. */
	private Long idDocumento;
	
	/** The consecutivo documento. */
	private String consecutivoDocumento;
	
	/** The fecha esperada entrega. */
	private Timestamp fechaEsperadaEntrega;
	
	/** The fecha esperada entrega. */
	private Date fechaEsperadaEntregaDate;
	
	/** The id ubicacion origen. */
	private Long idUbicacionOrigen;
	
	/** The nombre ubicacion origen. */
	private String nombreUbicacionOrigen;
	
	/** The id ubicacion destino. */
	private Long idUbicacionDestino;
	
	/** The nombre ubicacion destino. */
	private String nombreUbicacionDestino;
	
	/** The id tipo documento. */
	private Long idTipoDocumento;
	
	/** The abreviatura tipo documento. */
	private String abreviaturaTipoDocumento;
	
	/** The nombre tipo documento. */
	private String nombreTipoDocumento;
	
	/** The fecha generacion. */
	private Timestamp fechaGeneracion;

	/** The fecha entrega. */
	private Timestamp fechaEntrega;
	
	/** The id proveedor. */
	private Long idProveedor;
	
	/** The nombre proveedor. */
	private String nombreProveedor;
	
	/** The id estado. */
	private Long idEstado;
	
	/** The documento cliente. */
	private String documentoCliente;
	
	/** The id cliente. */
	private Long idCliente;
	
	/** The clientes id. */
	private Long clientesId;
	
	/** The clientes nombre. */
	private String clientesNit;
	
	/** The clientes nombre. */
	private String clientesNombre;
	
	/** The clientes direccion. */
	private String clientesDireccion;
	
	/** The clientes telefono. */
	private String clientesTelefono;
	
	/** The clientes contacto. */
	private String clientesContacto;
	
	/** The id termino incoterm. */
	private Long idTerminoIncoterm;
	
	/** The descripcion termino incoterm. */
	private String descripcionTerminoIncoterm;
	
	/** The valor total documento. */
	private BigDecimal valorTotalDocumento;
	
	/** The costo entrega. */
	private BigDecimal costoEntrega;
	
	/** The costo flete. */
	private BigDecimal costoFlete;
	
	/** The costo seguro. */
	private BigDecimal costoSeguro;
	
	/** The otros gastos. */
	private BigDecimal otrosGastos;

	/** The cantidad contenedores20. */
	private BigDecimal cantidadContenedores20;
	
	/** The cantidad contenedores40. */
	private BigDecimal cantidadContenedores40;
	
	/** The ciudad nombre. */
	private String ciudadNombre;
	
	/** The lugar incoterm. */
	private String lugarIncoterm;
	
	/** The estado nombre. */
	private String estadoNombre;
	
	/** The solicitud cafe. */
	private Boolean solicitudCafe;
	
	/** The observacion documento. */
	private String observacionDocumento;
	
	/** The observaciones marcacion2. */
	private String observacionesMarcacion2;
	
	/** The total peso neto. */
	private BigDecimal totalPesoNeto;
	
	/** The total peso bruto. */
	private BigDecimal totalPesoBruto;

	/** The total tendidos. */
	private BigDecimal totalTendidos;
	
	/** The total pallets. */
	private BigDecimal totalPallets;
	
	/** The id tipo documento. */
	private Integer cantidadDiasVigencia;
	
	/** The descripcion ingles metodo pago. */
	private String descripcionInglesMetodoPago;
	
	/** The descripcion metodo pago. */
	private String descripcionMetodoPago;
	
	/** The sitio entrega. */
	private String sitioEntrega;
	
	/**
	 * Gets the id documento.
	 *
	 * @return the id documento
	 */
	public Long getIdDocumento() {
		return idDocumento;
	}

	/**
	 * Sets the id documento.
	 *
	 * @param idDocumento the new id documento
	 */
	public void setIdDocumento(Long idDocumento) {
		this.idDocumento = idDocumento;
	}

	/**
	 * Gets the consecutivo documento.
	 *
	 * @return the consecutivo documento
	 */
	public String getConsecutivoDocumento() {
		return consecutivoDocumento;
	}

	/**
	 * Sets the consecutivo documento.
	 *
	 * @param consecutivoDocumento the new consecutivo documento
	 */
	public void setConsecutivoDocumento(String consecutivoDocumento) {
		this.consecutivoDocumento = consecutivoDocumento;
	}

	/**
	 * Gets the fecha esperada entrega.
	 *
	 * @return the fecha esperada entrega
	 */
	public Timestamp getFechaEsperadaEntrega() {
		return fechaEsperadaEntrega;
	}

	/**
	 * Sets the fecha esperada entrega.
	 *
	 * @param fechaEsperadaEntrega the new fecha esperada entrega
	 */
	public void setFechaEsperadaEntrega(Timestamp fechaEsperadaEntrega) {
		this.fechaEsperadaEntrega = fechaEsperadaEntrega;
	}

	/**
	 * Gets the id ubicacion origen.
	 *
	 * @return the id ubicacion origen
	 */
	public Long getIdUbicacionOrigen() {
		return idUbicacionOrigen;
	}

	/**
	 * Sets the id ubicacion origen.
	 *
	 * @param idUbicacionOrigen the new id ubicacion origen
	 */
	public void setIdUbicacionOrigen(Long idUbicacionOrigen) {
		this.idUbicacionOrigen = idUbicacionOrigen;
	}

	/**
	 * Gets the id ubicacion destino.
	 *
	 * @return the id ubicacion destino
	 */
	public Long getIdUbicacionDestino() {
		return idUbicacionDestino;
	}

	/**
	 * Sets the id ubicacion destino.
	 *
	 * @param idUbicacionDestino the new id ubicacion destino
	 */
	public void setIdUbicacionDestino(Long idUbicacionDestino) {
		this.idUbicacionDestino = idUbicacionDestino;
	}

	/**
	 * Gets the id tipo documento.
	 *
	 * @return the id tipo documento
	 */
	public Long getIdTipoDocumento() {
		return idTipoDocumento;
	}

	/**
	 * Sets the id tipo documento.
	 *
	 * @param idTipoDocumento the new id tipo documento
	 */
	public void setIdTipoDocumento(Long idTipoDocumento) {
		this.idTipoDocumento = idTipoDocumento;
	}

	/**
	 * Gets the fecha generacion.
	 *
	 * @return the fecha generacion
	 */
	public Timestamp getFechaGeneracion() {
		return fechaGeneracion;
	}

	/**
	 * Sets the fecha generacion.
	 *
	 * @param fechaGeneracion the new fecha generacion
	 */
	public void setFechaGeneracion(Timestamp fechaGeneracion) {
		this.fechaGeneracion = fechaGeneracion;
	}

	/**
	 * Gets the fecha entrega.
	 *
	 * @return the fecha entrega
	 */
	public Timestamp getFechaEntrega() {
		return fechaEntrega;
	}

	/**
	 * Sets the fecha entrega.
	 *
	 * @param fechaEntrega the new fecha entrega
	 */
	public void setFechaEntrega(Timestamp fechaEntrega) {
		this.fechaEntrega = fechaEntrega;
	}

	/**
	 * Gets the id proveedor.
	 *
	 * @return the id proveedor
	 */
	public Long getIdProveedor() {
		return idProveedor;
	}

	/**
	 * Sets the id proveedor.
	 *
	 * @param idProveedor the new id proveedor
	 */
	public void setIdProveedor(Long idProveedor) {
		this.idProveedor = idProveedor;
	}

	/**
	 * Gets the id estado.
	 *
	 * @return the id estado
	 */
	public Long getIdEstado() {
		return idEstado;
	}

	/**
	 * Sets the id estado.
	 *
	 * @param idEstado the new id estado
	 */
	public void setIdEstado(Long idEstado) {
		this.idEstado = idEstado;
	}

	/**
	 * Gets the documento cliente.
	 *
	 * @return the documento cliente
	 */
	public String getDocumentoCliente() {
		return documentoCliente;
	}

	/**
	 * Sets the documento cliente.
	 *
	 * @param documentoCliente the new documento cliente
	 */
	public void setDocumentoCliente(String documentoCliente) {
		this.documentoCliente = documentoCliente;
	}

	/**
	 * Gets the id cliente.
	 *
	 * @return the id cliente
	 */
	public Long getIdCliente() {
		return idCliente;
	}

	/**
	 * Sets the id cliente.
	 *
	 * @param idCliente the new id cliente
	 */
	public void setIdCliente(Long idCliente) {
		this.idCliente = idCliente;
	}

	/**
	 * Gets the clientes id.
	 *
	 * @return the clientes id
	 */
	public Long getClientesId() {
		return clientesId;
	}

	/**
	 * Sets the clientes id.
	 *
	 * @param clientesId the new clientes id
	 */
	public void setClientesId(Long clientesId) {
		this.clientesId = clientesId;
	}

	/**
	 * Gets the clientes nombre.
	 *
	 * @return the clientes nombre
	 */
	public String getClientesNombre() {
		return clientesNombre;
	}

	/**
	 * Sets the clientes nombre.
	 *
	 * @param clientesNombre the new clientes nombre
	 */
	public void setClientesNombre(String clientesNombre) {
		this.clientesNombre = clientesNombre;
	}

	/**
	 * Gets the clientes direccion.
	 *
	 * @return the clientes direccion
	 */
	public String getClientesDireccion() {
		return clientesDireccion;
	}

	/**
	 * Sets the clientes direccion.
	 *
	 * @param clientesDireccion the new clientes direccion
	 */
	public void setClientesDireccion(String clientesDireccion) {
		this.clientesDireccion = clientesDireccion;
	}

	/**
	 * Gets the clientes telefono.
	 *
	 * @return the clientes telefono
	 */
	public String getClientesTelefono() {
		return clientesTelefono;
	}

	/**
	 * Sets the clientes telefono.
	 *
	 * @param clientesTelefono the new clientes telefono
	 */
	public void setClientesTelefono(String clientesTelefono) {
		this.clientesTelefono = clientesTelefono;
	}

	/**
	 * Gets the clientes contacto.
	 *
	 * @return the clientes contacto
	 */
	public String getClientesContacto() {
		return clientesContacto;
	}

	/**
	 * Sets the clientes contacto.
	 *
	 * @param clientesContacto the new clientes contacto
	 */
	public void setClientesContacto(String clientesContacto) {
		this.clientesContacto = clientesContacto;
	}

	/**
	 * Gets the id termino incoterm.
	 *
	 * @return the id termino incoterm
	 */
	public Long getIdTerminoIncoterm() {
		return idTerminoIncoterm;
	}

	/**
	 * Sets the id termino incoterm.
	 *
	 * @param idTerminoIncoterm the new id termino incoterm
	 */
	public void setIdTerminoIncoterm(Long idTerminoIncoterm) {
		this.idTerminoIncoterm = idTerminoIncoterm;
	}

	/**
	 * Gets the descripcion termino incoterm.
	 *
	 * @return the descripcion termino incoterm
	 */
	public String getDescripcionTerminoIncoterm() {
		return descripcionTerminoIncoterm;
	}

	/**
	 * Sets the descripcion termino incoterm.
	 *
	 * @param descripcionTerminoIncoterm the new descripcion termino incoterm
	 */
	public void setDescripcionTerminoIncoterm(String descripcionTerminoIncoterm) {
		this.descripcionTerminoIncoterm = descripcionTerminoIncoterm;
	}

	/**
	 * Gets the valor total documento.
	 *
	 * @return the valor total documento
	 */
	public BigDecimal getValorTotalDocumento() {
		return valorTotalDocumento;
	}

	/**
	 * Sets the valor total documento.
	 *
	 * @param valorTotalDocumento the new valor total documento
	 */
	public void setValorTotalDocumento(BigDecimal valorTotalDocumento) {
		this.valorTotalDocumento = valorTotalDocumento;
	}

	/**
	 * Gets the costo entrega.
	 *
	 * @return the costo entrega
	 */
	public BigDecimal getCostoEntrega() {
		return costoEntrega;
	}

	/**
	 * Sets the costo entrega.
	 *
	 * @param costoEntrega the new costo entrega
	 */
	public void setCostoEntrega(BigDecimal costoEntrega) {
		this.costoEntrega = costoEntrega;
	}

	/**
	 * Gets the costo flete.
	 *
	 * @return the costo flete
	 */
	public BigDecimal getCostoFlete() {
		return costoFlete;
	}

	/**
	 * Sets the costo flete.
	 *
	 * @param costoFlete the new costo flete
	 */
	public void setCostoFlete(BigDecimal costoFlete) {
		this.costoFlete = costoFlete;
	}

	/**
	 * Gets the costo seguro.
	 *
	 * @return the costo seguro
	 */
	public BigDecimal getCostoSeguro() {
		return costoSeguro;
	}

	/**
	 * Sets the costo seguro.
	 *
	 * @param costoSeguro the new costo seguro
	 */
	public void setCostoSeguro(BigDecimal costoSeguro) {
		this.costoSeguro = costoSeguro;
	}

	/**
	 * Gets the otros gastos.
	 *
	 * @return the otros gastos
	 */
	public BigDecimal getOtrosGastos() {
		return otrosGastos;
	}

	/**
	 * Sets the otros gastos.
	 *
	 * @param otrosGastos the new otros gastos
	 */
	public void setOtrosGastos(BigDecimal otrosGastos) {
		this.otrosGastos = otrosGastos;
	}

	/**
	 * Gets the cantidad contenedores20.
	 *
	 * @return the cantidad contenedores20
	 */
	public BigDecimal getCantidadContenedores20() {
		return cantidadContenedores20;
	}

	/**
	 * Sets the cantidad contenedores20.
	 *
	 * @param cantidadContenedores20 the new cantidad contenedores20
	 */
	public void setCantidadContenedores20(BigDecimal cantidadContenedores20) {
		this.cantidadContenedores20 = cantidadContenedores20;
	}

	/**
	 * Gets the cantidad contenedores40.
	 *
	 * @return the cantidad contenedores40
	 */
	public BigDecimal getCantidadContenedores40() {
		return cantidadContenedores40;
	}

	/**
	 * Sets the cantidad contenedores40.
	 *
	 * @param cantidadContenedores40 the new cantidad contenedores40
	 */
	public void setCantidadContenedores40(BigDecimal cantidadContenedores40) {
		this.cantidadContenedores40 = cantidadContenedores40;
	}

	/**
	 * Gets the ciudad nombre.
	 *
	 * @return the ciudad nombre
	 */
	public String getCiudadNombre() {
		return ciudadNombre;
	}

	/**
	 * Sets the ciudad nombre.
	 *
	 * @param ciudadNombre the new ciudad nombre
	 */
	public void setCiudadNombre(String ciudadNombre) {
		this.ciudadNombre = ciudadNombre;
	}

	/**
	 * Gets the lugar incoterm.
	 *
	 * @return the lugar incoterm
	 */
	public String getLugarIncoterm() {
		return lugarIncoterm;
	}

	/**
	 * Sets the lugar incoterm.
	 *
	 * @param lugarIncoterm the new lugar incoterm
	 */
	public void setLugarIncoterm(String lugarIncoterm) {
		this.lugarIncoterm = lugarIncoterm;
	}

	/**
	 * Gets the estado nombre.
	 *
	 * @return the estado nombre
	 */
	public String getEstadoNombre() {
		return estadoNombre;
	}

	/**
	 * Sets the estado nombre.
	 *
	 * @param estadoNombre the new estado nombre
	 */
	public void setEstadoNombre(String estadoNombre) {
		this.estadoNombre = estadoNombre;
	}

	/**
	 * Gets the solicitud cafe.
	 *
	 * @return the solicitud cafe
	 */
	public Boolean getSolicitudCafe() {
		return solicitudCafe;
	}

	/**
	 * Sets the solicitud cafe.
	 *
	 * @param solicitudCafe the new solicitud cafe
	 */
	public void setSolicitudCafe(Boolean solicitudCafe) {
		this.solicitudCafe = solicitudCafe;
	}

	/**
	 * Gets the observacion documento.
	 *
	 * @return the observacion documento
	 */
	public String getObservacionDocumento() {
		return observacionDocumento;
	}

	/**
	 * Sets the observacion documento.
	 *
	 * @param observacionDocumento the new observacion documento
	 */
	public void setObservacionDocumento(String observacionDocumento) {
		this.observacionDocumento = observacionDocumento;
	}

	/**
	 * Gets the observaciones marcacion2.
	 *
	 * @return the observaciones marcacion2
	 */
	public String getObservacionesMarcacion2() {
		return observacionesMarcacion2;
	}

	/**
	 * Sets the observaciones marcacion2.
	 *
	 * @param observacionesMarcacion2 the new observaciones marcacion2
	 */
	public void setObservacionesMarcacion2(String observacionesMarcacion2) {
		this.observacionesMarcacion2 = observacionesMarcacion2;
	}

	/**
	 * Gets the fecha esperada entrega date.
	 *
	 * @return the fecha esperada entrega date
	 */
	public Date getFechaEsperadaEntregaDate() {
		return fechaEsperadaEntregaDate;
	}

	/**
	 * Sets the fecha esperada entrega date.
	 *
	 * @param fechaEsperadaEntregaDate the new fecha esperada entrega date
	 */
	public void setFechaEsperadaEntregaDate(Date fechaEsperadaEntregaDate) {
		this.fechaEsperadaEntregaDate = fechaEsperadaEntregaDate;
	}

	/**
	 * Gets the total peso neto.
	 *
	 * @return the total peso neto
	 */
	public BigDecimal getTotalPesoNeto() {
		return totalPesoNeto;
	}

	/**
	 * Sets the total peso neto.
	 *
	 * @param totalPesoNeto the new total peso neto
	 */
	public void setTotalPesoNeto(BigDecimal totalPesoNeto) {
		this.totalPesoNeto = totalPesoNeto;
	}

	/**
	 * Gets the total peso bruto.
	 *
	 * @return the total peso bruto
	 */
	public BigDecimal getTotalPesoBruto() {
		return totalPesoBruto;
	}

	/**
	 * Sets the total peso bruto.
	 *
	 * @param totalPesoBruto the new total peso bruto
	 */
	public void setTotalPesoBruto(BigDecimal totalPesoBruto) {
		this.totalPesoBruto = totalPesoBruto;
	}

	/**
	 * Gets the total tendidos.
	 *
	 * @return the total tendidos
	 */
	public BigDecimal getTotalTendidos() {
		return totalTendidos;
	}

	/**
	 * Sets the total tendidos.
	 *
	 * @param totalTendidos the new total tendidos
	 */
	public void setTotalTendidos(BigDecimal totalTendidos) {
		this.totalTendidos = totalTendidos;
	}

	/**
	 * Gets the total pallets.
	 *
	 * @return the total pallets
	 */
	public BigDecimal getTotalPallets() {
		return totalPallets;
	}

	/**
	 * Sets the total pallets.
	 *
	 * @param totalPallets the new total pallets
	 */
	public void setTotalPallets(BigDecimal totalPallets) {
		this.totalPallets = totalPallets;
	}

	/**
	 * Gets the clientes nit.
	 *
	 * @return the clientes nit
	 */
	public String getClientesNit() {
		return clientesNit;
	}

	/**
	 * Sets the clientes nit.
	 *
	 * @param clientesNit the new clientes nit
	 */
	public void setClientesNit(String clientesNit) {
		this.clientesNit = clientesNit;
	}

	/**
	 * Gets the cantidad dias vigencia.
	 *
	 * @return the cantidad dias vigencia
	 */
	public Integer getCantidadDiasVigencia() {
		return cantidadDiasVigencia;
	}

	/**
	 * Sets the cantidad dias vigencia.
	 *
	 * @param cantidadDiasVigencia the new cantidad dias vigencia
	 */
	public void setCantidadDiasVigencia(Integer cantidadDiasVigencia) {
		this.cantidadDiasVigencia = cantidadDiasVigencia;
	}

	/**
	 * Gets the descripcion ingles metodo pago.
	 *
	 * @return the descripcion ingles metodo pago
	 */
	public String getDescripcionInglesMetodoPago() {
		return descripcionInglesMetodoPago;
	}

	/**
	 * Sets the descripcion ingles metodo pago.
	 *
	 * @param descripcionInglesMetodoPago the new descripcion ingles metodo pago
	 */
	public void setDescripcionInglesMetodoPago(String descripcionInglesMetodoPago) {
		this.descripcionInglesMetodoPago = descripcionInglesMetodoPago;
	}

	/**
	 * Gets the descripcion metodo pago.
	 *
	 * @return the descripcion metodo pago
	 */
	public String getDescripcionMetodoPago() {
		return descripcionMetodoPago;
	}

	/**
	 * Sets the descripcion metodo pago.
	 *
	 * @param descripcionMetodoPago the new descripcion metodo pago
	 */
	public void setDescripcionMetodoPago(String descripcionMetodoPago) {
		this.descripcionMetodoPago = descripcionMetodoPago;
	}

	/**
	 * Gets the abreviatura tipo documento.
	 *
	 * @return the abreviatura tipo documento
	 */
	public String getAbreviaturaTipoDocumento() {
		return abreviaturaTipoDocumento;
	}

	/**
	 * Sets the abreviatura tipo documento.
	 *
	 * @param abreviaturaTipoDocumento the new abreviatura tipo documento
	 */
	public void setAbreviaturaTipoDocumento(String abreviaturaTipoDocumento) {
		this.abreviaturaTipoDocumento = abreviaturaTipoDocumento;
	}

	/**
	 * Gets the nombre tipo documento.
	 *
	 * @return the nombre tipo documento
	 */
	public String getNombreTipoDocumento() {
		return nombreTipoDocumento;
	}

	/**
	 * Sets the nombre tipo documento.
	 *
	 * @param nombreTipoDocumento the new nombre tipo documento
	 */
	public void setNombreTipoDocumento(String nombreTipoDocumento) {
		this.nombreTipoDocumento = nombreTipoDocumento;
	}

	/**
	 * Gets the nombre ubicacion origen.
	 *
	 * @return the nombre ubicacion origen
	 */
	public String getNombreUbicacionOrigen() {
		return nombreUbicacionOrigen;
	}

	/**
	 * Sets the nombre ubicacion origen.
	 *
	 * @param nombreUbicacionOrigen the new nombre ubicacion origen
	 */
	public void setNombreUbicacionOrigen(String nombreUbicacionOrigen) {
		this.nombreUbicacionOrigen = nombreUbicacionOrigen;
	}

	/**
	 * Gets the nombre ubicacion destino.
	 *
	 * @return the nombre ubicacion destino
	 */
	public String getNombreUbicacionDestino() {
		return nombreUbicacionDestino;
	}

	/**
	 * Sets the nombre ubicacion destino.
	 *
	 * @param nombreUbicacionDestino the new nombre ubicacion destino
	 */
	public void setNombreUbicacionDestino(String nombreUbicacionDestino) {
		this.nombreUbicacionDestino = nombreUbicacionDestino;
	}

	/**
	 * Gets the nombre proveedor.
	 *
	 * @return the nombre proveedor
	 */
	public String getNombreProveedor() {
		return nombreProveedor;
	}

	/**
	 * Sets the nombre proveedor.
	 *
	 * @param nombreProveedor the new nombre proveedor
	 */
	public void setNombreProveedor(String nombreProveedor) {
		this.nombreProveedor = nombreProveedor;
	}

	/**
	 * Gets the sitio entrega.
	 *
	 * @return the sitio entrega
	 */
	public String getSitioEntrega() {
		return sitioEntrega;
	}

	/**
	 * Sets the sitio entrega.
	 *
	 * @param sitioEntrega the new sitio entrega
	 */
	public void setSitioEntrega(String sitioEntrega) {
		this.sitioEntrega = sitioEntrega;
	}

		
	

}
