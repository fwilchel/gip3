package com.ssl.jv.gip.negocio.ejb;

import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.ssl.jv.gip.jpa.pojo.Documento;
import com.ssl.jv.gip.jpa.pojo.DocumentoXNegociacion;
import com.ssl.jv.gip.jpa.pojo.LogAuditoria;
import com.ssl.jv.gip.jpa.pojo.ProductosXCliente;
import com.ssl.jv.gip.jpa.pojo.ProductosXDocumento;
import com.ssl.jv.gip.jpa.pojo.TipoDocumento;
import com.ssl.jv.gip.jpa.pojo.Ubicacion;
import com.ssl.jv.gip.negocio.dto.CintaMagneticaDTO;
import com.ssl.jv.gip.negocio.dto.ComprobanteInformeDiarioDTO;
import com.ssl.jv.gip.negocio.dto.FacturaDirectaDTO;
import com.ssl.jv.gip.negocio.dto.ProductoFacturaDirectaDTO;
import com.ssl.jv.gip.negocio.dto.ProductoReporteTxtFacturaDirectaDTO;
import com.ssl.jv.gip.negocio.dto.ReporteVentaDTO;
import com.ssl.jv.gip.negocio.dao.DocumentoDAOLocal;
import com.ssl.jv.gip.negocio.dao.LogAuditoriaDAOLocal;
import com.ssl.jv.gip.negocio.dao.ProductoClienteDAOLocal;
import com.ssl.jv.gip.negocio.dao.ProductosXDocumentoDAOLocal;
import com.ssl.jv.gip.negocio.dao.TipoDocumentoDAOLocal;
import com.ssl.jv.gip.negocio.dao.UbicacionDAOLocal;
import com.ssl.jv.gip.web.mb.util.ConstantesDocumento;
import com.ssl.jv.gip.web.mb.util.ConstantesTipoDocumento;

import static com.ssl.jv.gip.web.util.SecurityFilter.LOGGER;

import java.util.HashMap;

/**
 * Session Bean implementation class VentasFacturacionEJB
 */
@Stateless
@LocalBean
public class VentasFacturacionEJB implements VentasFacturacionEJBLocal {

  /**
   * Default constructor.
   */
  public VentasFacturacionEJB() {
    // TODO Auto-generated constructor stub
  }

  @EJB
  private DocumentoDAOLocal documentoDAO;

  @EJB
  private ProductosXDocumentoDAOLocal productoDocumentoDAO;

  @EJB
  private ProductoClienteDAOLocal productoClienteDAO;
  
  @EJB
  private LogAuditoriaDAOLocal logAuditoriaDAO;
  
  @EJB
  private ProductosXDocumentoDAOLocal productoXDocumentoDAO;
  
  @EJB
  private UbicacionDAOLocal ubicacionDAOLocal;
  
  @EJB
  private TipoDocumentoDAOLocal tipoDocumentoDAOLocal;
  

  @Override
  public FacturaDirectaDTO consultarDocumentoFacturaDirecta(String strConsecutivoDocumento) {
    return documentoDAO.consultarDocumentoFacturaDirecta(strConsecutivoDocumento);
  }

  @Override
  public List<Documento> consultarDocumento(Map<String, Object> parametros, Long[] idEstados) {
    // TODO Auto-generated method stub
    return documentoDAO.consultarDocumento(parametros, idEstados);
  }

  @Override
  public List<ProductoFacturaDirectaDTO> consultarProductoFacturaDirecta(String strConsecutivoDocumento) {
    // TODO Auto-generated method stub
    return productoDocumentoDAO.consultarProductoFacturaDirecta(strConsecutivoDocumento);
  }

  public List<CintaMagneticaDTO> consultarCintaTestigoMagnetica(Map<String, Object> parametros) {
    // TODO Auto-generated method stub
    return documentoDAO.consultarCintaTestigoMagnetica(parametros);
  }

  public List<ComprobanteInformeDiarioDTO> consultarComprobanteInformeDiario(Map<String, Object> parametros) {
    // TODO Auto-generated method stub
    return documentoDAO.consultarComprobanteInformeDiario(parametros);
  }

  public List<ReporteVentaDTO> consultarReporteVentasFD(Map<String, Object> parametros) {
    // TODO Auto-generated method stub
    return documentoDAO.consultarReporteVentasFD(parametros);
  }

  public List<ProductoReporteTxtFacturaDirectaDTO> consultarReporteTxtVentasFD(Map<String, Object> parametros) {
    // TODO Auto-generated method stub
    return productoDocumentoDAO.consultarReporteTxtVentasFD(parametros);
  }

  @Override
  public List<Documento> consultarRemisionesPendientesPorRecibir(String consecutivo) {
    LOGGER.debug("Metodo: <<consultarRemisionesPendientesPorRecibir>>");
    Map<String, Object> parametros = new HashMap<>();
    parametros.put("idEstado", (long) ConstantesDocumento.PENDIENTE_POR_RECIBIR);
    parametros.put("idTipoDocumento", (long) ConstantesTipoDocumento.REMISION);
    parametros.put("consecutivoDocumento", consecutivo);
    return documentoDAO.consultarDocumentosPorEstadoPorTipoPorConsecutivo(parametros);
  }
  
  @Override
  public List<ProductosXCliente> consultarPorClientePuntoVenta(Long idCliente,
			Long idPuntoVenta) {
    return productoClienteDAO.consultarPorClientePuntoVenta(idCliente, idPuntoVenta);
  }
  
	/* (non-Javadoc)
	 * @see com.ssl.jv.gip.negocio.ejb.VentasFacturacionEJBLocal#crearVentaDirecta(com.ssl.jv.gip.jpa.pojo.Documento, com.ssl.jv.gip.jpa.pojo.LogAuditoria, java.util.List)
	 */
	@Override
	public Documento crearVentaDirecta(Documento documento, LogAuditoria auditoria,
			List<ProductosXDocumento> productos) {
		//Consultar consecutivo
		StringBuilder strConsecutivo = new StringBuilder();
		TipoDocumento tipoDocumento = tipoDocumentoDAOLocal.findByPK((long)ConstantesTipoDocumento.VENTA_DIRECTA);
		
		if(documento.getUbicacionDestino().getId().equals(com.ssl.jv.gip.util.Ubicacion.EXTERNA.getCodigo())){
			Ubicacion ubicacionOrigen = ubicacionDAOLocal.findByPK(documento.getUbicacionOrigen().getId());
			strConsecutivo.append(tipoDocumento.getAbreviatura()+ubicacionOrigen.getEmpresa().getId());
		}else{
			Ubicacion ubicacionDestino = ubicacionDAOLocal.findByPK(documento.getUbicacionDestino().getId());
			strConsecutivo.append(tipoDocumento.getAbreviatura()+ubicacionDestino.getEmpresa().getId());
		}
		
		documento.setConsecutivoDocumento(strConsecutivo.toString() + "-"
				+ this.documentoDAO.consultarProximoValorSecuencia(strConsecutivo.toString()+"_seq"));
		documento = (Documento) this.documentoDAO.add(documento);
//		auditoria.setIdRegTabla(documento.getId());
//		auditoria.setValorNuevo(documento.getConsecutivoDocumento());
//		this.logAuditoriaDAO.add(auditoria);
		for (ProductosXDocumento pxd : productos) {
			pxd.getId().setIdDocumento(documento.getId());
			this.productoXDocumentoDAO.add(pxd);
		}
		return documento;
	}

}
