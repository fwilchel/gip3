package com.ssl.jv.gip.web.mb.comercioexterior;

import com.ssl.jv.gip.jpa.pojo.Documento;
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

import com.ssl.jv.gip.jpa.pojo.LogAuditoria;
import com.ssl.jv.gip.jpa.pojo.Moneda;
import com.ssl.jv.gip.jpa.pojo.ProductosXClienteComext;
import com.ssl.jv.gip.jpa.pojo.ProductosXDocumento;
import com.ssl.jv.gip.jpa.pojo.ProductosXDocumentoPK;
import com.ssl.jv.gip.negocio.dto.FiltroDocumentoDTO;
import com.ssl.jv.gip.negocio.ejb.ComercioExteriorEJBLocal;
import com.ssl.jv.gip.negocio.ejb.ComunEJBLocal;
import com.ssl.jv.gip.web.mb.MenuMB;
import com.ssl.jv.gip.web.mb.UtilMB;
import com.ssl.jv.gip.web.mb.util.ConstantesDocumento;
import com.ssl.jv.gip.web.mb.util.ConstantesTipoDocumento;
import com.ssl.jv.gip.web.util.Modo;
import com.ssl.jv.gip.web.util.Utilidad;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * Title: GIP
 * </p>
 * <p>
 * Description: GIP
 * </p>
 * <p>
 * Copyright: Copyright (c) 2014
 * </p>
 * <p>
 * Company: Soft Studio Ltda.
 * </p>
 *
 * @author Diego Poveda
 * @email dpoveda@gmail.com
 * @phone 3192594013
 * @version 1.0
 */
@ManagedBean
@ViewScoped
public class ModificarFPMB extends UtilMB {

  /**
   *
   */
  private static final long serialVersionUID = -4732702749953900246L;
  private final MathContext mathContext = new MathContext(3, RoundingMode.HALF_DOWN);
  @EJB
  private ComercioExteriorEJBLocal comercioExteriorEJB;
  @EJB
  private ComunEJBLocal comunEJB;
  @ManagedProperty(value = "#{menuMB}")
  private MenuMB menu;
  private Modo modo;
  private FiltroDocumentoDTO filtro;
  private List<Documento> listaFP = new ArrayList<>();
  private Documento fp;
  private List<ProductosXDocumento> productosFP;
  private List<ProductosXClienteComext> productosCliente;
  private BigDecimal totalCantidad = new BigDecimal(0.00);
  private BigDecimal totalValorTotal = new BigDecimal(0.00);
  private BigDecimal totalPesoNeto = new BigDecimal(0.00);
  private BigDecimal totalPesoBruto = new BigDecimal(0.00);
  private BigDecimal totalCantidadCajas = new BigDecimal(0.00);
  private BigDecimal totalCantidadTendidos = new BigDecimal(0.00);
  private BigDecimal totalCantidadPallets = new BigDecimal(0.00);
  private BigDecimal totalCostos = new BigDecimal(0.00);
  private BigDecimal totalNegociacion = new BigDecimal(0.00);
  private Map<Long, BigDecimal> ultimosSaldosInventario;

  /**
   * Inits the.
   */
  @PostConstruct
  public void init() {
    this.filtro = new FiltroDocumentoDTO();
    this.modo = Modo.LISTAR;
    this.consultarListaFP();
  }

  public void consultarListaFP() {
    filtro.setTipoDocumento(new Long(ConstantesTipoDocumento.FACTURA_PROFORMA));
    filtro.setEstado(new Long(ConstantesDocumento.AUTORIZADO));
    filtro.setOrdenCampo("consecutivoDocumento DESC");
    this.listaFP = comunEJB.consultarDocumentos(filtro);
  }

