package com.ssl.jv.gip.negocio.dao;

import java.util.List;

import javax.ejb.Local;

import com.ssl.jv.gip.jpa.pojo.CuentaContable;
import com.ssl.jv.gip.negocio.dto.CuentaContableDTO;

@Local
public interface CuentaContableDAOLocal extends IGenericDAO<CuentaContable> {

  public List<CuentaContableDTO> consultarReporteFacturasFX(String consecDoc, String fechaIni, String fechaFin);

  public List<CuentaContableDTO> consultarReporteFacturasFD(String consecDoc, String fechaIni, String fechaFin);

}
