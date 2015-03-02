package com.ssl.jv.gip.negocio.dao;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import com.ssl.jv.gip.jpa.pojo.Documento;
import com.ssl.jv.gip.jpa.pojo.ProductosXDocumento;
import com.ssl.jv.gip.negocio.dto.ProductoDespacharMercanciaDTO;
import com.ssl.jv.gip.negocio.dto.ProductoFacturaDirectaDTO;
import com.ssl.jv.gip.negocio.dto.ProductoReporteTxtFacturaDirectaDTO;
import com.ssl.jv.gip.negocio.dto.ReporteVentaDTO;
import com.ssl.jv.gip.web.mb.util.ConstantesDocumento;
import com.ssl.jv.gip.web.mb.util.ConstantesTipoDocumento;

/**
 * Session Bean implementation class ProductosXDocumentoDAO
 */
@Stateless
@LocalBean
public class ProductosXDocumentoDAO extends GenericDAO<ProductosXDocumento>
		implements ProductosXDocumentoDAOLocal {

	private static final Logger LOGGER = Logger
			.getLogger(ProductosXDocumentoDAO.class);

	/**
	 * @see GenericDAO#GenericDAO()
	 */
	public ProductosXDocumentoDAO() {
		this.persistentClass = ProductosXDocumento.class;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProductosXDocumento> consultarPorDocumento(Long id) {
		Query query = em
				.createNamedQuery(ProductosXDocumento.FIND_BY_DOCUMENTO);
		query.setParameter("idDocumento", id);
		return query.getResultList();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<ProductoDespacharMercanciaDTO> consultarProductoVentaDirecta(String consecutivoDocumento){
		List<ProductoDespacharMercanciaDTO> listado = new ArrayList<ProductoDespacharMercanciaDTO>();
	    String query;
	    try {
	      query = "SELECT pxd.id_producto, pxd.id_documento, pi.sku, pi.nombre, u.abreviacion, pxd.cantidad1, pxd.cantidad2, pxd.valor_unitatrio_ml, pxd.observacion1, pxd.id_ml "
	      		  + "from productosxdocumentos pxd inner join documentos d on pxd.id_documento=d.id "
	      		  + "inner join productos_inventario pi on pi.id=pxd.id_producto "
	      		  + "inner join unidades u on u.id=pi.id_ud " 
	      		  + "where pxd.id_documento='"+consecutivoDocumento+"' ";
	      List<Object[]> aux = em.createNativeQuery(query).setMaxResults(20).getResultList();
	      if (aux != null) {
				for (Object[] objs : aux) {
					ProductoDespacharMercanciaDTO dto = new ProductoDespacharMercanciaDTO();
					dto.setId(objs[0] != null ? objs[0].toString() : null);
					dto.setIdDocumento(objs[1] != null ? objs[1].toString() : null);
					dto.setSku(objs[2] != null ? objs[2].toString() : null);
					dto.setNombre(objs[3] != null ? objs[3].toString() : null);
					dto.setUnidadVenta(objs[4] != null ? objs[4].toString() : null);
					dto.setCantidadAdespachar(objs[5] != null ? new BigDecimal(objs[5].toString()) : null);
					dto.setCantidadDespachada(objs[6] != null ? new BigDecimal(objs[6].toString()) : null);
					dto.setPrecioUnitario(objs[7] != null ? new BigDecimal(objs[7].toString()) : null);
					dto.setObservaciones(objs[8] != null ? objs[8].toString() : null);
					dto.setMoneda(objs[9] != null ? objs[9].toString() : null);
					dto.setSeleccionado(false);
					listado.add(dto);
				}
			}
	    } catch (Exception e) {
	      LOGGER.error(e
	              + "********Error consultando Productos Venta directa");
	      return null;
	    }
	    return listado;
	}
	
	
	
	public List<ProductoFacturaDirectaDTO> consultarProductoFacturaDirecta(String strConsecutivoDocumento) 
	{
		
		List<ProductoFacturaDirectaDTO> lista = new ArrayList<ProductoFacturaDirectaDTO>();
		
		try {
			
			
			String query="select pi.sku, pi.nombre ,u.abreviacion ,pxd.cantidad1,pxd.valor_unitatrio_ml,pxd.descuentoxproducto, pxd.valor_total,pxd.otros_descuentos,pxd.iva,"
			+ " (CASE WHEN pxd.otros_descuentos > 0 THEN '(*)' ELSE '' END) AS MARCA"		
			+ " from productosxdocumentos pxd inner join documentos d on pxd.id_documento=d.id" 
			+ " inner join productos_inventario pi on pi.id=pxd.id_producto"
			+ " inner join unidades u on u.id=pi.id_ud"
			+ " where d.consecutivo_documento='"+strConsecutivoDocumento+"'";

			
			
			
			List<Object[]> listado = em.createNativeQuery(query).getResultList();
			
			
			System.out.println("query PXD LE Query"+query);
			System.out.println("query PXD LE"+listado.size());
			if (listado != null) {
				for (Object[] objs : listado) {
					ProductoFacturaDirectaDTO dto = new ProductoFacturaDirectaDTO();
					//ClienteDTO clienteDTO = new ClienteDTO();
					dto.setSku(objs[0] != null ? objs[0].toString() : null);
					dto.setNombre(objs[1] != null ? objs[1].toString() : null);
					dto.setUnidad(objs[2] != null ? objs[2].toString() : null);
					dto.setCantidad(objs[3] != null ? new BigDecimal(objs[3].toString()) : null);
					
					dto.setPrecio(objs[4] != null ? new BigDecimal(objs[4].toString()) : null);
					dto.setValorDescuento(objs[5] != null ? new BigDecimal(objs[5].toString()) : null);
					dto.setValorTotal(objs[6] != null ? new BigDecimal(objs[6].toString()) : null);
					dto.setValorOtrosDescuento(objs[7] != null ? new BigDecimal(objs[7].toString()) : null);
					dto.setValorIva(objs[8] != null ? new BigDecimal(objs[8].toString()) : null);
					dto.setMarca(objs[9] != null ? objs[9].toString() : null);
					
									lista.add(dto);
				}
			}
			return lista;
			
			
			
		} catch (Exception e) {
			LOGGER.error(e);
		}
		
		
		
		return lista;
		
	}

	@Override
	public void modificarProductosXDocumentos(
			List<ProductosXDocumento> productosXDocumentos) {
		for (ProductosXDocumento productosXDocumento : productosXDocumentos) {
			this.update(productosXDocumento);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProductosXDocumento> consultarPorDocumentoYCliente(
			Long idDocumento, Long idCliente) {
		Query query = em
				.createNamedQuery(ProductosXDocumento.FIND_BY_DOCUMENTO_AND_CLIENTE);
		query.setParameter("idDocumento", idDocumento);
		query.setParameter("idCliente", idCliente);
		return query.getResultList();
	}
	
	
	
	@Override
	public List<ProductoReporteTxtFacturaDirectaDTO> consultarReporteTxtVentasFD(Map<String, Object> parametros) 
	{
		String fechaIni = (String) parametros.get("fechaInicial");
		String fechaFin = (String) parametros.get("fechaFinal");
		 
		
		Timestamp tsfechaIni=Timestamp.valueOf(fechaIni + " 00:00:00");
		Timestamp tsfechaFin=Timestamp.valueOf(fechaFin);
		
		int tipoDoc = (Integer) parametros.get("tipo");
		int estadoDoc = (Integer) parametros.get("estado");

		 
		
		List<ProductoReporteTxtFacturaDirectaDTO> lista = new ArrayList<ProductoReporteTxtFacturaDirectaDTO>();
		String query = "";
		
			
		
		query = "SELECT row_number() over (ORDER BY d.consecutivo_documento) as id, d.id_ubicacion_destino as idUbicacionDestino,p.sku as sku,p.nombre as nombre,round(pxd.cantidad1) as cantidad,"
				+ " to_char(d.fecha_generacion, 'YYYY-MM-DD') as fechaGeneracion, d.consecutivo_documento as ConsecutivoDocumento,round(pxd.valor_unitatrio_ml) as valorUnitario ,d.observacion_documento as observacionDocumento,"
				+ " u.cliente_icg as clienteIcg,pxd.descuentoxproducto as valorDescuentoProducto,pxd.iva as ValorIva"
				+ " FROM productosxdocumentos pxd,productos_inventario p, documentos d ,ubicaciones u" 
				+ " WHERE  pxd.id_documento=d.id" 
				+ " and pxd.id_producto=p.id"
				+ " and d.id_ubicacion_destino=u.id"
				+ " and d.fecha_generacion  between :fechaIni and :fechaFin" 
				+ " and d.id_tipo_documento= :tipoDoc and d.id_estado= :estadoDoc"
				+ " ORDER BY d.id,p.sku";
		
		
		lista = em.createNativeQuery(query, ProductoReporteTxtFacturaDirectaDTO.class).setParameter("fechaIni", tsfechaIni)
				.setParameter("fechaFin", tsfechaFin)
				.setParameter("tipoDoc", tipoDoc)
				.setParameter("estadoDoc", estadoDoc)
				.getResultList();
		
		
		
		/* System.out.println("query: "+query );
		 System.out.println("query arhivo Plano ventas" + lista.size());
		 System.out.println("estado: "+ estadoDoc);
		 System.out.println("fechaini: "+ tsfechaIni);
		 System.out.println("fechaFin: "+ tsfechaFin);
		 System.out.println("tipoDoc: "+ tipoDoc);
		 */
		
		return lista;

	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ProductosXDocumento> consultarPorDocumentoConColecciones(Long id) {
		Query query = em
				.createNamedQuery(ProductosXDocumento.FIND_BY_DOCUMENTO_COLECCIONES);
		query.setParameter("idDocumento", id);
		return query.getResultList();
	}
	

}
