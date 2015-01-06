package com.ssl.jv.gip.web.util;

/**
 * <p>Title: ErrorConstants</p>
 *
 * <p>Description: Constantes para los errores de la aplicacion GIP</p>
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
public enum ErrorConstants {
	NO_RESULT("La consulta no ha retornado resultados"),
	SQL_EXCEPTION("Ocurrio un fallo con la base de datos"),
	UNHANDLED_EXCEPTION("Ocurrio un error no manejado");
	
	private String description;
	
	private ErrorConstants(String description){
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
}
