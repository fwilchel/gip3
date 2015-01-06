package com.ssl.jv.gip.negocio.dao;

import javax.ejb.Local;

import com.ssl.jv.gip.jpa.pojo.ProductosInventarioComext;

@Local
public interface ProductosInventarioComextDAOLocal extends IGenericDAO<ProductosInventarioComext>{

	public ProductosInventarioComext findBySku(String sku);
	
}
