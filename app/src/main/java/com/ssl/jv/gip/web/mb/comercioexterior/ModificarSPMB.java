package com.ssl.jv.gip.web.mb.comercioexterior;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.ssl.jv.gip.jpa.pojo.Documento;
import com.ssl.jv.gip.jpa.pojo.DocumentoXNegociacion;
import com.ssl.jv.gip.jpa.pojo.LogAuditoria;
import com.ssl.jv.gip.jpa.pojo.Moneda;
import com.ssl.jv.gip.jpa.pojo.ProductosXClienteComext;
import com.ssl.jv.gip.jpa.pojo.ProductosXDocumento;
import com.ssl.jv.gip.jpa.pojo.ProductosXDocumentoPK;
import com.ssl.jv.gip.jpa.pojo.TerminoIncoterm;
import com.ssl.jv.gip.jpa.pojo.Ubicacion;
import com.ssl.jv.gip.negocio.dto.FiltroDocumentoDTO;
import com.ssl.jv.gip.negocio.ejb.ComercioExteriorEJBLocal;
import com.ssl.jv.gip.negocio.ejb.ComunEJBLocal;
import com.ssl.jv.gip.web.mb.MenuMB;
import com.ssl.jv.gip.web.mb.UtilMB;
import com.ssl.jv.gip.web.mb.util.ConstantesDocumento;
import com.ssl.jv.gip.web.mb.util.ConstantesTipoDocumento;
import com.ssl.jv.gip.web.util.Modo;
import com.ssl.jv.gip.web.util.Utilidad;

/**
 * The Class ModificarSPMB.
 */
@ManagedBean
@ViewScoped
public class ModificarSPMB extends UtilMB {

  private static final long serialVersionUID = 1L;
  private final MathContext mathContext = new MathContext(3, RoundingMode.HALF_DOWN);
  @EJB
  private ComercioExteriorEJBLocal comercioExteriorEJB;
  @EJB
  private ComunEJBLocal comunEJB;
  @ManagedProperty(value = "#{menuMB}")
  private MenuMB menu;
  private Modo modo;
  private FiltroDocumentoDTO filtro;
  private List<Documento> listaSP = new ArrayList<>();
  private Documento sp;
  private List<ProductosXDocumento> productosSP;
  private List<ProductosXClienteComext> productosXCliente;
  private BigDecimal totalCantidad = new BigDecimal(0.00);
  private BigDecimal totalValorTotal = new BigDecimal(0.00);
  private BigDecimal totalPesoNeto = new BigDecimal(0.00);
  private BigDecimal totalPesoBruto = new BigDecimal(0.00);
  private BigDecimal totalCantidadCajas = new BigDecimal(0.00);
  private BigDecimal totalCantidadTendidos = new BigDecimal(0.00);
  private BigDecimal totalCantidadPallets = new BigDecimal(0.00);
  private Map<Long, BigDecimal> ultimosSaldosInventario;

  /**
   * Inits the.
   */
  @PostConstruct
  public void init() {
    this.modo = Modo.LISTAR;
    this.filtro = new FiltroDocumentoDTO();
    this.consultarListaSP();
  }

  public void consultarListaSP() {
    filtro.setTipoDocumento(new Long(ConstantesTipoDocumento.SOLICITUD_PEDIDO));
    filtro.setEstado((long) ConstantesDocumento.ACTIVO, (long) ConstantesDocumento.VERIFICADO);
    filtro.setOrdenCampo("consecutivoDocumento DESC");
    this.listaSP = comunEJB.consultarDocumentos(filtro);
  }

