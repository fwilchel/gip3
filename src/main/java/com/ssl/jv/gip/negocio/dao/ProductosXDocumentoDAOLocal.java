package com.ssl.jv.gip.negocio.dao;

import java.util.List;

import javax.ejb.Local;

import com.ssl.jv.gip.jpa.pojo.ProductosXDocumento;
import com.ssl.jv.gip.negocio.dto.ProductoFacturaDirectaDTO;

@Local
public interface ProductosXDocumentoDAOLocal extends
		IGenericDAO<ProductosXDocumento> {

	public List<ProductosXDocumento> consultarPorDocumento(Long id);
	
	public List<ProductoFacturaDirectaDTO> consultarProductoFacturaDirecta(String strConsecutivoDocumento); 
	
	
}
