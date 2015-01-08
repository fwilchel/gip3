package com.ssl.jv.gip.negocio.dao;

import java.util.List;

import javax.ejb.Local;

import com.ssl.jv.gip.jpa.pojo.ProductosXClienteComExtFiltroVO;
import com.ssl.jv.gip.jpa.pojo.ProductosXClienteComext;
import com.ssl.jv.gip.negocio.dto.ProductoGenerarFacturaPFDTO;
import com.ssl.jv.gip.negocio.dto.ProductoPorClienteComExtDTO;
import com.ssl.jv.gip.jpa.pojo.ProductosXClienteComextPK;
import com.ssl.jv.gip.negocio.dto.ProductoDTO;


@Local
public interface ProductoClienteComercioExteriorDAOLocal {

	public List<ProductoPorClienteComExtDTO> consultarListaSolicitudesPedido(Long idDocumento, Long idCliente);
	
	public List<ProductoPorClienteComExtDTO> consultarListaProductosPorClienteCE(Long idCliente, String idsProductos, Boolean solicitudCafe);
	
	public List<ProductosXClienteComext> consultarPorFiltro(
			ProductosXClienteComExtFiltroVO filtroVO);

	public List<ProductosXClienteComext> consultarTodos();

	public List<ProductoDTO> consultarProductoPorDocumento(String idDocumento,
			String idCliente);

	public ProductosXClienteComext consultarPorPK(ProductosXClienteComextPK pk);
	
	public List<ProductoGenerarFacturaPFDTO> consultarProductoPorDocumentoGenerarFacturaProforma(Long idDocumento,
			Long idCliente);

}
