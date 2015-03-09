package com.ssl.jv.gip.negocio.dao;

import java.util.List;

import javax.ejb.Local;

import com.ssl.jv.gip.jpa.pojo.Ubicacion;
import com.ssl.jv.gip.negocio.dto.UbicacionRecibirDevolucionDTO;

/**
 * The Interface UbicacionDAOLocal.
 */
@Local
public interface UbicacionDAOLocal extends IGenericDAO<Ubicacion> {

  /**
   * Consultar ubicacion por filtro.
   *
   * @param pFiltro the filtro
   * @return the list
   */
  public List<Ubicacion> consultarUbicacionPorFiltro(Ubicacion pFiltro);

  /**
   * Consultar ubicaciones por usuario.
   *
   * @param idUsuario the id usuario
   * @return the list
   */
  public List<Ubicacion> consultarUbicacionesPorUsuario(String idUsuario);

  /**
   * Consultar por ids.
   *
   * @param ids the ids
   * @return the list
   */
  public List<Ubicacion> consultarPorIds(List<Long> ids);

  /**
   * Consultar todas ordenadas.
   *
   * @return the list
   */
  public List<Ubicacion> consultarTodasOrdenadas();

  /**
   * Metodo que consulta las ubicaciones que son tienda, para un usuario.
   *
   * @author Diego Poveda - Soft Studio Ltda.
   * @param usuario the usuario
   * @return the list
   * @email dpoveda@gmail.com
   * @phone 3192594013
   */
  List<Ubicacion> consultarUbicacionesQueSonTiendaPorUsuario(String usuario);
  
  /**
   * Consultar ubicaciones recibir devolucion por usuario.
   *
   * @param usuario the usuario
   * @return the list
   */
  public List<UbicacionRecibirDevolucionDTO> consultarUbicacionesRecibirDevolucionPorUsuario(String usuario);

}
