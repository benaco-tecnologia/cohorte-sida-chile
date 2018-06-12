<%
    Connection conexion=null; 
    String driver = "com.mysql.jdbc.Driver";
    String url = "jdbc:mysql://localhost:3306/cohorte?zeroDateTimeBehavior=convertToNull";
    String usuario = "root";
    String clave = "system";
	
	try{
        Class.forName(driver);
        conexion = DriverManager.getConnection(url,usuario,clave);
    } catch (Exception ex){
            out.println(ex);	
    }
        
    try{
        if(session.getAttribute("Usuario")==null||session.getAttribute("UID")==null){
                String redirectURL = "/Cohorte2/";
        }
    }catch(Exception e){
            String redirectURL = "/Cohorte2/";
    }
%>