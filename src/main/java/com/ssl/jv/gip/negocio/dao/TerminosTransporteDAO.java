package com.ssl.jv.gip.negocio.dao;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import com.ssl.jv.gip.jpa.pojo.Documento;
import com.ssl.jv.gip.jpa.pojo.ModalidadEmbarque;
import com.ssl.jv.gip.jpa.pojo.ShipmentConditions;
import com.ssl.jv.gip.jpa.pojo.TerminosTransporte;
import com.ssl.jv.gip.negocio.dto.DocTerminosTransporteDTO;
import com.ssl.jv.gip.negocio.dto.InstruccionesEmbarqueDTO;
import com.ssl.jv.gip.web.util.ErrorConstants;
import com.sun.org.apache.bcel.internal.generic.ARRAYLENGTH;

/**
 * <p>Title: TerminosTransporteDAO</p>
 *
 * <p>Description: DAO para la consulta de los terminos de transporte</p>
 *
 * <p>Copyright: Copyright (c) 2014</p>
 *
 * <p>Company: Soft Studio Ltda.</p>
 *
 * @author Sebastian Gamba Pinilla
 * @email sebas.gamba02@gmail.com
 * @phone 311 89376670
 * @version 1.0
 */
@Stateless
@LocalBean
public class TerminosTransporteDAO extends GenericDAO<TerminosTransporte> implements TerminosTransporteDAOLocal{
	
	private static final Logger LOGGER = Logger.getLogger(TerminosTransporteDAO.class);

	public TerminosTransporteDAO(){
		this.persistentClass = TerminosTransporte.class;
	}

	/**
	 * @see com.ssl.jv.gip.negocio.dao.TerminosTransporteDAOLocal#getAllShipmentConditions()
	 * @author Sebastian Gamba Pinilla - Soft Studio Ltda.
	 * @email seba.gamba02@gmail.com
	 * @phone 311 8376670
	 */
	@Override
	public List<ShipmentConditions> getAllShipmentConditions() {
		LOGGER.info("Entrando al metodo getAllShipmentConditions...");
		List<ShipmentConditions> answer = null;
		try {
			Query query = this.em.createNativeQuery("SELECT "
														+ " terminos_transporte.id, " 
														+ " terminos_transporte.fecha_embarque, "  
														+ " cli.nombre as nombre_cliente "
													+ " FROM terminos_transporte "
														+ " JOIN terminos_transporte_x_documento ON terminos_transporte_x_documento.id_terminos_transporte = terminos_transporte.id "  
														+ " JOIN documentos doc ON terminos_transporte_x_documento.id_documento = doc.id " 
														+ " JOIN clientes cli ON doc.id_cliente = cli.id " 
													 + " GROUP BY terminos_transporte.id, terminos_transporte.fecha_embarque, cli.nombre "
													 + " ORDER BY id DESC", ShipmentConditions.class);
			
			answer = query.getResultList();
		} catch (NoResultException ne){
			LOGGER.error(ErrorConstants.NO_RESULT.getDescription());
		} catch (Exception e) {
			LOGGER.error(ErrorConstants.UNHANDLED_EXCEPTION.getDescription());
		}
		return answer;
	}

	/**
	 * @see com.ssl.jv.gip.negocio.dao.TerminosTransporteDAOLocal#getShipmentConditionsById(java.lang.String)
	 * @author Sebastian Gamba Pinilla - Soft Studio Ltda.
	 * @email seba.gamba02@gmail.com
	 * @phone 311 8376670
	 */
	@Override
	public InstruccionesEmbarqueDTO getShipmentConditionsById(String shipCondId) {
		LOGGER.info("Entrando al metodo getAllShipmentConditions...");
		InstruccionesEmbarqueDTO answer = null;
		
		try {
			Query query = this.em.createNativeQuery("select distinct "
													+ " clientes.nombre, "
													+ " clientes.direccion, "
													+ " clientes.telefono, "
													+ " clientes.contacto, "
													+ " ciu.nombre as ciu_nombre, "
													+ " pai.nombre as pai_nombre "
												+ " from clientes " 
												 + " join documentos doc on doc.id_cliente = clientes.id " 
												 + " join terminos_transporte_x_documento on terminos_transporte_x_documento.id_documento = doc.id " 
												 + " join terminos_transporte on terminos_transporte_x_documento.id_terminos_transporte = terminos_transporte.id "  
												 + " join ciudades ciu on clientes.id_ciudad = ciu.id "  
												 + " join paises pai on ciu.id_pais = pai.id " 
												 + " where terminos_transporte.id = :id");
			query.setParameter("id", Long.parseLong(shipCondId));
			
			Object[] result = (Object[]) query.getSingleResult();
			
			if(result != null){
				answer = new InstruccionesEmbarqueDTO();
				
				answer.setCity((String)result[4]);
				answer.setClientAddress((String)result[1]);
				answer.setClientContact((String)result[3]);
				answer.setClientName((String)result[0]);
				answer.setClientPhone((String)result[2]);
				answer.setCountry((String)result[5]);
				
				LOGGER.info("Resultado consulta de informacion de cliente: " + answer);
				
				answer.setDocumentos(this.getDocumentosTerminosTranporteById(shipCondId));
				
				LOGGER.info("Resultado consulta de documentos de instruccion de embarque: " + answer);
				
				answer.setTerminosTransporte(findTerminosTransporteByIdFetchDocuments(Long.parseLong(shipCondId))); 
				
			}
		} catch (NoResultException ne){
			LOGGER.error(ErrorConstants.NO_RESULT.getDescription());
		} catch (Exception e) {
			LOGGER.error(ErrorConstants.UNHANDLED_EXCEPTION.getDescription());
		}
		LOGGER.info("Resultado consulta : " + answer);
		return answer;
	}
	
