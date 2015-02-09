package com.ssl.jv.gip.negocio.dao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import com.ssl.jv.gip.jpa.pojo.PuntoVenta;
//import com.ssl.jv.gip.negocio.dto.ClienteFiltroVO;

@Stateless
@LocalBean
public class PuntoVentaDAO extends GenericDAO<PuntoVenta> implements PuntoVentaDAOLocal {

	private static final Logger LOGGER = Logger.getLogger(PuntoVentaDAO.class);

	/**
	 * Default constructor.
	 */
	public PuntoVentaDAO() {
		this.persistentClass = PuntoVenta.class;
	}

	/*@Override
	public List<Cliente> consultarPorFiltro(ClienteFiltroVO filtroVO) {
	
		return null;
	}*/
	/*
	@SuppressWarnings("unchecked")
	@Override
	public List<PuntoVenta> consultarActivosPorUsuario(String idUsuario) {
		Query query = em
				.createNamedQuery(PuntoVenta.CLIENTE_ACTIVO_FIND_BY_USUARIO);
		query.setParameter("idUsuario", idUsuario);
		return query.getResultList();
	}*/

	@Override
	public void guardarPuntoVenta(PuntoVenta puntoVenta) {
		
		System.out.println("pv_id"+puntoVenta.getId());
		System.out.println("pv_nombre"+puntoVenta.getNombre());
		System.out.println("pv_direccion"+puntoVenta.getDireccion());
		System.out.println("pv_cliente"+puntoVenta.getCliente().getId());
		System.out.println("pv_ciudad"+puntoVenta.getCiudade().getId());
		System.out.println("pv_despacho"+puntoVenta.getCodDespachoSap());
		System.out.println("pv_barras"+puntoVenta.getCodigoBarras());
		
		puntoVenta = em.merge(puntoVenta);
		em.flush();		
	}
	
	@Override
	public List<PuntoVenta> findAll(){
		return (List<PuntoVenta>)em.createQuery("SELECT pv FROM PuntoVenta pv JOIN FETCH pv.ciudade ciu JOIN FETCH ciu.pais p").getResultList();
	}

	
	
}
