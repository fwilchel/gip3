package com.ssl.jv.gip.negocio.ejb;

import java.util.List;

import javax.ejb.Local;

import com.ssl.jv.gip.jpa.pojo.DocumentoXLotesoic;


/**
 * The Interface ComercioExteriorLocal.
 */
@Local
public interface ComercioExteriorEJBLocal {
	
	
	/**
	 * Consultar documentos por lote oic.
	 *
	 * @return the list
	 */
	public List<DocumentoXLotesoic> consultarDocumentosPorLoteOIC();
	
	/**
	 * Reiniciar consecutivo lote oic.
	 *
	 * @return the integer
	 */
	public Integer reiniciarConsecutivoLoteOIC();

}
