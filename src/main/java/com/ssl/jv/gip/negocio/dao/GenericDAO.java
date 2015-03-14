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
     * System.out.println(pt.getActualTypeArguments()[0]);
     * this.persistentClass = (Class<T>) ((ParameterizedType) getClass()
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
    List list = em
            .createQuery(
                    "from " + this.persistentClass.getName()
                    + " where id=?").setParameter(1, id)
            .getResultList();
    return (T) list.get(0);
  }

  @SuppressWarnings({"rawtypes", "unchecked"})
  public T findByPK(String id) {
    return em.find(persistentClass, id);
  }

  @SuppressWarnings("rawtypes")
  public List<?> findAll() {
    List list = em.createQuery("from " + this.persistentClass.getName())
            .getResultList();
    return list;
  }

  public List<?> findAllActivo() {
    List<?> list = em.createQuery(
            "from " + this.persistentClass.getName() + " WHERE activo=1")
            .getResultList();
    return list;
  }

  public List<?> findAllActivoBoolean() {
    List<?> list = em
            .createQuery(
                    "from " + this.persistentClass.getName()
                    + " WHERE activo=true").getResultList();
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
    return ((BigInteger) em.createNativeQuery(
            String.format("SELECT nextval('%s')", secuenceName))
            .getSingleResult()).longValue();
  }

  public Number consultarMaximoValorColumna(String column) {
    return (Number) em.createQuery(
            String.format("SELECT MAX(%s) FROM %s", column,
                    this.persistentClass.getName())).getSingleResult();
  }

  public static void main(String[] args) {
    try {
      Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
      Connection con = DriverManager.getConnection(
              "jdbc:sqlserver://FREDY-PC;databaseName=Procafecol", "sa",
              "FGWL750930");
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

  /**
   * Ejecuta la consulta nombrada que se recibe como parametro junto con un mapa en donde la llave seria el nombre del parametro en la consulta :PARAMETRO1 y el valor asociado a la llave seria el dato
   * enviado a la consulta.
   *
   * @param nombreConsulta Nombre de la consulta nombrada a ser ejecutada en el contexto de persistencia
   * @param parametros Mapa de parámetros utilizado para realizar la búsqueda en el contexto de persistencia
   * @param <T> generico
   * @return listado de entidades resultado de la consulta en el contexto de persistencia
   * @throws PersistenceException
   */
  public <T> List<T> buscarPorConsultaNombrada(String nombreConsulta, Map<String, Object> parametros) {
    LOGGER.info("Entro a: <<buscarPorConsultaNombrada>> nombreConsulta ->> {" + nombreConsulta + "}");
    try {
      Query consulta = em.createNamedQuery(nombreConsulta);
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

  /**
   * Ejecuta la consulta nombrada que se recibe como parametro junto con un mapa en donde la llave seria el nombre del parametro en la consulta :PARAMETRO1 y el valor asociado a la llave seria el dato
   * enviado a la consulta.
   *
   * @param consultaJPQL Cadena de consulta JPQL a ser ejecutada en el contexto de persistencia
   * @param parametros mapa de parámetros utilizado para realizar la búsqueda en el contexto de persistencia
   * @param <T> generico
   * @return listado de entidades resultado de la consulta en el contexto de persistencia
   * @throws PersistenceException
   */
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

  /**
   * 
   * @param <T>
   * @param consultaNativa
   * @param parametros
   * @return
   * @throws PersistenceException 
   */
  public <T> List<T> buscarPorConsultaNativa(String consultaNativa, Map<String, Object> parametros) {
    LOGGER.info("Entro a: <<buscarPorConsultaNativa>> consultaJPQL ->> {" + consultaNativa + "}");
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

  public <T> void ejecutarConsultaNombrada(String nombreConsulta, Map<String, Object> parametros) throws PersistenceException {
    LOGGER.info("Entro a: <<ejecutarConsultaNombrada>> consultaJPQL ->> {" + nombreConsulta + "}");
    try {
      Query consulta = em.createNamedQuery(nombreConsulta);
      for (String key : parametros.keySet()) {
        consulta.setParameter(key, parametros.get(key));
      }
      int registrosAfectados = consulta.executeUpdate();
      LOGGER.info("Sale de <<ejecutarConsultaNombrada>> con resultado ->> {" + registrosAfectados + "} registro(s) afectado(s)");
    } catch (Exception ebd) {
      LOGGER.error("Error en <<ejecutarConsultaNombrada>>", ebd.getCause());
      throw new PersistenceException(ebd);
    }
  }

  public <T> void ejecutarConsultaJPQL(String consultaJPQL, Map<String, Object> parametros) throws PersistenceException {
    LOGGER.info("Entro a: <<ejecutarConsultaJPQL>> consultaJPQL ->> {" + consultaJPQL + "}");
    try {
      Query consulta = em.createQuery(consultaJPQL);
      for (String key : parametros.keySet()) {
        consulta.setParameter(key, parametros.get(key));
      }
      int registrosAfectados = consulta.executeUpdate();
      LOGGER.info("Sale de <<ejecutarConsultaJPQL>> con resultado ->> {" + registrosAfectados + "} registro(s) afectado(s)");
    } catch (Exception ebd) {
      LOGGER.error("Error en <<ejecutarConsultaJPQL>>", ebd.getCause());
      throw new PersistenceException(ebd);
    }
  }

  public <T> void ejecutarConsultaNativa(String consultaNativa, Map<String, Object> parametros) throws PersistenceException {
    LOGGER.info("Entro a: <<ejecutarConsultaNativa>> consultaJPQL ->> {" + consultaNativa + "}");
    try {
      Query consulta = em.createNativeQuery(consultaNativa);
      for (String key : parametros.keySet()) {
        consulta.setParameter(key, parametros.get(key));
      }
      int registrosAfectados = consulta.executeUpdate();
      LOGGER.info("Sale de <<ejecutarConsultaNativa>> con resultado ->> {" + registrosAfectados + "} registro(s) afectado(s)");
    } catch (Exception ebd) {
      LOGGER.error("Error en <<ejecutarConsultaNativa>>", ebd.getCause());
      throw new PersistenceException(ebd);
    }
  }

}
