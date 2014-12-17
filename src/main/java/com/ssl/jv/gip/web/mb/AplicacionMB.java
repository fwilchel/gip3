package com.ssl.jv.gip.web.mb;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.component.EditableValueHolder;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.context.PartialViewContext;

import com.ssl.jv.gip.jpa.pojo.Pais;
import com.ssl.jv.gip.negocio.ejb.AdministracionEJB;

@ManagedBean(name="aplicacionMB")
@ApplicationScoped
public class AplicacionMB {
	
	private static final ResourceBundle msgsEn = ResourceBundle.getBundle("com.ssl.jv.gip.web.mb.MessageResources_en");
	private static final ResourceBundle msgsEs = ResourceBundle.getBundle("com.ssl.jv.gip.web.mb.MessageResources_es");
	
	public static final Integer SPANISH=1;
	public static final Integer ENGLISH=2;
	private List<Pais> paises;	
	
	@EJB
	private AdministracionEJB admonEjb;	
	
	static{
		
	}
	
	public static String getMessage(String llave, Integer language){
		if (language==SPANISH)
			return msgsEs.getString(llave);
		else
			return msgsEn.getString(llave);
	}

	@PostConstruct
	public void init(){
		paises = admonEjb.consultarPaises();
		Collections.sort(paises);
	}
	
	public List<Pais> getPaises() {
		return paises;
	}

	public void setPaises(List<Pais> paises) {
		this.paises = paises;
	}
	
}
