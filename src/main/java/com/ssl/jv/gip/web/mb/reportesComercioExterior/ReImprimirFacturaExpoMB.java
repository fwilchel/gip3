package com.ssl.jv.gip.web.mb.reportesComercioExterior;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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

import com.ssl.jv.gip.jpa.pojo.Cliente;
import com.ssl.jv.gip.jpa.pojo.Documento;
import com.ssl.jv.gip.jpa.pojo.Muestrasxlote;
import com.ssl.jv.gip.jpa.pojo.ProductosXDocumento;
import com.ssl.jv.gip.negocio.dto.FiltroDocumentoDTO;
import com.ssl.jv.gip.negocio.dto.ProductoLoteAsignarLoteOICDTO;
import com.ssl.jv.gip.negocio.dto.ReporteReimprimirFacturaDTO;
import com.ssl.jv.gip.negocio.ejb.ComercioExteriorEJBLocal;
import com.ssl.jv.gip.negocio.ejb.ReportesComercioExteriorEJBLocal;
import com.ssl.jv.gip.util.Estado;
import com.ssl.jv.gip.web.mb.AplicacionMB;
import com.ssl.jv.gip.web.mb.MenuMB;
import com.ssl.jv.gip.web.mb.UtilMB;
import com.ssl.jv.gip.web.mb.util.ConstantesTipoDocumento;
import com.ssl.jv.gip.web.mb.util.Numero_a_Letra_Ingles;
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

	private BigDecimal totalPallets = BigDecimal.ZERO;

	private BigDecimal totalOtrosGastos = BigDecimal.ZERO;

	private BigDecimal totalCostos = BigDecimal.ZERO;

	private BigDecimal totalValorNeg = BigDecimal.ZERO;

	private List<Muestrasxlote> listaMuestras;

	private List<ProductoLoteAsignarLoteOICDTO> listaProductoTotales;

	private StreamedContent reportePDF;

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
			
			this.totalCantitad1 = BigDecimal.ZERO;
			this.totalValorTotal = BigDecimal.ZERO;
			this.totalCantidadXEmbalaje = BigDecimal.ZERO;
			this.totalCantidadCajas = BigDecimal.ZERO;
			this.totalPesoNeto  = BigDecimal.ZERO;
			this.totalPesoBruto = BigDecimal.ZERO;	
			
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

				this.totalPallets = this.seleccionado.getDocumentoXNegociacions().get(0).getTotalPallets();
			}

			this.totalValorNeg = this.totalValorTotal.add(totalCostos);


		}catch(Exception e){

		}
	}

	public StreamedContent getReportePDF() {

		Map<String, Object> parametros = new HashMap<String, Object>();
		Timestamp tmsFecha;

		/************Llenado de parametros*************/
		SimpleDateFormat ft = new SimpleDateFormat("dd-MM-yyyy");
		String fechaStringGeneracion = ft.format(this.seleccionado.getFechaGeneracion());


		Calendar Calendario = Calendar.getInstance();
		Calendario.setTimeInMillis(this.seleccionado.getFechaGeneracion().getTime());

		Integer intCantidadDiasVigencia = 0;
		String strNombreIncoterm ="";
		String strLugarIncoterm ="";
		Integer cantidadEstibas = 0;
		Integer pesoBrutoEstibas = 0;
		String strObservacionMarcacion2 = "";
		
		this.seleccionado.setDocumentoXNegociacions(this.reportesComercioExteriorEJBLocal.consultarDocumentoXNegociacionxDocumento(this.seleccionado.getId()));
		
		if(this.seleccionado.getDocumentoXNegociacions() != null && !this.seleccionado.getDocumentoXNegociacions().isEmpty()){
			intCantidadDiasVigencia= this.seleccionado.getDocumentoXNegociacions().get(0).getCantidadDiasVigencia();
			strNombreIncoterm = this.seleccionado.getDocumentoXNegociacions().get(0).getTerminoIncoterm().getDescripcion();
			strLugarIncoterm = this.seleccionado.getDocumentoXNegociacions().get(0).getLugarIncoterm();
			cantidadEstibas = this.seleccionado.getDocumentoXNegociacions().get(0).getCantidadEstibas();
			pesoBrutoEstibas= this.seleccionado.getDocumentoXNegociacions().get(0).getPesoBrutoEstibas();
			strObservacionMarcacion2 = this.seleccionado.getDocumentoXNegociacions().get(0).getObservacionesMarcacion2();
		}

		Calendario.add(Calendar.DATE, intCantidadDiasVigencia);

		tmsFecha = new Timestamp(Calendario.getTimeInMillis());
		String fechaStringVigencia = ft.format(tmsFecha); 

		Cliente docCabeceraCli = this.seleccionado.getCliente();

		String fechaStringDespacho = ft.format(this.seleccionado.getFechaEsperadaEntrega());
		BigDecimal dblValorTotalNeg = this.totalValorNeg.multiply(new BigDecimal(100)).divide(new BigDecimal(100));

		Numero_a_Letra_Ingles NumLetraIng = new Numero_a_Letra_Ingles();
		String valorLetrasIngles = NumLetraIng.convert(dblValorTotalNeg.doubleValue());




		parametros.put("cliente",docCabeceraCli.getNombre());		 
		parametros.put("nit",docCabeceraCli.getNit());
		parametros.put("ciudad",docCabeceraCli.getCiudad().getNombre());		 
		parametros.put("direccion",docCabeceraCli.getDireccion());
		parametros.put("telefono",docCabeceraCli.getTelefono());
		parametros.put("contacto",docCabeceraCli.getContacto());
		parametros.put("documento",this.seleccionado.getDocumentoCliente());
		parametros.put("fecha",fechaStringGeneracion);
		parametros.put("numFactura", this.seleccionado.getConsecutivoDocumento());
		parametros.put("tipoImp", "COPY");
		parametros.put("fechaVigencia", fechaStringVigencia);
		parametros.put("fechaDespacho", fechaStringDespacho);
		parametros.put("totalPesoNeto", this.totalPesoNeto.doubleValue());
		parametros.put("totalPesoBruto", this.totalPesoBruto.doubleValue());
		parametros.put("totalCajas", this.totalValorTotal.doubleValue());
		parametros.put("totalPallets", this.totalPallets.doubleValue());
		parametros.put("costoEntrega", this.totalCostoEntrega.doubleValue());
		parametros.put("costoSeguro", this.totalCostoSeguro.doubleValue());
		parametros.put("costoFlete", this.totalCostoFlete.doubleValue());
		parametros.put("otrosCostos", this.totalOtrosGastos.doubleValue());
		parametros.put("totalNegociacion", this.totalValorNeg.doubleValue());
		parametros.put("incoterm", strNombreIncoterm);
		parametros.put("lugarIncoterm", "(" + strLugarIncoterm + ")");
		parametros.put("valorLetras",valorLetrasIngles);
		parametros.put("solicitud", this.seleccionado.getConsecutivoDocumento());
		parametros.put("qEstibas", cantidadEstibas.doubleValue());	
		parametros.put("PesoBrutoEstibas",pesoBrutoEstibas.doubleValue() ); 
		parametros.put("descripcion_envio", strObservacionMarcacion2);

		if (this.seleccionado.getEstadosxdocumento().getEstado().getId().equals(Estado.ANULADO)) {
			parametros.put("anulada", "ANULADA");
		}
		else{
			parametros.put("anulada", "");
		}	

		if (docCabeceraCli.getModoFactura() == 1){
			parametros.put("metodoPago", docCabeceraCli.getMetodoPago().getDescripcionIngles());
		}else{
			parametros.put("metodoPago", docCabeceraCli.getMetodoPago().getDescripcion());
		}	


		if (docCabeceraCli.getModoFactura()==1)

		{

			String productoIngles;
			String unidadIngles;
			String tipoLoteIngles;

			for (ProductosXDocumento produ: this.listaProductosDocumento){

				productoIngles = produ.getProductosInventario().getProductosInventarioComext().getDescripcion();
				unidadIngles = produ.getUnidade().getNombreIngles();
				tipoLoteIngles = produ.getProductosInventario().getProductosInventarioComext().getTipoLoteoic().getDescripcionIngles();

				produ.getProductosInventario().setNombre(productoIngles);
				produ.getUnidade().setNombre(unidadIngles);
				produ.getProductosInventario().getProductosInventarioComext().getTipoLoteoic().setDescripcion(tipoLoteIngles);  
			}
		}

		else if (docCabeceraCli.getModoFactura()==3)
		{

			String productoIngles;
			String unidadIngles;

			for (ProductosXDocumento produ: this.listaProductosDocumento){

				productoIngles = produ.getProductosInventario().getProductosInventarioComext().getNombrePrdProveedor();
				unidadIngles = produ.getUnidade().getNombre();

				produ.getProductosInventario().setNombre(productoIngles);
				produ.getUnidade().setNombre(unidadIngles);

			}
		}
		
		List<ReporteReimprimirFacturaDTO> reporteDTOS = new ArrayList<ReporteReimprimirFacturaDTO>();
		for(ProductosXDocumento prod:listaProductosDocumento){
			ReporteReimprimirFacturaDTO registro =  new ReporteReimprimirFacturaDTO();
			
			registro.setProductoInventarioNombre(prod.getProductosInventario().getNombre());
			registro.setCantidad1(prod.getCantidad1().doubleValue());
			registro.setTotalPesoNetoItem(prod.getTotalPesoNetoItem().doubleValue());
			registro.setProductoInventarioSku(prod.getProductosInventario().getSku());
			registro.setValorTotal(prod.getValorTotal().doubleValue());
			
			Double precioUS = 0.0;
			if(prod.getProductosInventario() != null && prod.getProductosInventario().getProductosxclientes() != null && !prod.getProductosInventario().getProductosxclientes().isEmpty()){
				precioUS=prod.getProductosInventario().getProductosxclientes().get(0).getPrecioUsd().doubleValue();
			}			
			registro.setPrecioUSD(precioUS);
			
			registro.setPosicionArancelaria(prod.getProductosInventario().getProductosInventarioComext().getPosicionArancelaria());
			
			registro.setUnidadNombre(prod.getUnidade().getNombre());
			
			registro.setTipoLoteOICDesc(prod.getProductosInventario().getProductosInventarioComext().getTipoLoteoic().getDescripcion());
			
			String consecDocxlote="";
			if(prod.getProductosInventario() != null && prod.getProductosInventario().getProductosInventarioComext() != null && 
					prod.getProductosInventario().getProductosInventarioComext().getTipoLoteoic() != null &&
					prod.getProductosInventario().getProductosInventarioComext().getTipoLoteoic().getDocumentoXLotesoics() != null &&
					!prod.getProductosInventario().getProductosInventarioComext().getTipoLoteoic().getDocumentoXLotesoics().isEmpty()){
				consecDocxlote = prod.getProductosInventario().getProductosInventarioComext().getTipoLoteoic().getDocumentoXLotesoics().get(0).getConsecutivo();
				
			}
			registro.setDocxLoteOICConsec(consecDocxlote);
			
			registro.setValorUnitarioUSD(prod.getValorUnitarioUsd().doubleValue());
			
			registro.setTotalCajasPallet(prod.getCantidadPalletsItem().doubleValue());
			
			reporteDTOS.add(registro);
			
		}

		JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(reporteDTOS);

		try {
			Hashtable<String, String> parametrosR=new Hashtable<String, String>();
			parametrosR.put("tipo", "pdf");
			String reporte=FacesContext.getCurrentInstance().getExternalContext().getRealPath("/reportes/Report_FX.jasper");
			ByteArrayOutputStream os=(ByteArrayOutputStream)com.ssl.jv.gip.util.GeneradorReportes.generar(parametrosR, reporte, null, null, null, parametros, ds);
			reportePDF = new DefaultStreamedContent(new ByteArrayInputStream(os.toByteArray()), "application/pdf ", "Report_FX.pdf");

		} catch (Exception e) {
			this.addMensajeError(e);
		}
		return reportePDF;

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
