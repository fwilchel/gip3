package com.ssl.jv.gip.web.mb.comercioexterior;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.ssl.jv.gip.negocio.dto.DatoContribucionCafeteraDTO;
import com.ssl.jv.gip.negocio.ejb.ComercioExterior;
import com.ssl.jv.gip.web.mb.UtilMB;
import com.ssl.jv.gip.web.mb.util.ConstantesDocumento;
import com.ssl.jv.gip.web.mb.util.ConstantesTipoDocumento;
import com.ssl.jv.gip.web.util.Modo;

@ManagedBean(name="asignarDatosContribucionCafeteraMB")
@SessionScoped
public class AsignarDatosContribucionCafeteraMB extends UtilMB{
	
	private List<DatoContribucionCafeteraDTO> listado;
	private DatoContribucionCafeteraDTO seleccionado;
	
	private Modo modo;
	
	@EJB
	private ComercioExterior comercioExteriorEjb;	
	
	public AsignarDatosContribucionCafeteraMB(){
		
	}
	
	@PostConstruct
	public void init(){
		
		Map<String, Object> parametros = new HashMap<String, Object>();
		
		parametros.put("tipo", ConstantesTipoDocumento.FACTURA_EXPORTACION);
		parametros.put("estado", ConstantesDocumento.IMPRESO);
		
		listado = comercioExteriorEjb.consultarDatosContribucionCafetera(parametros);
	}
	
	public String modificar(){
		
		return "";
	}

	public Modo getModo() {
		return modo;
	}

	public void setModo(Modo modo) {
		this.modo = modo;
	}

	public DatoContribucionCafeteraDTO getSeleccionado() {
		return seleccionado;
	}

	public void setSeleccionado(DatoContribucionCafeteraDTO seleccionado) {
		this.seleccionado = seleccionado;
		this.modo=Modo.EDICION;
	}
	
	public void nuevo(){
		seleccionado=new DatoContribucionCafeteraDTO();
		this.modo=Modo.CREACION;
	}
	
	public void guardar(){
//		if (this.modo.equals(Modo.CREACION)){
//			this.seleccionado = this.maestroEjb.crearLugarIncoterm(this.seleccionado);
//			if(this.lugarIncoterm == null){
//				this.lugarIncoterm = new ArrayList<LugarIncoterm>();
//			}
//			this.lugarIncoterm.add(0,this.seleccionado);
//		}else{
//			this.maestroEjb.actualizarLugarIncoterm(this.seleccionado);
//		}
//		
//		this.addMensajeInfo("LugarIncoterm almacenado exitosamente");
	}
		
	public boolean isCreacion(){
		if (this.modo!=null && this.modo.equals(Modo.CREACION)){
			return true;
		}else{
			return false;
		}
	}

	public List<DatoContribucionCafeteraDTO> getListado() {
		return listado;
	}

	public void setListado(List<DatoContribucionCafeteraDTO> listado) {
		this.listado = listado;
	}


}
