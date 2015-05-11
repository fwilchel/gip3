package com.ssl.jv.gip.negocio.dao;

import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import com.ssl.jv.gip.jpa.pojo.ProductosXCliente;

@Local
public interface ProductoClienteDAOLocal extends IGenericDAO<ProductosXCliente> {

  /**
   * 
   * @param idCliente
   * @param idPuntoVenta
   * @return 
   */
  List<ProductosXCliente> consultarPorClientePuntoVenta(Long idCliente, Long idPuntoVenta);
  
  /**
   * 
   * @param parametros
   * @return
   */
  List<ProductosXCliente> consultarProductosXCliente(Map<String, Object> parametros);

}
