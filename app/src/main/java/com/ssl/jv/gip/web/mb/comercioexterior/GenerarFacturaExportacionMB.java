package com.ssl.jv.gip.web.mb.comercioexterior;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.apache.log4j.Logger;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import com.ssl.jv.gip.jpa.pojo.Documento;
import com.ssl.jv.gip.jpa.pojo.DocumentoXLotesoic;
import com.ssl.jv.gip.jpa.pojo.DocumentoXNegociacion;
import com.ssl.jv.gip.jpa.pojo.DocumentoXNegociacionPK;
import com.ssl.jv.gip.jpa.pojo.Estadosxdocumento;
import com.ssl.jv.gip.jpa.pojo.EstadosxdocumentoPK;
import com.ssl.jv.gip.jpa.pojo.LogAuditoria;
import com.ssl.jv.gip.jpa.pojo.Moneda;
import com.ssl.jv.gip.jpa.pojo.Muestrasxlote;
import com.ssl.jv.gip.jpa.pojo.ProductosXDocumento;
import com.ssl.jv.gip.jpa.pojo.ProductosXDocumentoPK;
import com.ssl.jv.gip.jpa.pojo.Ubicacion;
import com.ssl.jv.gip.negocio.dto.ProductoLoteAsignarLoteOICDTO;
import com.ssl.jv.gip.negocio.dto.ReporteReimprimirFacturaDTO;
import com.ssl.jv.gip.negocio.ejb.ComercioExteriorEJBLocal;
import com.ssl.jv.gip.negocio.ejb.ComunEJBLocal;
import com.ssl.jv.gip.negocio.ejb.ReportesComercioExteriorEJBLocal;
import com.ssl.jv.gip.negocio.ejb.ReportesEJBLocal;
import com.ssl.jv.gip.web.mb.AplicacionMB;
import com.ssl.jv.gip.web.mb.MenuMB;
import com.ssl.jv.gip.web.mb.UtilMB;
import com.ssl.jv.gip.web.mb.util.ConstantesDocumento;
import com.ssl.jv.gip.web.mb.util.ConstantesTipoDocumento;
import com.ssl.jv.gip.web.mb.util.Numero_a_Letra_Ingles;
import com.ssl.jv.gip.web.util.Modo;
import com.ssl.jv.gip.web.util.Utilidad;

/**
 * The Class GenerarFacturaExportacionMB.
 */
@ManagedBean(name = "generarFacturaExportacionMB")
@ViewScoped
public class GenerarFacturaExportacionMB extends UtilMB {

  /**
   * The Constant serialVersionUID.
   */
  private static final long serialVersionUID = 5093870535116322203L;

  /**
   * The Constant LOGGER.
   */
  private static final Logger LOGGER = Logger.getLogger(GenerarFacturaExportacionMB.class);

  @ManagedProperty(value = "#{menuMB}")
  private MenuMB menu;

  /**
   * The reportes comercio exterior ejb local.
   */
  @EJB
  private ReportesComercioExteriorEJBLocal reportesComercioExteriorEJBLocal;

  /**
   * The reportes ejb local.
   */
  @EJB
  private ReportesEJBLocal reportesEJBLocal;

  /**
   * The comercio exterior ejb local.
   */
  @EJB
  private ComercioExteriorEJBLocal comercioExteriorEJBLocal;

  @EJB
  private ComunEJBLocal comunEJB;

  /**
   * The lista facturas exportacion.
   */
  private List<Documento> listaFacturasExportacion;

  /**
   * The language.
   */
  private Integer language = AplicacionMB.SPANISH;

  /**
   * The modo.
   */
  private Modo modo;

  /**
   * The seleccionado.
   */
  private Documento seleccionado;

  private Documento facturaGenerada;

  /**
   * The lista productos documento.
   */
  private List<ProductosXDocumento> listaProductosDocumento;

  /**
   * The total cantitad1.
   */
  private BigDecimal totalCantitad1 = BigDecimal.ZERO;

  /**
   * The total valor total.
   */
  private BigDecimal totalValorTotal = BigDecimal.ZERO;

  /**
   * The total cantidad x embalaje.
   */
  private BigDecimal totalCantidadXEmbalaje = BigDecimal.ZERO;

  /**
   * The total cantidad cajas.
   */
  private BigDecimal totalCantidadCajas = BigDecimal.ZERO;

  /**
   * The total peso neto.
   */
  private BigDecimal totalPesoNeto = BigDecimal.ZERO;

  /**
   * The total peso bruto.
   */
  private BigDecimal totalPesoBruto = BigDecimal.ZERO;

  /**
   * The total costo entrega.
   */
  private BigDecimal totalCostoEntrega = BigDecimal.ZERO;

