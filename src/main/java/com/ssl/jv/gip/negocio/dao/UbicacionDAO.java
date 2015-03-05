package com.ssl.jv.gip.negocio.dao;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import com.ssl.jv.gip.jpa.pojo.Ubicacion;

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

  public List<Ubicacion> consultarUbicacionesPorUsuario(String idUsuario) {

    List<Ubicacion> lista = new ArrayList<Ubicacion>();

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

}
