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
public class ControlesBean {
    private String resultado = "";
    private List<Object[]> resultados;
    private List<Object[]> resultadosCD4;
    private List<Object[]> resultadosCV;
    private Integer PacienteID;
    private Integer ControlID;
    
    private Usuario logueado;
    private final HttpServletRequest httpServletRequest;
    private final FacesContext faceContext;
    
    // Varibles de control base de datos
    private Integer NumeroControl;
    private String Tipo;
    private Integer ResultadoControl;
    private String Fecha;
    private String PCD4;
    private String Logaritmo;
    private Util util = new Util();
    private String codigo = "";
    private String Observaciones = "";
    
    private Integer ultimocd4;
    private Integer ultimocv;
    
    
    @PersistenceUnit 
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("Cohorte2PU");; 
   
   //@PersistenceContext
   /// EntityManager em = emf.createEntityManager();
    /**
     * Creates a new instance of ControlesBean
     */
    public ControlesBean() {
        this.resultado = "";
        this.codigo = "";
        faceContext=FacesContext.getCurrentInstance();
        httpServletRequest=(HttpServletRequest)faceContext.getExternalContext().getRequest();
        if(httpServletRequest.getSession().getAttribute("Usuario") != null)
        {
            logueado = (Usuario)httpServletRequest.getSession().getAttribute("Usuario");
        }
    }
    
