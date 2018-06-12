/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cohorte;

import java.io.Serializable;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
//import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;

/**
 *
 * @author Daniel
 */
@ManagedBean
@ViewScoped
public class ListBoxBean implements Serializable  {
    @PersistenceUnit 
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("Cohorte2PU");; 
   
    /*@PersistenceContext
    EntityManager em = emf.createEntityManager();
    */
    //private List<Etnia> etnias;
    /**
     * Creates a new instance of ListBoxBean
     */
    public ListBoxBean() {
    }
    
    public Map<String,Object> getAños() {
        Calendar calendar = Calendar.getInstance(); 
        int year = calendar.get(Calendar.YEAR);  
        
        Map<String,Object> year2Value = new LinkedHashMap<String,Object>();
        year2Value.put("TODOS", 0);
        for (Integer i = year; i >= 1900; i--) {
            year2Value.put(i.toString(), i); //label, value
        }
        return year2Value;
    }
    
    public Map<String,Object> getMeses() {
        Map<String,Object> year2Value = new LinkedHashMap<String,Object>();
        year2Value.put("TODOS", 0);
        year2Value.put("ENERO", 1);
        year2Value.put("FEBRERO", 2);
        year2Value.put("MARZO", 3);
        year2Value.put("ABRIL", 4);
        year2Value.put("MAYO", 5);
        year2Value.put("JUNIO", 6);
        year2Value.put("JULIO", 7);
        year2Value.put("AGOSTO", 8);
        year2Value.put("SEPTIEMBRE", 9);
        year2Value.put("OCTUBRE", 10);
        year2Value.put("NOVIEMBRE", 11);
        year2Value.put("DICIEMBRE", 12);
        return year2Value;
    }
    
    public Map<String,Object> getSexo() {
        Map<String,Object> listbox = new LinkedHashMap<String,Object>();
        EntityManager em = emf.createEntityManager();
        List<Object[]> list = (List<Object[]>)em.createNativeQuery("Select ID, Nombre FROM Sexo;").getResultList();
        for (Object[] var : list) {
            listbox.put(var[1].toString(), var[0].toString());
        }
        em.close();
        return listbox;
    }
    
    public Map<String,Object> getEtnia(){
        Map<String,Object> listbox = new LinkedHashMap<String,Object>();
        EntityManager em = emf.createEntityManager();
        List<Object[]> list = (List<Object[]>)em.createNativeQuery("Select ID, Nombre FROM Etnia;").getResultList();
        for (Object[] var : list) {
            listbox.put(var[1].toString(), var[0].toString());
        }
        em.close();
        return listbox;
    }
    
    public Map<String,Object> getFactorRiesgo(){
        Map<String,Object> listbox = new LinkedHashMap<String,Object>();
        EntityManager em = emf.createEntityManager();
        List<Object[]> list = (List<Object[]>)em.createNativeQuery("Select ID, Nombre FROM FactorRiesgo;").getResultList();
        for (Object[] var : list) {
            listbox.put(var[1].toString(), var[0].toString());
        }
        em.close();
        return listbox;
    }
    
    public Map<String,Object> getUsoAnticonceptivo(){
        Map<String,Object> listbox = new LinkedHashMap<String,Object>();
        EntityManager em = emf.createEntityManager();
        List<Object[]> list = (List<Object[]>)em.createNativeQuery("Select ID, Nombre FROM UsoAnticonceptivo;").getResultList();
        for (Object[] var : list) {
            listbox.put(var[1].toString(), var[0].toString());
        }
        em.close();
        return listbox;
    }
    
    public Map<String,Object> getPaisOrigen(){
        Map<String,Object> listbox = new LinkedHashMap<String,Object>();
        EntityManager em = emf.createEntityManager();
        List<Object[]> list = (List<Object[]>)em.createNativeQuery("Select ID, Nombre FROM PaisOrigen;").getResultList();
        for (Object[] var : list) {
            listbox.put(var[1].toString(), var[0].toString());
        }
        em.close();
        return listbox;
    }
    
    public Map<String,Object> getPreferenciaSexual(){
        Map<String,Object> listbox = new LinkedHashMap<String,Object>();
        EntityManager em = emf.createEntityManager();
        List<Object[]> list = (List<Object[]>)em.createNativeQuery("Select ID, Nombre FROM PreferenciaSexual;").getResultList();
        for (Object[] var : list) {
            listbox.put(var[1].toString(), var[0].toString());
        }
        em.close();
        return listbox;
    }
    
    public Map<String,Object> getIdentidadGenero(){
        Map<String,Object> listbox = new LinkedHashMap<String,Object>();
        EntityManager em = emf.createEntityManager();
        List<Object[]> list = (List<Object[]>)em.createNativeQuery("Select ID, Nombre FROM IdentidadGenero;").getResultList();
        for (Object[] var : list) {
            listbox.put(var[1].toString(), var[0].toString());
        }
        em.close();
        return listbox;
    }
    
    public Map<String,Object> getDroga(){
        Map<String,Object> listbox = new LinkedHashMap<String,Object>();
        EntityManager em = emf.createEntityManager();
        List<Object[]> list = (List<Object[]>)em.createNativeQuery("Select ID, Nombre FROM Droga;").getResultList();
        for (Object[] var : list) {
            listbox.put(var[1].toString(), var[0].toString());
        }
        em.close();
        return listbox;
    }
    
    public Map<String,Object> getCausaTermino(){
        Map<String,Object> listbox = new LinkedHashMap<String,Object>();
        EntityManager em = emf.createEntityManager();
        List<Object[]> list = (List<Object[]>)em.createNativeQuery("Select ID, Nombre FROM CausaTermino;").getResultList();
        for (Object[] var : list) {
            listbox.put(var[1].toString(), var[0].toString());
        }
        em.close();
        return listbox;
    }
    
