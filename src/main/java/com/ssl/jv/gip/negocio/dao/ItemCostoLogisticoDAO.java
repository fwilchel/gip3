package com.ssl.jv.gip.negocio.dao;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import com.ssl.jv.gip.jpa.pojo.ItemCostoLogistico;

@Stateless
@LocalBean
public class ItemCostoLogisticoDAO extends GenericDAO<ItemCostoLogistico> implements ItemCostoLogisticoDAOLocal{

	private static final Logger LOGGER = Logger.getLogger(ItemCostoLogisticoDAO.class);
	
	public ItemCostoLogisticoDAO(){
		this.persistentClass = ItemCostoLogistico.class;
	}	

}
