package com.ssl.jv.gip.negocio.dao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

import org.apache.log4j.Logger;

import com.ssl.jv.gip.jpa.pojo.Ciudad;

@Stateless
@LocalBean
public class CiudadDAO extends GenericDAO<Ciudad> implements CiudadDAOLocal {

  private static final Logger LOGGER = Logger.getLogger(CiudadDAO.class);

  public CiudadDAO() {
    this.persistentClass = Ciudad.class;

  }

  @Override
  public List<Ciudad> findByPais(String idPais) {
    TypedQuery<Ciudad> query = em.createNamedQuery("Ciudad.findByPais", Ciudad.class);
    query.setParameter("idPais", idPais);
    return query.getResultList();
  }

}
