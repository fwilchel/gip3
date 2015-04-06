package com.ssl.jv.gip.negocio.dao;

import java.math.BigDecimal;
import java.util.List;

import javax.ejb.Local;

import com.ssl.jv.gip.jpa.pojo.ItemCostoLogistico;
import com.ssl.jv.gip.negocio.dto.CostoLogisticoDTO;

@Local
public interface ItemCostoLogisticoDAOLocal extends IGenericDAO<ItemCostoLogistico>{

	public List<CostoLogisticoDTO> getCostosLogisticos(Long idCliente, List<Long> documentos, String terminoIncoterm, String puerto, String puertos, Long idCurrency, String pais, Integer tipoContenedor1, BigDecimal cantidad1, Integer tipoContenedor2, BigDecimal cantidad2, BigDecimal valorTotal);
	
	public List<String> getPuertosNacionales();

	public List<String> getPuertosInternacionales(String idPais);
	
}
