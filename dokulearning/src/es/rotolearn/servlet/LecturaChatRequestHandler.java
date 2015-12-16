package es.rotolearn.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LecturaChatRequestHandler implements RequestHandler{

	@Override
	public String handleRequest(HttpServletRequest request,	HttpServletResponse response) throws ServletException, IOException {
		
		ReadMessageQueueBrowserServlet lect = new ReadMessageQueueBrowserServlet();
		String strAux="";
		HttpSession session = ((HttpServletRequest) request).getSession();
		strAux=lect.leerbw(session.getAttribute("correlationID").toString());
		request.setAttribute("mensajes", strAux);
		return "Chat.jsp";
	}

}
