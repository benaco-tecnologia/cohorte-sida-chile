<%@page import="cohorte.Usuario"%>
<%@ page contentType="text/html;" pageEncoding="UTF-8" language="java" errorPage="" session="true" %>
<%@ page language="java" import="java.text.DecimalFormat" %>
<%@ page language="java" import="java.text.NumberFormat" %>
<%@ page language="java" import="java.util.Locale" %>
<%@ page language="java" import="java.text.DecimalFormatSymbols" %>
<%@ page language="java" import="java.sql.*" %>
<%@ include file="conection.jsp" %>
<%
    response.setContentType("text/csv; charset=latin1");
    response.setHeader("Content-Disposition","inline; filename=BASECOHORTE.csv");
    out.clear();
	out.print("Basales_Codigo CONASIDA;Centro;Num F Local;Fecha atc;Rut;Fecha nac;Inmigrante;Nacionalidad;Sexo;HSH;ISP C;FechaIngresoCentro;CD4_Ingreso;RazonTest;UsoAnticonceptivo;Nivel Educ;Empleo;Etnia;F riesgo;Class CDC L;Class CDC N;EDS1;Fecha EDS1;EDS2;Fecha EDS2;EDS3;Fecha EDS3;EDS4;Fecha EDS4;PPD;PPD Fecha;PPD Tratamiento;PPD RadioTorax;HBsAg;HBsAg Fecha;VHC;Fecha VHC;Tox;Tox Fecha;VDRL;VDRL Fecha;Chagas;Chagas Fecha;PAP;PAP Fecha;CD4 preTARV;CD4 PRE TARV Fecha;CD4 PRE TAR Porcentaje;CV preTARV;CV preTARV fecha;Basales_CV Logaritmo;Terapias_Codigo CONASIDA;Num TARV;Fecha;Droga 1;Droga 2;Droga 3;Droga 4;Droga 5;Droga 6;No Continua;Causa Termino;Razon Toxicidad;Fecha Termino;Terapias_Geno;Geno Fecha;Controles_CD4_CV_Codigo CONASIDA;Num Control;CD4 Resultado;CD4 Fecha;CD4 Porcentaje;CV Resultado;CV Fecha;Controles_CD4_CV_CV Logaritmo;Controles_CD4_CV_Colest Total;Controles_CD4_CV_Colest HDL;Controles_CD4_CV_Colest LDL;Controles_CD4_CV_Trigliceridos;Controles_CD4_CV_Glicemia Diagnostico;Controles_CD4_CV_Glicemia Fecha;Controles_CD4_CV_Glicemia;Controles_CD4_CV_Peso;Controles_CD4_CV_Hematocrito;Controles_CD4_CV_Hematocrito Fecha;Controles_CD4_CV_GPT;Controles_CD4_CV_GOT;\n");
	String query = "select Paciente.Codigo, Centro.Nombre, Ficha, 'Fecha Atc', CONCAT(FROM_BASE64(Rut),'-',DV), FechaNacimiento, IF(PaisOrigenID is not null, IF(PaisOrigenID=5,'NO','SI'),''), PaisOrigen.Nombre, Sexo.Nombre, PreferenciaSexual.Nombre, FechaISP, FechaIngreso, Paciente.CD4, RazonTestID, UsoAnticonceptivo.Nombre, NivelEducacional.Nombre, Empleo.Nombre, Etnia.Nombre, FactorRiesgo.Nombre, DatoBasal.ClasificacionL, DatoBasal.ClasificacionN, DatoBasal.Enf1ID, DatoBasal.Enf1Fecha, DatoBasal.Enf2ID, DatoBasal.Enf2Fecha, DatoBasal.Enf3ID, DatoBasal.Enf3Fecha, DatoBasal.Enf4ID, DatoBasal.Enf4Fecha, DatoBasal.Ppd, DatoBasal.PpdFecha, DatoBasal.PpdTratamiento, DatoBasal.RxTorax, DatoBasal.Hbs, DatoBasal.HbsaFecha, DatoBasal.Vhc, DatoBasal.VhcFecha, DatoBasal.Toxoplasmosis, DatoBasal.ToxoFecha, DatoBasal.Vdrl, DatoBasal.VdrlFecha, DatoBasal.Chagas, DatoBasal.ChagasFecha, DatoBasal.Pap, DatoBasal.PapFecha, DatoBasal.CD4, DatoBasal.CD4Fecha, DatoBasal.PCD4, DatoBasal.CV, DatoBasal.CVFecha, DatoBasal.Log,	DatoBasal.PacienteID as PacienteID from Paciente left join DatoBasal on Paciente.ID=DatoBasal.PacienteID left join Centro on Paciente.CentroID=Centro.ID left join Sexo on Paciente.SexoID = Sexo.ID left join PaisOrigen on Paciente.PaisOrigenID=PaisOrigen.ID left join PreferenciaSexual on Paciente.PreferenciaSexualID=PreferenciaSexual.ID left join UsoAnticonceptivo on Paciente.UsoAnticonceptivoID=UsoAnticonceptivo.ID left join Empleo on Paciente.EmpleoID=Empleo.ID left join Etnia on Paciente.EtniaID=Etnia.ID left join FactorRiesgo on Paciente.FactorRiesgoID=FactorRiesgo.ID left join NivelEducacional on Paciente.NivelEducacionalID=NivelEducacional.ID LIMIT 50";
	if (!conexion.isClosed()){
		Statement st = conexion.createStatement();
		ResultSet rs = st.executeQuery(query);
		ResultSetMetaData metadata = rs.getMetaData();
		int numberOfColumns = metadata.getColumnCount();
		while (rs.next()){
			String pacienteID=String.valueOf(rs.getObject("PacienteID"));
			String codigo=String.valueOf(rs.getObject("Codigo"));
			int maxTerapia = 0;
			int maxControl = 0;
			int maxLaboratorio = 0;
			// Verificamos si existen terapias, controles o laboratorio
			query = "select MAX(NumeroTar) as Numero from Terapia where PacienteID = "+pacienteID;
			Statement st3 = conexion.createStatement();
			ResultSet rs3 = st3.executeQuery(query);
			if(rs3.next()){
				if(rs3.getObject("Numero")!=null){
					maxTerapia = Integer.parseInt(String.valueOf(rs3.getObject("Numero")));
				}
			}
			query = "select MAX(NumeroControl) as Numero from Control where PacienteID = "+pacienteID;
			Statement st4 = conexion.createStatement();
			ResultSet rs4 = st4.executeQuery(query);
			if(rs4.next()){
				if(rs4.getObject("Numero")!=null){
					maxControl = Integer.parseInt(String.valueOf(rs4.getObject("Numero")));
				}
			}
			query = "select MAX(NumeroControl) as Numero from Laboratorio where PacienteID = "+pacienteID;
			Statement st5 = conexion.createStatement();
			ResultSet rs5 = st5.executeQuery(query);
			if(rs5.next()){
				if(rs5.getObject("Numero")!=null){
					maxLaboratorio = Integer.parseInt(String.valueOf(rs5.getObject("Numero")));
				}
			}
			
			if(maxTerapia==0&&maxControl==0&&maxLaboratorio==0){
				
				int i = 1;
				while(i < numberOfColumns) {
					if(rs.getString(i)!=null){
						out.print(""+rs.getString(i)+";");
					}else{
						out.print(";");
					}
					i++;
				}
				
				// <!-- Terapias -->
				out.print(""+codigo+";;;;;;;;;;;;;;;");
				out.print(";");
				out.print(";");
				out.print(";");
				out.print(";");
				out.print(";");
				out.print(";");
				out.print(";");
				out.print(";");
				out.print(";");
				out.print(";");
				out.print(";");
				out.print(";");
				out.print(";");
				out.print(";");
				out.print(";");
				out.print(";");
				out.print(";");
				out.print(";");
				out.print(";");
				out.print(";");
				out.print("\n");
			}
			
			int max = 0;
			if (maxControl>maxLaboratorio){
				max = maxControl;
			}else{
				max = maxLaboratorio;
			}
			
			if(maxTerapia>=1){
				// Se obtienen terepias
				query = "select *, IF(NoContinua='CA','CAMBIO',IF(NoContinua='SU','SUSPENDIO',IF(NoContinua='FA','FALLECIO',IF(NoContinua='SD','SIN DATOS','')))) as NContinua, CausaTermino.Nombre as NCausaTermino, RazonToxicidad.Nombre as NRazonToxicidad from Terapia left join CausaTermino on Terapia.CausaTerminoID=CausaTermino.ID left join RazonToxicidad on Terapia.RazonToxicidadID=RazonToxicidad.ID where PacienteID = "+pacienteID;
				st3 = conexion.createStatement();
				rs3 = st3.executeQuery(query);
				while(rs3.next()){
					// Se obtienen drogas
					String drogas = "";
					query = "SELECT Nombre, Numero FROM TerapiaDroga, Droga WHERE TerapiaDroga.TerapiaID = "+String.valueOf(rs3.getObject("ID"))+" AND TerapiaDroga.DrogaID = Droga.ID ORDER BY Numero;";
					Statement st6 = conexion.createStatement();
					ResultSet rs6 = st6.executeQuery(query);
					int k = 0;
					while(rs6.next()){
						drogas += ""+String.valueOf(rs6.getObject("Nombre"))+";";
						k++;
					}
					for(int n=k+1; n<=6; n++){
						drogas += ";";
					}
					if(max>0){
						for(int i=1; i<=max; i++){
							
							int j = 1;
							while(j < numberOfColumns) {
								if(rs.getString(j)!=null){
									out.print(""+rs.getString(j)+";");
								}else{
									out.print(";");
								}
								j++;
							}
							// <!-- Terapias -->
							out.print(""+codigo+";"+String.valueOf(rs3.getObject("NumeroTar"))+";"+String.valueOf(rs3.getObject("Fecha"))+";"+drogas+""+String.valueOf(rs3.getObject("NContinua"))+";"+String.valueOf(rs3.getObject("NCausaTermino"))+";"+String.valueOf(rs3.getObject("NRazonToxicidad"))+";"+String.valueOf(rs3.getObject("FechaTermino"))+";"+String.valueOf(rs3.getObject("Geno"))+";"+String.valueOf(rs3.getObject("GenoFecha"))+";");
							out.print(""+codigo+";");
							
							out.print(i+"");
							out.print(";");
							query = "SELECT Resultado, Fecha, PCD4 FROM Control WHERE PacienteID = "+pacienteID+" AND Tipo='CD' AND NumeroControl = "+i+";";
							Statement st7 = conexion.createStatement();
							ResultSet rs7 = st7.executeQuery(query);
							if(rs7.next()){
								out.print(""+String.valueOf(rs7.getObject("Resultado"))+";");
								out.print(""+String.valueOf(rs7.getObject("Fecha"))+";");
								out.print(""+String.valueOf(rs7.getObject("PCD4"))+";");
							}else{
								out.print(";");
								out.print(";");
								out.print(";");
							}
							query = "SELECT Resultado, Fecha, Logaritmo FROM Control WHERE PacienteID = "+pacienteID+" AND Tipo='CV' AND NumeroControl = "+i+";";
							st7 = conexion.createStatement();
							rs7 = st7.executeQuery(query);
							if(rs7.next()){
								out.print(""+String.valueOf(rs7.getObject("Resultado"))+";");
								out.print(""+String.valueOf(rs7.getObject("Fecha"))+";");
								out.print(""+String.valueOf(rs7.getObject("Logaritmo"))+";");
							}else{
								out.print(";");
								out.print(";");
								out.print(";");
							}
							query = "SELECT ColesTotal, ColesHdl, ColesLdl, Trigli FROM Laboratorio WHERE PacienteID = "+pacienteID+" AND Tipo='DI' AND NumeroControl = "+i+";";
							st7 = conexion.createStatement();
							rs7 = st7.executeQuery(query);
							if(rs7.next()){
								out.print(""+String.valueOf(rs7.getObject("ColesTotal"))+";");
								out.print(""+String.valueOf(rs7.getObject("ColesHdl"))+";");
								out.print(""+String.valueOf(rs7.getObject("ColesLdl"))+";");
								out.print(""+String.valueOf(rs7.getObject("Trigli"))+";");
							}else{
								out.print(";");
								out.print(";");
								out.print(";");
								out.print(";");
							}
							query = "SELECT Diagnostico, Fecha, Glice FROM Laboratorio WHERE PacienteID = "+pacienteID+" AND Tipo='GL' AND NumeroControl = "+i+";";
							st7 = conexion.createStatement();
							rs7 = st7.executeQuery(query);
							if(rs7.next()){
								out.print(""+String.valueOf(rs7.getObject("Diagnostico"))+";");
								out.print(""+String.valueOf(rs7.getObject("Fecha"))+";");
								out.print(""+String.valueOf(rs7.getObject("Glice"))+";");
							}else{
								out.print(";");
								out.print(";");
								out.print(";");
							}
							query = "SELECT Peso FROM Laboratorio WHERE PacienteID = "+pacienteID+" AND Tipo='HI' AND NumeroControl = "+i+";";
							st7 = conexion.createStatement();
							rs7 = st7.executeQuery(query);
							if(rs7.next()){
								out.print(""+String.valueOf(rs7.getObject("Peso"))+";");
							}else{
								out.print(";");
							}
							query = "SELECT Hematocrito, Fecha, Gpt, Got FROM Laboratorio WHERE PacienteID = "+pacienteID+" AND Tipo='HE' AND NumeroControl = "+i+";";
							st7 = conexion.createStatement();
							rs7 = st7.executeQuery(query);
							if(rs7.next()){
								out.print(""+String.valueOf(rs7.getObject("Hematocrito"))+";");
								out.print(""+String.valueOf(rs7.getObject("Fecha"))+";");
								out.print(""+String.valueOf(rs7.getObject("Gpt"))+";");
								out.print(""+String.valueOf(rs7.getObject("Got"))+";");
							}else{
								out.print(";");
								out.print(";");
								out.print(";");
								out.print(";");
							}
							out.print("\n");
						}
					}else{
						
						int j = 1;
						while(j < numberOfColumns) {
							if(rs.getString(j)!=null){
								out.print(""+rs.getString(j)+";");
							}else{
								out.print(";");
							}
							j++;
						}
						// <!-- Terapias -->
						out.print(""+codigo+";"+String.valueOf(rs3.getObject("NumeroTar"))+";"+String.valueOf(rs3.getObject("Fecha"))+";"+drogas+""+String.valueOf(rs3.getObject("NContinua"))+";"+String.valueOf(rs3.getObject("NCausaTermino"))+";"+String.valueOf(rs3.getObject("NRazonToxicidad"))+";"+String.valueOf(rs3.getObject("FechaTermino"))+";"+String.valueOf(rs3.getObject("Geno"))+";"+String.valueOf(rs3.getObject("GenoFecha"))+";");
						out.print(""+codigo+";");
						out.print(";");
						out.print(";");
						out.print(";");
						out.print(";");
						out.print(";");
						out.print(";");
						out.print(";");
						out.print(";");
						out.print(";");
						out.print(";");
						out.print(";");
						out.print(";");
						out.print(";");
						out.print(";");
						out.print(";");
						out.print(";");
						out.print(";");
						out.print(";");
						out.print(";");
						out.print("\n");
					}					
				}
			}else{
				for(int i=1; i<=max; i++){
					
					int j = 1;
					while(j < numberOfColumns) {
						if(rs.getString(j)!=null){
							out.print(""+rs.getString(j)+";");
						}else{
							out.print(";");
						}
						j++;
					}
					// <!-- Terapias -->
					out.print(""+codigo+";;;;;;;;;;;;;;;");
					out.print(""+codigo+";");
					
					out.print(i+"");
					out.print(";");
					query = "SELECT Resultado, Fecha, PCD4 FROM Control WHERE PacienteID = "+pacienteID+" AND Tipo='CD' AND NumeroControl = "+i+";";
					Statement st7 = conexion.createStatement();
					ResultSet rs7 = st7.executeQuery(query);
					if(rs7.next()){
						out.print(""+String.valueOf(rs7.getObject("Resultado"))+";");
						out.print(""+String.valueOf(rs7.getObject("Fecha"))+";");
						out.print(""+String.valueOf(rs7.getObject("PCD4"))+";");
					}else{
						out.print(";");
						out.print(";");
						out.print(";");
					}
					query = "SELECT Resultado, Fecha, Logaritmo FROM Control WHERE PacienteID = "+pacienteID+" AND Tipo='CV' AND NumeroControl = "+i+";";
					st7 = conexion.createStatement();
					rs7 = st7.executeQuery(query);
					if(rs7.next()){
						out.print(""+String.valueOf(rs7.getObject("Resultado"))+";");
						out.print(""+String.valueOf(rs7.getObject("Fecha"))+";");
						out.print(""+String.valueOf(rs7.getObject("Logaritmo"))+";");
					}else{
						out.print(";");
						out.print(";");
						out.print(";");
					}
					query = "SELECT ColesTotal, ColesHdl, ColesLdl, Trigli FROM Laboratorio WHERE PacienteID = "+pacienteID+" AND Tipo='DI' AND NumeroControl = "+i+";";
					st7 = conexion.createStatement();
					rs7 = st7.executeQuery(query);
					if(rs7.next()){
						out.print(""+String.valueOf(rs7.getObject("ColesTotal"))+";");
						out.print(""+String.valueOf(rs7.getObject("ColesHdl"))+";");
						out.print(""+String.valueOf(rs7.getObject("ColesLdl"))+";");
						out.print(""+String.valueOf(rs7.getObject("Trigli"))+";");
					}else{
						out.print(";");
						out.print(";");
						out.print(";");
						out.print(";");
					}
					query = "SELECT Diagnostico, Fecha, Glice FROM Laboratorio WHERE PacienteID = "+pacienteID+" AND Tipo='GL' AND NumeroControl = "+i+";";
					st7 = conexion.createStatement();
					rs7 = st7.executeQuery(query);
					if(rs7.next()){
						out.print(""+String.valueOf(rs7.getObject("Diagnostico"))+";");
						out.print(""+String.valueOf(rs7.getObject("Fecha"))+";");
						out.print(""+String.valueOf(rs7.getObject("Glice"))+";");
					}else{
						out.print(";");
						out.print(";");
						out.print(";");
					}
					query = "SELECT Peso FROM Laboratorio WHERE PacienteID = "+pacienteID+" AND Tipo='HI' AND NumeroControl = "+i+";";
					st7 = conexion.createStatement();
					rs7 = st7.executeQuery(query);
					if(rs7.next()){
						out.print(""+String.valueOf(rs7.getObject("Peso"))+";");
					}else{
						out.print(";");
					}
					query = "SELECT Hematocrito, Fecha, Gpt, Got FROM Laboratorio WHERE PacienteID = "+pacienteID+" AND Tipo='HE' AND NumeroControl = "+i+";";
					st7 = conexion.createStatement();
					rs7 = st7.executeQuery(query);
					if(rs7.next()){
						out.print(""+String.valueOf(rs7.getObject("Hematocrito"))+";");
						out.print(""+String.valueOf(rs7.getObject("Fecha"))+";");
						out.print(""+String.valueOf(rs7.getObject("Gpt"))+";");
						out.print(""+String.valueOf(rs7.getObject("Got"))+";");
					}else{
						out.print(";");
						out.print(";");
						out.print(";");
						out.print(";");
					}
					out.print("\n");
				}
			}
		}
	}
%>
<%@ include file="disconection.jsp" %>