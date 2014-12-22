package com.ssl.jv.gip.negocio.ejb;

import java.util.List;

import javax.ejb.Local;

import com.ssl.jv.gip.jpa.pojo.Documento;
import com.ssl.jv.gip.jpa.pojo.DocumentoXLotesoic;
import com.ssl.jv.gip.negocio.dto.DocumentoIncontermDTO;


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
	
	/**
	 * Consultar documentos costos inconterm
	 *
	 * @return the list
	 */
	public List<DocumentoIncontermDTO> consultarDocumentosCostosInconterm();
	
	/**
	 * Actualizar documento por negociacion.
	 *
	 * @param documento the documento
	 */
	public void actualizarDocumentoPorNegociacion(DocumentoIncontermDTO documento);

}
