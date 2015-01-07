package com.ssl.jv.gip.negocio.dao;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import com.ssl.jv.gip.jpa.pojo.Documento;
import com.ssl.jv.gip.negocio.dto.ClienteDTO;
import com.ssl.jv.gip.negocio.dto.DatoContribucionCafeteraDTO;
import com.ssl.jv.gip.negocio.dto.DocumentoIncontermDTO;
import com.ssl.jv.gip.negocio.dto.ListaEmpaqueDTO;
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

			sql.append(" WHERE  id_documento = " + documento.getIdDocumento());

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

	@SuppressWarnings("unchecked")
	@Override
	public List<ListaEmpaqueDTO> consultarDocumentoPorFacturaProforma(
			String consecutivoFacturaProforma) {
		List<ListaEmpaqueDTO> lista = new ArrayList<ListaEmpaqueDTO>();

		String sql = "SELECT  documentos.id,documentos.consecutivo_documento,documentos.fecha_generacion,"
				+ " documentos.documento_cliente,documentos.id_cliente as doc_id,"
				+ " documentos.id_tipo_documento,documentos.id_estado,"
				+ " cli.nombre AS NOMBRE_CLIENTE,cli.id AS ID_CLIENTE,"
				+ " estados.nombre AS NOMBRE_ESTADO,cli.direccion,cli.telefono,"
				+ " cli.contacto,inc.id AS ID_INCOTERM, inc.descripcion AS NOMBRE_INCOTERM,"
				+ " doc.costo_entrega, doc.costo_seguro, doc.costo_flete, doc.otros_gastos, doc.observaciones_marcacion_2, doc.total_peso_neto, doc.total_peso_bruto,"
				+ " doc.total_tendidos, doc.total_pallets, documentos.fecha_esperada_entrega,"
				+ " doc.cantidad_contenedores_de_20, doc.cantidad_contenedores_de_40, doc.lugar_incoterm,"
				+ " ciu.nombre AS NOMBRE_CIUDAD, doc.cantidad_dias_vigencia, metodo_pago.descripcion AS DESCRIPCION_PAGO,"
				+ " documentos.observacion_documento, metodo_pago.descripcion_ingles ,documentos.fecha_entrega,documentos.sitio_entrega,doc.solicitud_cafe,"
				+ " documentos.id_ubicacion_origen,documentos.id_ubicacion_destino,"
				+ " (select observacion_documento from documentos doc2"
				+ " where doc2.consecutivo_documento = documentos.observacion_documento)"
				+ " as observacion_documento2, ub.nombre, cli.nit,cli.factura_ingles"
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
				dto.setIdDocumento(objs[0] != null ? objs[0].toString() : null);
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
				dto.setTotalPallets(objs[23] != null ? Double.valueOf(objs[23]
						.toString()) : null);

				clienteDTO.setId(objs[4] != null ? objs[4].toString() : null);
				clienteDTO.setNombre(objs[7] != null ? objs[7].toString()
						: null);
				clienteDTO.setDireccion(objs[10] != null ? objs[10].toString()
						: null);
				clienteDTO.setTelefono(objs[11] != null ? objs[11].toString()
						: null);
				clienteDTO.setContacto(objs[12] != null ? objs[12].toString()
						: null);
				dto.setCliente(clienteDTO);

				dto.setCantidadEstibas(Math.ceil(dto.getTotalPallets()));
				dto.setPesoBrutoEstibas(dto.getCantidadEstibas() * 20D);
				lista.add(dto);
			}
		}
		return lista;
	}

	/**
	 * Consulta de ordenes de despacho por filtro
	 * 
	 * @return Lista de ordenes de despacho
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Documento> consultarOrdenesDeDespachoPorFiltro(Documento filtro) {
		List<Documento> listado = new ArrayList<Documento>();
		String query;
		try {
			query = "SELECT a FROM documentos a WHERE a.consecutivo_documento like :codigo order by a.consecutivo_documento";
			listado = em
					.createQuery(query)
					.setParameter(
							"codigo",
							PORCENTAJE_LIKE + filtro.getConsecutivoDocumento()
									+ PORCENTAJE_LIKE).getResultList();
		} catch (Exception e) {
			LOGGER.error(e
					+ "********Error consultando ordenes de despacho por filtro");
			return null;
		}
		return listado;
	}

	/**
	 * Consulta de todas las ordenes de despacho
	 * 
	 * @return Lista de ordenes de despacho
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Documento> consultarOrdenesDeDespacho() {
		List<Documento> listado = new ArrayList<Documento>();
		String sql;
		try {
			sql = "SELECT documentos.consecutivo_documento,"
					+ "documentos.numero_factura,"
					+ "documentos.fecha_generacion "
					+ "FROM documentos "
					+ "WHERE documentos.id_tipo_documento = "
					+ ConstantesTipoDocumento.ORDEN_DESPACHO+" ";
//					+ "LIMIT 100";
			List<Object[]> aux = em.createNativeQuery(sql).getResultList();
			for (Object[] objs : aux) {
				Documento ordenDeDespacho = new Documento();
				ordenDeDespacho.setConsecutivoDocumento(objs[0] != null ? objs[0].toString(): null);
				ordenDeDespacho.setNumeroFactura(objs[1] != null ? objs[1].toString(): null);
				ordenDeDespacho.setFechaGeneracion((java.sql.Timestamp) (objs[2] != null ? objs[2]: null));
				listado.add(ordenDeDespacho);
			}
		} catch (Exception e) {
			LOGGER.error(e + " ********Error consultando ordenes de despacho");
			return null;
		}
		return listado;
	}

}
