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
 * Title: GIP
 * </p>
 *
 * <p>
 * Description: GIP
 * </p>
 *
 * <p>
 * Copyright: Copyright (c) 2014
 * </p>
 *
 * <p>
 * Company: Soft Studio Ltda.
 * </p>
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
  private final Integer language = AplicacionMB.SPANISH;
  private List<Documento> listaSP;
  private Documento spSelected;
  private List<ProductoGenerarFacturaPFDTO> productosXSP;
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

  @PostConstruct
  public void init() {
    currentTimeStamp = new Timestamp(System.currentTimeMillis());
  }

  public String buscarSolicitudesPedidos() {
    listaSP = this.comercioEjb.consultarDocumentosSolicitudPedido(consecutivoDocumento);
    this.deshabilitado = false;
    return null;
  }

  public String consultarSolicitudPedido() {
    productosXSP = comercioEjb.consultarProductoPorDocumentoGenerarFacturaProforma(this.spSelected.getId(), this.spSelected.getCliente().getId());
    this.totalCantidad = 0;
    this.totalValorTotal = 0;
    this.totalPesoNeto = 0;
    this.totalPesoBruto = 0;
    this.totalCantidadCajas = 0;
    this.totalCantidadTendidos = 0;
    this.totalCantidadPallets = 0;
    for (ProductoGenerarFacturaPFDTO p : productosXSP) {
      this.totalCantidad += p.getCantidad().doubleValue();
      this.totalValorTotal += p.getValorTotal().doubleValue();
      this.totalPesoNeto += p.getTotalPesoNeto().doubleValue();
      this.totalPesoBruto += p.getTotalPesoBruto().doubleValue();
      this.totalCantidadCajas += p.getTotalCajas().doubleValue();
      this.totalCantidadTendidos += p.getTotalCajasTendido().doubleValue();
      this.totalCantidadPallets += p.getTotalCajasPallet().doubleValue();
    }
    totalCostos = 0;
    if (this.spSelected.getDocumentoXNegociacions() != null && this.spSelected.getDocumentoXNegociacions().size() > 0) {
      DocumentoXNegociacion dxn = this.spSelected.getDocumentoXNegociacions().get(0);
      BigDecimal totalCostosTmp = new BigDecimal(0);
      if (dxn.getCostoEntrega() != null) {
        totalCostosTmp = totalCostosTmp.add(dxn.getCostoEntrega());
      }
      if (dxn.getCostoSeguro() != null) {
        totalCostosTmp = totalCostosTmp.add(dxn.getCostoSeguro());
      }
      if (dxn.getCostoFlete() != null) {
        totalCostosTmp = totalCostosTmp.add(dxn.getCostoFlete());
      }
      if (dxn.getOtrosGastos() != null) {
        totalCostosTmp = totalCostosTmp.add(dxn.getOtrosGastos());
      }
      totalCostos = totalCostosTmp.doubleValue();
    }
    totalNegociacion = this.totalCostos + this.totalValorTotal;
    return null;
  }

  public String crearFactura() {
    Documento fp = new Documento();
    fp.setFechaGeneracion(new Timestamp(System.currentTimeMillis()));
    Estadosxdocumento estadosxdocumento = new Estadosxdocumento();
    EstadosxdocumentoPK estadosxdocumentoPK = new EstadosxdocumentoPK();
    estadosxdocumentoPK.setIdEstado((long) ConstantesDocumento.ACTIVO);
    estadosxdocumentoPK.setIdTipoDocumento((long) ConstantesTipoDocumento.FACTURA_PROFORMA);
    estadosxdocumento.setId(estadosxdocumentoPK);
    fp.setEstadosxdocumento(estadosxdocumento);
    fp.setObservacionDocumento(this.spSelected.getConsecutivoDocumento());
    fp.setUbicacionDestino(new Ubicacion());
    fp.setUbicacionOrigen(new Ubicacion());
    fp.getUbicacionDestino().setId(this.spSelected.getUbicacionOrigen().getId());
    fp.getUbicacionOrigen().setId(this.spSelected.getUbicacionOrigen().getId());
    fp.setCliente(this.spSelected.getCliente());
    fp.setValorTotal(new BigDecimal(this.totalNegociacion));
    fp.setDocumentoCliente(this.spSelected.getDocumentoCliente());
    fp.setFechaEsperadaEntrega(this.spSelected.getFechaEsperadaEntrega());
    fp.setNumeroFactura("0");
    // auditoria
    LogAuditoria auditoria = new LogAuditoria();
    auditoria.setIdUsuario(menu.getUsuario().getId());
    auditoria.setIdFuncionalidad(menu.getIdOpcionActual());
    auditoria.setTabla("Documentos");
    auditoria.setAccion("CRE");
    auditoria.setFecha(new Timestamp(System.currentTimeMillis()));
    // dxn
    DocumentoXNegociacion dxn = new DocumentoXNegociacion();
    dxn.setPk(new DocumentoXNegociacionPK());
    dxn.getPk().setIdTerminoIncoterm(this.spSelected.getDocumentoXNegociacions().get(0).getTerminoIncoterm().getId());
    dxn.setCostoEntrega(this.spSelected.getDocumentoXNegociacions().get(0).getCostoEntrega());
    dxn.setCostoSeguro(this.spSelected.getDocumentoXNegociacions().get(0).getCostoSeguro());
    dxn.setCostoFlete(this.spSelected.getDocumentoXNegociacions().get(0).getCostoFlete());
    dxn.setOtrosGastos(this.spSelected.getDocumentoXNegociacions().get(0).getOtrosGastos());
    dxn.setObservacionesMarcacion2(this.spSelected.getDocumentoXNegociacions().get(0).getObservacionesMarcacion2());
    dxn.setTotalPesoNeto(this.spSelected.getDocumentoXNegociacions().get(0).getTotalPesoNeto());
    dxn.setTotalPesoBruto(this.spSelected.getDocumentoXNegociacions().get(0).getTotalPesoBruto());
    dxn.setTotalTendidos(this.spSelected.getDocumentoXNegociacions().get(0).getTotalTendidos());
    dxn.setTotalPallets(this.spSelected.getDocumentoXNegociacions().get(0).getTotalPallets());
    dxn.setCantidadDiasVigencia(this.cantidadDiasVigencia);
    dxn.setSolicitudCafe(this.spSelected.getDocumentoXNegociacions().get(0).getSolicitudCafe());
    dxn.setCantidadContenedoresDe20(this.spSelected.getDocumentoXNegociacions().get(0).getCantidadContenedoresDe20());
    dxn.setCantidadContenedoresDe40(this.spSelected.getDocumentoXNegociacions().get(0).getCantidadContenedoresDe40());
    dxn.setLugarIncoterm(this.spSelected.getDocumentoXNegociacions().get(0).getLugarIncoterm());
    dxn.setCantidadEstibas(0);
    dxn.setPesoBrutoEstibas(0);
    // pxd
    List<ProductosXDocumento> pxd = new ArrayList<>();
    for (ProductoGenerarFacturaPFDTO pxc : this.productosXSP) {
      ProductosXDocumento productoDocumento = new ProductosXDocumento();
      productoDocumento.setInformacion(false);
      productoDocumento.setCalidad(false);
      productoDocumento.setFechaEstimadaEntrega(fp.getFechaGeneracion());
      productoDocumento.setFechaEntrega(fp.getFechaGeneracion());
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
      pxd.add(productoDocumento);
    }
    fp = this.comercioEjb.crearFacturaProforma(fp, auditoria, dxn, pxd, this.spSelected);
    //asignar los lotes OIC
    this.comercioEjb.asignarLotesOIC(fp);

    String mensaje = AplicacionMB.getMessage("VentasFPExito_Crear", language);
    String parametros[] = new String[2];
    parametros[0] = "" + fp.getId();
    parametros[1] = fp.getConsecutivoDocumento();
    mensaje = Utilidad.stringFormat(mensaje, parametros);
    this.addMensajeInfo(mensaje);
    this.buscarSolicitudesPedidos();
    this.deshabilitado = true;
    return null;
  }

  public List<ProductoGenerarFacturaPFDTO> getProductosXSP() {
    return productosXSP;
  }

  public void setProductosXSP(List<ProductoGenerarFacturaPFDTO> productosXSP) {
    this.productosXSP = productosXSP;
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

  public boolean isDeshabilitado() {
    return deshabilitado;
  }

  public void setDeshabilitado(boolean deshabilitado) {
    this.deshabilitado = deshabilitado;
  }

  public List<Documento> getListaSP() {
    return listaSP;
  }

  public void setListaSP(List<Documento> listaSP) {
    this.listaSP = listaSP;
  }

  public Documento getSpSelected() {
    return spSelected;
  }

  public void setSpSelected(Documento spSelected) {
    this.spSelected = spSelected;
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

  public MenuMB getMenu() {
    return menu;
  }

  public void setMenu(MenuMB menu) {
    this.menu = menu;
  }

}
