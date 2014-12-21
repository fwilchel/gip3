package com.ssl.jv.gip.negocio.dao;

import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import com.ssl.jv.gip.jpa.pojo.Documento;
import com.ssl.jv.gip.jpa.pojo.DocumentoXLotesoic;
import com.ssl.jv.gip.negocio.dto.DocumentoLotesContribucionCafeteriaDTO;

@Local
public interface DocumentoLotesOICDAOLocal extends IGenericDAO<DocumentoXLotesoic>{

	public List<DocumentoLotesContribucionCafeteriaDTO> consultarDocumentoLotesContribucionCafetera(Map<String, Object> parametros);
	
	public List<DocumentoLotesContribucionCafeteriaDTO> guardarDocumentoLotesContribucionCafetera(List<DocumentoLotesContribucionCafeteriaDTO> documentos);

}
