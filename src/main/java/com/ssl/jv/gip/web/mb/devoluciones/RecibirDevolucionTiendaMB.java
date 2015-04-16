package com.ssl.jv.gip.web.mb.devoluciones;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;

import com.ssl.jv.gip.jpa.pojo.Ubicacion;
import com.ssl.jv.gip.negocio.dto.DocumentoRecibirDevolucionDTO;
import com.ssl.jv.gip.negocio.dto.UbicacionRecibirDevolucionDTO;
import com.ssl.jv.gip.negocio.ejb.DevolucionesEJBLocal;
import com.ssl.jv.gip.negocio.ejb.MaestrosEJBLocal;
import com.ssl.jv.gip.web.mb.AplicacionMB;
import com.ssl.jv.gip.web.mb.MenuMB;
import com.ssl.jv.gip.web.mb.UtilMB;

import java.util.ArrayList;

@ManagedBean(name = "recibirDevolucionTiendaMB")
@ViewScoped
public class RecibirDevolucionTiendaMB extends UtilMB {

  /**
   *
   */
  private static final long serialVersionUID = -2780795923623719268L;

  private static final Logger LOGGER = Logger.getLogger(RecibirDevolucionTiendaMB.class);

  @EJB
  private DevolucionesEJBLocal devolucionesEJB;

  @EJB
  private MaestrosEJBLocal maestrosEJB;

  @ManagedProperty(value = "#{aplicacionMB}")
  private AplicacionMB appMB;

  @ManagedProperty(value = "#{menuMB}")
  private MenuMB menu;

  private final Integer language = AplicacionMB.SPANISH;

  private List<UbicacionRecibirDevolucionDTO> listaBodegas = new ArrayList<UbicacionRecibirDevolucionDTO>();

  private List<DocumentoRecibirDevolucionDTO> listadDocumentos = new ArrayList<DocumentoRecibirDevolucionDTO>();

  private String ubicacion;

  private Long documento;

  @PostConstruct
  public void init() {
    LOGGER.debug("Metodo: <<init>>");
    obtenerlistaBodegas();
  }

  public void obtenerlistaBodegas() {
    listaBodegas = devolucionesEJB.consultarUbicacionesRecibirDevolucionPorUsuario(menu.getUsuario().getId());
    listadDocumentos.clear();
    if (listaBodegas != null && listaBodegas.size() > 0) {
      listadDocumentos = devolucionesEJB.consultarDocumentosRecibirDevolucion(listaBodegas.get(0).getBodegaAbastecedora());
    }
  }

  public void obtenerListadoDocumentos() {
    if (listadDocumentos != null) {
      listadDocumentos.clear();
    }
    if (ubicacion != null) {
      listadDocumentos = devolucionesEJB.consultarDocumentosRecibirDevolucion(ubicacion);
    }
  }

  public List<UbicacionRecibirDevolucionDTO> getListaBodegas() {
    return listaBodegas;
  }

  public void setListaBodegas(List<UbicacionRecibirDevolucionDTO> listaBodegas) {
    this.listaBodegas = listaBodegas;
  }

  public List<DocumentoRecibirDevolucionDTO> getListadDocumentos() {
    return listadDocumentos;
  }

  public void setListadDocumentos(
      List<DocumentoRecibirDevolucionDTO> listadDocumentos) {
    this.listadDocumentos = listadDocumentos;
  }

  public String getUbicacion() {
    return ubicacion;
  }

  public void setUbicacion(String ubicacion) {
    this.ubicacion = ubicacion;
  }

  public Long getDocumento() {
    return documento;
  }

  public void setDocumento(Long documento) {
    this.documento = documento;
  }

  public AplicacionMB getAppMB() {
    return appMB;
  }

  public void setAppMB(AplicacionMB appMB) {
    this.appMB = appMB;
  }

  public MenuMB getMenu() {
    return menu;
  }

  public void setMenu(MenuMB menu) {
    this.menu = menu;
  }

}
