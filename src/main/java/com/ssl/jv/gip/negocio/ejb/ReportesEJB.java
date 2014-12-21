package com.ssl.jv.gip.negocio.ejb;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.ssl.jv.gip.negocio.dao.ComextFormatoNovedadesDAO;
import com.ssl.jv.gip.negocio.dto.ComextFormatoNovedadesDTO;

/**
 * Session Bean implementation class ReportesEJB
 */
@Stateless
@LocalBean
public class ReportesEJB implements ReportesEJBLocal {

	@EJB
	private ComextFormatoNovedadesDAO comextFormatoNovedadesDAO;
	
    /**
     * Default constructor. 
     */
    public ReportesEJB() {
        // TODO Auto-generated constructor stub
    }
    
    public List<ComextFormatoNovedadesDTO> consultarComextFormatoNovedades(){
    	return comextFormatoNovedadesDAO.consultarComextFormatoNovedades();
    }

}
