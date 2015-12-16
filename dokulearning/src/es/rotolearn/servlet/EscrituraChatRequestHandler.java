package es.rotolearn.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import es.rotolearn.javaBean.RegistroBean;


public class EscrituraChatRequestHandler implements RequestHandler {

	@Override
	public String handleRequest(HttpServletRequest request,	HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = ((HttpServletRequest) request).getSession();
		String user = (String) session.getAttribute("usuario");
		InteraccionJMS mq=new InteraccionJMS();

		if(request.getParameter("mensaje") != null && !request.getParameter("mensaje").equals(""))
			mq.escrituraJMS(request.getParameter("mensaje"),session.getAttribute("correlationID").toString(),user);
		return "leerChat.form";
	}

}
