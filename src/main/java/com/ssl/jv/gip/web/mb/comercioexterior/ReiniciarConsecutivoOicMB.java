package com.ssl.jv.gip.web.mb.comercioexterior;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.ssl.jv.gip.jpa.pojo.DocumentoXLotesoic;
import com.ssl.jv.gip.negocio.ejb.ComercioExteriorEJB;
import com.ssl.jv.gip.web.mb.UtilMB;

/**
 * The Class ReiniciarConsecutivoOicMB.
 */
@ManagedBean(name="reiniciarConsecutivoOicMB")
@SessionScoped
public class ReiniciarConsecutivoOicMB extends UtilMB{
	
	/** The lista lotes. */
	private List<DocumentoXLotesoic> listaLotes;
	
	
	/** The comercio ejb. */
	@EJB
	private ComercioExteriorEJB comercioEjb;	
	
	/**
	 * Instantiates a new reiniciar consecutivo oic mb.
	 */
	public ReiniciarConsecutivoOicMB(){
		
	}
	
	/**
	 * Inits the.
	 */
	@PostConstruct
	public void init(){
		listaLotes = comercioEjb.consultarDocumentosPorLoteOIC();
	}
	
	
	
	/**
	 * Reiniciar consecutivo lote.
	 */
	public void reiniciarConsecutivoLote(){
		this.comercioEjb.reiniciarConsecutivoLoteOIC();				
		this.addMensajeInfo("El consecutivo de lotes fue reinciado exitosamente.");
	}

	/**
	 * Gets the lista lotes.
	 *
	 * @return the lista lotes
	 */
	public List<DocumentoXLotesoic> getListaLotes() {
		return listaLotes;
	}

	/**
	 * Sets the lista lotes.
	 *
	 * @param listaLotes the new lista lotes
	 */
	public void setListaLotes(List<DocumentoXLotesoic> listaLotes) {
		this.listaLotes = listaLotes;
	}
		
	

}
