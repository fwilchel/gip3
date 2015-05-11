package com.ssl.jv.gip.negocio.dao;

import java.util.Date;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import com.ssl.jv.gip.jpa.pojo.FactsCurrencyConversion;

@Stateless
@LocalBean
public class FactsCurrencyConversionDAO extends GenericDAO<FactsCurrencyConversion> implements FactsCurrencyConversionDAOLocal {

  private static final Logger LOGGER = Logger.getLogger(FactsCurrencyConversionDAO.class);

  public FactsCurrencyConversionDAO() {
    this.persistentClass = FactsCurrencyConversion.class;
  }

  public FactsCurrencyConversion getTRMDian(Date fecha) {
    return (FactsCurrencyConversion) em.createNamedQuery("FactsCurrencyConversion.findByFecha").setParameter("fecha", fecha).setMaxResults(1).getSingleResult();
  }
}
