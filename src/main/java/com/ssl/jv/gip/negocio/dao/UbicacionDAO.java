package com.ssl.jv.gip.negocio.dao;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import com.ssl.jv.gip.jpa.pojo.Ubicacion;

@Stateless
@LocalBean
public class UbicacionDAO extends GenericDAO<Ubicacion> implements UbicacionDAOLocal{
	
	private static final Logger LOGGER = Logger.getLogger(UbicacionDAO.class);
	
	public UbicacionDAO(){
		this.persistentClass = Ubicacion.class;
	}

	public List<Ubicacion> consultarUbicacionPorFiltro(Ubicacion pFiltro){
		
		List<Ubicacion> listado = new ArrayList<Ubicacion>();
		
		try{
			listado = em.createQuery("from Ubicacion WHERE esTienda = :esTienda order by nombre").
					setParameter("esTienda", pFiltro.getEsTienda()).
					getResultList();
		} catch(Exception e){
			
		}
		return listado;
	}
	
}
