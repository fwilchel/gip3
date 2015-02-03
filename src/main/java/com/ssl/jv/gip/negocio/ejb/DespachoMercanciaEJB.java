package com.ssl.jv.gip.negocio.ejb;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import com.ssl.jv.gip.jpa.pojo.Documento;
import com.ssl.jv.gip.jpa.pojo.ProductosXDocumento;
import com.ssl.jv.gip.negocio.dao.DocumentoDAO;
import com.ssl.jv.gip.negocio.dao.DocumentoDAOLocal;
import com.ssl.jv.gip.negocio.dao.ProductosXDocumentoDAO;
import com.ssl.jv.gip.negocio.dao.ProductosXDocumentoDAOLocal;
import com.ssl.jv.gip.negocio.dto.ProductoDTO;

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
	public List<ProductoDTO> consultarProductoPorDocumento(String idDocumento, String idCliente){
		List<ProductosXDocumento> resultado = productoXDocumentoDao.consultarPorDocumento(Long.parseLong(idDocumento));
		List<ProductoDTO> productos= new ArrayList<ProductoDTO>();
		for (ProductosXDocumento producto : resultado) {
			ProductoDTO p= new ProductoDTO();
			p.setId(producto.getId().getIdProducto()+"");
			p.setIdDocumento(producto.getId().getIdDocumento()+"");
			p.setNombre("");
			p.setCantidad(producto.getCantidad1());
			p.setPesoBruto(producto.getTotalPesoBrutoItem());
			p.setPesoNeto(producto.getTotalPesoNetoItem());
			p.setCantidadCajas(producto.getCantidadCajasItem());
			p.setCantidadPorEmbalaje(producto.getCantidadXEmbalaje());
			p.setCantidadPallets(producto.getCantidadPalletsItem());
			p.setValorUnitarioUsd(producto.getValorUnitarioUsd());
			p.setValorTotal(producto.getValorTotal());
			productos.add(p);
		}
		return productos ;
	}

	@Override
	public List<Documento> consultarVentasDirectas() {
		try {
			return documentoDao.consultarVentasDirectas("");
		} catch (Exception e) {
			LOGGER.error(e + "Error consultando ordenes de despacho");
			return null;
		}
	}

	@Override
	public List<Documento> consultarVentasDirectas(Documento filtro) {
		try {
			return documentoDao.consultarVentasDirectas(filtro.getConsecutivoDocumento());
		} catch (Exception e) {
			LOGGER.error(e + "Error consultando ordenes de despacho con filtro");
			return null;
		}
	}
}
