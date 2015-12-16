package es.rotolearn.servlet;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import entities.CursoAlumno;
import entities.CursoAlumnoPK;
import es.rotolearn.javaBean.RegistroBean;

public class RespuestaExamenRequestHandler implements RequestHandler {

	@Override
	public String handleRequest(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		String ruta = "info_curso.jsp";
		
		String respuesta = request.getParameter("respuesta");
		int curso = Integer.parseInt(request.getParameter("curso"));
		HttpSession miSession = ((HttpServletRequest) request).getSession(false);
		
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("ProyectoJPA");//ESTO ES CLAVE
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		
		if(respuesta != null && !respuesta.trim().equals("")){
			CursoAlumnoPK cual = new CursoAlumnoPK();
			cual.setID_c(curso);
			RegistroBean usuario =(RegistroBean) miSession.getAttribute("perfil");
			cual.setID_u(usuario.getId());
			
			CursoAlumno cursoC =  em.find(CursoAlumno.class, cual);
			cursoC.setRespuesta(respuesta);
			em.getTransaction().commit();
		}
		em.close();
		return ruta;
	}

}
