package com.ssl.jv.gip.web.mb;

import java.util.ResourceBundle;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

@ManagedBean(name="aplicacionMB")
@ApplicationScoped
public class AplicacionMB {
	
	private static final ResourceBundle msgsEn = ResourceBundle.getBundle("com.ssl.jv.gip.web.mb.MessageResources_en");
	private static final ResourceBundle msgsEs = ResourceBundle.getBundle("com.ssl.jv.gip.web.mb.MessageResources_es");
	
	public static final Integer SPANISH=1;
	public static final Integer ENGLISH=2;
	
	static{
		
	}
	
	public static String getMessage(String llave, Integer language){
		if (language==SPANISH)
			return msgsEs.getString(llave);
		else
			return msgsEn.getString(llave);
	}

}
