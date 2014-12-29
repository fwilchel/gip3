package com.ssl.jv.gip.negocio.dao;

import java.util.List;

import javax.ejb.Local;

import com.ssl.jv.gip.jpa.pojo.CategoriasInventario;

@Local
public interface CategoriaInventarioDAOLocal extends IGenericDAO<CategoriasInventario>{

	public List<CategoriasInventario> findAll();
	
}
