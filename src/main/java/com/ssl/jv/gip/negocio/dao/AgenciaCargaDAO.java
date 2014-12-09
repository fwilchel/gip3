package com.ssl.jv.gip.negocio.dao;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.ssl.jv.gip.jpa.pojo.AgenciaCarga;
import com.ssl.jv.gip.jpa.pojo.Ubicacion;

@Stateless
@LocalBean
public class AgenciaCargaDAO extends GenericDAO<Ubicacion> implements AgenciaCargaDAOLocal {

	@Override
	public List<AgenciaCarga> consultarAgenciaCargaPorFiltro(
			AgenciaCarga pFiltro) {
List<AgenciaCarga> listado = new ArrayList<AgenciaCarga>();
		
		try{
			listado = em.createQuery("select AgenciaCarga a from AgenciaCarga WHERE activo = true AND nombre like :nombre order by nombre").
					setParameter("nombre", PORCENTAJE_LIKE + pFiltro.getNombre() == null ? "" : pFiltro.getNombre() + PORCENTAJE_LIKE).
					getResultList();
		} catch(Exception e){
			
		}
		return listado;
	}



}
