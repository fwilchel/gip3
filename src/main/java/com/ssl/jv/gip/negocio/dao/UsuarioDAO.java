package com.ssl.jv.gip.negocio.dao;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.ssl.jv.gip.jpa.pojo.Usuario;

@Stateless
@LocalBean
public class UsuarioDAO extends GenericDAO<Usuario>{
	
	public Usuario findByEmail(String email){
		return (Usuario)this.em.createNamedQuery("Usuario.findByEmail").setParameter("email", email).getSingleResult();
	}
	
}
