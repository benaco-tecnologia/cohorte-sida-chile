/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cohorte;

import java.io.Serializable;
import java.util.Collection;
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
@Table(name = "RazonTest")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RazonTest.findAll", query = "SELECT r FROM RazonTest r ORDER BY r.nombre"),
    @NamedQuery(name = "RazonTest.findById", query = "SELECT r FROM RazonTest r WHERE r.id = :id"),
    @NamedQuery(name = "RazonTest.findByNombre", query = "SELECT r FROM RazonTest r WHERE r.nombre = :nombre")})
public class RazonTest implements Serializable {
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "razonTestID")
    private List<PacienteRazonTest> pacienteRazonTestList;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "Nombre")
    private String nombre;
    @OneToMany(mappedBy = "razonTestID")
    private Collection<Paciente> pacienteCollection;

    public RazonTest() {
    }

    public RazonTest(Integer id) {
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
    public Collection<Paciente> getPacienteCollection() {
        return pacienteCollection;
    }

    public void setPacienteCollection(Collection<Paciente> pacienteCollection) {
        this.pacienteCollection = pacienteCollection;
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
        if (!(object instanceof RazonTest)) {
            return false;
        }
        RazonTest other = (RazonTest) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cohorte.RazonTest[ id=" + id + " ]";
    }

    @XmlTransient
    public List<PacienteRazonTest> getPacienteRazonTestList() {
        return pacienteRazonTestList;
    }

    public void setPacienteRazonTestList(List<PacienteRazonTest> pacienteRazonTestList) {
        this.pacienteRazonTestList = pacienteRazonTestList;
    }
    
}
