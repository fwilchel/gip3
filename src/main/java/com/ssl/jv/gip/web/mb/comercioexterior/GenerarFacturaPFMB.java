package com.ssl.jv.gip.web.mb.comercioexterior;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import org.apache.log4j.Logger;

import com.ssl.jv.gip.jpa.pojo.Documento;
import com.ssl.jv.gip.jpa.pojo.DocumentoXNegociacion;
import com.ssl.jv.gip.jpa.pojo.DocumentoXNegociacionPK;
import com.ssl.jv.gip.jpa.pojo.Estadosxdocumento;
import com.ssl.jv.gip.jpa.pojo.EstadosxdocumentoPK;
import com.ssl.jv.gip.jpa.pojo.LogAuditoria;
import com.ssl.jv.gip.jpa.pojo.Moneda;
import com.ssl.jv.gip.jpa.pojo.ProductosXDocumento;
import com.ssl.jv.gip.jpa.pojo.ProductosXDocumentoPK;
import com.ssl.jv.gip.jpa.pojo.Ubicacion;
import com.ssl.jv.gip.jpa.pojo.Unidad;
import com.ssl.jv.gip.negocio.dto.ProductoGenerarFacturaPFDTO;
import com.ssl.jv.gip.negocio.ejb.ComercioExteriorEJBLocal;
import com.ssl.jv.gip.web.mb.AplicacionMB;
import com.ssl.jv.gip.web.mb.MenuMB;
import com.ssl.jv.gip.web.mb.UtilMB;
import com.ssl.jv.gip.web.mb.util.ConstantesDocumento;
import com.ssl.jv.gip.web.mb.util.ConstantesTipoDocumento;
import com.ssl.jv.gip.web.util.Utilidad;

/**
 * <p>
 * Title: GIP</p>
 *
 * <p>
 * Description: GIP</p>
 *
 * <p>
 * Copyright: Copyright (c) 2014</p>
 *
 * <p>
 * Company: Soft Studio Ltda.</p>
 *
 * @author Fredy Giovanny Wilches López
 * @email fredy.wilches@gmail.com
 * @phone 300 2146240
 * @version 1.0
 */
@ManagedBean(name = "generarFacturaPFMB")
@SessionScoped
public class GenerarFacturaPFMB extends UtilMB {

  /**
   *
   */
  private static final long serialVersionUID = -2780795923623719268L;

  private static final Logger LOGGER = Logger.getLogger(GenerarFacturaPFMB.class);
  private Timestamp currentTimeStamp;
  private String consecutivoDocumento;
  private Integer language = AplicacionMB.SPANISH;
  private List<Documento> listaDocumentos;
  private Documento documentoSeleccionado;
  private List<ProductoGenerarFacturaPFDTO> productos;
  private double totalCantidad = 0;
  private double totalValorTotal = 0;
  private double totalPesoNeto = 0;
  private double totalPesoBruto = 0;
  private double totalCantidadCajas = 0;
  private double totalCantidadTendidos = 0;
  private double totalCantidadPallets = 0;
  private double totalCostos = 0;
  private double totalNegociacion = 0;
  private Integer cantidadDiasVigencia;
  private boolean deshabilitado = false;

  @EJB
  private ComercioExteriorEJBLocal comercioEjb;

  @ManagedProperty(value = "#{menuMB}")
  private MenuMB menu;

  public GenerarFacturaPFMB() {

  }

  @PostConstruct
  public void init() {
    currentTimeStamp = new Timestamp(System.currentTimeMillis());

  }

  public Timestamp getCurrentTimeStamp() {
    return currentTimeStamp;
  }

  public void setCurrentTimeStamp(Timestamp currentTimeStamp) {
    this.currentTimeStamp = currentTimeStamp;
  }

  public String getConsecutivoDocumento() {
    return consecutivoDocumento;
  }

  public void setConsecutivoDocumento(String consecutivoDocumento) {
    this.consecutivoDocumento = consecutivoDocumento;
  }

  public String buscarDocumentos() {
    listaDocumentos = this.comercioEjb.consultarDocumentosSolicitudPedido(consecutivoDocumento);
    this.deshabilitado = false;
    return null;
  }

