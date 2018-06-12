package cohorte;

import java.util.List;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Daniel
 */
public class FichaBean {
    
    private Util util = new Util();
    
    @PersistenceUnit 
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("Cohorte2PU");; 
   
    @PersistenceContext
    EntityManager em = emf.createEntityManager();
    
    private Integer pacienteID;
    
    // Datos Personales
    
    private Integer CentroID;
    private Integer SexoID;
    private Integer FactorRiesgoID;
    private Integer UsoAnticonceptivoID;
    private Integer RazonTestID;
    private Integer PaisOrigenID;
    private Integer NivelEducacionalID;
    private Integer EmpleoID;
    private Integer EtniaID;
    private Integer PreferenciaSexualID;
    private Integer ComunaID;
    private String Codigo = "";
    private String FechaISP = "";
    private String RegistroISP = "";
    private Integer CD4;
    private String FechaNotificacion = "";
    private Integer Ficha;
    private String FechaIngreso = "";
    private String FechaCD4 = "";
    private String FechaEncuesta = "";
    private String Rut = "";
    private String DV = "";
    private String FechaNacimiento = "";
    private String[] habitos;
    private String[] razones;
    
    
    // Datos basales
    
    private String clasificacionL;
    private String clasificacionN;
    private String pcd4;
    private String logaritmo;
    private Integer cd4b;
    private Integer cv;
    
    private String hla;
    private String ppd;
    private String hbsa;
    private String vhc;
    private String peso;
    private String talla;
    private String toxoplasmosis;
    private String imc;
    private String vdrl;
    private String chagas;
    private String pap;
    private Integer enfermedad1ID;
    private Integer enfermedad2ID;
    private Integer enfermedad3ID;
    private Integer enfermedad4ID;
    
    
    private String CD4bFecha;
    private String CVFecha;
    private String HlaFecha;
    private String PpdFecha;
    private String HbsaFecha;
    private String VhcFecha;
    private String ToxoFecha;
    private String VdrlFecha;
    private String ChagasFecha;
    private String PapFecha;
    private String Enf1Fecha;
    private String Enf2Fecha;
    private String Enf3Fecha;
    private String Enf4Fecha;
    private String HiperFecha;
    private String DisliFecha;
    private String GliceFecha;
    private String HemaFecha;
    
    private String ColesTotal;
    private String ColesLdl;
    private String ColesHdl;
    private String Glice;
    private String Hematocrito;
    private String Transaminasas;
    private String Trigi;
    
    
    private String PpdTratamiento;
    private String HiperDiag;
    private String DisliDiag;
    private String GliceDiag;
    private String PSist;
    private String PDias;
    
    private String Got;
    private String Gpt;
    
    private String RxTorax;
    private String ExamenOrina;
    private String Siges;
    
    private List<Object[]> terapias;
    private List<Object[]> resultadosCD4;
    private List<Object[]> resultadosCV;
    private List<Object[]> resultadosHiper;
    private List<Object[]> resultadosDisli;
    private List<Object[]> resultadosGlice;
    private List<Object[]> resultadosHema;
    
    /**
     * Creates a new instance of FichaBean
     */
    public FichaBean() {
    }
    
    
    public String generarFichaCompleta() {
        String value = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("pacienteIDH");
        String query = "";
        List<Object[]> result;
        
        try {
            this.setPacienteID((Integer) Integer.parseInt(value));
        } catch (Exception e) {
            this.setPacienteID((Integer) 0);
        }
        
        cargarPersonales();
        cargarBasales();
        listadoTerapias();
        
        listadoControlesCD4();
        listadoControlesCV();
        /*
        listadoHiper();
        listadoDisli();
        listadoGlice();
        listadoHema();
        */
        // Cargar habitos
        query = "SELECT PacienteID, HabitoID FROM PacienteHabito WHERE PacienteID = " + pacienteID;
        result = em.createNativeQuery(query).getResultList();
        int i = 0;
        this.habitos = new String[result.size()];
        for (Object[] var : result) {
            this.habitos[i] = var[1].toString();
            i++;
        }
        
        // Razon test
        query = "SELECT PacienteID, RazonTestID FROM PacienteRazonTest WHERE PacienteID = " + pacienteID;
        result = em.createNativeQuery(query).getResultList();
        i = 0;
        this.razones = new String[result.size()];
        for (Object[] var : result) {
            this.razones[i] = var[1].toString();
            i++;
        }
        
                
        
        return "encontrado";
    }
    
