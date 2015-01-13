package com.ssl.jv.gip.web.mb.comercioexterior;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;

import org.apache.log4j.Logger;
import org.hibernate.exception.ConstraintViolationException;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import com.ssl.jv.gip.jpa.pojo.Documento;
import com.ssl.jv.gip.jpa.pojo.ProductosInventario;
import com.ssl.jv.gip.negocio.ejb.ComercioExteriorEJBLocal;
import com.ssl.jv.gip.web.mb.AplicacionMB;
import com.ssl.jv.gip.web.mb.MenuMB;
import com.ssl.jv.gip.web.mb.UtilMB;
import com.ssl.jv.gip.web.mb.util.ConstantesTipoDocumento;
import com.ssl.jv.gip.web.util.Modo;

/**
 * @author Juan Jose Buzon
 *
 */
@ManagedBean(name = "modificarListaEmpaqueMB")
@SessionScoped
public class ModificarListaEmpaqueMB extends UtilMB {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4940573106269006240L;

	private static final Logger LOGGER = Logger
			.getLogger(ModificarListaEmpaqueMB.class);

	@EJB
	private ComercioExteriorEJBLocal comercioExteriorEJBLocal;

	@ManagedProperty(value = "#{menuMB}")
	private MenuMB menu;
	private String idUsuario;

	private List<Documento> listaEmpaques;

	private Integer language = AplicacionMB.SPANISH;
	private Modo modo;

	private Documento seleccionado;

	private Date fechaActual;
	private String consecutivoDocumento;

	private List<ProductosInventario> productosInventarios;

	private BigDecimal totalCantidad;
	private BigDecimal totalPallets;
	private BigDecimal totalCajas;
	private BigDecimal totalPesoNeto;
	private BigDecimal totalPesoBruto;

	@PostConstruct
	public void init() {
		productosInventarios = new ArrayList<ProductosInventario>();
		totalCantidad = BigDecimal.ZERO;
		totalPallets = BigDecimal.ZERO;
		totalCajas = BigDecimal.ZERO;
		totalPesoNeto = BigDecimal.ZERO;
		totalPesoBruto = BigDecimal.ZERO;
		fechaActual = new Date();
	}

	public void consultarListaEmpaques(ActionEvent actionEvent) {
		try {
			consecutivoDocumento = "%" + consecutivoDocumento + "%";
			listaEmpaques = comercioExteriorEJBLocal
					.consultarDocumentosActivosPorTipoDocumentoYConsecutivoDocumento(
							(long) ConstantesTipoDocumento.LISTA_EMPAQUE,
							consecutivoDocumento);
		} catch (Exception e) {
			LOGGER.error(e);
			Exception unrollException = (Exception) this.unrollException(e,
					ConstraintViolationException.class);
			if (unrollException != null) {
				this.addMensajeError(unrollException.getLocalizedMessage());
			} else {
				unrollException = (Exception) this.unrollException(e,
						RuntimeException.class);
				if (unrollException != null) {
					this.addMensajeError(unrollException.getLocalizedMessage());
				} else {
					this.addMensajeError(e);
				}
			}
		}
	}

	public String editar() {
		String outcome = null;
		try {
			this.modo = Modo.EDICION;
			outcome = "modificar_lista_empaque";
		} catch (Exception e) {
			LOGGER.error(e);
			this.addMensajeError(e);
		}
		return outcome;
	}

	public void cargarArchivo(FileUploadEvent fileUploadEvent) {
		try {
			UploadedFile uploadedFile = fileUploadEvent.getFile();
			this.addMensajeInfo("Archivo cargado con éxito");
		} catch (Exception e) {
			LOGGER.error(e);
			Exception unrollException = (Exception) this.unrollException(e,
					ConstraintViolationException.class);
			if (unrollException != null) {
				this.addMensajeError(unrollException.getLocalizedMessage());
			} else {
				unrollException = (Exception) this.unrollException(e,
						RuntimeException.class);
				if (unrollException != null) {
					this.addMensajeError(unrollException.getLocalizedMessage());
				} else {
					this.addMensajeError(e);
				}
			}
		}
	}

	public String modificarListaEmpaque() {
		String outcome = null;
		try {
			consecutivoDocumento = null;
			outcome = "listado_LE4";
		} catch (Exception e) {
			LOGGER.error(e);
			this.addMensajeError(e);
		}
		return outcome;
	}

	public boolean isCreacion() {
		if (this.modo != null && this.modo.equals(Modo.CREACION)) {
			return true;
		} else {
			return false;
		}
	}

	public String getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}

	public List<Documento> getListaEmpaques() {
		return listaEmpaques;
	}

	public void setListaEmpaques(List<Documento> listaEmpaques) {
		this.listaEmpaques = listaEmpaques;
	}

	public Modo getModo() {
		return modo;
	}

	public void setModo(Modo modo) {
		this.modo = modo;
	}

	public Documento getSeleccionado() {
		return seleccionado;
	}

	public void setSeleccionado(Documento seleccionado) {
		this.seleccionado = seleccionado;
	}

	public String getConsecutivoDocumento() {
		return consecutivoDocumento;
	}

	public void setConsecutivoDocumento(String consecutivoDocumento) {
		this.consecutivoDocumento = consecutivoDocumento;
	}

	public Date getFechaActual() {
		return fechaActual;
	}

	public void setFechaActual(Date fechaActual) {
		this.fechaActual = fechaActual;
	}

	public MenuMB getMenu() {
		return menu;
	}

	public void setMenu(MenuMB menu) {
		this.menu = menu;
	}

	public BigDecimal getTotalCantidad() {
		return totalCantidad;
	}

	public void setTotalCantidad(BigDecimal totalCantidad) {
		this.totalCantidad = totalCantidad;
	}

	public BigDecimal getTotalPallets() {
		return totalPallets;
	}

	public void setTotalPallets(BigDecimal totalPallets) {
		this.totalPallets = totalPallets;
	}

	public BigDecimal getTotalCajas() {
		return totalCajas;
	}

	public void setTotalCajas(BigDecimal totalCajas) {
		this.totalCajas = totalCajas;
	}

	public BigDecimal getTotalPesoNeto() {
		return totalPesoNeto;
	}

	public void setTotalPesoNeto(BigDecimal totalPesoNeto) {
		this.totalPesoNeto = totalPesoNeto;
	}

	public BigDecimal getTotalPesoBruto() {
		return totalPesoBruto;
	}

	public void setTotalPesoBruto(BigDecimal totalPesoBruto) {
		this.totalPesoBruto = totalPesoBruto;
	}

	public List<ProductosInventario> getProductosInventarios() {
		return productosInventarios;
	}

	public void setProductosInventarios(
			List<ProductosInventario> productosInventarios) {
		this.productosInventarios = productosInventarios;
	}

}
