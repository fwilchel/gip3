package com.ssl.jv.gip.web.mb.comercioexterior;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.apache.log4j.Logger;

import com.ssl.jv.gip.jpa.pojo.Cliente;
import com.ssl.jv.gip.jpa.pojo.Documento;
import com.ssl.jv.gip.jpa.pojo.Estado;
import com.ssl.jv.gip.jpa.pojo.Estadosxdocumento;
import com.ssl.jv.gip.jpa.pojo.EstadosxdocumentoPK;
import com.ssl.jv.gip.jpa.pojo.TipoDocumento;
import com.ssl.jv.gip.jpa.pojo.Ubicacion;
import com.ssl.jv.gip.negocio.dto.ProductoDTO;
import com.ssl.jv.gip.negocio.dto.ProductoGenerarFacturaPFDTO;
import com.ssl.jv.gip.negocio.ejb.ComercioExteriorEJBLocal;
import com.ssl.jv.gip.web.mb.AplicacionMB;
import com.ssl.jv.gip.web.mb.UtilMB;
import com.ssl.jv.gip.web.mb.util.ConstantesDocumento;
import com.ssl.jv.gip.web.mb.util.ConstantesTipoDocumento;

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
@ManagedBean(name="generarFacturaPFMB")
@SessionScoped
public class GenerarFacturaPFMB extends UtilMB{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2780795923623719268L;

	private static final Logger LOGGER = Logger.getLogger(GenerarFacturaPFMB.class);
	private Timestamp currentTimeStamp;
	private String consecutivoDocumento;
	private Integer language=AplicacionMB.SPANISH;
	private List<Documento> listaDocumentos;
	private Documento documentoSeleccionado;
	private List<ProductoGenerarFacturaPFDTO> productos;
	private double totalCantidad=0;
	private double totalValorTotal=0;
	private double totalPesoNeto=0;
	private double totalPesoBruto=0;
	private double totalCantidadCajas=0;
	private double totalCantidadTendidos=0;
	private double totalCantidadPallets=0;
	private double totalCostos=0;
	private double totalNegociacion=0;
	
	@EJB
	private ComercioExteriorEJBLocal comercioEjb;
	
	public GenerarFacturaPFMB(){
		
	}
	
	@PostConstruct
	public void init(){
		currentTimeStamp = new Timestamp(System.currentTimeMillis());
		
	}

	public Timestamp getCurrentTimeStamp() {
		return currentTimeStamp;
	}

	public void setCurrentTimeStamp(Timestamp currentTimeStamp) {
		this.currentTimeStamp = currentTimeStamp;
	}

	public String getConsecutivoDocumento() {
		return consecutivoDocumento;
	}

	public void setConsecutivoDocumento(String consecutivoDocumento) {
		this.consecutivoDocumento = consecutivoDocumento;
	}

	public String buscarDocumentos(){
		listaDocumentos=this.comercioEjb.consultarDocumentosPorConsecutivoPedido(consecutivoDocumento);
		return null;
	}

	public List<Documento> getListaDocumentos() {
		return listaDocumentos;
	}

	public void setListaDocumentos(List<Documento> listaDocumentos) {
		this.listaDocumentos = listaDocumentos;
	}

	public Documento getDocumentoSeleccionado() {
		return documentoSeleccionado;
	}

	public void setDocumentoSeleccionado(Documento documentoSeleccionado) {
		this.documentoSeleccionado = documentoSeleccionado;
	}
	
	public double getTotalCantidad() {
		return totalCantidad;
	}

	public void setTotalCantidad(double totalCantidad) {
		this.totalCantidad = totalCantidad;
	}

	public double getTotalValorTotal() {
		return totalValorTotal;
	}

	public void setTotalValorTotal(double totalValorTotal) {
		this.totalValorTotal = totalValorTotal;
	}

	public double getTotalPesoNeto() {
		return totalPesoNeto;
	}

	public void setTotalPesoNeto(double totalPesoNeto) {
		this.totalPesoNeto = totalPesoNeto;
	}

	public double getTotalPesoBruto() {
		return totalPesoBruto;
	}

	public void setTotalPesoBruto(double totalPesoBruto) {
		this.totalPesoBruto = totalPesoBruto;
	}

