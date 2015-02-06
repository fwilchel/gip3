package com.ssl.jv.gip.web.mb.abastecimiento;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.ssl.jv.gip.jpa.pojo.Documento;
import com.ssl.jv.gip.negocio.dto.ProductoDTO;
import com.ssl.jv.gip.negocio.dto.ProductoDespacharMercanciaDTO;
import com.ssl.jv.gip.negocio.ejb.DespachoMercanciaEJBLocal;
import com.ssl.jv.gip.web.mb.AplicacionMB;
import com.ssl.jv.gip.web.mb.UtilMB;

@ManagedBean(name="despacharMercanciaMB")
@ViewScoped
public class DespacharMercanciaVDMB extends UtilMB{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<Documento> documentos;
	private List<ProductoDespacharMercanciaDTO> productos;
	private Documento seleccionado;
	private Documento filtro;
	
	@EJB
	private DespachoMercanciaEJBLocal despachoMercancia;
	
	private Integer language=AplicacionMB.SPANISH;
	
	@PostConstruct
	public void init(){
		documentos = despachoMercancia.consultarVentasDirectas();
	}
	
	public void consultarProductosVentaDirecta(){
		productos=despachoMercancia.consultarProductoPorDocumento(seleccionado.getId()+"",seleccionado.getCliente().getId()+"");
	}

}
