package com.ssl.jv.gip.negocio.dao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.apache.log4j.Logger;

import com.ssl.jv.gip.jpa.pojo.CategoriasInventario;
import com.ssl.jv.gip.jpa.pojo.ProductosInventario;
import com.ssl.jv.gip.jpa.pojo.ProductosInventarioComext;
import com.ssl.jv.gip.negocio.dto.ProductosInventarioComextFiltroVO;

@Stateless
@LocalBean
public class ProductosInventarioComextDAO extends
    GenericDAO<ProductosInventarioComext> implements
    ProductosInventarioComextDAOLocal {

  private static final Logger LOGGER = Logger
      .getLogger(ProductosInventarioComextDAO.class);

  public ProductosInventarioComextDAO() {
    this.persistentClass = ProductosInventarioComext.class;
  }

  @Override
  public ProductosInventarioComext findBySku(String sku) {
    return (ProductosInventarioComext) this.em
        .createNamedQuery("ProductosInventarioComext.findBySku")
        .setParameter("sku", sku).getSingleResult();
  }

  @Override
  public List<ProductosInventarioComext> consultarPorNombreSKUProductoOCategoria(
      ProductosInventarioComextFiltroVO filtro) {
    CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
    CriteriaQuery<ProductosInventarioComext> query = criteriaBuilder
        .createQuery(ProductosInventarioComext.class);
    Root<ProductosInventarioComext> from = query
        .from(ProductosInventarioComext.class);
    Join<ProductosInventarioComext, ProductosInventario> joinProductoInventario = from
        .join("productosInventario");
    if (filtro.getIdCategoria().equals(-1L)) {
      query.where(criteriaBuilder.like(criteriaBuilder
          .upper(joinProductoInventario.<String>get("sku")),
          criteriaBuilder.upper(criteriaBuilder.literal(filtro
                  .getSkuProducto()))));
    } else {
      Subquery<Long> subquery = query.subquery(Long.class);
      Root<CategoriasInventario> fromSubquery = subquery
          .from(CategoriasInventario.class);
      subquery.select(fromSubquery.<Long>get("id"));
      subquery.where(criteriaBuilder.equal(
          fromSubquery.get("categoriasInventario").get("id"),
          filtro.getIdCategoria()));

      query.where(criteriaBuilder.or(
          criteriaBuilder.equal(
              joinProductoInventario.get("categoriasInventario")
              .get("id"), filtro.getIdCategoria()),
          criteriaBuilder.in(
              joinProductoInventario.get("categoriasInventario")
              .get("id")).value(subquery)));
    }
    TypedQuery<ProductosInventarioComext> typedQuery = em
        .createQuery(query);

    return typedQuery.getResultList();
  }
}
