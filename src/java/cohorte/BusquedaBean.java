/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cohorte;
import java.util.LinkedList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
//import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
/**
 *
 * @author Daniel
 */
@ManagedBean(name = "busquedaBean")
@RequestScoped
public class BusquedaBean {
    private Util util = new Util();
    
    // Busqueda
    private String codigo;
    private String rut;
    
    // Reporte
    private Integer sexoID;
    private Integer factorRiesgoID;
    private Integer centroID;
    private Integer preferenciaSexualID;
    private Integer usoAnticonceptivoID;
    private Integer empleoID;
    private Integer cantidadPacientes;
    
    // Columnas reportes
    private String columna1;
    private String columna2;
    private String columna3;
    private String columna4;
    private String columna5;
    private String columna6;
    private String columna7;
    private String columna8;
    
    // Columnas if
    private Boolean isColumna1;
    private Boolean isColumna2;
    private Boolean isColumna3;
    private Boolean isColumna4;
    private Boolean isColumna5;
    private Boolean isColumna6;
    private Boolean isColumna7;
    private Boolean isColumna8;
    
    // Global
    private Integer mesIngreso;
    private Integer añoIngreso;
    private Integer mesNacimiento;
    private Integer añoNacimiento;
    private String codigoResultado;
    private List<Object[]> resultados;
    @PersistenceUnit 
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("Cohorte2PU");; 
   /*
    @PersistenceContext
    EntityManager em = emf.createEntityManager();*/
    
    // Session
    private final HttpServletRequest httpServletRequest;
    private final FacesContext faceContext;
    
    private Usuario logueado;
    private Centro logueadoCentro;
    
    
    
    /**
     * Creates a new instance of busquedaBean
     */
    public BusquedaBean() {
        // Carga session
        faceContext=FacesContext.getCurrentInstance();
        httpServletRequest=(HttpServletRequest)faceContext.getExternalContext().getRequest();
        if(httpServletRequest.getSession().getAttribute("Usuario") != null)
        {
            logueado = (Usuario)httpServletRequest.getSession().getAttribute("Usuario");
        }
        
        if(httpServletRequest.getSession().getAttribute("Centro") != null)
        {
            //logueadoCentro = (Centro)httpServletRequest.getSession().getAttribute("Centro");
            centroID = Integer.parseInt(httpServletRequest.getSession().getAttribute("Centro").toString());
        }
    }
    
    public void Buscar() {
        if(httpServletRequest.getSession()==null||httpServletRequest.getSession().getAttribute("UID")==null){
            this.codigoResultado="<script>$(document).ready(function(){$('body').css('opacity','0.1');alert('Su sesion ha estado inactiva por mas de 60 minutos, por seguridad debe ingresar nuevamente.');window.location='/Cohorte2/';$('body').css('opacity','1');});</script>";
            return ;
        }
        if(httpServletRequest.getSession().getAttribute("Centro") != null) {
            centroID = Integer.parseInt(httpServletRequest.getSession().getAttribute("Centro").toString());
        }
        String query = "", join ="";
        if (this.codigo != null && this.codigo.length() > 0) {
            query += "p.Codigo LIKE '%" + this.codigo + "%' AND ";
        }
        
        if (this.rut != null && this.rut.length()> 0) {
            // Se agrega encriptacion al rut
            query += "p.Rut = TO_BASE64('" + this.rut + "') AND ";
            //query += "p.Rut LIKE '%" + this.rut + "%' AND ";
        }
        
        if (this.mesIngreso != null && this.mesIngreso > 0) {
            query += "MONTH(p.FechaIngreso) =  " + this.mesIngreso + " AND ";
        }
        
        if (this.añoIngreso != null && this.añoIngreso > 0) {
            query += "YEAR(p.FechaIngreso) =  " + this.añoIngreso + " AND ";
        }
        
        if (this.mesNacimiento != null && this.mesNacimiento > 0) {
            query += "MONTH(p.FechaNacimiento) =  " + this.mesNacimiento + " AND ";
        }
        
        if (this.añoNacimiento != null && this.añoNacimiento > 0) {
            query += "YEAR(p.FechaNacimiento) =  " + this.añoNacimiento + " AND ";
        }
        
        if (logueado != null && logueado.esUsuario()) {
            // Si no es administrador se agrega filtro del centro
           query += "p.CentroID = " + this.centroID + " AND ";
          // join=", Centro c ";
        }else{
            //join="LEFT JOIN Centro c ON p.CentroID=c.ID";
        }
        EntityManager em = emf.createEntityManager();
        query = "SELECT p.ID, p.Codigo, p.FechaIngreso, p.FechaNacimiento, c.Nombre, CAST(FROM_BASE64(rut) as char) AS Rut, p.DV FROM Paciente p LEFT JOIN Centro c ON p.CentroID=c.ID WHERE " + query + " 1=1;";
        resultados = (List<Object[]>)em.createNativeQuery(query).getResultList();
        //codigoResultado = "parametro buscado: " + codigo + ". " +resultados.size() + " resultados. " + query;
        em.close();
        em=null;
    }
    
