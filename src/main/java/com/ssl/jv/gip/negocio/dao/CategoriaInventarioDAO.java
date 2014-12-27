package com.ssl.jv.gip.negocio.dao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import com.ssl.jv.gip.jpa.pojo.CategoriasInventario;

@Stateless
@LocalBean
public class CategoriaInventarioDAO extends GenericDAO<CategoriasInventario> implements CategoriaInventarioDAOLocal{

	private static final Logger LOGGER = Logger.getLogger(CategoriaInventarioDAO.class);
	
	public CategoriaInventarioDAO(){
		this.persistentClass = CategoriasInventario.class;
	}	
	
	/**
	 * Consulta solo las categorias padre, y hace fetch de las hijas, ordenadas por nombre de padre y nombre de hijas
	 */
	public List<CategoriasInventario> findAll(){
		List list = this.em.createQuery("from CategoriasInventario ci JOIN FETCH ci.categoriasInventarios hijos WHERE ci.categoriasInventario IS NULL ORDER BY ci.nombre, hijos.nombre")
				.getResultList();
		return list;
	}
		
}
