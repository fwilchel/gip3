package com.ssl.jv.gip.negocio.dao;

import java.math.BigInteger;
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

	List<ListaEmpaqueDTO> consultarDocumentoPorFacturaProforma(String consecutivoFacturaProforma);

	BigInteger generarListaEmpaque(ListaEmpaqueDTO listaEmpaqueDTO);
	
}
