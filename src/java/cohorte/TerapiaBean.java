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
/*@ManagedBean
@RequestScoped*/
public class TerapiaBean {
    private Terapia terapia;
    
    // Datos Terapia
    private Integer TerapiaID;
    private Integer PacienteID;
    private Integer RazonToxicidadID;
    private Integer CausaTerminoID;
    private Integer NumeroTar;
    private String Fecha = ""; // Fecha
    private String NoContinua = "";
    private String FechaTermino = ""; // Fecha
    private String[] Geno = new String[1];
    private String GenoFecha = ""; // Fecha
    private String GenoObs = "";
    private String Tropismo = "";
    private String Observaciones = "";
    
    private Usuario logueado;
    private final HttpServletRequest httpServletRequest;
    private final FacesContext faceContext;
    
    // 6 Drogas, cantidad definida por requerimientos
    private Integer droga1;
    private Integer droga2;
    private Integer droga3;
    private Integer droga4;
    private Integer droga5;
    private Integer droga6;
    private String codigo = "";
    
    private String resultado = "";
    private List<Object[]> resultados;
    
    @PersistenceUnit 
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("Cohorte2PU");; 
   
    /*@PersistenceContext
    EntityManager em = emf.createEntityManager();
    */
    /**
     * Creates a new instance of PacienteBean
     */
    public TerapiaBean() {
        terapia = new Terapia();
        this.resultado = "";
        this.codigo = "";
        faceContext=FacesContext.getCurrentInstance();
        httpServletRequest=(HttpServletRequest)faceContext.getExternalContext().getRequest();
        if(httpServletRequest.getSession().getAttribute("Usuario") != null)
        {
            logueado = (Usuario)httpServletRequest.getSession().getAttribute("Usuario");
        }
    }
    
    public Terapia getTerapia() {
        return terapia;
    }
    
    public void setTerapia(Terapia terapia) {
        this.terapia = terapia;
    }
    
