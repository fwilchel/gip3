package com.ssl.jv.gip.web.mb.comercioexterior;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.ssl.jv.gip.jpa.pojo.Documento;
import com.ssl.jv.gip.jpa.pojo.DocumentoXNegociacion;
import com.ssl.jv.gip.jpa.pojo.DocumentoXNegociacionPK;
import com.ssl.jv.gip.jpa.pojo.LogAuditoria;
import com.ssl.jv.gip.jpa.pojo.Moneda;
import com.ssl.jv.gip.jpa.pojo.ProductosXClienteComext;
import com.ssl.jv.gip.jpa.pojo.ProductosXDocumento;
import com.ssl.jv.gip.jpa.pojo.ProductosXDocumentoPK;
import com.ssl.jv.gip.jpa.pojo.TerminoIncoterm;
import com.ssl.jv.gip.negocio.dto.DocumentoIncontermDTO;
import com.ssl.jv.gip.negocio.ejb.ComercioExteriorEJBLocal;
import com.ssl.jv.gip.web.mb.MenuMB;
import com.ssl.jv.gip.web.mb.UtilMB;
import com.ssl.jv.gip.web.util.Modo;
import com.ssl.jv.gip.web.util.Utilidad;
import java.util.HashMap;
import java.util.Map;

/**
 * The Class SolicitudPedidoMB.
 */
@ManagedBean(name = "solicitudPedidoMB")
@ViewScoped
public class SolicitudPedidoMB extends UtilMB {

