package com.ssl.jv.gip.negocio.dao;

import java.util.List;

import javax.ejb.Local;

import com.ssl.jv.gip.jpa.pojo.TerminoIncoterm;

@Local
public interface TerminoIncotermDAOLocal extends IGenericDAO<TerminoIncoterm> {

  public List<TerminoIncoterm> consultarListaIncontermPorCliente(Long idCliente);

  /**
   * Metodo que consulta todos los incoterm de despacho
   *
   * @author Sebastian Gamba Pinilla - Soft Studio Ltda.
   * @email seba.gamba02@gmail.com
   * @phone 311 8376670
   * @return
   */
  public List<TerminoIncoterm> getAll();
}
