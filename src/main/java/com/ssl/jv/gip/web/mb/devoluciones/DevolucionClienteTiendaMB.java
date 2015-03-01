package com.ssl.jv.gip.web.mb.devoluciones;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;

import com.ssl.jv.gip.negocio.ejb.DevolucionesEJBLocal;
import com.ssl.jv.gip.web.mb.UtilMB;

@ManagedBean(name = "devolucionClienteTiendaMB")
@ViewScoped
public class DevolucionClienteTiendaMB extends UtilMB {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4547719350295240598L;
	
	private static final Logger LOGGER = Logger
			.getLogger(DevolucionClienteTiendaMB.class);
	
	@EJB
	private DevolucionesEJBLocal devolucionesEJBLocal;
	
	@PostConstruct
	public void init() {}

}
