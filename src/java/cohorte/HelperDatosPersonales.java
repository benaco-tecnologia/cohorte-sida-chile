/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cohorte;

import java.util.Date;

/**
 *
 * @author Daniel
 */
public class HelperDatosPersonales {
    private Integer ID;
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
    private String Codigo;
    private String FechaISP;
    private String RegistroISP;
    private Integer CD4;
    private String FechaNotificacion;
    private Integer Ficha;
    private String FechaIngreso;
    private String FechaCD4;
    private String FechaEncuesta;
    private String Rut;
    private String DV;
    private String FechaNacimiento;
    
    public HelperDatosPersonales() {
        this.ID = 0;
        this.CentroID = 0;
        this.SexoID = 0;
        this.FactorRiesgoID = 0;
        this.UsoAnticonceptivoID = 0;
        this.RazonTestID = 0;
        this.PaisOrigenID = 0;
        this.NivelEducacionalID = 0;
        this.EmpleoID = 0;
        this.EtniaID = 0;
        this.PreferenciaSexualID = 0;
        this.ComunaID = 0;
    }

    /**
     * @return the ID
     */
    public Integer getId() {
        return ID;
    }

    /**
     * @param ID the ID to set
     */
    public void setId(Integer ID) {
        this.ID = ID;
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
    public void setCd4(Integer CD4) {
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
    
    
    public void cargarPaciente(Paciente var) {
        if (var.getId() != null && var.getId() > 0) { 
            this.ID = var.getId();
        }
        
        if (var.getCentroID()!= null && var.getCentroID().getId() > 0) { 
            this.CentroID = var.getCentroID().getId();
        }
        
        if (var.getSexoID() != null && var.getSexoID().getId() > 0) { 
            this.SexoID = var.getSexoID().getId();
        }
        
        if (var.getFactorRiesgoID()!= null && var.getFactorRiesgoID().getId() > 0) { 
            this.FactorRiesgoID = var.getFactorRiesgoID().getId();
        }
        
        if (var.getUsoAnticonceptivoID()!= null && var.getUsoAnticonceptivoID().getId() > 0) { 
            this.UsoAnticonceptivoID = var.getUsoAnticonceptivoID().getId();
        }
        
        if (var.getRazonTestID()!= null && var.getRazonTestID().getId() > 0) { 
            this.RazonTestID = var.getRazonTestID().getId();
        }
        
        if (var.getPaisOrigenID()!= null && var.getPaisOrigenID().getId() > 0) { 
            this.PaisOrigenID = var.getPaisOrigenID().getId();
        }
        
        if (var.getNivelEducacionalID()!= null && var.getNivelEducacionalID().getId() > 0) { 
            this.NivelEducacionalID = var.getNivelEducacionalID().getId();
        }
        
        if (var.getEmpleoID()!= null && var.getEmpleoID().getId() > 0) { 
            this.EmpleoID = var.getEmpleoID().getId();
        }
        
        if (var.getEtniaID()!= null && var.getEtniaID().getId() > 0) { 
            this.EtniaID = var.getEtniaID().getId();
        }
        
        if (var.getPreferenciaSexualID()!= null && var.getPreferenciaSexualID().getId() > 0) { 
            this.PreferenciaSexualID = var.getPreferenciaSexualID().getId();
        }
        
        if (var.getComunaID()!= null && var.getComunaID().getId() > 0) { 
            this.ComunaID = var.getComunaID().getId();
        }
        
        if (var.getCodigo() != null) {
            Codigo = var.getCodigo();
        }
        
        if (var.getFechaISP()!= null) {
            FechaISP = var.getFechaISP().toString();
        }
        
        if (var.getRegistroISP()!= null) {
            RegistroISP = var.getRegistroISP();
        }
        
        if (var.getRegistroISP()!= null) {
            RegistroISP = var.getRegistroISP();
        }
        
        if (var.getCd4() != null) {
            CD4 = var.getCd4();
        }
        
        if (var.getFechaNotificacion() != null) {
            FechaNotificacion = var.getFechaNotificacion().toString();
        }
         
          if (var.getFicha() != null) {
            Ficha = var.getFicha();
        }
          
        if (var.getFechaIngreso() != null) {
            FechaIngreso = var.getFechaIngreso().toString();
        }
           
        if (var.getFechaCD4() != null) {
            FechaCD4 = var.getFechaCD4().toString();
        }
        
        if (var.getFechaEncuesta() != null) {
            FechaEncuesta = var.getFechaEncuesta().toString();
        }
        
        if (var.getRut() != null) {
            Rut = var.getRut();
        }
        
        if (var.getFechaNacimiento() != null) {
            FechaNacimiento = var.getFechaNacimiento().toString();
        } 
    }
}