  public boolean isDeshabilitado() {
    return deshabilitado;
  }

  public void setDeshabilitado(boolean deshabilitado) {
    this.deshabilitado = deshabilitado;
  }

  public List<Documento> getListaDocumentos() {
    return listaDocumentos;
  }

  public void setListaDocumentos(List<Documento> listaDocumentos) {
    this.listaDocumentos = listaDocumentos;
  }

  public Documento getDocumentoSeleccionado() {
    return documentoSeleccionado;
  }

  public void setDocumentoSeleccionado(Documento documentoSeleccionado) {
    this.documentoSeleccionado = documentoSeleccionado;
  }

  public double getTotalCantidad() {
    return totalCantidad;
  }

  public void setTotalCantidad(double totalCantidad) {
    this.totalCantidad = totalCantidad;
  }

  public double getTotalValorTotal() {
    return totalValorTotal;
  }

  public void setTotalValorTotal(double totalValorTotal) {
    this.totalValorTotal = totalValorTotal;
  }

  public double getTotalPesoNeto() {
    return totalPesoNeto;
  }

  public void setTotalPesoNeto(double totalPesoNeto) {
    this.totalPesoNeto = totalPesoNeto;
  }

  public double getTotalPesoBruto() {
    return totalPesoBruto;
  }

  public void setTotalPesoBruto(double totalPesoBruto) {
    this.totalPesoBruto = totalPesoBruto;
  }

  public double getTotalCantidadCajas() {
    return totalCantidadCajas;
  }

  public void setTotalCantidadCajas(double totalCantidadCajas) {
    this.totalCantidadCajas = totalCantidadCajas;
  }

  public double getTotalCantidadTendidos() {
    return totalCantidadTendidos;
  }

  public void setTotalCantidadTendidos(double totalCantidadTendidos) {
    this.totalCantidadTendidos = totalCantidadTendidos;
  }

  public double getTotalCantidadPallets() {
    return totalCantidadPallets;
  }

  public void setTotalCantidadPallets(double totalCantidadPallets) {
    this.totalCantidadPallets = totalCantidadPallets;
  }

  public double getTotalCostos() {
    return totalCostos;
  }

  public void setTotalCostos(double totalCostos) {
    this.totalCostos = totalCostos;
  }

  public double getTotalNegociacion() {
    return totalNegociacion;
  }

  public void setTotalNegociacion(double totalNegociacion) {
    this.totalNegociacion = totalNegociacion;
  }

  public Integer getCantidadDiasVigencia() {
    return cantidadDiasVigencia;
  }

  public void setCantidadDiasVigencia(Integer cantidadDiasVigencia) {
    this.cantidadDiasVigencia = cantidadDiasVigencia;
  }

  public String consultarSolicitudPedido() {
    productos = comercioEjb.consultarProductoPorDocumentoGenerarFacturaProforma(this.documentoSeleccionado.getId(), this.documentoSeleccionado.getCliente().getId());
    this.totalCantidad = 0;
    this.totalValorTotal = 0;
    this.totalPesoNeto = 0;
    this.totalPesoBruto = 0;
    this.totalCantidadCajas = 0;
    this.totalCantidadTendidos = 0;
    this.totalCantidadPallets = 0;
    for (ProductoGenerarFacturaPFDTO p : productos) {
      this.totalCantidad += p.getCantidad().doubleValue();
      this.totalValorTotal += p.getValorTotal().doubleValue();
      this.totalPesoNeto += p.getTotalPesoNeto().doubleValue();
      this.totalPesoBruto += p.getTotalPesoBruto().doubleValue();
      this.totalCantidadCajas += p.getTotalCajas().doubleValue();
      this.totalCantidadTendidos += p.getTotalCajasTendido().doubleValue();
      this.totalCantidadPallets += p.getTotalCajasPallet().doubleValue();
    }

    totalCostos = 0;
    if (this.documentoSeleccionado.getDocumentoXNegociacions() != null && this.documentoSeleccionado.getDocumentoXNegociacions().size() > 0) {
      totalCostos = this.documentoSeleccionado.getDocumentoXNegociacions().get(0).getCostoEntrega().doubleValue() + this.documentoSeleccionado.getDocumentoXNegociacions().get(0).getCostoSeguro().doubleValue()
          + this.documentoSeleccionado.getDocumentoXNegociacions().get(0).getCostoFlete().doubleValue() + this.documentoSeleccionado.getDocumentoXNegociacions().get(0).getOtrosGastos().doubleValue();
    }
    totalNegociacion = this.totalCostos + this.totalValorTotal;
    return null;
  }