    public String buscarMenu() {
        if(httpServletRequest.getSession()==null||httpServletRequest.getSession().getAttribute("UID")==null){
            this.codigoResultado="<script>$(document).ready(function(){$('body').css('opacity','0.1');alert('Su sesion ha estado inactiva por mas de 60 minutos, por seguridad debe ingresar nuevamente.');window.location='/Cohorte2/';$('body').css('opacity','1');});</script>";
            return "encontrado";
        }
        if(httpServletRequest.getSession().getAttribute("Centro") != null) {
            centroID = Integer.parseInt(httpServletRequest.getSession().getAttribute("Centro").toString());
        }
        String query = "",join="";
        if (this.codigo != null && this.codigo.length() > 0) {
            query += "p.Codigo LIKE '%" + this.codigo + "%' AND ";
        }
        
        if (logueado != null && logueado.esUsuario()) {
            // Si no es administrador se agrega filtro del centro
           query += "p.CentroID = " + this.centroID + " AND ";
           //join=", Centro c ";
        }else{
            //join="LEFT JOIN Centro c ON p.CentroID=c.ID";
        }
        EntityManager em = emf.createEntityManager();
        query = "SELECT p.ID, p.Codigo, p.FechaIngreso, p.FechaNacimiento, c.Nombre, CAST(FROM_BASE64(rut) as char) AS Rut, p.DV FROM Paciente p LEFT JOIN Centro c ON p.CentroID=c.ID WHERE " + query + " 1=1;";
        resultados = (List<Object[]>)em.createNativeQuery(query).getResultList();
        //codigoResultado = "parametro buscado: " + codigo + ". " +resultados.size() + " resultados. " + query;
        em.close();
        em=null;
        return "encontrado";
    }
    
