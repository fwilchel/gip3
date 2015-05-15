package com.ssl.jv.gip.negocio.ejb;

import com.ssl.jv.gip.jpa.pojo.Documento;
import com.ssl.jv.gip.jpa.pojo.LogAuditoria;
import com.ssl.jv.gip.jpa.pojo.ProductosXCliente;
import com.ssl.jv.gip.jpa.pojo.ProductosXDocumento;
import java.util.List;

import javax.ejb.Local;

/**
 * <p>
 * Title: GIP
 * </p>
 * <p>
 * Description: GIP
 * </p>
 * <p>
 * Copyright: Copyright (c) 2014
 * </p>
 * <p>
 * Company: Soft Studio Ltda.
 * </p>
 *
 * @author Diego Poveda
 * @email diego.poveda@softstudio.co
 * @phone 3192594013
 * @version 1.0
 */
@Local
public interface ComercioNacionalEJBLocal {

  /**
   *
   * @param idCliente
   * @param idPuntoVenta
   * @return
   */
  List<ProductosXCliente> consultarProductosXCliente(Long idCliente, Long idPuntoVenta);

  /**
   *
   * @param solicitud
   * @param productosXDocumento
   * @param log
   * @return
   */
  Documento ingresarSolicitudComercioNacional(Documento solicitud, List<ProductosXDocumento> productosXDocumento, LogAuditoria log);
}
