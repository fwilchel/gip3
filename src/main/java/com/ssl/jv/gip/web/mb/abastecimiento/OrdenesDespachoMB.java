package com.ssl.jv.gip.web.mb.abastecimiento;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;

import com.ssl.jv.gip.jpa.pojo.Documento;
import com.ssl.jv.gip.negocio.dao.DocumentoDAO;
import com.ssl.jv.gip.negocio.ejb.MaestrosEJB;
import com.ssl.jv.gip.negocio.ejb.OrdenDespachoEJB;
import com.ssl.jv.gip.web.mb.AplicacionMB;
import com.ssl.jv.gip.web.mb.UtilMB;
import com.ssl.jv.gip.web.util.Modo;

@ManagedBean(name="ordenesDespachoMB")
@ViewScoped
public class OrdenesDespachoMB extends UtilMB{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	
	private List<Documento> documentos;
	private Documento seleccionado;
	private Documento filtro;
	
	private OrdenDespachoEJB orden;
	
	private Integer language=AplicacionMB.SPANISH;
	
	private Modo modo;

	@PostConstruct
	public void init(){
		documentos = orden.consultarOrdenesDeDespacho();
	}

	public OrdenesDespachoMB() {
	}
	
	public Documento getSeleccionado() {
		return seleccionado;
	}

	public void setSeleccionado(Documento seleccionado) {
		this.seleccionado = seleccionado;
		this.modo=Modo.EDICION;
	}
		
	public List<Documento> getDocumentos() {
		return documentos;
	}

	public void setDocumentos(List<Documento> documentos) {
		this.documentos = documentos;
	}

	public void nuevo(){
		seleccionado=new Documento();
		this.modo=Modo.CREACION;
	}
	
	public boolean esCreacion(){
		return this.modo!=null && this.modo.equals(Modo.CREACION);
	}

	public void guardar() {
		try {
			if (modo.equals(Modo.CREACION)) {
				this.orden.crearOrdenDeDespacho(seleccionado);
				this.documentos.add(0, this.seleccionado);
				this.modo = Modo.EDICION;
			} else {
				this.orden.actualizarOrdenDeDespacho(seleccionado);
			}
			this.addMensajeInfo(AplicacionMB.getMessage("Operación generar orden despacho realizada con éxito", language));
		} catch (Exception ex) {
			this.addMensajeError(AplicacionMB.getMessage("generarOrdenDespachoBotonError", language));
		}
	}

}
