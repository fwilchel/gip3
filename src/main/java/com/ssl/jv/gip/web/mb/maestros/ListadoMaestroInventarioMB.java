package com.ssl.jv.gip.web.mb.maestros;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.apache.log4j.Logger;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.ssl.jv.gip.jpa.pojo.CategoriasInventario;
import com.ssl.jv.gip.jpa.pojo.Pais;
import com.ssl.jv.gip.jpa.pojo.ProductosInventario;
import com.ssl.jv.gip.negocio.ejb.MaestrosEJBLocal;
import com.ssl.jv.gip.web.mb.UtilMB;

@ManagedBean(name="maestroInventarioMB")
@SessionScoped
public class ListadoMaestroInventarioMB extends UtilMB{

	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(ProductosMB.class);

	private ProductosInventario filtro;
	private LazyDataModel<ProductosInventario> modelo;
	private String campoOrden;
	private SortOrder orden;
	
	@EJB
	private MaestrosEJBLocal maestrosEjb;
	
	@PostConstruct
	public void init(){
		filtro = new ProductosInventario();
		filtro.setPais(new Pais());
		filtro.setCategoriasInventario(new CategoriasInventario());
		filtro.setDesactivado(true);
		modelo = new LazyProductsDataModel();
	}

	public ProductosInventario getFiltro() {
		return filtro;
	}

	public void setFiltro(ProductosInventario filtro) {
		this.filtro = filtro;
	}
	
	private class LazyProductsDataModel extends LazyDataModel<ProductosInventario>{

		/**
		 * 
		 */
		private static final long serialVersionUID = 283497341126330045L;
		private List<ProductosInventario> datos;
		
		@Override
	    public Object getRowKey(ProductosInventario pi) {
	        return pi.getId();
	    }
		
		@Override
	    public ProductosInventario getRowData(String rowKey) {
	        for(ProductosInventario pi : datos) {
	            if(pi.getId().toString().equals(rowKey))
	                return pi;
	        }
	        return null;
	    }

		
		@Override
	    public List<ProductosInventario> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters) {
			campoOrden = sortField;
			orden=sortOrder;
			Object rta[]=maestrosEjb.consultarProductos(filtro, first, pageSize, sortField, sortOrder, true);
			this.setRowCount(((Long)rta[0]).intValue());
			rta=maestrosEjb.consultarProductos(filtro, first, pageSize, sortField, sortOrder, false);
			datos=(List<ProductosInventario>)rta[1];
			return datos;
		}

	}
	
	
}
