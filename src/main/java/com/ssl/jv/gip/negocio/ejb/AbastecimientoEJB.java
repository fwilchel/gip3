package com.ssl.jv.gip.negocio.ejb;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.ssl.jv.gip.jpa.pojo.Documento;
import com.ssl.jv.gip.jpa.pojo.ProductosXDocumento;
import com.ssl.jv.gip.jpa.pojo.ProductosXDocumentoPK;
import com.ssl.jv.gip.jpa.pojo.TipoDocumento;
import com.ssl.jv.gip.jpa.pojo.Ubicacion;
import com.ssl.jv.gip.negocio.dao.DocumentoDAOLocal;
import com.ssl.jv.gip.negocio.dao.ProductosXDocumentoDAOLocal;
import com.ssl.jv.gip.negocio.dao.TipoDocumentoDAOLocal;
import com.ssl.jv.gip.negocio.dao.UbicacionDAOLocal;
import com.ssl.jv.gip.negocio.dto.FiltroDocumentoDTO;

/**
 * Session Bean implementation class AbastecimientoEJB
 */
@Stateless
@LocalBean
public class AbastecimientoEJB implements AbastecimientoEJBLocal {

	@EJB
	private DocumentoDAOLocal documentoDAOLocal;

	@EJB
	private UbicacionDAOLocal ubicacionDAOLocal;

	@EJB
	private ProductosXDocumentoDAOLocal productosXDocumentoDAOLocal;

	@EJB
	private TipoDocumentoDAOLocal tipoDocumentoDAOLocal;

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

	@Override
	public List<Ubicacion> consultarUbicacionesPorUsuario(String idUsuario) {
		return ubicacionDAOLocal.consultarUbicacionesPorUsuario(idUsuario);
	}

	@Override
	public List<ProductosXDocumento> consultarProductosXDocumentosPorDocumento(
			Long idDocumento) {
		return productosXDocumentoDAOLocal.consultarPorDocumento(idDocumento);
	}

	@Override
	public Documento guardarSugerenciaCompra(Documento sugerencia,
			List<ProductosXDocumento> productosXSugerencia) {
		Long sugerenciaId = null;
		if (sugerencia.getId() == null) {
			TipoDocumento tipoDocumento = tipoDocumentoDAOLocal
					.findByPK(sugerencia.getEstadosxdocumento().getId()
							.getIdTipoDocumento());
			Ubicacion ubicacion = null;
			if (com.ssl.jv.gip.util.Ubicacion.EXTERNA.getCodigo().equals(
					sugerencia.getUbicacionDestino().getId())) {
				ubicacion = ubicacionDAOLocal.findByPK(sugerencia
						.getUbicacionOrigen().getId());
			} else {
				ubicacion = ubicacionDAOLocal.findByPK(sugerencia
						.getUbicacionDestino().getId());
			}
			String prefijoConsecutivo = tipoDocumento.getAbreviatura()
					+ ubicacion.getEmpresa().getId();
			Long valorSecuencia = documentoDAOLocal
					.consultarProximoValorSecuencia(prefijoConsecutivo + "_seq");
			sugerencia.setConsecutivoDocumento(prefijoConsecutivo + "-"
					+ valorSecuencia);
			//
			// sugerenciaId = documentoDAOLocal
			// .consultarValorActualSecuencia("documentos_id_seq");
		}
		documentoDAOLocal.update(sugerencia);
		for (ProductosXDocumento productosXDocumento : productosXSugerencia) {
			if (productosXDocumento.getProductosInventario().isIncluido()) {
				ProductosXDocumentoPK id = productosXDocumento.getId();
				id.setIdDocumento(sugerencia.getId());
				productosXDocumento.setId(id);
				productosXDocumentoDAOLocal.update(productosXDocumento);
			} else {
				productosXDocumentoDAOLocal.delete(productosXDocumento);
			}
		}
		return sugerencia;
	}

}
