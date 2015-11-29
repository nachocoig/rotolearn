<%@ page import="java.util.ArrayList" %>
<%@ page import="entities.Curso" %>
<%@ page import="entities.Promocion" %>
<!DOCTYPE html>
<html lang="es">
	<head>
	  <title>Listado descuentos</title>
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
	                        <li class="dropdown">
	                          <a class="dropdown-toggle" data-toggle="dropdown" href="#">Usuarios
	                          <span class="caret"></span></a>
	                          <ul class="dropdown-menu">
	                            <li><a href="veralumn.form">Alumnos</a></li>
	                            <li><a href="verprofes.form">Profesores</a></li>
	                          </ul>
	                        </li>
	                        <li class="dropdown">
	                            <a class="dropdown-toggle" data-toggle="dropdown" href="#">Cursos
	                            <span class="caret"></span></a>
	                            <ul class="dropdown-menu">
	                                 <li><a href="listadovalidados.form">Alta</a></li>
	                                 <li><a href="listadocursos.form">Listado</a></li>
	                                <li><a href="listadodestacados.form">Destacados</a></li>
	                            </ul>
	                        </li>
	                        <li><a href="admin_listadomateriales.jsp">Materiales cursos</a></li>
	                    </ul>
	                    <ul class="nav navbar-nav navbar-right">
	                        <li><a href="admin_reg.jsp"><span class="glyphicon glyphicon-plus"></span>A&ntilde;adir Usuario</a></li>
	                        
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
	    <div id="formCupon" class="container-fluid">
	    	 <%
				if(request.getAttribute("desc") != null)
				if(request.getAttribute("desc").equals("si")){
			%>
					<div class="row aviso">
	                	<div class="col-md-8 col-md-offset-2">
	                		<div class="alert alert-success" style="margin-bottom:0px">
								<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
							    <strong>Descuento creado.</strong> El descuento se ha creado correctamente.
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
							    <strong>Error al crear el descuento.</strong> El descuento no se ha podido crear.
							</div>
						</div>
	                </div>
			<%
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
							    <strong>Descuento eliminado.</strong> El descuento se ha eliminado correctamente.
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
							    <strong>Error al eliminar el descuento.</strong> El descuento no se ha podido eliminar.
							</div>
						</div>
	                </div>
			<%
				}
			%>
    		<div class="row ">
    			<%
    				if(request.getAttribute("listaCursos") != null){
    					ArrayList<Curso> listaCursos = (ArrayList<Curso>) request.getAttribute("listaCursos");
    					if(listaCursos.isEmpty()){	
    			%>
    			<h3 style="margin-left:10px">No existen cursos validados en los que poder crear descuentos</h3>
    			<%		
    					}else{
    			%>
    		    <form role="form" action="crearDescuento.form" method="post">
        			<div class="col-md-6 col-md-offset-3">
        			    <h2>Crear descuento</h2>
        			    <div class="form-group">
    						<label for="sel1">Curso al que aplicar el descuento: </label>
    						<select class="form-control" name="cursoDesc" id="sel1" required>
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
    						<label name="descuento">Porcentaje a descontar: </label>
    						<input type="number" class="form-control" name="descuento" min="0" max="30" placeholder="Introduce el descuento en porcentaje" required>
    					</div>
    					<button type="submit" class="btn btn-success">Crear descuento</button>
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
			        <h2 class="titulo">Listado de Cupones de descuento</h2>
			        <%
	    				if(request.getAttribute("listaDescuentos") != null){
	    					ArrayList<Promocion> listaDescuentos = (ArrayList<Promocion>) request.getAttribute("listaDescuentos");
	    					if(listaDescuentos.isEmpty()){	
	    			%>
	    			<h3>No existen descuentos</h3>	
	    			<%		
	    					}else{
	    			%>
			        <div class="table-responsive">          
			            <table class="table table-condensed table-hover">
			            <thead>
			                <tr>
			                    <th>Eliminar descuento</th>
			                    <th>ID</th>
			                    <th>Curso</th>
			                    <th>Descuento (%)</th>
			                </tr>
			            </thead>
			            <tbody>
				            <%
	                   			for(int i=0;i<listaDescuentos.size();i++){
	                   				int ID = listaDescuentos.get(i).getId();
	                    	%>
				                <tr>
				                    <td><a class="btn btn-default btn-xs" href="eliminarDescuento.form?ID=<%=ID %>" >Eliminar</a></td>
				                    <td><%=ID %></td>
				                    <td><%=listaDescuentos.get(i).getCurso().getTitulo() %></td>
				                    <td><%=listaDescuentos.get(i).getDescuento() %></td>
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
	
	</body>
</html>