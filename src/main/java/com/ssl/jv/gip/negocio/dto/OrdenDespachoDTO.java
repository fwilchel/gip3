package com.ssl.jv.gip.negocio.dto;

import java.io.Serializable;
import com.ssl.jv.gip.jpa.pojo.Documento;
import com.ssl.jv.gip.jpa.pojo.ProductosXDocumento;
import java.util.List;

/**DTO para el manejo de front de las �rdenes de despacho
 * 
 * @author Daniel Cortes
 * @version 1
 * @since 08/01/2015
 *
 */
public class OrdenDespachoDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7621285605701774144L;

	private Documento documento;
	private List<ProductosXDocumento> productos;
	
}
