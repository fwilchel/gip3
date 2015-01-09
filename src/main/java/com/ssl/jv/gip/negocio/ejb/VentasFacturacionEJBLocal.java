package com.ssl.jv.gip.negocio.ejb;

import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import com.ssl.jv.gip.jpa.pojo.Documento;
import com.ssl.jv.gip.negocio.dto.FacturaDirectaDTO;

@Local
public interface VentasFacturacionEJBLocal {

	FacturaDirectaDTO consultarDocumentoFacturaDirecta(
			String strConsecutivoDocumento);

	List<Documento> consultarDocumento(Map<String, Object> parametros);

}
