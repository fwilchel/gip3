package com.ssl.jv.gip.negocio.dao;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import com.ssl.jv.gip.jpa.pojo.ProductosInventario;

@Stateless
@LocalBean
public class ProductoInventarioDAO extends GenericDAO<ProductosInventario> implements ProductoInventarioDAOLocal{

	private static final Logger LOGGER = Logger.getLogger(ProductoInventarioDAO.class);
	
	public ProductoInventarioDAO(){
		this.persistentClass = ProductosInventario.class;
	}	
	
}
