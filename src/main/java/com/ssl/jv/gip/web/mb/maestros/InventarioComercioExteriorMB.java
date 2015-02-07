package com.ssl.jv.gip.web.mb.maestros;

import java.math.BigDecimal;
import java.util.ArrayList;
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

import com.ssl.jv.gip.jpa.pojo.CategoriasInventario;
import com.ssl.jv.gip.jpa.pojo.MovimientosInventarioComext;
import com.ssl.jv.gip.jpa.pojo.ProductosInventario;
import com.ssl.jv.gip.jpa.pojo.ProductosInventarioComext;
import com.ssl.jv.gip.jpa.pojo.TipoMovimiento;
import com.ssl.jv.gip.negocio.dto.ProductosInventarioFiltroDTO;
import com.ssl.jv.gip.negocio.ejb.MaestrosEJBLocal;
import com.ssl.jv.gip.web.mb.AplicacionMB;
import com.ssl.jv.gip.web.mb.MenuMB;
import com.ssl.jv.gip.web.mb.UtilMB;
import com.ssl.jv.gip.web.util.Modo;

/**
 * @author Juan Jose Buzon
 *
 */
@ManagedBean(name = "inventarioComercioExteriorMB")
@SessionScoped
public class InventarioComercioExteriorMB extends UtilMB {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2386637213204199200L;

	private static final Logger LOGGER = Logger
			.getLogger(InventarioComercioExteriorMB.class);

	@ManagedProperty(value = "#{menuMB}")
	private MenuMB menu;
	private String idUsuario;

	private Integer language = AplicacionMB.SPANISH;
	private Modo modo;

	private String sku;
	private List<MovimientosInventarioComext> movimientosInventarioComexts;
	private MovimientosInventarioComext seleccionado;

	@EJB
	private MaestrosEJBLocal maestrosEJBLocal;

	private ProductosInventarioFiltroDTO productosInventarioFiltroDTO;
	private List<ProductosInventario> productosInventarios;
	private List<SelectItem> categoriasInventarios;
	private List<MovimientosInventarioComext> productosSeleccionados;

	@PostConstruct
	public void init() {
		idUsuario = menu.getUsuario().getId();
	}

