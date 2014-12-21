
package com.ssl.jv.gip.negocio.ejb;

import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.ssl.jv.gip.negocio.dao.DocumentoDAO;
import com.ssl.jv.gip.negocio.dao.DocumentoLotesOICDAO;
import com.ssl.jv.gip.negocio.dto.DatoContribucionCafeteraDTO;
import com.ssl.jv.gip.negocio.dto.DocumentoLotesContribucionCafeteriaDTO;

/**
 * Session Bean implementation class ComercioExterior
 */
@Stateless
@LocalBean
public class ComercioExterior implements ComercioExteriorLocal {

	@EJB
	private DocumentoDAO documentoDAO;
	
	@EJB
	private DocumentoLotesOICDAO documentoLotesOICDAO;
	
    /**
     * Default constructor. 
     */
    public ComercioExterior() {
        // TODO Auto-generated constructor stub
    }
    
	public List<DatoContribucionCafeteraDTO> consultarDatosContribucionCafetera(Map<String, Object> parametros){
		return documentoDAO.consultarDatosContribucionCafetera(parametros);
	}
	
	public List<DocumentoLotesContribucionCafeteriaDTO> consultarDocumentoLotesContribucionCafetera(Map<String, Object> parametros){
		return documentoLotesOICDAO.consultarDocumentoLotesContribucionCafetera(parametros);
	}

	public List<DocumentoLotesContribucionCafeteriaDTO> guardarDocumentoLotesContribucionCafetera(List<DocumentoLotesContribucionCafeteriaDTO> documentos){
		return documentoLotesOICDAO.guardarDocumentoLotesContribucionCafetera(documentos);
	}
}
