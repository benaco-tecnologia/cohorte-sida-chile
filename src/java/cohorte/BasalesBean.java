/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cohorte;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Daniel
 */
public class BasalesBean {
    private Util util = new Util();
    private Boolean pacienteSeleccionado;
    private List<Object[]> resultados;
    private String resultado = "";
    private Integer pacienteID;
    private Integer basalesID;
    
    // Datos basales
    private String clasificacionL;
    private String clasificacionN;
    private String pcd4;
    private String logaritmo;
    private Integer cd4;
    private Integer cv;
    
    private Usuario logueado;
    private final HttpServletRequest httpServletRequest;
    private final FacesContext faceContext;
    
    private String hla;
    private String ppd;
    private String hbsa;
    private String vhc;
    private String peso;
    private String talla;
    private String toxoplasmosis;
    private String imc;
    private String vdrl;
    private String chagas;
    private String pap;
    private Integer enfermedad1ID;
    private Integer enfermedad2ID;
    private Integer enfermedad3ID;
    private Integer enfermedad4ID;
    
    private String Observaciones;
    private String CD4Fecha;
    private String CVFecha;
    private String HlaFecha;
    private String PpdFecha;
    private String HbsaFecha;
    private String VhcFecha;
    private String ToxoFecha;
    private String VdrlFecha;
    private String ChagasFecha;
    private String PapFecha;
    private String Enf1Fecha;
    private String Enf2Fecha;
    private String Enf3Fecha;
    private String Enf4Fecha;
    private String HiperFecha;
    private String DisliFecha;
    private String GliceFecha;
    private String HemaFecha;
    
    private String ColesTotal;
    private String ColesLdl;
    private String ColesHdl;
    private String Glice;
    private String Hematocrito;
    private String Transaminasas;
    private String Trigi;
    
    
    private String PpdTratamiento;
    private String HiperDiag;
    private String DisliDiag;
    private String GliceDiag;
    private String PSist;
    private String PDias;
    
    private String Got;
    private String Gpt;
    
    private String RxTorax;
    private String ExamenOrina;
    private String Siges;
    private String codigo;
    
    @PersistenceUnit 
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("Cohorte2PU");; 
   
   // @PersistenceContext
    //EntityManager em = emf.createEntityManager();
    /**
     * Creates a new instance of BasalesBean
     */
    public BasalesBean() {
        this.resultado = "";
         // Carga session
        faceContext=FacesContext.getCurrentInstance();
        httpServletRequest=(HttpServletRequest)faceContext.getExternalContext().getRequest();
        if(httpServletRequest.getSession().getAttribute("Usuario") != null)
        {
            logueado = (Usuario)httpServletRequest.getSession().getAttribute("Usuario");
        }
    }
    
    public String cargarBasales() {
        if(httpServletRequest.getSession()==null||httpServletRequest.getSession().getAttribute("UID")==null){
            this.resultado="<script>$(document).ready(function(){$('body').css('opacity','0.1');alert('Su sesion ha estado inactiva por mas de 30 minutos, por seguridad debe ingresar nuevamente.');window.location='/Cohorte2/';$('body').css('opacity','1');});</script>";
            return "encontrado";
        }
        EntityManager em = emf.createEntityManager();
        String value = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("pacienteIDH");
        try {
            this.setPacienteID((Integer) Integer.parseInt(value));
        } catch (Exception e) {
            this.setPacienteID((Integer) 0);
        }
        
        /*String query = "SELECT d.ID, d.PacienteID, p.Codigo "
                + "FROM DatoBasal d, Paciente p "
                + "WHERE p.ID = " + this.getPacienteID() + " AND p.ID = d.PacienteID;";*/
        String query = "SELECT d.ID, d.PacienteID "
                + "FROM DatoBasal d "
                + "WHERE d.PacienteID = " + this.getPacienteID() + ";";
        List<Object[]> list = (List<Object[]>)em.createNativeQuery(query).getResultList();
        for (Object[] var : list) {
            this.setBasalesID((Integer) Integer.parseInt(var[0].toString()));
            //this.codigo = var[2].toString();
            this.setResultado("HAY datos basales. " + this.getPacienteID() + " Bid " + this.getBasalesID());
            cargarVariables();
            return "encontrado";
        }
        list = null;
        
        if (this.getPacienteID() != null && this.getPacienteID() > 0) {
            // Si no existe dato basal, se crea uno vacío.
            query = "INSERT INTO DatoBasal (PacienteID) VALUES (" + this.getPacienteID() + ");";
            em.getTransaction().begin();   
            em.createNativeQuery(query).executeUpdate();
            Query q = em.createNativeQuery("SELECT LAST_INSERT_ID()");
            Long val = ((BigInteger) q.getSingleResult()).longValue();    
            this.basalesID = val.intValue();
            em.getTransaction().commit();
            this.setResultado("NO hay datos basales, se CREÓ. " + " Bid " + this.getBasalesID());
        }
        
        /*query = "SELECT p.ID,p.Codigo "
                + "FROM Paciente p "
                + "WHERE p.ID = " + this.getPacienteID() + ";";
        list = (List<Object[]>)em.createNativeQuery(query).getResultList();
        for (Object[] var : list) {
            this.codigo = var[1].toString();
        }*/
        list = null;
        em.close();
        em = null;
        System.gc();
        cargarVariables();
        this.setResultado("NO hay datos basales, NO se creó. " + this.getPacienteID());
        return "encontrado";
    }
    