  public String verDetalle(Documento selected) {
    this.sp = selected;
    if (this.sp.getUbicacionOrigen() == null) {
      this.sp.setUbicacionOrigen(new Ubicacion());
    }
    if (this.sp.getUbicacionDestino() == null) {
      this.sp.setUbicacionDestino(new Ubicacion());
    }
    if (this.sp.getDocumentoXNegociacions() == null) {
      this.sp.setDocumentoXNegociacions(new ArrayList<DocumentoXNegociacion>());
      this.sp.getDocumentoXNegociacions().add(new DocumentoXNegociacion());
    }
    if (this.sp.getDocumentoXNegociacion().getTerminoIncoterm() == null) {
      this.sp.getDocumentoXNegociacion().setTerminoIncoterm(new TerminoIncoterm());
    }
    this.productosSP = comercioExteriorEJB.consultarProductosSP(this.sp.getId(), sp.getCliente().getId());
    this.recalcularTotalesLista();
    this.modo = Modo.EDITAR;
    this.consultarSaldosInventarioComercioExterior();
    return null;
  }

  public void consultarSaldosInventarioComercioExterior() {
    ultimosSaldosInventario = this.comercioExteriorEJB.consultarUltimosSaldos();
  }

  /**
   * Refrescar totales.
   */
  public void recalcularTotalesLista() {
    totalValorTotal = new BigDecimal(0);
    totalCantidad = new BigDecimal(0);
    totalPesoNeto = new BigDecimal(0);
    totalPesoBruto = new BigDecimal(0);
    totalCantidadTendidos = new BigDecimal(0);
    totalCantidadPallets = new BigDecimal(0);
    totalCantidadCajas = new BigDecimal(0);
    if (productosSP != null && !productosSP.isEmpty()) {
      for (ProductosXDocumento pxd : productosSP) {
        calcularTotalesRegistro(pxd);
        totalValorTotal = totalValorTotal.add(pxd.getValorTotal());
        totalCantidad = totalCantidad.add(pxd.getCantidad1());
        totalPesoNeto = totalPesoNeto.add(pxd.getTotalPesoNetoItem());
        totalPesoBruto = totalPesoBruto.add(pxd.getTotalPesoBrutoItem());
        totalCantidadTendidos = totalCantidadTendidos.add(pxd.getCantidadXEmbalaje());
        totalCantidadPallets = totalCantidadPallets.add(pxd.getCantidadPalletsItem());
        totalCantidadCajas = totalCantidadCajas.add(pxd.getCantidadCajasItem());
      }
    }
  }

  public void calcularTotalesRegistro(ProductosXDocumento pxd) {
    BigDecimal cantidad = pxd.getCantidad1();
    BigDecimal precio = pxd.getValorUnitarioUsd();
    BigDecimal cantidadXEmbalaje = pxd.getProductosInventario().getProductosInventarioComext().getCantidadXEmbalaje();
    BigDecimal pesoNetoEmbalaje = pxd.getProductosInventario().getProductosInventarioComext().getPesoNetoEmbalaje();
    BigDecimal pesoBrutoEmbalaje = pxd.getProductosInventario().getProductosInventarioComext().getPesoBrutoEmbalaje();
    BigDecimal cantidadCajasXTendido = pxd.getProductosInventario().getProductosInventarioComext().getCantCajasXTendido();
    BigDecimal totalCajasXPallet = pxd.getProductosInventario().getProductosInventarioComext().getTotalCajasXPallet();
    pxd.setValorUnitarioUsd(precio);
    pxd.setFechaEntrega(sp.getFechaEsperadaEntrega());
    pxd.setFechaEstimadaEntrega(sp.getFechaEsperadaEntrega());
    pxd.setValorTotal(cantidad.multiply(precio));
    if (cantidadXEmbalaje == null || cantidadXEmbalaje.compareTo(BigDecimal.ZERO) == 0) {
      pxd.setTotalPesoNetoItem(BigDecimal.ZERO);
      pxd.setTotalPesoBrutoItem(BigDecimal.ZERO);
      pxd.setCantidadXEmbalaje(BigDecimal.ZERO);
      pxd.setCantidadCajasItem(BigDecimal.ZERO);
      pxd.setCantidadPalletsItem(BigDecimal.ZERO);
    } else {
      pxd.setTotalPesoNetoItem(pesoNetoEmbalaje.divide(cantidadXEmbalaje, mathContext).multiply(cantidad, mathContext));
      pxd.setTotalPesoBrutoItem(pesoBrutoEmbalaje.divide(cantidadXEmbalaje, mathContext).multiply(cantidad, mathContext));
      pxd.setCantidadCajasItem(cantidad.divide(cantidadXEmbalaje, mathContext));
      if (cantidadCajasXTendido == null || cantidadCajasXTendido.compareTo(BigDecimal.ZERO) == 0) {
        pxd.setCantidadXEmbalaje(BigDecimal.ZERO);
      } else {
        pxd.setCantidadXEmbalaje(cantidad.divide(cantidadXEmbalaje, mathContext).divide(cantidadCajasXTendido, mathContext));
      }
      if (totalCajasXPallet == null || totalCajasXPallet.compareTo(BigDecimal.ZERO) == 0) {
        pxd.setCantidadPalletsItem(BigDecimal.ZERO);
      } else {
        pxd.setCantidadPalletsItem(cantidad.divide(cantidadXEmbalaje, mathContext).divide(totalCajasXPallet, mathContext));
      }
    }
  }