    public String guardarTerapia() {
        if(httpServletRequest.getSession()==null||httpServletRequest.getSession().getAttribute("UID")==null){
            this.resultado="<script>$(document).ready(function(){$('body').css('opacity','0.1');alert('Su sesion ha estado inactiva por mas de 60 minutos, por seguridad debe ingresar nuevamente.');window.location='/Cohorte2/';$('body').css('opacity','1');});</script>";
            return "guardado";
        }
        this.resultado="";
        EntityManager em = emf.createEntityManager();
        String query = "";
        if (this.PacienteID != null && this.PacienteID != 0 && this.TerapiaID != null && this.TerapiaID != 0) {
            query = "UPDATE Terapia SET "
                    + "RazonToxicidadID = "+ this.RazonToxicidadID+","
                    + "CausaTerminoID = "+ this.CausaTerminoID+","
                    + "NumeroTar = "+ this.NumeroTar+","
                    //+ "Fecha = "+ "" +(this.getFecha().equals("") ? "NULL" : "'"+convertirFecha(this.getFecha())+"'" )+ ", "
                    + "Fecha = "+ "" +(this.getFecha().equals("") ? "NULL" : "'"+this.getFecha()+"'" )+ ", "
                    + "NoContinua = "+ "'"+this.NoContinua+"',"
                    //+ "FechaTermino = "+ "" +(this.getFechaTermino().equals("") ? "NULL" : "'"+convertirFecha(this.getFechaTermino())+"'" )+ ", "
                    + "FechaTermino = "+ "" +(this.getFechaTermino().equals("") ? "NULL" : "'"+this.getFechaTermino()+"'" )+ ", "
                    + "Geno ="+ "" +(this.getGeno().length == 1 && this.getGeno()[0].equals("SI") ? "'SI'" : "NULL")+", "
                    //+ "GenoFecha ="+(this.getGenoFecha().equals("") ? "NULL" : "'"+convertirFecha(this.getGenoFecha())+"'" )+ ", "
                    + "GenoFecha ="+(this.getGenoFecha().equals("") ? "NULL" : "'"+this.getGenoFecha()+"'" )+ ", "
                    + "GenoObs ='"+this.GenoObs+"',"
                    + "Tropismo ='"+this.Tropismo+"', "
                    + "Observacion = '"+this.Observaciones+"' "
                    + "WHERE ID = " + this.TerapiaID + ";";
            try{
                em.getTransaction().begin();    
                em.createNativeQuery(query).executeUpdate();
                em.getTransaction().commit();
                try{
                    Date today = new Date();
                    SimpleDateFormat fecha = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                    if(httpServletRequest.getSession().getAttribute("UID")!=null){
                        Integer uid=(Integer)httpServletRequest.getSession().getAttribute("UID");
                        em.getTransaction().begin();
                        em.createNativeQuery("INSERT INTO registro_modificaciones (usuario_id,identificador,seccion,accion,fecha,SID)VALUES("+uid+","+this.TerapiaID+",'terapia','A','"+fecha.format(today)+":00','"+httpServletRequest.getSession().getId()+"');").executeUpdate();
                        em.getTransaction().commit();
                    }
                }catch(Exception e){ }
            }catch(Exception e){
                this.resultado="Ocurrio un error al intentar actualizar TAR, informe el siguiente error al Administrador: " + e.toString();
            }
        } else {
            query = "INSERT INTO Terapia "
                    + "(PacienteID, RazonToxicidadID, CausaTerminoID, NumeroTar, Fecha, NoContinua, FechaTermino, Geno, GenoFecha, GenoObs, Tropismo, Observacion) VALUES "
                    + "("
                    + this.PacienteID+","
                    + this.RazonToxicidadID+","
                    + this.CausaTerminoID+","
                    + this.NumeroTar+","
                    ///+ "" +(this.getFecha().equals("") ? "NULL" : "'"+convertirFecha(this.getFecha())+"'" )+ ", "
                    + "" +(this.getFecha().equals("") ? "NULL" : "'"+this.getFecha()+"'" )+ ", "
                    + "'"+this.NoContinua+"',"
                    //+ "" +(this.getFechaTermino().equals("") ? "NULL" : "'"+convertirFecha(this.getFechaTermino())+"'" )+ ", "
                    + "" +(this.getFechaTermino().equals("") ? "NULL" : "'"+this.getFechaTermino()+"'" )+ ", "
                    + "" +(this.getGeno().length == 1 && this.getGeno()[0].equals("SI") ? "'SI'" : "NULL")+", "
                    //+ "" +(this.getGenoFecha().equals("") ? "NULL" : "'"+convertirFecha(this.getGenoFecha())+"'" )+ ", "
                    + "" +(this.getGenoFecha().equals("") ? "NULL" : "'"+this.getGenoFecha()+"'" )+ ", "
                    + "'"+this.GenoObs+"',"
                    + "'"+this.Tropismo+"',"
                    + "'"+this.Observaciones+"'"
                    + ")";
            try{
                em.getTransaction().begin();    
                em.createNativeQuery(query).executeUpdate();
                em.getTransaction().commit();
                
                Query q = em.createNativeQuery("SELECT LAST_INSERT_ID()");
                Long value = ((BigInteger) q.getSingleResult()).longValue();    
                this.TerapiaID = value.intValue();
                try{
                    Date today = new Date();
                    SimpleDateFormat fecha = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                    if(httpServletRequest.getSession().getAttribute("UID")!=null){
                        Integer uid=(Integer)httpServletRequest.getSession().getAttribute("UID");
                        em.getTransaction().begin();
                        em.createNativeQuery("INSERT INTO registro_modificaciones (usuario_id,identificador,seccion,accion,fecha,SID)VALUES("+uid+","+this.TerapiaID+",'terapia','I','"+fecha.format(today)+":00','"+httpServletRequest.getSession().getId()+"');").executeUpdate();
                        em.getTransaction().commit();
                    }
                }catch(Exception e){ }
            }catch(Exception e){
                this.resultado="Ocurrio un error al registrar el nuevo TAR, informe el siguiente error al Administrador: " + e.toString();
                //em.close();
               // return "guardado";
            }
        }
        // Actualizamos las Drogas
        try{
            query = "DELETE FROM TerapiaDroga WHERE TerapiaID = " + this.TerapiaID;
            em.getTransaction().begin();
            em.createNativeQuery(query).executeUpdate();
            em.getTransaction().commit();

            if (this.droga1 != null && this.droga1 > 0) {
                query = "INSERT INTO TerapiaDroga (TerapiaID, DrogaID, Numero) VALUES (" + this.TerapiaID + "," + this.droga1 + ", 1);";
                em.getTransaction().begin();
                em.createNativeQuery(query).executeUpdate();
                em.getTransaction().commit();
            }

            if (this.droga2 != null && this.droga2 > 0) {
                query = "INSERT INTO TerapiaDroga (TerapiaID, DrogaID, Numero) VALUES (" + this.TerapiaID + "," + this.droga2 + ", 2);";
                em.getTransaction().begin();
                em.createNativeQuery(query).executeUpdate();
                em.getTransaction().commit();
            }

            if (this.droga3 != null && this.droga3 > 0) {
                query = "INSERT INTO TerapiaDroga (TerapiaID, DrogaID, Numero) VALUES (" + this.TerapiaID + "," + this.droga3 + ", 3);";
                em.getTransaction().begin();
                em.createNativeQuery(query).executeUpdate();
                em.getTransaction().commit();
            }

            if (this.droga4 != null && this.droga4 > 0) {
                query = "INSERT INTO TerapiaDroga (TerapiaID, DrogaID, Numero) VALUES (" + this.TerapiaID + "," + this.droga4 + ", 4);";
                em.getTransaction().begin();
                em.createNativeQuery(query).executeUpdate();
                em.getTransaction().commit();
            }

            if (this.droga5 != null && this.droga5 > 0) {
                query = "INSERT INTO TerapiaDroga (TerapiaID, DrogaID, Numero) VALUES (" + this.TerapiaID + "," + this.droga5 + ", 5);";
                em.getTransaction().begin();
                em.createNativeQuery(query).executeUpdate();
                em.getTransaction().commit();
            }

            if (this.droga6 != null && this.droga6 > 0) {
                query = "INSERT INTO TerapiaDroga (TerapiaID, DrogaID, Numero) VALUES (" + this.TerapiaID + "," + this.droga6 + ", 6);";
                em.getTransaction().begin();
                em.createNativeQuery(query).executeUpdate();
                em.getTransaction().commit();
            }
        }catch(Exception e){
            this.resultado="Ocurrio un error al registrar las drogar de TAR, informe el siguiente error al Administrador: " + e.toString();
            //em.close();
           // return "guardado";
        }
        //em.getTransaction().commit(); 
        if(this.resultado.equals("")){
            this.resultado = "Terapia registrada con exito.";
        }
        this.resultado="<script>alert('"+this.resultado+"');</script>";
        em.close();
        em=null;
        listadoTerapias();
        this.NumeroTar = cargarTar(this.PacienteID);
        
        TerapiaID = null;
        RazonToxicidadID = null;
        CausaTerminoID = null;
        //NumeroTar = null;
        Fecha = ""; // Fecha
        NoContinua = "";
        FechaTermino = ""; // Fecha
        Geno = new String[1];
        GenoFecha = ""; // Fecha
        GenoObs = "";
        Tropismo = "";
        Observaciones = "";
        
        droga1 = null;
        droga2 = null;
        droga3 = null;
        droga4 = null;
        droga5 = null;
        droga6 = null;
        
        return "guardado";
    }
    
