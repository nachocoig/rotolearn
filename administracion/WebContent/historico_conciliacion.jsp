<%@ page import="entities.*" %>
<%@ page import="java.util.ArrayList" %>
<!DOCTYPE html>
<html lang="es">
<head>
  <title>Hist&oacute;rico de conciliaci&oacute;n</title>
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
                          <a class="dropdown-toggle" data-toggle="dropdown" href="#">Usuarios<span class="caret"></span></a>
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
		                <li><a href="admin_listadocupones.jsp">Cupones descuento</a></li>
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
    <div id="filtros" class="container row col-md-12">
		<!-- Trigger the modal with a button -->
		<button type="button" class="btn btn-info btn-lg btn-block" data-toggle="modal" data-target="#myModal">Filtrar resultados</button>
    </div>
    
    <div id="cuerpo" class="container row col-sm-12">
        <h2 class="titulo">Hist&oacute;rico</h2>
        <div class="table-responsive">          
            <table class="table table-condensed table-hover">
            <thead>
                <tr>
                    <th>Cobrador (ID)</th>
                    <th>A&ntilde;o</th>
                    <th>Mes</th>
                    <th>Importe</th>
                    <th>C&oacute;digo de operaci&oacute;n</th>
                    <th>C&oacute;digo de pedido</th>
                    <th>Descuento</th>
                    <th>Promoci&oacute;n</th>
                    <th>Pagado</th>
                </tr>
            </thead>
            <tbody>
             <tbody>
			             <%
	                     ArrayList<Conciliacion> con = (ArrayList<Conciliacion>) request.getAttribute("conciliaciones");
			             if(con == null || con.isEmpty()){%>
                        	
                               <tr>
                                 <td>No hay ninguna entrada</td> 
                               </tr>
                            
                         
                       <%}else{
                		for(int i=0; i<con.size();i++){
                		%>
			           		<tr>
			           			<td><%=con.get(i).getUsuario().getId() %></td>
			            		<td><%=con.get(i).getAnio() %></td>
			            		<td><%=con.get(i).getMes() %></td>
			            		<td><%=con.get(i).getImporte() %></td>
			            		<td><%=con.get(i).getCodOp() %></td>
			            		<td><%=con.get(i).getCodPed() %></td>
			            		<td><%=con.get(i).getDescuento() %></td>
			            		<td><%=con.get(i).getPromocion() %></td>
			            		<td><%=con.get(i).getPagado() %></td>  		
		            		</tr>
			           <% }} %> 
            
            </tbody>
            </table>
        </div>
    </div>
	  <!--FIN CUERPO-->


				<!-- Modal -->
				<div id="myModal" class="modal fade" role="dialog">
				  <div class="modal-dialog">
				
				    <!-- Modal content-->
				    <div class="modal-content">
				      <div class="modal-header">
				        <button type="button" class="close" data-dismiss="modal">&times;</button>
				        <h4 class="modal-title">Filtrado</h4>
				      </div>
				      <div class="modal-body">
							
							<form action="historico.form" method="POST" class="form-horizontal" role="form">
								<div class="form-group">
									<label class="control-label col-sm-2">Mes</label>
									<div class="col-sm-10">
										<select class="form-control" id="sel1" name="mes" >
											<option>Enero</option>
											<option>Febrero</option>
											<option>Marzo</option>
											<option>Abril</option>
											<option>Mayo</option>
											<option>Junio</option>
											<option>Julio</option>
											<option>Agosto</option>
											<option>Septimebre</option>
											<option>Octubre</option>
											<option>Noviembre</option>
											<option>Diciembre</option>
										</select>
									</div>
								</div>
								<div class="form-group">
									<label class="control-label col-sm-2">A&ntilde;o</label>
									<div class="col-sm-10"> 
									         
										<input type="number" class="form-control" id="anyo" placeholder="P.e: 2015" name="ano">
									
									</div>
								</div>
								<div class="form-group">
									<label class="control-label col-sm-2">Profesor</label>
									<div class="col-sm-10"> 
									         
										<input type="text" class="form-control" id="profesor" placeholder="P.e: Pedro Ruiz" name="profesor">
									
									</div>
								</div>
								<div class="form-group">        
									<div class="col-sm-offset-2 col-sm-10">
										<button type="submit" class="btn btn-default">Filtrar</button>
									</div>
								</div>
							</form>	
				      </div>
				    </div>
				  </div>
				</div>
</body>
</html>