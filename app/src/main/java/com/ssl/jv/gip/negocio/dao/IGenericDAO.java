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

  public <T> List<T> buscarPorConsultaNombrada(String consultaNombrada, Map<String, Object> parametros);

  public <T> T buscarRegistroPorConsultaNombrada(String consultaNombrada, Map<String, Object> parametros);

  public <T> List<T> buscarPorConsultaJPQL(String consultaJPQL, Map<String, Object> parametros);

  public <T> T buscarRegistroPorConsultaJPQL(String consultaJPQL, Map<String, Object> parametros);

  public <T> List<T> buscarPorConsultaNativa(String consultaNativa, Object... parametros);

  public <T> T buscarRegistroPorConsultaNativa(String consultaNativa, Object... parametros);

  public <T> List<T> buscarPorConsultaNativa(String consultaNativa, Class<T> tipo, Object... parametros);

  public <T> T buscarRegistroPorConsultaNativa(String consultaNativa, Class<T> tipo, Object... parametros);

  public int ejecutarConsultaNombrada(String consultaNombrada, Map<String, Object> parametros);

  public int ejecutarConsultaJPQL(String consultaJPQL, Map<String, Object> parametros);

  public int ejecutarConsultaNativa(String consultaNativa, Map<String, Object> parametros);

}
