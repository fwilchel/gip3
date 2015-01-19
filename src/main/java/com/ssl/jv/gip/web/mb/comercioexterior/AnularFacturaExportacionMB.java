package com.ssl.jv.gip.web.mb.comercioexterior;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.ssl.jv.gip.jpa.pojo.Documento;
import com.ssl.jv.gip.jpa.pojo.Estado;
import com.ssl.jv.gip.negocio.ejb.ComercioExteriorEJBLocal;
import com.ssl.jv.gip.negocio.ejb.ComunEJBLocal;
import com.ssl.jv.gip.web.mb.AplicacionMB;
import com.ssl.jv.gip.web.mb.UtilMB;
import com.ssl.jv.gip.web.mb.util.ConstantesDocumento;
import com.ssl.jv.gip.web.util.Modo;

/**Managed Bean para anular factura de exportación
 * 
 * @author Daniel Cortes
 * @version 1.0
 * @email danicorc@gmail.com
 *
 */
@ManagedBean(name="anularFacturaExportacionMB")
@ViewScoped
public class AnularFacturaExportacionMB extends UtilMB{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer language=AplicacionMB.SPANISH;
	private Modo modo;
	
	private List<Documento> documentos;
	private Documento seleccionado;
	private Documento filtro;
	@EJB
	private ComercioExteriorEJBLocal comercioExteriorEJB;
	@EJB
	private ComunEJBLocal comunEJBlocal;
	
	
	public AnularFacturaExportacionMB() {
	}

	@PostConstruct
	public void init(){
		documentos = comercioExteriorEJB.consultarFacturasDeExportacion();
	}

	public Modo getModo() {
		return modo;
	}

	public void setModo(Modo modo) {
		this.modo = modo;
	}

	public List<Documento> getDocumentos() {
		return documentos;
	}

	public void setDocumentos(List<Documento> documentos) {
		this.documentos = documentos;
	}

	public Documento getSeleccionado() {
		return seleccionado;
	}

	public void setSeleccionado(Documento seleccionado) {
		this.seleccionado = seleccionado;
		this.modo=Modo.EDICION;
	}

	public Documento getFiltro() {
		return filtro;
	}

	public void setFiltro(Documento filtro) {
		this.filtro = filtro;
	}
	
	public void nuevo(){
		seleccionado=new Documento();
		this.modo=Modo.CREACION;
	}
	
	public void anularFactura(){
		try {
			List<Estado> estados = comunEJBlocal.consultarEstados();
			for (Estado estado : estados) {
				if(estado.getId()==ConstantesDocumento.ANULADO){
					seleccionado.getEstadosxdocumento().setEstado(estado);
				}
			}			
			this.addMensajeInfo(AplicacionMB.getMessage("UsuarioExitoPaginaTexto", language));
		} catch (Exception ex) {
			this.addMensajeError(AplicacionMB.getMessage("NivelInventarioError", language));
		}
	}
}
