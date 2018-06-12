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
public class eventoBean {
    private Util util = new Util();
    private String resultado = "";
    private List<Object[]> resultados;
    private List<Object[]> resultadosEventos;
    private List<Object[]> resultadosMuerte;
    private Integer pacienteID;
    private Integer muerteID;
    private Integer eventoID;
    
    // Variables de Eventos
    private Integer tipoEventoID;
    private Integer nEvento;
    private String fecha;
    private String tratamiento;    
    private String codigo;
    
    // Variables de Evento Muerte 
    private Integer causaMuerteID;
    private String fechaMuerte;
    private String observacion;
    private String Observaciones = "";
    
    private Usuario logueado;
    private final HttpServletRequest httpServletRequest;
    private final FacesContext faceContext;
    
    @PersistenceUnit 
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("Cohorte2PU");; 
   
    @PersistenceContext
    EntityManager em = emf.createEntityManager();

    /**
     * Creates a new instance of eventoBean
     */
    public eventoBean() {
        this.resultado = "";
        faceContext=FacesContext.getCurrentInstance();
        httpServletRequest=(HttpServletRequest)faceContext.getExternalContext().getRequest();
        if(httpServletRequest.getSession().getAttribute("Usuario") != null)
        {
            logueado = (Usuario)httpServletRequest.getSession().getAttribute("Usuario");
        }
    }
    
    public String cargarSeccionEventos() {
        if(httpServletRequest.getSession()==null||httpServletRequest.getSession().getAttribute("UID")==null){
            this.resultado="<script>$(document).ready(function(){$('body').css('opacity','0.1');alert('Su sesion ha estado inactiva por mas de 60 minutos, por seguridad debe ingresar nuevamente.');window.location='/Cohorte2/';$('body').css('opacity','1');});</script>";
            return "encontrado";
        }
        String value = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("pacienteIDH");
        try {
            this.pacienteID = Integer.parseInt(value);
        } catch (Exception e) {
            this.pacienteID = 0;
        }
        
        String query = "SELECT ID, Codigo "
                + "FROM Paciente "
                + "WHERE ID = " + this.getPacienteID() + ";";
        List<Object[]> list = (List<Object[]>)em.createNativeQuery(query).getResultList();
        for (Object[] var : list) {
            this.codigo = var[1].toString();
        }
        
        this.resultado = "paciente seleccionado: SELECT MAX(t.NumeroTar) FROM Terapia t WHERE t.PacienteID = " + this.pacienteID;
        
        // Variables de Eventos
        tipoEventoID = null;
        this.eventoID = null;
        nEvento = siguienteNumero();
        fecha = "";
        tratamiento = "";    
        Observaciones = "";

        // Variables de Evento Muerte 
        causaMuerteID = null;
        fechaMuerte = "";
        observacion = "";
        
    
        listadoEventos();
        listadoMuerte();
        
        return "encontrado";
    }
    
     public String generarFicha() {
        String query = "SELECT ID, Codigo "
                + "FROM Paciente "
                + "WHERE ID = " + this.getPacienteID() + ";";
        List<Object[]> list = (List<Object[]>)em.createNativeQuery(query).getResultList();
        for (Object[] var : list) {
            this.codigo = var[1].toString();
        }
        
        this.resultado = "paciente seleccionado: SELECT MAX(t.NumeroTar) FROM Terapia t WHERE t.PacienteID = " + this.pacienteID;
        
        // Variables de Eventos
        tipoEventoID = null;
        this.eventoID = null;
        nEvento = siguienteNumero();
        fecha = "";
        tratamiento = "";    

        // Variables de Evento Muerte 
        causaMuerteID = null;
        fechaMuerte = "";
        observacion = "";
    
        listadoEventos();
        listadoMuerte();
        
        return "encontrado";
    }
    
    public void listadoEventos() {
        String query = "SELECT ID, PacienteID, NEvento, Fecha, EventoID, Tratamiento, Observacion FROM PacienteEvento WHERE PacienteID = " + this.pacienteID + " ORDER BY NEvento DESC";
        this.resultadosEventos = (List<Object[]>)em.createNativeQuery(query).getResultList();
    }
    
