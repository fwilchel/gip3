package com.ssl.jv.gip.negocio.ejb;

import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import com.ssl.jv.gip.negocio.dto.DatoContribucionCafeteraDTO;

@Local
public interface ComercioExteriorLocal {

	public List<DatoContribucionCafeteraDTO> consultarDatosContrubicionCafetera(Map<String, Object> parametros);
	
}
