/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cohorte;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Daniel
 */
@Entity
@Table(name = "DatoBasal")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DatoBasal.findAll", query = "SELECT d FROM DatoBasal d"),
    @NamedQuery(name = "DatoBasal.findById", query = "SELECT d FROM DatoBasal d WHERE d.id = :id"),
    @NamedQuery(name = "DatoBasal.findByClasificacionL", query = "SELECT d FROM DatoBasal d WHERE d.clasificacionL = :clasificacionL"),
    @NamedQuery(name = "DatoBasal.findByCd4", query = "SELECT d FROM DatoBasal d WHERE d.cd4 = :cd4"),
    @NamedQuery(name = "DatoBasal.findByCv", query = "SELECT d FROM DatoBasal d WHERE d.cv = :cv"),
    @NamedQuery(name = "DatoBasal.findByHla", query = "SELECT d FROM DatoBasal d WHERE d.hla = :hla"),
    @NamedQuery(name = "DatoBasal.findByPpd", query = "SELECT d FROM DatoBasal d WHERE d.ppd = :ppd"),
    @NamedQuery(name = "DatoBasal.findByHbs", query = "SELECT d FROM DatoBasal d WHERE d.hbs = :hbs"),
    @NamedQuery(name = "DatoBasal.findByVhc", query = "SELECT d FROM DatoBasal d WHERE d.vhc = :vhc"),
    @NamedQuery(name = "DatoBasal.findByToxoplasmosis", query = "SELECT d FROM DatoBasal d WHERE d.toxoplasmosis = :toxoplasmosis"),
    @NamedQuery(name = "DatoBasal.findByVdrl", query = "SELECT d FROM DatoBasal d WHERE d.vdrl = :vdrl"),
    @NamedQuery(name = "DatoBasal.findByChagas", query = "SELECT d FROM DatoBasal d WHERE d.chagas = :chagas"),
    @NamedQuery(name = "DatoBasal.findByPap", query = "SELECT d FROM DatoBasal d WHERE d.pap = :pap"),
    @NamedQuery(name = "DatoBasal.findByClasificacionN", query = "SELECT d FROM DatoBasal d WHERE d.clasificacionN = :clasificacionN"),
    @NamedQuery(name = "DatoBasal.findByPcd4", query = "SELECT d FROM DatoBasal d WHERE d.pcd4 = :pcd4"),
    @NamedQuery(name = "DatoBasal.findByLog", query = "SELECT d FROM DatoBasal d WHERE d.log = :log"),
    @NamedQuery(name = "DatoBasal.findByTratamiento", query = "SELECT d FROM DatoBasal d WHERE d.tratamiento = :tratamiento"),
    @NamedQuery(name = "DatoBasal.findByRxTorax", query = "SELECT d FROM DatoBasal d WHERE d.rxTorax = :rxTorax"),
    @NamedQuery(name = "DatoBasal.findByExamenOrina", query = "SELECT d FROM DatoBasal d WHERE d.examenOrina = :examenOrina"),
    @NamedQuery(name = "DatoBasal.findByPeso", query = "SELECT d FROM DatoBasal d WHERE d.peso = :peso"),
    @NamedQuery(name = "DatoBasal.findByTalla", query = "SELECT d FROM DatoBasal d WHERE d.talla = :talla"),
    @NamedQuery(name = "DatoBasal.findByImc", query = "SELECT d FROM DatoBasal d WHERE d.imc = :imc"),
    @NamedQuery(name = "DatoBasal.findBySiges", query = "SELECT d FROM DatoBasal d WHERE d.siges = :siges"),
    @NamedQuery(name = "DatoBasal.findByHipertencion", query = "SELECT d FROM DatoBasal d WHERE d.hipertencion = :hipertencion"),
    @NamedQuery(name = "DatoBasal.findByHipertencionFecha", query = "SELECT d FROM DatoBasal d WHERE d.hipertencionFecha = :hipertencionFecha"),
    @NamedQuery(name = "DatoBasal.findBySistolica", query = "SELECT d FROM DatoBasal d WHERE d.sistolica = :sistolica"),
    @NamedQuery(name = "DatoBasal.findByDiastolica", query = "SELECT d FROM DatoBasal d WHERE d.diastolica = :diastolica"),
    @NamedQuery(name = "DatoBasal.findByDislipidemias", query = "SELECT d FROM DatoBasal d WHERE d.dislipidemias = :dislipidemias"),
    @NamedQuery(name = "DatoBasal.findByDislipidemiasFecha", query = "SELECT d FROM DatoBasal d WHERE d.dislipidemiasFecha = :dislipidemiasFecha"),
    @NamedQuery(name = "DatoBasal.findByColesTotal", query = "SELECT d FROM DatoBasal d WHERE d.colesTotal = :colesTotal"),
    @NamedQuery(name = "DatoBasal.findByColesLdl", query = "SELECT d FROM DatoBasal d WHERE d.colesLdl = :colesLdl"),
    @NamedQuery(name = "DatoBasal.findByColesHdl", query = "SELECT d FROM DatoBasal d WHERE d.colesHdl = :colesHdl"),
    @NamedQuery(name = "DatoBasal.findByTrigi", query = "SELECT d FROM DatoBasal d WHERE d.trigi = :trigi"),
    @NamedQuery(name = "DatoBasal.findByGlicemia", query = "SELECT d FROM DatoBasal d WHERE d.glicemia = :glicemia"),
    @NamedQuery(name = "DatoBasal.findByGlicemiaFecha", query = "SELECT d FROM DatoBasal d WHERE d.glicemiaFecha = :glicemiaFecha"),
    @NamedQuery(name = "DatoBasal.findByGlice", query = "SELECT d FROM DatoBasal d WHERE d.glice = :glice"),
    @NamedQuery(name = "DatoBasal.findByHematocrito", query = "SELECT d FROM DatoBasal d WHERE d.hematocrito = :hematocrito"),
    @NamedQuery(name = "DatoBasal.findByHematocritoFecha", query = "SELECT d FROM DatoBasal d WHERE d.hematocritoFecha = :hematocritoFecha"),
    @NamedQuery(name = "DatoBasal.findByGpt", query = "SELECT d FROM DatoBasal d WHERE d.gpt = :gpt"),
    @NamedQuery(name = "DatoBasal.findByPacienteID", query = "SELECT d FROM DatoBasal d WHERE d.pacienteID = :pacienteID"),
    @NamedQuery(name = "DatoBasal.findByGot", query = "SELECT d FROM DatoBasal d WHERE d.got = :got")})
