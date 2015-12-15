<%@ page import="entities.Curso" %>
<%@ page import="java.util.ArrayList" %>
<jsp:useBean id="perfil" class="es.rotolearn.javaBean.RegistroBean" scope="session"/>
<!DOCTYPE html>
<html lang="es">
    <head>
        <title>Listado de cursos</title>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
        <link rel="stylesheet" type="text/css" href="./style/profes_plantilla.css" media="screen"/><!--CSS PLANTILLA-->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css">
		<link rel="stylesheet" type="text/css" href="./style/footer.css" media="screen"/>
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
                            <li><a href="Notificacion.form">Inicio</a></li>
                            <!--<li><a href="perfil.form">Mi Perfil</a></li>-->
                            <li><a href="catalogo.form">Cat&aacute;logo de cursos</a></li>
                            <!--<li><a class="activa" href="profes_panel.jsp">Panel de control</a></li>-->
                        </ul>
                        <ul class="nav navbar-nav navbar-right">
                            <%
			            	if(session.getAttribute("usuario") == null){
							%>
	                        <!-- NO REGISTRADO -->
	                        <li><a href="login.jsp"><span class="glyphicon glyphicon-log-in"></span>Inicia sesi&oacute;n</a></li>
	                        <li><a href="formulario_registro.jsp"><span class="glyphicon glyphicon-user"></span>Registrate</a></li>
	                        <%
	                        	}else{ 
	                        %>
	                        <!-- REGISTRADO -->
	                        <li><a href="profes_crearcurso.jsp"><span class="glyphicon glyphicon-plus"></span>Crear curso</a></li>
	                        <li class="dropdown">
	                            <a class="dropdown-toggle" data-toggle="dropdown" href="#"><span class="caret"></span> <%=session.getAttribute("usuario")%> <img src="./images/perfil/anonimo.jpeg" class="img-circle" alt="Cinque Terre" width="30" height="30"/></a>
	                            <ul class="dropdown-menu">
	                                <li><a href="perfil.form"><span class="glyphicon glyphicon-user"></span>Mi perfil</a></li>
	                                <% if(!perfil.getTipo().equals("alumn")){%>
	                                <li><a href="profes_panel.jsp"><span class="glyphicon glyphicon-th-large"></span>Panel de Control</a></li>
	                                <% }%> 
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
        <!--  MENSAJES DE OK/ERROR ELIMINACION CURSO -->
        <%if(request.getAttribute("borrado")!=null){
    	if(request.getAttribute("borrado").equals("ok")){ %>
				<div class="alert alert-success">
	                <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
					<strong>¡Curso eliminado con exito!</strong> 
				</div>
			<% }else{ 
			 
			       %>
			<div class="alert alert-danger">
					<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
					<strong>¡Cuidado!</strong> ¡Algo ha ocurrido y no se ha podido eliminar!
				</div>
			<% }} %>
            <div class="row col-sm-12">
                <h2 class="titulo">Listado de Cursos</h2>
                <div class="table-responsive">          
                    <table class="table table-condensed table-hover">
                        <thead>
                            <tr>
                                <th>Administrar</th>
                                <th>Validado</th>
                                <th>Destacado</th>
                                <th>T&iacute;tulo</th>
                                <th>Dificultad</th>
                                <th>Horas</th>
                                <th>Precio</th>
                                <th>Descripci&oacute;n</th>
                               
                            </tr>
                        </thead>
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
                			int ID = aux.getId();
                		%>
                            <tr>
                                <td>
                                	<form action="administrarCursos.form"  enctype="multipart/form-data">
                                		<input type="hidden" value="<%= ID %>" name="curso"/>	
                                		<input type="hidden" value="listado" name="tipo"/>	
                                	    <input class="btn btn-default btn-xs" type="submit" value="Administrar"/>
                                	</form>
                                </td>
                              	<td><%=aux.getValidado() %></td>
                              	<td><%=aux.getDestacado() %></td>
                                <td><%=aux.getTitulo() %></td>
                                <td><%=aux.getDificultad() %></td>
                                <td><%=aux.getHoras() %></td>
                                <td><%=aux.getPrecio() %><span class="glyphicon glyphicon-eur"></span></td>
                                <td><%=aux.getDescripcion() %></td>
                             
                            </tr>
                            <% }} %>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
        
    	<!--FIN CUERPO-->
    	
    	<!--PIE DE PAGINA-->
	    <footer>
		    <div class="container-fluid" id="pie">
		    	<div class="row">
		    		<div class="col-md-3 col-md-offset-1" id="footer-left">
		    			<img class="logotipo img-responsive" src="./images/logo.png">
		                <p class="footer-links">
		    				<a href="Notificacion.form">Home</a> &#45;
		    				<a href="faq.jsp">Faq</a> &#45;
		    				<a href="#">Contact</a>
		    			</p>
		    			<p class="footer-company-name">Roto2 Company &copy; 2015</p>
		    		</div>
		    		<div class="col-md-3 col-md-offset-1" id="footer-center">
		    			<div>
		    				<i class="fa fa-map-marker"></i>
		    				<p><span>6 Calle Jaime Balmes</span> Le&oacute;n, Castilla y Le&oacute;n</p>
		    			</div>
		    			<div>
		    				<i class="fa fa-phone"></i>
		    				<p>+34 288 288 288</p>
		    			</div>
		    			<div>
		    				<i class="fa fa-envelope"></i>
		    				<p><a href="mailto:support@rotolearn.com">support@rotolearn.com</a></p>
		    			</div>
		    		</div>
		    		<div class="col-md-3 col-md-offset-1" id="footer-right">
		    			<p class="footer-company-about">
		    				<span>Sobre la compa&ntilde;ia</span>
		    				Roto2 Company lleva afincada en el sector del software desde que Ilitri fund&oacute; un equipo lleno de peque&ntilde;os troles y ni&ntilde;os ratas. 
	    			</p>
		    			<div class="footer-icons">
		    				<a href="#"><i class="fa fa-facebook"></i></a>
		    				<a href="#"><i class="fa fa-twitter"></i></a>
		    				<a href="#"><i class="fa fa-linkedin"></i></a>
		    				<a href="#"><i class="fa fa-github"></i></a>
		    			</div>
		    		</div>
		    	</div>
		    </div>
	    </footer>
	    <!--FIN PIE DE PAGINA-->
    
    </body>
</html>