package com.ssl.jv.gip.negocio.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

public abstract class TestBase {

  static private EntityManagerFactory emFactory;
  protected EntityManager em;

  @BeforeClass
  final public static void beforeClass() {
    try {
      emFactory = Persistence.createEntityManagerFactory("primary-test");
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  @Before
  final public void beforeTest() {
    em = emFactory.createEntityManager();
    em.getTransaction().begin();
  }

  @After
  final public void afterTest() {
    EntityTransaction transaction = em.getTransaction();
    if (transaction.isActive()) {
      transaction.rollback();
      // transaction.commit();
    }

    em.clear();
    em.close();
  }

  @AfterClass
  final public static void afterClass() {
    try {
      emFactory.close();
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  public abstract void beforeEachTest();

}
