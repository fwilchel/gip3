package com.ssl.jv.gip.web.mb.maestros;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;

import com.ssl.jv.gip.jpa.pojo.ProductosXClienteComExtFiltroVO;
import com.ssl.jv.gip.jpa.pojo.ProductosXClienteComext;
import com.ssl.jv.gip.negocio.ejb.MaestrosEJB;
import com.ssl.jv.gip.web.mb.AplicacionMB;
import com.ssl.jv.gip.web.mb.UtilMB;
import com.ssl.jv.gip.web.util.Modo;

@ManagedBean(name = "productoClienteComercioExteriorMB")
@ViewScoped
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

	@EJB
	private MaestrosEJB servicio;

	private Integer language = AplicacionMB.SPANISH;
	private Modo modo;

	public ProductoClienteComercioExteriorMB() {
		super();
	}

	@PostConstruct
	public void init() {
		try {
			productosXClienteComExteriorList = servicio
					.consultarProductosClienteComercioExterior();
		} catch (Exception e) {
			LOGGER.error(e);
			this.addMensajeError(e);
		}
	}

	public void listarProductosClientesComercioExtActivos() {
		try {
			filtro.setActivo(true);
			productosXClienteComExteriorList = servicio
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
			productosXClienteComExteriorList = servicio
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

	public void nuevo() {
		seleccionado = new ProductosXClienteComext();
		this.modo = Modo.CREACION;
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

}
