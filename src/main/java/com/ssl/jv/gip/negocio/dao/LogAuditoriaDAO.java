package com.ssl.jv.gip.negocio.dao;

import com.ssl.jv.gip.jpa.pojo.Funcionalidad;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import com.ssl.jv.gip.jpa.pojo.LogAuditoria;
import com.ssl.jv.gip.jpa.pojo.Usuario;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;

@Stateless
@LocalBean
public class LogAuditoriaDAO extends GenericDAO<LogAuditoria> implements LogAuditoriaDAOLocal {

  private static final Logger LOGGER = Logger.getLogger(LogAuditoriaDAO.class);

  public LogAuditoriaDAO() {
    this.persistentClass = LogAuditoria.class;
  }

  @Override
  public List<LogAuditoria> consultarLogAuditoria(Map<String, Object> parametros) {
    LOGGER.debug("Metodo: <<consultarLogAuditoria>>");
    CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
    CriteriaQuery<LogAuditoria> criteriaQuery = criteriaBuilder.createQuery(LogAuditoria.class);
    Root<LogAuditoria> from = criteriaQuery.from(LogAuditoria.class);
    Join<LogAuditoria, Usuario> usuario = from.join("usuario");
    Join<LogAuditoria, Funcionalidad> funcionalidad = from.join("funcionalidad");
    from.fetch("usuario");
    from.fetch("funcionalidad");
    criteriaQuery.select(from);
    criteriaQuery.orderBy(criteriaBuilder.desc(from.get("fecha")));
    // where
    if (parametros != null) {
      ParameterExpression<String> nombreUsuario = null;
      ParameterExpression<String> nombreFuncionalidad = null;
      ParameterExpression<Date> fecha = null;
      if (parametros.get("nombreUsuario") != null && !parametros.get("nombreUsuario").toString().isEmpty()) {
        nombreUsuario = criteriaBuilder.parameter(String.class, "sku");
      }
      if (parametros.get("nombreFuncionalidad") != null) {

      }
      if (parametros.get("fecha") != null) {

      }
    }
    // consulta
    TypedQuery<LogAuditoria> q = em.createQuery(criteriaQuery);
    List<LogAuditoria> result = q.getResultList();
    return result;
  }

}
