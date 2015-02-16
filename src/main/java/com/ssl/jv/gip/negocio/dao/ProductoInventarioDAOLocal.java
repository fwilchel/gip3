package com.ssl.jv.gip.negocio.dao;

import java.util.List;

import javax.ejb.Local;

import org.primefaces.model.SortOrder;

import com.ssl.jv.gip.jpa.pojo.ProductosInventario;
import com.ssl.jv.gip.negocio.dto.ProductosInventarioFiltroDTO;
import java.util.Map;

@Local
public interface ProductoInventarioDAOLocal extends
        IGenericDAO<ProductosInventario> {

  public Object[] consultar(ProductosInventario pi, int first, int pageSize,
          String sortField, SortOrder sortOrder, boolean count);

  public List<ProductosInventario> consultarTodos();

  public List<ProductosInventario> consultarActivos();

  public List<ProductosInventario> consultarPorUsuarioCategoriaSKUNombreAndEstado(
          ProductosInventarioFiltroDTO filtro);

  public ProductosInventario consultarPorSku(String sku);

  public List<ProductosInventario> consultarPorSkus(List<String> skus);

  public List<ProductosInventario> consultarPorEstadoCategoriaSKUNombreAndControlStock(
          ProductosInventarioFiltroDTO filtro);

  /**
   *
   * @param parametros
   * @return
   */
  public List<ProductosInventario> consultarListadoProductosReporteVentasCE(Map<String, Object> parametros);
}
