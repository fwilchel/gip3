package com.ssl.jv.gip.negocio.dao;

import java.io.Serializable;
import java.util.List;

public interface IGenericDAO<T> {

	public Serializable add(T pojo);

	public void update(T pojo);

	public void delete(T pojo);

	public T findByPK(Long id);

	public List<?> findAll();

	public List<?> findAllActivo();

	public void deleteMultiple(List<T> pojoList);

	public Long consultarProximoValorSecuencia(String secuenceName);

	public Number consultarMaximoValorColumna(String column);
}
