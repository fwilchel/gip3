package com.ssl.jv.gip.negocio.dao;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import com.ssl.jv.gip.jpa.pojo.AgenciaCarga;

@Stateless
@LocalBean
public class AgenciaCargaDAO extends GenericDAO<AgenciaCarga> implements AgenciaCargaDAOLocal {

  private static final Logger LOGGER = Logger.getLogger(AgenciaCargaDAO.class);

  public AgenciaCargaDAO() {
    this.persistentClass = AgenciaCarga.class;
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<AgenciaCarga> consultarAgenciaCargaPorFiltro(
      AgenciaCarga pFiltro) {
    List<AgenciaCarga> listado = new ArrayList<AgenciaCarga>();
    String query = null;

    try {

      if (pFiltro.getActivo()) {
        query = "select a from AgenciaCarga a WHERE a.activo = true AND a.nombre like :nombre order by a.nombre";
      } else {
        query = "select a from AgenciaCarga a WHERE a.nombre like :nombre order by a.nombre";
      }

      listado = em.createQuery(query).
          setParameter("nombre", PORCENTAJE_LIKE + pFiltro.getNombre() == null ? "" : pFiltro.getNombre() + PORCENTAJE_LIKE).
          getResultList();

    } catch (Exception e) {
      LOGGER.error(e + "********Error consultando agencias de carga");
      return null;
    }
    return listado;
  }

}
