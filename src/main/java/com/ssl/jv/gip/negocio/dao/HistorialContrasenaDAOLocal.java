package com.ssl.jv.gip.negocio.dao;

import java.util.List;

import javax.ejb.Local;

import com.ssl.jv.gip.jpa.pojo.HistorialContrasena;
import com.ssl.jv.gip.jpa.pojo.Usuario;

@Local
public interface HistorialContrasenaDAOLocal extends IGenericDAO<HistorialContrasena>{
	
	public List<HistorialContrasena> findByUsuarioContrasenaHoy(Usuario u);
	public List<HistorialContrasena> findByUsuarioContrasena(Usuario u);
	public List<HistorialContrasena> findByUsuarioId(String usuarioId);
	public void deleteAntiguo(HistorialContrasena hc);
	
}
