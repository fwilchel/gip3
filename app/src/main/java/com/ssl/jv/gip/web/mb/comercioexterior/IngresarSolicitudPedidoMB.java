package com.ssl.jv.gip.web.mb.comercioexterior;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBTransactionRolledbackException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;
import org.primefaces.event.FileUploadEvent;

import com.ssl.jv.gip.jpa.pojo.Cliente;
import com.ssl.jv.gip.jpa.pojo.Documento;
import com.ssl.jv.gip.jpa.pojo.DocumentoXNegociacion;
import com.ssl.jv.gip.jpa.pojo.DocumentoXNegociacionPK;
import com.ssl.jv.gip.jpa.pojo.Estadosxdocumento;
import com.ssl.jv.gip.jpa.pojo.EstadosxdocumentoPK;
import com.ssl.jv.gip.jpa.pojo.LogAuditoria;
import com.ssl.jv.gip.jpa.pojo.ProductosInventario;
import com.ssl.jv.gip.jpa.pojo.ProductosXClienteComext;
import com.ssl.jv.gip.jpa.pojo.TerminoIncoterm;
import com.ssl.jv.gip.negocio.dto.ProductoSolicitudPedidoDTO;
import com.ssl.jv.gip.negocio.ejb.ComercioExteriorEJBLocal;
import com.ssl.jv.gip.negocio.ejb.MaestrosEJBLocal;
import com.ssl.jv.gip.web.mb.AplicacionMB;
import com.ssl.jv.gip.web.mb.MenuMB;
import com.ssl.jv.gip.web.mb.UtilMB;
import com.ssl.jv.gip.web.mb.util.ConstantesDocumento;
import com.ssl.jv.gip.web.mb.util.ConstantesTipoDocumento;
import com.ssl.jv.gip.web.util.Utilidad;

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
 * @author Fredy Giovanny Wilches López
 * @email fredy.wilches@gmail.com
 * @phone 300 2146240
 * @version 1.0
 */
@ManagedBean(name = "ingresarSolicitudPedidoMB")
@SessionScoped
public class IngresarSolicitudPedidoMB extends UtilMB {

	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(IngresarSolicitudPedidoMB.class);
	private Long idCliente;
	private Long idTerminoIncoterm;
	private List<SelectItem> clientes;
	private List<SelectItem> terminosIncoterm;
	private Boolean solicitudCafe;
	private String nombreArchivo;
	private List<ProductoSolicitudPedidoDTO> listaProductosXDocumentoDTO;
	private boolean deshabilitado = true;
	@ManagedProperty(value = "#{menuMB}")
	private MenuMB menu;
	@EJB
	private MaestrosEJBLocal maestrosEjb;
	@ManagedProperty(value = "#{aplicacionMB}")
	private AplicacionMB appMB;
	@EJB
	private ComercioExteriorEJBLocal comercioEjb;
	private final Integer language = AplicacionMB.SPANISH;

	@SuppressWarnings("unchecked")
	@PostConstruct
	public void init() {
		List<Cliente> listaClientes = this.maestrosEjb.consultarClientes();
		Collections.sort(listaClientes);
		this.clientes = new ArrayList<>();
		for (Cliente c : listaClientes) {
			this.clientes.add(new SelectItem(c.getId(), c.getNombre().toUpperCase()));
		}
		this.terminosIncoterm = new ArrayList<>();
	}
	
	private void reset(){
		idCliente = null;
		idTerminoIncoterm = null;
		solicitudCafe = false;
		nombreArchivo = null;
		deshabilitado = true;
		listaProductosXDocumentoDTO = null;
	}

	public void cargarTerminos() {
		this.terminosIncoterm = new ArrayList<>();
		List<TerminoIncoterm> tis = this.comercioEjb.consultarListaIncontermPorCliente(this.idCliente);
		for (TerminoIncoterm ti : tis) {
			this.terminosIncoterm.add(new SelectItem(ti.getId(), ti.getDescripcion()));
		}
		if (this.terminosIncoterm.size() > 0) {
			this.idTerminoIncoterm = tis.get(0).getId();
		} else {
			this.idTerminoIncoterm = 0L;
		}
	}

