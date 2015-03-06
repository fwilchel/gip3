package com.ssl.jv.gip.jpa.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

/**
 * The persistent class for the documentos database table.
 *
 */
@Entity
@Table(name = "documentos")
@NamedQueries({
  @NamedQuery(name = Documento.FIND_ALL, query = "SELECT d FROM Documento d"),
  @NamedQuery(name = Documento.FIND_BY_OBSERVACION_DOCUMENTO, query = "SELECT d FROM Documento d WHERE d.observacionDocumento = :observacionDocumento"),
  @NamedQuery(name = Documento.FIND_BY_TIPO_DOCUMENTO_AND_ESTADO, query = "SELECT d FROM Documento d WHERE d.estadosxdocumento.id.idTipoDocumento = :idTipoDocumento AND d.estadosxdocumento.id.idEstado = :idEstado ORDER BY d.id"),
  @NamedQuery(name = Documento.FIND_BY_TIPO_DOCUMENTO_AND_ESTADOS, query = "SELECT distinct d FROM Documento d JOIN FETCH d.cliente cli JOIN FETCH cli.ciudad ciu JOIN FETCH cli.metodoPago mpa JOIN FETCH cli.terminoIncoterms ti WHERE d.estadosxdocumento.id.idTipoDocumento = :idTipoDocumento AND d.estadosxdocumento.id.idEstado IN (:idEstado,:idEstado2) ORDER BY d.id desc"),
  @NamedQuery(name = Documento.FIND_BY_FECHAS_TIPO_DOCUMENTO, query = "SELECT d FROM Documento d WHERE d.estadosxdocumento.id.idTipoDocumento = :idTipoDocumento AND d.fechaGeneracion BETWEEN :fechaInicio AND :fechaFin ORDER BY d.fechaGeneracion"),
  @NamedQuery(name = Documento.LISTA_EMPAQUE_FIND_BY_ESTADO_AND_TIPO_DOCUMENTO_AND_CONSECUTIVO, query = "SELECT d FROM Documento d JOIN FETCH d.documentoXNegociacions dn LEFT JOIN FETCH d.cliente cli LEFT JOIN FETCH cli.ciudad ciu LEFT JOIN FETCH cli.metodoPago mp WHERE d.estadosxdocumento.id.idTipoDocumento = :idTipoDocumento AND d.estadosxdocumento.id.idEstado IN (:idEstados) AND UPPER(d.consecutivoDocumento) LIKE UPPER(:consecutivoDocumento) ORDER BY d.id DESC"),
  @NamedQuery(name = Documento.LISTADO_ANULAR_SOLICITUD_PEDIDO, query = "SELECT d FROM Documento d WHERE d.estadosxdocumento.id.idTipoDocumento = :tipoDocumento AND d.estadosxdocumento.id.idEstado NOT IN (:cerrado, :anulado) AND d.consecutivoDocumento NOT IN (SELECT d2.observacionDocumento FROM Documento d2 WHERE d2.estadosxdocumento.id.idTipoDocumento = :facturaProforma) AND UPPER(d.consecutivoDocumento) LIKE UPPER(:consecutivoDocumento) ORDER BY d.id DESC"),
  @NamedQuery(name = Documento.FIND_BY_ESTADO_AND_TIPO_DOCUMENTO_AND_CONSECUTIVO_CUSTOM, query = "SELECT DISTINCT d FROM Documento d JOIN FETCH d.documentoXNegociacions dn LEFT JOIN FETCH d.cliente c WHERE d.estadosxdocumento.id.idTipoDocumento = :idTipoDocumento AND ((d.estadosxdocumento.id.idEstado = :idEstado1 AND dn.solicitudCafe = false) OR (d.estadosxdocumento.id.idEstado = :idEstado2 AND dn.solicitudCafe = true)) AND UPPER(d.consecutivoDocumento) LIKE UPPER(:consecutivoDocumento) ORDER BY d.id DESC"),
  @NamedQuery(name = Documento.FIND_BY_CONSECUTIVO, query = "SELECT d FROM Documento d  WHERE UPPER(d.consecutivoDocumento) = UPPER(:consecutivoDocumento)"),
  @NamedQuery(name = Documento.FIND_BY_TIPO_DOCUMENTO_AND_ESTADO_AND_TIPO_CAFE, query = "SELECT d FROM Documento d JOIN FETCH d.documentoXNegociacions dn WHERE d.estadosxdocumento.id.idTipoDocumento = :idTipoDocumento AND d.estadosxdocumento.id.idEstado = :idEstado AND dn.solicitudCafe = true ORDER BY d.id DESC"),
  @NamedQuery(name = Documento.FIND_BY_TIPO_DOCUMENTO_AND_ESTADO_AND_SOLICITUD_CAFE_AND_CONSECUTIVO, query = "SELECT d FROM Documento d JOIN FETCH d.documentoXNegociacions dn WHERE d.estadosxdocumento.id.idTipoDocumento = :idTipoDocumento AND d.estadosxdocumento.id.idEstado = :idEstado AND dn.solicitudCafe = :solicitudCafe AND UPPER(d.consecutivoDocumento) LIKE UPPER(:consecutivoDocumento) ORDER BY d.id DESC"),
  @NamedQuery(name = Documento.FIND_BY_FECHAS_AND_TIPO_DOCUMENTO_AND_ESTADO_AND_SOLICITUD_CAFE, query = "SELECT d FROM Documento d JOIN FETCH d.documentoXNegociacions dn WHERE d.estadosxdocumento.id.idTipoDocumento = :idTipoDocumento AND d.estadosxdocumento.id.idEstado = :idEstado AND d.fechaGeneracion BETWEEN :fechaInicio AND :fechaFin AND dn.solicitudCafe = :solicitudCafe ORDER BY d.fechaGeneracion"),
  @NamedQuery(name = Documento.FIND_BY_ESTADO_AND_TIPO_AND_CONSECUTIVO, query = "SELECT d FROM Documento d JOIN d.ubicacionOrigen origen JOIN d.cliente cliente WHERE d.estadosxdocumento.id.idTipoDocumento = :idTipoDocumento AND d.estadosxdocumento.id.idEstado IN (:idEstado) AND UPPER(d.consecutivoDocumento) LIKE UPPER(:consecutivoDocumento) ORDER BY d.id DESC"),
  @NamedQuery(name = Documento.FIND_BY_TIPO_DOCUMENTO_AND_OBSERVACION_DOCUMENTO, query = "SELECT d FROM Documento d WHERE d.observacionDocumento = :observacionDocumento AND d.estadosxdocumento.id.idTipoDocumento = :idTipoDocumento")})
