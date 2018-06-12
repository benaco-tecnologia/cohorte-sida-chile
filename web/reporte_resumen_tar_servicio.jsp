<%@page import="cohorte.Usuario"%>
<%@ page contentType="text/html;" pageEncoding="UTF-8" language="java" errorPage="" session="true" %>
<%@ page language="java" import="java.text.DecimalFormat" %>
<%@ page language="java" import="java.text.NumberFormat" %>
<%@ page language="java" import="java.util.Locale" %>
<%@ page language="java" import="java.text.DecimalFormatSymbols" %>
<%@ page language="java" import="java.sql.*" %>
<%@ include file="conection.jsp" %>
<%
ResultSet rs_tar = null;
try{
    if (!conexion.isClosed()){
      Statement st = conexion.createStatement();
      rs_tar = st.executeQuery("SELECT UPPER(Zona.Nombre) AS ZonaNombre, UPPER(Centro.ServicioSalud) AS ServicioSalud, COUNT(*) AS Total, COUNT(if(Paciente.SexoID=1, 1, NULL)) AS Femenino, COUNT(if(Paciente.SexoID=2, 1, NULL)) AS Masculino "+
                                     "FROM Paciente, Centro, Zona "+
                                     "WHERE Centro.ServicioSalud IS NOT NULL "+
                                            "AND Paciente.ID IN (SELECT PacienteID FROM Terapia WHERE Terapia.NumeroTar = 1) "+
                                            "AND Paciente.CentroID = Centro.ID "+
                                        "AND Centro.ZonaID = Zona.ID "+
                                     "GROUP BY Zona.Nombre, Centro.ServicioSalud "+
                                     "ORDER BY Zona.Orden;");
    }
}catch(Exception e){
}
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <meta name="viewport" content="user-scalable=yes" />
        <link type="text/css" rel="stylesheet" href="/Cohorte2/faces/javax.faces.resource/theme.css?ln=primefaces-aristo" />
        <link type="text/css" rel="stylesheet" href="/Cohorte2/faces/javax.faces.resource/cohorte.css?ln=css" />
        <link type="text/css" rel="stylesheet" href="/Cohorte2/faces/javax.faces.resource/menu.css?ln=css" />
        <link type="text/css" rel="stylesheet" href="/Cohorte2/faces/javax.faces.resource/jquery-ui.min.css?ln=css" />
        <link type="text/css" rel="stylesheet" href="/Cohorte2/faces/javax.faces.resource/jquery-ui.structure.min.css?ln=css" />
        <link type="text/css" rel="stylesheet" href="/Cohorte2/faces/javax.faces.resource/jquery-ui.theme.min.css?ln=css" />
        <script type="text/javascript" src="/Cohorte2/faces/javax.faces.resource/jquery.js?ln=scripts"></script>
        <script type="text/javascript" src="/Cohorte2/faces/javax.faces.resource/jquery-ui.js?ln=scripts"></script>
        <script type="text/javascript" src="/Cohorte2/faces/javax.faces.resource/despliegues.js?ln=scripts"></script>
        <link type="text/css" rel="stylesheet" href="/Cohorte2/faces/javax.faces.resource/primefaces.css?ln=primefaces&amp;v=5.0" />
        <script type="text/javascript" src="/Cohorte2/faces/javax.faces.resource/jquery/jquery.js?ln=primefaces&amp;v=5.0"></script>
        <script type="text/javascript" src="/Cohorte2/faces/javax.faces.resource/jquery/jquery-plugins.js?ln=primefaces&amp;v=5.0"></script>
        <script type="text/javascript" src="/Cohorte2/faces/javax.faces.resource/primefaces.js?ln=primefaces&amp;v=5.0"></script>
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <meta name="viewport" content="user-scalable=yes" />
        <script type="text/javascript" src="https://www.google.com/jsapi"></script>
        <!--[if lt IE 9]&gt;
            &lt;script src="http://css3-mediaqueries-js.googlecode.com/svn/trunk/css3-mediaqueries.js"&gt;&lt;/script&gt;
        &lt;![endif]-->
        <title>Reporte Pacientes Agrupados por Servicio Salud con Terapia Iniciada</title>
        
    </head>
    <body>
        <div class="no-movil"><img src="/Cohorte2/faces/javax.faces.resource/logoCohorte.png?ln=images" style="width: 150px;" />
            <div style="color:#052C58;position: absolute;top: 15px;left: 170px;font-size: 30px;">Sistema<br />Registro Electrónico<br />Cohorte SIDA</div>
        </div>
        <ul id="nav">
            <li><a href="/Cohorte2/faces/principal.xhtml">Inicio</a></li>
            <li><a href="#">Paciente</a>
                <ul>
                    <li><a href="/Cohorte2/faces/datosPersonales.xhtml">Ingresar</a>
                    <li class="no-movil"><a href="/Cohorte2/faces/busqueda.xhtml">Buscar</a></li>
                </ul>
            </li>
            <li><a href="/Cohorte2/faces/busqueda.xhtml">Búsqueda</a></li>
            <li><a href="#">Reportes</a>
                <ul>
                    <li><a href="/Cohorte2/reporte_resumen_drogas.jsp">Drogas por Terapia</a></li>
                    <li><a href="/Cohorte2/reporte_resumen_tar_zona.jsp">Pacientes por Zona</a></li>
                    <li><a href="/Cohorte2/reporte_resumen_tar_servicio.jsp">Pacientes por Servicio</a></li>
                    <li><a href="/Cohorte2/reporte_resumen_tar.jsp">Pacientes por Centro</a></li>
                    <li><a href="/Cohorte2/reporte_resumen_tar_edad.jsp">Pacientes por Edad</a></li>
                    <li><a href="/Cohorte2/reporte_resumen_tar_centro_edad.jsp">Pacientes por Centro/Edad</a></li>
                </ul>
            </li>
            <li><a href="#">Exportar</a>
                <ul>
                    <li><a href="/Cohorte/personales_xls.jsp?c=">Personales</a>
                    </li>
                    <li><a href="/Cohorte/basales_xls.jsp?c=">Basales</a>
                    </li>
                    <li><a href="/Cohorte/terapias_xls.jsp?c=">Terapias</a>
                    </li>
                    <li><a href="/Cohorte/controles_xls.jsp?c=">Controles</a>
                    </li>
                    <li><a href="/Cohorte/laboratorios_xls.jsp?c=">Laboratorios</a>
                    </li>
                    <li><a href="/Cohorte/modificaciones_xls.jsp?c=">Registro Modificaciones</a>
                    </li>
                    <li><a href="/Cohorte2/base_csv.jsp?c=">Base CSV</a>
                    </li>
                </ul>
            </li>
            <li><a href="/Cohorte/Documentos/" target="_blank">Documentación</a>
            </li>
            <li class="no-movil"><a href="/Cohorte2/faces/principal.xhtml" onclick="window.location='/Cohorte2/'; return false;">Salir</a></li>
        </ul>
        <div id="content" class="left_content">
            <h1>Reporte Pacientes Agrupados por Servicio Salud con Terapia Iniciada</h1>
            <div id="j_idt85:tbl" class="ui-datatable ui-widget">
                <div class="ui-datatable-header ui-widget-header ui-corner-top">
                Reporte
                </div>
            <div class="ui-datatable-tablewrapper">
                <table role="grid">
                <thead id="j_idt85:tbl_head">
                    <tr role="row">
                        <th class="ui-state-default" role="columnheader"><span class="ui-column-title">Zona</span></th>
                        <th class="ui-state-default" role="columnheader"><span class="ui-column-title">Servicio Salud</span></th>
                        <th class="ui-state-default" role="columnheader"><span class="ui-column-title">Pacientes</span></th>
                        <th class="ui-state-default" role="columnheader"><span class="ui-column-title">Mujeres</span></th>
                        <th class="ui-state-default" role="columnheader"><span class="ui-column-title">Hombres</span></th>
                    </tr>
                </thead>
                <tbody>
            <%
                if(rs_tar!=null){
                while (rs_tar.next()){
                    //Zona.Nombre AS ZonaNombre, Centro.ServicioSalud AS ServicioSalud, Centro.Nombre AS CentroNombre, COUNT(*) AS Total, COUNT(if(Paciente.SexoID=1, 1, NULL)) AS Femenino, COUNT(if(Paciente.SexoID=2, 1, NULL)) AS Masculino
                    //Integer.parseInt(String.valueOf(rs.getObject("ZonaNombre")));
                    out.println("<tr role=\"row\">");
                    out.println("<td>"+String.valueOf(rs_tar.getObject("ZonaNombre"))+"</td>");
                    out.println("<td>"+String.valueOf(rs_tar.getObject("ServicioSalud"))+"</td>");
                    
                    out.print("<td align=\"right\">");
                    {
                        DecimalFormatSymbols unusualSymbols = new DecimalFormatSymbols(Locale.US);
                        unusualSymbols.setDecimalSeparator(',');
                        unusualSymbols.setGroupingSeparator('.');
                        String strange = "#,##0.#";
                        DecimalFormat weirdFormatter = new DecimalFormat(strange, unusualSymbols);
                        weirdFormatter.setGroupingSize(3);
                        out.print(weirdFormatter.format(Integer.parseInt(String.valueOf(rs_tar.getObject("Total")))));
                    }
                    out.println("</td>");
                    
                    out.print("<td align=\"right\">");
                    {
                        DecimalFormatSymbols unusualSymbols = new DecimalFormatSymbols(Locale.US);
                        unusualSymbols.setDecimalSeparator(',');
                        unusualSymbols.setGroupingSeparator('.');
                        String strange = "#,##0.#";
                        DecimalFormat weirdFormatter = new DecimalFormat(strange, unusualSymbols);
                        weirdFormatter.setGroupingSize(3);
                        out.print(weirdFormatter.format(Integer.parseInt(String.valueOf(rs_tar.getObject("Femenino")))));
                    }
                    out.println("</td>");
                    
                    out.print("<td align=\"right\">");
                    {
                        DecimalFormatSymbols unusualSymbols = new DecimalFormatSymbols(Locale.US);
                        unusualSymbols.setDecimalSeparator(',');
                        unusualSymbols.setGroupingSeparator('.');
                        String strange = "#,##0.#";
                        DecimalFormat weirdFormatter = new DecimalFormat(strange, unusualSymbols);
                        weirdFormatter.setGroupingSize(3);
                        out.print(weirdFormatter.format(Integer.parseInt(String.valueOf(rs_tar.getObject("Masculino")))));
                    }
                    out.println("</td>");
                    out.println("</tr>");
                  }
                }
            %>
                </tbody>
                </table>
            </div>
            </div>
                <p>&nbsp;</p>
                <p>&nbsp;</p>
        </div>
        <div id="bottom">
            <%  
            try {
                Usuario u = (Usuario)session.getAttribute("Usuario");
                out.print(u.getNombre()+" "+u.getApellido());
                out.print("&nbsp;|&nbsp;");
                if(!u.esUsuario()){
                    out.print("TODOS");
                }else{
                    out.print((String)session.getAttribute("CentroNombre"));
                }
            } catch (Exception ex){}
            %>&nbsp;&nbsp;&nbsp;
        </div>
    </body>
</html>
<%@ include file="disconection.jsp" %>