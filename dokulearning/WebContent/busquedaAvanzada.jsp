<jsp:useBean id="perfil" class="es.rotolearn.javaBean.RegistroBean" scope="session"/>
<%@ page import="java.util.ArrayList" %>
<%@ page import="es.rotolearn.tablas.Curso" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <title>C&aacute;talogo de cursos</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
    <link rel="stylesheet" type="text/css" href="./style/plantilla.css" media="screen"/>
    <link href="style/catalogo.css" rel="stylesheet">
	<link href='https://fonts.googleapis.com/css?family=Merriweather+Sans' rel='stylesheet' type='text/css'>
	<link rel="stylesheet" type="text/css" href="./style/footer.css" media="screen"/>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css">

		<!-- The JavaScript -->
        <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.4.2/jquery.min.js"></script>
        <script type="text/javascript">
            $(function() {

				var $ui = $('#ui_element');
				

				$ui.find('.sb_input').bind('focus click',function(){
					$ui.find('.sb_down')
					   .addClass('sb_up')
					   .removeClass('sb_down')
					   .andSelf()
					   .find('.sb_dropdown')
					   .show();
				});
				

				$ui.bind('mouseleave',function(){
					$ui.find('.sb_up')
					   .addClass('sb_down')
					   .removeClass('sb_up')
					   .andSelf()
					   .find('.sb_dropdown')
					   .hide();
				});
				

				$ui.find('.sb_dropdown').find('label[for="all"]').prev().bind('click',function(){
					$(this).parent().siblings().find(':checkbox').attr('checked',this.checked).attr('disabled',this.checked);
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
                        <!--<li><a href="perfil.jsp">Mi perfil</a></li>-->
                        <li><a class="activa" href="catalogo.jsp">Cat&aacute;logo de cursos</a></li>
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
                                <li><a href="perfil.jsp"><span class="glyphicon glyphicon-user"></span>Mi perfil</a></li>
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
    <!--FIN CABECERA
    <div class="container-fluid">
	    <div class="row" id="buscador">
            <div class="col-md-12">
                <form class="field" id="searchform" action="#">
                    <input type="text" id="searchterm" placeholder="Encuentra el curso que buscas" />
                    <button type="submit" id="search" value="submit">Buscar</button>
                </form>
            </div>
        </div>
    </div>-->
    <%
    ArrayList<String> cats = (ArrayList<String>) request.getAttribute("categorias");
    String[] palabras =(String[]) request.getAttribute("palabras");
    %>
    <div class="container-fluid">
	    <div class="row" id="cuerpo">
	    	<div id="bAvanzada" class="col-md-2">
                <form  id="FiltroTag" class="busqueda" method="POST" action="busquedaAvanzada.form">
					    <h3>Palabra clave</h3>
					    <input class="form-control" id="palabra_clave" type="text" placeholder="Java, android, edici&oacute;n de videos, ..." name="palabra">
					    <h3>Categor&iacute;as </h3>
					    <div class="col-md-6">
	      					<label><input type="checkbox" name="cat1" value="Programacion" <%if(cats != null && cats.contains("Programacion")){%>checked<%} %>> Programaci&oacute;n</label><br>
	      					<label><input type="checkbox" name="cat2" value="IT y Software"<%if(cats != null && cats.contains("IT y Software")){%>checked<%} %>> IT y Software</label><br>
	      					<label><input type="checkbox" name="cat3" value="Negocios"<%if(cats != null && cats.contains("Negocios")){%>checked<%} %>> Negocios</label><br>
	      					<label><input type="checkbox" name="cat4" value="Diseño"<%if(cats != null && cats.contains("Diseño")){%>checked<%} %>> Dise&ntilde;o</label><br>
	      				</div>
					    <div class="col-md-6">
	      					<label><input type="checkbox" name="cat5" value="Fotografia"<%if(cats != null && cats.contains("Fotografia")){%>checked<%} %>> Fotograf&iacute;a</label><br>
	      					<label><input type="checkbox" name="cat6" value="Salud y Fitness"<%if(cats != null && cats.contains("Salud y Fitness")){%>checked<%} %>> Salud y Fitness</label><br>
	      					<label><input type="checkbox" name="cat7" value="Lifestyle"<%if(cats != null && cats.contains("Lifestyle")){%>checked<%} %>> Lifestyle</label><br>
	      					<label><input type="checkbox" name="cat8" value="Musica"<%if(cats != null && cats.contains("Musica")){%>checked<%} %>> M&uacute;sica</label><br>
	      					<label><input type="checkbox" name="cat9" value="Idioma"<%if(cats != null && cats.contains("Idioma")){%>checked<%} %>> Idioma</label><br>
	      				</div>
					    <button id="palabra_clave_submit" type="submit" class="btn btn-default btn-block">Buscar</button>
                </form>
                <%if(palabras != null){ %>
                <div id="tags" class="col-md-12">
       	        <%
               	for(int i=0; i<palabras.length;i++){
                %>
					<div class="alert alert-success">
						<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
						<strong><%=palabras[i] %></strong>
					</div>
				<%} %>
				</div>
				<%} %>
            </div>

            <div id="cursos" class="col-md-10">
                <ul class="portfolio-items">
                    <%
                    	System.out.println("MIraMAchoHastaAqui");
                		ArrayList<Curso> dest = (ArrayList<Curso>) request.getAttribute("buscados");
                    	System.out.println("MIraMAchoHastaAqui2");
                		String h;
                    	System.out.println("MIraMAchoHastaAqui3");
                    	if(dest.size() == 0){%>
                    	<li class="noResultados col-md-12">
                    		<h1>No se han encontrado resultados</h1>
                    	</li>
                    <% }else{
                		for(int i=0; i<dest.size();i++){
                	%>
	                    <li class="portfolio-item col-md-2">
	                        <div class="itemCatalogo">
	                            <img src="images/portfolio/thumb/item3.jpg" alt="">
	                            <h5><%=dest.get(i).getTitulo() %></h5>
	                            <h1 class="precios"><span class="precio"><%=dest.get(i).getPrecio() %>&euro;</span><span class="precioAntiguo">200$</span></h1>
	                            <div class="overlay">
	                                <a class="preview glyphicon glyphicon-heart" href="#" rel="prettyPhoto"><br><span>Deseado</span></a>
	                                <% h=dest.get(i).getTitulo();%>
	                                <a class="preview glyphicon glyphicon-eye-open" href="showCurso.form?titulo=<%=h %>" rel="prettyPhoto"><br><span>Ver</span></a>
	                            </div>           
	                        </div>           
	                    </li>
                    <%
                		}
                		}
                    %>
                    
                </ul>
	           <div class="col-md-7 col-md-offset-4">
	    	        <ul class="pagination">
	                        <li><a href="#">Primero</a></li>
	                        <li><a href="#">&laquo;</a></li>
	                        <li><a href="#">1</a></li>
	                        <li><a href="#">2</a></li>
	                        <li><a href="#">3</a></li>
	                        <li><a href="#">&raquo;</a></li>
	                        <li><a href="#">&Uacute;ltimo</a></li>
	                </ul>
	            </div>
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