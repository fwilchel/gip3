package com.ssl.jv.gip.negocio.dao;

import javax.ejb.Local;

import org.primefaces.model.SortOrder;

import com.ssl.jv.gip.jpa.pojo.ProductosInventario;

@Local
public interface ProductoInventarioDAOLocal extends IGenericDAO<ProductosInventario>{

	public Object[] consultar(ProductosInventario pi, int first, int pageSize, String sortField, SortOrder sortOrder, boolean count);

}
