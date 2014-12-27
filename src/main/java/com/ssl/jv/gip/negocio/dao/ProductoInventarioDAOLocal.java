package com.ssl.jv.gip.negocio.dao;

import java.util.List;

import javax.ejb.Local;

import com.ssl.jv.gip.jpa.pojo.ProductosInventario;

@Local
public interface ProductoInventarioDAOLocal extends IGenericDAO<ProductosInventario>{

	public List<ProductosInventario> consultar(ProductosInventario pi);

}
