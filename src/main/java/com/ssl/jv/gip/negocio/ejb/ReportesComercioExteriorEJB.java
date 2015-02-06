package com.ssl.jv.gip.negocio.ejb;

import java.math.BigDecimal;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.ssl.jv.gip.jpa.pojo.Documento;
import com.ssl.jv.gip.jpa.pojo.Muestrasxlote;
import com.ssl.jv.gip.jpa.pojo.ProductosXDocumento;
import com.ssl.jv.gip.negocio.dao.DocumentoDAOLocal;
import com.ssl.jv.gip.negocio.dao.MuestrasXLoteDAOLocal;
import com.ssl.jv.gip.negocio.dao.ProductosXDocumentoDAO;
import com.ssl.jv.gip.negocio.dto.FiltroDocumentoDTO;

/**
 * Session Bean implementation class ReportesComercioExteriorEJB
 */
@Stateless
@LocalBean
public class ReportesComercioExteriorEJB implements ReportesComercioExteriorEJBLocal {
	
	@EJB
	private DocumentoDAOLocal documentoDAO;
	
	@EJB
	private ProductosXDocumentoDAO productosXDocumentoDAO;
	
	@EJB
	private MuestrasXLoteDAOLocal  muestrasXLoteDAOLocal;

    /**
     * Default constructor. 
     */
    public ReportesComercioExteriorEJB() {
        // TODO Auto-generated constructor stub
    }

	@Override
	public List<Documento> consultarFacturasExportacion(FiltroDocumentoDTO filtro) {
		return documentoDAO.consultarDocumentosPorTipoDocumentoYEstados(filtro);
	}

	@Override
	public List<ProductosXDocumento> consultarProductosPorDocumento(Long id) {
		return productosXDocumentoDAO.consultarPorDocumento(id);
	}

	@Override
	public List<Muestrasxlote> consultarMuestrasPorCantidad(BigDecimal cantidad) {
		return muestrasXLoteDAOLocal.consultarMuestrasPorCantidad(cantidad);
	}

	@Override
	public List<Documento> consultarFacturasExportacionFechaTipo(
			FiltroDocumentoDTO filtro) {
		return documentoDAO.consultarDocumentosPorTipoDocumentoYFechas(filtro);
	}

	@Override
	public List<Documento> consultarDocumentosPorTipoDocumentoEstadoTipoCafe(
			FiltroDocumentoDTO filtro) {
		return documentoDAO.consultarDocumentosPorTipoDocumentoEstadoTipoCafe(filtro);
	}

}
