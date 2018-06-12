/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cohorte;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author Daniel
 */
@ManagedBean(name = "datoBasalBean") 
@RequestScoped
public class DatoBasalBean {
    private DatoBasal datoBasal;
    private Integer pacienteID;
    /**
     * Creates a new instance of DatoBasalBean
     */
    public DatoBasalBean() {
        this.datoBasal = new DatoBasal();
    }
    
    public DatoBasal getDatoBasal() {
        return this.datoBasal;
    }
    
    public void setDatoBasal(DatoBasal datoBasal) {
        this.datoBasal = datoBasal;
    }
    
    public String cargarBasales() {
        /*List<DatoBasal> datoBasal = (List<DatoBasal>)em.createNamedQuery("DatoBasal.findByPacienteID").setParameter("pacienteID", this.paciente).getResultList();
        if (datoBasal.size() == 0) {
            this.setDatoBasal(new DatoBasal());
        } else {
            this.setDatoBasal(datoBasal.get(0));
        }*/
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
}
