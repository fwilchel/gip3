package com.ssl.jv.gip.negocio.dao;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.apache.log4j.Logger;

public class GenericDAO<T> {

  public static final String PORCENTAJE_LIKE = "%";
  private static final Logger LOGGER = Logger.getLogger(GenericDAO.class);

  @PersistenceContext(unitName = "primary")
  EntityManager em;

  protected Class<T> persistentClass;

  @SuppressWarnings("unchecked")
  public GenericDAO() {
    /*
     * System.out.println(getClass());
     * System.out.println(getClass().getGenericSuperclass()); Type
     * type=getClass().getGenericSuperclass(); ParameterizedType pt =
     * (ParameterizedType) type;
     * System.out.println(pt.getActualTypeArguments()[0]); this.persistentClass
     * = (Class<T>) ((ParameterizedType) getClass()
     * .getGenericSuperclass()).getActualTypeArguments()[0];
     */
  }

  public T add(T pojo) {
    em.persist(pojo);
    return pojo;
  }

  public void update(T pojo) {
    em.merge(pojo);
    em.flush();
  }

  public void delete(T pojo) {
    em.remove(em.contains(pojo) ? pojo : em.merge(pojo));
    em.flush();
  }

  @SuppressWarnings({"rawtypes", "unchecked"})
  public T findByPK(Long id) {
    List list = em.createQuery("from " + this.persistentClass.getName() + " where id=?").setParameter(1, id).getResultList();
    return (T) list.get(0);
  }

  @SuppressWarnings({"rawtypes", "unchecked"})
  public T findByPK(String id) {
    return em.find(persistentClass, id);
  }

  @SuppressWarnings("rawtypes")
  public List<?> findAll() {
    List list = em.createQuery("from " + this.persistentClass.getName()).getResultList();
    return list;
  }

  public List<?> findAllActivo() {
    List<?> list = em.createQuery("from " + this.persistentClass.getName() + " WHERE activo=1").getResultList();
    return list;
  }

  public List<?> findAllActivoBoolean() {
    List<?> list = em.createQuery("from " + this.persistentClass.getName() + " WHERE activo=true").getResultList();
    return list;
  }

  /**
   * @see IGenericDAO#deleteMultiple(java.util.List)
   * @author Felipe Cifuentes
   */
  public void deleteMultiple(List<T> pojoList) {
    for (T pojo : pojoList) {
      em.remove(pojo);
    }
    em.flush();
  }

  public Long consultarProximoValorSecuencia(String secuenceName) {
    return ((BigInteger) em.createNativeQuery(String.format("SELECT nextval('%s')", secuenceName)).getSingleResult()).longValue();
  }

  public Number consultarMaximoValorColumna(String column) {
    return (Number) em.createQuery(String.format("SELECT MAX(%s) FROM %s", column, this.persistentClass.getName())).getSingleResult();
  }

  public static void main(String[] args) {
    try {
      Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
      Connection con = DriverManager.getConnection("jdbc:sqlserver://FREDY-PC;databaseName=Procafecol", "sa", "FGWL750930");
      System.out.println(con);
      con.close();

    } catch (Exception e) {
      LOGGER.error(e);
    }
  }

  public EntityManager getEm() {
    return em;
  }

  public void setEm(EntityManager em) {
    this.em = em;
  }

  public <T> List<T> buscarPorConsultaNombrada(String consultaNombrada, Map<String, Object> parametros) {
    LOGGER.info("Entro a: <<buscarPorConsultaNombrada>> consultaNombrada ->> {" + consultaNombrada + "}");
    try {
      Query consulta = em.createNamedQuery(consultaNombrada);
      for (String key : parametros.keySet()) {
        consulta.setParameter(key, parametros.get(key));
      }
      List<T> o = consulta.getResultList();
      LOGGER.info("Sale de <<buscarPorConsultaNombrada>> con resultado ->> {" + o + "}");
      return o;
    } catch (Exception ebd) {
      LOGGER.error("Error en <<buscarPorConsultaNombrada>>", ebd.getCause());
      throw new PersistenceException(ebd);
    }
  }