	public double getTotalCantidadCajas() {
		return totalCantidadCajas;
	}

	public void setTotalCantidadCajas(double totalCantidadCajas) {
		this.totalCantidadCajas = totalCantidadCajas;
	}

	public double getTotalCantidadTendidos() {
		return totalCantidadTendidos;
	}

	public void setTotalCantidadTendidos(double totalCantidadTendidos) {
		this.totalCantidadTendidos = totalCantidadTendidos;
	}

	public double getTotalCantidadPallets() {
		return totalCantidadPallets;
	}

	public void setTotalCantidadPallets(double totalCantidadPallets) {
		this.totalCantidadPallets = totalCantidadPallets;
	}

	public double getTotalCostos() {
		return totalCostos;
	}

	public void setTotalCostos(double totalCostos) {
		this.totalCostos = totalCostos;
	}

	public double getTotalNegociacion() {
		return totalNegociacion;
	}

	public void setTotalNegociacion(double totalNegociacion) {
		this.totalNegociacion = totalNegociacion;
	}

	public String consultarSolicitudPedido(){
		productos=comercioEjb.consultarProductoPorDocumentoGenerarFacturaProforma(this.documentoSeleccionado.getId(), this.documentoSeleccionado.getCliente().getId());
		this.totalCantidad=0;
		this.totalValorTotal=0;
		this.totalPesoNeto=0;
		this.totalPesoBruto=0;
		this.totalCantidadCajas=0;
		this.totalCantidadTendidos=0;
		this.totalCantidadPallets=0;
		for (ProductoGenerarFacturaPFDTO p:productos){
			this.totalCantidad+=p.getCantidad().doubleValue();
			this.totalValorTotal+=p.getValorTotal().doubleValue();
			this.totalPesoNeto+=p.getTotalPesoNeto().doubleValue();
			this.totalPesoBruto+=p.getTotalPesoBruto().doubleValue();
			this.totalCantidadCajas+=p.getTotalCajas().doubleValue();
			this.totalCantidadTendidos+=p.getTotalCajasTendido().doubleValue();
			this.totalCantidadPallets+=p.getTotalCajasPallet().doubleValue();
		}
		
		totalCostos=0;
		if (this.documentoSeleccionado.getDocumentoXNegociacions()!=null && this.documentoSeleccionado.getDocumentoXNegociacions().size()>0){
			totalCostos=this.documentoSeleccionado.getDocumentoXNegociacions().get(0).getCostoEntrega().doubleValue() + this.documentoSeleccionado.getDocumentoXNegociacions().get(0).getCostoSeguro().doubleValue() +
					this.documentoSeleccionado.getDocumentoXNegociacions().get(0).getCostoFlete().doubleValue() + this.documentoSeleccionado.getDocumentoXNegociacions().get(0).getOtrosGastos().doubleValue(); 
		}
		totalNegociacion= this.totalCostos + this.totalValorTotal;
		return null;
	}

	public List<ProductoGenerarFacturaPFDTO> getProductos() {
		return productos;
	}

	public void setProductos(List<ProductoGenerarFacturaPFDTO> productos) {
		this.productos = productos;
	}
	
