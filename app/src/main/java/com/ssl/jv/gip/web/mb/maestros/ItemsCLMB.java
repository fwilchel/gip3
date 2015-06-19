package com.ssl.jv.gip.web.mb.maestros;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;
import com.ssl.jv.gip.jpa.pojo.CategoriaCostoLogistico;
import com.ssl.jv.gip.jpa.pojo.ItemCostoLogistico;
import com.ssl.jv.gip.jpa.pojo.Moneda;
import com.ssl.jv.gip.jpa.pojo.RangoCostoLogistico;
import com.ssl.jv.gip.jpa.pojo.Unidad;
import com.ssl.jv.gip.negocio.ejb.MaestrosEJBLocal;
import com.ssl.jv.gip.util.TipoItemCostoLogistico;
import com.ssl.jv.gip.web.mb.AplicacionMB;
import com.ssl.jv.gip.web.mb.UtilMB;
import com.ssl.jv.gip.web.util.Modo;

/**
 * <p>
 * Title: GIP
 * </p>
 *
 * <p>
 * Description: GIP
 * </p>
 *
 * <p>
 * Copyright: Copyright (c) 2014
 * </p>
 *
 * <p>
 * Company: Soft Studio Ltda.
 * </p>
 *
 * @author Fredy Giovanny Wilches L�pez
 * @email fredy.wilches@gmail.com
 * @phone 300 2146240
 * @version 1.0
 */
@ManagedBean(name = "itemsCLMB")
@SessionScoped
public class ItemsCLMB extends UtilMB {

  /**
   *
   */
  private static final long serialVersionUID = -2780795923623719268L;

  private static final Logger LOGGER = Logger.getLogger(ItemsCLMB.class);

  private List<ItemCostoLogistico> items;
  private List<CategoriaCostoLogistico> categorias;
  private ItemCostoLogistico seleccionado;
  private RangoCostoLogistico seleccionado2;
  private List<Moneda> monedas;

  private Modo modo;

  @EJB
  private MaestrosEJBLocal maestrosEjb;

  @ManagedProperty(value = "#{aplicacionMB}")
  private AplicacionMB appMB;

  private Integer language = AplicacionMB.SPANISH;
  private List<SelectItem> tipos;
  private boolean nuevoRango=false;
  private List<Unidad> unidades;


  public ItemsCLMB() {

  }

  @PostConstruct
  public void init() {
    items = this.maestrosEjb.consultarItemsCostosLogisticos();
    tipos = new ArrayList<SelectItem>();
    unidades=this.maestrosEjb.consultarUnidades();
    for (TipoItemCostoLogistico i : TipoItemCostoLogistico.values()) {
      tipos.add(new SelectItem(i.getId(), i.getDescripcion()));
    }
    categorias = this.maestrosEjb.consultarCategoriasCostosLogisticos();
    monedas = this.maestrosEjb.consultarMonedas();
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
    this.modo = Modo.EDITAR;
    if (this.seleccionado.getMoneda() == null) {
      this.seleccionado.setMoneda(new Moneda());
    }
    if (this.seleccionado.getRangoCostoLogisticos() != null) {
//	  Collections.sort(this.seleccionado.getRangoCostoLogisticos());
      for (RangoCostoLogistico rcl : this.seleccionado.getRangoCostoLogisticos()) {
        if (rcl.getMoneda() == null) {
          rcl.setMoneda(new Moneda());
        }
      }
    }
  }

  public void nuevo() {
    seleccionado = new ItemCostoLogistico();
    seleccionado.setCategoriaCostoLogistico(new CategoriaCostoLogistico());
    seleccionado.setMoneda(new Moneda());
    this.modo = Modo.CREAR;
  }

