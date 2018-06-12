/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cohorte;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Daniel
 */
@Entity
@Table(name = "PacienteHabito")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PacienteHabito.findAll", query = "SELECT p FROM PacienteHabito p"),
    @NamedQuery(name = "PacienteHabito.findByPacienteID", query = "SELECT p FROM PacienteHabito p WHERE p.pacienteID = :pacienteID"),
    @NamedQuery(name = "PacienteHabito.findById", query = "SELECT p FROM PacienteHabito p WHERE p.id = :id")})
public class PacienteHabito implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @JoinColumn(name = "HabitoID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Habito habitoID;
    @JoinColumn(name = "PacienteID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Paciente pacienteID;

    public PacienteHabito() {
    }

    public PacienteHabito(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Habito getHabitoID() {
        return habitoID;
    }

    public void setHabitoID(Habito habitoID) {
        this.habitoID = habitoID;
    }

    public Paciente getPacienteID() {
        return pacienteID;
    }

    public void setPacienteID(Paciente pacienteID) {
        this.pacienteID = pacienteID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PacienteHabito)) {
            return false;
        }
        PacienteHabito other = (PacienteHabito) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cohorte.PacienteHabito[ id=" + id + " ]";
    }
    
}
