package com.ssl.jv.gip.negocio.dao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import com.ssl.jv.gip.jpa.pojo.ProductosXCliente;

@Stateless
@LocalBean
public class ProductoClienteDAO extends GenericDAO<ProductosXCliente> implements ProductoClienteDAOLocal {

  private static final Logger LOGGER = Logger.getLogger(ProductoClienteDAO.class);

  public ProductoClienteDAO() {
    this.persistentClass = ProductosXCliente.class;
  }

  @Override
  public List<ProductosXCliente> consultarPorClientePuntoVenta(Long idCliente,
      Long idPuntoVenta) {
    Query query = em
        .createNamedQuery("ProductosXCliente.findByClientePuntoVenta")
        .setParameter("idCliente", idCliente).setParameter("idPuntoVenta", idPuntoVenta);
    return query.getResultList();
  }

}
