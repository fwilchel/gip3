package com.ssl.jv.gip.negocio.ejb;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import com.ssl.jv.gip.jpa.pojo.Documento;
import com.ssl.jv.gip.jpa.pojo.DocumentoXLotesoic;
import com.ssl.jv.gip.jpa.pojo.DocumentoXNegociacion;
import com.ssl.jv.gip.jpa.pojo.LogAuditoria;
import com.ssl.jv.gip.jpa.pojo.MovimientosInventarioComext;
import com.ssl.jv.gip.jpa.pojo.ProductosInventario;
import com.ssl.jv.gip.jpa.pojo.ProductosXClienteComext;
import com.ssl.jv.gip.jpa.pojo.ProductosXDocumento;
import com.ssl.jv.gip.jpa.pojo.TerminoIncoterm;
import com.ssl.jv.gip.jpa.pojo.Ubicacion;
import com.ssl.jv.gip.negocio.dao.DocumentoDAOLocal;
import com.ssl.jv.gip.negocio.dao.DocumentoLotesOICDAOLocal;
import com.ssl.jv.gip.negocio.dao.DocumentoXLoteDAOLocal;
import com.ssl.jv.gip.negocio.dao.DocumentoXNegociacionDAOLocal;
import com.ssl.jv.gip.negocio.dao.LogAuditoriaDAOLocal;
import com.ssl.jv.gip.negocio.dao.MovimientosInventarioComextDAOLocal;
import com.ssl.jv.gip.negocio.dao.ProductoClienteComercioExteriorDAOLocal;
import com.ssl.jv.gip.negocio.dao.ProductoInventarioDAOLocal;
import com.ssl.jv.gip.negocio.dao.ProductosXDocumentoDAOLocal;
import com.ssl.jv.gip.negocio.dao.TerminoIncotermDAOLocal;
import com.ssl.jv.gip.negocio.dao.UbicacionDAOLocal;
import com.ssl.jv.gip.negocio.dto.DatoContribucionCafeteraDTO;
import com.ssl.jv.gip.negocio.dto.DocumentoIncontermDTO;
import com.ssl.jv.gip.negocio.dto.DocumentoLotesContribucionCafeteriaDTO;
import com.ssl.jv.gip.negocio.dto.ListaEmpaqueDTO;
import com.ssl.jv.gip.negocio.dto.ProductoAsignarLoteOICDTO;
import com.ssl.jv.gip.negocio.dto.ProductoDTO;
import com.ssl.jv.gip.negocio.dto.ProductoGenerarFacturaPFDTO;
import com.ssl.jv.gip.negocio.dto.ProductoImprimirLEDTO;
import com.ssl.jv.gip.negocio.dto.ProductoLoteAsignarLoteOICDTO;
import com.ssl.jv.gip.negocio.dto.ProductoPorClienteComExtDTO;
import com.ssl.jv.gip.util.Estado;
import com.ssl.jv.gip.web.mb.util.ConstantesDocumento;
import com.ssl.jv.gip.web.mb.util.ConstantesTipoDocumento;

/**
 * Session Bean implementation class ComercioExterior.
 */
@Stateless
@LocalBean
public class ComercioExteriorEJB implements ComercioExteriorEJBLocal {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = Logger
			.getLogger(ComercioExteriorEJB.class);

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
	public List<Documento> consultarDocumento(Map<String, Object> parametros) {
		// TODO Auto-generated method stub
		return documentoDAO.consultarDocumento(parametros);
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

}
