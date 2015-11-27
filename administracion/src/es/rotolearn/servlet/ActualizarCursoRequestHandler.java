package es.rotolearn.servlet;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entities.Curso;

public class ActualizarCursoRequestHandler implements RequestHandler {

	@Override
	public String handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String ruta = request.getServletPath();
	 	
	    System.out.println("Procedemos a actualizar el curso");
	    int ID = Integer.parseInt(request.getParameter("ID"));
	 // 1 Create the factory of Entity Manager
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("ProyectoJPA");//ESTO ES CLAVE

		// 2 Create the Entity Manager
		EntityManager em = factory.createEntityManager();
		
		
		// 3 Get one EntityTransaction
		em.getTransaction().begin();
		try{
			Curso editar = em.find(Curso.class, ID);
			if (ruta.equals("/añadirdestacado.form")){
			if(editar.getDestacado().equals("SI")){request.setAttribute("act", "ya");}
			else{
			editar.setDestacado("SI");
			request.setAttribute("act", "ok");
			}
			ruta="listadocursos.form";
					}
			else if(ruta.equals("/validarcurso.form")){
				editar.setValidado("SI");
				request.setAttribute("act", "okv");
				ruta="listadovalidados.form";
				
			}else{
				editar.setDestacado("NO");
				request.setAttribute("act", "ok");
				ruta="listadodestacados.form";
			}
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
