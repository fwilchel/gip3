package com.ssl.jv.gip.negocio.dao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import com.ssl.jv.gip.jpa.pojo.Funcionalidad;

@Stateless
@LocalBean
public class FuncionalidadDAO extends GenericDAO<Funcionalidad> implements FuncionalidadDAOLocal {

  private static final Logger LOGGER = Logger.getLogger(FuncionalidadDAO.class);

  public FuncionalidadDAO() {
    this.persistentClass = Funcionalidad.class;
  }

  public List<Funcionalidad> getMenu(String login) {
    List<Funcionalidad> opciones = null;
    try {
      String query = "SELECT DISTINCT f FROM Usuario u INNER JOIN u.role r INNER JOIN r.permisos p INNER JOIN p.funcionalidade f WHERE u.email = :email ORDER BY f.ordenar";
      opciones = em.createQuery(query).setParameter("email", login)
          .getResultList();
    } catch (Exception e) {
      LOGGER.error(e);
    }
    return opciones;
  }

}
