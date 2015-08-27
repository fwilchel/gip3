package com.ssl.jv.gip.negocio.dao;

import java.util.List;

import javax.ejb.Local;

import com.ssl.jv.gip.jpa.pojo.MovimientosInventarioComext;
import java.math.BigDecimal;
import java.util.Map;

@Local
public interface MovimientosInventarioComextDAOLocal extends IGenericDAO<MovimientosInventarioComext> {

  public Map<Long, BigDecimal> getMapUltimosSaldos();

  public List<MovimientosInventarioComext> getUltimosSaldos();

  public List<MovimientosInventarioComext> consultarPorSKU(String sku);

  public List<MovimientosInventarioComext> consultarPorSKU(String sku, boolean ultimoSaldo);

}