    public void cargarPersonales() {
        this.CentroID = null;
        this.SexoID = null;
        this.FactorRiesgoID = null;
        this.UsoAnticonceptivoID = null;
        this.RazonTestID = null;
        this.PaisOrigenID = null;
        this.NivelEducacionalID = null;
        this.EmpleoID = null;
        this.EtniaID = null;
        this.PreferenciaSexualID = null;
        this.ComunaID = null;
        this.Codigo = "";
        this.FechaISP = "";
        this.RegistroISP = "";
        this.CD4 = null;
        this.FechaNotificacion = "";
        this.Ficha = null;
        this.FechaIngreso = "";
        this.FechaCD4 = "";
        this.FechaEncuesta = "";
        this.Rut = "";
        this.DV = "";
        this.FechaNacimiento = "";
        
        //                        0       1          2              3                  4              5              6        7             8              9
        String query = "SELECT CentroID,SexoID,FactorRiesgoID,UsoAnticonceptivoID,PaisOrigenID,NivelEducacionalID,EmpleoID,EtniaID,PreferenciaSexualID,ComunaID,"
                //   10      11        12       13     14              15       16         17          18       19 20      21
                + "Codigo,FechaISP,RegistroISP,CD4,FechaNotificacion,Ficha,FechaIngreso,FechaCD4,FechaEncuesta,Rut,DV,FechaNacimiento "
                + "FROM Paciente WHERE ID=" + this.pacienteID;
        
        List<Object[]> list = (List<Object[]>)em.createNativeQuery(query).getResultList();
        for (Object[] tmp : list) {
            this.CentroID = (tmp[0] != null && !"".equals(tmp[0].toString()) ? Integer.parseInt(tmp[0].toString()) : null);
            this.SexoID = (tmp[1] != null && !"".equals(tmp[1].toString()) ? Integer.parseInt(tmp[1].toString()) : null);
            this.FactorRiesgoID = (tmp[2] != null && !"".equals(tmp[2].toString()) ? Integer.parseInt(tmp[2].toString()) : null);
            this.UsoAnticonceptivoID = (tmp[3] != null && !"".equals(tmp[3].toString()) ? Integer.parseInt(tmp[3].toString()) : null);
            this.PaisOrigenID = (tmp[4] != null && !"".equals(tmp[4].toString()) ? Integer.parseInt(tmp[4].toString()) : null);
            this.NivelEducacionalID = (tmp[5] != null && !"".equals(tmp[5].toString()) ? Integer.parseInt(tmp[5].toString()) : null);
            this.EmpleoID = (tmp[6] != null && !"".equals(tmp[6].toString()) ? Integer.parseInt(tmp[6].toString()) : null);
            this.EtniaID = (tmp[7] != null && !"".equals(tmp[7].toString()) ? Integer.parseInt(tmp[7].toString()) : null);
            this.PreferenciaSexualID = (tmp[8] != null && !"".equals(tmp[8].toString()) ? Integer.parseInt(tmp[8].toString()) : null);
            this.ComunaID = (tmp[9] != null && !"".equals(tmp[9].toString()) ? Integer.parseInt(tmp[9].toString()) : null);
            
            this.Codigo = (tmp[10] != null && !"".equals(tmp[10].toString()) ? tmp[10].toString() : "");
            this.FechaISP = (tmp[11] != null && !"".equals(tmp[11].toString()) ? util.fechaForm(tmp[11].toString()) : "");
            this.RegistroISP = (tmp[12] != null && !"".equals(tmp[12].toString()) ? tmp[12].toString() : "");
            this.CD4 = (tmp[13] != null && !"".equals(tmp[13].toString()) ? Integer.parseInt(tmp[13].toString()) : null);
            this.FechaNotificacion = (tmp[14] != null && !"".equals(tmp[14].toString()) ? util.fechaForm(tmp[14].toString()) : "");
            this.Ficha = (tmp[15] != null && !"".equals(tmp[15].toString()) ? Integer.parseInt(tmp[15].toString()) : null);
            this.FechaIngreso = (tmp[16] != null && !"".equals(tmp[16].toString()) ? util.fechaForm(tmp[16].toString()) : "");
            this.FechaCD4 = (tmp[17] != null && !"".equals(tmp[17].toString()) ? util.fechaForm(tmp[17].toString()) : "");
            this.FechaEncuesta = (tmp[18] != null && !"".equals(tmp[18].toString()) ? util.fechaForm(tmp[18].toString()) : "");
            this.Rut = (tmp[19] != null && !"".equals(tmp[19].toString()) ? tmp[19].toString() : "");
            this.DV = (tmp[20] != null && !"".equals(tmp[20].toString()) ? tmp[20].toString() : "");
            this.FechaNacimiento = (tmp[21] != null && !"".equals(tmp[21].toString()) ? util.fechaForm(tmp[21].toString()) : "");
            
            break;
        }
    }
    
