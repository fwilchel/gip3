package com.ssl.jv.gip.negocio.dao;

import java.lang.reflect.ParameterizedType;

public class ConsultaDAO<T> {

  private Class<T> persistentClass;

  @SuppressWarnings("unchecked")
  public ConsultaDAO() {
    this.persistentClass = (Class<T>) ((ParameterizedType) getClass()
        .getGenericSuperclass()).getActualTypeArguments()[0];
  }

}
