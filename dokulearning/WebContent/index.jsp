<jsp:useBean id="perfil" class="es.rotolearn.javaBean.RegistroBean" scope="session"/>
<%@ page import="entities.*" %>
<%@ page import="java.util.ArrayList" %>
<!DOCTYPE html>
<html lang="en-us">

	<head>
		<link rel="shortcut icon" type="image/x-icon" href="./images/favicon.ico" />
		<meta charset="utf-8"/>
		<!-- T&iacute;tulo del sitio -->
		<title>Home - ROTOLearn</title>
		<link rel="shortcut icon" type="image/x-icon" href="./images/ico2.ico" />
		<!-- Estilo del sitio -->
		<link href='https://fonts.googleapis.com/css?family=Muli:300' rel='stylesheet' type='text/css'>
		<link href='https://fonts.googleapis.com/css?family=Josefin+Sans' rel='stylesheet' type='text/css'>
		<link href='https://fonts.googleapis.com/css?family=Merriweather+Sans' rel='stylesheet' type='text/css'>
		<link href='https://fonts.googleapis.com/css?family=Lato:400,900,700' rel='stylesheet' type='text/css'>
		<link href='https://fonts.googleapis.com/css?family=Convergence' rel='stylesheet' type='text/css'>
		<link rel="stylesheet" href="./style/catalogo.css">
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
		<link rel="stylesheet" type="text/css" href="./style/index.css" media="screen" />
		<!--Bootstrap-->
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
		<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
		<link rel="stylesheet" type="text/css" href="./style/footer.css" media="screen"/>
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css">
		  	<script>
      		
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
	<div class="container-fluid">
		<div id="portada">
		
			<%
			if(session.getAttribute("usuario") == null){
			%>
			
			<div id="conectado" class="pull-right">
				<a href="login.jsp"><img id="imgConectado" src="./images/perfil/anonimo.jpeg" class="img-circle" alt="Cinque Terre" width="40" height="40"></a>
				<p id="nombreConectado"><a href="login.jsp">Accede</a></p>
			</div>
			
			<%}else{ %>
			
			
			<div id="conectado" class="pull-right">
			<div class="pull-left" id = "not"><a href="#" rel="popover" data-placement="bottom" data-popover-content="#myPopover" id="leer">
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
					                                		<input type="hidden" value="index" name="tipo"/>	
					                                		<input type="hidden" value="SI" name="leido"/>	
					                                		<input class="btn btn-default btn-xs glyphicon glyphicon-eye-open" type="submit" value="Marcar como leido">
					                                	</form>
                        	</div>
                        </div>
				<a href="perfil.form">
				<%if(perfil.getImagen()){%>
					      <img id="imgConectado" src="images/im_usuarios/<%=perfil.getNickName()%>_perfil.jpg" class="img-circle" alt="Cinque Terre" width="40" height="40"></a>
                            
                <%}else{ %>
                           <img id="imgConectado" src="./images/perfil/anonimo.jpeg" class="img-circle" alt="Cinque Terre" width="40" height="40"></a>
                            
                <%} %>
                <p id="nombreConectado"><a href="perfil.form"><%=session.getAttribute("usuario")%></a></p> 
			</div>
			<%}%>
			<div id="bienvenida" class="col-md-12">
				<h1>&Uacute;nete a la <span>Revoluci&oacute;n E-Learning</span></h1>
				<h3>Ya no necesitas vaciar tus bolsillos para aprender en Internet</h3>
				<h5>Los mejores cursos de la red aqu&iacute;, en RotoLearn</h5><br>
				<a href="#porque" class="btn-masinfo btn btn-lg">M&aacute;s informaci&oacute;n</a>
				<%
			if(session.getAttribute("usuario") == null){
			%>
				<a href="formulario_registro.jsp" class="btn-registro btn btn-lg">Reg&iacute;strate</a>
				<%} %>
			</div>
			<div class="limpiador"></div>
		</div>
		<div id="porque">
			<div class="imagen col-md-6">
				<img src="./images/index/aprender.jpg" alt="aprende" class="imagenAprende">
			</div>
			<div class="col-md-6">
				<h1>&iquest;Porqu&eacute; quedarme?</h1>
				<p>En <span>rotolearn</span> te ofrecemos los mejores cursos de toda la red. Podr&aacute;s inscribirte en cualquier curso por un precio m&aacute;s que jugoso, y podr&aacute;s disfrutar de nuestros cursos gratuitos.</p>
				<%
			if(session.getAttribute("usuario") == null){
			%>
				<p>No dejes escapar esta oportunidad y <a href="formulario_registro.jsp">Reg&iacute;strate</a></p>
				<% } %>
			</div>
			<div class="limpiador"></div>
		</div>
		<div id="funciona">
			<div class="col-md-3">
			</div>
			<div class="col-md-6">
				<h1>Aprende una nueva habilidad sin salir de casa</h1>
				<p>Con <span>rotolearn</span> podr&aacute;s acceder a todos los cursos que quieras desde cualquier dispositivo con acceso a internet.</p>
				<h6>&iexcl;Olvidat&eacute; de las pesadas clases presenciales, y apuesta por el mundo e-learning! &iexcl;Aprende a tu ritmo!</h6>
			</div>
			<div class="col-md-3">
			</div>
			<div class="limpiador"></div>
		</div>
		<div id="topCursos">
			 <div class="col-md-8 col-md-offset-2">
			 	<h1> Descubre los cursos m&aacute;s destacados</h1>
				<div class="limpiador"></div>
			</div>

			<div id="verMas">
	           	<a class="verMas col-md-8 col-md-offset-2" href="catalogo.form">Ver catalogo de cursos disponibles</a>
	        </div>
		</div>
	</div>
			<!--PIE DE PAGINA-->
        <footer>
	    <div class="container-fluid" id="pie">
	    	<div class="row">
	    		<div class="col-md-3 col-md-offset-1" id="footer-left">
	    			<img class="logotipo img-responsive" src="./images/logo.png">
	                <p class="footer-links">
	    				<a href="Notificacion.form">Home</a> &#45;
	    				<a href="faq.jsp">Faq</a> &#45;
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
	    		<div class="limpiador"></div>

	    </footer>
        <!--FIN PIE DE PAGINA-->
</body>
</html>