  /**
   * consultar los productos del cliente activos y q no estan ya en el documento
   */
  public void consultarOtrosProductosDelCliente() {
    Map<String, Object> parametros = new HashMap<>();
    List<Long> productosAExcluir = new ArrayList<>();
    if (productosSP != null && !productosSP.isEmpty()) {
      for (ProductosXDocumento pxd : productosSP) {
        productosAExcluir.add(pxd.getProductosInventario().getId());
      }
    }
    parametros.put("cliente", sp.getCliente().getId());
    parametros.put("solicitudCafe", sp.getDocumentoXNegociacion().getSolicitudCafe());
    parametros.put("productosAExcluir", productosAExcluir);
    productosXCliente = comercioExteriorEJB.consultarProductosActivosXCliente(parametros);
  }

  /**
   * Este metodo debe agregar el producto seleccionado a la solicitud pero debe realizar todas las validaciones necesarias
   *
   * @param pxc
   */
  public void agregarProducto(ProductosXClienteComext pxc) {
    if (pxc != null) {
      if (pxc.getProductosInventario().getProductosInventarioComext().getControlStock()) {
        BigDecimal ultimoSaldo = ultimosSaldosInventario.get(pxc.getProductosInventario().getId());
        if (ultimoSaldo == null) {
          ultimoSaldo = BigDecimal.ZERO;
        }
        if (ultimoSaldo.compareTo(BigDecimal.ZERO) == 0) {
          this.addMensajeWarn(":tabPanel:msgsBuscarProductos", Utilidad.stringFormat("Para el producto {0}, no hay saldo siponible.", new String[]{pxc.getProductosInventario().getSku()}));
          return;
        }
      }
      ProductosXDocumento pxd = new ProductosXDocumento();
      ProductosXDocumentoPK pxdID = new ProductosXDocumentoPK();
      pxdID.setIdDocumento(sp.getId());
      pxdID.setIdProducto(pxc.getProductosInventario().getId());
      pxd.setId(pxdID);
      pxd.setDocumento(sp); 
      pxd.setProductosInventario(pxc.getProductosInventario());
      pxd.setInformacion(false);
      pxd.setCalidad(false);
      pxd.setFechaEntrega(sp.getFechaEsperadaEntrega());
      pxd.setFechaEstimadaEntrega(sp.getFechaEsperadaEntrega());
      pxd.setCantidad1(BigDecimal.ONE);
      pxd.setCantidad2(BigDecimal.ZERO);
      pxd.setUnidade(pxc.getProductosInventario().getUnidadVenta());
      pxd.setMoneda(new Moneda(pxc.getIdMoneda()));
      pxd.setValorUnitarioUsd(pxc.getPrecio());
      pxd.setValorUnitatrioMl(BigDecimal.ZERO);
      this.productosSP.add(pxd);
      this.productosXCliente.remove(pxc);
      this.recalcularTotalesLista();
      this.addMensajeInfo(":tabPanel:msgsBuscarProductos", Utilidad.stringFormat("Producto {0} agregado.", new String[]{pxc.getProductosInventario().getSku()}));
    }
  }