  /**
   * The total costo seguro.
   */
  private BigDecimal totalCostoSeguro = BigDecimal.ZERO;

  /**
   * The total costo flete.
   */
  private BigDecimal totalCostoFlete = BigDecimal.ZERO;

  /**
   * The total pallets.
   */
  private BigDecimal totalPallets = BigDecimal.ZERO;

  /**
   * The total otros gastos.
   */
  private BigDecimal totalOtrosGastos = BigDecimal.ZERO;

  /**
   * The total costos.
   */
  private BigDecimal totalCostos = BigDecimal.ZERO;

  /**
   * The total valor neg.
   */
  private BigDecimal totalValorNeg = BigDecimal.ZERO;

  /**
   * The lista muestras.
   */
  private List<Muestrasxlote> listaMuestras;

  /**
   * The lista producto totales.
   */
  private List<ProductoLoteAsignarLoteOICDTO> listaProductoTotales;

  /**
   * The reporte pdf.
   */
  private StreamedContent reportePDF;

  /**
   * The fecha actual.
   */
  private Date fechaActual;

  /**
   * The consecutivo documento.
   */
  private String consecutivoDocumento;

  /**
   * The str descripcion.
   */
  private String strDescripcion;

  /**
   * The render imprimir factura.
   */
  private Boolean renderBtnGenerar = true;
  private Boolean renderBtnImprimir = false;
  private Boolean renderBtnCerrar = true;

  /**
   * Inits the.
   */
  @PostConstruct
  public void init() {
    FacesContext.getCurrentInstance().getExternalContext().getSession(true);
    try {
      fechaActual = new Date();
    } catch (Exception e) {
      LOGGER.error(e);
      this.addMensajeError(e);
    }

  }

  /**
   * Consultar facturas exportacion.
   */
  public void consultarFacturasExportacion() {
    try {
      consecutivoDocumento = "%" + consecutivoDocumento + "%";
      this.listaFacturasExportacion = reportesEJBLocal.consultarDocumentosParaGenerarFacturaExportacion(consecutivoDocumento);
    } catch (Exception e) {
      LOGGER.error(e);
      this.addMensajeError(e);
    }
  }

  /**
   * Sets the seleccionado.
   *
   * @param seleccionado the new seleccionado
   */
  public void setSeleccionado(Documento seleccionado) {
    this.seleccionado = seleccionado;
    try {
      this.totalCantitad1 = BigDecimal.ZERO;
      this.totalValorTotal = BigDecimal.ZERO;
      this.totalCantidadXEmbalaje = BigDecimal.ZERO;
      this.totalCantidadCajas = BigDecimal.ZERO;
      this.totalPesoNeto = BigDecimal.ZERO;
      this.totalPesoBruto = BigDecimal.ZERO;
      this.listaProductosDocumento = this.reportesComercioExteriorEJBLocal.consultarProductosPorDocumentoLE(seleccionado.getId());
      for (ProductosXDocumento pr : listaProductosDocumento) {
        this.totalCantitad1 = this.totalCantitad1.add(pr.getCantidad1());
        this.totalValorTotal = this.totalValorTotal.add(pr.getValorTotal());
        this.totalCantidadXEmbalaje = this.totalCantidadXEmbalaje.add(pr.getCantidadXEmbalaje());
        this.totalCantidadCajas = this.totalCantidadCajas.add(pr.getCantidadCajasItem());
        this.totalPesoNeto = this.totalPesoNeto.add(pr.getTotalPesoNetoItem());
        this.totalPesoBruto = this.totalPesoBruto.add(pr.getTotalPesoBrutoItem());
      }
      this.listaProductoTotales = comercioExteriorEJBLocal.consultarProductoPorDocumentoLoteAsignarLotesOIC(this.seleccionado.getId(), this.seleccionado.getCliente().getId());
      if (this.seleccionado.getDocumentoXNegociacion() != null) {
        this.totalCostoEntrega = this.seleccionado.getDocumentoXNegociacion().getCostoEntrega();
        this.totalCostoSeguro = this.seleccionado.getDocumentoXNegociacion().getCostoSeguro();
        this.totalCostoFlete = this.seleccionado.getDocumentoXNegociacion().getCostoFlete();
        this.totalOtrosGastos = this.seleccionado.getDocumentoXNegociacion().getOtrosGastos();
        this.totalCostos = totalCostoEntrega.add(totalCostoSeguro).add(totalCostoFlete).add(totalOtrosGastos);
        this.totalPallets = this.seleccionado.getDocumentoXNegociacion().getTotalPallets();
      }
      this.totalValorNeg = this.totalValorTotal.add(totalCostos);
      this.setRenderBtnGenerar((Boolean) true);
      this.setRenderBtnImprimir((Boolean) false);
      this.setRenderBtnCerrar((Boolean) true);
    } catch (Exception e) {
      LOGGER.error(e);
    }
  }

