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
import com.ssl.jv.gip.negocio.dto.DocumentoLotesContribucionCafeteriaDTO;
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
	
	private List<DocumentoLotesContribucionCafeteriaDTO> documentosLotes;
	
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
		documentosLotes = new ArrayList<DocumentoLotesContribucionCafeteriaDTO>();
		
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("consecutivo", seleccionado.getConsecutivo());
		
		documentosLotes = comercioExteriorEjb.consultarDocumentoLotesContribucionCafetera(parametros);
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
		
	public void guardar(){
		documentosLotes = comercioExteriorEjb.guardarDocumentoLotesContribucionCafetera(documentosLotes);
		
		this.addMensajeInfo("Datos de contribución cafetera asignada exitosamente");
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

	public List<DocumentoLotesContribucionCafeteriaDTO> getDocumentosLotes() {
		return documentosLotes;
	}

	public void setDocumentosLotes(
			List<DocumentoLotesContribucionCafeteriaDTO> documentosLotes) {
		this.documentosLotes = documentosLotes;
	}


}
