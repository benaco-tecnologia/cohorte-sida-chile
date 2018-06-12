/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cohorte;

import java.math.BigInteger;
import java.text.ParseException;
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
public class LaboratorioBean {
    private Util util = new Util();
    private List<Object[]> resultados;
    private String resultado = "";
    private Integer pacienteID;
    private Integer laboratorioID;
    private String codigo = "";
    
    private List<Object[]> resultadosHiper;
    private List<Object[]> resultadosDisli;
    private List<Object[]> resultadosGlice;
    private List<Object[]> resultadosHema;
    
    private Integer ultimohiper;
    private Integer ultimodisli;
    private Integer ultimoglice;
    private Integer ultimohema;
    
    private Integer NumeroControl;
    private String Tipo;
    private String Fecha;
    private String Diagnostico;
    private String Sistolica;
    private String Diastolica;
    private String Colestotal;
    private String ColesLdl;
    private String ColesHdl;
    private String Trigli;
    private String Glice;
    private String Peso;
    private String Hematocrito;
    private String Gpt;
    private String Got;
    
    private Usuario logueado;
    private final HttpServletRequest httpServletRequest;
    private final FacesContext faceContext;
    
    private String Observaciones = "";
    
    
    @PersistenceUnit 
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("Cohorte2PU");; 
   
   // @PersistenceContext
    //EntityManager em = emf.createEntityManager();
    
    /**
     * Creates a new instance of LaboratorioBean
     */
    public LaboratorioBean() {
        this.codigo = "";
        faceContext=FacesContext.getCurrentInstance();
        httpServletRequest=(HttpServletRequest)faceContext.getExternalContext().getRequest();
        if(httpServletRequest.getSession().getAttribute("Usuario") != null)
        {
            logueado = (Usuario)httpServletRequest.getSession().getAttribute("Usuario");
        }
    }
    
    public String cargarLaboratorio() {
        if(httpServletRequest.getSession()==null||httpServletRequest.getSession().getAttribute("UID")==null){
            this.resultado="<script>$(document).ready(function(){$('body').css('opacity','0.1');alert('Su sesion ha estado inactiva por mas de 60 minutos, por seguridad debe ingresar nuevamente.');window.location='/Cohorte2/';$('body').css('opacity','1');});</script>";
            return "encontrado";
        }
        EntityManager em = emf.createEntityManager();
        this.codigo = "";
        String value = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("pacienteIDH");
        try {
            this.setPacienteID((Integer) Integer.parseInt(value));
        } catch (Exception e) {
            this.setPacienteID((Integer) 0);
        }
        
        String query = "SELECT p.ID, p.Codigo "
                + "FROM Paciente p "
                + "WHERE p.ID = " + this.getPacienteID();
        List<Object[]> list = (List<Object[]>)em.createNativeQuery(query).getResultList();
        for (Object[] var : list) {
            this.codigo = var[1].toString();
        }
        
        this.setUltimodisli(this.siguienteNumero(this.getPacienteID(), "DI"));
        this.setUltimoglice(this.siguienteNumero(this.getPacienteID(), "GL"));
        this.setUltimohema(this.siguienteNumero(this.getPacienteID(), "HE"));
        this.setUltimohiper(this.siguienteNumero(this.getPacienteID(), "HI"));
        
        NumeroControl = this.getUltimohiper();
        this.Tipo = "HI";
        
        this.laboratorioID = null;
        this.Fecha = "";
        this.Diagnostico = "";
        this.Sistolica = "";
        this.Diastolica = "";
        this.Colestotal = "";
        this.ColesLdl = "";
        this.ColesHdl = "";
        this.Trigli = "";
        this.Glice = "";
        this.Peso = "";
        this.Hematocrito = "";
        this.Gpt = "";
        this.Got = "";
        em.close();
        em=null;
        listadoHiper();
        listadoDisli();
        listadoGlice();
        listadoHema();
        
        this.setResultado("Seccion LAB cargada. " + this.getPacienteID());
        return "encontrado";
    }
    