  /**
   * Crear factura exp.
   */
  public void crearFacturaExp() {
    facturaGenerada = new Documento();
    facturaGenerada.setFechaGeneracion(new Timestamp(System.currentTimeMillis()));
    Estadosxdocumento estadosxdocumento = new Estadosxdocumento();
    EstadosxdocumentoPK estadosxdocumentoPK = new EstadosxdocumentoPK();
    estadosxdocumentoPK.setIdEstado((long) ConstantesDocumento.GENERADO);
    estadosxdocumentoPK.setIdTipoDocumento((long) ConstantesTipoDocumento.FACTURA_EXPORTACION);
    estadosxdocumento.setId(estadosxdocumentoPK);
    facturaGenerada.setEstadosxdocumento(estadosxdocumento);
    facturaGenerada.setObservacionDocumento(this.seleccionado.getConsecutivoDocumento());
    facturaGenerada.setUbicacionDestino(new Ubicacion(this.seleccionado.getUbicacionOrigen().getId()));
    facturaGenerada.setUbicacionOrigen(new Ubicacion(this.seleccionado.getUbicacionOrigen().getId()));
    facturaGenerada.setCliente(this.seleccionado.getCliente());
    facturaGenerada.setValorTotal(this.totalValorNeg);
    facturaGenerada.setDocumentoCliente(this.seleccionado.getDocumentoCliente());
    facturaGenerada.setFechaEsperadaEntrega(this.seleccionado.getFechaEsperadaEntrega());
    facturaGenerada.setNumeroFactura("0");
    // auditoria
    LogAuditoria auditoria = new LogAuditoria();
    auditoria.setIdUsuario(menu.getUsuario().getId());
    auditoria.setIdFuncionalidad(menu.getIdOpcionActual());
    auditoria.setTabla("Documentos");
    auditoria.setAccion("CRE");
    auditoria.setFecha(new Timestamp(System.currentTimeMillis()));
    // documento x negociacion
    DocumentoXNegociacion dxn = new DocumentoXNegociacion();
    dxn.setPk(new DocumentoXNegociacionPK());
    dxn.getPk().setIdTerminoIncoterm(this.seleccionado.getDocumentoXNegociacion().getTerminoIncoterm().getId());
    dxn.setCostoEntrega(this.seleccionado.getDocumentoXNegociacion().getCostoEntrega());
    dxn.setCostoSeguro(this.seleccionado.getDocumentoXNegociacion().getCostoSeguro());
    dxn.setCostoFlete(this.seleccionado.getDocumentoXNegociacion().getCostoFlete());
    dxn.setOtrosGastos(this.seleccionado.getDocumentoXNegociacion().getOtrosGastos());
    dxn.setObservacionesMarcacion2(this.strDescripcion);
    dxn.setTotalPesoNeto(this.seleccionado.getDocumentoXNegociacion().getTotalPesoNeto());
    dxn.setTotalPesoBruto(this.seleccionado.getDocumentoXNegociacion().getTotalPesoBruto());
    dxn.setTotalTendidos(this.seleccionado.getDocumentoXNegociacion().getTotalTendidos());
    dxn.setTotalPallets(this.seleccionado.getDocumentoXNegociacion().getTotalPallets());
    dxn.setCantidadDiasVigencia(this.seleccionado.getDocumentoXNegociacion().getCantidadDiasVigencia());
    dxn.setSolicitudCafe(this.seleccionado.getDocumentoXNegociacion().getSolicitudCafe());
    dxn.setCantidadContenedoresDe20(this.seleccionado.getDocumentoXNegociacion().getCantidadContenedoresDe20());
    dxn.setCantidadContenedoresDe40(this.seleccionado.getDocumentoXNegociacion().getCantidadContenedoresDe40());
    dxn.setLugarIncoterm(this.seleccionado.getDocumentoXNegociacion().getLugarIncoterm());
    dxn.setCantidadEstibas(0);
    dxn.setPesoBrutoEstibas(0);
    // productos x documento
    List<ProductosXDocumento> productos = new ArrayList<>();
    for (ProductosXDocumento pxd : this.listaProductosDocumento) {
      ProductosXDocumento productoDocumento = new ProductosXDocumento();
      productoDocumento.setInformacion(false);
      productoDocumento.setCalidad(false);
      productoDocumento.setFechaEstimadaEntrega(facturaGenerada.getFechaGeneracion());
      productoDocumento.setFechaEntrega(facturaGenerada.getFechaGeneracion());
      productoDocumento.setId(new ProductosXDocumentoPK());
      productoDocumento.getId().setIdProducto(pxd.getId().getIdProducto());
      productoDocumento.setCantidad1(pxd.getCantidad1());
      productoDocumento.setUnidade(pxd.getUnidade()); // unidad de venta
      Moneda moneda = new Moneda();
      moneda.setId("USD");
      productoDocumento.setMoneda(new Moneda("USD"));
      productoDocumento.setCantidad2(new BigDecimal(0));
      productoDocumento.setValorUnitatrioMl(new BigDecimal(0));
      productoDocumento.setValorUnitarioUsd(pxd.getValorUnitarioUsd());
      productoDocumento.setValorTotal(pxd.getValorTotal());
      productoDocumento.setTotalPesoNetoItem(pxd.getTotalPesoNetoItem());
      productoDocumento.setTotalPesoBrutoItem(pxd.getTotalPesoBrutoItem());
      productoDocumento.setCantidadCajasItem(pxd.getCantidadCajasItem());
      productoDocumento.setCantidadPalletsItem(pxd.getCantidadPalletsItem());
      productoDocumento.setCantidadXEmbalaje(pxd.getCantidadXEmbalaje());
      Calendar c = Calendar.getInstance();
      c.add(Calendar.DATE, 2);
      productoDocumento.setFechaEstimadaEntrega(new Timestamp(c.getTimeInMillis()));
      productoDocumento.setFechaEntrega(new Timestamp(c.getTimeInMillis()));
      productos.add(productoDocumento);
    }
    facturaGenerada = this.comercioExteriorEJBLocal.crearFacturaExportacion(facturaGenerada, auditoria, dxn, productos, this.seleccionado);
    String mensaje = AplicacionMB.getMessage("VentasFXExito_Crear", language);
    String parametros[] = new String[2];
    parametros[0] = "" + facturaGenerada.getId();
    parametros[1] = facturaGenerada.getConsecutivoDocumento();
    mensaje = Utilidad.stringFormat(mensaje, parametros);
    this.addMensajeInfo(mensaje);
    this.consultarFacturasExportacion();
    this.setRenderBtnGenerar((Boolean) false);
    this.setRenderBtnImprimir((Boolean) true);
    this.setRenderBtnCerrar((Boolean) false);
  }

