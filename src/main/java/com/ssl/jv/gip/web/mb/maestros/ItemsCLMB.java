package com.ssl.jv.gip.web.mb.maestros;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;

import com.ssl.jv.gip.jpa.pojo.CategoriaCostoLogistico;
import com.ssl.jv.gip.jpa.pojo.ItemCostoLogistico;
import com.ssl.jv.gip.negocio.ejb.MaestrosEJBLocal;
import com.ssl.jv.gip.util.TipoItemCostoLogistico;
import com.ssl.jv.gip.web.mb.AplicacionMB;
import com.ssl.jv.gip.web.mb.UtilMB;
import com.ssl.jv.gip.web.util.Modo;

/**
 * <p>Title: GIP</p>
 *
 * <p>Description: GIP</p>
 *
 * <p>Copyright: Copyright (c) 2014</p>
 *
 * <p>Company: Soft Studio Ltda.</p>
 *
 * @author Fredy Giovanny Wilches López
 * @email fredy.wilches@gmail.com
 * @phone 300 2146240
 * @version 1.0
 */
@ManagedBean(name="itemsCLMB")
@SessionScoped
public class ItemsCLMB extends UtilMB{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2780795923623719268L;

	private static final Logger LOGGER = Logger.getLogger(ItemsCLMB.class);

	private List<ItemCostoLogistico> items;
	private List<CategoriaCostoLogistico> categorias;
	private ItemCostoLogistico seleccionado;
	
	private Modo modo;
	
	@EJB
	private MaestrosEJBLocal maestrosEjb;
	
	@ManagedProperty(value="#{aplicacionMB}")
	private AplicacionMB appMB;
	
	private Integer language=AplicacionMB.SPANISH;
	private List<SelectItem> tipos;

	
	public ItemsCLMB(){
		
	}
	
	@PostConstruct
	public void init(){
		items=this.maestrosEjb.consultarItemsCostosLogisticos();
		tipos=new ArrayList<SelectItem>();
		for (TipoItemCostoLogistico i:TipoItemCostoLogistico.values()){
			tipos.add(new SelectItem(i.getId(), i.getDescripcion()));
		}
		categorias=this.maestrosEjb.consultarCategoriasCostosLogisticos();
	}

	public AplicacionMB getAppMB() {
		return appMB;
	}

	public void setAppMB(AplicacionMB appMB) {
		this.appMB = appMB;
	}

	public Modo getModo() {
		return modo;
	}

	public void setModo(Modo modo) {
		this.modo = modo;
	}

	public MaestrosEJBLocal getMaestrosEjb() {
		return maestrosEjb;
	}

	public void setMaestrosEjb(MaestrosEJBLocal maestrosEjb) {
		this.maestrosEjb = maestrosEjb;
	}

	public ItemCostoLogistico getSeleccionado() {
		return seleccionado;
	}

	public void setSeleccionado(ItemCostoLogistico seleccionado) {
		this.seleccionado = seleccionado;
		this.modo=Modo.EDICION;
	}
	
	public void nuevo(){
		seleccionado=new ItemCostoLogistico();
		this.modo=Modo.CREACION;
	}
	
	public void guardar(){
		if (this.modo.equals(Modo.CREACION)){
			this.seleccionado=this.maestrosEjb.crearItemCostoLogistico(this.seleccionado);
			this.items=this.maestrosEjb.consultarItemsCostosLogisticos();
			this.modo = Modo.EDICION;
		}else{
			this.seleccionado=this.maestrosEjb.actualizarItemCostoLogistico(this.seleccionado);
		}
		
		this.addMensajeInfo(AplicacionMB.getMessage("itemsCLGuardado", language));
	}
	
	public boolean isCreacion(){
		if (this.modo!=null && this.modo.equals(Modo.CREACION)){
			return true;
		}else{
			return false;
		}
	}

	public List<ItemCostoLogistico> getItems() {
		return items;
	}

	public void setItems(List<ItemCostoLogistico> items) {
		this.items = items;
	}

	public List<SelectItem> getTipos() {
		return tipos;
	}

	public void setTipos(List<SelectItem> tipos) {
		this.tipos = tipos;
	}

	public List<CategoriaCostoLogistico> getCategorias() {
		return categorias;
	}

	public void setCategorias(List<CategoriaCostoLogistico> categorias) {
		this.categorias = categorias;
	}
	
	
	
}
