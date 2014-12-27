package com.ssl.jv.gip.web.mb.maestros;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ValueChangeEvent;

import com.ssl.jv.gip.jpa.pojo.Empresa;
import com.ssl.jv.gip.jpa.pojo.Pais;
import com.ssl.jv.gip.jpa.pojo.Region;
import com.ssl.jv.gip.jpa.pojo.Rol;
import com.ssl.jv.gip.jpa.pojo.Ubicacion;
import com.ssl.jv.gip.jpa.pojo.Usuario;
import com.ssl.jv.gip.negocio.ejb.AdministracionEJB;
import com.ssl.jv.gip.negocio.ejb.ComunEJB;
import com.ssl.jv.gip.negocio.ejb.MaestrosEJB;
import com.ssl.jv.gip.web.mb.UtilMB;
import com.ssl.jv.gip.web.util.Modo;

@ManagedBean(name="ubicacionMB")
@SessionScoped
public class UbicacionMB extends UtilMB{
	
	private List<Ubicacion> ubicacion;
	private Ubicacion seleccionado;
	
	private List<Pais> paises;
	
	private List<Region> regiones;
	
	private List<Ubicacion> bodegas;
	
	private List<Empresa> empresas;
	
	private Modo modo;
	
	@EJB
	private MaestrosEJB maestroEjb;	
	
	@EJB
	private ComunEJB comunEJB;
	
	private Region region;
	
	private Pais pais;
		
	public UbicacionMB(){
		
	}
	
	@PostConstruct
	public void init(){
		ubicacion = maestroEjb.consultarUbicaciones();
		paises = comunEJB.consultarPaises();
		bodegas = comunEJB.consultarBodegasAbastecedoras();
		empresas = comunEJB.consultarEmpresas();
	}
	
	public String modificar(){
		regiones = comunEJB.consultarRegiones(seleccionado.getRegione().getPais().getId());
		
		pais = seleccionado.getRegione() != null ? seleccionado.getRegione().getPais() : new Pais();
		
		region = seleccionado.getRegione();
		
		return "";
	}

	public Modo getModo() {
		return modo;
	}

	public void setModo(Modo modo) {
		this.modo = modo;
	}

	public Ubicacion getSeleccionado() {
		return seleccionado;
	}

	public void setSeleccionado(Ubicacion seleccionado) {
		this.seleccionado = seleccionado;
		this.modo=Modo.EDICION;
	}
	
	public void nuevo(){
		region=new Region();
		pais = new Pais();
		seleccionado=new Ubicacion();
		seleccionado.setUbicacione(new Ubicacion());
		seleccionado.setEmpresa(new Empresa());
		regiones = comunEJB.consultarRegiones(paises.get(0).getId());
		this.modo=Modo.CREACION;
	}
	
	public void guardar(){
		
//		Region reg = new Region();
//		
//		if(this.getRegiones() != null){
//			for(Region r : this.getRegiones()){
//				if(r.getId().longValue() == seleccionado.getRegione().getId().longValue()){
//					reg = r;
//					break;
//				}
//			}
//		}
//		
//		if(this.getPaises() != null){
//			for(Pais p : this.getPaises()){
//				if(p.getId().equals(seleccionado.getRegione().getPais().getId())){
//					reg.setPais(p);
//					break;
//				}
//			}
//		}
//		
//		seleccionado.setRegione(reg);
//		
//		if(this.getEmpresas() != null){
//			for(Empresa e : this.getEmpresas()){
//				if(e.getId().longValue() == seleccionado.getEmpresa().getId().longValue()){
//					seleccionado.setEmpresa(e);
//					break;
//				}
//			}
//		}
//		
//		if(this.getBodegas() != null){
//			for(Ubicacion u : this.getBodegas()){
//				if(u.getId().longValue() == seleccionado.getUbicacione().getId().longValue()){
//					seleccionado.setUbicacione(u);;
//					break;
//				}
//			}
//		}
		
		if (this.modo.equals(Modo.CREACION)){
			seleccionado.setRegione(region);
			this.seleccionado = this.maestroEjb.crearUbicacion(this.seleccionado);		
			//this.ubicacion.add(0,this.seleccionado);
		}else{
			seleccionado.setRegione(region);
			this.maestroEjb.actualizarUbicacion(this.seleccionado);
		}
		
		ubicacion = maestroEjb.consultarUbicaciones();
		
		this.addMensajeInfo("Ubicacion almacenado exitosamente");
	}
	
	public void cambioPais(){
		regiones = comunEJB.consultarRegiones(pais.getId());
	}
	
	public boolean isCreacion(){
		if (this.modo!=null && this.modo.equals(Modo.CREACION)){
			return true;
		}else{
			return false;
		}
	}

	public List<Ubicacion> getUbicacion() {
		return ubicacion;
	}

	public void setUbicacion(List<Ubicacion> ubicacion) {
		this.ubicacion = ubicacion;
	}

	public List<Pais> getPaises() {
		return paises;
	}

	public void setPaises(List<Pais> paises) {
		this.paises = paises;
	}

	public List<Region> getRegiones() {
		return regiones;
	}

	public void setRegiones(List<Region> regiones) {
		this.regiones = regiones;
	}

	public List<Ubicacion> getBodegas() {
		return bodegas;
	}

	public void setBodegas(List<Ubicacion> bodegas) {
		this.bodegas = bodegas;
	}

	public List<Empresa> getEmpresas() {
		return empresas;
	}

	public void setEmpresas(List<Empresa> empresas) {
		this.empresas = empresas;
	}

	public Region getRegion() {
		return region;
	}

	public void setRegion(Region region) {
		this.region = region;
	}

	public Pais getPais() {
		return pais;
	}

	public void setPais(Pais pais) {
		this.pais = pais;
	}

}
