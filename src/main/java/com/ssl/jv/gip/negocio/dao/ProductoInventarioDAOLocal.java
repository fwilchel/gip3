package com.ssl.jv.gip.negocio.dao;

import java.util.List;

import javax.ejb.Local;

import org.primefaces.model.SortOrder;

import com.ssl.jv.gip.jpa.pojo.ProductosInventario;
import com.ssl.jv.gip.negocio.dto.ProductosInventarioFiltroDTO;

@Local
public interface ProductoInventarioDAOLocal extends
		IGenericDAO<ProductosInventario> {

	public Object[] consultar(ProductosInventario pi, int first, int pageSize,
			String sortField, SortOrder sortOrder, boolean count);

	public List<ProductosInventario> consultarTodos();

	public List<ProductosInventario> consultarActivos();

	public List<ProductosInventario> consultarPorCategoriaAndSKUAndNombreAndEstado(
			ProductosInventarioFiltroDTO filtro);

}
