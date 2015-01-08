package com.ssl.jv.gip.negocio.ejb;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import com.ssl.jv.gip.jpa.pojo.Documento;
import com.ssl.jv.gip.jpa.pojo.DocumentoXLotesoic;
import com.ssl.jv.gip.jpa.pojo.DocumentoXNegociacion;
import com.ssl.jv.gip.jpa.pojo.DocumentoXNegociacionPK;
import com.ssl.jv.gip.jpa.pojo.LogAuditoria;
import com.ssl.jv.gip.jpa.pojo.ProductosXDocumento;
import com.ssl.jv.gip.jpa.pojo.TerminoIncoterm;
import com.ssl.jv.gip.jpa.pojo.Ubicacion;
import com.ssl.jv.gip.negocio.dao.DocumentoDAOLocal;
import com.ssl.jv.gip.negocio.dao.DocumentoLotesOICDAOLocal;
import com.ssl.jv.gip.negocio.dao.DocumentoXLoteDAOLocal;
import com.ssl.jv.gip.negocio.dao.DocumentoXNegociacionDAOLocal;
import com.ssl.jv.gip.negocio.dao.LogAuditoriaDAOLocal;
import com.ssl.jv.gip.negocio.dao.ProductoClienteComercioExteriorDAO;
import com.ssl.jv.gip.negocio.dao.ProductoClienteComercioExteriorDAOLocal;
import com.ssl.jv.gip.negocio.dao.TerminoIncotermDAO;
import com.ssl.jv.gip.negocio.dao.TerminoIncotermDAOLocal;
import com.ssl.jv.gip.negocio.dao.UbicacionDAO;
import com.ssl.jv.gip.negocio.dao.UbicacionDAOLocal;
import com.ssl.jv.gip.negocio.dto.DatoContribucionCafeteraDTO;
import com.ssl.jv.gip.negocio.dto.DocumentoIncontermDTO;
import com.ssl.jv.gip.negocio.dto.DocumentoLotesContribucionCafeteriaDTO;
import com.ssl.jv.gip.negocio.dto.ListaEmpaqueDTO;
import com.ssl.jv.gip.negocio.dto.ProductoDTO;
import com.ssl.jv.gip.negocio.dto.ProductoGenerarFacturaPFDTO;
import com.ssl.jv.gip.negocio.dto.ProductoPorClienteComExtDTO;
import com.ssl.jv.gip.web.mb.util.ConstantesDocumento;
import com.ssl.jv.gip.web.mb.util.ConstantesTipoDocumento;



/**
 * Session Bean implementation class ComercioExterior.
 */
@Stateless
@LocalBean
public class ComercioExteriorEJB implements ComercioExteriorEJBLocal {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = Logger.getLogger(ComercioExteriorEJB.class);

	@EJB
	private DocumentoXLoteDAOLocal documentoXLoteDAO;
	
	@EJB
	private DocumentoDAOLocal documentoDAO;
	
	@EJB
	private ProductoClienteComercioExteriorDAOLocal productoClienteComercioExteriorDAO;

	@EJB
	private DocumentoLotesOICDAOLocal documentoLotesOICDAO;
	
	@EJB
	private ProductoClienteComercioExteriorDAOLocal productoClienteCEDAO;
	
	@EJB
	private TerminoIncotermDAOLocal terminoDAO;
	
	@EJB
	private UbicacionDAOLocal ubicacionDAO;
	
	@EJB
	private LogAuditoriaDAOLocal logAuditoriaDAO;
	
	@EJB
	private DocumentoXNegociacionDAOLocal documentoXNegociacionDAO;
	
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
	
	/* (non-Javadoc)
	 * @see com.ssl.jv.gip.negocio.ejb.ComercioExteriorEJBLocal#consultarListaIncontermPorCliente(java.lang.Long)
	 */
	@Override
	public List<TerminoIncoterm> consultarListaIncontermPorCliente(Long idCliente){
		return terminoDAO.consultarListaIncontermPorCliente(idCliente);
	}
	
	/* (non-Javadoc)
	 * @see com.ssl.jv.gip.negocio.ejb.ComercioExteriorEJBLocal#consultarDocumentosSolicitudPedido()
	 */
	@Override
	public List<DocumentoIncontermDTO> consultarDocumentosSolicitudPedido() {
		List<DocumentoIncontermDTO> listado = new ArrayList<DocumentoIncontermDTO>();
    	
    	try{
    		listado = documentoDAO.consultarDocumentosSolicitudPedido();
    	} catch(Exception e){
    		
    	}
    	
    	return listado;
	}
	
	/* (non-Javadoc)
	 * @see com.ssl.jv.gip.negocio.ejb.ComercioExteriorEJBLocal#consultarListaSolicitudesPedido(java.lang.Long, java.lang.Long)
	 */
	@Override
	public List<ProductoPorClienteComExtDTO> consultarListaSolicitudesPedido(Long idDocumento, Long idCliente) {
		List<ProductoPorClienteComExtDTO> listado = new ArrayList<ProductoPorClienteComExtDTO>();
    	
    	try{
    		listado = productoClienteCEDAO.consultarListaSolicitudesPedido(idDocumento, idCliente);
    	} catch(Exception e){
    		
    	}
    	
    	return listado;
	}
	
