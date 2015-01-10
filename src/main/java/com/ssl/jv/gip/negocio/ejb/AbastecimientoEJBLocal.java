package com.ssl.jv.gip.negocio.ejb;

import java.util.List;

import javax.ejb.Local;

import com.ssl.jv.gip.jpa.pojo.Documento;
import com.ssl.jv.gip.negocio.dto.FiltroDocumentoDTO;

@Local
public interface AbastecimientoEJBLocal {

	public List<Documento> consultarDocumentosPorTipoYEstado(
			FiltroDocumentoDTO filtroDocumentoDTO);

}
