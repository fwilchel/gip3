package com.ssl.jv.gip.negocio.ejb;

import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.ssl.jv.gip.jpa.pojo.Documento;
import com.ssl.jv.gip.negocio.dto.FacturaDirectaDTO;
import com.ssl.jv.gip.negocio.dao.DocumentoDAOLocal;
import com.ssl.jv.gip.negocio.dao.ProductoClienteDAOLocal;


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
    
    //@EJB
	//private ProductoClienteDAOLocal productoClienteDAO;
    
    
    @Override
    public FacturaDirectaDTO consultarDocumentoFacturaDirecta(String strConsecutivoDocumento)
    {
    return documentoDAO.consultarDocumentoFacturaDirecta(strConsecutivoDocumento);
    }
    
    
    
    @Override
	public List<Documento> consultarDocumento(Map<String, Object> parametros) {
		// TODO Auto-generated method stub
		return documentoDAO.consultarDocumento(parametros);
	}
    
    /*
    
    @Override
    public  List<ProductoFacturaDirectaDTO> consultarProductoFacturaDirecta(String strConsecutivoDocumento)
    {
    return productoClienteDAO.consultarProductoFacturaDirecta(strConsecutivoDocumento);
    }
    
    */
    
  

}
