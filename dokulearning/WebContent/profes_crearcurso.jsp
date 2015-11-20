<jsp:useBean id="perfil" class="es.rotolearn.javaBean.RegistroBean" scope="session"/>
<!DOCTYPE html>
<html lang="es">
	<head>
		<title>Creaci&oacute;n de curso</title>
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
                            <li><a href="index.jsp">Inicio</a></li>
                           	<!--<li><a href="perfil.form">Mi Perfil</a></li> -->
                            <li><a href="catalogo.form">Cat&aacute;logo de cursos</a></li>
                            <!--<li><a class="activa" href="profes_panel.jsp">Panel de control</a></li> -->
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
           <div id="miga"><a href="profes_panel.jsp">Panel de control</a> > <a href="profes_crearcurso.jsp">A&ntilde;adir curso</a></div>

	    </header>
	    <!--FIN CABECERA-->
	    
	    <!--CUERPO-->
	    <div class="container-fluid">
        	<div class="row" id="cuerpo">
				<form role="form" method="post" action="curso.form">
		        <div class="col-md-4 col-md-offset-2">
		        	<%
						if(request.getAttribute("curso") != null)
						if(request.getAttribute("curso").equals("si")){
					%>
						<div class="alert alert-success" style="margin-bottom:0px">
							<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
						    <strong>Curso creado.</strong> Tu curso se ha creado correctamente.
						</div>
					<%
						}else{
					%>
						<div class="alert alert-danger" style="margin-top:10px" style="margin-bottom:0px">
							<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
						    <strong>Error al crear curso.</strong> El Titulo del curso ya existe.
						</div>
					<%
						}
					%>
		        	<h2>Creaci&oacute;n de curso</h2>
					<div class="form-group">
					    <label  for="titulo"><span class="red">*</span>T&iacute;tulo</label>
						<input type="text" class="form-control" name="titulo" placeholder="Introduce el t&iacute;tulo que tendr&aacute; tu curso" required>
					</div>
					<div class="form-group">
					    <label  for="nivel"><span class="red">*</span>Categor&iacute;a</label>
					    <select name="categoria" class="form-control">
						    <option value="Programacion">Programaci&oacute;n</option>
						    <option value="IT y Software">IT y Software</option>
						    <option value="Negocios">Negocios</option>
						    <option value="Diseño">Dise&ntilde;o</option>
						    <option value="Fotografia">Fotograf&iacute;a</option>
						    <option value="Salud y Fitness">Salud y Fitness</option>
						    <option value="Lifestyle">Lifestyle</option>
						    <option value="Musica">M&uacute;sica</option>
						    <option value="Idioma">Idioma</option>
						    <option value="Academico">Acad&eacute;mico</option>
						</select>
				    </div>	
					<!--<div class="form-group">
						<label  for="categoria"><span class="red">*</span>Categor&iacute;a</label>
						<input type="text" class="form-control" name="categoria" placeholder="Introduce la categor&iacute;a de tu curso" required>
					</div>-->
					<div class="form-group">	
					    <label for="moreinfo"><span class="red">*</span>Descripci&oacute;n</label>
						<textarea name="descripcion" class="form-control" rows="4" placeholder="Describe en que consiste el curso" required></textarea>
					</div>
					<div class="form-group">
					    <label  for="nivel"><span class="red">*</span>Dificultad</label>
					    <select name="dificultad" class="form-control">
						    <option value="basico">B&aacute;sico</option>
						    <option value="intermedio">Intermedio</option>
						    <option value="avanzado">Avanzado</option>
						</select>
				    </div>		 
					<div class="form-group">
						<label  for="horas"><span class="red">*</span>Horas de dedicaci&oacute;n</label>
						<input type="number" class="form-control" name="horas" min="1" max="240" placeholder="Introduce las horas de dedicaci&oacute;n que requiere el curso" required>
					</div>	 
					<div class="form-group">
					    <label  for="precio"><span class="red">*</span>Precio matricula</label>
						<input type="number" class="form-control" name="precio" min="0" max="6000" placeholder="Introduce el precio del curso" required>
					</div>
						     
					<div class="form-group">
						<label  for="foto">Imagen del curso</label>
						<input type="text" class="form-control" name="imagen" placeholder="Introduce la url de la imagen del curso" required>
						</dive>
						<p class="help-block">El formato debe ser jpg</p>
					</div>
					<h2>Informaci&oacute;n de cobro</h2>
					<div class="form-group" id="desplegable">					
						<label  for="paypal"><span class="red">*</span>Correo de paypal</label>
						<input type="text" class="form-control" name="paypal" placeholder="Introduce tu correo de paypal" required>
					</div>
					<div class="form-group">
					    <button type="submit" class="btn btn-success">Solicitud de alta</button>
					</div>	
				</div>
				</form>
				<div class="col-md-4">
				    <img class="img-responsive vertical-align imgfor img-rounded" src="http://elearningindustry.com/wp-content/uploads/2015/04/Creating-Branching-eLearning-Scenarios.jpg" alt="Imagen formulario"> 
				    <p>Prop&oacute;n tu curso y sera revisado por nuestros administradores para asegurarnos de que no es abusivo con los estudiantes. 
				    M&aacute;s de 50.000 profesores han confiado en nosotros, y gracias a ellos ofrecemos mas de 100.000 cursos.</p>
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