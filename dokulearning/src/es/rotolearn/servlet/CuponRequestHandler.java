package es.rotolearn.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CuponRequestHandler implements RequestHandler {

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String ruta = "profes_listadodescuentos.jsp";
		
		request.getParameter("curso");
		request.getParameter("tipodescuento");
	    request.getParameter("cupon");
	    request.getParameter("descuento");
	    
	    request.setAttribute("cupon", "si");
	    
		return ruta;
	}

}
