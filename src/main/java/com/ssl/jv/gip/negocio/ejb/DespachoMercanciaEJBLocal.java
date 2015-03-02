package com.ssl.jv.gip.negocio.ejb;

import java.util.List;

import javax.ejb.Local;

import com.ssl.jv.gip.jpa.pojo.Documento;
import com.ssl.jv.gip.jpa.pojo.MovimientosInventario;
import com.ssl.jv.gip.jpa.pojo.Unidad;
import com.ssl.jv.gip.negocio.dto.ProductoDespacharMercanciaDTO;

/**Interfaz para ordenes de despacho
 * 
 * @author Daniel Cortes
 *
 */
@Local
public interface DespachoMercanciaEJBLocal {
	
	public List<Documento> consultarVentasDirectas();

	public List<ProductoDespacharMercanciaDTO> consultarProductoPorDocumento(String idDocumento,String idCliente);

	public List<Documento> consultarVentasDirectas(String filtro);

	public MovimientosInventario crearMovimientoInventario(MovimientosInventario movimiento);

	public Unidad consultarUnidad(String unidadVenta);

}
