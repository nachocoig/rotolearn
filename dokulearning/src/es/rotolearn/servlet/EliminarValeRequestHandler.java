package es.rotolearn.servlet;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entities.Curso;
import entities.Descuento;

public class EliminarValeRequestHandler implements RequestHandler {

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String ruta = "profe_descuentos.form";
	 	
	    System.out.println("Procedemos a borrar el vale");
	    int ID = Integer.parseInt(request.getParameter("ID"));
	    // 1 Create the factory of Entity Manager
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("ProyectoJPA");//ESTO ES CLAVE

		// 2 Create the Entity Manager
		EntityManager em = factory.createEntityManager();
		
		// 3 Get one EntityTransaction
		em.getTransaction().begin();
		try{
			Descuento borrar = em.find(Descuento.class, ID);
			try {
				em.remove(borrar);
				em.getTransaction().commit();
				request.setAttribute("eliminado", "si");
		    } catch (Exception e1) {
				System.out.println("Descripcion: " + e1.getMessage());
				request.setAttribute("eliminado","no");
			}
		}catch(javax.persistence.NoResultException e){ 
			request.setAttribute("eliminado","no");
			System.out.println("Descripcion: " + e.getMessage());  
		}	
		em.close();
		
		return ruta;
	}

}