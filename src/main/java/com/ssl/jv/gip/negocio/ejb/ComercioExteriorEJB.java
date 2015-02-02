package com.ssl.jv.gip.negocio.ejb;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import com.ssl.jv.gip.jpa.pojo.AgenteAduana;
import com.ssl.jv.gip.jpa.pojo.BodegasLogica;
import com.ssl.jv.gip.jpa.pojo.Ciudad;
import com.ssl.jv.gip.jpa.pojo.Cliente;
import com.ssl.jv.gip.jpa.pojo.Documento;
import com.ssl.jv.gip.jpa.pojo.DocumentoXLotesoic;
import com.ssl.jv.gip.jpa.pojo.DocumentoXNegociacion;
import com.ssl.jv.gip.jpa.pojo.Estadosxdocumento;
import com.ssl.jv.gip.jpa.pojo.EstadosxdocumentoPK;
import com.ssl.jv.gip.jpa.pojo.LogAuditoria;
import com.ssl.jv.gip.jpa.pojo.ModalidadEmbarque;
import com.ssl.jv.gip.jpa.pojo.MovimientosInventario;
import com.ssl.jv.gip.jpa.pojo.MovimientosInventarioComext;
import com.ssl.jv.gip.jpa.pojo.Pais;
import com.ssl.jv.gip.jpa.pojo.ProductosInventario;
import com.ssl.jv.gip.jpa.pojo.ProductosXClienteComext;
import com.ssl.jv.gip.jpa.pojo.ProductosXDocumento;
import com.ssl.jv.gip.jpa.pojo.ProductosXDocumentoPK;
import com.ssl.jv.gip.jpa.pojo.TerminoIncoterm;
import com.ssl.jv.gip.jpa.pojo.TerminosTransporte;
import com.ssl.jv.gip.jpa.pojo.TipoDocumento;
import com.ssl.jv.gip.jpa.pojo.Ubicacion;
import com.ssl.jv.gip.jpa.pojo.Unidad;
import com.ssl.jv.gip.negocio.dao.AgenteAduanaDAOLocal;
import com.ssl.jv.gip.negocio.dao.CiudadDAOLocal;
import com.ssl.jv.gip.negocio.dao.ClienteDAOLocal;
import com.ssl.jv.gip.negocio.dao.DocumentoDAOLocal;
import com.ssl.jv.gip.negocio.dao.DocumentoLotesOICDAOLocal;
import com.ssl.jv.gip.negocio.dao.DocumentoXLoteDAOLocal;
import com.ssl.jv.gip.negocio.dao.DocumentoXNegociacionDAOLocal;
import com.ssl.jv.gip.negocio.dao.EstadoDAOLocal;
import com.ssl.jv.gip.negocio.dao.LogAuditoriaDAOLocal;
import com.ssl.jv.gip.negocio.dao.ModalidadEmbarqueDAOLocal;
import com.ssl.jv.gip.negocio.dao.MovimientoInventarioDAOLocal;
import com.ssl.jv.gip.negocio.dao.MovimientosInventarioComextDAOLocal;
import com.ssl.jv.gip.negocio.dao.PaisDAOLocal;
import com.ssl.jv.gip.negocio.dao.ProductoClienteComercioExteriorDAOLocal;
import com.ssl.jv.gip.negocio.dao.ProductoInventarioDAOLocal;
import com.ssl.jv.gip.negocio.dao.ProductosXDocumentoDAOLocal;
import com.ssl.jv.gip.negocio.dao.TerminoIncotermDAOLocal;
import com.ssl.jv.gip.negocio.dao.TerminosTransporteDAOLocal;
import com.ssl.jv.gip.negocio.dao.TipoDocumentoDAOLocal;
import com.ssl.jv.gip.negocio.dao.UbicacionDAOLocal;
import com.ssl.jv.gip.negocio.dto.AutorizarDocumentoDTO;
import com.ssl.jv.gip.negocio.dto.DatoContribucionCafeteraDTO;
import com.ssl.jv.gip.negocio.dto.DocumentoIncontermDTO;
import com.ssl.jv.gip.negocio.dto.DocumentoInstruccionEmbarqueDTO;
import com.ssl.jv.gip.negocio.dto.DocumentoLotesContribucionCafeteriaDTO;
import com.ssl.jv.gip.negocio.dto.DocumentoPorLotesInstruccionEmbarqueDTO;
import com.ssl.jv.gip.negocio.dto.FiltroConsultaSolicitudDTO;
import com.ssl.jv.gip.negocio.dto.ListaEmpaqueDTO;
import com.ssl.jv.gip.negocio.dto.ProductoAsignarLoteOICDTO;
import com.ssl.jv.gip.negocio.dto.ProductoDTO;
import com.ssl.jv.gip.negocio.dto.ProductoGenerarFacturaPFDTO;
import com.ssl.jv.gip.negocio.dto.ProductoImprimirLEDTO;
import com.ssl.jv.gip.negocio.dto.ProductoLoteAsignarLoteOICDTO;
import com.ssl.jv.gip.negocio.dto.ProductoPorClienteComExtDTO;
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
	private static final Logger LOGGER = Logger
			.getLogger(ComercioExteriorEJB.class);

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

	/**
	 * Default constructor.
	 */
	public ComercioExteriorEJB() {

	}

	/**
	 * Default constructor.
	 */
	public List<DatoContribucionCafeteraDTO> consultarDatosContribucionCafetera(
			Map<String, Object> parametros) {
		return documentoDAO.consultarDatosContribucionCafetera(parametros);
	}

	public List<DocumentoLotesContribucionCafeteriaDTO> consultarDocumentoLotesContribucionCafetera(
			Map<String, Object> parametros) {
		return documentoLotesOICDAO
				.consultarDocumentoLotesContribucionCafetera(parametros);
	}

	public List<DocumentoLotesContribucionCafeteriaDTO> guardarDocumentoLotesContribucionCafetera(
			List<DocumentoLotesContribucionCafeteriaDTO> documentos) {
		return documentoLotesOICDAO
				.guardarDocumentoLotesContribucionCafetera(documentos);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ssl.jv.gip.negocio.ejb.ComercioExteriorEJBLocal#
	 * consultarDocumentosPorLoteOIC()
	 */
	@Override
	public List<DocumentoXLotesoic> consultarDocumentosPorLoteOIC() {
		List<DocumentoXLotesoic> listado = new ArrayList<DocumentoXLotesoic>();

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
		try {
			return (Cliente) clienteDao.findByPK(idCliente);
		} catch (Exception e) {
			LOGGER.error(e + " Error consultando cliente");
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ssl.jv.gip.negocio.ejb.ComercioExteriorEJBLocal#
	 * consultarDocumentosCostosInconterm()
	 */
	@Override
	public List<DocumentoIncontermDTO> consultarDocumentosCostosInconterm() {
		List<DocumentoIncontermDTO> listado = new ArrayList<DocumentoIncontermDTO>();

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
	public void actualizarDocumentoPorNegociacion(
			DocumentoIncontermDTO documento) {
		documentoDAO.actualizarDocumentoPorNegociacion(documento);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ssl.jv.gip.negocio.ejb.ComercioExteriorEJBLocal#
	 * consultarListaIncontermPorCliente(java.lang.Long)
	 */
	@Override
	public List<TerminoIncoterm> consultarListaIncontermPorCliente(
			Long idCliente) {
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
		List<DocumentoIncontermDTO> listado = new ArrayList<DocumentoIncontermDTO>();

		try {
			listado = documentoDAO.consultarDocumentosSolicitudPedido();
		} catch (Exception e) {

		}

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
	public List<DocumentoIncontermDTO> consultarDocumentosSolicitudPedido(
			FiltroConsultaSolicitudDTO filtro) {
		List<DocumentoIncontermDTO> listado = new ArrayList<DocumentoIncontermDTO>();

		try {
			listado = documentoDAO.consultarDocumentosSolicitudPedido(filtro);
		} catch (Exception e) {

		}

		return listado;
	}

	@Override
	public List<DocumentoIncontermDTO> consultarDocumentosAprobarSolicitudPedido() {
		List<DocumentoIncontermDTO> listado = new ArrayList<DocumentoIncontermDTO>();

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
	public List<ProductoPorClienteComExtDTO> consultarListaSolicitudesPedido(
			Long idDocumento, Long idCliente) {
		List<ProductoPorClienteComExtDTO> listado = new ArrayList<ProductoPorClienteComExtDTO>();

		try {
			listado = productoClienteCEDAO.consultarListaSolicitudesPedido(
					idDocumento, idCliente);
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
	public List<ProductoPorClienteComExtDTO> consultarListaProductosPorClienteCE(
			Long idCliente, String idsProductos, Boolean solicitudCafe) {
		List<ProductoPorClienteComExtDTO> listado = new ArrayList<ProductoPorClienteComExtDTO>();

		try {
			listado = productoClienteCEDAO.consultarListaProductosPorClienteCE(
					idCliente, idsProductos, solicitudCafe);
		} catch (Exception e) {

		}

		return listado;
	}

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
	public void guardarSolicitudPedido(DocumentoIncontermDTO documento,
			List<ProductoPorClienteComExtDTO> listaSolicitudPedido) {
		if (documento.getIdEstado() == ConstantesDocumento.GENERADO) {
			documento.setIdEstado(new Long(ConstantesDocumento.ANULADO));
			documentoDAO.actualizarEstadoDocumentoPorConsecutivo(documento);
		}

		if (documento.getIdTipoDocumento() == ConstantesTipoDocumento.SOLICITUD_PEDIDO) {
			documento.setIdEstado(new Long(ConstantesDocumento.VERIFICADO));
		}

		if (documento.getFechaEsperadaEntregaDate() != null) {
			documento.setFechaEsperadaEntrega(new Timestamp(documento
					.getFechaEsperadaEntregaDate().getTime()));
		}

		Documento vdocumento = documentoDAO
				.findByPK(documento.getIdDocumento());
		vdocumento.setValorTotal(documento.getValorTotalDocumento());
		Ubicacion ubicacion1 = ubicacionDAO.findByPK(documento
				.getIdUbicacionOrigen());
		vdocumento.setUbicacionOrigen(ubicacion1);
		Ubicacion ubicacion2 = ubicacionDAO.findByPK(documento
				.getIdUbicacionDestino());
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

				if (vProducto.getControlStockProductoInventarioCE() != null
						&& vProducto.getControlStockProductoInventarioCE()) {
					if (vProducto.isBlnIncluirBusqueda()) {
						// Consultar saldo del producto inventario, si no tiene
						// lanzar excepcion
						dblSaldoActual = productoClienteComercioExteriorDAO
								.consultarUltimoSaldoProducto(vProducto
										.getIntIdProductoInventario());
						if (dblSaldoActual == null) {
							// TODO Lanzar mensaje de excepcion
							// Los siguientes skus no tienen saldo de inventario
							// asociado:
						}

					}
					// Si tiene saldo

					// Consultar la cantidad de producto
					dblCantidadActual = vProducto
							.getDblCantidad1ActualProductoxDocumento();

					// el calculo del nuevo saldo es saldo actual + la cantidad
					// actual - la cantidad nueva
					BigDecimal dblNuevoSaldo = dblSaldoActual.add(
							dblCantidadActual).min(
							vProducto.getDblCantidad1ProductoxDocumento());

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
						productoClienteComercioExteriorDAO.crearSaldo(
								documento, vProducto);
						// Consultar si existe producto por documento
						Boolean existe = productoClienteComercioExteriorDAO
								.consultarProductoCliente(
										documento.getIdDocumento(),
										vProducto.getIntIdProductoInventario());

						if (existe) {
							// Actualizar
							productoClienteComercioExteriorDAO
									.modificarFacturaProforma(documento,
											vProducto);
						} else {
							// Insertar
							productoClienteComercioExteriorDAO
									.crearFacturaProforma(documento, vProducto);
						}

					} else {
						// Eliminar
						productoClienteComercioExteriorDAO
								.eliminarFacturaProforma(documento, vProducto);

						// Consultar cantidad
						dblCantidadActual = vProducto
								.getDblCantidad1ActualProductoxDocumento();

						dblNuevoSaldo = dblSaldoActual.add(dblCantidadActual);

						vProducto.setDblSaldo(dblNuevoSaldo);
						vProducto.setIdTipoMovimiento(2L);
						// Crear saldo
						productoClienteComercioExteriorDAO.crearSaldo(
								documento, vProducto);
					}

				} else {
					if (vProducto.getControlStockProductoInventarioCE() == null
							|| !vProducto.getControlStockProductoInventarioCE()) {
						if (vProducto.isBlnIncluirBusqueda()) {
							// Consultar si existe producto por documento
							Boolean existe = productoClienteComercioExteriorDAO
									.consultarProductoCliente(documento
											.getIdDocumento(), vProducto
											.getIntIdProductoInventario());

							if (existe) {
								// Actualizar
								productoClienteComercioExteriorDAO
										.modificarFacturaProforma(documento,
												vProducto);
							} else {
								// Insertar
								productoClienteComercioExteriorDAO
										.crearFacturaProforma(documento,
												vProducto);
							}

						} else {
							// Eliminar
							productoClienteComercioExteriorDAO
									.eliminarFacturaProforma(documento,
											vProducto);
						}
					}

				}

			}
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ssl.jv.gip.negocio.ejb.ComercioExteriorEJBLocal#
	 * consultarUbicacionesPorUsuario(java.lang.String)
	 */
	@Override
	public List<Ubicacion> consultarUbicacionesPorUsuario(String idUsuario) {
		List<Ubicacion> listado = new ArrayList<Ubicacion>();

		try {
			listado = ubicacionDAO.consultarUbicacionesPorUsuario(idUsuario);
		} catch (Exception e) {

		}

		return listado;
	}

	@Override
	public List<ListaEmpaqueDTO> consultarDocumentoPorFacturaProforma(
			String consecutivoFacturaProforma) {
		return documentoDAO
				.consultarDocumentoPorFacturaProforma(consecutivoFacturaProforma);
	}

	@Override
	public List<ProductoDTO> consultarProductoPorDocumento(
			ListaEmpaqueDTO listaEmpaqueDTO) {
		return productoClienteComercioExteriorDAO
				.consultarProductoPorDocumento(listaEmpaqueDTO);
	}

	@Override
	public BigInteger generarListaEmpaque(ListaEmpaqueDTO listaEmpaqueDTO) {
		return documentoDAO.generarListaEmpaque(listaEmpaqueDTO);
	}

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
	public List<ProductoGenerarFacturaPFDTO> consultarProductoPorDocumentoGenerarFacturaProforma(
			Long idDocumento, Long idCliente) {
		return productoClienteComercioExteriorDAO
				.consultarProductoPorDocumentoGenerarFacturaProforma(
						idDocumento, idCliente);
	}

	@Override
	public List<ProductoAsignarLoteOICDTO> consultarProductoPorDocumentoAsignarLotesOIC(
			Long idDocumento, Long idCliente) {
		return productoClienteComercioExteriorDAO
				.consultarProductoPorDocumentoAsignarLotesOIC(idDocumento,
						idCliente);
	}

	@Override
	public List<ProductoLoteAsignarLoteOICDTO> consultarProductoPorDocumentoLoteAsignarLotesOIC(
			Long idDocumento, Long idCliente) {
		return productoClienteComercioExteriorDAO
				.consultarProductoPorDocumentoLoteAsignarLotesOIC(idDocumento,
						idCliente);
	}

	@Override
	public List<Documento> consultarDocumentosSolicitudPedido(
			String consecutivoDocumento) {
		return documentoDAO
				.consultarDocumentosSolicitudPedido(consecutivoDocumento);
	}

	@Override
	public List<Documento> consultarDocumentosFacturaPF(
			String consecutivoDocumento) {
		return documentoDAO.consultarDocumentosFacturaPF(consecutivoDocumento);
	}

	@Override
	public List<Documento> consultarDocumento(Map<String, Object> parametros,
			Long[] idEstados) {
		// TODO Auto-generated method stub
		return documentoDAO.consultarDocumento(parametros, idEstados);
	}

	@Override
	public ListaEmpaqueDTO consultarDocumentoListaEmpaque(
			String consecutivoDocumento) {
		return documentoDAO
				.consultarDocumentoListaEmpaque(consecutivoDocumento);
	}

	@Override
	public Documento crearFactura(Documento documento, LogAuditoria auditoria,
			DocumentoXNegociacion documentoPorNegociacion,
			List<ProductosXDocumento> productos, Documento original) {
		documento.setConsecutivoDocumento("FP1-"
				+ this.documentoDAO.consultarProximoValorSecuencia("fp1_seq"));
		documento = (Documento) this.documentoDAO.add(documento);
		auditoria.setIdRegTabla(documento.getId());
		auditoria.setValorNuevo(documento.getConsecutivoDocumento());
		this.logAuditoriaDAO.add(auditoria);
		documentoPorNegociacion.getPk().setIdDocumento(documento.getId());
		documentoXNegociacionDAO.add(documentoPorNegociacion);
		for (ProductosXDocumento pxd : productos) {
			pxd.getId().setIdDocumento(documento.getId());
			this.productoXDocumentoDAO.add(pxd);
		}
		original.getEstadosxdocumento().getId()
				.setIdEstado((long) ConstantesDocumento.APROBADA);
		this.documentoDAO.update(original);
		return documento;
	}

	@Override
	public Documento crearSolicitudPedido(Documento documento,
			LogAuditoria auditoria,
			DocumentoXNegociacion documentoPorNegociacion,
			List<ProductosXDocumento> productos,
			List<MovimientosInventarioComext> mice) {
		documento.setConsecutivoDocumento("SP1-"
				+ this.documentoDAO.consultarProximoValorSecuencia("sp1_seq"));
		documento = (Documento) this.documentoDAO.add(documento);
		auditoria.setIdRegTabla(documento.getId());
		auditoria.setValorNuevo(documento.getConsecutivoDocumento());
		this.logAuditoriaDAO.add(auditoria);
		documentoPorNegociacion.getPk().setIdDocumento(documento.getId());
		documentoXNegociacionDAO.add(documentoPorNegociacion);
		for (ProductosXDocumento pxd : productos) {
			pxd.getId().setIdDocumento(documento.getId());
			this.productoXDocumentoDAO.add(pxd);
		}
		for (MovimientosInventarioComext mic : mice) {
			mic = (MovimientosInventarioComext) movimientosInventarioComextDAO
					.add(mic);
			LogAuditoria aud = new LogAuditoria();
			aud.setIdUsuario(auditoria.getIdUsuario());
			aud.setIdRegTabla(mic.getId());
			aud.setIdFuncionalidad(auditoria.getIdFuncionalidad());
			aud.setTabla(MovimientosInventarioComext.class.getName());
			aud.setAccion("CRE");
			aud.setCampo(null);
			aud.setValorAnterior(null);
			aud.setFecha(new Timestamp(System.currentTimeMillis()));
			this.logAuditoriaDAO.add(aud);
		}
		return documento;
	}

	public List<ProductoImprimirLEDTO> consultarProductoListaEmpaque(
			String consecutivoDocumento) {
		return documentoDAO.consultarProductoListaEmpaque(consecutivoDocumento);
	}

	@Override
	public List<DocumentoXLotesoic> guardarLotes(
			List<DocumentoXLotesoic> lista, Documento documento) {
		for (DocumentoXLotesoic dxl : lista) {
			Long seq = documentoXLoteDAO
					.consultarProximoValorSecuencia("lote_oic_seq");
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
	public Hashtable<Long, BigDecimal> consultarUltimosSaldos() {
		List<MovimientosInventarioComext> saldos = this.movimientosInventarioComextDAO
				.getUltimosSaldos();
		Hashtable<Long, BigDecimal> lista = new Hashtable<Long, BigDecimal>();
		for (MovimientosInventarioComext mic : saldos) {
			lista.put(mic.getProductosInventarioComext().getIdProducto(),
					mic.getSaldo());
		}
		return lista;
	}

	@Override
	public ProductosXClienteComext consultarPorClienteSku(Long idCliente,
			String sku) {
		try {
			return this.productoClienteComercioExteriorDAO
					.consultarPorClienteSku(idCliente, sku);
		} catch (Exception e) {
			return null;
		}
	}

	public List<Documento> consultarDocumentosActivosPorTipoDocumentoYConsecutivoDocumento(
			Long idTipoDocumento, String consecutivoDocumento) {
		List<Documento> listaEmpaques = this.documentoDAO
				.consultarDocumentosPorTipoDocumentoConsecutivoDocumentoYEstados(
						idTipoDocumento, consecutivoDocumento,
						Estado.ACTIVO.getCodigo());
		for (Documento documento : listaEmpaques) {
			documento.getDocumentoXLotesoics().iterator().hasNext();
		}
		return listaEmpaques;
	}

	@Override
	public List<ProductosInventario> consultarProductosInventariosPorSkus(
			List<String> skus) {
		return this.productoInventarioDAOLocal.consultarPorSkus(skus);
	}

	@Override
	public List<ProductosXDocumento> consultarProductosXDocumentosPorDocumento(
			Long idDocumento) {
		return productosXDocumentoDAOLocal.consultarPorDocumento(idDocumento);
	}

	@Override
	public void modificarListaEmpaque(Documento documento,
			List<ProductosXDocumento> productosXDocumentos) {
		this.productosXDocumentoDAOLocal
				.modificarProductosXDocumentos(productosXDocumentos);

		List<DocumentoXNegociacion> documentoXNegociacions = documento
				.getDocumentoXNegociacions();
		for (DocumentoXNegociacion documentoXNegociacion : documentoXNegociacions) {
			documentoXNegociacionDAO.update(documentoXNegociacion);
		}

		List<DocumentoXLotesoic> documentoXLotesoics = documento
				.getDocumentoXLotesoics();
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
	public List<Documento> consultarFacturasDeExportacionFiltro(
			Documento documento) {
		try {
			return documentoDAO.consultarDocumentosFacturaExportacion(documento
					.getConsecutivoDocumento());
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

	public List<AutorizarDocumentoDTO> consultarDocumentosAutorizar(
			String consecutivoDocumento) {
		try {
			return documentoDAO
					.consultarDocumentosAutorizar(consecutivoDocumento);
		} catch (Exception e) {
			LOGGER.error(e + "Error consultando facturas a autorizar");
		}
		return null;
	}

	public void cambiarEstadoFacturaProforma(List<AutorizarDocumentoDTO> listado) {
		try {
			documentoDAO.cambiarEstadoFacturaProforma(listado);
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
	public List<Documento> consultarSolicitudesPedidoPorAnular(
			String consecutivoDocumento) {
		LOGGER.debug("Metodo: <<consultarSolicitudesPedidoPorAnular>> parametros / consecutivoDocumento ->> {"
				+ consecutivoDocumento + "} ");
		return documentoDAO
				.consultarSolicitudesPedidoPorAnular(consecutivoDocumento);
	}

	@Override
	public void anularSolicitudPedido(Documento documento) {
		LOGGER.debug("Metodo: <<anularSolicitudPedido>> parametros / documento ->> {"
				+ documento + "} ");
		documentoDAO.anularSolicitudPedido(documento);
	}

	@Override
	public List<Cliente> listadoClientesInstruccionEmbarque(String idUsuario) {
		return clienteDao.consultarActivosPorUsuario(idUsuario);
	}

	@Override
	public List<DocumentoInstruccionEmbarqueDTO> listadoDocumentosInstruccionEmbarque(
			Long idCliente) {
		return documentoDAO.consultarDocumentosInstruccionEmbarque(idCliente);
	}

	@Override
	public List<DocumentoPorLotesInstruccionEmbarqueDTO> consultarDocumentosPorLotes(
			String strDocs, String strDocsMerca) {
		return documentoXLoteDAO.consultarDocumentosPorLotes(strDocs,
				strDocsMerca);
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
	public TerminosTransporte updateTerminoTransporte(
			TerminosTransporte terminosTransporte) {

		terminosTransporte.setModalidadEmbarque(modalidadEmbarqueDAO
				.findByPK(terminosTransporte.getModalidadEmbarque().getId()));
		terminosTransporte.setTerminoIncoterm(terminoIncotermDAO
				.findByPK(terminosTransporte.getTerminoIncoterm().getId()));
		terminosTransporte.setCiudade(ciudadDAO.findByPK(terminosTransporte
				.getCiudade().getId()));

		return terminosTransporteDAO.add(terminosTransporte);
	}

	@Override
	public String guardarInstruccionEmbarque(
			List<DocumentoInstruccionEmbarqueDTO> listadoDocumentos,
			TerminosTransporte terminosTransporte) {
		for (DocumentoInstruccionEmbarqueDTO dto : listadoDocumentos) {

			if (dto.isSeleccionado()) {

				terminosTransporteDAO.guardarTerminosTransporteDocumento(
						dto.getId(), terminosTransporte.getId());

				Documento documento = documentoDAO.findByPK(dto.getId());

				documento
						.setFechaEta(terminosTransporte.getFechaEmbarqueDate());

				documentoDAO.update(documento);

			}
		}
		return null;
	}

	public List<Documento> consultarFacturasProformasParaGenerarListaEmpaque(
			String consecutivoDocumento) {
		return documentoDAO
				.consultarDocumentosParaGenerarListaEmpaque(consecutivoDocumento);
	}

	@Override
	public void generarListaEmpaque(Documento documento,
			DocumentoXNegociacion documentoXNegociacion,
			List<ProductoDTO> productoDTOs) {

		Documento listaEmpaque = crearListaEmpaque(documento);

		documentoXNegociacion.getPk().setIdDocumento(listaEmpaque.getId());
		documentoXNegociacion.setDocumento(listaEmpaque);
		documentoXNegociacion = documentoXNegociacionDAO
				.add(documentoXNegociacion);

		List<ProductosXDocumento> productosXDocumentosListaEmpaque = getProductosXDocumentosParaListaEmpaque(productoDTOs);
		for (ProductosXDocumento productosXDocumento : productosXDocumentosListaEmpaque) {
			productosXDocumento.getId().setIdDocumento(listaEmpaque.getId());
			productoXDocumentoDAO.add(productosXDocumento);
		}

		Documento ventaDirecta = crearVentaDirecta(listaEmpaque);
		List<ProductosXDocumento> productosXDocumentosVentaDirecta = getProductosXDocumentosParaVentaDirecta(productoDTOs);
		for (ProductosXDocumento productosXDocumento : productosXDocumentosVentaDirecta) {
			productosXDocumento.getId().setIdDocumento(ventaDirecta.getId());
			productoXDocumentoDAO.add(productosXDocumento);
		}

		Documento remision = crearRemision(ventaDirecta);
		List<ProductosXDocumento> productosXDocumentosRemision = getProductosXDocumentosParaRemision(productoDTOs);
		for (ProductosXDocumento productosXDocumento : productosXDocumentosRemision) {
			productosXDocumento.getId().setIdDocumento(remision.getId());
			productosXDocumento = productoXDocumentoDAO
					.add(productosXDocumento);

			crearMovimientos(remision, productosXDocumento);
		}

		listaEmpaque.setSitioEntrega(ventaDirecta.getConsecutivoDocumento()
				+ ";" + remision.getConsecutivoDocumento());
		documentoDAO.update(listaEmpaque);

		Documento solicitudPedido = documentoDAO
				.consultarDocumentoPorConsecutivo(documento
						.getObservacionDocumento());
		Estadosxdocumento estadosxdocumentoSP = solicitudPedido
				.getEstadosxdocumento();
		EstadosxdocumentoPK idEstDocSP = estadosxdocumentoSP.getId();
		idEstDocSP.setIdEstado(Estado.CERRADA.getCodigo());
		estadosxdocumentoSP.setId(idEstDocSP);
		solicitudPedido.setEstadosxdocumento(estadosxdocumentoSP);
		documentoDAO.update(solicitudPedido);

		Estadosxdocumento estadosxdocumento = documento.getEstadosxdocumento();
		EstadosxdocumentoPK idEstadoXDocumento = estadosxdocumento.getId();
		idEstadoXDocumento.setIdEstado(Estado.LISTADA.getCodigo());
		estadosxdocumento.setId(idEstadoXDocumento);
		documento.setEstadosxdocumento(estadosxdocumento);
		documentoDAO.update(documento);

	}

	private void crearMovimientos(Documento remision,
			ProductosXDocumento productosXDocumento) {
		crearMovimientoSalida(remision, productosXDocumento);

		crearMovimientoEntrada(remision, productosXDocumento);
	}

	private void crearMovimientoEntrada(Documento remision,
			ProductosXDocumento productosXDocumento) {
		MovimientosInventario movimientosInventarioEntrada = new MovimientosInventario();

		movimientosInventarioEntrada.setMoneda(productosXDocumento.getMoneda());
		ProductosInventario productosInventario = new ProductosInventario();
		productosInventario.setId(productosXDocumento.getId().getIdProducto());
		movimientosInventarioEntrada
				.setProductosInventario(productosInventario);
		movimientosInventarioEntrada.setUnidade(productosXDocumento
				.getUnidade());
		movimientosInventarioEntrada.setCantidad(productosXDocumento
				.getCantidad1());
		movimientosInventarioEntrada.setFecha(new Timestamp(productosXDocumento
				.getFechaEntrega().getTime()));

		movimientosInventarioEntrada.setUbicacionOrigen(remision
				.getUbicacionOrigen());
		Ubicacion ubicacionDestino = new Ubicacion(
				com.ssl.jv.gip.util.Ubicacion.TRANSITO.getCodigo());
		movimientosInventarioEntrada.setUbicacionDestino(ubicacionDestino);

		BodegasLogica bodegasLogica = new BodegasLogica(
				BodegaLogica.DEFAULT.getCodigo());
		movimientosInventarioEntrada.setBodegasLogica1(bodegasLogica);
		movimientosInventarioEntrada.setBodegasLogica2(bodegasLogica);

		com.ssl.jv.gip.jpa.pojo.TipoMovimiento tipoMovimiento = new com.ssl.jv.gip.jpa.pojo.TipoMovimiento();
		tipoMovimiento.setId(TipoMovimiento.ENTRADA.getCodigo());
		movimientosInventarioEntrada.setTipoMovimiento(tipoMovimiento);

		movimientosInventarioEntrada.setDocumento(remision);

		movimientoInventarioDAO.add(movimientosInventarioEntrada);
	}

	private void crearMovimientoSalida(Documento remision,
			ProductosXDocumento productosXDocumento) {
		MovimientosInventario movimientosInventarioSalida = new MovimientosInventario();

		movimientosInventarioSalida.setMoneda(productosXDocumento.getMoneda());
		ProductosInventario productosInventario = new ProductosInventario();
		productosInventario.setId(productosXDocumento.getId().getIdProducto());
		movimientosInventarioSalida.setProductosInventario(productosInventario);
		movimientosInventarioSalida
				.setUnidade(productosXDocumento.getUnidade());
		movimientosInventarioSalida.setCantidad(productosXDocumento
				.getCantidad1());
		movimientosInventarioSalida.setFecha(new Timestamp(productosXDocumento
				.getFechaEntrega().getTime()));

		movimientosInventarioSalida.setUbicacionOrigen(remision
				.getUbicacionOrigen());
		Ubicacion ubicacionDestino = new Ubicacion(
				com.ssl.jv.gip.util.Ubicacion.TRANSITO.getCodigo());
		movimientosInventarioSalida.setUbicacionDestino(ubicacionDestino);

		BodegasLogica bodegasLogica = new BodegasLogica(
				BodegaLogica.DEFAULT.getCodigo());
		movimientosInventarioSalida.setBodegasLogica1(bodegasLogica);
		movimientosInventarioSalida.setBodegasLogica2(bodegasLogica);

		com.ssl.jv.gip.jpa.pojo.TipoMovimiento tipoMovimiento = new com.ssl.jv.gip.jpa.pojo.TipoMovimiento();
		tipoMovimiento.setId(TipoMovimiento.SALIDA.getCodigo());
		movimientosInventarioSalida.setTipoMovimiento(tipoMovimiento);

		movimientosInventarioSalida.setDocumento(remision);

		movimientoInventarioDAO.add(movimientosInventarioSalida);
	}

	private List<ProductosXDocumento> getProductosXDocumentosParaRemision(
			List<ProductoDTO> productoDTOs) {
		List<ProductosXDocumento> productosXDocumentos = new ArrayList<ProductosXDocumento>();

		Calendar calendar = Calendar.getInstance();
		for (ProductoDTO productoDTO : productoDTOs) {
			productosXDocumentos.add(getProductoXDocumentoParaRemision(
					productoDTO, calendar));
		}
		return productosXDocumentos;
	}

	private ProductosXDocumento getProductoXDocumentoParaRemision(
			ProductoDTO productoDTO, Calendar calendar) {
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

		BodegasLogica bodegasLogica = new BodegasLogica(
				BodegaLogica.DEFAULT.getCodigo());
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

		remision.setIdPuntoVenta(Long.valueOf(0));

		remision.setDescuentoCliente(BigDecimal.ZERO);
		remision.setValorTotal(BigDecimal.ZERO);
		remision.setSubtotal(BigDecimal.ZERO);
		remision.setDescuento(BigDecimal.ZERO);
		remision.setValorIva10(BigDecimal.ZERO);
		remision.setValorIva16(BigDecimal.ZERO);
		remision.setValorIva5(BigDecimal.ZERO);

		remision.setNumeroFactura("0");

		Ubicacion ubicacionOrigen = new Ubicacion(
				com.ssl.jv.gip.util.Ubicacion.BODEGA_CENTRAL.getCodigo());
		remision.setUbicacionOrigen(ubicacionOrigen);
		remision.setUbicacionDestino(documento.getUbicacionDestino());

		TipoDocumento tipoDocumento = tipoDocumentoDAOLocal.findByPK(remision
				.getEstadosxdocumento().getId().getIdTipoDocumento());
		Ubicacion ubicacion = null;

		if (remision.getUbicacionOrigen() != null
				&& remision.getUbicacionDestino() != null
				&& com.ssl.jv.gip.util.Ubicacion.EXTERNA.getCodigo().equals(
						remision.getUbicacionDestino().getId())) {
			ubicacion = ubicacionDAOLocal.findByPK(remision
					.getUbicacionOrigen().getId());
		} else {
			if (remision.getUbicacionDestino() != null) {
				ubicacion = ubicacionDAOLocal.findByPK(remision
						.getUbicacionDestino().getId());
			} else {
				ubicacion = ubicacionDAOLocal
						.findByPK(com.ssl.jv.gip.util.Ubicacion.EXTERNA
								.getCodigo());
			}
		}

		String prefijoConsecutivo = tipoDocumento.getAbreviatura()
				+ ubicacion.getEmpresa().getId();
		Long valorSecuencia = documentoDAO
				.consultarProximoValorSecuencia(prefijoConsecutivo + "_seq");

		remision.setConsecutivoDocumento(prefijoConsecutivo + "-"
				+ valorSecuencia);

		remision = documentoDAO.add(remision);

		return remision;
	}

	private List<ProductosXDocumento> getProductosXDocumentosParaVentaDirecta(
			List<ProductoDTO> productoDTOs) {
		List<ProductosXDocumento> productosXDocumentos = new ArrayList<ProductosXDocumento>();
		Calendar calendar = Calendar.getInstance();
		for (ProductoDTO productoDTO : productoDTOs) {
			productosXDocumentos.add(getProductoXDocumentoParaVentaDirecta(
					productoDTO, calendar));
		}
		return productosXDocumentos;
	}

	private ProductosXDocumento getProductoXDocumentoParaVentaDirecta(
			ProductoDTO productoDTO, Calendar calendar) {
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

		BodegasLogica bodegasLogica = new BodegasLogica(
				BodegaLogica.DEFAULT.getCodigo());
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

		Ubicacion ubicacionOrigen = new Ubicacion(
				com.ssl.jv.gip.util.Ubicacion.EXTERNA.getCodigo());
		ventaDirecta.setUbicacionOrigen(ubicacionOrigen);

		ventaDirecta.setUbicacionDestino(documento.getUbicacionOrigen());

		ventaDirecta.setObservacionDocumento(documento
				.getObservacionDocumento());

		ventaDirecta.setDocumentoCliente(documento.getDocumentoCliente());

		ventaDirecta.setCliente(documento.getCliente());

		ventaDirecta.setIdPuntoVenta(Long.valueOf(0));

		ventaDirecta.setNumeroFactura("0");

		ventaDirecta.setDescuentoCliente(BigDecimal.ZERO);
		ventaDirecta.setValorTotal(BigDecimal.ZERO);
		ventaDirecta.setSubtotal(BigDecimal.ZERO);
		ventaDirecta.setDescuento(BigDecimal.ZERO);
		ventaDirecta.setValorIva10(BigDecimal.ZERO);
		ventaDirecta.setValorIva16(BigDecimal.ZERO);
		ventaDirecta.setValorIva5(BigDecimal.ZERO);

		TipoDocumento tipoDocumento = tipoDocumentoDAOLocal
				.findByPK(ventaDirecta.getEstadosxdocumento().getId()
						.getIdTipoDocumento());
		Ubicacion ubicacion = null;
		if (ventaDirecta.getUbicacionOrigen() != null
				&& ventaDirecta.getUbicacionDestino() != null
				&& com.ssl.jv.gip.util.Ubicacion.EXTERNA.getCodigo().equals(
						ventaDirecta.getUbicacionDestino().getId())) {
			ubicacion = ubicacionDAOLocal.findByPK(ventaDirecta
					.getUbicacionOrigen().getId());
		} else {
			if (ventaDirecta.getUbicacionDestino() != null) {
				ubicacion = ubicacionDAOLocal.findByPK(ventaDirecta
						.getUbicacionDestino().getId());
			} else {
				ubicacion = ubicacionDAOLocal
						.findByPK(com.ssl.jv.gip.util.Ubicacion.EXTERNA
								.getCodigo());
			}
		}

		String prefijoConsecutivo = tipoDocumento.getAbreviatura()
				+ ubicacion.getEmpresa().getId();
		Long valorSecuencia = documentoDAO
				.consultarProximoValorSecuencia(prefijoConsecutivo + "_seq");

		ventaDirecta.setConsecutivoDocumento(prefijoConsecutivo + "-"
				+ valorSecuencia);

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
		listaEmpaque.setObservacionDocumento(documento
				.getConsecutivoDocumento());
		listaEmpaque.setCliente(documento.getCliente());
		listaEmpaque.setDocumentoCliente(documento.getDocumentoCliente());
		listaEmpaque.setFechaEsperadaEntrega(documento
				.getFechaEsperadaEntrega());
		listaEmpaque.setIdPuntoVenta(Long.valueOf(0));
		listaEmpaque.setNumeroFactura("0");
		listaEmpaque.setValorTotal(BigDecimal.ZERO);
		listaEmpaque.setDescuentoCliente(BigDecimal.ZERO);
		listaEmpaque.setSubtotal(BigDecimal.ZERO);
		listaEmpaque.setDescuento(BigDecimal.ZERO);
		listaEmpaque.setValorIva10(BigDecimal.ZERO);
		listaEmpaque.setValorIva16(BigDecimal.ZERO);
		listaEmpaque.setValorIva5(BigDecimal.ZERO);

		TipoDocumento tipoDocumento = tipoDocumentoDAOLocal
				.findByPK(listaEmpaque.getEstadosxdocumento().getId()
						.getIdTipoDocumento());
		Ubicacion ubicacion = null;
		if (listaEmpaque.getUbicacionOrigen() != null
				&& listaEmpaque.getUbicacionDestino() != null
				&& com.ssl.jv.gip.util.Ubicacion.EXTERNA.getCodigo().equals(
						listaEmpaque.getUbicacionDestino().getId())) {
			ubicacion = ubicacionDAOLocal.findByPK(listaEmpaque
					.getUbicacionOrigen().getId());
		} else {
			if (listaEmpaque.getUbicacionDestino() != null) {
				ubicacion = ubicacionDAOLocal.findByPK(listaEmpaque
						.getUbicacionDestino().getId());
			} else {
				ubicacion = ubicacionDAOLocal
						.findByPK(com.ssl.jv.gip.util.Ubicacion.EXTERNA
								.getCodigo());
			}
		}

		String prefijoConsecutivo = tipoDocumento.getAbreviatura()
				+ ubicacion.getEmpresa().getId();
		Long valorSecuencia = documentoDAO
				.consultarProximoValorSecuencia(prefijoConsecutivo + "_seq");
		listaEmpaque.setConsecutivoDocumento(prefijoConsecutivo + "-"
				+ valorSecuencia);

		listaEmpaque.getEstadosxdocumento().setEstado(
				estadoDAOLocal
						.findByPK(estadosxdocumento.getId().getIdEstado()));

		listaEmpaque = (Documento) documentoDAO.add(listaEmpaque);

		return listaEmpaque;
	}

	private List<ProductosXDocumento> getProductosXDocumentosParaListaEmpaque(
			List<ProductoDTO> productoDTOs) {
		List<ProductosXDocumento> productosXDocumentos = new ArrayList<ProductosXDocumento>();
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
		productosXDocumento.setValorUnitarioUsd(productoDTO
				.getValorUnitarioUsd());
		productosXDocumento.setValorTotal(productoDTO.getValorTotal());
		com.ssl.jv.gip.jpa.pojo.Unidad unidade = new com.ssl.jv.gip.jpa.pojo.Unidad();
		unidade.setId(productoDTO.getIdUnidadVenta());
		productosXDocumento.setUnidade(unidade);
		com.ssl.jv.gip.jpa.pojo.Moneda moneda = new com.ssl.jv.gip.jpa.pojo.Moneda();
		moneda.setId(Moneda.USD.name());
		productosXDocumento.setMoneda(moneda);
		productosXDocumento.setTotalPesoBrutoItem(productoDTO.getPesoBruto());
		productosXDocumento.setTotalPesoNetoItem(productoDTO.getPesoNeto());
		productosXDocumento
				.setCantidadCajasItem(productoDTO.getCantidadCajas());
		productosXDocumento.setCantidadPalletsItem(productoDTO
				.getCantidadPallets());
		productosXDocumento.setCantidadXEmbalaje(productoDTO
				.getCantidadPorEmbalaje());

		productosXDocumento.setDescuentoxproducto(BigDecimal.ZERO);

		return productosXDocumento;
	}
}
