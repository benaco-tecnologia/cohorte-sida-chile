/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cohorte;

import java.io.Serializable;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
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
/*@ManagedBean(name = "pacienteBean")
@RequestScoped*/
public class PacienteBean implements Serializable {
    private Util util = new Util();
    
    @PersistenceUnit 
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("Cohorte2PU");; 
   
    /*@PersistenceContext
    EntityManager em = emf.createEntityManager();*/
    
    private final HttpServletRequest httpServletRequest;
    private final FacesContext faceContext;
    
    private Paciente paciente;
    private String resultado;
    private Integer pacienteID;
    private DatoBasal datoBasal;
    private List<Terapia> terapias;
    private Boolean pacienteSeleccionado = false;
    private Usuario logueado;
    private Centro logueadoCentro;
    private String[] habitos;
    private String[] razones;
    private HelperDatosPersonales datospersonales = new HelperDatosPersonales();
    
    // Datos personales
    
    private Integer CentroID;
    private Integer SexoID;
    private Integer FactorRiesgoID;
    private Integer UsoAnticonceptivoID;
    private Integer RazonTestID;
    private Integer PaisOrigenID;
    private Integer NivelEducacionalID;
    private Integer EmpleoID;
    private Integer EtniaID;
    private Integer PreferenciaSexualID;
    private Integer IdentidadGeneroID;
    private Integer ComunaID;
    private String Codigo = "";
    private String FechaISP = "";
    private String RegistroISP = "";
    private Integer CD4;
    private String FechaNotificacion = "";
    private Integer Ficha;
    private String FechaIngreso = "";
    private String FechaCD4 = "";
    private String FechaEncuesta = "";
    private String Rut = "";
    private String DV = "";
    private String FechaNacimiento = "";
    