    public void cargarBasales() {
        //                      0           1   2    3   4      5   6    7    8    9        10         11    12      13   14    15         16            17
        String query = "SELECT PacienteID, ID, CD4, CV, PCD4, Log, Hla, Ppd, Hbs, Vhc, Toxoplasmosis, Vdrl, Chagas, Pap, Peso, Talla,ClasificacionL, ClasificacionN, "
                + ""// 18      19       20          21        22         23        24       25            26         27         28         29          30        31         32          33           34         35
                + "CD4Fecha, CVFecha, HlaFecha, PpdFecha, HbsaFecha, VhcFecha, ToxoFecha, VdrlFecha, ChagasFecha, PapFecha, Enf1Fecha, Enf2Fecha, Enf3Fecha, Enf4Fecha, HiperFecha, DisliFecha, GliceFecha, HemaFecha, "
                + ""//36     37      38      39      40     41        42         43       44       45         46        47            48         49          50       51    52    53   54       55         56       57
                + "Enf1ID, Enf2ID, Enf3ID, Enf4ID, Imc, ColesTotal, ColesLdl, ColesHdl, Glice, Hematocrito, Trigi, PpdTratamiento, HiperDiag, DisliDiag, GliceDiag, PSist, PDias, Got, Gpt, ExamenOrina, RxTorax, Siges, Transaminasas "
                + ""
                + ""
                + "FROM DatoBasal "
                + "WHERE PacienteID = " + this.pacienteID;
      
        List<Object[]> list = (List<Object[]>)em.createNativeQuery(query).getResultList();
        for (Object[] var : list) {
            
            ColesTotal = (var[41] != null && !"".equals(var[41].toString()) ? var[41].toString() : "");
            ColesLdl = (var[42] != null && !"".equals(var[42].toString()) ? var[42].toString() : "");
            ColesHdl = (var[43] != null && !"".equals(var[43].toString()) ? var[43].toString() : "");
            Glice = (var[44] != null && !"".equals(var[44].toString()) ? var[44].toString() : "");
            Hematocrito = (var[45] != null && !"".equals(var[45].toString()) ? var[45].toString() : "");
            Transaminasas = (var[58] != null && !"".equals(var[58].toString()) ? var[58].toString() : "");
            Trigi = (var[46] != null && !"".equals(var[46].toString()) ? var[46].toString() : "");
    
            this.clasificacionL = (var[16] != null && !"".equals(var[16].toString()) ? var[16].toString() : "");
            this.clasificacionN = (var[17] != null && !"".equals(var[17].toString()) ? var[17].toString() : "");
            //cd4fecha;
            //cvfecha;
            this.pcd4 = (var[4] != null && !"".equals(var[4].toString()) ? var[4].toString() : "");
            this.logaritmo = (var[5] != null && !"".equals(var[5].toString()) ? var[5].toString() : "");
            this.cd4b = (var[2] != null && !"".equals(var[2].toString()) ? Integer.parseInt(var[2].toString()) : null);
            this.cv = (var[3] != null && !"".equals(var[3].toString()) ? Integer.parseInt(var[3].toString()) : null);

            this.hla = (var[6] != null && !"".equals(var[6].toString()) ? var[6].toString() : "");
            this.ppd = (var[7] != null && !"".equals(var[7].toString()) ? var[7].toString() : "");
            
            //ppdfecha;
            hbsa = (var[8] != null && !"".equals(var[8].toString()) ? var[8].toString() : "");
            //hbsagfecha;
            vhc = (var[9] != null && !"".equals(var[9].toString()) ? var[9].toString() : "");
            //vhcfecha;
            peso = (var[14] != null && !"".equals(var[14].toString()) ? var[14].toString() : "");
            talla = (var[15] != null && !"".equals(var[15].toString()) ? var[15].toString() : "");
            toxoplasmosis = (var[6] != null && !"".equals(var[6].toString()) ? var[6].toString() : "");
            //toxoplasmosisfecha;   
            imc = (var[40] != null && !"".equals(var[40].toString()) ? var[40].toString() : "");
            vdrl = (var[11] != null && !"".equals(var[11].toString()) ? var[11].toString() : "");
            //vdrlfecha;
            chagas = (var[12] != null && !"".equals(var[12].toString()) ? var[12].toString() : "");
            //chagasfecha;
            pap = (var[13] != null && !"".equals(var[13].toString()) ? var[13].toString() : "");
            
            
            // Fechas!!
            enfermedad1ID = (var[36] != null && !"".equals(var[36].toString()) ? Integer.parseInt(var[36].toString()) : null);
            enfermedad2ID = (var[37] != null && !"".equals(var[37].toString()) ? Integer.parseInt(var[37].toString()) : null);
            enfermedad3ID = (var[38] != null && !"".equals(var[38].toString()) ? Integer.parseInt(var[38].toString()) : null);
            enfermedad4ID = (var[39] != null && !"".equals(var[39].toString()) ? Integer.parseInt(var[39].toString()) : null);
            
            // Fechas!!
            CD4bFecha = (var[18] != null && !"".equals(var[18].toString()) ? util.fechaForm(var[18].toString()) : "");
            CVFecha = (var[19] != null && !"".equals(var[19].toString()) ? util.fechaForm(var[19].toString()) : "");
            PpdFecha = (var[21] != null && !"".equals(var[21].toString()) ? util.fechaForm(var[21].toString()) : "");
            HbsaFecha = (var[22] != null && !"".equals(var[22].toString()) ? util.fechaForm(var[22].toString()) : "");
            VhcFecha = (var[23] != null && !"".equals(var[23].toString()) ? util.fechaForm(var[23].toString()) : "");
            ToxoFecha = (var[24] != null && !"".equals(var[24].toString()) ? util.fechaForm(var[24].toString()) : "");
            VdrlFecha = (var[25] != null && !"".equals(var[25].toString()) ? util.fechaForm(var[25].toString()) : "");
            ChagasFecha = (var[26] != null && !"".equals(var[26].toString()) ? util.fechaForm(var[26].toString()) : "");
            PapFecha = (var[27] != null && !"".equals(var[27].toString()) ? util.fechaForm(var[27].toString()) : "");
            HiperFecha = (var[32] != null && !"".equals(var[32].toString()) ? util.fechaForm(var[32].toString()) : "");
            DisliFecha = (var[33] != null && !"".equals(var[33].toString()) ? util.fechaForm(var[33].toString()) : "");
            GliceFecha = (var[34] != null && !"".equals(var[34].toString()) ? util.fechaForm(var[34].toString()) : "");
            HemaFecha = (var[35] != null && !"".equals(var[35].toString()) ? util.fechaForm(var[35].toString()) : "");
            
            //HlaFecha = (var[20] != null && !"".equals(var[20].toString()) ? util.fechaForm(var[20].toString()) : "");
            
            Enf1Fecha = (var[28] != null && !"".equals(var[28].toString()) ? util.fechaForm(var[28].toString()) : "");
            Enf2Fecha = (var[29] != null && !"".equals(var[29].toString()) ? util.fechaForm(var[29].toString()) : "");
            Enf3Fecha = (var[30] != null && !"".equals(var[30].toString()) ? util.fechaForm(var[30].toString()) : "");
            Enf4Fecha = (var[31] != null && !"".equals(var[31].toString()) ? util.fechaForm(var[31].toString()) : "");
            
            PpdTratamiento = (var[47] != null && !"".equals(var[47].toString()) ? var[47].toString() : "");
            HiperDiag = (var[48] != null && !"".equals(var[48].toString()) ? var[48].toString() : "");
            DisliDiag = (var[49] != null && !"".equals(var[49].toString()) ? var[49].toString() : "");
            GliceDiag = (var[50] != null && !"".equals(var[50].toString()) ? var[50].toString() : "");
            PSist = (var[51] != null && !"".equals(var[51].toString()) ? var[51].toString() : "");
            PDias = (var[52] != null && !"".equals(var[52].toString()) ? var[52].toString() : "");
            
            Got = (var[53] != null && !"".equals(var[53].toString()) ? var[53].toString() : "");
            Gpt = (var[54] != null && !"".equals(var[54].toString()) ? var[54].toString() : "");
            
            ExamenOrina = (var[55] != null && !"".equals(var[55].toString()) ? var[55].toString() : "");
            RxTorax = (var[56] != null && !"".equals(var[56].toString()) ? var[56].toString() : "");
            Siges = (var[57] != null && !"".equals(var[57].toString()) ? var[57].toString() : "");
                    
            break;
        }
    }
    