    public String generarFicha() {
        EntityManager em = emf.createEntityManager();
        String query = "SELECT d.ID, d.PacienteID, p.Codigo "
                + "FROM DatoBasal d, Paciente p "
                + "WHERE p.ID = " + this.getPacienteID() + " AND p.ID = d.PacienteID;";
        List<Object[]> list = (List<Object[]>)em.createNativeQuery(query).getResultList();
        for (Object[] var : list) {
            this.setBasalesID((Integer) Integer.parseInt(var[0].toString()));
            this.codigo = var[2].toString();
            this.setResultado("HAY datos basales. " + this.getPacienteID() + " Bid " + this.getBasalesID());
            cargarVariables();
            return "encontrado";
        }
        
        this.setResultado("NO hay datos basales, NO se creó. " + this.getPacienteID());
        em.close();
        em = null;
        System.gc();
        return "encontrado";
    }
    
    public String guardarBasales () {
        if(httpServletRequest.getSession()==null||httpServletRequest.getSession().getAttribute("UID")==null){
            this.resultado="<script>$(document).ready(function(){$('body').css('opacity','0.1');alert('Su sesion ha estado inactiva por mas de 30 minutos, por seguridad debe ingresar nuevamente.');window.location='/Cohorte2/';$('body').css('opacity','1');});</script>";
            return "guardado";
        }
        EntityManager em = emf.createEntityManager();
        if ("".equals(this.ppd)) {
            this.PpdFecha = "";
        }
        
        if ("".equals(this.hbsa)) {
            this.HbsaFecha = "";
        }
        
        if ("".equals(this.vhc)) {
            this.VhcFecha = "";
        }
        
        if ("".equals(this.toxoplasmosis)) {
            this.ToxoFecha = "";
        }
        
        if ("".equals(this.vdrl)) {
            this.VdrlFecha = "";
        }
        
        if ("".equals(this.chagas)) {
            this.ChagasFecha = "";
        }
        
        if ("".equals(this.pap)) {
            this.PapFecha = "";
        }
        
        if (this.enfermedad1ID == null) { this.Enf1Fecha = ""; }
        if (this.enfermedad2ID == null) { this.Enf2Fecha = ""; }
        if (this.enfermedad3ID == null) { this.Enf3Fecha = ""; }
        if (this.enfermedad4ID == null) { this.Enf4Fecha = ""; }
        
        if (this.cd4 == null) { this.CD4Fecha = ""; }
        if (this.cv == null) { this.CVFecha = ""; }
        
        String query = "UPDATE DatoBasal SET "
                + " CD4=" + (this.cd4 != null ? this.cd4 : "NULL")
                + ",CV=" + (this.cv != null ? this.cv : "NULL")
                + ",PCD4="+ (!"".equals(this.pcd4) ? this.pcd4 : "NULL")
                + ",Log=" + (this.logaritmo != "" ? this.logaritmo : "NULL")
                + ",Hla=" + (this.hla != "" ? "'"+this.hla+"'" : "NULL")
                + ",Ppd=" + (this.ppd != "" ? "'"+this.ppd+"'" : "NULL")
                + ",Hbs=" + (this.hbsa != "" ? "'"+this.hbsa+"'" : "NULL")
                + ",Vhc=" + (this.vhc != "" ? "'"+this.vhc+"'" : "NULL")
                + ",Toxoplasmosis=" + (this.toxoplasmosis != "" ? "'"+this.toxoplasmosis+"'" : "NULL")
                + ",Vdrl=" + (this.vdrl != "" ? "'"+this.vdrl+"'" : "NULL")
                + ",Chagas=" + (this.chagas != "" ? "'"+this.chagas+"'" : "NULL")
                + ",Pap=" + (this.pap != "" ? "'"+this.pap+"'" : "NULL")
                + ",Peso=" + (this.peso != "" ? this.peso : "NULL")
                + ",Talla=" + (this.talla != "" ? this.talla : "NULL")
                + ",ClasificacionL=" + (this.clasificacionL != "" ? "'"+this.clasificacionL+"'" : "'A'")
                + ",ClasificacionN=" + (this.clasificacionN != "" ? "'"+this.clasificacionN+"'" : "NULL")
                + ",Enf1ID=" + (this.enfermedad1ID != null ? this.enfermedad1ID : "NULL")
                + ",Enf2ID=" + (this.enfermedad2ID != null ? this.enfermedad2ID : "NULL")
                + ",Enf3ID=" + (this.enfermedad3ID != null ? this.enfermedad3ID : "NULL")
                + ",Enf4ID=" + (this.enfermedad4ID != null ? this.enfermedad4ID : "NULL")
                + ",Enf1Fecha=" + (this.Enf1Fecha != "" ? "'"+(this.Enf1Fecha)+"'" : "NULL")
                + ",Enf2Fecha=" + (this.Enf2Fecha != "" ? "'"+(this.Enf2Fecha)+"'" : "NULL")
                + ",Enf3Fecha=" + (this.Enf3Fecha != "" ? "'"+(this.Enf3Fecha)+"'" : "NULL")
                + ",Enf4Fecha=" + (this.Enf4Fecha != "" ? "'"+(this.Enf4Fecha)+"'" : "NULL")
                + ",Imc=" + (this.imc != "" ? this.imc : "NULL")
                + ",CD4Fecha=" + (this.CD4Fecha != "" ? "'"+(this.CD4Fecha)+"'" : "NULL")
                + ",CVFecha=" + (this.CVFecha != "" ? "'"+(this.CVFecha)+"'" : "NULL")
                + ",PpdFecha=" + (this.PpdFecha != "" ? "'"+(this.PpdFecha)+"'" : "NULL")
                + ",HbsaFecha=" + (this.HbsaFecha != "" ? "'"+(this.HbsaFecha)+"'" : "NULL")
                + ",VhcFecha=" + (this.VhcFecha != "" ? "'"+(this.VhcFecha)+"'" : "NULL")
                + ",ToxoFecha=" + (this.ToxoFecha != "" ? "'"+(this.ToxoFecha)+"'" : "NULL")
                + ",VdrlFecha=" + (this.VdrlFecha != "" ? "'"+(this.VdrlFecha)+"'" : "NULL")
                + ",ChagasFecha=" + (this.ChagasFecha != "" ? "'"+(this.ChagasFecha)+"'" : "NULL")
                + ",PapFecha=" + (this.PapFecha != "" ? "'"+(this.PapFecha)+"'" : "NULL")
                + ",HiperFecha=" + (this.HiperFecha != "" ? "'"+(this.HiperFecha)+"'" : "NULL")
                + ",DisliFecha=" + (this.DisliFecha != "" ? "'"+(this.DisliFecha)+"'" : "NULL")
                + ",GliceFecha=" + (this.GliceFecha != "" ? "'"+(this.GliceFecha)+"'" : "NULL")
                + ",HemaFecha=" + (this.HemaFecha != "" ? "'"+(this.HemaFecha)+"'" : "NULL")
                + ",ColesTotal=" + (this.ColesTotal != "" ? this.ColesTotal : "NULL")
                + ",ColesLdl=" + (this.ColesLdl != "" ? this.ColesLdl : "NULL")
                + ",ColesHdl=" + (this.ColesHdl != "" ? this.ColesHdl : "NULL")
                + ",Glice=" + (this.Glice != "" ? this.Glice : "NULL")
                + ",Hematocrito=" + (this.Hematocrito != "" ? this.Hematocrito : "NULL")
                //+ ",Transaminasas=" + (this.Transaminasas != "" ? this.Transaminasas : "NULL")
                + ",Trigi=" + (this.Trigi != "" ? this.Trigi : "NULL")
                + ",PpdTratamiento=" + (this.PpdTratamiento != "" ? "'"+this.PpdTratamiento+"'" : "NULL")
                + ",HiperDiag=" + (this.HiperDiag != "" ? "'"+this.HiperDiag+"'" : "NULL")
                + ",DisliDiag=" + (this.DisliDiag != "" ? "'"+this.DisliDiag+"'" : "NULL")
                + ",GliceDiag=" + (this.GliceDiag != "" ? "'"+this.GliceDiag+"'" : "NULL")
                + ",PSist=" + (this.PSist != "" ? this.PSist : "NULL")
                + ",PDias=" + (this.PDias != "" ? this.PDias : "NULL")
                + ",Got=" + (this.Got != "" ? "'"+this.Got+"'" : "NULL")
                + ",Gpt=" + (this.Gpt != "" ? "'"+this.Gpt+"'" : "NULL")
                + ",ExamenOrina=" + (this.ExamenOrina != "" ? "'"+this.ExamenOrina+"'" : "NULL")
                + ",RxTorax=" + (this.RxTorax != "" ? "'"+this.RxTorax+"'" : "NULL")
                + ",Siges=" + (this.Siges != "" ? "'"+this.Siges+"'" : "NULL")
                + ",Observaciones=" + (this.Observaciones != "" ? "'"+this.Observaciones+"'" : "NULL")
                + " WHERE ID = " + this.getBasalesID();
        
    
        try {
            em.getTransaction().begin();
            em.createNativeQuery(query).executeUpdate();
            em.getTransaction().commit();
            
            try{
                Date today = new Date();
                SimpleDateFormat fecha = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                if(httpServletRequest.getSession().getAttribute("UID")!=null){
                    Integer uid=(Integer)httpServletRequest.getSession().getAttribute("UID");
                    em.getTransaction().begin();
                    em.createNativeQuery("INSERT INTO registro_modificaciones (usuario_id,identificador,seccion,accion,fecha,SID)VALUES("+uid+","+this.basalesID+",'basales','A','"+fecha.format(today)+":00','"+httpServletRequest.getSession().getId()+"');").executeUpdate();
                    em.getTransaction().commit();
                }
            }catch(Exception e){}   
            this.setResultado("<script>$(document).ready(function(){$('body').css('opacity','0.1');alert('Paciente actualizado con éxito!');$('body').css('opacity','1');});</script>");
        } catch (Exception e) {
            this.setResultado("<script>$(document).ready(function(){$('body').css('opacity','0.1');alert('Error al actualizar Paciente!');$('body').css('opacity','1');});</script>"+e.getMessage());
        }
        em.close();
        em = null;
        System.gc();
        cargarVariables();
        return "guardado";
    }
    
