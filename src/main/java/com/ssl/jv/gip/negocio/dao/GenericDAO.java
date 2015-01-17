package com.ssl.jv.gip.negocio.dao;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
		em.remove(pojo);
		em.flush();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public T findByPK(Long id) {
		List list = em
				.createQuery(
						"from " + this.persistentClass.getName()
								+ " where id=?").setParameter(1, id)
				.getResultList();
		return (T) list.get(0);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
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
}