    public void listadoTerapias() {
        String query = "SELECT PacienteID, NumeroTar, Fecha, NoContinua, CausaTerminoID, RazonToxicidadID, FechaTermino, IFNULL(Geno, 'NO') Geno, GenoFecha, Tropismo, ID FROM Terapia t WHERE t.PacienteID = " + this.pacienteID + " ORDER BY NumeroTar DESC";
        setTerapias((List<Object[]>)em.createNativeQuery(query).getResultList());
    }

    public void listadoControlesCD4() {
        String query = "SELECT ID, PacienteID, NumeroControl, Fecha, Resultado, PCD4, Tipo FROM Control WHERE PacienteID = " + this.pacienteID + " AND Tipo = 'CD' ORDER BY NumeroControl DESC";
        setResultadosCD4((List<Object[]>)em.createNativeQuery(query).getResultList());
    }
    
    public void listadoControlesCV() {
        String query = "SELECT ID, PacienteID, NumeroControl, Fecha, Resultado, Logaritmo, Tipo FROM Control WHERE PacienteID = " + this.pacienteID + " AND Tipo = 'CV' ORDER BY NumeroControl DESC";
        setResultadosCV((List<Object[]>)em.createNativeQuery(query).getResultList());
    }
    
    public void listadoHiper() {
        String query = "SELECT ID, PacienteID, NumeroControl, Tipo, Diagnostico, Sistolica, Diastolica, Fecha FROM Laboratorio WHERE PacienteID = " + this.pacienteID + " AND Tipo = 'HI' ORDER BY NumeroControl DESC";
        setResultadosHiper((List<Object[]>)em.createNativeQuery(query).getResultList());
    }
    
    public void listadoGlice() {
        String query = "SELECT ID, PacienteID, NumeroControl, Tipo, Diagnostico, Glice, Peso, Fecha FROM Laboratorio WHERE PacienteID = " + this.pacienteID + " AND Tipo = 'GL' ORDER BY NumeroControl DESC";
        setResultadosGlice((List<Object[]>)em.createNativeQuery(query).getResultList());
    }
    
    public void listadoDisli() {
        String query = "SELECT ID, PacienteID, NumeroControl, Tipo, Diagnostico, ColesTotal, ColesLdl, ColesHdl, Trigli, Fecha FROM Laboratorio WHERE PacienteID = " + this.pacienteID + " AND Tipo = 'DI' ORDER BY NumeroControl DESC";
        setResultadosDisli((List<Object[]>)em.createNativeQuery(query).getResultList());
    }
    
    public void listadoHema() {
        String query = "SELECT ID, PacienteID, NumeroControl, Tipo, Diagnostico, Hematocrito, Gpt, Got, Fecha FROM Laboratorio WHERE PacienteID = " + this.pacienteID + " AND Tipo = 'HE' ORDER BY NumeroControl DESC";
        setResultadosHema((List<Object[]>)em.createNativeQuery(query).getResultList());
    }
    
    /**
     * @return the pacienteID
     */
    public Integer getPacienteID() {
        return pacienteID;
    }

    /**
     * @param pacienteID the pacienteID to set
     */
    public void setPacienteID(Integer pacienteID) {
        this.pacienteID = pacienteID;
    }

    /**
     * @return the CentroID
     */
    public Integer getCentroID() {
        return CentroID;
    }

    /**
     * @param CentroID the CentroID to set
     */
    public void setCentroID(Integer CentroID) {
        this.CentroID = CentroID;
    }

    /**
     * @return the SexoID
     */
    public Integer getSexoID() {
        return SexoID;
    }

    /**
     * @param SexoID the SexoID to set
     */
    public void setSexoID(Integer SexoID) {
        this.SexoID = SexoID;
    }

    /**
     * @return the FactorRiesgoID
     */
    public Integer getFactorRiesgoID() {
        return FactorRiesgoID;
    }

    /**
     * @param FactorRiesgoID the FactorRiesgoID to set
     */
    public void setFactorRiesgoID(Integer FactorRiesgoID) {
        this.FactorRiesgoID = FactorRiesgoID;
    }

    /**
     * @return the UsoAnticonceptivoID
     */
    public Integer getUsoAnticonceptivoID() {
        return UsoAnticonceptivoID;
    }

    /**
     * @param UsoAnticonceptivoID the UsoAnticonceptivoID to set
     */
    public void setUsoAnticonceptivoID(Integer UsoAnticonceptivoID) {
        this.UsoAnticonceptivoID = UsoAnticonceptivoID;
    }

    /**
     * @return the RazonTestID
     */
    public Integer getRazonTestID() {
        return RazonTestID;
    }

    /**
     * @param RazonTestID the RazonTestID to set
     */
    public void setRazonTestID(Integer RazonTestID) {
        this.RazonTestID = RazonTestID;
    }

    /**
     * @return the PaisOrigenID
     */
    public Integer getPaisOrigenID() {
        return PaisOrigenID;
    }

    /**
     * @param PaisOrigenID the PaisOrigenID to set
     */
    public void setPaisOrigenID(Integer PaisOrigenID) {
        this.PaisOrigenID = PaisOrigenID;
    }

