<jsp:useBean id="perfil" class="es.rotolearn.javaBean.RegistroBean" scope="session"/>
<%@ page import="es.rotolearn.tablas.Curso" %>
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
	</head>
	<body>
	    <%	HttpSession miSession = request.getSession(true); %>
	
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
    <%
    	Curso aux = (Curso) request.getAttribute("curso");
    	String h = aux.getTitulo();
    %>
    
	<div id="cuerpo" class="container-fluid">
		<div class="row" id="cabeza" style="background-image:url('<%=aux.getImagen() %>')">
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
						<span class="info">Profesor/es:</span><%=aux.getUsuario() %>
						<br>
					</li>
					<li class="list-group-item">
						<button type="submit" class="btn btn-success breg">Inscribirse</button>
						<a class="btn btn-success breg glyphicon glyphicon-heart" href="añadirDeseo.form?titulo=<%=h %>" rel="prettyPhoto"><span>  Deseado</span></a>
					</li>
				</ul>
			</div>
		</div>
		<div class="row" id="descrip">
			<div class="col-md-10 col-md-offset-1" >
				<h2>Descripci&oacute;n del curso</h2>
				<p ><%=aux.getDescripcion() %></p>
				<p class="collapse" id="viewdetails">Te convertir&aacute;s en el mejor gamer de la historia por solo 5 &euro;.</p>
				<p><a class="btn" data-toggle="collapse" data-target="#viewdetails">Leer m&aacute;s &raquo;</a></p>
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