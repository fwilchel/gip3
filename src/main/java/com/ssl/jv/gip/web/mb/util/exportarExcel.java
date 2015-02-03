//=======================================================================
//ARCHIVO exportarExcel.java
//FECHA CREACIÓN: 26 Julio 2006  
//AUTOR: Compucetro Colombia LTDA.
//AUTOR: Diana Garcia
//=======================================================================
package com.ssl.jv.gip.web.mb.util;

//=======================================================================
//BIBLIOTECAS REQUERIDAS
//=======================================================================

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/*
 *{  Clase que exporta un archivo .xls por medio de http
 * @author=Compucentro Colombia Ltda. 
 * @author Diana Garcia 
 * @version=1.0, 
 * @since=26/07/2006,
 * $Author: dgarcia $,
 * $Revision: 1.1 $, 
 * $Date: 2006/07/26 22:41:34 $} 
 * */

public class exportarExcel extends HttpServlet {

	/**
	 * Metodo que abre el excel via protocolo http
	 * 
	 * @param Requuest
	 * @param Response
	 * @param Workbook,
	 *            contiene la informacion del libro de excel creado en el bean
	 * @param String,
	 *            nombre del arcivo en excel a abrir
	 */
	public void doGet(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, HSSFWorkbook wb,
			String nombre) throws ServletException, IOException {

		httpServletResponse.setContentType("application/vnd.ms-excel");
		httpServletResponse.addHeader("Content-disposition",
				"attachment; filename=\"" + nombre + ".xls\"");
		wb.write(httpServletResponse.getOutputStream());

	}

}