    public void cargarVariables() {
        EntityManager em = emf.createEntityManager();
        //                      0           1   2    3   4      5   6    7    8    9        10         11    12      13   14    15         16            17
        String query = "SELECT PacienteID, ID, CD4, CV, PCD4, Log, Hla, Ppd, Hbs, Vhc, Toxoplasmosis, Vdrl, Chagas, Pap, Peso, Talla,ClasificacionL, ClasificacionN, "
                + ""// 18      19       20          21        22         23        24       25            26         27         28         29          30        31         32          33           34         35
                + "CD4Fecha, CVFecha, HlaFecha, PpdFecha, HbsaFecha, VhcFecha, ToxoFecha, VdrlFecha, ChagasFecha, PapFecha, Enf1Fecha, Enf2Fecha, Enf3Fecha, Enf4Fecha, HiperFecha, DisliFecha, GliceFecha, HemaFecha, "
                + ""//36     37      38      39      40     41        42         43       44       45         46        47            48         49          50       51    52    53   54       55         56       57      58               59
                + "Enf1ID, Enf2ID, Enf3ID, Enf4ID, Imc, ColesTotal, ColesLdl, ColesHdl, Glice, Hematocrito, Trigi, PpdTratamiento, HiperDiag, DisliDiag, GliceDiag, PSist, PDias, Got, Gpt, ExamenOrina, RxTorax, Siges, Transaminasas, Observaciones "
                + ""
                + ""
                + "FROM DatoBasal "
                + "WHERE ID = " + this.getBasalesID() + ";";
      
        List<Object[]> list = (List<Object[]>)em.createNativeQuery(query).getResultList();
        for (Object[] var : list) {
            
            ColesTotal = (var[41] != null && !"".equals(var[41].toString()) ? var[41].toString() : "");
            ColesLdl = (var[42] != null && !"".equals(var[42].toString()) ? var[42].toString() : "");
            ColesHdl = (var[43] != null && !"".equals(var[43].toString()) ? var[43].toString() : "");
            Glice = (var[44] != null && !"".equals(var[44].toString()) ? var[44].toString() : "");
            Hematocrito = (var[45] != null && !"".equals(var[45].toString()) ? var[45].toString() : "");
            Trigi = (var[46] != null && !"".equals(var[46].toString()) ? var[46].toString() : "");
    
            this.clasificacionL = (var[16] != null && !"".equals(var[16].toString()) ? var[16].toString() : "A");
            this.clasificacionN = (var[17] != null && !"".equals(var[17].toString()) ? var[17].toString() : "");
            //cd4fecha;
            //cvfecha;
            this.pcd4 = (var[4] != null && !"".equals(var[4].toString()) ? var[4].toString() : "");
            this.logaritmo = (var[5] != null && !"".equals(var[5].toString()) ? var[5].toString() : "");
            this.cd4 = (var[2] != null && !"".equals(var[2].toString()) ? Integer.parseInt(var[2].toString()) : null);
            this.cv = (var[3] != null && !"".equals(var[3].toString()) ? Integer.parseInt(var[3].toString()) : null);

            this.hla = (var[6] != null && !"".equals(var[6].toString()) ? var[6].toString() : "");
            this.ppd = (var[7] != null && !"".equals(var[7].toString()) ? var[7].toString() : "");
            
            //ppdfecha;
            hbsa = (var[8] != null && !"".equals(var[8].toString()) ? var[8].toString() : "");
            //hbsagfecha;
            vhc = (var[9] != null && !"".equals(var[9].toString()) ? var[9].toString() : "");
            //vhcfecha;
            peso = (var[14] != null && !"".equals(var[14].toString()) ? var[14].toString() : "");
            talla = (var[15] != null && !"".equals(var[15].toString()) ? var[15].toString() : "");
            toxoplasmosis = (var[10] != null && !"".equals(var[10].toString()) ? var[10].toString() : "");
            //toxoplasmosisfecha;   
            imc = (var[40] != null && !"".equals(var[40].toString()) ? var[40].toString() : "");
            vdrl = (var[11] != null && !"".equals(var[11].toString()) ? var[11].toString() : "");
            //vdrlfecha;
            chagas = (var[12] != null && !"".equals(var[12].toString()) ? var[12].toString() : "");
            //chagasfecha;
            pap = (var[13] != null && !"".equals(var[13].toString()) ? var[13].toString() : "");
            
            
            // Fechas!!
            enfermedad1ID = (var[36] != null && !"".equals(var[36].toString()) ? Integer.parseInt(var[36].toString()) : null);
            enfermedad2ID = (var[37] != null && !"".equals(var[37].toString()) ? Integer.parseInt(var[37].toString()) : null);
            enfermedad3ID = (var[38] != null && !"".equals(var[38].toString()) ? Integer.parseInt(var[38].toString()) : null);
            enfermedad4ID = (var[39] != null && !"".equals(var[39].toString()) ? Integer.parseInt(var[39].toString()) : null);
            
            // Fechas!!
            CD4Fecha = (var[18] != null && !"".equals(var[18].toString()) ? (var[18].toString()) : "");
            CVFecha = (var[19] != null && !"".equals(var[19].toString()) ? (var[19].toString()) : "");
            PpdFecha = (var[21] != null && !"".equals(var[21].toString()) ? (var[21].toString()) : "");
            HbsaFecha = (var[22] != null && !"".equals(var[22].toString()) ? (var[22].toString()) : "");
            VhcFecha = (var[23] != null && !"".equals(var[23].toString()) ? (var[23].toString()) : "");
            ToxoFecha = (var[24] != null && !"".equals(var[24].toString()) ? (var[24].toString()) : "");
            VdrlFecha = (var[25] != null && !"".equals(var[25].toString()) ? (var[25].toString()) : "");
            ChagasFecha = (var[26] != null && !"".equals(var[26].toString()) ? (var[26].toString()) : "");
            PapFecha = (var[27] != null && !"".equals(var[27].toString()) ? (var[27].toString()) : "");
            HiperFecha = (var[32] != null && !"".equals(var[32].toString()) ? (var[32].toString()) : "");
            DisliFecha = (var[33] != null && !"".equals(var[33].toString()) ? (var[33].toString()) : "");
            GliceFecha = (var[34] != null && !"".equals(var[34].toString()) ? (var[34].toString()) : "");
            HemaFecha = (var[35] != null && !"".equals(var[35].toString()) ? (var[35].toString()) : "");
            
            //HlaFecha = (var[20] != null && !"".equals(var[20].toString()) ? (var[20].toString()) : "");
            
            Enf1Fecha = (var[28] != null && !"".equals(var[28].toString()) ? (var[28].toString()) : "");
            Enf2Fecha = (var[29] != null && !"".equals(var[29].toString()) ? (var[29].toString()) : "");
            Enf3Fecha = (var[30] != null && !"".equals(var[30].toString()) ? (var[30].toString()) : "");
            Enf4Fecha = (var[31] != null && !"".equals(var[31].toString()) ? (var[31].toString()) : "");
            
            PpdTratamiento = (var[47] != null && !"".equals(var[47].toString()) ? var[47].toString() : "");
            HiperDiag = (var[48] != null && !"".equals(var[48].toString()) ? var[48].toString() : "");
            DisliDiag = (var[49] != null && !"".equals(var[49].toString()) ? var[49].toString() : "");
            GliceDiag = (var[50] != null && !"".equals(var[50].toString()) ? var[50].toString() : "");
            PSist = (var[51] != null && !"".equals(var[51].toString()) ? var[51].toString() : "");
            PDias = (var[52] != null && !"".equals(var[52].toString()) ? var[52].toString() : "");
            
            Got = (var[53] != null && !"".equals(var[53].toString()) ? var[53].toString() : "");
            Gpt = (var[54] != null && !"".equals(var[54].toString()) ? var[54].toString() : "");
            
            ExamenOrina = (var[55] != null && !"".equals(var[55].toString()) ? var[55].toString() : "");
            RxTorax = (var[56] != null && !"".equals(var[56].toString()) ? var[56].toString() : "");
            Siges = (var[57] != null && !"".equals(var[57].toString()) ? var[57].toString() : "");
            Transaminasas = (var[58] != null && !"".equals(var[58].toString()) ? var[58].toString() : "");    
            Observaciones = (var[59] != null && !"".equals(var[59].toString()) ? var[59].toString() : "");
            break;
        }
        em.close();
        em = null;
        list = null;
        System.gc();
    }
    /**
     * @return the pacienteSeleccionado
     */
    public Boolean getPacienteSeleccionado() {
        return pacienteSeleccionado;
    }

