<jsp:useBean id="perfil" class="es.rotolearn.javaBean.RegistroBean" scope="session"/>
<%@ page import="entities.Curso" %>
<!DOCTYPE html>
<html lang="es">
	<head>
		<title>Informaci&oacute;n de curso</title>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
		<link rel="stylesheet" type="text/css" href="./style/plantilla.css" media="screen"/><!--CSS PLANTILLA-->
		<link rel="stylesheet" type="text/css" href="./style/pagos.css" media="screen"/>
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
		<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
		<link rel="stylesheet" type="text/css" href="./style/footer.css" media="screen"/>
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
    <%
    	Curso aux = (Curso) request.getAttribute("curso");
    	int h = aux.getId();
    %>
	<div id="cuerpo" class="container-fluid">
		<div class="row">
			<div class="col-md-8 col-md-offset-2">
			   <div class="panel panel-success">
                    <div class="panel-heading">Datos de pedido</div>
                    <div class="panel-body">
                        <div class="table-responsive">
                            <table class="table">
                                <thead>
                                    <tr>
                                        <th>Nombre del Curso</th>
                                        <th>Descripcion</th>
                                        <th>Precio</th>
                                        <th>Descuento Actual</th>
                                        <th>Cantidad</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <tr>
                                        <td><%=aux.getTitulo() %></td>
                                        <td><%=aux.getDescripcion() %></td>
                                        <td><%=aux.getPrecio() %></td>
                                        <%
                                        	String promo = (String) request.getAttribute("descuento");
                                        	if(promo != null){
                                        		String rebaja = (String) request.getAttribute("rebaja");
                                        %>
                                        <td><%=promo %>%</td>
                                        <td>-<%=rebaja %>&euro;</td>
                                        <%
                                        	}else{
                                        %>
                                        <td>No hay ning&uacute;n descuento</td>
                                        <td>-0&euro;</td>
                                        <%
                                        	}
                                        %>
                                        
                                    </tr>
                                </tbody>
                            </table>
                        </div>
                        
                        <div class="table-responsive">
                            <table class="table">
                                <thead>
                                    <tr>
                                        <th>Promociones Aplicadas</th>
                                        <th>Descuento</th>
                                        <th>Cantidad</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <tr>
                                    	<%
                                        	String vale = (String) request.getAttribute("valePromocional");
                                        	if(vale != null){
                                        		String rebaja = (String) request.getAttribute("rebaja");
                                        %>
                                        <td><%=vale %></td>
                                        <td><%=request.getAttribute("cuponVale") %>%</td>
                                        <td>-<%=request.getAttribute("precioCupon") %>&euro;</td>
                                        <%
                                        	}else{
                                        %>
                                        <td>No hay ning&uacute;n vale promocional aplicado</td>
                                        <td>0%</td>
                                        <td>-0&euro;</td>
                                        <%		
                                        	}
                                        %>
                                    </tr>
                                </tbody>
                            </table>
                        </div>
                        <h3>Precio Total: <label><%=request.getAttribute("total") %>&euro;</label></h3>
                    </div>
                </div>
			</div>
		</div>
		<%
        	if(vale == null){
        %>
		<div class="row">
		    <div class="col-md-8 col-md-offset-2">
		        <form action="pagina_pago.form" method=post>
   					<h4>Vale de Descuento</h4>
   					<div class="form-group">
						<label  for="desc">Cupon promocional</label>
						<input type="text" class="form-control" name="Cuponazo" placeholder="Introduce un cupon promocional" required>
					</div>
					<input type="hidden" value="<%=h %>" name="cursoCompra"/>
					<input type="hidden" value="valeDesc" name="tipo"/>
					<div class="form-group">        
						<button type="submit" class="btn btn-success">Aplicar vale descuento</button>
					</div>
   				</form>
		    </div>
		</div>
		<%
        	}
		%>
		<div class="row">
		    <div class="col-md-8 col-md-offset-2">
		        <form method=post action="pago.form">
   					<h4>Datos de pago</h4>
   					<div class="form-group">
						<label  for="tarjeta">Nombre y apellidos</label>
						<input type="text" class="form-control" name="nombre" placeholder="Introduce el nombre y apellido del titular de la tarjeta" required>
					</div>
					<div class="form-group">
						<label  for="tarjeta">Tarjeta de credito</label>
						<input type="text" class="form-control" name="tarjeta" placeholder="Introduce tu tarjeta de credito" required>
					</div>
					<div class="form-group">
						<input type="hidden" name="totalprecio" value="<%= request.getAttribute("total") %>" />
						<input type="hidden" name="valePromocional" value="<%= request.getAttribute("valePromocional") %>" />
						<input type="hidden" name="valeAdmin" value="<%= request.getAttribute("descuento") %>" />
						<input type="hidden" name="precioOriginal" value="<%= aux.getPrecio() %>" />
						<input type="hidden" name="profesor" value="<%= aux.getUsuario().getId() %>" />					
						<input type="hidden" value="<%=h %>" name="cursoCompra"/>        
						<button type="submit" class="btn btn-success">Comprar Curso</button>
					</div>
   				</form>
		    </div>
		</div>
	</div>
	<!--FIN CUERPO -->

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
	    				<span>Sobre la compañia</span>
	    				Roto2 Company lleva afincada en el sector del software desde que Ilitri fundo un equipo lleno de pequeños troles y niños ratas. 
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