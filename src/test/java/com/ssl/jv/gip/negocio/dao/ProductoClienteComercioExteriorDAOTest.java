package com.ssl.jv.gip.negocio.dao;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.ssl.jv.gip.jpa.pojo.ProductosXClienteComExtFiltroVO;
import com.ssl.jv.gip.jpa.pojo.ProductosXClienteComext;

public class ProductoClienteComercioExteriorDAOTest extends TestBase {

  private ProductoClienteComercioExteriorDAO productoClienteComercioExteriorDAO;

  @Override
  @Before
  public void beforeEachTest() {
    productoClienteComercioExteriorDAO = new ProductoClienteComercioExteriorDAO();
    productoClienteComercioExteriorDAO.setEm(this.em);
  }

  @Test
  public void consultarPorIdNombreYEstadoClienteDeberiaRetornarListaVaciaOConClientesActivos() {
    ProductosXClienteComExtFiltroVO filtroVO = new ProductosXClienteComExtFiltroVO();
    filtroVO.setActivo(true);
    List<ProductosXClienteComext> list = productoClienteComercioExteriorDAO
        .consultarPorFiltro(filtroVO);
    Assert.assertTrue(list != null);
    for (ProductosXClienteComext productosXClienteComext : list) {
      Assert.assertTrue(productosXClienteComext.getCliente().getActivo());
    }
  }

  @Test
  public void consultarPorIdNombreYEstadoClienteDeberiaRetornarUnClienteActivoOUnaListaVacia() {
    ProductosXClienteComExtFiltroVO filtroVO = new ProductosXClienteComExtFiltroVO();
    filtroVO.setActivo(true);
    String skuProducto = "faA";
    filtroVO.setSkuProducto(skuProducto);
    List<ProductosXClienteComext> list = productoClienteComercioExteriorDAO
        .consultarPorFiltro(filtroVO);
    Assert.assertTrue(list != null);
  }

  @Test
  public void consultarTodosDeberiaRetornarListaNoNula() {
    List<ProductosXClienteComext> list = productoClienteComercioExteriorDAO
        .consultarTodos();
    Assert.assertTrue(list != null);
  }

}
