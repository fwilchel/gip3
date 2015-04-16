package com.ssl.jv.gip.negocio.dao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.ssl.jv.gip.jpa.pojo.TerminoIncotermXMedioTransporte;

@Stateless
@LocalBean
public class IncotermXMedioTransDAO extends GenericDAO<TerminoIncotermXMedioTransporte> implements IncotermXMedioTransDAOLocal {

  public IncotermXMedioTransDAO() {
    this.persistentClass = TerminoIncotermXMedioTransporte.class;
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<TerminoIncotermXMedioTransporte> consultarTerminoIncotermXMedioTransporte() {
    return (List<TerminoIncotermXMedioTransporte>) em
        .createNamedQuery("TerminoIncotermXMedioTransporte.findAll").getResultList();
  }

}
