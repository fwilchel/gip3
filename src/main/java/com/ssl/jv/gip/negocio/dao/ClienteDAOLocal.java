package com.ssl.jv.gip.negocio.dao;

import java.util.List;

import javax.ejb.Local;

import com.ssl.jv.gip.jpa.pojo.Cliente;
import com.ssl.jv.gip.negocio.dto.ClienteFiltroVO;
import java.util.Map;

@Local
public interface ClienteDAOLocal extends IGenericDAO<Cliente> {

	public List<Cliente> consultarPorFiltro(ClienteFiltroVO filtroVO);

	public List<Cliente> consultarActivosPorUsuario(String idUsuario);
	
	public void guardarCliente(Cliente cliente);

    /**
     * 
     * @param parametros
     * @return 
     */
    public List<Cliente> consultarListadoClientesReporteVentasCE(Map<String, Object> parametros);
    
    public List<Cliente> consultarActivosInternacionales();
}
