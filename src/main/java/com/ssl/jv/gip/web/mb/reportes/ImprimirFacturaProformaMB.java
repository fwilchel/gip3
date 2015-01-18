package com.ssl.jv.gip.web.mb.reportes;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;

import org.apache.log4j.Logger;
import org.hibernate.exception.ConstraintViolationException;

import com.ssl.jv.gip.jpa.pojo.Documento;
import com.ssl.jv.gip.jpa.pojo.DocumentoXNegociacion;
import com.ssl.jv.gip.jpa.pojo.ProductosXDocumento;
import com.ssl.jv.gip.negocio.ejb.ReportesEJBLocal;
import com.ssl.jv.gip.web.mb.AplicacionMB;
import com.ssl.jv.gip.web.mb.MenuMB;
import com.ssl.jv.gip.web.mb.UtilMB;
import com.ssl.jv.gip.web.mb.comercioexterior.ModificarListaEmpaqueMB;
import com.ssl.jv.gip.web.util.Modo;

@ManagedBean(name = "imprimirFacturaProformaMB")
@SessionScoped
public class ImprimirFacturaProformaMB extends UtilMB {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6136563159395512436L;

	private static final Logger LOGGER = Logger
			.getLogger(ModificarListaEmpaqueMB.class);

	@ManagedProperty(value = "#{menuMB}")
	private MenuMB menu;
	private String idUsuario;

	private Integer language = AplicacionMB.SPANISH;
	private Modo modo;

	private List<Documento> facturasProforma;
	private List<ProductosXDocumento> productosXDocumentos;
	private Documento seleccionado;

	private Date fechaActual;
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
		idUsuario = menu.getUsuario().getId();
		fechaActual = new Date();
	}

	public void consultarFacturasProforma(ActionEvent actionEvent) {
		try {
			consecutivoDocumento = "%" + consecutivoDocumento + "%";
			facturasProforma = reportesEJBLocal
					.consultarFacturasProformasActivasAprobadasOAsignadasPorConsecutivo(consecutivoDocumento);
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

	public String editar() {
		String outcome = null;
		try {
			this.modo = Modo.EDICION;

			totalCantidad = BigDecimal.ZERO;
			totalValorUnitario = BigDecimal.ZERO;
			totalValorTotal = BigDecimal.ZERO;
			totalPesoNeto = BigDecimal.ZERO;
			totalPesoBruto = BigDecimal.ZERO;
			totalCantidadCajas = BigDecimal.ZERO;
			totalCantidadTendidos = BigDecimal.ZERO;
			totalCantidadPallets = BigDecimal.ZERO;

			totalGastos = BigDecimal.ZERO;
			List<DocumentoXNegociacion> documentoXNegociacions = seleccionado
					.getDocumentoXNegociacions();
			if (documentoXNegociacions != null
					&& documentoXNegociacions.isEmpty()) {
				DocumentoXNegociacion documentoXNegociacion = documentoXNegociacions
						.get(0);
				totalGastos = (documentoXNegociacion.getCostoEntrega() == null ? BigDecimal.ZERO
						: documentoXNegociacion.getCostoEntrega())
						.add(documentoXNegociacion.getCostoFlete() == null ? BigDecimal.ZERO
								: documentoXNegociacion.getCostoFlete())
						.add(documentoXNegociacion.getCostoSeguro() == null ? BigDecimal.ZERO
								: documentoXNegociacion.getCostoSeguro())
						.add(documentoXNegociacion.getOtrosGastos() == null ? BigDecimal.ZERO
								: documentoXNegociacion.getOtrosGastos());
			}

			productosXDocumentos = reportesEJBLocal
					.consultarProductosXDocumentosFacturaProformaPorDocumentoYCliente(
							seleccionado.getId(), seleccionado.getCliente()
									.getId());
			for (ProductosXDocumento productosXDocumento : productosXDocumentos) {
				totalCantidad = totalCantidad.add(productosXDocumento
						.getCantidad1() == null ? BigDecimal.ZERO
						: productosXDocumento.getCantidad1());
				totalValorUnitario = totalValorUnitario.add(productosXDocumento
						.getValorUnitarioUsd() == null ? BigDecimal.ZERO
						: productosXDocumento.getValorUnitarioUsd());
				totalValorTotal = totalValorTotal.add(productosXDocumento
						.getValorTotal() == null ? BigDecimal.ZERO
						: productosXDocumento.getValorTotal());
				totalPesoNeto = totalPesoNeto.add(productosXDocumento
						.getTotalPesoNetoItem() == null ? BigDecimal.ZERO
						: productosXDocumento.getTotalPesoNetoItem());
				totalPesoBruto = totalPesoBruto.add(productosXDocumento
						.getTotalPesoBrutoItem() == null ? BigDecimal.ZERO
						: productosXDocumento.getTotalPesoBrutoItem());
				totalCantidadCajas = totalCantidadCajas.add(productosXDocumento
						.getCantidadCajasItem() == null ? BigDecimal.ZERO
						: productosXDocumento.getCantidadCajasItem());
				totalCantidadTendidos = totalCantidadTendidos
						.add(productosXDocumento.getCantidadXEmbalaje() == null ? BigDecimal.ZERO
								: productosXDocumento.getCantidadXEmbalaje());
				totalCantidadPallets = totalCantidadPallets
						.add(productosXDocumento.getCantidadPalletsItem() == null ? BigDecimal.ZERO
								: productosXDocumento.getCantidadPalletsItem());
			}

			totalNegociacion = totalValorTotal.add(totalGastos);

			outcome = "consultar_factura_proforma";
		} catch (Exception e) {
			LOGGER.error(e);
			this.addMensajeError(e);
		}
		return outcome;
	}

	public String imprimirFacturaProforma() {
		String outcome = null;
		try {
			outcome = "listado_SP_Imprimir_FP";
		} catch (Exception e) {
			LOGGER.error(e);
			this.addMensajeError(e);
		}
		return outcome;
	}

	public boolean isCreacion() {
		if (this.modo != null && this.modo.equals(Modo.CREACION)) {
			return true;
		} else {
			return false;
		}
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

	public Date getFechaActual() {
		return fechaActual;
	}

	public void setFechaActual(Date fechaActual) {
		this.fechaActual = fechaActual;
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

	public void setProductosXDocumentos(
			List<ProductosXDocumento> productosXDocumentos) {
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
