package com.ssl.jv.gip.negocio.dao;

import java.math.BigDecimal;
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
import java.util.HashMap;
import java.util.Map;

@Stateless
@LocalBean
public class MovimientosInventarioComextDAO extends GenericDAO<MovimientosInventarioComext> implements MovimientosInventarioComextDAOLocal {

  private static final Logger LOGGER = Logger.getLogger(MovimientosInventarioComextDAO.class);

  public MovimientosInventarioComextDAO() {
    this.persistentClass = MovimientosInventarioComext.class;
  }

  @Override
  public Map<Long, BigDecimal> getMapUltimosSaldos() {
    LOGGER.trace("Method: getMapUltimosSaldos()");
    String query = "SELECT mic.id_producto, mic.saldo FROM movimientos_inventario_comext mic WHERE mic.fecha = (SELECT max(tmp.fecha) FROM movimientos_inventario_comext tmp WHERE tmp.id_producto = mic.id_producto) order by mic.id_producto";
    List<Object[]> listado = em.createNativeQuery(query).getResultList();
    if (listado != null) {
      Map<Long, BigDecimal> map = new HashMap<>();
      for (Object[] o : listado){
        Long idProducto = o[0] != null ? Long.parseLong(o[0].toString()) : null;
        BigDecimal saldo = o[1] != null ? new BigDecimal(o[1].toString()) : null;
        map.put(idProducto, saldo);
      }
      return map;
    }
    return null;
  }

  @Override
  public List<MovimientosInventarioComext> getUltimosSaldos() {
    try {
      return this.em.createNamedQuery("MovimientosInventarioComext.ultimosSaldos").getResultList();
    } catch (NoResultException e) {
      LOGGER.warn(e);
      return null;
    }
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<MovimientosInventarioComext> consultarPorSKU(String sku) {
    if (sku == null || "".equals(sku)) {
      sku = "%";
    } else {
      sku = "%" + sku + "%";
    }
    Query query = em.createNamedQuery(MovimientosInventarioComext.FIND_BY_SKU);
    query.setParameter("sku", sku);
    return query.getResultList();
  }

  @Override
  public List<MovimientosInventarioComext> consultarPorSKU(String sku, boolean ultimoSaldo) {
    CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
    CriteriaQuery<MovimientosInventarioComext> query = criteriaBuilder.createQuery(MovimientosInventarioComext.class);
    Root<MovimientosInventarioComext> from = query.from(MovimientosInventarioComext.class);
    Join<MovimientosInventarioComext, ProductosInventario> joinProductoInventario = from.join("productosInventarioComext").join("productosInventario");
    CriteriaQuery<MovimientosInventarioComext> select = query.select(from);

    if (ultimoSaldo) {
      Subquery<Date> subquery = query.subquery(Date.class);
      Root<MovimientosInventarioComext> fromSubQuery = subquery.from(MovimientosInventarioComext.class);
      Join<MovimientosInventarioComext, ProductosInventario> joinSubQuery = fromSubQuery.join("productosInventarioComext").join("productosInventario");
      Subquery<Date> selectSubQuery = subquery.select(criteriaBuilder.greatest(fromSubQuery.<Date>get("fecha")));
      selectSubQuery.where(criteriaBuilder.equal(joinSubQuery.get("id"), joinProductoInventario.get("id")));

      query.where(criteriaBuilder.and(criteriaBuilder.like(criteriaBuilder.upper(joinProductoInventario.<String>get("sku")), criteriaBuilder.upper(criteriaBuilder.literal(sku))), criteriaBuilder.equal(from.<Date>get("fecha"), subquery)));
    } else {
      query.where(criteriaBuilder.like(criteriaBuilder.upper(joinProductoInventario.<String>get("sku")), criteriaBuilder.upper(criteriaBuilder.literal(sku))));
      query.orderBy(criteriaBuilder.desc(from.get("fecha")));
    }
    TypedQuery<MovimientosInventarioComext> typedQuery = em.createQuery(query);
    return typedQuery.getResultList();
  }
}
