package com.ssl.jv.gip.web.mb.comercioexterior;

import java.sql.Timestamp;
import java.text.MessageFormat;
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

/**Managed Bean para anular factura de exportacion
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
	
	private String mensaje;
	private Timestamp currentTimeStamp;
	private String consecutivoDocumento;
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
		currentTimeStamp = new Timestamp(System.currentTimeMillis());
	}

	public String buscarDocumentos() {
		documentos = comercioExteriorEJB.consultarFacturasDeExportacionEstado(consecutivoDocumento);
		return null;
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
		mensaje=MessageFormat.format(AplicacionMB.getMessage("AdvertenciaAnularFX",1),seleccionado.getConsecutivoDocumento());
		this.seleccionado = seleccionado;
	}

	public Documento getFiltro() {
		return filtro;
	}

	public void setFiltro(Documento filtro) {
		this.filtro = filtro;
	}
	
	public Timestamp getCurrentTimeStamp() {
		return currentTimeStamp;
	}

	public void setCurrentTimeStamp(Timestamp currentTimeStamp) {
		this.currentTimeStamp = currentTimeStamp;
	}

	public String getConsecutivoDocumento() {
		return consecutivoDocumento;
	}

	public void setConsecutivoDocumento(String consecutivoDocumento) {
		this.consecutivoDocumento = consecutivoDocumento;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public void anularFactura(){
		try {
			List<Estado> estados = comunEJBlocal.consultarEstados();
			for (Estado estado : estados) {
				if(estado.getId()==ConstantesDocumento.ANULADO){
					seleccionado.getEstadosxdocumento().setEstado(estado);
					this.comercioExteriorEJB.actualizarEstadoDocumento(this.seleccionado);
					documentos = comercioExteriorEJB.consultarFacturasDeExportacionEstado(consecutivoDocumento);
				}
			}			
			this.addMensajeInfo(AplicacionMB.getMessage("UsuarioExitoPaginaTexto", language));
		} catch (Exception ex) {
			ex.printStackTrace();
			this.addMensajeError(AplicacionMB.getMessage("NivelInventarioError", language));
		}
	}
}
