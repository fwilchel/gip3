package com.ssl.jv.gip.negocio.dao;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import com.ssl.jv.gip.jpa.pojo.MetodoPago;

@Stateless
@LocalBean
public class MetodoPagoDAO extends GenericDAO<MetodoPago> implements MetodoPagoDAOLocal {

  private static final Logger LOGGER = Logger.getLogger(CiudadDAO.class);

  public MetodoPagoDAO() {
    this.persistentClass = MetodoPago.class;
  }

}
