package com.ssl.jv.gip.negocio.ejb;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.ssl.jv.gip.jpa.pojo.Documento;
import com.ssl.jv.gip.negocio.dao.DocumentoDAOLocal;
import com.ssl.jv.gip.negocio.dto.FiltroDocumentoDTO;

/**
 * Session Bean implementation class AbastecimientoEJB
 */
@Stateless
@LocalBean
public class AbastecimientoEJB implements AbastecimientoEJBLocal {

	@EJB
	DocumentoDAOLocal documentoDAOLocal;

	/**
	 * Default constructor.
	 */
	public AbastecimientoEJB() {

	}

	@Override
	public List<Documento> consultarDocumentosPorTipoYEstado(
			FiltroDocumentoDTO filtroDocumentoDTO) {
		return documentoDAOLocal
				.consultarDocumentosPorTipoDocumentoYEstado(filtroDocumentoDTO);
	}

}
