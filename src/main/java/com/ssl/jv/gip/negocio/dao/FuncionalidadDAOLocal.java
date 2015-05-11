package com.ssl.jv.gip.negocio.dao;

import java.util.List;

import javax.ejb.Local;

import com.ssl.jv.gip.jpa.pojo.Funcionalidad;

@Local
public interface FuncionalidadDAOLocal extends IGenericDAO<Funcionalidad> {

  public List<Funcionalidad> getMenu(String login);

}
