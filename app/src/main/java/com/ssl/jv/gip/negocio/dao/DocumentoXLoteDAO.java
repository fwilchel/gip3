package com.ssl.jv.gip.negocio.dao;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import com.ssl.jv.gip.jpa.pojo.Documento;
import com.ssl.jv.gip.jpa.pojo.DocumentoXLotesoic;
import com.ssl.jv.gip.negocio.dto.DocumentoInstruccionEmbarqueDTO;
import com.ssl.jv.gip.negocio.dto.DocumentoPorLotesInstruccionEmbarqueDTO;

/**
 * The Class DocumentoXLoteDAO.
 */
@Stateless
@LocalBean
public class DocumentoXLoteDAO extends GenericDAO<DocumentoXLotesoic> implements DocumentoXLoteDAOLocal {

  /**
   * The Constant LOGGER.
   */
  private static final Logger LOGGER = Logger.getLogger(DocumentoXLoteDAO.class);

  /**
   * Instantiates a new documento x lote dao.
   */
  public DocumentoXLoteDAO() {
    this.persistentClass = DocumentoXLotesoic.class;
  }

  /**
   * Consultar documento x lote oic.
   *
   * @return the list
   */
  public List<DocumentoXLotesoic> consultarDocumentoXLoteOIC() {

    List<DocumentoXLotesoic> listado = new ArrayList<DocumentoXLotesoic>();

    try {

      String sql = "SELECT documentos.consecutivo_documento, documento_x_lotesoic.fecha, documento_x_lotesoic.consecutivo "
              + "FROM documento_x_lotesoic "
              + "INNER JOIN tipo_loteoic on documento_x_lotesoic.id_tipo_lote = tipo_loteoic.id "
              + "INNER JOIN documentos on documento_x_lotesoic.id_documento = documentos.id "
              + "WHERE documento_x_lotesoic.id_documento = (select id_documento from documento_x_lotesoic order by fecha || consecutivo desc limit 1)";

      Query query = em.createNativeQuery(sql);
      List<Object[]> resultado = query.getResultList();

      if (resultado != null) {
        for (Object[] obj : resultado) {
          DocumentoXLotesoic dto = new DocumentoXLotesoic();

          Documento objDocumento = new Documento();
          objDocumento.setConsecutivoDocumento(obj[0] != null ? obj[0].toString() : "");
          dto.setDocumento(objDocumento);
          if (obj[1] != null) {
            dto.setFecha((Timestamp) (obj[1]));
          }
          dto.setConsecutivo(obj[2] != null ? obj[2].toString() : "");

          listado.add(dto);
        }
      }

    } catch (Exception e) {

    }
    return listado;
  }

  /**
   * Reiniciar consecutivo lote oic.
   *
   * @return the integer
   */
  public Integer reiniciarConsecutivoLoteOIC() {
    try {
      int q = em.createNativeQuery("ALTER SEQUENCE lote_oic_seq restart 1").executeUpdate();
    } catch (Exception e) {
      return 0;
    }
    return 1;
  }