	public void consultarMovimientosInventarioComExt(ActionEvent actionEvent) {
		try {
			if (sku == null || "".equals(sku)) {
				sku = "%";
			} else {
				sku = "%" + sku + "%";
			}

			movimientosInventarioComexts = maestrosEJBLocal
					.consultarMovimientosInventarioComextsPorSku(sku);

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

	public String nuevo() {
		String outcome = null;
		try {
			modo = Modo.CREACION;
			seleccionado = new MovimientosInventarioComext();
			productosInventarioFiltroDTO = new ProductosInventarioFiltroDTO();
			productosInventarios = new ArrayList<ProductosInventario>();
			productosSeleccionados = new ArrayList<MovimientosInventarioComext>();
			cargarCategoriasInventario();
			outcome = "crear_maestro_inventario_ce";
		} catch (Exception e) {
			LOGGER.error(e);
			this.addMensajeError(e);
		}
		return outcome;
	}

	private void cargarCategoriasInventario() {
		List<CategoriasInventario> categorias = maestrosEJBLocal
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
			productosInventarioFiltroDTO.setEstado(true);
			if (productosInventarioFiltroDTO.getIdCategoria() == -1) {
				productosInventarioFiltroDTO.setIdCategoria(null);
			}
			if (productosInventarioFiltroDTO.getNombre() != null
					&& productosInventarioFiltroDTO.getNombre().trim()
							.equals("")) {
				productosInventarioFiltroDTO.setNombre("%");
			}
			if (productosInventarioFiltroDTO.getSku() != null
					&& productosInventarioFiltroDTO.getSku().trim().equals("")) {
				productosInventarioFiltroDTO.setSku("%");
			}
			productosInventarioFiltroDTO.setControlStock(true);
			productosInventarios = maestrosEJBLocal
					.consultarProductosInventariosPorEstadoCategoriaSkuNombreAndControlStock(productosInventarioFiltroDTO);
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

	public void adicionarProductosSeleccionados() {
		try {
			for (ProductosInventario productosInventario : productosInventarios) {
				if (productosInventario.isIncluido()
						&& !estaRelacionado(productosInventario)) {
					productosSeleccionados
							.add(crearNuevoMovimientosInventarioComext(productosInventario));
				}
			}
		} catch (Exception e) {
			LOGGER.error(e);
			this.addMensajeError(e);
		}
	}

	private MovimientosInventarioComext crearNuevoMovimientosInventarioComext(
			ProductosInventario productosInventario) {
		MovimientosInventarioComext movimientosInventarioComext = new MovimientosInventarioComext();
		ProductosInventarioComext productosInventarioComext = new ProductosInventarioComext();
		productosInventarioComext.setProductosInventario(productosInventario);
		movimientosInventarioComext
				.setProductosInventarioComext(productosInventarioComext);
		movimientosInventarioComext.setCantidad(BigDecimal.ZERO);
		movimientosInventarioComext.setConsecutivoDocumento("INGRESO");
		TipoMovimiento tipoMovimiento = new TipoMovimiento();
		tipoMovimiento.setId(com.ssl.jv.gip.util.TipoMovimiento.ENTRADA
				.getCodigo());
		tipoMovimiento.setNombre(com.ssl.jv.gip.util.TipoMovimiento.ENTRADA
				.getNombre());
		movimientosInventarioComext.setTipoMovimiento(tipoMovimiento);
		movimientosInventarioComext.setSaldo(BigDecimal.ZERO);
		return movimientosInventarioComext;
	}

	private boolean estaRelacionado(ProductosInventario productosInventario) {
		boolean relacionado = false;
		for (MovimientosInventarioComext movimientosInventarioComext : productosSeleccionados) {
			if (productosInventario.getId().equals(
					movimientosInventarioComext.getProductosInventarioComext()
							.getProductosInventario().getId())) {
				relacionado = true;
				break;
			}
		}
		return relacionado;
	}

	public String guardarMovimientosInventario() {
		String outcome = null;
		try {
			if (productosSeleccionados.isEmpty()) {
				this.addMensajeError("No se han incluído productos");
				return outcome;
			}

			for (MovimientosInventarioComext movimientosInventarioComext : productosSeleccionados) {
				movimientosInventarioComext
						.setSaldo(movimientosInventarioComext.getCantidad());
			}

			maestrosEJBLocal
					.guardarMovimientosInventarioComercioExterior(productosSeleccionados);

			movimientosInventarioComexts.addAll(0, productosSeleccionados);

			outcome = "listado_maestro_inventario_ce";
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

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public MenuMB getMenu() {
		return menu;
	}

	public void setMenu(MenuMB menu) {
		this.menu = menu;
	}

	public List<MovimientosInventarioComext> getMovimientosInventarioComexts() {
		return movimientosInventarioComexts;
	}

	public void setMovimientosInventarioComexts(
			List<MovimientosInventarioComext> movimientosInventarioComexts) {
		this.movimientosInventarioComexts = movimientosInventarioComexts;
	}

	public MovimientosInventarioComext getSeleccionado() {
		return seleccionado;
	}

	public void setSeleccionado(MovimientosInventarioComext seleccionado) {
		this.seleccionado = seleccionado;
	}

	public ProductosInventarioFiltroDTO getProductosInventarioFiltroDTO() {
		return productosInventarioFiltroDTO;
	}

	public void setProductosInventarioFiltroDTO(
			ProductosInventarioFiltroDTO productosInventarioFiltroDTO) {
		this.productosInventarioFiltroDTO = productosInventarioFiltroDTO;
	}

	public List<ProductosInventario> getProductosInventarios() {
		return productosInventarios;
	}

	public void setProductosInventarios(
			List<ProductosInventario> productosInventarios) {
		this.productosInventarios = productosInventarios;
	}

	public List<SelectItem> getCategoriasInventarios() {
		return categoriasInventarios;
	}

	public void setCategoriasInventarios(List<SelectItem> categoriasInventarios) {
		this.categoriasInventarios = categoriasInventarios;
	}

	public List<MovimientosInventarioComext> getProductosSeleccionados() {
		return productosSeleccionados;
	}

	public void setProductosSeleccionados(
			List<MovimientosInventarioComext> productosSeleccionados) {
		this.productosSeleccionados = productosSeleccionados;
	}

}
