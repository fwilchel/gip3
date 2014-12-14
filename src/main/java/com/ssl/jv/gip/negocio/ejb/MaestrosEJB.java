package com.ssl.jv.gip.negocio.ejb;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.ssl.jv.gip.jpa.pojo.AgenciaCarga;
import com.ssl.jv.gip.jpa.pojo.Ubicacion;
import com.ssl.jv.gip.negocio.dao.AgenciaCargaDAO;
import com.ssl.jv.gip.negocio.dao.UbicacionDAO;
import com.ssl.jv.gip.negocio.dao.UsuarioDAO;

/**
 * Session Bean implementation class MaestrosEJB
 */
@Stateless
@LocalBean
public class MaestrosEJB implements MaestrosEJBLocal {
	
	private static final Logger LOGGER = Logger.getLogger(MaestrosEJB.class);

	@EJB
	private UbicacionDAO ubicacionDAO;
	
	@EJB
	private AgenciaCargaDAO agenciaCargaDAO;
	
    /**
     * Default constructor. 
     */
    public MaestrosEJB() {
        
    }
    
    @Override
    public List<Ubicacion> consultarUbicaciones(){
    	
    	List<Ubicacion> listado = new ArrayList<Ubicacion>();
    	
    	try{
    		listado = (List<Ubicacion>)ubicacionDAO.findAll();
    	} catch(Exception e){
    		
    	}
    	
    	return listado;
    	
    }
    
    /* (non-Javadoc)
     * @see com.ssl.jv.gip.negocio.ejb.MaestrosEJBLocal#consultarUbicaciones(com.ssl.jv.gip.jpa.pojo.Ubicacion)
     */
    @Override
    public List<Ubicacion> consultarUbicaciones(Ubicacion pFiltro){
    	
    	List<Ubicacion> listado = new ArrayList<Ubicacion>();
    	
    	try{
    		listado = ubicacionDAO.consultarUbicacionPorFiltro(pFiltro);
    	} catch(Exception e){
    		
    	}
    	
    	return listado;
    	
    }
    
    /* (non-Javadoc)
     * @see com.ssl.jv.gip.negocio.ejb.MaestrosEJBLocal#consultarUbicacion(java.lang.Long)
     */
    @Override
    public Ubicacion consultarUbicacion(Long pId){
    	
    	Ubicacion entidad = new Ubicacion();
    	
    	try{
    		entidad = ubicacionDAO.findByPK(pId);
    	} catch(Exception e){
    		
    	}
    	
    	return entidad;
    	
    }
    
    /* (non-Javadoc)
     * @see com.ssl.jv.gip.negocio.ejb.MaestrosEJBLocal#crearUbicacion(com.ssl.jv.gip.jpa.pojo.Ubicacion)
     */
    @Override
    public Ubicacion crearUbicacion(Ubicacion pEntidad){
    	    	
    	try{
    		pEntidad = ubicacionDAO.add(pEntidad);
    	} catch(Exception e){
    		
    	}
    	
    	return pEntidad;
    	
    }
    
    /* (non-Javadoc)
     * @see com.ssl.jv.gip.negocio.ejb.MaestrosEJBLocal#actualizarUbicacion(com.ssl.jv.gip.jpa.pojo.Ubicacion)
     */
    @Override
    public Ubicacion actualizarUbicacion(Ubicacion pEntidad){
    	    	
    	try{
    		ubicacionDAO.update(pEntidad);
    	} catch(Exception e){
    		
    	}
    	
    	return pEntidad;
    	
    }

	/* (non-Javadoc)
	 * @see com.ssl.jv.gip.negocio.ejb.MaestrosEJBLocal#consultarAgenciasCarga()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<AgenciaCarga> consultarAgenciasCarga() {
		try{
			return (List<AgenciaCarga>)agenciaCargaDAO.findAll();
		}catch(Exception e){
    		LOGGER.error(e + "Error consultando agencias de carga");
			return null;
    	}
	}

	/* (non-Javadoc)
	 * @see com.ssl.jv.gip.negocio.ejb.MaestrosEJBLocal#consultarAgenciasCarga(com.ssl.jv.gip.jpa.pojo.AgenciaCarga)
	 */
	@Override
	public List<AgenciaCarga> consultarAgenciasCarga(AgenciaCarga pFiltro) {
		try{
			return (List<AgenciaCarga>)agenciaCargaDAO.consultarAgenciaCargaPorFiltro(pFiltro);
		}catch(Exception e){
    		LOGGER.error(e + "Error consultando agencias de carga por filtro");
			return null;
    	}
	}

	@Override
	public AgenciaCarga crearAgenciaCarga(AgenciaCarga pEntidad) {
		try{
			return agenciaCargaDAO.add(pEntidad);
		}catch(Exception e){
    		LOGGER.error(e + "Error creando agencia de carga");
			return null;
    	}
	}

	@Override
	public AgenciaCarga actualizarAgenciaCarga(AgenciaCarga pEntidad) {
		try{
			agenciaCargaDAO.update(pEntidad);
			return pEntidad;
		}catch(Exception e){
    		LOGGER.error(e + "Error actualizando agencia de carga");
			return null;
    	}
	}

}
