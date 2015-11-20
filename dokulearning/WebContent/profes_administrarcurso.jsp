<jsp:useBean id="perfil" class="es.rotolearn.javaBean.RegistroBean" scope="session"/>
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
           <div id="miga"><a href="profes_panel.jsp">Panel de control</a> > <a href="profes_listadocursos.jsp">Ver tus cursos</a> > <a href="profes_administrarcurso.jsp">Administrar curso</a></div>
        </header>
        <!--FIN CABECERA-->
        
        <!--CUERPO-->
        <div id="cuerpo" class="container-fluid">
            <div class="row">
                <div class="col-md-10 col-md-offset-1">
                    <h2>Titulo de curso</h2>
                    <ul class="nav nav-tabs">
                        <li class="active"><a data-toggle="tab" href="#info">Informaci&oacute;n</a></li>
                        <li><a data-toggle="tab" href="#material">Material</a></li>
                        <li><a data-toggle="tab" href="#profes">Profesores</a></li>
                        <li><a data-toggle="tab" href="#alumn">Alumnos</a></li>
                        <li><a data-toggle="tab" href="#delete"><span class="red">Eliminar curso</span></a></li>
                    </ul>
                    <div class="tab-content">
                        <div id="info" class="tab-pane fade in active">
                            <h3>Descripci&oacute;n</h3>
                            <p>Poner textbox para actualizar las descripciones de los cursos</p>
                        </div>
                        <div id="alumn" class="tab-pane fade">
                            <div class="row">
                                <div class="col-md-12">
                                    <div class="table-responsive">
                                        <h2 class="titulo">Listado de Alumnos matriculados</h2>          
                                        <table class="table table-condensed table-hover">
                                        <thead>
                                            <tr>
                                                <th>Nombre</th>
                                                <th>Primer apellido</th>
                                                <th>Segundo apellido</th>
                                                <th>Email</th>
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
                        
                        <div id="material" class="tab-pane fade">
                            <h3>Material del curso</h3>
                            <div class="formleccion row">
                                <form class="form-inline" role="form">
                                    <div class="form-group">
                                        <label for="titulo">T&iacute;tulo Secci&oacute;n:</label>
                                        <input type="text" class="form-control input-sm" id="titulo" placeholder="Introduce el t&iacute;tulo" required>
                                    </div>
                                    <button type="submit" id="seccion" name="seccion" class="btn btn-info btn-sm">Crear Seccion</button>
                                </form>   
                            </div>
                            
                            <!--Seccion-->
                            <div class="row panel panel-info">
                                <div class="panel-heading col-md-12">
                                    <div class="col-md-9">
                                        Titulo de la Secci&oacute;n
                                        <span class="rojo"><span title="Eliminar Secci&oacute;n" class="glyphicon glyphicon-remove borrarSec"/></span>
                                    </div>
                                </div>
                                <div class="panel-body">
                                    <!--Crear leccion-->
                                    <div class="formleccion col-md-12">
                                        <form class="form-inline" role="form">
                                            <div class="form-group">
                                                <label for="titulo">T&iacute;tulo lecci&oacute;n:</label>
                                                <input type="text" class="form-control input-sm" id="titulo" placeholder="Introduce el t&iacute;tulo" required>
                                            </div>
                                            <button type="submit" class="btn btn-info btn-sm">Crear Lecci&oacute;n</button>
                                        </form>   
                                    </div>
                                    
                                    <!--Leccion-->
                                    <div class="panel panel-warning borrarLec">
                                        <div class="panel-heading col-md-12">
                                            <div class="col-md-9">
                                                Titulo de la Lecci&oacute;n
                                                <span class="rojo"><span title="Eliminar Lecci&oacute;n" class="glyphicon glyphicon-remove borrarLecB"/></span>
                                            </div>
                                        </div>
                                        <div class="panel-body">
                                            <!--Añadir material-->
                                            <div class="formleccion col-md-12">
                                                <form class="form-inline" role="form">
                                                    <div class="form-group">
                                                        <label for="nombremat">Nombre material:</label>
                                                        <input type="text" class="form-control input-sm" id="nombremat" placeholder="Introduce el nombre" required>
                                                    </div>
                                                    <div class="form-group">
                                						<input type="file" id="exampleInputFile" required>
                                					</div>
                                                    <button type="submit" class="btn btn-info btn-sm">Agregar material</button>
                                              </form>   
                                            </div>
                                            
                                            <!--AÑADIR TABLA MATERIAL-->
                                            
                                        </div>
                                    </div>
                                    <!--Fin Leccion-->
                                    
                                </div>
                            </div>
                            <!--Fin Seccion-->
                            
                            
                            <!--Seccion-->
                            <div class="row panel panel-info">
                                <div class="panel-heading col-md-12">
                                    <div class="col-md-9">
                                        Titulo de la Secci&oacute;n
                                        <span class="rojo"><span title="Eliminar Secci&oacute;n" class="glyphicon glyphicon-remove borrarSec"/></span>
                                    </div>
                                </div>
                                <div class="panel-body">
                                    <!--Crear leccion-->
                                    <div class="formleccion col-md-12">
                                        <form class="form-inline" role="form">
                                            <div class="form-group">
                                                <label for="titulo">T&iacute;tulo lecci&oacute;n:</label>
                                                <input type="text" class="form-control input-sm" id="titulo" placeholder="Introduce el t&iacute;tulo" required>
                                            </div>
                                            <button type="submit" class="btn btn-info btn-sm">Crear Lecci&oacute;n</button>
                                      </form>   
                                    </div>
                                    
                                    <!--Leccion-->
                                    <div class="panel panel-warning borrarLec">
                                        <div class="panel-heading col-md-12">
                                            <div class="col-md-9">
                                                Titulo de la Lecci&oacute;n
                                                <span class="rojo"><span title="Eliminar Lecci&oacute;n" class="glyphicon glyphicon-remove borrarLecB"/></span>
                                            </div>
                                        </div>
                                        <div class="panel-body">
                                            <!--Añadir material-->
                                            <div class="formleccion col-md-12">
                                                <form class="form-inline" role="form">
                                                    <div class="form-group">
                                                        <label for="nombremat">Nombre material:</label>
                                                        <input type="text" class="form-control input-sm" id="nombremat" placeholder="Introduce el nombre" required>
                                                    </div>
                                                    <div class="form-group">
                                						<input type="file" id="exampleInputFile" required>
                                					</div>
                                                    <button type="submit" class="btn btn-info btn-sm">Agregar material</button>
                                              </form>   
                                            </div>
                                            
                                            <!--AÑADIR TABLA MATERIAL-->
                                            
                                        </div>
                                    </div>
                                    <!--Fin Leccion-->
                                    
                                    <!--Leccion-->
                                    <div class="panel panel-warning borrarLec">
                                        <div class="panel-heading col-md-12">
                                            <div class="col-md-9">
                                                Titulo de la Lecci&oacute;n
                                                <span class="rojo"><span title="Eliminar Lecci&oacute;n" class="glyphicon glyphicon-remove borrarLecB"/></span>
                                            </div>
                                        </div>
                                        <div class="panel-body">
                                            <!--Añadir material-->
                                            <div class="formleccion col-md-12">
                                                <form class="form-inline" role="form">
                                                    <div class="form-group">
                                                        <label for="nombremat">Nombre material:</label>
                                                        <input type="text" class="form-control input-sm" id="nombremat" placeholder="Introduce el nombre" required>
                                                    </div>
                                                    <div class="form-group">
                                						<input type="file" id="exampleInputFile" required>
                                					</div>
                                                    <button type="submit" class="btn btn-info btn-sm">Agregar material</button>
                                              </form>   
                                            </div>
                                            
                                            <!--A&NTILDE;ADIR TABLA MATERIAL-->
                                            
                                        </div>
                                    </div>
                                    <!--Fin Leccion-->
                                    
                                </div>
                            </div>
                            <!--Fin Seccion-->
                            
                        </div>
                        
                        <div id="profes" class="tab-pane fade">
                            <div class="row">
                                <div class="col-md-12">
                                    <div class="table-responsive">
                                        <h2 id="profesores" class="titulo">Listado de Profesores asociados</h2>          
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
                                            <tr>
                                                <td><%=perfil.getNombre()%></td>
                                                <td><%=perfil.getApellido1()%></td>
                                                <td><%=perfil.getApellido2()%></td>
                                                <td><%=perfil.getEmail()%></td>
                                                <td><%=perfil.getNickName()%></td>
                                                <td><input class="btn btn-default btn-xs" type="submit" value="Eliminar"></td>
                                            </tr>
                                        </tbody>
                                        </table>
                                    </div>
                                </div>
                            </div>
                        </div>
                        
                        <div id="delete" class="tab-pane fade">
                            <h3>�Est&aacute;s seguro de que deseas borrar el curso?</h3>
                            <p>Si borras el curso eliminaras todo el rastro del curso de las bases de datos, como pueden ser los alumnos matriculados, logros, cupones de descuento,
                                este cambio no podra ser deshecho, por lo que una vez que confirme su eliminaci&oacute;n no hay vuelta a atras. �Esta seguro de que desea borrar el curso?</p>
                            <button type="button" class="btn btn-danger" name="delete">Eliminar curso</button>
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
    
    </body>
</html>