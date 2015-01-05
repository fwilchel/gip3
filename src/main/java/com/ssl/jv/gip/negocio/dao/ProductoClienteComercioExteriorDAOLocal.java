package com.ssl.jv.gip.negocio.dao;

import java.util.List;

import javax.ejb.Local;

import com.ssl.jv.gip.jpa.pojo.ProductosXClienteComExtFiltroVO;
import com.ssl.jv.gip.jpa.pojo.ProductosXClienteComext;
import com.ssl.jv.gip.jpa.pojo.ProductosXClienteComextPK;
import com.ssl.jv.gip.negocio.dto.ProductoDTO;

@Local
public interface ProductoClienteComercioExteriorDAOLocal {

	List<ProductosXClienteComext> consultarPorFiltro(
			ProductosXClienteComExtFiltroVO filtroVO);

	List<ProductosXClienteComext> consultarTodos();

	List<ProductoDTO> consultarProductoPorDocumento(String idDocumento,
			String idCliente);

	public ProductosXClienteComext consultarPorPK(ProductosXClienteComextPK pk);

}
