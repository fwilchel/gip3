package com.ssl.jv.gip.negocio.dao;

import java.util.List;

import javax.ejb.Local;

import com.ssl.jv.gip.jpa.pojo.ItemCostoLogistico;
import com.ssl.jv.gip.negocio.dto.CostoLogisticoDTO;

@Local
public interface ItemCostoLogisticoDAOLocal extends IGenericDAO<ItemCostoLogistico>{

	public List<CostoLogisticoDTO> getCostosLogisticos(Long idCliente, List<Long> documentos, String terminoIncoterm, String puerto, String puertos, Long idCurrency, String pais);
	
	public List<String> getPuertosNacionales();

	public List<String> getPuertosInternacionales(String idPais);
	
}
