package com.ssl.jv.gip.web.mb.abastecimiento;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import org.apache.log4j.Logger;
import org.hibernate.exception.ConstraintViolationException;
import org.primefaces.event.FileUploadEvent;

import com.ssl.jv.gip.jpa.pojo.Documento;
import com.ssl.jv.gip.negocio.dto.FiltroDocumentoDTO;
import com.ssl.jv.gip.negocio.ejb.AbastecimientoEJBLocal;
import com.ssl.jv.gip.util.Estado;
import com.ssl.jv.gip.util.TipoDocumento;
import com.ssl.jv.gip.web.mb.AplicacionMB;
import com.ssl.jv.gip.web.mb.MenuMB;
import com.ssl.jv.gip.web.mb.UtilMB;
import com.ssl.jv.gip.web.util.Modo;

@ManagedBean(name = "sugerenciaCompraMB")
@SessionScoped
public class SugerenciaCompraMB extends UtilMB {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2341202221993062048L;

	private static final Logger LOGGER = Logger
			.getLogger(SugerenciaCompraMB.class);

	@EJB
	private AbastecimientoEJBLocal abastecimientoEJBLocal;

	@ManagedProperty(value = "#{menuMB}")
	private MenuMB menu;
	private String idUsuario;

	private List<Documento> sugerenciasCompra;

	private Integer language = AplicacionMB.SPANISH;
	private Modo modo;

	private FiltroDocumentoDTO filtroDocumentoDTO;

	private Documento seleccionado;

	@PostConstruct
	public void init() {
		try {
			idUsuario = menu.getUsuario().getId();
			filtroDocumentoDTO = new FiltroDocumentoDTO();
			consultarSugerenciasCompras();
		} catch (Exception e) {
			LOGGER.error(e);
			this.addMensajeError(e);
		}
	}

	private void consultarSugerenciasCompras() {
		try {
			filtroDocumentoDTO
					.setIdTipoDocumento(TipoDocumento.SUGERENCIA_DE_COMPRAS
							.getCodigo());
			filtroDocumentoDTO.setIdEstado(Estado.ACTIVO.getCodigo());
			this.sugerenciasCompra = this.abastecimientoEJBLocal
					.consultarDocumentosPorTipoYEstado(filtroDocumentoDTO);
		} catch (Exception e) {
			LOGGER.error(e);
			this.addMensajeError(e);
		}
	}

	public String nuevo() {
		String outcome = null;
		try {

		} catch (Exception e) {
			LOGGER.error(e);
			this.addMensajeError(e);
		}
		return outcome;
	}

	public String editar() {
		String outcome = null;
		try {

		} catch (Exception e) {
			LOGGER.error(e);
			this.addMensajeError(e);
		}
		return outcome;
	}

	public void cargarArchivo(FileUploadEvent fileUploadEvent) {
		try {

		} catch (Exception e) {
			LOGGER.error(e);
			Exception unrollException = (Exception) this.unrollException(e,
					ConstraintViolationException.class);
			if (unrollException != null) {
				this.addMensajeError(unrollException.getLocalizedMessage());
			} else {
				this.addMensajeError(e);
			}
		}
	}

	public List<Documento> getSugerenciasCompra() {
		return sugerenciasCompra;
	}

	public void setSugerenciasCompra(List<Documento> sugerenciasCompra) {
		this.sugerenciasCompra = sugerenciasCompra;
	}

	public Integer getLanguage() {
		return language;
	}

	public void setLanguage(Integer language) {
		this.language = language;
	}

	public Modo getModo() {
		return modo;
	}

	public void setModo(Modo modo) {
		this.modo = modo;
	}

	public FiltroDocumentoDTO getFiltroDocumentoDTO() {
		return filtroDocumentoDTO;
	}

	public void setFiltroDocumentoDTO(FiltroDocumentoDTO filtroDocumentoDTO) {
		this.filtroDocumentoDTO = filtroDocumentoDTO;
	}

	public Documento getSeleccionado() {
		return seleccionado;
	}

	public void setSeleccionado(Documento seleccionado) {
		this.seleccionado = seleccionado;
	}

	public MenuMB getMenu() {
		return menu;
	}

	public void setMenu(MenuMB menu) {
		this.menu = menu;
	}

	public String getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}

}