  public <T> T buscarRegistroPorConsultaNombrada(String consultaNombrada, Map<String, Object> parametros) {
    LOGGER.info("Entro a: <<buscarRegistroPorConsultaNombrada>> consultaNombrada ->> {" + consultaNombrada + "}");
    try {
      Query consulta = em.createNamedQuery(consultaNombrada);
      for (String key : parametros.keySet()) {
        consulta.setParameter(key, parametros.get(key));
      }
      T o = (T) consulta.getSingleResult();
      LOGGER.info("Sale de <<buscarRegistroPorConsultaNombrada>> con resultado ->> {" + o + "}");
      return o;
    } catch (Exception ebd) {
      LOGGER.error("Error en <<buscarRegistroPorConsultaNombrada>>", ebd.getCause());
      throw new PersistenceException(ebd);
    }
  }

  public <T> List<T> buscarPorConsultaJPQL(String consultaJPQL, Map<String, Object> parametros) {
    LOGGER.info("Entro a: <<buscarPorConsultaJPQL>> consultaJPQL ->> {" + consultaJPQL + "}");
    try {
      Query consulta = em.createQuery(consultaJPQL);
      for (String key : parametros.keySet()) {
        consulta.setParameter(key, parametros.get(key));
      }
      List<T> o = consulta.getResultList();
      LOGGER.info("Sale de <<buscarPorConsultaJPQL>> con resultado ->> {" + o + "}");
      return o;
    } catch (Exception ebd) {
      LOGGER.error("Error en <<buscarPorConsultaJPQL>>", ebd.getCause());
      throw new PersistenceException(ebd);
    }
  }

  public <T> T buscarRegistroPorConsultaJPQL(String consultaJPQL, Map<String, Object> parametros) {
    LOGGER.info("Entro a: <<buscarRegistroPorConsultaJPQL>> consultaNombrada ->> {" + consultaJPQL + "}");
    try {
      Query consulta = em.createQuery(consultaJPQL);
      for (String key : parametros.keySet()) {
        consulta.setParameter(key, parametros.get(key));
      }
      T o = (T) consulta.getSingleResult();
      LOGGER.info("Sale de <<buscarRegistroPorConsultaJPQL>> con resultado ->> {" + o + "}");
      return o;
    } catch (Exception ebd) {
      LOGGER.error("Error en <<buscarRegistroPorConsultaJPQL>>", ebd.getCause());
      throw new PersistenceException(ebd);
    }
  }

  public <T> List<T> buscarPorConsultaNativa(String consultaNativa, Map<String, Object> parametros) {
    LOGGER.info("Entro a: <<buscarPorConsultaNativa>> consultaNativa ->> {" + consultaNativa + "}");
    try {
      Query consulta = em.createNativeQuery(consultaNativa);
      for (String key : parametros.keySet()) {
        consulta.setParameter(key, parametros.get(key));
      }
      List<T> o = consulta.getResultList();
      LOGGER.info("Sale de <<buscarPorConsultaNativa>> con resultado ->> {" + o + "}");
      return o;
    } catch (Exception ebd) {
      LOGGER.error("Error en <<buscarPorConsultaNativa>>", ebd.getCause());
      throw new PersistenceException(ebd);
    }
  }

  public <T> T buscarRegistroPorConsultaNativa(String consultaNativa, Map<String, Object> parametros) {
    LOGGER.info("Entro a: <<buscarRegistroPorConsultaNativa>> consultaNativa ->> {" + consultaNativa + "}");
    try {
      Query consulta = em.createNativeQuery(consultaNativa);
      for (String key : parametros.keySet()) {
        consulta.setParameter(key, parametros.get(key));
      }
      T o = (T) consulta.getSingleResult();
      LOGGER.info("Sale de <<buscarRegistroPorConsultaNativa>> con resultado ->> {" + o + "}");
      return o;
    } catch (Exception ebd) {
      LOGGER.error("Error en <<buscarRegistroPorConsultaNativa>>", ebd.getCause());
      throw new PersistenceException(ebd);
    }
  }

