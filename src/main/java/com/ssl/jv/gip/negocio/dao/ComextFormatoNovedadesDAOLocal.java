package com.ssl.jv.gip.negocio.dao;

import java.util.List;

import javax.ejb.Local;

import com.ssl.jv.gip.jpa.pojo.ComextFormatoNovedade;
import com.ssl.jv.gip.negocio.dto.ComextFormatoNovedadesDTO;

@Local
public interface ComextFormatoNovedadesDAOLocal extends IGenericDAO<ComextFormatoNovedade>{

	public List<ComextFormatoNovedadesDTO> consultarComextFormatoNovedades();

}