  public String verDetalle(Documento selected) {
    this.fp = comercioExteriorEJB.consultarDocumentoComercioExterior(selected.getId());
    this.productosFP = comercioExteriorEJB.consultarProductosDocumentoComercioExterior(this.fp.getId());
    this.recalcularTotalesLista();
    this.modo = Modo.EDITAR;
    this.consultarSaldosInventarioComercioExterior();
    return null;
  }

  public void consultarSaldosInventarioComercioExterior() {
    ultimosSaldosInventario = this.comercioExteriorEJB.consultarUltimosSaldos();
  }

  public void recalcularTotales() {
	this.fp.getDocumentoXNegociacion().setCostoEntrega(this.fp.getDocumentoXNegociacion().getCostoEntrega() != null ? this.fp.getDocumentoXNegociacion().getCostoEntrega() : BigDecimal.ZERO);
	this.fp.getDocumentoXNegociacion().setCostoSeguro(this.fp.getDocumentoXNegociacion().getCostoSeguro() != null ? this.fp.getDocumentoXNegociacion().getCostoSeguro() : BigDecimal.ZERO);
	this.fp.getDocumentoXNegociacion().setCostoFlete(this.fp.getDocumentoXNegociacion().getCostoFlete() != null ? this.fp.getDocumentoXNegociacion().getCostoFlete() : BigDecimal.ZERO);
	this.fp.getDocumentoXNegociacion().setOtrosGastos(this.fp.getDocumentoXNegociacion().getOtrosGastos() != null ? this.fp.getDocumentoXNegociacion().getOtrosGastos() : BigDecimal.ZERO);
    this.totalCostos = this.fp.getDocumentoXNegociacion().getCostoEntrega().add(this.fp.getDocumentoXNegociacion().getCostoSeguro()).add(this.fp.getDocumentoXNegociacion().getCostoFlete());
    this.totalNegociacion = totalValorTotal.add(this.totalCostos);
  }

  public void recalcularTotalesLista() {
    totalValorTotal = new BigDecimal(0);
    totalCantidad = new BigDecimal(0);
    totalPesoNeto = new BigDecimal(0);
    totalPesoBruto = new BigDecimal(0);
    totalCantidadTendidos = new BigDecimal(0);
    totalCantidadPallets = new BigDecimal(0);
    totalCantidadCajas = new BigDecimal(0);
    if (productosFP != null && !productosFP.isEmpty()) {
      for (ProductosXDocumento pxd : productosFP) {
        calcularTotalesPorRegistro(pxd);
        totalValorTotal = totalValorTotal.add(pxd.getValorTotal());
        totalCantidad = totalCantidad.add(pxd.getCantidad1());
        totalPesoNeto = totalPesoNeto.add(pxd.getTotalPesoNetoItem());
        totalPesoBruto = totalPesoBruto.add(pxd.getTotalPesoBrutoItem());
        totalCantidadTendidos = totalCantidadTendidos.add(pxd.getCantidadXEmbalaje());
        totalCantidadPallets = totalCantidadPallets.add(pxd.getCantidadPalletsItem());
        totalCantidadCajas = totalCantidadCajas.add(pxd.getCantidadCajasItem());
      }
      this.recalcularTotales();
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
    pxd.setFechaEntrega(fp.getFechaEsperadaEntrega());
    pxd.setFechaEstimadaEntrega(fp.getFechaEsperadaEntrega());
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
    if (productosFP != null && !productosFP.isEmpty()) {
      for (ProductosXDocumento pxd : productosFP) {
        productosAExcluir.add(pxd.getProductosInventario().getId());
      }
    }
    parametros.put("cliente", fp.getCliente().getId());
    parametros.put("solicitudCafe", fp.getDocumentoXNegociacion().getSolicitudCafe());
    parametros.put("productosAExcluir", productosAExcluir);
    productosCliente = comercioExteriorEJB.consultarProductosActivosXCliente(parametros);
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
      pxdID.setIdDocumento(fp.getId());
      pxdID.setIdProducto(pxc.getProductosInventario().getId());
      pxd.setId(pxdID);
      pxd.setDocumento(fp);
      pxd.setProductosInventario(pxc.getProductosInventario());
      pxd.setInformacion(false);
      pxd.setCalidad(false);
      pxd.setFechaEntrega(fp.getFechaEsperadaEntrega());
      pxd.setFechaEstimadaEntrega(fp.getFechaEsperadaEntrega());
      pxd.setCantidad1(BigDecimal.ONE);
      pxd.setCantidad2(BigDecimal.ZERO);
      pxd.setUnidade(pxc.getProductosInventario().getUnidadVenta());
      pxd.setMoneda(new Moneda(pxc.getIdMoneda()));
      pxd.setValorUnitarioUsd(pxc.getPrecio());
      pxd.setValorUnitatrioMl(BigDecimal.ZERO);
      this.productosFP.add(pxd);
      this.productosCliente.remove(pxc);
      this.recalcularTotalesLista();
      this.addMensajeInfo(":tabPanel:msgsBuscarProductos", Utilidad.stringFormat("Producto {0} agregado.", new String[]{pxc.getProductosInventario().getSku()}));
    }
  }

