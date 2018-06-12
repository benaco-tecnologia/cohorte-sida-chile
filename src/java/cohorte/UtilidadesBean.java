package cohorte;
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
public class UtilidadesBean {
    @PersistenceUnit 
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("Cohorte2PU");; 
   
    @PersistenceContext
    EntityManager em = emf.createEntityManager();
    /**
     * Creates a new instance of utilidadesBean
     */
    public UtilidadesBean() {
    }
    
    public String centro(String var) {
        try {
            if (var != null && !var.equals("")) {
                Query q = em.createNativeQuery("SELECT Nombre FROM Centro WHERE ID = ?").setParameter(1, new Integer(var));
                return ((String) q.getSingleResult());
            }
            return "SIN DATOS";
        } catch (Exception e) {
            return "SIN DATOS";
        }
    }
    
    public String usuario(String var) {
        try {
            if (var != null && !var.equals("")) {
                Query q = em.createNativeQuery("SELECT Nombre FROM Usuario WHERE ID = ?").setParameter(1, new Integer(var));
                return ((String) q.getSingleResult());
            }
            return "SIN DATOS";
        } catch (Exception e) {
            return "SIN DATOS";
        }
    }
    
    public String codigo(String var) {
        try {
            if (var != null && !var.equals("")) {
                Query q = em.createNativeQuery("SELECT Codigo FROM Paciente WHERE ID = ?").setParameter(1, new Integer(var));
                return ((String) q.getSingleResult());
            }
            return "SIN DATOS";
        } catch (Exception e) {
            return "SIN DATOS";
        }
    }
    
    
    public String factorRiesgo(String var) {
        try {
            if (var != null && !var.equals("")) {
                Query q = em.createNativeQuery("SELECT Nombre FROM FactorRiesgo WHERE ID = ?").setParameter(1, new Integer(var));
                return ((String) q.getSingleResult());
            }
            return "SIN DATOS";
        } catch (Exception e) {
            return "SIN DATOS";
        }
    }
    
    public String usoAnticonceptivo(String var) {
        try {
            if (var != null && !var.equals("")) {
                Query q = em.createNativeQuery("SELECT Nombre FROM UsoAnticonceptivo WHERE ID = ?").setParameter(1, new Integer(var));
                return ((String) q.getSingleResult());
            }
            return "SIN DATOS";
        } catch (Exception e) {
            return "SIN DATOS";
        }

    }
    
    public String identidadGenero(String var) {
        try {
            if (var != null && !var.equals("")) {
                Query q = em.createNativeQuery("SELECT Nombre FROM IdentidadGenero WHERE ID = ?").setParameter(1, new Integer(var));
                return ((String) q.getSingleResult());
            }
            return "SIN DATOS";
        } catch (Exception e) {
            return "SIN DATOS";
        }

    }
    
    public String preferenciaSexual(String var) {
        try {
            if (var != null && !var.equals("")) {
                Query q = em.createNativeQuery("SELECT Nombre FROM PreferenciaSexual WHERE ID = ?").setParameter(1, new Integer(var));
                return ((String) q.getSingleResult());
            }
            return "SIN DATOS";
        } catch (Exception e) {
            return "SIN DATOS";
        }
    }
    
    public String paisOrigen(String var) {
        try {
            if (var != null && !var.equals("")) {
                Query q = em.createNativeQuery("SELECT Nombre FROM PaisOrigen WHERE ID = ?").setParameter(1, new Integer(var));
                return ((String) q.getSingleResult());
            }
            return "SIN DATOS";
        } catch (Exception e) {
            return "SIN DATOS";
        }
    }
    
    public String comuna(String var) {
        try {
            if (var != null && !var.equals("")) {
                Query q = em.createNativeQuery("SELECT Nombre FROM Comuna WHERE ID = ?").setParameter(1, new Integer(var));
                return ((String) q.getSingleResult());
            }
            return "SIN DATOS";
        } catch (Exception e) {
            return "SIN DATOS";
        }
    }
    
    public String etnia(String var) {
        try {
            if (var != null && !var.equals("")) {
                Query q = em.createNativeQuery("SELECT Nombre FROM Etnia WHERE ID = ?").setParameter(1, new Integer(var));
                return ((String) q.getSingleResult());
            }
            return "SIN DATOS";
        } catch (Exception e) {
            return "SIN DATOS";
        }
    }
    
    public String empleo(String var) {
        try {
            if (var != null && !var.equals("")) {
                Query q = em.createNativeQuery("SELECT Nombre FROM Empleo WHERE ID = ?").setParameter(1, new Integer(var));
                return ((String) q.getSingleResult());
            }
            return "SIN DATOS";
        } catch (Exception e) {
            return "SIN DATOS";
        }
    }
    
