package com.ssl.jv.gip.negocio.ejb;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsReportConfiguration;

import org.apache.log4j.Logger;
import org.primefaces.model.SortOrder;

import com.ssl.jv.gip.jpa.pojo.AgenteAduana;
import com.ssl.jv.gip.jpa.pojo.BodegasLogica;
import com.ssl.jv.gip.jpa.pojo.Ciudad;
import com.ssl.jv.gip.jpa.pojo.Cliente;
import com.ssl.jv.gip.jpa.pojo.ComextRequerimientoexportacion;
import com.ssl.jv.gip.jpa.pojo.Documento;
import com.ssl.jv.gip.jpa.pojo.DocumentoXLotesoic;
import com.ssl.jv.gip.jpa.pojo.DocumentoXNegociacion;
import com.ssl.jv.gip.jpa.pojo.Estadosxdocumento;
import com.ssl.jv.gip.jpa.pojo.EstadosxdocumentoPK;
import com.ssl.jv.gip.jpa.pojo.LiquidacionCostoLogistico;
import com.ssl.jv.gip.jpa.pojo.LogAuditoria;
import com.ssl.jv.gip.jpa.pojo.ModalidadEmbarque;
import com.ssl.jv.gip.jpa.pojo.MovimientosInventario;
import com.ssl.jv.gip.jpa.pojo.MovimientosInventarioComext;
import com.ssl.jv.gip.jpa.pojo.Muestrasxlote;
import com.ssl.jv.gip.jpa.pojo.Pais;
import com.ssl.jv.gip.jpa.pojo.ProductosInventario;
import com.ssl.jv.gip.jpa.pojo.ProductosXClienteComext;
import com.ssl.jv.gip.jpa.pojo.ProductosXDocumento;
import com.ssl.jv.gip.jpa.pojo.ProductosXDocumentoPK;
import com.ssl.jv.gip.jpa.pojo.RequerimientosXDocumento;
import com.ssl.jv.gip.jpa.pojo.RequerimientosXDocumentoPK;
import com.ssl.jv.gip.jpa.pojo.Reqxproducto;
import com.ssl.jv.gip.jpa.pojo.TerminoIncoterm;
import com.ssl.jv.gip.jpa.pojo.TerminoIncotermXMedioTransporte;
import com.ssl.jv.gip.jpa.pojo.TerminosTransporte;
import com.ssl.jv.gip.jpa.pojo.TipoDocumento;
import com.ssl.jv.gip.jpa.pojo.Ubicacion;
import com.ssl.jv.gip.jpa.pojo.Unidad;
import com.ssl.jv.gip.negocio.dao.AgenteAduanaDAOLocal;
import com.ssl.jv.gip.negocio.dao.CiudadDAOLocal;
import com.ssl.jv.gip.negocio.dao.ClienteDAOLocal;
import com.ssl.jv.gip.negocio.dao.ComextRequerimientoExportacionDAOLocal;
import com.ssl.jv.gip.negocio.dao.DocumentoDAOLocal;
import com.ssl.jv.gip.negocio.dao.DocumentoLotesOICDAOLocal;
import com.ssl.jv.gip.negocio.dao.DocumentoXLoteDAOLocal;
import com.ssl.jv.gip.negocio.dao.DocumentoXNegociacionDAOLocal;
import com.ssl.jv.gip.negocio.dao.EstadoDAOLocal;
import com.ssl.jv.gip.negocio.dao.ItemCostoLogisticoDAOLocal;
import com.ssl.jv.gip.negocio.dao.LiquidacionCostoLogisticoDAOLocal;
import com.ssl.jv.gip.negocio.dao.LogAuditoriaDAOLocal;
import com.ssl.jv.gip.negocio.dao.ModalidadEmbarqueDAOLocal;
import com.ssl.jv.gip.negocio.dao.MovimientoInventarioDAOLocal;
import com.ssl.jv.gip.negocio.dao.MovimientosInventarioComextDAOLocal;
import com.ssl.jv.gip.negocio.dao.MuestrasXLoteDAOLocal;
import com.ssl.jv.gip.negocio.dao.PaisDAOLocal;
import com.ssl.jv.gip.negocio.dao.ProductoClienteComercioExteriorDAOLocal;
import com.ssl.jv.gip.negocio.dao.ProductoInventarioDAOLocal;
import com.ssl.jv.gip.negocio.dao.ProductosXDocumentoDAOLocal;
import com.ssl.jv.gip.negocio.dao.RequerimientoxdocumentoDAOLocal;
import com.ssl.jv.gip.negocio.dao.ReqxproductoDAOLocal;
import com.ssl.jv.gip.negocio.dao.TerminoIncotermDAOLocal;
import com.ssl.jv.gip.negocio.dao.TerminosTransporteDAOLocal;
import com.ssl.jv.gip.negocio.dao.TipoDocumentoDAOLocal;
import com.ssl.jv.gip.negocio.dao.UbicacionDAOLocal;
import com.ssl.jv.gip.negocio.dto.AutorizarDocumentoDTO;
import com.ssl.jv.gip.negocio.dto.ComextRequerimientoexportacionDTO;
import com.ssl.jv.gip.negocio.dto.CostoLogisticoDTO;
import com.ssl.jv.gip.negocio.dto.DatoContribucionCafeteraDTO;
import com.ssl.jv.gip.negocio.dto.DocumentoCostosLogisticosDTO;
import com.ssl.jv.gip.negocio.dto.DocumentoIncontermDTO;
import com.ssl.jv.gip.negocio.dto.DocumentoInstruccionEmbarqueDTO;
import com.ssl.jv.gip.negocio.dto.DocumentoLotesContribucionCafeteriaDTO;
import com.ssl.jv.gip.negocio.dto.DocumentoPorLotesInstruccionEmbarqueDTO;
import com.ssl.jv.gip.negocio.dto.DocumentoRequerimientoExportacionDTO;
import com.ssl.jv.gip.negocio.dto.FiltroConsultaSolicitudDTO;
import com.ssl.jv.gip.negocio.dto.FiltroDocumentoDTO;
import com.ssl.jv.gip.negocio.dto.ListaEmpaqueDTO;
import com.ssl.jv.gip.negocio.dto.ProductoAsignarLoteOICDTO;
import com.ssl.jv.gip.negocio.dto.ProductoDTO;
import com.ssl.jv.gip.negocio.dto.ProductoGenerarFacturaPFDTO;
import com.ssl.jv.gip.negocio.dto.ProductoImprimirLEDTO;
import com.ssl.jv.gip.negocio.dto.ProductoLoteAsignarLoteOICDTO;
import com.ssl.jv.gip.negocio.dto.ProductoODDTO;
import com.ssl.jv.gip.negocio.dto.ProductoPorClienteComExtDTO;
import com.ssl.jv.gip.negocio.dto.ProductoSolicitudPedidoDTO;
import com.ssl.jv.gip.util.BodegaLogica;
import com.ssl.jv.gip.util.Estado;
import com.ssl.jv.gip.util.Moneda;
import com.ssl.jv.gip.util.TipoMovimiento;
import com.ssl.jv.gip.web.mb.util.ConstantesDocumento;
import com.ssl.jv.gip.web.mb.util.ConstantesTipoDocumento;

/**
 * Session Bean implementation class ComercioExterior.
 */
@Stateless
@LocalBean
public class ComercioExteriorEJB implements ComercioExteriorEJBLocal {

	/**
	 * The Constant LOGGER.
	 */
	private static final Logger LOGGER = Logger.getLogger(ComercioExteriorEJB.class);

	@EJB
	private TerminoIncotermDAOLocal terminoIncotermDAO;

	@EJB
	private TerminosTransporteDAOLocal terminosTransporteDAO;

	@EJB
	private ModalidadEmbarqueDAOLocal modalidadEmbarqueDAO;

	@EJB
	private CiudadDAOLocal ciudadDAO;

	@EJB
	private PaisDAOLocal paisDAO;

	@EJB
	private AgenteAduanaDAOLocal agenteAduanaDAO;

	@EJB
	private DocumentoXLoteDAOLocal documentoXLoteDAO;

	@EJB
	private DocumentoDAOLocal documentoDAO;

	@EJB
	private ProductoClienteComercioExteriorDAOLocal productoClienteComercioExteriorDAO;

	@EJB
	private DocumentoLotesOICDAOLocal documentoLotesOICDAO;

	@EJB
	private ProductoClienteComercioExteriorDAOLocal productoClienteCEDAO;

	@EJB
	private TerminoIncotermDAOLocal terminoDAO;

	@EJB
	private UbicacionDAOLocal ubicacionDAO;

	@EJB
	private LogAuditoriaDAOLocal logAuditoriaDAO;

	@EJB
	private DocumentoXNegociacionDAOLocal documentoXNegociacionDAO;

	@EJB
	private MovimientosInventarioComextDAOLocal movimientosInventarioComextDAO;

	@EJB
	private ProductosXDocumentoDAOLocal productoXDocumentoDAO;

	@EJB
	private ProductoInventarioDAOLocal productoInventarioDAOLocal;

	@EJB
	private ProductosXDocumentoDAOLocal productosXDocumentoDAOLocal;

	@EJB
	private ClienteDAOLocal clienteDao;

	@EJB
	private UbicacionDAOLocal ubicacionDAOLocal;

	@EJB
	private TipoDocumentoDAOLocal tipoDocumentoDAOLocal;

	@EJB
	private EstadoDAOLocal estadoDAOLocal;

	@EJB
	private MovimientoInventarioDAOLocal movimientoInventarioDAO;

	@EJB
	private ItemCostoLogisticoDAOLocal itemCostoLogisticoDAO;

	@EJB
	private LiquidacionCostoLogisticoDAOLocal liquidacionCostoLogisticoDAO;

	@EJB
	private MuestrasXLoteDAOLocal muestrasxloteDAO;

	@EJB
	private ComextRequerimientoExportacionDAOLocal comextRequerimientoExportacionDAO;

	@EJB
	private ReqxproductoDAOLocal reqxproductoDAO;

	@EJB
	private RequerimientoxdocumentoDAOLocal RequerimientosXDocumentoDAO;

	@EJB
	private ProductoClienteComercioExteriorDAOLocal productosXClienteComercioExteriorDAO;

	/**
	 * Default constructor.
	 */
	public ComercioExteriorEJB() {

	}

	/**
	 * Default constructor.
	 *
	 * @param parametros
	 * @return
	 */
	@Override
	public List<DatoContribucionCafeteraDTO> consultarDatosContribucionCafetera(Map<String, Object> parametros) {
		return documentoDAO.consultarDatosContribucionCafetera(parametros);
	}

	@Override
	public List<DocumentoLotesContribucionCafeteriaDTO> consultarDocumentoLotesContribucionCafetera(Map<String, Object> parametros) {
		return documentoLotesOICDAO.consultarDocumentoLotesContribucionCafetera(parametros);
	}

