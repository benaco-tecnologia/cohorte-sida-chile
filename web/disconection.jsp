<%
try{
    conexion.close();
	conexion=null; 
} catch (Exception ex){
	out.println(ex);	
}	
%>