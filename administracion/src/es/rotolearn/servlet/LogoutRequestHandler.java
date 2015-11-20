package es.rotolearn.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LogoutRequestHandler implements RequestHandler {

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String ruta = "admin_login.jsp";
		HttpSession miSession = ((HttpServletRequest) request).getSession(false);
		System.out.println("Entro al logout");
		
		if(miSession!=null){
			miSession.invalidate();
			request.setAttribute("error", "logout");
		}
		
		return ruta;
	}

}
