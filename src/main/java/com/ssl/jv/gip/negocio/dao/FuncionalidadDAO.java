package com.ssl.jv.gip.negocio.dao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.ssl.jv.gip.jpa.pojo.Funcionalidad;

@Stateless
@LocalBean
public class FuncionalidadDAO extends GenericDAO<Funcionalidad>{
	
	public List<Funcionalidad> getMenu(String login) {
		List<Funcionalidad> opciones = null;
		try {
			String query = "SELECT DISTINCT f FROM Usuario u INNER JOIN u.role r INNER JOIN r.permisos p INNER JOIN p.funcionalidade f WHERE u.email = :email ORDER BY f.ordenar";
			opciones = em.createQuery(query).setParameter("email", login)
					.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return opciones;
	}
	
}