    public String guardarControl() {
        if(httpServletRequest.getSession()==null||httpServletRequest.getSession().getAttribute("UID")==null){
            this.resultado="<script>$(document).ready(function(){$('body').css('opacity','0.1');alert('Su sesion ha estado inactiva por mas de 60 minutos, por seguridad debe ingresar nuevamente.');window.location='/Cohorte2/';$('body').css('opacity','1');});</script>";
            return "guardado";
        }
        EntityManager em = emf.createEntityManager();
        String query = "";
        Integer t=0;
        if (this.PacienteID != null && this.PacienteID != 0 && this.ControlID != null && this.ControlID != 0) {
            // Actualizar
            if (this.Logaritmo.equals("")) {
                this.Logaritmo = "NULL";
            }
            
            if (this.PCD4.equals("")) {
                this.PCD4 = "NULL";
            }
            t=1;
            query = "UPDATE Control SET "
                    + "Resultado="+ this.ResultadoControl+","
                    //+ "Fecha="+ ""+ "" +(this.getFecha().equals("") ? "NULL" : "'"+getUtil().fechaSQL(this.getFecha())+"'" )+ ", "
                    + "Fecha="+ ""+ "" +(this.getFecha().equals("") ? "NULL" : "'"+this.getFecha()+"'" )+ ", "
                    + "PCD4="+ this.PCD4+","
                    + "Logaritmo="+ this.Logaritmo+", "
                    + "Observacion = '"+this.Observaciones+"' "
                    + "WHERE PacienteID = " + this.PacienteID + " AND ID = " + this.ControlID;
        } else {
            if (this.Tipo == null || this.Tipo.equals("")) {
                this.resultado = "Tipo invalido";
                return "guardado";
            }
            // Crear
            this.NumeroControl = siguienteNumero(this.PacienteID, this.Tipo);
            if (this.Logaritmo.equals("")) {
                this.Logaritmo = "NULL";
            }
            
            if (this.PCD4.equals("")) {
                this.PCD4 = "NULL";
            }
            t=2;
            query = "INSERT INTO Control (PacienteID, NumeroControl, Tipo, Resultado, Fecha, PCD4, Logaritmo, Observacion) VALUES "
                    + "("
                    + ""+ this.PacienteID+","
                    + ""+ this.NumeroControl+","
                    + "'"+ this.Tipo+"',"
                    + ""+ this.ResultadoControl+","
                    //+ ""+ "" +(this.getFecha().equals("") ? "NULL" : "'"+getUtil().fechaSQL(this.getFecha())+"'" )+ ", "
                    + ""+ "" +(this.getFecha().equals("") ? "NULL" : "'"+this.getFecha()+"'" )+ ", "
                    + ""+ this.PCD4+","
                    + ""+ this.Logaritmo+","
                    + "'"+this.Observaciones+"'"
                    + ");";
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
                    em.createNativeQuery("INSERT INTO registro_modificaciones (usuario_id,identificador,seccion,accion,fecha,SID)VALUES("+uid+","+this.ControlID+",'controles','A','"+fecha.format(today)+":00','"+httpServletRequest.getSession().getId()+"');").executeUpdate();
                    em.getTransaction().commit();
                }else if(t==2){
                    // Fix registro de modificacion
                     Query q = em.createNativeQuery("SELECT LAST_INSERT_ID()");
                    Long value = ((BigInteger) q.getSingleResult()).longValue();    
                    this.ControlID = value.intValue();
                    em.getTransaction().begin();
                    em.createNativeQuery("INSERT INTO registro_modificaciones (usuario_id,identificador,seccion,accion,fecha,SID)VALUES("+uid+","+this.ControlID+",'controles','I','"+fecha.format(today)+":00','"+httpServletRequest.getSession().getId()+"');").executeUpdate();
                    em.getTransaction().commit();
                }
            }
            this.resultado = query;
        } catch (Exception e) {
            this.resultado = e.getMessage();
        }
        
        ControlID = null;
        ResultadoControl = null;
        this.Tipo = "CD";
        Fecha = ""; // Fecha
        PCD4 = "";
        Logaritmo = "";
        Observaciones = "";
        
        this.setUltimocd4(this.siguienteNumero(this.PacienteID, "CD"));
        this.setUltimocv(this.siguienteNumero(this.PacienteID, "CV"));
        
        this.NumeroControl = ultimocd4;
        em.close();
        em=null;
        listadoControlesCD4();
        listadoControlesCV();
        
        return "guardado";
    }
    
    public String cargarSeccionControles() {
        if(httpServletRequest.getSession()==null||httpServletRequest.getSession().getAttribute("UID")==null){
            this.resultado="<script>$(document).ready(function(){$('body').css('opacity','0.1');alert('Su sesion ha estado inactiva por mas de 60 minutos, por seguridad debe ingresar nuevamente.');window.location='/Cohorte2/';$('body').css('opacity','1');});</script>";
            return "encontrado";
        }
        EntityManager em = emf.createEntityManager();
        this.codigo = "";
        String value = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("pacienteIDH");
        try {
            this.PacienteID = Integer.parseInt(value);
        } catch (Exception e) {
            this.PacienteID = 0;
        }
        
        String query = "SELECT p.ID, p.Codigo "
                + "FROM Paciente p "
                + "WHERE p.ID = " + this.getPacienteID() + ";";
        List<Object[]> list = (List<Object[]>)em.createNativeQuery(query).getResultList();
        for (Object[] var : list) {
            this.setCodigo(var[1].toString());
        }
        
        this.resultado = "paciente seleccionado: SELECT MAX(t.NumeroTar) FROM Terapia t WHERE t.PacienteID = " + this.PacienteID;
        
        ControlID = null;
        NumeroControl = this.siguienteNumero(this.PacienteID, "CD");
        ResultadoControl = null;
        this.Tipo = "CD";
        Fecha = ""; // Fecha
        PCD4 = "";
        Logaritmo = "";
        
        this.setUltimocd4(this.siguienteNumero(this.PacienteID, "CD"));
        this.setUltimocv(this.siguienteNumero(this.PacienteID, "CV"));
        em.close();
        em=null;
        listadoControlesCD4();
        listadoControlesCV();
        
        return "encontrado";
    }
    
    
    public String cargarSeccionTerapiaAgrupada() {
        EntityManager em = emf.createEntityManager();
        this.codigo = "";
        String value = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("pacienteIDH");
        try {
            this.PacienteID = Integer.parseInt(value);
        } catch (Exception e) {
            this.PacienteID = 0;
        }
        
        String query = "SELECT p.ID, p.Codigo "
                + "FROM Paciente p "
                + "WHERE p.ID = " + this.getPacienteID() + ";";
        List<Object[]> list = (List<Object[]>)em.createNativeQuery(query).getResultList();
        for (Object[] var : list) {
            this.setCodigo(var[1].toString());
        }
        
        this.resultado = "paciente seleccionado: SELECT MAX(t.NumeroTar) FROM Terapia t WHERE t.PacienteID = " + this.PacienteID;
        em.close();
        em=null;
        listadoControlesCD4();
        listadoControlesCV();
        
        return "encontrado";
    }
    
    public List<Object[]> listadoTerapias(String fechaControl) {
        EntityManager em = emf.createEntityManager();
        String query = "SELECT PacienteID, NumeroTar, Fecha, NoContinua, CausaTerminoID, RazonToxicidadID, FechaTermino, IFNULL(Geno, 'NO') Geno, GenoFecha, Tropismo, ID FROM Terapia t WHERE t.PacienteID = " + this.PacienteID + " AND Fecha <= '"+fechaControl+"' AND FechaTermino >= '"+fechaControl+"' ORDER BY NumeroTar DESC";
        List<Object[]> list = (List<Object[]>)em.createNativeQuery(query).getResultList();
        em.close();
        em = null;
        System.gc();
        return list;
    }
    
    public String generarFicha() {
        EntityManager em = emf.createEntityManager();
        String query = "SELECT p.ID, p.Codigo "
                + "FROM Paciente p "
                + "WHERE p.ID = " + this.getPacienteID() + ";";
        List<Object[]> list = (List<Object[]>)em.createNativeQuery(query).getResultList();
        for (Object[] var : list) {
            this.setCodigo(var[1].toString());
        }
        
        this.resultado = "paciente seleccionado: SELECT MAX(t.NumeroTar) FROM Terapia t WHERE t.PacienteID = " + this.PacienteID;
        
        ControlID = null;
        NumeroControl = this.siguienteNumero(this.PacienteID, "CD");
        ResultadoControl = null;
        this.Tipo = "CD";
        Fecha = ""; // Fecha
        PCD4 = "";
        Logaritmo = "";
        em.close();
        em=null;
        listadoControlesCD4();
        listadoControlesCV();
        
        return "encontrado";
    }
    
    public String generarFichaAgrupada() {
        EntityManager em = emf.createEntityManager();
        String query = "SELECT p.ID, p.Codigo "
                + "FROM Paciente p "
                + "WHERE p.ID = " + this.getPacienteID() + ";";
        List<Object[]> list = (List<Object[]>)em.createNativeQuery(query).getResultList();
        for (Object[] var : list) {
            this.setCodigo(var[1].toString());
        }
        
        this.resultado = "paciente seleccionado: SELECT MAX(t.NumeroTar) FROM Terapia t WHERE t.PacienteID = " + this.PacienteID;
        
        ControlID = null;
        NumeroControl = this.siguienteNumero(this.PacienteID, "CD");
        ResultadoControl = null;
        this.Tipo = "CD";
        Fecha = ""; // Fecha
        PCD4 = "";
        Logaritmo = "";
        em.close();
        em=null;
        listadoControlesCD4();
        listadoControlesCV();
        
        return "encontrado";
    }
    
    public Integer siguienteNumero(Integer pacienteID, String tipo) {
        EntityManager em = emf.createEntityManager();
        Integer r=1;
        try {
            Query q = em.createNativeQuery("SELECT MAX(c.NumeroControl) AS NumeroControl FROM Control c WHERE c.PacienteID = "+pacienteID+" AND c.Tipo = '"+tipo+"'");
            r=((Integer) q.getSingleResult()) + 1;
        } catch (Exception e) {
         // return 1;
        }
        em.close();
        em = null;
        System.gc();
        return r;
    }
    
    
    public void listadoControlesCD4() {
        EntityManager em = emf.createEntityManager();
        String query = "SELECT ID, PacienteID, NumeroControl, Fecha, Resultado, PCD4, Tipo, Observacion FROM Control WHERE PacienteID = " + this.PacienteID + " AND Tipo = 'CD' ORDER BY NumeroControl DESC";
        setResultadosCD4((List<Object[]>)em.createNativeQuery(query).getResultList());
        em.close();
        em=null;
    }
    
    public void listadoControlesCV() {
        EntityManager em = emf.createEntityManager();
        String query = "SELECT ID, PacienteID, NumeroControl, Fecha, Resultado, Logaritmo, Tipo, Observacion FROM Control WHERE PacienteID = " + this.PacienteID + " AND Tipo = 'CV' ORDER BY NumeroControl DESC";
        setResultadosCV((List<Object[]>)em.createNativeQuery(query).getResultList());
        em.close();
        em=null;
    }
    
    public String eliminarControl() {
        if(httpServletRequest.getSession()==null||httpServletRequest.getSession().getAttribute("UID")==null){
            this.resultado="<script>$(document).ready(function(){$('body').css('opacity','0.1');alert('Su sesion ha estado inactiva por mas de 60 minutos, por seguridad debe ingresar nuevamente.');window.location='/Cohorte2/';$('body').css('opacity','1');});</script>";
            return "eliminado";
        }
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin(); 
        
        String query = "DELETE FROM Control WHERE ID = " + this.ControlID + " AND PacienteID = " + this.PacienteID;
        em.createNativeQuery(query).executeUpdate();
        
        em.getTransaction().commit();
        
        ControlID = null;
        NumeroControl = this.siguienteNumero(this.PacienteID, "CD");
        ResultadoControl = null;
        this.Tipo = "CD";
        Fecha = ""; // Fecha
        PCD4 = "";
        Logaritmo = "";
        em.close();
        em=null;
        listadoControlesCD4();
        listadoControlesCV();
        
        return "eliminado";
    }
    
    public String modificarControl() throws ParseException {
        if(httpServletRequest.getSession()==null||httpServletRequest.getSession().getAttribute("UID")==null){
            this.resultado="<script>$(document).ready(function(){$('body').css('opacity','0.1');alert('Su sesion ha estado inactiva por mas de 60 minutos, por seguridad debe ingresar nuevamente.');window.location='/Cohorte2/';$('body').css('opacity','1');});</script>";
            return "encontrado";
        }
        EntityManager em = emf.createEntityManager();
        String query = "SELECT ID, PacienteID, NumeroControl, Fecha, Resultado, Logaritmo, Tipo, PCD4, Observacion "
                + "FROM Control "
                + "WHERE ID = " + this.ControlID + " AND PacienteID = " + this.PacienteID + ";";
        List<Object[]> list = (List<Object[]>)em.createNativeQuery(query).getResultList();
        for (Object[] var : list) {
            this.NumeroControl = Integer.parseInt(var[2].toString());
            this.PacienteID = Integer.parseInt(var[1].toString());
            this.ControlID = Integer.parseInt(var[0].toString());
            
            if (var[3] != null && var[3].toString() != null) {
                //this.Fecha = util.fechaForm(var[3].toString());
                this.Fecha = var[3].toString();
            }
            
            if (var[4] != null && var[4].toString() != null) {
                this.ResultadoControl = Integer.parseInt(var[4].toString());
            }
            
            if (var[5] != null && var[5].toString() != null) {
                this.Logaritmo = var[5].toString();
            }
            
            if (var[6] != null && var[6].toString() != null) {
                this.Tipo = var[6].toString();
            }
            
            if (var[7] != null && var[7].toString() != null) {
                this.PCD4 = var[7].toString();
            }
            
            if (var[8] != null && var[8].toString() != null) {
                this.Observaciones = var[8].toString();
            }
        }
        em.close();
        em=null;
        listadoControlesCD4();
        listadoControlesCV();
        
        return "encontrado";
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
     * @return the PacienteID
     */
    public Integer getPacienteID() {
        return PacienteID;
    }

    /**
     * @param PacienteID the PacienteID to set
     */
    public void setPacienteID(Integer PacienteID) {
        this.PacienteID = PacienteID;
    }

    /**
     * @return the ControlID
     */
    public Integer getControlID() {
        return ControlID;
    }

    /**
     * @param ControlID the ControlID to set
     */
    public void setControlID(Integer ControlID) {
        this.ControlID = ControlID;
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
     * @return the PCD4
     */
    public String getPCD4() {
        return PCD4;
    }

    /**
     * @param PCD4 the PCD4 to set
     */
    public void setPCD4(String PCD4) {
        this.PCD4 = PCD4;
    }

    /**
     * @return the Logaritmo
     */
    public String getLogaritmo() {
        return Logaritmo;
    }

    /**
     * @param Logaritmo the Logaritmo to set
     */
    public void setLogaritmo(String Logaritmo) {
        this.Logaritmo = Logaritmo;
    }

    /**
     * @return the ResultadoControl
     */
    public Integer getResultadoControl() {
        return ResultadoControl;
    }

    /**
     * @param ResultadoControl the ResultadoControl to set
     */
    public void setResultadoControl(Integer ResultadoControl) {
        this.ResultadoControl = ResultadoControl;
    }

    /**
     * @return the resultadosCD4
     */
    public List<Object[]> getResultadosCD4() {
        return resultadosCD4;
    }

    /**
     * @param resultadosCD4 the resultadosCD4 to set
     */
    public void setResultadosCD4(List<Object[]> resultadosCD4) {
        this.resultadosCD4 = resultadosCD4;
    }

    /**
     * @return the resultadosCV
     */
    public List<Object[]> getResultadosCV() {
        return resultadosCV;
    }

    /**
     * @param resultadosCV the resultadosCV to set
     */
    public void setResultadosCV(List<Object[]> resultadosCV) {
        this.resultadosCV = resultadosCV;
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
     * @return the ultimocd4
     */
    public Integer getUltimocd4() {
        return ultimocd4;
    }

    /**
     * @param ultimocd4 the ultimocd4 to set
     */
    public void setUltimocd4(Integer ultimocd4) {
        this.ultimocd4 = ultimocd4;
    }

    /**
     * @return the ultimocv
     */
    public Integer getUltimocv() {
        return ultimocv;
    }

    /**
     * @param ultimocv the ultimocv to set
     */
    public void setUltimocv(Integer ultimocv) {
        this.ultimocv = ultimocv;
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
