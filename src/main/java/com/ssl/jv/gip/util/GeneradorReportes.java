package com.ssl.jv.gip.util;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.StringTokenizer;

import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.JRRtfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.oasis.JROdsExporter;
import net.sf.jasperreports.engine.export.oasis.JROdtExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRPptxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleHtmlExporterOutput;
import net.sf.jasperreports.export.SimpleOdsReportConfiguration;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleWriterExporterOutput;
import net.sf.jasperreports.export.SimpleXlsReportConfiguration;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;

import org.apache.log4j.Logger;



public class GeneradorReportes {
	
	private static final Logger logger = Logger.getLogger(GeneradorReportes.class);
	
	public static void generar(Hashtable<String, String> parametrosR, String nombreReporte, HttpSession session, HttpServletRequest request, HttpServletResponse response) throws IOException, Exception{

		try {
			
			String tipo = parametrosR.get("tipo"); //pdf, xml, html, rtf, xls, jxl, csv, odt, ods, docx, xlsx, pptx, xhtml
			//String dataSource = parametrosR.get("datasource");
			//String tipoDatasource = parametrosR.get("tipoDatasource"); //1:Connection, 2:JRDataSource, 
			//String reporte = parametrosR.get("reporte");
			//String mb = parametrosR.get("mb");

			/*if (reporte == null) {
				reporte = "prueba";
			}*/

			File reportFile = new File(nombreReporte);
			
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("BASE_DIR", reportFile.getParentFile());
			parameters.put("CLIENTE", "Procafecol");
			parameters.put("NIT", "");
			parameters.put("LOGO_CLIENTE", "Logo.png");
			String otros = parametrosR.get("otros");
			if (otros != null && !otros.equals("")) {
				StringTokenizer st = new StringTokenizer(otros, "|");
				while (st.hasMoreTokens()) {
					String parametro = st.nextToken();
					StringTokenizer st2 = new StringTokenizer(parametro, "^");
					String campo = st2.nextToken();
					String valor = "";
					if (st2.hasMoreTokens()) {
						valor = st2.nextToken();
					}
					parameters.put(campo, valor);
				}
			}
			
			InitialContext initialContext = new InitialContext();
			DataSource dataSource = (DataSource)initialContext.lookup("java:/jdbc/GIPDS");
			
			String salida=nombreReporte+(int)(Math.random()*1000000000);
			
			System.out.println(new File(salida).getAbsolutePath());
			
			long start = System.currentTimeMillis();
			JasperPrint jasperPrint=JasperFillManager.fillReport(nombreReporte, parameters, dataSource.getConnection());
			if (tipo.equals("pdf"))
				JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());
			else if (tipo.equals("html"))
				JasperExportManager.exportReportToHtmlFile(jasperPrint, salida+".html");
			else if (tipo.equals("xml"))
				JasperExportManager.exportReportToXmlStream(jasperPrint, response.getOutputStream());
			else if (tipo.equals("rtf")){
				JRRtfExporter exporter = new JRRtfExporter();
				exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
				exporter.setExporterOutput(new SimpleWriterExporterOutput(response.getOutputStream()));
				exporter.exportReport();
			}else if (tipo.equals("xls")){
				JRXlsExporter exporter = new JRXlsExporter();
				exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
				exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(response.getOutputStream()));
				SimpleXlsReportConfiguration configuration = new SimpleXlsReportConfiguration();
				configuration.setOnePagePerSheet(true);
				exporter.setConfiguration(configuration);
				exporter.exportReport();
			}else if (tipo.equals("jxls")){
				net.sf.jasperreports.engine.export.JExcelApiExporter exporter = 
						new net.sf.jasperreports.engine.export.JExcelApiExporter();
				exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
				exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(response.getOutputStream()));
				net.sf.jasperreports.export.SimpleJxlReportConfiguration configuration = 
					new net.sf.jasperreports.export.SimpleJxlReportConfiguration();
				configuration.setOnePagePerSheet(true);
				exporter.setConfiguration(configuration);
				exporter.exportReport();
			}else if (tipo.equals("csv")){
				JRCsvExporter exporter = new JRCsvExporter();
				
				exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
				exporter.setExporterOutput(new SimpleWriterExporterOutput(response.getOutputStream()));
				exporter.exportReport();
				
			}else if (tipo.equals("odt")){
				JROdtExporter exporter = new JROdtExporter();
				exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
				exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(response.getOutputStream()));
				exporter.exportReport();
				
			}else if (tipo.equals("ods")){
				JROdsExporter exporter = new JROdsExporter();
				
				exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
				exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(response.getOutputStream()));
				SimpleOdsReportConfiguration configuration = new SimpleOdsReportConfiguration();
				configuration.setOnePagePerSheet(true);
				exporter.setConfiguration(configuration);
				
				exporter.exportReport();
			}else if (tipo.equals("docx")){
				JRDocxExporter exporter = new JRDocxExporter();
				
				exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
				exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(response.getOutputStream()));
				
				exporter.exportReport();
			}else if (tipo.equals("xlsx")){
				JRXlsxExporter exporter = new JRXlsxExporter();
				
				exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
				exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(response.getOutputStream()));
				SimpleXlsxReportConfiguration configuration = new SimpleXlsxReportConfiguration();
				configuration.setOnePagePerSheet(true);
				exporter.setConfiguration(configuration);
				
				exporter.exportReport();
				
			}else if (tipo.equals("pptx")){
				JRPptxExporter exporter = new JRPptxExporter();
				
				exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
				exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(response.getOutputStream()));

				exporter.exportReport();
				
			}else if (tipo.equals("xhtml")){
				net.sf.jasperreports.engine.export.JRXhtmlExporter exporter = 
						new net.sf.jasperreports.engine.export.JRXhtmlExporter();
				exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
				exporter.setExporterOutput(new SimpleHtmlExporterOutput(response.getOutputStream()));
				exporter.exportReport();
			}
			System.err.println("Creation time : " + (System.currentTimeMillis() - start));
			
		
		} catch (JRException e) {
			errores(response, e);
		} catch (SQLException e) {
			errores(response, e);
		} catch (SecurityException e) {
			errores(response, e);
		} catch (IllegalArgumentException e) {
			errores(response, e);
		} catch (Exception e) {
			errores(response, e);
			e.printStackTrace();
		}
	}
	public static void errores(HttpServletResponse response, Exception e) {
		try {
			PrintWriter out = response.getWriter();
			response.setContentType("text/html");
			out.println("<html><head><title>Errores al Generar Reporte</title></head><body><h1>Errores al generar el reporte</h1>");
			out.println("Mensaje: " + e.getMessage() + "<p>");
			out.println("Pila: ");
			e.printStackTrace(out);
			out.println("<p></body></html>");
			out.close();
		} catch (IOException ex) {
			System.out.println(ex);
		}
	}
}