package es.rotolearn.servlet;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import entities.Curso;
import entities.CursoAlumno;
import entities.CursoAlumnoPK;
import entities.Usuario;
import es.rotolearn.javabean.Registrobean;

public class QuitarUsuarioRequestHandler implements RequestHandler {

	@Override
	public String handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
			
		 	String ruta = request.getServletPath();
		 	if(ruta.equals("/borraralumn.form")) ruta="veralumn.form";
		 	else ruta="verprofes.form";
	        System.out.println("Procedemos a borrar el usuario");
	        int ID = Integer.parseInt(request.getParameter("ID"));
	     // 1 Create the factory of Entity Manager
			EntityManagerFactory factory = Persistence.createEntityManagerFactory("ProyectoJPA");//ESTO ES CLAVE

			// 2 Create the Entity Manager
			EntityManager em = factory.createEntityManager();
			
			
			// 3 Get one EntityTransaction
			em.getTransaction().begin();
			try{
				Usuario borrar = em.find(Usuario.class, ID);
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
