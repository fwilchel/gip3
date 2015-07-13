package com.ssl.jv.gip.web.mb.comercioexterior;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;

import com.ssl.jv.gip.jpa.pojo.Cliente;
import com.ssl.jv.gip.jpa.pojo.FactsCurrencyConversion;
import com.ssl.jv.gip.jpa.pojo.LiquidacionCostoLogistico;
import com.ssl.jv.gip.jpa.pojo.LiquidacionDocumento;
import com.ssl.jv.gip.jpa.pojo.LiquidacionItem;
import com.ssl.jv.gip.jpa.pojo.Pais;
import com.ssl.jv.gip.jpa.pojo.TerminoIncotermXMedioTransporte;
import com.ssl.jv.gip.negocio.dto.CostoLogisticoDTO;
import com.ssl.jv.gip.negocio.dto.DocumentoCostosLogisticosDTO;
import com.ssl.jv.gip.negocio.dto.GrupoCostoLogistico;
import com.ssl.jv.gip.negocio.ejb.ComercioExteriorEJBLocal;
import com.ssl.jv.gip.negocio.ejb.ComunEJB;
import com.ssl.jv.gip.negocio.ejb.MaestrosEJBLocal;
import com.ssl.jv.gip.web.mb.AplicacionMB;
import com.ssl.jv.gip.web.mb.MenuMB;
import com.ssl.jv.gip.web.mb.UtilMB;

/**
 * <p>
 * Title: GIP</p>
 *
 * <p>
 * Description: GIP</p>
 *
 * <p> El valor FOB del final toma todos los items seleccionados cuya categoria sume en valor fob
 * Los items cuyo valor es un % sobre el valor fob, solo toma los items seleccionados con baseFOB
 * Al momento de generar no se tienen en cuenta las etiquetas para distribuir en las solicitudes costos 
 * Al momento de guardar la distribucion de costos si tiene en cuenta las etiquetas
 * Copyright: Copyright (c) 2014</p>
 *
 * <p>
 * Company: Soft Studio Ltda.</p>
 *
 * @author Fredy Giovanny Wilches Lopez
 * @email fredy.wilches@gmail.com
 * @phone 300 2146240
 * @version 1.0
 */
@ManagedBean(name = "generarCLMB")
@SessionScoped
public class GenerarCostosLogisticosMB extends UtilMB {

  private static final Logger LOGGER = Logger.getLogger(GenerarCostosLogisticosMB.class);

  private List<Cliente> clientes;
  private List<TerminoIncotermXMedioTransporte> incoterms;
  private List<String> puertosNacionales;
  private List<String> puertosInternacionales;
  private List<Pais> paises;
  private Long terminoIncoterm;
  private FactsCurrencyConversion trm;
  private String puertoNal = "";
  private String puertoInternal = "";
  private String pais = "";
  private Long cliente;
  private List<GrupoCostoLogistico> costos;
  private List<DocumentoCostosLogisticosDTO> solicitudes = new ArrayList<DocumentoCostosLogisticosDTO>();
  private BigDecimal cantidad1;
  private BigDecimal cantidad2;
  private Integer tipoContenedor1;
  private Integer tipoContenedor2;
  private List<SelectItem> tipoContenedores = new ArrayList<SelectItem>();
  

  @ManagedProperty(value = "#{menuMB}")
  private MenuMB menu;

  @EJB
  private MaestrosEJBLocal maestrosEjb;

  @ManagedProperty(value = "#{aplicacionMB}")
  private AplicacionMB appMB;

  @EJB
  private ComercioExteriorEJBLocal comercioEjb;

  @EJB
  private ComunEJB comunEJB;

  private Integer language = AplicacionMB.SPANISH;

  public GenerarCostosLogisticosMB() {

  }

  @SuppressWarnings("unchecked")
  @PostConstruct
  public void init() {
    clientes = this.maestrosEjb.consultarClientesInternacionales();
    Collections.sort(clientes);
    trm = this.maestrosEjb.getTRMDian(new Date());
    paises = comunEJB.consultarPaisesTodos();
    Collections.sort(paises);
    this.incoterms = this.maestrosEjb.consultarTerminoIncotermXMedioTransporte();
    this.puertosNacionales = this.comercioEjb.consultarPuertosNacionales();
    this.tipoContenedores.add(new SelectItem(new Integer(0), "No aplica"));
    this.tipoContenedores.add(new SelectItem(new Integer(1), "Contenedor de 20"));
    this.tipoContenedores.add(new SelectItem(new Integer(2), "Contenedor de 40"));
    //this.tipoContenedores.add(new SelectItem(new Integer(3), "Contenedor"));
  }

