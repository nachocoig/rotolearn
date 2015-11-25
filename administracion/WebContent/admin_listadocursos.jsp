<%@ page import="entities.Curso" %>
<%@ page import="java.util.ArrayList" %>
<!DOCTYPE html>
<html lang="es">
<head>
  <title>Listado cursos</title>
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
                                <li><a href="admin_altacursos.jsp">Alta</a></li>
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
    <div id="cuerpo" class="container row col-sm-12">
        <h2 class="titulo">Listado de Cursos</h2>
        <div class="table-responsive">          
            <table class="table table-condensed table-hover">
            <thead>
                <tr>
                    <th>Destacar</th>
                    <th>Eliminar</th>
                    <th>Id</th>
                    <th>Validado</th>
                    <th>Destacado</th>
                    <th>T&iacute;tulo</th>
                    <th>Profesor</th>
                    <th>Dificultad</th>
                    <th>Horas</th>
                    <th>Precio</th>
                    <th>Categoria</th>
                    <th>Descripci&oacute;n</th>
                    <th>Imagen</th>
                </tr>
            </thead>
            <tbody>
             <tbody>
			            <%if(request.getAttribute("curso").equals("no")){
                        	%>
                        	
                               <tr>
                                 <td>No hay ningun curso</td> 
                               </tr>
                            
                        	<% 
                        }else{
                        ArrayList<Curso> dest = (ArrayList<Curso>) request.getAttribute("listaCursos");
                		for(int i=0; i<dest.size();i++){
                			Curso aux = dest.get(i);
                		
                		%>
			           		<tr>
			           			<td><input class="btn btn-default btn-xs" type="submit" value="Destacar"></td>
			            		<td><input class="btn btn-default btn-xs" type="submit" value="Eliminar"></td>
			            		<td><%= aux.getId() %></td>
			            		<td><%= aux.getValidado() %></td>
			            		<td><%= aux.getDestacado() %></td>
			            		<td><%= aux.getTitulo() %></td>
			            		<td><%= aux.getUsuario().getNickname() %></td>
			            		<td><%= aux.getDificultad() %></td>
			            		<td><%= aux.getHoras() %></td>
			            		<td><%= aux.getPrecio() %></td>			            		
			            		<td><%= aux.getCategoria() %></td>			            		
			            		<td><%= aux.getDescripcion() %></td>
			            		<td>Imagen.jpg</td>
		            		</tr>
			           	<% }} %>
            
            </tbody>
            </table>
        </div>
    </div>
	  <!--FIN CUERPO-->

</body>
</html>