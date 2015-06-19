package com.ssl.jv.gip.negocio.dao;



import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;













import com.ssl.jv.gip.jpa.pojo.ComextRequerimientoexportacion;
import com.ssl.jv.gip.negocio.dto.ComextRequerimientoexportacionDTO;



@Stateless
@LocalBean
public class ComextRequerimientoExportacionDAO  extends GenericDAO<ComextRequerimientoexportacion> implements  ComextRequerimientoExportacionDAOLocal {

	
	public ComextRequerimientoExportacionDAO () {
		    this.persistentClass = ComextRequerimientoexportacion.class;
		  }
	 
	
	
	
	  public List<ComextRequerimientoexportacionDTO> consultarMarcacionEspecial(Long id) {

	   
	    System.out.println("Requimiento_id" + id);
	   

	    //List<ComextRequerimientoexportacionDTO> lista = new ArrayList<ComextRequerimientoexportacionDTO>();
	    String query = "";

	    query = "select d.consecutivo_documento as consecutivodocumento, pi.sku as sku,pi.nombre as nombreproducto , rxp.mcajamaster as cajamaster" 
+ " ,rxp.mpallet as pallet ,rxp.tienemarcacion as observacion  ,rxp.observaciones as observaciones ,rxp.id as id from reqxproducto rxp inner join productos_inventario pi on rxp.producto=pi.id"
+ " inner join documentos d on d.id=rxp.documento"
+ " where rxp.requerimiento=:consecutivoDocumento";

	    List<ComextRequerimientoexportacionDTO> listado  = em.createNativeQuery(query, ComextRequerimientoexportacionDTO.class).setParameter("consecutivoDocumento", id).getResultList();
	    
	   // List<DocumentoCostosLogisticosDTO> listado = em.createNativeQuery(sql, DocumentoCostosLogisticosDTO.class).getResultList();
	  
	    System.out.println("queryReqExp1: " + query);
	    
	    
	    System.out.println("queryReqExp2: " + listado.size());
	    
	    
	    for (ComextRequerimientoexportacionDTO produ : listado) {
	        
	    	System.out.println("sku:"+ produ.getSku());
	        
	      }
	    

	    return listado;

	  }
	 

	 

	
}