  public AplicacionMB getAppMB() {
    return appMB;
  }

  public void setAppMB(AplicacionMB appMB) {
    this.appMB = appMB;
  }

  public List<SelectItem> getTipoContenedores() {
    return tipoContenedores;
  }

  public void setTipoContenedores(List<SelectItem> tipoContenedores) {
    this.tipoContenedores = tipoContenedores;
  }

  public MenuMB getMenu() {
    return menu;
  }

  public List<DocumentoCostosLogisticosDTO> getSolicitudes() {
    return solicitudes;
  }

  public void setSolicitudes(List<DocumentoCostosLogisticosDTO> solicitudes) {
    this.solicitudes = solicitudes;
  }

  public void setMenu(MenuMB menu) {
    this.menu = menu;
  }

  public BigDecimal getCantidad1() {
    return cantidad1;
  }

  public void setCantidad1(BigDecimal cantidad1) {
    this.cantidad1 = cantidad1;
  }

  public BigDecimal getCantidad2() {
    return cantidad2;
  }

  public void setCantidad2(BigDecimal cantidad2) {
    this.cantidad2 = cantidad2;
  }

  public Integer getTipoContenedor1() {
    return tipoContenedor1;
  }

  public void setTipoContenedor1(Integer tipoContenedor1) {
    this.tipoContenedor1 = tipoContenedor1;
  }

  public Integer getTipoContenedor2() {
    return tipoContenedor2;
  }

  public void setTipoContenedor2(Integer tipoContenedor2) {
    this.tipoContenedor2 = tipoContenedor2;
  }

  public List<Cliente> getClientes() {
    return clientes;
  }

  public void setClientes(List<Cliente> clientes) {
    this.clientes = clientes;
  }

  public void generarCostosLogisticos() {
    if (tipoContenedor1 > 0 && tipoContenedor1.equals(tipoContenedor2)) {
      this.addMensajeError(AplicacionMB.getMessage("costosLogisticosTipoContenedorRepetido", language));
      return;
    }
    BigDecimal valorTotal = this.getValorDocumentos1();
    List<Long> documentos = new ArrayList<Long>();
    for (DocumentoCostosLogisticosDTO r : this.solicitudes) {
      if (r.getSeleccionada()) {
        documentos.add(r.getIdDocumento());
      }
    }
    if (documentos.size() == 0) {
      this.addMensajeError(AplicacionMB.getMessage("costosLogisticosMinimoUnaSolicitud", language));
      return;
    }
    TerminoIncotermXMedioTransporte timt1 = null;
    for (TerminoIncotermXMedioTransporte timt : this.incoterms) {
      if (timt.getId().equals(this.terminoIncoterm)) {
        timt1 = timt;
        break;
      }
    }
    List<CostoLogisticoDTO> datos = this.comercioEjb.generarCostosLogisticos(cliente, documentos, timt1, puertoNal, puertoInternal, this.trm.getId(), this.pais, this.tipoContenedor1, this.cantidad1, this.tipoContenedor2, this.cantidad2, valorTotal);

    this.costos = new ArrayList<GrupoCostoLogistico>();
    for (CostoLogisticoDTO cl : datos) {
      GrupoCostoLogistico g = new GrupoCostoLogistico(cl.getId().getCategoria());
      int pos = this.costos.indexOf(g);
      if (pos == -1) {
        g.addCosto(cl);
        cl.setPrimero(true);
        this.costos.add(g);
      } else {
        g = this.costos.get(pos);
        cl.setPrimero(false);
        g.addCosto(cl);
      }
    }
    this.recalcular();
  }

  public Long getTerminoIncoterm() {
    return terminoIncoterm;
  }

  public void setTerminoIncoterm(Long terminoIncoterm) {
    this.terminoIncoterm = terminoIncoterm;
  }

  public Long getCliente() {
    return cliente;
  }

  public void setCliente(Long cliente) {
    this.cliente = cliente;
  }

  public List<Pais> getPaises() {
    return paises;
  }