    public void Reporte() {
        if(httpServletRequest.getSession().getAttribute("Centro") != null) {
            centroID = Integer.parseInt(httpServletRequest.getSession().getAttribute("Centro").toString());
        }
        resultados = new LinkedList<Object[]>();
        String query = "";
        String groupBy = "";
        String querySQL = ""; 
        String select = "";
        String select_interno = "";
        cantidadPacientes = 0;
        
        // Inicializamos columnas
        this.columna1 = "";
        this.columna2 = "";
        this.columna3 = "";
        this.columna4 = "";
        this.columna5 = "";
        this.columna6 = "";
        this.columna7 = "";
        this.columna8 = "";
        
        this.setIsColumna1((Boolean) false);
        this.setIsColumna2((Boolean) false);
        this.setIsColumna3((Boolean) false);
        this.setIsColumna4((Boolean) false);
        this.setIsColumna5((Boolean) false);
        this.setIsColumna6((Boolean) false);
        this.setIsColumna7((Boolean) false);
        this.setIsColumna8((Boolean) false);
        
        if (this.sexoID != null && this.sexoID > 0) {
            query += "SexoID  = " + this.sexoID + " AND ";
        } else {
            groupBy += "a.Sexo, ";
            select += "Sexo.Nombre as Sexo, ";
            query += "SexoID = Sexo.ID AND ";
            this.columna1 = "SEXO";
            this.setIsColumna1((Boolean) true);
        }
        
        if (this.factorRiesgoID != null && this.factorRiesgoID > 0) {
            query += "FactorRiesgoID  = " + this.factorRiesgoID + " AND ";
        } else {
            //groupBy += "FactorRiesgoID, ";
            //query += "FactorRiesgoID IS NOT NULL AND ";
            /*if (this.columna1.equals("")) {
                this.columna1 = "FACTOR RIESGO";
                this.setIsColumna1((Boolean) true);
            } else {
                this.columna2 = "FACTOR RIESGO";
                this.setIsColumna2((Boolean) true);
            }*/
        }
        
        if (logueado != null && logueado.esUsuario() != null && !logueado.esUsuario()) {
            if (this.centroID != null && this.centroID> 0) {
                query += "CentroID  = " + this.centroID+ " AND ";
            } else {
                groupBy += "a.Centro, ";
                select += "Centro.Nombre as Centro, ";
                query += "CentroID = Centro.ID AND ";
                if (this.columna1.equals("")) {
                    this.columna1 = "CENTRO";
                    this.setIsColumna1((Boolean) true);
                } else if (this.columna2.equals("")) {
                    this.columna2 = "CENTRO";
                    this.setIsColumna2((Boolean) true);
                } else {
                    this.columna3 = "CENTRO";
                    this.setIsColumna3((Boolean) true);
                }
            }
        }
        
        if (this.preferenciaSexualID != null && this.preferenciaSexualID > 0) {
            query += "PreferenciaSexualID  = " + this.preferenciaSexualID + " AND ";
        } else {
            groupBy += "a.PreferenciaSexual, ";
            select += "PreferenciaSexual.Nombre as PreferenciaSexual, ";
            query += "PreferenciaSexualID = PreferenciaSexual.ID AND ";
            //query += "PreferenciaSexualID IS NOT NULL AND ";
            if (this.columna1.equals("")) {
                this.columna1 = "PREFERENCIA";
                this.setIsColumna1((Boolean) true);
            } else if (this.columna2.equals("")) {
                this.columna2 = "PREFERENCIA";
                this.setIsColumna2((Boolean) true);
            } else if (this.columna3.equals("")) {
                this.columna3 = "PREFERENCIA";
                this.setIsColumna3((Boolean) true);
            } else {
                this.columna4 = "PREFERENCIA";
                this.setIsColumna4((Boolean) true);
            }
        }
        
        if (this.usoAnticonceptivoID != null && this.usoAnticonceptivoID > 0) {
            query += "UsoAnticonceptivoID  = " + this.usoAnticonceptivoID + " AND ";
        } else {
            groupBy += "a.UsoAnticonceptivo, ";
            select += "UsoAnticonceptivo.Nombre as UsoAnticonceptivo, ";
            query += "UsoAnticonceptivoID = UsoAnticonceptivo.ID AND ";
            if (this.columna1.equals("")) {
                this.columna1 = "ANTICONCEPTIVO";
                this.setIsColumna1((Boolean) true);
            } else if (this.columna2.equals("")) {
                this.columna2 = "ANTICONCEPTIVO";
                this.setIsColumna2((Boolean) true);
            } else if (this.columna3.equals("")) {
                this.columna3 = "ANTICONCEPTIVO";
                this.setIsColumna3((Boolean) true);
            } else if (this.columna4.equals("")) {
                this.columna4 = "ANTICONCEPTIVO";
                this.setIsColumna4((Boolean) true);
            } else {
                this.columna5 = "ANTICONCEPTIVO";
                this.setIsColumna5((Boolean) true);
            }
        }
        
        if (this.empleoID != null && this.empleoID > 0) {
            query += "EmpleoID  = " + this.empleoID + " AND ";
        } else {
            groupBy += "a.Empleo, ";
            select += "Empleo.Nombre as Empleo, ";
            query += "EmpleoID = Empleo.ID AND ";
            if (this.columna1.equals("")) {
                this.columna1 = "EMPLEO";
                this.setIsColumna1((Boolean) true);
            } else if (this.columna2.equals("")) {
                this.columna2 = "EMPLEO";
                this.setIsColumna2((Boolean) true);
            } else if (this.columna3.equals("")) {
                this.columna3 = "EMPLEO";
                this.setIsColumna3((Boolean) true);
            } else if (this.columna4.equals("")) {
                this.columna4 = "EMPLEO";
                this.setIsColumna4((Boolean) true);
            } else if (this.columna5.equals("")) {
                this.columna5 = "EMPLEO";
                this.setIsColumna5((Boolean) true);
            } else {
                this.columna6 = "EMPLEO";
                this.setIsColumna6((Boolean) true);
            }
        }
        
        // COUNT(*)
        
        if (this.columna1.equals("")) {
                this.columna1 = "CANTIDAD";
                this.setIsColumna1((Boolean) true);
            } else if (this.columna2.equals("")) {
                this.columna2 = "CANTIDAD";
                this.setIsColumna2((Boolean) true);
            } else if (this.columna3.equals("")) {
                this.columna3 = "CANTIDAD";
                this.setIsColumna3((Boolean) true);
            } else if (this.columna4.equals("")) {
                this.columna4 = "CANTIDAD";
                this.setIsColumna4((Boolean) true);
            } else if (this.columna5.equals("")) {
                this.columna5 = "CANTIDAD";
                this.setIsColumna5((Boolean) true);
            } else if (this.columna6.equals("")) {
                this.columna6 = "CANTIDAD";
                this.setIsColumna6((Boolean) true);
            } else {
                this.columna7 = "CANTIDAD";
                this.setIsColumna7((Boolean) true);
            }
        
        if (this.mesIngreso != null && this.mesIngreso > 0) {
            query += "MONTH(FechaIngreso) =  " + this.mesIngreso + " AND ";
        }
        
        if (this.añoIngreso != null && this.añoIngreso > 0) {
            query += "YEAR(FechaIngreso) =  " + this.añoIngreso + " AND ";
        }
        
        if (this.mesNacimiento != null && this.mesNacimiento > 0) {
            query += "MONTH(FechaNacimiento) =  " + this.mesNacimiento + " AND ";
        }
        
        if (this.añoNacimiento != null && this.añoNacimiento > 0) {
            query += "YEAR(FechaNacimiento) =  " + this.añoNacimiento + " AND ";
        }
        
        if (logueado.esUsuario()) {
            // Si no es administrador se agrega filtro del centro
           query += "CentroID = " + this.centroID + " AND ";
        }
        
        //querySQL = "SELECT Codigo, FechaIngreso, FechaNacimiento FROM Paciente WHERE " + query + " 1";
        
        if (!groupBy.equals("")) {
            querySQL = "SELECT " + select + " COUNT(*) FROM Paciente, Sexo, Centro, Empleo, FactorRiesgo, PreferenciaSexual WHERE " + query + " 1 GROUP BY " + groupBy + " 1";
        }
        
        querySQL  = "SELECT " + groupBy + " count(*) FROM ";
        querySQL += "( ";
        querySQL += "SELECT distinct Paciente.Codigo, " + select + " 1 ";
        querySQL += "FROM Paciente, Sexo, Centro, Empleo, FactorRiesgo, PreferenciaSexual, UsoAnticonceptivo ";
        querySQL += "WHERE "+ query +" 1 ";
        querySQL += ") a ";
        querySQL += "GROUP BY " + groupBy + " 1";
        EntityManager em = emf.createEntityManager();
        resultados = (List<Object[]>)em.createNativeQuery(querySQL).getResultList();
        codigoResultado = "parametro buscado: " + codigo + ". " +resultados.size() + " resultados. "+ querySQL;
        em.close();
        em=null;
    }
    
    
    public Integer cantidadTerapias(Integer pacienteID) {
        try {
            EntityManager em = emf.createEntityManager();
            Query q = em.createNativeQuery("SELECT COUNT(*) AS Cantidad FROM Terapia t WHERE t.PacienteID = ?").setParameter(1, pacienteID);
            Long value = ((Long) q.getSingleResult());
            em.close();
            em=null;
            return value.intValue();
        } catch (Exception e) {
           return 0;
        }
    }
    
    
    public Integer cantidadDrogas(Integer pacienteID) {
        try {
            EntityManager em = emf.createEntityManager();
            Query q = em.createNativeQuery("SELECT COUNT(*) AS Cantidad FROM Terapia t, TerapiaDroga td WHERE t.PacienteID = ? and t.ID = td.TerapiaID").setParameter(1, pacienteID);
            Long value = ((Long) q.getSingleResult());
            em.close();
            return value.intValue();
        } catch (Exception e) {
           return 0;
        }
    }
    
