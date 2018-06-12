/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cohorte;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
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
@Table(name = "UsoAnticonceptivo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UsoAnticonceptivo.findAll", query = "SELECT u FROM UsoAnticonceptivo u ORDER BY u.nombre"),
    @NamedQuery(name = "UsoAnticonceptivo.findById", query = "SELECT u FROM UsoAnticonceptivo u WHERE u.id = :id"),
    @NamedQuery(name = "UsoAnticonceptivo.findByNombre", query = "SELECT u FROM UsoAnticonceptivo u WHERE u.nombre = :nombre")})
public class UsoAnticonceptivo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "Nombre")
    private String nombre;
    @OneToMany(mappedBy = "usoAnticonceptivoID")
    private Collection<Paciente> pacienteCollection;

    public UsoAnticonceptivo() {
    }

    public UsoAnticonceptivo(Integer id) {
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
        if (!(object instanceof UsoAnticonceptivo)) {
            return false;
        }
        UsoAnticonceptivo other = (UsoAnticonceptivo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cohorte.UsoAnticonceptivo[ id=" + id + " ]";
    }
    
}
