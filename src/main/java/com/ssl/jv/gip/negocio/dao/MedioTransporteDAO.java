package com.ssl.jv.gip.negocio.dao;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.ssl.jv.gip.jpa.pojo.MedioTransporte;

@Stateless
@LocalBean
public class MedioTransporteDAO extends GenericDAO<MedioTransporte> implements MedioTransporteDAOLocal {

  public MedioTransporteDAO() {
    this.persistentClass = MedioTransporte.class;
  }

}
