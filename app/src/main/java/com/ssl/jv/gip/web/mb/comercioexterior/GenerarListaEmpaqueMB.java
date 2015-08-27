package com.ssl.jv.gip.web.mb.comercioexterior;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;

import org.apache.log4j.Logger;
import org.hibernate.exception.ConstraintViolationException;

import com.ssl.jv.gip.jpa.pojo.Documento;
import com.ssl.jv.gip.jpa.pojo.DocumentoXNegociacion;
import com.ssl.jv.gip.jpa.pojo.DocumentoXNegociacionPK;
import com.ssl.jv.gip.negocio.dto.ClienteDTO;
import com.ssl.jv.gip.negocio.dto.ListaEmpaqueDTO;
import com.ssl.jv.gip.negocio.dto.ProductoDTO;
import com.ssl.jv.gip.negocio.ejb.ComercioExteriorEJBLocal;
import com.ssl.jv.gip.web.mb.AplicacionMB;
import com.ssl.jv.gip.web.mb.MenuMB;
import com.ssl.jv.gip.web.mb.UtilMB;
import com.ssl.jv.gip.web.util.Modo;

/**
 * @author Juan Jose Buzon
 *
 */
@ManagedBean(name = "generarListaEmpaqueMB")
@SessionScoped
public class GenerarListaEmpaqueMB extends UtilMB {

  /**
   *
   */
  private static final long serialVersionUID = 3128811568955130122L;
  private static final Logger LOGGER = Logger.getLogger(ModificarListaEmpaqueMB.class);
  @ManagedProperty(value = "#{menuMB}")
  private MenuMB menu;
  private String idUsuario;
  private Integer language = AplicacionMB.SPANISH;
  private Modo modo;
  @EJB
  private ComercioExteriorEJBLocal comercioExteriorEJBLocal;
  private String consecutivoDocumento;
  private List<Documento> facturasProforma;
  private Documento seleccionado;
  private List<ProductoDTO> productoDTOs;
  private BigDecimal totalCantidad;
  private BigDecimal totalCantidadPorEmbalaje;
  private BigDecimal totalCajas;
  private BigDecimal totalPallets;
  private BigDecimal totalPesoNeto;
  private BigDecimal totalPesoBruto;
  private Documento listaEmpaqueGenerada;

  @PostConstruct
  public void init() {
    totalCantidad = BigDecimal.ZERO;
    totalPallets = BigDecimal.ZERO;
    totalCajas = BigDecimal.ZERO;
    totalPesoNeto = BigDecimal.ZERO;
    totalPesoBruto = BigDecimal.ZERO;
  }

  public void consultarFacturasProforma(ActionEvent actionEvent) {
    try {
      if (consecutivoDocumento != null && consecutivoDocumento.equals("")) {
        consecutivoDocumento = "%";
      } else {
        consecutivoDocumento = "%" + consecutivoDocumento + "%";
      }
      facturasProforma = comercioExteriorEJBLocal.consultarFacturasProformasParaGenerarListaEmpaque(consecutivoDocumento);
    } catch (Exception e) {
      LOGGER.error(e);
      Exception unrollException = (Exception) this.unrollException(e, ConstraintViolationException.class);
      if (unrollException != null) {
        this.addMensajeError(unrollException.getLocalizedMessage());
      } else {
        unrollException = (Exception) this.unrollException(e, RuntimeException.class);
        if (unrollException != null) {
          this.addMensajeError(unrollException.getLocalizedMessage());
        } else {
          this.addMensajeError(e);
        }
      }
    }
  }

