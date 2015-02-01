package com.ssl.jv.gip.web.mb.reportesComercioExterior;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;

import com.ssl.jv.gip.jpa.pojo.Documento;
import com.ssl.jv.gip.jpa.pojo.Muestrasxlote;
import com.ssl.jv.gip.jpa.pojo.ProductosXDocumento;
import com.ssl.jv.gip.negocio.dto.FiltroDocumentoDTO;
import com.ssl.jv.gip.negocio.dto.ProductoLoteAsignarLoteOICDTO;
import com.ssl.jv.gip.negocio.ejb.ComercioExteriorEJBLocal;
import com.ssl.jv.gip.negocio.ejb.ReportesComercioExteriorEJBLocal;
import com.ssl.jv.gip.util.Estado;
import com.ssl.jv.gip.web.mb.AplicacionMB;
import com.ssl.jv.gip.web.mb.MenuMB;
import com.ssl.jv.gip.web.mb.UtilMB;
import com.ssl.jv.gip.web.mb.util.ConstantesTipoDocumento;
import com.ssl.jv.gip.web.util.Modo;



/**
 * The Class ReImprimirFacturaExpoMB.
 */
@ManagedBean(name = "reimprimirFacturaExpoMB")
@ViewScoped
public class ReImprimirFacturaExpoMB extends UtilMB {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5093870535116322203L;
	
	private static final Logger LOGGER = Logger
			.getLogger(ReImprimirFacturaExpoMB.class);
	
	@EJB
	private ReportesComercioExteriorEJBLocal reportesComercioExteriorEJBLocal;
	
	@EJB
	private ComercioExteriorEJBLocal comercioExteriorEJBLocal;
	
	private List<Documento> listaFacturasExportacion;

	private Integer language = AplicacionMB.SPANISH;
	private Modo modo;
	
	private FiltroDocumentoDTO filtroDocumentoDTO;
	
	private Documento seleccionado;
	
	private List<ProductosXDocumento> listaProductosDocumento;
	
	private BigDecimal totalCantitad1 = BigDecimal.ZERO;
	
	private BigDecimal totalValorTotal = BigDecimal.ZERO;
	
	private BigDecimal totalCantidadXEmbalaje = BigDecimal.ZERO;
	
	private BigDecimal totalCantidadCajas = BigDecimal.ZERO;
	
	private BigDecimal totalPesoNeto = BigDecimal.ZERO;
	
	private BigDecimal totalPesoBruto = BigDecimal.ZERO;
	
	private BigDecimal totalCostoEntrega = BigDecimal.ZERO;
	
	private BigDecimal totalCostoSeguro = BigDecimal.ZERO;
	
	private BigDecimal totalCostoFlete = BigDecimal.ZERO;
	
	private BigDecimal totalOtrosGastos = BigDecimal.ZERO;
	
	private BigDecimal totalCostos = BigDecimal.ZERO;
	
	private BigDecimal totalValorNeg = BigDecimal.ZERO;
	
	private List<Muestrasxlote> listaMuestras;
	
	private List<ProductoLoteAsignarLoteOICDTO> listaProductoTotales;
	
	
	
	@PostConstruct
	public void init() {
		
		try {
			filtroDocumentoDTO = new FiltroDocumentoDTO();
			this.consultarFacturasExportacion();
		} catch (Exception e) {
			LOGGER.error(e);
			this.addMensajeError(e);
		}
		
	}
	
	private void consultarFacturasExportacion() {
		try {
			filtroDocumentoDTO
					.setIdTipoDocumento((long) ConstantesTipoDocumento.FACTURA_EXPORTACION);
			filtroDocumentoDTO.setIdEstado(Estado.IMPRESO.getCodigo());
			filtroDocumentoDTO.setIdEstado2(Estado.ANULADO.getCodigo());
			this.listaFacturasExportacion = this.reportesComercioExteriorEJBLocal
					.consultarFacturasExportacion(filtroDocumentoDTO);
		} catch (Exception e) {
			LOGGER.error(e);
			this.addMensajeError(e);
		}
	}
	
	public void reimprimir(){
		
		
		
	}

	public List<Documento> getListaFacturasExportacion() {
		return listaFacturasExportacion;
	}

	public void setListaFacturasExportacion(List<Documento> listaFacturasExportacion) {
		this.listaFacturasExportacion = listaFacturasExportacion;
	}

	public FiltroDocumentoDTO getFiltroDocumentoDTO() {
		return filtroDocumentoDTO;
	}

	public void setFiltroDocumentoDTO(FiltroDocumentoDTO filtroDocumentoDTO) {
		this.filtroDocumentoDTO = filtroDocumentoDTO;
	}

	public Integer getLanguage() {
		return language;
	}

	public void setLanguage(Integer language) {
		this.language = language;
	}

	public Modo getModo() {
		return modo;
	}

	public void setModo(Modo modo) {
		this.modo = modo;
	}

	public Documento getSeleccionado() {
		return seleccionado;
	}

