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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
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
@Table(name = "Terapia")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Terapia.findAll", query = "SELECT t FROM Terapia t"),
    @NamedQuery(name = "Terapia.findById", query = "SELECT t FROM Terapia t WHERE t.id = :id"),
    @NamedQuery(name = "Terapia.findByNumeroTar", query = "SELECT t FROM Terapia t WHERE t.numeroTar = :numeroTar"),
    @NamedQuery(name = "Terapia.findByFecha", query = "SELECT t FROM Terapia t WHERE t.fecha = :fecha"),
    @NamedQuery(name = "Terapia.findByNoContinua", query = "SELECT t FROM Terapia t WHERE t.noContinua = :noContinua"),
    @NamedQuery(name = "Terapia.findByFechaTermino", query = "SELECT t FROM Terapia t WHERE t.fechaTermino = :fechaTermino"),
    @NamedQuery(name = "Terapia.findByGeno", query = "SELECT t FROM Terapia t WHERE t.geno = :geno"),
    @NamedQuery(name = "Terapia.findByGenoFecha", query = "SELECT t FROM Terapia t WHERE t.genoFecha = :genoFecha"),
    @NamedQuery(name = "Terapia.findByPacienteID", query = "SELECT t FROM Terapia t WHERE t.pacienteID = :pacienteID"),
    @NamedQuery(name = "Terapia.findByTropismo", query = "SELECT t FROM Terapia t WHERE t.tropismo = :tropismo")})
public class Terapia implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "NumeroTar")
    private int numeroTar;
    @Column(name = "Fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Column(name = "NoContinua")
    private String noContinua;
    @Column(name = "FechaTermino")
    @Temporal(TemporalType.DATE)
    private Date fechaTermino;
    @Column(name = "Geno")
    private String geno;
    @Column(name = "GenoFecha")
    @Temporal(TemporalType.DATE)
    private Date genoFecha;
    @Lob
    @Column(name = "GenoObs")
    private String genoObs;
    @Column(name = "Tropismo")
    private String tropismo;
    @JoinColumn(name = "CausaTerminoID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private CausaTermino causaTerminoID;
    @JoinColumn(name = "PacienteID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Paciente pacienteID;
    @JoinColumn(name = "RazonToxicidadID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private RazonToxicidad razonToxicidadID;

    public Terapia() {
    }

    public Terapia(Integer id) {
        this.id = id;
    }

    public Terapia(Integer id, int numeroTar) {
        this.id = id;
        this.numeroTar = numeroTar;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getNumeroTar() {
        return numeroTar;
    }

    public void setNumeroTar(int numeroTar) {
        this.numeroTar = numeroTar;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getNoContinua() {
        return noContinua;
    }

    public void setNoContinua(String noContinua) {
        this.noContinua = noContinua;
    }

    public Date getFechaTermino() {
        return fechaTermino;
    }

    public void setFechaTermino(Date fechaTermino) {
        this.fechaTermino = fechaTermino;
    }

    public String getGeno() {
        return geno;
    }

    public void setGeno(String geno) {
        this.geno = geno;
    }

    public Date getGenoFecha() {
        return genoFecha;
    }

    public void setGenoFecha(Date genoFecha) {
        this.genoFecha = genoFecha;
    }

    public String getGenoObs() {
        return genoObs;
    }

    public void setGenoObs(String genoObs) {
        this.genoObs = genoObs;
    }

    public String getTropismo() {
        return tropismo;
    }

    public void setTropismo(String tropismo) {
        this.tropismo = tropismo;
    }

    public CausaTermino getCausaTerminoID() {
        return causaTerminoID;
    }

    public void setCausaTerminoID(CausaTermino causaTerminoID) {
        this.causaTerminoID = causaTerminoID;
    }

    public Paciente getPacienteID() {
        return pacienteID;
    }

    public void setPacienteID(Paciente pacienteID) {
        this.pacienteID = pacienteID;
    }

    public RazonToxicidad getRazonToxicidadID() {
        return razonToxicidadID;
    }

    public void setRazonToxicidadID(RazonToxicidad razonToxicidadID) {
        this.razonToxicidadID = razonToxicidadID;
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
        if (!(object instanceof Terapia)) {
            return false;
        }
        Terapia other = (Terapia) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cohorte.Terapia[ id=" + id + " ]";
    }
    
}
