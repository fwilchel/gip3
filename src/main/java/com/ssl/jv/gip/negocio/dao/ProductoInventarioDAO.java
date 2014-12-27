package com.ssl.jv.gip.negocio.dao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import com.ssl.jv.gip.jpa.pojo.ProductosInventario;

@Stateless
@LocalBean
public class ProductoInventarioDAO extends GenericDAO<ProductosInventario> implements ProductoInventarioDAOLocal{

	private static final Logger LOGGER = Logger.getLogger(ProductoInventarioDAO.class);
	
	public ProductoInventarioDAO(){
		this.persistentClass = ProductosInventario.class;
	}	
	
	public List<ProductosInventario> consultar(ProductosInventario pi){
		Query q = em.createQuery("SELECT pi FROM ProductosInventario pi WHERE 1=1 " +
				(pi.getSku()!=null && !pi.getSku().equals("")?" AND pi.sku= :sku ":"")+
				(pi.getNombre()!=null && !pi.getNombre().equals("")?" AND pi.nombre= :nombre ":"")+
				(pi.getCategoriasInventario().getId()!=null && pi.getCategoriasInventario().getId()!=0?" AND pi.categoriasInventario= :categoria ":"")+
				(pi.getPais().getId()!=null && !pi.getPais().getId().equals("")?" AND pi.pais.id= :idPais ":"")+
				(pi.getDesactivado()!=null?" AND pi.desactivado= :desactivado ":""));
			if (pi.getSku()!=null && !pi.getSku().equals(""))
				q=q.setParameter("sku", pi.getSku());
			if (pi.getNombre()!=null && !pi.getNombre().equals(""))
				q=q.setParameter("nombre", pi.getNombre());
			if (pi.getCategoriasInventario()!=null && pi.getCategoriasInventario().getId()!=0)
				q=q.setParameter("categoria", pi.getCategoriasInventario());
			if (pi.getPais().getId()!=null && !pi.getPais().getId().equals(""))
				q=q.setParameter("idPais", pi.getPais().getId());
			if (pi.getDesactivado()!=null)
				q=q.setParameter("desactivado", pi.getDesactivado());
		return (List<ProductosInventario>)q.getResultList();
	}
}
