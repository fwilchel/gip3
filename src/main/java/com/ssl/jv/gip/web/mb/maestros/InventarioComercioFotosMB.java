package com.ssl.jv.gip.web.mb.maestros;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;

import org.apache.log4j.Logger;
import org.hibernate.exception.ConstraintViolationException;

import com.ssl.jv.gip.jpa.pojo.CategoriasInventario;
import com.ssl.jv.gip.jpa.pojo.ProductosInventarioComext;
import com.ssl.jv.gip.negocio.dto.ProductosInventarioComextFiltroVO;
import com.ssl.jv.gip.negocio.ejb.MaestrosEJBLocal;
import com.ssl.jv.gip.web.mb.AplicacionMB;
import com.ssl.jv.gip.web.mb.MenuMB;
import com.ssl.jv.gip.web.mb.UtilMB;
import com.ssl.jv.gip.web.util.Modo;

/**
 * @author Juan Jose Buzon
 *
 */
@ManagedBean(name = "inventarioComercioFotosMB")
@SessionScoped
public class InventarioComercioFotosMB extends UtilMB {

	/**
	 * 
	 */
	private static final long serialVersionUID = 891641226527986872L;
	private static final Logger LOGGER = Logger
			.getLogger(InventarioComercioExteriorMB.class);

	@ManagedProperty(value = "#{menuMB}")
	private MenuMB menu;
	private String idUsuario;

	private Integer language = AplicacionMB.SPANISH;
	private Modo modo;

	private ProductosInventarioComextFiltroVO filtroVO;
	private List<ProductosInventarioComext> productosInventarioComexts;
	private ProductosInventarioComext seleccionado;

	private List<SelectItem> categoriasInventarios;

	@EJB
	private MaestrosEJBLocal maestrosEJBLocal;

	@PostConstruct
	public void init() {
		try {
			idUsuario = menu.getUsuario().getId();
			filtroVO = new ProductosInventarioComextFiltroVO();
			productosInventarioComexts = new ArrayList<ProductosInventarioComext>();
			cargarCategoriasInventario();
		} catch (Exception e) {
			LOGGER.error(e);
			this.addMensajeError(e);
		}
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

	public void consultarProductosInventarioComExt(ActionEvent actionEvent) {
		try {
			FacesContext context = FacesContext.getCurrentInstance();
			Map map = context.getExternalContext().getRequestParameterMap();
			String strPathFoto = (String) map.get("pathFoto");

			if ("".equals(filtroVO.getSkuProducto())) {
				filtroVO.setSkuProducto("%");
			} else {
				filtroVO.setSkuProducto("%" + filtroVO.getSkuProducto() + "%");
			}

			if ("".equals(filtroVO.getNombreProducto())) {
				filtroVO.setNombreProducto("%");
			} else {
				filtroVO.setNombreProducto("%" + filtroVO.getNombreProducto()
						+ "%");
			}

			productosInventarioComexts = maestrosEJBLocal
					.consultarProductosInventarioComextsParaInventarioComercioFotos(filtroVO);

			for (ProductosInventarioComext productosInventarioComext : productosInventarioComexts) {
				File f = new File(strPathFoto
						+ "med_"
						+ productosInventarioComext.getProductosInventario()
								.getSku() + ".jpg");
				if (f.exists()) {
					productosInventarioComext
							.setRutaFoto1("med_"
									+ productosInventarioComext
											.getProductosInventario().getSku()
									+ ".jpg");
				} else {
					productosInventarioComext.setRutaFoto1("med_JV_LOGO.jpg");
				}

				if (productosInventarioComext.getDescripcion() == null
						|| productosInventarioComext.getDescripcion()
								.equals("")) {
					productosInventarioComext.setDescripcion("N/A");
				}

				if (productosInventarioComext.getNombrePrdProveedor() == null
						|| productosInventarioComext.getNombrePrdProveedor()
								.equals("")) {
					productosInventarioComext.setNombrePrdProveedor("N/A");
				}
			}

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

	public ProductosInventarioComextFiltroVO getFiltroVO() {
		return filtroVO;
	}

	public void setFiltroVO(ProductosInventarioComextFiltroVO filtroVO) {
		this.filtroVO = filtroVO;
	}

	public List<ProductosInventarioComext> getProductosInventarioComexts() {
		return productosInventarioComexts;
	}

	public void setProductosInventarioComexts(
			List<ProductosInventarioComext> productosInventarioComexts) {
		this.productosInventarioComexts = productosInventarioComexts;
	}

	public ProductosInventarioComext getSeleccionado() {
		return seleccionado;
	}

	public void setSeleccionado(ProductosInventarioComext seleccionado) {
		this.seleccionado = seleccionado;
	}

	public List<SelectItem> getCategoriasInventarios() {
		return categoriasInventarios;
	}

	public void setCategoriasInventarios(List<SelectItem> categoriasInventarios) {
		this.categoriasInventarios = categoriasInventarios;
	}
}
