package com.ssl.jv.gip.negocio.dao;

import javax.ejb.Local;

import com.ssl.jv.gip.jpa.pojo.LogAuditoria;
import java.util.List;
import java.util.Map;
import org.primefaces.model.SortOrder;

@Local
public interface LogAuditoriaDAOLocal extends IGenericDAO<LogAuditoria> {

  List<LogAuditoria> consultarLogAuditoria(Map<String, Object> parametros);

  /**
   *
   * @param parametros
   * @param first
   * @param pageSize
   * @param sortField
   * @param sortOrder
   * @param count
   * @return
   */
  Object[] consultarLogAuditoria(Map<String, Object> parametros, int first, int pageSize, String sortField, SortOrder sortOrder, boolean count);
}
