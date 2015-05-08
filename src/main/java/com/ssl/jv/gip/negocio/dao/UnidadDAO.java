package com.ssl.jv.gip.negocio.dao;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import com.ssl.jv.gip.jpa.pojo.Unidad;

@Stateless
@LocalBean
public class UnidadDAO extends GenericDAO<Unidad> implements UnidadDAOLocal {

  private static final Logger LOGGER = Logger.getLogger(UnidadDAO.class);

  public UnidadDAO() {
    this.persistentClass = Unidad.class;
  }

}
