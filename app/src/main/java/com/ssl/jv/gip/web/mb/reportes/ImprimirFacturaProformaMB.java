package com.ssl.jv.gip.web.mb.reportes;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.apache.log4j.Logger;
import org.hibernate.exception.ConstraintViolationException;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import com.ssl.jv.gip.jpa.pojo.Cliente;
import com.ssl.jv.gip.jpa.pojo.Documento;
import com.ssl.jv.gip.jpa.pojo.DocumentoXNegociacion;
import com.ssl.jv.gip.jpa.pojo.ProductosXDocumento;
import com.ssl.jv.gip.negocio.ejb.ReportesEJBLocal;
import com.ssl.jv.gip.web.mb.MenuMB;
import com.ssl.jv.gip.web.mb.UtilMB;
import com.ssl.jv.gip.web.util.Modo;
import com.ssl.jv.gip.web.util.Utilidad;

@ManagedBean(name = "imprimirFacturaProformaMB")
@SessionScoped
public class ImprimirFacturaProformaMB extends UtilMB {

	/**
   *
   */
	private static final long serialVersionUID = -6136563159395512436L;
	private static final Logger LOGGER = Logger.getLogger(ImprimirFacturaProformaMB.class);
	@ManagedProperty(value = "#{menuMB}")
	private MenuMB menu;
	private Modo modo;
	private List<Documento> facturasProforma;
	private List<ProductosXDocumento> productosXDocumentos;
	private Documento seleccionado;
	private String consecutivoDocumento;
	@EJB
	private ReportesEJBLocal reportesEJBLocal;
	private BigDecimal totalCantidad;
	private BigDecimal totalValorUnitario;
	private BigDecimal totalValorTotal;
	private BigDecimal totalPesoNeto;
	private BigDecimal totalPesoBruto;
	private BigDecimal totalCantidadCajas;
	private BigDecimal totalCantidadTendidos;
	private BigDecimal totalCantidadPallets;
	private BigDecimal totalGastos;
	private BigDecimal totalNegociacion;

	@PostConstruct
	public void init() {
	}

	public void consultarFacturasProforma(ActionEvent actionEvent) {
		try {
			consecutivoDocumento = "%" + consecutivoDocumento + "%";
			facturasProforma = reportesEJBLocal.consultarFacturasProformasActivasAprobadasOAsignadasPorConsecutivo(consecutivoDocumento);
		} catch (Exception e) {
			LOGGER.error(e);
			Exception unrollException = (Exception) this.unrollException(e, ConstraintViolationException.class);
			if (unrollException != null) {
				this.addMensajeError(unrollException.getLocalizedMessage());
			} else {
				unrollException = (Exception) this.unrollException(e, RuntimeException.class);
				if (unrollException != null) {
					this.addMensajeError(unrollException.getLocalizedMessage());
				} else {
					this.addMensajeError(e);
				}
			}
		}
	}

