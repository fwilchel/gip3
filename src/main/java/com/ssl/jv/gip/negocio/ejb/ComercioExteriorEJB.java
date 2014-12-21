package com.ssl.jv.gip.negocio.ejb;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import com.ssl.jv.gip.jpa.pojo.DocumentoXLotesoic;
import com.ssl.jv.gip.negocio.dao.DocumentoXLoteDAO;


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

}
