package com.ssl.jv.gip.negocio.dao;

import java.util.List;

import javax.ejb.Local;

import com.ssl.jv.gip.jpa.pojo.ProductosXCliente;
import com.ssl.jv.gip.negocio.dto.ProductoFacturaDirectaDTO;

@Local
public interface ProductoClienteDAOLocal extends IGenericDAO<ProductosXCliente> {

  //List<ProductoFacturaDirectaDTO> consultarProductoFacturaDirecta(String strConsecutivoDocumento);
  List<ProductosXCliente> consultarPorClientePuntoVenta(Long idCliente,
      Long idPuntoVenta);

}