  public void eliminarProducto(ProductosXDocumento pxd) {
    if (pxd != null) {
      this.productosFP.remove(pxd);
      this.recalcularTotalesLista();
    }
  }

  /**
   * Guardar ajustes pedido.
   */
  public void guardarFP() {
    this.recalcularTotalesLista();
    this.recalcularTotales();
    // solicitud
    fp.setValorTotal(totalValorTotal);
    // auditoria
    LogAuditoria auditoria = new LogAuditoria();
    auditoria.setIdUsuario(menu.getUsuario().getId());
    auditoria.setIdFuncionalidad(menu.getIdOpcionActual());
    try {
      // verificar la fp
      comercioExteriorEJB.modificarFP(fp, productosFP, auditoria);
      this.addMensajeInfo("Se ha actualizado correctamente la FP " + fp.getConsecutivoDocumento());
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

  /**
   * Gets the fp.
   *
   * @return the fp
   */
  public Documento getFp() {
    return fp;
  }

  /**
   * Sets the fp.
   *
   * @param fp the new fp
   */
  public void setFp(Documento fp) {
    this.fp = fp;
  }

  /**
   * @return the productosFP
   */
  public List<ProductosXDocumento> getProductosFP() {
    return productosFP;
  }

  /**
   * @param productosFP the productosFP to set
   */
  public void setProductosFP(List<ProductosXDocumento> productosFP) {
    this.productosFP = productosFP;
  }

  /**
   * @return the productosCliente
   */
  public List<ProductosXClienteComext> getProductosCliente() {
    return productosCliente;
  }

  /**
   * @param productosCliente the productosCliente to set
   */
  public void setProductosCliente(List<ProductosXClienteComext> productosCliente) {
    this.productosCliente = productosCliente;
  }

  /**
   * Gets the lista documentos.
   *
   * @return the lista documentos
   */
  public List<Documento> getListaFP() {
    return listaFP;
  }

  /**
   * Sets the lista documentos.
   *
   * @param listaFP the new lista documentos
   */
  public void setListaFP(List<Documento> listaFP) {
    this.listaFP = listaFP;
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

  /**
   * @return the totalCostos
   */
  public BigDecimal getTotalCostos() {
    return totalCostos;
  }

  /**
   * @param totalCostos the totalCostos to set
   */
  public void setTotalCostos(BigDecimal totalCostos) {
    this.totalCostos = totalCostos;
  }

  /**
   * @return the totalNegociacion
   */
  public BigDecimal getTotalNegociacion() {
    return totalNegociacion;
  }

  /**
   * @param totalNegociacion the totalNegociacion to set
   */
  public void setTotalNegociacion(BigDecimal totalNegociacion) {
    this.totalNegociacion = totalNegociacion;
  }

}
