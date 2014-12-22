package com.ssl.jv.gip.negocio.ejb;

import java.util.List;

import javax.ejb.Local;

import com.ssl.jv.gip.negocio.dto.ComextFormatoNovedadesDTO;

@Local
public interface ReportesEJBLocal {

	public List<ComextFormatoNovedadesDTO> consultarComextFormatoNovedades();
	
}
