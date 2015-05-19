package com.ssl.jv.gip.negocio.ejb;

import java.util.List;

import javax.ejb.Local;

import com.ssl.jv.gip.jpa.pojo.Ciudad;
import com.ssl.jv.gip.jpa.pojo.Funcionalidad;
import com.ssl.jv.gip.jpa.pojo.HistorialContrasena;
import com.ssl.jv.gip.jpa.pojo.LogAuditoria;
import com.ssl.jv.gip.jpa.pojo.Pais;
import com.ssl.jv.gip.jpa.pojo.Parametro;
import com.ssl.jv.gip.jpa.pojo.ProductosInventario;
import com.ssl.jv.gip.jpa.pojo.Rol;
import com.ssl.jv.gip.jpa.pojo.Usuario;
import java.util.Map;
import org.primefaces.model.SortOrder;

@Local
public interface AdministracionEJBLocal {

  public Usuario findUsuarioByEmail(String email);

  public List<Funcionalidad> getMenu(String email);

  public void actualizarUsuario(Usuario u);

  public void crearUsuario(Usuario u);

  public List<Usuario> consultarUsuarios();

  public List<Rol> consultarRoles();

  public List<Pais> consultarPaises();

  public Parametro encontrarParametro(Long id);

  public void enviarEmail(String strEmail, String strContrasena,
      String nombre, String apellido);

  public List<HistorialContrasena> consultarHistorialContrasenaHoy(Usuario u);

  public List<HistorialContrasena> consultarHistorialContrasena(Usuario u);

  public List<HistorialContrasena> consultarHistorialContrasena(String usuarioId);

  public void crearHistorialContrasena(HistorialContrasena hc);

  public void actualizarHistorialContrasena(HistorialContrasena hc);

  public void eliminarHistorialContrasena(HistorialContrasena hc);

  public List<Ciudad> getCiudadesByIdPais(String idPais);

  /**
   *
   * @param parametros
   * @return
   */
  List<LogAuditoria> consultarLogAuditoria(Map<String, Object> parametros);

  /**
   *
   * @param parametros
   * @param first
   * @param pageSize
   * @param sortField
   * @param sortOrder
   * @param count
   * @return
   */
  Object[] consultarLogAuditoria(Map<String, Object> parametros, int first, int pageSize, String sortField, SortOrder sortOrder, boolean count);
}
