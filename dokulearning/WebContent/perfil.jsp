<jsp:useBean id="perfil" class="es.rotolearn.javaBean.RegistroBean" scope="session"/>
<%@ page import="entities.*" %>
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
      		$(function(){
      		    $('[rel="popover"]').popover({
      		        container: 'body',
      		        html: true,
      		        content: function () {
      		            var clone = $($(this).data('popover-content')).clone(true).removeClass('hide');
      		         
      		            return clone;
      		        }
      		    }).click(function(e) {      		    	
      		        e.preventDefault();
      		   
      		    });
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
                    	<li><a href="Notificacion.form">Inicio</a></li>
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
                         <li><a href="#" rel="popover" data-placement="bottom" data-popover-content="#myPopover" id="leer">
                        	<% String n = (String)request.getAttribute("Notificaciones");
                        	if(n.equals("0") || n.equals("no") || n == null){%>
                        		<span class="badge">0</span>
                        	<% }
                        	else{%>
                        		<span class="badge"><%=n %></span>
                        	<% } %>
                        	</a>
                        	</li>
                        	<div id="myPopover" class="hide">
                        		
                        		<p>Tus notificaciones: </p>
                        		<p></p>
                        		<%
                        		ArrayList<Notificacion> noLeidas= (ArrayList<Notificacion>)request.getAttribute("ListaNoLeidas");
                        		ArrayList<Notificacion> notificaciones= (ArrayList<Notificacion>)request.getAttribute("ListaNotificaciones");
                        	
                        		if(noLeidas == null && notificaciones == null){ %>
                        		<p> No tienes notificaciones </p>
                        		<%}else{ 
                        			if(noLeidas == null){}
                        			else{
                        				for(int i=0; i<noLeidas.size();i++){                 			
                        					%>
                        						<p> <strong><%=noLeidas.get(i).getDescripcion() %> </strong></p>
                        				<%}
                        			                      			}
                        			if(notificaciones == null){}
                        			else{
                        				int size;
                        				if(notificaciones.size() >=5){
                        					size=notificaciones.size()-5;
                        				}
                        				else size=0;
                        				for(int i=notificaciones.size()-1; i>=size;i--){                 			
                        					%>
                        						<p><%=  notificaciones.get(i).getDescripcion() %></p>
                        				<%}
                        			}
                        		}%>
                        		 <form method="POST" action="perfil.form"  enctype="multipart/form-data">
					                                		<input type="hidden" value="perfil" name="tipo"/>	
					                                		<input type="hidden" value="SI" name="leido"/>	
					                                		<input class="btn btn-default btn-xs glyphicon glyphicon-eye-open" type="submit" value="Marcar como leido">
					                                	</form>
                        	</div>
                        
                        <li class="dropdown">
                            <a class="dropdown-toggle" data-toggle="dropdown" href="#"><img src="images/im_usuarios/<%=perfil.getNickName()%>_perfil.jpg" class="img-circle" alt="Cinque Terre" width="30" height="30"/> <%=session.getAttribute("usuario")%> <span class="caret"> </span></a>
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
        <!----------------------------------------MODIFICAR PERFIL---------------------------------------->
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
				<!----------------------------------------ACEPTAR ---------------------------------------->
                <%
					if(request.getAttribute("act") != null)
					if(request.getAttribute("act").equals("okv")){
				%>
						<div class="alert alert-success" style="margin-bottom:0px">
							<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
						    <strong>Petici&oacute;n aceptada.</strong> Ahora estas en ese curso.
						</div>
				<%
					}else{
				%>
						<div class="alert alert-danger" style="margin-top:10px" style="margin-bottom:0px">
							<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
						    <strong>Error al aceptar</strong> Tus datos no han podido ser modificados.
						</div>
				<%
					}
				%>
				<!----------------------------------------DENEGAR ---------------------------------------->
                <%
					if(request.getAttribute("borrado") != null)
					if(request.getAttribute("borrado").equals("ok")){
				%>
						<div class="alert alert-success" style="margin-bottom:0px">
							<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
						    <strong>Petici&oacute;n denegada.</strong> 
						</div>
				<%
					}else{
				%>
						<div class="alert alert-danger" style="margin-top:10px" style="margin-bottom:0px">
							<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
						    <strong>Error al denegar</strong> Tus datos no han podido ser modificados.
						</div>
				<%
					}
				%>
		    <div class="row">

		        <div class="col-md-4 barra">
		            <div id="bSuperior">
		            	<form method="POST" action="Notificacion.form"  enctype="multipart/form-data">
					                                		<input type="hidden" value="editar" name="tipo"/>	
					                                		<input type="hidden" value="NO" name="leido"/>	
					                                		<input class="btn btn-default btn-xs" type="submit" value="Editar Perfil"><span class="glyphicon glyphicon glyphicon-pencil" aria-hidden="true"></span>
					                                	</form>
		            </div>
		            <div id="bMedio">
						<img id="imgPerfil" src="images/im_usuarios/<%=perfil.getNickName()%>_perfil.jpg" class="img-rounded"/>
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
                            
                            <%
                            	String [] interes = perfil.getIntereses().split("/");
                            	
                            	if(interes[0].equals("")){
                            %>
                            	<p>No tienes intereses</p>
                            
                            <%
                            	}else{
                           	%>
                           	<ul>
                           	<%
                            		for(int i=0;i<interes.length;i++){
                            			if(!interes[i].equals("")){
                            %>
                            	<li><%=interes[i] %></li>
                            <%
                            			}
                            		}
                            	}
                            %>
                            </ul>
                        </div>
                        <div id="cursos" class="tab-pane fade">  
                        
                        
                          <div class="table-responsive">
                        <%
                        if(perfil.getTipo().equals("alumn")){ 
                        %>
                        <h3>Cursos Matriculados</h3> 
                            <table class="table table-condensed table-hover">
                                <thead>
                                  <tr>
                                    <th>#</th>
                                    <th>Curso</th>
                                    <th>Descripci&oacute;n</th>
                                    <th>Profesor</th>
                                    <th>Horas</th>
                                  </tr>
                                </thead>
                                <tbody>
                                  <%if(request.getAttribute("cursos").equals("no")){
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
	                              	<td><%=aux.getId() %></td>
	                                <td><%=aux.getTitulo() %></td>
	                                <td><%=aux.getDescripcion() %></td>
	                                <td><%=aux.getUsuario().getNickname() %></td>
	                                <td><%=aux.getHoras() %></td>
	                                </tr>                         
                                 
                            <% 
                            		}
                        		}%>
                        	</tbody>
                            </table> 
                        	<%
                            }
                            else{ %>
                             <h3>Invitaciones a cursos</h3>
	                          <table class="table table-condensed table-hover">
                                <thead>
                                  <tr>
                                    <th>#</th>
                                    <th>Curso</th>
                                    <th>Descripci&oacute;n</th>
                                    <th>Profesor</th>
                                    <th>Horas</th>
                                    <th>Aceptar</th>
                                    <th>Denegar</th>
                                  </tr>
                                </thead>
                                <tbody>
							<%if(request.getAttribute("peticiones").equals("no")){
                                	%>
                                       <tr>
                                         <td>No tienes ninguna petici&oacute;n</td> 
                                       </tr>
                                	<% }else{
                            	ArrayList<Curso> pet = (ArrayList<Curso>) request.getAttribute("listaPeticiones");
                            	for(int i=0; i<pet.size();i++){                            		                          	
                					Curso aux = pet.get(i);
                				
                			 %>    
                			
                                  <tr>
                               	
	                              	<td><%=aux.getId() %></td>
	                                <td><%=aux.getTitulo() %></td>
	                                <td><%=aux.getDescripcion() %></td>
	                                <td><%=aux.getUsuario().getNickname() %></td>
	                                <td><%=aux.getHoras() %></td>
	                                 <td>
		                                                <form method="POST" action="administrarCursos.form"  enctype="multipart/form-data">
					                                		<input type="hidden" value="<%= aux.getId() %>" name="curso"/>
					                                		<input type="hidden" value="<%= perfil.getId() %>" name="asociado"/>	
					                                		<input type="hidden" value="validarAsociado" name="tipo"/>	
					                                		<input class="btn btn-default btn-xs" type="submit" value="Aceptar">
					                                	</form></td>
					                                	<td>
		                                                <form method="POST" action="administrarCursos.form"  enctype="multipart/form-data">
					                                		<input type="hidden" value="<%= aux.getId() %>" name="curso"/>
					                                		<input type="hidden" value="<%= perfil.getId() %>" name="asociado"/>	
					                                		<input type="hidden" value="denegarAsociado" name="tipo"/>	
					                                		<input class="btn btn-default btn-xs" type="submit" value="Denegar">
					                                	</form>
	                                                </td></tr>    
	                         <%}} %>                     
                            </tbody>
                            </table> 
                            <h3>Cursos impartidos</h3> 
	                        <table class="table table-condensed table-hover">
                                <thead>
                                  <tr>
                                    <th>#</th>
                                    <th>Curso</th>
                                    <th>Descripci&oacute;n</th>
                                    <th>Profesor</th>
                                    <th>Horas</th>
                                  </tr>
                                </thead>
                                <tbody>
	                                 <%if(request.getAttribute("cursos").equals("no")){
                        	%>
                        	
                               <tr>
                                 <td>No hay ningun curso</td> 
                               </tr>
                          
                        	<% 
                                  }else{
                            	ArrayList<Curso> cursos = (ArrayList<Curso>) request.getAttribute("listaCursos");
                            	for(int i=0; i<cursos.size();i++){                            		                          	
                					Curso aux = cursos.get(i);
                					
                        			
                			 %>  
                                 
                                  <tr>
                               	
	                              	<td><%=aux.getId() %></td>
	                                <td><%=aux.getTitulo() %></td>
	                                <td><%=aux.getDescripcion() %></td>
	                                <td><%=aux.getUsuario().getNickname() %></td>
	                                <td><%=aux.getHoras() %></td>
	                                
	                                </tr>                         
                                 
                            <%}}%>
                            </tbody>
                            </table> 
                            <%} %> 
                         </div>       		
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
                               	<th><h3>Cursos en lista de deseos</h3></th>
                              </tr>
                            </thead>
                        <%
                        
                        if(request.getAttribute("nodeseos").equals("si")){
                        	%>
                        	<tbody>
                               <tr>
                                 <td>No tienes ningun deseo</td> 
                               </tr>
                            </tbody>
                        	<% 
                        }else{
                        ArrayList<Curso> dest = (ArrayList<Curso>) request.getAttribute("deseos");
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