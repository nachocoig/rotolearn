package es.rotolearn.servlet;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entities.*;

public class EliminarRequestHandler implements RequestHandler {

public String handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	String ruta =request.getServletPath(); 
			
 	
    System.out.println("Procedemos a borrar");
                                                             
   
 // 1 Create the factory of Entity Manager
	EntityManagerFactory factory = Persistence.createEntityManagerFactory("ProyectoJPA");//ESTO ES CLAVE

	// 2 Create the Entity Manager
	EntityManager em = factory.createEntityManager();
	
	
	// 3 Get one EntityTransaction
	em.getTransaction().begin();
	try{
		if(ruta.equals("/eliminarCurso.form")){
			int ID = Integer.parseInt(request.getParameter("ID"));   
			Curso borrar = em.find(Curso.class, ID);
			em.remove(borrar);
			ruta="verCursosProfe.form";}
		else{
			 String datos = request.getParameter("datos");
			 String dat[]=datos.split("-");
			 CursoAlumnoPK pk = new CursoAlumnoPK();
			 System.out.println("VOY A BORRAR A "+ dat[0] + " del curso " + dat[1]);
			 pk.setID_u(Integer.parseInt(dat[0]));
			 pk.setID_c(Integer.parseInt(dat[1]));
			 CursoAlumno borrar = em.find(CursoAlumno.class,pk);
			 em.remove(borrar);
			 ruta="administrarCurso.form?ID="+Integer.parseInt(dat[1]);
		}
				try {
					
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
