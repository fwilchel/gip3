package com.ssl.jv.gip.negocio.dao;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import com.ssl.jv.gip.jpa.pojo.LiquidacionCostoLogistico;

@Stateless
@LocalBean
public class LiquidacionCostoLogisticoDAO extends GenericDAO<LiquidacionCostoLogistico> implements LiquidacionCostoLogisticoDAOLocal{

	private static final Logger LOGGER = Logger.getLogger(LiquidacionCostoLogisticoDAO.class);
	
	public LiquidacionCostoLogisticoDAO(){
		this.persistentClass = LiquidacionCostoLogistico.class;
	}	
	
}
