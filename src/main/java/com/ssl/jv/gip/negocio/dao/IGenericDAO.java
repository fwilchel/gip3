package com.ssl.jv.gip.negocio.dao;

import java.util.List;
import java.util.Map;

public interface IGenericDAO<T> {

  public T add(T pojo);

  public void update(T pojo);

  public void delete(T pojo);

  public T findByPK(Long id);

  public T findByPK(String id);

  public List<?> findAll();

  public List<?> findAllActivo();

  public void deleteMultiple(List<T> pojoList);

  public Long consultarProximoValorSecuencia(String secuenceName);

  public Number consultarMaximoValorColumna(String column);

  public List<?> findAllActivoBoolean();

  public <T> List<T> buscarPorConsultaNombrada(String nombreConsulta, Map<String, Object> parametros);
  
  public <T> List<T> buscarPorConsultaJPQL(String consultaJPQL, Map<String, Object> parametros);
  
  public <T> List<T> buscarPorConsultaNativa(String consultaNativa, Map<String, Object> parametros);

  public <T> void ejecutarConsultaNombrada(String nombreConsulta, Map<String, Object> parametros);

  public <T> void ejecutarConsultaJPQL(String consultaJPQL, Map<String, Object> parametros);

  public <T> void ejecutarConsultaNativa(String consultaNativa, Map<String, Object> parametros);

}
