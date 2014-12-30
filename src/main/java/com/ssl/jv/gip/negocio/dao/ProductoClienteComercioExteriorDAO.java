package com.ssl.jv.gip.negocio.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
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
import com.ssl.jv.gip.negocio.dto.ProductoDTO;

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
	 * @author Lorena Salamanca
	 * @email tachu.salamanca@gmail.com
	 * @phone 316 6537244
	 * @version 1.0
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ProductoDTO> consultarProductoPorDocumento(String idDocumento, String idCliente){
		List<ProductoDTO> lista = new ArrayList<ProductoDTO>();
		String sql = "SELECT  productos_inventario.id,"
				+ " productos_inventario.sku,"
				+ " productos_inventario.nombre,"
				+ "	productosXdocumentos.cantidad1, "
				+ "	pi_ce.cantidad_x_embalaje,"
				+ "	(CASE WHEN (pi_ce.cantidad_x_embalaje = 0) THEN 0 ELSE (productosXdocumentos.cantidad1/pi_ce.cantidad_x_embalaje) END) as TotalCajas,"
				+ "	((CASE WHEN (pi_ce.cantidad_x_embalaje = 0) THEN 0 ELSE (pi_ce.peso_neto_embalaje/pi_ce.cantidad_x_embalaje)*productosXdocumentos.cantidad1 END)) as TotalPesoNeto,"
				+ "	((CASE WHEN (pi_ce.cantidad_x_embalaje = 0) THEN 0 ELSE (pi_ce.peso_bruto_embalaje/pi_ce.cantidad_x_embalaje)*productosXdocumentos.cantidad1 END)) as TotalPesoBruto,"
				+ " ((CASE WHEN (pi_ce.cantidad_x_embalaje = 0 or pi_ce.total_cajas_x_pallet = 0) THEN 0 ELSE (productosXdocumentos.cantidad1/pi_ce.cantidad_x_embalaje)/pi_ce.total_cajas_x_pallet END)) AS TotalCajasPallet,"
				+ "	tl.descripcion as DESCRIPCION_LOTE, dxl.consecutivo as CONSECUTIVO_LOTE,"
				+ " productosXdocumentos.valor_unitario_usd,"
				+ " productosXdocumentos.valor_total"
				+ " FROM productosXdocumentos LEFT JOIN productos_inventario ON productosXdocumentos.id_producto=productos_inventario.id"
				+ "  LEFT JOIN productos_x_cliente_comext ON productos_x_cliente_comext.id_producto=productosXdocumentos.id_producto"
				+ "	 LEFT JOIN productos_inventario_comext pi_ce ON pi_ce.id_producto=productos_inventario.id"
				+ "	 left join tipo_loteoic tl on pi_ce.id_tipo_loteoic=tl.id"
				+ "  LEFT join documento_x_lotesoic dxl on dxl.id_tipo_lote=tl.id"
				+ "	 WHERE productosXdocumentos.id_documento = " + idDocumento
				+ "  AND (dxl.id_documento = " + idDocumento + " or dxl.id_documento is null) "
				+ "		 AND productos_x_cliente_comext.id_cliente= " + idCliente 
				+ "  order by CONSECUTIVO_LOTE";


		List<Object[]> listado = em.createNativeQuery(sql).getResultList();
		
		if(listado != null){
			for(Object[] objs : listado){
				ProductoDTO dto = new ProductoDTO();
				dto.setId(objs[0] != null ? objs[0].toString() : null);
				dto.setSku(objs[1] != null ? objs[1].toString() : null);
				dto.setNombre(objs[2] != null ? objs[2].toString() : null);
				dto.setCantidad(objs[3] != null ? new BigDecimal(objs[3].toString()) : null);
				dto.setCantidadPorEmbalaje(objs[4] != null ? new BigDecimal(objs[4].toString()) : null);
				dto.setCantidadCajas(objs[5] != null ? new BigDecimal(objs[5].toString()) : null);
				dto.setPesoNeto(objs[6] != null ? new BigDecimal(objs[6].toString()) : null);
				dto.setPesoBruto(objs[7] != null ? new BigDecimal(objs[7].toString()) : null);
				dto.setCantidadPallets(objs[8] != null ? new BigDecimal(objs[8].toString()) : null);
				lista.add(dto);
			}
		}
		return lista;
	}
}
