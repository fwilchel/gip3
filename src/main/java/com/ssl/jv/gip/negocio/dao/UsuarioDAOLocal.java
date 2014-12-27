package com.ssl.jv.gip.negocio.dao;

import javax.ejb.Local;

import com.ssl.jv.gip.jpa.pojo.Usuario;

@Local
public interface UsuarioDAOLocal extends IGenericDAO<Usuario>{
	
	public Usuario findByEmail(String email);
	
}
