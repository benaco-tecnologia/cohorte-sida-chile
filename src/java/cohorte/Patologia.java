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
@Table(name = "Patologia")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Patologia.findAll", query = "SELECT p FROM Patologia p"),
    @NamedQuery(name = "Patologia.findById", query = "SELECT p FROM Patologia p WHERE p.id = :id"),
    @NamedQuery(name = "Patologia.findByCodigo", query = "SELECT p FROM Patologia p WHERE p.codigo = :codigo"),
    @NamedQuery(name = "Patologia.findByNombre", query = "SELECT p FROM Patologia p WHERE p.nombre = :nombre"),
    @NamedQuery(name = "Patologia.findByClasificacion", query = "SELECT p FROM Patologia p WHERE p.clasificacion = :clasificacion")})
public class Patologia implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "Codigo")
    private String codigo;
    @Column(name = "Nombre")
    private String nombre;
    @Column(name = "Clasificacion")
    private Character clasificacion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "patologiaID")
    private List<EnfermedadBasal> enfermedadBasalList;

    public Patologia() {
    }

    public Patologia(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Character getClasificacion() {
        return clasificacion;
    }

    public void setClasificacion(Character clasificacion) {
        this.clasificacion = clasificacion;
    }

    @XmlTransient
    public List<EnfermedadBasal> getEnfermedadBasalList() {
        return enfermedadBasalList;
    }

    public void setEnfermedadBasalList(List<EnfermedadBasal> enfermedadBasalList) {
        this.enfermedadBasalList = enfermedadBasalList;
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
        if (!(object instanceof Patologia)) {
            return false;
        }
        Patologia other = (Patologia) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cohorte.Patologia[ id=" + id + " ]";
    }
    
}
