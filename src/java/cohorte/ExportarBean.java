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
import java.io.PrintWriter;

/**
 *
 * @author Daniel
 */
public class ExportarBean {
    private Util util = new Util();
    @PersistenceUnit 
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("Cohorte2PU");
    @PersistenceContext
    EntityManager em = emf.createEntityManager();
    private Integer CentroID;
    private final HttpServletRequest httpServletRequest;
    private final FacesContext faceContext;
    private List<Object[]> resultados;
    /**
     * Creates a new instance of ExportarBean
     */
    public ExportarBean() {
        this.CentroID=0;
        faceContext=FacesContext.getCurrentInstance();
        httpServletRequest=(HttpServletRequest)faceContext.getExternalContext().getRequest();
        if(httpServletRequest.getSession().getAttribute("Centro") != null){
            this.CentroID=Integer.parseInt(httpServletRequest.getSession().getAttribute("Centro").toString());
        }
    }
    
    public List<Object[]> Controles(){
        String query="SELECT Paciente.Codigo as Codigo, Centro.Nombre as Centro, Control.NumeroControl,Control.Tipo,Control.Fecha,Control.Resultado,Control.PCD4,Control.Logaritmo,Control.Observacion " +
                    " FROM Paciente " +
                    " LEFT JOIN Centro ON Paciente.CentroID=Centro.ID " +
                    " LEFT JOIN Control ON Paciente.ID=Control.PacienteID " +
                    " ORDER BY Paciente.Codigo;";
        EntityManager em = emf.createEntityManager();
        resultados = (List<Object[]>)em.createNativeQuery(query).getResultList();
        em.close();
        return resultados;
    }
    
    public List<Object[]> Laboratorio(){
        String query= "SELECT Paciente.Codigo as Codigo, Centro.Nombre as Centro, Laboratorio.Tipo,Laboratorio.NumeroControl,Laboratorio.Fecha,"
                + "Laboratorio.Diagnostico,Laboratorio.Sistolica,Laboratorio.Diastolica,Laboratorio.ColesTotal,Laboratorio.ColesLdl,Laboratorio.ColesHdl,"
                + "Laboratorio.Trigli,Laboratorio.Glice,Laboratorio.Peso,Laboratorio.Hematocrito,Laboratorio.Gpt,Laboratorio.Got "+
		" FROM Paciente "+
		 " LEFT JOIN Centro ON Paciente.CentroID=Centro.ID "+	
		  " LEFT JOIN Laboratorio ON Paciente.ID=Laboratorio.PacienteID "+
		 " ORDER BY Paciente.Codigo ";
        EntityManager em = emf.createEntityManager();
        resultados = (List<Object[]>)em.createNativeQuery(query).getResultList();
        em.close();
        return resultados;
    }
   
    public String TipoControl(String tipo){
        if(tipo.equals("CD")){
            return "CD4";
        }else if(tipo.equals("CV")){
            return "CV";
        }
        return "";
    }
    
    public String TipoLaboratorio(String tipo){
        if(tipo.equals("HI")){
            return "HIPERTENSIÓN ARTERIAL";
        }else if(tipo.equals("DI")){
            return "DISLIPIDEMIA";
        }else if(tipo.equals("GL")){
            return "GLICEMIA Y DIABETES";
        }else if(tipo.equals("HE")){
            return "HEMATOCRITO Y TRANSAMINASAS";
        }
        return "";
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
    
}
