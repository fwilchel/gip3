package com.ssl.jv.gip.negocio.ejb;

import java.util.List;

import javax.ejb.Local;

import com.ssl.jv.gip.jpa.pojo.ProductosInventario;
import com.ssl.jv.gip.jpa.pojo.Ubicacion;
import com.ssl.jv.gip.negocio.dto.ProductoDevolucionDTO;

@Local
public interface DevolucionesEJBLocal {
	
	public List<Ubicacion> consultarPorIds(List<Long> ids);

	public List<ProductoDevolucionDTO> consultarProductosInventarioPorPais(
			List<String> paises);
	
	public List<ProductoDevolucionDTO> consultarProductosActivos();

	public List<Ubicacion> consultarUbicacionesOrdenadas();

}
