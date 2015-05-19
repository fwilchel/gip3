package com.ssl.jv.gip.negocio.dao;

import java.util.Arrays;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import com.ssl.jv.gip.jpa.pojo.Parametro;

@Stateless
@LocalBean
public class ParametroDAO extends GenericDAO<Parametro> implements
    ParametroDAOLocal {

  private static final Logger LOGGER = Logger.getLogger(PaisDAO.class);

  public ParametroDAO() {
    this.persistentClass = Parametro.class;
  }

  @Override
  public Parametro consultarParametroPorNombre(String nombre) {
    Query query = this.em.createNamedQuery(Parametro.FIND_BY_NOMBRE);
    query.setParameter("nombres", nombre);
    return (Parametro) query.getSingleResult();
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<Parametro> consultarParametroPorNombres(String... nombres) {
    Query query = this.em.createNamedQuery(Parametro.FIND_BY_NOMBRE);
    query.setParameter("nombres", Arrays.asList(nombres));
    return query.getResultList();
  }

}
