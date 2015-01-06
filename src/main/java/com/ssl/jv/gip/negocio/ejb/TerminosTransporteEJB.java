package com.ssl.jv.gip.negocio.ejb;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import com.ssl.jv.gip.jpa.pojo.AgenciaCarga;
import com.ssl.jv.gip.jpa.pojo.AgenteAduana;
import com.ssl.jv.gip.jpa.pojo.CategoriasInventario;
import com.ssl.jv.gip.jpa.pojo.Cliente;
import com.ssl.jv.gip.jpa.pojo.CuentaContable;
import com.ssl.jv.gip.jpa.pojo.LugarIncoterm;
import com.ssl.jv.gip.jpa.pojo.ProductosInventario;
import com.ssl.jv.gip.jpa.pojo.ProductosXClienteComExtFiltroVO;
import com.ssl.jv.gip.jpa.pojo.ProductosXClienteComext;
import com.ssl.jv.gip.jpa.pojo.ShipmentConditions;
import com.ssl.jv.gip.jpa.pojo.TerminosTransporte;
import com.ssl.jv.gip.jpa.pojo.Ubicacion;
import com.ssl.jv.gip.jpa.pojo.Unidad;
import com.ssl.jv.gip.negocio.dao.AgenciaCargaDAO;
import com.ssl.jv.gip.negocio.dao.AgenteAduanaDAO;
import com.ssl.jv.gip.negocio.dao.CategoriaInventarioDAOLocal;
import com.ssl.jv.gip.negocio.dao.CuentaContableDAOLocal;
import com.ssl.jv.gip.negocio.dao.LugarIncotermDAO;
import com.ssl.jv.gip.negocio.dao.ProductoClienteComercioExteriorDAO;
import com.ssl.jv.gip.negocio.dao.ProductoInventarioDAOLocal;
import com.ssl.jv.gip.negocio.dao.TerminosTransporteDAOLocal;
import com.ssl.jv.gip.negocio.dao.UbicacionDAO;
import com.ssl.jv.gip.negocio.dao.UnidadDAOLocal;

/**
 * <p>Title: TerminosTransporteEJB</p>
 *
 * <p>Description: Bean para llamado a los DAOs de Terminos de transporte</p>
 *
 * <p>Copyright: Copyright (c) 2014</p>
 *
 * <p>Company: Soft Studio Ltda.</p>
 *
 * @author Sebastian Gamba Pinilla
 * @email sebas.gamba02@gmail.com
 * @phone 311 89376670
 * @version 1.0
 */
@Stateless
@LocalBean
public class TerminosTransporteEJB implements TerminosTransporteEJBLocal {

	private static final Logger LOGGER = Logger.getLogger(TerminosTransporteEJB.class);

	@EJB
	private TerminosTransporteDAOLocal terminosTransporteDAO; 
	
	/**
	 * @see com.ssl.jv.gip.negocio.ejb.TerminosTransporteEJBLocal#consultarListadoTerminosTransporte()
	 * @author Sebastian Gamba Pinilla - Soft Studio Ltda.
	 * @email seba.gamba02@gmail.com
	 * @phone 311 8376670
	 */
	@Override
	public List<ShipmentConditions> consultarListadoTerminosTransporte() {
		return terminosTransporteDAO.getAllShipmentConditions();
	}

	/**
	 * @see com.ssl.jv.gip.negocio.ejb.TerminosTransporteEJBLocal#consultarClientePorIdTerminosTransporte(java.lang.String)
	 * @author Sebastian Gamba Pinilla - Soft Studio Ltda.
	 * @email seba.gamba02@gmail.com
	 * @phone 311 8376670
	 */
	@Override
	public Cliente consultarClientePorIdTerminosTransporte(String idTermTrans) {
		return terminosTransporteDAO.getClientByShipmentConditionsId(idTermTrans);
	}
}
