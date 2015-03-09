package com.ssl.jv.gip.negocio.ejb;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.ssl.jv.gip.jpa.pojo.Ubicacion;
import com.ssl.jv.gip.negocio.dao.DocumentoDAOLocal;
import com.ssl.jv.gip.negocio.dao.ProductoInventarioDAOLocal;
import com.ssl.jv.gip.negocio.dao.UbicacionDAOLocal;
import com.ssl.jv.gip.negocio.dto.DocumentoRecibirDevolucionDTO;
import com.ssl.jv.gip.negocio.dto.ProductoDevolucionDTO;
import com.ssl.jv.gip.negocio.dto.UbicacionRecibirDevolucionDTO;

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
  
  @EJB
  private DocumentoDAOLocal documentoDAO;

  @Override
  public List<Ubicacion> consultarPorIds(List<Long> ids) {
    return ubicacionDAO.consultarPorIds(ids);
  }

  @Override
  public List<ProductoDevolucionDTO> consultarProductosInventarioPorPais(List<String> paises) {
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

  @Override
  public List<Ubicacion> consultarUbicacionesQueSonTiendaPorUsuario(String usuario) {
    return ubicacionDAO.consultarUbicacionesQueSonTiendaPorUsuario(usuario);
  }
  
  /* (non-Javadoc)
   * @see com.ssl.jv.gip.negocio.ejb.DevolucionesEJBLocal#consultarUbicacionesRecibirDevolucionPorUsuario(java.lang.String)
   */
  @Override
  public List<UbicacionRecibirDevolucionDTO> consultarUbicacionesRecibirDevolucionPorUsuario(String usuario) {
	  return ubicacionDAO.consultarUbicacionesRecibirDevolucionPorUsuario(usuario);
  }
  
  @Override
  public List<DocumentoRecibirDevolucionDTO> consultarDocumentosRecibirDevolucion(String bodega){
	  return documentoDAO.consultarDocumentosRecibirDevolucion(bodega);
  }

}
