<!DOCTYPE html>
<html lang="es">
	<head>
	  <title>Registro de Usuario</title>
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
		                	<li><a href="admin_index.jsp">Inicio</a></li>
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
					
				<%
		        	if(request.getAttribute("error") != null){
		            	if(request.getAttribute("error").equals("reg")){		
		        %>
			        	<div class="alert alert-danger" style="margin-top:10px" style="margin-bottom:0px">
							<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
						    <strong>Error al crear usuario</strong> Nombre de usuario en uso
						</div>
	             <%
		         		}else if(request.getAttribute("error").equals("ok")){
		         %>
			         	<div class="alert alert-success" style="margin-top:10px" style="margin-bottom:0px">
	    					<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
	    					<strong>Usuario creado correctamente</strong> El usuario ha sido agregado a la base de datos del sistema
	  					</div>
	             <%
		         		}
		         	}
		         %>
					
	    		<form role="form" method="POST" action="reg_user.form">
				<div class="col-md-5 col-md-offset-1">
					<h2>Registro de un nuevo Usuario</h2>
					<div class="form-group">
						<label for="user"><span class="red">*</span>Usuario</label>
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
						<input type="text" class="form-control" name="apellido2" id="apellido2" placeholder="Introduce tu segundo apellido" required>
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
						<textarea class="form-control" rows="4" name="descripcion" placeholder="Escribe una breve descripci&oacute;n sobre ti"></textarea>
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
						<label for="tlf"><span class="red">*</span>Telefono</label>
						<input type="number" class="form-control" name="tlf" required>
					</div>
					<div class="form-group">
						<label  for="foto">Imagen de perfil</label>
						<input type="text" class="form-control" name="imagen" id="exampleInputFile" placeholder="Introduce la url en la que se encuentra la imagen" >
						</dive>
						<p class="help-block">El formato debe ser jpg</p>
					</div>
					<div class="form-group">        
						<button type="submit" class="btn btn-success">A&ntilde;adir Usuario</button>
					</div>
				</div>		     
				</form>
	    	</div>
	    	<div class="row">
	    		<div class="col-lg-5 col-md-offset-1">
	   				<form role="form" method="POST" action="reg_admin.form">
	   					<h2>Registro de un usuario Administrador</h2>
						<div class="form-group">
							<label  for="nick"><span class="red">*</span>Nickname</label>
							<input type="text" class="form-control" name="nick" id="nick" placeholder="Introduce tu nick" required>
						</div>
							<div class="form-group">					
							<label for="pass"><span class="red">*</span>Contrase&ntilde;a</label>
							<input type="password" class="form-control" name="pass" placeholder="Introduce tu contrase&ntilde;a" required>
						</div>
						<div class="form-group">        
							<button type="submit" class="btn btn-success">A&ntilde;adir Usuario</button>
						</div>
	   				</form>
				</div>
	    	</div>
		</div>
		<!--FIN CUERPO-->
	
	</body>
</html>