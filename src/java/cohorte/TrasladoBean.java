/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cohorte;

import java.util.List;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Daniel
 */
public class TrasladoBean {
    private Integer pacienteID;
    private String resultado = "";
    private String codigo;
    private Boolean pacienteSeleccionado;
    
    // Datos traslado
    private Integer trasladoID;
    private Integer origenID;
    private Integer destinoID;
    private Integer solicitaID;
    private Integer supervisaID;
    private String observacion;
    private String comentario;
    private String estado;
    public Usuario usuario;
    private String FechaTraslado;
    
    private final HttpServletRequest httpServletRequest;
    private final FacesContext faceContext;
    
    private Util util = new Util();
    private List<Object[]> resultados;
    //private List<Object[]> pendientes; 
    
    @PersistenceUnit 
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("Cohorte2PU");; 
   
    @PersistenceContext
    EntityManager em = emf.createEntityManager();
    
    /**
     * Creates a new instance of TrasladoBean
     */
    public TrasladoBean() {
        faceContext = FacesContext.getCurrentInstance();
        httpServletRequest = (HttpServletRequest)faceContext.getExternalContext().getRequest();
        if(httpServletRequest.getSession().getAttribute("Usuario") != null)
        {
            this.usuario = (Usuario)httpServletRequest.getSession().getAttribute("Usuario");
        }
    }
    
    public String cargarTraslado() {
        String value = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("pacienteIDH");
        try {
            this.setPacienteSeleccionado((Boolean) true);
            this.setPacienteID((Integer) Integer.parseInt(value));
        } catch (Exception e) {
            //this.setPacienteID((Integer) 0);
            this.setPacienteSeleccionado((Boolean) false);
        }
        
        if (this.pacienteID != null && this.pacienteID > 0) {
            this.setPacienteSeleccionado((Boolean) true);
        } else {
            this.pacienteID = 0;
        }
        
        String query = "SELECT Codigo, CentroID, ID "
                + "FROM Paciente "
                + "WHERE ID = " + this.pacienteID;
        List<Object[]> list = (List<Object[]>)em.createNativeQuery(query).getResultList();
        for (Object[] var : list) {
            this.codigo = var[0].toString();
            this.origenID = Integer.parseInt(var[1].toString());
            break;
        }
        
        listarTraslados();
        
        this.resultado = "Cargado paciente " + this.pacienteID + " solicitante " + this.usuario.getId();
        return "encontrado";
    }
    
    public String solicitarTraslado() {
        String query = "SELECT Codigo, CentroID, ID "
                + "FROM Paciente "
                + "WHERE ID = " + this.pacienteID;
        List<Object[]> list = (List<Object[]>)em.createNativeQuery(query).getResultList();
        for (Object[] var : list) {
            this.codigo = var[0].toString();
            this.origenID = Integer.parseInt(var[1].toString());
            break;
        }
        
        if (this.destinoID == null) {
            this.resultado = "No hay destino seleccionado.";
            return "encontrado";
        }
        
        if (this.destinoID == this.origenID) {
            this.resultado = "Destino no puede ser igual a origen.";
            return "encontrado";
        }
        
        
        query = "INSERT INTO Traslado "
                + "(PacienteID, SolicitaID, Comentario, OrigenID, DestinoID, Fecha) VALUES "
                + "("+this.pacienteID+","
                + ""+this.usuario.getId()+","
                + ""+(this.comentario != null && !"".equals(this.comentario) ? "'"+this.comentario+"'" : "NULL")+","
                + ""+this.origenID+","
                + ""+this.destinoID+","
                + "'"+this.FechaTraslado+"'"
                + ");";
        
        em.getTransaction().begin();
        em.createNativeQuery(query).executeUpdate();
        em.getTransaction().commit();
            
        this.resultado = "Traslado solicitado paciente " + this.pacienteID + "  ---- " + query;
        this.comentario = "";
        this.destinoID = null;
        listarTraslados();
        return "encontrado";
    }
    
    public void listarTraslados() {
        String query = "SELECT * "
                + "FROM Traslado "
                + "WHERE PacienteID = " + this.getPacienteID() + " ORDER BY ID DESC;";
        this.resultados = (List<Object[]>)em.createNativeQuery(query).getResultList();
    }
    
