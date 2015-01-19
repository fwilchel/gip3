package com.ssl.jv.gip.negocio.ejb;

import java.util.List;

import javax.ejb.Local;

import com.ssl.jv.gip.jpa.pojo.Documento;
import com.ssl.jv.gip.jpa.pojo.ProductosXDocumento;
import com.ssl.jv.gip.negocio.dto.ComextFormatoNovedadesDTO;

@Local
public interface ReportesEJBLocal {

	public List<ComextFormatoNovedadesDTO> consultarComextFormatoNovedades();

	public List<Documento> consultarFacturasProformasActivasAprobadasOAsignadasPorConsecutivo(
			String consecutivo);

	public List<ProductosXDocumento> consultarProductosXDocumentosFacturaProformaPorDocumentoYCliente(
			Long idDocumento, Long idCliente);
}
