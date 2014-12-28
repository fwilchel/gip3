package com.ssl.jv.gip.negocio.dao;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import com.ssl.jv.gip.jpa.pojo.Cliente;

@Stateless
@LocalBean
public class ClienteDAO extends GenericDAO<Cliente> implements ClienteDAOLocal{
	
	private static final Logger LOGGER = Logger.getLogger(UsuarioDAO.class);
	
	public ClienteDAO(){
		this.persistentClass = Cliente.class;
	}

}
