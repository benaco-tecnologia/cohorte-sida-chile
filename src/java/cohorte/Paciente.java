/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cohorte;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Daniel
 */
@Entity
@Table(name = "Paciente")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Paciente.findAll", query = "SELECT p FROM Paciente p"),
    @NamedQuery(name = "Paciente.findById", query = "SELECT p FROM Paciente p WHERE p.id = :id"),
    @NamedQuery(name = "Paciente.findByComunaID", query = "SELECT p FROM Paciente p WHERE p.comunaID = :comunaID"),
    @NamedQuery(name = "Paciente.findByCentroID", query = "SELECT p FROM Paciente p WHERE p.centroID = :centroID"),
    @NamedQuery(name = "Paciente.findByCodigo", query = "SELECT p FROM Paciente p WHERE p.codigo = :codigo"),
    @NamedQuery(name = "Paciente.findByFechaISP", query = "SELECT p FROM Paciente p WHERE p.fechaISP = :fechaISP"),
    @NamedQuery(name = "Paciente.findByRegistroISP", query = "SELECT p FROM Paciente p WHERE p.registroISP = :registroISP"),
    @NamedQuery(name = "Paciente.findByCd4", query = "SELECT p FROM Paciente p WHERE p.cd4 = :cd4"),
    @NamedQuery(name = "Paciente.findByFechaNotificacion", query = "SELECT p FROM Paciente p WHERE p.fechaNotificacion = :fechaNotificacion"),
    @NamedQuery(name = "Paciente.findByFicha", query = "SELECT p FROM Paciente p WHERE p.ficha = :ficha"),
    @NamedQuery(name = "Paciente.findByPreferencia", query = "SELECT p FROM Paciente p WHERE p.preferencia = :preferencia"),
    @NamedQuery(name = "Paciente.findByFechaIngreso", query = "SELECT p FROM Paciente p WHERE p.fechaIngreso = :fechaIngreso"),
    @NamedQuery(name = "Paciente.findByFechaCD4", query = "SELECT p FROM Paciente p WHERE p.fechaCD4 = :fechaCD4"),
    @NamedQuery(name = "Paciente.findByFechaEncuesta", query = "SELECT p FROM Paciente p WHERE p.fechaEncuesta = :fechaEncuesta"),
    @NamedQuery(name = "Paciente.findByRut", query = "SELECT p FROM Paciente p WHERE p.rut = :rut"),
    @NamedQuery(name = "Paciente.findByDv", query = "SELECT p FROM Paciente p WHERE p.dv = :dv"),
    @NamedQuery(name = "Paciente.findByHabitos", query = "SELECT p FROM Paciente p WHERE p.habitos = :habitos"),
    @NamedQuery(name = "Paciente.findByFechaNacimiento", query = "SELECT p FROM Paciente p WHERE p.fechaNacimiento = :fechaNacimiento")})