    public Map<String,Object> getCausaMuerte(){
        Map<String,Object> listbox = new LinkedHashMap<String,Object>();
        EntityManager em = emf.createEntityManager();
        List<Object[]> list = (List<Object[]>)em.createNativeQuery("Select ID, Nombre FROM CausaMuerte;").getResultList();
        for (Object[] var : list) {
            listbox.put(var[1].toString(), var[0].toString());
        }
        em.close();
        return listbox;
    }
    
    public Map<String,Object> getTipoEvento(){
        Map<String,Object> listbox = new LinkedHashMap<String,Object>();
        EntityManager em = emf.createEntityManager();
        List<Object[]> list = (List<Object[]>)em.createNativeQuery("Select ID, Nombre FROM Evento;").getResultList();
        for (Object[] var : list) {
            listbox.put(var[1].toString(), var[0].toString());
        }
        em.close();
        return listbox;
    }
    
    public Map<String,Object> getComuna(){
        Map<String,Object> listbox = new LinkedHashMap<String,Object>();
        String query = "select Comuna.ID, Comuna.Nombre from Comuna group by Nombre";
        EntityManager em = emf.createEntityManager();
        List<Object[]> list = em.createNativeQuery(query).getResultList();
        for (Object[] var : list) {
            listbox.put(var[1].toString(), var[0].toString());
        }
        em.close();
        return listbox;
    }
    
    
    public Map<String,Object> getRazonToxicidad(){
        Map<String,Object> listbox = new LinkedHashMap<>();
        EntityManager em = emf.createEntityManager();
       List<Object[]> list = (List<Object[]>)em.createNativeQuery("Select ID, Nombre FROM RazonToxicidad;").getResultList();
        for (Object[] var : list) {
            listbox.put(var[1].toString(), var[0].toString());
        }
        em.close();
        return listbox;
    }
    
    public Map<String,Object> getEnfermedad(){
        Map<String,Object> listbox = new LinkedHashMap<>();
        EntityManager em = emf.createEntityManager();
        List<Object[]> list = (List<Object[]>)em.createNativeQuery("Select ID, Nombre FROM Patologia;").getResultList();
        for (Object[] var : list) {
            listbox.put(var[1].toString(), var[0].toString());
        }
        em.close();
        return listbox;
    }
    
    public Map<String,Object> getEnfermedadCode(){
        Map<String,Object> listbox = new LinkedHashMap<>();
        EntityManager em = emf.createEntityManager();
        List<Object[]> list = (List<Object[]>)em.createNativeQuery("Select ID, Nombre, Clasificacion FROM Patologia;").getResultList();
        for (Object[] var : list) {
            listbox.put(var[1].toString() + " | " + var[2].toString(), var[0].toString());
        }
        em.close();
        return listbox;
    }
    
    public Map<String,Object> getCentro(){
        Map<String,Object> listbox = new LinkedHashMap<>();
        EntityManager em = emf.createEntityManager();
        List<Object[]> list = (List<Object[]>)em.createNativeQuery("Select ID, Nombre FROM Centro;").getResultList();
        for (Object[] var : list) {
            listbox.put(var[1].toString(), var[0].toString());
        }
        em.close();
        return listbox;
    }
    
    public Map<String,Object> getEmpleo(){
        Map<String,Object> listbox = new LinkedHashMap<>();
        EntityManager em = emf.createEntityManager();
        List<Object[]> list = (List<Object[]>)em.createNativeQuery("Select ID, Nombre FROM Empleo;").getResultList();
        for (Object[] var : list) {
            listbox.put(var[1].toString(), var[0].toString());
        }
        em.close();
        return listbox;
    }
    
    public Map<String,Object> getNivelEducacional(){
        Map<String,Object> listbox = new LinkedHashMap<>();
        EntityManager em = emf.createEntityManager();
        List<Object[]> list = (List<Object[]>)em.createNativeQuery("Select ID, Nombre FROM NivelEducacional;").getResultList();
        for (Object[] var : list) {
            listbox.put(var[1].toString(), var[0].toString());
        }
        em.close();
        return listbox;
    }
    
    // Checkbox
    public Map<String,Object> getRazonTest(){
        Map<String,Object> listbox = new LinkedHashMap<>();
        EntityManager em = emf.createEntityManager();
        List<Object[]> list = (List<Object[]>)em.createNativeQuery("Select ID, Nombre FROM RazonTest;").getResultList();
        for (Object[] var : list) {
            listbox.put(var[1].toString(), var[0].toString());
        }
        em.close();
        return listbox;
    }
    // Checkbox
    public Map<String,Object> getHabito(){
        Map<String,Object> listbox = new LinkedHashMap<>();
        EntityManager em = emf.createEntityManager();
        List<Object[]> list = (List<Object[]>)em.createNativeQuery("Select ID, Nombre FROM Habito;").getResultList();
        for (Object[] var : list) {
            listbox.put(var[1].toString(), var[0].toString());
        }
        em.close();
        return listbox;
    }
    
    public String patologia(String value){
        if (!value.equals("")) {
            String query = "select Nombre, ID FROM Patologia WHERE ID = " + value;
            EntityManager em = emf.createEntityManager();
            List<Object[]> list = em.createNativeQuery(query).getResultList();
            em.close();
            for (Object[] var : list) {
               return var[0].toString();
            }
            return "SIN DATOS";
        }
        return "SIN DATOS";
    }
    
    public String examen(String value){
        switch(value) {
            case "VH":
                return "VHC";
            case "VD":
                return "VDRL";
            case "CH":
                return "CHAGAS";
            case "PA":
                return "PAP";
            case "HB":
                return "HBSAG";
        }
        return "SIN DATOS";
    }
}