  public List<ProductoGenerarFacturaPFDTO> getProductos() {
    return productos;
  }

  public void setProductos(List<ProductoGenerarFacturaPFDTO> productos) {
    this.productos = productos;
  }

  public String crearFactura() {

    Documento documento = new Documento();
    documento.setFechaGeneracion(new Timestamp(System.currentTimeMillis()));
    Estadosxdocumento estadosxdocumento = new Estadosxdocumento();
    EstadosxdocumentoPK estadosxdocumentoPK = new EstadosxdocumentoPK();
    estadosxdocumentoPK.setIdEstado((long) ConstantesDocumento.ACTIVO);
    estadosxdocumentoPK.setIdTipoDocumento((long) ConstantesTipoDocumento.FACTURA_PROFORMA);
    estadosxdocumento.setId(estadosxdocumentoPK);
    documento.setEstadosxdocumento(estadosxdocumento);
    documento.setObservacionDocumento(this.documentoSeleccionado.getConsecutivoDocumento());
    documento.setUbicacionDestino(new Ubicacion());
    documento.setUbicacionOrigen(new Ubicacion());
    documento.getUbicacionDestino().setId(this.documentoSeleccionado.getUbicacionOrigen().getId());
    documento.getUbicacionOrigen().setId(this.documentoSeleccionado.getUbicacionOrigen().getId());
    documento.setCliente(this.documentoSeleccionado.getCliente());
    documento.setValorTotal(new BigDecimal(this.totalNegociacion));
    documento.setDocumentoCliente(this.documentoSeleccionado.getDocumentoCliente());
    documento.setFechaEsperadaEntrega(this.documentoSeleccionado.getFechaEsperadaEntrega());
    documento.setNumeroFactura("0");

    LogAuditoria auditoria = new LogAuditoria();
    auditoria.setIdUsuario(menu.getUsuario().getId());
    auditoria.setIdFuncionalidad(menu.getIdOpcionActual());
    auditoria.setTabla("Documentos");
    auditoria.setAccion("CRE");
    auditoria.setFecha(new Timestamp(System.currentTimeMillis()));

    DocumentoXNegociacion dxn = new DocumentoXNegociacion();
    dxn.setPk(new DocumentoXNegociacionPK());
    dxn.getPk().setIdTerminoIncoterm(this.documentoSeleccionado.getDocumentoXNegociacions().get(0).getTerminoIncoterm().getId());
    dxn.setCostoEntrega(this.documentoSeleccionado.getDocumentoXNegociacions().get(0).getCostoEntrega());
    dxn.setCostoSeguro(this.documentoSeleccionado.getDocumentoXNegociacions().get(0).getCostoSeguro());
    dxn.setCostoFlete(this.documentoSeleccionado.getDocumentoXNegociacions().get(0).getCostoFlete());
    dxn.setOtrosGastos(this.documentoSeleccionado.getDocumentoXNegociacions().get(0).getOtrosGastos());
    dxn.setObservacionesMarcacion2(this.documentoSeleccionado.getDocumentoXNegociacions().get(0).getObservacionesMarcacion2());
    dxn.setTotalPesoNeto(this.documentoSeleccionado.getDocumentoXNegociacions().get(0).getTotalPesoNeto());
    dxn.setTotalPesoBruto(this.documentoSeleccionado.getDocumentoXNegociacions().get(0).getTotalPesoBruto());
    dxn.setTotalTendidos(this.documentoSeleccionado.getDocumentoXNegociacions().get(0).getTotalTendidos());
    dxn.setTotalPallets(this.documentoSeleccionado.getDocumentoXNegociacions().get(0).getTotalPallets());
    dxn.setCantidadDiasVigencia(this.cantidadDiasVigencia);
    dxn.setSolicitudCafe(this.documentoSeleccionado.getDocumentoXNegociacions().get(0).getSolicitudCafe());
    dxn.setCantidadContenedoresDe20(this.documentoSeleccionado.getDocumentoXNegociacions().get(0).getCantidadContenedoresDe20());
    dxn.setCantidadContenedoresDe40(this.documentoSeleccionado.getDocumentoXNegociacions().get(0).getCantidadContenedoresDe40());
    dxn.setLugarIncoterm(this.documentoSeleccionado.getDocumentoXNegociacions().get(0).getLugarIncoterm());
    dxn.setCantidadEstibas(0);
    dxn.setPesoBrutoEstibas(0);

    List<ProductosXDocumento> productos = new ArrayList<ProductosXDocumento>();

    for (ProductoGenerarFacturaPFDTO pxc : this.productos) {

      ProductosXDocumento productoDocumento = new ProductosXDocumento();
      productoDocumento.setInformacion(false);
      productoDocumento.setCalidad(false);
      productoDocumento.setFechaEstimadaEntrega(documento.getFechaGeneracion());
      productoDocumento.setFechaEntrega(documento.getFechaGeneracion());
      productoDocumento.setId(new ProductosXDocumentoPK());
      productoDocumento.getId().setIdProducto(pxc.getId().longValue());
      productoDocumento.setCantidad1(pxc.getCantidad());

      Unidad u = new Unidad();
      u.setId(pxc.getIdUnidad().longValue());
      productoDocumento.setUnidade(u); // unidad de venta

      Moneda moneda = new Moneda();
      moneda.setId("USD");

      productoDocumento.setMoneda(moneda);
      productoDocumento.setCantidad2(new BigDecimal(0));
      productoDocumento.setValorUnitatrioMl(new BigDecimal(0));
      productoDocumento.setValorUnitarioUsd(pxc.getPrecio());
      productoDocumento.setValorTotal(pxc.getValorTotal());
      productoDocumento.setTotalPesoNetoItem(pxc.getTotalPesoNeto());

      productoDocumento.setTotalPesoBrutoItem(pxc.getTotalPesoBruto());
      productoDocumento.setCantidadCajasItem(pxc.getTotalCajas());
      productoDocumento.setCantidadPalletsItem(pxc.getTotalCajasPallet());
      productoDocumento.setCantidadXEmbalaje(pxc.getTotalCajasTendido());

      Calendar c = Calendar.getInstance();
      c.add(Calendar.DATE, 2);

      productoDocumento.setFechaEstimadaEntrega(new Timestamp(c.getTimeInMillis()));
      productoDocumento.setFechaEntrega(new Timestamp(c.getTimeInMillis()));

      productos.add(productoDocumento);
    }
    
    
    documento = this.comercioEjb.crearFactura(documento, auditoria, dxn, productos, this.documentoSeleccionado);
    String mensaje = AplicacionMB.getMessage("VentasFPExito_Crear", language);
    String parametros[] = new String[2];
    parametros[0] = "" + documento.getId();
    parametros[1] = documento.getConsecutivoDocumento();
    mensaje = Utilidad.stringFormat(mensaje, parametros);
    //this.documentoSeleccionado.getEstadosxdocumento().getEstado().setId((long) ConstantesDocumento.APROBADA);
    
    System.out.println("Id_FP"+documento.getId());
    System.out.println("Consecutivo SP"+documento.getObservacionDocumento());
    
    
    this.comercioEjb.guardarLotesFP(documento);
    
    
    this.documentoSeleccionado.getEstadosxdocumento().getEstado().setId((long) ConstantesDocumento.CERRADO);
    this.comercioEjb.actualizarEstadoDocumentoPorId(this.documentoSeleccionado);
    this.addMensajeInfo(mensaje);
    this.buscarDocumentos();
    this.deshabilitado = true;
    return null;
  }

  public MenuMB getMenu() {
    return menu;
  }

  public void setMenu(MenuMB menu) {
    this.menu = menu;
  }

}
