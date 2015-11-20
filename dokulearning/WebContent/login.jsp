<!DOCTYPE html>
<html lang="en">
	<head>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<meta charset="utf-8">
		<title>Login - RotoLearn</title>
		<meta name="generator" content="Bootply" />
		<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
		<!--[if lt IE 9]>
			<script src="//html5shim.googlecode.com/svn/trunk/html5.js"></script>
		<![endif]-->
		<link href="style/login.css" rel="stylesheet">
	</head>
	<body>
    <!--login modal-->
    <div id="loginModal" class="modal show" tabindex="-1" role="dialog" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h1 class="text-center"><img src="images/logon.png" alt=""></h1>
            </div>
            <div class="modal-body">
            	<form class="form col-md-12 center-block" action="login.form" method="POST">
                    <%
	                  	if(request.getAttribute("error") != null){
	                  		if(request.getAttribute("error").equals("true")){		
	                %>
	                  	<div class="alert alert-danger">
	                  		<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
							<strong>¡Cuidado!</strong> ¡Usuario incorrecto!
						</div>
	                <%
	                  		}else if(request.getAttribute("error").equals("user")){
	                %>
						<div class="alert alert-danger">
							<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
							<strong>¡Cuidado!</strong> ¡Usuario sin permiso!
						</div>
	                <%
	                    	}else if(request.getAttribute("error").equals("pass")){
	                %>
	                  	<div class="alert alert-danger">
	                  		<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
							<strong>¡Cuidado!</strong> ¡Contrase&ntilde;a incorrecta!
						</div>
	                <%
	                    	}else if(request.getAttribute("error").equals("ok")){
	                %>
	                  	<div class="alert alert-success">
	                  		<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
							<strong>Usuario creado con exito</strong>¡Prueba a hacer login!
						</div>	
	                <%		
	                    	}else{
	                %> 		
	                  	<div class="alert alert-success">
	                  		<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
							<strong>Sesion cerrada con exito</strong>
						</div>
	                <%
	                    	}
	                  	}
	                %>
                  <div class="form-group">
                    <input type="text" class="form-control input-lg" name="Nickname" placeholder="Nickname">
                  </div>
                  <div class="form-group">
                    <input type="password" class="form-control input-lg" name="Password" placeholder="Contraseña">
                  </div>
                  <div class="form-group">
                    <button class="btn btn-primary btn-lg btn-block" type=submit>Acceder</button>
                    <span class="pull-right"><a href="formulario_registro.jsp">Registrate</a></span><span><a href="#">&iquest;Necesitas ayuda?</a></span>
                  </div>
                </form>
            </div>
            <div class="modal-footer">
                <div class="col-md-12">
                <a href="index.jsp" class="btn" data-dismiss="modal" aria-hidden="true">Cancelar</a>
      		    </div>	
            </div>
        </div>
      </div>
    </div>
	<!-- script references -->

	</body>
</html>