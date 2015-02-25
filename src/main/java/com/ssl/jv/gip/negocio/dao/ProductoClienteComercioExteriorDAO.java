package com.ssl.jv.gip.negocio.dao;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.log4j.Logger;

import com.ssl.jv.gip.jpa.pojo.Cliente;
import com.ssl.jv.gip.jpa.pojo.ProductosInventario;
import com.ssl.jv.gip.jpa.pojo.ProductosXClienteComExtFiltroVO;
import com.ssl.jv.gip.jpa.pojo.ProductosXClienteComext;
import com.ssl.jv.gip.jpa.pojo.ProductosXClienteComextPK;
import com.ssl.jv.gip.negocio.dto.DocumentoIncontermDTO;
import com.ssl.jv.gip.negocio.dto.ListaEmpaqueDTO;
import com.ssl.jv.gip.negocio.dto.ProductoAsignarLoteOICDTO;
import com.ssl.jv.gip.negocio.dto.ProductoDTO;
import com.ssl.jv.gip.negocio.dto.ProductoGenerarFacturaPFDTO;
import com.ssl.jv.gip.negocio.dto.ProductoLoteAsignarLoteOICDTO;
import com.ssl.jv.gip.negocio.dto.ProductoPorClienteComExtDTO;

/**
 * Session Bean implementation class ProductoClienteComercioExteriorDAO
 *
 * @author Juan Jose Buzon
 *
 */