    /**
     * @return the NivelEducacionalID
     */
    public Integer getNivelEducacionalID() {
        return NivelEducacionalID;
    }

    /**
     * @param NivelEducacionalID the NivelEducacionalID to set
     */
    public void setNivelEducacionalID(Integer NivelEducacionalID) {
        this.NivelEducacionalID = NivelEducacionalID;
    }

    /**
     * @return the EmpleoID
     */
    public Integer getEmpleoID() {
        return EmpleoID;
    }

    /**
     * @param EmpleoID the EmpleoID to set
     */
    public void setEmpleoID(Integer EmpleoID) {
        this.EmpleoID = EmpleoID;
    }

    /**
     * @return the EtniaID
     */
    public Integer getEtniaID() {
        return EtniaID;
    }

    /**
     * @param EtniaID the EtniaID to set
     */
    public void setEtniaID(Integer EtniaID) {
        this.EtniaID = EtniaID;
    }

    /**
     * @return the PreferenciaSexualID
     */
    public Integer getPreferenciaSexualID() {
        return PreferenciaSexualID;
    }

    /**
     * @param PreferenciaSexualID the PreferenciaSexualID to set
     */
    public void setPreferenciaSexualID(Integer PreferenciaSexualID) {
        this.PreferenciaSexualID = PreferenciaSexualID;
    }

    /**
     * @return the ComunaID
     */
    public Integer getComunaID() {
        return ComunaID;
    }

    /**
     * @param ComunaID the ComunaID to set
     */
    public void setComunaID(Integer ComunaID) {
        this.ComunaID = ComunaID;
    }

    /**
     * @return the Codigo
     */
    public String getCodigo() {
        return Codigo;
    }

    /**
     * @param Codigo the Codigo to set
     */
    public void setCodigo(String Codigo) {
        this.Codigo = Codigo;
    }

    /**
     * @return the FechaISP
     */
    public String getFechaISP() {
        return FechaISP;
    }

    /**
     * @param FechaISP the FechaISP to set
     */
    public void setFechaISP(String FechaISP) {
        this.FechaISP = FechaISP;
    }

    /**
     * @return the RegistroISP
     */
    public String getRegistroISP() {
        return RegistroISP;
    }

    /**
     * @param RegistroISP the RegistroISP to set
     */
    public void setRegistroISP(String RegistroISP) {
        this.RegistroISP = RegistroISP;
    }

    /**
     * @return the CD4
     */
    public Integer getCd4() {
        return CD4;
    }

    /**
     * @param CD4 the CD4 to set
     */
    public void setCD4(Integer Cd4) {
        this.CD4 = CD4;
    }

    /**
     * @return the FechaNotificacion
     */
    public String getFechaNotificacion() {
        return FechaNotificacion;
    }

    /**
     * @param FechaNotificacion the FechaNotificacion to set
     */
    public void setFechaNotificacion(String FechaNotificacion) {
        this.FechaNotificacion = FechaNotificacion;
    }

    /**
     * @return the Ficha
     */
    public Integer getFicha() {
        return Ficha;
    }

    /**
     * @param Ficha the Ficha to set
     */
    public void setFicha(Integer Ficha) {
        this.Ficha = Ficha;
    }

    /**
     * @return the FechaIngreso
     */
    public String getFechaIngreso() {
        return FechaIngreso;
    }

    /**
     * @param FechaIngreso the FechaIngreso to set
     */
    public void setFechaIngreso(String FechaIngreso) {
        this.FechaIngreso = FechaIngreso;
    }

    /**
     * @return the FechaCD4
     */
    public String getFechaCD4() {
        return FechaCD4;
    }

    /**
     * @param FechaCD4 the FechaCD4 to set
     */
    public void setFechaCD4(String FechaCD4) {
        this.FechaCD4 = FechaCD4;
    }

    /**
     * @return the FechaEncuesta
     */
    public String getFechaEncuesta() {
        return FechaEncuesta;
    }

    /**
     * @param FechaEncuesta the FechaEncuesta to set
     */
    public void setFechaEncuesta(String FechaEncuesta) {
        this.FechaEncuesta = FechaEncuesta;
    }

    /**
     * @return the Rut
     */
    public String getRut() {
        return Rut;
    }

    /**
     * @param Rut the Rut to set
     */
    public void setRut(String Rut) {
        this.Rut = Rut;
    }

    /**
     * @return the DV
     */
    public String getDv() {
        return DV;
    }

    /**
     * @param DV the DV to set
     */
    public void setDv(String DV) {
        this.DV = DV;
    }

    /**
     * @return the FechaNacimiento
     */
    public String getFechaNacimiento() {
        return FechaNacimiento;
    }

    /**
     * @param FechaNacimiento the FechaNacimiento to set
     */
    public void setFechaNacimiento(String FechaNacimiento) {
        this.FechaNacimiento = FechaNacimiento;
    }

    /**
     * @return the habitos
     */
    public String[] getHabitos() {
        return habitos;
    }

    /**
     * @param habitos the habitos to set
     */
    public void setHabitos(String[] habitos) {
        this.habitos = habitos;
    }

    /**
     * @return the razones
     */
    public String[] getRazones() {
        return razones;
    }

    /**
     * @param razones the razones to set
     */
    public void setRazones(String[] razones) {
        this.razones = razones;
    }

    /**
     * @return the clasificacionL
     */
    public String getClasificacionL() {
        return clasificacionL;
    }

    /**
     * @param clasificacionL the clasificacionL to set
     */
    public void setClasificacionL(String clasificacionL) {
        this.clasificacionL = clasificacionL;
    }

    /**
     * @return the clasificacionN
     */
    public String getClasificacionN() {
        return clasificacionN;
    }

