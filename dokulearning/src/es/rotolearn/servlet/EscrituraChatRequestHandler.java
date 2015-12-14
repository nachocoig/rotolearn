package es.rotolearn.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import es.rotolearn.javaBean.RegistroBean;


public class EscrituraChatRequestHandler implements RequestHandler {

	@Override
	public String handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = ((HttpServletRequest) request).getSession();
		String user = (String) session.getAttribute("usuario");
		InteraccionJMS mq=new InteraccionJMS();
/* CUANDO ESTE INTEGRADO EL CHAT HAY QUE PASARLE A ESCRITURA POR PARAMETRO EL ID DEL CURSO EN VEZ DE AAAAAAAA*/

		System.out.println("ESTOY EN EL SERVLET DE ESCRITURA");
		System.out.println("Me escriben esto:"+request.getParameter("mensaje"));
		mq.escrituraJMS(request.getParameter("mensaje"),session.getAttribute("correlationID").toString(),user);
		return "Chat.jsp";
	}

}
