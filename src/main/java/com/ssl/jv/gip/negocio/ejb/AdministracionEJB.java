package com.ssl.jv.gip.negocio.ejb;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import com.ssl.jv.gip.jpa.pojo.Funcionalidad;
import com.ssl.jv.gip.jpa.pojo.Pais;
import com.ssl.jv.gip.jpa.pojo.Parametro;
import com.ssl.jv.gip.jpa.pojo.Rol;
import com.ssl.jv.gip.jpa.pojo.Usuario;
import com.ssl.jv.gip.negocio.dao.FuncionalidadDAO;
import com.ssl.jv.gip.negocio.dao.PaisDAO;
import com.ssl.jv.gip.negocio.dao.ParametroDAO;
import com.ssl.jv.gip.negocio.dao.RolDAO;
import com.ssl.jv.gip.negocio.dao.UsuarioDAO;

/**
 * Session Bean implementation class AdministracionEJB
 */
@Stateless
@LocalBean
public class AdministracionEJB implements AdministracionEJBLocal {

	private static final Logger LOGGER = Logger.getLogger(AdministracionEJB.class);
	
	@EJB
	private UsuarioDAO usuarioDao;
	
	@EJB
	private FuncionalidadDAO funcionalidadDao;

	@EJB
	private PaisDAO paisDao;
	
	@EJB
	private RolDAO rolDao;

	@EJB
	private ParametroDAO parametroDao;
	
    /**
     * Default constructor. 
     */
    public AdministracionEJB() {
        // TODO Auto-generated constructor stub
    }
    
    public Usuario findUsuarioByEmail(String email){
   		return usuarioDao.findByEmail(email);
    }
    
    public List<Funcionalidad> getMenu(String email){
    	return funcionalidadDao.getMenu(email);
    }
    
    public void actualizarUsuario(Usuario u){
    	this.usuarioDao.update(u);
    }

    public void crearUsuario(Usuario u){
   		this.usuarioDao.add(u);
    }
    
    public List<Usuario> consultarUsuarios(){
    	return (List<Usuario>)usuarioDao.findAll();
    }

    public List<Rol> consultarRoles(){
    	return (List<Rol>)rolDao.findAll();
    }

    public List<Pais> consultarPaises(){
    	return (List<Pais>)paisDao.findAll();
    }
    
    public Parametro encontrarParametro(Long id){
    	return parametroDao.findByPK(id);
    }
}
