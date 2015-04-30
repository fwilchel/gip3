package com.ssl.jv.gip.web.mb.comercioexterior;

import java.util.Date;
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
import com.ssl.jv.gip.jpa.pojo.DocumentoXLotesoic;
import com.ssl.jv.gip.negocio.ejb.ComercioExteriorEJBLocal;
import com.ssl.jv.gip.web.mb.AplicacionMB;
import com.ssl.jv.gip.web.mb.MenuMB;
import com.ssl.jv.gip.web.mb.UtilMB;
import com.ssl.jv.gip.web.util.Modo;

/**
 * @author Juan Jose Buzon
 *
 */
@ManagedBean(name = "asignarDatosTLMB")
@SessionScoped
public class AsignarDatosTLMB extends UtilMB {

  /**
   *
   */
  private static final long serialVersionUID = 3716294263516995148L;

  private static final Logger LOGGER = Logger
      .getLogger(AsignarDatosTLMB.class);

  @ManagedProperty(value = "#{menuMB}")
  private MenuMB menu;
  private String idUsuario;

  private Integer language = AplicacionMB.SPANISH;
  private Modo modo;

  @EJB
  private ComercioExteriorEJBLocal comercioExteriorEJBLocal;

  private Date fechaActual;
  private String consecutivoDocumento;

  private List<Documento> listaEmpaques;
  private Documento seleccionado;
  private List<DocumentoXLotesoic> documentosXLotesOIC;

  @PostConstruct
  public void init() {
    fechaActual = new Date();
  }

  public void consultarListaEmpaques(ActionEvent actionEvent) {
    try {
      if (consecutivoDocumento != null && consecutivoDocumento.equals("")) {
        consecutivoDocumento = "%";
      } else {
        consecutivoDocumento = "%" + consecutivoDocumento + "%";
      }
      listaEmpaques = comercioExteriorEJBLocal
          .consultarListaEmpaquesParaAsignarDatosTL(consecutivoDocumento);
    } catch (Exception e) {
      LOGGER.error(e);
      Exception unrollException = (Exception) this.unrollException(e,
          ConstraintViolationException.class);
      if (unrollException != null) {
        this.addMensajeError(unrollException.getLocalizedMessage());
      } else {
        unrollException = (Exception) this.unrollException(e,
            RuntimeException.class);
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
      documentosXLotesOIC = comercioExteriorEJBLocal
          .consultarDocumentosXLotesOICParaAsignarDatosTL(seleccionado
              .getObservacionDocumento());
      outcome = "consultar_factura_exportacion";
    } catch (Exception e) {
      LOGGER.error(e);
      this.addMensajeError(e);
    }
    return outcome;
  }

  public String asignarDatosLote() {
    String outcome = null;
    try {
      comercioExteriorEJBLocal.asignarDatosTL(seleccionado,
          documentosXLotesOIC);
      listaEmpaques.remove(seleccionado);
      outcome = "listado_FX_AsignarDatosTL";
    } catch (Exception e) {
      LOGGER.error(e);
      Exception unrollException = (Exception) this.unrollException(e,
          ConstraintViolationException.class);
      if (unrollException != null) {
        this.addMensajeError(unrollException.getLocalizedMessage());
      } else {
        unrollException = (Exception) this.unrollException(e,
            RuntimeException.class);
        if (unrollException != null) {
          this.addMensajeError(unrollException.getLocalizedMessage());
        } else {
          this.addMensajeError(e);
        }
      }
    }
    return outcome;
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

  public Date getFechaActual() {
    return fechaActual;
  }

  public void setFechaActual(Date fechaActual) {
    this.fechaActual = fechaActual;
  }

  public String getConsecutivoDocumento() {
    return consecutivoDocumento;
  }

  public void setConsecutivoDocumento(String consecutivoDocumento) {
    this.consecutivoDocumento = consecutivoDocumento;
  }

  public List<Documento> getListaEmpaques() {
    return listaEmpaques;
  }

  public void setListaEmpaques(List<Documento> listaEmpaques) {
    this.listaEmpaques = listaEmpaques;
  }

  public Documento getSeleccionado() {
    return seleccionado;
  }

  public void setSeleccionado(Documento seleccionado) {
    this.seleccionado = seleccionado;
  }

  public List<DocumentoXLotesoic> getDocumentosXLotesOIC() {
    return documentosXLotesOIC;
  }

  public void setDocumentosXLotesOIC(
      List<DocumentoXLotesoic> documentosXLotesOIC) {
    this.documentosXLotesOIC = documentosXLotesOIC;
  }

}
