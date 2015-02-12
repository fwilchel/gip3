package com.ssl.jv.gip.negocio.ejb;

import java.util.List;

import javax.ejb.Local;

import com.ssl.jv.gip.jpa.pojo.Documento;
import com.ssl.jv.gip.jpa.pojo.MovimientosInventarioComext;
import com.ssl.jv.gip.jpa.pojo.ProductosXDocumento;
import com.ssl.jv.gip.negocio.dto.ComextFormatoNovedadesDTO;
import com.ssl.jv.gip.negocio.dto.CuentaContableDTO;

@Local
public interface ReportesEJBLocal {

	public List<ComextFormatoNovedadesDTO> consultarComextFormatoNovedades();

	public List<Documento> consultarFacturasProformasActivasAprobadasOAsignadasPorConsecutivo(
			String consecutivo);

	public List<Documento> consultarDocumentosParaGenerarFacturaExportacion(
			String consecutivoDocumento);

	public List<ProductosXDocumento> consultarProductosXDocumentosFacturaProformaPorDocumentoYCliente(
			Long idDocumento, Long idCliente);

	public List<CuentaContableDTO> consultarReporteFacturasFX(String consecDoc,
			String fechaIni, String fechaFin);

	public List<CuentaContableDTO> consultarReporteFacturasFD(
			String consecutivoDocumento, String fechaStringGeneracionInicial,
			String fechaFinalTmp);

	public List<MovimientosInventarioComext> consultarMovimientosInventarioComextsPorSKU(
			String sku, boolean ultimoSaldo);
}
