<!DOCTYPE html>
<html lang="es">
<head>
  <title>Listado administradores</title>
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
    		<div class="col-sm-12">
    			<h2 class="titulo">Listado de Administradores</h2>
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
			           		<tr>
			            		<td><input class="btn btn-default btn-xs" type="submit" value="Eliminar"></td>
			            		
		            		</tr>
			           	
			            </tbody>
		            </table>
	    		</div>
	    	</div>
    	</div>
    </div>
	<!--FIN CUERPO-->

</body>
</html>