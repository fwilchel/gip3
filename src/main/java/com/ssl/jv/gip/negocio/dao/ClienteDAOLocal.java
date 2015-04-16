package com.ssl.jv.gip.negocio.dao;

import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import com.ssl.jv.gip.jpa.pojo.Cliente;
import com.ssl.jv.gip.negocio.dto.ClienteFiltroVO;

@Local
public interface ClienteDAOLocal extends IGenericDAO<Cliente> {

  List<Cliente> consultarPorFiltro(ClienteFiltroVO filtroVO);

  List<Cliente> consultarActivosPorUsuario(String idUsuario);

  Cliente guardarCliente(Cliente cliente);

  /**
   *
   * @param parametros
   * @return
   */
  List<Cliente> consultarListadoClientesReporteVentasCE(Map<String, Object> parametros);

  List<Cliente> consultarActivosInternacionales();
}
