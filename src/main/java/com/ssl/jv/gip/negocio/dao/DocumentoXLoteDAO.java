package com.ssl.jv.gip.negocio.dao;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import com.ssl.jv.gip.jpa.pojo.Documento;
import com.ssl.jv.gip.jpa.pojo.DocumentoXLotesoic;


/**
 * The Class DocumentoXLoteDAO.
 */
@Stateless
@LocalBean
public class DocumentoXLoteDAO extends GenericDAO<DocumentoXLotesoic>{

	/** The Constant LOGGER. */
	private static final Logger LOGGER = Logger.getLogger(DocumentoXLoteDAO.class);

	/**
	 * Instantiates a new documento x lote dao.
	 */
	public DocumentoXLoteDAO(){
		this.persistentClass = DocumentoXLotesoic.class;
	}

	/**
	 * Consultar documento x lote oic.
	 *
	 * @return the list
	 */
	public List<DocumentoXLotesoic> consultarDocumentoXLoteOIC(){

		List<DocumentoXLotesoic> listado = new ArrayList<DocumentoXLotesoic>();

		try{
			
			String sql = "SELECT documentos.consecutivo_documento, documento_x_lotesoic.fecha, documento_x_lotesoic.consecutivo "
					 +"FROM documento_x_lotesoic "
					 +"INNER JOIN tipo_loteoic on documento_x_lotesoic.id_tipo_lote = tipo_loteoic.id "
					 +"INNER JOIN documentos on documento_x_lotesoic.id_documento = documentos.id "
					 +"WHERE documento_x_lotesoic.id_documento = (select id_documento from documento_x_lotesoic order by fecha || consecutivo desc limit 1)";
				
			
			Query query = em.createNativeQuery(sql);
			List<Object[]> resultado = query.getResultList();
			
			if(resultado != null){
				for(Object[] obj : resultado){
					DocumentoXLotesoic dto = new DocumentoXLotesoic();
					
					Documento objDocumento = new Documento();
					objDocumento.setConsecutivoDocumento(obj[0] != null ? obj[0].toString() : "");
					dto.setDocumento(objDocumento);
					if(obj[1] != null){
						dto.setFecha((Timestamp) (obj[1]));
					}					
					dto.setConsecutivo(obj[2] != null ? obj[2].toString() : "");
					
					listado.add(dto);
				}
			}
			
			
		} catch(Exception e){

		}
		return listado;
	}


	/**
	 * Reiniciar consecutivo lote oic.
	 *
	 * @return the integer
	 */
	public Integer reiniciarConsecutivoLoteOIC(){
		try{
			int q = em.createNativeQuery("ALTER SEQUENCE lote_oic_seq restart 1").executeUpdate();
		} catch(Exception e){
			return 0;
		}
		return 1;
	}

}
