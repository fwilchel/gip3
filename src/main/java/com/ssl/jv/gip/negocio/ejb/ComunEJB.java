package com.ssl.jv.gip.negocio.ejb;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.ssl.jv.gip.jpa.pojo.Empresa;
import com.ssl.jv.gip.jpa.pojo.Pais;
import com.ssl.jv.gip.jpa.pojo.Region;
import com.ssl.jv.gip.jpa.pojo.Ubicacion;
import com.ssl.jv.gip.negocio.dao.EmpresaDAO;
import com.ssl.jv.gip.negocio.dao.PaisDAO;
import com.ssl.jv.gip.negocio.dao.RegionDAO;
import com.ssl.jv.gip.negocio.dao.UbicacionDAO;

/**
 * Session Bean implementation class ComunEJB
 */
@Stateless
@LocalBean
public class ComunEJB implements ComunEJBLocal {
	
	@EJB
	private PaisDAO paisDao;
	
	@EJB
	private EmpresaDAO empresaDao;
	
	@EJB
	private RegionDAO regionDao;
	
	@EJB
	private UbicacionDAO ubicacionDao;

	/**
	 * Default constructor. 
	 */
	public ComunEJB() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see com.ssl.jv.gip.negocio.ejb.ComunEJBLocal#consultarEmpresas()
	 */
	@Override
	public List<Empresa> consultarEmpresas(){

		List<Empresa> listado = new ArrayList<Empresa>();

		try{
			listado = (List<Empresa>)empresaDao.findAll();
		} catch(Exception e){

		}

		return listado;

	}
	
	/* (non-Javadoc)
	 * @see com.ssl.jv.gip.negocio.ejb.ComunEJBLocal#consultarRegiones()
	 */
	@Override
	public List<Region> consultarRegiones(){
		
		List<Region> listado = new ArrayList<Region>();

		try{
			listado = (List<Region>)regionDao.findAll();
		} catch(Exception e){

		}

		return listado;
		
	}
	
	/* (non-Javadoc)
	 * @see com.ssl.jv.gip.negocio.ejb.ComunEJBLocal#consultarPaises()
	 */
	@Override
	public List<Pais> consultarPaises(){
		
		List<Pais> listado = new ArrayList<Pais>();

		try{
			listado = (List<Pais>)paisDao.findAll();
		} catch(Exception e){

		}

		return listado;
		
	}
	
    /* (non-Javadoc)
     * @see com.ssl.jv.gip.negocio.ejb.ComunEJBLocal#consultarBodegasAbastecedoras()
     */
    @Override
    public List<Ubicacion> consultarBodegasAbastecedoras(){
    	
    	List<Ubicacion> listado = new ArrayList<Ubicacion>();
    	
    	try{
    		
    		Ubicacion filtro = new Ubicacion();
    		filtro.setEsTienda(false);
    		
    		listado = ubicacionDao.consultarUbicacionPorFiltro(filtro);
    		
    	} catch(Exception e){
    		
    	}
    	
    	return listado;
    	
    }

}
