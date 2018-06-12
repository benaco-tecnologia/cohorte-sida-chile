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
public class SeguimientoBean {
    private Util util = new Util();
    private List<Object[]> resultados;
    private String resultado = "";
    private Integer pacienteID;
    private Integer seguimientoID;
    private String codigo = "";
    
    private List<Object[]> resultados1;
    private List<Object[]> resultados2;
    private List<Object[]> resultados3;
    private List<Object[]> resultados4;
    
    private Integer ultimo_enfermedad_asociada;
    private Integer ultimo_serologia;
    private Integer ultimo_control_vhb;
    private Integer ultimo_control_medico;
    
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
    private String Tratamiento;
    private String CVHepatitis;
    private String FechaControl;
    private String Antigeno;
    private String Examen;
    private String Patologia;
    private String PatologiaID;
    
    private Usuario logueado;
    private final HttpServletRequest httpServletRequest;
    private final FacesContext faceContext;
     
    private String Observaciones = "";
    @PersistenceUnit 
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("Cohorte2PU");; 
    //@PersistenceContext
    //EntityManager em = emf.createEntityManager();
    /**
     * Creates a new instance of SeguimientoBean
     */
    public SeguimientoBean() {
        this.codigo = "";
        faceContext=FacesContext.getCurrentInstance();
        httpServletRequest=(HttpServletRequest)faceContext.getExternalContext().getRequest();
        if(httpServletRequest.getSession().getAttribute("Usuario") != null)
        {
            logueado = (Usuario)httpServletRequest.getSession().getAttribute("Usuario");
        }
    }
    
    public String cargarSeguimiento() {
        if(httpServletRequest.getSession()==null||httpServletRequest.getSession().getAttribute("UID")==null){
            this.resultado="<script>$(document).ready(function(){$('body').css('opacity','0.1');alert('Su sesion ha estado inactiva por mas de 60 minutos, por seguridad debe ingresar nuevamente.');window.location='/Cohorte2/';$('body').css('opacity','1');});</script>";
            return "encontrado";
        }
        EntityManager em = emf.createEntityManager();
        this.setCodigo("");
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
            this.setCodigo(var[1].toString());
        }
        
        this.setUltimo_control_medico(this.siguienteNumero(this.getPacienteID(), "CM"));
        this.setUltimo_control_vhb(this.siguienteNumero(this.getPacienteID(), "CO"));
        this.setUltimo_enfermedad_asociada(this.siguienteNumero(this.getPacienteID(), "EA"));
        this.setUltimo_serologia(this.siguienteNumero(this.getPacienteID(), "SE"));
        
        setNumeroControl(this.getUltimo_enfermedad_asociada());
        this.setTipo("EA");
        
        this.seguimientoID = null;
        this.setFecha("");
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
        
        listadoEA();
        listadoSE();
        listadoCO();
        listadoCM();
        