@Stateless
@LocalBean
public class ProductoClienteComercioExteriorDAO extends
		GenericDAO<ProductosXClienteComext> implements
		ProductoClienteComercioExteriorDAOLocal {

	private static final Logger LOGGER = Logger
			.getLogger(ProductoClienteComercioExteriorDAO.class);

	/**
	 * @see GenericDAO#GenericDAO()
	 */
	public ProductoClienteComercioExteriorDAO() {
		this.persistentClass = ProductosXClienteComext.class;
	}

	/**
	 * Crea factura proforma.
	 *
	 */
	public void crearFacturaProforma(DocumentoIncontermDTO documento,
			ProductoPorClienteComExtDTO producto) {
		try {

			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO productosXdocumentos(id_documento,id_producto,cantidad1,"
					+ "fecha_estimada_entrega,fecha_entrega,id_ml,id_unidades,total_peso_neto_item,"
					+ "valor_unitario_usd,valor_total,total_peso_bruto_item,cantidad_cajas_item,"
					+ "cantidad_pallets_item,cantidad_x_embalaje) "
					+ "VALUES "
					+ " ("
					+ documento.getIdDocumento()
					+ ","
					+ producto.getIntIdProductoInventario()
					+ ","
					+ producto.getDblCantidad1ProductoxDocumento()
					+ ","
					+ "current_timestamp + '2 days'"
					+ ","
					+ "current_timestamp + '2 days'"
					+ ","
					+ "'USD'"
					+ ","
					+ "(select id_ud from productos_inventario where id = "
					+ producto.getIntIdProductoInventario()
					+ ")"
					+ ","
					+ producto.getDblTotalPesoNeto()
					+ ","
					+ producto.getDblPrecioUSD()
					+ ","
					+ producto.getDblValorTotalProductoxDocumento()
					+ ","
					+ producto.getDblTotalPesoBruto()
					+ ","
					+ producto.getDblTotalCajas()
					+ ","
					+ producto.getDblTotalCajasPallet()
					+ ","
					+ producto.getDblTotalCajasTendido() + ")");

			int q = em.createNativeQuery(sql.toString()).executeUpdate();

		} catch (Exception e) {

		}
	}

	/**
	 * modificar factura proforma.
	 *
	 */
	public void modificarFacturaProforma(DocumentoIncontermDTO documento,
			ProductoPorClienteComExtDTO producto) {
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE productosXdocumentos SET "
					+ " total_peso_neto_item = "
					+ producto.getDblTotalPesoNeto()
					+ " , total_peso_bruto_item = "
					+ producto.getDblTotalPesoBruto()
					+ " , valor_unitario_usd = " + producto.getDblPrecioUSD()
					+ " , cantidad1 = "
					+ producto.getDblCantidad1ProductoxDocumento()
					+ " , valor_total = "
					+ producto.getDblValorTotalProductoxDocumento()
					+ " , cantidad_cajas_item = " + producto.getDblTotalCajas()
					+ " , cantidad_pallets_item = "
					+ producto.getDblTotalCajasPallet()
					+ " , cantidad_x_embalaje = "
					+ producto.getDblTotalCajasTendido());

			sql.append(" WHERE  id_documento = " + documento.getIdDocumento()
					+ " AND id_producto = "
					+ producto.getIntIdProductoInventario());

			int q = em.createNativeQuery(sql.toString()).executeUpdate();

		} catch (Exception e) {

		}
	}

	/**
	 * eliminar factura proforma.
	 *
	 */
	public void eliminarFacturaProforma(DocumentoIncontermDTO documento,
			ProductoPorClienteComExtDTO producto) {
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("DELETE FROM productosXdocumentos "
					+ " WHERE  id_documento = " + documento.getIdDocumento()
					+ " AND id_producto = "
					+ producto.getIntIdProductoInventario());

			int q = em.createNativeQuery(sql.toString()).executeUpdate();

		} catch (Exception e) {

		}
	}

	/**
	 * Consulta si existe un producto cliente
	 *
	 */
	public BigDecimal consultarUltimoSaldoProducto(Long idProducto) {
		BigDecimal vSaldo = null;

		try {

			String sqlSec = "select mov1.saldo from  movimientos_inventario_comext mov1"
					+ " where mov1.fecha = (select max(mov2.fecha) from movimientos_inventario_comext mov2 where mov1.id_producto=mov2.id_producto)"
					+ " and mov1.id_producto = " + idProducto;
			vSaldo = (BigDecimal) em.createNativeQuery(sqlSec)
					.getSingleResult();

		} catch (Exception e) {

		}
		return vSaldo;
	}

	/**
	 * Consulta si existe un producto cliente
	 *
	 */
	public Boolean consultarProductoCliente(Long idDocumento, Long idProducto) {
		Boolean vRespuesta = false;

		try {

			String sqlSec = "select count(id_documento) as EXISTE from productosXdocumentos "
					+ "where id_documento = "
					+ idDocumento
					+ " and id_producto = " + idProducto;
			BigInteger existe = (BigInteger) em.createNativeQuery(sqlSec)
					.getSingleResult();

			if (existe != null
					&& (existe.compareTo(BigInteger.valueOf(0)) == 1)) {
				vRespuesta = true;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return vRespuesta;
	}

	/**
	 * Crea el saldo del producto.
	 *
	 */
	public void crearSaldo(DocumentoIncontermDTO documento,
			ProductoPorClienteComExtDTO producto) {
		try {
			String sqlSec = "select nextval('movimientos_inventario_comext_id_seq') AS SEQ";
			String secuencia = (String) em.createNativeQuery(sqlSec)
					.getSingleResult();

			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO movimientos_inventario_comext(consecutivo_documento,id_tipo_movimiento,id_producto,cantidad,fecha,saldo,id) "
					+ "VALUES "
					+ " ("
					+ documento.getConsecutivoDocumento()
					+ ","
					+ producto.getIdTipoMovimiento()
					+ ","
					+ producto.getIntIdProducto()
					+ ","
					+ producto.getDblCantidad1ProductoxDocumento()
					+ ","
					+ producto.getDtmFecha()
					+ ","
					+ producto.getDblSaldo()
					+ "," + secuencia + ")");

			int q = em.createNativeQuery(sql.toString()).executeUpdate();

		} catch (Exception e) {

		}
	}

	/**
	 * Consultar lista solicitudes pedido.
	 *
	 * @return the list
	 */
	public List<ProductoPorClienteComExtDTO> consultarListaSolicitudesPedido(
			Long idDocumento, Long idCliente) {

		List<ProductoPorClienteComExtDTO> lista = new ArrayList<ProductoPorClienteComExtDTO>();

		String sql = "SELECT  productos_inventario.id,"
				+ "productos_inventario.nombre,"
				+ "productos_inventario.sku,"
				+ "productos_inventario.id_ud,"
				+ "productosXdocumentos.cantidad1,"
				+ "productos_x_cliente_comext.precio,"
				+ "productosXdocumentos.cantidad1*productos_x_cliente_comext.precio as VALOR_TOTAL,"
				+ " (CASE WHEN (productos_inventario_comext.cantidad_x_embalaje = 0) THEN 0 "
				+ "ELSE round((productos_inventario_comext.peso_neto_embalaje/productos_inventario_comext.cantidad_x_embalaje),3)*productosXdocumentos.cantidad1 END) as TotalPesoNeto,"
				+ " (CASE WHEN (productos_inventario_comext.cantidad_x_embalaje = 0) THEN 0 "
				+ "ELSE (productos_inventario_comext.peso_bruto_embalaje/productos_inventario_comext.cantidad_x_embalaje)*productosXdocumentos.cantidad1 END) as TotalPesoBruto, "
				+ "productos_x_cliente_comext.id_cliente,"
				+ "productos_inventario_comext.peso_bruto,"
				+ "productos_inventario_comext.peso_neto,"
				+ "productos_inventario_comext.cant_cajas_x_tendido,"
				+ "productos_inventario_comext.total_cajas_x_pallet,"
				+ "productos_inventario_comext.cantidad_x_embalaje,"
				+ " (CASE WHEN (productos_inventario_comext.cantidad_x_embalaje = 0) THEN 0 "
				+ "ELSE (productosXdocumentos.cantidad1/productos_inventario_comext.cantidad_x_embalaje) END) as TotalCajas,"
				+ " (CASE WHEN (productos_inventario_comext.cantidad_x_embalaje = 0 or productos_inventario_comext.cant_cajas_x_tendido = 0) THEN 0 "
				+ "ELSE (productosXdocumentos.cantidad1/productos_inventario_comext.cantidad_x_embalaje)/productos_inventario_comext.cant_cajas_x_tendido END) AS TotalCajasTendido,"
				+ " (CASE WHEN (productos_inventario_comext.cantidad_x_embalaje = 0 or productos_inventario_comext.total_cajas_x_pallet = 0) THEN 0 "
				+ "ELSE (productosXdocumentos.cantidad1/productos_inventario_comext.cantidad_x_embalaje)/productos_inventario_comext.total_cajas_x_pallet END) AS TotalCajasPallet,"
				+ " productos_inventario_comext.posicion_arancelaria, "
				+ "unidades.nombre AS NOMBRE_UNIDAD, "
				+ "unidades.nombre_ingles, "
				+ "productos_inventario_comext.descripcion, "
				+ "productos_inventario_comext.peso_neto_embalaje, "
				+ "productos_inventario_comext.peso_bruto_embalaje, "
				+ "productos_inventario_comext.controlstock, "
				+ "productos_inventario_comext.nombre_prd_proveedor as PRODUCTO_CLIENTE"
				+ " FROM productosXdocumentos LEFT JOIN productos_inventario ON productosXdocumentos.id_producto=productos_inventario.id"
				+ " LEFT JOIN productos_x_cliente_comext ON productos_x_cliente_comext.id_producto=productosXdocumentos.id_producto"
				+ " LEFT JOIN productos_inventario_comext ON productos_inventario_comext.id_producto=productos_inventario.id"
				+ " INNER JOIN unidades ON productos_inventario.id_uv=unidades.id"
				+ " WHERE productosXdocumentos.id_documento=" + idDocumento
				+ " AND (productos_x_cliente_comext.id_cliente=" + idCliente
				+ " or productos_x_cliente_comext.id_cliente is null)";

		List<Object[]> listado = em.createNativeQuery(sql).getResultList();

		if (listado != null) {
			for (Object[] objs : listado) {
				ProductoPorClienteComExtDTO dto = new ProductoPorClienteComExtDTO();

				dto.setIntIdProductoInventario(objs[0] != null ? Long
						.parseLong(objs[0].toString()) : null);
				dto.setStrNombreProductoInventario(objs[1] != null ? objs[1]
						.toString() : null);
				dto.setStrSkuProductoInventario(objs[2] != null ? objs[2]
						.toString() : null);
				dto.setIntUnidadProductoInventario(objs[3] != null ? Long
						.parseLong(objs[3].toString()) : null);

				dto.setDblCantidad1ProductoxDocumento(objs[4] != null ? new BigDecimal(
						objs[4].toString()) : null);

				dto.setDblCantidad1ActualProductoxDocumento(objs[4] != null ? new BigDecimal(
						objs[4].toString()) : null);

				dto.setDblPrecioUSD(objs[5] != null ? new BigDecimal(objs[5]
						.toString()) : null);

				dto.setDblValorTotalProductoxDocumento(objs[6] != null ? new BigDecimal(
						objs[6].toString()) : null);

				dto.setDblTotalPesoNeto(objs[7] != null ? new BigDecimal(
						objs[7].toString()) : null);
				dto.setDblTotalPesoBruto(objs[8] != null ? new BigDecimal(
						objs[8].toString()) : null);

				dto.setIntIdCliente(objs[9] != null ? Long.parseLong(objs[9]
						.toString()) : null);

				dto.setDblPesoBrutoProductoInventarioCE(objs[10] != null ? new BigDecimal(
						objs[10].toString()) : null);
				dto.setDblPesoNetoProductoInventarioCE(objs[11] != null ? new BigDecimal(
						objs[11].toString()) : null);
				dto.setDblCantCajasXTendidoProductoInventarioCE(objs[12] != null ? new BigDecimal(
						objs[12].toString()) : null);
				dto.setDblTotalCajasXPalletProductoInventarioCE(objs[13] != null ? new BigDecimal(
						objs[13].toString()) : null);
				dto.setDblCantidadXEmbalajeProductoInventarioCE(objs[14] != null ? new BigDecimal(
						objs[14].toString()) : null);

				dto.setDblTotalCajas(objs[15] != null ? new BigDecimal(objs[15]
						.toString()) : null);
				dto.setDblTotalCajasTendido(objs[16] != null ? new BigDecimal(
						objs[16].toString()) : null);
				dto.setDblTotalCajasPallet(objs[17] != null ? new BigDecimal(
						objs[17].toString()) : null);

				dto.setPosicionArancelariaProductoInventarioCE(objs[18] != null ? objs[18]
						.toString() : null);

				dto.setStrPosicionArancelariaProductoInventario(objs[18] != null ? objs[18]
						.toString() : null);

				dto.setNombreUnidad(objs[19] != null ? objs[19].toString()
						: null);
				dto.setNombreInglesUnidad(objs[20] != null ? objs[20]
						.toString() : null);

				dto.setDescripcionProductoInventarioCE(objs[21] != null ? objs[21]
						.toString() : null);
				dto.setDblPesoNetoEmbalajeProductoInventarioCE(objs[22] != null ? new BigDecimal(
						objs[22].toString()) : null);
				dto.setDblPesoBrutoEmbalajeProductoInventarioCE(objs[23] != null ? new BigDecimal(
						objs[23].toString()) : null);
				dto.setControlStockProductoInventarioCE((Boolean) (objs[24] != null ? objs[24]
						: null));
				dto.setNombrePrdProveedorProductoInventarioCE(objs[25] != null ? objs[25]
						.toString() : null);

				dto.setBlnIncluirBusqueda(true);

				lista.add(dto);
			}
		}

		return lista;

	}

	/**
	 * Consultar lista productos por cliente ce.
	 *
	 * @return the list
	 */
	public List<ProductoPorClienteComExtDTO> consultarListaProductosPorClienteCE(
			Long idCliente, String idsProductos, Boolean solicitudCafe) {

		List<ProductoPorClienteComExtDTO> lista = new ArrayList<ProductoPorClienteComExtDTO>();

		StringBuilder sql = new StringBuilder();
		if (solicitudCafe) {
			sql.append("SELECT productos_x_cliente_comext.id, "
					+ "productos_inventario.id AS ID_PRODUCTO, "
					+ "productos_inventario.sku, "
					+ "productos_inventario.nombre, "
					+ "productos_x_cliente_comext.precio, "
					+ "productos_inventario_comext.peso_neto, "
					+ "productos_inventario_comext.peso_bruto, "
					+ "productos_inventario_comext.cant_cajas_x_tendido, "
					+ "productos_inventario_comext.total_cajas_x_pallet, "
					+ "0 AS CANTIDAD, "
					+ "0 AS VALOR, "
					+ "productos_inventario_comext.cantidad_x_embalaje, "
					+ "productos_inventario_comext.peso_neto_embalaje, "
					+ "productos_inventario_comext.peso_bruto_embalaje"
					+ " FROM productos_inventario"
					+ " INNER JOIN productos_x_cliente_comext ON productos_inventario.id = productos_x_cliente_comext.id_producto"
					+ " INNER JOIN productos_inventario_comext ON productos_inventario.id = productos_inventario_comext.id_producto"
					+ " WHERE productos_x_cliente_comext.id_cliente = "
					+ idCliente);
			if (idsProductos != null && !idsProductos.isEmpty()) {
				sql.append(" AND productos_inventario.id NOT IN ("
						+ idsProductos + ")");
			}

			sql.append(" AND productos_inventario_comext.id_tipo_loteoic <> 5"
					+ " AND productos_x_cliente_comext.activo = TRUE");
		} else // Mercadeo
		{
			sql.append("SELECT productos_x_cliente_comext.id, "
					+ "productos_inventario.id AS ID_PRODUCTO, "
					+ "productos_inventario.sku, "
					+ "productos_inventario.nombre, "
					+ "productos_x_cliente_comext.precio, "
					+ "productos_inventario_comext.peso_neto, "
					+ "productos_inventario_comext.peso_bruto, "
					+ "productos_inventario_comext.cant_cajas_x_tendido, "
					+ "productos_inventario_comext.total_cajas_x_pallet, "
					+ "0 AS CANTIDAD, "
					+ "0 AS VALOR, "
					+ "productos_inventario_comext.cantidad_x_embalaje, "
					+ "productos_inventario_comext.peso_neto_embalaje, "
					+ "productos_inventario_comext.peso_bruto_embalaje"
					+ " FROM productos_inventario"
					+ " INNER JOIN productos_x_cliente_comext ON productos_inventario.id = productos_x_cliente_comext.id_producto"
					+ " INNER JOIN productos_inventario_comext ON productos_inventario.id = productos_inventario_comext.id_producto"
					+ " WHERE productos_x_cliente_comext.id_cliente = "
					+ idCliente);
			if (idsProductos != null && !idsProductos.isEmpty()) {
				sql.append(" AND productos_inventario.id NOT IN ("
						+ idsProductos + ")");
			}
			sql.append(" AND productos_inventario_comext.id_tipo_loteoic = 5"
					+ " AND productos_x_cliente_comext.activo = TRUE"
					+ " order by productos_inventario.sku");
		}

		List<Object[]> listado = em.createNativeQuery(sql.toString())
				.getResultList();

		if (listado != null) {
			for (Object[] objs : listado) {
				ProductoPorClienteComExtDTO dto = new ProductoPorClienteComExtDTO();

				dto.setIntId(objs[0] != null ? Long.parseLong(objs[0]
						.toString()) : null);
				dto.setIntIdProductoInventario(objs[1] != null ? Long
						.parseLong(objs[1].toString()) : null);
				dto.setStrSkuProductoInventario(objs[2] != null ? objs[2]
						.toString() : null);
				dto.setStrNombreProductoInventario(objs[3] != null ? objs[3]
						.toString() : null);
				dto.setDblPrecioUSD(objs[4] != null ? new BigDecimal(objs[4]
						.toString()) : new BigDecimal(0));
				dto.setDblPesoNetoProductoInventarioCE(objs[5] != null ? new BigDecimal(
						objs[5].toString()) : new BigDecimal(0.00));
				dto.setDblPesoBrutoProductoInventarioCE(objs[6] != null ? new BigDecimal(
						objs[6].toString()) : new BigDecimal(0.00));
				dto.setDblCantCajasXTendidoProductoInventarioCE(objs[7] != null ? new BigDecimal(
						objs[7].toString()) : new BigDecimal(0.00));
				dto.setDblTotalCajasXPalletProductoInventarioCE(objs[8] != null ? new BigDecimal(
						objs[8].toString()) : new BigDecimal(0.00));

				dto.setDblCantidad1ProductoxDocumento(objs[9] != null ? new BigDecimal(
						objs[9].toString()) : new BigDecimal(0.00));
				dto.setDblValorTotalProductoxDocumento(objs[10] != null ? new BigDecimal(
						objs[10].toString()) : new BigDecimal(0.00));

				dto.setDblCantidadXEmbalajeProductoInventarioCE(objs[11] != null ? new BigDecimal(
						objs[11].toString()) : new BigDecimal(0.00));
				dto.setDblPesoNetoEmbalajeProductoInventarioCE(objs[12] != null ? new BigDecimal(
						objs[12].toString()) : new BigDecimal(0.00));
				dto.setDblPesoBrutoEmbalajeProductoInventarioCE(objs[13] != null ? new BigDecimal(
						objs[13].toString()) : new BigDecimal(0.00));

				dto.setBlnIncluirBusqueda(false);

				lista.add(dto);
			}
		}

		return lista;

	}

	@Override
	public List<ProductosXClienteComext> consultarPorFiltro(
			ProductosXClienteComExtFiltroVO filtroVO) {
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<ProductosXClienteComext> query = criteriaBuilder
				.createQuery(ProductosXClienteComext.class);
		Root<ProductosXClienteComext> from = query
				.from(ProductosXClienteComext.class);
		Join<ProductosXClienteComext, ProductosInventario> productosInventarioJoin = from
				.join("productosInventario");
		Join<ProductosXClienteComext, Cliente> clientJoin = from
				.join("cliente");
		CriteriaQuery<ProductosXClienteComext> select = query.select(from);

		query.orderBy(criteriaBuilder.asc(productosInventarioJoin.get("sku")));

		ParameterExpression<Boolean> peActivo = null;
		ParameterExpression<String> peSku = null;
		ParameterExpression<String> peNombre = null;

		List<Predicate> predicates = new ArrayList<Predicate>();
		if (filtroVO.getActivo() != null) {
			Expression<Boolean> path = from.get("activo");
			// predicates.add(criteriaBuilder.isTrue(path));
			// peActivo = criteriaBuilder.parameter(Boolean.class, "activo");
			if (filtroVO.getActivo()) {
				predicates.add(criteriaBuilder.isTrue(path));
				// predicates.add(criteriaBuilder.isTrue(peActivo));
			} else {
				predicates.add(criteriaBuilder.isFalse(path));
				// predicates.add(criteriaBuilder.isFalse(peActivo));
			}
		}
		if (filtroVO.getSkuProducto() != null
				&& !filtroVO.getSkuProducto().trim().isEmpty()) {
			// Path<Object> path = productosInventarioJoin.get("sku");
			peSku = criteriaBuilder.parameter(String.class, "sku");
			Expression<String> literal = criteriaBuilder.upper(criteriaBuilder
					.literal(filtroVO.getSkuProducto()));
			predicates
					.add(criteriaBuilder.like(
							criteriaBuilder.upper(productosInventarioJoin
									.<String> get("sku")), peSku));
		}
		if (filtroVO.getNombreCliente() != null
				&& !filtroVO.getNombreCliente().trim().isEmpty()) {
			// Path<Object> path = productosInventarioJoin.get("sku");
			peNombre = criteriaBuilder.parameter(String.class, "nombre");
			Expression<String> literal = criteriaBuilder.upper(criteriaBuilder
					.literal(filtroVO.getNombreCliente()));
			predicates.add(criteriaBuilder.like(
					criteriaBuilder.upper(clientJoin.<String> get("nombre")),
					peNombre));
		}
		// select.where(criteriaBuilder.equal(productosInventarioJoin.get("sku"),
		// filtroVO.getSkuProducto()));
		if (!predicates.isEmpty()) {
			select.where(predicates.toArray(new Predicate[0]));
		}

		TypedQuery<ProductosXClienteComext> typedQuery = em.createQuery(select);

		if (peSku != null) {
			typedQuery.setParameter(peSku, filtroVO.getSkuProducto()
					.toUpperCase());
		}
		if (peNombre != null) {
			typedQuery.setParameter(peNombre, filtroVO.getNombreCliente()
					.toUpperCase());
		}
		return typedQuery.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProductosXClienteComext> consultarTodos() {
		Query query = em
				.createNamedQuery(ProductosXClienteComext.PRODUCTOS_X_CLIENTE_COM_EXT_FIND_ALL);
		return query.getResultList();
	}

	/**
	 * Consulta producto por documento
	 * 
	 * @author Lorena Salamanca
	 * @email tachu.salamanca@gmail.com
	 * @phone 316 6537244
	 * @version 1.0
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ProductoDTO> consultarProductoPorDocumento(
			ListaEmpaqueDTO listaEmpaqueDTO) {
		List<ProductoDTO> lista = new ArrayList<ProductoDTO>();
		String sql = "";
		if (listaEmpaqueDTO.getSolicitudCafe()) {
			sql = "SELECT  productos_inventario.id,"
					+ " productos_inventario.sku,"
					+ " productos_inventario.nombre,"
					+ "	productosXdocumentos.cantidad1, "
					+ "	pi_ce.cantidad_x_embalaje,"
					+ "	(CASE WHEN (pi_ce.cantidad_x_embalaje = 0) THEN 0 ELSE (productosXdocumentos.cantidad1/pi_ce.cantidad_x_embalaje) END) as TotalCajas,"
					+ "	((CASE WHEN (pi_ce.cantidad_x_embalaje = 0) THEN 0 ELSE (pi_ce.peso_neto_embalaje/pi_ce.cantidad_x_embalaje)*productosXdocumentos.cantidad1 END)) as TotalPesoNeto,"
					+ "	((CASE WHEN (pi_ce.cantidad_x_embalaje = 0) THEN 0 ELSE (pi_ce.peso_bruto_embalaje/pi_ce.cantidad_x_embalaje)*productosXdocumentos.cantidad1 END)) as TotalPesoBruto,"
					+ " ((CASE WHEN (pi_ce.cantidad_x_embalaje = 0 or pi_ce.total_cajas_x_pallet = 0) THEN 0 ELSE (productosXdocumentos.cantidad1/pi_ce.cantidad_x_embalaje)/pi_ce.total_cajas_x_pallet END)) AS TotalCajasPallet,"
					+ " productosXdocumentos.valor_unitario_usd, "
					+ " productosXdocumentos.valor_total, "
					+ " productos_inventario.id_uv, "
					+ "	tl.descripcion as DESCRIPCION_LOTE, "
					+ " dxl.consecutivo as CONSECUTIVO_LOTE "
					+ " FROM productosXdocumentos LEFT JOIN productos_inventario ON productosXdocumentos.id_producto=productos_inventario.id"
					+ "  LEFT JOIN productos_x_cliente_comext ON productos_x_cliente_comext.id_producto=productosXdocumentos.id_producto"
					+ "	 LEFT JOIN productos_inventario_comext pi_ce ON pi_ce.id_producto=productos_inventario.id"
					+ "	 left join tipo_loteoic tl on pi_ce.id_tipo_loteoic=tl.id"
					+ "  LEFT join documento_x_lotesoic dxl on dxl.id_tipo_lote=tl.id"
					+ "	 WHERE productosXdocumentos.id_documento = "
					+ listaEmpaqueDTO.getIdDocumento()
					+ "  AND (dxl.id_documento = "
					+ listaEmpaqueDTO.getIdDocumento()
					+ " or dxl.id_documento is null) "
					+ "		 AND productos_x_cliente_comext.id_cliente = "
					+ listaEmpaqueDTO.getCliente().getId()
					+ "  order by CONSECUTIVO_LOTE";

		} else if (!listaEmpaqueDTO.getSolicitudCafe()) {
			sql = "SELECT  productos_inventario.id,"
					+ " productos_inventario.sku,"
					+ " productos_inventario.nombre,"
					+ " productosXdocumentos.cantidad1,"
					+ " pi_ce.cantidad_x_embalaje,"
					+ " (CASE WHEN (pi_ce.cantidad_x_embalaje = 0) THEN 0 ELSE (productosXdocumentos.cantidad1/pi_ce.cantidad_x_embalaje) END) as TotalCajas,"
					+ " ((CASE WHEN (pi_ce.cantidad_x_embalaje = 0) THEN 0 ELSE (pi_ce.peso_neto_embalaje/pi_ce.cantidad_x_embalaje)*productosXdocumentos.cantidad1 END)) as TotalPesoNeto,"
					+ " ((CASE WHEN (pi_ce.cantidad_x_embalaje = 0) THEN 0 ELSE (pi_ce.peso_bruto_embalaje/pi_ce.cantidad_x_embalaje)*productosXdocumentos.cantidad1 END)) as TotalPesoBruto,"
					+ " ((CASE WHEN (pi_ce.cantidad_x_embalaje = 0 or pi_ce.total_cajas_x_pallet = 0) THEN 0 ELSE (productosXdocumentos.cantidad1/pi_ce.cantidad_x_embalaje)/pi_ce.total_cajas_x_pallet END)) AS TotalCajasPallet,"
					+ " productosXdocumentos.valor_unitario_usd, "
					+ " productosXdocumentos.valor_total, "
					+ " productos_inventario.id_uv "
					+ " FROM productosXdocumentos"
					+ " LEFT JOIN productos_inventario ON productosXdocumentos.id_producto=productos_inventario.id"
					+ " LEFT JOIN productos_x_cliente_comext ON productos_x_cliente_comext.id_producto=productosXdocumentos.id_producto"
					+ " LEFT JOIN productos_inventario_comext pi_ce ON pi_ce.id_producto=productos_inventario.id"
					+ " WHERE productosXdocumentos.id_documento = "
					+ listaEmpaqueDTO.getIdDocumento()
					+ " AND (productos_x_cliente_comext.id_cliente = "
					+ listaEmpaqueDTO.getCliente().getId()
					+ " or productos_x_cliente_comext.id_cliente is null)";
		}

		List<Object[]> listado = em.createNativeQuery(sql).getResultList();

		if (listado != null) {
			for (Object[] objs : listado) {
				ProductoDTO dto = new ProductoDTO();
				dto.setId(objs[0] != null ? objs[0].toString() : null);
				dto.setSku(objs[1] != null ? objs[1].toString() : null);
				dto.setNombre(objs[2] != null ? objs[2].toString() : null);
				dto.setCantidad(objs[3] != null ? new BigDecimal(objs[3]
						.toString()) : BigDecimal.ZERO);
				dto.setCantidadPorEmbalaje(objs[4] != null ? new BigDecimal(
						objs[4].toString()) : BigDecimal.ZERO);
				dto.setCantidadCajas(objs[5] != null ? new BigDecimal(objs[5]
						.toString()) : BigDecimal.ZERO);
				dto.setPesoNeto(objs[6] != null ? new BigDecimal(objs[6]
						.toString()) : BigDecimal.ZERO);
				dto.setPesoBruto(objs[7] != null ? new BigDecimal(objs[7]
						.toString()) : BigDecimal.ZERO);
				dto.setCantidadPallets(objs[8] != null ? new BigDecimal(objs[8]
						.toString()) : BigDecimal.ZERO);
				dto.setValorUnitarioUsd(objs[9] != null ? new BigDecimal(
						objs[9].toString()) : BigDecimal.ZERO);
				dto.setValorTotal(objs[10] != null ? new BigDecimal(objs[10]
						.toString()) : BigDecimal.ZERO);
				dto.setIdUnidadVenta(objs[11] != null ? Long.valueOf(objs[11]
						.toString()) : null);
				lista.add(dto);
			}
		}
		return lista;
	}

	@Override
	public void generarListaEmpaque(ProductoDTO productoDTO) {

		String sql = "INSERT INTO productosXdocumentos (id_documento, id_producto, cantidad1, "
				+ "	fecha_estimada_entrega, fecha_entrega, id_ml, "
				+ " id_unidades, total_peso_neto_item, valor_unitario_usd,"
				+ " valor_total, total_peso_bruto_item, cantidad_cajas_item,"
				+ " cantidad_pallets_item, cantidad_x_embalaje)" + " VALUES ("
				+ productoDTO.getIdDocumento()
				+ ","
				+ productoDTO.getId()
				+ ","
				+ productoDTO.getCantidad()
				+ ","
				+ " current_timestamp + '2 days', current_timestamp + '2 days', 'USD', "
				+ " (select id_ud from productos_inventario where id = "
				+ productoDTO.getId()
				+ "), "
				+ productoDTO.getPesoNeto()
				+ ","
				+ productoDTO.getValorUnitarioUsd()
				+ ","
				+ productoDTO.getValorTotal()
				+ ","
				+ productoDTO.getPesoBruto()
				+ ","
				+ productoDTO.getCantidadCajas()
				+ ","
				+ productoDTO.getCantidadPallets()
				+ ","
				+ productoDTO.getCantidadPorEmbalaje() + ")";

		em.createNativeQuery(sql).executeUpdate();
	}

	/**
	 * Consulta productos por documento para Generar Factura Proforma
	 * 
	 * @author Fredy Wilches
	 * @email fredy.wilches@softstudio.co
	 * @phone 3002146240
	 * @version 1.0
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ProductoGenerarFacturaPFDTO> consultarProductoPorDocumentoGenerarFacturaProforma(
			Long idDocumento, Long idCliente) {

		String sql = "SELECT productos_inventario.id, productos_inventario.nombre, productos_inventario.sku, productosXdocumentos.cantidad1 as cantidad, productos_x_cliente_comext.precio, productosXdocumentos.cantidad1*productos_x_cliente_comext.precio as valorTotal, "
				+ "(CASE WHEN (productos_inventario_comext.cantidad_x_embalaje = 0) THEN 0 ELSE round((productos_inventario_comext.peso_neto_embalaje/productos_inventario_comext.cantidad_x_embalaje),3)*productosXdocumentos.cantidad1 END) as totalPesoNeto,  "
				+ "(CASE WHEN (productos_inventario_comext.cantidad_x_embalaje = 0) THEN 0 ELSE (productos_inventario_comext.peso_bruto_embalaje/productos_inventario_comext.cantidad_x_embalaje)*productosXdocumentos.cantidad1 END) as totalPesoBruto,  "
				+ "productos_x_cliente_comext.id_cliente as idCliente, productos_inventario_comext.peso_bruto as pesoBruto, productos_inventario_comext.peso_neto as pesoneto, productos_inventario_comext.cant_cajas_x_tendido as cantidadCajas, productos_inventario_comext.total_cajas_x_pallet as cajasPorPallets, "
				+ "productos_inventario_comext.cantidad_x_embalaje as cantidadPorEmbalaje,productos_inventario.id_ud as idUnidad, (CASE WHEN (productos_inventario_comext.cantidad_x_embalaje = 0) THEN 0 ELSE (productosXdocumentos.cantidad1/productos_inventario_comext.cantidad_x_embalaje) END) as totalCajas, "
				+ "(CASE WHEN (productos_inventario_comext.cantidad_x_embalaje = 0 or productos_inventario_comext.cant_cajas_x_tendido = 0) THEN 0 ELSE (productosXdocumentos.cantidad1/productos_inventario_comext.cantidad_x_embalaje)/productos_inventario_comext.cant_cajas_x_tendido END) AS totalCajasTendido, "
				+ "(CASE WHEN (productos_inventario_comext.cantidad_x_embalaje = 0 or productos_inventario_comext.total_cajas_x_pallet = 0) THEN 0 ELSE (productosXdocumentos.cantidad1/productos_inventario_comext.cantidad_x_embalaje)/productos_inventario_comext.total_cajas_x_pallet END) AS totalCajasPallet, "
				+ "productos_inventario_comext.posicion_arancelaria as posicionArancelaria, unidades.nombre AS unidad, unidades.nombre_ingles as nombreIngles, productos_inventario_comext.descripcion, productos_inventario_comext.peso_neto_embalaje as pesoNetoEmbalaje, "
				+ "productos_inventario_comext.peso_bruto_embalaje as pesoBrutoEmbalaje, productos_inventario_comext.controlstock as controlStock "
				+ "FROM productosXdocumentos LEFT JOIN productos_inventario ON productosXdocumentos.id_producto=productos_inventario.id "
				+ "LEFT JOIN productos_x_cliente_comext ON productos_x_cliente_comext.id_producto=productosXdocumentos.id_producto  "
				+ "LEFT JOIN productos_inventario_comext ON productos_inventario_comext.id_producto=productos_inventario.id  "
				+ "INNER JOIN unidades ON productos_inventario.id_uv=unidades.id  "
				+ "WHERE productosXdocumentos.id_documento= :idDocumento AND (productos_x_cliente_comext.id_cliente= :idCliente or productos_x_cliente_comext.id_cliente is null) ";

		return em.createNativeQuery(sql, ProductoGenerarFacturaPFDTO.class)
				.setParameter("idDocumento", idDocumento)
				.setParameter("idCliente", idCliente).getResultList();

	}

	/**
	 * Consulta productos por documento para Asignar Lotes OIC
	 * 
	 * @author Fredy Wilches
	 * @email fredy.wilches@softstudio.co
	 * @phone 3002146240
	 * @version 1.0
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ProductoAsignarLoteOICDTO> consultarProductoPorDocumentoAsignarLotesOIC(
			Long idDocumento, Long idCliente) {

		String sql = "SELECT productos_inventario.id, productos_inventario.nombre, productos_inventario.sku, productosXdocumentos.cantidad1 as cantidad, productos_x_cliente_comext.precio, productosXdocumentos.cantidad1*productos_x_cliente_comext.precio as valorTotal, "
				+ "(CASE WHEN (productos_inventario_comext.cantidad_x_embalaje = 0) THEN 0 ELSE (productos_inventario_comext.peso_neto_embalaje/productos_inventario_comext.cantidad_x_embalaje)*productosXdocumentos.cantidad1 END) as totalPesoNeto,  "
				+ "(CASE WHEN (productos_inventario_comext.cantidad_x_embalaje = 0) THEN 0 ELSE (productos_inventario_comext.peso_bruto_embalaje/productos_inventario_comext.cantidad_x_embalaje)*productosXdocumentos.cantidad1 END) as totalPesoBruto,  "
				+ "productos_x_cliente_comext.id_cliente as idCliente, productos_inventario_comext.peso_bruto as pesoBruto, productos_inventario_comext.peso_neto as pesoneto, productos_inventario_comext.cant_cajas_x_tendido as cantidadCajas, productos_inventario_comext.total_cajas_x_pallet as cajasPorPallets, "
				+ "productos_inventario_comext.cantidad_x_embalaje as cantidadPorEmbalaje,productos_inventario.id_ud as idUnidad, (CASE WHEN (productos_inventario_comext.cantidad_x_embalaje = 0) THEN 0 ELSE (productosXdocumentos.cantidad1/productos_inventario_comext.cantidad_x_embalaje) END) as totalCajas, "
				+ "(CASE WHEN (productos_inventario_comext.cantidad_x_embalaje = 0 or productos_inventario_comext.cant_cajas_x_tendido = 0) THEN 0 ELSE (productosXdocumentos.cantidad1/productos_inventario_comext.cantidad_x_embalaje)/productos_inventario_comext.cant_cajas_x_tendido END) AS totalCajasTendido, "
				+ "(CASE WHEN (productos_inventario_comext.cantidad_x_embalaje = 0 or productos_inventario_comext.total_cajas_x_pallet = 0) THEN 0 ELSE (productosXdocumentos.cantidad1/productos_inventario_comext.cantidad_x_embalaje)/productos_inventario_comext.total_cajas_x_pallet END) AS totalCajasPallet, "
				+ "productos_inventario_comext.posicion_arancelaria as posicionArancelaria, unidades.nombre AS unidad, unidades.nombre_ingles as nombreIngles, productos_inventario_comext.descripcion, productos_inventario_comext.peso_neto_embalaje as pesoNetoEmbalaje, "
				+ "productos_inventario_comext.peso_bruto_embalaje as pesoBrutoEmbalaje, productos_inventario_comext.id_tipo_loteoic as tipoLote, tipo_loteoic.descripcion as descripcionLote "
				+ "FROM productosXdocumentos LEFT JOIN productos_inventario ON productosXdocumentos.id_producto=productos_inventario.id "
				+ "LEFT JOIN productos_x_cliente_comext ON productos_x_cliente_comext.id_producto=productosXdocumentos.id_producto  "
				+ "LEFT JOIN productos_inventario_comext ON productos_inventario_comext.id_producto=productos_inventario.id  "
				+ "LEFT JOIN tipo_loteoic ON tipo_loteoic.id = productos_inventario_comext.id_tipo_loteoic "
				+ "INNER JOIN unidades ON productos_inventario.id_uv=unidades.id  "
				+ "WHERE productosXdocumentos.id_documento= :idDocumento AND (productos_x_cliente_comext.id_cliente= :idCliente or productos_x_cliente_comext.id_cliente is null) ";

		return em.createNativeQuery(sql, ProductoAsignarLoteOICDTO.class)
				.setParameter("idDocumento", idDocumento)
				.setParameter("idCliente", idCliente).getResultList();

	}

	/**
	 * Consulta productos por documento para Asignar Lotes OIC agrupados por
	 * Lote
	 * 
	 * @author Fredy Wilches
	 * @email fredy.wilches@softstudio.co
	 * @phone 3002146240
	 * @version 1.0
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ProductoLoteAsignarLoteOICDTO> consultarProductoPorDocumentoLoteAsignarLotesOIC(
			Long idDocumento, Long idCliente) {

		String sql = "SELECT pic.id_tipo_loteoic as tipoLote, tipo_loteoic.descripcion as descripcionLote, SUM(productosXdocumentos.cantidad1) as totalCantidad,"
				+ " SUM ((CASE WHEN (pic.cantidad_x_embalaje = 0) THEN 0 ELSE (pic.peso_neto_embalaje/pic.cantidad_x_embalaje)*productosXdocumentos.cantidad1 END)) as totalPesoNeto,"
				+ " SUM((CASE WHEN (pic.cantidad_x_embalaje = 0) THEN 0 ELSE (pic.peso_bruto_embalaje/pic.cantidad_x_embalaje)*productosXdocumentos.cantidad1 END)) as totalPesoBruto,"
				+ " SUM((CASE WHEN (pic.cantidad_x_embalaje = 0) THEN 0 ELSE (productosXdocumentos.cantidad1/pic.cantidad_x_embalaje) END)) as totalCajas,"
				+ " SUM((CASE WHEN (pic.cantidad_x_embalaje = 0 or pic.total_cajas_x_pallet = 0) THEN 0 ELSE (productosXdocumentos.cantidad1/pic.cantidad_x_embalaje)/pic.total_cajas_x_pallet END)) AS totalCajasPallet"
				+ " FROM productosXdocumentos LEFT JOIN productos_inventario ON productosXdocumentos.id_producto=productos_inventario.id"
				+ " LEFT JOIN productos_x_cliente_comext ON productos_x_cliente_comext.id_producto=productosXdocumentos.id_producto"
				+ " LEFT JOIN productos_inventario_comext pic ON pic.id_producto=productos_inventario.id"
				+ " LEFT JOIN tipo_loteoic ON tipo_loteoic.id = pic.id_tipo_loteoic"
				+ " INNER JOIN unidades ON productos_inventario.id_uv=unidades.id"
				+ " WHERE productosxdocumentos.id_documento= :idDocumento "
				+ " AND (productos_x_cliente_comext.id_cliente= :idCliente or productos_x_cliente_comext.id_cliente is null)"
				+ " GROUP BY pic.id_tipo_loteoic, tipo_loteoic.descripcion"
				+ " ORDER BY tipo_loteoic.descripcion DESC";

		return em.createNativeQuery(sql, ProductoLoteAsignarLoteOICDTO.class)
				.setParameter("idDocumento", idDocumento)
				.setParameter("idCliente", idCliente).getResultList();

	}

	@Override
	public ProductosXClienteComext consultarPorPK(ProductosXClienteComextPK pk) {
		ProductosXClienteComext productosClienteComExt = null;
		try {
			productosClienteComExt = em.find(ProductosXClienteComext.class, pk);
		} catch (NoResultException e) {
		}
		return productosClienteComExt;
	}

	@Override
	public ProductosXClienteComext consultarPorClienteSku(Long idCliente,
			String sku) {
		Query query = em
				.createNamedQuery(
						ProductosXClienteComext.PRODUCTOS_X_CLIENTE_SKU)
				.setParameter("idCliente", idCliente).setParameter("sku", sku);
		ProductosXClienteComext r=(ProductosXClienteComext) query.getSingleResult();
		return r;
	}
	
	@Override
	public List<ProductoPorClienteComExtDTO> consultarListaProductosClienteFacturaProforma(
			Long idDocumento, Long idCliente) {

		List<ProductoPorClienteComExtDTO> lista = new ArrayList<ProductoPorClienteComExtDTO>();

		String sql = "select pi.id as ID_PRODUCTO,"
				+ "pi.sku AS SKU , "
				+ "pi.nombre AS NOMBRE_PRODUCTO ,"
				+ "pxd.cantidad1 AS Q,"
				+ "pxd.total_peso_neto_item AS PESONETO,"
				+ "pxd.total_peso_bruto_item AS PESOBRUTO,"
				+ "pxd.cantidad_cajas_item AS QCAJAS,  "
				+ "pxd.cantidad_x_embalaje AS QXEMBALAJE ,"
				+ "tl.descripcion as DESCRIPCION_LOTE, "				
				+ "pxd.cantidad_pallets_item as PALLETS , "
				+ "pxc_ce.reg_sanitario as REG_SANITARIO, "
				+ "pxc_ce.precio as PRECIO_USD, "
				+ "pxd.valor_total,"
				+ "(pxd.cantidad1/pi_ce.cantidad_x_embalaje)/pi_ce.total_cajas_x_pallet AS TOTAL_CAJAS_PALLET,"
				+ "pi_ce.descripcion as PRODUCTO_INGLES, "
				+ "pi_ce.posicion_arancelaria, "
				+ "unidades.nombre as UNIDAD, "
				+ "unidades.nombre_ingles as UNIDAD_INGLES,"
				+ "pxd.valor_unitario_usd AS VALOR_UNITARIO_USD,"
				+ "pi_ce.cantidad_x_embalaje as PICE_QXEMBALAJE,"
				+ "pi_ce.peso_neto_embalaje as PICE_PESONETOEMBALAJE,"
				+ "pi_ce.peso_bruto_embalaje as PICE_PESOBRUTOEMBALAJE,"
				+ "pi_ce.cant_cajas_x_tendido as PICE_QCAJASXTENDIDO,"
				+ "pi_ce.total_cajas_x_pallet as PICE_CAJASXPALLET,"
				+ "pi_ce.nombre_prd_proveedor as PRODUCTO_CLIENTE, "
				+ "0 as CONSECUTIVO_LOTE  "
				+ "from productosXdocumentos pxd "
				+ "left join productos_inventario pi on  pxd.id_producto=pi.id "
				+ "LEFT JOIN productos_inventario_comext pi_ce ON pi_ce.id_producto=pi.id "
				+ "LEFT join tipo_loteoic tl on pi_ce.id_tipo_loteoic=tl.id  "
				+ "LEFT join productos_x_cliente_comext pxc_ce on pxc_ce.id_producto=pi.id "
				+ "INNER JOIN unidades on unidades.id = pi.id_uv "
				+ "WHERE pxd.id_documento=" + idDocumento + " "
				+ "and pxc_ce.id_cliente=" + idCliente + " "
				+ "order by CONSECUTIVO_LOTE ,  pxc_ce.reg_sanitario";

		List<Object[]> listado = em.createNativeQuery(sql).getResultList();

		if (listado != null) {
			for (Object[] objs : listado) {
				ProductoPorClienteComExtDTO dto = new ProductoPorClienteComExtDTO();

				dto.setIntIdProductoInventario(objs[0] != null ? Long
						.parseLong(objs[0].toString()) : null);
				dto.setStrNombreProductoInventario(objs[2] != null ? objs[2]
						.toString() : null);
				dto.setStrSkuProductoInventario(objs[1] != null ? objs[1]
						.toString() : null);

				dto.setDblCantidad1ProductoxDocumento(objs[3] != null ? new BigDecimal(
						objs[3].toString()) : null);

				dto.setDblTotalPesoNeto(objs[4] != null ? new BigDecimal(
						objs[4].toString()) : null);
				dto.setDblTotalPesoBruto(objs[5] != null ? new BigDecimal(
						objs[5].toString()) : null);

				dto.setDblCantCajasXTendidoProductoInventarioCE(objs[6] != null ? new BigDecimal(
						objs[6].toString()) : null);
				
				dto.setDblCantidadXEmbalajeProductoInventarioCE(objs[7] != null ? new BigDecimal(
						objs[7].toString()) : null);
				
				dto.setDescripcionProductoInventarioCE(objs[8] != null ? objs[8]
						.toString() : null);
				
				dto.setDblTotalCajasXPalletProductoInventarioCE(objs[9] != null ? new BigDecimal(
						objs[9].toString()) : null);
				
				dto.setStrRegSanitario(objs[10] != null ? objs[10]
						.toString() : null);
				
				dto.setDblPrecioUSD(objs[11] != null ? new BigDecimal(
						objs[11].toString()) : null);
				
				dto.setDblValorTotalProductoxDocumento(objs[12] != null ? new BigDecimal(
						objs[12].toString()) : null);
				
				dto.setDblTotalCajasPallet(objs[13] != null ? new BigDecimal(
						objs[13].toString()) : null);
				
				dto.setDescripcionProductoInventarioInglesCE(objs[14] != null ? objs[14]
						.toString() : null);
				
				dto.setPosicionArancelariaProductoInventarioCE(objs[15] != null ? objs[15]
						.toString() : null);
				
				dto.setNombreUnidad(objs[16] != null ? objs[16].toString()
						: null);
				dto.setNombreInglesUnidad(objs[17] != null ? objs[17]
						.toString() : null);
				
				dto.setDblUnitarioTotalProductoxDocumentoIngles(objs[18] != null ? new BigDecimal(
						objs[18].toString()) : null);
				
				
				
				
				
				dto.setDblPesoNetoEmbalajeProductoInventarioCE(objs[20] != null ? new BigDecimal(
						objs[20].toString()) : null);
				
				dto.setDblPesoBrutoEmbalajeProductoInventarioCE(objs[21] != null ? new BigDecimal(
						objs[21].toString()) : null);
				
				dto.setDblTotalCajasTendido(objs[22] != null ? new BigDecimal(
						objs[22].toString()) : null);
				
				
				dto.setNombrePrdProveedorProductoInventarioCE(objs[24] != null ? objs[24]
						.toString() : null);
				
				
				dto.setBlnIncluirBusqueda(true);

				lista.add(dto);
			}
		}

		return lista;

	}
}
