package com.ssl.jv.gip.negocio.dao;

import java.util.List;

import javax.ejb.Local;

import com.ssl.jv.gip.jpa.pojo.TerminoIncotermXMedioTransporte;

@Local
public interface IncotermXMedioTransDAOLocal extends IGenericDAO<TerminoIncotermXMedioTransporte> {

  public List<TerminoIncotermXMedioTransporte> consultarTerminoIncotermXMedioTransporte();

}
