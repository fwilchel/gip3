package com.ssl.jv.gip.negocio.ejb;

import com.ssl.jv.gip.jpa.pojo.Documento;
import com.ssl.jv.gip.jpa.pojo.LogAuditoria;
import com.ssl.jv.gip.jpa.pojo.ProductosXCliente;
import com.ssl.jv.gip.jpa.pojo.ProductosXDocumento;
import com.ssl.jv.gip.negocio.dao.ProductoClienteDAOLocal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import org.apache.log4j.Logger;

@Stateless
@LocalBean
public class ComercioNacionalEJB implements ComercioNacionalEJBLocal {

  private static final Logger LOGGER = Logger.getLogger(ComercioNacionalEJB.class);
  @EJB
  private ProductoClienteDAOLocal productoClienteDAO;

  @Override
  public List<ProductosXCliente> consultarProductosXCliente(Long idCliente) {
    LOGGER.trace("Metodo: <<consultarProductosXCliente>>");
    Map<String, Object> parametros = new HashMap<>();
    parametros.put("idCliente", idCliente);
    return productoClienteDAO.buscarPorConsultaNombrada(ProductosXCliente.NQ_BUSCAR_PRODUCTOS_X_CLIENTES_ACTIVOS, parametros);
  }

  @Override
  public Documento ingresarSolicitudComercioNacional(Documento solicitud, List<ProductosXDocumento> productosXDocumento, LogAuditoria log) {
    LOGGER.trace("Metodo: <<ingresarSolicitudComercioNacional>>");
    return null;
  }

}
