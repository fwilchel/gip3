package com.ssl.jv.gip.negocio.dao;

import java.util.Date;

import javax.ejb.Local;

import com.ssl.jv.gip.jpa.pojo.FactsCurrencyConversion;

@Local
public interface FactsCurrencyConversionDAOLocal extends IGenericDAO<FactsCurrencyConversion> {

  public FactsCurrencyConversion getTRMDian(Date fecha);
}