  public void eliminarProducto(ProductosXDocumento pxd) {
    if (pxd != null) {
      this.productosSP.remove(pxd);
      this.recalcularTotalesLista();
    }
  }

  /**
   * Guardar ajustes pedido.
   */
  public void guardar() {
    this.recalcularTotalesLista();
    // solicitud
    sp.setValorTotal(totalValorTotal);
    // TODO: como se calculan estos costos, se deben incluir de una vez
    sp.getDocumentoXNegociacion().setCostoEntrega(new BigDecimal(0));
    sp.getDocumentoXNegociacion().setCostoSeguro(new BigDecimal(0));
    sp.getDocumentoXNegociacion().setCostoFlete(new BigDecimal(0));
    sp.getDocumentoXNegociacion().setOtrosGastos(new BigDecimal(0));
    sp.getDocumentoXNegociacion().setTotalPesoNeto(totalPesoNeto);
    sp.getDocumentoXNegociacion().setTotalPesoBruto(totalPesoBruto);
    sp.getDocumentoXNegociacion().setTotalTendidos(totalCantidadTendidos);
    sp.getDocumentoXNegociacion().setTotalPallets(totalCantidadPallets);
    sp.getDocumentoXNegociacion().setObservacionesMarcacion2(null);
    sp.getDocumentoXNegociacion().setCantidadDiasVigencia(0);
    sp.getDocumentoXNegociacion().setCantidadEstibas(0);
    sp.getDocumentoXNegociacion().setPesoBrutoEstibas(0);
    // auditoria
    LogAuditoria auditoria = new LogAuditoria();
    auditoria.setIdUsuario(menu.getUsuario().getId());
    auditoria.setIdFuncionalidad(menu.getIdOpcionActual());
    try {
      // verificar la sp
      comercioExteriorEJB.modificarSP(sp, productosSP, auditoria);
      this.addMensajeInfo("Se ha actualizado correctamente la Solicitud de Pedido " + sp.getConsecutivoDocumento());
      this.init();
    } catch (Exception ex) {
      this.addMensajeError(ex.getMessage());
    }
  }

  public void volverAlListado() {
    this.init();
  }

  public boolean renderedFormList() {
    boolean showList = (modo == Modo.LISTAR);
    return showList;
  }

  public boolean renderedFormDetail() {
    boolean showList = (modo == Modo.EDITAR);
    return showList;
  }

  public void setMenu(MenuMB menu) {
    this.menu = menu;
  }

  public FiltroDocumentoDTO getFiltro() {
    return filtro;
  }

  public void setFiltro(FiltroDocumentoDTO filtro) {
    this.filtro = filtro;
  }

  public List<Documento> getListaSP() {
    return listaSP;
  }

  public void setListaSP(List<Documento> listaSP) {
    this.listaSP = listaSP;
  }

  public Documento getSp() {
    return sp;
  }

  public void setSp(Documento sp) {
    this.sp = sp;
  }

  public List<ProductosXDocumento> getProductosSP() {
    return productosSP;
  }

  public void setProductosSP(List<ProductosXDocumento> productosSP) {
    this.productosSP = productosSP;
  }

  public List<ProductosXClienteComext> getProductosXCliente() {
    return productosXCliente;
  }

  public void setProductosXCliente(List<ProductosXClienteComext> productosXCliente) {
    this.productosXCliente = productosXCliente;
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

}