	/* (non-Javadoc)
	 * @see com.ssl.jv.gip.negocio.ejb.ComercioExteriorEJBLocal#consultarListaProductosPorClienteCE(java.lang.Long, java.lang.String, java.lang.Boolean)
	 */
	@Override
	public List<ProductoPorClienteComExtDTO> consultarListaProductosPorClienteCE(Long idCliente, String idsProductos, Boolean solicitudCafe) {
		List<ProductoPorClienteComExtDTO> listado = new ArrayList<ProductoPorClienteComExtDTO>();
    	
    	try{
    		listado = productoClienteCEDAO.consultarListaProductosPorClienteCE(idCliente, idsProductos, solicitudCafe);
    	} catch(Exception e){
    		
    	}
    	
    	return listado;
	}
	
	/* (non-Javadoc)
	 * @see com.ssl.jv.gip.negocio.ejb.ComercioExteriorEJBLocal#guardarSolicitudPedido(com.ssl.jv.gip.negocio.dto.DocumentoIncontermDTO, java.util.List)
	 */
	@Override
	public void guardarSolicitudPedido(DocumentoIncontermDTO documento, List<ProductoPorClienteComExtDTO> listaSolicitudPedido){
		if (documento.getIdEstado() == ConstantesDocumento.GENERADO){
			documento.setIdEstado(new Long(ConstantesDocumento.ANULADO));
		}
		
		if ( documento.getIdTipoDocumento()==ConstantesTipoDocumento.SOLICITUD_PEDIDO)
		{
			documento.setIdEstado(new Long(ConstantesDocumento.VERIFICADO));
		}
		
		if(documento.getFechaEsperadaEntregaDate()!=null){
			documento.setFechaEsperadaEntrega(new Timestamp(documento.getFechaEsperadaEntregaDate().getTime()));
		}
		
		Documento vdocumento = documentoDAO.findByPK(documento.getIdDocumento());
		vdocumento.setValorTotal(documento.getValorTotalDocumento());
		Ubicacion ubicacion1 = ubicacionDAO.findByPK(documento.getIdUbicacionOrigen());
		vdocumento.setUbicacionOrigen(ubicacion1);
		Ubicacion ubicacion2 = ubicacionDAO.findByPK(documento.getIdUbicacionDestino());
		vdocumento.setUbicacionDestino(ubicacion2);
		vdocumento.setFechaEsperadaEntrega(documento.getFechaEsperadaEntrega());
		
		
		documentoDAO.update(vdocumento);
		documentoDAO.actualizarDocumentoPorNegociacion(documento);
		
		//TODO Falta almacenar la lista de solicitud de pedido
	}
	
	/* (non-Javadoc)
	 * @see com.ssl.jv.gip.negocio.ejb.ComercioExteriorEJBLocal#consultarUbicacionesPorUsuario(java.lang.String)
	 */
	@Override
	public List<Ubicacion> consultarUbicacionesPorUsuario(String idUsuario) {
		List<Ubicacion> listado = new ArrayList<Ubicacion>();
    	
    	try{
    		listado = ubicacionDAO.consultarUbicacionesPorUsuario(idUsuario);
    	} catch(Exception e){
    		
    	}
    	
    	return listado;
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
	public List<ProductoGenerarFacturaPFDTO> consultarProductoPorDocumentoGenerarFacturaProforma(Long idDocumento, Long idCliente){
		return productoClienteComercioExteriorDAO.consultarProductoPorDocumentoGenerarFacturaProforma(idDocumento, idCliente);
	}
	
	@Override
	public List<Documento> consultarDocumentosPorConsecutivoPedido(String consecutivoDocumento){
		return documentoDAO.consultarDocumentosPorConsecutivoPedido(consecutivoDocumento);
	}
	
	@Override
	public Documento crearFactura(Documento documento, LogAuditoria auditoria, DocumentoXNegociacion documentoPorNegociacion, List<ProductosXDocumento> productos){
		documento.setConsecutivoDocumento("FP1-"+this.documentoDAO.consultarProximoValorSecuencia("fp1_seq"));
		documento=(Documento)this.documentoDAO.add(documento);
		auditoria.setIdRegTabla(documento.getId());
		auditoria.setValorNuevo(documento.getConsecutivoDocumento());
		this.logAuditoriaDAO.add(auditoria);
		documentoPorNegociacion.getPk().setIdDocumento(documento.getId());
		documentoXNegociacionDAO.add(documentoPorNegociacion);
		for (ProductosXDocumento pxd:productos){
			pxd.getId().setIdDocumento(documento.getId());
		}
		documento.getEstadosxdocumento().getId().setIdEstado((long)ConstantesDocumento.APROBADA);
		return documento;
	}

}
