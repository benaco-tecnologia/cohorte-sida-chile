/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cohorte;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Daniel
 */
@Entity
@Table(name = "Habito")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Habito.findAll", query = "SELECT h FROM Habito h"),
    @NamedQuery(name = "Habito.findById", query = "SELECT h FROM Habito h WHERE h.id = :id"),
    @NamedQuery(name = "Habito.findByNombre", query = "SELECT h FROM Habito h WHERE h.nombre = :nombre")})
public class Habito implements Serializable {
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "habitoID")
    private List<PacienteHabito> pacienteHabitoList;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "Nombre")
    private String nombre;

    public Habito() {
    }

    public Habito(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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
        if (!(object instanceof Habito)) {
            return false;
        }
        Habito other = (Habito) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cohorte.Habito[ id=" + id + " ]";
    }

    @XmlTransient
    public List<PacienteHabito> getPacienteHabitoList() {
        return pacienteHabitoList;
    }

    public void setPacienteHabitoList(List<PacienteHabito> pacienteHabitoList) {
        this.pacienteHabitoList = pacienteHabitoList;
    }
    
}
