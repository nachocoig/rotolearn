<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
	<link rel="stylesheet" type="text/css" href="./style/chat.css" media="screen"/>
	
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

	<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.3/jquery.min.js"></script>
	<title>Chat</title>
</head>
<body>
	<div id="wrapper">
	    <div id="menu">
	        <p class="welcome"><b></b></p>
	        <div style="clear:both"></div>
	    </div>
	    <% String mensaje;
	    if(request.getAttribute("mensajes")==null){
	    	mensaje = "";
	    	
	    }else mensaje=request.getAttribute("mensajes").toString(); %>
	    <ul id="chatbox">
	    <li><%=mensaje%> </li>
	    </ul>
	     
	    <form name="message" method="post" action="escribirChat.form">
	        <input name="mensaje" type="text" id="usermsg" size="63" />
	        <input name="submitmsg" type="submit"  id="submitmsg" value="Send" />
	     </form>
	    <form name="leer" method="post" action="leerChat.form">
	        <input name="refrescar" type="submit"  id="refrescar" value="Refrescar" />
	    </form>
	</div>

</body>
</html>