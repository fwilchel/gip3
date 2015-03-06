package com.ssl.jv.gip.negocio.dto;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class ReportePeticionExcelDTO extends HttpServlet  {

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
				"attachment; filename=\"" + nombre + ".xlsx\"");
		wb.write(httpServletResponse.getOutputStream());

	}
	
}
