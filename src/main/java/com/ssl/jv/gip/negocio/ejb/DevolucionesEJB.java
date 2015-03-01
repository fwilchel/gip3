package com.ssl.jv.gip.negocio.ejb;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.ssl.jv.gip.jpa.pojo.ProductosInventario;
import com.ssl.jv.gip.jpa.pojo.Ubicacion;
import com.ssl.jv.gip.negocio.dao.ProductoInventarioDAOLocal;
import com.ssl.jv.gip.negocio.dao.UbicacionDAOLocal;
import com.ssl.jv.gip.negocio.dto.ProductoDevolucionDTO;

/**
 * Session Bean implementation class DevolucionesEJB
 */
@Stateless
@LocalBean
public class DevolucionesEJB implements DevolucionesEJBLocal {
	
	@EJB
	private UbicacionDAOLocal ubicacionDAO;
	
	@EJB
	private ProductoInventarioDAOLocal productoInventarioDAO;

    /**
     * Default constructor. 
     */
    public DevolucionesEJB() {
        // TODO Auto-generated constructor stub
    }

	@Override
	public List<Ubicacion> consultarPorIds(List<Long> ids) {
		return ubicacionDAO.consultarPorIds(ids);
	}
	
	@Override
	public List<ProductoDevolucionDTO> consultarProductosInventarioPorPais(List<String> paises){
		return productoInventarioDAO.consultarActivosPorPaises(paises);
	}

	@Override
	public List<ProductoDevolucionDTO> consultarProductosActivos() {
		return productoInventarioDAO.consultarActivosDev();
	}
	
	@Override
	public List<Ubicacion> consultarUbicacionesOrdenadas() {
		return ubicacionDAO.consultarTodasOrdenadas();
	}

}
