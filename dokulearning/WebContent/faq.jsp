<jsp:useBean id="perfil" class="es.rotolearn.javaBean.RegistroBean" scope="session"/>
<%@ page isErrorPage="true" %>
<!DOCTYPE html>
<html lang="es">
    <head>
     	<%	HttpSession miSession = request.getSession(true); %>
    	<title>Preguntas frecuentes</title>
    	<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
		<link rel="stylesheet" type="text/css" href="./style/plantilla.css" media="screen"/>
      	<link rel="stylesheet" type="text/css" href="./style/faq.css" media="screen"/>
      	<link rel="stylesheet" type="text/css" href="./style/footer.css" media="screen"/>
      	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
      	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
      	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css">
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
			<div class="row">
			    <div class="col-md-5 col-md-offset-3">
			    	<h1>Preguntas frecuentes</h1>
			    	<h5>Antes de ponerte en contacto con nosotros, quiz&aacute;s estas preguntas frecuentes te ayuden con tu problema.</h5><br><br>
						<span class="pregunta">¿Qué aspecto tiene un curso típico de rotolearn?</span>
						<span class="respuesta"><p>¡Buena pregunta! Un curso típico de rotolearn tiene 1-3 horas de contenido, con un requisito mínimo de al menos 30 minutos de contenido y 60 % de contenido de vídeo.</p></span>
						
						<span class="pregunta">¿Cómo es el tráfico de rotolearn?</span>
						<span class="respuesta"><p>¡Tenemos más de 4 millones de estudiantes que utilizan actualmente la plataforma, además de más de 4 millones de visitantes mensuales!</p></span>
						
						<span class="pregunta">¿Hay algún coste asociado con la creación de un curso en rotolearn?</span>
						<span class="respuesta"><p>¡Nop! Crear un curso es completamente gratis para nuestros instructores y no habrá nunca tarifas de mantenimiento.</p>
							<p>Tú mantienes el 100 % de los ingresos (menos tasas de pagos) cuando traes estudiantes a tu curso rotolearn. Por cada estudiante que rotolearn lleva a tu curso mediante nuestros esfuerzos de marketing, tú mantienes el 50 % de la venta.</p>
							<p>rotolearn gestiona todo el servicio al cliente, el procesamiento de pagos y las tarifas de alojamiento y te proporciona acceso a todo el conjunto de características de rotolearn, incluyendo la versión móvil de tus cursos, ¡sin coste adicional!</p>
						</span>
						
						<span class="pregunta">¿Cómo debería valorar mi curso?</span>
						<span class="respuesta"><p>Algunos instructores utilizan una medida de alrededor de 10-30 $ por hora de contenido del curso. La mayoría de los cursos en rotolearn tienen un precio de entre 29 y 99 $.</p>
							<p>No dudes en revisar nuestros Términos de uso <a href="#">aquí</a>.</p>
						</span>
							
						<span class="pregunta">¿Cuánto tiempo pueden acceder a mi curso los estudiantes?</span>
						<span class="respuesta"><p>¡Una vez que se haya adquirido tu curso, el estudiante tiene acceso a todos los materiales del curso indefinidamente, igual que un canal bajo demanda!</p></span>
							
						<span class="pregunta">¿Qué hace rotolearn para promocionar mi curso?</span>
						<span class="respuesta"><p>¡Con más de 4 millones de usuarios registrados, tenemos unos cuantos estudiantes buscando cursos en rotolearn! También nos centramos en capacitar a nuestros instructores a promocionar con éxito sus cursos mediante: códigos de cupones, nuestro programa de afiliados y la programación de marketing por e-mail de rotolearn.</p></span>
						
						<span class="pregunta">¿Hay algún estándar o requisito que deba saber acerca de la creación de mi curso?</span>
						<span class="respuesta"><p>Sí. rotolearn repasa todos los cursos publicados en rotolearn.com para asegurarse de que cumplen nuestros estándares de calidad. Puedes echar un vistazo a nuestra lista de comprobación de creación de cursos aquí. Hay 3 estándares importantes a tener en cuenta:</p>
							<p>Los cursos deberían contener al menos 30 minutos de contenido con 60 % de contenido en vídeo.<br>
							Los cursos deberían estar bien estructurados para proporcionar objetivos de aprendizaje.<br>
							El sonido debería ser claro e inteligible; el vídeo debería ser claro, bien iluminado y en alta definición.</p>
						</span>
						
						<span class="pregunta">¿Qué tipo de equipamiento (cámara, micrófono, software) necesito para crear un curso online?</span>
						<span class="respuesta"><p>Hay muchas formas y de muy bajo precio de crear tu curso online, y muchas herramientas para ayudarte a filmar tu vídeo, editar vídeo, crear un sonido estupendo, configurar la iluminación y más. Aquí tienes unas pocas de las herramientas favoritas de nuestros instructores.</p></span>
			    <br><br><br>
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
