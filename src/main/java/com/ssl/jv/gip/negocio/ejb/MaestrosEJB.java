package com.ssl.jv.gip.negocio.ejb;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.ssl.jv.gip.jpa.pojo.Ubicacion;
import com.ssl.jv.gip.negocio.dao.UbicacionDAO;

/**
 * Session Bean implementation class MaestrosEJB
 */
@Stateless
@LocalBean
public class MaestrosEJB implements MaestrosEJBLocal {

	@EJB
	private UbicacionDAO ubicacionDAO;
	
    /**
     * Default constructor. 
     */
    public MaestrosEJB() {
        
    }
    
    /* (non-Javadoc)
     * @see com.ssl.jv.gip.negocio.ejb.MaestrosEJBLocal#consultarUbicaciones()
     */
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

}
