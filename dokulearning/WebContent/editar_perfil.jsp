<jsp:useBean id="perfil" class="es.rotolearn.javaBean.RegistroBean" scope="session"/>
<!DOCTYPE html>
<html lang="es">
    <head>
    	<title>Editar perfil de <%=session.getAttribute("usuario")%></title>
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
	    <div class="container-fluid">
	    	<div class="row" id="cuerpo">
	    	
				<form role="form" action="perfil.form" method="POST">
				<div class="col-md-4 col-md-offset-4" >
					

					<div class="form-group">						
						<label  for="nombre"><span class="red">*</span>Nombre</label>
						<input type="text" class="form-control" name="nombre" id="nombre" value="<%=perfil.getNombre()%>" required>
					</div>
					<div class="form-group">						
						<label  for="apellido1"><span class="red">*</span>Primer apellido</label>
						<input type="text" class="form-control" name="apellido1" id="apellido1" value="<%=perfil.getApellido1()%>" required>
					</div>
					<div class="form-group">						
						<label  for="apellido2"><span class="red">*</span>Segundo apellido</label>
						<input type="text" class="form-control" name="apellido2" value="<%=perfil.getApellido2()%>" required>
					</div>
					<div class="form-group">					
						<label for="email"><span class="red">*</span>Email</label>
						<input type="email" class="form-control" name="email" value="<%=perfil.getEmail()%>" required>
					</div>

					<h2>Informaci&oacute;n complementaria</h2>
					
					<div class="form-group">
						<label for="date">Fecha de nacimiento</label>
						<input type="date" class="form-control" name="date" value="<%=perfil.getNacimiento()%>">
					</div>
					<div class="form-group">					
						<label for="direccion"><span class="red">*</span>Direcci&oacute;n</label>
						<input type="text" class="form-control" name="direccion" value="<%=perfil.getDireccion()%>" required>
						</dive>
						</span><p class="help-block">Calle, Puerta, Piso, Localidad, C&oacute;digo postal.</p>
					</div>
					<div class="form-group">  					
						<label  for="descrip">Descripci&oacute;n</label>
						<textarea name="descripcion" class="form-control" rows="4" ><%=perfil.getDescripcion()%></textarea>
					</div>
					<div class="form-group">  					
						<label  for="intereses">Intereses</label>
						<textarea name="intereses" class="form-control" rows="2" ><%=perfil.getIntereses()%></textarea>
					</div>
					<div class="form-group">					
						<label for="tlf"><span class="red">*</span>Tel&eacute;fono</label>
						<input type="number" class="form-control" name="tlf" value="<%=perfil.getTelefono()%>" required>
					</div>
					<div class="form-group">
						<label  for="foto">Imagen de perfil</label>
						<input type="file" id="exampleInputFile">
						</dive>
						<p class="help-block">El formato debe ser jpg</p>
					</div>
					<div class="form-group">        
						<button type="submit" class="btn btn-success">Guardar cambios</button>
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