    public void listadoTerapias() {
        EntityManager em = emf.createEntityManager();
        String query = "SELECT PacienteID, NumeroTar, Fecha, NoContinua, CausaTerminoID, RazonToxicidadID, FechaTermino, IFNULL(Geno, 'NO') Geno, GenoFecha, Tropismo, ID, Observacion, GenoObs FROM Terapia t WHERE t.PacienteID = " + this.PacienteID + " ORDER BY NumeroTar DESC";
        resultados = (List<Object[]>)em.createNativeQuery(query).getResultList();
        em.close();
        em=null;
    }
    
    public String modificarTerapia() throws ParseException {
        if(httpServletRequest.getSession()==null||httpServletRequest.getSession().getAttribute("UID")==null){
            this.resultado="<script>$(document).ready(function(){$('body').css('opacity','0.1');alert('Su sesion ha estado inactiva por mas de 60 minutos, por seguridad debe ingresar nuevamente.');window.location='/Cohorte2/';$('body').css('opacity','1');});</script>";
            return "eliminado";
        }
        EntityManager em = emf.createEntityManager();
        String query = "SELECT ID, PacienteID, NumeroTar, Fecha, NoContinua, CausaTerminoID, RazonToxicidadID, FechaTermino, IFNULL(Geno, 'NO') Geno, GenoFecha, Tropismo, Observacion, GenoObs "
                + "FROM Terapia t "
                + "WHERE t.ID = " + this.TerapiaID;
        List<Object[]> list = (List<Object[]>)em.createNativeQuery(query).getResultList();
        for (Object[] var : list) {
            this.NumeroTar = Integer.parseInt(var[2].toString());
            this.PacienteID = Integer.parseInt(var[1].toString());
            this.TerapiaID = Integer.parseInt(var[0].toString());
            
            if (var[3] != null && var[3].toString() != null) {
                //this.Fecha = reformatoFecha(var[3].toString());
                this.Fecha = var[3].toString();
            }
            
            if (var[4] != null && var[4].toString() != null) {
                this.NoContinua = var[4].toString();
            }
            
            if (var[5] != null && var[5].toString() != null) {
                this.CausaTerminoID = Integer.parseInt(var[5].toString());
            }
            
            if (var[6] != null && var[6].toString() != null) {
                this.RazonToxicidadID = Integer.parseInt(var[6].toString());
            }
            
            if (var[7] != null && var[7].toString() != null) {
                //this.FechaTermino = reformatoFecha(var[7].toString());
                this.FechaTermino = var[7].toString();
            }
            this.Geno = new String[1];
            if (var[8] != null && var[8].toString() != null && "SI".equals(var[8].toString())) {
                this.Geno[0] = "SI";
            }
             
            if (var[9] != null && var[9].toString() != null) {
                //this.GenoFecha = reformatoFecha(var[9].toString());
                this.GenoFecha = var[9].toString();
            }
            
            if (var[10] != null && var[10].toString() != null) {
                this.Tropismo = var[10].toString();
            }
            
            if (var[11] != null && var[11].toString() != null) {
                this.Observaciones = var[11].toString();
            }
            if (var[12] != null && var[12].toString() != null) {
                this.GenoObs = var[12].toString();
            }
        }
        
        query = "SELECT t.TerapiaID, t.DrogaID "
                + "FROM TerapiaDroga t "
                + "WHERE t.TerapiaID = " + this.TerapiaID + ";";
        list = (List<Object[]>)em.createNativeQuery(query).getResultList();
        int i = 1;
        for (Object[] var : list) {
            Integer droga = Integer.parseInt(var[1].toString());
            switch (i) {
                case 1:
                    this.droga1 = droga;
                    break;
                case 2:
                    this.droga2 = droga;
                    break;
                case 3:
                    this.droga3 = droga;
                    break;
                case 4:
                    this.droga4 = droga;
                    break;
                case 5:
                    this.droga5 = droga;
                    break;
                case 6:
                    this.droga6 = droga;
                    break;
            }
            i++;
        }
        em.close();
        em=null;
        listadoTerapias();
        return "encontrado";
    }
    