  public void setPaises(List<Pais> paises) {
    this.paises = paises;
  }

  public List<TerminoIncotermXMedioTransporte> getIncoterms() {
    return incoterms;
  }

  public void setIncoterms(List<TerminoIncotermXMedioTransporte> incoterms) {
    this.incoterms = incoterms;
  }

  public String getPais() {
    return pais;
  }

  public void setPais(String pais) {
    this.pais = pais;
  }

  public String getPuertoNal() {
    return puertoNal;
  }

  public void setPuertoNal(String puertoNal) {
    this.puertoNal = puertoNal;
  }

  public String getPuertoInternal() {
    return puertoInternal;
  }

  public void setPuertoInternal(String puertoInternal) {
    this.puertoInternal = puertoInternal;
  }

  public List<String> getPuertosNacionales() {
    return puertosNacionales;
  }

  public void setPuertosNacionales(List<String> puertosNacionales) {
    this.puertosNacionales = puertosNacionales;
  }

  public List<String> getPuertosInternacionales() {
    return puertosInternacionales;
  }

  public void setPuertosInternacionales(List<String> puertosInternacionales) {
    this.puertosInternacionales = puertosInternacionales;
  }

  public void cargarPuertosInternacionales() {
    this.puertosInternacionales = this.comercioEjb.consultarPuertosInternacionales(this.pais);
  }

  public void consultarSolicitudes() {
    this.solicitudes = this.comercioEjb.consultarDocumentosCostosLogisticos(this.cliente);
  }

  public List<GrupoCostoLogistico> getCostos() {
    return costos;
  }

  public void setCostos(List<GrupoCostoLogistico> costos) {
    this.costos = costos;
  }

  public BigDecimal getTotal() {
    BigDecimal v = new BigDecimal(0);
    if (this.costos != null) {
      for (GrupoCostoLogistico g : this.costos) {
        v = v.add(g.getTotal());
      }
    }
    return v;
  }

  public BigDecimal getTotalFOB() {
    BigDecimal v = new BigDecimal(0);
    if (this.costos != null) {
      for (GrupoCostoLogistico g : this.costos) {
        v = v.add(g.getTotalFOB());
      }
    }
    
    for (DocumentoCostosLogisticosDTO d:this.solicitudes){
    	if (d.getSeleccionada()){
    		v=v.add(d.getEtiquetas());
    	}
    }
    return v;
  }

  public BigDecimal getTotalFletes() {
    BigDecimal v = new BigDecimal(0);
    if (this.costos != null) {
      for (GrupoCostoLogistico g : this.costos) {
        v = v.add(g.getTotalFletes());
      }
    }
    return v;
  }

  public BigDecimal getTotalSeguros() {
    BigDecimal v = new BigDecimal(0);
    if (this.costos != null) {
      for (GrupoCostoLogistico g : this.costos) {
        v = v.add(g.getTotalSeguros());
      }
    }
    return v;
  }

  public BigDecimal getValorDocumentos1() {
    BigDecimal valorTotal = new BigDecimal(0);
    for (DocumentoCostosLogisticosDTO d : this.solicitudes) {
      if (d.getSeleccionada()) {
        valorTotal = valorTotal.add(d.getValorTotalDocumento());
      }
    }
    return valorTotal;
  }

  public BigDecimal getValorDocumentos2() {
	    BigDecimal valorTotal = new BigDecimal(0);
	    for (DocumentoCostosLogisticosDTO d : this.solicitudes) {
	      if (d.getSeleccionada()) {
	        valorTotal = valorTotal.add(d.getValorTotalDocumento()).add(d.getEtiquetas());
	      }
	    }
	    return valorTotal;
	  }