    /**
     * @param pacienteSeleccionado the pacienteSeleccionado to set
     */
    public void setPacienteSeleccionado(Boolean pacienteSeleccionado) {
        this.pacienteSeleccionado = pacienteSeleccionado;
    }

    /**
     * @return the resultados
     */
    public List<Object[]> getResultados() {
        return resultados;
    }

    /**
     * @param resultados the resultados to set
     */
    public void setResultados(List<Object[]> resultados) {
        this.resultados = resultados;
    }

    /**
     * @return the resultado
     */
    public String getResultado() {
        return resultado;
    }

    /**
     * @param resultado the resultado to set
     */
    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    /**
     * @return the pacienteID
     */
    public Integer getPacienteID() {
        return pacienteID;
    }

    /**
     * @param pacienteID the pacienteID to set
     */
    public void setPacienteID(Integer pacienteID) {
        this.pacienteID = pacienteID;
    }

    /**
     * @return the clasificacionL
     */
    public String getClasificacionL() {
        return clasificacionL;
    }

    /**
     * @param clasificacionL the clasificacionL to set
     */
    public void setClasificacionL(String clasificacionL) {
        this.clasificacionL = clasificacionL;
    }

    /**
     * @return the clasificacionN
     */
    public String getClasificacionN() {
        return clasificacionN;
    }

