package com.ssl.jv.gip.negocio.dao;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.ssl.jv.gip.jpa.pojo.Ubicacion;

@Stateless
@LocalBean
public class UbicacionDAO extends GenericDAO<Ubicacion>{

	public List<Ubicacion> consultarUbicacionPorFiltro(Ubicacion pFiltro){
		
		List<Ubicacion> listado = new ArrayList<Ubicacion>();
		
		try{
			listado = em.createQuery("from Ubicacion WHERE esTienda = :esTienda AND nombre like :nombre order by nombre").
					setParameter("esTienda", pFiltro.getEsTienda()).
					setParameter("nombre", PORCENTAJE_LIKE + pFiltro.getNombre() == null ? "" : pFiltro.getNombre() + PORCENTAJE_LIKE).
					getResultList();
		} catch(Exception e){
			
		}
		return listado;
	}
	
}
