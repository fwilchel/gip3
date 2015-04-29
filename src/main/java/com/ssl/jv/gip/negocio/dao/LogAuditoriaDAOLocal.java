package com.ssl.jv.gip.negocio.dao;

import javax.ejb.Local;

import com.ssl.jv.gip.jpa.pojo.LogAuditoria;
import java.util.List;
import java.util.Map;

@Local
public interface LogAuditoriaDAOLocal extends IGenericDAO<LogAuditoria> {

  List<LogAuditoria> consultarLogAuditoria(Map<String, Object> parametros);

}
