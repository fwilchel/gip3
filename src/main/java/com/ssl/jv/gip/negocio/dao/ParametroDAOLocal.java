package com.ssl.jv.gip.negocio.dao;

import java.util.List;

import javax.ejb.Local;

import com.ssl.jv.gip.jpa.pojo.Parametro;

@Local
public interface ParametroDAOLocal extends IGenericDAO<Parametro> {

	public Parametro consultarParametroPorNombre(String nombre);

	public List<Parametro> consultarParametroPorNombres(String... nombres);

}
