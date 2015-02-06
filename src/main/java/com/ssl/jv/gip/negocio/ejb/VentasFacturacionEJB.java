package com.ssl.jv.gip.negocio.ejb;

import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.ssl.jv.gip.jpa.pojo.Documento;
import com.ssl.jv.gip.negocio.dto.CintaMagneticaDTO;
import com.ssl.jv.gip.negocio.dto.ComprobanteInformeDiarioDTO;
import com.ssl.jv.gip.negocio.dto.FacturaDirectaDTO;
import com.ssl.jv.gip.negocio.dto.ProductoFacturaDirectaDTO;
import com.ssl.jv.gip.negocio.dto.ProductoReporteTxtFacturaDirectaDTO;
import com.ssl.jv.gip.negocio.dto.ReporteVentaDTO;
import com.ssl.jv.gip.negocio.dao.DocumentoDAOLocal;


import com.ssl.jv.gip.negocio.dao.ProductoClienteDAOLocal;
import com.ssl.jv.gip.negocio.dao.ProductosXDocumentoDAOLocal;

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
    
    
    @Override
    public FacturaDirectaDTO consultarDocumentoFacturaDirecta(String strConsecutivoDocumento)
    {
    	return documentoDAO.consultarDocumentoFacturaDirecta(strConsecutivoDocumento);
    }
    
    
    
    @Override
	public List<Documento> consultarDocumento(Map<String, Object> parametros, Long[] idEstados) {
		// TODO Auto-generated method stub
		return documentoDAO.consultarDocumento(parametros , idEstados);
	}


    @Override
	public List<ProductoFacturaDirectaDTO> consultarProductoFacturaDirecta(String strConsecutivoDocumento) {
		// TODO Auto-generated method stub
		return  productoDocumentoDAO.consultarProductoFacturaDirecta(strConsecutivoDocumento);
	}



	public List<CintaMagneticaDTO> consultarCintaTestigoMagnetica(Map<String, Object> parametros) {
		// TODO Auto-generated method stub
		return documentoDAO.consultarCintaTestigoMagnetica(parametros);
	}



	public List<ComprobanteInformeDiarioDTO> consultarComprobanteInformeDiario(	Map<String, Object> parametros) {
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



	
	


   
    
    
    
    
    
    
    
  

}
