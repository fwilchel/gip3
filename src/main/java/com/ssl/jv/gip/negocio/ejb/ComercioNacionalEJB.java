package com.ssl.jv.gip.negocio.ejb;

import com.ssl.jv.gip.jpa.pojo.Documento;
import com.ssl.jv.gip.jpa.pojo.LogAuditoria;
import com.ssl.jv.gip.jpa.pojo.ProductosXCliente;
import com.ssl.jv.gip.jpa.pojo.ProductosXDocumento;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import org.apache.log4j.Logger;

@Stateless
@LocalBean
public class ComercioNacionalEJB implements ComercioNacionalEJBLocal {

    private static final Logger LOGGER = Logger.getLogger(ComercioNacionalEJB.class);

  @Override
  public List<ProductosXCliente> consultarProductosXCliente(Long idCliente) {
    LOGGER.trace("Metodo: <<consultarProductosXCliente>>");
    return null;
  }

  @Override
  public Documento ingresarSolicitudComercioNacional(Documento solicitud, List<ProductosXDocumento> productosXDocumento, LogAuditoria log) {
    LOGGER.trace("Metodo: <<ingresarSolicitudComercioNacional>>");
    return null;
  }

}
