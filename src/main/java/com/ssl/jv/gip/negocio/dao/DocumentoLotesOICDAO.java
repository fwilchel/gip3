package com.ssl.jv.gip.negocio.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import com.ssl.jv.gip.jpa.pojo.Documento;
import com.ssl.jv.gip.jpa.pojo.DocumentoXLotesoic;
import com.ssl.jv.gip.jpa.pojo.DocumentoXLotesoicPK;
import com.ssl.jv.gip.negocio.dto.DocumentoLotesContribucionCafeteriaDTO;

@Stateless
@LocalBean
public class DocumentoLotesOICDAO extends GenericDAO<DocumentoXLotesoic>{

	private static final Logger LOGGER = Logger.getLogger(DocumentoLotesOICDAO.class);

	public DocumentoLotesOICDAO(){
		this.persistentClass = DocumentoXLotesoic.class;
	}

	public List<DocumentoLotesContribucionCafeteriaDTO> consultarDocumentoLotesContribucionCafetera(Map<String, Object> parametros){
		
		List<DocumentoLotesContribucionCafeteriaDTO> lista = new ArrayList<DocumentoLotesContribucionCafeteriaDTO>();

		String sql="";

		String consecutivo = (String)parametros.get("consecutivo");
		sql= "select d.id as id_documento,tl.descripcion ,dxl.consecutivo, dxl.contribucion, dxl.dex ,tl.id from documentos d "
				+"LEFT JOIN documento_x_lotesoic dxl on dxl.id_documento = d.id " 
				+"inner join  productosXdocumentos pxd on d.id=pxd.id_documento  " 
				+"LEFT JOIN productos_inventario_comext pi_ce on pi_ce.id_producto=pxd.id_producto  "
				+"LEFT JOIN tipo_loteoic tl on pi_ce.id_tipo_loteoic=tl.id   "
				+"where  (dxl.id_tipo_lote=tl.id  or   dxl.id_documento is null) and  " 
				+" d.consecutivo_documento  "
				+"in(select observacion_documento from documentos where consecutivo_documento =  " 
				+"(select observacion_documento from documentos where consecutivo_documento = '" + consecutivo + "')) "
				+"group by  d.id,tl.descripcion,dxl.consecutivo ,dxl.contribucion, dxl.dex ,tl.id "
									+"order by dxl.consecutivo";
		
		List<Object[]> listado = em.createNativeQuery(sql).getResultList();
		
		if(listado != null){
			for(Object[] objs : listado){
				DocumentoLotesContribucionCafeteriaDTO dto = new DocumentoLotesContribucionCafeteriaDTO();
				
				dto.setIdDocumento(objs[0] != null ? Long.parseLong(objs[0].toString()) : null);
				dto.setDescripcion(objs[1] != null ? objs[1].toString() : null);
				dto.setConsecutivo(objs[2] != null ? objs[2].toString() : null);
				dto.setContribucion(objs[3] != null ? BigDecimal.valueOf(Double.parseDouble(objs[3].toString())) : null);
				dto.setDex(objs[4] != null ? BigDecimal.valueOf(Double.parseDouble(objs[4].toString())) : null);
				dto.setTipoLoteId(objs[5] != null ? Long.parseLong(objs[5].toString()) : null);
				
				lista.add(dto);
			}
		}
		
		return lista;

	}
	
	public List<DocumentoLotesContribucionCafeteriaDTO> guardarDocumentoLotesContribucionCafetera(List<DocumentoLotesContribucionCafeteriaDTO> documentos){
		
		if(documentos != null){
			for(DocumentoLotesContribucionCafeteriaDTO dto : documentos){
				DocumentoXLotesoicPK pk = new DocumentoXLotesoicPK();
				pk.setIdDocumento(dto.getIdDocumento());
				pk.setIdTipoLote(dto.getTipoLoteId());
				
				DocumentoXLotesoic entity = new DocumentoXLotesoic();
				entity.setId(pk);
				
				entity = em.find(DocumentoXLotesoic.class, pk);
				
				entity.setContribucion(dto.getContribucion());
				entity.setDex(dto.getDex());
				
				em.merge(entity);
			}
		}
		
		return documentos;
		
	}

}
