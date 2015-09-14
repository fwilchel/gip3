package com.ssl.jv.gip.web.mb.comercioexterior;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import com.ssl.jv.gip.jpa.pojo.TerminoIncoterm;
import com.ssl.jv.gip.jpa.pojo.Ubicacion;
import com.ssl.jv.gip.jpa.pojo.Usuario;
import com.ssl.jv.gip.negocio.dto.DocumentoIncontermDTO;
import com.ssl.jv.gip.negocio.dto.ProductoPorClienteComExtDTO;
import com.ssl.jv.gip.negocio.ejb.ComercioExteriorEJB;
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
  @EJB
  private ComercioExteriorEJB comercioEjb;
  @ManagedProperty(value = "#{menuMB}")
  private MenuMB menu;
  private Modo modo;
  private ArrayList<DocumentoIncontermDTO> listaDocumentos = new ArrayList<>();
  private List<TerminoIncoterm> listaTerminoInconterm;
  private List<Ubicacion> listaUbicaciones;
  private List<ProductoPorClienteComExtDTO> listaSolicitudPedido = new ArrayList<>();
  private List<ProductoPorClienteComExtDTO> listaProductosMostrarUno = new ArrayList<>();
  private DocumentoIncontermDTO seleccionado;
  private BigDecimal totalValorT = new BigDecimal(0.00);
  private BigDecimal cantidadValorT = new BigDecimal(0.00);
  private BigDecimal valorPesoNetoT = new BigDecimal(0.00);
  private BigDecimal valorPesoBrutoT = new BigDecimal(0.00);
  private BigDecimal valorCajasPalletT = new BigDecimal(0.00);
  private BigDecimal valorCajasTendidoT = new BigDecimal(0.00);
  private BigDecimal valorCajasT = new BigDecimal(0.00);
  private BigDecimal dblTotalPrecio = new BigDecimal(0.00);

  /**
   * Inits the.
   */
  @PostConstruct
  public void init() {
    this.modo = Modo.LISTAR;
    this.listaTerminoInconterm = null;
    this.listaSolicitudPedido = new ArrayList<>();
    this.listaProductosMostrarUno = new ArrayList<>();
    this.totalValorT = new BigDecimal(0.00);
    this.cantidadValorT = new BigDecimal(0.00);
    this.valorPesoNetoT = new BigDecimal(0.00);
    this.valorPesoBrutoT = new BigDecimal(0.00);
    this.valorCajasPalletT = new BigDecimal(0.00);
    this.valorCajasTendidoT = new BigDecimal(0.00);
    this.valorCajasT = new BigDecimal(0.00);
    this.dblTotalPrecio = new BigDecimal(0.00);
    this.seleccionado = new DocumentoIncontermDTO();
    this.listaDocumentos = (ArrayList<DocumentoIncontermDTO>) comercioEjb.consultarDocumentosSolicitudPedido();
  }

  /**
   * Consultar solicitud pedido.
   *
   * @return the string
   */
  public String consultarSolicitudPedido() {
    // Consultar la lista inconterm poor cliente
    listaTerminoInconterm = comercioEjb.consultarListaIncontermPorCliente(seleccionado.getClientesId());
    listaSolicitudPedido = comercioEjb.consultarListaSolicitudesPedido(seleccionado.getIdDocumento(), seleccionado.getClientesId());
    listaProductosMostrarUno = new ArrayList<>();
    this.refrescarTotales();
    this.modo = Modo.EDITAR;
    return "";
  }

  /**
   * Refrescar totales.
   */
  public void refrescarTotales() {
    BigDecimal ValorTotal = new BigDecimal(0);
    BigDecimal ValorPesoNeto = new BigDecimal(0);
    BigDecimal ValorPesoBruto = new BigDecimal(0);
    BigDecimal ValorCajas = new BigDecimal(0);
    BigDecimal ValorCajasTendido = new BigDecimal(0);
    BigDecimal ValorCajasPallet = new BigDecimal(0);
    totalValorT = new BigDecimal(0);
    cantidadValorT = new BigDecimal(0);
    valorPesoNetoT = new BigDecimal(0);
    valorPesoBrutoT = new BigDecimal(0);
    valorCajasTendidoT = new BigDecimal(0);
    valorCajasPalletT = new BigDecimal(0);
    valorCajasT = new BigDecimal(0);
    dblTotalPrecio = new BigDecimal(0);
    if (listaSolicitudPedido != null && !listaSolicitudPedido.isEmpty()) {
      for (ProductoPorClienteComExtDTO pxc : listaSolicitudPedido) {
        if (pxc.isBlnIncluirBusqueda()) {
          ValorTotal = pxc.getDblCantidad1ProductoxDocumento().multiply(pxc.getDblPrecioUSD());
          if (pxc.getDblCantidadXEmbalajeProductoInventarioCE().compareTo(new BigDecimal(0)) == 1) {
            ValorPesoNeto = (pxc.getDblPesoNetoEmbalajeProductoInventarioCE().divide(pxc.getDblCantidadXEmbalajeProductoInventarioCE(), 2, RoundingMode.HALF_DOWN)).multiply(pxc.getDblCantidad1ProductoxDocumento());
            ValorPesoBruto = (pxc.getDblPesoBrutoEmbalajeProductoInventarioCE().divide(pxc.getDblCantidadXEmbalajeProductoInventarioCE(), 2, RoundingMode.HALF_DOWN)).multiply(pxc.getDblCantidad1ProductoxDocumento());
            ValorCajas = pxc.getDblCantidad1ProductoxDocumento().divide(pxc.getDblCantidadXEmbalajeProductoInventarioCE(), 2, RoundingMode.HALF_DOWN);
          } else {
            ValorPesoNeto = new BigDecimal(0);
            ValorPesoBruto = new BigDecimal(0);
            ValorCajas = new BigDecimal(0);
          }
          if (pxc.getDblCantidadXEmbalajeProductoInventarioCE().compareTo(new BigDecimal(0)) == 1 && pxc.getDblCantCajasXTendidoProductoInventarioCE().compareTo(new BigDecimal(0)) == 1) {
            ValorCajasTendido = (pxc.getDblCantidad1ProductoxDocumento().divide(pxc.getDblCantidadXEmbalajeProductoInventarioCE(), 2, RoundingMode.HALF_DOWN)).divide(pxc.getDblCantCajasXTendidoProductoInventarioCE(), 2, RoundingMode.HALF_DOWN);
          } else {
            ValorCajasTendido = new BigDecimal(0);
          }
          if (pxc.getDblCantidadXEmbalajeProductoInventarioCE().compareTo(new BigDecimal(0)) == 1 && pxc.getDblTotalCajasXPalletProductoInventarioCE().compareTo(new BigDecimal(0)) == 1) {
            BigDecimal cantidad1ProductoxDocumento = pxc.getDblCantidad1ProductoxDocumento().setScale(2);
            BigDecimal cantidadXEmbalajeProductoInventarioCE = pxc.getDblCantidadXEmbalajeProductoInventarioCE().setScale(2);
            BigDecimal totalCajasXPalletProductoInventarioCE = pxc.getDblTotalCajasXPalletProductoInventarioCE().setScale(2);
            ValorCajasPallet = cantidad1ProductoxDocumento.divide(cantidadXEmbalajeProductoInventarioCE, 2, RoundingMode.HALF_DOWN).divide(totalCajasXPalletProductoInventarioCE, 2, RoundingMode.HALF_DOWN);
          } else {
            ValorCajasPallet = new BigDecimal(0);
          }
          totalValorT = totalValorT.add(ValorTotal);
          cantidadValorT = cantidadValorT.add(pxc.getDblCantidad1ProductoxDocumento());
          valorPesoNetoT = valorPesoNetoT.add(ValorPesoNeto);
          valorPesoBrutoT = valorPesoBrutoT.add(ValorPesoBruto);
          valorCajasTendidoT = valorCajasTendidoT.add(ValorCajasTendido);
          valorCajasPalletT = valorCajasPalletT.add(ValorCajasPallet);
          valorCajasT = valorCajasT.add(ValorCajas);
          dblTotalPrecio = dblTotalPrecio.add(pxc.getDblPrecioUSD());
          pxc.setDblValorTotalProductoxDocumento(ValorTotal);
          pxc.setDblTotalPesoNeto(ValorPesoNeto);
          pxc.setDblTotalPesoBruto(ValorPesoBruto);
          pxc.setDblTotalCajas(ValorCajas);
          pxc.setDblTotalCajasTendido(ValorCajasTendido);
          pxc.setDblTotalCajasPallet(ValorCajasPallet);
        }
      }
    }
  }

  /**
   * Consultar lista productosx cli.
   */
  public void consultarListaProductosxCli() {
    listaProductosMostrarUno = new ArrayList<>();
    StringBuilder cadenaIdPrds = new StringBuilder("");
    if (listaSolicitudPedido != null && !listaSolicitudPedido.isEmpty()) {
      int x = 0;
      for (ProductoPorClienteComExtDTO prdxcom : listaSolicitudPedido) {
        if (x == listaSolicitudPedido.size() - 1) {
          cadenaIdPrds.append(prdxcom.getIntIdProductoInventario());
        } else {
          cadenaIdPrds.append(prdxcom.getIntIdProductoInventario() + ",");
        }
        x++;
      }
    }
    listaProductosMostrarUno = comercioEjb.consultarListaProductosPorClienteCE(seleccionado.getClientesId(), cadenaIdPrds.toString(), seleccionado.getSolicitudCafe());
  }

  /**
   * Insertar producto tabla.
   */
  public void insertarProductoTabla() {
    Iterator<ProductoPorClienteComExtDTO> iterator = listaProductosMostrarUno.iterator();
    ProductoPorClienteComExtDTO objProducto = null;
    Boolean yaEsta = false;
    while (iterator.hasNext()) {
      objProducto = iterator.next();
      if (objProducto.isBlnIncluirBusqueda()) {
        for (ProductoPorClienteComExtDTO vProducto : listaSolicitudPedido) {
          if (vProducto.getIntIdProductoInventario().equals(objProducto.getIntIdProductoInventario())) {
            yaEsta = true;
            break;
          }
        }
        if (!yaEsta) {
          listaSolicitudPedido.add(objProducto);
        }
      }
      yaEsta = false;
    }
    objProducto = null;
  }

  /**
   * Guardar ajustes pedido.
   */
  public void guardarAjustesSP() {
    this.refrescarTotales();
    seleccionado.setValorTotalDocumento(totalValorT);
    seleccionado.setIdUbicacionOrigen(seleccionado.getIdUbicacionDestino());
    seleccionado.setTotalPesoNeto(valorPesoNetoT);
    seleccionado.setTotalPesoBruto(valorPesoBrutoT);
    seleccionado.setTotalTendidos(valorCajasTendidoT);
    seleccionado.setTotalPallets(valorCajasPalletT);
    seleccionado.setCantidadContenedores20(seleccionado.getCantidadContenedores20());
    seleccionado.setCantidadContenedores40(seleccionado.getCantidadContenedores40());
    seleccionado.setLugarIncoterm(seleccionado.getLugarIncoterm());
    comercioEjb.guardarSolicitudPedido(seleccionado, listaSolicitudPedido);
    this.addMensajeInfo("Se almaceno la solicitud de pedido exitosamente");
    listaDocumentos = (ArrayList<DocumentoIncontermDTO>) comercioEjb.consultarDocumentosSolicitudPedido();
    for (DocumentoIncontermDTO vdoc : listaDocumentos) {
      if (vdoc.getIdDocumento().equals(seleccionado.getIdDocumento())) {
        seleccionado = vdoc;
        break;
      }
    }
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
   * Gets the seleccionado.
   *
   * @return the seleccionado
   */
  public DocumentoIncontermDTO getSeleccionado() {
    return seleccionado;
  }

  /**
   * Sets the seleccionado.
   *
   * @param seleccionado the new seleccionado
   */
  public void setSeleccionado(DocumentoIncontermDTO seleccionado) {
    this.seleccionado = seleccionado;
  }

  /**
   * Gets the lista documentos.
   *
   * @return the lista documentos
   */
  public ArrayList<DocumentoIncontermDTO> getListaDocumentos() {
    return listaDocumentos;
  }

  /**
   * Sets the lista documentos.
   *
   * @param listaDocumentos the new lista documentos
   */
  public void setListaDocumentos(ArrayList<DocumentoIncontermDTO> listaDocumentos) {
    this.listaDocumentos = listaDocumentos;
  }

  /**
   * Gets the lista termino inconterm.
   *
   * @return the lista termino inconterm
   */
  public List<TerminoIncoterm> getListaTerminoInconterm() {
    return listaTerminoInconterm;
  }

  /**
   * Sets the lista termino inconterm.
   *
   * @param listaTerminoInconterm the new lista termino inconterm
   */
  public void setListaTerminoInconterm(List<TerminoIncoterm> listaTerminoInconterm) {
    this.listaTerminoInconterm = listaTerminoInconterm;
  }

  /**
   * Gets the lista solicitud pedido.
   *
   * @return the lista solicitud pedido
   */
  public List<ProductoPorClienteComExtDTO> getListaSolicitudPedido() {
    return listaSolicitudPedido;
  }

  /**
   * Sets the lista solicitud pedido.
   *
   * @param listaSolicitudPedido the new lista solicitud pedido
   */
  public void setListaSolicitudPedido(List<ProductoPorClienteComExtDTO> listaSolicitudPedido) {
    this.listaSolicitudPedido = listaSolicitudPedido;
  }

  /**
   * Gets the total valor t.
   *
   * @return the total valor t
   */
  public BigDecimal getTotalValorT() {
    return totalValorT;
  }

  /**
   * Sets the total valor t.
   *
   * @param totalValorT the new total valor t
   */
  public void setTotalValorT(BigDecimal totalValorT) {
    this.totalValorT = totalValorT;
  }

  /**
   * Gets the cantidad valor t.
   *
   * @return the cantidad valor t
   */
  public BigDecimal getCantidadValorT() {
    return cantidadValorT;
  }

  /**
   * Sets the cantidad valor t.
   *
   * @param cantidadValorT the new cantidad valor t
   */
  public void setCantidadValorT(BigDecimal cantidadValorT) {
    this.cantidadValorT = cantidadValorT;
  }

  /**
   * Gets the valor peso neto t.
   *
   * @return the valor peso neto t
   */
  public BigDecimal getValorPesoNetoT() {
    return valorPesoNetoT;
  }

  /**
   * Sets the valor peso neto t.
   *
   * @param valorPesoNetoT the new valor peso neto t
   */
  public void setValorPesoNetoT(BigDecimal valorPesoNetoT) {
    this.valorPesoNetoT = valorPesoNetoT;
  }

  /**
   * Gets the valor peso bruto t.
   *
   * @return the valor peso bruto t
   */
  public BigDecimal getValorPesoBrutoT() {
    return valorPesoBrutoT;
  }

  /**
   * Sets the valor peso bruto t.
   *
   * @param valorPesoBrutoT the new valor peso bruto t
   */
  public void setValorPesoBrutoT(BigDecimal valorPesoBrutoT) {
    this.valorPesoBrutoT = valorPesoBrutoT;
  }

  /**
   * Gets the valor cajas pallet t.
   *
   * @return the valor cajas pallet t
   */
  public BigDecimal getValorCajasPalletT() {
    return valorCajasPalletT;
  }

  /**
   * Sets the valor cajas pallet t.
   *
   * @param valorCajasPalletT the new valor cajas pallet t
   */
  public void setValorCajasPalletT(BigDecimal valorCajasPalletT) {
    this.valorCajasPalletT = valorCajasPalletT;
  }

  /**
   * Gets the valor cajas tendido t.
   *
   * @return the valor cajas tendido t
   */
  public BigDecimal getValorCajasTendidoT() {
    return valorCajasTendidoT;
  }

  /**
   * Sets the valor cajas tendido t.
   *
   * @param valorCajasTendidoT the new valor cajas tendido t
   */
  public void setValorCajasTendidoT(BigDecimal valorCajasTendidoT) {
    this.valorCajasTendidoT = valorCajasTendidoT;
  }

  /**
   * Gets the valor cajas t.
   *
   * @return the valor cajas t
   */
  public BigDecimal getValorCajasT() {
    return valorCajasT;
  }

  /**
   * Sets the valor cajas t.
   *
   * @param valorCajasT the new valor cajas t
   */
  public void setValorCajasT(BigDecimal valorCajasT) {
    this.valorCajasT = valorCajasT;
  }

  /**
   * Gets the dbl total precio.
   *
   * @return the dbl total precio
   */
  public BigDecimal getDblTotalPrecio() {
    return dblTotalPrecio;
  }

  /**
   * Sets the dbl total precio.
   *
   * @param dblTotalPrecio the new dbl total precio
   */
  public void setDblTotalPrecio(BigDecimal dblTotalPrecio) {
    this.dblTotalPrecio = dblTotalPrecio;
  }

  /**
   * Gets the lista productos mostrar uno.
   *
   * @return the lista productos mostrar uno
   */
  public List<ProductoPorClienteComExtDTO> getListaProductosMostrarUno() {
    return listaProductosMostrarUno;
  }

  /**
   * Sets the lista productos mostrar uno.
   *
   * @param listaProductosMostrarUno the new lista productos mostrar uno
   */
  public void setListaProductosMostrarUno(List<ProductoPorClienteComExtDTO> listaProductosMostrarUno) {
    this.listaProductosMostrarUno = listaProductosMostrarUno;
  }

  /**
   * Gets the lista ubicaciones.
   *
   * @return the lista ubicaciones
   */
  public List<Ubicacion> getListaUbicaciones() {
    if (listaUbicaciones == null) {
      Usuario objUsuario;

      ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
      HttpSession session = (HttpSession) context.getSession(true);
      objUsuario = (Usuario) session.getAttribute("usuarioLogin");

      listaUbicaciones = comercioEjb.consultarUbicacionesPorUsuario(menu.getUsuario().getId());
    }
    return listaUbicaciones;
  }

  /**
   * Sets the lista ubicaciones.
   *
   * @param listaUbicaciones the new lista ubicaciones
   */
  public void setListaUbicaciones(List<Ubicacion> listaUbicaciones) {
    this.listaUbicaciones = listaUbicaciones;
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
