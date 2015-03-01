package com.ssl.jv.gip.negocio.ejb;

import java.util.List;

import javax.ejb.Local;

import com.ssl.jv.gip.jpa.pojo.ProductosInventario;
import com.ssl.jv.gip.jpa.pojo.Ubicacion;

@Local
public interface DevolucionesEJBLocal {
	
	public List<Ubicacion> consultarPorIds(List<Long> ids);

	public List<ProductosInventario> consultarProductosInventarioPorPais(
			List<String> paises);
	
	public List<ProductosInventario> consultarProductosActivos();

}