	public void handleFileUpload(FileUploadEvent event) {
		if (this.idCliente == null || this.idCliente == 0) {
			this.addMensajeError("Debe seleccionar Cliente y Término Incoterm");
			return;
		}
		this.nombreArchivo = event.getFile().getFileName();
		Map<Long, BigDecimal> saldos = this.comercioEjb.consultarUltimosSaldos();
		this.listaProductosXDocumentoDTO = new ArrayList<>();
		try {
			boolean errorValidacion = false;
			BufferedReader di = new BufferedReader(new InputStreamReader(event.getFile().getInputstream()));
			String linea;
			for (int numeroRegistros = 0;; numeroRegistros++) {
				linea = di.readLine();
				if (linea == null) {
					if (numeroRegistros == 0) {
						errorValidacion = true;
					}
					break;
				}
				StringTokenizer st = new StringTokenizer(linea, "|");
				String sku = st.nextToken().trim();
				BigDecimal cantidad = new BigDecimal(st.nextToken().trim());
				ProductoSolicitudPedidoDTO dto = new ProductoSolicitudPedidoDTO();
				dto.setObservaciones("OK");
				dto.setSku(sku);
				ProductosInventario pi = null;
				try {
					pi = this.maestrosEjb.consultarPorSku(sku);
				} catch (EJBTransactionRolledbackException ex) {
					errorValidacion = true;
					if (this.isException(ex, "No entity found for query")) {
						LOGGER.error("Producto identificado con sku: " + sku + " , no existe en producto o productos por cliente", ex);
					}
				}
				if (pi == null) {
					dto.setSeleccionado(false);
					dto.setDesactivado(true);
					dto.setEstilo("rojoNegrita");
					dto.setNombre("PRODUCTO NO EXISTE");
					dto.setControlStock(false);
					dto.setObservaciones("N/A");
					this.addMensajeError("SKUs inexistentes en el maestro productoxcliente");
					errorValidacion = true;
				} else {
					dto.setSeleccionado(true);
					dto.setDesactivado(false);
					if (!pi.getDesactivado()) {
						dto.setEstilo("naranjaNegrita");
						dto.setSeleccionado(false);
						dto.setDesactivado(true);
					} else {
						dto.setEstilo("verdeNegrita");
					}
					dto.setCantidad(cantidad);
					dto.setNombre(pi.getNombre());
					dto.setId(pi.getId());
					dto.setControlStock(pi.getProductosInventarioComext().getControlStock());
					dto.setIdUnidad(pi.getUnidadVenta().getId());
					dto.setProductoInventarioComext(pi.getProductosInventarioComext());
					ProductosXClienteComext pcce = this.comercioEjb.consultarPorClienteSku(this.idCliente, sku);
					if (pcce == null) {
						dto.setEstilo("rojoNegrita");
						dto.setObservaciones("N/A");
						dto.setSeleccionado(false);
						errorValidacion = true;
					}
				}
				if (pi != null && pi.getProductosInventarioComext() != null && pi.getProductosInventarioComext().getTipoLoteoic() != null) {
					if (this.solicitudCafe && pi.getProductosInventarioComext().getTipoLoteoic().getId().equals(5L)) {
						this.addMensajeError("Tipo Lote de los productos no corresponde con el Tipo de Solicitud");
						errorValidacion = true;
					} else if (!this.solicitudCafe && !pi.getProductosInventarioComext().getTipoLoteoic().getId().equals(5L)) {
						this.addMensajeError("Tipo Lote de los productos no corresponde con el Tipo de Solicitud");
						errorValidacion = true;
					}
				}
				this.listaProductosXDocumentoDTO.add(dto);
				// Consultar saldos
				if (dto.getControlStock() != null && dto.getControlStock()) {
					BigDecimal saldo = saldos.get(dto.getId());
					dto.setSaldoAnterior(saldo);
					if (saldo == null) {
						dto.setEstilo("rojoNegrita");
						dto.setSeleccionado(false);
						dto.setDesactivado(true);
						dto.setObservaciones("Sin saldo");
						errorValidacion = true;
					} else {
						dto.setSaldo(saldo.subtract(dto.getCantidad()));
						if (dto.getSaldo().doubleValue() < 0) {
							dto.setEstilo("rojoNegrita");
							dto.setSeleccionado(false);
							dto.setDesactivado(true);
							dto.setObservaciones("Saldo insuficiente");
							errorValidacion = true;
						}
					}
				}
			}
			this.deshabilitado = errorValidacion;
		} catch (IOException e) {
			this.addMensajeError(e);
		}
	}

