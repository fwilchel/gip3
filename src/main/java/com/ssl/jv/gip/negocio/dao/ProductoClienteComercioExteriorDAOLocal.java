package com.ssl.jv.gip.negocio.dao;

import java.math.BigDecimal;
import java.util.List;

import javax.ejb.Local;

import com.ssl.jv.gip.jpa.pojo.ProductosXClienteComExtFiltroVO;
import com.ssl.jv.gip.jpa.pojo.ProductosXClienteComext;
import com.ssl.jv.gip.jpa.pojo.ProductosXClienteComextPK;
import com.ssl.jv.gip.negocio.dto.DocumentoIncontermDTO;
import com.ssl.jv.gip.negocio.dto.ListaEmpaqueDTO;
import com.ssl.jv.gip.negocio.dto.ProductoAsignarLoteOICDTO;
import com.ssl.jv.gip.negocio.dto.ProductoDTO;
import com.ssl.jv.gip.negocio.dto.ProductoGenerarFacturaPFDTO;
import com.ssl.jv.gip.negocio.dto.ProductoLoteAsignarLoteOICDTO;
import com.ssl.jv.gip.negocio.dto.ProductoPorClienteComExtDTO;


@Local
public interface ProductoClienteComercioExteriorDAOLocal extends IGenericDAO<ProductosXClienteComext>{
	
	public void crearFacturaProforma(DocumentoIncontermDTO documento,ProductoPorClienteComExtDTO producto);
	
	public void modificarFacturaProforma(DocumentoIncontermDTO documento,ProductoPorClienteComExtDTO producto);
	
	public void eliminarFacturaProforma(DocumentoIncontermDTO documento,ProductoPorClienteComExtDTO producto);
	
	public BigDecimal consultarUltimoSaldoProducto(Long idProducto);

	public Boolean consultarProductoCliente(Long idDocumento, Long idProducto);
	
	public void crearSaldo(DocumentoIncontermDTO documento,ProductoPorClienteComExtDTO producto);
	
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
	
	public ProductosXClienteComext consultarPorClienteSku(Long idCliente, String sku);
	
	public List<ProductoPorClienteComExtDTO> consultarListaProductosClienteFacturaProforma(
			Long idDocumento, Long idCliente);
}
