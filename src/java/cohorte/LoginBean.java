/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cohorte;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
//import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Daniel
 */
public class LoginBean {
    @PersistenceUnit 
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("Cohorte2PU");; 
    /*@PersistenceContext
    EntityManager em = emf.createEntityManager();*/
    
    private String username;
    private String password;
    private String mensaje;
    private Boolean error;
    private Login login = new Login();
    private Integer uid;
    
    // Session
    private final HttpServletRequest httpServletRequest;
    private final FacesContext faceContext;
    
    /**
     * Creates a new instance of Login
     */
    
    public LoginBean() {
        this.error = false;
        faceContext = FacesContext.getCurrentInstance();
        httpServletRequest = (HttpServletRequest)faceContext.getExternalContext().getRequest();
    }
    
    public String checkValidUser() {
        EntityManager em = emf.createEntityManager();
        httpServletRequest.getSession().removeAttribute("Centro");
        httpServletRequest.getSession().removeAttribute("Usuario");
        httpServletRequest.getSession().removeAttribute("UID");
        this.error = true;
        /*
        if (this.username.isEmpty() || this.password.isEmpty() || this.username.equals("") || this.password.equals("")) {
            this.mensaje = "Debe ingresar un nombre de usuario y contraseña.";
            return "novalido";
        }
        */
        
        if (this.login.getNombreUsuario().isEmpty() || this.login.getContrasena().isEmpty() || this.login.getNombreUsuario().equals("") || this.login.getContrasena().equals("")) {
            this.mensaje = "Debe ingresar un nombre de usuario y contraseña.";
            em.close();
            em = null;
            return "novalido";
        }
        
        try {            
            //String query = "SELECT u.ID, u.NombreUsuario, u.Contrasena FROM Usuario u WHERE u.NombreUsuario = '"+this.username+"' AND u.Contrasena = MD5('"+this.password+"');";
            String query = "SELECT u.ID, u.NombreUsuario, u.Contrasena, u.Estado, u.NivelUsuarioID, u.Nombre, u.Apellido FROM Usuario u WHERE u.NombreUsuario = '" + this.login.getNombreUsuario() + "' AND u.Contrasena = MD5('" + this.login.getContrasena() + "');";
            List<Object[]> list = (List<Object[]>)em.createNativeQuery(query).getResultList();
            if (list.isEmpty()) {
                this.mensaje = "Nombre de usuario o contraseña incorrectos.";
            } else if (list.size() == 1) {
                Object[] var = list.get(0);
                Usuario usuario = new Usuario(Integer.parseInt(var[0].toString()));
                usuario.setNombreUsuario(var[1].toString());
                usuario.setNombre(var[5].toString());
                usuario.setApellido(var[6].toString());
                usuario.setEstado(var[3].toString().charAt(0));
                NivelUsuario nivelusuario = new NivelUsuario(Integer.parseInt(var[4].toString()));
                usuario.setNivelUsuarioID(nivelusuario);
                //Usuario usuario = (Usuario)em.createNamedQuery("Usuario.findById").setParameter("id", Integer.parseInt(var[0].toString())).getSingleResult();
                
                
                if (!usuario.estaActivo()) {
                    this.mensaje = "Su usuario existe, pero no está autorizado a ingresar al sistema.";
                    em.close();
                    em = null;
                    var = null;
                    System.gc();
                    return "novalido";
                }
                
                if (usuario.esUsuario()) {
                     // Asignar Centro en que tiene permiso
                    query = "SELECT CentroID, UsuarioID, Centro.Nombre FROM Permiso, Centro WHERE UsuarioID = '" + var[0].toString() + "' AND Permiso.CentroID = Centro.ID LIMIT 1;";
                    List<Object[]> list2 = (List<Object[]>)em.createNativeQuery(query).getResultList();
                    if (list2.size() == 1) {
                        Object[] var2 = list2.get(0);
                        httpServletRequest.getSession().setAttribute("Centro", var2[0].toString());
                        httpServletRequest.getSession().setAttribute("CentroNombre", var2[2].toString());
                        var2 = null;
                    }
                    list2 = null;
                    /*Permiso permiso = (Permiso)em.createNamedQuery("Permiso.findByUsuarioID").setParameter("usuarioID", usuario).getSingleResult();
                    Centro centro = permiso.getCentroID();
                    httpServletRequest.getSession().setAttribute("Centro", centro);*/
                }
                try {  
                    Date today = new Date();
                    SimpleDateFormat fecha = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                    query = "INSERT UsuarioRegistro (UsuarioID,Ip,Fecha,SID) VALUES ('"+var[0].toString()+"', '"+httpServletRequest.getRemoteAddr()+"', '"+fecha.format(today)+":00','"+httpServletRequest.getSession().getId()+"');";
                    //Date today = new Date();
                    //SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                    //query = "INSERT UsuarioRegistro (UsuarioID, Ip, Fecha) VALUES ('"+var[0].toString()+"', '"+httpServletRequest.getRemoteAddr()+"', '"+format.format(today)+":00');";
                    em.getTransaction().begin();    
                    em.createNativeQuery(query).executeUpdate();
                    em.getTransaction().commit();
                } catch (Exception e) { }
                
                httpServletRequest.getSession().setAttribute("Usuario",usuario);
                httpServletRequest.getSession().setAttribute("UID",usuario.getId());
                this.uid=usuario.getId();
                this.error = false;
                em.close();
                usuario = null;
                em = null;
                var = null;
                System.gc();
                return "valido";
            } else {
                this.mensaje = "Ocurrió un error al intentar seleccionar el usuario.";
            }
            list = null;
        } catch (Exception e) {            
            if (e.getMessage().equals("getSingleResult() did not retrieve any entities.")) {
                this.mensaje = "Nombre de usuario o contraseña incorrectos.";
            } else {
                this.mensaje = "Ocurrió un error al intentar ingresar al Sistema";//e.getMessage();
            }
        }
        em.close();
        em = null;
        System.gc();
        return "novalido";
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username.toUpperCase();
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password.toUpperCase();
    }

    /**
     * @return the mensaje
     */
    public String getMensaje() {
        return mensaje;
    }

    /**
     * @param mensaje the mensaje to set
     */
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    /**
     * @return the error
     */
    public Boolean getError() {
        return error;
    }

    /**
     * @param error the error to set
     */
    public void setError(Boolean error) {
        this.error = error;
    }

    /**
     * @return the login
     */
    public Login getLogin() {
        return login;
    }

    /**
     * @param login the login to set
     */
    public void setLogin(Login login) {
        this.login = login;
    }

    /**
     * @return the uid
     */
    public Integer getUid() {
        return uid;
    }

    /**
     * @param uid the uid to set
     */
    public void setUid(Integer uid) {
        this.uid = uid;
    }
    
}