    /**
     * @param clasificacionN the clasificacionN to set
     */
    public void setClasificacionN(String clasificacionN) {
        this.clasificacionN = clasificacionN;
    }

    /**
     * @return the pcd4
     */
    public String getPcd4() {
        return pcd4;
    }

    /**
     * @param pcd4 the pcd4 to set
     */
    public void setPcd4(String pcd4) {
        this.pcd4 = pcd4;
    }

    /**
     * @return the logaritmo
     */
    public String getLogaritmo() {
        return logaritmo;
    }

    /**
     * @param logaritmo the logaritmo to set
     */
    public void setLogaritmo(String logaritmo) {
        this.logaritmo = logaritmo;
    }

    /**
     * @return the cd4b
     */
    public Integer getCd4b() {
        return cd4b;
    }

    /**
     * @param cd4b the cd4b to set
     */
    public void setCd4b(Integer cd4b) {
        this.cd4b = cd4b;
    }

    /**
     * @return the cv
     */
    public Integer getCv() {
        return cv;
    }

    /**
     * @param cv the cv to set
     */
    public void setCv(Integer cv) {
        this.cv = cv;
    }

    /**
     * @return the hla
     */
    public String getHla() {
        return hla;
    }

    /**
     * @param hla the hla to set
     */
    public void setHla(String hla) {
        this.hla = hla;
    }

    /**
     * @return the ppd
     */
    public String getPpd() {
        return ppd;
    }

    /**
     * @param ppd the ppd to set
     */
    public void setPpd(String ppd) {
        this.ppd = ppd;
    }

    /**
     * @return the hbsa
     */
    public String getHbsa() {
        return hbsa;
    }

    /**
     * @param hbsa the hbsa to set
     */
    public void setHbsa(String hbsa) {
        this.hbsa = hbsa;
    }

    /**
     * @return the vhc
     */
    public String getVhc() {
        return vhc;
    }

    /**
     * @param vhc the vhc to set
     */
    public void setVhc(String vhc) {
        this.vhc = vhc;
    }

    /**
     * @return the peso
     */
    public String getPeso() {
        return peso;
    }

    /**
     * @param peso the peso to set
     */
    public void setPeso(String peso) {
        this.peso = peso;
    }

    /**
     * @return the talla
     */
    public String getTalla() {
        return talla;
    }

    /**
     * @param talla the talla to set
     */
    public void setTalla(String talla) {
        this.talla = talla;
    }

    /**
     * @return the toxoplasmosis
     */
    public String getToxoplasmosis() {
        return toxoplasmosis;
    }

    /**
     * @param toxoplasmosis the toxoplasmosis to set
     */
    public void setToxoplasmosis(String toxoplasmosis) {
        this.toxoplasmosis = toxoplasmosis;
    }

    /**
     * @return the imc
     */
    public String getImc() {
        return imc;
    }

    /**
     * @param imc the imc to set
     */
    public void setImc(String imc) {
        this.imc = imc;
    }

    /**
     * @return the vdrl
     */
    public String getVdrl() {
        return vdrl;
    }

    /**
     * @param vdrl the vdrl to set
     */
    public void setVdrl(String vdrl) {
        this.vdrl = vdrl;
    }

    /**
     * @return the chagas
     */
    public String getChagas() {
        return chagas;
    }

    /**
     * @param chagas the chagas to set
     */
    public void setChagas(String chagas) {
        this.chagas = chagas;
    }

    /**
     * @return the pap
     */
    public String getPap() {
        return pap;
    }

    /**
     * @param pap the pap to set
     */
    public void setPap(String pap) {
        this.pap = pap;
    }

    /**
     * @return the enfermedad1ID
     */
    public Integer getEnfermedad1ID() {
        return enfermedad1ID;
    }

    /**
     * @param enfermedad1ID the enfermedad1ID to set
     */
    public void setEnfermedad1ID(Integer enfermedad1ID) {
        this.enfermedad1ID = enfermedad1ID;
    }

    /**
     * @return the enfermedad2ID
     */
    public Integer getEnfermedad2ID() {
        return enfermedad2ID;
    }

    /**
     * @param enfermedad2ID the enfermedad2ID to set
     */
    public void setEnfermedad2ID(Integer enfermedad2ID) {
        this.enfermedad2ID = enfermedad2ID;
    }

    /**
     * @return the enfermedad3ID
     */
    public Integer getEnfermedad3ID() {
        return enfermedad3ID;
    }

    /**
     * @param enfermedad3ID the enfermedad3ID to set
     */
    public void setEnfermedad3ID(Integer enfermedad3ID) {
        this.enfermedad3ID = enfermedad3ID;
    }

    /**
     * @return the enfermedad4ID
     */
    public Integer getEnfermedad4ID() {
        return enfermedad4ID;
    }

    /**
     * @param enfermedad4ID the enfermedad4ID to set
     */
    public void setEnfermedad4ID(Integer enfermedad4ID) {
        this.enfermedad4ID = enfermedad4ID;
    }

    /**
     * @return the CD4bFecha
     */
    public String getCD4bFecha() {
        return CD4bFecha;
    }

    /**
     * @param CD4bFecha the CD4bFecha to set
     */
    public void setCD4bFecha(String CD4bFecha) {
        this.CD4bFecha = CD4bFecha;
    }

    /**
     * @return the CVFecha
     */
    public String getCVFecha() {
        return CVFecha;
    }

    /**
     * @param CVFecha the CVFecha to set
     */
    public void setCVFecha(String CVFecha) {
        this.CVFecha = CVFecha;
    }

    /**
     * @return the HlaFecha
     */
    public String getHlaFecha() {
        return HlaFecha;
    }

    /**
     * @param HlaFecha the HlaFecha to set
     */
    public void setHlaFecha(String HlaFecha) {
        this.HlaFecha = HlaFecha;
    }