    public String cargarTerapias() {
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
        this.resultado = "paciente seleccionado: SELECT MAX(t.NumeroTar) FROM Terapia t WHERE t.PacienteID = " + this.PacienteID;
        
        String query = "SELECT p.ID, p.Codigo "
                + "FROM Paciente p "
                + "WHERE p.ID = " + this.getPacienteID() + ";";
        List<Object[]> list = (List<Object[]>)em.createNativeQuery(query).getResultList();
        for (Object[] var : list) {
            this.codigo = var[1].toString();
        }
        
        TerapiaID = null;
        RazonToxicidadID = null;
        CausaTerminoID = null;
        //NumeroTar = null;
        Fecha = ""; // Fecha
        NoContinua = "";
        FechaTermino = ""; // Fecha
        Geno = new String[1];
        GenoFecha = ""; // Fecha
        GenoObs = "";
        Tropismo = "";
        em.close();
        em=null;
        listadoTerapias();
        
        // Cargamos ultimo numero de TAR
        this.NumeroTar = cargarTar(this.PacienteID);
        return "encontrado";
    }
    
    public String generarFicha() {
        EntityManager em = emf.createEntityManager();
        String query = "SELECT p.ID,p.Codigo "
                + "FROM Paciente p "
                + "WHERE p.ID = " + this.getPacienteID() + ";";
        List<Object[]> list = (List<Object[]>)em.createNativeQuery(query).getResultList();
        for (Object[] var : list) {
            this.codigo = var[1].toString();
        }
        
        this.resultado = "paciente seleccionado: SELECT MAX(t.NumeroTar) FROM Terapia t WHERE t.PacienteID = " + this.PacienteID;
        
        TerapiaID = null;
        RazonToxicidadID = null;
        CausaTerminoID = null;
        //NumeroTar = null;
        Fecha = ""; // Fecha
        NoContinua = "";
        FechaTermino = ""; // Fecha
        Geno = new String[1];
        GenoFecha = ""; // Fecha
        GenoObs = "";
        Tropismo = "";
        em.close();
        em=null;
        listadoTerapias();
        
        // Cargamos ultimo numero de TAR
        //this.NumeroTar = cargarTar(this.PacienteID);
        return "encontrado";
    }
    
