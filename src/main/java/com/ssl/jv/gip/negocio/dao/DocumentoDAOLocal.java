package com.ssl.jv.gip.negocio.dao;

import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import com.ssl.jv.gip.jpa.pojo.Documento;
import com.ssl.jv.gip.negocio.dto.DatoContribucionCafeteraDTO;

@Local
public interface DocumentoDAOLocal extends IGenericDAO<Documento>{

	public List<DatoContribucionCafeteraDTO> consultarDatosContrubicionCafetera(Map<String, Object> parametros);
	
}