	@Override
	public List<DocumentoLotesContribucionCafeteriaDTO> guardarDocumentoLotesContribucionCafetera(List<DocumentoLotesContribucionCafeteriaDTO> documentos) {
		return documentoLotesOICDAO.guardarDocumentoLotesContribucionCafetera(documentos);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ssl.jv.gip.negocio.ejb.ComercioExteriorEJBLocal#
	 * consultarDocumentosPorLoteOIC()
	 */
	@Override
	public List<DocumentoXLotesoic> consultarDocumentosPorLoteOIC() {
		List<DocumentoXLotesoic> listado = new ArrayList<>();

		try {
			listado = documentoXLoteDAO.consultarDocumentoXLoteOIC();
		} catch (Exception e) {

		}

		return listado;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ssl.jv.gip.negocio.ejb.ComercioExteriorEJBLocal#
	 * reiniciarConsecutivoLoteOIC()
	 */
	@Override
	public Integer reiniciarConsecutivoLoteOIC() {
		return documentoXLoteDAO.reiniciarConsecutivoLoteOIC();
	}

	@Override
	public Cliente consultarClientePorId(Long idCliente) {
		Map<String, Object> parametros = new HashMap<>();
		parametros.put("id", idCliente);
		return clienteDao.buscarRegistroPorConsultaNombrada(Cliente.BUSCAR_CLIENTE_FETCH_CIUDAD, parametros);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ssl.jv.gip.negocio.ejb.ComercioExteriorEJBLocal#
	 * consultarDocumentosCostosInconterm()
	 */
	@Override
	public List<DocumentoIncontermDTO> consultarDocumentosCostosInconterm() {
		List<DocumentoIncontermDTO> listado = new ArrayList<>();

		try {
			listado = documentoDAO.consultarDocumentosCostosInconterm();
		} catch (Exception e) {

		}

		return listado;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ssl.jv.gip.negocio.ejb.ComercioExteriorEJBLocal#
	 * actualizarDocumentoPorNegociacion
	 * (com.ssl.jv.gip.negocio.dto.DocumentoIncontermDTO)
	 */
	@Override
	public void actualizarDocumentoPorNegociacion(DocumentoIncontermDTO documento) {
		documentoDAO.actualizarDocumentoPorNegociacion(documento);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ssl.jv.gip.negocio.ejb.ComercioExteriorEJBLocal#
	 * consultarListaIncontermPorCliente(java.lang.Long)
	 */
	@Override
	public List<TerminoIncoterm> consultarListaIncontermPorCliente(Long idCliente) {
		return terminoDAO.consultarListaIncontermPorCliente(idCliente);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ssl.jv.gip.negocio.ejb.ComercioExteriorEJBLocal#
	 * consultarDocumentosSolicitudPedido()
	 */
	@Override
	public List<DocumentoIncontermDTO> consultarDocumentosSolicitudPedido() {
		List<DocumentoIncontermDTO> listado = documentoDAO.consultarDocumentosSolicitudPedido();
		return listado;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ssl.jv.gip.negocio.ejb.ComercioExteriorEJBLocal#
	 * consultarDocumentosSolicitudPedido
	 * (com.ssl.jv.gip.negocio.dto.FiltroConsultaSolicitudDTO)
	 */
	@Override
	public List<DocumentoIncontermDTO> consultarDocumentosSolicitudPedido(FiltroConsultaSolicitudDTO filtro) {
		List<DocumentoIncontermDTO> listado = new ArrayList<>();

		try {
			listado = documentoDAO.consultarDocumentosSolicitudPedido(filtro);
		} catch (Exception e) {

		}

		return listado;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ssl.jv.gip.negocio.ejb.ComercioExteriorEJBLocal#consultarDocumentosGeneral
	 * (com.ssl.jv.gip.negocio.dto.FiltroConsultaSolicitudDTO)
	 */
	@Override
	public List<DocumentoIncontermDTO> consultarDocumentosGeneral(FiltroConsultaSolicitudDTO filtro) {
		List<DocumentoIncontermDTO> listado = new ArrayList<>();

		try {
			listado = documentoDAO.consultarDocumentosGeneral(filtro);
		} catch (Exception e) {

		}

		return listado;
	}

	@Override
	public Object[] consultarDocumentosGeneral(FiltroConsultaSolicitudDTO filtro, int first, int pageSize, String sortField, SortOrder sortOrder, boolean count) {
		LOGGER.trace("Metodo: <<consultarDocumentosGeneral>> parametros / filtro, first, pageSize, sortField, sortOrder, count ->>");
		return documentoDAO.consultarDocumentosGeneral(filtro, first, pageSize, sortField, sortOrder, count);
	}

	@Override
	public List<DocumentoIncontermDTO> consultarDocumentosAprobarSolicitudPedido() {
		List<DocumentoIncontermDTO> listado = new ArrayList<>();

		try {
			listado = documentoDAO.consultarDocumentosAprobarSolicitudPedido();
		} catch (Exception e) {

		}

		return listado;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ssl.jv.gip.negocio.ejb.ComercioExteriorEJBLocal#
	 * consultarListaSolicitudesPedido(java.lang.Long, java.lang.Long)
	 */
	@Override
	public List<ProductoPorClienteComExtDTO> consultarListaSolicitudesPedido(Long idDocumento, Long idCliente) {
		List<ProductoPorClienteComExtDTO> listado = new ArrayList<>();

		try {
			listado = productoClienteCEDAO.consultarListaSolicitudesPedido(idDocumento, idCliente);
		} catch (Exception e) {

		}

		return listado;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ssl.jv.gip.negocio.ejb.ComercioExteriorEJBLocal#
	 * consultarListaProductosPorClienteCE(java.lang.Long, java.lang.String,
	 * java.lang.Boolean)
	 */
	@Override
	public List<ProductoPorClienteComExtDTO> consultarListaProductosPorClienteCE(Long idCliente, String idsProductos, Boolean solicitudCafe) {
		List<ProductoPorClienteComExtDTO> listado = new ArrayList<>();

		try {
			listado = productoClienteCEDAO.consultarListaProductosPorClienteCE(idCliente, idsProductos, solicitudCafe);
		} catch (Exception e) {

		}

		return listado;
	}

	@Override
	public void actualizarEstadoDocumento(DocumentoIncontermDTO documento) {
		documentoDAO.actualizarEstadoDocumento(documento);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ssl.jv.gip.negocio.ejb.ComercioExteriorEJBLocal#guardarSolicitudPedido
	 * (com.ssl.jv.gip.negocio.dto.DocumentoIncontermDTO, java.util.List)
	 */
	@Override
	public void guardarSolicitudPedido(DocumentoIncontermDTO documento, List<ProductoPorClienteComExtDTO> listaSolicitudPedido) {
		if (documento.getIdEstado() == ConstantesDocumento.GENERADO) {
			documento.setIdEstado(new Long(ConstantesDocumento.ANULADO));
			documentoDAO.actualizarEstadoDocumentoPorConsecutivo(documento);
		}

		if (documento.getIdTipoDocumento() == ConstantesTipoDocumento.SOLICITUD_PEDIDO) {
			documento.setIdEstado(new Long(ConstantesDocumento.VERIFICADO));
		}

		if (documento.getFechaEsperadaEntregaDate() != null) {
			documento.setFechaEsperadaEntrega(new Timestamp(documento.getFechaEsperadaEntregaDate().getTime()));
		}

		Documento vdocumento = documentoDAO.findByPK(documento.getIdDocumento());
		vdocumento.setValorTotal(documento.getValorTotalDocumento());
		Ubicacion ubicacion1 = ubicacionDAO.findByPK(documento.getIdUbicacionOrigen());
		vdocumento.setUbicacionOrigen(ubicacion1);
		Ubicacion ubicacion2 = ubicacionDAO.findByPK(documento.getIdUbicacionDestino());
		vdocumento.setUbicacionDestino(ubicacion2);
		vdocumento.setFechaEsperadaEntrega(documento.getFechaEsperadaEntrega());

		documentoDAO.update(vdocumento);
		documentoDAO.actualizarEstadoDocumento(documento);
		documentoDAO.actualizarDocumentoPorNegociacion(documento);

		// Falta almacenar la lista de solicitud de pedido
		if (listaSolicitudPedido != null && !listaSolicitudPedido.isEmpty()) {
			for (ProductoPorClienteComExtDTO vProducto : listaSolicitudPedido) {

				BigDecimal dblSaldoActual = new BigDecimal(0);
				BigDecimal dblCantidadActual = new BigDecimal(0);

				String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
				SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
				Calendar c1 = Calendar.getInstance(); // Fecha y Tiempo actual
				String datatime = sdf.format(c1.getTime());

				if (vProducto.getControlStockProductoInventarioCE() != null && vProducto.getControlStockProductoInventarioCE()) {
					if (vProducto.isBlnIncluirBusqueda()) {
						// Consultar saldo del producto inventario, si no tiene
						// lanzar excepcion
						dblSaldoActual = productoClienteComercioExteriorDAO.consultarUltimoSaldoProducto(vProducto.getIntIdProductoInventario());
						if (dblSaldoActual == null) {
							// TODO Lanzar mensaje de excepcion
							// Los siguientes skus no tienen saldo de inventario
							// asociado:
						}

					}
					// Si tiene saldo

					// Consultar la cantidad de producto
					dblCantidadActual = vProducto.getDblCantidad1ActualProductoxDocumento();

					// el calculo del nuevo saldo es saldo actual + la cantidad
					// actual - la cantidad nueva
					BigDecimal dblNuevoSaldo = dblSaldoActual.add(dblCantidadActual).min(vProducto.getDblCantidad1ProductoxDocumento());

					if (dblNuevoSaldo.compareTo(new BigDecimal(0)) == -1) {
						// Error excepcion
						// TODO
						// "El calculo del nuevo saldo seria negativo para los siguientes skus:"

					}

					if (vProducto.isBlnIncluirBusqueda()) {
						vProducto.setIdTipoMovimiento(2L);
						vProducto.setDtmFecha(Timestamp.valueOf(datatime));
						vProducto.setDblSaldo(dblNuevoSaldo);
						// Crear saldo
						productoClienteComercioExteriorDAO.crearSaldo(documento, vProducto);
						// Consultar si existe producto por documento
						Boolean existe = productoClienteComercioExteriorDAO.consultarProductoCliente(documento.getIdDocumento(), vProducto.getIntIdProductoInventario());

						if (existe) {
							// Actualizar
							productoClienteComercioExteriorDAO.modificarFacturaProforma(documento, vProducto);
						} else {
							// Insertar
							productoClienteComercioExteriorDAO.crearFacturaProforma(documento, vProducto);
						}

					} else {
						// Eliminar
						productoClienteComercioExteriorDAO.eliminarFacturaProforma(documento, vProducto);

						// Consultar cantidad
						dblCantidadActual = vProducto.getDblCantidad1ActualProductoxDocumento();

						dblNuevoSaldo = dblSaldoActual.add(dblCantidadActual);

						vProducto.setDblSaldo(dblNuevoSaldo);
						vProducto.setIdTipoMovimiento(2L);
						// Crear saldo
						productoClienteComercioExteriorDAO.crearSaldo(documento, vProducto);
					}

				} else {
					if (vProducto.getControlStockProductoInventarioCE() == null || !vProducto.getControlStockProductoInventarioCE()) {
						if (vProducto.isBlnIncluirBusqueda()) {
							// Consultar si existe producto por documento
							Boolean existe = productoClienteComercioExteriorDAO.consultarProductoCliente(documento.getIdDocumento(), vProducto.getIntIdProductoInventario());

							if (existe) {
								// Actualizar
								productoClienteComercioExteriorDAO.modificarFacturaProforma(documento, vProducto);
							} else {
								// Insertar
								productoClienteComercioExteriorDAO.crearFacturaProforma(documento, vProducto);
							}

						} else {
							// Eliminar
							productoClienteComercioExteriorDAO.eliminarFacturaProforma(documento, vProducto);
						}
					}

				}

			}
		}

	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void verificarSolicitudPedido(DocumentoIncontermDTO sp, DocumentoXNegociacion dxn, List<ProductosXDocumento> productosSeleccionados, LogAuditoria auditoria) throws Exception {
		LOGGER.debug("method: update(record, selected)");
		try {
			Map<Long, BigDecimal> saldos = this.consultarUltimosSaldos();
			// actualizar el documento
			Documento solicitud = documentoDAO.findByPK(sp.getIdDocumento());
			Estadosxdocumento estadosxdocumento = new Estadosxdocumento();
			EstadosxdocumentoPK estadosxdocumentoPK = new EstadosxdocumentoPK();
			estadosxdocumentoPK.setIdEstado((long) ConstantesDocumento.VERIFICADO);
			estadosxdocumentoPK.setIdTipoDocumento((long) ConstantesTipoDocumento.SOLICITUD_PEDIDO);
			estadosxdocumento.setId(estadosxdocumentoPK);
			solicitud.setEstadosxdocumento(estadosxdocumento);
			solicitud.setValorTotal(sp.getValorTotalDocumento());
			solicitud.setUbicacionOrigen(new Ubicacion(sp.getIdUbicacionDestino()));
			solicitud.setUbicacionDestino(new Ubicacion(sp.getIdUbicacionDestino()));
			solicitud.setFechaEsperadaEntrega(sp.getFechaEsperadaEntregaDate());
			documentoDAO.update(solicitud);
			// ingresar el registro de auditoria
			auditoria.setTabla("Documentos");
			auditoria.setAccion("MOD");
			auditoria.setCampo("Estado");
			auditoria.setValorAnterior(sp.getEstadoNombre());
			auditoria.setValorNuevo("Verificado");
			auditoria.setFecha(new Timestamp(System.currentTimeMillis()));
			auditoria.setIdRegTabla(solicitud.getId());
			this.logAuditoriaDAO.add(auditoria);
			// documento x negociacion
			// TODO: necesario pq no hay manera de identificar el dxn para la sp
			List<DocumentoXNegociacion> documentosXNegociacion = documentoXNegociacionDAO.consultarDocumentoXNegociacionPorIdDocumento(solicitud.getId());
			for (DocumentoXNegociacion record : documentosXNegociacion){
				this.documentoXNegociacionDAO.delete(record);
			}
			this.documentoXNegociacionDAO.update(dxn);
			// consultar los productos asociados a la sp
			List<ProductosXDocumento> productosPreseleccionados = this.consultarProductosSP(sp.getIdDocumento(), sp.getClientesId());
			Map<Long, BigDecimal> cantidadesPreseleccionadas = new HashMap<>();
			for (ProductosXDocumento pxd : productosPreseleccionados) {
				cantidadesPreseleccionadas.put(pxd.getProductosInventario().getId(), pxd.getCantidad1());
			}
			// recorro primero la lista de los preseleccionados, para determinar que
			// registros se deben eliminar.
			for (ProductosXDocumento pxdPreseleccionado : productosPreseleccionados) {
				// si no existe se debe eliminar
				if (!buscarProductoEnLista(pxdPreseleccionado, productosSeleccionados)) {
					productoXDocumentoDAO.delete(pxdPreseleccionado);
					LOGGER.debug("productos eliminado");
					if (pxdPreseleccionado.getProductosInventario().getProductosInventarioComext().getControlStock()) {
						BigDecimal ultimoSaldo = saldos.get(pxdPreseleccionado.getProductosInventario().getProductosInventarioComext().getIdProducto());
						if (ultimoSaldo == null) {
							ultimoSaldo = BigDecimal.ZERO;
						}
						MovimientosInventarioComext movimientoInventario = new MovimientosInventarioComext();
						movimientoInventario.setConsecutivoDocumento(sp.getConsecutivoDocumento());
						movimientoInventario.setCantidad(pxdPreseleccionado.getCantidad1());
						movimientoInventario.setFecha(new Timestamp(System.currentTimeMillis()));
						movimientoInventario.setProductosInventarioComext(pxdPreseleccionado.getProductosInventario().getProductosInventarioComext());
						movimientoInventario.setSaldo(ultimoSaldo.add(pxdPreseleccionado.getCantidad1()));
						movimientoInventario.setTipoMovimiento(new com.ssl.jv.gip.jpa.pojo.TipoMovimiento(TipoMovimiento.ENTRADA.getCodigo()));
						movimientoInventario = (MovimientosInventarioComext) movimientosInventarioComextDAO.add(movimientoInventario);
						// auditoria para el movimiento
						LogAuditoria aud = new LogAuditoria();
						aud.setIdUsuario(auditoria.getIdUsuario());
						aud.setIdRegTabla(movimientoInventario.getId());
						aud.setIdFuncionalidad(auditoria.getIdFuncionalidad());
						aud.setTabla("movimientos_inventario_comext");
						aud.setCampo("Saldo");
						aud.setAccion(TipoMovimiento.ENTRADA.getNombre());
						aud.setValorAnterior(ultimoSaldo.toString());
						aud.setValorNuevo(movimientoInventario.getSaldo().toString());
						aud.setFecha(new Timestamp(System.currentTimeMillis()));
						this.logAuditoriaDAO.add(aud);
					}
				}
			}
			// recorro despues la lista de los seleccionados contra los
			// preseleccionados, de esta manera determino que registros nuevos debo
			// crear y cuales actualizar
			for (ProductosXDocumento pxdSeleccionado : productosSeleccionados) {
				// si no existe se debe crear, si existe se debe actualizar
				if (!buscarProductoEnLista(pxdSeleccionado, productosPreseleccionados)) {
					if (pxdSeleccionado.getProductosInventario().getProductosInventarioComext().getControlStock()) {
						BigDecimal ultimoSaldo = saldos.get(pxdSeleccionado.getProductosInventario().getProductosInventarioComext().getIdProducto());
						if (ultimoSaldo == null || ultimoSaldo.compareTo(pxdSeleccionado.getCantidad1()) == -1) {
							throw new Exception("Saldo insuficiente para el producto " + pxdSeleccionado.getProductosInventario().getSku());
						}
						productoXDocumentoDAO.add(pxdSeleccionado);
						LOGGER.debug("producto agregado");
						MovimientosInventarioComext movimientoInventario = new MovimientosInventarioComext();
						movimientoInventario.setConsecutivoDocumento(sp.getConsecutivoDocumento());
						movimientoInventario.setCantidad(pxdSeleccionado.getCantidad1());
						movimientoInventario.setFecha(new Timestamp(System.currentTimeMillis()));
						movimientoInventario.setProductosInventarioComext(pxdSeleccionado.getProductosInventario().getProductosInventarioComext());
						movimientoInventario.setSaldo(ultimoSaldo.subtract(pxdSeleccionado.getCantidad1()));
						movimientoInventario.setTipoMovimiento(new com.ssl.jv.gip.jpa.pojo.TipoMovimiento(TipoMovimiento.SALIDA.getCodigo()));
						movimientoInventario = (MovimientosInventarioComext) movimientosInventarioComextDAO.add(movimientoInventario);
						LOGGER.debug("movimiento agregado");
						// auditoria para el movimiento
						LogAuditoria aud = new LogAuditoria();
						aud.setIdUsuario(auditoria.getIdUsuario());
						aud.setIdRegTabla(movimientoInventario.getId());
						aud.setIdFuncionalidad(auditoria.getIdFuncionalidad());
						aud.setTabla("movimientos_inventario_comext");
						aud.setCampo("Saldo");
						aud.setAccion(TipoMovimiento.SALIDA.getNombre());
						aud.setValorAnterior(ultimoSaldo.toString());
						aud.setValorNuevo(movimientoInventario.getSaldo().toString());
						aud.setFecha(new Timestamp(System.currentTimeMillis()));
						this.logAuditoriaDAO.add(aud);
						LOGGER.debug("log agregado");
					} else {
						productoXDocumentoDAO.add(pxdSeleccionado);
						LOGGER.debug("producto agregado");
					}
				} else {
					if (pxdSeleccionado.getProductosInventario().getProductosInventarioComext().getControlStock()) {
						BigDecimal ultimoSaldo = saldos.get(pxdSeleccionado.getProductosInventario().getProductosInventarioComext().getIdProducto());
						if (ultimoSaldo == null) {
							ultimoSaldo = BigDecimal.ZERO;
						}
						// obtener la cantidad del anterior, mejor usar un mapa
						BigDecimal cantidadAnterior = cantidadesPreseleccionadas.get(pxdSeleccionado.getProductosInventario().getId());
						BigDecimal cantidadNueva = pxdSeleccionado.getCantidad1();
						if (cantidadAnterior.compareTo(cantidadNueva) == 0) {
							// significa q no se modificaron las cantidades
							productoXDocumentoDAO.update(pxdSeleccionado);
							LOGGER.debug("producto actualizado");
						} else {
							BigDecimal diferencia;
							MovimientosInventarioComext movimientoInventario = new MovimientosInventarioComext();
							LogAuditoria aud = new LogAuditoria();
							if (cantidadAnterior.compareTo(cantidadNueva) == -1) {
								// significa q aumento la cantidad / se debe hacer una salida /
								// debe validar contra el inventario
								diferencia = cantidadNueva.subtract(cantidadAnterior);
								if (ultimoSaldo == null || ultimoSaldo.compareTo(diferencia) == -1) {
									throw new Exception("Saldo insuficiente para el producto " + pxdSeleccionado.getProductosInventario().getSku() + ". " + ultimoSaldo + " unidades disponibles.");
								}
								movimientoInventario.setCantidad(diferencia);
								movimientoInventario.setSaldo(ultimoSaldo.subtract(diferencia));
								movimientoInventario.setTipoMovimiento(new com.ssl.jv.gip.jpa.pojo.TipoMovimiento(TipoMovimiento.SALIDA.getCodigo()));
								aud.setAccion(TipoMovimiento.SALIDA.getNombre());
							}
							if (cantidadAnterior.compareTo(cantidadNueva) == 1) {
								// significa q q bajo la cantidad / se debe hacer una entrada
								diferencia = cantidadAnterior.subtract(cantidadNueva);
								movimientoInventario.setCantidad(diferencia);
								movimientoInventario.setSaldo(ultimoSaldo.add(diferencia));
								movimientoInventario.setTipoMovimiento(new com.ssl.jv.gip.jpa.pojo.TipoMovimiento(TipoMovimiento.ENTRADA.getCodigo()));
								aud.setAccion(TipoMovimiento.ENTRADA.getNombre());
							}
							productoXDocumentoDAO.update(pxdSeleccionado);
							LOGGER.debug("producto actualizado");
							movimientoInventario.setProductosInventarioComext(pxdSeleccionado.getProductosInventario().getProductosInventarioComext());
							movimientoInventario.setConsecutivoDocumento(sp.getConsecutivoDocumento());
							movimientoInventario.setFecha(new Timestamp(System.currentTimeMillis()));
							movimientoInventario = (MovimientosInventarioComext) movimientosInventarioComextDAO.add(movimientoInventario);
							LOGGER.debug("movimiento agregado");
							aud.setIdUsuario(auditoria.getIdUsuario());
							aud.setIdRegTabla(movimientoInventario.getId());
							aud.setIdFuncionalidad(auditoria.getIdFuncionalidad());
							aud.setTabla("movimientos_inventario_comext");
							aud.setCampo("Saldo");
							aud.setValorAnterior(ultimoSaldo.toString());
							aud.setValorNuevo(movimientoInventario.getSaldo().toString());
							aud.setFecha(new Timestamp(System.currentTimeMillis()));
							this.logAuditoriaDAO.add(aud);
							LOGGER.debug("log agregado");
						}
					} else {
						productoXDocumentoDAO.update(pxdSeleccionado);
						LOGGER.debug("producto actualizado");
					}
				}
			}
		} catch (Exception ex) {
			LOGGER.error("Error en <<record>> ->> mensaje ->> {} / causa ->> {} ");
			throw ex;
		}
	}

	/**
	 * Valida si un producto ya se encuentra en la lista. Retorna true si el
	 * producto existe en la lista
	 *
	 * @param producto
	 * @param lista
	 * @return
	 */
	private boolean buscarProductoEnLista(ProductosXDocumento producto, List<ProductosXDocumento> lista) {
		if (lista != null) {
			for (ProductosXDocumento registro : lista) {
				if (producto.getProductosInventario().getSku().equalsIgnoreCase(registro.getProductosInventario().getSku())) {
					return true;
				}
			}
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ssl.jv.gip.negocio.ejb.ComercioExteriorEJBLocal#
	 * consultarUbicacionesPorUsuario(java.lang.String)
	 */
	@Override
	public List<Ubicacion> consultarUbicacionesPorUsuario(String idUsuario) {
		List<Ubicacion> listado = new ArrayList<>();

		try {
			listado = ubicacionDAO.consultarUbicacionesPorUsuario(idUsuario);
		} catch (Exception e) {

		}

		return listado;
	}

	@Override
	public List<ListaEmpaqueDTO> consultarDocumentoPorFacturaProforma(String consecutivoFacturaProforma) {
		return documentoDAO.consultarDocumentoPorFacturaProforma(consecutivoFacturaProforma);
	}

	@Override
	public List<ProductoDTO> consultarProductoPorDocumento(ListaEmpaqueDTO listaEmpaqueDTO) {
		return productoClienteComercioExteriorDAO.consultarProductoPorDocumento(listaEmpaqueDTO);
	}

	/*
	 * @Override public Documento generarListaEmpaque(ListaEmpaqueDTO
	 * listaEmpaqueDTO) { return
	 * documentoDAO.generarListaEmpaque(listaEmpaqueDTO); }
	 */
	@Override
	public void generarListaEmpaque(ProductoDTO productoDTO) {
		productoClienteComercioExteriorDAO.generarListaEmpaque(productoDTO);
	}

	@Override
	public Documento consultarDocumentoPorId(Long pId) {
		Documento entidad = documentoDAO.findByPK(pId);
		return entidad;
	}

	@Override
	public List<ProductoGenerarFacturaPFDTO> consultarProductoPorDocumentoGenerarFacturaProforma(Long idDocumento, Long idCliente) {
		return productoClienteComercioExteriorDAO.consultarProductoPorDocumentoGenerarFacturaProforma(idDocumento, idCliente);
	}

	@Override
	public List<ProductoAsignarLoteOICDTO> consultarProductoPorDocumentoAsignarLotesOIC(Long idDocumento, Long idCliente) {
		return productoClienteComercioExteriorDAO.consultarProductoPorDocumentoAsignarLotesOIC(idDocumento, idCliente);
	}

	@Override
	public List<ProductoLoteAsignarLoteOICDTO> consultarProductoPorDocumentoLoteAsignarLotesOIC(Long idDocumento, Long idCliente) {
		return productoClienteComercioExteriorDAO.consultarProductoPorDocumentoLoteAsignarLotesOIC(idDocumento, idCliente);
	}

	@Override
	public List<Documento> consultarDocumentosSolicitudPedido(String consecutivoDocumento) {
		return documentoDAO.consultarDocumentosSolicitudPedido(consecutivoDocumento);
	}

	@Override
	public List<Documento> consultarDocumentosFacturaPF(String consecutivoDocumento) {
		return documentoDAO.consultarDocumentosFacturaPF(consecutivoDocumento);
	}

	@Override
	public List<Documento> consultarDocumento(Map<String, Object> parametros, Long[] idEstados) {
		// TODO Auto-generated method stub
		return documentoDAO.consultarDocumento(parametros, idEstados);
	}

	@Override
	public ListaEmpaqueDTO consultarDocumentoListaEmpaque(String consecutivoDocumento) {
		return documentoDAO.consultarDocumentoListaEmpaque(consecutivoDocumento);
	}

	@Override
	public Documento crearFacturaProforma(Documento fp, LogAuditoria auditoria, DocumentoXNegociacion dxn, List<ProductosXDocumento> pxd, Documento sp) {
		// crear el documento FP
		fp.setConsecutivoDocumento("FP1-" + this.documentoDAO.consultarProximoValorSecuencia("fp1_seq"));
		fp = (Documento) this.documentoDAO.add(fp);
		// crear la auditoria
		auditoria.setIdRegTabla(fp.getId());
		auditoria.setValorNuevo(fp.getConsecutivoDocumento());
		this.logAuditoriaDAO.add(auditoria);
		// crear el DXN
		dxn.getPk().setIdDocumento(fp.getId());
		documentoXNegociacionDAO.add(dxn);
		// crear los PXD
		for (ProductosXDocumento productoxdocumento : pxd) {
			productoxdocumento.getId().setIdDocumento(fp.getId());
			this.productoXDocumentoDAO.add(productoxdocumento);
		}
		// cambiar de estado la SP a CERRADO
		sp.getEstadosxdocumento().getId().setIdEstado((long) ConstantesDocumento.CERRADO);
		this.documentoDAO.update(sp);
		// retornar el documento creado
		return fp;
	}

	@Override
	public Documento crearFacturaExportacion(Documento documento, LogAuditoria auditoria, DocumentoXNegociacion documentoPorNegociacion, List<ProductosXDocumento> productos, Documento original) {
		documento.setConsecutivoDocumento("FX1-" + this.documentoDAO.consultarProximoValorSecuencia("fx1_seq"));
		documento = (Documento) this.documentoDAO.add(documento);
		documentoPorNegociacion.getPk().setIdDocumento(documento.getId());
		documentoXNegociacionDAO.add(documentoPorNegociacion);
		for (ProductosXDocumento pxd : productos) {
			pxd.getId().setIdDocumento(documento.getId());
			this.productoXDocumentoDAO.add(pxd);
		}
		actualizarEstadoDocumento(original.getId(), new Long(ConstantesDocumento.CERRADO));
		return documento;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Documento crearSolicitudPedido(Documento sp, LogAuditoria auditoria, DocumentoXNegociacion dxn, List<ProductoSolicitudPedidoDTO> listaPXDDTO) {
		// crear el documento
		sp.setConsecutivoDocumento("SP1-" + this.documentoDAO.consultarProximoValorSecuencia("sp1_seq"));
		sp = (Documento) this.documentoDAO.add(sp);
		// ingresar el registro de auditoria
		auditoria.setTabla("Documentos");
		auditoria.setAccion("CRE");
		auditoria.setCampo("Consecutivo");
		auditoria.setValorAnterior(null);
		auditoria.setFecha(new Timestamp(System.currentTimeMillis()));
		auditoria.setIdRegTabla(sp.getId());
		auditoria.setValorNuevo(sp.getConsecutivoDocumento());
		this.logAuditoriaDAO.add(auditoria);
		// crear el documento por negociacion
		dxn.getPk().setIdDocumento(sp.getId());
		documentoXNegociacionDAO.add(dxn);
		// crear los productos asociados al documento
		for (ProductoSolicitudPedidoDTO productoDTO : listaPXDDTO) {
			if (productoDTO.isSeleccionado()) {
				ProductosXDocumento pxd = new ProductosXDocumento();
				pxd.setId(new ProductosXDocumentoPK());
				pxd.getId().setIdProducto(productoDTO.getId());
				pxd.getId().setIdDocumento(sp.getId());
				pxd.setInformacion(false);
				pxd.setCalidad(false);
				pxd.setFechaEstimadaEntrega(sp.getFechaGeneracion());
				pxd.setFechaEntrega(sp.getFechaGeneracion());
				pxd.setCantidad1(productoDTO.getCantidad());
				Unidad u = new Unidad();
				u.setId(productoDTO.getIdUnidad());
				pxd.setUnidade(u); // unidad de venta
				com.ssl.jv.gip.jpa.pojo.Moneda moneda = new com.ssl.jv.gip.jpa.pojo.Moneda();
				moneda.setId("USD");
				pxd.setMoneda(moneda);
				pxd.setCantidad2(new BigDecimal(0));
				pxd.setValorUnitatrioMl(new BigDecimal(0));
				pxd.setValorUnitarioUsd(new BigDecimal(0));
				pxd.setValorTotal(new BigDecimal(0));
				pxd.setTotalPesoNetoItem(new BigDecimal(0));
				pxd.setTotalPesoBrutoItem(new BigDecimal(0));
				pxd.setCantidadCajasItem(new BigDecimal(0));
				pxd.setCantidadPalletsItem(new BigDecimal(0));
				pxd.setCantidadXEmbalaje(new BigDecimal(0));
				Calendar c = Calendar.getInstance();
				c.add(Calendar.DATE, 2);
				pxd.setFechaEstimadaEntrega(new Timestamp(c.getTimeInMillis()));
				pxd.setFechaEntrega(new Timestamp(c.getTimeInMillis()));
				this.productoXDocumentoDAO.add(pxd);
				// crear los movimientos de inventario
				if (productoDTO.getProductoInventarioComext().getControlStock() != null && productoDTO.getProductoInventarioComext().getControlStock()) {
					MovimientosInventarioComext movimientoInventario = new MovimientosInventarioComext();
					movimientoInventario.setConsecutivoDocumento(sp.getConsecutivoDocumento());
					movimientoInventario.setCantidad(productoDTO.getCantidad());
					movimientoInventario.setFecha(new Timestamp(System.currentTimeMillis()));
					movimientoInventario.setProductosInventarioComext(productoDTO.getProductoInventarioComext());
					movimientoInventario.setSaldo(productoDTO.getSaldo());
					movimientoInventario.setTipoMovimiento(new com.ssl.jv.gip.jpa.pojo.TipoMovimiento());
					movimientoInventario.getTipoMovimiento().setId(1L);
					movimientoInventario = (MovimientosInventarioComext) movimientosInventarioComextDAO.add(movimientoInventario);
					// auditoria para el movimiento
					LogAuditoria aud = new LogAuditoria();
					aud.setIdUsuario(auditoria.getIdUsuario());
					aud.setIdRegTabla(movimientoInventario.getId());
					aud.setIdFuncionalidad(auditoria.getIdFuncionalidad());
					aud.setTabla("movimientos_inventario_comext");
					aud.setAccion("S");
					aud.setCampo("Saldo");
					aud.setValorAnterior(productoDTO.getSaldoAnterior().toString());
					aud.setValorNuevo(movimientoInventario.getSaldo().toString());
					aud.setFecha(new Timestamp(System.currentTimeMillis()));
					this.logAuditoriaDAO.add(aud);
				}
			}
		}
		return sp;
	}

	@Override
	public List<ProductoImprimirLEDTO> consultarProductoListaEmpaque(String consecutivoDocumento) {
		return documentoDAO.consultarProductoListaEmpaque(consecutivoDocumento);
	}

	@Override
	public List<DocumentoXLotesoic> guardarLotes(List<DocumentoXLotesoic> lista, Documento documento) {
		for (DocumentoXLotesoic dxl : lista) {
			Long seq = documentoXLoteDAO.consultarProximoValorSecuencia("lote_oic_seq");
			String seqStr = seq.toString();
			seqStr = String.format("%4s", seqStr).replace(' ', '0');
			seqStr = "3-223-" + seqStr;
			dxl.setConsecutivo(seqStr);
			documentoXLoteDAO.add(dxl);
		}
		this.documentoDAO.update(documento);
		return lista;
	}

	@Override
	public Map<Long, BigDecimal> consultarUltimosSaldos() {
		Map<Long, BigDecimal> saldos = this.movimientosInventarioComextDAO.getMapUltimosSaldos();
		return saldos;
	}

	@Override
	public ProductosXClienteComext consultarPorClienteSku(Long idCliente, String sku) {
		try {
			return this.productoClienteComercioExteriorDAO.consultarPorClienteSku(idCliente, sku);
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public List<Documento> consultarDocumentosActivosPorTipoDocumentoYConsecutivoDocumento(Long idTipoDocumento, String consecutivoDocumento) {
		List<Documento> listaEmpaques = this.documentoDAO.consultarDocumentosPorTipoDocumentoConsecutivoDocumentoYEstados(idTipoDocumento, consecutivoDocumento, Estado.ACTIVO.getCodigo());
		for (Documento documento : listaEmpaques) {
			documento.getDocumentoXLotesoics().iterator().hasNext();
		}
		return listaEmpaques;
	}

	@Override
	public List<ProductosInventario> consultarProductosInventariosPorSkus(List<String> skus) {
		return this.productoInventarioDAOLocal.consultarPorSkus(skus);
	}

	@Override
	public List<ProductosXDocumento> consultarProductosXDocumentosPorDocumento(Long idDocumento) {
		return productosXDocumentoDAOLocal.consultarPorDocumento(idDocumento);
	}

	@Override
	public void modificarListaEmpaque(Documento documento, List<ProductosXDocumento> productosXDocumentos) {
		this.productosXDocumentoDAOLocal.modificarProductosXDocumentos(productosXDocumentos);

		List<DocumentoXNegociacion> documentoXNegociacions = documento.getDocumentoXNegociacions();
		for (DocumentoXNegociacion documentoXNegociacion : documentoXNegociacions) {
			documentoXNegociacionDAO.update(documentoXNegociacion);
		}

		List<DocumentoXLotesoic> documentoXLotesoics = documento.getDocumentoXLotesoics();
		for (DocumentoXLotesoic documentoXLotesoic : documentoXLotesoics) {
			documentoLotesOICDAO.update(documentoXLotesoic);
		}
	}

	@Override
	public List<Documento> consultarFacturasDeExportacion() {
		try {
			return documentoDAO.consultarDocumentosFacturaExportacion("");
		} catch (Exception e) {
			LOGGER.error(e + "Error consultando facturas de exportacion");
			return null;
		}
	}

	@Override
	public List<Documento> consultarFacturasDeExportacionFiltro(Documento documento) {
		try {
			return documentoDAO.consultarDocumentosFacturaExportacion(documento.getConsecutivoDocumento());
		} catch (Exception e) {
			LOGGER.error(e + "Error consultando facturas de exportacion");
			return null;
		}
	}

	@Override
	public void actualizarFacturaDeExportacionFiltro(Documento documento) {
		try {
			documentoDAO.update(documento);
		} catch (Exception e) {
			LOGGER.error(e + "Error actualizando facturas de exportacion");
		}
	}

	@Override
	public List<AutorizarDocumentoDTO> consultarDocumentosAutorizar(String consecutivoDocumento) {
		try {
			return documentoDAO.consultarDocumentosAutorizar(consecutivoDocumento);
		} catch (Exception e) {
			LOGGER.error(e + "Error consultando facturas a autorizar");
		}
		return null;
	}

	@Override
	public void cambiarEstadoFacturaProforma(List<AutorizarDocumentoDTO> listado, LogAuditoria auditoria) {
		try {
			documentoDAO.cambiarEstadoFacturaProforma(listado, auditoria);
		} catch (Exception e) {
			LOGGER.error(e + "Error cambiando estado factura proforma");
		}
	}

	@Override
	public List<Documento> consultarTodosLosDocumentos() {
		LOGGER.debug("Metodo: <<consultarTodosLosDocumentos>>");
		return documentoDAO.consultarTodosLosDocumentos();
	}

	@Override
	public List<Documento> consultarSolicitudesPedidoPorAnular(String consecutivoDocumento) {
		LOGGER.debug("Metodo: <<consultarSolicitudesPedidoPorAnular>> parametros / consecutivoDocumento ->> {" + consecutivoDocumento + "} ");
		return documentoDAO.consultarSolicitudesPedidoPorAnular(consecutivoDocumento);
	}

	@Override
	public void anularSolicitudPedido(Documento documento) {
		LOGGER.debug("Metodo: <<anularSolicitudPedido>> parametros / documento ->> {" + documento + "} ");
		documentoDAO.anularSolicitudPedido(documento);
	}

	@Override
	public List<Cliente> listadoClientesInstruccionEmbarque(String idUsuario) {
		return clienteDao.consultarActivosPorUsuario(idUsuario);
	}

	@Override
	public List<DocumentoInstruccionEmbarqueDTO> listadoDocumentosInstruccionEmbarque(Long idCliente) {
		return documentoDAO.consultarDocumentosInstruccionEmbarque(idCliente);
	}

	@Override
	public List<DocumentoPorLotesInstruccionEmbarqueDTO> consultarDocumentosPorLotes(String strDocs, String strDocsMerca) {
		return documentoXLoteDAO.consultarDocumentosPorLotes(strDocs, strDocsMerca);
	}

	@Override
	public List<AgenteAduana> consultarAgenteAduana() {
		return agenteAduanaDAO.getAllActive();
	}

	@Override
	public List<Pais> findByPaisTodos() {
		return paisDAO.findByPaisTodos();
	}

	@Override
	public List<TerminoIncoterm> findTerminoIncotermAll() {
		return terminoDAO.getAll();
	}

	@Override
	public List<Ciudad> findCiudadesAll(String idPais) {
		return ciudadDAO.findByPais(idPais);
	}

	@Override
	public List<ModalidadEmbarque> findModalidadEmbarque() {
		return (List<ModalidadEmbarque>) modalidadEmbarqueDAO.findAll();
	}

	@Override
	public TerminosTransporte updateTerminoTransporte(TerminosTransporte terminosTransporte) {

		terminosTransporte.setModalidadEmbarque(modalidadEmbarqueDAO.findByPK(terminosTransporte.getModalidadEmbarque().getId()));
		terminosTransporte.setTerminoIncoterm(terminoIncotermDAO.findByPK(terminosTransporte.getTerminoIncoterm().getId()));
		terminosTransporte.setCiudade(ciudadDAO.findByPK(terminosTransporte.getCiudade().getId()));

		return terminosTransporteDAO.add(terminosTransporte);
	}

	@Override
	public String guardarInstruccionEmbarque(List<DocumentoInstruccionEmbarqueDTO> listadoDocumentos, TerminosTransporte terminosTransporte) {
		for (DocumentoInstruccionEmbarqueDTO dto : listadoDocumentos) {

			if (dto.isSeleccionado()) {

				terminosTransporteDAO.guardarTerminosTransporteDocumento(dto.getId(), terminosTransporte.getId());

				Documento documento = documentoDAO.findByPK(dto.getId());

				documento.setFechaEta(terminosTransporte.getFechaEmbarqueDate());

				documentoDAO.update(documento);

			}
		}
		return null;
	}

	@Override
	public List<Documento> consultarFacturasProformasParaGenerarListaEmpaque(String consecutivoDocumento) {
		return documentoDAO.consultarDocumentosParaGenerarListaEmpaque(consecutivoDocumento);
	}

	@Override
	public Documento generarListaEmpaque(Documento facturaProforma, DocumentoXNegociacion documentoXNegociacion, List<ProductoDTO> productoDTOs) {
		// crea la LE
		Documento listaEmpaque = crearListaEmpaque(facturaProforma);
		// crea el dxn
		documentoXNegociacion.getPk().setIdDocumento(listaEmpaque.getId());
		documentoXNegociacion.setDocumento(listaEmpaque);
		documentoXNegociacionDAO.add(documentoXNegociacion);
		// crea los pxd
		List<ProductosXDocumento> productosXDocumentosListaEmpaque = getProductosXDocumentosParaListaEmpaque(productoDTOs);
		for (ProductosXDocumento productosXDocumento : productosXDocumentosListaEmpaque) {
			productosXDocumento.getId().setIdDocumento(listaEmpaque.getId());
			productoXDocumentoDAO.add(productosXDocumento);
		}
		// actualizar estado de la SP
		Documento solicitudPedido = documentoDAO.consultarDocumentoPorConsecutivo(facturaProforma.getObservacionDocumento());
		Estadosxdocumento estadosxdocumentoSP = solicitudPedido.getEstadosxdocumento();
		EstadosxdocumentoPK idEstDocSP = estadosxdocumentoSP.getId();
		idEstDocSP.setIdEstado(Estado.CERRADA.getCodigo());
		estadosxdocumentoSP.setId(idEstDocSP);
		solicitudPedido.setEstadosxdocumento(estadosxdocumentoSP);
		documentoDAO.update(solicitudPedido);
		// actualizar estadod e la FP
		Estadosxdocumento estadosxdocumento = facturaProforma.getEstadosxdocumento();
		EstadosxdocumentoPK idEstadoXDocumento = estadosxdocumento.getId();
		idEstadoXDocumento.setIdEstado(Estado.LISTADA.getCodigo());
		estadosxdocumento.setId(idEstadoXDocumento);
		facturaProforma.setEstadosxdocumento(estadosxdocumento);
		documentoDAO.update(facturaProforma);
		return listaEmpaque;
	}

	private void crearMovimientos(Documento remision, ProductosXDocumento productosXDocumento) {
		crearMovimientoSalida(remision, productosXDocumento);

		crearMovimientoEntrada(remision, productosXDocumento);
	}

	private void crearMovimientoEntrada(Documento remision, ProductosXDocumento productosXDocumento) {
		MovimientosInventario movimientosInventarioEntrada = new MovimientosInventario();

		movimientosInventarioEntrada.setMoneda(productosXDocumento.getMoneda());
		ProductosInventario productosInventario = new ProductosInventario();
		productosInventario.setId(productosXDocumento.getId().getIdProducto());
		movimientosInventarioEntrada.setProductosInventario(productosInventario);
		movimientosInventarioEntrada.setUnidade(productosXDocumento.getUnidade());
		movimientosInventarioEntrada.setCantidad(productosXDocumento.getCantidad1());
		movimientosInventarioEntrada.setFecha(new Timestamp(productosXDocumento.getFechaEntrega().getTime()));

		movimientosInventarioEntrada.setUbicacionOrigen(remision.getUbicacionOrigen());
		Ubicacion ubicacionDestino = new Ubicacion(com.ssl.jv.gip.util.Ubicacion.TRANSITO.getCodigo());
		movimientosInventarioEntrada.setUbicacionDestino(ubicacionDestino);

		BodegasLogica bodegasLogica = new BodegasLogica(BodegaLogica.DEFAULT.getCodigo());
		movimientosInventarioEntrada.setBodegasLogica1(bodegasLogica);
		movimientosInventarioEntrada.setBodegasLogica2(bodegasLogica);

		com.ssl.jv.gip.jpa.pojo.TipoMovimiento tipoMovimiento = new com.ssl.jv.gip.jpa.pojo.TipoMovimiento();
		tipoMovimiento.setId(TipoMovimiento.ENTRADA.getCodigo());
		movimientosInventarioEntrada.setTipoMovimiento(tipoMovimiento);

		movimientosInventarioEntrada.setDocumento(remision);

		movimientoInventarioDAO.add(movimientosInventarioEntrada);
	}

	private void crearMovimientoSalida(Documento remision, ProductosXDocumento productosXDocumento) {
		MovimientosInventario movimientosInventarioSalida = new MovimientosInventario();

		movimientosInventarioSalida.setMoneda(productosXDocumento.getMoneda());
		ProductosInventario productosInventario = new ProductosInventario();
		productosInventario.setId(productosXDocumento.getId().getIdProducto());
		movimientosInventarioSalida.setProductosInventario(productosInventario);
		movimientosInventarioSalida.setUnidade(productosXDocumento.getUnidade());
		movimientosInventarioSalida.setCantidad(productosXDocumento.getCantidad1());
		movimientosInventarioSalida.setFecha(new Timestamp(productosXDocumento.getFechaEntrega().getTime()));

		movimientosInventarioSalida.setUbicacionOrigen(remision.getUbicacionOrigen());
		Ubicacion ubicacionDestino = new Ubicacion(com.ssl.jv.gip.util.Ubicacion.TRANSITO.getCodigo());
		movimientosInventarioSalida.setUbicacionDestino(ubicacionDestino);

		BodegasLogica bodegasLogica = new BodegasLogica(BodegaLogica.DEFAULT.getCodigo());
		movimientosInventarioSalida.setBodegasLogica1(bodegasLogica);
		movimientosInventarioSalida.setBodegasLogica2(bodegasLogica);

		com.ssl.jv.gip.jpa.pojo.TipoMovimiento tipoMovimiento = new com.ssl.jv.gip.jpa.pojo.TipoMovimiento();
		tipoMovimiento.setId(TipoMovimiento.SALIDA.getCodigo());
		movimientosInventarioSalida.setTipoMovimiento(tipoMovimiento);

		movimientosInventarioSalida.setDocumento(remision);

		movimientoInventarioDAO.add(movimientosInventarioSalida);
	}

	private List<ProductosXDocumento> getProductosXDocumentosParaRemision(List<ProductoDTO> productoDTOs) {
		List<ProductosXDocumento> productosXDocumentos = new ArrayList<>();

		Calendar calendar = Calendar.getInstance();
		for (ProductoDTO productoDTO : productoDTOs) {
			productosXDocumentos.add(getProductoXDocumentoParaRemision(productoDTO, calendar));
		}
		return productosXDocumentos;
	}

	private ProductosXDocumento getProductoXDocumentoParaRemision(ProductoDTO productoDTO, Calendar calendar) {
		ProductosXDocumento productosXDocumento = new ProductosXDocumento();

		productosXDocumento.setInformacion(Boolean.FALSE);
		productosXDocumento.setCalidad(Boolean.FALSE);

		productosXDocumento.setFechaEntrega(calendar.getTime());
		productosXDocumento.setFechaEstimadaEntrega(calendar.getTime());

		ProductosXDocumentoPK id = new ProductosXDocumentoPK();
		id.setIdProducto(Long.parseLong(productoDTO.getId()));
		productosXDocumento.setId(id);

		productosXDocumento.setCantidad1(productoDTO.getCantidad());

		Unidad unidade = new Unidad();
		unidade.setId(productoDTO.getIdUnidadVenta());
		productosXDocumento.setUnidade(unidade);

		com.ssl.jv.gip.jpa.pojo.Moneda moneda = new com.ssl.jv.gip.jpa.pojo.Moneda();
		moneda.setId(Moneda.COP.name());
		productosXDocumento.setMoneda(moneda);

		BodegasLogica bodegasLogica = new BodegasLogica(BodegaLogica.DEFAULT.getCodigo());
		productosXDocumento.setBodegasLogica1(bodegasLogica);
		productosXDocumento.setBodegasLogica2(bodegasLogica);

		productosXDocumento.setCantidad2(BigDecimal.ZERO);
		productosXDocumento.setValorUnitatrioMl(BigDecimal.ZERO);
		productosXDocumento.setValorUnitarioUsd(BigDecimal.ZERO);
		productosXDocumento.setValorTotal(BigDecimal.ZERO);
		productosXDocumento.setIva(BigDecimal.ZERO);
		productosXDocumento.setDescuentoxproducto(BigDecimal.ZERO);
		productosXDocumento.setOtrosDescuentos(BigDecimal.ZERO);

		return productosXDocumento;
	}

	private Documento crearRemision(Documento documento) {
		Documento remision = new Documento();
		Calendar calendar = Calendar.getInstance();
		remision.setFechaGeneracion(calendar.getTime());

		calendar.add(Calendar.DAY_OF_MONTH, 2);
		remision.setFechaEntrega(calendar.getTime());
		remision.setFechaEsperadaEntrega(calendar.getTime());

		Estadosxdocumento estadosxdocumento = new Estadosxdocumento();
		EstadosxdocumentoPK id = new EstadosxdocumentoPK();
		id.setIdEstado(Estado.PENDIENTE_RECIBIR.getCodigo());
		id.setIdTipoDocumento((long) ConstantesTipoDocumento.REMISION);
		estadosxdocumento.setId(id);
		remision.setEstadosxdocumento(estadosxdocumento);

		remision.setObservacionDocumento(documento.getConsecutivoDocumento());

		remision.setDocumentoCliente(documento.getDocumentoCliente());

		remision.setCliente(documento.getCliente());

		// remision.setPuntoVenta(Long.valueOf(0));
		remision.setDescuentoCliente(BigDecimal.ZERO);
		remision.setValorTotal(BigDecimal.ZERO);
		remision.setSubtotal(BigDecimal.ZERO);
		remision.setDescuento(BigDecimal.ZERO);
		remision.setValorIva10(BigDecimal.ZERO);
		remision.setValorIva16(BigDecimal.ZERO);
		remision.setValorIva5(BigDecimal.ZERO);

		remision.setNumeroFactura("0");

		Ubicacion ubicacionOrigen = new Ubicacion(com.ssl.jv.gip.util.Ubicacion.BODEGA_CENTRAL.getCodigo());
		remision.setUbicacionOrigen(ubicacionOrigen);
		remision.setUbicacionDestino(documento.getUbicacionDestino());

		TipoDocumento tipoDocumento = tipoDocumentoDAOLocal.findByPK(remision.getEstadosxdocumento().getId().getIdTipoDocumento());
		Ubicacion ubicacion = null;

		if (remision.getUbicacionOrigen() != null && remision.getUbicacionDestino() != null && com.ssl.jv.gip.util.Ubicacion.EXTERNA.getCodigo().equals(remision.getUbicacionDestino().getId())) {
			ubicacion = ubicacionDAOLocal.findByPK(remision.getUbicacionOrigen().getId());
		} else {
			if (remision.getUbicacionDestino() != null) {
				ubicacion = ubicacionDAOLocal.findByPK(remision.getUbicacionDestino().getId());
			} else {
				ubicacion = ubicacionDAOLocal.findByPK(com.ssl.jv.gip.util.Ubicacion.EXTERNA.getCodigo());
			}
		}

		String prefijoConsecutivo = tipoDocumento.getAbreviatura() + ubicacion.getEmpresa().getId();
		Long valorSecuencia = documentoDAO.consultarProximoValorSecuencia(prefijoConsecutivo + "_seq");

		remision.setConsecutivoDocumento(prefijoConsecutivo + "-" + valorSecuencia);

		remision = documentoDAO.add(remision);

		return remision;
	}

	private List<ProductosXDocumento> getProductosXDocumentosParaVentaDirecta(List<ProductoDTO> productoDTOs) {
		List<ProductosXDocumento> productosXDocumentos = new ArrayList<>();
		Calendar calendar = Calendar.getInstance();
		for (ProductoDTO productoDTO : productoDTOs) {
			productosXDocumentos.add(getProductoXDocumentoParaVentaDirecta(productoDTO, calendar));
		}
		return productosXDocumentos;
	}

	private ProductosXDocumento getProductoXDocumentoParaVentaDirecta(ProductoDTO productoDTO, Calendar calendar) {
		ProductosXDocumento productosXDocumento = new ProductosXDocumento();
		productosXDocumento.setInformacion(Boolean.FALSE);
		productosXDocumento.setCalidad(Boolean.FALSE);

		productosXDocumento.setFechaEntrega(calendar.getTime());
		productosXDocumento.setFechaEstimadaEntrega(calendar.getTime());

		ProductosXDocumentoPK id = new ProductosXDocumentoPK();
		id.setIdProducto(Long.parseLong(productoDTO.getId()));
		productosXDocumento.setId(id);

		productosXDocumento.setCantidad1(productoDTO.getCantidad());

		Unidad unidade = new Unidad();
		unidade.setId(productoDTO.getIdUnidadVenta());
		productosXDocumento.setUnidade(unidade);

		com.ssl.jv.gip.jpa.pojo.Moneda moneda = new com.ssl.jv.gip.jpa.pojo.Moneda();
		moneda.setId(Moneda.COP.name());
		productosXDocumento.setMoneda(moneda);

		BodegasLogica bodegasLogica = new BodegasLogica(BodegaLogica.DEFAULT.getCodigo());
		productosXDocumento.setBodegasLogica1(bodegasLogica);
		productosXDocumento.setBodegasLogica2(bodegasLogica);

		productosXDocumento.setCantidad2(BigDecimal.ZERO);
		productosXDocumento.setValorUnitatrioMl(BigDecimal.ZERO);
		productosXDocumento.setValorUnitarioUsd(BigDecimal.ZERO);
		productosXDocumento.setValorTotal(BigDecimal.ZERO);
		productosXDocumento.setIva(BigDecimal.ZERO);
		productosXDocumento.setDescuentoxproducto(BigDecimal.ZERO);
		productosXDocumento.setOtrosDescuentos(BigDecimal.ZERO);

		return productosXDocumento;
	}

	private Documento crearVentaDirecta(Documento documento) {
		Documento ventaDirecta = new Documento();
		Calendar calendar = Calendar.getInstance();
		ventaDirecta.setFechaGeneracion(calendar.getTime());
		calendar.add(Calendar.DAY_OF_MONTH, 2);
		ventaDirecta.setFechaEntrega(calendar.getTime());
		ventaDirecta.setFechaEsperadaEntrega(calendar.getTime());

		Estadosxdocumento estadosxdocumento = new Estadosxdocumento();
		EstadosxdocumentoPK id = new EstadosxdocumentoPK();
		id.setIdEstado(Estado.REMISIONADA.getCodigo());
		id.setIdTipoDocumento((long) ConstantesTipoDocumento.VENTA_DIRECTA);
		estadosxdocumento.setId(id);
		ventaDirecta.setEstadosxdocumento(estadosxdocumento);

		Ubicacion ubicacionOrigen = new Ubicacion(com.ssl.jv.gip.util.Ubicacion.EXTERNA.getCodigo());
		ventaDirecta.setUbicacionOrigen(ubicacionOrigen);

		ventaDirecta.setUbicacionDestino(documento.getUbicacionOrigen());

		ventaDirecta.setObservacionDocumento(documento.getObservacionDocumento());

		ventaDirecta.setDocumentoCliente(documento.getDocumentoCliente());

		ventaDirecta.setCliente(documento.getCliente());

		// ventaDirecta.setPuntoVenta(Long.valueOf(0));
		ventaDirecta.setNumeroFactura("0");

		ventaDirecta.setDescuentoCliente(BigDecimal.ZERO);
		ventaDirecta.setValorTotal(BigDecimal.ZERO);
		ventaDirecta.setSubtotal(BigDecimal.ZERO);
		ventaDirecta.setDescuento(BigDecimal.ZERO);
		ventaDirecta.setValorIva10(BigDecimal.ZERO);
		ventaDirecta.setValorIva16(BigDecimal.ZERO);
		ventaDirecta.setValorIva5(BigDecimal.ZERO);

		TipoDocumento tipoDocumento = tipoDocumentoDAOLocal.findByPK(ventaDirecta.getEstadosxdocumento().getId().getIdTipoDocumento());
		Ubicacion ubicacion = null;
		if (ventaDirecta.getUbicacionOrigen() != null && ventaDirecta.getUbicacionDestino() != null && com.ssl.jv.gip.util.Ubicacion.EXTERNA.getCodigo().equals(ventaDirecta.getUbicacionDestino().getId())) {
			ubicacion = ubicacionDAOLocal.findByPK(ventaDirecta.getUbicacionOrigen().getId());
		} else {
			if (ventaDirecta.getUbicacionDestino() != null) {
				ubicacion = ubicacionDAOLocal.findByPK(ventaDirecta.getUbicacionDestino().getId());
			} else {
				ubicacion = ubicacionDAOLocal.findByPK(com.ssl.jv.gip.util.Ubicacion.EXTERNA.getCodigo());
			}
		}

		String prefijoConsecutivo = tipoDocumento.getAbreviatura() + ubicacion.getEmpresa().getId();
		Long valorSecuencia = documentoDAO.consultarProximoValorSecuencia(prefijoConsecutivo + "_seq");

		ventaDirecta.setConsecutivoDocumento(prefijoConsecutivo + "-" + valorSecuencia);

		// ventaDirecta.getEstadosxdocumento().setEstado(
		// estadoDAOLocal
		// .findByPK(estadosxdocumento.getId().getIdEstado()));
		ventaDirecta = (Documento) documentoDAO.add(ventaDirecta);

		return ventaDirecta;
	}

	private Documento crearListaEmpaque(Documento documento) {
		Documento listaEmpaque = new Documento();
		Estadosxdocumento estadosxdocumento = new Estadosxdocumento();
		EstadosxdocumentoPK id = new EstadosxdocumentoPK();
		id.setIdEstado(Estado.ACTIVO.getCodigo());
		id.setIdTipoDocumento((long) ConstantesTipoDocumento.LISTA_EMPAQUE);
		estadosxdocumento.setId(id);
		listaEmpaque.setEstadosxdocumento(estadosxdocumento);
		listaEmpaque.setUbicacionOrigen(documento.getUbicacionOrigen());
		listaEmpaque.setUbicacionDestino(documento.getUbicacionOrigen());
		listaEmpaque.setFechaGeneracion(new Date());
		listaEmpaque.setObservacionDocumento(documento.getConsecutivoDocumento());
		listaEmpaque.setCliente(documento.getCliente());
		listaEmpaque.setDocumentoCliente(documento.getDocumentoCliente());
		listaEmpaque.setFechaEsperadaEntrega(documento.getFechaEsperadaEntrega());
		listaEmpaque.setNumeroFactura("0");
		listaEmpaque.setValorTotal(BigDecimal.ZERO);
		listaEmpaque.setDescuentoCliente(BigDecimal.ZERO);
		listaEmpaque.setSubtotal(BigDecimal.ZERO);
		listaEmpaque.setDescuento(BigDecimal.ZERO);
		listaEmpaque.setValorIva10(BigDecimal.ZERO);
		listaEmpaque.setValorIva16(BigDecimal.ZERO);
		listaEmpaque.setValorIva5(BigDecimal.ZERO);
		TipoDocumento tipoDocumento = tipoDocumentoDAOLocal.findByPK(listaEmpaque.getEstadosxdocumento().getId().getIdTipoDocumento());
		Ubicacion ubicacion = null;
		if (listaEmpaque.getUbicacionOrigen() != null && listaEmpaque.getUbicacionDestino() != null && com.ssl.jv.gip.util.Ubicacion.EXTERNA.getCodigo().equals(listaEmpaque.getUbicacionDestino().getId())) {
			ubicacion = ubicacionDAOLocal.findByPK(listaEmpaque.getUbicacionOrigen().getId());
		} else {
			if (listaEmpaque.getUbicacionDestino() != null) {
				ubicacion = ubicacionDAOLocal.findByPK(listaEmpaque.getUbicacionDestino().getId());
			} else {
				ubicacion = ubicacionDAOLocal.findByPK(com.ssl.jv.gip.util.Ubicacion.EXTERNA.getCodigo());
			}
		}
		listaEmpaque.setConsecutivoDocumento("LE1-" + this.documentoDAO.consultarProximoValorSecuencia("le1_seq"));
		listaEmpaque.getEstadosxdocumento().setEstado(estadoDAOLocal.findByPK(estadosxdocumento.getId().getIdEstado()));
		listaEmpaque = (Documento) documentoDAO.add(listaEmpaque);
		return listaEmpaque;
	}

	private List<ProductosXDocumento> getProductosXDocumentosParaListaEmpaque(List<ProductoDTO> productoDTOs) {
		List<ProductosXDocumento> productosXDocumentos = new ArrayList<>();
		for (ProductoDTO productoDTO : productoDTOs) {
			productosXDocumentos.add(getProductoXDocumento(productoDTO));
		}
		return productosXDocumentos;
	}

	private ProductosXDocumento getProductoXDocumento(ProductoDTO productoDTO) {
		ProductosXDocumento productosXDocumento = new ProductosXDocumento();
		ProductosXDocumentoPK id = new ProductosXDocumentoPK();
		id.setIdProducto(Long.valueOf(productoDTO.getId()));
		productosXDocumento.setId(id);
		Date fechaActual = new Date();
		// productosXDocumento.setInformacion(Boolean.FALSE);
		// productosXDocumento.setCalidad(Boolean.FALSE);
		productosXDocumento.setFechaEntrega(fechaActual);
		productosXDocumento.setFechaEstimadaEntrega(fechaActual);
		productosXDocumento.setCantidad1(productoDTO.getCantidad());
		// productosXDocumento.setCantidad2(BigDecimal.ZERO);
		// productosXDocumento.setValorUnitatrioMl(BigDecimal.ZERO);
		productosXDocumento.setValorUnitarioUsd(productoDTO.getValorUnitarioUsd());
		productosXDocumento.setValorTotal(productoDTO.getValorTotal());
		com.ssl.jv.gip.jpa.pojo.Unidad unidade = new com.ssl.jv.gip.jpa.pojo.Unidad();
		unidade.setId(productoDTO.getIdUnidadVenta());
		productosXDocumento.setUnidade(unidade);
		com.ssl.jv.gip.jpa.pojo.Moneda moneda = new com.ssl.jv.gip.jpa.pojo.Moneda();
		moneda.setId(Moneda.USD.name());
		productosXDocumento.setMoneda(moneda);
		productosXDocumento.setTotalPesoBrutoItem(productoDTO.getPesoBruto());
		productosXDocumento.setTotalPesoNetoItem(productoDTO.getPesoNeto());
		productosXDocumento.setCantidadCajasItem(productoDTO.getCantidadCajas());
		productosXDocumento.setCantidadPalletsItem(productoDTO.getCantidadPallets());
		productosXDocumento.setCantidadXEmbalaje(productoDTO.getCantidadPorEmbalaje());

		productosXDocumento.setDescuentoxproducto(BigDecimal.ZERO);

		return productosXDocumento;
	}

	@Override
	public List<CostoLogisticoDTO> generarCostosLogisticos(Long idCliente, List<Long> documentos, TerminoIncotermXMedioTransporte terminoIncoterm, String puerto, String puertos, Long idCurrency,
			String pais, Integer tipoContenedor1, BigDecimal cantidad1, Integer tipoContenedor2, BigDecimal cantidad2, BigDecimal valorTotal) {
		/*
		 * aplica_fob, cfr, cif, fca, cip, dap, dapm, cpt, fcat
		 */
		String campo = terminoIncoterm.getTerminoIncoterm().getDescripcion().toLowerCase();
		if ((terminoIncoterm.getTerminoIncoterm().getDescripcion().equals("FCA") && terminoIncoterm.getMedioTransporte().getId().equals(new Long(2)))) {
			campo += "t";
		} else {
			if (terminoIncoterm.getTerminoIncoterm().getDescripcion().equals("DAP") && terminoIncoterm.getMedioTransporte().getId().equals(new Long(1))) {
				campo += "m";
			}
		}
		return this.itemCostoLogisticoDAO.getCostosLogisticos(idCliente, documentos, campo, puerto, puertos, idCurrency, pais, tipoContenedor1, cantidad1, tipoContenedor2, cantidad2, valorTotal);
	}

	@Override
	public List<Documento> consultarListaEmpaquesParaAsignarDatosTL(String consecutivo) {
		FiltroDocumentoDTO filtro = new FiltroDocumentoDTO();
		filtro.setIdTipoDocumento((long) ConstantesTipoDocumento.LISTA_EMPAQUE);
		filtro.setIdEstado(Estado.ACTIVO.getCodigo());
		filtro.setSolicitudCafe(true);
		filtro.setConsecutivoDocumento(consecutivo);
		return documentoDAO.consultarDocumentosPorTipoDocumentoEstadoSolicitudCafeAndConsecutivo(filtro);
	}

	@Override
	public List<DocumentoXLotesoic> consultarDocumentosXLotesOICParaAsignarDatosTL(String consecutivo) {
		return documentoLotesOICDAO.consultarPorConsecutivoDocumento(consecutivo);
	}

	@Override
	public void asignarDatosTL(Documento listaEmpaque, List<DocumentoXLotesoic> documentoXLotesoics) {
		for (DocumentoXLotesoic documentoXLotesoic : documentoXLotesoics) {
			documentoLotesOICDAO.update(documentoXLotesoic);
		}
		Estadosxdocumento estadosxdocumento = listaEmpaque.getEstadosxdocumento();
		EstadosxdocumentoPK id = estadosxdocumento.getId();
		id.setIdEstado(Estado.ASIGNADA.getCodigo());
		estadosxdocumento.setId(id);
		listaEmpaque.setEstadosxdocumento(estadosxdocumento);
		documentoDAO.update(listaEmpaque);
	}

	@Override
	public List<DocumentoIncontermDTO> consultarDocumentosAutorizadosParaModificarFacturaProforma() {
		return documentoDAO.consultarDocumentosAutorizadosParaModificarFacturaProforma();
	}

	@Override
	public List<ProductoPorClienteComExtDTO> consultarListaProductosClienteFacturaProforma(Long idDocumento, Long idCliente) {
		return productoClienteComercioExteriorDAO.consultarListaProductosClienteFacturaProforma(idDocumento, idCliente);
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public String modificarFacturaProforma(DocumentoIncontermDTO documento, List<ProductoPorClienteComExtDTO> listado, LogAuditoria logAuditoria) {

		if (documento.getIdEstado() == ConstantesDocumento.GENERADO) {
			List<Documento> documentos = documentoDAO.consultarDocumentosPorObservacionDocumento(documento.getConsecutivoDocumento());
			if (documentos != null) {
				com.ssl.jv.gip.jpa.pojo.Estado estado = estadoDAOLocal.findByPK(new Long(ConstantesDocumento.ANULADO));
				for (Documento doc : documentos) {
					doc.getEstadosxdocumento().setEstado(estado);
					documentoDAO.update(doc);
				}
			}
		}

		List<Object[]> auditoria = documentoDAO.consultarAuditoriaEstadoModificacionFacturaProforma(documento);

		if (auditoria != null && auditoria.size() > 0) {

			String estadoAuditoria = auditoria.get(0)[2] != null ? auditoria.get(0)[2].toString().trim() : null;

			LOGGER.debug("Devolver el estado de la FP con id: " + documento.getIdDocumento());
			Map<String, Object> parametros = new HashMap<>();
			parametros.put("estado", new Long(estadoAuditoria));
			parametros.put("valorTotal", documento.getValorTotalDocumento());
			parametros.put("fechaEtrega", documento.getFechaEsperadaEntrega());
			parametros.put("id", (long) documento.getIdDocumento());
			documentoDAO.ejecutarConsultaNativa("UPDATE documentos SET id_estado = :estado, valor_total = :valorTotal, fecha_entrega = :fechaEtrega WHERE id = :id", parametros);
			LOGGER.debug("FP devuelta exitosamente");

			List<DocumentoXNegociacion> documentosXNegociaciones = documentoXNegociacionDAO.consultarDocumentoXNegociacionPorIdDocumento(documento.getIdDocumento());
			if (documentosXNegociaciones != null) {
				for (DocumentoXNegociacion docxneg : documentosXNegociaciones) {
					TerminoIncoterm terminoIncoterm = terminoIncotermDAO.findByPK(documento.getIdTerminoIncoterm());
					docxneg.setTerminoIncoterm(terminoIncoterm);
					docxneg.setTotalPesoNeto(documento.getTotalPesoNeto());
					docxneg.setTotalPesoBruto(documento.getTotalPesoBruto());
					docxneg.setTotalTendidos(documento.getTotalTendidos());
					docxneg.setTotalPallets(documento.getTotalPallets());
					docxneg.setCantidadContenedoresDe20(documento.getCantidadContenedores20());
					docxneg.setCantidadContenedoresDe40(documento.getCantidadContenedores40());
					docxneg.setLugarIncoterm(documento.getLugarIncoterm());
					docxneg.setCostoEntrega(documento.getCostoEntrega());
					docxneg.setCostoFlete(documento.getCostoFlete());
					docxneg.setCostoSeguro(documento.getCostoSeguro());
					docxneg.setOtrosGastos(documento.getOtrosGastos());
					documentoXNegociacionDAO.update(docxneg);
				}
			}

			return documentoDAO.modificarFacturaProforma(documento, listado);
		}

		return null;
	}

	@Override
	public void actualizarEstadoDocumento(Documento documento) {
		documentoDAO.actualizarEstadoDocumentoPorConsecutivo(documento);
	}

	@Override
	public void actualizarEstadoDocumentoPorId(Documento documento) {
		documentoDAO.actualizarEstadoDocumentoPorId(documento);
	}

	@Override
	public List<Documento> consultarFP(String consecutivoDocumento) {
		LOGGER.trace("Metodo: <<consultarFP>> parametros -->> consecutivoDocumento = " + consecutivoDocumento);
		Map<String, Object> parametros = new HashMap<>();
		parametros.put("tipoDocumento", (long) ConstantesTipoDocumento.FACTURA_PROFORMA);
		parametros.put("estadoAprobada", (long) ConstantesDocumento.APROBADA);
		parametros.put("estadoAsignada", (long) ConstantesDocumento.ASIGNADA);
		if (consecutivoDocumento != null && !consecutivoDocumento.isEmpty()) {
			parametros.put("consecutivoDocumento", "%" + consecutivoDocumento + "%");
		} else {
			parametros.put("consecutivoDocumento", "%");
		}
		return documentoDAO.buscarPorConsultaNombrada(Documento.BUSCAR_FACTURAS_PROFORMA, parametros);
	}

	@Override
	public List<Documento> consultarFacturasDeExportacionEstado(String consecutivoDocumento) {
		LOGGER.trace("Metodo: <<consultarFacturasDeExportacionEstado>> parametros -->> consecutivoDocumento = " + consecutivoDocumento);
		Map<String, Object> parametros = new HashMap<>();
		parametros.put("tipoDocumento", (long) ConstantesTipoDocumento.FACTURA_EXPORTACION);
		parametros.put("estado", (long) ConstantesDocumento.IMPRESO);
		if (consecutivoDocumento != null && !consecutivoDocumento.isEmpty()) {
			parametros.put("consecutivoDocumento", "%" + consecutivoDocumento + "%");
		} else {
			parametros.put("consecutivoDocumento", "%");
		}
		return documentoDAO.buscarPorConsultaNombrada(Documento.BUSCAR_FACTURAS_FX_ANULAR, parametros);
	}

	@Override
	public List<String> consultarPuertosNacionales() {
		return this.itemCostoLogisticoDAO.getPuertosNacionales();
	}

	@Override
	public List<String> consultarPuertosInternacionales(String idPais) {
		return this.itemCostoLogisticoDAO.getPuertosInternacionales(idPais);
	}

	@Override
	public List<DocumentoCostosLogisticosDTO> consultarDocumentosCostosLogisticos(Long idCliente) {
		return this.documentoDAO.consultarDocumentosCostosLogisticos(idCliente);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProductoODDTO> consultarProductoPorDocumentoOrdenDespacho(Long idDocumento, Long idCliente, Boolean cafe) {
		Map<String, Object> parametros = new HashMap<>();
		parametros.put("idDocumento", idDocumento);
		parametros.put("idCliente", idCliente);
		List<ProductoODDTO> lista = productoClienteCEDAO.buscarPorConsultaNativa(ProductoODDTO.BUSCAR_PRODUCTOS_ORDEN_DESPACHO, ProductoODDTO.class, parametros);
		for (ProductoODDTO p : lista) {
			BigDecimal calidades;
			List<Muestrasxlote> muestras = muestrasxloteDAO.consultarMuestrasPorCantidad(p.getCantidad());
			if (cafe) {
				for (Muestrasxlote m : muestras) {
					switch (m.getTipo()) {
					case "Calidades":
						if (p.getCantidadPorEmbalaje() != null) {
							calidades = p.getCantidadPorEmbalaje().multiply(m.getFactor()).add(m.getMuestras()).setScale(0, BigDecimal.ROUND_CEILING);
							p.setMuestrasCalidades(calidades);
							if (p.getMuestrasCalidades().longValue() < 2) {
								p.setMuestrasCalidades(new BigDecimal(2));
							}
						}
						break;
					case "Fito":
						p.setMuestrasFITOYANTICO(m.getMuestras());
						break;
					}
				}
			}
			if (p.getMuestrasCalidades() == null) {
				p.setMuestrasCalidades(new BigDecimal(0));
			}
			if (p.getMuestrasFITOYANTICO() == null) {
				p.setMuestrasFITOYANTICO(new BigDecimal(0));
			}
		}
		return lista;
	}

	@Override
	public int actualizarCostosLogisticos(BigDecimal valorTotal, BigDecimal fob, BigDecimal fletes, BigDecimal seguros, List<DocumentoCostosLogisticosDTO> documentos, LiquidacionCostoLogistico lcl) {
		// public int actualizarCostosLogisticos(Long idDocumento, Long
		// idTerminoIncoterm, BigDecimal valorFob, BigDecimal valorFletes,
		// BigDecimal valorSeguros, LiquidacionCostoLogistico lcl) {
		this.liquidacionCostoLogisticoDAO.add(lcl);
		int cuantos = 0;
		for (DocumentoCostosLogisticosDTO d : documentos) {
			cuantos += this.documentoDAO.actualizarCostosLogisticos(d.getIdDocumento(), d.getIdTerminoIncoterm(),
					d.getValorTotalDocumento().add(d.getEtiquetas()).divide(valorTotal, 2, RoundingMode.HALF_EVEN).multiply(fob),
					d.getValorTotalDocumento().add(d.getEtiquetas()).divide(valorTotal, 2, RoundingMode.HALF_EVEN).multiply(fletes),
					d.getValorTotalDocumento().add(d.getEtiquetas()).divide(valorTotal, 2, RoundingMode.HALF_EVEN).multiply(seguros));
		}
		return cuantos;
	}

	@Override
	public void generarReporteOrdenDespachoPDF(JasperPrint jasperPrint, Long id) throws ClassNotFoundException, IOException, JRException {
		HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
		httpServletResponse.addHeader("Content-disposition", "attachment; filename=" + id + ".pdf");
		ServletOutputStream servletStream = httpServletResponse.getOutputStream();
		JasperExportManager.exportReportToPdfStream(jasperPrint, servletStream);
		FacesContext.getCurrentInstance().responseComplete();
	}

	@SuppressWarnings("deprecation")
	@Override
	public void generarReporteOrdenDespachoExcel(JasperPrint jasperPrint, Long id) throws ClassNotFoundException, IOException, JRException {
		HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
		httpServletResponse.addHeader("Content-disposition", "attachment; filename=" + id + ".xlsx");
		ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
		JRXlsxExporter docxExporter = new JRXlsxExporter();
		docxExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		docxExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, servletOutputStream);
		docxExporter.exportReport();
		FacesContext.getCurrentInstance().responseComplete();
	}

	@Override
	public OutputStream generar(JasperPrint jasperPrint, String nombre, String tipo) throws JRException, IOException {
		OutputStream os = new ByteArrayOutputStream();
		if (tipo.equals("pdf")) {
			JasperExportManager.exportReportToPdfStream(jasperPrint, os);
		} else if (tipo.equals("xls")) {
			JRXlsExporter exporter = new JRXlsExporter();
			exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
			exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(os));
			SimpleXlsReportConfiguration configuration = new SimpleXlsReportConfiguration();
			configuration.setOnePagePerSheet(true);
			exporter.setConfiguration(configuration);
			exporter.exportReport();
		}
		os.flush();
		return os;
	}

	@Override
	public void actualizarEstadoDocumento(Long id, Long estado) {
		LOGGER.trace("Metodo: <<actualizarEstadoDocumento>> parametros -->> id = " + id + " estado = " + estado);
		Map<String, Object> parametros = new HashMap<>();
		parametros.put("id", id);
		parametros.put("id_estado", estado);
		documentoDAO.ejecutarConsultaNativa(Documento.ACTUALIZAR_ESTADO_DOCUMENTO, parametros);
		LOGGER.debug("Factura FX actualizada a estado impresa");
	}

	@Override
	public List<String> obtenerListaConsecutivosPorTipoLoteIOC(Long idTipoLoteIOC) {
		LOGGER.trace("Metodo: <<obtenerListaConsecutivosPorTipoLoteIOC>> parametros -->> idTipoLoteIOC = " + idTipoLoteIOC);
		String CONSECUTIVOS_DOCUMENTO_POR_LOTE_IOC = "select consecutivo from documento_x_lotesoic where id_tipo_lote = :tipoLote";
		List<String> consecutivos = null;
		Map<String, Object> parametros = new HashMap<>();
		parametros.put("tipoLote", idTipoLoteIOC);
		List<String> listaConsecutivos = documentoDAO.buscarPorConsultaNativa(CONSECUTIVOS_DOCUMENTO_POR_LOTE_IOC, parametros);
		if (listaConsecutivos != null) {
			for (String obj : listaConsecutivos) {
				if (consecutivos == null) {
					consecutivos = new ArrayList<>();
				}
				consecutivos.add((obj));
			}
		}
		return consecutivos;
	}

	@Override
	public void anularFacturaFX(Documento documento) {
		LOGGER.trace("Metodo: <<anularFacturaFX>> parametros -->> documento = " + documento);
		Map<String, Object> parametros;
		String consecutivoDocumentoFP = null;
		{ // primer paso: anular el documento
			LOGGER.debug("Actualizar el estado de la factura ");
			parametros = new HashMap<>();
			parametros.put("id_estado", (long) ConstantesDocumento.ANULADO);
			parametros.put("id", (long) documento.getId());
			documentoDAO.ejecutarConsultaNativa(Documento.ACTUALIZAR_ESTADO_DOCUMENTO, parametros);
			LOGGER.debug("Factura fx en estado anulada: OK");
		}
		{ // segundo paso: Eliminacion de VD, RM y Movimientos.
			LOGGER.debug("Eliminacion de VD, RM y Movimientos");
			parametros = new HashMap<>();
			parametros.put("consecutivoDocumento", documento.getObservacionDocumento());
			Documento orden = documentoDAO.buscarRegistroPorConsultaNombrada(Documento.BUSCAR_DOCUMENTO_POR_CONSECUTIVO, parametros);
			if (orden != null) {
				consecutivoDocumentoFP = orden.getObservacionDocumento();
				String[] arrayCadena = orden.getSitioEntrega().split(";");
				String consecVD = arrayCadena[0];
				String consecRM = arrayCadena[1];
				{
					LOGGER.debug("Consulta la venta directa asociada a la orden");
					parametros = new HashMap<>();
					parametros.put("consecutivoDocumento", consecVD);
					Documento vd = documentoDAO.buscarRegistroPorConsultaNombrada(Documento.BUSCAR_DOCUMENTO_POR_CONSECUTIVO, parametros);
					if (vd != null) {
						documentoDAO.delete(vd);
					}
				}
				{
					LOGGER.debug("Consulta la remision asociada a la orden");
					parametros = new HashMap<>();
					parametros.put("consecutivoDocumento", consecRM);
					Documento rm = documentoDAO.buscarRegistroPorConsultaNombrada(Documento.BUSCAR_DOCUMENTO_POR_CONSECUTIVO, parametros);
					if (rm != null) {
						documentoDAO.delete(rm);
					}
				}
			}
			LOGGER.debug("Eliminacion de VD, RM y Movimientos exitosa");
		}
		{ // tercer paso: Reactivacion de la respectiva FP
			LOGGER.debug("Reactivacion de la respectiva FP");
			if (consecutivoDocumentoFP != null) {
				parametros = new HashMap<>();
				parametros.put("consecutivoDocumento", consecutivoDocumentoFP);
				Documento fp = documentoDAO.buscarRegistroPorConsultaNombrada(Documento.BUSCAR_DOCUMENTO_POR_CONSECUTIVO, parametros);
				if (fp != null) {
					LOGGER.debug("Actualizar el estado de la factura fp");
					parametros = new HashMap<>();
					if (documento.getDocumentoXNegociacion().getSolicitudCafe()) {
						parametros.put("id_estado", (long) ConstantesDocumento.ASIGNADA);
					} else {
						parametros.put("id_estado", (long) ConstantesDocumento.APROBADA);
					}
					parametros.put("id", fp.getId());
					documentoDAO.ejecutarConsultaNativa(Documento.ACTUALIZAR_ESTADO_DOCUMENTO, parametros);
					LOGGER.debug("Factura fp actualizada");
				}
			}
			LOGGER.debug("Reactivacion de la respectiva FP exitosa");
		}
		{ // cuarto paso: Anulacion de la respectiva LE
			LOGGER.debug("Anulacion de la respectiva LE");
			parametros = new HashMap<>();
			parametros.put("estado", (long) ConstantesDocumento.ANULADO);
			parametros.put("observacionDocumento", null);
			parametros.put("consecutivoDocumento", documento.getObservacionDocumento());
			documentoDAO.ejecutarConsultaJPQL(Documento.ACTUALIZAR_ESTADO_Y_OBSERVACION_POR_CONSECUTIVO, parametros);
			LOGGER.debug("Anulacion de la respectiva LE exitosa");
		}
	}

	@Override
	public List<Documento> consultarDocumentosSP(String consecutivoDocumento) {
		return documentoDAO.consultarDocumentosSP(consecutivoDocumento);
	}

	@Override
	public List<Documento> consultarDocumentosOD(String consecutivoDocumento) {
		return documentoDAO.consultarDocumentosOD(consecutivoDocumento);
	}

	@Override
	public void asignarLotesOIC(Documento fp) {
		documentoXLoteDAO.asignarLotesOIC(fp);
	}

	@Override
	public Documento consultarFX(Long id) {
		LOGGER.trace("metodo: consultarFX()");
		Map<String, Object> parametros;
		parametros = new HashMap<>();
		parametros.put("id", id);
		return documentoDAO.buscarRegistroPorConsultaNombrada(Documento.BUSCAR_DOCUMENTO_FX_REIMPRIMIR_BY_ID, parametros);
	}

	@Override
	public List<DocumentoRequerimientoExportacionDTO> consultarDocumentosSolicitudPedidoRE(Map<String, Object> parametros) {
		return documentoDAO.consultarDocumentosRE(parametros);
	}

	@Override
	public ComextRequerimientoexportacion crearRequerimientoExportacion(ComextRequerimientoexportacion comextRequerimientoexportacion) {

		comextRequerimientoExportacionDAO.add(comextRequerimientoexportacion);

		return comextRequerimientoexportacion;
	}

	@Override
	public void crearReqxprod(List<ComextRequerimientoexportacionDTO> productos, long idrequerimiento, boolean selectedMarcacionEspecial) {

		for (ComextRequerimientoexportacionDTO rxp : productos) {
			Reqxproducto reqxprod = new Reqxproducto();
			reqxprod.setDocumento(rxp.getIddocumento());
			reqxprod.setProducto(rxp.getIdproducto());
			reqxprod.setIdrequerimiento(idrequerimiento);

			reqxprod.setMcajamaster(rxp.getCajamaster());
			reqxprod.setMpallet(rxp.getPallet());
			reqxprod.setMproducto(rxp.getProducto());
			reqxprod.setObservaciones(rxp.getObservaciones());
			reqxprod.setTienemarcacion(selectedMarcacionEspecial);

			reqxproductoDAO.add(reqxprod);
		}

	}

	@Override
	public List<ComextRequerimientoexportacionDTO> crearMarcacionEspecial(String id) {

		return comextRequerimientoExportacionDAO.crearMarcacionEspecial(id);

	}

	@Override
	public ComextRequerimientoexportacion actualizarRequerimientoExportacion(ComextRequerimientoexportacion comextRequerimientoexportacion) {

		comextRequerimientoExportacionDAO.update(comextRequerimientoexportacion);

		return comextRequerimientoexportacion;
	}

	@Override
	public void crearRequerimientoxdocumento(List<DocumentoRequerimientoExportacionDTO> listaDocumentos, long idrequerimiento) {

		for (DocumentoRequerimientoExportacionDTO rxd : listaDocumentos) {

			if (rxd.isSeleccionado()) {
				RequerimientosXDocumentoPK reqxdocPK = new RequerimientosXDocumentoPK();
				reqxdocPK.setDocumentoId(rxd.getIdDocumento());
				reqxdocPK.setRequerimientoexportacionId(idrequerimiento);

				RequerimientosXDocumento reqxdoc = new RequerimientosXDocumento();
				reqxdoc.setId(reqxdocPK);

				RequerimientosXDocumentoDAO.add(reqxdoc);
			}
		}

	}

	@Override
	public List<RequerimientosXDocumento> consultarComextRequerimientoExportacionXDocumento(Long id) {
		// TODO Auto-generated method stub
		LOGGER.debug("Metodo: <<consultarRequerimientoExportacion>>");
		if (id == null) {
			throw new IllegalArgumentException("El parametro <<idDocumento>> es requerido");
		} else {
			Map<String, Object> parametros = new HashMap<>();
			parametros.put("id", id);

			return RequerimientosXDocumentoDAO.buscarPorConsultaNombrada(RequerimientosXDocumento.BUSCAR_DOCUMENTO_POR_CONSECUTIVO, parametros);
		}
	}

	@Override
	public void actualizarComextRequerimientoExportacionEstado(DocumentoRequerimientoExportacionDTO requerimiento) {

		comextRequerimientoExportacionDAO.actualizarEstadoRequerimientoExportacion(requerimiento);

	}

	@Override
	public List<ComextRequerimientoexportacion> consultarComextRequerimientoExportacionEstado(Integer idEstado) {
		// TODO Auto-generated method stub
		LOGGER.debug("Metodo: <<consultarRequerimientoExportacionEstado>>");
		if (idEstado == null) {
			throw new IllegalArgumentException("El parametro <<idEstado>> es requerido");
		} else {
			Map<String, Object> parametros = new HashMap<>();
			parametros.put("idEstado", idEstado);

			return comextRequerimientoExportacionDAO.buscarPorConsultaNombrada(ComextRequerimientoexportacion.FIND_BY_ESTADO, parametros);
		}
	}

	@Override
	public List<ComextRequerimientoexportacion> consultarComextRequerimientoExportacionEstadoXConsecutivo(Integer idEstado, Long id) {
		// TODO Auto-generated method stub
		LOGGER.debug("Metodo: <<consultarRequerimientoExportacionEstado>>");
		if (id == null) {
			throw new IllegalArgumentException("El parametro <<idDocumento>> es requerido");
		} else {
			Map<String, Object> parametros = new HashMap<>();
			parametros.put("idEstado", idEstado);
			parametros.put("id", id);

			return comextRequerimientoExportacionDAO.buscarPorConsultaNombrada(ComextRequerimientoexportacion.FIND_BY_ESTADO_POR_CONSECUTIVO, parametros);
		}
	}

	@Override
	public void actualizarReqxprod(List<ComextRequerimientoexportacionDTO> productos, long idrequerimiento, boolean selectedMarcacionEspecial) {
		System.out.println("Ingresa a actualizar reqxprod EJB");

		for (ComextRequerimientoexportacionDTO rxp : productos) {
			Reqxproducto reqxprod = new Reqxproducto();

			reqxprod.setId(rxp.getId());
			reqxprod.setDocumento(rxp.getIddocumento());
			reqxprod.setProducto(rxp.getIdproducto());
			reqxprod.setIdrequerimiento(idrequerimiento);

			reqxprod.setMcajamaster(rxp.getCajamaster());
			reqxprod.setMpallet(rxp.getPallet());
			reqxprod.setMproducto(rxp.getProducto());
			reqxprod.setObservaciones(rxp.getObservaciones());
			reqxprod.setTienemarcacion(selectedMarcacionEspecial);

			reqxproductoDAO.update(reqxprod);

		}

	}

	@Override
	public List<ProductosXClienteComext> consultarProductosActivosXCliente(Map<String, Object> filtro) {
		LOGGER.trace("Metodo: <<consultarProductosActivosXCliente>>");
		if (filtro == null) {
			throw new IllegalArgumentException();
		}
		Long cliente = (Long) filtro.get("cliente");
		Boolean solicitudCafe = (Boolean) filtro.get("solicitudCafe");
		List<Long> productosAExcluir = (List<Long>) filtro.get("productosAExcluir");
		if (cliente == null) {
			throw new IllegalArgumentException();
		}
		StringBuilder query = new StringBuilder(
				"SELECT p FROM ProductosXClienteComext p JOIN FETCH p.productosInventario pi JOIN FETCH pi.productosInventarioComext pic JOIN FETCH pic.tipoLoteoic WHERE p.pk.idCliente= :cliente AND p.activo = true ");
		if (solicitudCafe) {
			query.append("AND pic.tipoLoteoic.id <> 5 ");
		} else {
			query.append("AND pic.tipoLoteoic.id = 5 ");
		}
		if (productosAExcluir != null && !productosAExcluir.isEmpty()) {
			query.append("AND p.pk.idProducto NOT IN (:productosAExcluir) ");
		}
		query.append("ORDER BY p.productosInventario.sku");
		Map<String, Object> parametros = new HashMap<>();
		parametros.put("cliente", cliente);
		if (productosAExcluir != null && !productosAExcluir.isEmpty()) {
			parametros.put("productosAExcluir", productosAExcluir);
		}
		return productosXClienteComercioExteriorDAO.buscarPorConsultaJPQL(query.toString(), parametros);
	}

	@Override
	public List<ProductosXDocumento> consultarProductosSP(Long sp, Long cliente) {
		LOGGER.trace("Metodo: <<consultarProductosSP>>");
		if (sp == null) {
			throw new IllegalArgumentException();
		}
		Map<String, Object> parametros;
		List<ProductosXDocumento> lista;
		{
			parametros = new HashMap<>();
			parametros.put("idDocumento", sp);
			lista = productoXDocumentoDAO.buscarPorConsultaNombrada(ProductosXDocumento.BUSCAR_PRODUCTOS_SP_COMERCIO_EXTERIOR, parametros);
		}
		{
			for (ProductosXDocumento pxd : lista) {
				parametros = new HashMap<>();
				parametros.put("idCliente", cliente);
				parametros.put("idProducto", pxd.getProductosInventario().getId());
				try {
					ProductosXClienteComext pxc = productosXClienteComercioExteriorDAO.buscarRegistroPorConsultaNombrada(ProductosXClienteComext.BUSCAR_POR_PRODUCTO_Y_CLIENTE, parametros);
					pxd.setValorUnitarioUsd(pxc.getPrecio());
				} catch (Exception ex) {
					LOGGER.error("no existe el producto para el cliente");
				}
			}
		}
		return lista;
	}
}
