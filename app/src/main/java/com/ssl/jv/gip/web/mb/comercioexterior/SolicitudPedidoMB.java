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
import com.ssl.jv.gip.jpa.pojo.Moneda;
import com.ssl.jv.gip.jpa.pojo.ProductosXClienteComext;
import com.ssl.jv.gip.jpa.pojo.ProductosXDocumento;
import com.ssl.jv.gip.negocio.dto.DocumentoIncontermDTO;
import com.ssl.jv.gip.negocio.dto.ProductoPorClienteComExtDTO;
import com.ssl.jv.gip.negocio.ejb.ComercioExteriorEJBLocal;
import com.ssl.jv.gip.negocio.ejb.ComunEJBLocal;
import com.ssl.jv.gip.web.mb.MenuMB;
import com.ssl.jv.gip.web.mb.UtilMB;
import com.ssl.jv.gip.web.util.Modo;

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
  @EJB
  private ComunEJBLocal comunEJB;
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
  private BigDecimal totalPrecio = new BigDecimal(0.00);

  @Deprecated
  private List<ProductoPorClienteComExtDTO> productosXClienteComercioExteriorDTO = new ArrayList<>();

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
    this.productosXDocumento = comunEJB.consultarProductosXDocumento(this.sp.getIdDocumento());
    this.recalcularTotales();
    this.modo = Modo.EDITAR;
    return null;
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
    totalPrecio = new BigDecimal(0);
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
        totalPrecio = totalPrecio.add(pxd.getValorUnitarioUsd());
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
    pxd.setValorTotal(cantidad.multiply(precio));
    if (cantidadXEmbalaje == null || cantidadXEmbalaje.compareTo(BigDecimal.ZERO) == 0) {
      pxd.setTotalPesoNetoItem(BigDecimal.ZERO);
      pxd.setTotalPesoBrutoItem(BigDecimal.ZERO);
      pxd.setCantidadXEmbalaje(BigDecimal.ZERO);
      pxd.setCantidadCajasItem(BigDecimal.ZERO);
      pxd.setCantidadPalletsItem(BigDecimal.ZERO);
    } else {
      pxd.setTotalPesoNetoItem(pesoNetoEmbalaje.divide(cantidadXEmbalaje, mathContext).multiply(cantidad));
      pxd.setTotalPesoBrutoItem(pesoBrutoEmbalaje.divide(cantidadXEmbalaje, mathContext).multiply(cantidad));
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
    List<Long> productosSeleccionados = new ArrayList<>();
    if (productosXDocumento != null && !productosXDocumento.isEmpty()) {
      for (ProductosXDocumento pxd : productosXDocumento) {
        productosSeleccionados.add(pxd.getProductosInventario().getId());
      }
    }
    productosXCliente = comercioExteriorEJB.consultarProductosActivosXCliente(sp.getClientesId(), productosSeleccionados);
  }

  /**
   * Este metodo debe agregar el producto seleccionado a la solicitud pero debe realizar todas las validaciones necesarias
   *
   * @param pxc
   */
  public void agregarProducto(ProductosXClienteComext pxc) {
    if (pxc != null) {
      ProductosXDocumento pxd = new ProductosXDocumento();
      pxd.setDocumento(new Documento(sp.getIdDocumento()));
      pxd.setProductosInventario(pxc.getProductosInventario());
      pxd.setUnidade(pxc.getProductosInventario().getUnidadVenta());
      pxd.setFechaEntrega(sp.getFechaEntrega());
      pxd.setMoneda(new Moneda(pxc.getIdMoneda()));
      pxd.setInformacion(false);
      pxd.setCalidad(false);
      pxd.setCantidad1(BigDecimal.ZERO);
      pxd.setValorUnitarioUsd(pxc.getPrecio());
      this.calcularTotalesPorRegistro(pxd);
      this.productosXDocumento.add(pxd);
      this.productosXCliente.remove(pxc);
      this.recalcularTotales();
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
    sp.setValorTotalDocumento(totalValorTotal);
    sp.setIdUbicacionOrigen(sp.getIdUbicacionDestino());
    sp.setTotalPesoNeto(totalPesoNeto);
    sp.setTotalPesoBruto(totalPesoBruto);
    sp.setTotalTendidos(totalCantidadTendidos);
    sp.setTotalPallets(totalCantidadPallets);
    sp.setCantidadContenedores20(sp.getCantidadContenedores20());
    sp.setCantidadContenedores40(sp.getCantidadContenedores40());
    sp.setLugarIncoterm(sp.getLugarIncoterm());
    comercioExteriorEJB.guardarSolicitudPedido(sp, productosXClienteComercioExteriorDTO);
    this.addMensajeInfo("Se almaceno la solicitud de pedido exitosamente");
    this.init();
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
   * Gets the lista solicitud pedido.
   *
   * @return the lista solicitud pedido
   */
  public List<ProductoPorClienteComExtDTO> getProductosXClienteComercioExteriorDTO() {
    return productosXClienteComercioExteriorDTO;
  }

  /**
   * Sets the lista solicitud pedido.
   *
   * @param productosXClienteComercioExteriorDTO the new lista solicitud pedido
   */
  public void setProductosXClienteComercioExteriorDTO(List<ProductoPorClienteComExtDTO> productosXClienteComercioExteriorDTO) {
    this.productosXClienteComercioExteriorDTO = productosXClienteComercioExteriorDTO;
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
   * Gets the dbl total precio.
   *
   * @return the dbl total precio
   */
  public BigDecimal getTotalPrecio() {
    return totalPrecio;
  }

  /**
   * Sets the dbl total precio.
   *
   * @param totalPrecio the new dbl total precio
   */
  public void setTotalPrecio(BigDecimal totalPrecio) {
    this.totalPrecio = totalPrecio;
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
