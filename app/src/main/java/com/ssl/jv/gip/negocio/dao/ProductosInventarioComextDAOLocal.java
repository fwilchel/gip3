package com.ssl.jv.gip.negocio.dao;

import java.util.List;

import javax.ejb.Local;

import com.ssl.jv.gip.jpa.pojo.ProductosInventarioComext;
import com.ssl.jv.gip.negocio.dto.ProductosInventarioComextFiltroVO;

@Local
public interface ProductosInventarioComextDAOLocal extends
    IGenericDAO<ProductosInventarioComext> {

  public ProductosInventarioComext findBySku(String sku);

  public List<ProductosInventarioComext> consultarPorNombreSKUProductoOCategoria(
      ProductosInventarioComextFiltroVO filtro);

}
