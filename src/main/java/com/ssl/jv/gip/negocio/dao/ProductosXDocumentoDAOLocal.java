package com.ssl.jv.gip.negocio.dao;

import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import com.ssl.jv.gip.jpa.pojo.ProductosXDocumento;
import com.ssl.jv.gip.negocio.dto.ProductoFacturaDirectaDTO;
import com.ssl.jv.gip.negocio.dto.ProductoReporteTxtFacturaDirectaDTO;

@Local
public interface ProductosXDocumentoDAOLocal extends
		IGenericDAO<ProductosXDocumento> {

	public List<ProductosXDocumento> consultarPorDocumento(Long id);
	public List<ProductoFacturaDirectaDTO> consultarProductoFacturaDirecta(String strConsecutivoDocumento); 
	public void modificarProductosXDocumentos(List<ProductosXDocumento> productosXDocumentos);
	public List<ProductosXDocumento> consultarPorDocumentoYCliente(Long idDocumento, Long idCliente);
	public List<ProductoReporteTxtFacturaDirectaDTO> consultarReporteTxtVentasFD(Map<String, Object> parametros);
}