    public Integer cantidadCD4(Integer pacienteID) {
        try {
            EntityManager em = emf.createEntityManager();
            Query q = em.createNativeQuery("SELECT COUNT(*) AS Cantidad FROM Control WHERE PacienteID = ? AND Tipo = 'CD'").setParameter(1, pacienteID);
            Long value = ((Long) q.getSingleResult());
            em.close();
            em=null;
            return value.intValue();
        } catch (Exception e) {
           return 0;
        }
    }
    
    public Integer cantidadCV(Integer pacienteID) {
        try {
            EntityManager em = emf.createEntityManager();
            Query q = em.createNativeQuery("SELECT COUNT(*) AS Cantidad FROM Control WHERE PacienteID = ? AND Tipo = 'CV'").setParameter(1, pacienteID);
            Long value = ((Long) q.getSingleResult());
            em.close();
            em=null;
            return value.intValue();
        } catch (Exception e) {
           return 0;
        }
    }
    
    public String ultimoEvento(Integer pacienteID) {
        String rtn = "SIN EVENTOS";
        if (pacienteID != null && pacienteID > 0) {
            EntityManager em = emf.createEntityManager();
            String query = "SELECT Fecha, ID, PacienteID FROM Muerte WHERE PacienteID = " + pacienteID;
            List<Object[]> list = (List<Object[]>)em.createNativeQuery(query).getResultList();
            
            for (Object[] var : list) {
                if (var[0] != null && var[0].toString() != null) {
                    rtn = util.fechaTabla(var[0].toString()) + " (MUERTE)";
                }
            }
            
            if (rtn.equals("SIN EVENTOS")) {
                query = "SELECT Fecha, ID, PacienteID FROM PacienteEvento WHERE PacienteID = " + pacienteID;
                list = (List<Object[]>)em.createNativeQuery(query).getResultList();
                for (Object[] var : list) {
                    if (var[0] != null && var[0].toString() != null) {
                        rtn = util.fechaTabla(var[0].toString()) + " (EVENTO)";
                    }
                }
            }
            em.close();
            em=null;
        }
        return rtn;
    }
    
