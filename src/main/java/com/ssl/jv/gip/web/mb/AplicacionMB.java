package com.ssl.jv.gip.web.mb;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.model.SelectItem;

import com.ssl.jv.gip.jpa.pojo.Pais;
import com.ssl.jv.gip.negocio.ejb.AdministracionEJB;
import com.ssl.jv.gip.web.util.Parametro;

@ManagedBean(name="aplicacionMB")
@ApplicationScoped
public class AplicacionMB {
	
	private static final ResourceBundle msgsEn = ResourceBundle.getBundle("com.ssl.jv.gip.web.mb.MessageResources_en");
	private static final ResourceBundle msgsEs = ResourceBundle.getBundle("com.ssl.jv.gip.web.mb.MessageResources_es");
	
	public static final Integer SPANISH=1;
	public static final Integer ENGLISH=2;
	private List<Pais> paises;
	private static final List<String> images;
	
	private String ambiente;
	private String version;
	private Boolean debug;
	
	public final SelectItem[] siNo= { 
		new SelectItem(new Boolean(true), "SI"),
		new SelectItem(new Boolean(false), "NO"), 
	};
	
	@EJB
	private AdministracionEJB admonEjb;	
	
	static{
		images = new ArrayList<String>();
        for (int i = 1; i <= 3; i++) {
            images.add("Imagen" + i + ".png");
        }		
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
		ambiente=admonEjb.encontrarParametro(Parametro.AMBIENTE.getId()).getValor();
		version=admonEjb.encontrarParametro(Parametro.VERSION.getId()).getValor();
		com.ssl.jv.gip.jpa.pojo.Parametro p = admonEjb.encontrarParametro(Parametro.DEBUG.getId());
		debug=p!=null & p.getValor().equals("SI");
	}
	
	public List<Pais> getPaises() {
		return paises;
	}

	public void setPaises(List<Pais> paises) {
		this.paises = paises;
	}
	
	public Pais getPais(String id){
		for (Pais p:paises){
			if (p.getId().equals(id))
				return p;
		}
		return null;
	}

	public String getAmbiente() {
		return ambiente;
	}

	public void setAmbiente(String ambiente) {
		this.ambiente = ambiente;
	}

	public Boolean getDebug() {
		return debug;
	}

	public void setDebug(Boolean debug) {
		this.debug = debug;
	}

    public List<String> getImages() {
        return images;
    }

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public SelectItem[] getSiNo() {
		return siNo;
	}
    
}
