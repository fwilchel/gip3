package com.ssl.jv.gip.negocio.dao;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import com.ssl.jv.gip.jpa.pojo.CuentaContable;
import com.ssl.jv.gip.negocio.dto.CuentaContableDTO;
import com.ssl.jv.gip.web.mb.util.ConstantesDocumento;

@Stateless
@LocalBean
public class CuentaContableDAO extends GenericDAO<CuentaContable> implements CuentaContableDAOLocal{

	private static final Logger LOGGER = Logger.getLogger(CuentaContableDAO.class);
	
	public CuentaContableDAO(){
		this.persistentClass = CuentaContable.class;
	}	
	
	public List<CuentaContableDTO> consultarReporteFacturasFX(String consecDoc, String fechaIni, String fechaFin){
		
		List<CuentaContableDTO> respuesta = new ArrayList<CuentaContableDTO>();
		String sql;
		
		if (consecDoc.equals("") || consecDoc.equals(null))
		{			
			
			sql="select  documentos.id as id_documento,	 productos_inventario_comext.id_cuenta_contable_ce as id, cuenta_contable.descripcion, cuenta_contable.indicador_iva," 
			 +" to_char(documentos.fecha_generacion,'YYYY-MM-DD') as fecha_generacion, documentos.consecutivo_documento,"
			 +" documentos.valor_total as total_factura, clientes.codigo_sap, ubicaciones.nombre as nombre_ubicacion, ubicaciones.objeto_co,"
			 +" clientes.nombre as nombre_cliente,"
			 + "sum (productosXdocumentos.valor_total) as totalxcta_contable , "
			 + "(documento_x_negociacion.costo_entrega +documento_x_negociacion.costo_seguro+ documento_x_negociacion.costo_flete) as costos_log,"
			 +" max(sum (productosXdocumentos.valor_total)) OVER (Partition By documentos.id ),"
			 +" (CASE WHEN (max(sum (productosXdocumentos.valor_total)) OVER (Partition By documentos.id )=sum (productosXdocumentos.valor_total)) THEN  sum (productosXdocumentos.valor_total) + (documento_x_negociacion.costo_entrega +documento_x_negociacion.costo_seguro+ documento_x_negociacion.costo_flete)"
			 +" else sum (productosXdocumentos.valor_total) END) as total , "
			 + "documento_x_negociacion.solicitud_cafe "
			 + "from productos_inventario_comext left outer join  cuenta_contable on   productos_inventario_comext.id_cuenta_contable_ce=cuenta_contable.id,"
			 +" productosXdocumentos,  documentos, clientes, ubicaciones, documento_x_negociacion" 
									       	   + " where productos_inventario_comext.id_producto = productosXdocumentos.id_producto"
									       	   + " and documentos.id = productosXdocumentos.id_documento"
									       	   + " and clientes.id = documentos.id_cliente"
									       	   + " and ubicaciones.id = documentos.id_ubicacion_destino"
									       	   + " and documentos.id = documento_x_negociacion.id_documento"
											   + " and documentos.fecha_generacion between '" + fechaIni + "' and '" + fechaFin + "'"
											   + " and documentos.id_tipo_documento = 25" 
									       	   + " and productos_inventario_comext.id_cuenta_contable_ce is not null"
											   + " and documentos.id_estado in (" + ConstantesDocumento.GENERADO + "," + ConstantesDocumento.IMPRESO + ")"
			+ " group by  documentos.id, productos_inventario_comext.id_cuenta_contable_ce," 
			+ " cuenta_contable.descripcion, cuenta_contable.indicador_iva,to_char(documentos.fecha_generacion,'YYYY-MM-DD') ,"
			+ " documentos.consecutivo_documento, documentos.valor_total, clientes.codigo_sap, ubicaciones.nombre,ubicaciones.objeto_co ,clientes.nombre,"
			+ " (documento_x_negociacion.costo_entrega +documento_x_negociacion.costo_seguro+ documento_x_negociacion.costo_flete) , documento_x_negociacion.solicitud_cafe"
			+ " order by documentos.id"; 
			
		}
		else
		{
			sql = "select documentos.id as id_documento, productos_inventario_comext.id_cuenta_contable_ce as id, cuenta_contable.descripcion, cuenta_contable.indicador_iva,"
			       	   + "to_char(documentos.fecha_generacion,'YYYY-MM-DD') as fecha_generacion, documentos.consecutivo_documento," 
			       	   + "documentos.valor_total as total_factura, clientes.codigo_sap, ubicaciones.nombre as nombre_ubicacion, ubicaciones.objeto_co,"
			       	   + "clientes.nombre as nombre_cliente," 
			       	   + "productosXdocumentos.valor_total as total, "
			       	   + "0.0 as base_iva, "
			       	   + "productosXdocumentos.valor_total + documento_x_negociacion.costo_entrega + documento_x_negociacion.costo_seguro + documento_x_negociacion.costo_flete + documento_x_negociacion.otros_gastos as total_con_costos,"
			       	   + "documento_x_negociacion.solicitud_cafe"
			       	   + " from productos_inventario_comext, productosXdocumentos, cuenta_contable, documentos, clientes, ubicaciones, documento_x_negociacion"
			       	   + " where productos_inventario_comext.id_producto = productosXdocumentos.id_producto"
			       	   + " and cuenta_contable.id = productos_inventario_comext.id_cuenta_contable_ce"
			       	   + " and documentos.id = productosXdocumentos.id_documento"
			       	   + " and clientes.id = documentos.id_cliente"
			       	   + " and ubicaciones.id = documentos.id_ubicacion_destino"
			       	   + " and documentos.id = documento_x_negociacion.id_documento"
					   + " and documentos.consecutivo_documento = '" + consecDoc + "'"
					   + " and documentos.id_tipo_documento = 25" 
			       	   + " and productos_inventario_comext.id_cuenta_contable_ce is not null" 
					   + " and documentos.id_estado in (" + ConstantesDocumento.GENERADO + "," + ConstantesDocumento.IMPRESO + ")"
			   		   + " order by documentos.id, productos_inventario_comext.id_cuenta_contable_ce asc";						
		}
		
		List<Object[]> listado = em.createNativeQuery(sql).getResultList();
		
		if(listado != null && !listado.isEmpty()){
			for(Object[] obj : listado){
				CuentaContableDTO dto = new CuentaContableDTO();
				
				if (consecDoc.equals("") || consecDoc.equals(null)){
					dto.setIntIdDocumento(obj[0] != null ? new Long(obj[0].toString()) : null);				
					dto.setLngId(obj[1] != null ? new Long(obj[1].toString()) : null);				
					dto.setStrDescripcion(obj[2] != null ? obj[2].toString() : "");
					dto.setStrIndicadorIVA(obj[3] != null ? obj[3].toString() : "");				
					dto.setStrFechaGeneracion(obj[4] != null ? obj[4].toString() : "");				
					dto.setStrConsecutivoDoc(obj[5] != null ? obj[5].toString() : "");
					dto.setDblTotalFactura(obj[6] != null ? new Double(obj[6].toString()) : null);
					dto.setStrCodigoSAP(obj[7] != null ? obj[7].toString() : "");
					dto.setStrNomUbicacion(obj[8] != null ? obj[8].toString() : "");
					dto.setLngObjetoCO(obj[9] != null ? new Long(obj[9].toString()) : null);
					dto.setStrNomCliente(obj[10] != null ? obj[10].toString() : "");									
					dto.setDblTotal(obj[14] != null ? new Double(obj[14].toString()) : null);				
					dto.setBlnSolicitudCafe(obj[15] != null ? new Boolean(obj[15].toString()) : null);
					
				}else{
					dto.setIntIdDocumento(obj[0] != null ? new Long(obj[0].toString()) : null);				
					dto.setLngId(obj[1] != null ? new Long(obj[1].toString()) : null);				
					dto.setStrDescripcion(obj[2] != null ? obj[2].toString() : "");
					dto.setStrIndicadorIVA(obj[3] != null ? obj[3].toString() : "");				
					dto.setStrFechaGeneracion(obj[4] != null ? obj[4].toString() : "");				
					dto.setStrConsecutivoDoc(obj[5] != null ? obj[5].toString() : "");
					dto.setDblTotalFactura(obj[6] != null ? new Double(obj[6].toString()) : null);
					dto.setStrCodigoSAP(obj[7] != null ? obj[7].toString() : "");
					dto.setStrNomUbicacion(obj[8] != null ? obj[8].toString() : "");
					dto.setLngObjetoCO(obj[9] != null ? new Long(obj[9].toString()) : null);
					dto.setStrNomCliente(obj[10] != null ? obj[10].toString() : "");				
					dto.setDblTotal(obj[11] != null ? new Double(obj[11].toString()) : null);				
					dto.setBlnSolicitudCafe(obj[14] != null ? new Boolean(obj[14].toString()) : null);
				}
				
				
				
				respuesta.add(dto);
			}
		}
		
		return respuesta;
	}
	
}