	public String editar() {
		String outcome = null;
		try {
			this.modo = Modo.EDITAR;
			totalCantidad = BigDecimal.ZERO;
			totalValorUnitario = BigDecimal.ZERO;
			totalValorTotal = BigDecimal.ZERO;
			totalPesoNeto = BigDecimal.ZERO;
			totalPesoBruto = BigDecimal.ZERO;
			totalCantidadCajas = BigDecimal.ZERO;
			totalCantidadTendidos = BigDecimal.ZERO;
			totalCantidadPallets = BigDecimal.ZERO;
			totalGastos = BigDecimal.ZERO;
			List<DocumentoXNegociacion> documentoXNegociacions = seleccionado.getDocumentoXNegociacions();
			if (documentoXNegociacions != null && !documentoXNegociacions.isEmpty()) {
				DocumentoXNegociacion documentoXNegociacion = documentoXNegociacions.get(0);
				totalGastos = totalGastos.add(documentoXNegociacion.getCostoEntrega() == null ? BigDecimal.ZERO : documentoXNegociacion.getCostoEntrega())
						.add(documentoXNegociacion.getCostoFlete() == null ? BigDecimal.ZERO : documentoXNegociacion.getCostoFlete())
						.add(documentoXNegociacion.getCostoSeguro() == null ? BigDecimal.ZERO : documentoXNegociacion.getCostoSeguro())
						.add(documentoXNegociacion.getOtrosGastos() == null ? BigDecimal.ZERO : documentoXNegociacion.getOtrosGastos());
			}
			productosXDocumentos = reportesEJBLocal.consultarProductosXDocumentosFacturaProformaPorDocumento_PICE(seleccionado.getId());
			for (ProductosXDocumento productosXDocumento : productosXDocumentos) {
				totalCantidad = totalCantidad.add(productosXDocumento.getCantidad1() == null ? BigDecimal.ZERO : productosXDocumento.getCantidad1());
				totalValorUnitario = totalValorUnitario.add(productosXDocumento.getValorUnitarioUsd() == null ? BigDecimal.ZERO : productosXDocumento.getValorUnitarioUsd());
				totalValorTotal = totalValorTotal.add(productosXDocumento.getValorTotal() == null ? BigDecimal.ZERO : productosXDocumento.getValorTotal());
				totalPesoNeto = totalPesoNeto.add(productosXDocumento.getTotalPesoNetoItem() == null ? BigDecimal.ZERO : productosXDocumento.getTotalPesoNetoItem());
				totalPesoBruto = totalPesoBruto.add(productosXDocumento.getTotalPesoBrutoItem() == null ? BigDecimal.ZERO : productosXDocumento.getTotalPesoBrutoItem());
				totalCantidadCajas = totalCantidadCajas.add(productosXDocumento.getCantidadCajasItem() == null ? BigDecimal.ZERO : productosXDocumento.getCantidadCajasItem());
				totalCantidadTendidos = totalCantidadTendidos.add(productosXDocumento.getCantidadXEmbalaje() == null ? BigDecimal.ZERO : productosXDocumento.getCantidadXEmbalaje());
				BigDecimal cantidadXEmbalaje = productosXDocumento.getProductosInventario().getProductosInventarioComext().getCantidadXEmbalaje();
				BigDecimal totalCajasXPallet = productosXDocumento.getProductosInventario().getProductosInventarioComext().getTotalCajasXPallet();
				if ((cantidadXEmbalaje == null || cantidadXEmbalaje.compareTo(BigDecimal.ZERO) == 0) || (totalCajasXPallet == null || totalCajasXPallet.compareTo(BigDecimal.ZERO) == 0)) {
					totalCantidadPallets = BigDecimal.ZERO;
				} else {
					totalCantidadPallets = totalCantidadPallets.add(productosXDocumento.getCantidad1().divide(cantidadXEmbalaje, 2, RoundingMode.HALF_DOWN).divide(totalCajasXPallet, 2, RoundingMode.HALF_DOWN));
				}
			}
			totalNegociacion = totalValorTotal.add(totalGastos);
			outcome = "consultar_factura_proforma";
		} catch (Exception e) {
			LOGGER.error(e);
			this.addMensajeError(e);
		}
		return outcome;
	}

	public StreamedContent getReporteFacturaProforma() {
		StreamedContent reportePDF = null;
		try {
			Map<String, Object> parametros = new HashMap<>();
			Cliente cliente = seleccionado.getCliente();
			DocumentoXNegociacion documentoXNegociacion = new DocumentoXNegociacion();
			List<DocumentoXNegociacion> documentoXNegociacions = seleccionado.getDocumentoXNegociacions();
			if (documentoXNegociacions != null && !documentoXNegociacions.isEmpty()) {
				documentoXNegociacion = documentoXNegociacions.get(0);
			}
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			Date fechaGeneracion = seleccionado.getFechaGeneracion();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(fechaGeneracion);
			calendar.add(Calendar.DAY_OF_MONTH, 1);
			parametros.put("cliente", cliente.getNombre());
			parametros.put("nit", cliente.getNit());
			parametros.put("ciudad", cliente.getCiudad().getNombre());
			parametros.put("direccion", cliente.getDireccion());
			parametros.put("telefono", cliente.getTelefono());
			parametros.put("contacto", cliente.getContacto());
			parametros.put("documento", seleccionado.getDocumentoCliente());
			parametros.put("fecha", sdf.format(fechaGeneracion));
			parametros.put("numFactura", seleccionado.getConsecutivoDocumento());
			parametros.put("tipoImp", "ORIGINAL");
			parametros.put("fechaVigencia", sdf.format(seleccionado.getFechaEsperadaEntrega()));
			parametros.put("fechaDespacho", sdf.format(calendar.getTime()));
			parametros.put("totalPesoNeto", null); // totalPesoNeto
			parametros.put("totalPesoBruto", null); // totalPesoBruto
			parametros.put("totalCajas", null); // totalCantidadCajas
			parametros.put("totalPallets", null); // totalCantidadPallets
			parametros.put("costoEntrega", documentoXNegociacion.getCostoEntrega());
			parametros.put("costoSeguro", documentoXNegociacion.getCostoSeguro());
			parametros.put("costoFlete", documentoXNegociacion.getCostoFlete());
			parametros.put("otrosCostos", documentoXNegociacion.getOtrosGastos());
			parametros.put("totalNegociacion", this.totalNegociacion);
			parametros.put("incoterm", documentoXNegociacion.getTerminoIncoterm() == null ? "" : documentoXNegociacion.getTerminoIncoterm().getDescripcion());
			String lugarIncoterm = documentoXNegociacion.getLugarIncoterm() == null ? "" : documentoXNegociacion.getLugarIncoterm();
			parametros.put("lugarIncoterm", "(" + lugarIncoterm + ")");
			parametros.put("valorLetras", Utilidad.convertNumberToWords(this.totalNegociacion.doubleValue()));
			parametros.put("solicitud", seleccionado.getObservacionDocumento());
			if (cliente.getModoFactura() != null && cliente.getModoFactura() == 1) {
				parametros.put("metodoPago", cliente.getMetodoPago().getDescripcionIngles());
			} else {
				parametros.put("metodoPago", cliente.getMetodoPago().getDescripcion());
			}
			JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(productosXDocumentos);
			Hashtable<String, String> parametrosR = new Hashtable<>();
			parametrosR.put("tipo", "pdf");
			String reporte = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/reportes/FP.jasper");
			ByteArrayOutputStream os = (ByteArrayOutputStream) com.ssl.jv.gip.util.GeneradorReportes.generar(parametrosR, reporte, null, null, null, parametros, ds);
			reportePDF = new DefaultStreamedContent(new ByteArrayInputStream(os.toByteArray()), "application/pdf ", (seleccionado.getConsecutivoDocumento() + "_ORIGINAL.pdf"));
		} catch (Exception e) {
			LOGGER.error(e);
			this.addMensajeError(e);
		}
		return reportePDF;
	}