    public String porcentajeDatosPersonales(Integer pacienteID) {
        Integer i = 0;
        Integer j = 0;
        if (pacienteID != null && pacienteID > 0) {
            EntityManager em = emf.createEntityManager();
            String query = "SELECT CentroID, SexoID, FactorRiesgoID, UsoAnticonceptivoID, PaisOrigenID, "
                    + "NivelEducacionalID, EmpleoID, EtniaID, PreferenciaSexualID, ComunaID, Codigo, "
                    + "FechaISP, RegistroISP, CD4, FechaNotificacion, Ficha, FechaIngreso, FechaCD4, FechaEncuesta, "
                    + "Rut, DV, FechaNacimiento, IdentidadGeneroID "
                    + "FROM Paciente WHERE ID = " + pacienteID;
            List<Object[]> list = (List<Object[]>)em.createNativeQuery(query).getResultList();
            for (Object[] var : list) {
                for (Object dato : var) {
                    i++;
                    if (dato != null && !"".equals(dato.toString())) {
                        j++;
                    }
                }
                break;
            }
            em.close();
            em=null;
        }
        
        if (i == 0) {
             i = 1;
        }
        
        float porcentaje = Float.parseFloat(j.toString()) * 100 / Float.parseFloat(i.toString());
        return Math.round(porcentaje) + "%";
    }
    
    public String camposVaciosDatosPersonales(Integer pacienteID) {
        Integer i = 0;
        Integer j = 0;
        String resultado = "";
        String [] campos = {"", "Centro", "Sexo", "Factor de Riesgo", "Uso Anticonceptivo", "País de Origen", "Nivel Educacional"
                + "","Empleo", "Etnia", "Preferencia Sexual", "Comuna", "Código", "Fecha Confirmación ISP", "N° Registro ISP"
                + "", "CD4 Ingreso", "Fecha Notificación ENO", "N° Ficha", "Fecha Ingreso Centro", "Fecha CD4 Ingreso", "Fecha Encuesta Caso VIH"
                + "", "Rut", "DV", "Fecha de Nacimiento", "Identidad de Genero" };
        
        if (pacienteID != null && pacienteID > 0) {
            EntityManager em = emf.createEntityManager();
            String query = "SELECT CentroID, SexoID, FactorRiesgoID, UsoAnticonceptivoID, PaisOrigenID, "
                    + "NivelEducacionalID, EmpleoID, EtniaID, PreferenciaSexualID, ComunaID, Codigo, "
                    + "FechaISP, RegistroISP, CD4, FechaNotificacion, Ficha, FechaIngreso, FechaCD4, FechaEncuesta, "
                    + "Rut, DV, FechaNacimiento, IdentidadGeneroID "
                    + "FROM Paciente WHERE ID = " + pacienteID;
            List<Object[]> list = (List<Object[]>)em.createNativeQuery(query).getResultList();
            for (Object[] var : list) {
                for (Object dato : var) {
                    i++;
                    if (dato == null || "".equals(dato.toString())) {
                        j++;
                        resultado += "<li>" + campos[i] + "</li>"; 
                    }
                }
                break;
            }
            em.close();
            em=null;
        }
        
        if (j == 0 && "".equals(resultado)) {
            resultado = "<strong>Todos los datos están ingresados.</strong>";
        } else {
            resultado = "<strong>Faltan los siguientes datos:</strong><ul>" + resultado+"</ul>";
        }
        
        return resultado;
    }
    
