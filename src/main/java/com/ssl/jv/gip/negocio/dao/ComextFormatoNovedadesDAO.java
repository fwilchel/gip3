package com.ssl.jv.gip.negocio.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import com.ssl.jv.gip.jpa.pojo.ComextFormatoNovedade;
import com.ssl.jv.gip.negocio.dto.ComextFormatoNovedadesDTO;

@Stateless
@LocalBean
public class ComextFormatoNovedadesDAO extends GenericDAO<ComextFormatoNovedade> implements ComextFormatoNovedadesDAOLocal {

  private static final Logger LOGGER = Logger.getLogger(ComextFormatoNovedadesDAO.class);

  public ComextFormatoNovedadesDAO() {
    this.persistentClass = ComextFormatoNovedade.class;
  }

  public List<ComextFormatoNovedadesDTO> consultarComextFormatoNovedades() {

    List<ComextFormatoNovedadesDTO> lista = new ArrayList<ComextFormatoNovedadesDTO>();

    String sql = "";

    sql = "SELECT  comext_formato_novedades.id,"
        + "(Case comext_formato_novedades.distribucion_optima when false then 'No' Else 'Si' End) as DISTRIBUCION_OPTIMA"
        + ",(Case comext_formato_novedades.documentos_solicitados when false then 'No' Else 'Si' End) as DOC_SOLICITADO"
        + ",(Case comext_formato_novedades.formatodiligenciado when false then 'No' Else 'Si' End) as FORMATO_DILIGENCIADO"
        + ",(Case comext_formato_novedades.producto_completo when false then 'No' Else 'Si' End) as PROD_COMPLETO"
        + ",(Case comext_formato_novedades.productos_buen_estado when false then 'No' Else 'Si' End) as PROD_BUEN_ESTADO"
        + ",(Case comext_formato_novedades.productos_tiempo when false then 'No' Else 'Si' End) as PROD_TIEMPO"
        + ",comext_formato_novedades.obsdistoptima,comext_formato_novedades.obsdocsolicitados,comext_formato_novedades.obsprodbuenestado,"
        + "comext_formato_novedades.obsprodcompleto,comext_formato_novedades.obsprodtiempo,documentos.consecutivo_documento,"
        + "documentos.fecha_generacion "
        + "FROM comext_formato_novedades "
        + "inner join documentos on comext_formato_novedades.consecutivo_documento=documentos.id "
        + "order by documentos.consecutivo_documento";

    List<Object[]> listado = em.createNativeQuery(sql).getResultList();

    if (listado != null) {
      for (Object[] objs : listado) {
        ComextFormatoNovedadesDTO dto = new ComextFormatoNovedadesDTO();

        dto.setId(objs[0] != null ? Long.parseLong(objs[0].toString()) : null);
        dto.setDistribucionOptima(objs[1] != null ? objs[1].toString() : null);
        dto.setDocSolicitado(objs[2] != null ? objs[2].toString() : null);
        dto.setFormatoDiligenciado(objs[3] != null ? objs[3].toString() : null);
        dto.setProdCompleto(objs[4] != null ? objs[4].toString() : null);
        dto.setProdBuenEstado(objs[5] != null ? objs[5].toString() : null);
        dto.setProdTiempo(objs[6] != null ? objs[6].toString() : null);
        dto.setObsdistoptima(objs[7] != null ? objs[7].toString() : null);
        dto.setObsdocsolicitados(objs[8] != null ? objs[8].toString() : null);
        dto.setObsprodbuenestado(objs[9] != null ? objs[9].toString() : null);
        dto.setObsprodcompleto(objs[10] != null ? objs[10].toString() : null);
        dto.setObsprodtiempo(objs[11] != null ? objs[11].toString() : null);
        dto.setConsecutivo(objs[12] != null ? objs[12].toString() : null);
        dto.setFecha(objs[13] != null ? (Date) objs[13] : null);

        lista.add(dto);
      }
    }

    return lista;

  }

}
