package com.ssl.jv.gip.negocio.dao;

import java.math.BigDecimal;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import com.ssl.jv.gip.jpa.pojo.ItemCostoLogistico;
import com.ssl.jv.gip.negocio.dto.CostoLogisticoDTO;

@Stateless
@LocalBean
public class ItemCostoLogisticoDAO extends GenericDAO<ItemCostoLogistico> implements ItemCostoLogisticoDAOLocal {

  private static final Logger LOGGER = Logger.getLogger(ItemCostoLogisticoDAO.class);

  public ItemCostoLogisticoDAO() {
    this.persistentClass = ItemCostoLogistico.class;
  }

  public List<ItemCostoLogistico> findAll() {
    List list = em.createNamedQuery("ItemCostoLogistico.findAll")
        .getResultList();
    return list;
  }

  @SuppressWarnings("unchecked")
  public List<CostoLogisticoDTO> getCostosLogisticos(Long idCliente, List<Long> documentos, String terminoIncoterm, String puerto, String puertos, Long idCurrency, String pais, Integer tipoContenedor1, BigDecimal cantidad1, Integer tipoContenedor2, BigDecimal cantidad2, BigDecimal valorTotal) {
    if (puerto != null && puerto.equals("")) {
      puerto = null;
    }
    if (puertos != null && puertos.equals("")) {
      puertos = null;
    }
    if (pais != null && pais.equals("")) {
      pais = null;
    }
    String documento = "";
    for (Long d : documentos) {
      documento += d + ", ";
    }
    if (documento.length() > 0) {
      documento = documento.substring(0, documento.length() - 2);
    }
    String query1 = "";

    if (tipoContenedor1.equals(1) || tipoContenedor2.equals(1)) {
      query1 += "select 1 as tipo, ccl.nombre as categoria, icl.nombre as item, icl.descripcion, d.valor_total / :valorTotal * :cantidad20 as cantidad, d.valor_total / :valorTotal * :cantidad20 * case when icl.id_moneda='USD' then icl.valor else icl.valor/(select destination_source_exchange_rate from facts_currency_conversion where id=:idCurrency) end as valor, case when icl.id_moneda='USD' then icl.valor_minimo else icl.valor_minimo / (select destination_source_exchange_rate from facts_currency_conversion where id=:idCurrency) end as valorMinimo, icl.base_Fob baseFob, ccl.campo_acumula campoAcumula, d.consecutivo_documento as consecutivoDocumento, ccl.orden, ccl.id as categoriaId, icl.id as itemId "
          + "from documentos d inner join documento_x_negociacion dn on d.id=dn.id_documento inner join item_costo_logistico icl on 1=1 inner join categorias_costos_logisticos ccl on icl.id_categoria=ccl.id "
          + "where (icl.tipo=1 or icl.tipo=10) and id_cliente=:idCliente and d.id in (" + documento + ") and icl.aplica_" + terminoIncoterm + " and (icl.nombre_puerto_nal IS NULL " + (puerto != null ? " or icl.nombre_puerto_nal= :puertoNal" : "") + ") and (icl.id_pais_destino IS NULL " + (pais != null ? "or icl.id_pais_destino= :pais" : "") + ") and (icl.nombre_puertos_nal_internal IS NULL " + (puertos != null ? " or icl.nombre_puertos_nal_internal= :puertoInternal" : "") + ") ";
    }
    if (tipoContenedor1.equals(2) || tipoContenedor2.equals(2)) {
      if (!query1.equals("")) {
        query1 += "UNION ALL ";
      }
      query1 += "select 2 as tipo, ccl.nombre as categoria, icl.nombre as item, icl.descripcion, d.valor_total / :valorTotal * :cantidad40 as cantidad, d.valor_total / :valorTotal * :cantidad40 * case when icl.id_moneda='USD' then icl.valor else icl.valor/(select destination_source_exchange_rate from facts_currency_conversion where id=:idCurrency) end as valor, case when icl.id_moneda='USD' then icl.valor_minimo else icl.valor_minimo / (select destination_source_exchange_rate from facts_currency_conversion where id=:idCurrency) end as valorMinimo, icl.base_Fob as baseFob, ccl.campo_acumula campoAcumula, d.consecutivo_documento as consecutivoDocumento, ccl.orden, ccl.id as categoriaId, icl.id as itemId "
          + "from documentos d inner join documento_x_negociacion dn on d.id=dn.id_documento inner join item_costo_logistico icl on 1=1 inner join categorias_costos_logisticos ccl on icl.id_categoria=ccl.id "
          + "where (icl.tipo=2 or icl.tipo=10) and id_cliente=:idCliente and d.id in (" + documento + ") and icl.aplica_" + terminoIncoterm + " and (icl.nombre_puerto_nal IS NULL " + (puerto != null ? " or icl.nombre_puerto_nal= :puertoNal" : "") + ") and (icl.id_pais_destino IS NULL " + (pais != null ? "or icl.id_pais_destino= :pais" : "") + ")  and (icl.nombre_puertos_nal_internal IS NULL " + (puertos != null ? " or icl.nombre_puertos_nal_internal= :puertoInternal" : "") + ")  "; //group by 1,2,3,4,7,8,campo_acumula,consecutivoDocumento, ccl.orden, ccl.id, icl.id
    }
    /*if (tipoContenedor1.equals(3) || tipoContenedor2.equals(3)){
     if (!query1.equals("")){
     query1+="UNION ALL ";
     }
     query1+="select 10 as tipo, ccl.nombre as categoria, icl.nombre as item, icl.descripcion, d.valor_total / :valorTotal * :cantidad as cantidad, d.valor_total / :valorTotal * :cantidad * case when icl.id_moneda='USD' then icl.valor else (select destination_source_exchange_rate from facts_currency_conversion where id=:idCurrency)* icl.valor end as valor, case when icl.id_moneda='USD' then icl.valor_minimo else (select destination_source_exchange_rate from facts_currency_conversion where id=:idCurrency) * icl.valor_minimo end as valorMinimo, icl.base_fob as baseFob, ccl.campo_acumula campoAcumula, d.consecutivo_documento as consecutivoDocumento, ccl.orden, ccl.id as categoriaId, icl.id as itemId "+
     "from documentos d inner join documento_x_negociacion dn on d.id=dn.id_documento inner join item_costo_logistico icl on 1=1 inner join categorias_costos_logisticos ccl on icl.id_categoria=ccl.id "+
     "where icl.tipo=10 and id_cliente=:idCliente and d.id in ("+documento+") and icl.aplica_"+terminoIncoterm+" and (icl.nombre_puerto_nal IS NULL "+(puerto!=null?" or icl.nombre_puerto_nal= :puertoNal":"")+") and (icl.id_pais_destino IS NULL "+(pais!=null?"or icl.id_pais_destino= :pais":"")+") and (icl.nombre_puertos_nal_internal IS NULL "+(puertos!=null?" or icl.nombre_puertos_nal_internal= :puertoInternal":"")+") ";
     }*/
    /*
     * "select 1 as tipo, ccl.nombre as categoria, icl.nombre as item, icl.descripcion, dn.cantidad_contenedores_de_20 as cantidad, dn.cantidad_contenedores_de_20 * case when icl.id_moneda='USD' then icl.valor else (select destination_source_exchange_rate from facts_currency_conversion where id=:idCurrency)* icl.valor end as valor, case when icl.id_moneda='USD' then icl.valor_minimo else (select destination_source_exchange_rate from facts_currency_conversion where id=:idCurrency) * icl.valor_minimo end as valorMinimo, icl.base_Fob baseFob, ccl.campo_acumula campoAcumula, d.consecutivo_documento as consecutivoDocumento, ccl.orden, ccl.id as categoriaId, icl.id as itemId "+
     "from documentos d inner join documento_x_negociacion dn on d.id=dn.id_documento inner join item_costo_logistico icl on 1=1 inner join categorias_costos_logisticos ccl on icl.id_categoria=ccl.id "+
     "where icl.tipo=1 and id_cliente=:idCliente and d.id in ("+documento+") and icl.aplica_"+terminoIncoterm+" and (icl.nombre_puerto_nal IS NULL "+(puerto!=null?" or icl.nombre_puerto_nal= :puertoNal":"")+") and (icl.id_pais_destino IS NULL "+(pais!=null?"or icl.id_pais_destino= :pais":"")+") and (icl.nombre_puertos_nal_internal IS NULL "+(puertos!=null?" or icl.nombre_puertos_nal_internal= :puertoInternal":"")+") "+ 
     "UNION ALL "+ 
     "select 2, ccl.nombre, icl.nombre, icl.descripcion, sum(dn.cantidad_contenedores_de_40), sum(dn.cantidad_contenedores_de_40 * case when icl.id_moneda='USD' then icl.valor else (select destination_source_exchange_rate from facts_currency_conversion where id=:idCurrency)* icl.valor end) as valor, case when icl.id_moneda='USD' then icl.valor_minimo else (select destination_source_exchange_rate from facts_currency_conversion where id=:idCurrency) * icl.valor_minimo end as valorMinimo, icl.base_Fob, ccl.campo_acumula campoAcumula, d.consecutivo_documento as consecutivoDocumento, ccl.orden, ccl.id as categoriaId, icl.id as itemId "+
     "from documentos d inner join documento_x_negociacion dn on d.id=dn.id_documento inner join item_costo_logistico icl on 1=1 inner join categorias_costos_logisticos ccl on icl.id_categoria=ccl.id "+
     "where icl.tipo=2 and id_cliente=:idCliente and d.id in ("+documento+") and icl.aplica_"+terminoIncoterm+" and (icl.nombre_puerto_nal IS NULL "+(puerto!=null?" or icl.nombre_puerto_nal= :puertoNal":"")+") and (icl.id_pais_destino IS NULL "+(pais!=null?"or icl.id_pais_destino= :pais":"")+")  and (icl.nombre_puertos_nal_internal IS NULL "+(puertos!=null?" or icl.nombre_puertos_nal_internal= :puertoInternal":"")+") group by 1,2,3,4,7,8,campo_acumula,consecutivoDocumento, ccl.orden, ccl.id, icl.id "+
     "UNION ALL "+

     +
     "UNION ALL  "+
     "select 10 as tipo, ccl.nombre, icl.nombre, icl.descripcion, dn.cantidad_contenedores_de_20+dn.cantidad_contenedores_de_40 as cantidad, (dn.cantidad_contenedores_de_20 +dn.cantidad_contenedores_de_40) * case when icl.id_moneda='USD' then icl.valor else (select destination_source_exchange_rate from facts_currency_conversion where id=:idCurrency)* icl.valor end as valor, case when icl.id_moneda='USD' then icl.valor_minimo else (select destination_source_exchange_rate from facts_currency_conversion where id=:idCurrency) * icl.valor_minimo end as valorMinimo, icl.base_fob, ccl.campo_acumula campoAcumula, d.consecutivo_documento as consecutivoDocumento, ccl.orden, ccl.id as categoriaId, icl.id as itemId "+
     "from documentos d inner join documento_x_negociacion dn on d.id=dn.id_documento inner join item_costo_logistico icl on 1=1 inner join categorias_costos_logisticos ccl on icl.id_categoria=ccl.id "+
     "where icl.tipo=10 and id_cliente=:idCliente and d.id in ("+documento+") and icl.aplica_"+terminoIncoterm+" and (icl.nombre_puerto_nal IS NULL "+(puerto!=null?" or icl.nombre_puerto_nal= :puertoNal":"")+") and (icl.id_pais_destino IS NULL "+(pais!=null?"or icl.id_pais_destino= :pais":"")+") and (icl.nombre_puertos_nal_internal IS NULL "+(puertos!=null?" or icl.nombre_puertos_nal_internal= :puertoInternal":"")+") ";				
				
     */
    if (!query1.equals("")) {
      query1 += "UNION ALL ";
    }

    query1 += "select 3 as tipo, ccl.nombre as categoria, icl.nombre as item, icl.descripcion, sum(pd.cantidad_pallets_item) as cantidad, sum(cantidad_pallets_item * case when icl.id_moneda='USD' then icl.valor else icl.valor / (select destination_source_exchange_rate from facts_currency_conversion where id=:idCurrency) end) as valor, case when icl.id_moneda='USD' then icl.valor_minimo else icl.valor_minimo / (select destination_source_exchange_rate from facts_currency_conversion where id=:idCurrency) end as valorMinimo, icl.base_fob as baseFob, ccl.campo_acumula campoAcumula, d.consecutivo_documento as consecutivoDocumento, ccl.orden, ccl.id as categoriaId, icl.id as itemId "
        + "from documentos d inner join productosxdocumentos pd on d.id=pd.id_documento inner join item_costo_logistico icl on 1=1 inner join categorias_costos_logisticos ccl on icl.id_categoria=ccl.id "
        + "where icl.tipo=3 and id_cliente=:idCliente and d.id in (" + documento + ") and icl.aplica_" + terminoIncoterm + " and (icl.nombre_puerto_nal IS NULL " + (puerto != null ? " or icl.nombre_puerto_nal= :puertoNal" : "") + ") and (icl.id_pais_destino IS NULL " + (pais != null ? "or icl.id_pais_destino= :pais" : "") + ") and (icl.nombre_puertos_nal_internal IS NULL " + (puertos != null ? " or icl.nombre_puertos_nal_internal= :puertoInternal" : "") + ") group by 1,2,3,4,7,8,campo_acumula, consecutivoDocumento, ccl.orden, ccl.id, icl.id "
        + "UNION ALL "
        + "select 4, ccl.nombre, icl.nombre, icl.descripcion, count(1), sum(case when icl.id_moneda='USD' then icl.valor else icl.valor / (select destination_source_exchange_rate from facts_currency_conversion where id=:idCurrency) end) as valor, case when icl.id_moneda='USD' then icl.valor_minimo else icl.valor_minimo / (select destination_source_exchange_rate from facts_currency_conversion where id=:idCurrency) end as valorMinimo, icl.base_fob, ccl.campo_acumula campoAcumula, d.consecutivo_documento as consecutivoDocumento, ccl.orden, ccl.id as categoriaId, icl.id as itemId "
        + "from documentos d left join item_costo_logistico icl on 1=1 inner join categorias_costos_logisticos ccl on icl.id_categoria=ccl.id "
        + "where icl.tipo=4 and id_cliente=:idCliente and d.id in (" + documento + ") and icl.aplica_" + terminoIncoterm + " and (icl.nombre_puerto_nal IS NULL " + (puerto != null ? " or icl.nombre_puerto_nal= :puertoNal" : "") + ") and (icl.id_pais_destino IS NULL " + (pais != null ? "or icl.id_pais_destino= :pais" : "") + ") and (icl.nombre_puertos_nal_internal IS NULL " + (puertos != null ? " or icl.nombre_puertos_nal_internal= :puertoInternal" : "") + ") group by 1,2,3,4,7,8,campo_acumula, consecutivoDocumento, ccl.orden, ccl.id, icl.id "
        + "UNION ALL "
        + "select 5, ccl.nombre, icl.nombre, icl.descripcion, sum(dl.total_cantidad), sum(dl.total_cantidad * case when icl.id_moneda='USD' then icl.valor else icl.valor / (select destination_source_exchange_rate from facts_currency_conversion where id=:idCurrency) end) as valor, case when icl.id_moneda='USD' then icl.valor_minimo else icl.valor_minimo / (select destination_source_exchange_rate from facts_currency_conversion where id=:idCurrency) end as valorMinimo, icl.base_fob, ccl.campo_acumula campoAcumula, d.consecutivo_documento as consecutivoDocumento, ccl.orden, ccl.id as categoriaId, icl.id as itemId "
        + "from documentos d inner join documento_x_lotesoic dl on d.id=dl.id_documento inner join item_costo_logistico icl on 1=1 inner join categorias_costos_logisticos ccl on icl.id_categoria=ccl.id "
        + "where icl.tipo=5 and id_cliente=:idCliente and d.id in (" + documento + ") and icl.aplica_" + terminoIncoterm + " and (icl.nombre_puerto_nal IS NULL " + (puerto != null ? " or icl.nombre_puerto_nal= :puertoNal" : "") + ") and (icl.id_pais_destino IS NULL " + (pais != null ? "or icl.id_pais_destino= :pais" : "") + ") and (icl.nombre_puertos_nal_internal IS NULL " + (puertos != null ? " or icl.nombre_puertos_nal_internal= :puertoInternal" : "") + ") group by 1,2,3,4,7,8,campo_acumula, consecutivoDocumento, ccl.orden, ccl.id, icl.id "
        + "UNION ALL  "
        + "select 7, ccl.nombre, icl.nombre, icl.descripcion, dn.total_peso_bruto, dn.total_peso_bruto * case when rcl.id_moneda='USD' then rcl.valor else rcl.valor / (select destination_source_exchange_rate from facts_currency_conversion where id=:idCurrency) end as valor, case when icl.id_moneda='USD' then icl.valor_minimo else icl.valor_minimo / (select destination_source_exchange_rate from facts_currency_conversion where id=:idCurrency) end as valorMinimo, icl.base_Fob, ccl.campo_acumula campoAcumula, d.consecutivo_documento as consecutivoDocumento, ccl.orden, ccl.id as categoriaId, icl.id as itemId "
        + "from documentos d inner join documento_x_negociacion dn on d.id=dn.id_documento inner join item_costo_logistico icl on 1=1inner join categorias_costos_logisticos ccl on icl.id_categoria=ccl.id inner join rango_costo_logistico rcl on rcl.id_item_costo_logistico=icl.id "
        + "where icl.tipo=7 and id_cliente=:idCliente and d.id in (" + documento + ") and dn.total_peso_bruto>=rcl.desde and dn.total_peso_bruto<rcl.hasta and icl.aplica_" + terminoIncoterm + " and (icl.nombre_puerto_nal IS NULL " + (puerto != null ? " or icl.nombre_puerto_nal= :puertoNal" : "") + ")  and (icl.id_pais_destino IS NULL " + (pais != null ? "or icl.id_pais_destino= :pais" : "") + ")  and (icl.nombre_puertos_nal_internal IS NULL " + (puertos != null ? " or icl.nombre_puertos_nal_internal= :puertoInternal" : "") + ") "
        + "UNION ALL "
        + "select 8, ccl.nombre, icl.nombre, icl.descripcion, sum(dn.total_peso_bruto), sum(dn.total_peso_bruto * case when icl.id_moneda='USD' then icl.valor else icl.valor / (select destination_source_exchange_rate from facts_currency_conversion where id=:idCurrency) end) as valor, case when icl.id_moneda='USD' then icl.valor_minimo else icl.valor_minimo / (select destination_source_exchange_rate from facts_currency_conversion where id=:idCurrency) end as valorMinimo, icl.base_fob, ccl.campo_acumula campoAcumula, d.consecutivo_documento as consecutivoDocumento, ccl.orden, ccl.id as categoriaId, icl.id as itemId "
        + "from documentos d inner join documento_x_negociacion dn on d.id=dn.id_documento inner join item_costo_logistico icl on 1=1 inner join categorias_costos_logisticos ccl on icl.id_categoria=ccl.id "
        + "where icl.tipo=8 and id_cliente=:idCliente and d.id in (" + documento + ") and icl.aplica_" + terminoIncoterm + " and (icl.nombre_puerto_nal IS NULL " + (puerto != null ? " or icl.nombre_puerto_nal= :puertoNal" : "") + ")  and (icl.id_pais_destino IS NULL " + (pais != null ? "or icl.id_pais_destino= :pais" : "") + ") and (icl.nombre_puertos_nal_internal IS NULL " + (puertos != null ? " or icl.nombre_puertos_nal_internal= :puertoInternal" : "") + ") group by 1,2,3,4,7,8,campo_acumula, consecutivoDocumento, ccl.orden, ccl.id, icl.id "
        + "UNION ALL  "
        + "select 9, ccl.nombre, icl.nombre, icl.descripcion, sum(dn.total_peso_neto), sum(dn.total_peso_neto * case when icl.id_moneda='USD' then icl.valor else icl.valor / (select destination_source_exchange_rate from facts_currency_conversion where id=:idCurrency) end) as valor, case when icl.id_moneda='USD' then icl.valor_minimo else icl.valor_minimo / (select destination_source_exchange_rate from facts_currency_conversion where id=:idCurrency) end as valorMinimo, icl.base_fob, ccl.campo_acumula campoAcumula, d.consecutivo_documento as consecutivoDocumento, ccl.orden, ccl.id as categoriaId, icl.id as itemId "
        + "from documentos d inner join documento_x_negociacion dn on d.id=dn.id_documento inner join item_costo_logistico icl on 1=1 inner join categorias_costos_logisticos ccl on icl.id_categoria=ccl.id "
        + "where icl.tipo=9 and id_cliente=:idCliente and d.id in (" + documento + ") and icl.aplica_" + terminoIncoterm + " and (icl.nombre_puerto_nal IS NULL " + (puerto != null ? " or icl.nombre_puerto_nal= :puertoNal" : "") + ") and (icl.id_pais_destino IS NULL " + (pais != null ? "or icl.id_pais_destino= :pais" : "") + ") and (icl.nombre_puertos_nal_internal IS NULL " + (puertos != null ? " or icl.nombre_puertos_nal_internal= :puertoInternal" : "") + ") group by 1,2,3,4,7,8,campo_acumula, consecutivoDocumento, ccl.orden, ccl.id, icl.id ";

    /*if (puerto!=null){
     query1+="UNION ALL  "+
     "select 11 as tipo, ccl.nombre, icl.nombre, icl.nombre_puerto_nal, 1 as cantidad, case when icl.id_moneda='USD' then icl.valor else (select destination_source_exchange_rate from facts_currency_conversion where id=:idCurrency)* icl.valor end as valor, case when icl.id_moneda='USD' then icl.valor_minimo else (select destination_source_exchange_rate from facts_currency_conversion where id=:idCurrency) * icl.valor_minimo end as valorMinimo, icl.base_fob, ccl.campo_acumula campoAcumula "+
     "from documentos d inner join item_costo_logistico icl on 1=1 inner join categorias_costos_logisticos ccl on icl.id_categoria=ccl.id "+
     "where icl.tipo=11 and id_cliente=:idCliente and d.id in ("+documento+") "+(puerto!=null?"AND icl.nombre_puerto_nal = :puertoNal ":"")+" and (icl.id_pais_destino IS NULL "+(pais!=null?"or icl.id_pais_destino= :pais":"")+") and icl.aplica_"+terminoIncoterm+" ";
     }
     if (puertos!=null){
     query1+="UNION ALL  "+
     "select 12 as tipo, ccl.nombre, icl.nombre, icl.nombre_puertos_nal_internal, 1 as cantidad, case when icl.id_moneda='USD' then icl.valor else (select destination_source_exchange_rate from facts_currency_conversion where id=:idCurrency)* icl.valor end as valor, case when icl.id_moneda='USD' then icl.valor_minimo else (select destination_source_exchange_rate from facts_currency_conversion where id=:idCurrency) * icl.valor_minimo end as valorMinimo, icl.base_fob, ccl.campo_acumula campoAcumula "+
     "from documentos d inner join item_costo_logistico icl on 1=1 inner join categorias_costos_logisticos ccl on icl.id_categoria=ccl.id "+
     "where icl.tipo=12 and id_cliente=:idCliente and d.id in ("+documento+") "+(puertos!=null?"AND icl.nombre_puertos_nal_internal = :puertoInternal ":"AND ")+" and (icl.id_pais_destino IS NULL "+(pais!=null?"or icl.id_pais_destino= :pais":"")+") and icl.aplica_"+terminoIncoterm+" ";
     }*/
    /*
     * 
     * 
     * 
     * icl.valor * ( "+ 
     "(select sum(valor_total) from documentos d where id_cliente=:idCliente and d.id in ("+documento+")) + "+
     "(select sum(valor) from ( "+
     query1+
     ") subq where basefob) "+
     ") /100
     */
    String query2 = "SELECT * FROM (" + query1
        + "UNION ALL "
        + "select 6 as tipo, ccl.nombre, icl.nombre, icl.descripcion, icl.valor, 0, case when icl.id_moneda='USD' then icl.valor_minimo else icl.valor_minimo / (select destination_source_exchange_rate from facts_currency_conversion where id=:idCurrency) end as valorMinimo, icl.base_fob, ccl.campo_acumula campoAcumula, '' as consecutivoDocumento, ccl.orden, ccl.id as categoriaId, icl.id as itemId "
        + "from item_costo_logistico icl left outer join categorias_costos_logisticos ccl on icl.id_categoria=ccl.id "
        + "where icl.tipo=6 and icl.aplica_" + terminoIncoterm + " ) sqn WHERE valor>0 or tipo=6 ORDER BY orden, categoria, item, descripcion, consecutivoDocumento";

    Query q = em.createNativeQuery(query2, CostoLogisticoDTO.class);
    q = q.setParameter("idCliente", idCliente).setParameter("idCurrency", idCurrency);
    if (puerto != null) {
      q = q.setParameter("puertoNal", puerto);
    }
    if (puertos != null) {
      q = q.setParameter("puertoInternal", puertos);
    }
    if (pais != null) {
      q = q.setParameter("pais", pais);
    }
    if (!tipoContenedor1.equals(0) || !tipoContenedor2.equals(0)) {
      if (tipoContenedor1.equals(1)) {
        q = q.setParameter("cantidad20", cantidad1);
      }
      if (tipoContenedor2.equals(1)) {
        q = q.setParameter("cantidad20", cantidad2);
      }
      if (tipoContenedor1.equals(2)) {
        q = q.setParameter("cantidad40", cantidad1);
      }
      if (tipoContenedor2.equals(2)) {
        q = q.setParameter("cantidad40", cantidad2);
      }
      /*if (tipoContenedor1.equals(3)){
       q=q.setParameter("cantidad", cantidad1);
       }
       if (tipoContenedor2.equals(3)){
       q=q.setParameter("cantidad", cantidad2);
       }*/
      q = q.setParameter("valorTotal", valorTotal);
    }
    System.out.println(query2);
    return q.getResultList();

  }

  @Override
  public List<String> getPuertosNacionales() {
    Query q = em.createNamedQuery("ItemCostoLogistico.puertosNacionales");
    return q.getResultList();

  }

  @Override
  public List<String> getPuertosInternacionales(String idPais) {
    Query q = em.createNamedQuery("ItemCostoLogistico.puertosInternacionales").setParameter("idPais", idPais);
    return q.getResultList();

  }

}