	private TerminosTransporte findTerminosTransporteByIdFetchDocuments(Long idTermTrans){
		LOGGER.info("Entrando al metodo findTerminosTransporteByIdFetchDocuments");
		TerminosTransporte answer = null;
		try {
			Query q = this.em.createQuery("SELECT tt FROM TerminosTransporte tt "
					+ " JOIN FETCH tt.documentos "
					+ " WHERE tt.id = :idTermTrans ");
			q.setParameter("idTermTrans", idTermTrans);
			
			answer = (TerminosTransporte) q.getSingleResult();
		} catch (NoResultException ne){
			LOGGER.error(ErrorConstants.NO_RESULT.getDescription());
		} catch (Exception e) {
			LOGGER.error(ErrorConstants.UNHANDLED_EXCEPTION.getDescription());
		}
		return answer;
	}
	
	/**
	 * Metodo que consulta los documentos por instruccino de embarque
	 * @author Sebastian Gamba Pinilla - Soft Studio Ltda.
	 * @email seba.gamba02@gmail.com
	 * @phone 311 8376670
	 * @param idTerminos
	 * @return
	 */ 
	public List<DocTerminosTransporteDTO> getDocumentosTerminosTranporteById(String idTerminos){
		LOGGER.info("Consultando los documentos para la instruccion de embarque: " + idTerminos);
		List<DocTerminosTransporteDTO> docs = null;

		List<Long> idDocs = null;
		List<Long> idDocsMercadeo = null;
		
		StringBuilder strQuery = null;
		try {
			Query query = this.em.createNativeQuery("select "
					+ "	documento_x_negociacion.id_documento, "
					+ " documento_x_negociacion.solicitud_cafe "
				+ " from terminos_transporte_x_documento " 
				+ " inner join Documento_x_Negociacion on terminos_transporte_x_documento.id_documento = documento_x_negociacion.id_documento " 
				+ " where terminos_transporte_x_documento.id_terminos_transporte = :id");
			query.setParameter("id", Long.parseLong(idTerminos));

			List<Object[]> resultList = query.getResultList();

			if (resultList != null) {
				for (Object[] obj : resultList) {
					if (obj[1].equals(new Boolean(true))) {
						if (idDocs == null) {
							idDocs = new ArrayList<Long>();
						}
						idDocs.add(((BigInteger) obj[0]).longValue());
					} else {
						if (idDocsMercadeo == null) {
							idDocsMercadeo = new ArrayList<Long>();
						}
						idDocsMercadeo.add(((BigInteger) obj[0]).longValue());
					}
				}
				
				strQuery = new StringBuilder();
				
				if(idDocs != null){
					strQuery.append("SELECT "
							+ "(select consecutivo_documento from documentos doc2 "
							+ " where observacion_documento = (select doc.consecutivo_documento from documentos doc " 
							+ " where doc.observacion_documento = documentos.consecutivo_documento "
							+ " and doc.id_tipo_documento = 24) and doc2.id_estado <> 11 "
							+ " order by doc2.consecutivo_documento desc limit 1) as consecutivo_documento, "
							+ " documento_x_lotesoic.consecutivo, "
							+ " tipo_loteoic.descripcion, "
							+ " documento_x_lotesoic.total_cajas, "
							+ " documento_x_lotesoic.pedido, "
							+ " documento_x_lotesoic.asignacion, "
							+ " documento_x_lotesoic.aviso "
							+ " FROM documento_x_lotesoic " 
							+ " INNER JOIN tipo_loteoic on documento_x_lotesoic.id_tipo_lote = tipo_loteoic.id "  
							+ " INNER JOIN documentos on documento_x_lotesoic.id_documento = documentos.id " 
							+ " WHERE documento_x_lotesoic.id_documento IN "
							+ "(select documentos.id from documentos "
							+ "where documentos.consecutivo_documento IN ("
							+ "select documentos.observacion_documento "
							+ "from documentos "
							+ "where documentos.consecutivo_documento IN ("
							+ "select documentos.observacion_documento "
							+ "from documentos where documentos.id IN ( :idDocs )))) "); 
				}
				
				if(idDocsMercadeo != null){
					if(!strQuery.toString().isEmpty()){
						strQuery.append(" UNION ALL ");
					}
					
					strQuery.append("SELECT "
							+ "docs.consecutivo_documento, "
							+ " 'MERCADEO' as consecutivo, "
							+ " tipo_loteoic.descripcion, " 
							+ " SUM((CASE WHEN (pic.cantidad_x_embalaje = 0) THEN 0 ELSE (productosXdocumentos.cantidad1/pic.cantidad_x_embalaje) END)) as Total_Cajas, "
							+ " 'MERCADEO' as pedido, 'MERCADEO' as asignacion, 'MERCADEO' as aviso "
							+ " FROM productosXdocumentos " 
							+ " LEFT JOIN productos_inventario ON productosXdocumentos.id_producto=productos_inventario.id " 
							+ " LEFT JOIN productos_inventario_comext pic ON pic.id_producto=productos_inventario.id " 
							+ " LEFT JOIN tipo_loteoic ON tipo_loteoic.id = pic.id_tipo_loteoic " 
							+ " INNER JOIN unidades ON productos_inventario.id_uv=unidades.id " 
							+ " INNER JOIN documentos docs ON docs.id = productosXdocumentos.id_documento " 
							+ " WHERE productosXdocumentos.id_documento IN (  :idDocsMercadeo  ) " 
							+ " GROUP BY tipo_loteoic.descripcion, docs.consecutivo_documento");					
				}
				
				if(!strQuery.toString().isEmpty()){
					strQuery.append(" ORDER BY consecutivo ");
					
					query = this.em.createNativeQuery(strQuery.toString());
					if(idDocs != null){
						query.setParameter("idDocs", idDocs);
					}
					if(idDocsMercadeo != null){
						query.setParameter("idDocsMercadeo", idDocsMercadeo);
					}
					
					resultList = query.getResultList();
					
					if(resultList != null){
						for(Object[] obj : resultList){
							DocTerminosTransporteDTO dto = new DocTerminosTransporteDTO();
							dto.setAsignacionNum((String) obj[5]);
							dto.setAvisoNum((String) obj[6]);
							dto.setCantCajas(((BigDecimal) obj[3]).toString());
							dto.setDescripcion((String) obj[2]);
							dto.setFactura((String) obj[0]);
							dto.setLote((String) obj[1]);
							dto.setPedidoNum((String) obj[4]);
							
							if(docs == null){
								docs = new ArrayList<DocTerminosTransporteDTO>();
							}
							docs.add(dto);
						}
					}
				}
			}
		} catch (NoResultException ne){
			LOGGER.error(ErrorConstants.NO_RESULT.getDescription());
		} catch (Exception e) {
			LOGGER.error(ErrorConstants.UNHANDLED_EXCEPTION.getDescription());
		}
		return docs;
	}

