<jsp:useBean id="perfil" class="es.rotolearn.javaBean.RegistroBean" scope="session"/>
<!DOCTYPE html>
<html lang="es">
	<head>
		<title>Registro de Usuario</title>
	  	<meta charset="utf-8">
	  	<meta name="viewport" content="width=device-width, initial-scale=1">
	  	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
	  	<link rel="stylesheet" type="text/css" href="./style/plantilla.css" media="screen"/><!--CSS PLANTILLA-->
	  	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
	  	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
	  	<link rel="stylesheet" type="text/css" href="./style/footer.css" media="screen"/><!--CSS FOOTER-->
	  	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css">
	</head>
	
	<body>
	    <!--CABECERA-->

    	<!--CABECERA-->
       <header>
        <div class="container-fluid">
            <nav id="menu" class="navbar navbar-default">
                <div class="navbar-header">
            	    <img class="navbar-brand logotipo" src="./images/logo.png">
                </div>
                <div class="collapse navbar-collapse" id="myNavbar">
                    <ul class="nav navbar-nav">
                        <li><a href="index.jsp">Inicio</a></li>
                        <li><a href="perfil.form">Mi perfil</a></li>
                        <li><a class="activa" href="catalogo.form">Catalogo de cursos</a></li>
                    </ul>
                    <%
					
            if(session.getAttribute("usuario") == null){
			%>
                    <ul class="nav navbar-nav navbar-right">
                       <li><a href="login.jsp">Inicia sesi&oacute;n</a></li>
                       <li><a href="formulario_registro.jsp">Registrate</a></li>
                    </ul>
			</div>
			<%}else{ %>
			        <ul class="nav navbar-nav navbar-right">
                       <li><a href="perfil.form"><%=session.getAttribute("usuario")%></a></li>
                       <li class="dropdown">
                           <a href="#" class="dropdown-toggle" data-toggle="dropdown"><img id="imgConectado" src="./images/perfil/anonimo.jpeg" class="img-circle" alt="Cinque Terre" width="40" height="40">
                            <span class="caret"></span></a>
                            <ul class="dropdown-menu">
                                <li><a href="perfil.form"><span class="glyphicon glyphicon-user"></span> Mi perfil</a></li>  
                             <% if(!perfil.getTipo().equals("alumn")){%>                     	
                        		<li><a href="profes_panel.jsp"><span class="glyphicon glyphicon-th-large"></span> Panel de control</a></li>
                        	 <% }%> 
                                <li><a href="logout.form"><span class="glyphicon glyphicon-remove-sign"></span> Cerrar sesi&oacute;n</a></li>
                            </ul>
                        </li>
                    </ul>
			<%}%>
                </div>
            </nav>
        </div>
    </header>
	    <!--FIN CABECERA-->
	    
	    <!--CUERPO-->
	    <div class="container-fluid">
	    	<div class="row" id="cuerpo">
	    	
			    <%
					if(request.getAttribute("error") != null)
					if(request.getAttribute("error").equals("si")){
				%>
						<div class="alert alert-danger" style="margin-top:10px" style="margin-bottom:0px">
							<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
						    <strong>Error al crear usuario</strong> Las contrase&ntilde;as no coinciden
						</div>
				<%
					}else{
				%>
						<div class="alert alert-danger" style="margin-top:10px" style="margin-bottom:0px">
							<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
						    <strong>Error al crear usuario</strong> Nombre de usuario en uso
						</div>
				<%
					}
				%>
	    	
				<form role="form" action="reg.form" method="POST">
				<div class="col-md-5 col-md-offset-1" >
					<h2>Registrate ahora y comienza a formarte</h2>
					<div class="form-group">
						<label for="user"><span class="red">*</span>¿Alumno o profesor?</label>
					    <label class="radio-inline">
					      <input type="radio" name="optradio" value="alumn" required>Alumno
					    </label>
					    <label class="radio-inline">
					      <input type="radio" name="optradio" value="profe" required>Profesor
					    </label>
					</div>
					<div class="form-group">
						<label  for="nick"><span class="red">*</span>Nickname</label>
						<input type="text" class="form-control" name="nick" id="nick" placeholder="Introduce tu nick" required>
					</div>
					<div class="form-group">						
						<label  for="nombre"><span class="red">*</span>Nombre</label>
						<input type="text" class="form-control" name="nombre" id="nombre" placeholder="Introduce tu nombre" required>
					</div>
					<div class="form-group">						
						<label  for="apellido1"><span class="red">*</span>Primer apellido</label>
						<input type="text" class="form-control" name="apellido1" id="apellido1" placeholder="Introduce tu primer apellido" required>
					</div>
					<div class="form-group">						
						<label  for="apellido2"><span class="red">*</span>Segundo apellido</label>
						<input type="text" class="form-control" name="apellido2" placeholder="Introduce tu segundo apellido" required>
					</div>
					<div class="form-group">					
						<label for="email"><span class="red">*</span>Email</label>
						<input type="email" class="form-control" name="email" placeholder="Introduce tu correo electronico" required>
					</div>
					<div class="form-group">					
						<label for="pass"><span class="red">*</span>Contrase&ntilde;a</label>
						<input type="password" class="form-control" name="pass" placeholder="Introduce tu contrase&ntilde;a" required>
					</div>
				</div>
				<div class="col-md-5" >
					<h2>Informaci&oacute;n complementaria</h2>
					<div class="form-group">
						<label for="date">Fecha de nacimiento</label>
						<input type="date" class="form-control" name="date">
					</div>
					<div class="form-group">					
						<label for="direccion"><span class="red">*</span>Direcci&oacute;n</label>
						<input type="text" class="form-control" name="direccion" placeholder="Introduce tu direcci&oacute;n" required>
						</dive>
						</span><p class="help-block">Calle, Puerta, Piso, Localidad, C&oacute;digo postal.</p>
					</div>
					<div class="form-group">  					
						<label  for="descrip">Descripci&oacute;n</label>
						<textarea name="descripcion" class="form-control" rows="4" placeholder="Escribe una breve descripci&oacute;n sobre ti"></textarea>
					</div>
					
					<label  for="intereses">Intereses</label>
					<div class="form-group">  					
						
						<div class="col-md-6">
							<div class="checkbox">
								<label><input type="checkbox" name="intereses1" value="Programacion">Programaci&oacute;n</label>
							</div>
							<div class="checkbox">
							  	<label><input type="checkbox" name="intereses2" value="IT y Software">IT y Software</label>
							</div>
							<div class="checkbox">
								<label><input type="checkbox" name="intereses3" value="Negocios">Negocios</label>
							</div>
							<div class="checkbox">
								<label><input type="checkbox" name="intereses4" value="Diseño">Dise&ntilde;o</label>
							</div>
							<div class="checkbox">
								<label><input type="checkbox" name="intereses5" value="Fotografia">Fotograf&iacute;a</label>
							</div>
						</div>
						<div class="col-md-6">
							<div class="checkbox">
								<label><input type="checkbox" name="intereses6" value="Salud y Fitness">Salud y Fitness</label>
							</div>
							<div class="checkbox">
								<label><input type="checkbox" name="intereses7" value="Lifestyle">Lifestyle</label>
							</div>
							<div class="checkbox">
								<label><input type="checkbox" name="intereses8" value="Musica">M&uacute;sica</label>
							</div>
							<div class="checkbox">
								<label><input type="checkbox" name="intereses9" value="Idioma">Idioma</label>
							</div>
							<div class="checkbox">
								<label><input type="checkbox" name="intereses10" value="Academico">Acad&eacute;mico</label>
							</div>
						</div>
					</div>
					
					<div class="form-group">					
						<label for="tlf"><span class="red">*</span>Tel&eacute;fono</label>
						<input type="number" class="form-control" name="tlf" required>
					</div>
					<div class="form-group">
						<label  for="foto">Imagen de perfil</label>
						<input type="text" class="form-control" name="exampleInputFile" id="exampleInputFile" placeholder="Introduce la url en la que se encuentra la imagen" >
						<p class="help-block">El formato debe ser jpg</p>
					</div>
					<div class="form-group">        
						<button type="submit" class="btn btn-success">Registrate</button>
					</div>
				</div>		     
				</form>
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