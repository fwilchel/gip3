package com.ssl.jv.gip.negocio.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;

import org.apache.log4j.Logger;
import org.primefaces.model.SortOrder;

import com.ssl.jv.gip.jpa.pojo.Documento;
import com.ssl.jv.gip.jpa.pojo.Funcionalidad;
import com.ssl.jv.gip.jpa.pojo.LogAuditoria;
import com.ssl.jv.gip.jpa.pojo.Usuario;

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

  @Override
  public Object[] consultarLogAuditoria(Map<String, Object> parametros, int first, int pageSize, String sortField, SortOrder sortOrder, boolean count) {
    LOGGER.trace("Metodo: <<consultarDocumentos (filtro, first, pageSize, sortField, sortOrder, count)>>");
    StringBuilder select = new StringBuilder();
    select.append("SELECT ");
    if (count) {
      select.append("COUNT(la) ");
    } else {
      select.append("la ");
    }
    StringBuilder from = new StringBuilder();
    from.append("FROM LogAuditoria la ");
    StringBuilder where = new StringBuilder();
    where.append("WHERE 1 = 1 ");
    if (parametros != null) {
      // construir la clausula where
      if (parametros.get("nombreUsuario") != null && !parametros.get("nombreUsuario").toString().isEmpty()) {
        where.append("AND UPPER(la.usuario.nombre) LIKE UPPER(:nombreUsuario) ");
      }
      if (parametros.get("nombreFuncionalidad") != null && !parametros.get("nombreFuncionalidad").toString().isEmpty()) {
        where.append("AND UPPER(la.funcionalidad.nombre) LIKE UPPER(:nombreFuncionalidad) ");
      }
      if (parametros.get("fecha") != null) {
        where.append("AND la.fecha = :fecha ");
      }
    }
    StringBuilder orderBy = new StringBuilder();
    if (!count && sortField != null && !sortField.equals("")) {
      orderBy.append("ORDER BY la.");
      orderBy.append(sortField);
      if (!sortOrder.equals(SortOrder.UNSORTED)) {
        if (sortOrder.equals(SortOrder.ASCENDING)) {
          orderBy.append(" ASC");
        } else {
          orderBy.append(" DESC");
        }
      }
    }
    StringBuilder jpql = new StringBuilder();
    jpql.append(select);
    jpql.append(from);
    jpql.append(where);
    jpql.append(orderBy);
    Query query = em.createQuery(jpql.toString());
    if (parametros != null) {
      // setear los parametros
      if (parametros.get("nombreUsuario") != null && !parametros.get("nombreUsuario").toString().isEmpty()) {
        query.setParameter("nombreUsuario", "%" + parametros.get("nombreUsuario") + "%");
      }
      if (parametros.get("nombreFuncionalidad") != null && !parametros.get("nombreFuncionalidad").toString().isEmpty()) {
        query.setParameter("nombreFuncionalidad", "%" + parametros.get("nombreFuncionalidad") + "%");
      }
      if (parametros.get("fecha") != null) {
        query.setParameter("fecha", parametros.get("nombreFuncionalidad"));
      }
    }
    LOGGER.debug(first + " " + pageSize + " " + jpql.toString());
    Object rta[] = new Object[2];
    if (count) {
      rta[0] = query.getSingleResult();
    } else {
      if (sortField != null && !sortField.equals("")) {
        query.setFirstResult(first);
      }
      query.setMaxResults(pageSize);
      rta[1] = (List<LogAuditoria>) query.getResultList();
    }
    return rta;
  }
}
