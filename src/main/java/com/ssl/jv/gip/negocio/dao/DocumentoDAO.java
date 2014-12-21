package com.ssl.jv.gip.negocio.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import com.ssl.jv.gip.jpa.pojo.Documento;
import com.ssl.jv.gip.negocio.dto.DatoContribucionCafeteraDTO;

@Stateless
@LocalBean
public class DocumentoDAO extends GenericDAO<Documento>{

	private static final Logger LOGGER = Logger.getLogger(DocumentoDAO.class);

	public DocumentoDAO(){
		this.persistentClass = Documento.class;
	}

	public List<DatoContribucionCafeteraDTO> consultarDatosContribucionCafetera(Map<String, Object> parametros){
		
		List<DatoContribucionCafeteraDTO> lista = new ArrayList<DatoContribucionCafeteraDTO>();

		String sql="";

		int tipo = (Integer)parametros.get("tipo");
		int estado = (Integer) parametros.get("estado");
		sql="select distinct(doc_lot.id_documento) as doc_lot,docFX.id , docFX.consecutivo_documento , docFX.fecha_generacion"
				+ " from documentos docFX "
				+ " inner join documentos docLE on docFX.observacion_documento=docLE.consecutivo_documento "
				+ " inner join documentos docFP on docLE.observacion_documento=docFP.consecutivo_documento "
				+ " left outer join documento_x_lotesoic  doc_lot on docFP.id=doc_lot.id_documento "
				+ " where docFX.id_tipo_documento= " + tipo
				+ " AND docFX.id_estado = " + estado
				+ " ORDER BY docFX.id DESC";
		
		List<Object[]> listado = em.createNativeQuery(sql).getResultList();
		
		if(listado != null){
			for(Object[] objs : listado){
				DatoContribucionCafeteraDTO dto = new DatoContribucionCafeteraDTO();
				
				dto.setIdDocumento(objs[0] != null ? Long.parseLong(objs[0].toString()) : null);
				dto.setIdDocumentoFX(objs[1] != null ? Long.parseLong(objs[1].toString()) : null);
				dto.setConsecutivo(objs[2] != null ? objs[2].toString() : null);
				dto.setFechaGeneracion(objs[3] != null ? (Date)objs[3] : null);
				
				lista.add(dto);
			}
		}
		
		return lista;

	}

}