    public void listadoMuerte() {
        String query = "SELECT ID, PacienteID, CausaMuerteID, Fecha, Observacion FROM Muerte WHERE PacienteID = " + this.pacienteID;
        this.resultadosMuerte = ((List<Object[]>)em.createNativeQuery(query).getResultList());
    }
    
    public Integer siguienteNumero() {
        try {
            Query q = em.createNativeQuery("SELECT MAX(p.NEvento) AS NEvento FROM PacienteEvento p WHERE p.PacienteID = " + pacienteID);
            return ((Integer) q.getSingleResult()) + 1;
        } catch (Exception e) {
          return 1;
        }
    }
    
    public String guardarEvento() {
        if(httpServletRequest.getSession()==null||httpServletRequest.getSession().getAttribute("UID")==null){
            this.resultado="<script>$(document).ready(function(){$('body').css('opacity','0.1');alert('Su sesion ha estado inactiva por mas de 60 minutos, por seguridad debe ingresar nuevamente.');window.location='/Cohorte2/';$('body').css('opacity','1');});</script>";
            return "guardado";
        }
        String query = "";
        Integer t=0;
        if (this.pacienteID != null && this.pacienteID != 0 && this.eventoID != null && this.eventoID != 0) {
            query = "UPDATE PacienteEvento SET "
                    + "EventoID = "+ this.tipoEventoID+","
                    //+ "Fecha = "+(this.fecha.equals("") ? "NULL" : "'"+util.fechaSQL(this.fecha)+"'" )+ ", "
                    + "Fecha = "+(this.fecha.equals("") ? "NULL" : "'"+this.fecha+"'" )+ ", "
                    + "Tratamiento = '"+ this.tratamiento+"', "
                    + "Observacion = '"+this.Observaciones+"' "
                    + "WHERE ID = " + this.eventoID + " AND PacienteID = " + this.pacienteID + ";";
            t=1;
        } else {
            t=2;
            query = "INSERT INTO PacienteEvento "
                    + "(EventoID, PacienteID, NEvento, Fecha, Tratamiento, Observacion) VALUES "
                    + "("
                    + this.tipoEventoID+","
                    + this.pacienteID+","
                    + this.nEvento+","
                    //+ "" +(this.fecha.equals("") ? "NULL" : "'"+util.fechaSQL(this.fecha)+"'" )+ ", "
                    + "" +(this.fecha.equals("") ? "NULL" : "'"+this.fecha+"'" )+ ", "
                    + "'"+this.tratamiento+"',"
                    + "'"+this.Observaciones+"'"
                    + ")";
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
                    em.createNativeQuery("INSERT INTO registro_modificaciones (usuario_id,identificador,seccion,accion,fecha,SID)VALUES("+uid+","+this.eventoID+",'evento','A','"+fecha.format(today)+":00','"+httpServletRequest.getSession().getId()+"');").executeUpdate();
                    em.getTransaction().commit();
                }else if(t==2){
                    // Fix registro de modificacion
                     Query q = em.createNativeQuery("SELECT LAST_INSERT_ID()");
                    Long value = ((BigInteger) q.getSingleResult()).longValue();    
                    this.eventoID = value.intValue();
                    em.getTransaction().begin();
                    em.createNativeQuery("INSERT INTO registro_modificaciones (usuario_id,identificador,seccion,accion,fecha,SID)VALUES("+uid+","+this.eventoID+",'evento','I','"+fecha.format(today)+":00','"+httpServletRequest.getSession().getId()+"');").executeUpdate();
                    em.getTransaction().commit();
                }
            }

