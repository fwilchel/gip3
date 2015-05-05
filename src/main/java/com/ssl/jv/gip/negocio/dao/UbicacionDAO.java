package com.ssl.jv.gip.negocio.dao;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import com.ssl.jv.gip.jpa.pojo.Ubicacion;
import com.ssl.jv.gip.negocio.dto.UbicacionRecibirDevolucionDTO;

@Stateless
@LocalBean
public class UbicacionDAO extends GenericDAO<Ubicacion> implements UbicacionDAOLocal {

  private static final Logger LOGGER = Logger.getLogger(UbicacionDAO.class);

  public UbicacionDAO() {
    this.persistentClass = Ubicacion.class;
  }

  public List<Ubicacion> consultarUbicacionPorFiltro(Ubicacion pFiltro) {

    List<Ubicacion> listado = new ArrayList<Ubicacion>();

    try {
      listado = em.createQuery("from Ubicacion WHERE esTienda = :esTienda order by nombre").
          setParameter("esTienda", pFiltro.getEsTienda()).
          getResultList();
    } catch (Exception e) {

    }
    return listado;
  }

  @Override
  public List<Ubicacion> consultarUbicacionesPorUsuario(String idUsuario) {
    List<Ubicacion> lista = new ArrayList<>();
    try {
      String sql = "SELECT  ubicaciones.id, 	"
          + "ubicaciones.nombre "
          + "FROM usuariosXgeografias,ubicaciones "
          + "WHERE ubicaciones.id=usuariosXgeografias.id_geografia "
          + "AND usuariosXgeografias.id_usuario= '" + idUsuario + "' "
          + "ORDER BY UPPER (ubicaciones.nombre) ASC";
      List<Object[]> listado = em.createNativeQuery(sql).getResultList();
      if (listado != null) {
        for (Object[] objs : listado) {
          Ubicacion dto = new Ubicacion();
          dto.setId(objs[0] != null ? Long.parseLong(objs[0].toString()) : null);
          dto.setNombre(objs[1] != null ? objs[1].toString() : null);
          lista.add(dto);
        }
      }
    } catch (Exception e) {
    }
    return lista;
  }

  public List<Ubicacion> findAll() {
    return this.em.createNamedQuery("Ubicacion.findAll").getResultList();
  }

  @Override
  public List<Ubicacion> consultarPorIds(List<Long> ids) {
    Query query = em
        .createNamedQuery("Ubicacion.findByIDs", Ubicacion.class);
    query.setParameter("ids", ids);
    return query.getResultList();

  }

  @Override
  public List<Ubicacion> consultarTodasOrdenadas() {
    Query query = em
        .createNamedQuery("Ubicacion.findAll", Ubicacion.class);
    return query.getResultList();
  }

  @Override
  public List<Ubicacion> consultarUbicacionesQueSonTiendaPorUsuario(String usuario) {
    LOGGER.debug("Metodo: <<consultarDocumentosReporteCintaTestigoMagnetica>>");
    String sql = Ubicacion.BUSCAR_UBICACIONES_QUE_SON_TIENDA_POR_USUARIO;
    Query query = em.createNativeQuery(sql);
    query.setParameter("usuario", usuario);
    List<Ubicacion> ubicaciones = null;
    List<Object[]> listado = query.getResultList();
    if (listado != null) {
      ubicaciones = new ArrayList<>();
      for (Object[] objs : listado) {
        Ubicacion ubicacion = new Ubicacion();
        ubicacion.setId(objs[0] != null ? Long.parseLong(objs[0].toString()) : null);
        ubicacion.setNombre(objs[1] != null ? objs[1].toString() : null);
        Ubicacion bodegaAbastecedora = new Ubicacion();
        bodegaAbastecedora.setId(objs[2] != null ? Long.parseLong(objs[2].toString()) : null);
        bodegaAbastecedora.setNombre(objs[3] != null ? objs[3].toString() : null);
        ubicacion.setUbicacione(bodegaAbastecedora);
        ubicaciones.add(ubicacion);
      }
    }
    return ubicaciones;
  }

  /* (non-Javadoc)
   * @see com.ssl.jv.gip.negocio.dao.UbicacionDAOLocal#consultarUbicacionesRecibirDevolucionPorUsuario(java.lang.String)
   */
  @Override
  public List<UbicacionRecibirDevolucionDTO> consultarUbicacionesRecibirDevolucionPorUsuario(String usuario) {
    LOGGER.debug("Metodo: <<consultarUbicacionesRecibirDevolucionPorUsuario>>");
    String sql = Ubicacion.BUSCAR_UBICACIONES_RECIBIR_DEVOLUCIONES_TIENDA;

    sql = sql.replaceAll(":usuario", usuario);

    List<Object[]> listado = em.createNativeQuery(sql)
        .getResultList();

    List<UbicacionRecibirDevolucionDTO> listadoFinal = new ArrayList<UbicacionRecibirDevolucionDTO>();

    for (Object[] objs : listado) {
      UbicacionRecibirDevolucionDTO ubicacion = new UbicacionRecibirDevolucionDTO();
      ubicacion.setIdGeografia(objs[0] != null ? objs[0].toString() : null);
      ubicacion.setIdUsuario(objs[1] != null ? objs[1].toString() : null);
      ubicacion.setTipoGeografia(objs[2] != null ? objs[2].toString() : null);
      ubicacion.setIdRegion(objs[3] != null ? objs[3].toString() : null);
      ubicacion.setIdPais(objs[4] != null ? objs[4].toString() : null);
      ubicacion.setNombrePais(objs[5] != null ? objs[5].toString() : null);
      ubicacion.setIdEmpresa(objs[6] != null ? objs[6].toString() : null);
      ubicacion.setNombreEmpresa(objs[7] != null ? objs[7].toString() : null);
      ubicacion.setBodegaAbastecedora(objs[8] != null ? objs[8].toString() : null);
      ubicacion.setNombre(objs[9] != null ? objs[9].toString() : null);
      listadoFinal.add(ubicacion);
    }

    return listadoFinal;
  }

}
