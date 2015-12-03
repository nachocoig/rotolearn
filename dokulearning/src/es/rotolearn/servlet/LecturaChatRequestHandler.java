package es.rotolearn.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LecturaChatRequestHandler implements RequestHandler{

	@Override
	public String handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		ReadMessageQueueBrowserServlet lect = new ReadMessageQueueBrowserServlet();
		String strAux="";
		
		System.out.println("ESTOY EN EL SERVLET DE Lectura");
			//strAux=mq.lecturaJMS(3, "AAAAAAAA");
		  	//strAux=mq.lecturaBrowser("AAAAAAAA");
			strAux=lect.leerbw();
			System.out.println("LEO esto: "+strAux);
			request.setAttribute("mensajes", strAux);
			return "Chat.jsp";
	}

}
