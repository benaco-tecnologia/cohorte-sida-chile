/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cohorte;


/**
 *
 * @author Daniel
 */
public class Util {
    public String fechaTabla(String var) {
        if (var == null || var.equals("")) {
            return "SIN DATOS";
        } else {
            String[] tmp = var.split("-");
            
            // return yyyy-MM-dd
            return tmp[2]+"-"+tmp[1]+"-"+tmp[0];
        }
    }
    
    public String fechaForm(String var) {
        if (var == null || var.equals("")) {
            return "";
        } else {
            String[] tmp = var.split("-");
            
            // return yyyy-MM-dd
            return tmp[2]+"/"+tmp[1]+"/"+tmp[0];
        }
    }
    
    public String fechaSQL(String var){
        if (var != null && !var.equals("") && var.length() == 10) {
            // input dd/MM/yyyy
            String[] tmp = var.split("/");
            
            // return yyyy-MM-dd
            return tmp[2]+"-"+tmp[1]+"-"+tmp[0];
        }
        return "";
    }
    
    
}
