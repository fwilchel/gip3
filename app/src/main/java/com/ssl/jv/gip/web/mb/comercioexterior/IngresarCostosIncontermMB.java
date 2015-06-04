package com.ssl.jv.gip.web.mb.comercioexterior;

import java.math.BigDecimal;
import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.ssl.jv.gip.negocio.dto.DocumentoIncontermDTO;
import com.ssl.jv.gip.negocio.ejb.ComercioExteriorEJB;
import com.ssl.jv.gip.web.mb.UtilMB;

/**
 * The Class IngresarCostosIncontermMB.
 */
@ManagedBean(name = "ingresarCostosIncotermMB")
@SessionScoped
public class IngresarCostosIncontermMB extends UtilMB {

  /**
   * The lista documentos.
   */
  private ArrayList<DocumentoIncontermDTO> listaDocumentos = new ArrayList<DocumentoIncontermDTO>();

  /**
   * The seleccionado.
   */
  private DocumentoIncontermDTO seleccionado;

  /**
   * The dbl valor total neg.
   */
  private BigDecimal dblValorTotalNeg;

  private BigDecimal dblValorCostoLogistico;

  private BigDecimal dblValorCostoEtiquetas;

  /**
   * The dbl valor fob.
   */
  private BigDecimal dblValorFOB;

  /**
   * The dbl valor fletes.
   */
  private BigDecimal dblValorFletes;

  /**
   * The dbl valor seguro.
   */
  private BigDecimal dblValorSeguro;

  /**
   * The dbl valor otros gastos.
   */
  private BigDecimal dblValorOtrosGastos;

  /**
   * The dbl total valor t.
   */
  private BigDecimal dblTotalValorT;

  /**
   * The comercio ejb.
   */
  @EJB
  private ComercioExteriorEJB comercioEjb;

  /**
   * Instantiates a new ingresar costos inconterm mb.
   */
  public IngresarCostosIncontermMB() {

  }

  /**
   * Inits the.
   */
  @PostConstruct
  public void init() {
	listaDocumentos = (ArrayList<DocumentoIncontermDTO>) comercioEjb.consultarDocumentosCostosInconterm();
  }

  /**
   * Refrescar totales.
   */
  public void refrescarTotales() {

	dblValorFOB = dblValorCostoLogistico.add(dblValorCostoEtiquetas);

	dblValorTotalNeg = dblTotalValorT.add(dblValorFletes).add(dblValorSeguro).add(dblValorOtrosGastos).add(dblValorFOB);

  }

  /**
   * Gets the seleccionado.
   *
   * @return the seleccionado
   */
  public DocumentoIncontermDTO getSeleccionado() {
	return seleccionado;
  }

  /**
   * Sets the seleccionado.
   *
   * @param seleccionado
   *          the new seleccionado
   */
  public void setSeleccionado(DocumentoIncontermDTO seleccionado) {
	this.seleccionado = seleccionado;
  }

  /**
   * Consultar solicitud pedido.
   *
   * @return the string
   */
  public String consultarSolicitudPedido() {
	dblTotalValorT = seleccionado.getValorTotalDocumento() == null ? new BigDecimal(0) : seleccionado.getValorTotalDocumento();
	this.dblValorCostoLogistico = seleccionado.getCostoEntrega() == null ? new BigDecimal(0) : seleccionado.getCostoEntrega();
	dblValorFOB = seleccionado.getCostoEntrega() == null ? new BigDecimal(0) : seleccionado.getCostoEntrega();
	dblValorFletes = seleccionado.getCostoFlete() == null ? new BigDecimal(0) : seleccionado.getCostoFlete();
	dblValorSeguro = seleccionado.getCostoSeguro() == null ? new BigDecimal(0) : seleccionado.getCostoSeguro();
	dblValorOtrosGastos = seleccionado.getOtrosGastos() == null ? new BigDecimal(0) : seleccionado.getOtrosGastos();
	dblValorTotalNeg = dblTotalValorT.add(dblValorFletes).add(dblValorSeguro).add(dblValorOtrosGastos).add(dblValorFOB);

	return "";
  }

