/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cohorte;

import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;

/**
 *
 * @author Daniel
 */
@ManagedBean
@ViewScoped
public class CheckBoxBean {
    @PersistenceUnit 
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("Cohorte2PU");; 
   
    @PersistenceContext
    EntityManager em = emf.createEntityManager();

    /**
     * Creates a new instance of CheckBoxBean
     */
    public CheckBoxBean() {
    }
    
    public List<RazonTest> getRazonTest(){
        return (List<RazonTest>)em.createNamedQuery("Etnia.findAll").getResultList();
    }
    
    
    public Integer getCantidadRazonTest(){
        return ((List<RazonTest>)em.createNamedQuery("RazonTest.findAll").getResultList()).size();
    }
    
}
