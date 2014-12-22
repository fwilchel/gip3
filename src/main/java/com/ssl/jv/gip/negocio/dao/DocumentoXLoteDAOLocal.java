package com.ssl.jv.gip.negocio.dao;

import java.util.List;

import javax.ejb.Local;

import com.ssl.jv.gip.jpa.pojo.DocumentoXLotesoic;

/**
 * The Interface DocumentoXLoteDAOLocal.
 */
@Local
public interface DocumentoXLoteDAOLocal extends IGenericDAO<DocumentoXLotesoic>{

	/**
	 * Consultar documento x lote oic.
	 *
	 * @return the list
	 */
	public List<DocumentoXLotesoic> consultarDocumentoXLoteOIC();
	
	/**
	 * Reiniciar consecutivo lote oic.
	 *
	 * @return the integer
	 */
	public Integer reiniciarConsecutivoLoteOIC();
	
	
}