    /**
     * Creates a new instance of PacienteBean
     */
    public PacienteBean() {
        paciente = new Paciente();
        logueado = new Usuario();
        //datospersonales = new HelperDatosPersonales();
        this.resultado = "";
        pacienteSeleccionado = false;
        
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
            CentroID = Integer.parseInt(httpServletRequest.getSession().getAttribute("Centro").toString());
        }
    }
    
    public Paciente getPaciente() {
        return paciente;
    }
    
    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }
    
    public String guardarPersonales() {
        if(httpServletRequest.getSession()==null||httpServletRequest.getSession().getAttribute("UID")==null){
            this.resultado="<script>$(document).ready(function(){$('body').css('opacity','0.1');alert('Su sesion ha estado inactiva por mas de 60 minutos, por seguridad debe ingresar nuevamente.');window.location='/Cohorte2/';$('body').css('opacity','1');});</script>";
            return "guardado";
        }
       EntityManager em = emf.createEntityManager();
        if (this.Codigo != null && !this.Codigo.equals("") && this.Codigo.length() >= 10) {
            if (pacienteID != null && pacienteID != 0) {
                String query = "UPDATE Paciente " 
                            +" SET CentroID="+ this.CentroID + ","
                            + "SexoID="+ this.SexoID+ ","
                            + "FactorRiesgoID="+ this.getFactorRiesgoID() + ","
                            + "UsoAnticonceptivoID="+ this.getUsoAnticonceptivoID() + ","
                            + "PaisOrigenID="+ this.getPaisOrigenID()+ "," 
                            + "NivelEducacionalID="+ this.getNivelEducacionalID() + ", "
                            + "EmpleoID="+ this.getEmpleoID() + ", "
                            + "EtniaID="+ this.getEtniaID()+ ", "
                            + "PreferenciaSexualID="+ this.getPreferenciaSexualID() + ", "
                            + "ComunaID="+ this.getComunaID() + ", "
                            + "Codigo="+ "'" +this.getCodigo() + "', "
                            //+ "FechaISP="+ "" +(this.getFechaISP().equals("") ? "NULL" : "'"+convertirFecha(this.getFechaISP())+"'" )+ ", "
                            + "FechaISP="+ "" +(this.getFechaISP().equals("") ? "NULL" : "'"+this.getFechaISP()+"'" )+ ", "
                            + "RegistroISP="+ "'" +this.getRegistroISP() + "', "
                            + "CD4=" + this.getCd4() + ", "
                            //+ "FechaNotificacion="+ "" +(this.getFechaNotificacion().equals("") ? "NULL" : "'"+convertirFecha(this.getFechaNotificacion())+"'" )+ ", "
                            + "FechaNotificacion="+ "" +(this.getFechaNotificacion().equals("") ? "NULL" : "'"+this.getFechaNotificacion()+"'" )+ ", "
                            + "Ficha=" + this.getFicha() + ", "
                            //+ "FechaIngreso="+ "" + (this.getFechaIngreso().equals("") ? "NULL" : "'"+convertirFecha(this.getFechaIngreso())+"'" )+ ", "
                            + "FechaIngreso="+ "" + (this.getFechaIngreso().equals("") ? "NULL" : "'"+this.getFechaIngreso()+"'" )+ ", "
                            //+ "FechaCD4="+ "" +(this.getFechaCD4().equals("") ? "NULL" : "'"+convertirFecha(this.getFechaCD4())+"'" )+ ", "
                            + "FechaCD4="+ "" +(this.getFechaCD4().equals("") ? "NULL" : "'"+this.getFechaCD4()+"'" )+ ", "
                            //+ "FechaEncuesta="+ "" +(this.getFechaEncuesta().equals("") ? "NULL" : "'"+convertirFecha(this.getFechaEncuesta())+"'" )+ ", "
                            + "FechaEncuesta="+ "" +(this.getFechaEncuesta().equals("") ? "NULL" : "'"+this.getFechaEncuesta()+"'" )+ ", "
                            // Se agrega encriptacion al rut
                            + "Rut="+ "TO_BASE64('" +this.getRut() + "'), "
                            + "DV="+ "'" +this.getDv()+ "', "
                            + "IdentidadGeneroID="+this.IdentidadGeneroID+","
                            //+ "FechaNacimiento="+ "" +(this.getFechaNacimiento().equals("") ? "NULL" : "'"+convertirFecha(this.getFechaNacimiento())+"'")+ " "
                            + "FechaNacimiento="+ "" +(this.getFechaNacimiento().equals("") ? "NULL" : "'"+this.getFechaNacimiento()+"'")+ " "
                            + "WHERE ID = " + pacienteID + ";";
                
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
                            em.createNativeQuery("INSERT INTO registro_modificaciones (usuario_id,paciente_id,seccion,accion,fecha,SID)VALUES("+uid+","+pacienteID+",'paciente','A','"+fecha.format(today)+":00','"+httpServletRequest.getSession().getId()+"');").executeUpdate();
                            em.getTransaction().commit();
                        }
                    }catch(Exception e){}   
                    //this.setResultado("Datos actualizados. " + query);
                    this.setResultado("<script>$(document).ready(function(){$('body').css('opacity','0.1');alert('Paciente actualizado con éxito!');$('body').css('opacity','1');});</script>");
                } catch (Exception e) {
                    this.setResultado("<script>$(document).ready(function(){$('body').css('opacity','0.1');alert('Error al actualizar Paciente!');$('body').css('opacity','1');});</script>"+e.getMessage());
                    //this.setResultado("Datos NO actualizados, error. " + query + " " + e.getMessage());
                    em.close();
                    em = null;
                    System.gc();
                    return "guardado";
                }
               // this.resultado = "El Paciente seleccionado ha sido actualizado! " + query;
            } else {
                String query = "INSERT INTO Paciente " 
                            +"(CentroID,SexoID,FactorRiesgoID,UsoAnticonceptivoID,PaisOrigenID,NivelEducacionalID,EmpleoID,EtniaID,PreferenciaSexualID,ComunaID,"
                            + "Codigo,FechaISP,RegistroISP,CD4,FechaNotificacion,Ficha,FechaIngreso,FechaCD4,FechaEncuesta,Rut,DV,IdentidadGeneroID,FechaNacimiento) "
                            +"VALUES ("
                            + CentroID + ", "
                            + this.getSexoID()+ ", "
                            + this.getFactorRiesgoID() + ", "
                            + this.getUsoAnticonceptivoID() + ", "
                            + this.getPaisOrigenID()+ ", "
                            + this.getNivelEducacionalID() + ", "
                            + this.getEmpleoID() + ", "
                            + this.getEtniaID()+ ", "
                            + this.getPreferenciaSexualID() + ", "
                            + this.getComunaID() + ", "
                            + "'" +this.getCodigo() + "', "
                            //+ "" +(this.getFechaISP().equals("") ? "NULL" : "'"+convertirFecha(this.getFechaISP())+"'" )+ ", "
                            + "" +(this.getFechaISP().equals("") ? "NULL" : "'"+this.getFechaISP()+"'" )+ ", "
                            + "'" +this.getRegistroISP() + "', "
                            + this.getCd4() + ", "
                            //+ "" +(this.getFechaNotificacion().equals("") ? "NULL" : "'"+convertirFecha(this.getFechaNotificacion())+"'" )+ ", "
                            + "" +(this.getFechaNotificacion().equals("") ? "NULL" : "'"+this.getFechaNotificacion()+"'" )+ ", "
                            + this.getFicha() + ", "
                            //+ "" + (this.getFechaIngreso().equals("") ? "NULL" : "'"+convertirFecha(this.getFechaIngreso())+"'" )+ ", "
                           // + "" +(this.getFechaCD4().equals("") ? "NULL" : "'"+convertirFecha(this.getFechaCD4())+"'" )+ ", "
                            //+ "" +(this.getFechaEncuesta().equals("") ? "NULL" : "'"+convertirFecha(this.getFechaEncuesta())+"'" )+ ", "
                            + "" + (this.getFechaIngreso().equals("") ? "NULL" : "'"+this.getFechaIngreso()+"'" )+ ", "
                            + "" +(this.getFechaCD4().equals("") ? "NULL" : "'"+this.getFechaCD4()+"'" )+ ", "
                            + "" +(this.getFechaEncuesta().equals("") ? "NULL" : "'"+this.getFechaEncuesta()+"'" )+ ", "
                             // Se agrega encriptacion al rut
                            + "TO_BASE64('" +this.getRut() + "'), "
                            + "'" +this.getDv()+ "', "
                            + this.IdentidadGeneroID + ", "
                            //+ "" +(this.getFechaNacimiento().equals("") ? "NULL" : "'"+convertirFecha(this.getFechaNacimiento())+"'") +");";
                            + "" +(this.getFechaNacimiento().equals("") ? "NULL" : "'"+this.getFechaNacimiento()+"'") +");";
                try {
                    em.getTransaction().begin();    
                    em.createNativeQuery(query).executeUpdate();
                    em.getTransaction().commit();
                    
                    this.setResultado("<script>$(document).ready(function(){$('body').css('opacity','0.1');alert('Paciente actualizado con éxito!');$('body').css('opacity','1');});</script>");
                } catch (Exception e) {
                    if (e.getMessage().contains("Codigo_UNIQUE")) {
                        this.setResultado("<script>$(document).ready(function(){$('body').css('opacity','0.1');alert('ERROR: El Paciente ingresado con código "+this.getCodigo()+" ya existe! Para modificar su información debe modificarlo, no ingresarlo como nuevo.');$('body').css('opacity','1');});</script>");
                    } else if (e.getMessage().contains("Data truncation: Data too long for column 'Codigo'")) {
                        this.setResultado("<script>$(document).ready(function(){$('body').css('opacity','0.1');alert('ERROR: El código "+this.getCodigo()+" debe tener como máximo 10 carácteres.');$('body').css('opacity','1');});</script>");
                    } else {
                        //this.setResultado("Datos NO actualizados, error. " + query + " " + e.getMessage());
                        this.setResultado("<script>$(document).ready(function(){$('body').css('opacity','0.1');alert('ERROR: Ocurrió un error al ingresar el Paciente con código "+this.getCodigo()+", intente nuevamente!');$('body').css('opacity','1');});</script>"+e.getMessage());
                    }
                    em.close();
                    em = null;
                    System.gc();
                    return "guardado";
                }
                Query q = em.createNativeQuery("SELECT LAST_INSERT_ID()");
		Long value = ((BigInteger) q.getSingleResult()).longValue();
                
                // Fix registro de modificacion
                try{
                    Date today = new Date();
                    SimpleDateFormat fecha = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                    if(httpServletRequest.getSession().getAttribute("UID")!=null){
                        Integer uid=(Integer)httpServletRequest.getSession().getAttribute("UID");
                        em.getTransaction().begin();
                        em.createNativeQuery("INSERT INTO registro_modificaciones (usuario_id,paciente_id,seccion,accion,fecha,SID)VALUES("+uid+","+value+",'paciente','I','"+fecha.format(today)+":00','"+httpServletRequest.getSession().getId()+"');").executeUpdate();
                        em.getTransaction().commit();
                    }
                }catch(Exception e){}
                
                if (value > 0) {
                    //this.resultado = "paciente creado y seleccionado "+value.toString()+" --- " + query + "---" + Arrays.toString(razones);
                    this.pacienteID = value.intValue();
                    this.pacienteSeleccionado = true;
                    try {
                        em.getTransaction().begin();
                        query = "INSERT INTO DatoBasal (PacienteID) VALUES (" + this.pacienteID + ");";
                        em.createNativeQuery(query).executeUpdate();
                        em.getTransaction().commit();
                    } catch (Exception e) {  }
                } else {
                    //this.resultado = "paciente creado y NO seleccionado " + query;
                    this.pacienteID = 0;
                    this.pacienteSeleccionado = false;
                }
                value = null;
            }
            
            // Se actualizan razones y habitos
            if (pacienteID != null && pacienteID != 0) {
                try {
                    String query = "DELETE FROM PacienteRazonTest WHERE PacienteID = " + pacienteID;
                    em.getTransaction().begin();
                    em.createNativeQuery(query).executeUpdate();
                    em.getTransaction().commit();
                    for (String var : razones) {
                        query = "INSERT INTO PacienteRazonTest (PacienteID, RazonTestID) VALUES (" + pacienteID + "," + var + ");";
                        em.getTransaction().begin();
                        em.createNativeQuery(query).executeUpdate();
                        em.getTransaction().commit();
                    }

                    query = "DELETE FROM PacienteHabito WHERE PacienteID = " + pacienteID;
                    em.getTransaction().begin();
                        em.createNativeQuery(query).executeUpdate();
                        em.getTransaction().commit();

                    for (String var : habitos) {
                        query = "INSERT INTO PacienteHabito (PacienteID, HabitoID) VALUES (" + pacienteID + "," + var + ");";
                        em.getTransaction().begin();
                        em.createNativeQuery(query).executeUpdate();
                        em.getTransaction().commit();
                    }
                } catch (Exception e) { }
            }
        } else {
            //this.resultado = "codigo no valido";
            this.setResultado("<script>alert('El código "+this.getCodigo()+" no es válido, debe cambiarlo e intentar nuevamente!');</script>");
        }
        
        
        if (this.pacienteID != null && this.pacienteID != 0) {
            /*
            this.cargarPaciente();
             // Cargar habitos
            List<PacienteHabito> lista = (List<PacienteHabito>)em.createNamedQuery("PacienteHabito.findByPacienteID").setParameter("pacienteID", this.paciente).getResultList();
            int i = 0;
            this.habitos = new String[lista.size()];
            for (PacienteHabito var : lista) {
                this.habitos[i] = var.getHabitoID().getId().toString();
                i++;
            }

            // Razon test
            List<PacienteRazonTest> listb = (List<PacienteRazonTest>)em.createNamedQuery("PacienteRazonTest.findByPacienteID").setParameter("pacienteID", this.paciente).getResultList();
            i = 0;
            this.razones = new String[listb.size()];
            for (PacienteRazonTest var : listb) {
                this.razones[i] = var.getRazonTestID().getId().toString();
                i++;
            }
            */
        }
        em.close();
        em = null;
        System.gc();
        this.cargarPaciente();
        return "guardado";
    }   
    
    public String getResultado() {
        return resultado;
    }    
    
    public String seleccionarPaciente() {
        if(httpServletRequest.getSession()==null||httpServletRequest.getSession().getAttribute("UID")==null){
            this.resultado="<script>$(document).ready(function(){$('body').css('opacity','0.1');alert('Su sesion ha estado inactiva por mas de 60 minutos, por seguridad debe ingresar nuevamente.');window.location='/Cohorte2/';$('body').css('opacity','1');});</script>";
            return "encontrado";
        }
        if (pacienteID != null && pacienteID != 0) {
            this.cargarPaciente();
            // Cargar habitos
            /*List<PacienteHabito> lista = (List<PacienteHabito>)em.createNamedQuery("PacienteHabito.findByPacienteID").setParameter("pacienteID", this.paciente).getResultList();
            int i = 0;
            this.habitos = new String[lista.size()];
            for (PacienteHabito var : lista) {
                this.habitos[i] = var.getHabitoID().getId().toString();
                i++;
            }

            // Razon test
            List<PacienteRazonTest> listb = (List<PacienteRazonTest>)em.createNamedQuery("PacienteRazonTest.findByPacienteID").setParameter("pacienteID", this.paciente).getResultList();
            i = 0;
            this.razones = new String[listb.size()];
            for (PacienteRazonTest var : listb) {
                this.razones[i] = var.getRazonTestID().getId().toString();
                i++;
            }*/

            this.resultado = "encontrado " + this.paciente.getId();
            this.pacienteSeleccionado = true;
            return "encontrado";
        } else {
            this.paciente = null;
            //this.setDatospersonales(new HelperDatosPersonales());
            this.pacienteSeleccionado = false;
            this.resultado = "no encontrado " + 0;
        }
        return "encontrado";
    }
    
    public String generarFicha() {
        if (pacienteID != null && pacienteID != 0) {
            // Cargamos info paciente;
            this.cargarPaciente();
           // Cargar habitos
           /* List<PacienteHabito> lista = (List<PacienteHabito>)em.createNamedQuery("PacienteHabito.findByPacienteID").setParameter("pacienteID", this.paciente).getResultList();
            int i = 0;
            this.habitos = new String[lista.size()];
            for (PacienteHabito var : lista) {
                this.habitos[i] = var.getHabitoID().getId().toString();
                i++;
            }

            // Razon test
            List<PacienteRazonTest> listb = (List<PacienteRazonTest>)em.createNamedQuery("PacienteRazonTest.findByPacienteID").setParameter("pacienteID", this.paciente).getResultList();
            i = 0;
            this.razones = new String[listb.size()];
            for (PacienteRazonTest var : listb) {
                this.razones[i] = var.getRazonTestID().getId().toString();
                i++;
            }*/

            this.resultado = "encontrado " + this.paciente.getId();
            this.pacienteSeleccionado = true;
            return "encontrado";
        } else {
            this.paciente = null;
            //this.setDatospersonales(new HelperDatosPersonales());
            this.pacienteSeleccionado = false;
            this.resultado = "no encontrado " + 0;
        }
        return "encontrado";
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
     * @param resultado the resultado to set
     */
    public void setResultado(String resultado) {
        this.resultado = resultado;
    }
    
    public String cargarBasales() {
        EntityManager em = emf.createEntityManager();
        List<Paciente> list = (List<Paciente>)em.createNamedQuery("Paciente.findById").setParameter("id", pacienteID).getResultList();
        if (list.size() == 1) {
            this.paciente = list.get(0);
            this.pacienteID = this.paciente.getId();
            List<DatoBasal> datoBasal = (List<DatoBasal>)em.createNamedQuery("DatoBasal.findByPacienteID").setParameter("pacienteID", this.paciente).getResultList();
            if (datoBasal.size() == 0) {
                this.resultado = "sin datos basal";
                this.setDatoBasal(new DatoBasal());
            } else {
                this.resultado = "con dato basal";
                this.setDatoBasal(datoBasal.get(0));
            }
            this.pacienteSeleccionado = true;
        } else {
            this.pacienteSeleccionado = false;
            this.resultado = "no hay pacienteid";
        }
        em.close();
        return "encontrado";
    }
    
    public String cargarTerapias() {
        EntityManager em = emf.createEntityManager();
        List<Paciente> list = (List<Paciente>)em.createNamedQuery("Paciente.findById").setParameter("id", pacienteID).getResultList();
        if (list.size() == 1) {
            this.paciente = list.get(0);
            //this.pacienteID = this.paciente.getId();
            this.resultado = "hay terapias";
            this.pacienteSeleccionado = true;
            this.terapias = (List<Terapia>)em.createNamedQuery("Terapia.findByPacienteID").setParameter("pacienteID", this.paciente).getResultList();
        } else {
            this.pacienteSeleccionado = false;
            this.resultado = "no hay terapias";
        }
        em.close();
        return "encontrado";
    }
    
    public String cargarTraslado() {
        EntityManager em = emf.createEntityManager();
        List<Paciente> list = (List<Paciente>)em.createNamedQuery("Paciente.findById").setParameter("id", pacienteID).getResultList();
        if (list.size() == 1) {
            this.paciente = list.get(0);
            this.pacienteID = this.paciente.getId();
            this.pacienteSeleccionado = true;
            //this.resultado = "paciente seleccionado ok";
        } else {
            this.pacienteSeleccionado = false;
            //this.resultado = "no hay pacienteid";
        }
        em.close();
        return "encontrado";
    }
    
    public String cargarSeccionControles() {
        EntityManager em = emf.createEntityManager();
        List<Paciente> list = (List<Paciente>)em.createNamedQuery("Paciente.findById").setParameter("id", pacienteID).getResultList();
        if (list.size() == 1) {
            this.paciente = list.get(0);
            //this.pacienteID = this.paciente.getId();
            this.resultado = "hay controles cd4 / cv";
            this.pacienteSeleccionado = true;
            this.terapias = (List<Terapia>)em.createNamedQuery("Terapia.findByPacienteID").setParameter("pacienteID", this.paciente).getResultList();
        } else {
            this.pacienteSeleccionado = false;
            this.resultado = "no hay hay controles cd4 / cv";
        }
        em.close();
        return "encontrado";
    }
    
    
    public String cargarSeccionLaboratorios() {
        EntityManager em = emf.createEntityManager();
        List<Paciente> list = (List<Paciente>)em.createNamedQuery("Paciente.findById").setParameter("id", pacienteID).getResultList();
        if (list.size() == 1) {
            this.paciente = list.get(0);
            //this.pacienteID = this.paciente.getId();
            this.resultado = "hay controles laboratorios";
            this.pacienteSeleccionado = true;
            this.terapias = (List<Terapia>)em.createNamedQuery("Terapia.findByPacienteID").setParameter("pacienteID", this.paciente).getResultList();
        } else {
            this.pacienteSeleccionado = false;
            this.resultado = "no hay hay controles laboratorios";
        }
        em.close();
        return "encontrado";
    }
    
    public String cargarSeccionSeguimiento() {
        EntityManager em = emf.createEntityManager();
        List<Paciente> list = (List<Paciente>)em.createNamedQuery("Paciente.findById").setParameter("id", pacienteID).getResultList();
        if (list.size() == 1) {
            this.paciente = list.get(0);
            //this.pacienteID = this.paciente.getId();
            this.resultado = "hay controles laboratorios";
            this.pacienteSeleccionado = true;
            this.terapias = (List<Terapia>)em.createNamedQuery("Terapia.findByPacienteID").setParameter("pacienteID", this.paciente).getResultList();
        } else {
            this.pacienteSeleccionado = false;
            this.resultado = "no hay hay controles laboratorios";
        }
        em.close();
        return "encontrado";
    }
    
    public String cargarSeccionEvento() {
        EntityManager em = emf.createEntityManager();
        List<Paciente> list = (List<Paciente>)em.createNamedQuery("Paciente.findById").setParameter("id", pacienteID).getResultList();
        if (list.size() == 1) {
            this.paciente = list.get(0);
            //this.pacienteID = this.paciente.getId();
            this.resultado = "hay controles laboratorios";
            this.pacienteSeleccionado = true;
            this.terapias = (List<Terapia>)em.createNamedQuery("Terapia.findByPacienteID").setParameter("pacienteID", this.paciente).getResultList();
        } else {
            this.pacienteSeleccionado = false;
            this.resultado = "no hay hay controles laboratorios";
        }
        em.close();
        return "encontrado";
    }
    
    public String cargarSeccionTerapiaAgrupada() {
        EntityManager em = emf.createEntityManager();
        List<Paciente> list = (List<Paciente>)em.createNamedQuery("Paciente.findById").setParameter("id", pacienteID).getResultList();
        if (list.size() == 1) {
            this.paciente = list.get(0);
            //this.pacienteID = this.paciente.getId();
            this.resultado = "hay controles laboratorios";
            this.pacienteSeleccionado = true;
            this.terapias = (List<Terapia>)em.createNamedQuery("Terapia.findByPacienteID").setParameter("pacienteID", this.paciente).getResultList();
        } else {
            this.pacienteSeleccionado = false;
            this.resultado = "no hay hay controles laboratorios";
        }
        em.close();
        return "encontrado";
    }
    
    public String cargarSeccionObservacion() {
        EntityManager em = emf.createEntityManager();
        List<Paciente> list = (List<Paciente>)em.createNamedQuery("Paciente.findById").setParameter("id", pacienteID).getResultList();
        if (list.size() == 1) {
            this.paciente = list.get(0);
            //this.pacienteID = this.paciente.getId();
            this.resultado = "hay controles laboratorios";
            this.pacienteSeleccionado = true;
            this.terapias = (List<Terapia>)em.createNamedQuery("Terapia.findByPacienteID").setParameter("pacienteID", this.paciente).getResultList();
        } else {
            this.pacienteSeleccionado = false;
            this.resultado = "no hay hay controles laboratorios";
        }
        em.close();
        return "encontrado";
    }
    
    /**
     * @return the datoBasal
     */
    public DatoBasal getDatoBasal() {
        return datoBasal;
    }

    /**
     * @param datoBasal the datoBasal to set
     */
    public void setDatoBasal(DatoBasal datoBasal) {
        this.datoBasal = datoBasal;
    }

    /**
     * @return the terapias
     */
    public List<Terapia> getTerapias() {
        return terapias;
    }

    /**
     * @param terapias the terapias to set
     */
    public void setTerapias(List<Terapia> terapias) {
        this.terapias = terapias;
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
     * @return the habitos
     */
    public String[] getHabitos() {
        return habitos;
    }

    /**
     * @param habitos the habitos to set
     */
    public void setHabitos(String[] habitos) {
        this.habitos = habitos;
    }

    /**
     * @return the razones
     */
    public String[] getRazones() {
        return razones;
    }

    /**
     * @param razones the razones to set
     */
    public void setRazones(String[] razones) {
        this.razones = razones;
    }
    
    public String Guardar() {
        if (pacienteID != null && pacienteID != 0) {
            // Actualizar paciente;
            int sexoID;
            
            
             //resultados = (List<Object[]>)em.createNativeQuery(query).getResultList();
            
        } else {
            // Ingresar paciente;
            String query = "UPDATE Paciente " 
                            +"(CentroID,SexoID,FactorRiesgoID,UsoAnticonceptivoID,PaisOrigenID,NivelEducacionalID,EmpleoID,EtniaID,PreferenciaSexualID,ComunaID,"
                            + "Codigo,FechaISP,RegistroISP,CD4,FechaNotificacion,Ficha,FechaIngreso,FechaCD4,FechaEncuesta,Rut,DV,FechaNacimiento) "
                            +"VALUES ("
                            /*+ this.paciente.getCentroID().getId() + ", "
                            + this.paciente.getSexoID()+ ", "
                            + this.paciente.getFactorRiesgoID().getId() + ", "
                            + this.paciente.getUsoAnticonceptivoID().getId() + ", "
                            + this.paciente.getRazonTestID().getId() + ", "
                            + this.paciente.getPaisOrigenID().getId() + ", "
                            + this.paciente.getNivelEducacionalID().getId() + ", "
                            + this.paciente.getEmpleoID().getId() + ", "
                            + this.paciente.getEtniaID().getId() + ", "
                            + this.paciente.getPreferenciaSexualID().getId() + ", "
                            + this.paciente.getComunaID() + ", "*/
                            + "'" +this.paciente.getCodigo() + "', "
                            + "'" +this.paciente.getFechaISP() + "', "
                            + "'" +this.paciente.getRegistroISP() + "', "
                            + this.paciente.getCd4() + ", "
                            + "'" +this.paciente.getFechaNotificacion() + "', "
                            + this.paciente.getFicha() + ", "
                            + "'" +this.paciente.getFechaIngreso() + "', "
                            + "'" +this.paciente.getFechaEncuesta() + "', "
                            + "'" +this.paciente.getRut() + "', "
                            + "'" +this.paciente.getDv()+ "', "
                            + "'" +this.paciente.getFechaNacimiento()+ "')";
            
            seleccionarPaciente();
            this.resultado = query;
            
        }
        
        
        return "guardado";
    }

    /**
     * @return the datospersonales
     */
    public HelperDatosPersonales getDatospersonales() {
        return datospersonales;
    }

    /**
     * @param datospersonales the datospersonales to set
     */
    public void setDatospersonales(HelperDatosPersonales datospersonales) {
        this.datospersonales = datospersonales;
    }

    /**
     * @return the FechaIngreso
     */
    public String getFechaIngreso() {
        return FechaIngreso;
    }

    /**
     * @param FechaIngreso the FechaIngreso to set
     */
    public void setFechaIngreso(String FechaIngreso) {
        this.FechaIngreso = FechaIngreso;
    }

    /**
     * @return the Codigo
     */
    public String getCodigo() {
        return Codigo;
    }

    /**
     * @param Codigo the Codigo to set
     */
    public void setCodigo(String Codigo) {
        this.Codigo = Codigo;
    }

    /**
     * @return the CentroID
     */
    public Integer getCentroID() {
        return CentroID;
    }

    /**
     * @param CentroID the CentroID to set
     */
    public void setCentroID(Integer CentroID) {
        this.CentroID = CentroID;
    }

    /**
     * @return the SexoID
     */
    public Integer getSexoID() {
        return SexoID;
    }

    /**
     * @param SexoID the SexoID to set
     */
    public void setSexoID(Integer SexoID) {
        this.SexoID = SexoID;
    }

    /**
     * @return the FactorRiesgoID
     */
    public Integer getFactorRiesgoID() {
        return FactorRiesgoID;
    }

    /**
     * @param FactorRiesgoID the FactorRiesgoID to set
     */
    public void setFactorRiesgoID(Integer FactorRiesgoID) {
        this.FactorRiesgoID = FactorRiesgoID;
    }

    /**
     * @return the UsoAnticonceptivoID
     */
    public Integer getUsoAnticonceptivoID() {
        return UsoAnticonceptivoID;
    }

    /**
     * @param UsoAnticonceptivoID the UsoAnticonceptivoID to set
     */
    public void setUsoAnticonceptivoID(Integer UsoAnticonceptivoID) {
        this.UsoAnticonceptivoID = UsoAnticonceptivoID;
    }

    /**
     * @return the RazonTestID
     */
    public Integer getRazonTestID() {
        return RazonTestID;
    }

    /**
     * @param RazonTestID the RazonTestID to set
     */
    public void setRazonTestID(Integer RazonTestID) {
        this.RazonTestID = RazonTestID;
    }

    /**
     * @return the PaisOrigenID
     */
    public Integer getPaisOrigenID() {
        return PaisOrigenID;
    }

    /**
     * @param PaisOrigenID the PaisOrigenID to set
     */
    public void setPaisOrigenID(Integer PaisOrigenID) {
        this.PaisOrigenID = PaisOrigenID;
    }

    /**
     * @return the NivelEducacionalID
     */
    public Integer getNivelEducacionalID() {
        return NivelEducacionalID;
    }

    /**
     * @param NivelEducacionalID the NivelEducacionalID to set
     */
    public void setNivelEducacionalID(Integer NivelEducacionalID) {
        this.NivelEducacionalID = NivelEducacionalID;
    }

    /**
     * @return the EmpleoID
     */
    public Integer getEmpleoID() {
        return EmpleoID;
    }

    /**
     * @param EmpleoID the EmpleoID to set
     */
    public void setEmpleoID(Integer EmpleoID) {
        this.EmpleoID = EmpleoID;
    }

    /**
     * @return the EtniaID
     */
    public Integer getEtniaID() {
        return EtniaID;
    }

    /**
     * @param EtniaID the EtniaID to set
     */
    public void setEtniaID(Integer EtniaID) {
        this.EtniaID = EtniaID;
    }

    /**
     * @return the PreferenciaSexualID
     */
    public Integer getPreferenciaSexualID() {
        return PreferenciaSexualID;
    }

    /**
     * @param PreferenciaSexualID the PreferenciaSexualID to set
     */
    public void setPreferenciaSexualID(Integer PreferenciaSexualID) {
        this.PreferenciaSexualID = PreferenciaSexualID;
    }

    /**
     * @return the ComunaID
     */
    public Integer getComunaID() {
        return ComunaID;
    }

    /**
     * @param ComunaID the ComunaID to set
     */
    public void setComunaID(Integer ComunaID) {
        this.ComunaID = ComunaID;
    }

    /**
     * @return the FechaISP
     */
    public String getFechaISP() {
        return FechaISP;
    }

    /**
     * @param FechaISP the FechaISP to set
     */
    public void setFechaISP(String FechaISP) {
        this.FechaISP = FechaISP;
    }

    /**
     * @return the RegistroISP
     */
    public String getRegistroISP() {
        return RegistroISP;
    }

    /**
     * @param RegistroISP the RegistroISP to set
     */
    public void setRegistroISP(String RegistroISP) {
        this.RegistroISP = RegistroISP;
    }

    /**
     * @return the CD4
     */
    public Integer getCd4() {
        return CD4;
    }

    /**
     * @param CD4 the CD4 to set
     */
    public void setCd4(Integer CD4) {
        this.CD4 = CD4;
    }

    /**
     * @return the FechaNotificacion
     */
    public String getFechaNotificacion() {
        return FechaNotificacion;
    }

    /**
     * @param FechaNotificacion the FechaNotificacion to set
     */
    public void setFechaNotificacion(String FechaNotificacion) {
        this.FechaNotificacion = FechaNotificacion;
    }

    /**
     * @return the Ficha
     */
    public Integer getFicha() {
        return Ficha;
    }

    /**
     * @param Ficha the Ficha to set
     */
    public void setFicha(Integer Ficha) {
        this.Ficha = Ficha;
    }

    /**
     * @return the FechaCD4
     */
    public String getFechaCD4() {
        return FechaCD4;
    }

    /**
     * @param FechaCD4 the FechaCD4 to set
     */
    public void setFechaCD4(String FechaCD4) {
        this.FechaCD4 = FechaCD4;
    }

    /**
     * @return the FechaEncuesta
     */
    public String getFechaEncuesta() {
        return FechaEncuesta;
    }

    /**
     * @param FechaEncuesta the FechaEncuesta to set
     */
    public void setFechaEncuesta(String FechaEncuesta) {
        this.FechaEncuesta = FechaEncuesta;
    }

    /**
     * @return the Rut
     */
    public String getRut() {
        return Rut;
    }

    /**
     * @param Rut the Rut to set
     */
    public void setRut(String Rut) {
        this.Rut = Rut;
    }

    /**
     * @return the DV
     */
    public String getDv() {
        return DV;
    }

    /**
     * @param DV the DV to set
     */
    public void setDv(String DV) {
        this.DV = DV;
    }

    /**
     * @return the FechaNacimiento
     */
    public String getFechaNacimiento() {
        return FechaNacimiento;
    }

    /**
     * @param FechaNacimiento the FechaNacimiento to set
     */
    public void setFechaNacimiento(String FechaNacimiento) {
        this.FechaNacimiento = FechaNacimiento;
    }
    
    public void cargarPaciente(/*Paciente var*/) {
        EntityManager em = emf.createEntityManager();
        this.CentroID = null;
        this.SexoID = null;
        this.FactorRiesgoID = null;
        this.UsoAnticonceptivoID = null;
        this.RazonTestID = null;
        this.PaisOrigenID = null;
        this.NivelEducacionalID = null;
        this.EmpleoID = null;
        this.EtniaID = null;
        this.PreferenciaSexualID = null;
        this.ComunaID = null;
        this.Codigo = "";
        this.FechaISP = "";
        this.RegistroISP = "";
        this.CD4 = null;
        this.FechaNotificacion = "";
        this.Ficha = null;
        this.FechaIngreso = "";
        this.FechaCD4 = "";
        this.FechaEncuesta = "";
        this.Rut = "";
        this.DV = "";
        this.FechaNacimiento = "";
        
        //                        0       1          2              3                  4              5              6        7             8              9
        String query = "SELECT CentroID,SexoID,FactorRiesgoID,UsoAnticonceptivoID,PaisOrigenID,NivelEducacionalID,EmpleoID,EtniaID,PreferenciaSexualID,ComunaID,"
                //   10      11        12       13     14              15       16         17          18       19 20      21
                + "Codigo,FechaISP,RegistroISP,CD4,FechaNotificacion,Ficha,FechaIngreso,FechaCD4,FechaEncuesta,CAST(FROM_BASE64(rut) as char),DV,FechaNacimiento,IdentidadGeneroID "
                + "FROM Paciente WHERE ID=" + this.pacienteID+" LIMIT 1";
        
        List<Object[]> list = (List<Object[]>)em.createNativeQuery(query).getResultList();
        for (Object[] tmp : list) {
            this.CentroID = (tmp[0] != null && !"".equals(tmp[0].toString()) ? Integer.parseInt(tmp[0].toString()) : null);
            this.SexoID = (tmp[1] != null && !"".equals(tmp[1].toString()) ? Integer.parseInt(tmp[1].toString()) : null);
            this.FactorRiesgoID = (tmp[2] != null && !"".equals(tmp[2].toString()) ? Integer.parseInt(tmp[2].toString()) : null);
            this.UsoAnticonceptivoID = (tmp[3] != null && !"".equals(tmp[3].toString()) ? Integer.parseInt(tmp[3].toString()) : null);
            this.PaisOrigenID = (tmp[4] != null && !"".equals(tmp[4].toString()) ? Integer.parseInt(tmp[4].toString()) : null);
            this.NivelEducacionalID = (tmp[5] != null && !"".equals(tmp[5].toString()) ? Integer.parseInt(tmp[5].toString()) : null);
            this.EmpleoID = (tmp[6] != null && !"".equals(tmp[6].toString()) ? Integer.parseInt(tmp[6].toString()) : null);
            this.EtniaID = (tmp[7] != null && !"".equals(tmp[7].toString()) ? Integer.parseInt(tmp[7].toString()) : null);
            this.PreferenciaSexualID = (tmp[8] != null && !"".equals(tmp[8].toString()) ? Integer.parseInt(tmp[8].toString()) : null);
            this.ComunaID = (tmp[9] != null && !"".equals(tmp[9].toString()) ? Integer.parseInt(tmp[9].toString()) : null);
            
            this.Codigo = (tmp[10] != null && !"".equals(tmp[10].toString()) ? tmp[10].toString() : "");
            //this.FechaISP = (tmp[11] != null && !"".equals(tmp[11].toString()) ? util.fechaForm(tmp[11].toString()) : "");
            this.FechaISP = (tmp[11] != null && !"".equals(tmp[11].toString()) ? tmp[11].toString() : "");
            this.RegistroISP = (tmp[12] != null && !"".equals(tmp[12].toString()) ? tmp[12].toString() : "");
            this.CD4 = (tmp[13] != null && !"".equals(tmp[13].toString()) ? Integer.parseInt(tmp[13].toString()) : null);
            //this.FechaNotificacion = (tmp[14] != null && !"".equals(tmp[14].toString()) ? util.fechaForm(tmp[14].toString()) : "");
            this.FechaNotificacion = (tmp[14] != null && !"".equals(tmp[14].toString()) ? tmp[14].toString() : "");
            this.Ficha = (tmp[15] != null && !"".equals(tmp[15].toString()) ? Integer.parseInt(tmp[15].toString()) : null);
           // this.FechaIngreso = (tmp[16] != null && !"".equals(tmp[16].toString()) ? util.fechaForm(tmp[16].toString()) : "");
            //this.FechaCD4 = (tmp[17] != null && !"".equals(tmp[17].toString()) ? util.fechaForm(tmp[17].toString()) : "");
            //this.FechaEncuesta = (tmp[18] != null && !"".equals(tmp[18].toString()) ? util.fechaForm(tmp[18].toString()) : "");
            this.FechaIngreso = (tmp[16] != null && !"".equals(tmp[16].toString()) ? tmp[16].toString() : "");
            this.FechaCD4 = (tmp[17] != null && !"".equals(tmp[17].toString()) ? tmp[17].toString() : "");
            this.FechaEncuesta = (tmp[18] != null && !"".equals(tmp[18].toString()) ? tmp[18].toString() : "");
            // Se agrega encriptacion al rut
            this.Rut = (tmp[19] != null && !"".equals(tmp[19].toString()) ? tmp[19].toString() : "");
            this.DV = (tmp[20] != null && !"".equals(tmp[20].toString()) ? tmp[20].toString() : "");
            //this.FechaNacimiento = (tmp[21] != null && !"".equals(tmp[21].toString()) ? util.fechaForm(tmp[21].toString()) : "");
            this.FechaNacimiento = (tmp[21] != null && !"".equals(tmp[21].toString()) ? tmp[21].toString() : "");
            this.IdentidadGeneroID = (tmp[22] != null && !"".equals(tmp[22].toString()) ? Integer.parseInt(tmp[22].toString()) : null);
        }
        list = null;
        
        // Se cargan habitos
        query = "SELECT HabitoID, ID "
                + "FROM PacienteHabito WHERE PacienteID = " + this.pacienteID;
        List<Object[]> list2 = (List<Object[]>)em.createNativeQuery(query).getResultList();
        int i = 0;
        this.habitos = new String[list2.size()];
        if (list2.size() > 0) {
            for (Object[] tmp2 : list2) {
                this.habitos[i] = (tmp2[0] != null && !"".equals(tmp2[0].toString()) ? tmp2[0].toString() : "");
                i++;
            }
        }
        list2 = null;
       /* List<PacienteHabito> lista = (List<PacienteHabito>)em.createNamedQuery("PacienteHabito.findByPacienteID").setParameter("pacienteID", this.paciente).getResultList();
        int i = 0;
        this.habitos = new String[lista.size()];
        for (PacienteHabito var : lista) {
            this.habitos[i] = var.getHabitoID().getId().toString();
            i++;
        }*/

        // Razon test
        query = "SELECT RazonTestID, ID "
                + "FROM PacienteRazonTest WHERE PacienteID = " + this.pacienteID;
        List<Object[]> list3 = (List<Object[]>)em.createNativeQuery(query).getResultList();
        i = 0;
        this.razones = new String[list3.size()];
        if (list3.size() > 0) {
            for (Object[] tmp3 : list3) {
                this.razones[i] = (tmp3[0] != null && !"".equals(tmp3[0].toString()) ? tmp3[0].toString() : "");
                i++;
            }
        }
        list3 = null;
        /*List<PacienteRazonTest> listb = (List<PacienteRazonTest>)em.createNamedQuery("PacienteRazonTest.findByPacienteID").setParameter("pacienteID", this.paciente).getResultList();
        i = 0;
        this.razones = new String[listb.size()];
        for (PacienteRazonTest var : listb) {
            this.razones[i] = var.getRazonTestID().getId().toString();
            i++;
        }*/
        em.close();
        em = null;
        System.gc();
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

    /**
     * @return the IdentidadGeneroID
     */
    public Integer getIdentidadGeneroID() {
        return IdentidadGeneroID;
    }

    /**
     * @param IdentidadGeneroID the IdentidadGeneroID to set
     */
    public void setIdentidadGeneroID(Integer IdentidadGeneroID) {
        this.IdentidadGeneroID = IdentidadGeneroID;
    }
}
