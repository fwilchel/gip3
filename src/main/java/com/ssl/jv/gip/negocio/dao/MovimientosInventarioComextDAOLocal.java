package com.ssl.jv.gip.negocio.dao;

import java.util.List;

import javax.ejb.Local;

import com.ssl.jv.gip.jpa.pojo.MovimientosInventarioComext;

@Local
public interface MovimientosInventarioComextDAOLocal extends IGenericDAO<MovimientosInventarioComext>{

	public List<MovimientosInventarioComext> getUltimosSaldos();
	
}
