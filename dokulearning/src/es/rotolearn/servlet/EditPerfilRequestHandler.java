package es.rotolearn.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import es.rotolearn.javaBean.RegistroBean;

public class EditPerfilRequestHandler implements RequestHandler {

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String ruta = "perfil.jsp";
		HttpSession miSession = request.getSession(true);
		
		String Nombre = request.getParameter("nombre");
	    String Apellido1 = request.getParameter("apellido1");
	    String Apellido2 = request.getParameter("apellido2");
	    String Email = request.getParameter("email");
	    String Nacimiento = request.getParameter("date");
	    String Direccion = request.getParameter("direccion");
	    String Descripcion = request.getParameter("descripcion");
	    String Intereses = request.getParameter("intereses");
	    String Telefono = request.getParameter("tlf");
	    

	    RegistroBean actualizado = (RegistroBean) miSession.getAttribute("perfil");

	    
	    actualizado.setNombre(Nombre);
	    actualizado.setApellido1(Apellido1);
	    actualizado.setApellido2(Apellido2);
	    actualizado.setEmail(Email);
	    actualizado.setNacimiento(Nacimiento);
	    actualizado.setDireccion(Direccion);
	    actualizado.setDescripcion(Descripcion);
	    actualizado.setIntereses(Intereses);
	    actualizado.setTelefono(Telefono);
		    
	    request.setAttribute("modificado", "si");
		
		return ruta;
	}

}