	public boolean isCreacion() {
		return this.modo != null && this.modo.equals(Modo.CREAR);
	}

	public MenuMB getMenu() {
		return menu;
	}

	public void setMenu(MenuMB menu) {
		this.menu = menu;
	}

	public List<Documento> getFacturasProforma() {
		return facturasProforma;
	}

	public void setFacturasProforma(List<Documento> facturasProforma) {
		this.facturasProforma = facturasProforma;
	}

	public Documento getSeleccionado() {
		return seleccionado;
	}

	public void setSeleccionado(Documento seleccionado) {
		this.seleccionado = seleccionado;
	}

	public String getConsecutivoDocumento() {
		return consecutivoDocumento;
	}

	public void setConsecutivoDocumento(String consecutivoDocumento) {
		this.consecutivoDocumento = consecutivoDocumento;
	}

	public List<ProductosXDocumento> getProductosXDocumentos() {
		return productosXDocumentos;
	}

	public void setProductosXDocumentos(List<ProductosXDocumento> productosXDocumentos) {
		this.productosXDocumentos = productosXDocumentos;
	}

	public BigDecimal getTotalCantidad() {
		return totalCantidad;
	}

	public void setTotalCantidad(BigDecimal totalCantidad) {
		this.totalCantidad = totalCantidad;
	}

	public BigDecimal getTotalValorUnitario() {
		return totalValorUnitario;
	}

	public void setTotalValorUnitario(BigDecimal totalValorUnitario) {
		this.totalValorUnitario = totalValorUnitario;
	}

	public BigDecimal getTotalValorTotal() {
		return totalValorTotal;
	}

	public void setTotalValorTotal(BigDecimal totalValorTotal) {
		this.totalValorTotal = totalValorTotal;
	}

	public BigDecimal getTotalPesoNeto() {
		return totalPesoNeto;
	}

	public void setTotalPesoNeto(BigDecimal totalPesoNeto) {
		this.totalPesoNeto = totalPesoNeto;
	}

	public BigDecimal getTotalPesoBruto() {
		return totalPesoBruto;
	}

	public void setTotalPesoBruto(BigDecimal totalPesoBruto) {
		this.totalPesoBruto = totalPesoBruto;
	}

	public BigDecimal getTotalCantidadCajas() {
		return totalCantidadCajas;
	}

	public void setTotalCantidadCajas(BigDecimal totalCantidadCajas) {
		this.totalCantidadCajas = totalCantidadCajas;
	}

	public BigDecimal getTotalCantidadTendidos() {
		return totalCantidadTendidos;
	}

	public void setTotalCantidadTendidos(BigDecimal totalCantidadTendidos) {
		this.totalCantidadTendidos = totalCantidadTendidos;
	}

	public BigDecimal getTotalCantidadPallets() {
		return totalCantidadPallets;
	}

	public void setTotalCantidadPallets(BigDecimal totalCantidadPallets) {
		this.totalCantidadPallets = totalCantidadPallets;
	}

	public BigDecimal getTotalGastos() {
		return totalGastos;
	}

	public void setTotalGastos(BigDecimal totalGastos) {
		this.totalGastos = totalGastos;
	}

	public BigDecimal getTotalNegociacion() {
		return totalNegociacion;
	}

	public void setTotalNegociacion(BigDecimal totalNegociacion) {
		this.totalNegociacion = totalNegociacion;
	}

}