    public String camposVaciosDatosBasales(Integer pacienteID) {
        Integer i = 0;
        Integer j = 0;
        String resultado = "";
        String [] campos = {"", "CD4", "CV", "% CD4", "Logaritmo", "HLA-B5701", "PPD", "HBsAg", "VHC", "Toxoplasmosis", "VDRL", "Chagas", "PAP", "Peso Kg", "Talla","Clasificacion CDC L", "Clasificacion CDC N"
                + "", "Fecha CD4", "Fecha CV", "Fecha PPD", "Fecha HBsAg", "Fecha VHC", "Fecha Toxoplasmosis", "Fecha VDRL", "Fecha Chagas", "Fecha PAP", "Fecha Hipertensión", "Fecha Dislipidemia", "Fecha Glicemia", "Fecha Hematocrito"
                + "", "IMC", "Colesterol Total", "Colesterol LDL", "Colesterol HDL", "Glicemia", "Hematocrito", "Trigliceridos", "Tratamiento PPD", "Diagnóstico Hipertensión", "Diagnóstico Dislipidemia", "Diagnóstico Glicemia", "Pres. Sistólica"
                + "", "Presión Diastólica", "GOT", "GPT", "Examen de Orina", "Rx Torax", "Siges" };
        
        if (pacienteID != null && pacienteID > 0) {
            EntityManager em = emf.createEntityManager();
            String query = "SELECT CD4, CV, PCD4, Log, Hla, Ppd, Hbs, Vhc, Toxoplasmosis, Vdrl, Chagas, Pap, Peso, Talla,ClasificacionL, ClasificacionN, "
                + " CD4Fecha, CVFecha, PpdFecha, HbsaFecha, VhcFecha, ToxoFecha, VdrlFecha, ChagasFecha, PapFecha, HiperFecha, DisliFecha, GliceFecha, HemaFecha, "
                + " Imc, ColesTotal, ColesLdl, ColesHdl, Glice, Hematocrito, Trigi, PpdTratamiento, HiperDiag, DisliDiag, GliceDiag, PSist, PDias, Got, Gpt, ExamenOrina, RxTorax, Siges "
                    + "FROM DatoBasal WHERE PacienteID = " + pacienteID;
            List<Object[]> list = (List<Object[]>)em.createNativeQuery(query).getResultList();
            
            if (list.size() == 0) {return "<strong>No hay datos ingresados!</strong>";}
            
            for (Object[] var : list) {
                for (Object dato : var) {
                    i++;
                    if (dato == null || "".equals(dato.toString())) {
                        j++;
                        resultado += "<li>" + campos[i] + "</li>"; 
                    }
                }
                break;
            }
            em.close();
            em=null;
        }
        
        if (j == 0 && "".equals(resultado)) {
            resultado = "<strong>Todos los datos están ingresados.</strong>";
        } else {
            resultado = "<strong>Faltan los siguientes datos:</strong><ul>" + resultado+"</ul>";
        }
        
        if (i == j) {
            return "<strong>No hay datos ingresados!</strong>";
        }
        
        return resultado;
    }
    
    
    
    public String porcentajeDatosBasales(Integer pacienteID) {
        Integer i = 0;
        Integer j = 0;
        if (pacienteID != null && pacienteID > 0) {
            EntityManager em = emf.createEntityManager();
            String query = "SELECT CD4, CV, PCD4, Log, Hla, Ppd, Hbs, Vhc, Toxoplasmosis, Vdrl, Chagas, Pap, Peso, Talla,ClasificacionL, ClasificacionN, "
                + " CD4Fecha, CVFecha, PpdFecha, HbsaFecha, VhcFecha, ToxoFecha, VdrlFecha, ChagasFecha, PapFecha, HiperFecha, DisliFecha, GliceFecha, HemaFecha, "
                + " Imc, ColesTotal, ColesLdl, ColesHdl, Glice, Hematocrito, Trigi, PpdTratamiento, HiperDiag, DisliDiag, GliceDiag, PSist, PDias, Got, Gpt, ExamenOrina, RxTorax, Siges  "
                + " FROM DatoBasal WHERE PacienteID = " + pacienteID;
            List<Object[]> list = (List<Object[]>)em.createNativeQuery(query).getResultList();
            for (Object[] var : list) {
                for (Object dato : var) {
                    i++;
                    if (dato != null && !"".equals(dato.toString())) {
                        j++;
                    }
                }
                break;
            }
            em.close();
            em=null;
        }
        
        if (i == 0) {
            return "0%";
        }
        
        float porcentaje = Float.parseFloat(j.toString()) * 100 / Float.parseFloat(i.toString());
        return Math.round(porcentaje) + "%";
    }
    
