package com.ssl.jv.gip.negocio.ejb;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import com.ssl.jv.gip.jpa.pojo.Documento;
import com.ssl.jv.gip.jpa.pojo.MovimientosInventario;
import com.ssl.jv.gip.jpa.pojo.Unidad;
import com.ssl.jv.gip.negocio.dao.DocumentoDAOLocal;
import com.ssl.jv.gip.negocio.dao.MovimientoInventarioDAOLocal;
import com.ssl.jv.gip.negocio.dao.ProductosXDocumentoDAOLocal;
import com.ssl.jv.gip.negocio.dto.ProductoDespacharMercanciaDTO;

/**
 * Session Bean implementation class OrdenDespachoEJB
 * 
 * @author Daniel Cortes
 * @version 1.0
 * @email danicorc@gmail.com
 * 
 */
@Stateless
@LocalBean
public class DespachoMercanciaEJB implements DespachoMercanciaEJBLocal{
	
	private static final Logger LOGGER = Logger.getLogger(MaestrosEJB.class);
	
	@EJB
	DocumentoDAOLocal documentoDao;
	
	@EJB
	ProductosXDocumentoDAOLocal productoXDocumentoDao;
	
	@EJB
	MovimientoInventarioDAOLocal movimientoDao;

	@Override
	public List<ProductoDespacharMercanciaDTO> consultarProductoPorDocumento(String idDocumento, String idCliente){
		try {
			return productoXDocumentoDao.consultarProductoVentaDirecta(idDocumento);
		} catch (Exception e) {
			LOGGER.error(e + "Error consultando productos de ventas directas");
			return null;
		}
	}

	@Override
	public List<Documento> consultarVentasDirectas() {
		try {
			return documentoDao.consultarDocumentosDespacharMercancia("");
		} catch (Exception e) {
			LOGGER.error(e + "Error consultando ordenes de despacho");
			return null;
		}
	}

	@Override
	public List<Documento> consultarVentasDirectas(String filtro) {
		try {
			return documentoDao.consultarDocumentosDespacharMercancia(filtro);
		} catch (Exception e) {
			LOGGER.error(e + "Error consultando ordenes de despacho con filtro");
			return null;
		}
	}
	
	@Override
	public MovimientosInventario crearMovimientoInventario(MovimientosInventario movimiento) {
		try {
			return movimientoDao.add(movimiento);
		} catch (Exception e) {
			LOGGER.error(e + "Error creando la salida de inventario de despacho");
			return null;
		}
	}

	@Override
	public Unidad consultarUnidad(String unidadVenta) {
		// TODO Auto-generated method stub
		return null;
	}
}
