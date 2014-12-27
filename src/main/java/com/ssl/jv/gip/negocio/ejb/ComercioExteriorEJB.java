package com.ssl.jv.gip.negocio.ejb;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import com.ssl.jv.gip.jpa.pojo.DocumentoXLotesoic;
import com.ssl.jv.gip.negocio.dao.DocumentoDAO;
import com.ssl.jv.gip.negocio.dao.DocumentoXLoteDAO;
import com.ssl.jv.gip.negocio.dto.DocumentoIncontermDTO;


/**
 * Session Bean implementation class ComercioExterior.
 */
@Stateless
@LocalBean
public class ComercioExteriorEJB implements ComercioExteriorEJBLocal {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = Logger.getLogger(ComercioExteriorEJB.class);

	/** The documento x lote dao. */
	@EJB
	private DocumentoXLoteDAO documentoXLoteDAO;
	
	@EJB
	private DocumentoDAO documentoDAO;
	
	/**
     * Default constructor. 
     */
    public ComercioExteriorEJB() {

    }

	/* (non-Javadoc)
	 * @see com.ssl.jv.gip.negocio.ejb.ComercioExteriorEJBLocal#consultarDocumentosPorLoteOIC()
	 */
	@Override
	public List<DocumentoXLotesoic> consultarDocumentosPorLoteOIC() {
		List<DocumentoXLotesoic> listado = new ArrayList<DocumentoXLotesoic>();
    	
    	try{
    		listado = documentoXLoteDAO.consultarDocumentoXLoteOIC();
    	} catch(Exception e){
    		
    	}
    	
    	return listado;
	}

	/* (non-Javadoc)
	 * @see com.ssl.jv.gip.negocio.ejb.ComercioExteriorEJBLocal#reiniciarConsecutivoLoteOIC()
	 */
	@Override
	public Integer reiniciarConsecutivoLoteOIC() {
		return documentoXLoteDAO.reiniciarConsecutivoLoteOIC();
	}
	
	/* (non-Javadoc)
	 * @see com.ssl.jv.gip.negocio.ejb.ComercioExteriorEJBLocal#consultarDocumentosCostosInconterm()
	 */
	@Override
	public List<DocumentoIncontermDTO> consultarDocumentosCostosInconterm() {
		List<DocumentoIncontermDTO> listado = new ArrayList<DocumentoIncontermDTO>();
    	
    	try{
    		listado = documentoDAO.consultarDocumentosCostosInconterm();
    	} catch(Exception e){
    		
    	}
    	
    	return listado;
	}
	
	/* (non-Javadoc)
	 * @see com.ssl.jv.gip.negocio.ejb.ComercioExteriorEJBLocal#actualizarDocumentoPorNegociacion(com.ssl.jv.gip.negocio.dto.DocumentoIncontermDTO)
	 */
	@Override
	public void actualizarDocumentoPorNegociacion(DocumentoIncontermDTO documento) {
		 documentoDAO.actualizarDocumentoPorNegociacion(documento);
	}

}
