<!DOCTYPE html>
<%@ page import="java.util.ArrayList" %>
<%@ page import="es.rotolearn.tablas.Curso" %>
<html lang="es">
<head>
  <title>Alta de cursos</title>
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
    <div id="cuerpo" class="container row col-sm-12">
        <h2 class="titulo">Alta de Cursos</h2>
        <div class="table-responsive">          
            <table class="table table-condensed table-hover">
            <thead>
                <tr>
                    <th>Aceptar</th>
                    <th>Rechazar</th>
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
               	<%
               		ArrayList<Curso> val = (ArrayList<Curso>) request.getAttribute("validados");
               		String h;
               		for(int i=0; i<val.size();i++){
               	%>
            
                <tr>
                	<td>
                		<% h=val.get(i).getTitulo();%>
                    		<a href="validar.form?validar=<%=h%>" class="btn btn-default btn-xs" type="submit" >Aceptar</a>
                    </td>
                    <td>
                    		<a href="denegar.form?denegar=<%=h%>" class="btn btn-default btn-xs" type="submit" >Denegar</a>
                    </td> 
                    <td><%=val.get(i).getTitulo() %></td>
                    <td><%=val.get(i).getUsuario() %></td>
                    <td><%=val.get(i).getDificultad() %></td>
                    <td><%=val.get(i).getHoras() %></td>
                    <td><%=val.get(i).getPrecio() %></td>
                    <td><%=val.get(i).getCategoria() %></td>
                    <td><%=val.get(i).getDescripcion() %></td>
                    <td><%=val.get(i).getImagen() %></td>
                </tr>
                <%
               		}
                %>
            </tbody>
            </table>
        </div>
    </div>
	<!--FIN CUERPO-->

</body>
</html>