  public void guardar() {
    BigDecimal valorTotal = this.getValorDocumentos2();
    BigDecimal fob = this.getTotalFOB();
    BigDecimal fletes = this.getTotalFletes();
    BigDecimal seguros = this.getTotalSeguros();

    LiquidacionCostoLogistico lcl = new LiquidacionCostoLogistico();
    lcl.setCantidad1(cantidad1);
    lcl.setCantidad2(cantidad2);
    lcl.setClienteId(this.cliente);
    lcl.setIncotermTransporteId(this.terminoIncoterm);
    lcl.setPaisId(pais);
    lcl.setPuertoInternal(puertoInternal);
    lcl.setPuertoNal(puertoNal);
    lcl.setTipoContenedor1(tipoContenedor1);
    lcl.setTipoContenedor2(tipoContenedor2);

    for (GrupoCostoLogistico g : this.costos) {
      for (CostoLogisticoDTO cl : g.getCostos()) {
        if (cl.isSeleccionado()) {
          LiquidacionItem li = new LiquidacionItem();

          li.setBaseFob(cl.getId().getBaseFob());
          li.setCampoAcumula(cl.getId().getCampoAcumula());
          li.setCantidad(cl.getId().getCantidad());
          li.setCategoriaId(cl.getId().getCategoriaId());
          li.setConsecutivoDocumento(cl.getId().getConsecutivoDocumento());
          li.setItemId(cl.getId().getItemId());
          li.setLiquidacionCostoLogistico(lcl);
          li.setOrden(cl.getId().getOrden());
          li.setTipo(cl.getId().getTipo());
          li.setValor(new BigDecimal(cl.getId().getValor()));
          li.setValorMinimo(new BigDecimal(cl.getId().getValorMinimo()));

          lcl.getLiquidacionItems().add(li);
        }
      }
    }
    List<DocumentoCostosLogisticosDTO> doctos = new ArrayList<DocumentoCostosLogisticosDTO>();

    for (DocumentoCostosLogisticosDTO d : this.solicitudes) {
      if (d.getSeleccionada()) {
        doctos.add(d);
        LiquidacionDocumento ld = new LiquidacionDocumento();
        ld.setConsecutivoDocumento(d.getConsecutivoDocumento());
        ld.setEtiquetas(d.getEtiquetas());
        ld.setLiquidacionCostoLogistico(lcl);
        lcl.getLiquidacionDocumentos().add(ld);

        //this.comercioEjb.actualizarCostosLogisticos(d.getIdDocumento(), d.getIdTerminoIncoterm(), d.getValorTotalDocumento().divide(valorTotal).multiply(fob), d.getValorTotalDocumento().divide(valorTotal).multiply(fletes), d.getValorTotalDocumento().divide(valorTotal).multiply(seguros), lcl);
      }
    }
    try {
      this.comercioEjb.actualizarCostosLogisticos(valorTotal, fob, fletes, seguros, doctos, lcl);

      this.addMensajeInfo(AplicacionMB.getMessage("costosLogisticosActualizacionExitosa", language));
    } catch (Exception e) {
      this.addMensajeError(AplicacionMB.getMessage("costosLogisticosSolicitudYaGuardada", language));
      if (e.getCause() != null && e.getCause().getCause() != null) {
        this.addMensajeInfo(e.getCause().getCause().getMessage());
      }
    }
  }

  public void recalcular() {
    Double valorFob = new Double(0);
    if (this.costos != null) {
      for (GrupoCostoLogistico g : this.costos) {
        for (CostoLogisticoDTO cl : g.getCostos()) {
          if (cl.isSeleccionado() && cl.getId().getBaseFob()) {
            valorFob += Math.max(cl.getId().getValor(), cl.getId().getValorMinimo());
          }
        }
      }
    }
    if (this.solicitudes!=null){
    	for (DocumentoCostosLogisticosDTO d:this.solicitudes){
    		if (d.getSeleccionada()){
    			valorFob+=d.getEtiquetas().doubleValue()+d.getValorTotalDocumento().doubleValue();
    		}
    	}
    }
    
    if (this.costos != null) {
      for (GrupoCostoLogistico g : this.costos) {
        for (CostoLogisticoDTO cl : g.getCostos()) {
          if (cl.getId().getTipo().equals(new Integer(6))) {
            Double valor = new BigDecimal(valorFob).multiply(cl.getId().getCantidad()).doubleValue() / 100;
            cl.getId().setValor(valor < cl.getId().getValorMinimo() ? cl.getId().getValorMinimo() : valor);
          }
        }
      }
    }
  }
  
  	public void probar(String categoria){
  		for (GrupoCostoLogistico grupo: this.costos){
  			if (grupo.getCategoria().equals(categoria)){
  				grupo.setSeleccionado(!grupo.getSeleccionado());
  				for (CostoLogisticoDTO c:grupo.getCostos()){
  					c.setSeleccionado(grupo.getSeleccionado());
  				}
  				break;
  			}
  		}
  	}
}
