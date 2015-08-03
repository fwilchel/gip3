package com.ssl.jv.gip.negocio.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import com.ssl.jv.gip.jpa.pojo.DocumentoXLotesoic;
import com.ssl.jv.gip.jpa.pojo.DocumentoXLotesoicPK;
import com.ssl.jv.gip.negocio.dto.DocumentoLotesContribucionCafeteriaDTO;

@Stateless
@LocalBean
public class DocumentoLotesOICDAO extends GenericDAO<DocumentoXLotesoic> implements DocumentoLotesOICDAOLocal {

  private static final Logger LOGGER = Logger.getLogger(DocumentoLotesOICDAO.class);

  public DocumentoLotesOICDAO() {
	this.persistentClass = DocumentoXLotesoic.class;
  }

  @Override
  public List<DocumentoLotesContribucionCafeteriaDTO> consultarDocumentoLotesContribucionCafetera(Map<String, Object> parametros) {

	List<DocumentoLotesContribucionCafeteriaDTO> lista = new ArrayList<>();

	String sql = "";

	String consecutivo = (String) parametros.get("consecutivo");
	sql = "select " + "d.id as id_documento, " + "tl.descripcion, " + "dxl.consecutivo, " + "dxl.contribucion, " + "dxl.dex, " + "tl.id " + "from documentos d " + "LEFT JOIN documento_x_lotesoic dxl on dxl.id_documento = d.id " + "inner join  productosXdocumentos pxd on d.id=pxd.id_documento  " + "LEFT JOIN productos_inventario_comext pi_ce on pi_ce.id_producto=pxd.id_producto  " + "LEFT JOIN tipo_loteoic tl on pi_ce.id_tipo_loteoic=tl.id   "
		+ "where  (dxl.id_tipo_lote=tl.id  or   dxl.id_documento is null) and  " + " d.consecutivo_documento  " + "in(select observacion_documento from documentos where consecutivo_documento =  " + "(select observacion_documento from documentos where consecutivo_documento = '" + consecutivo + "')) " + "group by  d.id,tl.descripcion,dxl.consecutivo ,dxl.contribucion, dxl.dex ,tl.id " + "order by dxl.consecutivo";

	List<Object[]> listado = em.createNativeQuery(sql).getResultList();

	if (listado != null) {
	  for (Object[] objs : listado) {
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

  @Override
  public List<DocumentoLotesContribucionCafeteriaDTO> guardarDocumentoLotesContribucionCafetera(List<DocumentoLotesContribucionCafeteriaDTO> documentos) {
	if (documentos != null) {
	  Long idTipoLote = documentos.get(0).getTipoLoteId();
	  Long idDocumento = documentos.get(0).getIdDocumento();
	  String consecutivo = documentos.get(0).getConsecutivo();
	  BigDecimal contribucion = documentos.get(0).getContribucion();
	  BigDecimal dex = documentos.get(0).getDex();
	  if (idTipoLote != 5L) {
		for (DocumentoLotesContribucionCafeteriaDTO dto : documentos) {
		  Map<String, Object> parametros = new HashMap<>();
		  parametros.put("contribucion", dto.getContribucion());
		  parametros.put("dex", dto.getDex());
		  parametros.put("id_documento", dto.getIdDocumento());
		  parametros.put("id_tipo_lote", (long) dto.getTipoLoteId());
		  ejecutarConsultaNativa("UPDATE documento_x_lotesoic SET contribucion = :contribucion, dex = :dex WHERE id_documento = :id_documento AND id_tipo_lote = :id_tipo_lote", parametros);
		}
	  } else if (idTipoLote == 5L && (consecutivo == null || consecutivo.isEmpty())) {
		// adicionar
		DocumentoXLotesoicPK pk = new DocumentoXLotesoicPK();
		pk.setIdDocumento(idDocumento);
		pk.setIdTipoLote(idTipoLote);
		DocumentoXLotesoic entity = new DocumentoXLotesoic();
		entity.setId(pk);
		entity.setContribucion(contribucion);
		entity.setDex(dex);
		entity.setConsecutivo("0");
		em.persist(entity);
	  } else if (idTipoLote == 5L && (consecutivo != null && consecutivo.contentEquals("0"))) {
		Map<String, Object> parametros = new HashMap<>();
		parametros.put("contribucion",contribucion);
		parametros.put("dex", dex);
		parametros.put("id_documento", idDocumento);
		parametros.put("id_tipo_lote", idTipoLote);
		ejecutarConsultaNativa("UPDATE documento_x_lotesoic SET contribucion = :contribucion, dex = :dex WHERE id_documento = :id_documento AND id_tipo_lote = :id_tipo_lote", parametros);
	  }
	}
	return documentos;
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<DocumentoXLotesoic> consultarPorConsecutivoDocumento(String consecutivoDocumento) {
	Query query = em.createNamedQuery(DocumentoXLotesoic.FIND_BY_CONSECUIVO_DOCUMENTO);
	query.setParameter("consecutivoDocumento", consecutivoDocumento);
	return query.getResultList();
  }

}
