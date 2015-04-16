<%@ page import="net.sf.jasperreports.engine.*, java.util.*, java.io.*" %>
<html>
  <head>
    <title>JasperReports - Compilador</title>
  </head>
  <body bgcolor="white">
    <%
    try{
      System.out.println(application.getRealPath("/reportes/"+request.getParameter("reporte")));
      JasperCompileManager.compileReportToFile(application.getRealPath("/reportes/"+request.getParameter("reporte")));

      //System.out.println(s);
      out.print("<span class=\"bold\">El reporte "+ request.getParameter("reporte") + "(XML) fue compilado.</span>");
    }catch(JRException e){
      out.println(e);
    }

    %>

  </body>
</html>