  /**
   * Guardar ajustes pedido.
   */
  public void guardarAjustesPedido() {
	seleccionado.setCostoEntrega(dblValorFOB);
	seleccionado.setCostoFlete(dblValorFletes);
	seleccionado.setCostoSeguro(dblValorSeguro);
	seleccionado.setOtrosGastos(dblValorOtrosGastos);

	comercioEjb.actualizarDocumentoPorNegociacion(seleccionado);

	this.addMensajeInfo("Se actualizaron los costos exitosamente");
	listaDocumentos = (ArrayList<DocumentoIncontermDTO>) comercioEjb.consultarDocumentosCostosInconterm();

  }

  /**
   * Gets the lista documentos.
   *
   * @return the lista documentos
   */
  public ArrayList<DocumentoIncontermDTO> getListaDocumentos() {
	return listaDocumentos;
  }

  /**
   * Sets the lista documentos.
   *
   * @param listaDocumentos
   *          the new lista documentos
   */
  public void setListaDocumentos(ArrayList<DocumentoIncontermDTO> listaDocumentos) {
	this.listaDocumentos = listaDocumentos;
  }

  /**
   * Gets the dbl valor total neg.
   *
   * @return the dbl valor total neg
   */
  public BigDecimal getDblValorTotalNeg() {
	return dblValorTotalNeg;
  }

  /**
   * Sets the dbl valor total neg.
   *
   * @param dblValorTotalNeg
   *          the new dbl valor total neg
   */
  public void setDblValorTotalNeg(BigDecimal dblValorTotalNeg) {
	this.dblValorTotalNeg = dblValorTotalNeg;
  }

  /**
   * Gets the dbl valor fob.
   *
   * @return the dbl valor fob
   */
  public BigDecimal getDblValorFOB() {
	return dblValorFOB;
  }

  /**
   * Sets the dbl valor fob.
   *
   * @param dblValorFOB
   *          the new dbl valor fob
   */
  public void setDblValorFOB(BigDecimal dblValorFOB) {
	this.dblValorFOB = dblValorFOB;
  }

  /**
   * Gets the dbl valor fletes.
   *
   * @return the dbl valor fletes
   */
  public BigDecimal getDblValorFletes() {
	return dblValorFletes;
  }

  /**
   * Sets the dbl valor fletes.
   *
   * @param dblValorFletes
   *          the new dbl valor fletes
   */
  public void setDblValorFletes(BigDecimal dblValorFletes) {
	this.dblValorFletes = dblValorFletes;
  }

  /**
   * Gets the dbl valor seguro.
   *
   * @return the dbl valor seguro
   */
  public BigDecimal getDblValorSeguro() {
	return dblValorSeguro;
  }

  /**
   * Sets the dbl valor seguro.
   *
   * @param dblValorSeguro
   *          the new dbl valor seguro
   */
  public void setDblValorSeguro(BigDecimal dblValorSeguro) {
	this.dblValorSeguro = dblValorSeguro;
  }

  /**
   * Gets the dbl valor otros gastos.
   *
   * @return the dbl valor otros gastos
   */
  public BigDecimal getDblValorOtrosGastos() {
	return dblValorOtrosGastos;
  }

  /**
   * Sets the dbl valor otros gastos.
   *
   * @param dblValorOtrosGastos
   *          the new dbl valor otros gastos
   */
  public void setDblValorOtrosGastos(BigDecimal dblValorOtrosGastos) {
	this.dblValorOtrosGastos = dblValorOtrosGastos;
  }

  /**
   * Gets the dbl total valor t.
   *
   * @return the dbl total valor t
   */
  public BigDecimal getDblTotalValorT() {
	return dblTotalValorT;
  }

  /**
   * Sets the dbl total valor t.
   *
   * @param dblTotalValorT
   *          the new dbl total valor t
   */
  public void setDblTotalValorT(BigDecimal dblTotalValorT) {
	this.dblTotalValorT = dblTotalValorT;
  }

  public BigDecimal getDblValorCostoLogistico() {
	return dblValorCostoLogistico;
  }

  public void setDblValorCostoLogistico(BigDecimal dblValorCostoLogistico) {
	this.dblValorCostoLogistico = dblValorCostoLogistico;
  }

  public BigDecimal getDblValorCostoEtiquetas() {
	if (dblValorCostoEtiquetas == null) {
	  dblValorCostoEtiquetas = new BigDecimal(0);
	}
	return dblValorCostoEtiquetas;
  }

  public void setDblValorCostoEtiquetas(BigDecimal dblValorCostoEtiquetas) {
	this.dblValorCostoEtiquetas = dblValorCostoEtiquetas;
  }

}
