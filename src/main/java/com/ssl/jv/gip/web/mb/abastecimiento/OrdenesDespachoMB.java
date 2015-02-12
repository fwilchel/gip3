package com.ssl.jv.gip.web.mb.abastecimiento;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;

import com.ssl.jv.gip.jpa.pojo.AgenteAduana;
import com.ssl.jv.gip.jpa.pojo.Documento;
import com.ssl.jv.gip.negocio.dao.DocumentoDAO;
import com.ssl.jv.gip.negocio.dto.ProductoDTO;
import com.ssl.jv.gip.negocio.dto.ProductoGenerarFacturaPFDTO;
import com.ssl.jv.gip.negocio.ejb.MaestrosEJB;
import com.ssl.jv.gip.negocio.ejb.OrdenDespachoEJB;
import com.ssl.jv.gip.web.mb.AplicacionMB;
import com.ssl.jv.gip.web.mb.UtilMB;
import com.ssl.jv.gip.web.util.Modo;

/**Managed Bean para ordenes de despacho
 * 
 * @author Daniel Cortes
 * @version 1.0
 * @email danicorc@gmail.com
 *
 */
@ManagedBean(name="ordenesDespachoMB")
@ViewScoped
public class OrdenesDespachoMB extends UtilMB{

	private static final long serialVersionUID = 1L;
	
	private List<Documento> documentos=new ArrayList<Documento>();
	private List<ProductoDTO> productos;
	private Documento seleccionado;
	private Documento filtro;
	private double totalCantidad=0;
	private double totalPesoNeto=0;
	private double totalPesoBruto=0;
	private double totalCantidadCajas=0;
	private double totalCantidadPorEmbalaje=0;
	
	@EJB
	private OrdenDespachoEJB orden;
	
	private Integer language=AplicacionMB.SPANISH;
	
	private Modo modo;
	
	public OrdenesDespachoMB() {
	}

	@PostConstruct
	public void init(){
		documentos = orden.consultarOrdenesDeDespacho();
	}

	public Documento getSeleccionado() {
		return seleccionado;
	}

	public void setSeleccionado(Documento seleccionado) {
		this.seleccionado = seleccionado;
		this.modo=Modo.EDICION;
	}
		
	public List<Documento> getDocumentos() {
		return documentos;
	}

	public void setDocumentos(List<Documento> documentos) {
		this.documentos = documentos;
	}
	
	public List<ProductoDTO> getProductos() {
		return productos;
	}

	public void setProductos(List<ProductoDTO> productos) {
		this.productos = productos;
	}

	public double getTotalCantidad() {
		return totalCantidad;
	}

	public void setTotalCantidad(double totalCantidad) {
		this.totalCantidad = totalCantidad;
	}

	public double getTotalPesoNeto() {
		return totalPesoNeto;
	}

	public void setTotalPesoNeto(double totalPesoNeto) {
		this.totalPesoNeto = totalPesoNeto;
	}

	public double getTotalPesoBruto() {
		return totalPesoBruto;
	}

	public void setTotalPesoBruto(double totalPesoBruto) {
		this.totalPesoBruto = totalPesoBruto;
	}

	public double getTotalCantidadCajas() {
		return totalCantidadCajas;
	}

	public void setTotalCantidadCajas(double totalCantidadCajas) {
		this.totalCantidadCajas = totalCantidadCajas;
	}

	public double getTotalCantidadPorEmbalaje() {
		return totalCantidadPorEmbalaje;
	}

	public void setTotalCantidadPorEmbalaje(double totalCantidadPorEmbalaje) {
		this.totalCantidadPorEmbalaje = totalCantidadPorEmbalaje;
	}

	public Documento getFiltro() {
		return filtro;
	}

	public void setFiltro(Documento filtro) {
		this.filtro = filtro;
	}


	public void nuevo(){
		seleccionado=new Documento();
		this.modo=Modo.CREACION;
	}
	
	public boolean esCreacion(){
		return this.modo!=null && this.modo.equals(Modo.CREACION);
	}

	public void guardar() {
		try {
			if (modo.equals(Modo.CREACION)) {
				this.orden.crearOrdenDeDespacho(seleccionado);
				this.documentos.add(0, this.seleccionado);
				this.modo = Modo.EDICION;
			} else {
				this.orden.actualizarOrdenDeDespacho(seleccionado);
			}
			this.addMensajeInfo(AplicacionMB.getMessage("Operacion generar orden despacho realizada con exito", language));
		} catch (Exception ex) {
			this.addMensajeError(AplicacionMB.getMessage("generarOrdenDespachoBotonError", language));
		}
	}

	public void consultarOrdenDeDespacho(){
		productos=orden.consultarProductoPorDocumento(seleccionado.getId()+"",seleccionado.getCliente().getId()+"");
		totalCantidad=0;
		totalPesoNeto=0;
		totalPesoBruto=0;
		totalCantidadCajas=0;
		totalCantidadPorEmbalaje=0;
		for (ProductoDTO p : productos) {
			this.totalCantidad+=p.getCantidad().doubleValue();
//			this.totalPesoNeto+=p.getPesoNeto().doubleValue();
//			this.totalPesoBruto+=p.getPesoBruto().doubleValue();
//			this.totalCantidadCajas+=p.getCantidadCajas().doubleValue();
//			this.totalCantidadPorEmbalaje+=p.getCantidadPorEmbalaje().doubleValue();
		}
	}
	
}
