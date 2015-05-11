package com.ssl.jv.gip.negocio.dao;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import com.ssl.jv.gip.jpa.pojo.HistorialContrasena;
import com.ssl.jv.gip.jpa.pojo.Usuario;

@Stateless
@LocalBean
public class HistorialContrasenaDAO extends GenericDAO<HistorialContrasena> implements HistorialContrasenaDAOLocal {

  private static final Logger LOGGER = Logger.getLogger(HistorialContrasenaDAO.class);

  public HistorialContrasenaDAO() {
    this.persistentClass = HistorialContrasena.class;
  }

  @SuppressWarnings("unchecked")
  public List<HistorialContrasena> findByUsuarioContrasenaHoy(Usuario u) {
    Timestamp ti = new Timestamp(new Date().getTime());
    return (List<HistorialContrasena>) this.em.createNamedQuery("HistorialContrasena.findByUsuarioPwdHoy").setParameter("id", u.getId()).setParameter("contrasena", u.getContrasena()).setParameter("hoy", ti).getResultList();
  }

  @SuppressWarnings("unchecked")
  public List<HistorialContrasena> findByUsuarioContrasena(Usuario u) {
    return (List<HistorialContrasena>) this.em.createNamedQuery("HistorialContrasena.findByUsuarioPwd").setParameter("id", u.getId()).setParameter("contrasena", u.getContrasena()).getResultList();
  }

  @SuppressWarnings("unchecked")
  public List<HistorialContrasena> findByUsuarioId(String usuarioId) {
    return (List<HistorialContrasena>) this.em.createNamedQuery("HistorialContrasena.findByUsuarioId").setParameter("id", usuarioId).getResultList();
  }

  public void deleteAntiguo(HistorialContrasena hc) {
    this.em.createQuery("DELETE FROM HistorialContrasena hc WHERE hc.pk.usuarioId= :usuarioId AND hc.fechaCaducidad IN (SELECT MIN(hc2.fechaCaducidad) FROM HistorialContrasena hc2 WHERE hc2.pk.usuarioId= :usuarioId)").setParameter("usuarioId", hc.getPk().getUsuarioId()).executeUpdate();
  }

}