  private static final long serialVersionUID = 1L;
  private final MathContext mathContext = new MathContext(3, RoundingMode.HALF_DOWN);
  @EJB
  private ComercioExteriorEJBLocal comercioExteriorEJB;
  @ManagedProperty(value = "#{menuMB}")
  private MenuMB menu;
  private Modo modo;
  private ArrayList<DocumentoIncontermDTO> listaSP = new ArrayList<>();
  private DocumentoIncontermDTO sp;
  private List<ProductosXDocumento> productosXDocumento;
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
    this.listaSP = (ArrayList<DocumentoIncontermDTO>) comercioExteriorEJB.consultarDocumentosSolicitudPedido();
    this.modo = Modo.LISTAR;
  }

  public String onEdit(DocumentoIncontermDTO selected) {
    this.sp = selected;
    this.productosXDocumento = comercioExteriorEJB.consultarProductosSP(this.sp.getIdDocumento(), sp.getClientesId());
    this.recalcularTotales();
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
  public void recalcularTotales() {
    totalValorTotal = new BigDecimal(0);
    totalCantidad = new BigDecimal(0);
    totalPesoNeto = new BigDecimal(0);
    totalPesoBruto = new BigDecimal(0);
    totalCantidadTendidos = new BigDecimal(0);
    totalCantidadPallets = new BigDecimal(0);
    totalCantidadCajas = new BigDecimal(0);
    if (productosXDocumento != null && !productosXDocumento.isEmpty()) {
      for (ProductosXDocumento pxd : productosXDocumento) {
        calcularTotalesPorRegistro(pxd);
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

  public void calcularTotalesPorRegistro(ProductosXDocumento pxd) {
    BigDecimal cantidad = pxd.getCantidad1();
    BigDecimal precio = pxd.getValorUnitarioUsd();
    BigDecimal cantidadXEmbalaje = pxd.getProductosInventario().getProductosInventarioComext().getCantidadXEmbalaje();
    BigDecimal pesoNetoEmbalaje = pxd.getProductosInventario().getProductosInventarioComext().getPesoNetoEmbalaje();
    BigDecimal pesoBrutoEmbalaje = pxd.getProductosInventario().getProductosInventarioComext().getPesoBrutoEmbalaje();
    BigDecimal cantidadCajasXTendido = pxd.getProductosInventario().getProductosInventarioComext().getCantCajasXTendido();
    BigDecimal totalCajasXPallet = pxd.getProductosInventario().getProductosInventarioComext().getTotalCajasXPallet();
    pxd.setValorUnitarioUsd(precio);
    pxd.setFechaEntrega(sp.getFechaEsperadaEntregaDate());
    pxd.setFechaEstimadaEntrega(sp.getFechaEsperadaEntregaDate());
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
    if (productosXDocumento != null && !productosXDocumento.isEmpty()) {
      for (ProductosXDocumento pxd : productosXDocumento) {
        productosAExcluir.add(pxd.getProductosInventario().getId());
      }
    }
    parametros.put("cliente", sp.getIdCliente());
    parametros.put("solicitudCafe", sp.getSolicitudCafe());
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
          this.addMensajeWarn("tabPanel:msgsBuscarProductos", Utilidad.stringFormat("Para el producto {0}, no hay saldo siponible.", new String[]{pxc.getProductosInventario().getSku()}));
          return;
        }
      }
      ProductosXDocumento pxd = new ProductosXDocumento();
      ProductosXDocumentoPK pxdID = new ProductosXDocumentoPK();
      pxdID.setIdDocumento(sp.getIdDocumento());
      pxdID.setIdProducto(pxc.getProductosInventario().getId());
      pxd.setId(pxdID);
      pxd.setDocumento(new Documento(sp.getIdDocumento()));
      pxd.setProductosInventario(pxc.getProductosInventario());
      pxd.setUnidade(pxc.getProductosInventario().getUnidadVenta());
      pxd.setFechaEntrega(sp.getFechaEntrega());
      pxd.setMoneda(new Moneda(pxc.getIdMoneda()));
      pxd.setInformacion(false);
      pxd.setCalidad(false);
      pxd.setCantidad1(BigDecimal.ONE);
      pxd.setValorUnitarioUsd(pxc.getPrecio());
      this.calcularTotalesPorRegistro(pxd);
      this.productosXDocumento.add(pxd);
      this.productosXCliente.remove(pxc);
      this.recalcularTotales();
      this.addMensajeInfo("tabPanel:msgsBuscarProductos", Utilidad.stringFormat("Producto {0} agregado.", new String[]{pxc.getProductosInventario().getSku()}));
    }
  }

  public void eliminarProducto(ProductosXDocumento pxd) {
    if (pxd != null) {
      this.productosXDocumento.remove(pxd);
      this.recalcularTotales();
    }
  }

  /**
   * Guardar ajustes pedido.
   */
  public void guardarAjustesSP() {
    this.recalcularTotales();
    // solicitud
    sp.setValorTotalDocumento(totalValorTotal);
    // documento x negociacion
    DocumentoXNegociacion dxn = new DocumentoXNegociacion();
    DocumentoXNegociacionPK dxnPK = new DocumentoXNegociacionPK();
    dxnPK.setIdDocumento(sp.getIdDocumento());
    dxnPK.setIdTerminoIncoterm(sp.getIdTerminoIncoterm());
    dxn.setPk(dxnPK);
    // TODO: como se calculan estos costos, se deben incluir de una vez
    dxn.setCostoEntrega(new BigDecimal(0));
    dxn.setCostoSeguro(new BigDecimal(0));
    dxn.setCostoFlete(new BigDecimal(0));
    dxn.setOtrosGastos(new BigDecimal(0));
    dxn.setTotalPesoNeto(totalPesoNeto);
    dxn.setTotalPesoBruto(totalPesoBruto);
    dxn.setTotalTendidos(totalCantidadTendidos);
    dxn.setTotalPallets(totalCantidadPallets);
    dxn.setSolicitudCafe(sp.getSolicitudCafe());
    dxn.setLugarIncoterm(sp.getLugarIncoterm());
    dxn.setTerminoIncoterm(new TerminoIncoterm(sp.getIdTerminoIncoterm()));
    dxn.setObservacionesMarcacion2(null);
    dxn.setCantidadDiasVigencia(0);
    dxn.setCantidadContenedoresDe20(sp.getCantidadContenedores20());
    dxn.setCantidadContenedoresDe40(sp.getCantidadContenedores40());
    dxn.setCantidadEstibas(0);
    dxn.setPesoBrutoEstibas(0);
    // auditoria
    LogAuditoria auditoria = new LogAuditoria();
    auditoria.setIdUsuario(menu.getUsuario().getId());
    auditoria.setIdFuncionalidad(menu.getIdOpcionActual());
    try {
      // verificar la sp
      comercioExteriorEJB.verificarSolicitudPedido(sp, dxn, productosXDocumento, auditoria);
      this.addMensajeInfo("Se ha actualizado correctamente la Solicitud de Pedido " + sp.getConsecutivoDocumento());
      this.init();
    } catch (Exception ex) {
      this.addMensajeError(ex.getMessage());
    }
  }

  public void backToList() {
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

  /**
   * Gets the sp.
   *
   * @return the sp
   */
  public DocumentoIncontermDTO getSp() {
    return sp;
  }

  /**
   * Sets the sp.
   *
   * @param sp the new sp
   */
  public void setSp(DocumentoIncontermDTO sp) {
    this.sp = sp;
  }

  /**
   * @return the productosXDocumento
   */
  public List<ProductosXDocumento> getProductosXDocumento() {
    return productosXDocumento;
  }

  /**
   * @param productosXDocumento the productosXDocumento to set
   */
  public void setProductosXDocumento(List<ProductosXDocumento> productosXDocumento) {
    this.productosXDocumento = productosXDocumento;
  }

  /**
   * @return the productosXCliente
   */
  public List<ProductosXClienteComext> getProductosXCliente() {
    return productosXCliente;
  }

  /**
   * @param productosXCliente the productosXCliente to set
   */
  public void setProductosXCliente(List<ProductosXClienteComext> productosXCliente) {
    this.productosXCliente = productosXCliente;
  }

  /**
   * Gets the lista documentos.
   *
   * @return the lista documentos
   */
  public ArrayList<DocumentoIncontermDTO> getListaSP() {
    return listaSP;
  }

  /**
   * Sets the lista documentos.
   *
   * @param listaSP the new lista documentos
   */
  public void setListaSP(ArrayList<DocumentoIncontermDTO> listaSP) {
    this.listaSP = listaSP;
  }

  /**
   * Gets the total valor t.
   *
   * @return the total valor t
   */
  public BigDecimal getTotalValorTotal() {
    return totalValorTotal;
  }

  /**
   * Sets the total valor t.
   *
   * @param totalValorTotal the new total valor t
   */
  public void setTotalValorTotal(BigDecimal totalValorTotal) {
    this.totalValorTotal = totalValorTotal;
  }

  /**
   * Gets the cantidad valor t.
   *
   * @return the cantidad valor t
   */
  public BigDecimal getTotalCantidad() {
    return totalCantidad;
  }

  /**
   * Sets the cantidad valor t.
   *
   * @param totalCantidad the new cantidad valor t
   */
  public void setTotalCantidad(BigDecimal totalCantidad) {
    this.totalCantidad = totalCantidad;
  }

  /**
   * Gets the valor peso neto t.
   *
   * @return the valor peso neto t
   */
  public BigDecimal getTotalPesoNeto() {
    return totalPesoNeto;
  }

  /**
   * Sets the valor peso neto t.
   *
   * @param totalPesoNeto the new valor peso neto t
   */
  public void setTotalPesoNeto(BigDecimal totalPesoNeto) {
    this.totalPesoNeto = totalPesoNeto;
  }

  /**
   * Gets the valor peso bruto t.
   *
   * @return the valor peso bruto t
   */
  public BigDecimal getTotalPesoBruto() {
    return totalPesoBruto;
  }

  /**
   * Sets the valor peso bruto t.
   *
   * @param totalPesoBruto the new valor peso bruto t
   */
  public void setTotalPesoBruto(BigDecimal totalPesoBruto) {
    this.totalPesoBruto = totalPesoBruto;
  }

  /**
   * Gets the valor cajas pallet t.
   *
   * @return the valor cajas pallet t
   */
  public BigDecimal getTotalCantidadPallets() {
    return totalCantidadPallets;
  }

  /**
   * Sets the valor cajas pallet t.
   *
   * @param totalCantidadPallets the new valor cajas pallet t
   */
  public void setTotalCantidadPallets(BigDecimal totalCantidadPallets) {
    this.totalCantidadPallets = totalCantidadPallets;
  }

  /**
   * Gets the valor cajas tendido t.
   *
   * @return the valor cajas tendido t
   */
  public BigDecimal getTotalCantidadTendidos() {
    return totalCantidadTendidos;
  }

  /**
   * Sets the valor cajas tendido t.
   *
   * @param totalCantidadTendidos the new valor cajas tendido t
   */
  public void setTotalCantidadTendidos(BigDecimal totalCantidadTendidos) {
    this.totalCantidadTendidos = totalCantidadTendidos;
  }

  /**
   * Gets the valor cajas t.
   *
   * @return the valor cajas t
   */
  public BigDecimal getTotalCantidadCajas() {
    return totalCantidadCajas;
  }

  /**
   * Sets the valor cajas t.
   *
   * @param totalCantidadCajas the new valor cajas t
   */
  public void setTotalCantidadCajas(BigDecimal totalCantidadCajas) {
    this.totalCantidadCajas = totalCantidadCajas;
  }

  /**
   * Gets the menu.
   *
   * @return the menu
   */
  public MenuMB getMenu() {
    return menu;
  }

  /**
   * Sets the menu.
   *
   * @param menu the new menu
   */
  public void setMenu(MenuMB menu) {
    this.menu = menu;
  }

}
