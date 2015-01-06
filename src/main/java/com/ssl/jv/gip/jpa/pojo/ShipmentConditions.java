package com.ssl.jv.gip.jpa.pojo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import com.ssl.jv.gip.web.util.Utilidad;

/**
 * <p>Title: ShipmentConditions</p>
 *
 * <p>Description: POJO para la informacion basica de una instruccion de embarque</p>
 *
 * <p>Copyright: Copyright (c) 2014</p>
 *
 * <p>Company: Soft Studio Ltda.</p>
 *
 * @author Sebastian Gamba Pinilla
 * @email sebas.gamba02@gmail.com
 * @phone 311 89376670
 * @version 1.0
 */
@Entity
//@NamedNativeQueries({
//	@NamedNativeQuery(name="ShipmentConditions.getAll", query="SELECT "
//														+ " terminos_transporte.id, " 
//														+ " terminos_transporte.fecha_embarque, "  
//														+ " cli.nombre as nombre_cliente "
//													+ " FROM terminos_transporte "
//														+ " JOIN terminos_transporte_x_documento ON terminos_transporte_x_documento.id_terminos_transporte = terminos_transporte.id "  
//														+ " JOIN documentos doc ON terminos_transporte_x_documento.id_documento = doc.id " 
//														+ " JOIN clientes cli ON doc.id_cliente = cli.id " 
//													 + " GROUP BY terminos_transporte.id, terminos_transporte.fecha_embarque, cli.nombre "
//													 + " ORDER BY id DESC"),
//})
public class ShipmentConditions implements Serializable{

	private static final long serialVersionUID = -3600093110693764983L;
	
	@Id
	private String id;
	@Column(name="nombre_cliente")
	private String clientName;
	@Column(name="fecha_embarque")
	private Date shipmentDate;
	
	public ShipmentConditions() {
	}

	public String getId() {
		return id;
	}

	public String getClientName() {
		return clientName;
	}

	public Date getShipmentDate() {
		return shipmentDate;
	}
	
	public String getShipmentDateString() {
		return Utilidad.convertirDateToString(shipmentDate);
	}
}
