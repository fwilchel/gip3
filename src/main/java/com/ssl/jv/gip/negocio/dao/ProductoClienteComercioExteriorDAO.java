package com.ssl.jv.gip.negocio.dao;

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
}