  public String editar() {
    String outcome = null;
    try {
      this.modo = Modo.EDITAR;
      ListaEmpaqueDTO listaEmpaqueDTO = new ListaEmpaqueDTO();
      listaEmpaqueDTO.setIdDocumento(seleccionado.getId().toString());
      ClienteDTO cliente = new ClienteDTO();
      cliente.setId(seleccionado.getCliente().getId().toString());
      listaEmpaqueDTO.setCliente(cliente);
      listaEmpaqueDTO.setSolicitudCafe(Boolean.TRUE);
      List<DocumentoXNegociacion> documentoXNegociacions = seleccionado.getDocumentoXNegociacions();
      if (documentoXNegociacions != null && !documentoXNegociacions.isEmpty()) {
        listaEmpaqueDTO.setSolicitudCafe(documentoXNegociacions.get(0).getSolicitudCafe());
      }
      productoDTOs = comercioExteriorEJBLocal.consultarProductoPorDocumento(listaEmpaqueDTO);
      calcularTotales();
      if (documentoXNegociacions != null && !documentoXNegociacions.isEmpty()) {
        documentoXNegociacions.get(0).setCantidadEstibas((int) Math.ceil(totalPallets.doubleValue()));
        documentoXNegociacions.get(0).setPesoBrutoEstibas(documentoXNegociacions.get(0).getCantidadEstibas() * 20);
      }
      outcome = "generar_lista_empaque";
    } catch (Exception e) {
      LOGGER.error(e);
      this.addMensajeError(e);
    }
    return outcome;
  }

  public void calcularTotales() {
    totalCantidad = BigDecimal.ZERO;
    totalCantidadPorEmbalaje = BigDecimal.ZERO;
    totalCajas = BigDecimal.ZERO;
    totalPallets = BigDecimal.ZERO;
    totalPesoNeto = BigDecimal.ZERO;
    totalPesoBruto = BigDecimal.ZERO;
    for (ProductoDTO productoDTO : productoDTOs) {
      totalCantidad = totalCantidad.add(productoDTO.getCantidad());
      totalCantidadPorEmbalaje = totalCantidadPorEmbalaje.add(productoDTO.getCantidadPorEmbalaje());
      totalCajas = totalCajas.add(productoDTO.getCantidadCajas());
      totalPallets = totalPallets.add(productoDTO.getCantidadPallets());
      totalPesoNeto = totalPesoNeto.add(productoDTO.getPesoNeto());
      totalPesoBruto = totalPesoBruto.add(productoDTO.getPesoBruto());
    }
  }

  public String generarListaEmpaque() {
    String outcome = null;
    try {
      DocumentoXNegociacion documentoXNegociacion = getDocumentoXNegociacion();
      listaEmpaqueGenerada = comercioExteriorEJBLocal.generarListaEmpaque(seleccionado, documentoXNegociacion, productoDTOs);
      facturasProforma.remove(seleccionado);
      consecutivoDocumento = null;
      this.modo = Modo.CREAR;
      outcome = "listado_LE";
      addMensajeInfo(formatearCadenaConParametros("VentasLEExito_Crear", language, listaEmpaqueGenerada.getId().toString(), listaEmpaqueGenerada.getConsecutivoDocumento()));
    } catch (Exception e) {
      LOGGER.error(e);
      Exception unrollException = (Exception) this.unrollException(e, ConstraintViolationException.class);
      if (unrollException != null) {
        this.addMensajeError(unrollException.getLocalizedMessage());
      } else {
        unrollException = (Exception) this.unrollException(e, RuntimeException.class);
        if (unrollException != null) {
          this.addMensajeError(unrollException.getLocalizedMessage());
        } else {
          this.addMensajeError(AplicacionMB.getMessage("PedidoErrorPaginaBoton", language));
        }
      }
    }
    return outcome;
  }

