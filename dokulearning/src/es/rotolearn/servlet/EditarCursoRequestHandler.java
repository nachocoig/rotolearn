package es.rotolearn.servlet;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entities.Curso;
import es.rotolearn.controlImagenes.MultipartRequest;

public class EditarCursoRequestHandler implements RequestHandler {

	@Override
	public String handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		String ruta = "administrarCurso.form";
	 	
	    
	    int ID = Integer.parseInt(request.getParameter("ID"));
	    System.out.println("Procedemos a actualizar el curso" + ID);
	 // 1 Create the factory of Entity Manager
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("ProyectoJPA");//ESTO ES CLAVE

		// 2 Create the Entity Manager
		EntityManager em = factory.createEntityManager();
		
		
		// 3 Get one EntityTransaction
		em.getTransaction().begin();
		try{
			MultipartRequest mr = new MultipartRequest(request);
			Curso editar = em.find(Curso.class, ID);
			System.out.println("Hemos encontrado el curso con titulo: " + editar.getTitulo() + " y le vamos a añadir" + mr.getParameterValues("descripcion")[0]);
			editar.setDescripcion(mr.getParameterValues("descripcion")[0]);
			
			request.setAttribute("act", "ok");
				
			
			em.getTransaction().commit();
			//em.close();				   
			}catch(javax.persistence.NoResultException e){ 
				//em.close();
				request.setAttribute("act", "no");
				System.out.println("Descripcion: " + e.getMessage());  
			}	
		em.close();
		System.out.println("VAMOS A" + ruta);
	 return ruta;
	}
		
		
}
