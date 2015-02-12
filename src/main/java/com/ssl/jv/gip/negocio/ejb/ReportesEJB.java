package com.ssl.jv.gip.negocio.ejb;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.ssl.jv.gip.jpa.pojo.Documento;
import com.ssl.jv.gip.jpa.pojo.MovimientosInventarioComext;
import com.ssl.jv.gip.jpa.pojo.ProductosXDocumento;
import com.ssl.jv.gip.negocio.dao.ComextFormatoNovedadesDAO;
import com.ssl.jv.gip.negocio.dao.CuentaContableDAOLocal;
import com.ssl.jv.gip.negocio.dao.DocumentoDAOLocal;
import com.ssl.jv.gip.negocio.dao.MovimientosInventarioComextDAOLocal;
import com.ssl.jv.gip.negocio.dao.ProductosXDocumentoDAOLocal;
import com.ssl.jv.gip.negocio.dto.ComextFormatoNovedadesDTO;
import com.ssl.jv.gip.negocio.dto.CuentaContableDTO;
import com.ssl.jv.gip.util.Estado;
import com.ssl.jv.gip.web.mb.util.ConstantesTipoDocumento;

/**
 * Session Bean implementation class ReportesEJB
 */
@Stateless
@LocalBean
public class ReportesEJB implements ReportesEJBLocal {

	@EJB
	private ComextFormatoNovedadesDAO comextFormatoNovedadesDAO;

	@EJB
	private DocumentoDAOLocal documentoDAO;

	@EJB
	private ProductosXDocumentoDAOLocal productosXDocumentoDAOLocal;

	@EJB
	private CuentaContableDAOLocal cuentaContableDAOLocal;

	@EJB
	private MovimientosInventarioComextDAOLocal movimientosInventarioComextDAOLocal;

	/**
	 * Default constructor.
	 */
	public ReportesEJB() {
		// TODO Auto-generated constructor stub
	}

	public List<ComextFormatoNovedadesDTO> consultarComextFormatoNovedades() {
		return comextFormatoNovedadesDAO.consultarComextFormatoNovedades();
	}

	@Override
	public List<Documento> consultarFacturasProformasActivasAprobadasOAsignadasPorConsecutivo(
			String consecutivo) {
		return documentoDAO
				.consultarDocumentosPorTipoDocumentoConsecutivoDocumentoYEstados(
						(long) ConstantesTipoDocumento.FACTURA_PROFORMA,
						consecutivo, Estado.ACTIVO.getCodigo(),
						Estado.APROBADA.getCodigo(),
						Estado.ASIGNADA.getCodigo());
	}

	@Override
	public List<Documento> consultarDocumentosParaGenerarFacturaExportacion(
			String consecutivoDocumento) {
		return documentoDAO
				.consultarDocumentosParaGenerarFacturaExportacion(consecutivoDocumento);
	}

	@Override
	public List<ProductosXDocumento> consultarProductosXDocumentosFacturaProformaPorDocumentoYCliente(
			Long idDocumento, Long idCliente) {
		return productosXDocumentoDAOLocal.consultarPorDocumentoYCliente(
				idDocumento, idCliente);
	}

	@Override
	public List<CuentaContableDTO> consultarReporteFacturasFX(String consecDoc,
			String fechaIni, String fechaFin) {
		return cuentaContableDAOLocal.consultarReporteFacturasFX(consecDoc,
				fechaIni, fechaFin);
	}

	@Override
	public List<CuentaContableDTO> consultarReporteFacturasFD(String consecDoc,
			String fechaIni, String fechaFin) {
		return cuentaContableDAOLocal.consultarReporteFacturasFD(consecDoc,
				fechaIni, fechaFin);
	}

	@Override
	public List<MovimientosInventarioComext> consultarMovimientosInventarioComextsPorSKU(
			String sku, boolean ultimoSaldo) {
		return movimientosInventarioComextDAOLocal.consultarPorSKU(sku,
				ultimoSaldo);
	}
}