  private DocumentoXNegociacion getDocumentoXNegociacion() {
    DocumentoXNegociacion documentoXNegociacion = new DocumentoXNegociacion();
    DocumentoXNegociacion docXNegociacion = null;
    List<DocumentoXNegociacion> documentoXNegociacions = seleccionado.getDocumentoXNegociacions();
    if (documentoXNegociacions != null && !documentoXNegociacions.isEmpty()) {
      docXNegociacion = documentoXNegociacions.get(0);
    }
    if (docXNegociacion != null) {
      DocumentoXNegociacionPK pk = new DocumentoXNegociacionPK();
      pk.setIdTerminoIncoterm(docXNegociacion.getTerminoIncoterm().getId());
      documentoXNegociacion.setPk(pk);
      documentoXNegociacion.setTerminoIncoterm(docXNegociacion.getTerminoIncoterm());
      documentoXNegociacion.setCostoEntrega(docXNegociacion.getCostoEntrega());
      documentoXNegociacion.setCostoFlete(docXNegociacion.getCostoFlete());
      documentoXNegociacion.setCostoSeguro(docXNegociacion.getCostoSeguro());
      documentoXNegociacion.setOtrosGastos(docXNegociacion.getOtrosGastos());
      documentoXNegociacion.setCantidadContenedoresDe20(docXNegociacion.getCantidadContenedoresDe20());
      documentoXNegociacion.setCantidadContenedoresDe40(docXNegociacion.getCantidadContenedoresDe40());
      documentoXNegociacion.setSolicitudCafe(docXNegociacion.getSolicitudCafe());
      documentoXNegociacion.setCantidadDiasVigencia(docXNegociacion.getCantidadDiasVigencia());
      documentoXNegociacion.setCantidadEstibas(docXNegociacion.getCantidadEstibas());
      documentoXNegociacion.setPesoBrutoEstibas(docXNegociacion.getPesoBrutoEstibas());
      documentoXNegociacion.setLugarIncoterm(docXNegociacion.getLugarIncoterm());
      documentoXNegociacion.setObservacionesMarcacion2(docXNegociacion.getObservacionesMarcacion2());
      documentoXNegociacion.setDescripcion(docXNegociacion.getDescripcion());
    }
    documentoXNegociacion.setTotalPesoBruto(totalPesoBruto);
    documentoXNegociacion.setTotalPesoNeto(totalPesoNeto);
    documentoXNegociacion.setTotalTendidos(totalCajas);
    documentoXNegociacion.setTotalPallets(totalPallets);
    return documentoXNegociacion;
  }

  public boolean isCreacion() {
    if (this.modo != null && this.modo.equals(Modo.CREAR)) {
      return true;
    } else {
      return false;
    }
  }

  public MenuMB getMenu() {
    return menu;
  }

  public void setMenu(MenuMB menu) {
    this.menu = menu;
  }

  public String getIdUsuario() {
    return idUsuario;
  }

  public void setIdUsuario(String idUsuario) {
    this.idUsuario = idUsuario;
  }

  public Integer getLanguage() {
    return language;
  }

  public void setLanguage(Integer language) {
    this.language = language;
  }

  public Modo getModo() {
    return modo;
  }

  public void setModo(Modo modo) {
    this.modo = modo;
  }

  public String getConsecutivoDocumento() {
    return consecutivoDocumento;
  }

  public void setConsecutivoDocumento(String consecutivoDocumento) {
    this.consecutivoDocumento = consecutivoDocumento;
  }

  public List<Documento> getFacturasProforma() {
    return facturasProforma;
  }

  public void setFacturasProforma(List<Documento> facturasProforma) {
    this.facturasProforma = facturasProforma;
  }

  public Documento getSeleccionado() {
    return seleccionado;
  }

  public void setSeleccionado(Documento seleccionado) {
    this.seleccionado = seleccionado;
  }

  public BigDecimal getTotalCantidad() {
    return totalCantidad;
  }

  public void setTotalCantidad(BigDecimal totalCantidad) {
    this.totalCantidad = totalCantidad;
  }

  public BigDecimal getTotalPallets() {
    return totalPallets;
  }

  public void setTotalPallets(BigDecimal totalPallets) {
    this.totalPallets = totalPallets;
  }

  public BigDecimal getTotalCajas() {
    return totalCajas;
  }

  public void setTotalCajas(BigDecimal totalCajas) {
    this.totalCajas = totalCajas;
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

  public List<ProductoDTO> getProductoDTOs() {
    return productoDTOs;
  }

  public void setProductoDTOs(List<ProductoDTO> productoDTOs) {
    this.productoDTOs = productoDTOs;
  }

  public BigDecimal getTotalCantidadPorEmbalaje() {
    return totalCantidadPorEmbalaje;
  }

  public void setTotalCantidadPorEmbalaje(BigDecimal totalCantidadPorEmbalaje) {
    this.totalCantidadPorEmbalaje = totalCantidadPorEmbalaje;
  }

}