    public String guardarLaboratorio() {
        if(httpServletRequest.getSession()==null||httpServletRequest.getSession().getAttribute("UID")==null){
            this.resultado="<script>$(document).ready(function(){$('body').css('opacity','0.1');alert('Su sesion ha estado inactiva por mas de 60 minutos, por seguridad debe ingresar nuevamente.');window.location='/Cohorte2/';$('body').css('opacity','1');});</script>";
            return "guardado";
        }
        EntityManager em = emf.createEntityManager();
        String query = "";
        Integer t=0;
        if (this.pacienteID != null && this.pacienteID != 0 && this.laboratorioID != null && this.laboratorioID != 0) {
            query = "UPDATE Laboratorio SET "
                    + " Diagnostico="+(this.Diagnostico.equals("") ? "NULL" : "'"+this.Diagnostico+"'" )+ ", "
                    + " Sistolica="+(this.Diastolica.equals("") ? "NULL" : ""+this.Sistolica+"" )+ ", "
                    + " Diastolica="+(this.Diastolica.equals("") ? "NULL" : ""+this.Diastolica+"" )+ ", "
                    + " ColesTotal="+(this.Colestotal.equals("") ? "NULL" : ""+this.Colestotal+"" )+ ", "
                    + " ColesLdl="+(this.ColesLdl.equals("") ? "NULL" : ""+this.ColesLdl+"" )+ ", "
                    + " ColesHdl="+(this.ColesHdl.equals("") ? "NULL" : ""+this.ColesHdl+"" )+ ", "
                    + " Trigli="+(this.Trigli.equals("") ? "NULL" : ""+this.Trigli+"" )+ ", "
                    + " Glice="+(this.Glice.equals("") ? "NULL" : ""+this.Glice+"" )+ ", "
                    + " Peso="+(this.Peso.equals("") ? "NULL" : ""+this.Peso+"" )+ ", "
                    + " Hematocrito="+(this.Hematocrito.equals("") ? "NULL" : ""+this.Hematocrito+"" )+ ", "
                    + " Gpt="+(this.Gpt.equals("") ? "NULL" : "'"+this.Gpt+"" )+ ", "
                    + " Got=" +(this.Gpt.equals("") ? "NULL" : "'"+this.Gpt+"'" )+ ", "
                    //+ " Fecha=" +(this.getFecha().equals("") ? "NULL" : "'"+util.fechaSQL(this.getFecha())+"'" )+ ""
                    + " Fecha=" +(this.getFecha().equals("") ? "NULL" : "'"+this.getFecha()+"'" )+ ""
                    + " WHERE ID = " + this.laboratorioID;
            t=1;
        } else {
            if (this.Tipo == null || this.Tipo.equals("")) {
                this.resultado = "Tipo invalido";
                return "guardado";
            }
            query = "INSERT INTO Laboratorio "
                    + "(PacienteID, NumeroControl, Tipo, Diagnostico, Sistolica, Diastolica, ColesTotal, ColesLdl, ColesHdl, Trigli, Glice, Peso, Hematocrito, Gpt, Got, Fecha) "
                    + "VALUES "
                    + "("
                    + ""+ this.pacienteID+","
                    + ""+ this.NumeroControl+","
                    + "'"+ this.Tipo+"',"
                    + ""+ "" +(this.Diagnostico.equals("") ? "NULL" : "'"+this.Diagnostico+"'" )+ ", "
                    + ""+ "" +(this.Diastolica.equals("") ? "NULL" : ""+this.Sistolica+"" )+ ", "
                    + ""+ "" +(this.Diastolica.equals("") ? "NULL" : ""+this.Diastolica+"" )+ ", "
                    + ""+ "" +(this.Colestotal.equals("") ? "NULL" : ""+this.Colestotal+"" )+ ", "
                    + ""+ "" +(this.ColesLdl.equals("") ? "NULL" : ""+this.ColesLdl+"" )+ ", "
                    + ""+ "" +(this.ColesHdl.equals("") ? "NULL" : ""+this.ColesHdl+"" )+ ", "
                    + ""+ "" +(this.Trigli.equals("") ? "NULL" : ""+this.Trigli+"" )+ ", "
                    + ""+ "" +(this.Glice.equals("") ? "NULL" : ""+this.Glice+"" )+ ", "
                    + ""+ "" +(this.Peso.equals("") ? "NULL" : ""+this.Peso+"" )+ ", "
                    + ""+ "" +(this.Hematocrito.equals("") ? "NULL" : ""+this.Hematocrito+"" )+ ", "
                    + ""+ "" +(this.Gpt.equals("") ? "NULL" : "'"+this.Gpt+"'" )+ ", "
                    + ""+ "" +(this.Got.equals("") ? "NULL" : "'"+this.Got+"'" )+ ", "
                    //+ ""+ "" +(this.getFecha().equals("") ? "NULL" : "'"+util.fechaSQL(this.getFecha())+"'" )+ ""
                    + ""+ "" +(this.getFecha().equals("") ? "NULL" : "'"+this.getFecha()+"'" )+ ""
                    + ");";
            t=2;
        }
        
        try {
            em.getTransaction().begin();    
            em.createNativeQuery(query).executeUpdate();
            em.getTransaction().commit();
            if(httpServletRequest.getSession().getAttribute("UID")!=null){
                Integer uid=(Integer)httpServletRequest.getSession().getAttribute("UID");
                Date today = new Date();
                SimpleDateFormat fecha = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                if(t==1){
                    // Fix registro de modificacion
                    em.getTransaction().begin();
                    em.createNativeQuery("INSERT INTO registro_modificaciones (usuario_id,identificador,seccion,accion,fecha,SID)VALUES("+uid+","+this.laboratorioID+",'laboratorio','A','"+fecha.format(today)+":00','"+httpServletRequest.getSession().getId()+"');").executeUpdate();
                    em.getTransaction().commit();
                }else if(t==2){
                    // Fix registro de modificacion
                     Query q = em.createNativeQuery("SELECT LAST_INSERT_ID()");
                    Long value = ((BigInteger) q.getSingleResult()).longValue();    
                    this.laboratorioID = value.intValue();
                    em.getTransaction().begin();
                    em.createNativeQuery("INSERT INTO registro_modificaciones (usuario_id,identificador,seccion,accion,fecha,SID)VALUES("+uid+","+this.laboratorioID+",'laboratorio','I','"+fecha.format(today)+":00','"+httpServletRequest.getSession().getId()+"');").executeUpdate();
                    em.getTransaction().commit();
                }
            }
            this.resultado = query;
        } catch (Exception e) {
            this.resultado = e.getMessage();
        }
        
        this.setUltimodisli(this.siguienteNumero(this.getPacienteID(), "DI"));
        this.setUltimoglice(this.siguienteNumero(this.getPacienteID(), "GL"));
        this.setUltimohema(this.siguienteNumero(this.getPacienteID(), "HE"));
        this.setUltimohiper(this.siguienteNumero(this.getPacienteID(), "HI"));
        
        NumeroControl = this.getUltimohiper();
        this.Tipo = "HI";
        this.laboratorioID = null;
        this.Fecha = "";
        this.Diagnostico = "";
        this.Sistolica = "";
        this.Diastolica = "";
        this.Colestotal = "";
        this.ColesLdl = "";
        this.ColesHdl = "";
        this.Trigli = "";
        this.Glice = "";
        this.Peso = "";
        this.Hematocrito = "";
        this.Gpt = "";
        this.Got = "";
        em.close();
        em=null;
        listadoHiper();
        listadoDisli();
        listadoGlice();
        listadoHema();
        
        return "guardado";
    }
    
