package com.ssl.jv.gip.negocio.dao;

import java.util.List;

import javax.ejb.Local;

import com.ssl.jv.gip.jpa.pojo.PuntoVenta;
//import com.ssl.jv.gip.negocio.dto.ClienteFiltroVO;

@Local
public interface PuntoVentaDAOLocal extends IGenericDAO<PuntoVenta> {

  void guardarPuntoVenta(PuntoVenta puntoVenta);

  //List<Cliente> consultarPorFiltro(ClienteFiltroVO filtroVO);
  //List<PuntoVenta> consultarActivosPorUsuario(String idUsuario);
  //void guardarCliente(Cliente cliente);
  List<PuntoVenta> findByCliente(Long idCliente);

}
