package com.ssl.jv.gip.negocio.dao;

import java.util.Date;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.apache.log4j.Logger;

import com.ssl.jv.gip.jpa.pojo.MovimientosInventarioComext;
import com.ssl.jv.gip.jpa.pojo.ProductosInventario;

@Stateless
@LocalBean
public class MovimientosInventarioComextDAO extends
    GenericDAO<MovimientosInventarioComext> implements
    MovimientosInventarioComextDAOLocal {

  private static final Logger LOGGER = Logger
      .getLogger(MovimientosInventarioComextDAO.class);

  public MovimientosInventarioComextDAO() {
    this.persistentClass = MovimientosInventarioComext.class;
  }

  @Override
  public List<MovimientosInventarioComext> getUltimosSaldos() {
    try {
      return this.em.createNamedQuery(
          "MovimientosInventarioComext.ultimosSaldos")
          .getResultList();
    } catch (NoResultException e) {
      LOGGER.warn(e);
      return null;
    }
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<MovimientosInventarioComext> consultarPorSKU(String sku) {
    Query query = em
        .createNamedQuery(MovimientosInventarioComext.FIND_BY_SKU);
    query.setParameter("sku", sku);
    return query.getResultList();
  }

  @Override
  public List<MovimientosInventarioComext> consultarPorSKU(String sku,
      boolean ultimoSaldo) {
    CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
    CriteriaQuery<MovimientosInventarioComext> query = criteriaBuilder
        .createQuery(MovimientosInventarioComext.class);
    Root<MovimientosInventarioComext> from = query
        .from(MovimientosInventarioComext.class);
    Join<MovimientosInventarioComext, ProductosInventario> joinProductoInventario = from
        .join("productosInventarioComext").join("productosInventario");
    CriteriaQuery<MovimientosInventarioComext> select = query.select(from);

    if (ultimoSaldo) {
      Subquery<Date> subquery = query.subquery(Date.class);
      Root<MovimientosInventarioComext> fromSubQuery = subquery
          .from(MovimientosInventarioComext.class);
      Join<MovimientosInventarioComext, ProductosInventario> joinSubQuery = fromSubQuery
          .join("productosInventarioComext").join(
              "productosInventario");
      Subquery<Date> selectSubQuery = subquery.select(criteriaBuilder
          .greatest(fromSubQuery.<Date>get("fecha")));
      selectSubQuery.where(criteriaBuilder.equal(joinSubQuery.get("id"),
          joinProductoInventario.get("id")));

      query.where(criteriaBuilder.and(criteriaBuilder.like(
          criteriaBuilder.upper(joinProductoInventario
              .<String>get("sku")), criteriaBuilder
          .upper(criteriaBuilder.literal(sku))),
          criteriaBuilder.equal(from.<Date>get("fecha"), subquery)));
    } else {
      query.where(criteriaBuilder.like(criteriaBuilder
          .upper(joinProductoInventario.<String>get("sku")),
          criteriaBuilder.upper(criteriaBuilder.literal(sku))));
      query.orderBy(criteriaBuilder.desc(from.get("fecha")));
    }
    TypedQuery<MovimientosInventarioComext> typedQuery = em
        .createQuery(query);
    return typedQuery.getResultList();
  }
}
