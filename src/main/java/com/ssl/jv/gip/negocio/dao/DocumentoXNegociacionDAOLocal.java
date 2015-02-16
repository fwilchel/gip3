package com.ssl.jv.gip.negocio.dao;

import java.util.List;

import javax.ejb.Local;

import com.ssl.jv.gip.jpa.pojo.DocumentoXNegociacion;

@Local
public interface DocumentoXNegociacionDAOLocal extends IGenericDAO<DocumentoXNegociacion>{

	public List<DocumentoXNegociacion> consultarDocumentoXNegociacionPorIdDocumento(Long idDocumento);
	
}
