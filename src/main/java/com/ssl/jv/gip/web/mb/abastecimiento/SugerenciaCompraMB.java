package com.ssl.jv.gip.web.mb.abastecimiento;

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
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;

import org.apache.log4j.Logger;
import org.hibernate.exception.ConstraintViolationException;
import org.primefaces.event.FileUploadEvent;

import com.ssl.jv.gip.jpa.pojo.CategoriasInventario;
import com.ssl.jv.gip.jpa.pojo.Cliente;
import com.ssl.jv.gip.jpa.pojo.Documento;
import com.ssl.jv.gip.jpa.pojo.Estadosxdocumento;
import com.ssl.jv.gip.jpa.pojo.EstadosxdocumentoPK;
import com.ssl.jv.gip.jpa.pojo.ProductosInventario;
import com.ssl.jv.gip.jpa.pojo.ProductosXDocumento;
import com.ssl.jv.gip.jpa.pojo.ProductosXDocumentoPK;
import com.ssl.jv.gip.jpa.pojo.Ubicacion;
import com.ssl.jv.gip.negocio.dto.FiltroDocumentoDTO;
import com.ssl.jv.gip.negocio.dto.ProductosInventarioFiltroDTO;
import com.ssl.jv.gip.negocio.ejb.AbastecimientoEJBLocal;
import com.ssl.jv.gip.negocio.ejb.MaestrosEJBLocal;
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

	@EJB
	private MaestrosEJBLocal maestroEJBLocal;

	@ManagedProperty(value = "#{menuMB}")
	private MenuMB menu;
	private String idUsuario;

	private List<Documento> sugerenciasCompra;

	private Integer language = AplicacionMB.SPANISH;
	private Modo modo;

	private FiltroDocumentoDTO filtroDocumentoDTO;
	private ProductosInventarioFiltroDTO productosInventarioFiltroDTO;

	private Documento seleccionado;
	private List<ProductosInventario> productosInventarios;
	private List<ProductosXDocumento> productosSeleccionados;
	private List<Ubicacion> ubicaciones;
	private List<Cliente> clientes;
	private List<SelectItem> categoriasInventarios;

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
			cargarValoresCombos();
			productosInventarioFiltroDTO = new ProductosInventarioFiltroDTO();
			productosInventarios = new ArrayList<ProductosInventario>();
			productosSeleccionados = new ArrayList<ProductosXDocumento>();
			seleccionado = crearNuevoDocumento();
			this.modo = Modo.CREACION;
			outcome = "sugerencia_compra";
		} catch (Exception e) {
			LOGGER.error(e);
			this.addMensajeError(e);
		}
		return outcome;
	}

	private Documento crearNuevoDocumento() {
		Documento documento = new Documento();
		documento.setUbicacionDestino(new Ubicacion());
		documento.setUbicacionOrigen(new Ubicacion(
				com.ssl.jv.gip.util.Ubicacion.EXTERNA.getCodigo()));

		Estadosxdocumento estadosxdocumento = new Estadosxdocumento();
		EstadosxdocumentoPK id = new EstadosxdocumentoPK();
		id.setIdEstado(Estado.ACTIVO.getCodigo());
		id.setIdTipoDocumento(TipoDocumento.SUGERENCIA_DE_COMPRAS.getCodigo());
		estadosxdocumento.setId(id);

		documento.setEstadosxdocumento(estadosxdocumento);
		documento.setFechaGeneracion(new Date());

		documento.setNumeroFactura("0");

		return documento;
	}

	public String editar() {
		String outcome = null;
		try {
			cargarValoresCombos();
			productosInventarioFiltroDTO = new ProductosInventarioFiltroDTO();
			productosInventarios = new ArrayList<ProductosInventario>();
			productosSeleccionados = abastecimientoEJBLocal
					.consultarProductosXDocumentosPorDocumento(seleccionado
							.getId());
			for (ProductosXDocumento productosXDocumento : productosSeleccionados) {
				productosXDocumento.getProductosInventario().setIncluido(true);
			}
			this.modo = Modo.EDICION;
			outcome = "sugerencia_compra";
		} catch (Exception e) {
			LOGGER.error(e);
			this.addMensajeError(e);
		}
		return outcome;
	}

	private void cargarValoresCombos() {
		ubicaciones = this.abastecimientoEJBLocal
				.consultarUbicacionesPorUsuario(idUsuario);
		clientes = maestroEJBLocal
				.consultarClientesActivosPorUsuario(idUsuario);
		cargarCategoriasInventario();
	}

	private void cargarCategoriasInventario() {
		List<CategoriasInventario> categorias = maestroEJBLocal
				.consultarCategoriasInventarios();
		SelectItemGroup group = null;
		categoriasInventarios = new ArrayList<SelectItem>();
		for (CategoriasInventario categoriasInventario : categorias) {
			group = new SelectItemGroup(categoriasInventario.getNombre());
			group.setSelectItems(getSelectItems(categoriasInventario
					.getCategoriasInventarios()));
			categoriasInventarios.add(group);
		}

	}

	private SelectItem[] getSelectItems(
			List<CategoriasInventario> categoriasInventarios) {
		List<SelectItem> items = new ArrayList<SelectItem>();
		SelectItem item = null;
		for (CategoriasInventario categoriasInventario : categoriasInventarios) {
			item = new SelectItem(categoriasInventario.getId(),
					categoriasInventario.getNombre());
			items.add(item);
		}
		return items.toArray(new SelectItem[categoriasInventarios.size()]);
	}

	public void buscarProductosInventarios(ActionEvent event) {
		try {
			productosInventarioFiltroDTO.setIdUsuario(idUsuario);
			productosInventarioFiltroDTO.setEstado(true);
			if (productosInventarioFiltroDTO.getIdCategoria() == -1) {
				productosInventarioFiltroDTO.setIdCategoria(null);
			}
			if (productosInventarioFiltroDTO.getNombre() != null
					&& productosInventarioFiltroDTO.getNombre().trim()
							.equals("")) {
				productosInventarioFiltroDTO.setNombre(null);
			}
			if (productosInventarioFiltroDTO.getSku() != null
					&& productosInventarioFiltroDTO.getSku().trim().equals("")) {
				productosInventarioFiltroDTO.setSku(null);
			}
			productosInventarios = maestroEJBLocal
					.consultarProductosInventariosPorUsuarioCategoriaSkuNombreAndEstado(productosInventarioFiltroDTO);
			// productosInventarios = maestroFacade
			// .consultarProductosInventariosActivos();
			if (productosInventarios.isEmpty()) {
				this.addMensajeInfo(":formaDlg:msgs2",
						"No se encontraron registros");
			}
		} catch (Exception e) {
			LOGGER.error(e);
			this.addMensajeError(e);
		}
	}

	public void adicionarProductosSeleccionados() {
		try {
			for (ProductosInventario productosInventario : productosInventarios) {
				if (productosInventario.isIncluido()
						&& !estaRelacionado(productosInventario)) {
					productosSeleccionados
							.add(crearProductoXDocumento(productosInventario));
				}
			}
		} catch (Exception e) {
			LOGGER.error(e);
			this.addMensajeError(e);
		}
	}

	private ProductosXDocumento crearProductoXDocumento(
			ProductosInventario productosInventario) {
		ProductosXDocumento productosXDocumento = new ProductosXDocumento();
		ProductosXDocumentoPK id = new ProductosXDocumentoPK();
		id.setIdProducto(productosInventario.getId());
		productosXDocumento.setId(id);
		productosXDocumento.setProductosInventario(productosInventario);
		productosXDocumento.setUnidade(productosInventario.getUnidadVenta());
		productosXDocumento.setCantidad1(BigDecimal.ZERO);
		productosXDocumento.setCantidad2(BigDecimal.ZERO);
		productosXDocumento.setValorUnitatrioMl(BigDecimal.ZERO);
		productosXDocumento.setValorUnitarioUsd(BigDecimal.ZERO);
		return productosXDocumento;
	}

	private boolean estaRelacionado(ProductosInventario productosInventario) {
		boolean relacionado = false;
		for (ProductosXDocumento productosXDocumento : productosSeleccionados) {
			if (productosInventario.getId().equals(
					productosXDocumento.getProductosInventario().getId())) {
				relacionado = true;
				break;
			}
		}
		return relacionado;
	}

	public String guardarSugerenciaCompra() {
		String outcome = null;
		try {

			if (productosSeleccionados.isEmpty()) {
				this.addMensajeError("Debe asociar al menos un producto");
				return outcome;
			}

			seleccionado = abastecimientoEJBLocal.guardarSugerenciaCompra(
					seleccionado, productosSeleccionados);

			int indexOf = sugerenciasCompra.indexOf(seleccionado);
			if (indexOf != -1) {
				sugerenciasCompra.set(indexOf, seleccionado);
			} else {
				sugerenciasCompra.add(0, seleccionado);
			}
			outcome = "lista_sugerencia";
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

	public boolean isCreacion() {
		if (this.modo != null && this.modo.equals(Modo.CREACION)) {
			return true;
		} else {
			return false;
		}
	}

	public List<ProductosInventario> getProductosInventarios() {
		return productosInventarios;
	}

	public void setProductosInventarios(
			List<ProductosInventario> productosInventarios) {
		this.productosInventarios = productosInventarios;
	}

	public ProductosInventarioFiltroDTO getProductosInventarioFiltroDTO() {
		return productosInventarioFiltroDTO;
	}

	public void setProductosInventarioFiltroDTO(
			ProductosInventarioFiltroDTO productosInventarioFiltroDTO) {
		this.productosInventarioFiltroDTO = productosInventarioFiltroDTO;
	}

	public List<Ubicacion> getUbicaciones() {
		return ubicaciones;
	}

	public void setUbicaciones(List<Ubicacion> ubicaciones) {
		this.ubicaciones = ubicaciones;
	}

	public List<Cliente> getClientes() {
		return clientes;
	}

	public void setClientes(List<Cliente> clientes) {
		this.clientes = clientes;
	}

	public List<SelectItem> getCategoriasInventarios() {
		return categoriasInventarios;
	}

	public void setCategoriasInventarios(List<SelectItem> categoriasInventarios) {
		this.categoriasInventarios = categoriasInventarios;
	}

	public List<ProductosXDocumento> getProductosSeleccionados() {
		return productosSeleccionados;
	}

	public void setProductosSeleccionados(
			List<ProductosXDocumento> productosSeleccionados) {
		this.productosSeleccionados = productosSeleccionados;
	}

}