  /**
   * Metodo que se encarga de enviar la solicitud de poner en estado impreso el documento.
   */
  private void imprimirFacturaFX() {
    LOGGER.trace("Metodo: <<imprimirFacturaFX>>");
    LOGGER.debug("Actuaizar estado de la factura FX");
    comercioExteriorEJBLocal.actualizarEstadoDocumento(facturaGenerada.getId(), new Long(ConstantesDocumento.IMPRESO));
    this.setRenderBtnGenerar((Boolean) false);
    this.setRenderBtnImprimir((Boolean) false);
    this.setRenderBtnCerrar((Boolean) true);
    LOGGER.debug("Factura FX actualizada a estado impresa");
  }

  /**
   * Gets the reporte pdf.
   *
   * @return the reporte pdf
   */
  public StreamedContent getReportePDF() {
	// cambiar a estado impreso la fx
    this.imprimirFacturaFX();
    // generar el reporte
    Map<String, Object> parametros = new HashMap<>();
    Timestamp tmsFecha;
    facturaGenerada = reportesComercioExteriorEJBLocal.consultarFacturaFXReimprimir(facturaGenerada.getId());
    listaProductosDocumento = this.reportesComercioExteriorEJBLocal.consultarProductosPorDocumento(facturaGenerada.getId());
    SimpleDateFormat ft = new SimpleDateFormat("dd-MM-yyyy");
    String fechaStringGeneracion = ft.format(this.facturaGenerada.getFechaGeneracion());
    Calendar Calendario = Calendar.getInstance();
    Calendario.setTimeInMillis(this.facturaGenerada.getFechaGeneracion().getTime());
    Integer intCantidadDiasVigencia = 0;
    String strNombreIncoterm = "";
    String strLugarIncoterm = "";
    Integer cantidadEstibas = 0;
    Integer pesoBrutoEstibas = 0;
    String strObservacionMarcacion2 = "";
    if (this.facturaGenerada.getDocumentoXNegociacion() != null) {
      intCantidadDiasVigencia = this.facturaGenerada.getDocumentoXNegociacion().getCantidadDiasVigencia();
      strNombreIncoterm = this.facturaGenerada.getDocumentoXNegociacion().getTerminoIncoterm().getDescripcion();
      strLugarIncoterm = this.facturaGenerada.getDocumentoXNegociacion().getLugarIncoterm();
      cantidadEstibas = this.facturaGenerada.getDocumentoXNegociacion().getCantidadEstibas();
      pesoBrutoEstibas = this.facturaGenerada.getDocumentoXNegociacion().getPesoBrutoEstibas();
      strObservacionMarcacion2 = this.facturaGenerada.getDocumentoXNegociacion().getObservacionesMarcacion2();
    }
    Calendario.add(Calendar.DATE, intCantidadDiasVigencia);
    tmsFecha = new Timestamp(Calendario.getTimeInMillis());
    String fechaStringVigencia = ft.format(tmsFecha);
    String fechaStringDespacho = ft.format(this.facturaGenerada.getFechaEsperadaEntrega());
    BigDecimal dblValorTotalNeg = this.totalValorNeg.multiply(new BigDecimal(100)).divide(new BigDecimal(100));
    Numero_a_Letra_Ingles NumLetraIng = new Numero_a_Letra_Ingles();
    String valorLetrasIngles = NumLetraIng.convert(dblValorTotalNeg.doubleValue());
    parametros.put("cliente", facturaGenerada.getCliente().getNombre());
    parametros.put("nit", facturaGenerada.getCliente().getNit());
    parametros.put("ciudad", facturaGenerada.getCliente().getCiudad() == null ? "" : facturaGenerada.getCliente().getCiudad().getNombre());
    parametros.put("direccion", facturaGenerada.getCliente().getDireccion());
    parametros.put("telefono", facturaGenerada.getCliente().getTelefono());
    parametros.put("contacto", facturaGenerada.getCliente().getContacto());
    parametros.put("documento", this.facturaGenerada.getDocumentoCliente());
    parametros.put("fecha", fechaStringGeneracion);
    parametros.put("numFactura", this.facturaGenerada.getConsecutivoDocumento());
    parametros.put("tipoImp", "ORIGINAL");
    parametros.put("fechaVigencia", fechaStringVigencia);
    parametros.put("fechaDespacho", fechaStringDespacho);
    parametros.put("totalPesoNeto", this.totalPesoNeto.doubleValue());
    parametros.put("totalPesoBruto", this.totalPesoBruto.doubleValue());
    parametros.put("totalCajas", this.totalCantidadCajas.doubleValue());
    parametros.put("totalPallets", this.totalPallets.doubleValue());
    parametros.put("costoEntrega", this.totalCostoEntrega.doubleValue());
    parametros.put("costoSeguro", this.totalCostoSeguro.doubleValue());
    parametros.put("costoFlete", this.totalCostoFlete.doubleValue());
    parametros.put("otrosCostos", this.totalOtrosGastos.doubleValue());
    parametros.put("totalNegociacion", this.totalValorNeg.doubleValue());
    parametros.put("incoterm", strNombreIncoterm);
    parametros.put("lugarIncoterm", "(" + strLugarIncoterm + ")");
    parametros.put("valorLetras", valorLetrasIngles);
    parametros.put("qEstibas", cantidadEstibas.doubleValue());
    parametros.put("PesoBrutoEstibas", pesoBrutoEstibas.doubleValue());
    parametros.put("descripcion_envio", this.strDescripcion);
    parametros.put("anulada", "");
    if (facturaGenerada.getCliente().getModoFactura() == 1) {
      parametros.put("metodoPago", facturaGenerada.getCliente().getMetodoPago() == null ? "" : facturaGenerada.getCliente().getMetodoPago().getDescripcionIngles());
    } else {
      parametros.put("metodoPago", facturaGenerada.getCliente().getMetodoPago() == null ? "" : facturaGenerada.getCliente().getMetodoPago().getDescripcion());
    }
    if (facturaGenerada.getCliente().getModoFactura() == 1) {
      String productoIngles;
      String unidadIngles;
      String tipoLoteIngles;
      for (ProductosXDocumento produ : this.listaProductosDocumento) {
        productoIngles = produ.getProductosInventario().getProductosInventarioComext().getDescripcion();
        unidadIngles = produ.getUnidade().getNombreIngles();
        tipoLoteIngles = produ.getProductosInventario().getProductosInventarioComext().getTipoLoteoic().getDescripcionIngles();
        produ.getProductosInventario().setNombre(productoIngles);
        produ.getUnidade().setNombre(unidadIngles);
        produ.getProductosInventario().getProductosInventarioComext().getTipoLoteoic().setDescripcion(tipoLoteIngles);
      }
    } else if (facturaGenerada.getCliente().getModoFactura() == 3) {
      String productoIngles;
      String unidadIngles;
      for (ProductosXDocumento produ : this.listaProductosDocumento) {
        productoIngles = produ.getProductosInventario().getProductosInventarioComext().getNombrePrdProveedor();
        unidadIngles = produ.getUnidade().getNombre();
        produ.getProductosInventario().setNombre(productoIngles);
        produ.getUnidade().setNombre(unidadIngles);
      }
    }
    String consecutivoFP = "";
    if (this.listaProductosDocumento != null && !this.listaProductosDocumento.isEmpty()) {
      try {
        consecutivoFP = this.reportesComercioExteriorEJBLocal.consultarConsecutivoOrdenFacturaFX(this.listaProductosDocumento.get(0).getId().getIdDocumento());
        parametros.put("solicitud", consecutivoFP);
      } catch (Exception ex) {
        consecutivoFP = null;
        parametros.put("solicitud", null);
        LOGGER.error(ex.getMessage());
      }
    }
    List<DocumentoXLotesoic> docxLotesOic = this.reportesComercioExteriorEJBLocal.consultarPorConsecutivoDocumento(consecutivoFP);
    Map<Long, String> lotesMap = new HashMap<>();
    for (DocumentoXLotesoic lotes : docxLotesOic) {
      lotesMap.put(lotes.getTipoLoteoic().getId(), lotes.getConsecutivo());
    }
    List<ReporteReimprimirFacturaDTO> reporteDTOS = new ArrayList<ReporteReimprimirFacturaDTO>();
    for (ProductosXDocumento prod : listaProductosDocumento) {
      ReporteReimprimirFacturaDTO registro = new ReporteReimprimirFacturaDTO();
      registro.setProductoInventarioNombre(prod.getProductosInventario().getNombre());
      registro.setCantidad1(prod.getCantidad1().doubleValue());
      registro.setTotalPesoNetoItem(prod.getTotalPesoNetoItem().doubleValue());
      registro.setProductoInventarioSku(prod.getProductosInventario().getSku());
      registro.setValorTotal(prod.getValorTotal().doubleValue());
      Double precioUS = 0.0;
      if (prod.getProductosInventario() != null && prod.getProductosInventario().getProductosXClienteComexts() != null && !prod.getProductosInventario().getProductosXClienteComexts().isEmpty()) {
        precioUS = prod.getProductosInventario().getProductosXClienteComexts().get(0).getPrecio().doubleValue();
      }
      registro.setPrecioUSD(precioUS);
      registro.setPosicionArancelaria(prod.getProductosInventario().getProductosInventarioComext().getPosicionArancelaria());
      registro.setUnidadNombre(prod.getUnidade().getNombre());
      registro.setTipoLoteOICDesc(prod.getProductosInventario().getProductosInventarioComext().getTipoLoteoic().getDescripcion());
      String consecDocxlote = "";
      if (prod.getProductosInventario().getProductosInventarioComext().getTipoLoteoic().getId() != null) {
        registro.setDocxLoteOICConsec(lotesMap.get(prod.getProductosInventario().getProductosInventarioComext().getTipoLoteoic().getId()));
      }
      registro.setValorUnitarioUSD(prod.getValorUnitarioUsd().doubleValue());
      registro.setTotalCajasPallet(prod.getCantidadPalletsItem().doubleValue());
      reporteDTOS.add(registro);
    }
    if (facturaGenerada.getDocumentoXNegociacion().getSolicitudCafe()) {
      Collections.sort(reporteDTOS);
    }
    JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(reporteDTOS);
    try {
      Hashtable<String, String> parametrosConfigReporte = new Hashtable<>();
      parametrosConfigReporte.put("tipo", "pdf");
      String nombreReporte = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/reportes/Report_FX.jasper");
      ByteArrayOutputStream os = (ByteArrayOutputStream) com.ssl.jv.gip.util.GeneradorReportes.generar(parametrosConfigReporte, nombreReporte, null, null, null, parametros, ds);
      reportePDF = new DefaultStreamedContent(new ByteArrayInputStream(os.toByteArray()), "application/pdf ", "FacturaExportacion.pdf");
    } catch (Exception e) {
      this.addMensajeError(e);
    }
    return reportePDF;
  }

