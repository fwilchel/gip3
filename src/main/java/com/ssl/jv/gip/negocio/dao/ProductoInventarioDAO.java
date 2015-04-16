package com.ssl.jv.gip.negocio.dao;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.primefaces.model.SortOrder;

import com.ssl.jv.gip.jpa.pojo.ProductosInventario;
import com.ssl.jv.gip.negocio.dto.ProductoDevolucionDTO;
import com.ssl.jv.gip.negocio.dto.ProductosInventarioFiltroDTO;

import java.util.Map;

@Stateless
@LocalBean
public class ProductoInventarioDAO extends GenericDAO<ProductosInventario>
    implements ProductoInventarioDAOLocal {

  private static final Logger LOGGER = Logger
      .getLogger(ProductoInventarioDAO.class);

  public ProductoInventarioDAO() {
    this.persistentClass = ProductosInventario.class;
  }

  @Override
  public Object[] consultar(ProductosInventario pi, int first, int pageSize, String sortField, SortOrder sortOrder, boolean count) {
    Object rta[] = new Object[2];

    String query = "SELECT " + (count ? "COUNT(pi)" : "pi")
        + " FROM ProductosInventario pi " + (count ? "" : "LEFT JOIN FETCH pi.productosInventarioComext pic LEFT JOIN FETCH pi.unidadVenta uv LEFT JOIN FETCH pi.unidadDespacho ud LEFT JOIN FETCH pic.unidadEmbalaje ue LEFT JOIN FETCH pic.tipoLoteoic tl LEFT JOIN FETCH pi.categoriasInventario ci1 LEFT JOIN FETCH ci1.categoriasInventario ci2 LEFT JOIN FETCH pi.pais pa ")
        + " WHERE 1=1 "
        + (pi.getSku() != null && !pi.getSku().equals("") ? " AND UPPER (pi.sku) LIKE UPPER (:sku) " : "")
        + (pi.getNombre() != null && !pi.getNombre().equals("") ? " AND UPPER (pi.nombre) LIKE UPPER (:nombre) " : "")
        + (pi.getCategoriasInventario() != null && pi.getCategoriasInventario().getId() != null && pi.getCategoriasInventario().getId() != 0 ? " AND pi.categoriasInventario= :categoria " : "")
        + (pi.getPais() != null && pi.getPais().getId() != null && !pi.getPais().getId().equals("") ? " AND pi.pais.id= :idPais " : "")
        + (pi.getDesactivado() != null ? " AND pi.desactivado= :desactivado " : "")
        + (!count && sortField != null && !sortField.equals("") ? " ORDER BY pi." + sortField + (sortOrder.equals(SortOrder.UNSORTED) ? "" : sortOrder.equals(SortOrder.ASCENDING) ? " ASC" : " DESC") : "");

    Query q = em.createQuery(query);

    if (pi.getSku() != null && !pi.getSku().equals("")) {
      q = q.setParameter("sku", pi.getSku());
    }
    if (pi.getNombre() != null && !pi.getNombre().equals("")) {
      q = q.setParameter("nombre", "%" + pi.getNombre() + "%");
    }
    if (pi.getCategoriasInventario() != null && pi.getCategoriasInventario().getId() != null && pi.getCategoriasInventario().getId() != 0) {
      q = q.setParameter("categoria", pi.getCategoriasInventario());
    }
    if (pi.getPais() != null && pi.getPais().getId() != null && !pi.getPais().getId().equals("")) {
      q = q.setParameter("idPais", pi.getPais().getId());
    }
    if (pi.getDesactivado() != null) {
      q = q.setParameter("desactivado", pi.getDesactivado());
    }

    System.out.println(first + " " + pageSize + " " + query);

    if (count) {
      rta[0] = q.getSingleResult();
    } else {
      if (sortField != null && !sortField.equals("")) {
        q.setFirstResult(first);
      }
      q.setMaxResults(pageSize);
      rta[1] = (List<ProductosInventario>) q.getResultList();
    }
    return rta;
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<ProductosInventario> consultarTodos() {
    return em.createNamedQuery(
        ProductosInventario.PRODUCTOS_INVENTARIO_FIND_ALL)
        .getResultList();
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<ProductosInventario> consultarPorUsuarioCategoriaSKUNombreAndEstado(
      ProductosInventarioFiltroDTO filtro) {
    Query query = em
        .createNamedQuery(ProductosInventario.PRODUCTOS_INVENTARIO_FIND_BY_USUARIO_CATEGORIA_SKU_NOMBRE_ESTADO);
    query.setParameter("idUsuario", filtro.getIdUsuario());
    query.setParameter("paramDesactivado",
        filtro.getEstado() != null ? true : false);
    query.setParameter("desactivado", filtro.getEstado());
    query.setParameter("paramCategoria",
        filtro.getIdCategoria() != null ? true : false);
    query.setParameter("idCategoria", filtro.getIdCategoria());
    query.setParameter("paramSku", filtro.getSku() != null ? true : false);
    query.setParameter("sku",
        filtro.getSku() != null ? "%" + filtro.getSku() + "%" : null);
    query.setParameter("paramNombre", filtro.getNombre() != null ? true
        : false);
    query.setParameter("nombre",
        filtro.getNombre() != null ? "%" + filtro.getNombre() + "%"
            : null);
    return query.getResultList();
  }

  @Override
  public List<ProductosInventario> consultarActivos() {
    return em.createNamedQuery(
        ProductosInventario.PRODUCTOS_INVENTARIO_FIND_ACTIVOS)
        .getResultList();
  }

  @Override
  public ProductosInventario consultarPorSku(String sku) {
    Query query = em
        .createNamedQuery(ProductosInventario.PRODUCTOS_INVENTARIO_FIND_BY_SKU);
    query.setParameter("sku", sku);
    ProductosInventario r = (ProductosInventario) query.getSingleResult();
    return r;
  }

  @Override
  public List<ProductosInventario> consultarPorSkus(List<String> skus) {
    Query query = em
        .createNamedQuery(ProductosInventario.PRODUCTOS_INVENTARIO_FIND_BY_SKUs);
    query.setParameter("skus", skus);
    return query.getResultList();
  }

  @Override
  public List<ProductosInventario> consultarPorEstadoCategoriaSKUNombreAndControlStock(
      ProductosInventarioFiltroDTO filtro) {
    Query query = em
        .createNamedQuery(ProductosInventario.PRODUCTOS_INVENTARIO_FIND_BY_DESACTIVADO_CATEGORIA_SKU_NOMBRE_AND_CONTROLSTOCK);
    query.setParameter("desactivado", filtro.getEstado());
    query.setParameter("paramCategoria",
        filtro.getIdCategoria() != null ? true : false);
    query.setParameter("idCategoria", filtro.getIdCategoria());
    query.setParameter("sku", filtro.getSku());
    query.setParameter("nombre", filtro.getNombre());
    query.setParameter("controlStock", filtro.getControlStock());
    return query.getResultList();
  }

  @Override
  public List<ProductosInventario> consultarListadoProductosReporteVentasCE(Map<String, Object> parametros) {
    LOGGER.debug("Metodo: <<consultarProductosReporteVentasCE>>");
    Query query = em.createNamedQuery(ProductosInventario.BUSCAR_PRODUCTOS_REPORTE_VENTAS_CE);
    if (parametros.get("sku") == null) {
      query.setParameter("sku", "%");
    } else {
      query.setParameter("sku", "%" + parametros.get("sku") + "%");
    }
    if (parametros.get("nombre") == null) {
      query.setParameter("nombre", "%");
    } else {
      query.setParameter("nombre", "%" + parametros.get("nombre") + "%");
    }
    query.setParameter("desactivado", true);
    return query.getResultList();
  }

  @Override
  public List<ProductoDevolucionDTO> consultarActivosPorPaises(List<String> paises) {
    List<ProductoDevolucionDTO> lista = new ArrayList<ProductoDevolucionDTO>();
    Query query = em
        .createNamedQuery(ProductosInventario.PRODUCTOS_INVENTARIO_FIND_ACTIVOS_PAISES);
    query.setParameter("paises", paises);
    List<Object[]> listado = query.getResultList();

    if (listado != null) {
      for (Object[] objs : listado) {
        ProductoDevolucionDTO dto = new ProductoDevolucionDTO((Long) objs[0], (String) objs[1], (String) objs[2], (Long) objs[3], (String) objs[4], (String) objs[5], (Long) objs[6]);
        lista.add(dto);
      }
    }
    return lista;

  }

  @Override
  public List<ProductoDevolucionDTO> consultarActivosDev() {
    List<ProductoDevolucionDTO> lista = new ArrayList<ProductoDevolucionDTO>();
    Query query = em.createNamedQuery(
        ProductosInventario.PRODUCTOS_INVENTARIO_FIND_ACTIVOS_DEV);

    List<Object[]> listado = query.getResultList();

    if (listado != null) {
      for (Object[] objs : listado) {
        ProductoDevolucionDTO dto = new ProductoDevolucionDTO((Long) objs[0], (String) objs[1], (String) objs[2], (Long) objs[3], (String) objs[4], (String) objs[5], (Long) objs[6]);
        lista.add(dto);
      }
    }
    return lista;

  }

}
