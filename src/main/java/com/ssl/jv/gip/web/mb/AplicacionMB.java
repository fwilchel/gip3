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

import com.ssl.jv.gip.jpa.pojo.Ciudad;
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
	
	private static String[] MONTHS;
	
	public final SelectItem[] siNo= { 
		new SelectItem(new Boolean(true), "SI"),
		new SelectItem(new Boolean(false), "NO"), 
	};
	
	@EJB
	private static AdministracionEJB admonEjb;	
	
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
    
	/**
	 * Metodo que retorna el list de selectItem con los meses en ingles
	 * @author Sebastian Gamba Pinilla - Soft Studio Ltda.
	 * @email seba.gamba02@gmail.com
	 * @phone 311 8376670
	 * @return
	 */ 
	public List<String> getMonthsEn(){
		return getMonthsByLanguage(ENGLISH);
	}
	
	/**
	 * Metodo que retorna el list de selectItem con los meses en espanol
	 * @author Sebastian Gamba Pinilla - Soft Studio Ltda.
	 * @email seba.gamba02@gmail.com
	 * @phone 311 8376670
	 * @return
	 */ 
	public List<String> getMonthsEs(){
		return getMonthsByLanguage(SPANISH);
	}
	
	/**
	 * Metodo que retorna el listado de meses segun el idioma
	 * @author Sebastian Gamba Pinilla - Soft Studio Ltda.
	 * @email seba.gamba02@gmail.com
	 * @phone 311 8376670
	 * @param language
	 * @return
	 */ 
	private List<String> getMonthsByLanguage(Integer language){
		List<String> monthList = null;
		MONTHS = getMessage("meses", language).split(",");
		if(MONTHS != null){
			monthList = new ArrayList<String>();
			for(String m : MONTHS){
				monthList.add(m);
			}
		}
		return monthList;
	}
	
	/**
	 * Metodo que retorna los tipos de contenedor para los terminos de transporte
	 * @author Sebastian Gamba Pinilla - Soft Studio Ltda.
	 * @email seba.gamba02@gmail.com
	 * @phone 311 8376670
	 * @return
	 */ 
	public List<Integer> getTiposContenedorTerminosTrans(){
		List<Integer> tipos = new ArrayList<Integer>();
		tipos.add(new Integer(20));
		tipos.add(new Integer(40));
		return tipos;
	}
	
	/**
	 * Metodo que consulta las ciudades de una ciudad
	 * @author Sebastian Gamba Pinilla - Soft Studio Ltda.
	 * @email seba.gamba02@gmail.com
	 * @phone 311 8376670
	 * @param idPais
	 * @return
	 */ 
	public static List<Ciudad> getCiudadesList(String idPais){
		return admonEjb.getCiudadesByIdPais(idPais);
	}
	
//	/**
//	 * Metodo que retorna el listado de paises
//	 * @author Sebastian Gamba Pinilla - Soft Studio Ltda.
//	 * @email seba.gamba02@gmail.com
//	 * @phone 311 8376670
//	 * @return
//	 */ 
//	public List<Pais> getPaisesList(){
//		List<SelectItem> listPaises = new ArrayList<SelectItem>();
//		for(Pais p : paises){
//			SelectItem item = new SelectItem();
//			item.setLabel(p.getNombre());
//			item.setValue(p.getId());
//			
//			listPaises.add(item);
//		}
//		return listPaises;
//	}
}