  public <T> List<T> buscarPorConsultaNativa(String consultaNativa, Class<T> tipo, Map<String, Object> parametros) {
    LOGGER.info("Entro a: <<buscarPorConsultaNativa>> consultaNativa ->> {" + consultaNativa + "}");
    try {
      Query consulta = em.createNativeQuery(consultaNativa, tipo);
      for (String key : parametros.keySet()) {
        consulta.setParameter(key, parametros.get(key));
      }
      List<T> o = consulta.getResultList();
      LOGGER.info("Sale de <<buscarPorConsultaNativa>> con resultado ->> {" + o + "}");
      return o;
    } catch (Exception ebd) {
      LOGGER.error("Error en <<buscarPorConsultaNativa>>", ebd.getCause());
      throw new PersistenceException(ebd);
    }
  }

  public <T> T buscarRegistroPorConsultaNativa(String consultaNativa, Class<T> tipo, Map<String, Object> parametros) {
    LOGGER.info("Entro a: <<buscarRegistroPorConsultaNativa>> consultaNativa ->> {" + consultaNativa + "}");
    try {
      Query consulta = em.createNativeQuery(consultaNativa, tipo);
      for (String key : parametros.keySet()) {
        consulta.setParameter(key, parametros.get(key));
      }
      T o = (T) consulta.getSingleResult();
      LOGGER.info("Sale de <<buscarRegistroPorConsultaNativa>> con resultado ->> {" + o + "}");
      return o;
    } catch (Exception ebd) {
      LOGGER.error("Error en <<buscarRegistroPorConsultaNativa>>", ebd.getCause());
      throw new PersistenceException(ebd);
    }
  }

  public int ejecutarConsultaNombrada(String nombreConsulta, Map<String, Object> parametros) throws PersistenceException {
    LOGGER.info("Entro a: <<ejecutarConsultaNombrada>> consultaJPQL ->> {" + nombreConsulta + "}");
    try {
      Query consulta = em.createNamedQuery(nombreConsulta);
      for (String key : parametros.keySet()) {
        consulta.setParameter(key, parametros.get(key));
      }
      int registrosAfectados = consulta.executeUpdate();
      LOGGER.info("Sale de <<ejecutarConsultaNombrada>> con resultado ->> {" + registrosAfectados + "} registro(s) afectado(s)");
      return registrosAfectados;
    } catch (Exception ebd) {
      LOGGER.error("Error en <<ejecutarConsultaNombrada>>", ebd.getCause());
      throw new PersistenceException(ebd);
    }
  }

  public int ejecutarConsultaJPQL(String consultaJPQL, Map<String, Object> parametros) throws PersistenceException {
    LOGGER.info("Entro a: <<ejecutarConsultaJPQL>> consultaJPQL ->> {" + consultaJPQL + "}");
    try {
      Query consulta = em.createQuery(consultaJPQL);
      for (String key : parametros.keySet()) {
        consulta.setParameter(key, parametros.get(key));
      }
      int registrosAfectados = consulta.executeUpdate();
      LOGGER.info("Sale de <<ejecutarConsultaJPQL>> con resultado ->> {" + registrosAfectados + "} registro(s) afectado(s)");
      return registrosAfectados;
    } catch (Exception ebd) {
      LOGGER.error("Error en <<ejecutarConsultaJPQL>>", ebd.getCause());
      throw new PersistenceException(ebd);
    }
  }

  public int ejecutarConsultaNativa(String consultaNativa, Map<String, Object> parametros) throws PersistenceException {
    LOGGER.info("Entro a: <<ejecutarConsultaNativa>> consultaJPQL ->> {" + consultaNativa + "}");
    try {
      Query consulta = em.createNativeQuery(consultaNativa);
      for (String key : parametros.keySet()) {
        consulta.setParameter(key, parametros.get(key));
      }
      int registrosAfectados = consulta.executeUpdate();
      LOGGER.info("Sale de <<ejecutarConsultaNativa>> con resultado ->> {" + registrosAfectados + "} registro(s) afectado(s)");
      return registrosAfectados;
    } catch (Exception ebd) {
      LOGGER.error("Error en <<ejecutarConsultaNativa>>", ebd.getCause());
      throw new PersistenceException(ebd);
    }
  }

}
