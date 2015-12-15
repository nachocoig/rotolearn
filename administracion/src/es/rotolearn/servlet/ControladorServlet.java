package es.rotolearn.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import es.rotolearn.listener.AsynchConsumer;


@WebServlet("/ControladorServlet")
public class ControladorServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	// Hash table of RequestHandler instances, keyed by request URL
	private Map handlerHash = new HashMap();
       
	// Initialize mappings: not implemented here
	public void init() throws ServletException {
		
		// Listener de pagos
		System.out.println("LecturaAsincrona ON...");
		AsynchConsumer miAc=new AsynchConsumer();
		miAc.lecturaAsynch();
		
		// This will read mapping definitions and populate handlerHash
		handlerHash.put("/login.form", new es.rotolearn.servlet.LoginRequestHandler());
		handlerHash.put("/reg_user.form", new es.rotolearn.servlet.RegistroRequestHandler());
		handlerHash.put("/reg_admin.form", new es.rotolearn.servlet.RegistroAdminRequestHandler());
		handlerHash.put("/logout.form", new es.rotolearn.servlet.LogoutRequestHandler());
		handlerHash.put("/admin_altacursos.form", new es.rotolearn.servlet.AltaCursosRequestHandler());
		//handlerHash.put("/validar.form", new es.rotolearn.servlet.ValidarRequestHandler());
		handlerHash.put("/denegar.form", new es.rotolearn.servlet.DenegarRequestHandler());
		handlerHash.put("/verprofes.form", new es.rotolearn.servlet.MostrarUsuariosRequestHandler());
		handlerHash.put("/veralumn.form", new es.rotolearn.servlet.MostrarUsuariosRequestHandler());
		handlerHash.put("/borrarprofes.form", new es.rotolearn.servlet.QuitarUsuarioRequestHandler());
		handlerHash.put("/borraralumn.form", new es.rotolearn.servlet.QuitarUsuarioRequestHandler());
		handlerHash.put("/listadocursos.form", new es.rotolearn.servlet.ListarCursosRequestHandler());
		handlerHash.put("/listadodestacados.form", new es.rotolearn.servlet.ListarCursosRequestHandler());
		handlerHash.put("/listadovalidados.form", new es.rotolearn.servlet.ListarCursosRequestHandler());
		handlerHash.put("/borrarcurso.form", new es.rotolearn.servlet.EliminarCursoRequestHandler());
		handlerHash.put("/validarcurso.form", new es.rotolearn.servlet.ActualizarCursoRequestHandler());
		handlerHash.put("/denegarcurso.form", new es.rotolearn.servlet.EliminarCursoRequestHandler());
		handlerHash.put("/añadirdestacado.form", new es.rotolearn.servlet.ActualizarCursoRequestHandler());
		handlerHash.put("/quitardestacado.form", new es.rotolearn.servlet.ActualizarCursoRequestHandler());
		handlerHash.put("/descuento.form", new es.rotolearn.servlet.DescuentoRequestHandler());
		handlerHash.put("/mostrarDescuentos.form", new es.rotolearn.servlet.MostrarDescuentoRequestHandler());
		handlerHash.put("/conciliacionEmpresa.form", new es.rotolearn.servlet.ConciliacionEmpresaRequestHandler());
		handlerHash.put("/conciliacionProfesor.form", new es.rotolearn.servlet.ConciliacionProfesorRequestHandler());
		handlerHash.put("/historico.form", new es.rotolearn.servlet.ConciliacionHistoricoRequestHandler());
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

		// Complete. If n0 instance is retrieved redirects to error
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
