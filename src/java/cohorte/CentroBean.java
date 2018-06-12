/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cohorte;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import cohorte.Centro;
import javax.persistence.EntityManager;

@ManagedBean(name = "centrosBean")
@SessionScoped
public class CentroBean {
    public EntityManager em;
    public List<Centro> getCentros() {
        return (List<Centro>)em.createNamedQuery("findAll").getResultList();
    }
}