    public String modificarLaboratorio() throws ParseException {
        if(httpServletRequest.getSession()==null||httpServletRequest.getSession().getAttribute("UID")==null){
            this.resultado="<script>$(document).ready(function(){$('body').css('opacity','0.1');alert('Su sesion ha estado inactiva por mas de 60 minutos, por seguridad debe ingresar nuevamente.');window.location='/Cohorte2/';$('body').css('opacity','1');});</script>";
            return "encontrado";
        }
        EntityManager em = emf.createEntityManager();
       //                      0         1          2          3        4           5           6         7             8       9         10      11    12        13       14   15   16
        String query = "SELECT ID, PacienteID, NumeroControl, Tipo, Diagnostico, Sistolica, Diastolica, ColesTotal, ColesLdl, ColesHdl, Trigli, Glice, Peso, Hematocrito, Gpt, Got, Fecha "
                + "FROM Laboratorio "
                + "WHERE ID = " + this.laboratorioID + " AND PacienteID = " + this.pacienteID + ";";
        List<Object[]> list = (List<Object[]>)em.createNativeQuery(query).getResultList();
        for (Object[] var : list) {
            this.NumeroControl = Integer.parseInt(var[2].toString());
            this.pacienteID = Integer.parseInt(var[1].toString());
            this.laboratorioID = Integer.parseInt(var[0].toString());
            
            this.Tipo = var[3].toString();
            
            if (var[16] != null && var[16].toString() != null) {
                //this.Fecha = util.fechaForm(var[16].toString());
                this.Fecha=var[16].toString();
            }
            
            if (var[4] != null && var[4].toString() != null) {
                this.Diagnostico = var[4].toString();
            }
            
            if (var[5] != null && var[5].toString() != null) {
                this.Sistolica = var[5].toString();
            }
            
            if (var[6] != null && var[6].toString() != null) {
                this.Diastolica = var[6].toString();
            }
            
            if (var[7] != null && var[7].toString() != null) {
                this.Colestotal = var[7].toString();
            }
            
            if (var[8] != null && var[8].toString() != null) {
                this.ColesLdl = var[8].toString();
            }
            
            if (var[9] != null && var[9].toString() != null) {
                this.ColesHdl = var[9].toString();
            }
            
            if (var[10] != null && var[10].toString() != null) {
                this.Trigli = var[10].toString();
            }
            
            if (var[11] != null && var[11].toString() != null) {
                this.Glice = var[11].toString();
            }
            
            if (var[12] != null && var[12].toString() != null) {
                this.Peso = var[12].toString();
            }
            
            if (var[13] != null && var[13].toString() != null) {
                this.Hematocrito = var[13].toString();
            }
            
            if (var[14] != null && var[14].toString() != null) {
                this.Gpt = var[1].toString();
            }
            
            if (var[15] != null && var[15].toString() != null) {
                this.Got = var[15].toString();
            }
            
            break;
        }
        em.close();
        em=null;
        listadoHiper();
        listadoDisli();
        listadoGlice();
        listadoHema();
        
        return "encontrado";
    }
    
