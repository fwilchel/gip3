package com.ssl.jv.gip.negocio.ejb;

import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

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
	
	@Resource(mappedName="java:jboss/mail/Default")    
	private Session mailSessionServer;
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
    
	public void enviarEmail(String strEmail, String strContrasena,
			String nombre, String apellido) {

		MimeMessage message = new MimeMessage( mailSessionServer );       
		try {
			message.setFrom( new InternetAddress( "fredy.wilches@gmail.com"));
			//message.setReplyTo( new Address[]{new InternetAddress( strEmail )} );       
			message.addRecipient( Message.RecipientType.TO, new InternetAddress( strEmail ) );       
			message.setHeader( "Content-Type", "text/html" + "; charset=\"" + "charset=iso-8859-1" + "\"");       
			message.setSubject( "Juan Valdez - Gip3 - Recordar Datos Acceso" );
			
			String sMailText="Sr(a) " + nombre.toUpperCase() + " "
					+ apellido.toUpperCase() + "\n\n"
					+ "Sus datos para acceso al sistema GIP3 son :\n\n"
					+ "Usuario: " + strEmail + "\n" + "Contraseña: "
					+ strContrasena + "\n\n"
					+ "Cualquier duda comuníquese con el administrador";
			
			message.setText( sMailText );       
			Transport.send( message );
		} catch (AddressException e) {
			LOGGER.error(e);
		} catch (MessagingException e) {
			LOGGER.error(e);
		}       
		
	}
	
    
}
