/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cohorte;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Daniel
 */
@Entity
@Table(name = "EnfermedadBasal")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EnfermedadBasal.findAll", query = "SELECT e FROM EnfermedadBasal e"),
    @NamedQuery(name = "EnfermedadBasal.findById", query = "SELECT e FROM EnfermedadBasal e WHERE e.id = :id"),
    @NamedQuery(name = "EnfermedadBasal.findByFecha", query = "SELECT e FROM EnfermedadBasal e WHERE e.fecha = :fecha")})
public class EnfermedadBasal implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "Fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @JoinColumn(name = "PacienteID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Paciente pacienteID;
    @JoinColumn(name = "PatologiaID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Patologia patologiaID;

    public EnfermedadBasal() {
    }

    public EnfermedadBasal(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Paciente getPacienteID() {
        return pacienteID;
    }

    public void setPacienteID(Paciente pacienteID) {
        this.pacienteID = pacienteID;
    }

    public Patologia getPatologiaID() {
        return patologiaID;
    }

    public void setPatologiaID(Patologia patologiaID) {
        this.patologiaID = patologiaID;
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
        if (!(object instanceof EnfermedadBasal)) {
            return false;
        }
        EnfermedadBasal other = (EnfermedadBasal) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cohorte.EnfermedadBasal[ id=" + id + " ]";
    }
    
}