public class Documento implements Serializable {

  /**
   *
   */
  private static final long serialVersionUID = -5401061913463904605L;

  public static final String FIND_ALL = "Documento.findAll";
  public static final String FIND_BY_OBSERVACION_DOCUMENTO = "Documento.findByObservacionDocumento";
  public static final String FIND_BY_TIPO_DOCUMENTO_AND_ESTADO = "Documento.findByTipoDocumentoAndEstado";
  public static final String FIND_BY_TIPO_DOCUMENTO_AND_ESTADOS = "Documento.findByTipoDocumentoAndEstados";
  public static final String LISTA_EMPAQUE_FIND_BY_ESTADO_AND_TIPO_DOCUMENTO_AND_CONSECUTIVO = "Documento.findByEstadoTipoDocumentoConsecutivoDocumento";
  public static final String LISTADO_ANULAR_SOLICITUD_PEDIDO = "Documento.findByPendientePorAnular";
  public static final String ACTUALIZAR_ESTADO_DOCUMENTO = "UPDATE documentos SET id_estado = :id_estado WHERE id = :id";
  public static final String FIND_BY_FECHAS_TIPO_DOCUMENTO = "Documento.findByFechasAndTipoDocumento";
  public static final String FIND_BY_ESTADO_AND_TIPO_DOCUMENTO_AND_CONSECUTIVO_CUSTOM = "Documento.findByEstadoTipoDocumentoConsecutivoDocumentoCustom";
  public static final String FIND_BY_CONSECUTIVO = "Documento.findByConsecutivoDocumento";
  public static final String FIND_BY_TIPO_DOCUMENTO_AND_ESTADO_AND_TIPO_CAFE = "Documento.findByTipoDocumentoAndEstadoAndTipoCafe";
  public static final String FIND_BY_TIPO_DOCUMENTO_AND_ESTADO_AND_SOLICITUD_CAFE_AND_CONSECUTIVO = "Documento.findByTipoDocumentoAndEstadoAndSolicitudCafeAndConsecutivo";
  public static final String FIND_BY_FECHAS_AND_TIPO_DOCUMENTO_AND_ESTADO_AND_SOLICITUD_CAFE = "Documento.findByFechasAndTipoDocumentoAndEstado";
  public static final String FIND_BY_ESTADO_AND_TIPO_AND_CONSECUTIVO = "Documento.findByEstadoAndTipoAndConsecutivo";
  public static final String FIND_BY_TIPO_DOCUMENTO_AND_OBSERVACION_DOCUMENTO = "Documento.findByObservacionAndTipo";

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
  @Temporal(TemporalType.TIMESTAMP)
  private Date fechaEntrega;

