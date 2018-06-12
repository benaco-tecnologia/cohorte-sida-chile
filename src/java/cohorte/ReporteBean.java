package cohorte;

import java.util.LinkedList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Daniel
 */
public class ReporteBean {
    private List<Object[]> resultados;
    @PersistenceUnit 
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("Cohorte2PU");; 
   
    @PersistenceContext
    EntityManager em = emf.createEntityManager();

    /**
     * Creates a new instance of ReporteBean
     */
    public ReporteBean() {
        
    }
    
    public String laboratorioAgrupadoCentro() {
        setResultados(new LinkedList<Object[]>());
        String query = "";
        String groupBy = "";
        String querySQL = ""; 
        String select = "";
        String select_interno = "";
        
        querySQL = "SELECT ID, Nombre FROM Centro ORDER BY Nombre";
        
        setResultados((List<Object[]>)em.createNativeQuery(querySQL).getResultList());
        return "generado";
    }
    
    public String terapiaAgrupadoCentro() {
        setResultados(new LinkedList<Object[]>());
        String query = "";
        String groupBy = "";
        String querySQL = ""; 
        String select = "";
        String select_interno = "";
        
        querySQL = "SELECT ID, Nombre FROM Centro ORDER BY Nombre";
        
        setResultados((List<Object[]>)em.createNativeQuery(querySQL).getResultList());
        return "generado";
    }
    
    public String mcd4(String var) {
        if (var != null && !var.equals("")) {
            Query q = em.createNativeQuery("SELECT COUNT(*) AS Cantidad FROM Paciente p, Control c WHERE p.CentroID = ? AND p.SexoID = 2 AND c.PacienteID = p.ID AND Tipo = 'CD';").setParameter(1, new Integer(var));
            Long value = ((Long) q.getSingleResult());
            if (value > 0) {
                return value.toString();
            }
        }
        return "0";
    }
    
    public String tmcd4() {
        //if (var != null && !var.equals("")) {
            Query q = em.createNativeQuery("SELECT COUNT(*) AS Cantidad FROM Paciente p, Control c WHERE p.SexoID = 2 AND c.PacienteID = p.ID AND Tipo = 'CD';");
            Long value = ((Long) q.getSingleResult());
            if (value > 0) {
                return value.toString();
            }
        //}
        return "0";
    }
    
    public String fcd4(String var) {
        if (var != null && !var.equals("")) {
            Query q = em.createNativeQuery("SELECT COUNT(*) AS Cantidad FROM Paciente p, Control c WHERE p.CentroID = ? AND p.SexoID = 1 AND c.PacienteID = p.ID AND Tipo = 'CD';").setParameter(1, new Integer(var));
            Long value = ((Long) q.getSingleResult());
            if (value > 0) {
                return value.toString();
            }
        }
        return "0";
    }
    
    public String tfcd4() {
        //if (var != null && !var.equals("")) {
            Query q = em.createNativeQuery("SELECT COUNT(*) AS Cantidad FROM Paciente p, Control c WHERE p.SexoID = 1 AND c.PacienteID = p.ID AND Tipo = 'CD';");
            Long value = ((Long) q.getSingleResult());
            if (value > 0) {
                return value.toString();
            }
        //}
        return "0";
    }
    
    public String mcv(String var) {
        if (var != null && !var.equals("")) {
            Query q = em.createNativeQuery("SELECT COUNT(*) AS Cantidad FROM Paciente p, Control c WHERE p.CentroID = ? AND p.SexoID = 2 AND c.PacienteID = p.ID AND Tipo = 'CV';").setParameter(1, new Integer(var));
            Long value = ((Long) q.getSingleResult());
            if (value > 0) {
                return value.toString();
            }
        }
        return "0";
    }
    
    public String tmcv() {
        //if (var != null && !var.equals("")) {
            Query q = em.createNativeQuery("SELECT COUNT(*) AS Cantidad FROM Paciente p, Control c WHERE p.SexoID = 2 AND c.PacienteID = p.ID AND Tipo = 'CV';");
            Long value = ((Long) q.getSingleResult());
            if (value > 0) {
                return value.toString();
            }
       // }
        return "0";
    }
    
    public String fcv(String var) {
        if (var != null && !var.equals("")) {
            Query q = em.createNativeQuery("SELECT COUNT(*) AS Cantidad FROM Paciente p, Control c WHERE p.CentroID = ? AND p.SexoID = 1 AND c.PacienteID = p.ID AND Tipo = 'CV';").setParameter(1, new Integer(var));
            Long value = ((Long) q.getSingleResult());
            if (value > 0) {
                return value.toString();
            }
        }
        return "0";
    }
    
