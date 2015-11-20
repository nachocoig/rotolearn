<jsp:useBean id="perfil" class="es.rotolearn.javaBean.RegistroBean" scope="session"/>

<!DOCTYPE html>
<html lang="es">
    <head>
        <title>Cupones de descuento</title>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
        <link rel="stylesheet" type="text/css" href="./style/profes_plantilla.css" media="screen"/><!--CSS PLANTILLA-->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css">
        <link rel="stylesheet" type="text/css" href="./style/footer.css" media="screen"/><!--CSS FOOTER-->
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
                            <li><a href="catalogo.form">Cat&aacute;logo de cursos</a></li>
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
            <div id="miga"><a href="profes_panel.jsp">Panel de control</a> > <a href="profes_listadodescuentos.jsp">Ver tus descuentos</a></div>
        </header>
        <!--FIN CABECERA-->
        
        <!--CUERPO-->
    	<div id="formCupon" class="container-fluid">
                
                <%
					if(request.getAttribute("cupon") != null)
					if(request.getAttribute("cupon").equals("si")){
				%>
						<div class="alert alert-success" style="margin-bottom:0px">
							<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
						    <strong>Cupon creado.</strong> Tu cupon se ha creado correctamente.
						</div>
				<%
					}else{
				%>
						<div class="alert alert-danger" style="margin-top:10px" style="margin-bottom:0px">
							<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
						    <strong>Error al crear cupon.</strong> Tu cupon no se ha podido crear.
						</div>
				<%
					}
				%>
        
    		<div class="row ">
    		    <form role="form" method="post" action="cupon.form">
        			<div class="col-md-4 col-md-offset-2">
        			    <h2>Crear cup&oacute;n de descuento</h2>
        			    <div class="form-group">
    						<label for="sel1">Curso: </label>
    						<select class="form-control"  id="sel1" required>
    							<option>Curso 1</option>
    							<option>Curso 2</option>
    							<option>Curso 3</option>
    							<option>Curso 4</option>
    						</select>
    					</div>
    					<div class="form-group">
    						<label for="sel1">Tipo de descuento: </label>
    						<select class="form-control" id="sel1" required>
    							<option>Porcentaje</option>
    							<option>Descuento fijo</option>
    						</select>
    					</div>
        			</div>
        			<div class="col-md-4 ">
        				<div class="form-group">
    						<label for="cupon">Cup&oacute;n: </label>
    						<input type="text" class="form-control" name="cupon" placeholder="Introduce el cup&oacute;n" required>
    					</div>
    					<div class="form-group">
    						<label for="descuento">Descuento: </label>
    						<input type="number" class="form-control" name="descuento" min="0" max="100" placeholder="Introduce el descuento" required>
    					</div>
    					<button type="submit" class="btn btn-success">Crear cup&oacute;n</button>
        			</div>
        		</form>
    		</div>
    	</div>
    	
    	<div id="cuerpo" class="container-fluid">
            <div class="row">
                <div class="col-md-12">
                    <div class="table-responsive">
                        <h2 class="titulo">Listado de Descuentos</h2>          
                        <table class="table table-condensed table-hover">
                        <thead>
                            <tr>
                                <th>Curso</th>
                                <th>Tipo descuento</th>
                                <th>Codigo</th>
                                <th>Descuento</th>
                                <th>Eliminar</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td>Jaba</td>
                                <td>Porcentaje</td>
                                <td>roto25</td>
                                <td>25</td>
                                <td><input class="btn btn-default btn-xs" type="submit" value="Eliminar"></td>
                            </tr>
                            <tr>
                                <td>J2E</td>
                                <td>Porcentaje</td>
                                <td>roto30</td>
                                <td>30</td>
                                <td><input class="btn btn-default btn-xs" type="submit" value="Eliminar"></td>
                            </tr>
                            <tr>
                                <td>Javascript</td>
                                <td>Descuento fijo</td>
                                <td>roto250</td>
                                <td>250</td>
                                <td><input class="btn btn-default btn-xs" type="submit" value="Eliminar"></td>
                            </tr>
                            <tr>
                                <td>Labrador minecraft</td>
                                <td>Descuento fijo</td>
                                <td>roto250</td>
                                <td>250</td>
                                <td><input class="btn btn-default btn-xs" type="submit" value="Eliminar"></td>
                            </tr>
                        </tbody>
                        </table>
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