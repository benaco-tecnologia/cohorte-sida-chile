/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cohorte;

import java.io.Serializable;

/**
 *
 * @author Daniel
 */
public class Login implements Serializable  {
    private String nombreUsuario;
    private String contrasena;

    public Login() {
    
    }
    
    /**
     * @return the nombreUsuario
     */
    public String getNombreUsuario() {
        return nombreUsuario == null ? "" : nombreUsuario.toUpperCase();
    }

    /**
     * @param nombreUsuario the nombreUsuario to set
     */
    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    /**
     * @return the contrasena
     */
    public String getContrasena() {
        return contrasena == null ? "" : contrasena.toUpperCase();
    }

    /**
     * @param contrasena the contrasena to set
     */
    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
}
