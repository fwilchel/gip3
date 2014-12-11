package com.ssl.jv.gip.negocio.ejb;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.ssl.jv.gip.jpa.pojo.Funcionalidad;
import com.ssl.jv.gip.jpa.pojo.Usuario;
import com.ssl.jv.gip.negocio.dao.FuncionalidadDAO;
import com.ssl.jv.gip.negocio.dao.UsuarioDAO;

/**
 * Session Bean implementation class AdministracionEJB
 */
@Stateless
@LocalBean
public class AdministracionEJB implements AdministracionEJBLocal {

	@EJB
	private UsuarioDAO usuarioDao;
	
	@EJB
	private FuncionalidadDAO funcionalidadDao;
	
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

}