@SuppressWarnings("JPQLValidation")
public class Paciente implements Serializable {
    @JoinColumn(name = "ComunaID", referencedColumnName = "ID")
    @ManyToOne
    private Comuna comunaID;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pacienteID")
    private List<PacienteRazonTest> pacienteRazonTestList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pacienteID")
    private List<PacienteHabito> pacienteHabitoList;
    @Column(name = "RegistroISP")
    private String registroISP;
    @Column(name = "Ficha")
    private Integer ficha;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pacienteID")
    private List<DatoBasal> datoBasalList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pacienteID")
    private List<EnfermedadBasal> enfermedadBasalList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pacienteID")
    private List<Terapia> terapiaList;
    @JoinColumn(name = "PreferenciaSexualID", referencedColumnName = "ID")
    @ManyToOne
    private PreferenciaSexual preferenciaSexualID;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "Codigo")
    private String codigo;
    @Column(name = "FechaISP")
    @Temporal(TemporalType.DATE)
    private Date fechaISP;
    @Column(name = "CD4")
    private Integer cd4;
    @Column(name = "FechaNotificacion")
    @Temporal(TemporalType.DATE)
    private Date fechaNotificacion;
    @Column(name = "Preferencia")
    private String preferencia;
    @Basic(optional = false)
    @Column(name = "FechaIngreso")
    @Temporal(TemporalType.DATE)
    private Date fechaIngreso;
    @Column(name = "FechaCD4")
    @Temporal(TemporalType.DATE)
    private Date fechaCD4;
    @Column(name = "FechaEncuesta")
    @Temporal(TemporalType.DATE)
    private Date fechaEncuesta;
    @Basic(optional = false)
    @Column(name = "Rut")
    private String rut;
    @Basic(optional = false)
    @Column(name = "DV")
    private String dv;
    @Column(name = "Habitos")
    private String habitos;
    @Column(name = "FechaNacimiento")
    @Temporal(TemporalType.DATE)
    private Date fechaNacimiento;
    @JoinColumn(name = "CentroID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Centro centroID;
    @JoinColumn(name = "EmpleoID", referencedColumnName = "ID")
    @ManyToOne
    private Empleo empleoID;
    @JoinColumn(name = "EtniaID", referencedColumnName = "ID")
    @ManyToOne
    private Etnia etniaID;
    @JoinColumn(name = "FactorRiesgoID", referencedColumnName = "ID")
    @ManyToOne
    private FactorRiesgo factorRiesgoID;
    @JoinColumn(name = "NivelEducacionalID", referencedColumnName = "ID")
    @ManyToOne
    private NivelEducacional nivelEducacionalID;
    @JoinColumn(name = "PaisOrigenID", referencedColumnName = "ID")
    @ManyToOne
    private PaisOrigen paisOrigenID;
    @JoinColumn(name = "RazonTestID", referencedColumnName = "ID")
    @ManyToOne
    private RazonTest razonTestID;
    @JoinColumn(name = "SexoID", referencedColumnName = "ID")
    @ManyToOne
    private Sexo sexoID;
    @JoinColumn(name = "UsoAnticonceptivoID", referencedColumnName = "ID")
    @ManyToOne
    private UsoAnticonceptivo usoAnticonceptivoID;

    

    
    public Paciente() {
        this.id = 0;
    }

    public Paciente(Integer id) {
        this.id = id;
    }

    public Paciente(Integer id, String codigo, Date fechaIngreso, String rut, String dv) {
        this.id = id;
        this.codigo = codigo;
        this.fechaIngreso = fechaIngreso;
        this.rut = rut;
        this.dv = dv;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Comuna getComunaID() {
        return comunaID;
    }

    public void setComunaID(Comuna comunaID) {
        this.comunaID = comunaID;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Date getFechaISP() {
        return fechaISP;
    }

    public void setFechaISP(Date fechaISP) {
        this.fechaISP = fechaISP;
    }


    public Integer getCd4() {
        return cd4;
    }

    public void setCd4(Integer cd4) {
        this.cd4 = cd4;
    }

    public Date getFechaNotificacion() {
        return fechaNotificacion;
    }

    public void setFechaNotificacion(Date fechaNotificacion) {
        this.fechaNotificacion = fechaNotificacion;
    }


    public String getPreferencia() {
        return preferencia;
    }

    public void setPreferencia(String preferencia) {
        this.preferencia = preferencia;
    }

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public Date getFechaCD4() {
        return fechaCD4;
    }

    public void setFechaCD4(Date fechaCD4) {
        this.fechaCD4 = fechaCD4;
    }

    public Date getFechaEncuesta() {
        return fechaEncuesta;
    }

    public void setFechaEncuesta(Date fechaEncuesta) {
        this.fechaEncuesta = fechaEncuesta;
    }

    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public String getDv() {
        return dv;
    }

    public void setDv(String dv) {
        this.dv = dv;
    }

    public String getHabitos() {
        return habitos;
    }

    public void setHabitos(String habitos) {
        this.habitos = habitos;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }


    public Centro getCentroID() {
        return centroID;
    }

    public void setCentroID(Centro centroID) {
        this.centroID = centroID;
    }

    public Empleo getEmpleoID() {
        return empleoID;
    }

    public void setEmpleoID(Empleo empleoID) {
        this.empleoID = empleoID;
    }

    public Etnia getEtniaID() {
        return etniaID;
    }

    public void setEtniaID(Etnia etniaID) {
        this.etniaID = etniaID;
    }

    public FactorRiesgo getFactorRiesgoID() {
        return factorRiesgoID;
    }

    public void setFactorRiesgoID(FactorRiesgo factorRiesgoID) {
        this.factorRiesgoID = factorRiesgoID;
    }

    public NivelEducacional getNivelEducacionalID() {
        return nivelEducacionalID;
    }

    public void setNivelEducacionalID(NivelEducacional nivelEducacionalID) {
        this.nivelEducacionalID = nivelEducacionalID;
    }

    public PaisOrigen getPaisOrigenID() {
        return paisOrigenID;
    }

    public void setPaisOrigenID(PaisOrigen paisOrigenID) {
        this.paisOrigenID = paisOrigenID;
    }

    public RazonTest getRazonTestID() {
        return razonTestID;
    }

    public void setRazonTestID(RazonTest razonTestID) {
        this.razonTestID = razonTestID;
    }

    public Sexo getSexoID() {
        return sexoID;
    }

    public void setSexoID(Sexo sexoID) {
        this.sexoID = sexoID;
    }

    public UsoAnticonceptivo getUsoAnticonceptivoID() {
        return usoAnticonceptivoID;
    }

    public void setUsoAnticonceptivoID(UsoAnticonceptivo usoAnticonceptivoID) {
        this.usoAnticonceptivoID = usoAnticonceptivoID;
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
        if (!(object instanceof Paciente)) {
            return false;
        }
        Paciente other = (Paciente) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cohorte.Paciente[ id=" + id + " ]";
    }

    public PreferenciaSexual getPreferenciaSexualID() {
        return preferenciaSexualID;
    }

    public void setPreferenciaSexualID(PreferenciaSexual preferenciaSexualID) {
        this.preferenciaSexualID = preferenciaSexualID;
    }

    @XmlTransient
    public List<Terapia> getTerapiaList() {
        return terapiaList;
    }

    public void setTerapiaList(List<Terapia> terapiaList) {
        this.terapiaList = terapiaList;
    }

    @XmlTransient
    public List<EnfermedadBasal> getEnfermedadBasalList() {
        return enfermedadBasalList;
    }

    public void setEnfermedadBasalList(List<EnfermedadBasal> enfermedadBasalList) {
        this.enfermedadBasalList = enfermedadBasalList;
    }

    @XmlTransient
    public List<DatoBasal> getDatoBasalList() {
        return datoBasalList;
    }

    public void setDatoBasalList(List<DatoBasal> datoBasalList) {
        this.datoBasalList = datoBasalList;
    }

    public String getRegistroISP() {
        return registroISP;
    }

    public void setRegistroISP(String registroISP) {
        this.registroISP = registroISP;
    }

    public Integer getFicha() {
        return ficha;
    }

    public void setFicha(Integer ficha) {
        this.ficha = ficha;
    }
    
    public void Guardar(){
        
    }

    @XmlTransient
    public List<PacienteHabito> getPacienteHabitoList() {
        return pacienteHabitoList;
    }

    public void setPacienteHabitoList(List<PacienteHabito> pacienteHabitoList) {
        this.pacienteHabitoList = pacienteHabitoList;
    }

    @XmlTransient
    public List<PacienteRazonTest> getPacienteRazonTestList() {
        return pacienteRazonTestList;
    }

    public void setPacienteRazonTestList(List<PacienteRazonTest> pacienteRazonTestList) {
        this.pacienteRazonTestList = pacienteRazonTestList;
    }

}
