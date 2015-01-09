package com.ssl.jv.gip.negocio.dto;

import java.io.Serializable;

/**DTO para el manejo de front de las �rdenes de despacho
 * 
 * @author Daniel Cortes
 * @version 1
 * @since 08/01/2015
 *
 */
public class OrdenDeDespachoDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7621285605701774144L;

	private String documento;
	private String consecutivoDocumento;
	private String fechaGeneracion;
	private String cliente;
	private String direccion;
	private String telefono;
	private String contacto;
	private String terminoInconterm;
	private String lugarInconterm;
	private double cantidadContenedores20;
	private double cantidadContenedores40;
	private String observacionesDocumento;
	private String observacionesMarcacion;
	private String despachadoEn;
	private String fechaCargue;
	
}
