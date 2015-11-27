<%@ page import="entities.Usuario" %>
<%@ page import="java.util.ArrayList" %>

<!DOCTYPE html>
<html lang="es">
<head>
  <title>Listado profesores</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
  <link rel="stylesheet" type="text/css" href="./style/admin_plantilla.css" media="screen"/><!--CSS PLANTILLA-->
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
  
</head>
<body>
    <!--CABECERA-->
    <header>
        <div class="container-fluid">
            <nav id="menu" class="navbar navbar-default">
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#myNavbar">
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>                        
                    </button>
            	    <img class="navbar-brand logotipo" src="./images/logo.png">
                </div>
                <div class="collapse navbar-collapse" id="myNavbar">
                    <ul class="nav navbar-nav">
                        <li class="dropdown">
                          <a class="dropdown-toggle" data-toggle="dropdown" href="#">Usuarios
                          <span class="caret"></span></a>
                          <ul class="dropdown-menu">
                            <li><a href="veralumn.form">Alumnos</a></li>
	                         <li><a href="verprofes.form">Profesores</a></li>
                          </ul>
                        </li>
                        <li class="dropdown">
                            <a class="dropdown-toggle" data-toggle="dropdown" href="#">Cursos
                            <span class="caret"></span></a>
                            <ul class="dropdown-menu">
                                 <li><a href="listadovalidados.form">Alta</a></li>
                              <li><a href="listadocursos.form">Listado</a></li>
	                                <li><a href="listadodestacados.form">Destacados</a></li>
                            </ul>
                        </li>
                        <li><a href="admin_listadomateriales.jsp">Materiales cursos</a></li>
	                    <li><a href="admin_listadocupones.jsp">Cupones descuento</a></li>
                    </ul>
                    <ul class="nav navbar-nav navbar-right">
                        <li><a href="admin_reg.jsp"><span class="glyphicon glyphicon-plus"></span>A&ntilde;adir Usuario</a></li>
                        
                        <%
                        	if(session.getAttribute("logueado").equals("true")){
                        %>
                        <li class="dropdown">
                        	<a class="dropdown-toggle" data-toggle="dropdown" href="#"><span class="caret"></span> <%= session.getAttribute("usuario") %> <img src="./images/perfil/anonimo.jpeg" class="img-circle" alt="Cinque Terre" width="30" height="30"/></a>
                       		<ul class="dropdown-menu">
                            	<li><a href="admin_index.jsp"><span class="glyphicon glyphicon-th-large"></span>Panel de Control</a></li>
                            	<li><a href="logout.form"><span class="glyphicon glyphicon-remove-sign"></span>Cerrar sesi&oacute;n</a></li>
                        	</ul>
                        </li>
                        <%
                        	}	
                        %>
                        
                    </ul>
                </div>
            </nav>
        </div>
    </header>
    <!--FIN CABECERA-->
    
    <!--CUERPO-->
    <div id="cuerpo" class="container-fluid">
    	<div class="row">
    	<%if(request.getAttribute("borrado")!=null){
    	if(request.getAttribute("borrado").equals("ok")){ %>
				<div class="alert alert-success">
	                <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
					<strong>¡Usuario eliminado con exito!</strong> 
				</div>
			<% }else{ 
			 
			       %>
			<div class="alert alert-danger">
					<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
					<strong>¡Cuidado!</strong> ¡Algo ha ocurrido y no se ha podido eliminar!
				</div>
			<% }} %>
    		<div class="col-sm-12">
    			<h2 class="titulo">Listado de Profesores</h2>
	        	<div class="table-responsive">          
		            <table class="table table-condensed table-hover">
			            <thead>
			                <tr>
			                    <th>Eliminar</th>
			                    <th>Tipo usuario</th>
			                    <th>Nombre</th>
			                    <th>Primer apellido</th>
			                    <th>Segundo apellido</th>
			                    <th>Nickname</th>
			                    <th>Email</th>
			                    <th>Contrase&ntilde;a</th>
			                    <th>Fecha nacimiento</th>
			                    <th>Direcci&oacute;n</th>
			                    <th>Descripci&oacute;n</th>
			                    <th>Intereses</th>
			                    <th>Telefono</th>
			                </tr>
			            </thead>
			            <tbody>
			             <%if(request.getAttribute("usuarios").equals("no")){
                        	%>
                        	
                               <tr>
                                 <td>No hay ningun profesor</td> 
                               </tr>
                            
                        	<% 
                        }else{
                        ArrayList<Usuario> dest = (ArrayList<Usuario>) request.getAttribute("listaUsuarios");
                		for(int i=0; i<dest.size();i++){
                			Usuario aux = dest.get(i);
                			int ID = aux.getId();
                		%>
			           		<tr>
			            		<td><a class="glyphicon glyphicon-remove" href="borrarprofes.form?ID=<%=ID %>"></a></td>
			            		<td><%= aux.getTipo() %></td>
			            		<td><%= aux.getNombre() %></td>
			            		<td><%= aux.getApellido1() %></td>
			            		<td><%= aux.getApellido2() %></td>
			            		<td><%= aux.getNickname() %></td>
			            		<td><%= aux.getEmail() %></td>
			            		<td><%= aux.getPass() %></td>
			            		<% if (aux.getFecha_nac()==null){ %>
			            		<td> No especificado </td>
			            		<%}else{ %>
			            		<td><%= aux.getFecha_nac() %></td>
			            		<% }if (aux.getDireccion()==null){ %>
			            		<td> No especificado </td>
			            		<%}else{ %>
			            		<td><%= aux.getDireccion() %></td>
			            		<%} if (aux.getDescripcion()==null){ %>
			            		<td> No especificado </td>
			            		<%}else{ %>
			            		<td><%= aux.getDescripcion() %></td>
			            		<%} if (aux.getIntereses()==null){ %>
			            		<td> No especificado </td>
			            		<%}else{ %>
			            		<td><%= aux.getIntereses() %></td>
			            		<% } %>
			            		<td><%= aux.getTelefono() %></td>
		            		</tr>
			           	<% }} %>
			           	
			            </tbody>
		            </table>
	    		</div>
	    	</div>
    	</div>
    </div>
	  <!--FIN CUERPO-->

</body>
</html>