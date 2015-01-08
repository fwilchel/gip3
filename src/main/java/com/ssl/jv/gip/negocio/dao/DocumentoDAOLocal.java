package com.ssl.jv.gip.negocio.dao;

import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import com.ssl.jv.gip.jpa.pojo.Documento;
import com.ssl.jv.gip.negocio.dto.DatoContribucionCafeteraDTO;
import com.ssl.jv.gip.negocio.dto.DocumentoIncontermDTO;
import com.ssl.jv.gip.negocio.dto.ListaEmpaqueDTO;

@Local
public interface DocumentoDAOLocal extends IGenericDAO<Documento>{

	public List<DatoContribucionCafeteraDTO> consultarDatosContribucionCafetera(Map<String, Object> parametros);
	
	public List<DocumentoIncontermDTO> consultarDocumentosCostosInconterm();
	
	public void actualizarDocumentoPorNegociacion(DocumentoIncontermDTO documento);

	public List<ListaEmpaqueDTO> consultarDocumentoPorFacturaProforma(String consecutivoFacturaProforma);
	
	public List<Documento> consultarDocumentosSolicitudPedido(String consecutivoDocumento);
	public List<Documento> consultarDocumentosFacturaPF(String consecutivoDocumento);

	public List<Documento> consultarOrdenesDeDespachoPorFiltro(Documento filtro);

	public List<Documento> consultarOrdenesDeDespacho();
	
	public List<DocumentoIncontermDTO> consultarDocumentosSolicitudPedido();
}