    public String tfcv() {
       // if (var != null && !var.equals("")) {
            Query q = em.createNativeQuery("SELECT COUNT(*) AS Cantidad FROM Paciente p, Control c WHERE p.SexoID = 1 AND c.PacienteID = p.ID AND Tipo = 'CV';");
            Long value = ((Long) q.getSingleResult());
            if (value > 0) {
                return value.toString();
            }
        //}
        return "0";
    }
    
    public String tcv(String var) {
        if (var != null && !var.equals("")) {
            Query q = em.createNativeQuery("SELECT COUNT(*) AS Cantidad FROM Paciente p, Control c WHERE p.CentroID = ? AND c.PacienteID = p.ID AND Tipo = 'CV';").setParameter(1, new Integer(var));
            Long value = ((Long) q.getSingleResult());
            if (value > 0) {
                return value.toString();
            }
        }
        return "0";
    }
    
    public String ttcv() {
        //if (var != null && !var.equals("")) {
            Query q = em.createNativeQuery("SELECT COUNT(*) AS Cantidad FROM Control c WHERE c.Tipo = 'CV';");
            Long value = ((Long) q.getSingleResult());
            if (value > 0) {
                return value.toString();
            }
       // }
        return "0";
    }
    
     public String ttcd4() {
        //if (var != null && !var.equals("")) {
            Query q = em.createNativeQuery("SELECT COUNT(*) AS Cantidad FROM Control c WHERE c.Tipo = 'CD';");
            Long value = ((Long) q.getSingleResult());
            if (value > 0) {
                return value.toString();
            }
        //}
        return "0";
    }
    
    public String tcd4(String var) {
        if (var != null && !var.equals("")) {
            Query q = em.createNativeQuery("SELECT COUNT(*) AS Cantidad FROM Paciente p, Control c WHERE p.CentroID = ? AND c.PacienteID = p.ID AND Tipo = 'CD';").setParameter(1, new Integer(var));
            Long value = ((Long) q.getSingleResult());
            if (value > 0) {
                return value.toString();
            }
        }
        return "0";
    }
    
    
    // Terapias
    
    public String mt(String var) {
        if (var != null && !var.equals("")) {
            Query q = em.createNativeQuery("SELECT COUNT(*) AS Cantidad FROM Paciente p, Terapia t WHERE p.CentroID = ? AND p.SexoID = 2 AND t.PacienteID = p.ID;").setParameter(1, new Integer(var));
            Long value = ((Long) q.getSingleResult());
            if (value > 0) {
                return value.toString();
            }
        }
        return "0";
    }
    
    public String ft(String var) {
        if (var != null && !var.equals("")) {
            Query q = em.createNativeQuery("SELECT COUNT(*) AS Cantidad FROM Paciente p, Terapia t WHERE p.CentroID = ? AND p.SexoID = 1 AND t.PacienteID = p.ID;").setParameter(1, new Integer(var));
            Long value = ((Long) q.getSingleResult());
            if (value > 0) {
                return value.toString();
            }
        }
        return "0";
    }
    
    public String tt(String var) {
        if (var != null && !var.equals("")) {
            Query q = em.createNativeQuery("SELECT COUNT(*) AS Cantidad FROM Paciente p, Terapia t WHERE p.CentroID = ? AND t.PacienteID = p.ID;").setParameter(1, new Integer(var));
            Long value = ((Long) q.getSingleResult());
            if (value > 0) {
                return value.toString();
            }
        }
        return "0";
    }
    
    public String tmt() {
        //if (var != null && !var.equals("")) {
            Query q = em.createNativeQuery("SELECT COUNT(*) AS Cantidad FROM Paciente p, Terapia t WHERE p.SexoID = 2 AND t.PacienteID = p.ID;");
            Long value = ((Long) q.getSingleResult());
            if (value > 0) {
                return value.toString();
            }
        //}
        return "0";
    }
    
    public String tft() {
        //if (var != null && !var.equals("")) {
            Query q = em.createNativeQuery("SELECT COUNT(*) AS Cantidad FROM Paciente p, Terapia t WHERE p.SexoID = 1 AND t.PacienteID = p.ID;");
            Long value = ((Long) q.getSingleResult());
            if (value > 0) {
                return value.toString();
            }
        //}
        return "0";
    }
    
    public String ttt() {
        //if (var != null && !var.equals("")) {
            Query q = em.createNativeQuery("SELECT COUNT(*) AS Cantidad FROM Paciente p, Terapia t WHERE t.PacienteID = p.ID;");
            Long value = ((Long) q.getSingleResult());
            if (value > 0) {
                return value.toString();
            }
       // }
        return "0";
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
    
}