    public String nivelEducacional(String var) {
        try {
            if (var != null && !var.equals("")) {
                Query q = em.createNativeQuery("SELECT Nombre FROM NivelEducacional WHERE ID = ?").setParameter(1, new Integer(var));
                return ((String) q.getSingleResult());
            }
            return "SIN DATOS";
        } catch (Exception e) {
            return "SIN DATOS";
        }
    }
    
    public String razonTest(String var) {
        try {
            if (var != null && !var.equals("")) {
                Query q = em.createNativeQuery("SELECT Nombre FROM RazonTest WHERE ID = ?").setParameter(1, new Integer(var));
                return ((String) q.getSingleResult());
            }
            return "SIN DATOS";
        } catch (Exception e) {
            return "SIN DATOS";
        }
    }
    
    public String habitos(String var) {
        try {
            if (var != null && !var.equals("")) {
                Query q = em.createNativeQuery("SELECT Nombre FROM Habito WHERE ID = ?").setParameter(1, new Integer(var));
                return ((String) q.getSingleResult());
            }
            return "SIN DATOS";
        } catch (Exception e) {
            return "SIN DATOS";
        }
    }
    
    public String enfermedad(String var) {
        try {
            if (var != null && !var.equals("")) {
                Query q = em.createNativeQuery("SELECT Nombre FROM Patologia WHERE ID = ?").setParameter(1, new Integer(var));
                return ((String) q.getSingleResult());
            }
            return "SIN DATOS";
        } catch (Exception e) {
            return "SIN DATOS";
        }
    }
    
    public String nacionalidad(String var) {
        return paisOrigen(var);
    }
    
    public String siNo(String var) {
        if (var != null && !"".equals(var)) {
            switch (var) {
                case "S":
                    return "SI";
                case "N":
                    return "NO";
            }
        }
        return "SIN DATOS";
    }
    
    public String sexo(Integer var) {
        if (var != null && var > 0) {
            switch (var) {
                case 1:
                    return "FEMENINO";
                case 2:
                    return "MASCULINO";
            }
        }
        return "SIN DATOS";
    }
    
    public String resultadoTipo1(String var) {
        if (var != null && !"".equals(var)) {
            switch (var) {
                case "PO":
                    return "POSITIVO";
                case "NE":
                    return "NEGATIVO";
                case "NS":
                    return "SOLICITADO, NO REALIZADO";
                case "NN":
                    return "NO DISPONIBLE NO SOLICITADO";
            }
        }
        return "SIN DATOS";
    }
    
    public String resultadoTipo2(String var) {
        if (var != null && !"".equals(var)) {
            switch (var) {
                case "NO":
                    return "NORMAL";
                case "AS":
                    return "ANORMAL SIN ATIPIAS";
                case "NC":
                    return "ANORMAL ATIPIAS Y CÁNCER";
                case "SN":
                    return "SOLICITADO NO DISPONIBLE";
                case "ND":
                    return "NO DISPONIBLE";
                case "NR":
                    return "NO REALIZADO";
            }
        }
        return "SIN DATOS";
    }
    
    public String resultadoDiag(String var) {
        if (var != null && !"".equals(var)) {
            switch (var) {
                case "SC":
                    return "SÍ, CON FÁRMACOS";
                case "SS":
                    return "SÍ, SIN FÁRMACOS";
                case "NO":
                    return "NO";
                case "ND":
                    return "NO DISPONIBLE";
            }
        }
        return "SIN DATOS";
    }
    
    public String resultadoG(String var) {
        if (var != null && !"".equals(var)) {
            switch (var) {
                case "NO":
                    return "NORMAL";
                case "l2X":
                    return "<2X";
                case "l3X":
                    return "<3X";
                case "l5X":
                    return "<5X";
                case "l10X":
                    return "<10Xs";
                case "g10X":
                    return ">10X";
                case "ND":
                    return "NO DISPONIBLE";
            }
        }
        return "SIN DATOS";
    }
    
    public String razon(String[] var) {
        if (var.length == 0) 
            return "SIN DATOS";
        
        String respuesta = "";
        for (String tmp : var) {
            if (!tmp.equals(""))
                respuesta += razonTest(tmp) + "<br />";
        }
        
        if (respuesta.length() > 6)
            return respuesta.substring(0, respuesta.length() - 6);
        return "SIN DATOS";
    }
    
    public String habito(String[] var) {
        if (var.length == 0) 
            return "SIN DATOS";
        
        String respuesta = "";
        for (String tmp : var) {
            if (!tmp.equals(""))
                respuesta += habitos(tmp) + "<br />";
        }
        
        if (respuesta.length() > 6)
            return respuesta.substring(0, respuesta.length() - 6);
        return "SIN DATOS";
    }
    
}
