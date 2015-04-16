package com.ssl.jv.gip.negocio.dao;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.ssl.jv.gip.jpa.pojo.MovimientosInventario;

/**
 * Session Bean implementation class MovimientoInventarioDAO
 */
@Stateless
@LocalBean
public class MovimientoInventarioDAO extends GenericDAO<MovimientosInventario>
    implements MovimientoInventarioDAOLocal {

  /**
   * Default constructor.
   */
  public MovimientoInventarioDAO() {
    this.persistentClass = MovimientosInventario.class;
  }

}