    public Integer cargarTar(Integer pacienteID) {
        Integer rt = 0;
        try {
            EntityManager em = emf.createEntityManager();
            Query q = em.createNativeQuery("SELECT MAX(t.NumeroTar) AS NumeroTar FROM Terapia t WHERE t.PacienteID = ?").setParameter(1, pacienteID);
            rt = ((Integer) q.getSingleResult()) + 1;
            em.close();
            em=null;
        } catch (Exception e) {
           rt = 1;
        }
        return rt;
    }
    
    public String eliminarTerapia() {
        if(httpServletRequest.getSession()==null||httpServletRequest.getSession().getAttribute("UID")==null){
            this.resultado="<script>$(document).ready(function(){$('body').css('opacity','0.1');alert('Su sesion ha estado inactiva por mas de 60 minutos, por seguridad debe ingresar nuevamente.');window.location='/Cohorte2/';$('body').css('opacity','1');});</script>";
            return "eliminado";
        }
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        String query = "DELETE FROM TerapiaDroga WHERE TerapiaID = " + this.TerapiaID;
        em.createNativeQuery(query).executeUpdate();
        em.getTransaction().commit();
        em.getTransaction().begin(); 
        query = "DELETE FROM Terapia WHERE ID = " + this.TerapiaID;
        em.createNativeQuery(query).executeUpdate();
        em.getTransaction().commit();
        em.close();
        em=null;
        listadoTerapias();
        this.NumeroTar = cargarTar(this.PacienteID);
        return "eliminado";
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
     * @return the RazonToxicidadID
     */
    public Integer getRazonToxicidadID() {
        return RazonToxicidadID;
    }

    /**
     * @param RazonToxicidadID the RazonToxicidadID to set
     */
    public void setRazonToxicidadID(Integer RazonToxicidadID) {
        this.RazonToxicidadID = RazonToxicidadID;
    }

    /**
     * @return the CausaTerminoID
     */
    public Integer getCausaTerminoID() {
        return CausaTerminoID;
    }

    /**
     * @param CausaTerminoID the CausaTerminoID to set
     */
    public void setCausaTerminoID(Integer CausaTerminoID) {
        this.CausaTerminoID = CausaTerminoID;
    }

    /**
     * @return the NumeroTar
     */
    public Integer getNumeroTar() {
        return NumeroTar;
    }

    /**
     * @param NumeroTar the NumeroTar to set
     */
    public void setNumeroTar(Integer NumeroTar) {
        this.NumeroTar = NumeroTar;
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
     * @return the NoContinua
     */
    public String getNoContinua() {
        return NoContinua;
    }

    /**
     * @param NoContinua the NoContinua to set
     */
    public void setNoContinua(String NoContinua) {
        this.NoContinua = NoContinua;
    }

    /**
     * @return the FechaTermino
     */
    public String getFechaTermino() {
        return FechaTermino;
    }

    /**
     * @param FechaTermino the FechaTermino to set
     */
    public void setFechaTermino(String FechaTermino) {
        this.FechaTermino = FechaTermino;
    }

    /**
     * @return the Geno
     */
    public String[] getGeno() {
        return Geno;
    }

    /**
     * @param Geno the Geno to set
     */
    public void setGeno(String[] Geno) {
        this.Geno = Geno;
    }

    /**
     * @return the GenoFecha
     */
    public String getGenoFecha() {
        return GenoFecha;
    }

    /**
     * @param GenoFecha the GenoFecha to set
     */
    public void setGenoFecha(String GenoFecha) {
        this.GenoFecha = GenoFecha;
    }

    /**
     * @return the GenoObs
     */
    public String getGenoObs() {
        return GenoObs;
    }

    /**
     * @param GenoObs the GenoObs to set
     */
    public void setGenoObs(String GenoObs) {
        this.GenoObs = GenoObs;
    }

    /**
     * @return the Tropismo
     */
    public String getTropismo() {
        return Tropismo;
    }

    /**
     * @param Tropismo the Tropismo to set
     */
    public void setTropismo(String Tropismo) {
        this.Tropismo = Tropismo;
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
     * @return the TerapiaID
     */
    public Integer getTerapiaID() {
        return TerapiaID;
    }

    /**
     * @param TerapiaID the TerapiaID to set
     */
    public void setTerapiaID(Integer TerapiaID) {
        this.TerapiaID = TerapiaID;
    }
    
    private String convertirFecha(String var){
        if (var != null && !var.equals("") && var.length() == 10) {
            // input dd/MM/yyyy
            String[] tmp = var.split("/");
            
            // return yyyy-MM-dd
            return tmp[2]+"-"+tmp[1]+"-"+tmp[0];
        }
        return "";
    }
    
    public String razonToxicidad(String var) {
        String rt = "SIN DATOS";
        EntityManager em = emf.createEntityManager();
        if (var != null && !var.equals("")) {
            Query q = em.createNativeQuery("SELECT Nombre FROM RazonToxicidad WHERE ID = ?").setParameter(1, new Integer(var));
            rt = ((String) q.getSingleResult());
        }
        em.close();
        return rt;
    }
    
    public String causaTermino(String var) {
        String rt = "SIN DATOS";
        EntityManager em = emf.createEntityManager();
        if (var != null && !var.equals("")) {
            Query q = em.createNativeQuery("SELECT Nombre FROM CausaTermino WHERE ID = ?").setParameter(1, new Integer(var));
            rt = ((String) q.getSingleResult());
        }
        em.close();
        return rt;
    }
    
    public String despliegueDroga(Integer var, Integer num, String terapia) {
        String rt = "";
        try {
            EntityManager em = emf.createEntityManager();
            if (var != null && var > 0) {
                Query q = em.createNativeQuery("SELECT d.Nombre FROM TerapiaDroga td, Droga d \n" +
                                               "WHERE td.TerapiaID = ? and td.Numero = ? AND td.DrogaID = d.ID \n" +
                                               //"ORDER BY td.ID\n" +
                                               "limit 1;").setParameter(1, new Integer(terapia)).setParameter(2, num);
                rt = ((String) q.getSingleResult());
            }
            em.close();
        } catch (Exception e) {
            //return rt;
        }
        return rt;
    }
    
    public String listarDrogas(Integer pacienteID, String terapia) {
        String result = "";
        EntityManager em = emf.createEntityManager();
        String query = "SELECT d.Nombre, d.ID FROM Terapia t, TerapiaDroga td, Droga d "
                + "WHERE t.PacienteID = " + pacienteID +" and t.ID = " + terapia +" and t.ID = td.TerapiaID and td.DrogaID = d.ID "
                + "ORDER BY td.Numero;";
        List<Object[]> list = (List<Object[]>)em.createNativeQuery(query).getResultList();
        for (Object[] var : list) {
            result += var[0].toString() + " | ";
        }
        em.close();
        if (result.length() > 3)
            return result.substring(0, result.length() - 3);
        return result;
        
    }
    
    public String torpismoViral(String var) {
        if (var != null && !var.equals("")) {
            switch (var) {
                case "PO":
                    return "POSITIVO";
                case "NE":
                    return "NEGATIVO";
                case "NS":
                    return "NO DISPONIBLE SOLICITADO";
                case "NN":
                    return "NO DISPONIBLE NO SOLICITADO";
            }
        }
        return "SIN DATOS";
    }
    
    public String noContinuaDespliegue(String var) {
        if (var != null && !var.equals("")) {
            switch (var) {
                case "CA":
                    return "CAMBIO";
                case "SU":
                    return "SUSPENDIÓ";
            }
        }
        return "SIN DATOS";
    }
    
    /*public String razonToxicidad(String var) {
        if (var != null && !var.equals("")) {
            Query q = em.createNativeQuery("SELECT Nombre FROM RazonToxicidad WHERE ID = ?").setParameter(1, new Integer(var));
            return ((String) q.getSingleResult());
        }
        return "SIN DATOS";
    }*/
    
    public String formatoDespliegueFecha(String var) {
        if (var == null || var.equals("")) {
            return "SIN DATOS";
        } else {
            String[] tmp = var.split("-");
            
            // return yyyy-MM-dd
            return tmp[2]+"-"+tmp[1]+"-"+tmp[0];
        }
    }
    
    public String desplegarObservacion(String var) {
        if (var == null || var.equals("")) {
            return "NO";
        } else {
            return "SI";
        }
    }
    
    public String reformatoFecha(String var) {
        if (var == null || var.equals("")) {
            return "";
        } else {
            String[] tmp = var.split("-");
            
            // return yyyy-MM-dd
            return tmp[2]+"/"+tmp[1]+"/"+tmp[0];
        }
    }

    /**
     * @return the droga1
     */
    public Integer getDroga1() {
        return droga1;
    }

    /**
     * @param droga1 the droga1 to set
     */
    public void setDroga1(Integer droga1) {
        this.droga1 = droga1;
    }

    /**
     * @return the droga2
     */
    public Integer getDroga2() {
        return droga2;
    }

    /**
     * @param droga2 the droga2 to set
     */
    public void setDroga2(Integer droga2) {
        this.droga2 = droga2;
    }

    /**
     * @return the droga3
     */
    public Integer getDroga3() {
        return droga3;
    }

    /**
     * @param droga3 the droga3 to set
     */
    public void setDroga3(Integer droga3) {
        this.droga3 = droga3;
    }

    /**
     * @return the droga4
     */
    public Integer getDroga4() {
        return droga4;
    }

    /**
     * @param droga4 the droga4 to set
     */
    public void setDroga4(Integer droga4) {
        this.droga4 = droga4;
    }

    /**
     * @return the droga5
     */
    public Integer getDroga5() {
        return droga5;
    }

    /**
     * @param droga5 the droga5 to set
     */
    public void setDroga5(Integer droga5) {
        this.droga5 = droga5;
    }

    /**
     * @return the droga6
     */
    public Integer getDroga6() {
        return droga6;
    }

    /**
     * @param droga6 the droga6 to set
     */
    public void setDroga6(Integer droga6) {
        this.droga6 = droga6;
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