    public String eliminarLaboratorio() {
        if(httpServletRequest.getSession()==null||httpServletRequest.getSession().getAttribute("UID")==null){
            this.resultado="<script>$(document).ready(function(){$('body').css('opacity','0.1');alert('Su sesion ha estado inactiva por mas de 60 minutos, por seguridad debe ingresar nuevamente.');window.location='/Cohorte2/';$('body').css('opacity','1');});</script>";
            return "eliminado";
        }
        EntityManager em = emf.createEntityManager();
        //this.codigo = "";
        em.getTransaction().begin(); 
        String query = "DELETE FROM Laboratorio WHERE ID = " + this.laboratorioID + " AND PacienteID = " + this.pacienteID;
        em.createNativeQuery(query).executeUpdate();
        em.getTransaction().commit();
        
        this.resultado = "LABORATORIO ELIMINADO " + this.laboratorioID;
        
        this.setUltimodisli(this.siguienteNumero(this.getPacienteID(), "DI"));
        this.setUltimoglice(this.siguienteNumero(this.getPacienteID(), "GL"));
        this.setUltimohema(this.siguienteNumero(this.getPacienteID(), "HE"));
        this.setUltimohiper(this.siguienteNumero(this.getPacienteID(), "HI"));
        
        NumeroControl = this.getUltimohiper();
        this.laboratorioID = null;
        this.Tipo = "HI";
        this.Fecha = "";
        this.Diagnostico = "";
        this.Sistolica = "";
        this.Diastolica = "";
        this.Colestotal = "";
        this.ColesLdl = "";
        this.ColesHdl = "";
        this.Trigli = "";
        this.Glice = "";
        this.Peso = "";
        this.Hematocrito = "";
        this.Gpt = "";
        this.Got = "";
        em.close();
        em=null;
        listadoHiper();
        listadoDisli();
        listadoGlice();
        listadoHema();
        
        return "eliminado";
    }
    
    public void listadoHiper() {
        EntityManager em = emf.createEntityManager();
        String query = "SELECT ID, PacienteID, NumeroControl, Tipo, Diagnostico, Sistolica, Diastolica, Fecha, Peso FROM Laboratorio WHERE PacienteID = " + this.pacienteID + " AND Tipo = 'HI' ORDER BY NumeroControl DESC";
        setResultadosHiper((List<Object[]>)em.createNativeQuery(query).getResultList());
        em.close();
        em=null;
    }
    
