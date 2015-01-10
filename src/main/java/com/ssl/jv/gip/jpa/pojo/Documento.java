package com.ssl.jv.gip.jpa.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * The persistent class for the documentos database table.
 * 
 */
@Entity
@Table(name = "documentos")
@NamedQueries({
		@NamedQuery(name = "Documento.findAll", query = "SELECT d FROM Documento d"),
		@NamedQuery(name = Documento.FIND_BY_TIPO_DOCUMENTO_AND_ESTADO, query = "SELECT d FROM Documento d WHERE d.estadosxdocumento.id.idTipoDocumento = :idTipoDocumento AND d.estadosxdocumento.id.idEstado = :idEstado ORDER BY d.id") })
public class Documento implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5401061913463904605L;

	public static final String FIND_BY_TIPO_DOCUMENTO_AND_ESTADO = "Documento.findByTipoDocumentoAndEstado";

	@Id
	@SequenceGenerator(name = "documentoSeq", sequenceName = "documentos_id_seq", allocationSize = 1)
	@GeneratedValue(generator = "documentoSeq", strategy = GenerationType.SEQUENCE)
	private Long id;

	@Column(name = "consecutivo_documento")
	private String consecutivoDocumento;

	private BigDecimal descuento;

	@Column(name = "descuento_cliente")
	private BigDecimal descuentoCliente;

	@Column(name = "documento_cliente")
	private String documentoCliente;

	@Column(name = "estado_novedad")
	private Boolean estadoNovedad;

	@Column(name = "fecha_entrega")
	private Timestamp fechaEntrega;

	@Column(name = "fecha_esperada_entrega")
	private Timestamp fechaEsperadaEntrega;

	@Column(name = "fecha_eta")
	private Timestamp fechaEta;

	@Column(name = "fecha_generacion")
	private Timestamp fechaGeneracion;

	@ManyToOne
	@JoinColumn(name = "id_cliente")
	private Cliente cliente;

	@Column(name = "id_punto_venta")
	private Long idPuntoVenta;

	@Column(name = "numero_factura")
	private String numeroFactura;

	@Column(name = "observacion_documento")
	private String observacionDocumento;

	private String observacion2;

	private String observacion3;

	@Column(name = "sitio_entrega")
	private String sitioEntrega;

	private BigDecimal subtotal;

	@Column(name = "valor_iva10")
	private BigDecimal valorIva10;

	@Column(name = "valor_iva16")
	private BigDecimal valorIva16;

	@Column(name = "valor_iva5")
	private BigDecimal valorIva5;

	@Column(name = "valor_total")
	private BigDecimal valorTotal;

	// bi-directional many-to-one association to DocumentoXLotesoic
	@OneToMany(mappedBy = "documento")
	private List<DocumentoXLotesoic> documentoXLotesoics;

	// bi-directional many-to-one association to DocumentoXNegociacion
	@OneToMany(mappedBy = "documento")
	private List<DocumentoXNegociacion> documentoXNegociacions;

	// bi-directional many-to-one association to Estadosxdocumento
	@ManyToOne
	@JoinColumns({
			@JoinColumn(name = "id_estado", referencedColumnName = "id_estado"),
			@JoinColumn(name = "id_tipo_documento", referencedColumnName = "id_tipo_documento") })
	private Estadosxdocumento estadosxdocumento;

	// bi-directional many-to-one association to Proveedor
	@ManyToOne
	@JoinColumn(name = "id_proveedor")
	private Proveedor proveedore;

	// bi-directional many-to-one association to Ubicacion
	@ManyToOne
	@JoinColumn(name = "id_ubicacion_destino")
	private Ubicacion ubicacionDestino;

	// bi-directional many-to-one association to Ubicacion
	@ManyToOne
	@JoinColumn(name = "id_ubicacion_origen")
	private Ubicacion ubicacionOrigen;

	// bi-directional many-to-one association to MovimientosInventario
	@OneToMany(mappedBy = "documento")
	private List<MovimientosInventario> movimientosInventarios;

	// bi-directional many-to-many association to TerminosTransporte
	@ManyToMany(mappedBy = "documentos")
	private List<TerminosTransporte> terminosTransportes;

	public Documento() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getConsecutivoDocumento() {
		return this.consecutivoDocumento;
	}

	public void setConsecutivoDocumento(String consecutivoDocumento) {
		this.consecutivoDocumento = consecutivoDocumento;
	}

	public BigDecimal getDescuento() {
		return this.descuento;
	}

	public void setDescuento(BigDecimal descuento) {
		this.descuento = descuento;
	}

	public BigDecimal getDescuentoCliente() {
		return this.descuentoCliente;
	}

	public void setDescuentoCliente(BigDecimal descuentoCliente) {
		this.descuentoCliente = descuentoCliente;
	}

	public String getDocumentoCliente() {
		return this.documentoCliente;
	}

	public void setDocumentoCliente(String documentoCliente) {
		this.documentoCliente = documentoCliente;
	}

	public Boolean getEstadoNovedad() {
		return this.estadoNovedad;
	}

	public void setEstadoNovedad(Boolean estadoNovedad) {
		this.estadoNovedad = estadoNovedad;
	}

	public Timestamp getFechaEntrega() {
		return this.fechaEntrega;
	}

	public void setFechaEntrega(Timestamp fechaEntrega) {
		this.fechaEntrega = fechaEntrega;
	}

	public Timestamp getFechaEsperadaEntrega() {
		return this.fechaEsperadaEntrega;
	}

	public void setFechaEsperadaEntrega(Timestamp fechaEsperadaEntrega) {
		this.fechaEsperadaEntrega = fechaEsperadaEntrega;
	}

	public Timestamp getFechaEta() {
		return this.fechaEta;
	}

	public void setFechaEta(Timestamp fechaEta) {
		this.fechaEta = fechaEta;
	}

	public Timestamp getFechaGeneracion() {
		return this.fechaGeneracion;
	}

	public void setFechaGeneracion(Timestamp fechaGeneracion) {
		this.fechaGeneracion = fechaGeneracion;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Long getIdPuntoVenta() {
		return this.idPuntoVenta;
	}

	public void setIdPuntoVenta(Long idPuntoVenta) {
		this.idPuntoVenta = idPuntoVenta;
	}

	public String getNumeroFactura() {
		return this.numeroFactura;
	}

	public void setNumeroFactura(String numeroFactura) {
		this.numeroFactura = numeroFactura;
	}

	public String getObservacionDocumento() {
		return this.observacionDocumento;
	}

	public void setObservacionDocumento(String observacionDocumento) {
		this.observacionDocumento = observacionDocumento;
	}

	public String getObservacion2() {
		return this.observacion2;
	}

	public void setObservacion2(String observacion2) {
		this.observacion2 = observacion2;
	}

	public String getObservacion3() {
		return this.observacion3;
	}

	public void setObservacion3(String observacion3) {
		this.observacion3 = observacion3;
	}

	public String getSitioEntrega() {
		return this.sitioEntrega;
	}

	public void setSitioEntrega(String sitioEntrega) {
		this.sitioEntrega = sitioEntrega;
	}

	public BigDecimal getSubtotal() {
		return this.subtotal;
	}

	public void setSubtotal(BigDecimal subtotal) {
		this.subtotal = subtotal;
	}

	public BigDecimal getValorIva10() {
		return this.valorIva10;
	}

	public void setValorIva10(BigDecimal valorIva10) {
		this.valorIva10 = valorIva10;
	}

	public BigDecimal getValorIva16() {
		return this.valorIva16;
	}

	public void setValorIva16(BigDecimal valorIva16) {
		this.valorIva16 = valorIva16;
	}

	public BigDecimal getValorIva5() {
		return this.valorIva5;
	}

	public void setValorIva5(BigDecimal valorIva5) {
		this.valorIva5 = valorIva5;
	}

	public BigDecimal getValorTotal() {
		return this.valorTotal;
	}

	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}

	public List<DocumentoXLotesoic> getDocumentoXLotesoics() {
		return this.documentoXLotesoics;
	}

	public void setDocumentoXLotesoics(
			List<DocumentoXLotesoic> documentoXLotesoics) {
		this.documentoXLotesoics = documentoXLotesoics;
	}

	public DocumentoXLotesoic addDocumentoXLotesoic(
			DocumentoXLotesoic documentoXLotesoic) {
		getDocumentoXLotesoics().add(documentoXLotesoic);
		documentoXLotesoic.setDocumento(this);

		return documentoXLotesoic;
	}

	public DocumentoXLotesoic removeDocumentoXLotesoic(
			DocumentoXLotesoic documentoXLotesoic) {
		getDocumentoXLotesoics().remove(documentoXLotesoic);
		documentoXLotesoic.setDocumento(null);

		return documentoXLotesoic;
	}

	public List<DocumentoXNegociacion> getDocumentoXNegociacions() {
		return this.documentoXNegociacions;
	}

	public void setDocumentoXNegociacions(
			List<DocumentoXNegociacion> documentoXNegociacions) {
		this.documentoXNegociacions = documentoXNegociacions;
	}

	public DocumentoXNegociacion addDocumentoXNegociacion(
			DocumentoXNegociacion documentoXNegociacion) {
		getDocumentoXNegociacions().add(documentoXNegociacion);
		documentoXNegociacion.setDocumento(this);

		return documentoXNegociacion;
	}

	public DocumentoXNegociacion removeDocumentoXNegociacion(
			DocumentoXNegociacion documentoXNegociacion) {
		getDocumentoXNegociacions().remove(documentoXNegociacion);
		documentoXNegociacion.setDocumento(null);

		return documentoXNegociacion;
	}

	public Estadosxdocumento getEstadosxdocumento() {
		return this.estadosxdocumento;
	}

	public void setEstadosxdocumento(Estadosxdocumento estadosxdocumento) {
		this.estadosxdocumento = estadosxdocumento;
	}

	public Proveedor getProveedore() {
		return this.proveedore;
	}

	public void setProveedore(Proveedor proveedore) {
		this.proveedore = proveedore;
	}

	public Ubicacion getUbicacionDestino() {
		return this.ubicacionDestino;
	}

	public void setUbicacionDestino(Ubicacion ubicacionDestino) {
		this.ubicacionDestino = ubicacionDestino;
	}

	public Ubicacion getUbicacionOrigen() {
		return this.ubicacionOrigen;
	}

	public void setUbicacionOrigen(Ubicacion ubicacionOrigen) {
		this.ubicacionOrigen = ubicacionOrigen;
	}

	public List<MovimientosInventario> getMovimientosInventarios() {
		return this.movimientosInventarios;
	}

	public void setMovimientosInventarios(
			List<MovimientosInventario> movimientosInventarios) {
		this.movimientosInventarios = movimientosInventarios;
	}

	public MovimientosInventario addMovimientosInventario(
			MovimientosInventario movimientosInventario) {
		getMovimientosInventarios().add(movimientosInventario);
		movimientosInventario.setDocumento(this);

		return movimientosInventario;
	}

	public MovimientosInventario removeMovimientosInventario(
			MovimientosInventario movimientosInventario) {
		getMovimientosInventarios().remove(movimientosInventario);
		movimientosInventario.setDocumento(null);

		return movimientosInventario;
	}

	public List<TerminosTransporte> getTerminosTransportes() {
		return this.terminosTransportes;
	}

	public void setTerminosTransportes(
			List<TerminosTransporte> terminosTransportes) {
		this.terminosTransportes = terminosTransportes;
	}

}