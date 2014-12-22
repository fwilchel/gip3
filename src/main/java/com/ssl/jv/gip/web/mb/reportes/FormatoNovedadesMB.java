package com.ssl.jv.gip.web.mb.reportes;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;

import com.ssl.jv.gip.negocio.dto.ComextFormatoNovedadesDTO;
import com.ssl.jv.gip.negocio.ejb.ReportesEJB;
import com.ssl.jv.gip.web.mb.UtilMB;

@ManagedBean(name="formatoNovedadesMB")
@SessionScoped
public class FormatoNovedadesMB extends UtilMB{
	
	private List<ComextFormatoNovedadesDTO> listado;
		
	@EJB
	private ReportesEJB reportesEJB;
		
	public FormatoNovedadesMB(){
		
	}
	
	@PostConstruct
	public void init(){
		listado = reportesEJB.consultarComextFormatoNovedades();
	}
	
	public void previsualizarFN(ActionEvent ae){
		
	}

	public List<ComextFormatoNovedadesDTO> getListado() {
		return listado;
	}

	public void setListado(List<ComextFormatoNovedadesDTO> listado) {
		this.listado = listado;
	}

}
