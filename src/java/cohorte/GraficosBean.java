/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cohorte;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
///import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Daniel
 */
@ManagedBean(name = "graficosBean")
public class GraficosBean {
    private final String backgroundColor = "#F2F2F2";
    private final Integer sliceMargin = 5;
    private final boolean dataLabels = true;
    private final String legendPosition = "2";
    private Integer centroID;
    private Boolean mostrarGrafico;
    private String centroNombre;
    
    private final HttpServletRequest httpServletRequest;
    private final FacesContext faceContext;
    
    private Usuario logueado;
    private Centro logueadoCentro;
    
    @PersistenceUnit 
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("Cohorte2PU");; 
   
    /*@PersistenceContext
    EntityManager em = emf.createEntityManager();
    */
    public GraficosBean(){
        centroID = 0;
        mostrarGrafico = true;
        
        // Carga session
        faceContext=FacesContext.getCurrentInstance();
        httpServletRequest=(HttpServletRequest)faceContext.getExternalContext().getRequest();
        if(httpServletRequest.getSession().getAttribute("Usuario") != null)
        {
            logueado = (Usuario)httpServletRequest.getSession().getAttribute("Usuario");
        }
        
        if(httpServletRequest.getSession().getAttribute("Centro") != null)
        {
           // logueadoCentro = (Centro)httpServletRequest.getSession().getAttribute("Centro");
            centroNombre = httpServletRequest.getSession().getAttribute("CentroNombre").toString().toUpperCase();
            centroID = Integer.parseInt(httpServletRequest.getSession().getAttribute("Centro").toString());
        }
    }

    /**
     * @return the centroID
     */
    public Integer getCentroID() {
        return centroID;
    }

    /**
     * @param centroID the centroID to set
     */
    public void setCentroID(Integer centroID) {
        this.centroID = centroID;
        if (centroID == 0) {
            mostrarGrafico = true;
        } else {
            mostrarGrafico = false;
        }
    }
    
    public String filtrar() {
        return "filtrado";
    }

    /**
     * @return the mostrarGrafico
     */
    public Boolean getMostrarGrafico() {
        return mostrarGrafico;
    }

    /**
     * @param mostrarGrafico the mostrarGrafico to set
     */
    public void setMostrarGrafico(Boolean mostrarGrafico) {
        this.mostrarGrafico = mostrarGrafico;
    }

    /**
     * @return the httpServletRequest
     */
    public HttpServletRequest getHttpServletRequest() {
        return httpServletRequest;
    }

