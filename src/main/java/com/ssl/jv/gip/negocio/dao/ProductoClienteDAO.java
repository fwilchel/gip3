package com.ssl.jv.gip.negocio.dao;

import com.ssl.jv.gip.jpa.pojo.Cliente;
import com.ssl.jv.gip.jpa.pojo.Moneda;
import com.ssl.jv.gip.jpa.pojo.ProductosInventario;
import java.util.List;
import java.util.Map;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import com.ssl.jv.gip.jpa.pojo.ProductosXCliente;
import com.ssl.jv.gip.jpa.pojo.PuntoVenta;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;

@Stateless
@LocalBean
public class ProductoClienteDAO extends GenericDAO<ProductosXCliente> implements ProductoClienteDAOLocal {

  private static final Logger LOGGER = Logger.getLogger(ProductoClienteDAO.class);

  @Override
  public List<ProductosXCliente> consultarPorClientePuntoVenta(Long idCliente, Long idPuntoVenta) {
	Query query = em.createNamedQuery("ProductosXCliente.findByClientePuntoVenta").setParameter("idCliente", idCliente).setParameter("idPuntoVenta", idPuntoVenta);
	return query.getResultList();
  }

  @Override
  public List<ProductosXCliente> consultarProductosXCliente(Map<String, Object> parametros) {
	LOGGER.debug("Metodo: <<consultarProductosXCliente>>");
	String sql;
	String sku = (String) parametros.get("sku");
	String nombreCliente = (String) parametros.get("nombreCliente");
	String nombrePuntoVenta = (String) parametros.get("nombrePuntoVenta");
	boolean activo = (boolean) parametros.get("activo");
	if (activo) {
	  sql = ProductosXCliente.BUSCAR_PRODUCTOS_X_CLIENTES_ACTIVOS;
	} else {
	  sql = ProductosXCliente.BUSCAR_PRODUCTOS_X_CLIENTES;
	}
	Query query = em.createNativeQuery(sql);
	if (sku == null || sku.isEmpty()) {
	  query.setParameter("sku", "%");
	} else {
	  query.setParameter("sku", "%" + sku + "%");
	}
	if (nombreCliente == null || nombreCliente.isEmpty()) {
	  query.setParameter("nombreCliente", "%");
	} else {
	  query.setParameter("nombreCliente", "%" + nombreCliente + "%");
	}
	if (nombrePuntoVenta == null || nombrePuntoVenta.isEmpty()) {
	  query.setParameter("nombrePuntoVenta", "%");
	} else {
	  query.setParameter("nombrePuntoVenta", "%" + nombrePuntoVenta + "%");
	}
	List<Object[]> listado = query.getResultList();
    List<ProductosXCliente> listaRetorno = null;
	if (listado != null) {
      listaRetorno = new ArrayList<>();
	  for (Object[] objs : listado) {
        ProductosXCliente pxc = new ProductosXCliente();
        pxc.setId(objs[0] == null ? null : Long.parseLong(objs[0].toString()));
        pxc.setActivo(objs[1] == null ? null : Boolean.valueOf(objs[1].toString()));
        pxc.setDescuentoxproducto(objs[2] == null ? null : new BigDecimal(objs[2].toString()));
        pxc.setFechaFinalVigencia((Timestamp) (objs[3] != null ? objs[3] : null));
        pxc.setFechaInicialVigencia((Timestamp) (objs[4] != null ? objs[4] : null));
        pxc.setIva(objs[5] == null ? null : new BigDecimal(objs[5].toString()));
        pxc.setOtrosDescuentos(objs[6] == null ? null : new BigDecimal(objs[6].toString()));
        pxc.setPrecioMl(objs[7] == null ? null : new BigDecimal(objs[7].toString()));
        pxc.setPrecioUsd(objs[8] == null ? null : new BigDecimal(objs[8].toString()));
        pxc.setVigente(objs[9] == null ? null : Boolean.valueOf(objs[9].toString()));
        Cliente cliente = new Cliente();
        cliente.setId(objs[10] == null ? null : Long.parseLong(objs[10].toString()));
        cliente.setNombre(objs[11] == null ? null : objs[11].toString());
        ProductosInventario productosInventario = new ProductosInventario();
        productosInventario.setId(objs[12] == null ? null : Long.parseLong(objs[12].toString()));
        productosInventario.setSku(objs[13] == null ? null : objs[13].toString());
        productosInventario.setNombre(objs[14] == null ? null : objs[14].toString());
        PuntoVenta puntoVenta = new PuntoVenta();
        puntoVenta.setId(objs[15] == null ? null : Long.parseLong(objs[15].toString()));
        puntoVenta.setNombre(objs[16] == null ? null : objs[16].toString());
        Moneda moneda = new Moneda();
        moneda.setId(objs[17] == null ? null : objs[17].toString());
        pxc.setCliente(cliente);
        pxc.setProductosInventario(productosInventario);
        pxc.setMoneda(moneda);
        listaRetorno.add(pxc);
	  }
	}
	return listaRetorno;
  }

}
