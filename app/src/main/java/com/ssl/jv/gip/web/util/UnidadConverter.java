package com.ssl.jv.gip.web.util;

import java.util.ArrayList;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.ssl.jv.gip.jpa.pojo.Unidad;

@FacesConverter("unidadesConverter")
public class UnidadConverter implements Converter{

	  //@ManagedProperty(value = "#{aplicacionMB}")
	  //private AplicacionMB appMB;
	
	private static List<Unidad> unidades=new ArrayList<Unidad>();
	
	static{
		for (int i=1; i<21; i++){
			unidades.add(new Unidad((long)i, "Unidad"+i, "A"+i));	
		}
	}
	  
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		for (Unidad u:unidades){
			if (arg2.equals(""+u.getId()))
				return u;
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		/*Long id=(Long)arg2;
		for (Unidad u:unidades){
			if (id.equals(u.getId()))
				return u.getAbreviacion();
		}*/
		return ""+arg2;
	}

}
