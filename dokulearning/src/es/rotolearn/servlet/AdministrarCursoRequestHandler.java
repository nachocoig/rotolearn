package es.rotolearn.servlet;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entities.Curso;
import entities.ProfesorAsociado;
import entities.ProfesorAsociadoPK;
import entities.Usuario;
import es.rotolearn.controlImagenes.MultipartRequest;

public class AdministrarCursoRequestHandler implements RequestHandler {

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String ruta = "profes_administrarcurso.jsp";
		
		MultipartRequest mr = new MultipartRequest(request);

		String tipo = mr.getParameterValues("tipo")[0];
		int idCurso = Integer.parseInt(mr.getParameterValues("curso")[0]);
		
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("ProyectoJPA");//ESTO ES CLAVE
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		
		Curso editar = em.find(Curso.class, idCurso);
		
		if(tipo.equals("editarDescripcion")){ //Para modificar la descripcion del curso
			System.out.println("Modificar descripcion.");
			try{
				
				editar.setDescripcion(mr.getParameterValues("descripcion")[0]);
				em.getTransaction().commit();
				request.setAttribute("aviso","SI/Descripci&oacute;n modificada correctamente");
			}catch(Exception e){
				//e.printStackTrace();
				request.setAttribute("aviso","NO/La descripci&oacute;n no ha podido ser modificada");
			}
		}else{
			
		}
		
		Curso a = em.find(Curso.class, idCurso);
		request.setAttribute("curso",a);

		em.close();
	 return ruta;
	}

}
