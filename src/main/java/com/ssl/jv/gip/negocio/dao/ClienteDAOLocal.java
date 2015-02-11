package com.ssl.jv.gip.negocio.dao;

import java.util.List;

import javax.ejb.Local;

import com.ssl.jv.gip.jpa.pojo.Cliente;
import com.ssl.jv.gip.negocio.dto.ClienteFiltroVO;
import java.util.Map;

@Local
public interface ClienteDAOLocal extends IGenericDAO<Cliente> {

	List<Cliente> consultarPorFiltro(ClienteFiltroVO filtroVO);

	List<Cliente> consultarActivosPorUsuario(String idUsuario);
	
	void guardarCliente(Cliente cliente);

    /**
     * 
     * @param parametros
     * @return 
     */
    public List<Cliente> consultarClientesReporteVentasCE(Map<String, Object> parametros);
}