    public List<Object[]> getResultados() {
        return resultados;
    }

    /**
     * @return the codigo
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * @param codigo the codigo to set
     */
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    /**
     * @return the mesIngreso
     */
    public Integer getMesIngreso() {
        return mesIngreso;
    }

    /**
     * @param mesIngreso the mesIngreso to set
     */
    public void setMesIngreso(Integer mesIngreso) {
        this.mesIngreso = mesIngreso;
    }

    /**
     * @return the añoIngreso
     */
    public Integer getAñoIngreso() {
        return añoIngreso;
    }

    /**
     * @param añoIngreso the añoIngreso to set
     */
    public void setAñoIngreso(Integer añoIngreso) {
        this.añoIngreso = añoIngreso;
    }

    /**
     * @return the mesNacimiento
     */
    public Integer getMesNacimiento() {
        return mesNacimiento;
    }

    /**
     * @param mesNacimiento the mesNacimiento to set
     */
    public void setMesNacimiento(Integer mesNacimiento) {
        this.mesNacimiento = mesNacimiento;
    }

    /**
     * @return the añoNaciomiento
     */
    public Integer getAñoNacimiento() {
        return añoNacimiento;
    }

    /**
     * @param añoNaciomiento the añoNaciomiento to set
     */
    public void setAñoNacimiento(Integer añoNacimiento) {
        this.añoNacimiento = añoNacimiento;
    }

    /**
     * @return the codigoResultado
     */
    public String getCodigoResultado() {
        return codigoResultado;
    }

    /**
     * @param codigoResultado the codigoResultado to set
     */
    public void setCodigoResultado(String codigoResultado) {
        this.codigoResultado = codigoResultado;
    }

    /**
     * @return the sexoID
     */
    public Integer getSexoID() {
        return sexoID;
    }

    /**
     * @param sexoID the sexoID to set
     */
    public void setSexoID(Integer sexoID) {
        this.sexoID = sexoID;
    }

    /**
     * @return the factorRiesgoID
     */
    public Integer getFactorRiesgoID() {
        return factorRiesgoID;
    }

    /**
     * @param factorRiesgoID the factorRiesgoID to set
     */
    public void setFactorRiesgoID(Integer factorRiesgoID) {
        this.factorRiesgoID = factorRiesgoID;
    }

    /**
     * @return the centroID
     */
    public Integer getCentroID() {
        return centroID;
    }

    /**
     * @param centroID the centroID to set
     */
    public void setCentroID(Integer centroID) {
        this.centroID = centroID;
    }

    /**
     * @return the preferenciaSexualID
     */
    public Integer getPreferenciaSexualID() {
        return preferenciaSexualID;
    }

    /**
     * @param preferenciaSexualID the preferenciaSexualID to set
     */
    public void setPreferenciaSexualID(Integer preferenciaSexualID) {
        this.preferenciaSexualID = preferenciaSexualID;
    }

    /**
     * @return the usoAnticonceptivoID
     */
    public Integer getUsoAnticonceptivoID() {
        return usoAnticonceptivoID;
    }

    /**
     * @param usoAnticonceptivoID the usoAnticonceptivoID to set
     */
    public void setUsoAnticonceptivoID(Integer usoAnticonceptivoID) {
        this.usoAnticonceptivoID = usoAnticonceptivoID;
    }

    /**
     * @return the empleoID
     */
    public Integer getEmpleoID() {
        return empleoID;
    }

    /**
     * @param empleoID the empleoID to set
     */
    public void setEmpleoID(Integer empleoID) {
        this.empleoID = empleoID;
    }

    /**
     * @return the columna1
     */
    public String getColumna1() {
        return columna1;
    }

    /**
     * @param columna1 the columna1 to set
     */
    public void setColumna1(String columna1) {
        this.columna1 = columna1;
    }

    /**
     * @return the columna2
     */
    public String getColumna2() {
        return columna2;
    }

    /**
     * @param columna2 the columna2 to set
     */
    public void setColumna2(String columna2) {
        this.columna2 = columna2;
    }

    /**
     * @return the columna3
     */
    public String getColumna3() {
        return columna3;
    }

    /**
     * @param columna3 the columna3 to set
     */
    public void setColumna3(String columna3) {
        this.columna3 = columna3;
    }

    /**
     * @return the columna4
     */
    public String getColumna4() {
        return columna4;
    }

    /**
     * @param columna4 the columna4 to set
     */
    public void setColumna4(String columna4) {
        this.columna4 = columna4;
    }

