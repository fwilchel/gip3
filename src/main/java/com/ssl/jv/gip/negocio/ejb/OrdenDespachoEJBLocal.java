package com.ssl.jv.gip.negocio.ejb;

import java.util.List;

import javax.ejb.Local;

import com.ssl.jv.gip.jpa.pojo.Documento;
import com.ssl.jv.gip.negocio.dto.ProductoDTO;

/**Interfaz para ordenes de despacho
 * 
 * @author Daniel Cortés
 *
 */
@Local
public interface OrdenDespachoEJBLocal {
	
	public List<Documento> consultarOrdenesDeDespacho();
	
	public List<Documento> consultarOrdenesDeDespacho(Documento filtro);
	
	public Documento crearOrdenDeDespacho(Documento pEntidad);
	
	public Documento actualizarOrdenDeDespacho(Documento pEntidad);

	public List<ProductoDTO> consultarProductoPorDocumento(String idDocumento,String idCliente);

}
