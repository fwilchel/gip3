package com.ssl.jv.gip.web.mb.reportesComercioExterior;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;

import com.ssl.jv.gip.jpa.pojo.Documento;
import com.ssl.jv.gip.negocio.dto.FiltroDocumentoDTO;
import com.ssl.jv.gip.negocio.ejb.ReportesComercioExteriorEJBLocal;
import com.ssl.jv.gip.util.Estado;
import com.ssl.jv.gip.web.mb.UtilMB;
import com.ssl.jv.gip.web.mb.util.ConstantesTipoDocumento;

@ManagedBean(name = "generarInformeTiendaLineaMB")
@ViewScoped
public class GenerarInformeTiendaLineaMB extends UtilMB{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5283083824411596113L;
	
	@EJB
	private ReportesComercioExteriorEJBLocal reportesComercioExteriorEJBLocal;
	
	private static final Logger LOGGER = Logger
			.getLogger(GenerarInformeTiendaLineaMB.class);
	
	private FiltroDocumentoDTO filtroDocumentoDTO;
	
	private List<Documento> listaFacturasExportacion;
	
	private Documento seleccionado;
	
	@PostConstruct
	public void init() {

		try {
			filtroDocumentoDTO = new FiltroDocumentoDTO();
			this.consultarFacturasExportacion();
		} catch (Exception e) {
			LOGGER.error(e);
			this.addMensajeError(e);
		}

	}

	private void consultarFacturasExportacion() {
		try {
			filtroDocumentoDTO
			.setIdTipoDocumento((long) ConstantesTipoDocumento.FACTURA_EXPORTACION);
			filtroDocumentoDTO.setIdEstado(Estado.IMPRESO.getCodigo());
			filtroDocumentoDTO.setIdEstado2(Estado.ANULADO.getCodigo());
			this.listaFacturasExportacion = this.reportesComercioExteriorEJBLocal
					.consultarDocumentosPorTipoDocumentoEstadoTipoCafe(filtroDocumentoDTO);
		} catch (Exception e) {
			LOGGER.error(e);
			this.addMensajeError(e);
		}
	}

	public FiltroDocumentoDTO getFiltroDocumentoDTO() {
		return filtroDocumentoDTO;
	}

	public void setFiltroDocumentoDTO(FiltroDocumentoDTO filtroDocumentoDTO) {
		this.filtroDocumentoDTO = filtroDocumentoDTO;
	}

	public List<Documento> getListaFacturasExportacion() {
		return listaFacturasExportacion;
	}

	public void setListaFacturasExportacion(List<Documento> listaFacturasExportacion) {
		this.listaFacturasExportacion = listaFacturasExportacion;
	}

	public Documento getSeleccionado() {
		return seleccionado;
	}

	public void setSeleccionado(Documento seleccionado) {
		this.seleccionado = seleccionado;
	}
	
	

}
