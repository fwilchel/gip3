package com.ssl.jv.gip.negocio.dao;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import com.ssl.jv.gip.jpa.pojo.Documento;
import com.ssl.jv.gip.negocio.dto.ClienteDTO;
import com.ssl.jv.gip.negocio.dto.DatoContribucionCafeteraDTO;
import com.ssl.jv.gip.negocio.dto.DocumentoIncontermDTO;
import com.ssl.jv.gip.negocio.dto.FacturaDirectaDTO;
import com.ssl.jv.gip.negocio.dto.FiltroDocumentoDTO;
import com.ssl.jv.gip.negocio.dto.ListaEmpaqueDTO;
import com.ssl.jv.gip.negocio.dto.ProductoImprimirLEDTO;
import com.ssl.jv.gip.negocio.dto.UbicacionDTO;
import com.ssl.jv.gip.web.mb.util.ConstantesDocumento;
import com.ssl.jv.gip.web.mb.util.ConstantesTipoDocumento;

@Stateless
@LocalBean
public class DocumentoDAO extends GenericDAO<Documento> implements
		DocumentoDAOLocal {

	private static final Logger LOGGER = Logger.getLogger(DocumentoDAO.class);

	public DocumentoDAO() {
		this.persistentClass = Documento.class;
	}

	public List<DatoContribucionCafeteraDTO> consultarDatosContribucionCafetera(
			Map<String, Object> parametros) {

		List<DatoContribucionCafeteraDTO> lista = new ArrayList<DatoContribucionCafeteraDTO>();

		String sql = "";

		int tipo = (Integer) parametros.get("tipo");
		int estado = (Integer) parametros.get("estado");
		sql = "select distinct(doc_lot.id_documento) as doc_lot,docFX.id , docFX.consecutivo_documento , docFX.fecha_generacion"
				+ " from documentos docFX "
				+ " inner join documentos docLE on docFX.observacion_documento=docLE.consecutivo_documento "
				+ " inner join documentos docFP on docLE.observacion_documento=docFP.consecutivo_documento "
				+ " left outer join documento_x_lotesoic  doc_lot on docFP.id=doc_lot.id_documento "
				+ " where docFX.id_tipo_documento= "
				+ tipo
				+ " AND docFX.id_estado = "
				+ estado
				+ " ORDER BY docFX.id DESC";

		List<Object[]> listado = em.createNativeQuery(sql).getResultList();

		if (listado != null) {
			for (Object[] objs : listado) {
				DatoContribucionCafeteraDTO dto = new DatoContribucionCafeteraDTO();

				dto.setIdDocumento(objs[0] != null ? Long.parseLong(objs[0]
						.toString()) : null);
				dto.setIdDocumentoFX(objs[1] != null ? Long.parseLong(objs[1]
						.toString()) : null);
				dto.setConsecutivo(objs[2] != null ? objs[2].toString() : null);
				dto.setFechaGeneracion(objs[3] != null ? (Date) objs[3] : null);

				lista.add(dto);
			}
		}

		return lista;

	}

	/**
	 * Consultar documentos costos inconterm.
	 *
	 * @return the list
	 */
	public List<DocumentoIncontermDTO> consultarDocumentosCostosInconterm() {

		List<DocumentoIncontermDTO> lista = new ArrayList<DocumentoIncontermDTO>();

		String sql = "SELECT documentos.id iddocumento, 	"
				+ "documentos.consecutivo_documento, 	"
				+ "documentos.fecha_esperada_entrega, 	"
				+ "documentos.id_ubicacion_origen, 	"
				+ "documentos.id_ubicacion_destino, 	"
				+ "documentos.id_tipo_documento, 	"
				+ "documentos.fecha_generacion, 	"
				+ "documentos.fecha_entrega, 	"
				+ "documentos.id_proveedor, 	"
				+ "documentos.id_estado, 	"
				+ "documentos.documento_cliente, 	"
				+ "documentos.id_cliente, 	"
				+ "clientes.id idcliente, 	"
				+ "clientes.nombre nombrecliente, 	"
				+ "clientes.direccion, 	"
				+ "clientes.telefono, 	"
				+ "clientes.contacto,  	"
				+ "Documento_x_Negociacion.id_termino_incoterm, 	"
				+ "termino_incoterm.descripcion, 	"
				+ "documentos.valor_total, 	"
				+ "Documento_x_Negociacion.costo_entrega, 	"
				+ "Documento_x_Negociacion.costo_flete, 	"
				+ "Documento_x_Negociacion.costo_seguro, 	"
				+ "Documento_x_Negociacion.otros_gastos, 	"
				+ "Documento_x_Negociacion.cantidad_contenedores_de_20, 	"
				+ "Documento_x_Negociacion.cantidad_contenedores_de_40, 	"
				+ "ciudades.nombre nombrecuidad, 	"
				+ "Documento_x_Negociacion.lugar_incoterm, 	"
				+ "estados.nombre nombreestados, 	"
				+ "Documento_x_Negociacion.solicitud_cafe, 	"
				+ "documentos.observacion_documento, 	"
				+ "Documento_x_Negociacion.observaciones_marcacion_2   "
				+ "FROM documentos,clientes,Documento_x_Negociacion,termino_incoterm,ciudades,estados   "
				+ "WHERE documentos.id_cliente = clientes.id  "
				+ "AND documentos.id=Documento_x_Negociacion.id_documento   "
				+ "AND Documento_x_Negociacion.id_termino_incoterm=termino_incoterm.id   "
				+ "AND clientes.id_ciudad=ciudades.id  	"
				+ "AND documentos.id_estado=estados.id  "
				+ "AND documentos.id_tipo_documento=22  "
				+ "AND documentos.id_estado IN (15,14)  "
				+ "AND documentos.consecutivo_documento not in (select observacion_documento from documentos where observacion_documento  in (SELECT consecutivo_documento from documentos where  id_tipo_documento= 22  and id_estado IN (15,14) ))   "
				+ "ORDER BY documentos.id DESC ;";

		List<Object[]> listado = em.createNativeQuery(sql).getResultList();

		if (listado != null) {
			for (Object[] objs : listado) {
				DocumentoIncontermDTO dto = new DocumentoIncontermDTO();

				dto.setIdDocumento(objs[0] != null ? Long.parseLong(objs[0]
						.toString()) : null);
				dto.setConsecutivoDocumento(objs[1] != null ? objs[1]
						.toString() : null);
				dto.setFechaEsperadaEntrega((Timestamp) (objs[2] != null ? objs[2]
						: null));
				dto.setIdUbicacionOrigen(objs[3] != null ? Long
						.parseLong(objs[3].toString()) : null);
				dto.setIdUbicacionDestino(objs[4] != null ? Long
						.parseLong(objs[4].toString()) : null);
				dto.setIdTipoDocumento(objs[5] != null ? Long.parseLong(objs[5]
						.toString()) : null);
				dto.setFechaGeneracion((Timestamp) (objs[6] != null ? objs[6]
						: null));
				dto.setFechaEntrega((Timestamp) (objs[7] != null ? objs[7]
						: null));
				dto.setIdProveedor(objs[8] != null ? Long.parseLong(objs[8]
						.toString()) : null);
				dto.setIdEstado(objs[9] != null ? Long.parseLong(objs[9]
						.toString()) : null);
				dto.setDocumentoCliente(objs[10] != null ? objs[10].toString()
						: null);
				dto.setIdCliente(objs[11] != null ? Long.parseLong(objs[11]
						.toString()) : null);
				dto.setClientesId(objs[12] != null ? Long.parseLong(objs[12]
						.toString()) : null);
				dto.setClientesNombre(objs[13] != null ? objs[13].toString()
						: null);
				dto.setClientesDireccion(objs[14] != null ? objs[14].toString()
						: null);
				dto.setClientesTelefono(objs[15] != null ? objs[15].toString()
						: null);
				dto.setClientesContacto(objs[16] != null ? objs[16].toString()
						: null);
				dto.setIdTerminoIncoterm(objs[17] != null ? Long
						.parseLong(objs[17].toString()) : null);
				dto.setDescripcionTerminoIncoterm(objs[18] != null ? objs[18]
						.toString() : null);
				dto.setValorTotalDocumento(objs[19] != null ? new BigDecimal(
						objs[19].toString()) : null);
				dto.setCostoEntrega(objs[20] != null ? new BigDecimal(objs[20]
						.toString()) : null);
				dto.setCostoFlete(objs[21] != null ? new BigDecimal(objs[21]
						.toString()) : null);
				dto.setCostoSeguro(objs[22] != null ? new BigDecimal(objs[22]
						.toString()) : null);
				dto.setOtrosGastos(objs[23] != null ? new BigDecimal(objs[23]
						.toString()) : null);
				dto.setCantidadContenedores20(objs[24] != null ? new BigDecimal(
						objs[24].toString()) : null);
				dto.setCantidadContenedores40(objs[25] != null ? new BigDecimal(
						objs[25].toString()) : null);
				dto.setCiudadNombre(objs[26] != null ? objs[26].toString()
						: null);
				dto.setLugarIncoterm(objs[27] != null ? objs[27].toString()
						: null);
				dto.setEstadoNombre(objs[28] != null ? objs[28].toString()
						: null);
				dto.setSolicitudCafe((Boolean) (objs[29] != null ? objs[29]
						: null));
				dto.setObservacionDocumento(objs[30] != null ? objs[30]
						.toString() : null);
				dto.setObservacionesMarcacion2(objs[31] != null ? objs[31]
						.toString() : null);

				lista.add(dto);
			}
		}

		return lista;

	}

	/**
	 * Actualiza los documento por negociacion.
	 *
	 */
	public void actualizarDocumentoPorNegociacion(
			DocumentoIncontermDTO documento) {
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE Documento_x_Negociacion SET "
					+ " costo_entrega = " + documento.getCostoEntrega()
					+ " , costo_flete = " + documento.getCostoFlete()
					+ " , costo_seguro = " + documento.getCostoSeguro()
					+ " , otros_gastos = " + documento.getOtrosGastos());
			if (documento.getTotalPesoNeto() != null) {
				sql.append(" , total_peso_neto = "
						+ documento.getTotalPesoNeto());
			}
			if (documento.getTotalPesoBruto() != null) {
				sql.append(" , total_peso_bruto = "
						+ documento.getTotalPesoBruto());
			}
			if (documento.getTotalTendidos() != null) {
				sql.append(" , total_tendidos = "
						+ documento.getTotalTendidos());
			}
			if (documento.getTotalPallets() != null) {
				sql.append(" , total_pallets = " + documento.getTotalPallets());
			}
			if (documento.getLugarIncoterm() != null && !documento.getLugarIncoterm().isEmpty()) {
				sql.append(" , lugar_incoterm = " + "'" + documento.getLugarIncoterm()+ "'");
			}
			if (documento.getCantidadContenedores20() != null) {
				sql.append(" , cantidad_contenedores_de_20 = " + documento.getCantidadContenedores20());
			}
			if (documento.getCantidadContenedores40() != null) {
				sql.append(" , cantidad_contenedores_de_40 = " + documento.getCantidadContenedores40());
			}
			if (documento.getIdTerminoIncoterm() != null) {
				sql.append(" , id_termino_incoterm = " + documento.getIdTerminoIncoterm());
			}
			

			sql.append(" WHERE  id_documento = " + documento.getIdDocumento());

			int q = em.createNativeQuery(sql.toString()).executeUpdate();

		} catch (Exception e) {

		}
	}
	
	/**
	 * Actualiza el estado del documento.
	 *
	 */
	public void actualizarEstadoDocumento(
			DocumentoIncontermDTO documento) {
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE documentos SET "
					+ " id_estado = " + documento.getIdEstado()
					+ " WHERE  id = " + documento.getIdDocumento());

			int q = em.createNativeQuery(sql.toString()).executeUpdate();

		} catch (Exception e) {

		}
	}
	
	/**
	 * Actualiza el estado del documento por consecutivo.
	 *
	 */
	public void actualizarEstadoDocumentoPorConsecutivo(
			DocumentoIncontermDTO documento) {
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE documentos SET "
					+ " id_estado = " + documento.getIdEstado()
					+ " WHERE  consecutivo_documento = " + documento.getConsecutivoDocumento());

			int q = em.createNativeQuery(sql.toString()).executeUpdate();

		} catch (Exception e) {

		}
	}

	/**
	 * Consultar documentos solicitud pedido.
	 *
	 * @return the list
	 */
	public List<DocumentoIncontermDTO> consultarDocumentosSolicitudPedido() {

		List<DocumentoIncontermDTO> lista = new ArrayList<DocumentoIncontermDTO>();

		String sql = "SELECT documentos.id iddocumento, 	"
				+ "documentos.consecutivo_documento, 	"
				+ "documentos.fecha_esperada_entrega, 	"
				+ "documentos.id_ubicacion_origen, 	"
				+ "documentos.id_ubicacion_destino, 	"
				+ "documentos.id_tipo_documento, 	"
				+ "documentos.fecha_generacion, 	"
				+ "documentos.fecha_entrega, 	"
				+ "documentos.id_proveedor, 	"
				+ "documentos.id_estado, 	"
				+ "documentos.documento_cliente, 	"
				+ "documentos.id_cliente, 	"
				+ "clientes.id idcliente, 	"
				+ "clientes.nombre nombrecliente, 	"
				+ "clientes.direccion, 	"
				+ "clientes.telefono, 	"
				+ "clientes.contacto,  	"
				+ "Documento_x_Negociacion.id_termino_incoterm, 	"
				+ "termino_incoterm.descripcion, 	"
				+ "documentos.valor_total, 	"
				+ "Documento_x_Negociacion.costo_entrega, 	"
				+ "Documento_x_Negociacion.costo_flete, 	"
				+ "Documento_x_Negociacion.costo_seguro, 	"
				+ "Documento_x_Negociacion.otros_gastos, 	"
				+ "Documento_x_Negociacion.cantidad_contenedores_de_20, 	"
				+ "Documento_x_Negociacion.cantidad_contenedores_de_40, 	"
				+ "ciudades.nombre nombrecuidad, 	"
				+ "Documento_x_Negociacion.lugar_incoterm, 	"
				+ "estados.nombre nombreestados, 	"
				+ "Documento_x_Negociacion.solicitud_cafe, 	"
				+ "documentos.observacion_documento, 	"
				+ "Documento_x_Negociacion.observaciones_marcacion_2   "
				+ "FROM documentos,clientes,Documento_x_Negociacion,termino_incoterm,ciudades,estados   "
				+ "WHERE documentos.id_cliente = clientes.id  "
				+ "AND documentos.id=Documento_x_Negociacion.id_documento   "
				+ "AND Documento_x_Negociacion.id_termino_incoterm=termino_incoterm.id   "
				+ "AND clientes.id_ciudad=ciudades.id  	"
				+ "AND documentos.id_estado=estados.id  "
				+ "AND documentos.id_tipo_documento=22  "
				+ "AND documentos.id_estado IN (1,14)  "
				+ "ORDER BY documentos.id DESC ;";

		List<Object[]> listado = em.createNativeQuery(sql).getResultList();

		if (listado != null) {
			for (Object[] objs : listado) {
				DocumentoIncontermDTO dto = new DocumentoIncontermDTO();

				dto.setIdDocumento(objs[0] != null ? Long.parseLong(objs[0]
						.toString()) : null);
				dto.setConsecutivoDocumento(objs[1] != null ? objs[1]
						.toString() : null);
				dto.setFechaEsperadaEntrega((Timestamp) (objs[2] != null ? objs[2]
						: null));
				if (dto.getFechaEsperadaEntrega() != null) {
					dto.setFechaEsperadaEntregaDate(new java.sql.Date(dto
							.getFechaEsperadaEntrega().getTime()));
				}

				dto.setIdUbicacionOrigen(objs[3] != null ? Long
						.parseLong(objs[3].toString()) : null);
				dto.setIdUbicacionDestino(objs[4] != null ? Long
						.parseLong(objs[4].toString()) : null);
				dto.setIdTipoDocumento(objs[5] != null ? Long.parseLong(objs[5]
						.toString()) : null);
				dto.setFechaGeneracion((Timestamp) (objs[6] != null ? objs[6]
						: null));
				dto.setFechaEntrega((Timestamp) (objs[7] != null ? objs[7]
						: null));
				dto.setIdProveedor(objs[8] != null ? Long.parseLong(objs[8]
						.toString()) : null);
				dto.setIdEstado(objs[9] != null ? Long.parseLong(objs[9]
						.toString()) : null);
				dto.setDocumentoCliente(objs[10] != null ? objs[10].toString()
						: null);
				dto.setIdCliente(objs[11] != null ? Long.parseLong(objs[11]
						.toString()) : null);
				dto.setClientesId(objs[12] != null ? Long.parseLong(objs[12]
						.toString()) : null);
				dto.setClientesNombre(objs[13] != null ? objs[13].toString()
						: null);
				dto.setClientesDireccion(objs[14] != null ? objs[14].toString()
						: null);
				dto.setClientesTelefono(objs[15] != null ? objs[15].toString()
						: null);
				dto.setClientesContacto(objs[16] != null ? objs[16].toString()
						: null);
				dto.setIdTerminoIncoterm(objs[17] != null ? Long
						.parseLong(objs[17].toString()) : null);
				dto.setDescripcionTerminoIncoterm(objs[18] != null ? objs[18]
						.toString() : null);
				dto.setValorTotalDocumento(objs[19] != null ? new BigDecimal(
						objs[19].toString()) : null);
				dto.setCostoEntrega(objs[20] != null ? new BigDecimal(objs[20]
						.toString()) : null);
				dto.setCostoFlete(objs[21] != null ? new BigDecimal(objs[21]
						.toString()) : null);
				dto.setCostoSeguro(objs[22] != null ? new BigDecimal(objs[22]
						.toString()) : null);
				dto.setOtrosGastos(objs[23] != null ? new BigDecimal(objs[23]
						.toString()) : null);
				dto.setCantidadContenedores20(objs[24] != null ? new BigDecimal(
						objs[24].toString()) : null);
				dto.setCantidadContenedores40(objs[25] != null ? new BigDecimal(
						objs[25].toString()) : null);
				dto.setCiudadNombre(objs[26] != null ? objs[26].toString()
						: null);
				dto.setLugarIncoterm(objs[27] != null ? objs[27].toString()
						: null);
				dto.setEstadoNombre(objs[28] != null ? objs[28].toString()
						: null);
				dto.setSolicitudCafe((Boolean) (objs[29] != null ? objs[29]
						: null));
				dto.setObservacionDocumento(objs[30] != null ? objs[30]
						.toString() : null);
				dto.setObservacionesMarcacion2(objs[31] != null ? objs[31]
						.toString() : null);

				lista.add(dto);
			}
		}

		return lista;

	}

	/**
	 * Consulta de Documentos tipo Solicitud Pedido, para el CU Generar Facturas
	 * Proforma
	 */

	public List<Documento> consultarDocumentosSolicitudPedido(
			String consecutivoDocumento) {

		List<Documento> listado = new ArrayList<Documento>();
		String query;
		try {
			query = "SELECT d FROM Documento d "
					+ "JOIN FETCH d.cliente c "
					+ "JOIN FETCH d.estadosxdocumento exd "
					+ "JOIN FETCH exd.estado e "
					+ "JOIN FETCH d.documentoXNegociacions dxn "
					+ "JOIN FETCH dxn.terminoIncoterm ti "
					+ "JOIN FETCH c.ciudad ciu "
					+ "JOIN FETCH c.metodoPago mp "
					+ "WHERE d.estadosxdocumento.id.idTipoDocumento = :tipoDocumento AND e.id IN (:estado1, :estado2) "
					+ " AND d.consecutivoDocumento NOT IN (SELECT d2.observacionDocumento FROM Documento d2 WHERE d2.observacionDocumento IN (SELECT d3.consecutivoDocumento FROM Documento d3 WHERE d3.estadosxdocumento.id.idTipoDocumento = :tipoDocumento AND d3.estadosxdocumento.estado.id IN (:estado1, :estado2)))"
					+ " AND UPPER(d.consecutivoDocumento) LIKE UPPER(:consecutivo) "
					+ " ORDER BY d.id DESC";

			listado = em
					.createQuery(query)
					.setParameter("tipoDocumento",
							(long) ConstantesTipoDocumento.SOLICITUD_PEDIDO)
					.setParameter("estado1",
							(long) ConstantesDocumento.VERIFICADO)
					.setParameter("estado2",
							(long) ConstantesDocumento.APROBADA)
					.setParameter(
							"consecutivo",
							consecutivoDocumento.equals("") ? "%" : "%"
									+ consecutivoDocumento + "%")
					.getResultList();
		} catch (Exception e) {
			LOGGER.error(e
					+ "********Error consultando Documentos por Consecutivo de Pedido");
			return null;
		}
		return listado;
	}

	/**
	 * Consulta de Documentos tipo Factura Proforma, para el CU Asignar Lotes
	 * OCI
	 */
	public List<Documento> consultarDocumentosFacturaPF(
			String consecutivoDocumento) {

		List<Documento> listado = new ArrayList<Documento>();
		String query;
		try {
			query = "SELECT d FROM Documento d "
					+ "JOIN FETCH d.cliente c "
					+ "JOIN FETCH d.estadosxdocumento exd "
					+ "JOIN FETCH exd.estado e "
					+ "JOIN FETCH d.documentoXNegociacions dxn "
					+ "JOIN FETCH dxn.terminoIncoterm ti "
					+ "JOIN FETCH c.ciudad ciu "
					+ "JOIN FETCH c.metodoPago mp "
					+ "WHERE d.estadosxdocumento.id.idTipoDocumento = :tipoDocumento AND e.id= :estado "
					+ " AND UPPER(d.consecutivoDocumento) LIKE UPPER(:consecutivo) "
					+ " AND dxn.solicitudCafe = :solicitudCafe "
					+ " ORDER BY d.id DESC";

			listado = em
					.createQuery(query)
					.setParameter("tipoDocumento",
							(long) ConstantesTipoDocumento.FACTURA_PROFORMA)
					.setParameter("estado", (long) ConstantesDocumento.APROBADA)
					.setParameter("solicitudCafe", true)
					.setParameter(
							"consecutivo",
							consecutivoDocumento.equals("") ? "%" : "%"
									+ consecutivoDocumento + "%")
					.getResultList();
		} catch (Exception e) {
			LOGGER.error(e
					+ "********Error consultando Documentos por Consecutivo de Pedido");
			return null;
		}
		return listado;

	}

	/**
	 * Consulta de todas las ordenes de despacho con opci�n de filtro
	 * 
	 * @return Lista de documentos de tipo ordenes de despacho
	 */
	@Override
	public List<ListaEmpaqueDTO> consultarDocumentoPorFacturaProforma(
			String consecutivoFacturaProforma) {
		List<ListaEmpaqueDTO> lista = new ArrayList<ListaEmpaqueDTO>();
		try {
			String sql = "SELECT  documentos.id, documentos.consecutivo_documento,documentos.fecha_generacion,"
					+ " documentos.documento_cliente, documentos.id_cliente as doc_id,"
					+ " documentos.id_tipo_documento, documentos.id_estado,"
					+ " cli.nombre AS NOMBRE_CLIENTE, cli.id AS ID_CLIENTE,"
					+ " estados.nombre AS NOMBRE_ESTADO, cli.direccion, cli.telefono,"
					+ " cli.contacto, inc.id AS ID_INCOTERM, inc.descripcion AS NOMBRE_INCOTERM,"
					+ " doc.costo_entrega, doc.costo_seguro, doc.costo_flete, doc.otros_gastos, doc.observaciones_marcacion_2, doc.total_peso_neto, doc.total_peso_bruto,"
					+ " doc.total_tendidos, doc.total_pallets, documentos.fecha_esperada_entrega,"
					+ " doc.cantidad_contenedores_de_20, doc.cantidad_contenedores_de_40, doc.lugar_incoterm,"
					+ " ciu.nombre AS NOMBRE_CIUDAD, doc.cantidad_dias_vigencia, metodo_pago.descripcion AS DESCRIPCION_PAGO,"
					+ " documentos.observacion_documento, metodo_pago.descripcion_ingles ,documentos.fecha_entrega,documentos.sitio_entrega,doc.solicitud_cafe,"
					+ " documentos.id_ubicacion_origen,documentos.id_ubicacion_destino,"
					+ " (select observacion_documento from documentos doc2"
					+ " where doc2.consecutivo_documento = documentos.observacion_documento)"
					+ " as observacion_documento2, ub.nombre, cli.nit,cli.factura_ingles, documentos.id_proveedor "
					+ " FROM documentos"
					+ " INNER JOIN clientes cli on documentos.id_cliente=cli.id"
					+ " INNER JOIN estados on documentos.id_estado=estados.id"
					+ " INNER JOIN Documento_x_Negociacion doc on documentos.id=doc.id_documento"
					+ " INNER JOIN termino_incoterm inc on inc.id = doc.id_termino_incoterm"
					+ " INNER JOIN ciudades ciu on ciu.id = cli.id_ciudad"
					+ " INNER JOIN metodo_pago on cli.id_metodo_pago = metodo_pago.id"
					+ " INNER JOIN ubicaciones ub on documentos.id_ubicacion_origen = ub.id"
					+ " WHERE documentos.id_tipo_documento = '23'"
					+ " AND ((documentos.id_estado = '15' AND doc.solicitud_cafe="
					+ false
					+ ") "
					+ " OR (documentos.id_estado = '16'  AND doc.solicitud_cafe="
					+ true
					+ ")) "
					+ " AND UPPER(documentos.consecutivo_documento) LIKE UPPER('%"
					+ consecutivoFacturaProforma
					+ "%')"
					+ " ORDER BY documentos.id DESC";

			List<Object[]> listado = em.createNativeQuery(sql).getResultList();

			if (listado != null) {
				for (Object[] objs : listado) {
					ListaEmpaqueDTO dto = new ListaEmpaqueDTO();
					ClienteDTO clienteDTO = new ClienteDTO();
					dto.setIdDocumento(objs[0] != null ? objs[0].toString()
							: null);
					dto.setConsecutivoDocumento(objs[1] != null ? objs[1]
							.toString() : null);
					dto.setFechaGeneracion((Timestamp) (objs[2] != null ? objs[2]
							: null));
					dto.setDescripcionTerminoIncoterm(objs[14] != null ? objs[14]
							.toString() : null);
					dto.setCantidadContenedores20(objs[25] != null ? new BigDecimal(
							objs[25].toString()) : null);
					dto.setCantidadContenedores40(objs[26] != null ? new BigDecimal(
							objs[26].toString()) : null);
					dto.setLugarIncoterm(objs[27] != null ? objs[27].toString()
							: null);
					dto.setNumeroPedidoWeb(objs[3] != null ? objs[3].toString()
							: null);
					dto.setFechaEntrega((Timestamp) (objs[33] != null ? objs[33]
							: null));
					dto.setObservacionDocumento(objs[38] != null ? objs[38]
							.toString() : null);
					dto.setCanal(objs[39] != null ? objs[39].toString() : null);
					dto.setObservacionMarcacion(objs[19] != null ? objs[19]
							.toString() : null);
					dto.setSitioEntrega(objs[34] != null ? objs[34].toString()
							: null);
					dto.setTotalPallets(objs[23] != null ? Double
							.valueOf(objs[23].toString()) : null);
					dto.setSolicitudCafe(objs[35].toString() == "t" ? true
							: false);
					dto.setIdEstado(objs[6] != null ? objs[6].toString() : null);
					dto.setFechaEsperadaEntrega((Timestamp) (objs[24] != null ? objs[24]
							: null));
					dto.setIdProveedor(objs[42] != null ? objs[42].toString()
							: null);
					dto.setIdUbicacionOrigen(objs[36] != null ? objs[36]
							.toString() : null);
					dto.setIdUbicacionDestino(objs[37] != null ? objs[37]
							.toString() : null);
					dto.setIdTerminoIncoterm(objs[13] != null ? objs[13]
							.toString() : null);
					dto.setCostoEntrega(objs[15] != null ? new BigDecimal(
							objs[15].toString()) : null);
					dto.setCostoSeguro(objs[16] != null ? new BigDecimal(
							objs[16].toString()) : null);
					dto.setCostoFlete(objs[17] != null ? new BigDecimal(
							objs[17].toString()) : null);
					dto.setOtrosGastos(objs[18] != null ? new BigDecimal(
							objs[18].toString()) : null);
					dto.setCantidadDiasVigencia(objs[29] != null ? objs[29]
							.toString() : null);
					dto.setTotalPesoNeto(objs[20] != null ? Double
							.valueOf(objs[20].toString()) : null);
					dto.setTotalPesoBruto(objs[21] != null ? Double
							.valueOf(objs[21].toString()) : null);
					dto.setTotalTendidos(objs[22] != null ? Double
							.valueOf(objs[22].toString()) : null);
					dto.setIdTipoDocumento(objs[5] != null ? objs[5].toString()
							: null);

					clienteDTO.setId(objs[4] != null ? objs[4].toString()
							: null);
					clienteDTO.setDocumento(objs[3] != null ? objs[3]
							.toString() : null);
					clienteDTO.setNombre(objs[7] != null ? objs[7].toString()
							: null);
					clienteDTO.setDireccion(objs[10] != null ? objs[10]
							.toString() : null);
					clienteDTO.setTelefono(objs[11] != null ? objs[11]
							.toString() : null);
					clienteDTO.setContacto(objs[12] != null ? objs[12]
							.toString() : null);
					dto.setCliente(clienteDTO);

					dto.setCantidadEstibas(Math.ceil(dto.getTotalPallets()));
					dto.setPesoBrutoEstibas(dto.getCantidadEstibas() * 20D);
					lista.add(dto);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lista;
	}

	@SuppressWarnings("unchecked")
	public List<Documento> consultarOrdenesDeDespacho(
			String consecutivoDocumento) {
		List<Documento> listado = new ArrayList<Documento>();
		String query;
		try {
			query = "SELECT d FROM Documento d "
					+ "JOIN FETCH d.estadosxdocumento exd "
					+ "WHERE d.estadosxdocumento.id.idTipoDocumento = :tipoDocumento"
					+ " ORDER BY d.id DESC";

			listado = em
					.createQuery(query)
					.setParameter("tipoDocumento",
							(long) ConstantesTipoDocumento.ORDEN_DESPACHO)
					.getResultList();
		} catch (Exception e) {
			LOGGER.error(e
					+ "********Error consultando Documentos por tipo de documento ORDEN DE DESPACHO");
			return null;
		}
		return listado;
	}

	public List<Documento> consultarDocumento(Map<String, Object> parametros) {
		// TODO Auto-generated method stub

		List<Documento> lista = new ArrayList<Documento>();

		int tipo = (Integer) parametros.get("tipo");
		int estado = (Integer) parametros.get("estado");

		String parametroConseDoc = (String) parametros.get("parametroConseDoc");

		// System.out.println("tipo doc:"+tipo);
		// System.out.println("estado doc:"+estado);
		// System.out.println("parametroConseDoc:"+parametroConseDoc);

		try {
			// String query =
			// "SELECT DISTINCT f FROM Usuario u INNER JOIN u.role r INNER JOIN r.permisos p INNER JOIN p.funcionalidade f WHERE u.email = :email ORDER BY f.ordenar";

			String query = "select d from Documento d where id_tipo_documento= :tipo AND id_estado = :estado AND UPPER(consecutivoDocumento) LIKE UPPER( :parametroConseDoc) ORDER BY id DESC";

			lista = em.createQuery(query).setParameter("tipo", tipo)
					.setParameter("estado", estado)
					.setParameter("parametroConseDoc", parametroConseDoc)
					.getResultList();
			// lista = em.createQuery(query).setParameter("tipo", tipo)

			System.out.println("query LE" + lista.size());
		} catch (Exception e) {
			LOGGER.error(e);
		}

		return lista;

	}

	public ListaEmpaqueDTO consultarDocumentoListaEmpaque(
			String strConsecutivoDocumento) {
		// TODO Auto-generated method stub

		ListaEmpaqueDTO dto = new ListaEmpaqueDTO();

		String query = "";
		query = "SELECT  doc.id,doc.consecutivo_documento,doc.fecha_generacion,doc.documento_cliente,"
				+ " cli.nombre AS NOMBRE_CLIENTE,cli.direccion,cli.telefono,cli.contact"
				+ "o, inc.descripcion AS NOMBRE_INCOTERM,"
				+ " docxneg.observaciones_marcacion_2, docxneg.cantidad_contenedores_de_20, docxneg.cantidad_contenedores_de_40, docxneg.lugar_incoterm,"
				+ " docxneg.cantidad_estibas ,docxneg.peso_bruto_estibas, docxneg.descripcion"
				+ " FROM documentos doc INNER JOIN clientes cli on doc.id_cliente=cli.id "
				+ " INNER JOIN Documento_x_Negociacion docxneg on doc.id=docxneg.id_documento"
				+ " INNER JOIN termino_incoterm inc on inc.id = docxneg.id_termino_incoterm "
				+ " WHERE  doc.consecutivo_documento='"
				+ strConsecutivoDocumento + "'";

		List<Object[]> listado = em.createNativeQuery(query).getResultList();

		if (listado != null) {
			for (Object[] objs : listado) {

				dto.setIdDocumento(objs[0] != null ? objs[0].toString() : null);
				dto.setConsecutivoDocumento(objs[1] != null ? objs[1]
						.toString() : null);
				dto.setFechaGeneracion((Timestamp) (objs[2] != null ? objs[2]
						: null));
				dto.setNumeroPedidoWeb(objs[3] != null ? objs[3].toString()
						: null);

				ClienteDTO objCli = new ClienteDTO();

				objCli.setNombre(objs[4] != null ? objs[4].toString() : null);
				objCli.setDireccion(objs[5] != null ? objs[5].toString() : null);
				objCli.setTelefono(objs[6] != null ? objs[6].toString() : null);
				objCli.setContacto(objs[7] != null ? objs[7].toString() : null);

				dto.setCliente(objCli);

				dto.setDescripcionTerminoIncoterm(objs[8] != null ? objs[8]
						.toString() : null);
				dto.setObservacionMarcacion(objs[9] != null ? objs[9]
						.toString() : null);
				dto.setCantidadContenedores20(objs[10] != null ? new BigDecimal(
						objs[10].toString()) : null);
				dto.setCantidadContenedores40(objs[11] != null ? new BigDecimal(
						objs[11].toString()) : null);
				dto.setLugarIncoterm(objs[12] != null ? objs[12].toString()
						: null);
				dto.setCantidadEstibas(objs[13] != null ? new Double(objs[13]
						.toString()) : null);
				dto.setPesoBrutoEstibas(objs[14] != null ? new Double(objs[14]
						.toString()) : null);
				dto.setObservacionDocumento(objs[15] != null ? objs[15]
						.toString() : null);

			}
		}

		return dto;

	}

	public List<ProductoImprimirLEDTO> consultarProductoListaEmpaque(
			String strConsecutivoDocumento) {

		List<ProductoImprimirLEDTO> lista = new ArrayList<ProductoImprimirLEDTO>();

		try {
			// String query =
			// "SELECT DISTINCT f FROM Usuario u INNER JOIN u.role r INNER JOIN r.permisos p INNER JOIN p.funcionalidade f WHERE u.email = :email ORDER BY f.ordenar";
			// String query
			// ="select d from Documento d where id_tipo_documento= :tipo AND id_estado = :estado AND UPPER(consecutivoDocumento) LIKE UPPER( :parametroConseDoc) ORDER BY id DESC";
			/*
			 * String query ="select pi.sku" +" from ProductosXDocumentoPK pxd"
			 * +" LEFT JOIN ProductosInventario pi on pxd.idProducto=pi.id" +
			 * " LEFT JOIN ProductosInventarioComext pi_ce on pi_ce.idProducto=pxd.idProducto"
			 * +" LEFT JOIN Documento d on d.id=pxd.idProducto" +
			 * " LEFT join ProductosXClienteComext pxc_ce on pxc_ce.productosInventario=pxd.idProducto and pxc_ce.cliente=d.cliente"
			 * +" LEFT join TipoLoteoic tl on pi_ce.tipoLoteoic=tl.id "
			 * +" LEFT join DocumentoXLotesoic dxl on dxl.documento=" +
			 * " (select d2.id from Documento d2 where d2.consecutivoDocumento=(select d1.observacionDocumento from Documento d1 where d1.consecutivoDocumento= :parametroConseDoc))"
			 * +" and dxl.tipoLoteoic=pi_ce.tipoLoteoic" +
			 * " LEFT JOIN Unidad u on u.id = pi.unidadVenta WHERE d.consecutivoDocumento= :parametroConseDoc"
			 * ;
			 */

			String query = "select pi.sku , pi.nombre ,pxd.cantidad1 , pxd.total_peso_neto_item,"
					+ " pxd.total_peso_bruto_item ,pxd.cantidad_cajas_item ,  pxd.cantidad_x_embalaje,"
					+ " dxl.consecutivo ,"
					+ "  pxd.cantidad_pallets_item ,"
					+ "  pxc_ce.reg_sanitario ,"
					+ " (pxd.cantidad1/pi_ce.cantidad_x_embalaje)/pi_ce.total_cajas_x_pallet AS TOTAL_CAJAS_PALLET,pi_ce.descripcion as PRODUCTO_INGLES,"
					+ " unidades.nombre as UNIDAD, unidades.nombre_ingles as UNIDAD_INGLES,"
					+ " pi_ce.nombre_prd_proveedor as PRODUCTO_CLIENTE ,"
					+ " tl.descripcion_ingles as DESCRIPCION_LOTE_INGLES,"
					+ " tl.descripcion as DESCRIPCION_LOTE"
					+ " from productosXdocumentos pxd  "
					+ " LEFT JOIN productos_inventario pi on  pxd.id_producto=pi.id"
					+ " LEFT JOIN productos_inventario_comext pi_ce ON pi_ce.id_producto=pxd.id_producto"
					+ " LEFT JOIN documentos d on d.id=pxd.id_documento"
					+ " LEFT join productos_x_cliente_comext pxc_ce on pxc_ce.id_producto=pxd.id_producto and pxc_ce.id_cliente=d.id_cliente"
					+ " LEFT join tipo_loteoic tl on pi_ce.id_tipo_loteoic=tl.id "
					+ " LEFT join documento_x_lotesoic dxl on dxl.id_documento= "
					+ " (select documentos.id from documentos where documentos.consecutivo_documento=(select documentos.observacion_documento from documentos where documentos.consecutivo_documento='"
					+ strConsecutivoDocumento
					+ "'))"
					+ " and dxl.id_tipo_lote=pi_ce.id_tipo_loteoic"
					+ " LEFT JOIN unidades on unidades.id = pi.id_uv WHERE d.consecutivo_documento='"
					+ strConsecutivoDocumento + "'";
			List<Object[]> listado = em.createNativeQuery(query)
					.getResultList();

			System.out.println("query PXD LE Query" + query);
			System.out.println("query PXD LE" + listado.size());
			if (listado != null) {
				for (Object[] objs : listado) {
					ProductoImprimirLEDTO dto = new ProductoImprimirLEDTO();
					// ClienteDTO clienteDTO = new ClienteDTO();
					dto.setSku(objs[0] != null ? objs[0].toString() : null);
					dto.setNombre(objs[1] != null ? objs[1].toString() : null);
					dto.setCantidad(objs[2] != null ? new BigDecimal(objs[2]
							.toString()) : null);
					dto.setPesoNeto(objs[3] != null ? new BigDecimal(objs[3]
							.toString()) : null);
					dto.setPesoBruto(objs[4] != null ? new BigDecimal(objs[4]
							.toString()) : null);
					dto.setCantidadCajas(objs[5] != null ? new BigDecimal(
							objs[5].toString()) : null);
					dto.setCantidadPorEmbalaje(objs[6] != null ? new BigDecimal(
							objs[6].toString()) : null);
					dto.setConsecutivoLote(objs[7] != null ? objs[7].toString()
							: null);
					dto.setPallet(objs[8] != null ? new BigDecimal(objs[8]
							.toString()) : null);
					dto.setRegistroSanitario(objs[9] != null ? objs[9]
							.toString() : null);
					dto.setCajasPorPallets(objs[10] != null ? new BigDecimal(
							objs[10].toString()) : null);
					dto.setNombreIngles(objs[11] != null ? objs[11].toString()
							: null);
					dto.setUnidad(objs[12] != null ? objs[12].toString() : null);
					dto.setUnidadIngles(objs[13] != null ? objs[13].toString()
							: null);
					dto.setNombreProductoCliente(objs[14] != null ? objs[14]
							.toString() : null);
					dto.setDescripcionLoteIngles(objs[15] != null ? objs[15]
							.toString() : null);
					dto.setDescripcionLote(objs[16] != null ? objs[16]
							.toString() : null);

					lista.add(dto);
				}
			}
			return lista;

		} catch (Exception e) {
			LOGGER.error(e);
		}

		return lista;

	}

	@Override
	public List<Documento> consultarDocumentosPorConsecutivoPedido(
			String consecutivoDocumento) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FacturaDirectaDTO consultarDocumentoFacturaDirecta(
			String strConsecutivoDocumento) {

		FacturaDirectaDTO dto = new FacturaDirectaDTO();

		String query = "";
		query = "SELECT  d.id,d.consecutivo_documento,d.fecha_generacion,"
				+ " cli.nombre as cliente,cli.direccion,cli.telefono,cli.contacto,"
				+ " d.observacion_documento , u.nombre as ubicacion"
				+ " FROM documentos d INNER JOIN clientes cli on d.id_cliente=cli.id "
				+ " INNER JOIN ubicaciones u on d.id_ubicacion_destino=u.id "
				+ " WHERE d.consecutivo_documento='" + strConsecutivoDocumento
				+ "'";

		List<Object[]> listado = em.createNativeQuery(query).getResultList();

		if (listado != null) {
			for (Object[] objs : listado) {

				dto.setIdDocumento(objs[0] != null ? objs[0].toString() : null);
				dto.setConsecutivoDocumento(objs[1] != null ? objs[1]
						.toString() : null);
				dto.setFechaGeneracion((Timestamp) (objs[2] != null ? objs[2]
						: null));

				ClienteDTO objCli = new ClienteDTO();

				objCli.setNombre(objs[3] != null ? objs[3].toString() : null);
				objCli.setDireccion(objs[4] != null ? objs[4].toString() : null);
				objCli.setTelefono(objs[5] != null ? objs[5].toString() : null);
				objCli.setContacto(objs[6] != null ? objs[6].toString() : null);

				dto.setCliente(objCli);

				dto.setObservacionDocumento(objs[7] != null ? objs[7]
						.toString() : null);

				UbicacionDTO objUbiv = new UbicacionDTO();
				objUbiv.setNombre(objs[8] != null ? objs[8].toString() : null);

			}
		}

		return dto;

	}

	@SuppressWarnings("unchecked")
	@Override
	public BigInteger generarListaEmpaque(ListaEmpaqueDTO listaEmpaqueDTO) {
		String sqlSec = "select nextval('documentos_id_seq') AS SEQ";
		BigInteger secuence = (BigInteger) em.createNativeQuery(sqlSec)
				.getSingleResult();
		// SimpleDateFormat dateFormat = new
		// SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// String fechaoshig =
		// dateFormat.format(listaEmpaqueDTO.getFechaEsperadaEntrega());
		//
		// System.out.println("********FECHA  " +fechaoshig);

		String sqlEmpresa = "select id_empresa from ubicaciones where id = "
				+ listaEmpaqueDTO.getIdUbicacionOrigen() + " or id = "
				+ listaEmpaqueDTO.getIdUbicacionDestino();
		BigInteger prefijoEmpresa = (BigInteger) em.createNativeQuery(
				sqlEmpresa).getSingleResult();

		String sqlConsecutivoEmpresa = "select nextval('" + "LE"
				+ prefijoEmpresa + "_seq') AS SEQ";
		BigInteger consecutivoEmpresa = (BigInteger) em.createNativeQuery(
				sqlConsecutivoEmpresa).getSingleResult();

		String sql;
		try {
			sql = "INSERT INTO documentos (consecutivo_documento,"
					+ " fecha_esperada_entrega, id_ubicacion_origen, id_ubicacion_destino,"
					+ " id_tipo_documento, fecha_generacion,"
					+ " id_estado, observacion_documento, id, documento_cliente, "
					+ " sitio_entrega, id_cliente)" + " VALUES ('LE"
					+ prefijoEmpresa
					+ "-"
					+ consecutivoEmpresa.toString()
					+ "','"
					+ listaEmpaqueDTO.getFechaEsperadaEntrega()
					+ "','"
					+ listaEmpaqueDTO.getIdUbicacionOrigen()
					+ "','"
					+ listaEmpaqueDTO.getIdUbicacionDestino()
					+ "','"
					+ listaEmpaqueDTO.getIdTipoDocumento()
					+ "','"
					+ listaEmpaqueDTO.getFechaGeneracion()
					+ "','"
					+ listaEmpaqueDTO.getIdEstado()
					+ "','"
					+ listaEmpaqueDTO.getObservacionDocumento()
					+ "','"
					+ secuence
					+ "','"
					+ listaEmpaqueDTO.getCliente().getDocumento()
					+ "','"
					+ listaEmpaqueDTO.getSitioEntrega()
					+ "','"
					+ listaEmpaqueDTO.getCliente().getId() + "')";

			em.createNativeQuery(sql).executeUpdate();

			sql = "INSERT INTO Documento_x_Negociacion (id_documento, id_termino_incoterm, "
					+ " costo_entrega, costo_seguro, costo_flete, otros_gastos,"
					+ " observaciones_marcacion_2,total_peso_neto,total_peso_bruto,"
					+ "	total_tendidos,total_pallets,cantidad_dias_vigencia,"
					+ " solicitud_cafe,cantidad_contenedores_de_20,cantidad_contenedores_de_40, "
					+ " lugar_incoterm,cantidad_estibas,peso_bruto_estibas,descripcion)"
					+ " VALUES ('"
					+ secuence
					+ "','"
					+ listaEmpaqueDTO.getIdTerminoIncoterm()
					+ "','"
					+ listaEmpaqueDTO.getCostoEntrega()
					+ "','"
					+ listaEmpaqueDTO.getCostoSeguro()
					+ "','"
					+ listaEmpaqueDTO.getCostoFlete()
					+ "','"
					+ listaEmpaqueDTO.getOtrosGastos()
					+ "','"
					+ listaEmpaqueDTO.getObservacionMarcacion()
					+ "','"
					+ listaEmpaqueDTO.getTotalPesoNeto()
					+ "','"
					+ listaEmpaqueDTO.getTotalPesoBruto()
					+ "','"
					+ listaEmpaqueDTO.getTotalTendidos()
					+ "','"
					+ listaEmpaqueDTO.getTotalPallets()
					+ "','"
					+ listaEmpaqueDTO.getCantidadDiasVigencia()
					+ "','"
					+ listaEmpaqueDTO.getSolicitudCafe()
					+ "','"
					+ listaEmpaqueDTO.getCantidadContenedores20()
					+ "','"
					+ listaEmpaqueDTO.getCantidadContenedores40()
					+ "','"
					+ listaEmpaqueDTO.getLugarIncoterm()
					+ "',"
					+ listaEmpaqueDTO.getCantidadEstibas()
					+ ","
					+ listaEmpaqueDTO.getPesoBrutoEstibas()
					+ ",'"
					+ listaEmpaqueDTO.getDescripcionTerminoIncoterm() + "')";

			em.createNativeQuery(sql).executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return secuence;

	}

	@Override
	public List<Documento> consultarDocumentosPorTipoDocumentoYEstado(
			FiltroDocumentoDTO filtro) {
		Query query = em
				.createNamedQuery(Documento.FIND_BY_TIPO_DOCUMENTO_AND_ESTADO);
		query.setParameter("idTipoDocumento", filtro.getIdTipoDocumento());
		query.setParameter("idEstado", filtro.getIdEstado());
		return query.getResultList();
	}

}