  @Column(name = "fecha_esperada_entrega")
  @Temporal(TemporalType.TIMESTAMP)
  private Date fechaEsperadaEntrega;

  @Column(name = "fecha_eta")
  @Temporal(TemporalType.TIMESTAMP)
  private Date fechaEta;

  @Column(name = "fecha_generacion")
  @Temporal(TemporalType.TIMESTAMP)
  private Date fechaGeneracion;

  @ManyToOne
  @JoinColumn(name = "id_cliente")
  private Cliente cliente;

  @ManyToOne
  @JoinColumn(name = "id_punto_venta")
  private PuntoVenta puntoVenta;

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
  @LazyCollection(LazyCollectionOption.FALSE)
  private List<DocumentoXLotesoic> documentoXLotesoics;

  // bi-directional many-to-one association to DocumentoXNegociacion
  @OneToMany(mappedBy = "documento")
  @LazyCollection(LazyCollectionOption.FALSE)
  private List<DocumentoXNegociacion> documentoXNegociacions;

  // bi-directional many-to-one association to Estadosxdocumento
  @ManyToOne
  @JoinColumns({
    @JoinColumn(name = "id_estado", referencedColumnName = "id_estado"),
    @JoinColumn(name = "id_tipo_documento", referencedColumnName = "id_tipo_documento")})
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

  public Date getFechaEntrega() {
    return this.fechaEntrega;
  }

  public void setFechaEntrega(Date fechaEntrega) {
    this.fechaEntrega = fechaEntrega;
  }

  public Date getFechaEsperadaEntrega() {
    return this.fechaEsperadaEntrega;
  }

  public void setFechaEsperadaEntrega(Date fechaEsperadaEntrega) {
    this.fechaEsperadaEntrega = fechaEsperadaEntrega;
  }

  public Date getFechaEta() {
    return this.fechaEta;
  }

  public void setFechaEta(Date fechaEta) {
    this.fechaEta = fechaEta;
  }

  public Date getFechaGeneracion() {
    return this.fechaGeneracion;
  }

  public void setFechaGeneracion(Date fechaGeneracion) {
    this.fechaGeneracion = fechaGeneracion;
  }

  public Cliente getCliente() {
    return cliente;
  }

  public void setCliente(Cliente cliente) {
    this.cliente = cliente;
  }

  public PuntoVenta getPuntoVenta() {
    return this.puntoVenta;
  }

  public void setPuntoVenta(PuntoVenta puntoVenta) {
    this.puntoVenta = puntoVenta;
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

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    Documento other = (Documento) obj;
    if (id == null) {
      if (other.id != null) {
        return false;
      }
    } else if (!id.equals(other.id)) {
      return false;
    }
    return true;
  }

}
