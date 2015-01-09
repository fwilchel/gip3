package com.ssl.jv.gip.negocio.dao;

import java.util.List;

import javax.ejb.Local;

import com.ssl.jv.gip.jpa.pojo.ProductosXClienteComExtFiltroVO;
import com.ssl.jv.gip.jpa.pojo.ProductosXClienteComext;
import com.ssl.jv.gip.negocio.dto.ListaEmpaqueDTO;
import com.ssl.jv.gip.jpa.pojo.ProductosXClienteComext;
import com.ssl.jv.gip.negocio.dto.ProductoAsignarLoteOICDTO;
import com.ssl.jv.gip.negocio.dto.ProductoGenerarFacturaPFDTO;
import com.ssl.jv.gip.negocio.dto.ProductoLoteAsignarLoteOICDTO;
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

	List<ProductoDTO> consultarProductoPorDocumento(ListaEmpaqueDTO listaEmpaqueDTO);

	void generarListaEmpaque(ProductoDTO productoDTO);

	public ProductosXClienteComext consultarPorPK(ProductosXClienteComextPK pk);
	
	public List<ProductoGenerarFacturaPFDTO> consultarProductoPorDocumentoGenerarFacturaProforma(Long idDocumento,
			Long idCliente);
	
	public List<ProductoAsignarLoteOICDTO> consultarProductoPorDocumentoAsignarLotesOIC(Long idDocumento,
			Long idCliente);

	public List<ProductoLoteAsignarLoteOICDTO> consultarProductoPorDocumentoLoteAsignarLotesOIC(Long idDocumento,
			Long idCliente);
}