    /**
     * @param clasificacionN the clasificacionN to set
     */
    public void setClasificacionN(String clasificacionN) {
        this.clasificacionN = clasificacionN;
    }

    /**
     * @return the cd4
     */
    public Integer getCd4() {
        return cd4;
    }

    /**
     * @param cd4 the cd4 to set
     */
    public void setCd4(Integer cd4) {
        this.cd4 = cd4;
    }

    /**
     * @return the cv
     */
    public Integer getCv() {
        return cv;
    }

    /**
     * @param cv the cv to set
     */
    public void setCv(Integer cv) {
        this.cv = cv;
    }

    /**
     * @return the cd4fecha
     */
    
    /**
     * @return the pcd4
     */
    public String getPcd4() {
        return pcd4;
    }

    /**
     * @param pcd4 the pcd4 to set
     */
    public void setPcd4(String pcd4) {
        this.pcd4 = pcd4;
    }

    /**
     * @return the logaritmo
     */
    public String getLogaritmo() {
        return logaritmo;
    }

    /**
     * @param logaritmo the logaritmo to set
     */
    public void setLogaritmo(String logaritmo) {
        this.logaritmo = logaritmo;
    }

    /**
     * @return the ppd
     */
    public String getPpd() {
        return ppd;
    }

    /**
     * @param ppd the ppd to set
     */
    public void setPpd(String ppd) {
        this.ppd = ppd;
    }