  public void guardar() {
    String moneda = null;
    if (this.seleccionado.getIdPaisDestino().equals("")) {
      this.seleccionado.setIdPaisDestino(null);
    }
    if (this.seleccionado.getMoneda().getId() == null || this.seleccionado.getMoneda().getId().equals("")) {
      this.seleccionado.setMoneda(null);
    } else {
      moneda = this.seleccionado.getMoneda().getId();
    }
    if (this.modo.equals(Modo.CREAR)) {
      this.seleccionado = this.maestrosEjb.crearItemCostoLogistico(this.seleccionado);
      this.items = this.maestrosEjb.consultarItemsCostosLogisticos();
      this.modo = Modo.EDITAR;
    } else {
      this.seleccionado = this.maestrosEjb.actualizarItemCostoLogistico(this.seleccionado);
    }
    if (moneda != null) {
      this.seleccionado.setMoneda(new Moneda());
      this.seleccionado.getMoneda().setId(moneda);
    }
    if (this.seleccionado.getMoneda() != null && this.seleccionado.getMoneda().getId() != null) {
      for (Moneda m : this.monedas) {
        if (m.getId().equals(this.seleccionado.getMoneda().getId())) {
          this.seleccionado.setMoneda(m);
          break;
        }
      }
    }
    if (this.seleccionado.getMoneda() == null) {
      this.seleccionado.setMoneda(new Moneda());
    }
    if (this.seleccionado.getRangoCostoLogisticos() != null) {
      for (RangoCostoLogistico rcl : this.seleccionado.getRangoCostoLogisticos()) {
        if (rcl.getMoneda() == null) {
          rcl.setMoneda(new Moneda());
        }
      }
    }
    this.addMensajeInfo(AplicacionMB.getMessage("itemsCLGuardado", language));
  }

  public boolean isCreacion() {
    if (this.modo != null && this.modo.equals(Modo.CREAR)) {
      return true;
    } else {
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

  public List<Unidad> getUnidades() {
    return unidades;
  }

  public String adicionarRango() {
	 this.nuevoRango=true;
	 this.seleccionado2 = new RangoCostoLogistico();
	 this.seleccionado2.setUnidad(new Unidad());
	 this.seleccionado2.setItemCostoLogistico(this.seleccionado);
	 this.seleccionado2.setMoneda(new Moneda());
    if (this.seleccionado.getRangoCostoLogisticos() == null) {
      this.seleccionado.setRangoCostoLogisticos(new ArrayList<RangoCostoLogistico>());
    }
    return null;
  }

  public String eliminarRango() {
    if (this.seleccionado2.getId() != null && this.seleccionado2.getId() != 0) {
      this.seleccionado2.setItemCostoLogistico(null);
      this.maestrosEjb.eliminarRangoCostoLogistico(this.seleccionado2);
      this.seleccionado.getRangoCostoLogisticos().remove(this.seleccionado2);
    } else {
      this.seleccionado.getRangoCostoLogisticos().remove(this.seleccionado2);
    }
    return null;
  }

  public RangoCostoLogistico getSeleccionado2() {
    return seleccionado2;
  }

  public void setSeleccionado2(RangoCostoLogistico seleccionado2) {
    this.seleccionado2 = seleccionado2;
    if (this.seleccionado2.getMoneda() == null) {
      this.seleccionado2.setMoneda(new Moneda());
    }
    if (this.seleccionado2.getUnidad()==null){
    	this.seleccionado2.setUnidad(new Unidad());
    }
    this.nuevoRango=false;
  }

  public List<Moneda> getMonedas() {
    return monedas;
  }

  public void setMonedas(List<Moneda> monedas) {
    this.monedas = monedas;
  }

  public void actualizarValor(AjaxBehaviorEvent e) {
    Long l = new Long(((org.primefaces.component.inputtext.InputText) e.getSource()).getLabel());
    for (ItemCostoLogistico icl : this.items) {
      if (icl.getId().equals(l)) {
        icl.setValor(new BigDecimal(((org.primefaces.component.inputtext.InputText) e.getSource()).getSubmittedValue().toString()));
        this.maestrosEjb.actualizarItemCostoLogistico(icl);
        break;
      }
    }
  }
  
  public void guardarRango(){
	  for (Unidad u:this.getUnidades()){
		  if (u.getId().equals(this.seleccionado2.getUnidad().getId())){
			  this.seleccionado2.getUnidad().setAbreviacion(u.getAbreviacion());
			  this.seleccionado2.getUnidad().setNombre(u.getNombre());
			  break;
		  }
	  }
	  if (this.nuevoRango)
		  this.seleccionado.addRangoCostoLogistico(this.seleccionado2);
	  this.nuevoRango=false;
	  this.addMensajeInfo("Rango guardado en el Item, debe Guardar el Item");
  }

}