  public List<DocumentoPorLotesInstruccionEmbarqueDTO> consultarDocumentosPorLotes(String strDocs, String strDocsMerca) {

    int conta = 0;

    String sql = "";

    if (strDocs.length() > 0) {
      conta = conta + 1;

      sql = "SELECT documento_x_lotesoic.id_documento, documento_x_lotesoic.consecutivo, documento_x_lotesoic.id_tipo_lote, "
              + "documento_x_lotesoic.total_cajas, documento_x_lotesoic.pedido, documento_x_lotesoic.asignacion, documento_x_lotesoic.aviso, "
              + "tipo_loteoic.descripcion, documento_x_lotesoic.total_cantidad, "
              + "(select consecutivo_documento from documentos doc2 "
              + "where observacion_documento = (select doc.consecutivo_documento from documentos doc "
              + "where doc.observacion_documento = documentos.consecutivo_documento "
              + "and doc.id_tipo_documento = 24) and doc2.id_estado <> 11 "
              + "order by doc2.consecutivo_documento desc limit 1) as consecutivo_documento, "
              + "(select doc.consecutivo_documento from documentos doc "
              + "where doc.observacion_documento = documentos.consecutivo_documento "
              + "and doc.id_tipo_documento = 24 and doc.id_estado <> 11 limit 1) as observacion_documento, "
              + "documentos.consecutivo_documento as factura_proforma "
              + ",(select fecha_eta from documentos doc2 "
              + "where observacion_documento = (select doc.consecutivo_documento from documentos doc "
              + "where doc.observacion_documento = documentos.consecutivo_documento "
              + "and doc.id_tipo_documento = 24) and doc2.id_estado <> 11 "
              + "order by doc2.consecutivo_documento desc limit 1) as fecha_eta "
              + "FROM documento_x_lotesoic "
              + "INNER JOIN tipo_loteoic on documento_x_lotesoic.id_tipo_lote = tipo_loteoic.id "
              + "INNER JOIN documentos on documento_x_lotesoic.id_documento = documentos.id "
              + "WHERE documento_x_lotesoic.id_documento IN (select documentos.id from documentos where documentos.consecutivo_documento IN (select documentos.observacion_documento from documentos where documentos.consecutivo_documento IN (select documentos.observacion_documento from documentos where documentos.id IN (" + strDocs + ")))) ";
    }
    if (strDocsMerca.length() > 0) {
      conta = conta + 1;

      if (conta == 2) {
        sql = sql + "UNION ALL ";
      }

      sql = sql + "SELECT productosXdocumentos.id_documento, 'MERCADEO' as consecutivo, pic.id_tipo_loteoic as id_tipo_lote, "
              + "SUM((CASE WHEN (pic.cantidad_x_embalaje = 0) THEN 0 ELSE (productosXdocumentos.cantidad1/pic.cantidad_x_embalaje) END)) as Total_Cajas, "
              + "'MERCADEO' as pedido, "
              + "'MERCADEO' as asignacion, "
              + "'MERCADEO' as aviso, "
              + "tipo_loteoic.descripcion, "
              + "SUM(productosXdocumentos.cantidad1) as Total_Cantidad, docs.consecutivo_documento, docs.observacion_documento,"
              + "(select observacion_documento from documentos where consecutivo_documento = docs.observacion_documento) as factura_proforma "
              + ",(select fecha_eta from documentos where consecutivo_documento = docs.consecutivo_documento) as fecha_eta "
              + "FROM productosXdocumentos "
              + "LEFT JOIN productos_inventario ON productosXdocumentos.id_producto=productos_inventario.id "
              + "LEFT JOIN productos_inventario_comext pic ON pic.id_producto=productos_inventario.id "
              + "LEFT JOIN tipo_loteoic ON tipo_loteoic.id = pic.id_tipo_loteoic "
              + "INNER JOIN unidades ON productos_inventario.id_uv=unidades.id "
              + "INNER JOIN documentos docs ON docs.id = productosXdocumentos.id_documento "
              + "WHERE productosXdocumentos.id_documento IN (" + strDocsMerca + ") "
              + "GROUP BY pic.id_tipo_loteoic, tipo_loteoic.descripcion, productosXdocumentos.id_documento, docs.consecutivo_documento, docs.observacion_documento ";
    }

    sql = sql + "ORDER BY id_documento, consecutivo";

    List<Object[]> listado = em.createNativeQuery(sql)
            .getResultList();

    List<DocumentoPorLotesInstruccionEmbarqueDTO> listadoDocumentos = new ArrayList<DocumentoPorLotesInstruccionEmbarqueDTO>();

    if (listado != null) {
      for (Object[] objs : listado) {

        DocumentoPorLotesInstruccionEmbarqueDTO dto = new DocumentoPorLotesInstruccionEmbarqueDTO();

        dto.setIdDocumento(objs[0] != null ? new Long(objs[0].toString()) : null);
        dto.setConsecutivo(objs[1] != null ? objs[1].toString() : null);
        dto.setIdTipoLote(objs[2] != null ? new Long(objs[2].toString()) : null);
        dto.setTotalCajas(objs[3] != null ? new Double(objs[3].toString()) : null);
        dto.setPedido(objs[4] != null ? objs[4].toString() : null);
        dto.setAsignacion(objs[5] != null ? objs[5].toString() : null);
        dto.setAviso(objs[6] != null ? objs[6].toString() : null);
        dto.setDescripcion(objs[7] != null ? objs[7].toString() : null);
        dto.setTotalCantidad(objs[8] != null ? new Double(objs[8].toString()) : null);
        dto.setConsecutivoDocumento(objs[9] != null ? objs[9].toString() : null);
        dto.setObservacionDocumento(objs[10] != null ? objs[10].toString() : null);
        dto.setFacturaProforma(objs[11] != null ? objs[11].toString() : null);
        dto.setFechaEta(objs[12] != null ? (Date) objs[12] : null);

        listadoDocumentos.add(dto);

      }
    }

    return listadoDocumentos;

  }

  @Override
  public void asignarLotesOIC(Documento fp) {
    String query = "insert into documento_x_lotesoic(id_documento,consecutivo,id_tipo_lote,fecha,total_cantidad,total_cajas,pedido,asignacion,aviso,total_peso_neto,contribucion,dex) select " + fp.getId() + ", consecutivo,id_tipo_lote,fecha,total_cantidad,total_cajas,pedido,asignacion,aviso,total_peso_neto,contribucion,dex from documento_x_lotesoic where id_documento  in (select id from documentos where consecutivo_documento ='" + fp.getObservacionDocumento() + "')";
    em.createNativeQuery(query).executeUpdate();
  }
}