    /**
     * @return the faceContext
     */
    public FacesContext getFaceContext() {
        return faceContext;
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
    
    public String CasosPorAnoInicioTar(){
        if(httpServletRequest.getSession().getAttribute("Centro") != null) {
            centroID = Integer.parseInt(httpServletRequest.getSession().getAttribute("Centro").toString());
        }
        String query = "SELECT YEAR( Fecha ), COUNT( * ) \n" +
"            FROM  `Terapia`, Paciente \n" +
"            WHERE Paciente.ID =Terapia.PacienteID AND NumeroTar = 1 and SexoID is not null\n" +
"            GROUP BY YEAR(Fecha) \n" +
"           ORDER BY YEAR(Fecha) DESC \n" +
"           LIMIT 100";
        if (this.centroID != null && this.centroID != 0) {
            query ="SELECT YEAR( Fecha ), COUNT( * ) \n" +
"            FROM  `Terapia`, Paciente \n" +
"            WHERE Paciente.CentroID = "+this.centroID+" AND Paciente.ID =Terapia.PacienteID AND NumeroTar = 1 and SexoID is not null\n" +
"            GROUP BY YEAR(Fecha) \n" +
"           ORDER BY YEAR(Fecha) DESC \n" +
"           LIMIT 100";
        }
        EntityManager em = emf.createEntityManager();
        List<Object[]> result = em.createNativeQuery(query).getResultList();
        em.close();
        String[] retorno = new String[result.size()];
        int i = result.size() - 1;
        for (Object[] var : result) {
           // if(var[2].toString().equals("1")){
                retorno[i--] = "['"+var[0].toString()+"',"+var[1].toString()+"]";
            //}else{
           //     retorno[i--] = "["+var[0].toString()+","+var[1].toString()+"]";
           // }
        }
        result = null;
        em = null;
        System.gc();
        return String.join(",", retorno);
    }
    
    public String CasosPorPeriodoInicioTar(){
        if(httpServletRequest.getSession().getAttribute("Centro") != null) {
            centroID = Integer.parseInt(httpServletRequest.getSession().getAttribute("Centro").toString());
        }
        String query = "select YEAR(Terapia.Fecha), COUNT(*)\n" +
                        "from Paciente, Terapia \n" +
                        "where Paciente.ID = Terapia.PacienteID AND Terapia.NumeroTar = 1\n" +
                        "group by year(Terapia.Fecha)\n" +
                        "order by year(Terapia.Fecha) desc;";
                        //"limit 20";
        if (this.centroID != null && this.centroID != 0) {
            query = "select YEAR(Terapia.Fecha), COUNT(*)\n" +
                    "from Paciente, Terapia \n" +
                    "where Paciente.ID = Terapia.PacienteID AND Terapia.NumeroTar = 1 and Paciente.CentroID = "+this.centroID+" \n" +
                    "group by year(Terapia.Fecha)\n" +
                    "order by year(Terapia.Fecha) desc;";
        }
        EntityManager em = emf.createEntityManager();
        List<Object[]> result = em.createNativeQuery(query).getResultList();
        em.close();
        String[] retorno = new String[4];
        // Se definene cuatro periodos
        int periodo_1 = 0;
        int periodo_2 = 0;
        int periodo_3 = 0;
        int periodo_4 = 0;
        Date now = new Date();
        int año1 = now.getYear() + 1900;
        
        for (Object[] var : result) {
            if (var[0] != null && var[0].toString() != null) {
                int añoRegistro = Integer.parseInt(var[0].toString());
                if (añoRegistro <= año1 && añoRegistro > año1 - 5) {
                    periodo_1 += Integer.parseInt(var[1].toString());
                }
                if (añoRegistro <= año1 - 5 && añoRegistro > año1 - 10) {
                    periodo_2 += Integer.parseInt(var[1].toString());
                }
                if (añoRegistro <= año1 - 10 && añoRegistro > año1 - 15) {
                    periodo_3 += Integer.parseInt(var[1].toString());
                }
                if (añoRegistro <= año1 - 15) {
                    periodo_4 += Integer.parseInt(var[1].toString());
                }
            }
        }
        result = null;
        em = null;
        System.gc();
        retorno[3] = "['"+(año1 - 4)+"-"+año1+"', "+periodo_1+"]";
        retorno[2] = "['"+(año1 - 9)+"-"+(año1 - 5)+"', "+periodo_2+"]";
        retorno[1] = "['"+(año1 - 14)+"-"+(año1 - 10)+"', "+periodo_3+"]";
        retorno[0] = "['<"+(año1 - 15)+"', "+periodo_4+"]";
        return String.join(",", retorno);
    }
    
    public String ZonaInicioTar(){
        if(httpServletRequest.getSession().getAttribute("Centro") != null) {
            centroID = Integer.parseInt(httpServletRequest.getSession().getAttribute("Centro").toString());
        }
        String query = "SELECT Zona.Nombre, Count(*) FROM Terapia, Paciente, Centro, Zona " +
                       "where Terapia.NumeroTar = 1 and Terapia.PacienteID = Paciente.ID and Paciente.CentroID = Centro.ID and Centro.ZonaID = Zona.ID " +
                       "group by Zona.Nombre";
        if (this.centroID != null && this.centroID != 0) {
            query = "SELECT Zona.Nombre, Count(*) FROM Terapia, Paciente, Centro, Zona " +
                    "where Terapia.NumeroTar = 1 and Terapia.PacienteID = Paciente.ID and Centro.ID = "+this.centroID+" and Paciente.CentroID = Centro.ID and Centro.ZonaID = Zona.ID " +
                    "group by Zona.Nombre";
        }
        EntityManager em = emf.createEntityManager();
        List<Object[]> result = em.createNativeQuery(query).getResultList();
        em.close();
        String[] retorno = new String[result.size()];
        int i = 0;
        for (Object[] var : result) {
            retorno[i++] = "['"+var[0].toString()+"',"+var[1].toString()+"]";
        }
        result = null;
        em = null;
        System.gc();
        return String.join(",", retorno);
    }
    
    public String SexoEnTiempo(){
        if(httpServletRequest.getSession().getAttribute("Centro") != null) {
            centroID = Integer.parseInt(httpServletRequest.getSession().getAttribute("Centro").toString());
        }
        String query = "SELECT Year(Terapia.Fecha), coalesce(sum(Paciente.SexoID = 1), 0), coalesce(sum(Paciente.SexoID = 2), 0), coalesce(sum(Paciente.SexoID is null OR Paciente.SexoID = 3), 0)\n" +
                        "FROM Paciente, Terapia \n" +
                        "WHERE (Paciente.SexoID=1 OR Paciente.SexoID=2) AND Terapia.NumeroTar = 1 AND Terapia.PacienteID = Paciente.ID\n" +
                        "Group by Year(Terapia.Fecha) \n" +
                        "ORDER BY YEAR(Fecha) DESC \n" +
                        "limit 10";
        if (this.centroID != null && this.centroID != 0) {
            query = "SELECT Year(Terapia.Fecha), coalesce(sum(Paciente.SexoID = 1), 0), coalesce(sum(Paciente.SexoID = 2), 0), coalesce(sum(Paciente.SexoID is null OR Paciente.SexoID = 3), 0)\n" +
                        "FROM Paciente, Terapia \n" +
                        "WHERE (Paciente.SexoID=1 OR Paciente.SexoID=2) AND Terapia.NumeroTar = 1 AND Terapia.PacienteID = Paciente.ID AND Paciente.CentroID = "+this.centroID+" \n" +
                        "Group by Year(Terapia.Fecha) \n" +
                        "ORDER BY YEAR(Fecha) DESC \n" +
                        "limit 10";
        }
        EntityManager em = emf.createEntityManager();
        List<Object[]> result = em.createNativeQuery(query).getResultList();
        em.close();
        String[] retorno = new String[result.size()];
        int i = result.size() - 1;
        for (Object[] var : result) {
            retorno[i--] = "['"+var[0].toString()+"',"+var[1].toString()+","+var[2].toString()+"]";
            //retorno[i--] = "['"+var[0].toString()+"',"+var[1].toString()+","+var[2].toString()+", "+var[3].toString()+"]";
        }
        result = null;
        em = null;
        System.gc();
        return String.join(",", retorno);
    }
    
    public String PromedioMedianaCD4(){
        if(httpServletRequest.getSession().getAttribute("Centro") != null) {
            centroID = Integer.parseInt(httpServletRequest.getSession().getAttribute("Centro").toString());
        }
        String query = "select ROUND(AVG(B.CD4)), Count(*), B.Año FROM\n" +
                        "(SELECT DatoBasal.CD4 as CD4, Year(Terapia.Fecha) as Año FROM DatoBasal, Terapia WHERE NumeroTar = 1 AND (CD4 IS NOT NULL AND CD4 > 0) AND Terapia.PacienteID = DatoBasal.PacienteID) B\n" +
                        "GROUP BY B.Año "+
                        "ORDER BY B.Año DESC "+
                        "limit 10";
        if (this.centroID != null && this.centroID != 0) {
            query = "select ROUND(AVG(B.CD4)), Count(*), B.Año FROM\n" +
                    "(SELECT DatoBasal.CD4 as CD4, Year(Terapia.Fecha) as Año FROM DatoBasal, Terapia, Paciente WHERE NumeroTar = 1 AND (DatoBasal.CD4 IS NOT NULL AND DatoBasal.CD4 > 0) AND Terapia.PacienteID = DatoBasal.PacienteID AND Terapia.PacienteID = Paciente.ID AND Paciente.CentroID="+this.centroID+") B\n" +
                    "GROUP BY B.Año "+
                    "ORDER BY B.Año DESC "+
                    "limit 10";
        }
        EntityManager em = emf.createEntityManager();
        List<Object[]> result = em.createNativeQuery(query).getResultList();
        String[] retorno = new String[result.size()];
        int i = result.size() - 1;
        for (Object[] var : result) {
            int mitad = Integer.parseInt(var[1].toString()) / 2;
            String query2 = "Select CD4, DatoBasal.ID FROM DatoBasal, Terapia \n" +
                            "WHERE NumeroTar = 1 AND (CD4 IS NOT NULL AND CD4 > 0) AND Terapia.PacienteID = DatoBasal.PacienteID AND YEAR(Terapia.Fecha) = "+var[2].toString()+"\n" +
                            "ORDER BY DatoBasal.CD4 ASC\n" +
                            "limit "+mitad+", 1";
            if (this.centroID != null && this.centroID != 0) {
                query2 = "Select DatoBasal.CD4, DatoBasal.ID FROM DatoBasal, Terapia, Paciente \n" +
                        "WHERE NumeroTar = 1 AND (DatoBasal.CD4 IS NOT NULL AND DatoBasal.CD4 > 0) AND Terapia.PacienteID = DatoBasal.PacienteID AND DatoBasal.PacienteID = Paciente.ID AND Paciente.CentroID="+this.centroID+" AND YEAR(Terapia.Fecha) = "+var[2].toString()+"\n" +
                        "ORDER BY DatoBasal.CD4 ASC\n" +
                        "limit "+mitad+", 1";
            }
            List<Object[]> result2 = em.createNativeQuery(query2).getResultList();
            for (Object[] var2 : result2) {
                retorno[i--] = "['"+var[2].toString()+"',"+var[0].toString()+","+var2[0].toString()+"]";
            }
        }
        em.close();
        result = null;
        em = null;
        System.gc();
        return String.join(",", retorno);
    }
    public String PromedioMedianaCV(){
        if(httpServletRequest.getSession().getAttribute("Centro") != null) {
            centroID = Integer.parseInt(httpServletRequest.getSession().getAttribute("Centro").toString());
        }
        String query = "select ROUND(AVG(B.CV)), Count(*), B.Año FROM\n" +
                        "(SELECT DatoBasal.CV as CV, Year(Terapia.Fecha) as Año FROM DatoBasal, Terapia WHERE NumeroTar = 1 AND (CV IS NOT NULL AND CV > 0) AND Terapia.PacienteID = DatoBasal.PacienteID) B\n" +
                        "GROUP BY B.Año "+
                        "ORDER BY B.Año DESC "+
                        "limit 10";
        if (this.centroID != null && this.centroID != 0) {
            query = "select ROUND(AVG(B.CV)), Count(*), B.Año FROM\n" +
                    "(SELECT DatoBasal.CV as CV, Year(Terapia.Fecha) as Año FROM DatoBasal, Terapia, Paciente WHERE NumeroTar = 1 AND (DatoBasal.CV IS NOT NULL AND DatoBasal.CV > 0) AND Terapia.PacienteID = DatoBasal.PacienteID AND Terapia.PacienteID = Paciente.ID AND Paciente.CentroID="+this.centroID+") B\n" +
                    "GROUP BY B.Año "+
                    "ORDER BY B.Año DESC "+
                    "limit 10";
        }
        EntityManager em = emf.createEntityManager();
        List<Object[]> result = em.createNativeQuery(query).getResultList();
        String[] retorno = new String[result.size()];
        int i = result.size() - 1;
        for (Object[] var : result) {
            int mitad = Integer.parseInt(var[1].toString()) / 2;
            String query2 = "Select CV, DatoBasal.ID FROM DatoBasal, Terapia \n" +
                            "WHERE NumeroTar = 1 AND (CV IS NOT NULL AND CV > 0) AND Terapia.PacienteID = DatoBasal.PacienteID AND YEAR(Terapia.Fecha) = "+var[2].toString()+"\n" +
                            "ORDER BY DatoBasal.CV ASC\n" +
                            "limit "+mitad+", 1";
            if (this.centroID != null && this.centroID != 0) {
                query2 = "Select DatoBasal.CV, DatoBasal.ID FROM DatoBasal, Terapia, Paciente \n" +
                        "WHERE NumeroTar = 1 AND (DatoBasal.CV IS NOT NULL AND DatoBasal.CV > 0) AND Terapia.PacienteID = DatoBasal.PacienteID AND DatoBasal.PacienteID = Paciente.ID AND Paciente.CentroID="+this.centroID+" AND YEAR(Terapia.Fecha) = "+var[2].toString()+"\n" +
                        "ORDER BY DatoBasal.CV ASC\n" +
                        "limit "+mitad+", 1";
            }
            List<Object[]> result2 = em.createNativeQuery(query2).getResultList();
            for (Object[] var2 : result2) {
                retorno[i--] = "['"+var[2].toString()+"',"+var[0].toString()+","+var2[0].toString()+"]";
            }
        }
        em.close();
        result = null;
        em = null;
        System.gc();
        return String.join(",", retorno);
    }
    
     public String EtapaClinicaInicioTar(){
         if(httpServletRequest.getSession().getAttribute("Centro") != null) {
            centroID = Integer.parseInt(httpServletRequest.getSession().getAttribute("Centro").toString());
        }
        String query = "SELECT coalesce(sum(DatoBasal.ClasificacionL = 'A'), 0) as A, coalesce(sum(DatoBasal.ClasificacionL = 'B'), 0) as B, coalesce(sum(DatoBasal.ClasificacionL = 'C'), 0) as C, coalesce(sum(DatoBasal.ClasificacionL IS NULL or DatoBasal.ClasificacionL=''), 0) as n, Year(Terapia.Fecha), Count(*) " +
                        "FROM DatoBasal, Terapia WHERE NumeroTar = 1 AND Terapia.PacienteID = DatoBasal.PacienteID \n" +
                        "group by year(Terapia.Fecha)\n" +
                        "ORDER BY YEAR(Terapia.Fecha) DESC\n" +
                        "limit 10";
        if (this.centroID != null && this.centroID != 0) {
            query = "SELECT coalesce(sum(DatoBasal.ClasificacionL = 'A'), 0) as A, coalesce(sum(DatoBasal.ClasificacionL = 'B'), 0) as B, coalesce(sum(DatoBasal.ClasificacionL = 'C'), 0) as C, coalesce(sum(DatoBasal.ClasificacionL IS NULL or DatoBasal.ClasificacionL=''), 0) as n, Year(Terapia.Fecha), Count(*) " +
                    "FROM DatoBasal, Terapia, Paciente WHERE NumeroTar = 1 AND Terapia.PacienteID = DatoBasal.PacienteID and DatoBasal.PacienteID = Paciente.ID and Paciente.CentroID = "+this.centroID+" \n" +
                    "group by year(Terapia.Fecha)\n" +
                    "ORDER BY YEAR(Terapia.Fecha) DESC\n" +
                    "limit 10";
        }
        EntityManager em = emf.createEntityManager();
        List<Object[]> result = em.createNativeQuery(query).getResultList();
        em.close();
        String[] retorno = new String[result.size()];
        int i = result.size() - 1;
        for (Object[] var : result) {
            retorno[i--] = "['"+var[4].toString()+"',"+var[0].toString()+","+var[1].toString()+", "+var[2].toString()+", "+var[3].toString()+"]";
        }
        result = null;
        em = null;
        System.gc();
        return String.join(",", retorno);
    }
     
      public String EdadMaximaMinimaMediana(){
          if(httpServletRequest.getSession().getAttribute("Centro") != null) {
            centroID = Integer.parseInt(httpServletRequest.getSession().getAttribute("Centro").toString());
        }
        String query = "select floor(datediff(curdate(),  MAX(Fecha)) / 365), floor(datediff(curdate(),  MIN(Fecha)) / 365), Count(Fecha), B.Año FROM\n" +
                        "(SELECT Paciente.FechaNacimiento as Fecha, Year(Terapia.Fecha) as Año FROM Paciente, Terapia WHERE Terapia.Fecha IS NOT NULL AND Paciente.FechaNacimiento is not null AND NumeroTar = 1 AND Terapia.PacienteID = Paciente.ID) B\n" +
                        "GROUP BY B.Año\n" +
                        "order by B.Año DESC\n" +
                        "limit 10";
        if (this.centroID != null && this.centroID != 0) {
            query = "select floor(datediff(curdate(),  MAX(Fecha)) / 365), floor(datediff(curdate(),  MIN(Fecha)) / 365), Count(Fecha), B.Año FROM\n" +
                    "(SELECT Paciente.FechaNacimiento as Fecha, Year(Terapia.Fecha) as Año FROM Paciente, Terapia WHERE Terapia.Fecha IS NOT NULL AND Paciente.FechaNacimiento is not null AND NumeroTar = 1 AND Terapia.PacienteID = Paciente.ID AND Paciente.CentroID ="+this.centroID+" ) B\n" +
                    "GROUP BY B.Año\n" +
                    "order by B.Año DESC\n" +
                    "limit 10";
        }
        EntityManager em = emf.createEntityManager();
        List<Object[]> result = em.createNativeQuery(query).getResultList();
        String[] retorno = new String[result.size()];
        int i = result.size() - 1;
        for (Object[] var : result) {
            int mitad = Integer.parseInt(var[2].toString()) / 2;
            String query2 = "SELECT floor(datediff(curdate(),  Paciente.FechaNacimiento) / 365), Year(Terapia.Fecha) FROM Paciente, Terapia WHERE NumeroTar = 1 AND Terapia.Fecha IS NOT NULL AND Paciente.FechaNacimiento is not null AND Terapia.PacienteID = Paciente.ID and Year(Terapia.Fecha) = "+var[3].toString()+"\n" +
                            "order by Paciente.FechaNacimiento\n" +
                            "limit "+mitad+", 1";
            if (this.centroID != null && this.centroID != 0) {
                query2 = "SELECT floor(datediff(curdate(),  Paciente.FechaNacimiento) / 365), Year(Terapia.Fecha) FROM Paciente, Terapia WHERE NumeroTar = 1 AND Terapia.Fecha IS NOT NULL AND Paciente.FechaNacimiento is not null AND Terapia.PacienteID = Paciente.ID and Paciente.CentroID = "+this.centroID+" and Year(Terapia.Fecha) = "+var[3].toString()+"\n" +
                        "order by Paciente.FechaNacimiento\n" +
                        "limit "+mitad+", 1";
            }
            List<Object[]> result2 = em.createNativeQuery(query2).getResultList();
            for (Object[] var2 : result2) {
                if(var[3]!=null&&var[0]!=null&&var[1]!=null&&var2[0]!=null){
                    retorno[i--] = "['"+var[3].toString()+"',"+var[0].toString()+","+var[1].toString()+","+var2[0].toString()+"]";
                }
            }
        }
        em.close();
        result = null;
        em = null;
        System.gc();
        return String.join(",", retorno);
    }
    
      
    public String Ppd(){
        if(httpServletRequest.getSession().getAttribute("Centro") != null) {
            centroID = Integer.parseInt(httpServletRequest.getSession().getAttribute("Centro").toString());
        }
        String query = "SELECT COALESCE( SUM( Ppd =  'PO' ) , 0 ) AS Positivo, COALESCE( SUM( Ppd =  'NE' ) , 0 ) AS Negativo, COALESCE( SUM( Ppd =  'NS' ) , 0 ) AS Solicitado, COALESCE( SUM( Ppd =  'NN' ) , 0 ) AS NoSolicitado, COALESCE( SUM( Ppd =  'ND' ) , 0 ) AS NoDisponible\n" +
"FROM DatoBasal";
        if (this.centroID != null && this.centroID != 0) {
            query = "SELECT COALESCE( SUM( Ppd =  'PO' ) , 0 ) AS Positivo, COALESCE( SUM( Ppd =  'NE' ) , 0 ) AS Negativo, COALESCE( SUM( Ppd =  'NS' ) , 0 ) AS Solicitado, COALESCE( SUM( Ppd =  'NN' ) , 0 ) AS NoSolicitado, COALESCE( SUM( Ppd =  'ND' ) , 0 ) AS NoDisponible\n" +
"FROM DatoBasal, Paciente WHERE DatoBasal.PacienteID = Paciente.ID AND Paciente.CentroID = " + this.centroID;
        }
        EntityManager em = emf.createEntityManager();
        List<Object[]> result = em.createNativeQuery(query).getResultList();
        String[] retorno = new String[5];
        for (Object[] var : result) {
            retorno[0] = "['POSITIVO',"+var[0].toString()+"]";
            retorno[1] = "['NEGATIVO',"+var[1].toString()+"]";
            retorno[2] = "['SOLICITADO',"+var[2].toString()+"]";
            retorno[3] = "['NO SOLICITADO',"+var[3].toString()+"]";
            retorno[4] = "['NO DISPONIBLE',"+var[4].toString()+"]";
            break;
        }
        em.close();
        result = null;
        em = null;
        System.gc();
        return String.join(",", retorno);
    }
     
    public String Vhc(){
        if(httpServletRequest.getSession().getAttribute("Centro") != null) {
            centroID = Integer.parseInt(httpServletRequest.getSession().getAttribute("Centro").toString());
        }
        String query = "SELECT COALESCE( SUM( Vhc =  'PO' ) , 0 ) AS Positivo, COALESCE( SUM( Vhc =  'NE' ) , 0 ) AS Negativo, COALESCE( SUM( Vhc =  'NS' ) , 0 ) AS Solicitado, COALESCE( SUM( Vhc =  'NN' ) , 0 ) AS NoSolicitado, COALESCE( SUM( Vhc =  'ND' ) , 0 ) AS NoDisponible FROM DatoBasal;";
        if (this.centroID != null && this.centroID != 0) {
            query = "SELECT COALESCE( SUM( Vhc =  'PO' ) , 0 ) AS Positivo, COALESCE( SUM( Vhc =  'NE' ) , 0 ) AS Negativo, COALESCE( SUM( Vhc =  'NS' ) , 0 ) AS Solicitado, COALESCE( SUM( Vhc =  'NN' ) , 0 ) AS NoSolicitado, COALESCE( SUM( Vhc =  'ND' ) , 0 ) AS NoDisponible "
                    + "FROM DatoBasal, Paciente WHERE DatoBasal.PacienteID = Paciente.ID AND Paciente.CentroID = " + this.centroID;
        }
        EntityManager em = emf.createEntityManager();
        List<Object[]> result = em.createNativeQuery(query).getResultList();
        String[] retorno = new String[5];
        for (Object[] var : result) {
            retorno[0] = "['POSITIVO',"+var[0].toString()+"]";
            retorno[1] = "['NEGATIVO',"+var[1].toString()+"]";
            retorno[2] = "['SOLICITADO',"+var[2].toString()+"]";
            retorno[3] = "['NO SOLICITADO',"+var[3].toString()+"]";
            retorno[4] = "['NO DISPONIBLE',"+var[4].toString()+"]";
            break;
        }
        em.close();
        result = null;
        em = null;
        System.gc();
        return String.join(",", retorno);
    }
    
    public String Vdrl(){
        if(httpServletRequest.getSession().getAttribute("Centro") != null) {
            centroID = Integer.parseInt(httpServletRequest.getSession().getAttribute("Centro").toString());
        }
        String query = "SELECT COALESCE( SUM( Vdrl =  'PO' ) , 0 ) AS Positivo, COALESCE( SUM( Vdrl =  'NE' ) , 0 ) AS Negativo, COALESCE( SUM( Vdrl =  'NS' ) , 0 ) AS Solicitado, COALESCE( SUM( Vdrl =  'NN' ) , 0 ) AS NoSolicitado, COALESCE( SUM( Vdrl =  'ND' ) , 0 ) AS NoDisponible FROM DatoBasal;";
        if (this.centroID != null && this.centroID != 0) {
            query = "SELECT COALESCE( SUM( Vdrl =  'PO' ) , 0 ) AS Positivo, COALESCE( SUM( Vdrl =  'NE' ) , 0 ) AS Negativo, COALESCE( SUM( Vdrl =  'NS' ) , 0 ) AS Solicitado, COALESCE( SUM( Vdrl =  'NN' ) , 0 ) AS NoSolicitado, COALESCE( SUM( Vdrl =  'ND' ) , 0 ) AS NoDisponible "
                    + "FROM DatoBasal, Paciente WHERE DatoBasal.PacienteID = Paciente.ID AND Paciente.CentroID = " + this.centroID;
        }
        EntityManager em = emf.createEntityManager();
        List<Object[]> result = em.createNativeQuery(query).getResultList();
        String[] retorno = new String[5];
        for (Object[] var : result) {
            retorno[0] = "['POSITIVO',"+var[0].toString()+"]";
            retorno[1] = "['NEGATIVO',"+var[1].toString()+"]";
            retorno[2] = "['SOLICITADO',"+var[2].toString()+"]";
            retorno[3] = "['NO SOLICITADO',"+var[3].toString()+"]";
            retorno[4] = "['NO DISPONIBLE',"+var[4].toString()+"]";
            break;
        }
        em.close();
        result = null;
        em = null;
        System.gc();
        return String.join(",", retorno);
    }
    
    public String HBsAg(){
        if(httpServletRequest.getSession().getAttribute("Centro") != null) {
            centroID = Integer.parseInt(httpServletRequest.getSession().getAttribute("Centro").toString());
        }
        String query = "SELECT COALESCE( SUM( Hbs =  'PO' ) , 0 ) AS Positivo, COALESCE( SUM( Hbs =  'NE' ) , 0 ) AS Negativo, COALESCE( SUM( Hbs =  'NS' ) , 0 ) AS Solicitado, COALESCE( SUM( Hbs =  'NN' ) , 0 ) AS NoSolicitado, COALESCE( SUM( Hbs =  'ND' ) , 0 ) AS NoDisponible FROM DatoBasal;";
        if (this.centroID != null && this.centroID != 0) {
            query = "SELECT COALESCE( SUM( Hbs =  'PO' ) , 0 ) AS Positivo, COALESCE( SUM( Hbs =  'NE' ) , 0 ) AS Negativo, COALESCE( SUM( Hbs =  'NS' ) , 0 ) AS Solicitado, COALESCE( SUM( Hbs =  'NN' ) , 0 ) AS NoSolicitado, COALESCE( SUM( Hbs =  'ND' ) , 0 ) AS NoDisponible "
                    + "FROM DatoBasal, Paciente WHERE DatoBasal.PacienteID = Paciente.ID AND Paciente.CentroID = " + this.centroID;
        }
        EntityManager em = emf.createEntityManager();
        List<Object[]> result = em.createNativeQuery(query).getResultList();
        String[] retorno = new String[5];
        for (Object[] var : result) {
            retorno[0] = "['POSITIVO',"+var[0].toString()+"]";
            retorno[1] = "['NEGATIVO',"+var[1].toString()+"]";
            retorno[2] = "['SOLICITADO',"+var[2].toString()+"]";
            retorno[3] = "['NO SOLICITADO',"+var[3].toString()+"]";
            retorno[4] = "['NO DISPONIBLE',"+var[4].toString()+"]";
            break;
        }
        em.close();
        result = null;
        em = null;
        System.gc();
        return String.join(",", retorno);
    }
    
    public String Tox(){
        if(httpServletRequest.getSession().getAttribute("Centro") != null) {
            centroID = Integer.parseInt(httpServletRequest.getSession().getAttribute("Centro").toString());
        }
        String query = "SELECT COALESCE( SUM( Toxoplasmosis =  'PO' ) , 0 ) AS Positivo, COALESCE( SUM( Toxoplasmosis =  'NE' ) , 0 ) AS Negativo, COALESCE( SUM( Toxoplasmosis =  'NS' ) , 0 ) AS Solicitado, COALESCE( SUM( Toxoplasmosis =  'NN' ) , 0 ) AS NoSolicitado, COALESCE( SUM( Toxoplasmosis =  'ND' ) , 0 ) AS NoDisponible FROM DatoBasal;";
        if (this.centroID != null && this.centroID != 0) {
            query = "SELECT COALESCE( SUM( Toxoplasmosis =  'PO' ) , 0 ) AS Positivo, COALESCE( SUM( Toxoplasmosis =  'NE' ) , 0 ) AS Negativo, COALESCE( SUM( Toxoplasmosis =  'NS' ) , 0 ) AS Solicitado, COALESCE( SUM( Toxoplasmosis =  'NN' ) , 0 ) AS NoSolicitado, COALESCE( SUM( Toxoplasmosis =  'ND' ) , 0 ) AS NoDisponible "
                    + "FROM DatoBasal, Paciente WHERE DatoBasal.PacienteID = Paciente.ID AND Paciente.CentroID = " + this.centroID;
        }
        EntityManager em = emf.createEntityManager();
        List<Object[]> result = em.createNativeQuery(query).getResultList();
        String[] retorno = new String[5];
        for (Object[] var : result) {
            retorno[0] = "['POSITIVO',"+var[0].toString()+"]";
            retorno[1] = "['NEGATIVO',"+var[1].toString()+"]";
            retorno[2] = "['SOLICITADO',"+var[2].toString()+"]";
            retorno[3] = "['NO SOLICITADO',"+var[3].toString()+"]";
            retorno[4] = "['NO DISPONIBLE',"+var[4].toString()+"]";
            break;
        }
        em.close();
        result = null;
        em = null;
        System.gc();
        return String.join(",", retorno);
    }
    
    
    public String DistribucionGlobalSexo(){
        if(httpServletRequest.getSession().getAttribute("Centro") != null) {
            centroID = Integer.parseInt(httpServletRequest.getSession().getAttribute("Centro").toString());
        }
        String query = "SELECT COALESCE( SUM( SexoID =  1 ) , 0 ) AS F, COALESCE( SUM( SexoID =  2 ) , 0 ) AS M FROM Paciente";
        if (this.centroID != null && this.centroID != 0) {
            query = "SELECT COALESCE( SUM( SexoID =  1 ) , 0 ) AS F, COALESCE( SUM( SexoID =  2 ) , 0 ) AS M FROM Paciente WHERE CentroID = " + this.centroID;
        }
        EntityManager em = emf.createEntityManager();
        List<Object[]> result = em.createNativeQuery(query).getResultList();
        String[] retorno = new String[2];
        for (Object[] var : result) {
            retorno[0] = "['MASCULINO',"+var[1].toString()+"]";
            retorno[1] = "['FEMENINO',"+var[0].toString()+"]";
            //retorno[2] = "['ND',"+var[2].toString()+"]";
            break;
        }
        em.close();
        result = null;
        em = null;
        System.gc();
        return String.join(",", retorno);
    }
    
    public String DistribucionGenero(){
        if(httpServletRequest.getSession().getAttribute("Centro") != null) {
            centroID = Integer.parseInt(httpServletRequest.getSession().getAttribute("Centro").toString());
        }
        String query = "SELECT PreferenciaSexual.Nombre, Count(*) FROM Paciente, PreferenciaSexual where Paciente.PreferenciaSexualID = PreferenciaSexual.ID " +
                       "group by PreferenciaSexual.Nombre";
        if (this.centroID != null && this.centroID != 0) {
            query = "SELECT PreferenciaSexual.Nombre, Count(*) FROM Paciente, PreferenciaSexual where Paciente.PreferenciaSexualID = PreferenciaSexual.ID AND Paciente.CentroID = " + this.centroID + " " +
                       "group by PreferenciaSexual.Nombre";
        }
        EntityManager em = emf.createEntityManager();
        List<Object[]> result = em.createNativeQuery(query).getResultList();
        String[] retorno = new String[result.size()];
        int i = 0;
        for (Object[] var : result) {
            retorno[i] = "['"+var[0].toString()+"',"+var[1].toString()+"]";
            i++;
        }
        em.close();
        result = null;
        em = null;
        System.gc();
        return String.join(",", retorno);
    }
    
    public String DistribucionRazonTest(){
        if(httpServletRequest.getSession().getAttribute("Centro") != null) {
            centroID = Integer.parseInt(httpServletRequest.getSession().getAttribute("Centro").toString());
        }
        String query = "SELECT RazonTest.Nombre, Count(*) \n" +
                       "FROM RazonTest, PacienteRazonTest, Paciente \n" +
                       "where PacienteRazonTest.RazonTestID = RazonTest.ID and Paciente.ID = PacienteRazonTest.PacienteID\n" +
                       "group by RazonTest.Nombre;";
        if (this.centroID != null && this.centroID != 0) {
            query = "SELECT RazonTest.Nombre, Count(*) \n" +
                    "FROM RazonTest, PacienteRazonTest, Paciente \n" +
                    "where PacienteRazonTest.RazonTestID = RazonTest.ID and Paciente.ID = PacienteRazonTest.PacienteID AND Paciente.CentroID = "+this.centroID+"  \n" +
                    "group by RazonTest.Nombre;";
        }
        EntityManager em = emf.createEntityManager();
        List<Object[]> result = em.createNativeQuery(query).getResultList();
        String[] retorno = new String[result.size()];
        int i = 0;
        for (Object[] var : result) {
            retorno[i] = "['"+var[0].toString()+"',"+var[1].toString()+"]";
            i++;
        }
        em.close();
        result = null;
        em = null;
        System.gc();
        return String.join(",", retorno);
    }
    
    public String CasosValidos(){
        if(httpServletRequest.getSession().getAttribute("Centro") != null) {
            centroID = Integer.parseInt(httpServletRequest.getSession().getAttribute("Centro").toString());
        }
        String query = "SELECT COALESCE( SUM( SexoID =  1 or SexoID =  2) , 0 ) AS V, COALESCE( SUM( SexoID is null or SexoID = 3 ) , 0 ) AS N FROM Paciente";
        if (this.centroID != null && this.centroID != 0) {
            query = "SELECT COALESCE( SUM( SexoID =  1 or SexoID =  2) , 0 ) AS V, COALESCE( SUM( SexoID is null or SexoID = 3 ) , 0 ) AS N FROM Paciente WHERE Paciente.CentroID = "+this.centroID+";";
        }
        EntityManager em = emf.createEntityManager();
        List<Object[]> result = em.createNativeQuery(query).getResultList();
        String[] retorno = new String[2];
        for (Object[] var : result) {
            retorno[0] = "['VÁLIDOS',"+var[0].toString()+"]";
            retorno[1] = "['NO VÁLIDOS',"+var[1].toString()+"]";
            break;
        }
        em.close();
        result = null;
        em = null;
        System.gc();
        return String.join(",", retorno);
    }
    
    public String DiasEspera(){
        String query = "select DATEDIFF(FechaIngreso, FechaISP) as IngresoCentro_ConfirmacionISP, DATEDIFF(Terapia.Fecha, FechaIngreso) as InicioTar_IngresoCentro, DATEDIFF(Terapia.Fecha, FechaISP) as InicioTar_ConfirmacionISP " +
                       "from Paciente, Terapia " +
                       "where Paciente.ID = Terapia.PacienteID AND Terapia.NumeroTar = 1";
        if (this.centroID != null && this.centroID != 0) {
            query = "select DATEDIFF(FechaIngreso, FechaISP) as IngresoCentro_ConfirmacionISP, DATEDIFF(Terapia.Fecha, FechaIngreso) as InicioTar_IngresoCentro, DATEDIFF(Terapia.Fecha, FechaISP) as InicioTar_ConfirmacionISP " +
                    "from Paciente, Terapia " +
                    "where Paciente.ID = Terapia.PacienteID AND Terapia.NumeroTar = 1 AND Paciente.CentroID = " + this.centroID;
        }
        int IngresoCentro_ConfirmacionISP = 0;
        int IngresoCentro_ConfirmacionISP_num = 0;
        int InicioTar_IngresoCentro = 0;
        int InicioTar_IngresoCentro_num = 0;
        int InicioTar_ConfirmacionISP = 0;
        int InicioTar_ConfirmacionISP_num = 0;
        int IngresoCentro_ConfirmacionISP_Mediana = 0;
        int InicioTar_IngresoCentro_Mediana = 0;
        int InicioTar_ConfirmacionISP_Mediana = 0;
        int mitad = 0;
        EntityManager em = emf.createEntityManager();
        List<Object[]> result = em.createNativeQuery(query).getResultList();
        for (Object[] var : result) {
            if (var[0] != null && Integer.parseInt(var[0].toString()) >= 0) {
                IngresoCentro_ConfirmacionISP += Integer.parseInt(var[0].toString());
                IngresoCentro_ConfirmacionISP_num++;
            }
            if (var[1] != null && Integer.parseInt(var[1].toString()) >= 0) {
                InicioTar_IngresoCentro += Integer.parseInt(var[1].toString());
                InicioTar_IngresoCentro_num++;
            }
            if (var[2] != null && Integer.parseInt(var[2].toString()) >= 0) {
                InicioTar_ConfirmacionISP += Integer.parseInt(var[2].toString());
                InicioTar_ConfirmacionISP_num++;
            }
        }
        
        // Calculo de Mediana
        mitad = IngresoCentro_ConfirmacionISP_num / 2;
        query = "select DATEDIFF(FechaIngreso, FechaISP), Paciente.ID " +
                "from Paciente, Terapia  " +
                "where DATEDIFF(FechaIngreso, FechaISP) IS NOT NULL AND DATEDIFF(FechaIngreso, FechaISP) >= 0 AND Paciente.ID = Terapia.PacienteID AND Terapia.NumeroTar = 1 " +
                "order by DATEDIFF(FechaIngreso, FechaISP) " +
                "limit "+mitad+", 1;";
        if (this.centroID != null && this.centroID != 0) {
            query = "select DATEDIFF(FechaIngreso, FechaISP), Paciente.ID " +
                "from Paciente, Terapia  " +
                "where DATEDIFF(FechaIngreso, FechaISP) IS NOT NULL AND DATEDIFF(FechaIngreso, FechaISP) >= 0 AND Paciente.ID = Terapia.PacienteID AND Terapia.NumeroTar = 1 AND Paciente.CentroID = " + this.centroID + " " +
                "order by DATEDIFF(FechaIngreso, FechaISP) " +
                "limit "+mitad+", 1;";
        }
        result = em.createNativeQuery(query).getResultList();
        for (Object[] var : result) {
            if (var[0] != null) {
                IngresoCentro_ConfirmacionISP_Mediana = Integer.parseInt(var[0].toString());
            }
            break;
        }
        
        mitad = InicioTar_IngresoCentro_num / 2;
        query = "select DATEDIFF(Terapia.Fecha, FechaIngreso), Paciente.ID " +
                "from Paciente, Terapia  " +
                "where DATEDIFF(Terapia.Fecha, FechaIngreso) IS NOT NULL AND DATEDIFF(Terapia.Fecha, FechaIngreso) >= 0 AND Paciente.ID = Terapia.PacienteID AND Terapia.NumeroTar = 1 " +
                "order by DATEDIFF(Terapia.Fecha, FechaIngreso) " +
                "limit "+mitad+", 1;";
        if (this.centroID != null && this.centroID != 0) {
            query = "select DATEDIFF(Terapia.Fecha, FechaIngreso), Paciente.ID " +
                "from Paciente, Terapia  " +
                "where DATEDIFF(Terapia.Fecha, FechaIngreso) IS NOT NULL AND DATEDIFF(Terapia.Fecha, FechaIngreso) >= 0 AND Paciente.ID = Terapia.PacienteID AND Terapia.NumeroTar = 1 AND Paciente.CentroID = " + this.centroID + " " +
                "order by DATEDIFF(Terapia.Fecha, FechaIngreso) " +
                "limit "+mitad+", 1;";
        }
        result = em.createNativeQuery(query).getResultList();
        for (Object[] var : result) {
            if (var[0] != null) {
                InicioTar_IngresoCentro_Mediana = Integer.parseInt(var[0].toString());
            }
            break;
        }
        
        mitad = InicioTar_ConfirmacionISP_num / 2;
        query = "select DATEDIFF(Terapia.Fecha, FechaISP), Paciente.ID " +
                "from Paciente, Terapia  " +
                "where DATEDIFF(Terapia.Fecha, FechaISP) IS NOT NULL AND DATEDIFF(Terapia.Fecha, FechaISP) >= 0 AND Paciente.ID = Terapia.PacienteID AND Terapia.NumeroTar = 1 " +
                "order by DATEDIFF(Terapia.Fecha, FechaISP) " +
                "limit "+mitad+", 1;";
        if (this.centroID != null && this.centroID != 0) {
            query = "select DATEDIFF(Terapia.Fecha, FechaISP), Paciente.ID " +
                "from Paciente, Terapia  " +
                "where DATEDIFF(Terapia.Fecha, FechaISP) IS NOT NULL AND DATEDIFF(Terapia.Fecha, FechaISP) >= 0 AND Paciente.ID = Terapia.PacienteID AND Terapia.NumeroTar = 1 AND Paciente.CentroID = " + this.centroID + " " +
                "order by DATEDIFF(Terapia.Fecha, FechaISP) " +
                "limit "+mitad+", 1;";
        }
        result = em.createNativeQuery(query).getResultList();
        for (Object[] var : result) {
            if (var[0] != null) {
                InicioTar_ConfirmacionISP_Mediana = Integer.parseInt(var[0].toString());
            }
            break;
        }
        if (IngresoCentro_ConfirmacionISP_num == 0) {
            IngresoCentro_ConfirmacionISP = 0;
            IngresoCentro_ConfirmacionISP_Mediana = 0;
        } else {
            IngresoCentro_ConfirmacionISP = IngresoCentro_ConfirmacionISP / IngresoCentro_ConfirmacionISP_num;
        }
        if (InicioTar_IngresoCentro_num == 0) {
            InicioTar_IngresoCentro = 0;
            InicioTar_IngresoCentro_Mediana = 0;
        } else {
            InicioTar_IngresoCentro = InicioTar_IngresoCentro / InicioTar_IngresoCentro_num;
        }
        if (InicioTar_ConfirmacionISP_num == 0) {
            InicioTar_ConfirmacionISP = 0;
            InicioTar_ConfirmacionISP_Mediana = 0;
        } else {
            InicioTar_ConfirmacionISP = InicioTar_ConfirmacionISP / InicioTar_ConfirmacionISP_num;
        }
        String[] retorno = new String[3];
        retorno[0] = "['F.ING. CENTRO - F.ISP', "+IngresoCentro_ConfirmacionISP+", "+IngresoCentro_ConfirmacionISP_Mediana+"]";
        retorno[1] = "['F.INIC. TAR - F.ING. CENTRO', "+InicioTar_IngresoCentro+", "+InicioTar_IngresoCentro_Mediana+"]";
        retorno[2] = "['F.INIC. TAR - F.ISP', "+InicioTar_ConfirmacionISP+", "+InicioTar_ConfirmacionISP_Mediana+"]";
        em.close();
        result = null;
        em = null;
        System.gc();
        return String.join(",", retorno);
    }
    
    public String Segmentacion(){
        if(httpServletRequest.getSession().getAttribute("Centro") != null) {
            centroID = Integer.parseInt(httpServletRequest.getSession().getAttribute("Centro").toString());
        }
        String query = "SELECT CONCAT(DatoBasal.ClasificacionL, DatoBasal.ClasificacionN), count(*) \n" +
                        "FROM DatoBasal, Paciente, Terapia\n" +
                        "where Paciente.ID = DatoBasal.PacienteID and Terapia.PacienteID = Paciente.ID and Terapia.NumeroTar = 1 \n" +
                        "group by CONCAT(DatoBasal.ClasificacionL, DatoBasal.ClasificacionN)";
        if (this.centroID != null && this.centroID != 0) {
            query = "SELECT CONCAT(DatoBasal.ClasificacionL, DatoBasal.ClasificacionN), count(*) \n" +
                    "FROM DatoBasal, Paciente, Terapia\n" +
                    "where Paciente.ID = DatoBasal.PacienteID and Terapia.PacienteID = Paciente.ID and Terapia.NumeroTar = 1 AND Paciente.CentroID = "+this.centroID+" "+
                    "group by CONCAT(DatoBasal.ClasificacionL, DatoBasal.ClasificacionN)";
        }
        EntityManager em = emf.createEntityManager();
        List<Object[]> result = em.createNativeQuery(query).getResultList();
        String[] retorno = new String[3];
        int sida = 0;
        int sinto = 0;
        int asinto = 0;
        //int nd = 0;
        for (Object[] var : result) {
            if (var[0] != null && var[0].toString() != null) {
                switch(var[0].toString()){
                    case "A3":
                    case "B3":
                    case "C1":
                    case "C2":
                    case "C3":
                        sida += Integer.parseInt(var[1].toString());
                        break;
                    case "B1":
                    case "B2":
                        sinto += Integer.parseInt(var[1].toString());
                        break;
                    case "A1":
                    case "A2":
                        asinto += Integer.parseInt(var[1].toString());
                        break;
                    default:
                        //nd += Integer.parseInt(var[1].toString());
                }
            }
        }
        retorno[0] = "['SIDA',"+sida+"]";
        retorno[1] = "['SINTOMÁTICOS',"+sinto+"]";
        retorno[2] = "['ASINTOMÁTICOS',"+asinto+"]";
        //retorno[3] = "['ND',"+nd+"]";
        em.close();
        result = null;
        em = null;
        System.gc();
        return String.join(",", retorno);
    }
    
    public String DistribucionSerologias(){
        if(httpServletRequest.getSession().getAttribute("Centro") != null) {
            centroID = Integer.parseInt(httpServletRequest.getSession().getAttribute("Centro").toString());
        }
        String query = "SELECT  Examen, Count(*) FROM `Seguimiento` where Tipo='SE' and Examen is not null and Examen <> '' group by Examen;";
        if (this.centroID != null && this.centroID != 0) {
            query = "SELECT  Examen, Count(*) FROM Seguimiento, Paciente where Paciente.ID = Seguimiento.PacienteID and Paciente.CentroID = "+this.centroID+" AND Tipo='SE' and Examen is not null and Examen <> '' group by Examen;";
        }
        EntityManager em = emf.createEntityManager();
        List<Object[]> result = em.createNativeQuery(query).getResultList();
        String[] retorno = new String[result.size()];
        int i = 0;
        for (Object[] var : result) {
            String label = "";
            switch (var[0].toString()) {
                case "VH":
                    label = "VHC";
                    break;
                case "VD":
                    label = "VDRL";
                    break;
                case "CH":
                    label = "CHAGAS";
                    break;
                case "PA":
                    label = "PAP";
                    break;
                case "HB":
                    label = "HDSAG";
                    break;
            }
            retorno[i] = "['"+label+"',"+var[1].toString()+"]";
            i++;
        }
        em.close();
        result = null;
        em = null;
        System.gc();
        return String.join(",", retorno);
    }
    
    public String PiePacientesCentroTar(){
        if(httpServletRequest.getSession().getAttribute("Centro") != null) {
            centroID = Integer.parseInt(httpServletRequest.getSession().getAttribute("Centro").toString());
        }
        EntityManager em = emf.createEntityManager();
        String query = "SELECT Centro.Nombre, COUNT(*)\n" +
                        " FROM Paciente, Centro \n" +
                        " WHERE Paciente.ID IN (SELECT PacienteID FROM Terapia) AND Paciente.CentroID = Centro.ID "
                        + " GROUP BY Centro.Nombre;";
        if (this.centroID != null && this.centroID != 0) {
            query = "SELECT Centro.Nombre, COUNT(*)\n" +
                    " FROM Paciente, Centro \n" +
                    " WHERE Paciente.ID IN (SELECT PacienteID FROM Terapia) AND Paciente.CentroID = Centro.ID and Centro.ID = "+this.centroID+" "
                    + " GROUP BY Centro.Nombre;";
        }
        List<Object[]> result = em.createNativeQuery(query).getResultList();
        String[] retorno = new String[result.size()];
        int i = 0;
        for (Object[] var : result) {
            retorno[i++] = "['"+var[0].toString()+"',"+var[1].toString()+"]";
        }
        em.close();
        result = null;
        em = null;
        System.gc();
        return String.join(",", retorno);
    }
    
    public String PiePacientesCentroSinTar(){
        if(httpServletRequest.getSession().getAttribute("Centro") != null) {
            centroID = Integer.parseInt(httpServletRequest.getSession().getAttribute("Centro").toString());
        }
        EntityManager em = emf.createEntityManager();
        String query = "SELECT Centro.Nombre, COUNT(*)\n" +
                        " FROM Paciente, Centro \n" +
                        " WHERE Paciente.ID NOT IN (SELECT PacienteID FROM Terapia) AND Paciente.CentroID = Centro.ID "
                        + " GROUP BY Centro.Nombre;";
        if (this.centroID != null && this.centroID != 0) {
            query = "SELECT Centro.Nombre, COUNT(*)\n" +
                    " FROM Paciente, Centro \n" +
                    " WHERE Paciente.ID NOT IN (SELECT PacienteID FROM Terapia) AND Paciente.CentroID = Centro.ID and Centro.ID = "+this.centroID+" "
                    + " GROUP BY Centro.Nombre;";
        }
        List<Object[]> result = em.createNativeQuery(query).getResultList();
        String[] retorno = new String[result.size()];
        int i = 0;
        for (Object[] var : result) {
            retorno[i++] = "['"+var[0].toString()+"',"+var[1].toString()+"]";
        }
        em.close();
        result = null;
        em = null;
        System.gc();
        return String.join(",", retorno);
    }
    
    public String PacientesPorCentroyTar(){
        EntityManager em = emf.createEntityManager();
        if(httpServletRequest.getSession().getAttribute("Centro") != null) {
            centroID = Integer.parseInt(httpServletRequest.getSession().getAttribute("Centro").toString());
        }
        String query = "SELECT Centro.Nombre, Centro.ID FROM Centro ORDER BY Centro.Nombre;";
       /* String query = "SELECT Centro.Nombre, count(*)\n" +
                        " FROM Paciente, Centro\n" +
                        " WHERE Paciente.ID IN (Select PacienteID from Terapia) AND Paciente.CentroID = Centro.ID\n" +
                        " group by Centro.Nombre ORDER BY Centro.Nombre;";*/
        if (this.centroID != null && this.centroID != 0) {
            query = "SELECT Centro.Nombre, Centro.ID FROM Centro WHERE Centro.ID = "+this.centroID+";";
        }
        List<Object[]> result = em.createNativeQuery(query).getResultList();
        String[] retorno = new String[result.size()];
        int i = 0;
        for (Object[] var : result) {
            String c = "0";
            String s = "0";
            String query1 = "SELECT Paciente.CentroID, count(*)\n" +
                        " FROM Paciente \n" +
                        " WHERE Paciente.ID IN (Select PacienteID from Terapia) AND Paciente.CentroID = '"+var[1].toString()+"';";
            List<Object[]> result1 = em.createNativeQuery(query1).getResultList();
            for (Object[] var1 : result1) {
                c = var1[1].toString();
                break;
            }
            String query2 = "SELECT Paciente.CentroID, count(*)\n" +
                        " FROM Paciente \n" +
                        " WHERE Paciente.ID NOT IN (Select PacienteID from Terapia) AND Paciente.CentroID = '"+var[1].toString()+"';";
            List<Object[]> result2 = em.createNativeQuery(query2).getResultList();
            for (Object[] var2 : result2) {
                s = var2[1].toString();
                break;
            }
            if (!"0".equals(c) || !"0".equals(s)) {
                retorno[i++] = "['"+var[0].toString()+"',"+c+","+s+"]";
            }
        }
        String[] retorno2 = new String[i];
        for (int j = 0; j < retorno2.length; j++) {
            retorno2[j] = retorno[j];
        }
        em.close();
        result = null;
        em = null;
        System.gc();
        return String.join(",", retorno2);
    }
    /*
    SELECT Centro.Nombre, count(*)
    FROM Paciente, Centro
    WHERE Paciente.ID IN (Select PacienteID from Terapia) AND Paciente.CentroID = Centro.ID
    group by Centro.Nombre
    */
            
    /*
    SELECT Centro.Nombre, count(*)
    FROM Paciente, Centro
    WHERE Paciente.ID NOT IN (Select PacienteID from Terapia) AND Paciente.CentroID = Centro.ID
    group by Centro.Nombre
    */

    /**
     * @return the centroNombre
     */
    public String getCentroNombre() {
        return centroNombre;
    }

    /**
     * @param centroNombre the centroNombre to set
     */
    public void setCentroNombre(String centroNombre) {
        this.centroNombre = centroNombre;
    }
    
    
    // ADDon tabla resumen
    public String totalPacientes(){
        if(httpServletRequest.getSession().getAttribute("Centro") != null) {
            centroID = Integer.parseInt(httpServletRequest.getSession().getAttribute("Centro").toString());
        }
        String query = "SELECT  COUNT(*),1 \n" +
"            FROM  Paciente";
        if (this.centroID != null && this.centroID != 0) {
            query ="SELECT  COUNT(*),1 \n" +
"            FROM   Paciente \n" +
"            WHERE Paciente.CentroID = "+this.centroID+" ";
        }
        
        EntityManager em = emf.createEntityManager();
        List<Object[]> result = em.createNativeQuery(query).getResultList();
        em.close();
        String r="0";
        for (Object[] var : result) {
                r=var[0].toString();
        }
        result = null;
        em = null;
        System.gc();
        return r;
    }
    
    public String totalHombres(){
        if(httpServletRequest.getSession().getAttribute("Centro") != null) {
            centroID = Integer.parseInt(httpServletRequest.getSession().getAttribute("Centro").toString());
        }
        String query = "SELECT  COUNT(*),1 \n" +
"            FROM  Paciente where SexoID=2";
        if (this.centroID != null && this.centroID != 0) {
            query ="SELECT  COUNT(*),1 \n" +
"            FROM   Paciente \n" +
"            WHERE Paciente.CentroID = "+this.centroID+" and SexoID=2 ";
        }
        
        EntityManager em = emf.createEntityManager();
        List<Object[]> result = em.createNativeQuery(query).getResultList();
        em.close();
        String r="0";
        for (Object[] var : result) {
                r=var[0].toString();
        }
        result = null;
        em = null;
        System.gc();
        return r;
    }
    
    public String totalMujeres(){
        if(httpServletRequest.getSession().getAttribute("Centro") != null) {
            centroID = Integer.parseInt(httpServletRequest.getSession().getAttribute("Centro").toString());
        }
        String query = "SELECT  COUNT(*),1 \n" +
"            FROM  Paciente where SexoID=1";
        if (this.centroID != null && this.centroID != 0) {
            query ="SELECT  COUNT(*),1 \n" +
"            FROM   Paciente \n" +
"            WHERE Paciente.CentroID = "+this.centroID+"  and SexoID=1";
        }
        
        EntityManager em = emf.createEntityManager();
        List<Object[]> result = em.createNativeQuery(query).getResultList();
        em.close();
        String r="0";
        for (Object[] var : result) {
                r=var[0].toString();
        }
        result = null;
        em = null;
        System.gc();
        return r;
    }
    
    public String totalFallecidos(){
        if(httpServletRequest.getSession().getAttribute("Centro") != null) {
            centroID = Integer.parseInt(httpServletRequest.getSession().getAttribute("Centro").toString());
        }
        String query = "select count(*),1 from Paciente where ID IN(select PacienteID from Muerte);";
        if (this.centroID != null && this.centroID != 0) {
            query ="select count(*),1 from Paciente where Paciente.CentroID = "+this.centroID+" AND ID IN(select PacienteID from Muerte);";
        }
        
        EntityManager em = emf.createEntityManager();
        List<Object[]> result = em.createNativeQuery(query).getResultList();
        em.close();
        String r="0";
        for (Object[] var : result) {
                r=var[0].toString();
        }
        result = null;
        em = null;
        System.gc();
        return r;
    }
    
    public String totalSinTar(){
        if(httpServletRequest.getSession().getAttribute("Centro") != null) {
            centroID = Integer.parseInt(httpServletRequest.getSession().getAttribute("Centro").toString());
        }
        String query = "select count(*),1 from Paciente where ID  IN(select PacienteID from Terapia where FechaTermino IS NOT null)";
        if (this.centroID != null && this.centroID != 0) {
            query ="select count(*),1 from Paciente where Paciente.CentroID = "+this.centroID+" AND ID  IN(select PacienteID from Terapia where FechaTermino IS NOT null);";
        }
        
        EntityManager em = emf.createEntityManager();
        List<Object[]> result = em.createNativeQuery(query).getResultList();
        em.close();
        String r="0";
        for (Object[] var : result) {
                r=var[0].toString();
        }
        result = null;
        em = null;
        System.gc();
        return r;
    }
    
    public String totalNoTar(){
        if(httpServletRequest.getSession().getAttribute("Centro") != null) {
            centroID = Integer.parseInt(httpServletRequest.getSession().getAttribute("Centro").toString());
        }
        String query = "select count(*),1 from Paciente where ID NOT IN(select PacienteID from Terapia)";
        if (this.centroID != null && this.centroID != 0) {
            query ="select count(*),1 from Paciente where Paciente.CentroID = "+this.centroID+" AND ID NOT IN(select PacienteID from Terapia);";
        }
        
        EntityManager em = emf.createEntityManager();
        List<Object[]> result = em.createNativeQuery(query).getResultList();
        em.close();
        String r="0";
        for (Object[] var : result) {
                r=var[0].toString();
        }
        result = null;
        em = null;
        System.gc();
        return r;
    }
    
    public String totalTar(){
        if(httpServletRequest.getSession().getAttribute("Centro") != null) {
            centroID = Integer.parseInt(httpServletRequest.getSession().getAttribute("Centro").toString());
        }
        String query = "select count(*),1 from Terapia ";
        if (this.centroID != null && this.centroID != 0) {
            query ="select count(*),1 from Terapia where PacienteID IN (SELECT ID from Paciente where Paciente.CentroID = "+this.centroID+");";
        }
        
        EntityManager em = emf.createEntityManager();
        List<Object[]> result = em.createNativeQuery(query).getResultList();
        em.close();
        String r="0";
        for (Object[] var : result) {
                r=var[0].toString();
        }
        result = null;
        em = null;
        System.gc();
        return r;
    }
    
    public String totalDrogas(){
        if(httpServletRequest.getSession().getAttribute("Centro") != null) {
            centroID = Integer.parseInt(httpServletRequest.getSession().getAttribute("Centro").toString());
        }
        String query = "select count(*),1 from TerapiaDroga where TerapiaID IN(select ID from Terapia);";
        if (this.centroID != null && this.centroID != 0) {
            query ="select count(*),1 from TerapiaDroga where TerapiaID IN(select ID from Terapia where PacienteID IN (SELECT ID from Paciente where Paciente.CentroID = "+this.centroID+"));";
        }
        
        EntityManager em = emf.createEntityManager();
        List<Object[]> result = em.createNativeQuery(query).getResultList();
        em.close();
        String r="0";
        for (Object[] var : result) {
                r=var[0].toString();
        }
        result = null;
        em = null;
        System.gc();
        return r;
    }
    
    public String totalCD4(){
        if(httpServletRequest.getSession().getAttribute("Centro") != null) {
            centroID = Integer.parseInt(httpServletRequest.getSession().getAttribute("Centro").toString());
        }
        String query = "select count(*),1 from Control WHERE Tipo='CD' ";
        if (this.centroID != null && this.centroID != 0) {
            query ="select count(*),1 from Control where Tipo='CD' AND PacienteID IN (SELECT ID from Paciente where Paciente.CentroID = "+this.centroID+");";
        }
        
        EntityManager em = emf.createEntityManager();
        List<Object[]> result = em.createNativeQuery(query).getResultList();
        em.close();
        String r="0";
        for (Object[] var : result) {
                r=var[0].toString();
        }
        result = null;
        em = null;
        System.gc();
        return r;
    }
    
    public String totalCV(){
        if(httpServletRequest.getSession().getAttribute("Centro") != null) {
            centroID = Integer.parseInt(httpServletRequest.getSession().getAttribute("Centro").toString());
        }
        String query = "select count(*),1 from Control WHERE Tipo='CV' ";
        if (this.centroID != null && this.centroID != 0) {
            query ="select count(*),1 from Control where Tipo='CV' AND PacienteID IN (SELECT ID from Paciente where Paciente.CentroID = "+this.centroID+");";
        }
        
        EntityManager em = emf.createEntityManager();
        List<Object[]> result = em.createNativeQuery(query).getResultList();
        em.close();
        String r="0";
        for (Object[] var : result) {
                r=var[0].toString();
        }
        result = null;
        em = null;
        System.gc();
        return r;
    }
    
    public String totalConTar(){
        if(httpServletRequest.getSession().getAttribute("Centro") != null) {
            centroID = Integer.parseInt(httpServletRequest.getSession().getAttribute("Centro").toString());
        }
        String query = "select count(*),1 from Paciente where ID  IN(select PacienteID from Terapia)";
        if (this.centroID != null && this.centroID != 0) {
            query ="select count(*),1 from Paciente where Paciente.CentroID = "+this.centroID+" AND ID  IN(select PacienteID from Terapia);";
        }
        
        EntityManager em = emf.createEntityManager();
        List<Object[]> result = em.createNativeQuery(query).getResultList();
        em.close();
        String r="0";
        for (Object[] var : result) {
                r=var[0].toString();
        }
        result = null;
        em = null;
        System.gc();
        return r;
    }
    
    public String totalConTarHombre(){
        if(httpServletRequest.getSession().getAttribute("Centro") != null) {
            centroID = Integer.parseInt(httpServletRequest.getSession().getAttribute("Centro").toString());
        }
        String query = "select count(*),1 from Paciente where SexoID=2 AND ID IN(select PacienteID from Terapia)";
        if (this.centroID != null && this.centroID != 0) {
            query ="select count(*),1 from Paciente where Paciente.CentroID = "+this.centroID+" AND SexoID=2 AND ID IN(select PacienteID from Terapia);";
        }
        
        EntityManager em = emf.createEntityManager();
        List<Object[]> result = em.createNativeQuery(query).getResultList();
        em.close();
        String r="0";
        for (Object[] var : result) {
                r=var[0].toString();
        }
        result = null;
        em = null;
        System.gc();
        return r;
    }
    
    public String totalConTarMujer(){
        if(httpServletRequest.getSession().getAttribute("Centro") != null) {
            centroID = Integer.parseInt(httpServletRequest.getSession().getAttribute("Centro").toString());
        }
        String query = "select count(*),1 from Paciente where SexoID=1 AND ID IN(select PacienteID from Terapia)";
        if (this.centroID != null && this.centroID != 0) {
            query ="select count(*),1 from Paciente where Paciente.CentroID = "+this.centroID+" AND SexoID=1 AND ID IN(select PacienteID from Terapia);";
        }
        
        EntityManager em = emf.createEntityManager();
        List<Object[]> result = em.createNativeQuery(query).getResultList();
        em.close();
        String r="0";
        for (Object[] var : result) {
                r=var[0].toString();
        }
        result = null;
        em = null;
        System.gc();
        return r;
    }
    
    public String terapiasPaciente(){
        String tar = this.totalConTar();
        if(tar.equals("0")){
            return "0";
        }
        try {
            Double t=Double.parseDouble(this.totalTar());
            Double p=Double.parseDouble(tar);
            Double r=t*1.0/p;
            return String.format("%.1f",r);
        } catch (Exception e) {
            return "0";
        }
    }
    
    public String drogasTerapias(){
        String tar = this.totalTar();
        if(tar.equals("0")){
            return "0";
        }
        try {
            Double t=Double.parseDouble(this.totalDrogas());
            Double p=Double.parseDouble(tar);
            Double r=t*1.0/p;
            return String.format("%.1f",r);
        } catch (Exception e) {
            return "0";
        }
    }
}