    /**
     * @return the columna5
     */
    public String getColumna5() {
        return columna5;
    }

    /**
     * @param columna5 the columna5 to set
     */
    public void setColumna5(String columna5) {
        this.columna5 = columna5;
    }

    /**
     * @return the columna6
     */
    public String getColumna6() {
        return columna6;
    }

    /**
     * @param columna6 the columna6 to set
     */
    public void setColumna6(String columna6) {
        this.columna6 = columna6;
    }

    /**
     * @return the columna7
     */
    public String getColumna7() {
        return columna7;
    }

    /**
     * @param columna7 the columna7 to set
     */
    public void setColumna7(String columna7) {
        this.columna7 = columna7;
    }

    /**
     * @return the columna8
     */
    public String getColumna8() {
        return columna8;
    }

    /**
     * @param columna8 the columna8 to set
     */
    public void setColumna8(String columna8) {
        this.columna8 = columna8;
    }

    /**
     * @return the isColumna1
     */
    public Boolean getIsColumna1() {
        return isColumna1;
    }

    /**
     * @param isColumna1 the isColumna1 to set
     */
    public void setIsColumna1(Boolean isColumna1) {
        this.isColumna1 = isColumna1;
    }

    /**
     * @return the isColumna2
     */
    public Boolean getIsColumna2() {
        return isColumna2;
    }

    /**
     * @param isColumna2 the isColumna2 to set
     */
    public void setIsColumna2(Boolean isColumna2) {
        this.isColumna2 = isColumna2;
    }

    /**
     * @return the isColumna3
     */
    public Boolean getIsColumna3() {
        return isColumna3;
    }

    /**
     * @param isColumna3 the isColumna3 to set
     */
    public void setIsColumna3(Boolean isColumna3) {
        this.isColumna3 = isColumna3;
    }

    /**
     * @return the isColumna4
     */
    public Boolean getIsColumna4() {
        return isColumna4;
    }

    /**
     * @param isColumna4 the isColumna4 to set
     */
    public void setIsColumna4(Boolean isColumna4) {
        this.isColumna4 = isColumna4;
    }

    /**
     * @return the isColumna5
     */
    public Boolean getIsColumna5() {
        return isColumna5;
    }

    /**
     * @param isColumna5 the isColumna5 to set
     */
    public void setIsColumna5(Boolean isColumna5) {
        this.isColumna5 = isColumna5;
    }

    /**
     * @return the isColumna6
     */
    public Boolean getIsColumna6() {
        return isColumna6;
    }

    /**
     * @param isColumna6 the isColumna6 to set
     */
    public void setIsColumna6(Boolean isColumna6) {
        this.isColumna6 = isColumna6;
    }

    /**
     * @return the isColumna7
     */
    public Boolean getIsColumna7() {
        return isColumna7;
    }

    /**
     * @param isColumna7 the isColumna7 to set
     */
    public void setIsColumna7(Boolean isColumna7) {
        this.isColumna7 = isColumna7;
    }

    /**
     * @return the isColumna8
     */
    public Boolean getIsColumna8() {
        return isColumna8;
    }

    /**
     * @param isColumna8 the isColumna8 to set
     */
    public void setIsColumna8(Boolean isColumna8) {
        this.isColumna8 = isColumna8;
    }

    /**
     * @return the cantidadPacientes
     */
    public Integer getCantidadPacientes() {
        return cantidadPacientes;
    }

    /**
     * @param cantidadPacientes the cantidadPacientes to set
     */
    public void setCantidadPacientes(Integer cantidadPacientes) {
        this.cantidadPacientes = cantidadPacientes;
    }

    /**
     * @return the logueado
     */
    public Usuario getLogueado() {
        return logueado;
    }

    /**
     * @param logueado the logueado to set
     */
    public void setLogueado(Usuario logueado) {
        this.logueado = logueado;
    }

    /**
     * @return the logueadoCentro
     */
    public Centro getLogueadoCentro() {
        return logueadoCentro;
    }

    /**
     * @param logueadoCentro the logueadoCentro to set
     */
    public void setLogueadoCentro(Centro logueadoCentro) {
        this.logueadoCentro = logueadoCentro;
    }

    /**
     * @return the util
     */
    public Util getUtil() {
        return util;
    }

    /**
     * @param util the util to set
     */
    public void setUtil(Util util) {
        this.util = util;
    }

    /**
     * @return the rut
     */
    public String getRut() {
        return rut;
    }

    /**
     * @param rut the rut to set
     */
    public void setRut(String rut) {
        this.rut = rut;
    }

    
}