package com.ssl.jv.gip.negocio.dao;



import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.ssl.jv.gip.jpa.pojo.ComextRequerimientoexportacion;
import com.ssl.jv.gip.negocio.dto.ComextRequerimientoexportacionDTO;
import com.ssl.jv.gip.negocio.dto.DocumentoIncontermDTO;
import com.ssl.jv.gip.negocio.dto.DocumentoRequerimientoExportacionDTO;



@Stateless
@LocalBean
public class ComextRequerimientoExportacionDAO  extends GenericDAO<ComextRequerimientoexportacion> implements  ComextRequerimientoExportacionDAOLocal {

	
	public ComextRequerimientoExportacionDAO () {
		    this.persistentClass = ComextRequerimientoexportacion.class;
		  }
	 
	
	
	
	  public List<ComextRequerimientoexportacionDTO> consultarMarcacionEspecial(Long id) {

	   
	    //System.out.println("Requimiento_id" + id);
	    String query = "";

	    query = "select d.consecutivo_documento as consecutivodocumento, pi.sku as sku,pi.nombre as nombreproducto , rxp.mcajamaster as cajamaster" 
        + " ,rxp.mpallet as pallet ,rxp.mproducto as producto  ,rxp.observaciones as observaciones ,rxp.id as id ,rxp.producto as idproducto , rxp.documento as iddocumento"
        + " ,rxp.tienemarcacion as tienemarcacion"	    		
        + " from reqxproducto rxp inner join productos_inventario pi on rxp.producto=pi.id"
        + " inner join documentos d on d.id=rxp.documento"
        + " where rxp.requerimiento=:consecutivoDocumento";

	    List<ComextRequerimientoexportacionDTO> listado  = em.createNativeQuery(query, ComextRequerimientoexportacionDTO.class).setParameter("consecutivoDocumento", id).getResultList();
	    
	   	    

	    return listado;

	  }
	 

	 
	  public List<ComextRequerimientoexportacionDTO> crearMarcacionEspecial(String strDocs) {

		   
		    
		    String query = "";

		    query = "SELECT pxd.id_producto as idproducto , pi.sku as sku, pi.nombre as nombreproducto,d.id as iddocumento, d.consecutivo_documento  as consecutivodocumento"
		    		+",false as cajamaster , false as pallet , false as producto , null as observaciones , row_number() over (ORDER BY d.consecutivo_documento) as id , null as tienemarcacion"
		    		+" from documentos d inner join productosxdocumentos pxd on d.id=pxd.id_documento inner join productos_inventario pi on pxd.id_producto=pi.id"
		    		+" where d.id in (" + strDocs + ")";

		    List<ComextRequerimientoexportacionDTO> listado  = em.createNativeQuery(query, ComextRequerimientoexportacionDTO.class).getResultList();
		    
		    

		    return listado;

		  }
	  
	  
	  /**
	   * Actualiza el estado del Requerimiento.
	   *
	   */
	  public void actualizarEstadoRequerimientoExportacion(DocumentoRequerimientoExportacionDTO requerimiento) {
	    try {
	      StringBuilder sql = new StringBuilder();
	      sql.append("UPDATE comext_requerimientoexportacion SET " + "estado = " + requerimiento.getIdEstado() + " WHERE  id = " + requerimiento.getIdDocumento());

	      int q = em.createNativeQuery(sql.toString()).executeUpdate();

	    } catch (Exception e) {

	    }
	  }



	  
	/*  public List<DocumentoRequerimientoExportacionDTO> consultarComextRequerimientoExportacion(Long id) {

		   
		    //System.out.println("Requimiento_id" + id);
		    String query = "";

		    query = "select d.consecutivo_documento as consecutivodocumento, pi.sku as sku,pi.nombre as nombreproducto , rxp.mcajamaster as cajamaster" 
	        + " ,rxp.mpallet as pallet ,rxp.mproducto as producto  ,rxp.observaciones as observaciones ,rxp.id as id ,rxp.producto as idproducto , rxp.documento as iddocumento"
	        + " ,rxp.tienemarcacion as tienemarcacion"	    		
	        + " from reqxproducto rxp inner join productos_inventario pi on rxp.producto=pi.id"
	        + " inner join documentos d on d.id=rxp.documento"
	        + " where rxp.requerimiento=:consecutivoDocumento";

		    List<DocumentoRequerimientoExportacionDTO> listado  = em.createNativeQuery(query, DocumentoRequerimientoExportacionDTO.class).setParameter("consecutivoDocumento", id).getResultList();
		    
		   	    

		    return listado;

		  }

	
	  */
	  

	
}
