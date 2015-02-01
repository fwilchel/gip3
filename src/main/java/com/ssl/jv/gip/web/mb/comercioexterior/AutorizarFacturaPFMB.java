package com.ssl.jv.gip.web.mb.comercioexterior;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;

import org.apache.log4j.Logger;

import com.ssl.jv.gip.jpa.pojo.Documento;
import com.ssl.jv.gip.jpa.pojo.DocumentoXNegociacion;
import com.ssl.jv.gip.jpa.pojo.DocumentoXNegociacionPK;
import com.ssl.jv.gip.jpa.pojo.Estadosxdocumento;
import com.ssl.jv.gip.jpa.pojo.EstadosxdocumentoPK;
import com.ssl.jv.gip.jpa.pojo.LogAuditoria;
import com.ssl.jv.gip.jpa.pojo.Moneda;
import com.ssl.jv.gip.jpa.pojo.ProductosXDocumento;
import com.ssl.jv.gip.jpa.pojo.ProductosXDocumentoPK;
import com.ssl.jv.gip.jpa.pojo.Ubicacion;
import com.ssl.jv.gip.jpa.pojo.Unidad;
import com.ssl.jv.gip.negocio.dto.AutorizarDocumentoDTO;
import com.ssl.jv.gip.negocio.dto.ProductoGenerarFacturaPFDTO;
import com.ssl.jv.gip.negocio.ejb.ComercioExteriorEJBLocal;
import com.ssl.jv.gip.web.mb.AplicacionMB;
import com.ssl.jv.gip.web.mb.MenuMB;
import com.ssl.jv.gip.web.mb.UtilMB;
import com.ssl.jv.gip.web.mb.util.ConstantesDocumento;
import com.ssl.jv.gip.web.mb.util.ConstantesTipoDocumento;
import com.ssl.jv.gip.web.util.Utilidad;

@ManagedBean(name="autorizarFacturaPFMB")
@SessionScoped
public class AutorizarFacturaPFMB extends UtilMB{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2780795923623719268L;

	private static final Logger LOGGER = Logger.getLogger(AutorizarFacturaPFMB.class);
	private Timestamp currentTimeStamp;
	private String consecutivoDocumento;
	private Integer language=AplicacionMB.SPANISH;
	private List<AutorizarDocumentoDTO> listaDocumentos;
	private boolean deshabilitado=false;
	
	@EJB
	private ComercioExteriorEJBLocal comercioEjb;
	
	@ManagedProperty(value="#{menuMB}")
	private MenuMB menu;
	
	public AutorizarFacturaPFMB(){
		
	}
	
	@PostConstruct
	public void init(){
		currentTimeStamp = new Timestamp(System.currentTimeMillis());
		
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

	public String buscarDocumentos(){
		listaDocumentos=this.comercioEjb.consultarDocumentosAutorizar(consecutivoDocumento);
		this.deshabilitado=false;
		return null;
	}
	
	public String autorizarDocumentos(){
		if(listaDocumentos != null && listaDocumentos.size() > 0){
			
			List<AutorizarDocumentoDTO> listado = new ArrayList<AutorizarDocumentoDTO>();
			
			for(AutorizarDocumentoDTO dto : listaDocumentos){
				if(dto.isSeleccionado()){
					listado.add(dto);
				}
			}
			
			if(listado.size() > 0){
				
				comercioEjb.cambiarEstadoFacturaProforma(listado);
				
				String mensaje = AplicacionMB.getMessage("PedidoExitoPaginaBoton", language);
				
				this.addMensajeInfo(mensaje);
				
				buscarDocumentos();
				
			} else {
				String mensaje = AplicacionMB.getMessage("debeSeleccionarFacturaProforma", language);
				
				this.addMensajeInfo(mensaje);
			}
		} else {
			String mensaje = AplicacionMB.getMessage("debeSeleccionarFacturaProforma", language);
			
			this.addMensajeInfo(mensaje);
		}
		return null;
	}

	public boolean isDeshabilitado() {
		return deshabilitado;
	}

	public void setDeshabilitado(boolean deshabilitado) {
		this.deshabilitado = deshabilitado;
	}

	public String autorizarFacturaProforma(){
		
		return null;
	}

	public MenuMB getMenu() {
		return menu;
	}

	public void setMenu(MenuMB menu) {
		this.menu = menu;
	}

	public List<AutorizarDocumentoDTO> getListaDocumentos() {
		return listaDocumentos;
	}

	public void setListaDocumentos(List<AutorizarDocumentoDTO> listaDocumentos) {
		this.listaDocumentos = listaDocumentos;
	}
	
}
