package es.rotolearn.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class EscrituraChatRequestHandler implements RequestHandler {

	@Override
	public String handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		InteraccionJMS mq=new InteraccionJMS();

		System.out.println("ESTOY EN EL SERVLET DE ESCRITURA");
		System.out.println("Me escriben esto:"+request.getParameter("mensaje"));
		mq.escrituraJMS(request.getParameter("mensaje"),"AAAAAAAA");
		return "Chat.jsp";
	}

}
