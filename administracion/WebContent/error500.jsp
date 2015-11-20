<%@ page isErrorPage="true" %>
<!DOCTYPE html>
	<html lang="es">
	<head>
	  <title>Panel Administraci&oacute;n</title>
	  <meta charset="utf-8">
	  <meta name="viewport" content="width=device-width, initial-scale=1">
	  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
	  <link rel="stylesheet" type="text/css" href="./style/admin_plantilla.css" media="screen"/><!--CSS PLANTILLA-->
	  <link rel="stylesheet" type="text/css" href="./style/error.css" media="screen"/><!--CSS PLANTILLA-->
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
			    <div class="col-md-8 col-md-offset-2">
			    	<h1> <img src="http://s8.postimg.org/63bxmuh85/NUSENUSE_HD.gif" height="100px">Error 500 - Error interno del servidor.</h1>
			    </div>
			</div>
        </div>
        <!--FIN CUERPO-->
        
    </body>
</html>
