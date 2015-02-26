package com.ssl.jv.gip.negocio.ejb;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import com.ssl.jv.gip.jpa.pojo.Documento;
import com.ssl.jv.gip.jpa.pojo.ProductosXDocumento;
import com.ssl.jv.gip.negocio.dao.DocumentoDAOLocal;
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

	@Override
	public List<ProductoDespacharMercanciaDTO> consultarProductoPorDocumento(String idDocumento, String idCliente){
		List<ProductosXDocumento> resultado = productoXDocumentoDao.consultarPorDocumento(Long.parseLong(idDocumento));
		List<ProductoDespacharMercanciaDTO> productos= new ArrayList<ProductoDespacharMercanciaDTO>();
		for (ProductosXDocumento producto : resultado) {
			ProductoDespacharMercanciaDTO p= new ProductoDespacharMercanciaDTO();
			p.setId(producto.getId().getIdProducto()+"");
			p.setIdDocumento(producto.getId().getIdDocumento()+"");
			p.setNombre("");
			p.setCantidadDespachada(producto.getCantidad1());
			productos.add(p);
		}
		return productos ;
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
}
