<jsp:useBean id="perfil" class="es.rotolearn.javaBean.RegistroBean" scope="session"/>
<!DOCTYPE html>
<html lang="es">
    <head>
        <title>Panel de Administraci&oacute;n</title>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css">
        <link rel="stylesheet" type="text/css" href="./style/footer.css" media="screen"/><!--CSS FOOTER-->
        <link rel="stylesheet" type="text/css" href="./style/profes_plantilla.css" media="screen"/><!--CSS PLANTILLA-->
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
                            <!--<li><a href="perfil.form">Mi Perfil</a></li>-->
                            <li><a href="catalogo.jsp">Cat&aacute;logo de cursos</a></li>
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
           <div id="miga"><a href="profes_panel.jsp">Panel de control</a></div>
        </header>
        <!--FIN CABECERA-->
        
        <!--CUERPO-->
        <div id="cuerpo" class="container-fluid">
            <div class="col-md-10 col-md-offset-1">
                <h1><span id="user"><%=perfil.getNombre()%></span> , bienvenido al panel de administraci&oacute;n.</h1>
                <a href="profes_crearcurso.jsp" class="opcionPanel btn btn-danger"><br><span class="simbolos glyphicon glyphicon-plus"></span><br>A&ntilde;adir curso</a>
                <a href="profes_listadocursos.jsp" class="opcionPanel btn btn-primary"><br><span class="simbolos glyphicon glyphicon glyphicon-th-list"></span><br>Ver tus cursos</a>
                <a href="profes_listadodescuentos.jsp" class="opcionPanel btn btn-success"><br><span class="simbolos glyphicon glyphicon glyphicon-tags"></span><br>Ver tus descuentos</a>
                <a href="profes_administrarcurso.jsp#profesores" class="opcionPanel btn btn-warning"><br><span class="simbolos glyphicon glyphicon glyphicon-user"></span><br>Invitar al claustro</a>
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
    						<a href="index.jsp">Home</a> &#45;
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