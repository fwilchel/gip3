package com.ssl.jv.gip.negocio.dao;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;

import org.apache.log4j.Logger;

import com.ssl.jv.gip.jpa.pojo.Usuario;

@Stateless
@LocalBean
public class UsuarioDAO extends GenericDAO<Usuario>{
	
	private static final Logger LOGGER = Logger.getLogger(UsuarioDAO.class);
	
	public UsuarioDAO(){
		this.persistentClass = Usuario.class;
	}	
	
	public Usuario findByEmail(String email){
		try{
			return (Usuario)this.em.createNamedQuery("Usuario.findByEmail").setParameter("email", email).getSingleResult();
    	}catch(NoResultException e){
    		LOGGER.warn(e + "**** Email "+email);
    		return null;
    	}
	}
	
}
