package com.ssl.jv.gip.negocio.ejb;

import java.util.List;

import javax.ejb.Local;

import com.ssl.jv.gip.jpa.pojo.Documento;
import com.ssl.jv.gip.jpa.pojo.ProductosXDocumento;
import com.ssl.jv.gip.jpa.pojo.Ubicacion;
import com.ssl.jv.gip.negocio.dto.FiltroDocumentoDTO;

@Local
public interface AbastecimientoEJBLocal {

	public List<Documento> consultarDocumentosPorTipoYEstado(
			FiltroDocumentoDTO filtroDocumentoDTO);

	public List<Ubicacion> consultarUbicacionesPorUsuario(String idUsuario);

	public List<ProductosXDocumento> consultarProductosXDocumentosPorDocumento(
			Long idDocumento);

	public Documento guardarSugerenciaCompra(Documento sugerencia,
			List<ProductosXDocumento> productosXSugerencia);

	public void importarSugerenciasCompra(byte[] archivo);

}
