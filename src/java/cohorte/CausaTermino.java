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
@Table(name = "CausaTermino")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CausaTermino.findAll", query = "SELECT c FROM CausaTermino c ORDER BY c.nombre"),
    @NamedQuery(name = "CausaTermino.findById", query = "SELECT c FROM CausaTermino c WHERE c.id = :id"),
    @NamedQuery(name = "CausaTermino.findByNombre", query = "SELECT c FROM CausaTermino c WHERE c.nombre = :nombre")})
public class CausaTermino implements Serializable {
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "causaTerminoID")
    private List<Terapia> terapiaList;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "Nombre")
    private String nombre;

    public CausaTermino() {
    }

    public CausaTermino(Integer id) {
        this.id = id;
    }

    public CausaTermino(Integer id, String nombre) {
        this.id = id;
        this.nombre = nombre;
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
        if (!(object instanceof CausaTermino)) {
            return false;
        }
        CausaTermino other = (CausaTermino) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cohorte.CausaTermino[ id=" + id + " ]";
    }

    @XmlTransient
    public List<Terapia> getTerapiaList() {
        return terapiaList;
    }

    public void setTerapiaList(List<Terapia> terapiaList) {
        this.terapiaList = terapiaList;
    }
    
}
