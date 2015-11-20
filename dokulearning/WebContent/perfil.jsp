<jsp:useBean id="perfil" class="es.rotolearn.javaBean.RegistroBean" scope="session"/>
<%@ page import="es.rotolearn.tablas.Curso_Alumno" %>
<%@ page import="java.util.ArrayList" %>
<!DOCTYPE html>
<html lang="es">
    <head>
    	<title>Perfil de <%=session.getAttribute("usuario")%></title>
    	<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
		<link rel="stylesheet" type="text/css" href="./style/plantilla.css" media="screen"/>
      	<link rel="stylesheet" type="text/css" href="./style/perfil_plantilla.css" media="screen"/>
      	<link rel="stylesheet" type="text/css" href="./style/footer.css" media="screen"/>
      	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
      	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
      	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css">
      	<script>
      		$(document).ready(function(){
				$('[data-toggle="tooltip"]').tooltip();   
			});
      	</script>
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
                    	<li><a href="index.jsp">Inicio</a></li>
                        <!--<li><a href="perfil.form">Mi perfil</a></li>-->
                        <li><a href="catalogo.form">Catalogo de cursos</a></li>
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
        
                <%
					if(request.getAttribute("modificado") != null)
					if(request.getAttribute("modificado").equals("si")){
				%>
						<div class="alert alert-success" style="margin-bottom:0px">
							<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
						    <strong>Datos guardados.</strong> Tus datos se han modificado correctamente.
						</div>
				<%
					}else{
				%>
						<div class="alert alert-danger" style="margin-top:10px" style="margin-bottom:0px">
							<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
						    <strong>Error al guardar</strong> Tus datos no han podido ser modificados.
						</div>
				<%
					}
				%>
		    <div class="row">

		        <div class="col-md-4 barra">
		            <div id="bSuperior">
		            	<a href="editar_perfil.jsp">Editar perfil<span class="glyphicon glyphicon glyphicon-pencil" aria-hidden="true"></span></a>
		            	<div class="limpiador"></div>
		            </div>
		            <div id="bMedio">
						<img id="imgPerfil" src="./images/maxresdefault.jpg" class="img-rounded"/>
						<div class="limpiador"></div>
					</div>
		            <div id="bInferior">
						<h1 id="nombre" class="datos"><%=perfil.getNombre()%></h1>
			            <h6 id="apellidos" class="datos"><%=perfil.getApellido1()%> <%= perfil.getApellido2()%></h6>
		            </div>
		        </div>
		        
		        <div class="col-md-8">
		            <ul class="nav nav-tabs">
                        <li class="active"><a data-toggle="tab" href="#perfil">Informaci&oacute;n</a></li>
                        <% 
                        if(perfil.getTipo().equals("alumn")){                        	
                			 %>
                        <li><a data-toggle="tab" href="#cursos">Cursos Matriculados</a></li>
                        <% } else {                            	
                			 %>  
                		<li><a data-toggle="tab" href="#cursos">Cursos Impartidos</a></li>
                			  <% }                            	
                			 %> 
                			 
                			 
                        <li><a data-toggle="tab" href="#logros">Logros</a></li>
                        <li><a data-toggle="tab" href="#deseos">Deseos</a></li>
                    </ul>
    
                    <div class="tab-content">
                        <div id="perfil" class="tab-pane fade in active">
                            <h3>Descripci&oacute;n</h3>
                            <p><%=perfil.getDescripcion()%></p>
                            
                            <h3>Intereses</h3>
                            <p><%=perfil.getIntereses()%></p>
                        </div>
                        <div id="cursos" class="tab-pane fade">  
                        <% 
                        if(perfil.getTipo().equals("alumn")){
                        	
                			 %>                      
                            <h3>Cursos Matriculados</h3>  
                            <% } else{
                            	
                			 %>    
                			 <h3>Cursos Impartidos</h3> 
                			 <% } 
                			 %>    
                               <table class="table">
                                <thead>
                                  <tr>
                                    <th>#</th>
                                    <th>Curso</th>
                                    <th>Descripci&oacute;n</th>
                                    <th>Iniciado</th>
                                    <th>Profesor</th>
                                    <th>Horas</th>
                                  </tr>
                                </thead>
                                <tbody>
                                  <tr>
                                    <td>1</td>
                                    <td>Programaci&oacute;n orientada a objetos</td>
                                    <td>Descripcion sobre la programaci&oacute;n orientada a objetos</td>
                                    <td>07/07/2015</td>
                      				<% 
                        if(perfil.getTipo().equals("alumn")){                        	
                			 %>
                                    <td>Roberto Arismendi Rodriguez</td>
                                    <% 
                        }else{                        	
                			 %>
                			 <td> <%=perfil.getNombre()%> <%=perfil.getApellido1()%> <%= perfil.getApellido2()%></td>
                			 <% 
                        }             	
                			 %>
                                    <td>288</td>
                                  </tr>
                                </tbody>
                            </table>
                        </div>
                        <div id="logros" class="tab-pane fade">
                            <h3>Mis logros</h3>
                            <p>Los logros se consiguen completando cursos.</p>
                            <div class="logro col-md-1" data-toggle="tooltip" data-placement="top" title="Logro conseguido en 'Curso de JAVA'"><img  src="./images/cursoTerminado.png" ></div>
                            <div class="logro col-md-1" data-toggle="tooltip" data-placement="top" title="Logro conseguido en 'Curso de C++'"><img  src="./images/cursoTerminado.png" ></div>
                            <div class="logro col-md-1" data-toggle="tooltip" data-placement="top" title="Logro conseguido en 'Curso de Wordpress'"><img  src="./images/cursoTerminado.png" ></div>
                            <div class="logro col-md-1" data-toggle="tooltip" data-placement="top" title="Logro conseguido en 'Curso de Joomla'"><img  src="./images/cursoTerminado.png" ></div>
                            <div class="logro col-md-1" data-toggle="tooltip" data-placement="top" title="Logro conseguido en 'Curso de MySQL'"><img  src="./images/cursoTerminado.png" ></div>

                        </div>
                        <div id="deseos" class="tab-pane fade">
                             <table class="table table-condensed table-hover">
                            <thead>
                              <tr>                                   
                               	<th>Curso</th>
                              </tr>
                            </thead>
                        <%
                        ArrayList<Curso_Alumno> dest = (ArrayList<Curso_Alumno>) request.getAttribute("deseos");
                        if(dest.size()==0){
                        	%>
                        	<tbody>
                               <tr>
                                 <td>No tienes ningun deseo</td> 
                               </tr>
                            </tbody>
                        	<% 
                        }else{
                		for(int i=0; i<dest.size();i++){
                			String h = dest.get(i).getTitulo();
                		%>
                	  		<tbody>
                               <tr>
                                 <td><a href="showCurso.form?titulo=<%= h %>"><%= dest.get(i).getTitulo()%></a></td> 
                               </tr>
                            </tbody>
                         <% }} %>
                         </table>
                        </div>
                    </div>
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
	    				<a href="index.html">Home</a> &#45;
	    				<a href="https://www.forocoches.com/">Faq</a> &#45;
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
	    				Roto2 Company lleva afincada en el sector del software desde que Ilitri fundo un equipo lleno de peque&ntilde;os troles y ni&ntilde;os ratas. 
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