	public String crearFactura(){
		
		Documento documento = new Documento();
		documento.setFechaGeneracion(new Timestamp(System.currentTimeMillis()));
		Estadosxdocumento estadosxdocumento=new Estadosxdocumento();
		EstadosxdocumentoPK estadosxdocumentoPK=new EstadosxdocumentoPK();
		estadosxdocumentoPK.setIdEstado((long)ConstantesDocumento.ACTIVO);
		estadosxdocumentoPK.setIdTipoDocumento((long)ConstantesTipoDocumento.FACTURA_PROFORMA);
		estadosxdocumento.setId(estadosxdocumentoPK);
		documento.setEstadosxdocumento(estadosxdocumento);
		documento.setObservacionDocumento(this.documentoSeleccionado.getConsecutivoDocumento());
		documento.setUbicacionDestino(new Ubicacion());
		documento.setUbicacionOrigen(new Ubicacion());
		documento.getUbicacionDestino().setId(this.documentoSeleccionado.getUbicacionOrigen().getId());
		documento.getUbicacionOrigen().setId(this.documentoSeleccionado.getUbicacionOrigen().getId());
		documento.setCliente(this.documentoSeleccionado.getCliente());
		documento.setValorTotal(new BigDecimal(this.totalNegociacion));
		documento.setDocumentoCliente(this.documentoSeleccionado.getDocumentoCliente());
		documento.setFechaEsperadaEntrega(this.documentoSeleccionado.getFechaEsperadaEntrega());

		this.comercioEjb.crearFactura(documento);
			
			//REVISAR CREACION DOC
			String cadena = postDocumentoDao.adicionar3(documento);
				    	      			
			//id_tipo_doc, id_estado, fecha_generacion, observacion, id_cliente, valor_total, documento_cliente 
			
		String[] arrayCadena = cadena.split(";");
		idDocumentoCons = Integer.parseInt(arrayCadena[0]);
		String consecDoc = arrayCadena[1];	    	      			

		
		return null;
	}
	