  /**
   * Cancelar.
   */
  public void cancelar() {
    setRenderBtnImprimir((Boolean) false);
    this.consultarFacturasExportacion();
  }

  /**
   * Gets the lista facturas exportacion.
   *
   * @return the lista facturas exportacion
   */
  public List<Documento> getListaFacturasExportacion() {
    return listaFacturasExportacion;
  }

  /**
   * Sets the lista facturas exportacion.
   *
   * @param listaFacturasExportacion the new lista facturas exportacion
   */
  public void setListaFacturasExportacion(List<Documento> listaFacturasExportacion) {
    this.listaFacturasExportacion = listaFacturasExportacion;
  }

  /**
   * Gets the language.
   *
   * @return the language
   */
  public Integer getLanguage() {
    return language;
  }

  /**
   * Sets the language.
   *
   * @param language the new language
   */
  public void setLanguage(Integer language) {
    this.language = language;
  }

  /**
   * Gets the modo.
   *
   * @return the modo
   */
  public Modo getModo() {
    return modo;
  }

  /**
   * Sets the modo.
   *
   * @param modo the new modo
   */
  public void setModo(Modo modo) {
    this.modo = modo;
  }

  /**
   * Gets the seleccionado.
   *
   * @return the seleccionado
   */
  public Documento getSeleccionado() {
    return seleccionado;
  }

