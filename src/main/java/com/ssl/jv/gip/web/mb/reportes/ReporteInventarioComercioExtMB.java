package com.ssl.jv.gip.web.mb.reportes;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;

import org.apache.log4j.Logger;
import org.hibernate.exception.ConstraintViolationException;

import com.ssl.jv.gip.jpa.pojo.MovimientosInventarioComext;
import com.ssl.jv.gip.negocio.ejb.ReportesEJBLocal;
import com.ssl.jv.gip.web.mb.AplicacionMB;
import com.ssl.jv.gip.web.mb.MenuMB;
import com.ssl.jv.gip.web.mb.UtilMB;
import com.ssl.jv.gip.web.util.Modo;

/**
 * @author Juan Jose Buzon
 *
 */
@ManagedBean(name = "reporteInventarioComercioExtMB")
@SessionScoped
public class ReporteInventarioComercioExtMB extends UtilMB {

  /**
   *
   */
  private static final long serialVersionUID = 8439835485814158653L;

  private static final Logger LOGGER = Logger
      .getLogger(ReporteInventarioComercioExtMB.class);

  @ManagedProperty(value = "#{menuMB}")
  private MenuMB menu;
  private String idUsuario;

  private Integer language = AplicacionMB.SPANISH;
  private Modo modo;

  private String sku;
  private boolean ultimoSaldo;
  private List<MovimientosInventarioComext> movimientosInventarioComexts;

  @EJB
  private ReportesEJBLocal reporteEJB;

  @PostConstruct
  public void init() {
    idUsuario = menu.getUsuario().getId();
    movimientosInventarioComexts = new ArrayList<MovimientosInventarioComext>();
    ultimoSaldo = true;
  }

  public void consultarMovimientosInventarioComExt(ActionEvent actionEvent) {
    try {
      if (sku == null || "".equals(sku)) {
        sku = "%";
      } else {
        sku = "%" + sku + "%";
      }

      movimientosInventarioComexts = reporteEJB
          .consultarMovimientosInventarioComextsPorSKU(sku,
              ultimoSaldo);
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

  public String getSku() {
    return sku;
  }

  public void setSku(String sku) {
    this.sku = sku;
  }

  public boolean isUltimoSaldo() {
    return ultimoSaldo;
  }

  public void setUltimoSaldo(boolean ultimoSaldo) {
    this.ultimoSaldo = ultimoSaldo;
  }

  public List<MovimientosInventarioComext> getMovimientosInventarioComexts() {
    return movimientosInventarioComexts;
  }

  public void setMovimientosInventarioComexts(
      List<MovimientosInventarioComext> movimientosInventarioComexts) {
    this.movimientosInventarioComexts = movimientosInventarioComexts;
  }

}
