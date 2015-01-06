package com.ssl.jv.gip.negocio.dao;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import com.ssl.jv.gip.jpa.pojo.TerminoIncoterm;


@Stateless
@LocalBean
public class TerminoIncotermDAO extends GenericDAO<TerminoIncoterm> implements TerminoIncotermDAOLocal{
	
	private static final Logger LOGGER = Logger.getLogger(TerminoIncotermDAO.class);
	
	public TerminoIncotermDAO(){
		this.persistentClass = TerminoIncoterm.class;
	}
	
public List<TerminoIncoterm> consultarListaIncontermPorCliente(Long idCliente){
		
		List<TerminoIncoterm> lista = new ArrayList<TerminoIncoterm>();

		String sql= "SELECT  incoterm_x_cliente.id_incoterm,termino_incoterm.descripcion AS NOMBRE_INCOTERM "
		          + "FROM incoterm_x_cliente " 
		          + "INNER JOIN termino_incoterm ON incoterm_x_cliente.id_incoterm = termino_incoterm.id "
		          + "AND incoterm_x_cliente.id_cliente = " + idCliente
		          + " ORDER BY UPPER (termino_incoterm.descripcion) ASC ";
		
		List<Object[]> listado = em.createNativeQuery(sql).getResultList();
		
		if(listado != null){
			for(Object[] objs : listado){
				TerminoIncoterm dto = new TerminoIncoterm();
				
				dto.setId(objs[0] != null ? Long.parseLong(objs[0].toString()) : null);
				dto.setDescripcion(objs[1] != null ? objs[1].toString() : null);
				
				lista.add(dto);
			}
		}
		
		return lista;

	}
	
}