  /**
   * Gets the lista productos documento.
   *
   * @return the lista productos documento
   */
  public List<ProductosXDocumento> getListaProductosDocumento() {
    return listaProductosDocumento;
  }

  /**
   * Sets the lista productos documento.
   *
   * @param listaProductosDocumento the new lista productos documento
   */
  public void setListaProductosDocumento(List<ProductosXDocumento> listaProductosDocumento) {
    this.listaProductosDocumento = listaProductosDocumento;
  }

  /**
   * Gets the total cantitad1.
   *
   * @return the total cantitad1
   */
  public BigDecimal getTotalCantitad1() {
    return totalCantitad1;
  }

  /**
   * Sets the total cantitad1.
   *
   * @param totalCantitad1 the new total cantitad1
   */
  public void setTotalCantitad1(BigDecimal totalCantitad1) {
    this.totalCantitad1 = totalCantitad1;
  }

  /**
   * Gets the total valor total.
   *
   * @return the total valor total
   */
  public BigDecimal getTotalValorTotal() {
    return totalValorTotal;
  }

  /**
   * Sets the total valor total.
   *
   * @param totalValorTotal the new total valor total
   */
  public void setTotalValorTotal(BigDecimal totalValorTotal) {
    this.totalValorTotal = totalValorTotal;
  }

