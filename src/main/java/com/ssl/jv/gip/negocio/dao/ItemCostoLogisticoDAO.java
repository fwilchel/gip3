package com.ssl.jv.gip.negocio.dao;

import java.util.List;

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
	
	public List<ItemCostoLogistico> findAll() {
		List list = em.createNamedQuery("ItemCostoLogistico.findAll")
				.getResultList();
		return list;
	}	

}