    /**
     * @return the ppdfecha
     */
    
    /**
     * @return the hbsa
     */
    public String getHbsa() {
        return hbsa;
    }

    /**
     * @param hbsa the hbsa to set
     */
    public void setHbsa(String hbsa) {
        this.hbsa = hbsa;
    }

    /**
     * @return the hbsagfecha
     */
   

    /**
     * @return the vhc
     */
    public String getVhc() {
        return vhc;
    }

    /**
     * @param vhc the vhc to set
     */
    public void setVhc(String vhc) {
        this.vhc = vhc;
    }

    /**
     * @return the vhcfecha
     */
   

    /**
     * @return the peso
     */
    public String getPeso() {
        return peso;
    }

    /**
     * @param peso the peso to set
     */
    public void setPeso(String peso) {
        this.peso = peso;
    }

    /**
     * @return the talla
     */
    public String getTalla() {
        return talla;
    }

    /**
     * @param talla the talla to set
     */
    public void setTalla(String talla) {
        this.talla = talla;
    }

    /**
     * @return the toxoplasmosis
     */
    public String getToxoplasmosis() {
        return toxoplasmosis;
    }

    /**
     * @param toxoplasmosis the toxoplasmosis to set
     */
    public void setToxoplasmosis(String toxoplasmosis) {
        this.toxoplasmosis = toxoplasmosis;
    }

    /**
     * @return the toxoplasmosisfecha
     */
    
    /**
     * @return the imc
     */
    public String getImc() {
        return imc;
    }

    /**
     * @param imc the imc to set
     */
    public void setImc(String imc) {
        this.imc = imc;
    }

    /**
     * @return the vdrl
     */
    public String getVdrl() {
        return vdrl;
    }

    /**
     * @param vdrl the vdrl to set
     */
    public void setVdrl(String vdrl) {
        this.vdrl = vdrl;
    }

    /**
     * @return the vdrlfecha
     */
   

    /**
     * @return the chagas
     */
    public String getChagas() {
        return chagas;
    }

    /**
     * @param chagas the chagas to set
     */
    public void setChagas(String chagas) {
        this.chagas = chagas;
    }

    /**
     * @return the chagasfecha
     */
   

    /**
     * @return the pap
     */
    public String getPap() {
        return pap;
    }

    /**
     * @param pap the pap to set
     */
    public void setPap(String pap) {
        this.pap = pap;
    }

    /**
     * @return the papfecha
     */
    
    /**
     * @return the enfermedad1ID
     */
    public Integer getEnfermedad1ID() {
        return enfermedad1ID;
    }

    /**
     * @param enfermedad1ID the enfermedad1ID to set
     */
    public void setEnfermedad1ID(Integer enfermedad1ID) {
        this.enfermedad1ID = enfermedad1ID;
    }

    /**
     * @return the enfermedad2ID
     */
    public Integer getEnfermedad2ID() {
        return enfermedad2ID;
    }

    /**
     * @param enfermedad2ID the enfermedad2ID to set
     */
    public void setEnfermedad2ID(Integer enfermedad2ID) {
        this.enfermedad2ID = enfermedad2ID;
    }

    /**
     * @return the enfermedad3ID
     */
    public Integer getEnfermedad3ID() {
        return enfermedad3ID;
    }

    /**
     * @param enfermedad3ID the enfermedad3ID to set
     */
    public void setEnfermedad3ID(Integer enfermedad3ID) {
        this.enfermedad3ID = enfermedad3ID;
    }

    /**
     * @return the enfermedad4ID
     */
    public Integer getEnfermedad4ID() {
        return enfermedad4ID;
    }

    /**
     * @param enfermedad4ID the enfermedad4ID to set
     */
    public void setEnfermedad4ID(Integer enfermedad4ID) {
        this.enfermedad4ID = enfermedad4ID;
    }

    /**
     * @return the hla
     */
    public String getHla() {
        return hla;
    }

    /**
     * @param hla the hla to set
     */
    public void setHla(String hla) {
        this.hla = hla;
    }

    /**
     * @return the CD4Fecha
     */
    public String getCD4Fecha() {
        return CD4Fecha;
    }