  /**
   * Gets the total cantidad x embalaje.
   *
   * @return the total cantidad x embalaje
   */
  public BigDecimal getTotalCantidadXEmbalaje() {
    return totalCantidadXEmbalaje;
  }

  /**
   * Sets the total cantidad x embalaje.
   *
   * @param totalCantidadXEmbalaje the new total cantidad x embalaje
   */
  public void setTotalCantidadXEmbalaje(BigDecimal totalCantidadXEmbalaje) {
    this.totalCantidadXEmbalaje = totalCantidadXEmbalaje;
  }

  /**
   * Gets the total cantidad cajas.
   *
   * @return the total cantidad cajas
   */
  public BigDecimal getTotalCantidadCajas() {
    return totalCantidadCajas;
  }

  /**
   * Sets the total cantidad cajas.
   *
   * @param totalCantidadCajas the new total cantidad cajas
   */
  public void setTotalCantidadCajas(BigDecimal totalCantidadCajas) {
    this.totalCantidadCajas = totalCantidadCajas;
  }

  /**
   * Gets the total peso neto.
   *
   * @return the total peso neto
   */
  public BigDecimal getTotalPesoNeto() {
    return totalPesoNeto;
  }

  /**
   * Sets the total peso neto.
   *
   * @param totalPesoNeto the new total peso neto
   */
  public void setTotalPesoNeto(BigDecimal totalPesoNeto) {
    this.totalPesoNeto = totalPesoNeto;
  }

  /**
   * Gets the total peso bruto.
   *
   * @return the total peso bruto
   */
  public BigDecimal getTotalPesoBruto() {
    return totalPesoBruto;
  }

  /**
   * Sets the total peso bruto.
   *
   * @param totalPesoBruto the new total peso bruto
   */
  public void setTotalPesoBruto(BigDecimal totalPesoBruto) {
    this.totalPesoBruto = totalPesoBruto;
  }

  /**
   * Gets the lista muestras.
   *
   * @return the lista muestras
   */
  public List<Muestrasxlote> getListaMuestras() {
    return listaMuestras;
  }

  /**
   * Sets the lista muestras.
   *
   * @param listaMuestras the new lista muestras
   */
  public void setListaMuestras(List<Muestrasxlote> listaMuestras) {
    this.listaMuestras = listaMuestras;
  }

  /**
   * Gets the lista producto totales.
   *
   * @return the lista producto totales
   */
  public List<ProductoLoteAsignarLoteOICDTO> getListaProductoTotales() {
    return listaProductoTotales;
  }

  /**
   * Sets the lista producto totales.
   *
   * @param listaProductoTotales the new lista producto totales
   */
  public void setListaProductoTotales(List<ProductoLoteAsignarLoteOICDTO> listaProductoTotales) {
    this.listaProductoTotales = listaProductoTotales;
  }

  /**
   * Gets the total costos.
   *
   * @return the total costos
   */
  public BigDecimal getTotalCostos() {
    return totalCostos;
  }

  /**
   * Sets the total costos.
   *
   * @param totalCostos the new total costos
   */
  public void setTotalCostos(BigDecimal totalCostos) {
    this.totalCostos = totalCostos;
  }

