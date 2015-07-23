package com.ssl.jv.gip.negocio.dao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import com.ssl.jv.gip.jpa.pojo.LiquidacionDocumento;

@Stateless
@LocalBean
public class LiquidacionDocumentoDAO extends GenericDAO<LiquidacionDocumento> implements LiquidacionDocumentoDAOLocal {

  private static final Logger LOGGER = Logger.getLogger(LiquidacionDocumentoDAO.class);

  public LiquidacionDocumentoDAO() {
    this.persistentClass = LiquidacionDocumento.class;
  }

  public List<LiquidacionDocumento> findAll(){
	  return em.createNamedQuery("LiquidacionDocumento.findAll").getResultList();
  }
  
}