    /**
     * @return the PpdFecha
     */
    public String getPpdFecha() {
        return PpdFecha;
    }

    /**
     * @param PpdFecha the PpdFecha to set
     */
    public void setPpdFecha(String PpdFecha) {
        this.PpdFecha = PpdFecha;
    }

    /**
     * @return the HbsaFecha
     */
    public String getHbsaFecha() {
        return HbsaFecha;
    }

    /**
     * @param HbsaFecha the HbsaFecha to set
     */
    public void setHbsaFecha(String HbsaFecha) {
        this.HbsaFecha = HbsaFecha;
    }

    /**
     * @return the VhcFecha
     */
    public String getVhcFecha() {
        return VhcFecha;
    }

    /**
     * @param VhcFecha the VhcFecha to set
     */
    public void setVhcFecha(String VhcFecha) {
        this.VhcFecha = VhcFecha;
    }

    /**
     * @return the ToxoFecha
     */
    public String getToxoFecha() {
        return ToxoFecha;
    }

    /**
     * @param ToxoFecha the ToxoFecha to set
     */
    public void setToxoFecha(String ToxoFecha) {
        this.ToxoFecha = ToxoFecha;
    }

    /**
     * @return the VdrlFecha
     */
    public String getVdrlFecha() {
        return VdrlFecha;
    }

    /**
     * @param VdrlFecha the VdrlFecha to set
     */
    public void setVdrlFecha(String VdrlFecha) {
        this.VdrlFecha = VdrlFecha;
    }

    /**
     * @return the ChagasFecha
     */
    public String getChagasFecha() {
        return ChagasFecha;
    }

    /**
     * @param ChagasFecha the ChagasFecha to set
     */
    public void setChagasFecha(String ChagasFecha) {
        this.ChagasFecha = ChagasFecha;
    }

    /**
     * @return the PapFecha
     */
    public String getPapFecha() {
        return PapFecha;
    }

    /**
     * @param PapFecha the PapFecha to set
     */
    public void setPapFecha(String PapFecha) {
        this.PapFecha = PapFecha;
    }

    /**
     * @return the Enf1Fecha
     */
    public String getEnf1Fecha() {
        return Enf1Fecha;
    }

    /**
     * @param Enf1Fecha the Enf1Fecha to set
     */
    public void setEnf1Fecha(String Enf1Fecha) {
        this.Enf1Fecha = Enf1Fecha;
    }

    /**
     * @return the Enf2Fecha
     */
    public String getEnf2Fecha() {
        return Enf2Fecha;
    }

    /**
     * @param Enf2Fecha the Enf2Fecha to set
     */
    public void setEnf2Fecha(String Enf2Fecha) {
        this.Enf2Fecha = Enf2Fecha;
    }

    /**
     * @return the Enf3Fecha
     */
    public String getEnf3Fecha() {
        return Enf3Fecha;
    }

    /**
     * @param Enf3Fecha the Enf3Fecha to set
     */
    public void setEnf3Fecha(String Enf3Fecha) {
        this.Enf3Fecha = Enf3Fecha;
    }

    /**
     * @return the Enf4Fecha
     */
    public String getEnf4Fecha() {
        return Enf4Fecha;
    }

    /**
     * @param Enf4Fecha the Enf4Fecha to set
     */
    public void setEnf4Fecha(String Enf4Fecha) {
        this.Enf4Fecha = Enf4Fecha;
    }

    /**
     * @return the HiperFecha
     */
    public String getHiperFecha() {
        return HiperFecha;
    }

    /**
     * @param HiperFecha the HiperFecha to set
     */
    public void setHiperFecha(String HiperFecha) {
        this.HiperFecha = HiperFecha;
    }

    /**
     * @return the DisliFecha
     */
    public String getDisliFecha() {
        return DisliFecha;
    }

    /**
     * @param DisliFecha the DisliFecha to set
     */
    public void setDisliFecha(String DisliFecha) {
        this.DisliFecha = DisliFecha;
    }

    /**
     * @return the GliceFecha
     */
    public String getGliceFecha() {
        return GliceFecha;
    }

    /**
     * @param GliceFecha the GliceFecha to set
     */
    public void setGliceFecha(String GliceFecha) {
        this.GliceFecha = GliceFecha;
    }

    /**
     * @return the HemaFecha
     */
    public String getHemaFecha() {
        return HemaFecha;
    }

    /**
     * @param HemaFecha the HemaFecha to set
     */
    public void setHemaFecha(String HemaFecha) {
        this.HemaFecha = HemaFecha;
    }

    /**
     * @return the ColesTotal
     */
    public String getColesTotal() {
        return ColesTotal;
    }

    /**
     * @param ColesTotal the ColesTotal to set
     */
    public void setColesTotal(String ColesTotal) {
        this.ColesTotal = ColesTotal;
    }

    /**
     * @return the ColesLdl
     */
    public String getColesLdl() {
        return ColesLdl;
    }

    /**
     * @param ColesLdl the ColesLdl to set
     */
    public void setColesLdl(String ColesLdl) {
        this.ColesLdl = ColesLdl;
    }

    /**
     * @return the ColesHdl
     */
    public String getColesHdl() {
        return ColesHdl;
    }

    /**
     * @param ColesHdl the ColesHdl to set
     */
    public void setColesHdl(String ColesHdl) {
        this.ColesHdl = ColesHdl;
    }

    /**
     * @return the Glice
     */
    public String getGlice() {
        return Glice;
    }

    /**
     * @param Glice the Glice to set
     */
    public void setGlice(String Glice) {
        this.Glice = Glice;
    }

    /**
     * @return the Hematocrito
     */
    public String getHematocrito() {
        return Hematocrito;
    }

