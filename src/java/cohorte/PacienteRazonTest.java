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
@Table(name = "PacienteRazonTest")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PacienteRazonTest.findAll", query = "SELECT p FROM PacienteRazonTest p"),
    @NamedQuery(name = "PacienteRazonTest.findByPacienteID", query = "SELECT p FROM PacienteRazonTest p WHERE p.pacienteID = :pacienteID"),
    @NamedQuery(name = "PacienteRazonTest.findById", query = "SELECT p FROM PacienteRazonTest p WHERE p.id = :id")})
public class PacienteRazonTest implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @JoinColumn(name = "PacienteID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Paciente pacienteID;
    @JoinColumn(name = "RazonTestID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private RazonTest razonTestID;

    public PacienteRazonTest() {
    }

    public PacienteRazonTest(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Paciente getPacienteID() {
        return pacienteID;
    }

    public void setPacienteID(Paciente pacienteID) {
        this.pacienteID = pacienteID;
    }

    public RazonTest getRazonTestID() {
        return razonTestID;
    }

    public void setRazonTestID(RazonTest razonTestID) {
        this.razonTestID = razonTestID;
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
        if (!(object instanceof PacienteRazonTest)) {
            return false;
        }
        PacienteRazonTest other = (PacienteRazonTest) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cohorte.PacienteRazonTest[ id=" + id + " ]";
    }
    
}