            this.resultado = query;
        } catch (Exception e) {
            this.resultado = e.getMessage();
        }
        // Variables de Eventos
        this.eventoID = null;
        tipoEventoID = null;
        nEvento = siguienteNumero();
        fecha = "";
        tratamiento = "";  
        Observaciones = "";

        // Variables de Evento Muerte 
        this.muerteID = null;
        causaMuerteID = null;
        fechaMuerte = "";
        observacion = "";
    
        listadoEventos();
        listadoMuerte();
        
        return "guardado";
    }
    
    public String guardarMuerte() {
        String query = "";
        Integer t=0;
        if (this.pacienteID != null && this.pacienteID != 0 && this.muerteID != null && this.muerteID != 0) {
            t=1;
            query = "UPDATE Muerte SET "
                    + "CausaMuerteID = "+ this.causaMuerteID+","
                    //+ "Fecha = "+(this.fechaMuerte.equals("") ? "NULL" : "'"+util.fechaSQL(this.fechaMuerte)+"'" )+ ", "
                    + "Fecha = "+(this.fechaMuerte.equals("") ? "NULL" : "'"+this.fechaMuerte+"'" )+ ", "
                    + "Observacion = '"+ this.observacion+"' "
                    + "WHERE ID = " + this.muerteID + " AND PacienteID = " + this.pacienteID + ";";
        } else {
            t=2;
            query = "INSERT INTO Muerte "
                    + "(CausaMuerteID, PacienteID, Fecha, Observacion) VALUES "
                    + "("
                    + this.causaMuerteID+","
                    + this.pacienteID+","
                    //+ "" +(this.fechaMuerte.equals("") ? "NULL" : "'"+util.fechaSQL(this.fechaMuerte)+"'" )+ ", "
                    + "" +(this.fechaMuerte.equals("") ? "NULL" : "'"+this.fechaMuerte+"'" )+ ", "
                    + "'"+this.observacion+"'"
                    + ")";
        }
        try {
            em.getTransaction().begin();    
            em.createNativeQuery(query).executeUpdate();
            em.getTransaction().commit();
            Date today = new Date();
            SimpleDateFormat fecha = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            if(t==1){
                // Fix registro de modificacion
                em.getTransaction().begin();
                em.createNativeQuery("INSERT INTO registro_modificaciones (usuario_id,identificador,seccion,accion,fecha)VALUES("+logueado.getId()+","+this.muerteID+",'muerte','A','"+fecha.format(today)+":00','"+httpServletRequest.getSession().getId()+"');").executeUpdate();
                em.getTransaction().commit();
            }else if(t==2){
                // Fix registro de modificacion
                 Query q = em.createNativeQuery("SELECT LAST_INSERT_ID()");
                Long value = ((BigInteger) q.getSingleResult()).longValue();    
                this.muerteID = value.intValue();
                em.getTransaction().begin();
                em.createNativeQuery("INSERT INTO registro_modificaciones (usuario_id,identificador,seccion,accion,fecha)VALUES("+logueado.getId()+","+this.muerteID+",'muerte','I','"+fecha.format(today)+":00','"+httpServletRequest.getSession().getId()+"');").executeUpdate();
                em.getTransaction().commit();
            }

            // Si se registra muerte, se cierran todas las terapias activas con la fecha de muerte ingresada.
            if(!this.fechaMuerte.equals("")){
                query="UPDATE Terapia SET FechaTermino='"+this.fechaMuerte+"' WHERE PacienteID="+this.pacienteID+" AND FechaTermino IS NULL;";
                em.getTransaction().begin();    
                em.createNativeQuery(query).executeUpdate();
                em.getTransaction().commit();
            }

            this.resultado = query;
        } catch (Exception e) {
            this.resultado = e.getMessage();
        }
        // Variables de Eventos
        this.eventoID = null;
        this.muerteID = null;
        tipoEventoID = null;
        nEvento = siguienteNumero();
        fecha = "";
        tratamiento = "";    

        // Variables de Evento Muerte 
        causaMuerteID = null;
        fechaMuerte = "";
        observacion = "";
    
        listadoEventos();
        listadoMuerte();
        
        return "guardado";
    }
    
    public String despliegueTratamiento(String var) {
        switch (var) {
            case "S":
                return "SI";
            case "N":
                return "NO";
        }
        return "SIN DATOS";
    }
    
    
    public String despliegueEvento(String var) {
        if (var != null && !var.equals("")) {
            Query q = em.createNativeQuery("SELECT Nombre FROM Evento WHERE ID = ?").setParameter(1, new Integer(var));
            return ((String) q.getSingleResult());
        }
        return "SIN DATOS";
    }
     
    public String despliegueCausa(String var) {
        if (var != null && !var.equals("")) {
            Query q = em.createNativeQuery("SELECT Nombre FROM CausaMuerte WHERE ID = ?").setParameter(1, new Integer(var));
            return ((String) q.getSingleResult());
        }
        return "SIN DATOS";
    }
    
    public Boolean muerte(String var) {
        if (var != null && !var.equals("")) {
            Query q = em.createNativeQuery("SELECT COUNT(*) AS Cantidad FROM Muerte WHERE PacienteID = ?").setParameter(1, new Integer(var));
            Long value = ((Long) q.getSingleResult());
            if (value > 0) {
                return true;
            }
        }
        return false;
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
     * @return the resultadosEventos
     */
    public List<Object[]> getResultadosEventos() {
        return resultadosEventos;
    }

    /**
     * @param resultadosEventos the resultadosEventos to set
     */
    public void setResultadosEventos(List<Object[]> resultadosEventos) {
        this.resultadosEventos = resultadosEventos;
    }

    /**
     * @return the resultadosMuerte
     */
    public List<Object[]> getResultadosMuerte() {
        return resultadosMuerte;
    }

    /**
     * @param resultadosMuerte the resultadosMuerte to set
     */
    public void setResultadosMuerte(List<Object[]> resultadosMuerte) {
        this.resultadosMuerte = resultadosMuerte;
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
     * @return the muertelID
     */
    public Integer getMuerteID() {
        return muerteID;
    }

    /**
     * @param muertelID the muertelID to set
     */
    public void setMuerteID(Integer muerteID) {
        this.muerteID = muerteID;
    }

    /**
     * @return the eventoID
     */
    public Integer getEventoID() {
        return eventoID;
    }

    /**
     * @param eventoID the eventoID to set
     */
    public void setEventoID(Integer eventoID) {
        this.eventoID = eventoID;
    }

    /**
     * @return the tipoEventoID
     */
    public Integer getTipoEventoID() {
        return tipoEventoID;
    }

    /**
     * @param tipoEventoID the tipoEventoID to set
     */
    public void setTipoEventoID(Integer tipoEventoID) {
        this.tipoEventoID = tipoEventoID;
    }

    /**
     * @return the nEvento
     */
    public Integer getNEvento() {
        return nEvento;
    }

    /**
     * @param nEvento the nEvento to set
     */
    public void setNEvento(Integer nEvento) {
        this.nEvento = nEvento;
    }

    /**
     * @return the fecha
     */
    public String getFecha() {
        return fecha;
    }

    /**
     * @param fecha the fecha to set
     */
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    /**
     * @return the tratamiento
     */
    public String getTratamiento() {
        return tratamiento;
    }

    /**
     * @param tratamiento the tratamiento to set
     */
    public void setTratamiento(String tratamiento) {
        this.tratamiento = tratamiento;
    }

    /**
     * @return the causaMuerteID
     */
    public Integer getCausaMuerteID() {
        return causaMuerteID;
    }

    /**
     * @param causaMuerteID the causaMuerteID to set
     */
    public void setCausaMuerteID(Integer causaMuerteID) {
        this.causaMuerteID = causaMuerteID;
    }

    /**
     * @return the fechaMuerte
     */
    public String getFechaMuerte() {
        return fechaMuerte;
    }

    /**
     * @param fechaMuerte the fechaMuerte to set
     */
    public void setFechaMuerte(String fechaMuerte) {
        this.fechaMuerte = fechaMuerte;
    }

    /**
     * @return the observacion
     */
    public String getObservacion() {
        return observacion;
    }

    /**
     * @param observacion the observacion to set
     */
    public void setObservacion(String observacion) {
        this.observacion = observacion;
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
    
}
