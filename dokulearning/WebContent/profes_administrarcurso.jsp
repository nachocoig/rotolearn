<jsp:useBean id="perfil" class="es.rotolearn.javaBean.RegistroBean" scope="session"/>
<%@ page import="entities.*" %>
<%@ page import="java.util.ArrayList" %>
<!DOCTYPE html>
<html lang="es">
    <head>
        <title>Administrar curso</title>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
        <link rel="stylesheet" type="text/css" href="./style/profes_plantilla.css" media="screen"/><!--CSS PLANTILLA-->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css">
        <link rel="stylesheet" type="text/css" href="./style/footer.css" media="screen"/>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css">
        <script>
            $(document).ready(function(){
                $(".borrarSec").click(function(){
                    $(this).parents(".panel").remove();
				});
				$(".borrarLecB").click(function(){
                    $(this).parents(".borrarLec").remove();
				});
            });
        </script>
    </head>
    <body>
 	<%String etiqueta =(String) request.getAttribute("actual");
 	  if(etiqueta == null)
 		  etiqueta = "info";
 	%>
 
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
        </header>
        <!--FIN CABECERA-->
        <%
            
        	Curso aux = (Curso) request.getAttribute("curso");
    		int ID = aux.getId();
    	%>
        <!--CUERPO-->
        <div id="cuerpo" class="container-fluid">    
        
           	
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
				
				<%
					if(request.getAttribute("eliminado") != null)
					if(request.getAttribute("eliminado").equals("si")){
				%>
						<div class="row aviso">
		                	<div class="col-md-8 col-md-offset-2">
		                		<div class="alert alert-success" style="margin-bottom:0px">
									<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
								    <strong>Vale descuento eliminado.</strong> Tu vale descuento se ha eliminado correctamente.
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
								    <strong>Error al eliminar vale descuento.</strong> Tu vale no se ha podido eliminar.
								</div>
							</div>
		                </div>
				<%
					}
				%>
        
        
        
            <div class="row">
                <div class="col-md-10 col-md-offset-1">
                    <h2>Titulo de curso</h2>
                    <ul class="nav nav-tabs">
                        <li<%if(etiqueta.equals("info")){%> class="active"<%}%>><a data-toggle="tab" href="#info">Informaci&oacute;n</a></li>
                        <li<%if(etiqueta.equals("material")){%> class="active"<%}%>><a data-toggle="tab" href="#material">Material</a></li>
                        <li<%if(etiqueta.equals("profes")){%> class="active"<%}%>><a data-toggle="tab" href="#profes">Profesores</a></li>
                        <li<%if(etiqueta.equals("alumn")){%> class="active"<%}%>><a data-toggle="tab" href="#alumn">Alumnos</a></li>
                        <li<%if(etiqueta.equals("delete")){%> class="active"<%}%>><a data-toggle="tab" href="#delete"><span class="red">Eliminar curso</span></a></li>
                    </ul>
                    <div class="tab-content">
                    
                        <div id="info" class="tab-pane fade <%if(etiqueta.equals("info")){%>  in active<%}%>">
                            <h3>Descripci&oacute;n</h3>
                            <p><%=aux.getDescripcion() %></p>
                            <h3>Añade nueva descripci&oacute;n:</h3>
                            <form role="form" method="post" action="administrarCursos.form" enctype="multipart/form-data" >
                           		<div class="form-group">	
                           			<input type="hidden" value="editarDescripcion" name="tipo"/>	
                           			<input type="hidden" value="<%=ID%>" name="curso"/>
					    			<label for="moreinfo"><span class="red">*</span>Descripci&oacute;n</label>
									<textarea name="descripcion" class="form-control" rows="4" placeholder="Describe en que consiste el curso" required></textarea>
								</div>
								<div class="form-group">
									<button type="submit" class="btn btn-success">Actualizar</button>
								</div>	
							</form>
                        </div>
                        <div id="alumn" class="tab-pane fade <%if(etiqueta.equals("alumn")){%>  in active<%}%>">
                            <div class="row">
                                <div class="col-md-12">
                                    <div class="table-responsive">
                                        <h2 id="profesores" class="titulo">Listado de Profesores asociados</h2>          
                                  		<% 
                                        ArrayList<Usuario> alumnos = (ArrayList<Usuario>) request.getAttribute("alumnos");
                                      	if(alumnos == null){
                                   		%>
                                   			<h4> No hay ning&uacute;n alumno matriculado en este curso</h4> 
                                   		<%}else{%>         
	                                        <table class="table table-condensed table-hover">
	                                        <thead>
	                                            <tr>
	                                                <th>Nick</th>
	                                                <th>Nombre</th>
	                                                <th>Primer apellido</th>
	                                                <th>Segundo apellido</th>
	                                                <th>Email</th>
	                                                <th>Eliminar</th>
	                                            </tr>
	                                        </thead>
	                                        <tbody>
	                                        <%for(int i = 0; i < alumnos.size(); i++){%>
	                                            <tr>
	                                                <td><%=alumnos.get(i).getNickname() %></td>
	                                                <td><%=alumnos.get(i).getNombre() %></td>
	                                                <td><%=alumnos.get(i).getApellido1() %></td>
	                                                <td><%=alumnos.get(i).getApellido2() %></td>
	                                                <td><%=alumnos.get(i).getEmail() %></td>
													<td>
		                                                <form method="POST" action="administrarCursos.form"  enctype="multipart/form-data">
					                                		<input type="hidden" value="<%= ID %>" name="curso"/>
					                                		<input type="hidden" value="<%= alumnos.get(i).getId() %>" name="alumno"/>	
					                                		<input type="hidden" value="eliminarAlumno" name="tipo"/>	
					                                		<input class="btn btn-default btn-xs" type="submit" value="Eliminar">
					                                	</form>
	                                                </td>
	                                            </tr>
	                                         <%}%>
	                                        </tbody>
	                                        </table>
                                        <%}%>
                                    </div>
                                </div>
                            </div>
                        </div>
                        
                        <div id="material" class="tab-pane fade <%if(etiqueta.equals("material")){%>  in active<%}%>">
                          <%ArrayList<Seccion> secciones = (ArrayList<Seccion>) request.getAttribute("secciones");%>
                          <%ArrayList<Leccion> lecciones = (ArrayList<Leccion>) request.getAttribute("lecciones");%>
                          <%ArrayList<Material> materiales = (ArrayList<Material>) request.getAttribute("materiales");%>
                          <%ArrayList<Leccion> leccionesAux = new ArrayList<Leccion>();%>
                          <%ArrayList<Material> materialesAux = new ArrayList<Material>();%>
                             <h3>Material del curso</h3>
                             
                             
                              <div class="panel-group">
                              <%for(int i = 0; i < secciones.size(); i++){
	                              for(int w = 0; w < lecciones.size(); w++){
	                            	  if(lecciones.get(w).getSeccion().getId() == secciones.get(i).getId())
	                            		  leccionesAux.add(lecciones.get(w));
	                              }
	                              %>
                              
                                <div class="panel panel-primary">
                                  <div class="panel-heading">
                                    <h4 class="panel-title">
                                      <a data-toggle="collapse" href="#seccion<%=secciones.get(i).getId()%>"><%=secciones.get(i).getNombre() %></a>
                                    </h4>
                                  </div>
                                  <div id="seccion<%=secciones.get(i).getId()%>" class="panel-collapse collapse">
                                    <%
                                    if(leccionesAux != null)
                                    for(int j = 0; j < leccionesAux.size(); j++){ %>
                                    <div class="panel-body">
                                        <h2><%=leccionesAux.get(j).getNombre()%></h2>
                                        <p><%=leccionesAux.get(j).getDescripcion() %></p>
                                        <div class="list-group">
                                        <% //para obtener todas los materiales de una leccion
	      	                              for(int w = 0; w < materiales.size(); w++){
	    	                            	  if(materiales.get(w).getLeccion().getId() == leccionesAux.get(j).getId())
	    	                            		  materialesAux.add(materiales.get(w));
	    	                              }
                                          if(materialesAux != null)
                                          for(int w = 0; w < materialesAux.size(); w++){
                                          %>
                                          <a href="materiales/<%=materialesAux.get(w).getId()%>_mat.<%=materialesAux.get(w).getTipo() %>" download="<%=materialesAux.get(w).getNombre()%>.<%=materialesAux.get(w).getTipo() %>" class="list-group-item"><%=materialesAux.get(w).getNombre()%></a>
                                          <%} 
                                          	materialesAux.clear();
                                          %>
                                          <button type="button" class="btn btn-info btn-lg btn-block" data-toggle="modal" data-target="#myModal<%=i+1%><%=j+1%>"><span class="glyphicon glyphicon-plus"></span></button>
                                        </div>
                                    </div>
                                    
                                    <!-- Modal -->
	                                <div id="myModal<%=i+1%><%=j+1%>" class="modal fade" role="dialog">
	                                  <div class="modal-dialog">
	                                    <!-- Modal content-->
	                                    <div class="modal-content">
	                                       	  <form method="POST" action="administrarCursos.form"  enctype="multipart/form-data">
	                                              <div class="modal-header">
	                                                <button type="button" class="close" data-dismiss="modal">&times;</button>
	                                                <h4 class="modal-title">Nuevo material</h4>
	                                              </div>
	                                              <div class="modal-body">
		                               				 <input type="hidden" value="<%= ID %>" name="curso"/>
		                               				 <input type="hidden" value="<%=leccionesAux.get(j).getId() %>" name="leccion">
		                                     		 <input type="hidden" value="crearMaterial" name="tipo"/>
	                                                 <label for="material">Nombre del material: </label>
	                                                 <input type="text" class="form-control" id="material" placeholder="Introduce aqu&iacute; el nombre del material" name="nombre" required>
	                                                 <label for="material2">Archivo: </label>
	                                                 <input type="file" class="form-control" id="material2" name="fichero" required>
	                                              </div>
	                                              <div class="modal-footer">
	                                                <button type="submit" class="btn btn-default">A&ntilde;adir</button>
	                                                <button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
	                                              </div>
	                                        </form>
	                                    </div>
	                                
	                                  </div>
	                                </div>
                                    <%}
                                	 leccionesAux.clear();
                                     %>
                                    <div class="panel-footer">
                                    <form method="POST" action="administrarCursos.form"  enctype="multipart/form-data">
                               				  <input type="hidden" value="<%= ID %>" name="curso"/>
                               				  <input type="hidden" value="<%=secciones.get(i).getId() %>" name="seccion">
                                     		  <input type="hidden" value="crearLeccion" name="tipo"/>
                                              <div class="modal-header">
                                                <h4 class="modal-title">A&ntilde;adir nueva lecci&oacute;n</h4>
                                              </div>
                                              <div class="modal-body">
                                                  <label for="leccion">Nombre de la lecci&oacute;n: </label>
                                                  <input type="text" class="form-control" id="leccion" placeholder="Introduce aqu&iacute; el nombre de la lecci&oacute;n" name="nombre">
                                                  <label for="leccion2">Descripci&oacute;n de la lecci&oacute;n </label>
                                                  <textarea class="form-control" rows="5" id="leccion2" name="descripcion"></textarea>
                                              </div>
                                              <div class="modal-footer">
                                                <button type="submit" class="btn btn-default">A&ntilde;adir</button>
                                                <button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
                                              </div>
                                     </form>
                                     <!-- Trigger the modal with a button
                                        <button type="button" class="btn btn-info btn-lg btn-block" data-toggle="modal" data-target="#myModal2">A&ntilde;adir nueva lecci&oacute;n</button>
                                     --></div>
                                  </div>
                                </div>
                             <%} %>
                              </div>
                              
                               <!-- Trigger the modal with a button -->
                              <button type="button" class="btn btn-info btn-lg btn-block" data-toggle="modal" data-target="#myModal1">A&ntilde;adir nueva secci&oacute;n</button>
                            
                        </div>
                        
                        <div id="profes" class="tab-pane fade <%if(etiqueta.equals("profes")){%>  in active<%}%>">
                            <div class="row">
                                <div class="col-md-12">
                                    <div class="table-responsive">
                                        <h2 id="profesores" class="titulo">Listado de Profesores asociados</h2>          
                                       		<% 
	                                        ArrayList<Usuario> asociados = (ArrayList<Usuario>) request.getAttribute("asociados");
                                       		ArrayList<Usuario> asociadosSinValidar = (ArrayList<Usuario>) request.getAttribute("asociadosSinValidar");
	                                       	if(asociados == null && asociadosSinValidar == null){
                                       		%>
                                       		<h4> No existen profesores asociados al curso</h4> 
                                       		<%}else{%>
                                        <table class="table table-condensed table-hover">
	                                        <thead>
	                                            <tr>
	                                                <th>Nombre</th>
	                                                <th>Primer apellido</th>
	                                                <th>Segundo apellido</th>
	                                                <th>Email</th>
	                                                <th>Nickname</th>
	                                                <th>Eliminar</th>
	                                            </tr>
	                                        </thead>
	                                        <tbody>
					                        <%    
					                        for(int i = 0; i < asociados.size(); i++){
					                        %>            
	                                            <tr>
	                                                <td><%= asociados.get(i).getNombre()%></td>
	                                                <td><%= asociados.get(i).getApellido1()%></td>
	                                                <td><%= asociados.get(i).getApellido2()%></td>
	                                                <td><%= asociados.get(i).getEmail()%></td>
	                                                <td><%= asociados.get(i).getNickname()%></td>
	                                                <td>
		                                                <form method="POST" action="administrarCursos.form"  enctype="multipart/form-data">
					                                		<input type="hidden" value="<%= ID %>" name="curso"/>
					                                		<input type="hidden" value="<%= asociados.get(i).getId() %>" name="asociado"/>	
					                                		<input type="hidden" value="eliminarAsociado" name="tipo"/>	
					                                		<input class="btn btn-default btn-xs" type="submit" value="Eliminar">
					                                	</form>
	                                                </td>
	                                            </tr>
	                        				<%}
											/* Asociados sin validar */
											if(asociadosSinValidar != null || !asociadosSinValidar.isEmpty())
					                        for(int i = 0; i < asociadosSinValidar.size(); i++){
					                        %>            
	                                            <tr class="asociadosSinValidar">
	                                                <td><%= asociadosSinValidar.get(i).getNombre()%></td>
	                                                <td><%= asociadosSinValidar.get(i).getApellido1()%></td>
	                                                <td><%= asociadosSinValidar.get(i).getApellido2()%></td>
	                                                <td><%= asociadosSinValidar.get(i).getEmail()%></td>
	                                                <td><%= asociadosSinValidar.get(i).getNickname()%></td>
	                                                <td>
		                                                <form method="POST" action="administrarCursos.form"  enctype="multipart/form-data">
					                                		<input type="hidden" value="<%= ID %>" name="curso"/>
					                                		<input type="hidden" value="<%= asociadosSinValidar.get(i).getId() %>" name="asociado"/>	
					                                		<input type="hidden" value="validarAsociado" name="tipo"/>	
					                                		<input class="btn btn-default btn-xs" type="submit" value="Aceptar">
					                                	</form>
		                                                <form method="POST" action="administrarCursos.form"  enctype="multipart/form-data">
					                                		<input type="hidden" value="<%= ID %>" name="curso"/>
					                                		<input type="hidden" value="<%= asociadosSinValidar.get(i).getId() %>" name="asociado"/>	
					                                		<input type="hidden" value="eliminarAsociado" name="tipo"/>	
					                                		<input class="btn btn-default btn-xs" type="submit" value="Denegar">
					                                	</form>
	                                                </td>
	                                            </tr>
	                        				<%}%>                    
	                                        </tbody>
                                        </table>
                        				<%}%>
                                    </div>
                                    
									<div class="container">
									  <button type="button" class="btn btn-default" data-toggle="modal" data-target="#myModal">A&ntilde;adir asociado</button>
									
									  <!-- Modal -->
									  <div class="modal fade" id="myModal" role="dialog">
									    <div class="modal-dialog modal-md">
									      <div class="modal-content">
									      	<form method="POST" action="administrarCursos.form"  enctype="multipart/form-data">
										        <div class="modal-header">
										          <button type="button" class="close" data-dismiss="modal">&times;</button>
										          <h4 class="modal-title">Agregar nuevo profesor asociado</h4>
										        </div>
										        <div class="modal-body">
										         	<p>Escribe el nick o el correo electr&oacute;nico del profesor que quieres agregar como asociado del curso.</p>
										         	<input type="hidden" value="<%= ID %>" name="curso"/>
										         	<input type="hidden" value="agregarAsociado" name="tipo"/>	
										         	<input type="text" name="asociado" placeholder="P.e profe@profe.es o profe1"/>
										        </div>
										        <div class="modal-footer">
										        	<input type="submit" class="btn btn-default" value="Agregar"/>
										        	<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
										        </div>
									        </form>
									      </div>
									    </div>
									  </div>
									</div>
                                    
                                </div>
                            </div>
                        </div>
                        
                        <div id="delete" class="tab-pane fade <%if(etiqueta.equals("delete")){%> in active<%}%>">
                            <h3>¿Est&aacute;s seguro de que deseas borrar el curso?</h3>
                            <p>Si borras el curso eliminaras todo el rastro del curso de las bases de datos, como pueden ser los alumnos matriculados, logros, cupones de descuento,
                                este cambio no podra ser deshecho, por lo que una vez que confirme su eliminaci&oacute;n no hay vuelta a atras. ¿Esta seguro de que desea borrar el curso?</p>
							<form method="POST" action="administrarCursos.form"  enctype="multipart/form-data">
								<input type="hidden" value="<%= ID %>" name="curso"/>
								<input type="hidden" value="eliminar" name="tipo"/>	
								<input class="btn btn-danger" type="submit" value="Si, eliminar">
					     	</form>
                        </div>
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
   		<!-- MODALES -->                                      
                                <!-- Modal -->
                                <div id="myModal1" class="modal fade" role="dialog">
                                  <div class="modal-dialog">
                                    <!-- Modal content-->
                                    <div class="modal-content">
                                     	<form method="POST" action="administrarCursos.form"  enctype="multipart/form-data">
                               				  <input type="hidden" value="<%= ID %>" name="curso"/>
                                     		  <input type="hidden" value="crearSeccion" name="tipo"/>
                                              <div class="modal-header">
                                                <button type="button" class="close" data-dismiss="modal">&times;</button>
                                                <h4 class="modal-title">Nueva secci&oacute;n</h4>
                                              </div>
                                              <div class="modal-body">
                                                  <label for="seccion">Nombre de la secci&oacute;n: </label>
                                                  <input type="text" class="form-control" id="seccion" placeholder="Introduce aqu&iacute; el nombre de la secci&oacute;n" name="nombre">
                                              </div>
                                              <div class="modal-footer">
                                                <input type="submit" class="btn btn-default" value="A&ntilde;adir"></input>
                                                <button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
                                              </div>
                                        </form>
                                    </div>
                                
                                  </div>
                                </div>
                                

    </body>
</html>