    /**
     * @param Hematocrito the Hematocrito to set
     */
    public void setHematocrito(String Hematocrito) {
        this.Hematocrito = Hematocrito;
    }

    /**
     * @return the Trigi
     */
    public String getTrigi() {
        return Trigi;
    }

    /**
     * @param Trigi the Trigi to set
     */
    public void setTrigi(String Trigi) {
        this.Trigi = Trigi;
    }

    /**
     * @return the PpdTratamiento
     */
    public String getPpdTratamiento() {
        return PpdTratamiento;
    }

    /**
     * @param PpdTratamiento the PpdTratamiento to set
     */
    public void setPpdTratamiento(String PpdTratamiento) {
        this.PpdTratamiento = PpdTratamiento;
    }

    /**
     * @return the HiperDiag
     */
    public String getHiperDiag() {
        return HiperDiag;
    }

    /**
     * @param HiperDiag the HiperDiag to set
     */
    public void setHiperDiag(String HiperDiag) {
        this.HiperDiag = HiperDiag;
    }

    /**
     * @return the DisliDiag
     */
    public String getDisliDiag() {
        return DisliDiag;
    }

    /**
     * @param DisliDiag the DisliDiag to set
     */
    public void setDisliDiag(String DisliDiag) {
        this.DisliDiag = DisliDiag;
    }

    /**
     * @return the GliceDiag
     */
    public String getGliceDiag() {
        return GliceDiag;
    }

    /**
     * @param GliceDiag the GliceDiag to set
     */
    public void setGliceDiag(String GliceDiag) {
        this.GliceDiag = GliceDiag;
    }

    /**
     * @return the PSist
     */
    public String getPSist() {
        return PSist;
    }

    /**
     * @param PSist the PSist to set
     */
    public void setPSist(String PSist) {
        this.PSist = PSist;
    }

    /**
     * @return the PDias
     */
    public String getPDias() {
        return PDias;
    }

    /**
     * @param PDias the PDias to set
     */
    public void setPDias(String PDias) {
        this.PDias = PDias;
    }

    /**
     * @return the Got
     */
    public String getGot() {
        return Got;
    }

    /**
     * @param Got the Got to set
     */
    public void setGot(String Got) {
        this.Got = Got;
    }

    /**
     * @return the Gpt
     */
    public String getGpt() {
        return Gpt;
    }

    /**
     * @param Gpt the Gpt to set
     */
    public void setGpt(String Gpt) {
        this.Gpt = Gpt;
    }

    /**
     * @return the RxTorax
     */
    public String getRxTorax() {
        return RxTorax;
    }

    /**
     * @param RxTorax the RxTorax to set
     */
    public void setRxTorax(String RxTorax) {
        this.RxTorax = RxTorax;
    }

    /**
     * @return the ExamenOrina
     */
    public String getExamenOrina() {
        return ExamenOrina;
    }

    /**
     * @param ExamenOrina the ExamenOrina to set
     */
    public void setExamenOrina(String ExamenOrina) {
        this.ExamenOrina = ExamenOrina;
    }

    /**
     * @return the Siges
     */
    public String getSiges() {
        return Siges;
    }

    /**
     * @param Siges the Siges to set
     */
    public void setSiges(String Siges) {
        this.Siges = Siges;
    }

    /**
     * @return the terapias
     */
    public List<Object[]> getTerapias() {
        return terapias;
    }

    /**
     * @param terapias the terapias to set
     */
    public void setTerapias(List<Object[]> terapias) {
        this.terapias = terapias;
    }

    /**
     * @return the resultadosCD4
     */
    public List<Object[]> getResultadosCD4() {
        return resultadosCD4;
    }

    /**
     * @param resultadosCD4 the resultadosCD4 to set
     */
    public void setResultadosCD4(List<Object[]> resultadosCD4) {
        this.resultadosCD4 = resultadosCD4;
    }

    /**
     * @return the resultadosCV
     */
    public List<Object[]> getResultadosCV() {
        return resultadosCV;
    }

    /**
     * @param resultadosCV the resultadosCV to set
     */
    public void setResultadosCV(List<Object[]> resultadosCV) {
        this.resultadosCV = resultadosCV;
    }

    /**
     * @return the resultadosHiper
     */
    public List<Object[]> getResultadosHiper() {
        return resultadosHiper;
    }

    /**
     * @param resultadosHiper the resultadosHiper to set
     */
    public void setResultadosHiper(List<Object[]> resultadosHiper) {
        this.resultadosHiper = resultadosHiper;
    }

    /**
     * @return the resultadosDisli
     */
    public List<Object[]> getResultadosDisli() {
        return resultadosDisli;
    }

    /**
     * @param resultadosDisli the resultadosDisli to set
     */
    public void setResultadosDisli(List<Object[]> resultadosDisli) {
        this.resultadosDisli = resultadosDisli;
    }

    /**
     * @return the resultadosGlice
     */
    public List<Object[]> getResultadosGlice() {
        return resultadosGlice;
    }

    /**
     * @param resultadosGlice the resultadosGlice to set
     */
    public void setResultadosGlice(List<Object[]> resultadosGlice) {
        this.resultadosGlice = resultadosGlice;
    }

    /**
     * @return the resultadosHema
     */
    public List<Object[]> getResultadosHema() {
        return resultadosHema;
    }

    /**
     * @param resultadosHema the resultadosHema to set
     */
    public void setResultadosHema(List<Object[]> resultadosHema) {
        this.resultadosHema = resultadosHema;
    }

    /**
     * @return the Transaminasas
     */
    public String getTransaminasas() {
        return Transaminasas;
    }

    /**
     * @param Transaminasas the Transaminasas to set
     */
    public void setTransaminasas(String Transaminasas) {
        this.Transaminasas = Transaminasas;
    }
    
}
