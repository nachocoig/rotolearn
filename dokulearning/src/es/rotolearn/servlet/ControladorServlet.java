package es.rotolearn.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/ControladorServlet")
public class ControladorServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	// Hash table of RequestHandler instances, keyed by request URL
	private Map<String, RequestHandler> handlerHash = new HashMap<String, RequestHandler>();
	
	// Initialize mappings: not implemented here
	public void init() throws ServletException {

		// This will read mapping definitions and populate handlerHash
		handlerHash.put("/login.form", new es.rotolearn.servlet.LoginRequestHandler());
		handlerHash.put("/reg.form", new es.rotolearn.servlet.RegistroRequestHandler());
		handlerHash.put("/logout.form", new es.rotolearn.servlet.LogoutRequestHandler());
		handlerHash.put("/vale.form", new es.rotolearn.servlet.ValeRequestHandler());
		handlerHash.put("/curso.form", new es.rotolearn.servlet.CursoRequestHandler());
		handlerHash.put("/catalogo.form", new es.rotolearn.servlet.CatalogoRequestHandler());
		handlerHash.put("/showCurso.form", new es.rotolearn.servlet.ShowCursoRequestHandler());
		handlerHash.put("/perfil.form", new es.rotolearn.servlet.PerfilRequestHandler());
        handlerHash.put("/añadirDeseo.form", new es.rotolearn.servlet.AddDeseoRequestHandler());
        handlerHash.put("/busquedaAvanzada.form", new es.rotolearn.servlet.busquedaAvanzadaRequestHandler());
        handlerHash.put("/editarPerfil.form", new es.rotolearn.servlet.EditPerfilRequestHandler());
        handlerHash.put("/profe_descuentos.form", new es.rotolearn.servlet.ProfeDescuentosRequestHandler());
        handlerHash.put("/eliminarVale.form", new es.rotolearn.servlet.EliminarValeRequestHandler());
        handlerHash.put("/verCursosProfe.form", new es.rotolearn.servlet.CursosProfeRequestHandler());
        handlerHash.put("/administrarCurso.form", new es.rotolearn.servlet.ShowCursoRequestHandler());
        handlerHash.put("/eliminarCurso.form", new es.rotolearn.servlet.EliminarRequestHandler());
        handlerHash.put("/editarCurso.form", new es.rotolearn.servlet.EditarCursoRequestHandler());
        handlerHash.put("/mostrarInscritos.form", new es.rotolearn.servlet.ListarUsuariosRequestHandler());
        handlerHash.put("/eliminarInscrito.form", new es.rotolearn.servlet.EliminarRequestHandler());
        handlerHash.put("/listarAsociados.form", new es.rotolearn.servlet.ListadoProfesoresRequestHandler());
        handlerHash.put("/asociarProfe.form", new es.rotolearn.servlet.EditarCursoRequestHandler());
        handlerHash.put("/eliminarAsociados.form", new es.rotolearn.servlet.EditarCursoRequestHandler());
        handlerHash.put("/denegarCurso.form", new es.rotolearn.servlet.EliminarRequestHandler());
        handlerHash.put("/validarPeticion.form", new es.rotolearn.servlet.EditarCursoRequestHandler());
        handlerHash.put("/leerChat.form", new es.rotolearn.servlet.LecturaChatRequestHandler());
        handlerHash.put("/escribirChat.form", new es.rotolearn.servlet.EscrituraChatRequestHandler());
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Complete. Retrieve from the HashMap the instance of the class which
		// implements the logic of the requested url
		System.out.println("RUTA DE LA QUE ME LLAMAN "+request.getServletPath());
		RequestHandler rh = (RequestHandler) handlerHash.get(request.getServletPath());

		System.out.println("Servlet received the request");

		// Complete. If no instance is retrieved redirects to error
		if (rh == null) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);

		} else {

			System.out.println("Servlet passed the request to a handler");

			// Complete. Call the method handleRequsest of the instance in order
			// to obtain the url
			String viewURL = rh.handleRequest(request, response);

			System.out.println("Servlet sent the response");

			// Complete. Dispatch the request to the url obtained
			if (viewURL != null) {
				request.getRequestDispatcher(viewURL).forward(request, response);
			}
		}
	}

}