  /**
   * Gets the total costo entrega.
   *
   * @return the total costo entrega
   */
  public BigDecimal getTotalCostoEntrega() {
    return totalCostoEntrega;
  }

  /**
   * Sets the total costo entrega.
   *
   * @param totalCostoEntrega the new total costo entrega
   */
  public void setTotalCostoEntrega(BigDecimal totalCostoEntrega) {
    this.totalCostoEntrega = totalCostoEntrega;
  }

  /**
   * Gets the total costo seguro.
   *
   * @return the total costo seguro
   */
  public BigDecimal getTotalCostoSeguro() {
    return totalCostoSeguro;
  }

  /**
   * Sets the total costo seguro.
   *
   * @param totalCostoSeguro the new total costo seguro
   */
  public void setTotalCostoSeguro(BigDecimal totalCostoSeguro) {
    this.totalCostoSeguro = totalCostoSeguro;
  }

  /**
   * Gets the total costo flete.
   *
   * @return the total costo flete
   */
  public BigDecimal getTotalCostoFlete() {
    return totalCostoFlete;
  }

  /**
   * Sets the total costo flete.
   *
   * @param totalCostoFlete the new total costo flete
   */
  public void setTotalCostoFlete(BigDecimal totalCostoFlete) {
    this.totalCostoFlete = totalCostoFlete;
  }

  /**
   * Gets the total otros gastos.
   *
   * @return the total otros gastos
   */
  public BigDecimal getTotalOtrosGastos() {
    return totalOtrosGastos;
  }

  /**
   * Sets the total otros gastos.
   *
   * @param totalOtrosGastos the new total otros gastos
   */
  public void setTotalOtrosGastos(BigDecimal totalOtrosGastos) {
    this.totalOtrosGastos = totalOtrosGastos;
  }

  /**
   * Gets the total valor neg.
   *
   * @return the total valor neg
   */
  public BigDecimal getTotalValorNeg() {
    return totalValorNeg;
  }

  /**
   * Sets the total valor neg.
   *
   * @param totalValorNeg the new total valor neg
   */
  public void setTotalValorNeg(BigDecimal totalValorNeg) {
    this.totalValorNeg = totalValorNeg;
  }

  /**
   * Gets the fecha actual.
   *
   * @return the fecha actual
   */
  public Date getFechaActual() {
    return fechaActual;
  }

  /**
   * Sets the fecha actual.
   *
   * @param fechaActual the new fecha actual
   */
  public void setFechaActual(Date fechaActual) {
    this.fechaActual = fechaActual;
  }

  /**
   * Gets the consecutivo documento.
   *
   * @return the consecutivo documento
   */
  public String getConsecutivoDocumento() {
    return consecutivoDocumento;
  }

  /**
   * Sets the consecutivo documento.
   *
   * @param consecutivoDocumento the new consecutivo documento
   */
  public void setConsecutivoDocumento(String consecutivoDocumento) {
    this.consecutivoDocumento = consecutivoDocumento;
  }

  /**
   * Gets the str descripcion.
   *
   * @return the str descripcion
   */
  public String getStrDescripcion() {
    return strDescripcion;
  }

  /**
   * Sets the str descripcion.
   *
   * @param strDescripcion the new str descripcion
   */
  public void setStrDescripcion(String strDescripcion) {
    this.strDescripcion = strDescripcion;
  }

  /**
   * @return the renderBtnGenerar
   */
  public Boolean getRenderBtnGenerar() {
    return renderBtnGenerar;
  }

  /**
   * @param renderBtnGenerar the renderBtnGenerar to set
   */
  public void setRenderBtnGenerar(Boolean renderBtnGenerar) {
    this.renderBtnGenerar = renderBtnGenerar;
  }

  /**
   * @return the renderBtnCerrar
   */
  public Boolean getRenderBtnCerrar() {
    return renderBtnCerrar;
  }

  /**
   * @param renderBtnCerrar the renderBtnCerrar to set
   */
  public void setRenderBtnCerrar(Boolean renderBtnCerrar) {
    this.renderBtnCerrar = renderBtnCerrar;
  }

  /**
   * Gets the render imprimir factura.
   *
   * @return the render imprimir factura
   */
  public Boolean getRenderBtnImprimir() {
    return renderBtnImprimir;
  }

  /**
   * Sets the render imprimir factura.
   *
   * @param renderBtnImprimir the new render imprimir factura
   */
  public void setRenderBtnImprimir(Boolean renderBtnImprimir) {
    this.renderBtnImprimir = renderBtnImprimir;
  }

  public MenuMB getMenu() {
    return menu;
  }

  public void setMenu(MenuMB menu) {
    this.menu = menu;
  }
}