       // this.setResultado("Seccion LAB cargada. " + this.getPacienteID());*/
        em.close();
        em = null;
        System.gc();
        return "encontrado";
    }
    
    public Integer siguienteNumero(Integer pacienteID, String tipo) {
        EntityManager em = emf.createEntityManager();
        Integer r=1;
        try {
            Query q = em.createNativeQuery("SELECT MAX(l.NumeroControl) AS NumeroControl FROM Seguimiento l WHERE l.PacienteID = "+pacienteID+" AND l.Tipo = '"+tipo+"'");
            r=((Integer) q.getSingleResult()) + 1;
        } catch (Exception e) {
          //return 1;
        }
        em.close();
        em = null;
        System.gc();
        return r;
    }
    
    public String guardarSeguimiento() {
        if(httpServletRequest.getSession()==null||httpServletRequest.getSession().getAttribute("UID")==null){
            this.resultado="<script>$(document).ready(function(){$('body').css('opacity','0.1');alert('Su sesion ha estado inactiva por mas de 60 minutos, por seguridad debe ingresar nuevamente.');window.location='/Cohorte2/';$('body').css('opacity','1');});</script>";
            return "guardado";
        }
        EntityManager em = emf.createEntityManager();
        String query = "";
        Integer t=0;
        if (this.pacienteID != null && this.pacienteID != 0 && this.getSeguimientoID() != null && this.getSeguimientoID() != 0) {
            query = "UPDATE Seguimiento SET "
                    + " PatologiaID="+(this.getPatologiaID().equals("") ? "NULL" : ""+this.getPatologiaID()+"" )+ ", "
                    + " Examen="+(this.Examen.equals("") ? "NULL" : "'"+this.Examen+"'" )+ ", "
                    + " Tratamiento="+(this.Tratamiento.equals("") ? "NULL" : "'"+this.Tratamiento+"'" )+ ", "
                    + " CVHepatitis="+(this.CVHepatitis.equals("") ? "NULL" : ""+this.CVHepatitis+"" )+ ", "
                    + " Antigeno="+(this.Antigeno.equals("") ? "NULL" : "'"+this.Antigeno+"'" )+ ", "
                    //+ " FechaControl=" +(this.FechaControl.equals("") ? "NULL" : "'"+util.fechaSQL(this.FechaControl)+"'" )+ ","
                    + " FechaControl=" +(this.FechaControl.equals("") ? "NULL" : "'"+this.FechaControl+"'" )+ ","
                    + " Observacion="+(this.Observaciones.equals("") ? "NULL" : "'"+this.Observaciones+"'" )+ ", "
                    //+ " Fecha=" +(this.Fecha.equals("") ? "NULL" : "'"+util.fechaSQL(this.Fecha)+"'" )+ ""
                    + " Fecha=" +(this.Fecha.equals("") ? "NULL" : "'"+this.Fecha+"'" )+ ""
                    + " WHERE PacienteID = " + this.pacienteID + " AND ID = " + this.getSeguimientoID() + " LIMIT 1";
            t=1;
        } else {
            if (this.Tipo == null || this.Tipo.equals("")) {
                this.setResultado("Tipo invalido");
                return "guardado";
            }
            query = "INSERT INTO Seguimiento "
                    + "(PacienteID, NumeroControl, Tipo, PatologiaID, Examen, Tratamiento, CVHepatitis, Antigeno, Observacion, FechaControl, Fecha) "
                    + "VALUES "
                    + "("
                    + ""+ this.pacienteID+","
                    + ""+ this.NumeroControl+","
                    + "'"+ this.Tipo+"',"
                    + ""+ "" +(this.PatologiaID.equals("") ? "NULL" : ""+this.PatologiaID+"" )+ ", "
                    + ""+ "" +(this.Examen.equals("") ? "NULL" : "'"+this.Examen+"'" )+ ", "
                    + ""+ "" +(this.Tratamiento.equals("") ? "NULL" : "'"+this.Tratamiento+"'" )+ ", "
                    + ""+ "" +(this.CVHepatitis.equals("") ? "NULL" : ""+this.CVHepatitis+"" )+ ", "
                    + ""+ "" +(this.Antigeno.equals("") ? "NULL" : "'"+this.Antigeno+"'" )+ ", "
                    + ""+ "" +(this.Observaciones.equals("") ? "NULL" : "'"+this.Observaciones+"'" )+ ", "
                    //+ ""+ "" +(this.FechaControl.equals("") ? "NULL" : "'"+util.fechaSQL(this.FechaControl)+"'" )+ ","
                    + ""+ "" +(this.FechaControl.equals("") ? "NULL" : "'"+this.FechaControl+"'" )+ ","
                    //+ ""+ "" +(this.Fecha.equals("") ? "NULL" : "'"+util.fechaSQL(this.Fecha)+"'" )+ ""
                    + ""+ "" +(this.Fecha.equals("") ? "NULL" : "'"+this.Fecha+"'" )+ ""
                    + ");";
            t=2;
        }
        
        try {
            em.getTransaction().begin();    
            em.createNativeQuery(query).executeUpdate();
            em.getTransaction().commit();
            Date today = new Date();
            SimpleDateFormat fecha = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            if(httpServletRequest.getSession().getAttribute("UID")!=null){
                Integer uid=(Integer)httpServletRequest.getSession().getAttribute("UID");
                if(t==1){
                    // Fix registro de modificacion
                    em.getTransaction().begin();
                    em.createNativeQuery("INSERT INTO registro_modificaciones (usuario_id,identificador,seccion,accion,fecha,SID)VALUES("+uid+","+this.seguimientoID+",'seguimiento','A','"+fecha.format(today)+":00','"+httpServletRequest.getSession().getId()+"');").executeUpdate();
                    em.getTransaction().commit();
                }else if(t==2){
                    // Fix registro de modificacion
                     Query q = em.createNativeQuery("SELECT LAST_INSERT_ID()");
                    Long value = ((BigInteger) q.getSingleResult()).longValue();    
                    this.seguimientoID = value.intValue();
                    em.getTransaction().begin();
                    em.createNativeQuery("INSERT INTO registro_modificaciones (usuario_id,identificador,seccion,accion,fecha,SID)VALUES("+uid+","+this.seguimientoID+",'seguimiento','I','"+fecha.format(today)+":00','"+httpServletRequest.getSession().getId()+"');").executeUpdate();
                    em.getTransaction().commit();
                }
            }
            this.resultado = query;
        } catch (Exception e) {
            this.resultado = e.getMessage();
        }
        
        this.setUltimo_control_medico(this.siguienteNumero(this.getPacienteID(), "CM"));
        this.setUltimo_control_vhb(this.siguienteNumero(this.getPacienteID(), "CO"));
        this.setUltimo_enfermedad_asociada(this.siguienteNumero(this.getPacienteID(), "EA"));
        this.setUltimo_serologia(this.siguienteNumero(this.getPacienteID(), "SE"));
        setNumeroControl(this.getUltimo_enfermedad_asociada());
        this.Tipo = "EA";
        this.seguimientoID = null;
        this.Fecha = "";
        this.PatologiaID = "";
        this.Examen = "";
        this.Tratamiento = "";
        this.CVHepatitis = "";
        this.Antigeno = "";
        this.Observaciones = "";
        this.FechaControl = "";
        this.Glice = "";
        this.Peso = "";
        this.Hematocrito = "";
        this.Gpt = "";
        this.Got = "";
        
        listadoEA();
        listadoSE();
        listadoCO();
        listadoCM();
        em.close();
        em = null;
        System.gc();
        
        return "guardado";
    }
    /*
     private List<Object[]> resultadosHiper;
    private List<Object[]> resultadosDisli;
    private List<Object[]> resultadosGlice;
    private List<Object[]> resultadosHema;*/
    
   //public List<Object[]> listadoEA() {
    public void listadoEA() {
        EntityManager em = emf.createEntityManager();
        String query = "SELECT ID,PacienteID,PatologiaID,Tipo,Examen,NumeroControl,Fecha,Tratamiento,CVHepatitis,Antigeno,FechaControl,Observacion FROM Seguimiento WHERE PacienteID = " + this.pacienteID + " AND Tipo = 'EA' ORDER BY NumeroControl DESC";
        //List<Object[]> list = (List<Object[]>)em.createNativeQuery(query).getResultList();
        resultados1 = (List<Object[]>)em.createNativeQuery(query).getResultList();
        em.close();
        em = null;
        System.gc();
        //return list;
    }
    
    public void listadoSE() {
        EntityManager em = emf.createEntityManager();
        String query = "SELECT ID,PacienteID,PatologiaID,Tipo,Examen,NumeroControl,Fecha,Tratamiento,CVHepatitis,Antigeno,FechaControl,Observacion FROM Seguimiento WHERE PacienteID = " + this.pacienteID + " AND Tipo = 'SE' ORDER BY NumeroControl DESC";
        resultados2 = (List<Object[]>)em.createNativeQuery(query).getResultList();
        //List<Object[]> list = (List<Object[]>)em.createNativeQuery(query).getResultList();
        em.close();
        em = null;
        System.gc();
        //return list;
    }
    
    public void listadoCO() {
        EntityManager em = emf.createEntityManager();
        String query = "SELECT ID,PacienteID,PatologiaID,Tipo,Examen,NumeroControl,Fecha,Tratamiento,CVHepatitis,Antigeno,FechaControl,Observacion FROM Seguimiento WHERE PacienteID = " + this.pacienteID + " AND Tipo = 'CO' ORDER BY NumeroControl DESC";
        //List<Object[]> list = (List<Object[]>)em.createNativeQuery(query).getResultList();
        resultados3 = (List<Object[]>)em.createNativeQuery(query).getResultList();
        em.close();
        em = null;
        System.gc();
        //return list;
    }
    
    public void listadoCM() {
        EntityManager em = emf.createEntityManager();
        String query = "SELECT ID,PacienteID,PatologiaID,Tipo,Examen,NumeroControl,Fecha,Tratamiento,CVHepatitis,Antigeno,FechaControl,Observacion FROM Seguimiento WHERE PacienteID = " + this.pacienteID + " AND Tipo = 'CM' ORDER BY NumeroControl DESC";
        //List<Object[]> list = (List<Object[]>)em.createNativeQuery(query).getResultList();
        resultados4 = (List<Object[]>)em.createNativeQuery(query).getResultList();
        em.close();
        em = null;
        System.gc();
        //return list;
    }
    
    public String eliminar() {
        EntityManager em = emf.createEntityManager();
        //this.codigo = "";
        em.getTransaction().begin(); 
        String query = "DELETE FROM Seguimiento WHERE ID = " + this.getSeguimientoID() + " AND PacienteID = " + this.pacienteID + " LIMIT 1;";
        em.createNativeQuery(query).executeUpdate();
        em.getTransaction().commit(); 
        this.resultado = "Seg. Eliminado "+this.getSeguimientoID();
        this.setUltimo_control_medico(this.siguienteNumero(this.getPacienteID(), "CM"));
        this.setUltimo_control_vhb(this.siguienteNumero(this.getPacienteID(), "CO"));
        this.setUltimo_enfermedad_asociada(this.siguienteNumero(this.getPacienteID(), "EA"));
        this.setUltimo_serologia(this.siguienteNumero(this.getPacienteID(), "SE"));
        setNumeroControl(this.getUltimo_enfermedad_asociada());
        this.Tipo = "EA";
        this.seguimientoID = null;
        this.Fecha = "";
        this.PatologiaID = "";
        this.Examen = "";
        this.Tratamiento = "";
        this.CVHepatitis = "";
        this.Antigeno = "";
        this.Observaciones = "";
        this.FechaControl = "";
        
        listadoEA();
        listadoSE();
        listadoCO();
        listadoCM();
        
        em.close();
        em = null;
        System.gc();
        return "eliminado";
    }
    
    public String modificar() {
        EntityManager em = emf.createEntityManager();
       //                      0         1          2          3        4           5           6         7             8       9         10      11    12        13       14   15   16
        String query = "SELECT ID, PacienteID, NumeroControl, Tipo, Fecha, PatologiaID, Examen, Tratamiento, CVHepatitis, Antigeno, Observacion, FechaControl "
                + "FROM Seguimiento "
                + "WHERE ID = " + this.seguimientoID + " AND PacienteID = " + this.pacienteID + ";";
        List<Object[]> list = (List<Object[]>)em.createNativeQuery(query).getResultList();
        for (Object[] var : list) {
            //String[] tmp = var[4].toString().split("-");
            
            this.NumeroControl = Integer.parseInt(var[2].toString());
            this.pacienteID = Integer.parseInt(var[1].toString());
            this.seguimientoID = Integer.parseInt(var[0].toString());
            this.Tipo = var[3].toString();
            if (var[4] != null && var[4].toString() != null) {
                this.Fecha=var[4].toString();
            }
            this.PatologiaID = var[5] != null && var[5].toString() != null ? var[5].toString():"";
            this.Examen = var[6] != null && var[6].toString() != null ? var[6].toString():"";
            this.Tratamiento = var[7] != null && var[7].toString() != null ? var[7].toString():"";
            this.CVHepatitis = var[8] != null && var[8].toString() != null ? var[8].toString():"";
            this.Antigeno = var[9] != null && var[9].toString() != null ? var[9].toString():"";
            this.Observaciones = var[10] != null && var[10].toString() != null ? var[10].toString():"";
            if (var[11] != null && var[11].toString() != null) {
                //String[] tmp2 = var[11].toString().split("-");
                //this.FechaControl = tmp2[2]+"/"+tmp2[1]+"/"+tmp2[0];
                this.FechaControl = var[11].toString();
            } else {
                this.FechaControl = "";
            }
            break;
        }
        
        listadoEA();
        listadoSE();
        listadoCO();
        listadoCM();
        
        em.close();
        em = null;
        System.gc();
        return "encontrado";
    }
    /*
    SE
            CO
            CM*/

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
     * @return the ultimo_enfermedad_asociada
     */
    public Integer getUltimo_enfermedad_asociada() {
        return ultimo_enfermedad_asociada;
    }

    /**
     * @param ultimo_enfermedad_asociada the ultimo_enfermedad_asociada to set
     */
    public void setUltimo_enfermedad_asociada(Integer ultimo_enfermedad_asociada) {
        this.ultimo_enfermedad_asociada = ultimo_enfermedad_asociada;
    }

    /**
     * @return the ultimo_serologia
     */
    public Integer getUltimo_serologia() {
        return ultimo_serologia;
    }

    /**
     * @param ultimo_serologia the ultimo_serologia to set
     */
    public void setUltimo_serologia(Integer ultimo_serologia) {
        this.ultimo_serologia = ultimo_serologia;
    }

    /**
     * @return the ultimo_control_vhb
     */
    public Integer getUltimo_control_vhb() {
        return ultimo_control_vhb;
    }

    /**
     * @param ultimo_control_vhb the ultimo_control_vhb to set
     */
    public void setUltimo_control_vhb(Integer ultimo_control_vhb) {
        this.ultimo_control_vhb = ultimo_control_vhb;
    }

    /**
     * @return the ultimo_control_medico
     */
    public Integer getUltimo_control_medico() {
        return ultimo_control_medico;
    }

    /**
     * @param ultimo_control_medico the ultimo_control_medico to set
     */
    public void setUltimo_control_medico(Integer ultimo_control_medico) {
        this.ultimo_control_medico = ultimo_control_medico;
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
     * @return the Tratamiento
     */
    public String getTratamiento() {
        return Tratamiento;
    }

    /**
     * @param Tratamiento the Tratamiento to set
     */
    public void setTratamiento(String Tratamiento) {
        this.Tratamiento = Tratamiento;
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
     * @return the seguimientoID
     */
    public Integer getSeguimientoID() {
        return seguimientoID;
    }

    /**
     * @param seguimientoID the seguimientoID to set
     */
    public void setSeguimientoID(Integer seguimientoID) {
        this.seguimientoID = seguimientoID;
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
     * @return the CVHepatitis
     */
    public String getCVHepatitis() {
        return CVHepatitis;
    }

    /**
     * @param CVHepatitis the CVHepatitis to set
     */
    public void setCVHepatitis(String CVHepatitis) {
        this.CVHepatitis = CVHepatitis;
    }

    /**
     * @return the FechaControl
     */
    public String getFechaControl() {
        return FechaControl;
    }

    /**
     * @param FechaControl the FechaControl to set
     */
    public void setFechaControl(String FechaControl) {
        this.FechaControl = FechaControl;
    }

    /**
     * @return the Antigeno
     */
    public String getAntigeno() {
        return Antigeno;
    }

    /**
     * @param Antigeno the Antigeno to set
     */
    public void setAntigeno(String Antigeno) {
        this.Antigeno = Antigeno;
    }

    /**
     * @return the Examen
     */
    public String getExamen() {
        return Examen;
    }

    /**
     * @param Examen the Examen to set
     */
    public void setExamen(String Examen) {
        this.Examen = Examen;
    }

    /**
     * @return the Patologia
     */
    public String getPatologia() {
        return Patologia;
    }

    /**
     * @param Patologia the Patologia to set
     */
    public void setPatologia(String Patologia) {
        this.Patologia = Patologia;
    }

    /**
     * @return the PatologiaID
     */
    public String getPatologiaID() {
        return PatologiaID;
    }

    /**
     * @param PatologiaID the PatologiaID to set
     */
    public void setPatologiaID(String PatologiaID) {
        this.PatologiaID = PatologiaID;
    }

    /**
     * @return the resultados1
     */
    public List<Object[]> getResultados1() {
        return resultados1;
    }

    /**
     * @param resultados1 the resultados1 to set
     */
    public void setResultados1(List<Object[]> resultados1) {
        this.resultados1 = resultados1;
    }

    /**
     * @return the resultados2
     */
    public List<Object[]> getResultados2() {
        return resultados2;
    }

    /**
     * @param resultados2 the resultados2 to set
     */
    public void setResultados2(List<Object[]> resultados2) {
        this.resultados2 = resultados2;
    }

    /**
     * @return the resultados3
     */
    public List<Object[]> getResultados3() {
        return resultados3;
    }

    /**
     * @param resultados3 the resultados3 to set
     */
    public void setResultados3(List<Object[]> resultados3) {
        this.resultados3 = resultados3;
    }

    /**
     * @return the resultados4
     */
    public List<Object[]> getResultados4() {
        return resultados4;
    }

    /**
     * @param resultados4 the resultados4 to set
     */
    public void setResultados4(List<Object[]> resultados4) {
        this.resultados4 = resultados4;
    }
    
}