	/**
	 * @see com.ssl.jv.gip.negocio.dao.TerminosTransporteDAOLocal#getById(java.lang.Long)
	 * @author Sebastian Gamba Pinilla - Soft Studio Ltda.
	 * @email seba.gamba02@gmail.com
	 * @phone 311 8376670
	 */
	@Override
	public TerminosTransporte getById(Long idTerminosTrans) {
		return this.findByPK(idTerminosTrans);
	}

	@Override
	public List<ModalidadEmbarque> getAllShipmentMod() {
		List<ModalidadEmbarque> list = null;
		try {
			String query = "SELECT me FROM ModalidadEmbarque me";
			Query q = this.em.createQuery(query);
			
			list = q.getResultList();
		} catch (NoResultException ne) {
			LOGGER.error(ErrorConstants.NO_RESULT.getDescription());
		} catch (Exception e) {
			LOGGER.error(ErrorConstants.UNHANDLED_EXCEPTION.getDescription());
		}
		return list;
	}

	/**
	 * @see com.ssl.jv.gip.negocio.dao.TerminosTransporteDAOLocal#getDocumentsByShipCondId(java.lang.Long)
	 * @author Sebastian Gamba Pinilla - Soft Studio Ltda.
	 * @email seba.gamba02@gmail.com
	 * @phone 311 8376670
	 */
	@Override
	public List<Documento> getDocumentsByShipCondId(Long idShipmntCond) {
		List<Documento> list = null;
		try {
			String query = "SELECT * FROM documentos d WHERE d.id IN "
					+ " (SELECT ttd.id_documento FROM terminos_transporte_x_documento ttd WHERE ttd.id_terminos_transporte = ?)";
			
			Query q = this.em.createNativeQuery(query);
			q.setParameter(1, idShipmntCond);
						
			list = q.getResultList();
		} catch (NoResultException ne) {
			LOGGER.error(ErrorConstants.NO_RESULT.getDescription());
		} catch (Exception e) {
			LOGGER.error(ErrorConstants.UNHANDLED_EXCEPTION.getDescription());
		}
		return list;
	}

	/**
	 * @see com.ssl.jv.gip.negocio.dao.TerminosTransporteDAOLocal#updateTerminoTransporte(com.ssl.jv.gip.jpa.pojo.TerminosTransporte)
	 * @author Sebastian Gamba Pinilla - Soft Studio Ltda.
	 * @email seba.gamba02@gmail.com
	 * @phone 311 8376670
	 */
	@Override
	public TerminosTransporte updateTerminoTransporte(
			TerminosTransporte terminosTransporte) {
		update(terminosTransporte);
		this.em.flush();		
		return findByPK(terminosTransporte.getId());
	}
}