	public String generarSolicitudPedido() {
		// se crea el documento
		Documento sp = new Documento();
		Estadosxdocumento estadosxdocumento = new Estadosxdocumento();
		EstadosxdocumentoPK estadosxdocumentoPK = new EstadosxdocumentoPK();
		estadosxdocumentoPK.setIdEstado((long) ConstantesDocumento.ACTIVO);
		estadosxdocumentoPK.setIdTipoDocumento((long) ConstantesTipoDocumento.SOLICITUD_PEDIDO);
		estadosxdocumento.setId(estadosxdocumentoPK);
		sp.setFechaGeneracion(new Timestamp(System.currentTimeMillis()));
		sp.setEstadosxdocumento(estadosxdocumento);
		sp.setObservacionDocumento(null);
		sp.setValorTotal(new BigDecimal(0));
		sp.setCliente(new Cliente());
		sp.getCliente().setId(this.idCliente);
		sp.setDocumentoCliente("Cargue Manual");
		sp.setNumeroFactura("0");
		DocumentoXNegociacion dxn = new DocumentoXNegociacion();
		dxn.setPk(new DocumentoXNegociacionPK());
		dxn.getPk().setIdTerminoIncoterm(this.idTerminoIncoterm);
		dxn.setCostoEntrega(new BigDecimal(0));
		dxn.setCostoSeguro(new BigDecimal(0));
		dxn.setCostoFlete(new BigDecimal(0));
		dxn.setOtrosGastos(new BigDecimal(0));
		dxn.setObservacionesMarcacion2(null);
		dxn.setTotalPesoNeto(new BigDecimal(0));
		dxn.setTotalPesoBruto(new BigDecimal(0));
		dxn.setTotalTendidos(new BigDecimal(0));
		dxn.setTotalPallets(new BigDecimal(0));
		dxn.setCantidadDiasVigencia(0);
		dxn.setSolicitudCafe(this.solicitudCafe);
		dxn.setCantidadContenedoresDe20(new BigDecimal(0));
		dxn.setCantidadContenedoresDe40(new BigDecimal(0));
		dxn.setLugarIncoterm(null);
		dxn.setCantidadEstibas(0);
		dxn.setPesoBrutoEstibas(0);
		// auditoria
		LogAuditoria auditoria = new LogAuditoria();
		auditoria.setIdUsuario(menu.getUsuario().getId());
		auditoria.setIdFuncionalidad(menu.getIdOpcionActual());
		// crear la sp
		try {
			sp = this.comercioEjb.generarSP(sp, auditoria, dxn, listaProductosXDocumentoDTO);
			String mensaje = AplicacionMB.getMessage("VentasSPExito_Crear", language);
			String parametros[] = new String[2];
			parametros[0] = "" + sp.getId();
			parametros[1] = sp.getConsecutivoDocumento();
			mensaje = Utilidad.stringFormat(mensaje, parametros);
			this.addMensajeInfo(mensaje);
			this.reset();
		} catch (Exception e) {
			this.addMensajeError("No fue posible ingresar la solicitud");
		}
		return null;
	}

	public AplicacionMB getAppMB() {
		return appMB;
	}

	public void setAppMB(AplicacionMB appMB) {
		this.appMB = appMB;
	}

	public Long getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(Long idCliente) {
		this.idCliente = idCliente;
	}

	public MenuMB getMenu() {
		return menu;
	}

	public void setMenu(MenuMB menu) {
		this.menu = menu;
	}

	public Long getIdTerminoIncoterm() {
		return idTerminoIncoterm;
	}

	public void setIdTerminoIncoterm(Long idTerminoIncoterm) {
		this.idTerminoIncoterm = idTerminoIncoterm;
	}

	public List<SelectItem> getClientes() {
		return clientes;
	}

	public void setClientes(List<SelectItem> clientes) {
		this.clientes = clientes;
	}

	public List<SelectItem> getTerminosIncoterm() {
		return terminosIncoterm;
	}

	public void setTerminosIncoterm(List<SelectItem> terminosIncoterm) {
		this.terminosIncoterm = terminosIncoterm;
	}

	public Boolean getSolicitudCafe() {
		return solicitudCafe;
	}

	public void setSolicitudCafe(Boolean solicitudCafe) {
		this.solicitudCafe = solicitudCafe;
	}

	public String getNombreArchivo() {
		return nombreArchivo;
	}

	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}

	public List<ProductoSolicitudPedidoDTO> getListaProductosXDocumentoDTO() {
		return listaProductosXDocumentoDTO;
	}

	public void setListaProductosXDocumentoDTO(List<ProductoSolicitudPedidoDTO> listaProductosXDocumentoDTO) {
		this.listaProductosXDocumentoDTO = listaProductosXDocumentoDTO;
	}

	public boolean isDeshabilitado() {
		return deshabilitado;
	}

	public void setDeshabilitado(boolean deshabilitado) {
		this.deshabilitado = deshabilitado;
	}
}
