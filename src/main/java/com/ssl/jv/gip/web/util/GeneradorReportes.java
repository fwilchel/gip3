package com.ssl.jv.gip.web.util;

import java.io.IOException;
import java.util.Hashtable;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


//import net.sf.jxls.transformer.XLSTransformer;

/*
 Tipo
 pdf, html, excel, jxls

 Tipo Datasource
 1 JRDataSource se instancia pasando session
 2 Connection
 3 MB que implementa JRDataSource en session
   parametros:
   tipo,tipoDatasource,reporte y mb
 4 JRDataSource se instancia pasando session y parametros
    parametros:
    tipo,datasource,tipoDatasource y reporte

 DataSource
 clase a instanciar si tipo es 1


 Reporte
 el .jasper o .xls

 */

@WebServlet("/generadorReportes")
public class GeneradorReportes extends HttpServlet  {

	public GeneradorReportes(){

	}
	private static final String CONTENT_TYPE = "text/html";
	
	

	//Initialize global variables
	public void init() throws ServletException {
	}

	//Process the HTTP Get request
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Iniciando GR"+request);
		
		HttpSession session = request.getSession(false);
		//ApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
		
		
		System.out.println("Iniciando GR1");
		try {
			Hashtable<String, String> parametrosR=new Hashtable<String, String>();
			if (request.getParameter("tipo")!=null)
				parametrosR.put("tipo", request.getParameter("tipo"));
			if (request.getParameter("datasource")!=null)
				parametrosR.put("datasource", request.getParameter("datasource"));
			if (request.getParameter("tipoDatasource")!=null)
				parametrosR.put("tipoDatasource", request.getParameter("tipoDatasource"));
			if (request.getParameter("reporte")!=null)
				parametrosR.put("reporte", request.getParameter("reporte"));
			if (request.getParameter("mb")!=null)
				parametrosR.put("mb", request.getParameter("mb"));
			if (request.getParameter("otros")!=null)
				parametrosR.put("otros", request.getParameter("otros"));
			
			String nombreReporte;
			System.out.println("**** EXC1 ");
			if (request.getParameter("tipo").equals("jxls")) {
				nombreReporte = this.getServletContext().getRealPath("/reportes/" + request.getParameter("reporte") + "");
			} else {
				nombreReporte = this.getServletContext().getRealPath("/reportes/" + request.getParameter("reporte") + ".jasper");
			}
			
			/*Enumeration enu=getServletContext().getAttributeNames();
			while (enu.hasMoreElements())
				System.out.println(enu.nextElement());
			
			basicDataSource = (BasicDataSource)getServletContext().getAttribute("DataSource");*/

			com.ssl.jv.gip.util.GeneradorReportes.generar(parametrosR, nombreReporte, session, request, response);
			System.out.println("**** EXCNN ");

		} catch (Exception e) {
			System.out.println("**** EXC "+e.getMessage());
			if (response!=null){
				response.setContentType("text/html");
				response.setHeader("content-type", "text/html");
			}
			request.getSession(false).setAttribute("msg", e);
			request.getRequestDispatcher("/jsp/mensaje.jsp").forward(request, response);
		}
	}

	public void destroy() {
	}

}
