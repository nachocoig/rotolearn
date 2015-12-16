<jsp:useBean id="perfil" class="es.rotolearn.javaBean.RegistroBean" scope="session"/>
<%@ page import="java.util.ArrayList" %>
<%@ page import="entities.Curso" %>
<%@ page import="entities.Descuento" %>
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
                            <li><a href="Notificacion.form">Inicio</a></li>
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
	                            <a class="dropdown-toggle" data-toggle="dropdown" href="#"><span class="caret"></span> <%=session.getAttribute("usuario")%> <%if(perfil.getImagen()){%>
                            <img src="images/im_usuarios/<%=perfil.getNickName()%>_perfil.jpg" class="img-circle" alt="Cinque Terre" width="30" height="30"/> </a>
                            <%}else{ %>
                            <img src="./images/perfil/anonimo.jpeg" class="img-circle" alt="Cinque Terre" width="30" height="30"/> </a>
                            
                            <%} %>
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
    	<div id="formCupon" class="container-fluid">
    	
    			<%
					if(request.getAttribute("aviso") != null){
						String aux2 = (String) request.getAttribute("aviso");
						String mensaje [] = aux2.split("/");
						if(mensaje[0].equals("SI")){
				%>
							<div class="row aviso">
			                	<div class="col-md-8 col-md-offset-2">
			                		<div class="alert alert-success" style="margin-bottom:0px">
										<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
									    <strong>¡Perfecto!</strong> <%=mensaje[1] %>.
									</div>
			                	</div>
			                </div>
				<%
						}else{
				%>
							<div class="row aviso">
			                	<div class="col-md-8 col-md-offset-2">
									<div class="alert alert-danger" style="margin-top:10px" style="margin-bottom:0px">
										<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
									    <strong>¡Error!</strong> <%=mensaje[1] %>.
									</div>
								</div>
			                </div>
				<%
						}
					}
				%>
        
    		<div class="row ">
    		
    			<%
    				if(request.getAttribute("listaCursos") != null){
    					ArrayList<Curso> listaCursos = (ArrayList<Curso>) request.getAttribute("listaCursos");
    					if(listaCursos.isEmpty()){	
    			%>
    			<h3 style="margin-left:10px">Debes de crear un curso y que sea validado para poder crear descuentos</h3>	
    			<%		
    					}else{
    			%>
    		    <form role="form" method="post" action="vale.form">
        			<div class="col-md-3 col-md-offset-3">
        			    <h2>Crear vale de descuento</h2>
						<input type=hidden name=tipo value="VALE">
        			    <div class="form-group">
    						<label for="sel1">Curso al que aplicar el vale: </label>
    						<select class="form-control"  id="sel1" name="cursoVale"required>
    						<%
    							for(int i=0;i<listaCursos.size();i++){
    						%>
    							<option value="<%=listaCursos.get(i).getId() %>"><%=listaCursos.get(i).getTitulo() %></option>
    						<%
    							}
    						%>
    						</select>
    					</div>
    					<div class="form-group">
    						<label for="descuento">Porcentaje a descontar: </label>
    						<input type="number" class="form-control" name="descuento" min="0" max="70" placeholder="Introduce el descuento en porcentaje" required>
    					</div>
        			</div>
        			<div class="col-md-3 ">
                        <h3>Condiciones de validez del vale</h3>
    					<div class="form-group">
    						<label for="minCursos">Numero minimo de cursos matriculados por el alumno: </label>
    						<input type="number" class="form-control" name="minCursos" min="0" max="10" required>
    					</div>
                        <div class="form-group">
                            <label for="descuento">Fecha de validez del vale: </label>
                            <input type="text" class="form-control" name="validez" placeholder="DD/MM/AAAA">
                        </div>
    					<button type="submit" class="btn btn-success">Crear vale descuento</button>
        			</div>
        		</form>
        		<%
    					}
    				}
        		%>
    		</div>
    	</div>
    	
    	<div id="cuerpo" class="container-fluid">
            <div class="row">
                <div class="col-md-12">
                
	                <%
	    				if(request.getAttribute("listaDescuentos") != null){
	    					ArrayList<Descuento> listaDescuentos = (ArrayList<Descuento>) request.getAttribute("listaDescuentos");
	    					if(listaDescuentos.isEmpty()){	
	    			%>
	    			<h3>Aun no tienes ningun vale de descuento creado, debes de crear un curso y que sea validado para poder crear descuentos</h3>	
	    			<%		
	    					}else{
	    			%>
                    <div class="table-responsive">
                        <h2 class="titulo">Listado de Descuentos</h2>          
                        <table class="table table-condensed table-hover">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Curso</th>
                                <th>Codigo</th>
                                <th>Validez</th>
                                <th>Eliminar</th>
                            </tr>
                        </thead>
                        <tbody>
                            
                    		<%
                    			for(int i=0;i<listaDescuentos.size();i++){
                    				int ID = listaDescuentos.get(i).getId();
                    		%>
                    		<tr>
                                <td><%=ID %></td>
                                <td><%=listaDescuentos.get(i).getCurso().getTitulo() %></td>
                                <td><%=listaDescuentos.get(i).getCupon() %></td>
                                <td><%=listaDescuentos.get(i).getValidez() %></td>
                                <td>
                                	<form role="form" method="post" action="vale.form">
                                		<input type=hidden name=eliminar value="<%=ID %>">
                                		<button type="submit" class="btn btn-default btn-xs">Eliminar</button>
                                	</form>
                                </td>
                            </tr>
                            <%
                    			}
                            %>
                            
                        </tbody>
                        </table>
                    </div>
                    <%
	    					}
	    				}
                    %>
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
    	</footer>
    	<!--FIN PIE DE PAGINA-->
    
    </body>
</html>