public class DatoBasal implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "ClasificacionL")
    private String clasificacionL;
    @Column(name = "CD4")
    private Integer cd4;
    @Column(name = "CV")
    private BigInteger cv;
    @Column(name = "Hla")
    private String hla;
    @Column(name = "Ppd")
    private String ppd;
    @Column(name = "Hbs")
    private String hbs;
    @Column(name = "Vhc")
    private String vhc;
    @Column(name = "Toxoplasmosis")
    private String toxoplasmosis;
    @Column(name = "Vdrl")
    private String vdrl;
    @Column(name = "Chagas")
    private String chagas;
    @Column(name = "Pap")
    private String pap;
    @Column(name = "ClasificacionN")
    private String clasificacionN;
    @Column(name = "PCD4")
    private Integer pcd4;
    @Column(name = "Log")
    private Integer log;
    @Column(name = "Tratamiento")
    private String tratamiento;
    @Column(name = "RxTorax")
    private String rxTorax;
    @Column(name = "ExamenOrina")
    private String examenOrina;
    @Column(name = "Peso")
    private Integer peso;
    @Column(name = "Talla")
    private Integer talla;
    @Column(name = "Imc")
    private Integer imc;
    @Column(name = "Siges")
    private String siges;
    @Column(name = "Hipertencion")
    private String hipertencion;
    @Column(name = "HipertencionFecha")
    @Temporal(TemporalType.DATE)
    private Date hipertencionFecha;
    @Column(name = "Sistolica")
    private Integer sistolica;
    @Column(name = "Diastolica")
    private Integer diastolica;
    @Column(name = "Dislipidemias")
    private String dislipidemias;
    @Column(name = "DislipidemiasFecha")
    @Temporal(TemporalType.DATE)
    private Date dislipidemiasFecha;
    @Column(name = "ColesTotal")
    private Integer colesTotal;
    @Column(name = "ColesLdl")
    private Integer colesLdl;
    @Column(name = "ColesHdl")
    private Integer colesHdl;
    @Column(name = "Trigi")
    private Integer trigi;
    @Column(name = "Glicemia")
    private String glicemia;
    @Column(name = "GlicemiaFecha")
    @Temporal(TemporalType.DATE)
    private Date glicemiaFecha;
    @Column(name = "Glice")
    private Integer glice;
    @Column(name = "Hematocrito")
    private Integer hematocrito;
    @Column(name = "HematocritoFecha")
    @Temporal(TemporalType.DATE)
    private Date hematocritoFecha;
    @Column(name = "Gpt")
    private String gpt;
    @Column(name = "Got")
    private String got;
    @JoinColumn(name = "PacienteID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Paciente pacienteID;

    public DatoBasal() {
    }

    public DatoBasal(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getClasificacionL() {
        return clasificacionL;
    }

    public void setClasificacionL(String clasificacionL) {
        this.clasificacionL = clasificacionL;
    }

    public Integer getCd4() {
        return cd4;
    }

    public void setCd4(Integer cd4) {
        this.cd4 = cd4;
    }

    public BigInteger getCv() {
        return cv;
    }

    public void setCv(BigInteger cv) {
        this.cv = cv;
    }

    public String getHla() {
        return hla;
    }

    public void setHla(String hla) {
        this.hla = hla;
    }

    public String getPpd() {
        return ppd;
    }

    public void setPpd(String ppd) {
        this.ppd = ppd;
    }

    public String getHbs() {
        return hbs;
    }

    public void setHbs(String hbs) {
        this.hbs = hbs;
    }

    public String getVhc() {
        return vhc;
    }

    public void setVhc(String vhc) {
        this.vhc = vhc;
    }

    public String getToxoplasmosis() {
        return toxoplasmosis;
    }

    public void setToxoplasmosis(String toxoplasmosis) {
        this.toxoplasmosis = toxoplasmosis;
    }

    public String getVdrl() {
        return vdrl;
    }

    public void setVdrl(String vdrl) {
        this.vdrl = vdrl;
    }

    public String getChagas() {
        return chagas;
    }

    public void setChagas(String chagas) {
        this.chagas = chagas;
    }

    public String getPap() {
        return pap;
    }

    public void setPap(String pap) {
        this.pap = pap;
    }

    public String getClasificacionN() {
        return clasificacionN;
    }

    public void setClasificacionN(String clasificacionN) {
        this.clasificacionN = clasificacionN;
    }

    public Integer getPcd4() {
        return pcd4;
    }

    public void setPcd4(Integer pcd4) {
        this.pcd4 = pcd4;
    }

    public Integer getLog() {
        return log;
    }

    public void setLog(Integer log) {
        this.log = log;
    }

    public String getTratamiento() {
        return tratamiento;
    }

    public void setTratamiento(String tratamiento) {
        this.tratamiento = tratamiento;
    }

    public String getRxTorax() {
        return rxTorax;
    }

    public void setRxTorax(String rxTorax) {
        this.rxTorax = rxTorax;
    }

    public String getExamenOrina() {
        return examenOrina;
    }

    public void setExamenOrina(String examenOrina) {
        this.examenOrina = examenOrina;
    }

    public Integer getPeso() {
        return peso;
    }

    public void setPeso(Integer peso) {
        this.peso = peso;
    }

    public Integer getTalla() {
        return talla;
    }

    public void setTalla(Integer talla) {
        this.talla = talla;
    }

    public Integer getImc() {
        return imc;
    }

    public void setImc(Integer imc) {
        this.imc = imc;
    }

    public String getSiges() {
        return siges;
    }

    public void setSiges(String siges) {
        this.siges = siges;
    }

    public String getHipertencion() {
        return hipertencion;
    }

    public void setHipertencion(String hipertencion) {
        this.hipertencion = hipertencion;
    }

    public Date getHipertencionFecha() {
        return hipertencionFecha;
    }

    public void setHipertencionFecha(Date hipertencionFecha) {
        this.hipertencionFecha = hipertencionFecha;
    }

    public Integer getSistolica() {
        return sistolica;
    }

    public void setSistolica(Integer sistolica) {
        this.sistolica = sistolica;
    }

    public Integer getDiastolica() {
        return diastolica;
    }

    public void setDiastolica(Integer diastolica) {
        this.diastolica = diastolica;
    }

    public String getDislipidemias() {
        return dislipidemias;
    }

    public void setDislipidemias(String dislipidemias) {
        this.dislipidemias = dislipidemias;
    }

    public Date getDislipidemiasFecha() {
        return dislipidemiasFecha;
    }

    public void setDislipidemiasFecha(Date dislipidemiasFecha) {
        this.dislipidemiasFecha = dislipidemiasFecha;
    }

    public Integer getColesTotal() {
        return colesTotal;
    }

    public void setColesTotal(Integer colesTotal) {
        this.colesTotal = colesTotal;
    }

    public Integer getColesLdl() {
        return colesLdl;
    }

    public void setColesLdl(Integer colesLdl) {
        this.colesLdl = colesLdl;
    }

    public Integer getColesHdl() {
        return colesHdl;
    }

    public void setColesHdl(Integer colesHdl) {
        this.colesHdl = colesHdl;
    }

    public Integer getTrigi() {
        return trigi;
    }

    public void setTrigi(Integer trigi) {
        this.trigi = trigi;
    }

    public String getGlicemia() {
        return glicemia;
    }

    public void setGlicemia(String glicemia) {
        this.glicemia = glicemia;
    }

    public Date getGlicemiaFecha() {
        return glicemiaFecha;
    }

    public void setGlicemiaFecha(Date glicemiaFecha) {
        this.glicemiaFecha = glicemiaFecha;
    }

    public Integer getGlice() {
        return glice;
    }

    public void setGlice(Integer glice) {
        this.glice = glice;
    }

    public Integer getHematocrito() {
        return hematocrito;
    }

    public void setHematocrito(Integer hematocrito) {
        this.hematocrito = hematocrito;
    }

    public Date getHematocritoFecha() {
        return hematocritoFecha;
    }

    public void setHematocritoFecha(Date hematocritoFecha) {
        this.hematocritoFecha = hematocritoFecha;
    }

    public String getGpt() {
        return gpt;
    }

    public void setGpt(String gpt) {
        this.gpt = gpt;
    }

    public String getGot() {
        return got;
    }

    public void setGot(String got) {
        this.got = got;
    }

    public Paciente getPacienteID() {
        return pacienteID;
    }

    public void setPacienteID(Paciente pacienteID) {
        this.pacienteID = pacienteID;
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
        if (!(object instanceof DatoBasal)) {
            return false;
        }
        DatoBasal other = (DatoBasal) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cohorte.DatoBasal[ id=" + id + " ]";
    }
    
}
