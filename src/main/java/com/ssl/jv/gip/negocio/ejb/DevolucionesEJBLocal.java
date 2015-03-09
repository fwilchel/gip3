package com.ssl.jv.gip.negocio.ejb;

import java.util.List;

import javax.ejb.Local;

import com.ssl.jv.gip.jpa.pojo.Ubicacion;
import com.ssl.jv.gip.negocio.dto.DocumentoRecibirDevolucionDTO;
import com.ssl.jv.gip.negocio.dto.ProductoDevolucionDTO;
import com.ssl.jv.gip.negocio.dto.UbicacionRecibirDevolucionDTO;

@Local
public interface DevolucionesEJBLocal {

  public List<Ubicacion> consultarPorIds(List<Long> ids);

  public List<ProductoDevolucionDTO> consultarProductosInventarioPorPais(List<String> paises);

  public List<ProductoDevolucionDTO> consultarProductosActivos();

  public List<Ubicacion> consultarUbicacionesOrdenadas();
  
  /**
   * Metodo que consulta las ubicaciones que son tienda, para un usuario
   *
   * @author Diego Poveda - Soft Studio Ltda.
   * @email dpoveda@gmail.com
   * @phone 3192594013
   * @param usuario
   * @return 
   */
  List<Ubicacion> consultarUbicacionesQueSonTiendaPorUsuario(String usuario);
  
  /**
   * Consultar ubicaciones recibir devolucion por usuario.
   *
   * @param usuario the usuario
   * @return the list
   */
  public List<UbicacionRecibirDevolucionDTO> consultarUbicacionesRecibirDevolucionPorUsuario(String usuario);
  
  /**
   * Consultar documentos recibir devolucion.
   *
   * @param bodega the bodega
   * @return the list
   */
  public List<DocumentoRecibirDevolucionDTO> consultarDocumentosRecibirDevolucion(String bodega);
  
}
