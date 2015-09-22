package com.ssl.jv.gip.web.mb.comercioexterior;

import java.math.BigDecimal;
import java.sql.Timestamp;
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
import com.ssl.jv.gip.jpa.pojo.ProductosXDocumento;
import com.ssl.jv.gip.jpa.pojo.Ubicacion;
import com.ssl.jv.gip.negocio.ejb.ComercioExteriorEJBLocal;
import com.ssl.jv.gip.web.mb.AplicacionMB;
import com.ssl.jv.gip.web.mb.MenuMB;
import com.ssl.jv.gip.web.mb.UtilMB;
import com.ssl.jv.gip.web.mb.util.ConstantesDocumento;
import com.ssl.jv.gip.web.mb.util.ConstantesTipoDocumento;
import com.ssl.jv.gip.web.util.Modo;
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
@ManagedBean
@SessionScoped
public class GenerarFPMB extends UtilMB {

  /**
   *
   */
  private static final long serialVersionUID = -2780795923623719268L;

  private static final Logger LOGGER = Logger.getLogger(GenerarFPMB.class);
  private String consecutivoDocumento;
  private final Integer language = AplicacionMB.SPANISH;
  private List<Documento> listaSP;
  private Documento spSelected;
  private List<ProductosXDocumento> productosSP;
  private BigDecimal totalCantidad = new BigDecimal(0.00);
  private BigDecimal totalValorTotal = new BigDecimal(0.00);
  private BigDecimal totalPesoNeto = new BigDecimal(0.00);
  private BigDecimal totalPesoBruto = new BigDecimal(0.00);
  private BigDecimal totalCantidadCajas = new BigDecimal(0.00);
  private BigDecimal totalCantidadTendidos = new BigDecimal(0.00);
  private BigDecimal totalCantidadPallets = new BigDecimal(0.00);
  private BigDecimal totalCostos = new BigDecimal(0.00);
  private BigDecimal totalNegociacion = new BigDecimal(0.00);
  private Integer cantidadDiasVigencia;
  private Modo modo;

  @EJB
  private ComercioExteriorEJBLocal comercioExteriorEJB;

  @ManagedProperty(value = "#{menuMB}")
  private MenuMB menu;

  @PostConstruct
  public void init() {
    this.modo = Modo.LISTAR;
    this.buscarSolicitudesPedidos();
  }

  public boolean isModoLista() {
    return modo == Modo.LISTAR;
  }

  public boolean isModoGenerar() {
    return modo == Modo.GENERAR;
  }

  public void buscarSolicitudesPedidos() {
    listaSP = this.comercioExteriorEJB.consultarDocumentosSolicitudPedido(consecutivoDocumento);
  }

  public String verDetalle(Documento sp) {
    this.spSelected = sp;
    this.cantidadDiasVigencia = null;
    this.consultarSolicitudPedido();
    this.modo = Modo.GENERAR;
    return null;
  }

  private void consultarSolicitudPedido() {
    productosSP = comercioExteriorEJB.consultarProductosSP(this.spSelected.getId(), spSelected.getCliente().getId());
    totalValorTotal = new BigDecimal(0);
    totalCantidad = new BigDecimal(0);
    totalPesoNeto = new BigDecimal(0);
    totalPesoBruto = new BigDecimal(0);
    totalCantidadTendidos = new BigDecimal(0);
    totalCantidadPallets = new BigDecimal(0);
    totalCantidadCajas = new BigDecimal(0);
    if (productosSP != null && !productosSP.isEmpty()) {
      for (ProductosXDocumento pxd : productosSP) {
        totalValorTotal = totalValorTotal.add(pxd.getValorTotal());
        totalCantidad = totalCantidad.add(pxd.getCantidad1());
        totalPesoNeto = totalPesoNeto.add(pxd.getTotalPesoNetoItem());
        totalPesoBruto = totalPesoBruto.add(pxd.getTotalPesoBrutoItem());
        totalCantidadTendidos = totalCantidadTendidos.add(pxd.getCantidadXEmbalaje());
        totalCantidadPallets = totalCantidadPallets.add(pxd.getCantidadPalletsItem());
        totalCantidadCajas = totalCantidadCajas.add(pxd.getCantidadCajasItem());
      }
    }
    totalCostos = BigDecimal.ZERO;
    if (this.spSelected.getDocumentoXNegociacion() != null && this.spSelected.getDocumentoXNegociacions().size() > 0) {
      DocumentoXNegociacion dxn = this.spSelected.getDocumentoXNegociacion();
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
      totalCostos = totalCostosTmp;
    }
    totalNegociacion = this.totalCostos.add(this.totalValorTotal);
  }

