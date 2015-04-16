package com.ssl.jv.gip.negocio.dao;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.ssl.jv.gip.jpa.pojo.MedioTransporte;
import com.ssl.jv.gip.jpa.pojo.ModalidadEmbarque;

@Stateless
@LocalBean
public class ModalidadEmbarqueDAO extends GenericDAO<ModalidadEmbarque> implements ModalidadEmbarqueDAOLocal {

  public ModalidadEmbarqueDAO() {
    this.persistentClass = ModalidadEmbarque.class;
  }

}
