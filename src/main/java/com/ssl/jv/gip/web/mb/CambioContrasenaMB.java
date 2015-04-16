package com.ssl.jv.gip.web.mb;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import org.apache.log4j.Logger;

import com.ssl.jv.gip.jpa.pojo.HistorialContrasena;
import com.ssl.jv.gip.jpa.pojo.HistorialContrasenaPK;
import com.ssl.jv.gip.negocio.ejb.AdministracionEJBLocal;
import com.ssl.jv.gip.web.util.Utilidad;

@ManagedBean(name = "cambioContrasenaMB")
@SessionScoped
public class CambioContrasenaMB extends UtilMB {

  /**
   *
   */
  private static final Logger LOGGER = Logger
      .getLogger(CambioContrasenaMB.class);
  private Integer language = AplicacionMB.SPANISH;
  private static final int REPE = 5;

  private static final long serialVersionUID = -1947610603374558443L;

  @EJB
  private AdministracionEJBLocal admonEjb;

  @ManagedProperty(value = "#{loginMB}")
  private LoginMB loginMB;

  @ManagedProperty(value = "#{menuMB}")
  private MenuMB menuMB;

  private int intMinC = 9;
  private int intMaxC = 16;
  private String strNueva1;
  private String strNueva2;

  public int getIntMinC() {
    return intMinC;
  }

  public void setIntMinC(int intMinC) {
    this.intMinC = intMinC;
  }

  public int getIntMaxC() {
    return intMaxC;
  }

  public void setIntMaxC(int intMaxC) {
    this.intMaxC = intMaxC;
  }

  public String getStrNueva1() {
    return strNueva1;
  }

  public void setStrNueva1(String strNueva1) {
    this.strNueva1 = strNueva1;
  }

  public String getStrNueva2() {
    return strNueva2;
  }

  public MenuMB getMenuMB() {
    return menuMB;
  }

  public void setMenuMB(MenuMB menuMB) {
    this.menuMB = menuMB;
  }

  public void setStrNueva2(String strNueva2) {
    this.strNueva2 = strNueva2;
  }

  public LoginMB getLoginMB() {
    return loginMB;
  }

  public void setLoginMB(LoginMB loginMB) {
    this.loginMB = loginMB;
  }

  public String cambiarCaducidadContrasenaUsuario() {
    boolean opc = false;
    String strMsn = "";
    String remoteAddr = this.getRemoteAddress();

    if (strNueva1.equals(strNueva2)) {
      char clave;
      byte contLetra = 0, contNumero = 0, contSimbolo = 0;
      String pas2 = "";
      String pas1 = strNueva1;

      for (int i = 0; i < pas1.length(); i++) {
        clave = pas1.charAt(i);
        pas2 = String.valueOf(clave);

        Pattern p = Pattern.compile("\\d");
        Matcher m = p.matcher(pas2);
        boolean b = m.matches();
        if (b == true) {
          contNumero++;
        }

        p = Pattern.compile("[a-zA-Z]");
        m = p.matcher(pas2);
        b = m.matches();
        if (b == true) {
          contLetra++;
        }

        p = Pattern.compile("\\p{Punct}");
        m = p.matcher(pas2);
        b = m.matches();
        if (b == true) {
          contSimbolo++;

        }
      }

      if (contLetra == contNumero && contSimbolo >= 2 && contLetra != 0
          && contNumero != 0) {
        strMsn = "contrasenaComplejidadAlta";
        opc = true;
      } else if (contNumero >= 2 && contLetra >= 2 && contSimbolo >= 1) {
        strMsn = "contrasenaComplejidadBuena";
        opc = true;
      } else if (contNumero >= 1 && contLetra >= 1 && contSimbolo == 0) {
        strMsn = "contrasenaComplejidadMedia";
        opc = true;
      } else if (contNumero == 0 && contSimbolo == 0) {
        strMsn = "contrasenaComplejidadBajaNumero";
        opc = false;
      } else if (contLetra == 0 && contSimbolo == 0) {
        strMsn = "contrasenaComplejidadBajaLetra";
        opc = false;
      } else if (contLetra == 0 && contNumero == 0 && contSimbolo >= 2) {
        strMsn = "contrasenaComplejidadBajaNumeroLetra";
        opc = false;
      }

      if (opc == true) {
        String encrp = Utilidad.cifrar(strNueva2);
        this.loginMB.getUsuario().setContrasena(encrp);
        List<HistorialContrasena> lista = this.admonEjb.consultarHistorialContrasena(this.loginMB.getUsuario());
        List<HistorialContrasena> lista2 = this.admonEjb.consultarHistorialContrasena(this.loginMB.getUsuario().getId());

        if (lista.isEmpty()) {

          HistorialContrasena objHistorialContrasena = new HistorialContrasena();
          HistorialContrasenaPK pk = new HistorialContrasenaPK();
          pk.setContrasena(encrp);
          pk.setUsuarioId(this.loginMB.getUsuario().getId());
          objHistorialContrasena.setPk(pk);

          Calendar c = Calendar.getInstance();
          c.add(Calendar.DATE, 30);
          objHistorialContrasena.setFechaCaducidad(new Timestamp(c.getTimeInMillis()));

          this.loginMB.getUsuario().setContrasena(encrp);
          this.loginMB.getUsuario().setIntentos(0L);
          this.admonEjb.actualizarUsuario(this.loginMB.getUsuario());

          if (lista2.size() < REPE) {
            this.admonEjb.crearHistorialContrasena(objHistorialContrasena);
          } else if (lista2.size() >= REPE) {
            this.admonEjb.eliminarHistorialContrasena(objHistorialContrasena);
            this.admonEjb.crearHistorialContrasena(objHistorialContrasena);
          }

          LOGGER.info("Client IP address=|"
              + remoteAddr
              + " |Identificacion=|"
              + this.loginMB.getUsuario().getEmail()
              + " |Modificacion registro|Cambio de contraseña se completó satisfactoriamente");

          menuMB.setMenu(admonEjb.getMenu(this.loginMB.getUsuario().getEmail()));
          menuMB.setUsuario(this.loginMB.getUsuario());
          menuMB.setLanguage(language);
          return "introduccion";

        } else {

          this.addMensajeError(AplicacionMB.getMessage("contrasenaYaUtilizada", language));

          LOGGER.info("Client IP address=|"
              + remoteAddr
              + " |Identificacion=|"
              + this.loginMB.getUsuario().getEmail()
              + " |Cambio contraseña|Historial ya cuenta con esta contraseña");
          return null;
        }

      } else {

        LOGGER.info("Client IP address=|" + remoteAddr
            + " |Identificacion=|" + this.loginMB.getUsuario().getEmail()
            + " |Cambio contraseña|" + " |Cambio contraseña|" + AplicacionMB.getMessage(strMsn, language));

        this.addMensajeError(AplicacionMB.getMessage(strMsn, language));
        return null;

      }

    } else {
      strMsn = "contrasenasDiferentes";
      LOGGER.info("Client IP address=|" + remoteAddr
          + " |Identificacion=|" + this.loginMB.getUsuario().getEmail()
          + " |Cambio contraseña|" + "Contraseña a cambiar no corresponde");

    }
    this.addMensajeError(AplicacionMB.getMessage(strMsn, language));
    return null;
  }

}
