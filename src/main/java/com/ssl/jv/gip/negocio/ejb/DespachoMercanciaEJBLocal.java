package com.ssl.jv.gip.negocio.ejb;

import java.util.List;

import javax.ejb.Local;

import com.ssl.jv.gip.jpa.pojo.Documento;
import com.ssl.jv.gip.negocio.dto.ProductoDTO;
import com.ssl.jv.gip.negocio.dto.ProductoDespacharMercanciaDTO;

/**Interfaz para ordenes de despacho
 * 
 * @author Daniel Cortés
 *
 */
@Local
public interface DespachoMercanciaEJBLocal {
	
	public List<Documento> consultarVentasDirectas();
	
	public List<Documento> consultarVentasDirectas(Documento filtro);

	public List<ProductoDespacharMercanciaDTO> consultarProductoPorDocumento(String idDocumento,String idCliente);

}