    public List<Object[]> pendientes() {
        String query = "SELECT * "
                + "FROM Traslado "
                + "WHERE SupervisaID IS NULL AND Estado IS NULL ORDER BY ID DESC;";
        return (List<Object[]>)em.createNativeQuery(query).getResultList();
    }
    
    public String aceptar() {
        em.getTransaction().begin();
        String query = "UPDATE Traslado SET Estado = 'A', Observacion='"+this.observacion+"', SupervisaID="+this.usuario.getId()+" WHERE ID = " + this.trasladoID;
        em.createNativeQuery(query).executeUpdate();
        em.getTransaction().commit();
         em.getTransaction().begin();
        query = "UPDATE Paciente SET CentroID="+this.destinoID+" WHERE ID = " + this.pacienteID;
        em.createNativeQuery(query).executeUpdate();
        em.getTransaction().commit();
        this.observacion="";
        this.trasladoID=null;
        this.pacienteID=null;
        this.destinoID=null;
        return "aceptado";
    }
    
    public String rechazar() {
        em.getTransaction().begin(); 
        String query = "UPDATE Traslado SET Estado = 'R', Observacion='"+this.observacion+"', SupervisaID="+this.usuario.getId()+" WHERE ID = " + this.trasladoID;
        em.createNativeQuery(query).executeUpdate();
        em.getTransaction().commit();
        this.observacion="";
        this.trasladoID=null;
        this.pacienteID=null;
        this.destinoID=null;
        return "aceptado";
    }
    
     public String eliminar() {
        em.getTransaction().begin(); 
        String query = "DELETE FROM Traslado WHERE ID = " + this.trasladoID;
        em.createNativeQuery(query).executeUpdate();
        em.getTransaction().commit();
        
        query = "SELECT Codigo, CentroID, ID "
                + "FROM Paciente "
                + "WHERE ID = " + this.pacienteID;
        List<Object[]> list = (List<Object[]>)em.createNativeQuery(query).getResultList();
        for (Object[] var : list) {
            this.codigo = var[0].toString();
            this.origenID = Integer.parseInt(var[1].toString());
            break;
        }
        
        listarTraslados();
        
        this.resultado = "traspaso eliminado " + this.pacienteID + " ID " + this.trasladoID;
        this.trasladoID=null;
        return "eliminado";
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
     * @return the trasladoID
     */
    public Integer getTrasladoID() {
        return trasladoID;
    }

    /**
     * @param trasladoID the trasladoID to set
     */
    public void setTrasladoID(Integer trasladoID) {
        this.trasladoID = trasladoID;
    }

    /**
     * @return the origenID
     */
    public Integer getOrigenID() {
        return origenID;
    }

    /**
     * @param origenID the origenID to set
     */
    public void setOrigenID(Integer origenID) {
        this.origenID = origenID;
    }

    /**
     * @return the destinoID
     */
    public Integer getDestinoID() {
        return destinoID;
    }

    /**
     * @param destinoID the destinoID to set
     */
    public void setDestinoID(Integer destinoID) {
        this.destinoID = destinoID;
    }

    /**
     * @return the solicitaID
     */
    public Integer getSolicitaID() {
        return solicitaID;
    }

    /**
     * @param solicitaID the solicitaID to set
     */
    public void setSolicitaID(Integer solicitaID) {
        this.solicitaID = solicitaID;
    }

    /**
     * @return the supervisaID
     */
    public Integer getSupervisaID() {
        return supervisaID;
    }

    /**
     * @param supervisaID the supervisaID to set
     */
    public void setSupervisaID(Integer supervisaID) {
        this.supervisaID = supervisaID;
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
     * @return the comentario
     */
    public String getComentario() {
        return comentario;
    }

    /**
     * @param comentario the comentario to set
     */
    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    /**
     * @return the estado
     */
    public String getEstado() {
        return estado;
    }

    /**
     * @param estado the estado to set
     */
    public void setEstado(String estado) {
        this.estado = estado;
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
     * @return the FechaTraslado
     */
    public String getFechaTraslado() {
        return FechaTraslado;
    }

    /**
     * @param FechaTraslado the FechaTraslado to set
     */
    public void setFechaTraslado(String FechaTraslado) {
        this.FechaTraslado = FechaTraslado;
    }
}
