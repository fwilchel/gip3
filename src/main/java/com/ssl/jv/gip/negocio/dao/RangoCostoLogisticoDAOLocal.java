package com.ssl.jv.gip.negocio.dao;

import java.util.List;

import javax.ejb.Local;

import com.ssl.jv.gip.jpa.pojo.ItemCostoLogistico;
import com.ssl.jv.gip.jpa.pojo.RangoCostoLogistico;

@Local
public interface RangoCostoLogisticoDAOLocal extends IGenericDAO<RangoCostoLogistico>{
	public List<RangoCostoLogistico> findByItem(ItemCostoLogistico icl);
}
