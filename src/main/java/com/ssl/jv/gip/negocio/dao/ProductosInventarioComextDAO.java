package com.ssl.jv.gip.negocio.dao;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import com.ssl.jv.gip.jpa.pojo.ProductosInventarioComext;

@Stateless
@LocalBean
public class ProductosInventarioComextDAO extends GenericDAO<ProductosInventarioComext> implements ProductosInventarioComextDAOLocal{

	private static final Logger LOGGER = Logger.getLogger(ProductosInventarioComextDAO.class);
	
	public ProductosInventarioComextDAO(){
		this.persistentClass = ProductosInventarioComext.class;
	}	
	
	@Override
	public ProductosInventarioComext findBySku(String sku) {
		return (ProductosInventarioComext)this.em.createNamedQuery("ProductosInventarioComext.findBySku").setParameter("sku", sku).getSingleResult();
	}
	
}
