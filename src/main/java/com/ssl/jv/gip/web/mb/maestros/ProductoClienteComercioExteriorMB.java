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
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import com.ssl.jv.gip.jpa.pojo.CategoriasInventario;
import com.ssl.jv.gip.jpa.pojo.Cliente;
import com.ssl.jv.gip.jpa.pojo.Moneda;
import com.ssl.jv.gip.jpa.pojo.ProductosInventario;
import com.ssl.jv.gip.jpa.pojo.ProductosXClienteComExtFiltroVO;
import com.ssl.jv.gip.jpa.pojo.ProductosXClienteComext;
import com.ssl.jv.gip.jpa.pojo.ProductosXClienteComextPK;
import com.ssl.jv.gip.negocio.dto.ProductosInventarioFiltroDTO;
import com.ssl.jv.gip.negocio.ejb.MaestrosEJBLocal;
import com.ssl.jv.gip.web.mb.AplicacionMB;
import com.ssl.jv.gip.web.mb.MenuMB;
import com.ssl.jv.gip.web.mb.UtilMB;
import com.ssl.jv.gip.web.util.Modo;

@ManagedBean(name = "productoClienteComercioExteriorMB")
@SessionScoped
public class ProductoClienteComercioExteriorMB extends UtilMB {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5062780818102804030L;

	private static final Logger LOGGER = Logger
			.getLogger(ProductoClienteComercioExteriorMB.class);

	private List<ProductosXClienteComext> productosXClienteComExteriorList;
	private ProductosXClienteComext seleccionado;
	private ProductosXClienteComExtFiltroVO filtro;

	private List<Cliente> clientes;
	private List<Moneda> monedas;
	private List<SelectItem> categoriasInventarios;
	private List<ProductosInventario> productosInventarios;
	private List<ProductosXClienteComext> productosSeleccionados;

	private UploadedFile uploadedFile;

	@EJB
	private MaestrosEJBLocal maestroFacade;

	private Integer language = AplicacionMB.SPANISH;
	private Modo modo;

	@ManagedProperty(value = "#{menuMB}")
	private MenuMB menu;

	private ProductosInventarioFiltroDTO productosInventarioFiltroDTO;

	private String idUsuario;

	public ProductoClienteComercioExteriorMB() {
		super();
	}

	@PostConstruct
	public void init() {
		try {
			idUsuario = menu.getUsuario().getId();
			productosXClienteComExteriorList = maestroFacade
					.consultarProductosClienteComercioExterior();
		} catch (Exception e) {
			LOGGER.error(e);
			this.addMensajeError(e);
		}
	}

	public void listarProductosClientesComercioExtActivos() {
		try {
			filtro.setActivo(true);
			productosXClienteComExteriorList = maestroFacade
					.consultarProductosClienteComercioExteriorPorFiltro(filtro);
			if (productosXClienteComExteriorList == null
					|| productosXClienteComExteriorList.isEmpty()) {
				this.addMensajeInfo("No se encontraron registros");
			}
		} catch (Exception e) {
			LOGGER.error(e);
			this.addMensajeError(e);
		}
	}

	public void listarProductosClientesComercioExt() {
		try {
			filtro.setActivo(null);
			productosXClienteComExteriorList = maestroFacade
					.consultarProductosClienteComercioExteriorPorFiltro(filtro);
			if (productosXClienteComExteriorList == null
					|| productosXClienteComExteriorList.isEmpty()) {
				this.addMensajeInfo("No se encontraron registros");
			}
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
			seleccionado = new ProductosXClienteComext();
			seleccionado.setCliente(new Cliente());
			productosSeleccionados = new ArrayList<ProductosXClienteComext>();
			this.modo = Modo.CREACION;
			outcome = "crear_maestro_productosporcliente_CE";
		} catch (Exception e) {
			LOGGER.error(e);
			this.addMensajeError(e);
		}
		return outcome;
	}

