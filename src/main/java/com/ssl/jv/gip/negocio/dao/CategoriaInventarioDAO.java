package com.ssl.jv.gip.negocio.dao;

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
	
}