	 /*public String crearFactura() throws DataBaseException, SQLException,
		InstantiationException, ClassNotFoundException,
		IllegalAccessException {

   FUNCIONALIDAD = 102; //Crear Factura Proforma
	String res = "error_generar_FP";
	Timestamp timestampEsperada = null;
	Timestamp timestampGeneracion = null;
	int res2 = 0;
	int idDoc = this.intIdDocumento;
	
	 try {
		// CREAR DOCUMENTO
			Documento documento = new Documento();
			    		
	    	      			// CONSULTAR ESTADO DE DOCUMENTO = ACTIVO			
  							TipoDocumento objTipoDocumento = new TipoDocumento();
  							objTipoDocumento.setIntId(ConstantesTipoDocumento.FACTURA_PROFORMA);
  							
	    	      			Estado objEstado = new Estado();
	    	      			objEstado.setIntId(ConstantesDocumento.ACTIVO);
	    	      			
	    	    			PostgresDocumentoDAO postDocumentoDao;
	    	    			postDocumentoDao = fabricaPostgres.getDocumentoDAO();

	    	      			// CONVERSION DE FECHAS
	    	      			try {
	    	      				
	    	      				timestampGeneracion = Utilidad.convertirDateTotimestampCompleto(new Date());
	    	      				documento.setDtmFechaGeneracion(timestampGeneracion);
	    	      				
	    	      			} catch (Exception e) {
	    	      				e.printStackTrace();
	    	      			}
	    	      			documento.setObjTipoDocumento(objTipoDocumento);
	    	      			documento.setObjEstado(objEstado);
	    	      			//documento.setStrObservacion((this.intIdDocumento));
	    	      			
	    	      			documento.setStrObservacion(strConsecutivoDocumento);
	    	      			
	    	      			documento.setIntIdUbicacionOrigen(this.intUbicacion);
	    	      			documento.setIntIdUbicacionDestino(this.intUbicacion);
	    	      			
	    	      			Cliente objCliente = new Cliente();
	    	      			objCliente.setIntId(docConsultado.getObjCliente().getIntId());
	    	      			
	    	      			documento.setObjCliente(objCliente);
	    	      			documento.setDblVrTotal(dblValorTotalNeg);
	    	      			documento.setStrDocumentoCliente(docConsultado.getStrDocumentoCliente());	    	      			
	    	      			documento.setDtmFechaEsperadaEntrega(docConsultado.getDtmFechaEsperadaEntrega());
	    	      			
	    	      			//REVISAR CREACION DOC
	    	      			String cadena = postDocumentoDao.adicionar3(documento);
	    	      				    	      			
	    	      			//id_tipo_doc, id_estado, fecha_generacion, observacion, id_cliente, valor_total, documento_cliente 
	    	      			
							String[] arrayCadena = cadena.split(";");
							idDocumentoCons = Integer.parseInt(arrayCadena[0]);
							String consecDoc = arrayCadena[1];	    	      			
	    	      			
	    	    			if (idDocumentoCons > 0) {
	    	    								
	    	    				Auditoria auditoriamod = new Auditoria();				
	    	    				final PostgresAuditoriaDAO AuditoriaDao;				
	    	    				fabricaPostgres = new PostgresDAOFactory();
	    	    				AuditoriaDao = fabricaPostgres.getAuditoriaDAO();
	    	    				
	    	    			    String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	    	    			    SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
	    	    			    Calendar c1 = Calendar.getInstance(); //Fecha y Tiempo actual
	    	    			    String datatime = sdf.format(c1.getTime());
	    	    			
	    	    				FacesContext context = javax.faces.context.FacesContext.getCurrentInstance();
	    	    				HttpSession session = (HttpSession) context.getExternalContext().getSession(true);	            
	    	    	            String idUsuario = (String) session.getAttribute("Id_Usuario"); //Id del usuario logueado
	    	    				
	    	    				auditoriamod.setStrIdUsuario(idUsuario);
	    	    				auditoriamod.setIntIdFuncionalidad(FUNCIONALIDAD);
	    	    				auditoriamod.setStrTabla(TABLA);
	    	    				auditoriamod.setStrAccion(CREAR);
	    	    				auditoriamod.setLngIdRegTabla(Long.valueOf(idDocumentoCons));
	    	    				auditoriamod.setStrCampo(null);
	    	    				auditoriamod.setStrValorAnterior(null);
	    	    				auditoriamod.setStrValorNuevo(consecDoc);
	    	    				auditoriamod.setTmpFecha(Timestamp.valueOf((datatime)));
	    	    				
	    	    				int audit = AuditoriaDao.adicionar(auditoriamod);				

	    	    			} 	    	      			
	    	      			
	    	    			PostgresDocumentoPorNegociacionDAO PostDocumentoNegociacionDao;
	    	    			PostDocumentoNegociacionDao = fabricaPostgres.getDocumentoPorNegociacionDAO();
							
							DocumentoxNegociacion dxn = new DocumentoxNegociacion();
							dxn.setIntIdDocumento(idDocumentoCons);
							dxn.setIntIdTerminoIncoterm(docConsultado.getObjIncoterm().getIntId());
							dxn.setDblCostoEntrega(docConsultado.getObjDocumentoxNegociacion().getDblCostoEntrega());
							dxn.setDblCostoSeguro(docConsultado.getObjDocumentoxNegociacion().getDblCostoSeguro());
							dxn.setDblCostoFlete(docConsultado.getObjDocumentoxNegociacion().getDblCostoFlete());
							dxn.setDblOtrosGastos(docConsultado.getObjDocumentoxNegociacion().getDblOtrosGastos());
							dxn.setStrObservacionMarcacion2(docConsultado.getObjDocumentoxNegociacion().getStrObservacionMarcacion2());
							dxn.setDblTotalPesoNeto(docConsultado.getObjDocumentoxNegociacion().getDblTotalPesoNeto());
							dxn.setDblTotalPesoBruto(docConsultado.getObjDocumentoxNegociacion().getDblTotalPesoBruto());
							dxn.setDblTotalTendidos(docConsultado.getObjDocumentoxNegociacion().getDblTotalTendidos());
							dxn.setDblTotalPallets(docConsultado.getObjDocumentoxNegociacion().getDblTotalPallets());
							dxn.setIntCantidadDiasVigencia(this.intCantidadDiasVigencia);
							dxn.setBlnSolicitudCafe(docConsultado.getObjDocumentoxNegociacion().getBlnSolicitudCafe());
							dxn.setDblCantidadContenedores20(docConsultado.getObjDocumentoxNegociacion().getDblCantidadContenedores20());
							dxn.setDblCantidadContenedores40(docConsultado.getObjDocumentoxNegociacion().getDblCantidadContenedores40());
							dxn.setStrLugarIncoterm(docConsultado.getObjDocumentoxNegociacion().getStrLugarIncoterm());
							
							dxn.setDblCantidadEstibas(0);
							dxn.setDblPesoBrutoEstibas(0);
							
							res2 = PostDocumentoNegociacionDao.adicionar(dxn);
							
							if (res2 > 0)
							{
								
		    	      			documento.setIntId(idDocumentoCons);
		    	      			
		    	    			PostgresProductoPorDocumentoDAO PostProductoDocumentoDao;
		    	    			PostProductoDocumentoDao = fabricaPostgres.getProductoPorDocumento();
		    	    			
		    	      			for (int cont = 0; cont < listaSugrenciasConsultadas.size(); cont++) {
		    	      				    	      				    	      				
		    	      				ProductoPorClienteComExt pxc = new ProductoPorClienteComExt();
		    	      				pxc=((ProductoPorClienteComExt) listaSugrenciasConsultadas.get(cont));
		    	      				
		    	      				//Crear ProductoxDocumento	    	    				
	 	    	      				ProductoPorDocumento productoDocumento = new ProductoPorDocumento();
	 	    	      				productoDocumento.setBlnInformacion(false);
	 	    	      				productoDocumento.setBlnCalidad(false);
	 	    	      				productoDocumento.setDtmFechaEstimadaEntrega(timestampGeneracion);
	 	    	      				productoDocumento.setDtmFechaEntrega(timestampGeneracion);
	 	    	      				productoDocumento.setObjDocumento(documento);
	    	      				
	 	    	      				ProductoInventario objProducto = new ProductoInventario();
	 	    	      				objProducto.setIntId(pxc.getObjProductoInventario().getIntId());
	 	    	      				productoDocumento.setObjProductoInventario(objProducto);
	
	 	    	      				productoDocumento.setDblCantidad1(pxc.getObjProductoxDocumento().getDblCantidad1());    	      				
	 	    	      				
	 	    	      				Unidad u = new Unidad();
	 	    	      				u.setIntId(pxc.getObjProductoInventario().getIntUnidad());
	 	    	      				productoDocumento.setObjUnidad(u); // unidad de venta
	
	 	    	      				Moneda moneda = new Moneda();
									moneda.setStrNombre("USD");
	
									productoDocumento.setObjMoneda(moneda); 	    	      				
	 	    	      				productoDocumento.setDblCantidad2(0);
	 	    	      				productoDocumento.setValorUnitarioMl(0);
	 	    	      				productoDocumento.setValorUnitarioUsd(pxc.getDblPrecioUSD());
	 	    	      				productoDocumento.setDblValorTotal(pxc.getObjProductoxDocumento().getDblValorTotal());
	 	    	      				productoDocumento.setDblTotalPesoNetoItem(pxc.getDblTotalPesoNeto());
	 	    	      				
	
	 	    	      				//productoDocumento.setDblTotalPesoNetoItem(0);
	 	    	      				productoDocumento.setDblTotalPesoBrutoItem(pxc.getDblTotalPesoBruto());
	 	    	      				productoDocumento.setDblTotalCajasItem(pxc.getDblTotalCajas());
	 	    	      				productoDocumento.setDblTotalCajasPalletItem(pxc.getDblTotalCajasPallet());
  									productoDocumento.setDblTotalCajasPorEmbalaje(pxc.getDblTotalCajasTendido());
	 	    	      				int insertoProd = PostProductoDocumentoDao.agregar3(productoDocumento);
			 	      				
	 	    	      				if (insertoProd == 1) {
			 	      					
			 	      					    this.strConsecutivoDocumento = consecDoc;
			     	      					res = "exito_generar_FP"; //Factura Directa
			 	      				}
		    	      			}
							}
							
							
							if (res.equals("exito_generar_FP"))
							{
								Estado objEstado2 =new Estado();
								objEstado2.setIntId(ConstantesDocumento.APROBADA);
								
								Documento objDoc2 = new Documento();
								objDoc2.setObjEstado(objEstado2);
								objDoc2.setIntId(idDoc);
								
								int resUpdEsado = postDocumentoDao.modificar_Estado(objDoc2);
							}
			} catch (DataBaseException e) {
			e.printStackTrace();
			res = "error_generar_FP";
			} catch (SQLException e) {
			e.printStackTrace();
			res = "error_generar_FP";
			} catch (InstantiationException e) {
			e.printStackTrace();
			res = "error_generar_FP";
			} catch (ClassNotFoundException e) {
			e.printStackTrace();
			res = "error_generar_FP";
			} catch (IllegalAccessException e) {
			e.printStackTrace();
			res = "error_generar_FP";
			}
			return res;

		}*/
	
	

}
