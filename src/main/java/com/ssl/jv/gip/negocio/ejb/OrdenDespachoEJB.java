package com.ssl.jv.gip.negocio.ejb;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import com.ssl.jv.gip.jpa.pojo.Documento;
import com.ssl.jv.gip.negocio.dao.DocumentoDAO;

/**
 * Session Bean implementation class OrdenDespachoEJB
 */
@Stateless
@LocalBean
public class OrdenDespachoEJB implements OrdenDespachoEJBLocal{
	
	private static final Logger LOGGER = Logger.getLogger(MaestrosEJB.class);
	
	@EJB
	DocumentoDAO ordenes;

	@Override
	public List<Documento> consultarOrdenesDeDespacho() {
		try {
			return ordenes.consultarOrdenesDeDespacho();
		} catch (Exception e) {
			LOGGER.error(e + "Error consultando ordenes de despacho");
			return null;
		}
	}

	@Override
	public List<Documento> consultarOrdenesDeDespacho(Documento filtro) {
		try {
			return ordenes.consultarOrdenesDeDespachoPorFiltro(filtro);
		} catch (Exception e) {
			LOGGER.error(e + "Error consultando ordenes de despacho con filtro");
			return null;
		}
	}

	@Override
	public Documento crearOrdenDeDespacho(Documento pEntidad) {
		try {
			return ordenes.add(pEntidad);
		} catch (Exception e) {
			LOGGER.error(e + " Error creando orden de despacho");
			return null;
		}
	}

	@Override
	public Documento actualizarOrdenDeDespacho(Documento pEntidad) {
		try {
			ordenes.update(pEntidad);
			return pEntidad;
		} catch (Exception e) {
			LOGGER.error(e + " Error actualizando orden de despacho");
			return null;
		}
	}

}
