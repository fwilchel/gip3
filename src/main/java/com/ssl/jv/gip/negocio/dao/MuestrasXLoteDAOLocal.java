package com.ssl.jv.gip.negocio.dao;

import java.math.BigDecimal;
import java.util.List;

import javax.ejb.Local;

import com.ssl.jv.gip.jpa.pojo.Muestrasxlote;

@Local
public interface MuestrasXLoteDAOLocal extends IGenericDAO<Muestrasxlote>{
	
	public List<Muestrasxlote> consultarMuestrasPorCantidad(BigDecimal cantidad);

}
