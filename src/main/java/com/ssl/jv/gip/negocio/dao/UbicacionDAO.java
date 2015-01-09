package com.ssl.jv.gip.negocio.dao;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import com.ssl.jv.gip.jpa.pojo.Ubicacion;

@Stateless
@LocalBean
public class UbicacionDAO extends GenericDAO<Ubicacion> implements UbicacionDAOLocal{

	private static final Logger LOGGER = Logger.getLogger(UbicacionDAO.class);

	public UbicacionDAO(){
		this.persistentClass = Ubicacion.class;
	}

	public List<Ubicacion> consultarUbicacionPorFiltro(Ubicacion pFiltro){

		List<Ubicacion> listado = new ArrayList<Ubicacion>();

		try{
			listado = em.createQuery("from Ubicacion WHERE esTienda = :esTienda order by nombre").
					setParameter("esTienda", pFiltro.getEsTienda()).
					getResultList();
		} catch(Exception e){

		}
		return listado;
	}

	public List<Ubicacion> consultarUbicacionesPorUsuario(String idUsuario){

		List<Ubicacion> lista = new ArrayList<Ubicacion>();

		try{
			String sql = "SELECT  ubicaciones.id, 	"
					+ "ubicaciones.nombre "
					+ "FROM usuariosXgeografias,ubicaciones "
					+ "WHERE ubicaciones.id=usuariosXgeografias.id_geografia "
					+ "AND usuariosXgeografias.id_usuario= '" + idUsuario + "' "
					+ "ORDER BY UPPER (ubicaciones.nombre) ASC";

			List<Object[]> listado = em.createNativeQuery(sql).getResultList();
			
			if(listado != null){
				for(Object[] objs : listado){
					Ubicacion dto = new Ubicacion();
					dto.setId(objs[0] != null ? Long.parseLong(objs[0].toString()) : null);
					dto.setNombre(objs[1] != null ? objs[1].toString() : null);
					
					lista.add(dto);
				}
			}
			
		} catch(Exception e){

		}
		return lista;
	}

}
