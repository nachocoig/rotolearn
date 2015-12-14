<jsp:useBean id="perfil" class="es.rotolearn.javaBean.RegistroBean" scope="session"/>
<%@ page import="entities.*" %>
<%@ page import="java.util.ArrayList" %>
<!DOCTYPE html>
<html lang="es">
	<head>
		<title>Informaci&oacute;n de curso</title>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
		<link rel="stylesheet" type="text/css" href="./style/plantilla.css" media="screen"/><!--CSS PLANTILLA-->
		<link rel="stylesheet" type="text/css" href="./style/info-curso.css" media="screen"/>
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
		<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
		<link rel="stylesheet" type="text/css" href="./style/footer.css" media="screen"/>
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css">
		 <script>
        
        $(function() {
        	$('[rel="popover"]').popover({
       
	 		        container: 'body',
	 		        html: true,
	 		        content: function () {
	 		            var clone = $($(this).data('popover-content')).clone(true).removeClass('hide');
	 		         
	 		            return clone;
	 		        }
	 		    }).click(function(e) {      		    	
	 		        e.preventDefault();
	 		   
	 		    });   });
        </script>
	</head>
	<body>
	    <%	HttpSession miSession = request.getSession(true); %>
	
    <!--CABECERA-->
       <header>
        <%
    	Curso aux = (Curso) request.getAttribute("curso");
    	int h = aux.getId();
    %>
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
                        		 <form method="POST" action="Notificacion.form"  enctype="multipart/form-data">
                        		 							<input type="hidden" value="<%=h %>" name="id"/>
					                                		<input type="hidden" value="curso" name="tipo"/>	
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
		<div class="row" id="cabeza" style="background-image:url('images/im_cursos/<%=aux.getId()%>_curso.jpg')">
			<div class="col-md-3 col-md-offset-1" >
			<% if(request.getAttribute("deseo")!=null) 
				
				if(request.getAttribute("deseo").equals("ok")){ %>
				<div class="alert alert-success">
	                <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
					<strong>¡Curso a&ntilde;adido a la lista de deseos!</strong> 
				</div>
			<% }else{ 
			 
			       if(session.getAttribute("usuario") == null){
						%>
				<div class="alert alert-danger">
					<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
					<strong>¡Cuidado!</strong> ¡Necesitas loguearte para añadir a deseados!
				</div>
			<% }else{ %>
			<div class="alert alert-danger">
					<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
					<strong>¡Cuidado!</strong> ¡Ya a&ntilde;adiste este curso!
				</div>
			<% }} %>
				<ul class="list-group">
					<li class="list-group-item titulo">
						<span class="info"><%=aux.getTitulo() %></span>
					</li>
					<li class="list-group-item">
						<span class="glyphicon glyphicon-time" aria-hidden="true"></span>
						<span class="info">Numero de horas:</span><%=aux.getHoras() %>
					</li>
					<li class="list-group-item">
						<span class="glyphicon glyphicon-euro" aria-hidden="true"></span>
						<span class="info">Precio:</span><%=aux.getPrecio() %> &euro;
					</li>
					<li class="list-group-item">
						<span class="glyphicon glyphicon-piggy-bank" aria-hidden="true"></span>
						<span class="info">Descuento activo:</span>75%
					</li>
					<li class="list-group-item">
						<span class="glyphicon glyphicon-signal" aria-hidden="true"></span>
						<span class="info">Dificultad: </span><%=aux.getDificultad() %>
					</li>
					<li class="list-group-item">
						<span class="glyphicon glyphicon-education" aria-hidden="true"></span>
						<span class="info">Profesor/es:</span><%=aux.getUsuario().getNombre() + " " + aux.getUsuario().getApellido1() + " " + aux.getUsuario().getApellido2()  %>
						<br>
					</li>
					<li class="list-group-item">
						<form method=post action="pagina_pago.form">
							<input type="hidden" value="<%=h %>" name="cursoCompra"/>
							<button class="btn btn-success breg btn-block" type="submit">Inscr&iacute;bete</button>
						</form>
						<form method="post" action="añadirDeseo.form">
							<input type="hidden" value="<%=h %>" name="id"/>
							<button class="btn btn-danger breg btn-block" type="submit">Deseado <span class="glyphicon glyphicon-heart" aria-hidden="true"></span></button>
						</form>
					</li>
				</ul>
			</div>
		</div>
				<div class="row" id="descrip">
			<ul class="nav nav-pills nav-justified">
			  <li class="active"><a data-toggle="tab" href="#descripcion">Descripci&oacute;n</a></li>
			  <li><a data-toggle="tab" href="#menu2">Temario del curso</a></li>
			  <li><a data-toggle="tab" href="#chat">Chat</a></li>
			</ul>
			
			<div class="tab-content opciones col-md-6 col-md-offset-3">
			  <div id="descripcion" class="tab-pane fade in active">
			    <h3>Descripci&oacute;n del curso</h3>
			    <p><%=aux.getDescripcion() %></p>
			  </div>
			  
			  <div id="menu2" class="tab-pane fade">
			    <h3>Temario del curso</h3>
			    
			    <%
			    ArrayList<Seccion> secciones = (ArrayList<Seccion>) request.getAttribute("secciones");
			    ArrayList<Leccion> lecciones = (ArrayList<Leccion>) request.getAttribute("lecciones");
			    ArrayList<Leccion> auxLecciones = new ArrayList<Leccion>();
			    ArrayList<Material> materiales = (ArrayList<Material>) request.getAttribute("materiales");
				ArrayList<Material> auxMateriales = new ArrayList<Material>();
			    
			    
		    for(int i = 0; i < secciones.size(); i++){    %>
			    <h4>Seccion <%=i+1%>: <%=secciones.get(i).getNombre() %></h4>
		    	<%
		    	for(int j = 0; j < lecciones.size(); j++)
		    		if(lecciones.get(j).getSeccion().getId() == secciones.get(i).getId())
		    			auxLecciones.add(lecciones.get(j));
		    	
		    	for(int j = 0; j < auxLecciones.size(); j++){
		    	%>
			     <div class="panel-group" id="accordion">
				  <div class="panel panel-default">
				    <div class="panel-heading">
				      <h4 class="panel-title">
				        <a data-toggle="collapse" data-parent="#accordion" href="#collapse<%=i+1%><%=j+1%>"><%=auxLecciones.get(j).getNombre()%></a>
				      </h4>
				    </div>
				    <div id="collapse<%=i+1%><%=j+1%>" class="panel-collapse collapse">
				      <div class="panel-body">
						<p><%=auxLecciones.get(j).getDescripcion() %>
						
						<div class="list-group">
						<%
		    			for(int k = 0; k < materiales.size(); k++)
		    				if(materiales.get(k).getLeccion().getId() == auxLecciones.get(j).getId())
		    					auxMateriales.add(materiales.get(k));
		    			for(int k = 0; k < auxMateriales.size(); k++){
		    				
		    				String glyphicon = "";
		    				if(auxMateriales.get(k).getTipo().toLowerCase().equals("mp3"))
		    					glyphicon = "music";
		    				else if(auxMateriales.get(k).getTipo().toLowerCase().equals("mp4") || auxMateriales.get(k).getTipo().toLowerCase().equals("m4v"))
		    					glyphicon = "film";
		    				else
		    					glyphicon = "file";
		    			%>
					        <a href="materiales/<%=auxMateriales.get(k).getId()%>_mat.<%=auxMateriales.get(k).getTipo() %>" download="<%=auxMateriales.get(k).getNombre()%>.<%=auxMateriales.get(k).getTipo() %>" class="list-group-item">
					            <span class="glyphicon glyphicon-<%=glyphicon%>"></span> 
					            <%=auxMateriales.get(k).getNombre()%> 
					        </a>
					    <%auxMateriales.clear();
					    } %>
					    </div>
						
						
						
				      </div>
				    </div>
				  </div>
				</div> 
			<%	auxLecciones.clear();
		    	}
		    } %>
			  </div>
			  <div id="chat" class="tab-pane fade">
			    <h3>Chat</h3>
			    
			    <div id="wrapper">
				    <div id="menu">
				        <p class="welcome">Welcome, <b></b></p>
				        <p class="logout"><a id="exit" href="#">Exit Chat</a></p>
				        <div style="clear:both"></div>
				    </div>
				    <% String mensaje;
				    if(request.getAttribute("mensajes")==null){
				    	mensaje = "";
				    	
				    }else mensaje=request.getAttribute("mensajes").toString(); %>
				    <ul id="chatbox">
				    <li><%=mensaje%> </li>
				    </ul>
			     
				    <form name="message" method="post" action="escribirChat.form">
				        <input name="mensaje" type="text" id="usermsg" size="63" />
				        <input name="submitmsg" type="submit"  id="submitmsg" value="Send" />
				     </form>
				    <form name="leer" method="post" action="leerChat.form">
				        <input name="refrescar" type="submit"  id="refrescar" value="Refrescar" />
				    </form>
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