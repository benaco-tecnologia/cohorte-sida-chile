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
@Table(name = "RazonToxicidad")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RazonToxicidad.findAll", query = "SELECT r FROM RazonToxicidad r"),
    @NamedQuery(name = "RazonToxicidad.findById", query = "SELECT r FROM RazonToxicidad r WHERE r.id = :id"),
    @NamedQuery(name = "RazonToxicidad.findByNombre", query = "SELECT r FROM RazonToxicidad r WHERE r.nombre = :nombre")})
public class RazonToxicidad implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "Nombre")
    private String nombre;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "razonToxicidadID")
    private List<Terapia> terapiaList;

    public RazonToxicidad() {
    }

    public RazonToxicidad(Integer id) {
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

    @XmlTransient
    public List<Terapia> getTerapiaList() {
        return terapiaList;
    }

    public void setTerapiaList(List<Terapia> terapiaList) {
        this.terapiaList = terapiaList;
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
        if (!(object instanceof RazonToxicidad)) {
            return false;
        }
        RazonToxicidad other = (RazonToxicidad) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cohorte.RazonToxicidad[ id=" + id + " ]";
    }
    
}
