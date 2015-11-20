<!DOCTYPE html>
<html lang="es">
	<head>
	  <title>Listado materiales</title>
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
	                            <li><a href="admin_listadoalumnos.jsp">Alumnos</a></li>
	                            <li><a href="admin_listadoprofesores.jsp">Profesores</a></li>
	                            <li><a href="admin_listadoadmin.jsp">Administradores</a></li>
	                          </ul>
	                        </li>
	                        <li class="dropdown">
	                            <a class="dropdown-toggle" data-toggle="dropdown" href="#">Cursos
	                            <span class="caret"></span></a>
	                            <ul class="dropdown-menu">
	                                <li><a href="admin_altacursos.jsp">Alta</a></li>
	                                <li><a href="admin_listadocursos.jsp">Listado</a></li>
	                                <li><a href="admin_destacadocursos.jsp">Destacados</a></li> 
	                            </ul>
	                        </li>
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
	        <h2 class="titulo">Listado de Materiales</h2>
	        <div class="table-responsive">          
	            <table class="table table-condensed table-hover">
	            <thead>
	                <tr>
	                    <th>Eliminar</th>
	                    <th>Curso</th>
	                    <th>Profesor</th>
	                    <th>Secci&oacute;n</th>
	                    <th>Lecci&oacute;n</th>
	                    <th>Nombre</th>
	                </tr>
	            </thead>
	            <tbody>
	                <tr>
	                    <td><input class="btn btn-default btn-xs" type="submit" value="Eliminar"></td>
	                    <td>Java 1</td>
	                    <td>Tomasito</td>
	                    <td>1. Java desde cero</td>
	                    <td>1.1. Tipos de datos b&aacute;sicos</td>
	                    <td>java_tipos.pdf</td>
	                </tr>
	                <tr>
	                    <td><input class="btn btn-default btn-xs" type="submit" value="Eliminar"></td>
	                    <td>Java 1</td>
	                    <td>Tomasito</td>
	                    <td>1. Java desde cero</td>
	                    <td>1.2. Arrays</td>
	                    <td>java_arrays.pdf</td>
	                </tr>
	                <tr>
	                    <td><input class="btn btn-default btn-xs" type="submit" value="Eliminar"></td>
	                    <td>Java 1</td>
	                    <td>Tomasito</td>
	                    <td>2. Clases en Java</td>
	                    <td>2.1. Hola Mundo</td>
	                    <td>holamundo.jar</td>
	                </tr>
	            </tbody>
	            </table>
	        </div>
	    </div>
		<!--FIN CUERPO-->
	
	</body>
</html>