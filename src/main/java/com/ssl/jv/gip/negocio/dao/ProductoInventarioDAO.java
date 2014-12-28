package com.ssl.jv.gip.negocio.dao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.primefaces.model.SortOrder;

import com.ssl.jv.gip.jpa.pojo.ProductosInventario;

@Stateless
@LocalBean
public class ProductoInventarioDAO extends GenericDAO<ProductosInventario> implements ProductoInventarioDAOLocal{

	private static final Logger LOGGER = Logger.getLogger(ProductoInventarioDAO.class);
	
	public ProductoInventarioDAO(){
		this.persistentClass = ProductosInventario.class;
	}	
	
	public Object[] consultar(ProductosInventario pi, int first, int pageSize, String sortField, SortOrder sortOrder, boolean count){
		Object rta[]=new Object[2];
		
		String query="SELECT "+(count?"COUNT(pi)":"pi")+" FROM ProductosInventario pi WHERE 1=1 " +
				(pi.getSku()!=null && !pi.getSku().equals("")?" AND pi.sku= :sku ":"")+
				(pi.getNombre()!=null && !pi.getNombre().equals("")?" AND pi.nombre= :nombre ":"")+
				(pi.getCategoriasInventario().getId()!=null && pi.getCategoriasInventario().getId()!=null && pi.getCategoriasInventario().getId()!=0?" AND pi.categoriasInventario= :categoria ":"")+
				(pi.getPais().getId()!=null && pi.getPais().getId()!=null && !pi.getPais().getId().equals("")?" AND pi.pais.id= :idPais ":"")+
				(pi.getDesactivado()!=null?" AND pi.desactivado= :desactivado ":"")+
				(!count && sortField!=null && !sortField.equals("")?" ORDER BY pi."+sortField+(sortOrder.equals(SortOrder.UNSORTED)?"":sortOrder.equals(SortOrder.ASCENDING)?" ASC":" DESC"):"");
		
		Query q = em.createQuery(query);

			if (pi.getSku()!=null && !pi.getSku().equals(""))
				q=q.setParameter("sku", pi.getSku());
			if (pi.getNombre()!=null && !pi.getNombre().equals(""))
				q=q.setParameter("nombre", pi.getNombre());
			if (pi.getCategoriasInventario()!=null && pi.getCategoriasInventario().getId()!=null && pi.getCategoriasInventario().getId()!=0)
				q=q.setParameter("categoria", pi.getCategoriasInventario());
			if (pi.getPais().getId()!=null && pi.getPais().getId()!=null && !pi.getPais().getId().equals(""))
				q=q.setParameter("idPais", pi.getPais().getId());
			if (pi.getDesactivado()!=null)
				q=q.setParameter("desactivado", pi.getDesactivado());

			System.out.println(first + " "+ pageSize+" "+query);
			
		if (count){
			rta[0]=q.getSingleResult();
		}else{
			if (sortField!=null && !sortField.equals(""))
			q.setFirstResult(first);
			q.setMaxResults(pageSize);
			rta[1]=(List<ProductosInventario>)q.getResultList();
		}
		return rta;
	}
}
