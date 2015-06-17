package com.ssl.jv.gip.negocio.dao;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
	 
	
	
	  @Override
	  public List<ComextRequerimientoexportacionDTO> consultarMarcacionEspecial(Long id) {

	   
	    System.out.println("id" + id);
	   

	    List<ComextRequerimientoexportacionDTO> lista = new ArrayList<ComextRequerimientoexportacionDTO>();
	    String query = "";

	    query = "select d.consecutivo_documento as consecutivoDocumento, pi.sku,pi.nombre as nombreProducto , rxp.mcajamaster as cajamaster" 
+ " ,rxp.mpallet as pallet ,rxp.tienemarcacion as observacion  ,rxp.observaciones from reqxproducto rxp inner join productos_inventario pi on rxp.producto=pi.id"
+ " inner join documentos d on d.id=rxp.documento"
+ " where requerimiento=:consecutivoDocumento";

	    lista = em.createNativeQuery(query, ComextRequerimientoexportacionDTO.class).setParameter("consecutivoDocumento", id).getResultList();

	    System.out.println("query: " + query);

	    return lista;

	  }
	 

	 
/*	 @Override
	  public FacturaDirectaDTO consultarRequerimientoExportacion(String strConsecutivoDocumento) {

	    FacturaDirectaDTO dto;

	    String query = "";
	    query = "SELECT  d.id as idDocumento,d.consecutivo_documento as consecutivoDocumento, d.fecha_generacion as fechaGeneracion," + " cli.nombre as nombreCliente,cli.direccion as direccionCliente ,cli.telefono  as telefonoCliente,cli.contacto as contactoCliente," + " d.observacion_documento as observacionDocumento , u.nombre as nombreUbicacion,"
	        + " d.subtotal as valorSubtotal ,d.descuento as valorDescuento ,d.valor_iva10 as  valorIva10  ,d.valor_iva16 as valorIva16, d.valor_iva5 as valorIva5, d.valor_total as valorTotal, " + " pv.nombre as nombrePuntoVenta ,pv.direccion as direccionPuntoVenta ,pv.telefono as telefonoPuntoVenta,ciupv.nombre as nombreCiudadPuntoVenta, " + " cli.nit as nitCliente," + " ciucli.nombre as nombreCiudadCliente, d.id_estado as estado , cli.descuento_cliente as descuentoCliente,"
	        + " (select d2.fecha_generacion as fechaGeneracionVD from documentos d2 where d2.consecutivo_documento=d.observacion_documento)," + " d.numero_factura as numeroFactura" + " FROM documentos d INNER JOIN clientes cli on d.id_cliente=cli.id " + " INNER JOIN ubicaciones u on d.id_ubicacion_destino=u.id " + " INNER JOIN punto_venta pv on pv.id=d.id_punto_venta " + " INNER JOIN ciudades ciupv on ciupv.id=pv.id_ciudad" + " INNER JOIN ciudades ciucli on ciucli.id=cli.id_ciudad"
	        + " WHERE d.consecutivo_documento= :consecutivoDocumento";

	    dto = (FacturaDirectaDTO) em.createNativeQuery(query, FacturaDirectaDTO.class).setParameter("consecutivoDocumento", strConsecutivoDocumento).getSingleResult();

	    

	 	    return dto;

	  }
	  */
	
}
