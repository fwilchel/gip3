package com.ssl.jv.gip.negocio.ejb;

import java.util.List;

import javax.ejb.Local;

import com.ssl.jv.gip.jpa.pojo.Empresa;
import com.ssl.jv.gip.jpa.pojo.Estado;
import com.ssl.jv.gip.jpa.pojo.Pais;
import com.ssl.jv.gip.jpa.pojo.Region;
import com.ssl.jv.gip.jpa.pojo.Ubicacion;

/**
 * The Interface ComunEJBLocal.
 */
@Local
public interface ComunEJBLocal {

	/**
	 * Consultar empresas.
	 *
	 * @return the list
	 */
	public List<Empresa> consultarEmpresas();

	/**
	 * Consultar regiones.
	 *
	 * @return the list
	 */
	public List<Region> consultarRegiones(String pais);

	/**
	 * Consultar paises.
	 *
	 * @return the list
	 */
	public List<Pais> consultarPaises();

	/**
	 * Consultar bodegas abastecedoras.
	 *
	 * @return the list
	 */
	public List<Ubicacion> consultarBodegasAbastecedoras();

	public List<Estado> consultarEstados();

}