	public String editar() {
		String outcome = null;
		try {
			cargarValoresCombos();
			productosInventarioFiltroDTO = new ProductosInventarioFiltroDTO();
			productosInventarios = new ArrayList<ProductosInventario>();
			productosSeleccionados = new ArrayList<ProductosXClienteComext>();
			seleccionado.getProductosInventario().setIncluido(true);
			productosSeleccionados.add(seleccionado);
			this.modo = Modo.EDICION;
			outcome = "crear_maestro_productosporcliente_CE";
		} catch (Exception e) {
			LOGGER.error(e);
			this.addMensajeError(e);
		}
		return outcome;
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
			productosInventarios = maestroFacade
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
							.add(crearNuevoProductosXClienteComExt(productosInventario));
				}
			}
		} catch (Exception e) {
			LOGGER.error(e);
			this.addMensajeError(e);
		}
	}

	public String guardarRelacionProductoClienteComExt() {
		String outcome = null;
		try {
			if (productosSeleccionados.isEmpty()) {
				this.addMensajeError("Debe asociar al menos un producto");
				return outcome;
			}
			for (ProductosXClienteComext productosXClienteComext : productosSeleccionados) {
				productosXClienteComext.setPk(new ProductosXClienteComextPK(
						productosXClienteComext.getProductosInventario()
								.getId(), seleccionado.getCliente().getId()));
				productosXClienteComext.setId(seleccionado.getId());
				productosXClienteComext.setCliente(seleccionado.getCliente());
				productosXClienteComext.setIdMoneda(seleccionado.getIdMoneda());
				productosXClienteComext.setActivo(seleccionado.getActivo());
				productosXClienteComext.setIva(BigDecimal.ZERO);
			}
			maestroFacade
					.guardarRelacionProductosClienteComercioExterior(productosSeleccionados);
			for (ProductosXClienteComext productosXClienteComext : productosSeleccionados) {
				if (productosXClienteComext.getProductosInventario()
						.isIncluido()) {
					int indexOf = productosXClienteComExteriorList
							.indexOf(productosXClienteComext);
					if (indexOf != -1) {
						productosXClienteComExteriorList.set(indexOf,
								productosXClienteComext);
					} else {
						productosXClienteComExteriorList.add(0,
								productosXClienteComext);
					}
				} else {
					productosXClienteComExteriorList
							.remove(productosXClienteComext);
				}
			}
			outcome = "listado_maestro_ProductosPorCliente_CE";
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

	private boolean estaRelacionado(ProductosInventario productosInventario) {
		boolean relacionado = false;
		for (ProductosXClienteComext productosXClienteComext : productosSeleccionados) {
			if (productosInventario.getId().equals(
					productosXClienteComext.getProductosInventario().getId())) {
				relacionado = true;
				break;
			}
		}
		return relacionado;
	}

	private ProductosXClienteComext crearNuevoProductosXClienteComExt(
			ProductosInventario productosInventario) {
		ProductosXClienteComext productosXClienteComext = new ProductosXClienteComext();
		productosXClienteComext.setProductosInventario(productosInventario);
		productosXClienteComext.setCliente(seleccionado.getCliente());
		return productosXClienteComext;
	}

	private void cargarValoresCombos() {
		clientes = maestroFacade.consultarClientesActivosPorUsuario(idUsuario);
		monedas = maestroFacade.consultarMonedas();
		cargarCategoriasInventario();
	}

	private void cargarCategoriasInventario() {
		List<CategoriasInventario> categorias = maestroFacade
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

	public void cargarArchivo(FileUploadEvent fileUploadEvent) {
		try {
			uploadedFile = fileUploadEvent.getFile();
			String fileName = uploadedFile.getFileName();
			maestroFacade
					.cargarProductosPorClienteComExtDesdeArchivo(uploadedFile
							.getInputstream());
			this.addMensajeInfo("Archivo cargado con éxito");
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

	public List<ProductosXClienteComext> getProductosXClienteComExteriorList() {
		return productosXClienteComExteriorList;
	}

	public void setProductosXClienteComExteriorList(
			List<ProductosXClienteComext> productosXClienteComExteriorList) {
		this.productosXClienteComExteriorList = productosXClienteComExteriorList;
	}

	public ProductosXClienteComext getSeleccionado() {
		return seleccionado;
	}

	public void setSeleccionado(ProductosXClienteComext seleccionado) {
		this.seleccionado = seleccionado;
	}

	public ProductosXClienteComExtFiltroVO getFiltro() {
		return filtro;
	}

	public void setFiltro(ProductosXClienteComExtFiltroVO filtro) {
		this.filtro = filtro;
	}

	public List<Cliente> getClientes() {
		return clientes;
	}

	public void setClientes(List<Cliente> clientes) {
		this.clientes = clientes;
	}

	public List<Moneda> getMonedas() {
		return monedas;
	}

	public void setMonedas(List<Moneda> monedas) {
		this.monedas = monedas;
	}

	public MenuMB getMenu() {
		return menu;
	}

	public void setMenu(MenuMB menu) {
		this.menu = menu;
	}

	public List<ProductosInventario> getProductosInventarios() {
		return productosInventarios;
	}

	public void setProductosInventarios(
			List<ProductosInventario> productosInventarios) {
		this.productosInventarios = productosInventarios;
	}

	public List<ProductosXClienteComext> getProductosSeleccionados() {
		return productosSeleccionados;
	}

	public void setProductosSeleccionados(
			List<ProductosXClienteComext> productosSeleccionados) {
		this.productosSeleccionados = productosSeleccionados;
	}

	public List<SelectItem> getCategoriasInventarios() {
		return categoriasInventarios;
	}

	public void setCategoriasInventarios(List<SelectItem> categoriasInventarios) {
		this.categoriasInventarios = categoriasInventarios;
	}

	public ProductosInventarioFiltroDTO getProductosInventarioFiltroDTO() {
		return productosInventarioFiltroDTO;
	}

	public void setProductosInventarioFiltroDTO(
			ProductosInventarioFiltroDTO productosInventarioFiltroDTO) {
		this.productosInventarioFiltroDTO = productosInventarioFiltroDTO;
	}

	public UploadedFile getUploadedFile() {
		return uploadedFile;
	}

	public void setUploadedFile(UploadedFile uploadedFile) {
		this.uploadedFile = uploadedFile;
	}

}