    public void listadoGlice() {
        EntityManager em = emf.createEntityManager();
        String query = "SELECT ID, PacienteID, NumeroControl, Tipo, Diagnostico, Glice, Peso, Fecha FROM Laboratorio WHERE PacienteID = " + this.pacienteID + " AND Tipo = 'GL' ORDER BY NumeroControl DESC";
        setResultadosGlice((List<Object[]>)em.createNativeQuery(query).getResultList());
        em.close();
        em=null;
    }
    
    public void listadoDisli() {
        EntityManager em = emf.createEntityManager();
        String query = "SELECT ID, PacienteID, NumeroControl, Tipo, Diagnostico, ColesTotal, ColesLdl, ColesHdl, Trigli, Fecha FROM Laboratorio WHERE PacienteID = " + this.pacienteID + " AND Tipo = 'DI' ORDER BY NumeroControl DESC";
        setResultadosDisli((List<Object[]>)em.createNativeQuery(query).getResultList());
        em.close();
        em=null;
    }
    
    public void listadoHema() {
        EntityManager em = emf.createEntityManager();
        String query = "SELECT ID, PacienteID, NumeroControl, Tipo, Diagnostico, Hematocrito, Gpt, Got, Fecha FROM Laboratorio WHERE PacienteID = " + this.pacienteID + " AND Tipo = 'HE' ORDER BY NumeroControl DESC";
        setResultadosHema((List<Object[]>)em.createNativeQuery(query).getResultList());
        em.close();
        em=null;
    }
    
    public String generarFicha() {
        EntityManager em = emf.createEntityManager();
        String query = "SELECT p.ID, p.Codigo "
                + "FROM Paciente p "
                + "WHERE p.ID = " + this.getPacienteID();
        List<Object[]> list = (List<Object[]>)em.createNativeQuery(query).getResultList();
        for (Object[] var : list) {
            this.codigo = var[1].toString();
        }
        
        this.setUltimodisli(this.siguienteNumero(this.getPacienteID(), "DI"));
        this.setUltimoglice(this.siguienteNumero(this.getPacienteID(), "GL"));
        this.setUltimohema(this.siguienteNumero(this.getPacienteID(), "HE"));
        this.setUltimohiper(this.siguienteNumero(this.getPacienteID(), "HI"));
        
        NumeroControl = this.getUltimohiper();
        this.Tipo = "HI";
        
        this.laboratorioID = null;
        this.Fecha = "";
        this.Diagnostico = "";
        this.Sistolica = "";
        this.Diastolica = "";
        this.Colestotal = "";
        this.ColesLdl = "";
        this.ColesHdl = "";
        this.Trigli = "";
        this.Glice = "";
        this.Peso = "";
        this.Hematocrito = "";
        this.Gpt = "";
        this.Got = "";
        em.close();
        em=null;
        listadoHiper();
        listadoDisli();
        listadoGlice();
        listadoHema();
        
        this.setResultado("Seccion LAB cargada. " + this.getPacienteID());
        return "encontrado";
    }
    
