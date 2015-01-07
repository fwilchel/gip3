package com.ssl.jv.gip.web.mb.maestros;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.ssl.jv.gip.jpa.pojo.AgenteAduana;
import com.ssl.jv.gip.negocio.ejb.MaestrosEJB;
import com.ssl.jv.gip.web.mb.AplicacionMB;
import com.ssl.jv.gip.web.mb.UtilMB;
import com.ssl.jv.gip.web.util.Modo;

@ManagedBean(name="agenteAduanaMB")
@ViewScoped
public class AgenteAduanaMB extends UtilMB{

	private static final long serialVersionUID = 1L;
	
	private List<AgenteAduana> agentes;
	private AgenteAduana seleccionado;
	private AgenteAduana filtro;
	
	@EJB
	private MaestrosEJB servicio;
	
	private Integer language=AplicacionMB.SPANISH;
	
	private Modo modo;
	
	public AgenteAduanaMB(){}
	
	@PostConstruct
	public void init(){
		agentes = servicio.consultarAgentesAduana();
	}
	
	public void setSeleccionado(AgenteAduana seleccionado) {
		this.seleccionado = seleccionado;
		this.modo=Modo.EDICION;
	}
	
	public AgenteAduana getSeleccionado() {
		return seleccionado;
	}
	
	public void nuevo(){
		seleccionado=new AgenteAduana();
		this.modo=Modo.CREACION;
	}
	
	public void guardar() {
		try {
			if (modo.equals(Modo.CREACION)) {
				this.servicio.crearAgenteAduana(seleccionado);
				this.agentes.add(0, this.seleccionado);
				this.modo = Modo.EDICION;
			} else {
				this.servicio.actualizarAgenteAduana(this.seleccionado);
			}
			this.addMensajeInfo("Agente de Aduana almacenado exitosamente");
		} catch (Exception ex) {
			this.addMensajeError(AplicacionMB.getMessage("UsuarioErrorPaginaTexto", language));
		}
	}
	
	public boolean isCreacion(){
		return this.modo!=null && this.modo.equals(Modo.CREACION);
	}

	public List<AgenteAduana> getAgentes() {
		return agentes;
	}

	public void setAgentes(List<AgenteAduana> agentes) {
		this.agentes = agentes;
	}

	public AgenteAduana getFiltro() {
		return filtro;
	}

	public void setFiltro(AgenteAduana filtro) {
		this.filtro = filtro;
	}
	
}