    /**
     * @param CD4Fecha the CD4Fecha to set
     */
    public void setCD4Fecha(String CD4Fecha) {
        this.CD4Fecha = CD4Fecha;
    }

    /**
     * @return the CVFecha
     */
    public String getCVFecha() {
        return CVFecha;
    }

    /**
     * @param CVFecha the CVFecha to set
     */
    public void setCVFecha(String CVFecha) {
        this.CVFecha = CVFecha;
    }

    /**
     * @return the HlaFecha
     */
    public String getHlaFecha() {
        return HlaFecha;
    }

    /**
     * @param HlaFecha the HlaFecha to set
     */
    public void setHlaFecha(String HlaFecha) {
        this.HlaFecha = HlaFecha;
    }

    /**
     * @return the PpdFecha
     */
    public String getPpdFecha() {
        return PpdFecha;
    }

    /**
     * @param PpdFecha the PpdFecha to set
     */
    public void setPpdFecha(String PpdFecha) {
        this.PpdFecha = PpdFecha;
    }

    /**
     * @return the HbsaFecha
     */
    public String getHbsaFecha() {
        return HbsaFecha;
    }

    /**
     * @param HbsaFecha the HbsaFecha to set
     */
    public void setHbsaFecha(String HbsaFecha) {
        this.HbsaFecha = HbsaFecha;
    }

    /**
     * @return the VhcFecha
     */
    public String getVhcFecha() {
        return VhcFecha;
    }

    /**
     * @param VhcFecha the VhcFecha to set
     */
    public void setVhcFecha(String VhcFecha) {
        this.VhcFecha = VhcFecha;
    }

    /**
     * @return the ToxoFecha
     */
    public String getToxoFecha() {
        return ToxoFecha;
    }

    /**
     * @param ToxoFecha the ToxoFecha to set
     */
    public void setToxoFecha(String ToxoFecha) {
        this.ToxoFecha = ToxoFecha;
    }

    /**
     * @return the VdrlFecha
     */
    public String getVdrlFecha() {
        return VdrlFecha;
    }

    /**
     * @param VdrlFecha the VdrlFecha to set
     */
    public void setVdrlFecha(String VdrlFecha) {
        this.VdrlFecha = VdrlFecha;
    }

    /**
     * @return the ChagasFecha
     */
    public String getChagasFecha() {
        return ChagasFecha;
    }

    /**
     * @param ChagasFecha the ChagasFecha to set
     */
    public void setChagasFecha(String ChagasFecha) {
        this.ChagasFecha = ChagasFecha;
    }

    /**
     * @return the PapFecha
     */
    public String getPapFecha() {
        return PapFecha;
    }

    /**
     * @param PapFecha the PapFecha to set
     */
    public void setPapFecha(String PapFecha) {
        this.PapFecha = PapFecha;
    }

    /**
     * @return the Enf1Fecha
     */
    public String getEnf1Fecha() {
        return Enf1Fecha;
    }

    /**
     * @param Enf1Fecha the Enf1Fecha to set
     */
    public void setEnf1Fecha(String Enf1Fecha) {
        this.Enf1Fecha = Enf1Fecha;
    }

    /**
     * @return the Enf2Fecha
     */
    public String getEnf2Fecha() {
        return Enf2Fecha;
    }

    /**
     * @param Enf2Fecha the Enf2Fecha to set
     */
    public void setEnf2Fecha(String Enf2Fecha) {
        this.Enf2Fecha = Enf2Fecha;
    }

    /**
     * @return the Enf3Fecha
     */
    public String getEnf3Fecha() {
        return Enf3Fecha;
    }

    /**
     * @param Enf3Fecha the Enf3Fecha to set
     */
    public void setEnf3Fecha(String Enf3Fecha) {
        this.Enf3Fecha = Enf3Fecha;
    }

    /**
     * @return the Enf4Fecha
     */
    public String getEnf4Fecha() {
        return Enf4Fecha;
    }

    /**
     * @param Enf4Fecha the Enf4Fecha to set
     */
    public void setEnf4Fecha(String Enf4Fecha) {
        this.Enf4Fecha = Enf4Fecha;
    }

    /**
     * @return the HiperFecha
     */
    public String getHiperFecha() {
        return HiperFecha;
    }

    /**
     * @param HiperFecha the HiperFecha to set
     */
    public void setHiperFecha(String HiperFecha) {
        this.HiperFecha = HiperFecha;
    }

    /**
     * @return the DisliFecha
     */
    public String getDisliFecha() {
        return DisliFecha;
    }

    /**
     * @param DisliFecha the DisliFecha to set
     */
    public void setDisliFecha(String DisliFecha) {
        this.DisliFecha = DisliFecha;
    }

    /**
     * @return the GliceFecha
     */
    public String getGliceFecha() {
        return GliceFecha;
    }

    /**
     * @param GliceFecha the GliceFecha to set
     */
    public void setGliceFecha(String GliceFecha) {
        this.GliceFecha = GliceFecha;
    }

    /**
     * @return the HemaFecha
     */
    public String getHemaFecha() {
        return HemaFecha;
    }

    /**
     * @param HemaFecha the HemaFecha to set
     */
    public void setHemaFecha(String HemaFecha) {
        this.HemaFecha = HemaFecha;
    }

