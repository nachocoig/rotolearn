package es.rotolearn.servlet;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entities.*;

public class EliminarCursoRequestHandler implements RequestHandler {

public String handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	String ruta = "verCursosProfe.form";
 	
    System.out.println("Procedemos a borrar el curso");
    int ID = Integer.parseInt(request.getParameter("ID"));
 // 1 Create the factory of Entity Manager
	EntityManagerFactory factory = Persistence.createEntityManagerFactory("ProyectoJPA");//ESTO ES CLAVE

	// 2 Create the Entity Manager
	EntityManager em = factory.createEntityManager();
	
	
	// 3 Get one EntityTransaction
	em.getTransaction().begin();
	try{
		Curso borrar = em.find(Curso.class, ID);
				try {
					em.remove(borrar);
					em.getTransaction().commit();
					request.setAttribute("borrado", "ok");
					//em.close();
			    	} catch (Exception e2) {
					System.out.println("Descripcion: " + e2.getMessage());
					request.setAttribute("borrado","no");
					
				}
			
		}catch(javax.persistence.NoResultException e){ 
			//em.close();
			request.setAttribute("borrado", "no");
			System.out.println("Descripcion: " + e.getMessage());  
		}	
	em.close();
	System.out.println("VAMOS A" + ruta);
 return ruta;
}
}