    public Integer siguienteNumero(Integer pacienteID, String tipo) {
        EntityManager em = emf.createEntityManager();
        Integer r=1;
        try {
            Query q = em.createNativeQuery("SELECT MAX(l.NumeroControl) AS NumeroControl FROM Laboratorio l WHERE l.PacienteID = "+pacienteID+" AND l.Tipo = '"+tipo+"'");
            r=((Integer) q.getSingleResult()) + 1;
        } catch (Exception e) {
          //return 1;
        }
        em.close();
        em = null;
        System.gc();
        return r;
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
     * @return the laboratorioID
     */
    public Integer getLaboratorioID() {
        return laboratorioID;
    }

    /**
     * @param laboratorioID the laboratorioID to set
     */
    public void setLaboratorioID(Integer laboratorioID) {
        this.laboratorioID = laboratorioID;
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
     * @return the ultimohiper
     */
    public Integer getUltimohiper() {
        return ultimohiper;
    }

    /**
     * @param ultimohiper the ultimohiper to set
     */
    public void setUltimohiper(Integer ultimohiper) {
        this.ultimohiper = ultimohiper;
    }

    /**
     * @return the ultimodisli
     */
    public Integer getUltimodisli() {
        return ultimodisli;
    }

    /**
     * @param ultimodisli the ultimodisli to set
     */
    public void setUltimodisli(Integer ultimodisli) {
        this.ultimodisli = ultimodisli;
    }

    /**
     * @return the ultimoglice
     */
    public Integer getUltimoglice() {
        return ultimoglice;
    }

    /**
     * @param ultimoglice the ultimoglice to set
     */
    public void setUltimoglice(Integer ultimoglice) {
        this.ultimoglice = ultimoglice;
    }

    /**
     * @return the ultimohema
     */
    public Integer getUltimohema() {
        return ultimohema;
    }

    /**
     * @param ultimohema the ultimohema to set
     */
    public void setUltimohema(Integer ultimohema) {
        this.ultimohema = ultimohema;
    }

    /**
     * @return the NumeroControl
     */
    public Integer getNumeroControl() {
        return NumeroControl;
    }

    /**
     * @param NumeroControl the NumeroControl to set
     */
    public void setNumeroControl(Integer NumeroControl) {
        this.NumeroControl = NumeroControl;
    }

    /**
     * @return the Tipo
     */
    public String getTipo() {
        return Tipo;
    }

    /**
     * @param Tipo the Tipo to set
     */
    public void setTipo(String Tipo) {
        this.Tipo = Tipo;
    }

    /**
     * @return the Fecha
     */
    public String getFecha() {
        return Fecha;
    }

    /**
     * @param Fecha the Fecha to set
     */
    public void setFecha(String Fecha) {
        this.Fecha = Fecha;
    }

    /**
     * @return the Diagnostico
     */
    public String getDiagnostico() {
        return Diagnostico;
    }

    /**
     * @param Diagnostico the Diagnostico to set
     */
    public void setDiagnostico(String Diagnostico) {
        this.Diagnostico = Diagnostico;
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
     * @return the Sistolica
     */
    public String getSistolica() {
        return Sistolica;
    }

    /**
     * @param Sistolica the Sistolica to set
     */
    public void setSistolica(String Sistolica) {
        this.Sistolica = Sistolica;
    }

    /**
     * @return the Diastolica
     */
    public String getDiastolica() {
        return Diastolica;
    }

    /**
     * @param Diastolica the Diastolica to set
     */
    public void setDiastolica(String Diastolica) {
        this.Diastolica = Diastolica;
    }

    /**
     * @return the Colestotal
     */
    public String getColestotal() {
        return Colestotal;
    }

    /**
     * @param Colestotal the Colestotal to set
     */
    public void setColestotal(String Colestotal) {
        this.Colestotal = Colestotal;
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
     * @return the Trigli
     */
    public String getTrigli() {
        return Trigli;
    }

    /**
     * @param Trigli the Trigli to set
     */
    public void setTrigli(String Trigli) {
        this.Trigli = Trigli;
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
     * @return the Peso
     */
    public String getPeso() {
        return Peso;
    }

    /**
     * @param Peso the Peso to set
     */
    public void setPeso(String Peso) {
        this.Peso = Peso;
    }

    /**
     * @return the resultadosHiper
     */
    public List<Object[]> getResultadosHiper() {
        return resultadosHiper;
    }

    /**
     * @param resultadosHiper the resultadosHiper to set
     */
    public void setResultadosHiper(List<Object[]> resultadosHiper) {
        this.resultadosHiper = resultadosHiper;
    }

    /**
     * @return the resultadosDisli
     */
    public List<Object[]> getResultadosDisli() {
        return resultadosDisli;
    }

    /**
     * @param resultadosDisli the resultadosDisli to set
     */
    public void setResultadosDisli(List<Object[]> resultadosDisli) {
        this.resultadosDisli = resultadosDisli;
    }

    /**
     * @return the resultadosGlice
     */
    public List<Object[]> getResultadosGlice() {
        return resultadosGlice;
    }

    /**
     * @param resultadosGlice the resultadosGlice to set
     */
    public void setResultadosGlice(List<Object[]> resultadosGlice) {
        this.resultadosGlice = resultadosGlice;
    }

    /**
     * @return the resultadosHema
     */
    public List<Object[]> getResultadosHema() {
        return resultadosHema;
    }

    /**
     * @param resultadosHema the resultadosHema to set
     */
    public void setResultadosHema(List<Object[]> resultadosHema) {
        this.resultadosHema = resultadosHema;
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