    /**
     * @return the basalesID
     */
    public Integer getBasalesID() {
        return basalesID;
    }

    /**
     * @param basalesID the basalesID to set
     */
    public void setBasalesID(Integer basalesID) {
        this.basalesID = basalesID;
    }

    /**
     * @return the ColesTotal
     */
    public String getColesTotal() {
        return ColesTotal;
    }

    /**
     * @param ColesTotal the ColesTotal to set
     */
    public void setColesTotal(String ColesTotal) {
        this.ColesTotal = ColesTotal;
    }

    /**
     * @return the ColesLdl
     */
    public String getColesLdl() {
        return ColesLdl;
    }

    /**
     * @param ColesLdl the ColesLdl to set
     */
    public void setColesLdl(String ColesLdl) {
        this.ColesLdl = ColesLdl;
    }

    /**
     * @return the ColesHdl
     */
    public String getColesHdl() {
        return ColesHdl;
    }

    /**
     * @param ColesHdl the ColesHdl to set
     */
    public void setColesHdl(String ColesHdl) {
        this.ColesHdl = ColesHdl;
    }

    /**
     * @return the Glice
     */
    public String getGlice() {
        return Glice;
    }

    /**
     * @param Glice the Glice to set
     */
    public void setGlice(String Glice) {
        this.Glice = Glice;
    }

    /**
     * @return the Hematocrito
     */
    public String getHematocrito() {
        return Hematocrito;
    }

    /**
     * @param Hematocrito the Hematocrito to set
     */
    public void setHematocrito(String Hematocrito) {
        this.Hematocrito = Hematocrito;
    }

    /**
     * @return the Trigi
     */
    public String getTrigi() {
        return Trigi;
    }

    /**
     * @param Trigi the Trigi to set
     */
    public void setTrigi(String Trigi) {
        this.Trigi = Trigi;
    }

    /**
     * @return the PpdTratamiento
     */
    public String getPpdTratamiento() {
        return PpdTratamiento;
    }

    /**
     * @param PpdTratamiento the PpdTratamiento to set
     */
    public void setPpdTratamiento(String PpdTratamiento) {
        this.PpdTratamiento = PpdTratamiento;
    }

    /**
     * @return the HiperDiag
     */
    public String getHiperDiag() {
        return HiperDiag;
    }

    /**
     * @param HiperDiag the HiperDiag to set
     */
    public void setHiperDiag(String HiperDiag) {
        this.HiperDiag = HiperDiag;
    }

    /**
     * @return the DisliDiag
     */
    public String getDisliDiag() {
        return DisliDiag;
    }

    /**
     * @param DisliDiag the DisliDiag to set
     */
    public void setDisliDiag(String DisliDiag) {
        this.DisliDiag = DisliDiag;
    }

    /**
     * @return the GliceDiag
     */
    public String getGliceDiag() {
        return GliceDiag;
    }

    /**
     * @param GliceDiag the GliceDiag to set
     */
    public void setGliceDiag(String GliceDiag) {
        this.GliceDiag = GliceDiag;
    }

    /**
     * @return the PSist
     */
    public String getPSist() {
        return PSist;
    }

    /**
     * @param PSist the PSist to set
     */
    public void setPSist(String PSist) {
        this.PSist = PSist;
    }

    /**
     * @return the PDias
     */
    public String getPDias() {
        return PDias;
    }

    /**
     * @param PDias the PDias to set
     */
    public void setPDias(String PDias) {
        this.PDias = PDias;
    }

    /**
     * @return the Got
     */
    public String getGot() {
        return Got;
    }

    /**
     * @param Got the Got to set
     */
    public void setGot(String Got) {
        this.Got = Got;
    }

    /**
     * @return the Gpt
     */
    public String getGpt() {
        return Gpt;
    }

    /**
     * @param Gpt the Gpt to set
     */
    public void setGpt(String Gpt) {
        this.Gpt = Gpt;
    }

    /**
     * @return the RxTorax
     */
    public String getRxTorax() {
        return RxTorax;
    }

    /**
     * @param RxTorax the RxTorax to set
     */
    public void setRxTorax(String RxTorax) {
        this.RxTorax = RxTorax;
    }

    /**
     * @return the ExamenOrina
     */
    public String getExamenOrina() {
        return ExamenOrina;
    }

    /**
     * @param ExamenOrina the ExamenOrina to set
     */
    public void setExamenOrina(String ExamenOrina) {
        this.ExamenOrina = ExamenOrina;
    }

    /**
     * @return the Siges
     */
    public String getSiges() {
        return Siges;
    }

    /**
     * @param Siges the Siges to set
     */
    public void setSiges(String Siges) {
        this.Siges = Siges;
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
     * @return the Transaminasas
     */
    public String getTransaminasas() {
        return Transaminasas;
    }

    /**
     * @param Transaminasas the Transaminasas to set
     */
    public void setTransaminasas(String Transaminasas) {
        this.Transaminasas = Transaminasas;
    }

    /**
     * @return the Observaciones
     */
    public String getObservaciones() {
        return Observaciones;
    }

    /**
     * @param Observaciones the Observaciones to set
     */
    public void setObservaciones(String Observaciones) {
        this.Observaciones = Observaciones;
    }
    
    
}
