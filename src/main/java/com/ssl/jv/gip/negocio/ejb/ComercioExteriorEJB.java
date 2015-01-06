package com.ssl.jv.gip.negocio.ejb;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import com.ssl.jv.gip.jpa.pojo.Documento;
import com.ssl.jv.gip.jpa.pojo.DocumentoXLotesoic;
import com.ssl.jv.gip.negocio.dao.DocumentoDAOLocal;
import com.ssl.jv.gip.negocio.dao.DocumentoLotesOICDAOLocal;
import com.ssl.jv.gip.negocio.dao.DocumentoXLoteDAOLocal;
import com.ssl.jv.gip.negocio.dao.ProductoClienteComercioExteriorDAOLocal;
import com.ssl.jv.gip.negocio.dto.DatoContribucionCafeteraDTO;
import com.ssl.jv.gip.negocio.dto.DocumentoIncontermDTO;
import com.ssl.jv.gip.negocio.dto.DocumentoLotesContribucionCafeteriaDTO;
import com.ssl.jv.gip.negocio.dto.ListaEmpaqueDTO;
import com.ssl.jv.gip.negocio.dto.ProductoDTO;


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
	private DocumentoXLoteDAOLocal documentoXLoteDAO;
	
	@EJB
	private DocumentoDAOLocal documentoDAO;
	
	@EJB
	private ProductoClienteComercioExteriorDAOLocal productoClienteComercioExteriorDAO;

	@EJB
	private DocumentoLotesOICDAOLocal documentoLotesOICDAO;
	
	/**
     * Default constructor. 
     */
    public ComercioExteriorEJB() {

    }

	
    /**
     * Default constructor. 
     */
   
	public List<DatoContribucionCafeteraDTO> consultarDatosContribucionCafetera(Map<String, Object> parametros){
		return documentoDAO.consultarDatosContribucionCafetera(parametros);
	}
	
	public List<DocumentoLotesContribucionCafeteriaDTO> consultarDocumentoLotesContribucionCafetera(Map<String, Object> parametros){
		return documentoLotesOICDAO.consultarDocumentoLotesContribucionCafetera(parametros);
	}

	public List<DocumentoLotesContribucionCafeteriaDTO> guardarDocumentoLotesContribucionCafetera(List<DocumentoLotesContribucionCafeteriaDTO> documentos){
		return documentoLotesOICDAO.guardarDocumentoLotesContribucionCafetera(documentos);
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
	
	@Override
	public List<ListaEmpaqueDTO> consultarDocumentoPorFacturaProforma(String consecutivoFacturaProforma){
		return documentoDAO.consultarDocumentoPorFacturaProforma(consecutivoFacturaProforma);	
	}
	
	@Override
	public List<ProductoDTO> consultarProductoPorDocumento(String idDocumento, String idCliente){
		return productoClienteComercioExteriorDAO.consultarProductoPorDocumento(idDocumento, idCliente);
	}
	
	@Override
	public List<Documento> consultarDocumentosPorConsecutivoPedido(String consecutivoDocumento){
		return documentoDAO.consultarDocumentosPorConsecutivoPedido(consecutivoDocumento);
	}

}