	public void setSeleccionado(Documento seleccionado) {
		this.seleccionado = seleccionado;
		try{
			listaProductosDocumento = this.reportesComercioExteriorEJBLocal.consultarProductosPorDocumento(seleccionado.getId());	
			for(ProductosXDocumento pr:listaProductosDocumento){
				this.totalCantitad1 = this.totalCantitad1.add(pr.getCantidad1());
				this.totalValorTotal = this.totalValorTotal.add(pr.getValorTotal());
				this.totalCantidadXEmbalaje = this.totalCantidadXEmbalaje.add(pr.getCantidadXEmbalaje());
				this.totalCantidadCajas = this.totalCantidadCajas.add(pr.getCantidadCajasItem());
				this.totalPesoNeto  = this.totalPesoNeto.add(pr.getTotalPesoNetoItem());
				this.totalPesoBruto = this.totalPesoBruto.add(pr.getTotalPesoBrutoItem());				
			}
			
			//this.listaMuestras = this.reportesComercioExteriorEJBLocal.consultarMuestrasPorCantidad(this.seleccionado.getDocumentoXLotesoics().get(0).getTotalCantidad());
			this.listaProductoTotales = comercioExteriorEJBLocal.consultarProductoPorDocumentoLoteAsignarLotesOIC(this.seleccionado.getId(), this.seleccionado.getCliente().getId());
			
			if(this.seleccionado.getDocumentoXNegociacions() != null && !this.seleccionado.getDocumentoXNegociacions().isEmpty()){
				this.totalCostoEntrega = this.seleccionado.getDocumentoXNegociacions().get(0).getCostoEntrega();
				this.totalCostoSeguro = this.seleccionado.getDocumentoXNegociacions().get(0).getCostoSeguro();
				this.totalCostoFlete = this.seleccionado.getDocumentoXNegociacions().get(0).getCostoFlete();
				this.totalOtrosGastos = this.seleccionado.getDocumentoXNegociacions().get(0).getOtrosGastos();

				this.totalCostos = totalCostoEntrega.add(totalCostoSeguro).add(totalCostoFlete).add(totalOtrosGastos);
			}
			
			this.totalValorNeg = this.totalValorTotal.add(totalCostos);
			
			
		}catch(Exception e){
			
		}
	}

	public List<ProductosXDocumento> getListaProductosDocumento() {
		return listaProductosDocumento;
	}

	public void setListaProductosDocumento(
			List<ProductosXDocumento> listaProductosDocumento) {
		this.listaProductosDocumento = listaProductosDocumento;
	}

	public BigDecimal getTotalCantitad1() {
		return totalCantitad1;
	}

	public void setTotalCantitad1(BigDecimal totalCantitad1) {
		this.totalCantitad1 = totalCantitad1;
	}

	public BigDecimal getTotalValorTotal() {
		return totalValorTotal;
	}

	public void setTotalValorTotal(BigDecimal totalValorTotal) {
		this.totalValorTotal = totalValorTotal;
	}

	public BigDecimal getTotalCantidadXEmbalaje() {
		return totalCantidadXEmbalaje;
	}

	public void setTotalCantidadXEmbalaje(BigDecimal totalCantidadXEmbalaje) {
		this.totalCantidadXEmbalaje = totalCantidadXEmbalaje;
	}

	public BigDecimal getTotalCantidadCajas() {
		return totalCantidadCajas;
	}

	public void setTotalCantidadCajas(BigDecimal totalCantidadCajas) {
		this.totalCantidadCajas = totalCantidadCajas;
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

	public List<Muestrasxlote> getListaMuestras() {
		return listaMuestras;
	}

	public void setListaMuestras(List<Muestrasxlote> listaMuestras) {
		this.listaMuestras = listaMuestras;
	}

	public List<ProductoLoteAsignarLoteOICDTO> getListaProductoTotales() {
		return listaProductoTotales;
	}

	public void setListaProductoTotales(
			List<ProductoLoteAsignarLoteOICDTO> listaProductoTotales) {
		this.listaProductoTotales = listaProductoTotales;
	}

	public BigDecimal getTotalCostos() {
		return totalCostos;
	}

	public void setTotalCostos(BigDecimal totalCostos) {
		this.totalCostos = totalCostos;
	}

	public BigDecimal getTotalCostoEntrega() {
		return totalCostoEntrega;
	}

	public void setTotalCostoEntrega(BigDecimal totalCostoEntrega) {
		this.totalCostoEntrega = totalCostoEntrega;
	}

	public BigDecimal getTotalCostoSeguro() {
		return totalCostoSeguro;
	}

	public void setTotalCostoSeguro(BigDecimal totalCostoSeguro) {
		this.totalCostoSeguro = totalCostoSeguro;
	}

	public BigDecimal getTotalCostoFlete() {
		return totalCostoFlete;
	}

	public void setTotalCostoFlete(BigDecimal totalCostoFlete) {
		this.totalCostoFlete = totalCostoFlete;
	}

	public BigDecimal getTotalOtrosGastos() {
		return totalOtrosGastos;
	}

	public void setTotalOtrosGastos(BigDecimal totalOtrosGastos) {
		this.totalOtrosGastos = totalOtrosGastos;
	}

	public BigDecimal getTotalValorNeg() {
		return totalValorNeg;
	}

	public void setTotalValorNeg(BigDecimal totalValorNeg) {
		this.totalValorNeg = totalValorNeg;
	}
	
	
	
	
	

}