  public void crearFactura() {
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
    fp.setValorTotal(this.totalNegociacion);
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
    dxn.getPk().setIdTerminoIncoterm(this.spSelected.getDocumentoXNegociacion().getTerminoIncoterm().getId());
    dxn.setCostoEntrega(this.spSelected.getDocumentoXNegociacion().getCostoEntrega());
    dxn.setCostoSeguro(this.spSelected.getDocumentoXNegociacion().getCostoSeguro());
    dxn.setCostoFlete(this.spSelected.getDocumentoXNegociacion().getCostoFlete());
    dxn.setOtrosGastos(this.spSelected.getDocumentoXNegociacion().getOtrosGastos());
    dxn.setObservacionesMarcacion2(this.spSelected.getDocumentoXNegociacion().getObservacionesMarcacion2());
    dxn.setTotalPesoNeto(this.spSelected.getDocumentoXNegociacion().getTotalPesoNeto());
    dxn.setTotalPesoBruto(this.spSelected.getDocumentoXNegociacion().getTotalPesoBruto());
    dxn.setTotalTendidos(this.spSelected.getDocumentoXNegociacion().getTotalTendidos());
    dxn.setTotalPallets(this.spSelected.getDocumentoXNegociacion().getTotalPallets());
    dxn.setCantidadDiasVigencia(this.cantidadDiasVigencia);
    dxn.setSolicitudCafe(this.spSelected.getDocumentoXNegociacion().getSolicitudCafe());
    dxn.setCantidadContenedoresDe20(this.spSelected.getDocumentoXNegociacion().getCantidadContenedoresDe20());
    dxn.setCantidadContenedoresDe40(this.spSelected.getDocumentoXNegociacion().getCantidadContenedoresDe40());
    dxn.setLugarIncoterm(this.spSelected.getDocumentoXNegociacion().getLugarIncoterm());
    dxn.setCantidadEstibas(0);
    dxn.setPesoBrutoEstibas(0);
    fp = this.comercioExteriorEJB.crearFacturaProforma(fp, auditoria, dxn, productosSP, this.spSelected);
    //TODO: cambiar esto: asignar los lotes OIC
    this.comercioExteriorEJB.asignarLotesOIC(fp);
    String mensaje = AplicacionMB.getMessage("VentasFPExito_Crear", language);
    String parametros[] = new String[2];
    parametros[0] = fp.getId().toString();
    parametros[1] = fp.getConsecutivoDocumento();
    mensaje = Utilidad.stringFormat(mensaje, parametros);
    this.formatearCadenaConParametros("VentasFPExito_Crear", language, parametros);
    this.addMensajeInfo(mensaje);
    this.init();
  }

  public void volverAlListado() {
    this.modo = Modo.LISTAR;
  }

  public void setMenu(MenuMB menu) {
    this.menu = menu;
  }

  public String getConsecutivoDocumento() {
    return consecutivoDocumento;
  }

  public void setConsecutivoDocumento(String consecutivoDocumento) {
    this.consecutivoDocumento = consecutivoDocumento;
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

  public List<ProductosXDocumento> getProductosSP() {
    return productosSP;
  }

  public void setProductosSP(List<ProductosXDocumento> productosSP) {
    this.productosSP = productosSP;
  }

  public BigDecimal getTotalCantidad() {
    return totalCantidad;
  }

  public void setTotalCantidad(BigDecimal totalCantidad) {
    this.totalCantidad = totalCantidad;
  }

  public BigDecimal getTotalValorTotal() {
    return totalValorTotal;
  }

  public void setTotalValorTotal(BigDecimal totalValorTotal) {
    this.totalValorTotal = totalValorTotal;
  }

  public BigDecimal getTotalPesoNeto() {
    return totalPesoNeto;
  }

  public void setTotalPesoNeto(BigDecimal totalPesoNeto) {
    this.totalPesoNeto = totalPesoNeto;
  }

  public BigDecimal getTotalPesoBruto() {
    return totalPesoBruto;
  }

  public void setTotalPesoBruto(BigDecimal totalPesoBruto) {
    this.totalPesoBruto = totalPesoBruto;
  }

  public BigDecimal getTotalCantidadCajas() {
    return totalCantidadCajas;
  }

  public void setTotalCantidadCajas(BigDecimal totalCantidadCajas) {
    this.totalCantidadCajas = totalCantidadCajas;
  }

  public BigDecimal getTotalCantidadTendidos() {
    return totalCantidadTendidos;
  }

  public void setTotalCantidadTendidos(BigDecimal totalCantidadTendidos) {
    this.totalCantidadTendidos = totalCantidadTendidos;
  }

  public BigDecimal getTotalCantidadPallets() {
    return totalCantidadPallets;
  }

  public void setTotalCantidadPallets(BigDecimal totalCantidadPallets) {
    this.totalCantidadPallets = totalCantidadPallets;
  }

  public BigDecimal getTotalCostos() {
    return totalCostos;
  }

  public void setTotalCostos(BigDecimal totalCostos) {
    this.totalCostos = totalCostos;
  }

  public BigDecimal getTotalNegociacion() {
    return totalNegociacion;
  }

  public void setTotalNegociacion(BigDecimal totalNegociacion) {
    this.totalNegociacion = totalNegociacion;
  }

  public Integer getCantidadDiasVigencia() {
    return cantidadDiasVigencia;
  }

  public void setCantidadDiasVigencia(Integer cantidadDiasVigencia) {
    this.cantidadDiasVigencia = cantidadDiasVigencia;
  }

}
