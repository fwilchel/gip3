package com.ssl.jv.gip.negocio.dao;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.ssl.jv.gip.jpa.pojo.ProductosInventario;
import com.ssl.jv.gip.negocio.dto.ProductosInventarioFiltroDTO;

public class ProductoInventarioDAOTest extends TestBase {

	private ProductoInventarioDAO productoInventarioDao;

	@Test
	public void testConsultarPorCategoriaAndSKUAndNombreAndEstado() {
		ProductosInventarioFiltroDTO filtro = new ProductosInventarioFiltroDTO();
		filtro.setEstado(false);
		filtro.setNombre("Arequipe");
		List<ProductosInventario> list = productoInventarioDao
				.consultarPorCategoriaAndSKUAndNombreAndEstado(filtro);
		Assert.assertFalse(list.isEmpty());
	}

	@Override
	@Before
	public void beforeEachTest() {
		productoInventarioDao = new ProductoInventarioDAO();
		productoInventarioDao.setEm(this.em);
	}

}
