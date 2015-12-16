package es.rotolearn.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import entities.*;

public class ListarCursosRequestHandler implements RequestHandler {
	
	public String handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

	String ruta = request.getServletPath();
    HttpSession miSession = request.getSession(false);
	
	// 1 Create the factory of Entity Manager
	EntityManagerFactory factory = Persistence.createEntityManagerFactory("ProyectoJPA");//ESTO ES CLAVE

	// 2 Create the Entity Manager
	EntityManager em = factory.createEntityManager();
	
	
	// 3 Get one EntityTransaction
	em.getTransaction().begin();
	ArrayList <Curso> listaCursos = new ArrayList <Curso> ();
	try{
		List <Curso> aux = null;
		if(ruta.equals("/listadocursos.form")){
			ruta="admin_listadocursos.jsp";
		aux=em.createQuery("SELECT i FROM Curso i WHERE i.validado= 'SI'").getResultList();}
		else if(ruta.equals("/listadovalidados.form")){
			ruta="admin_altacursos.jsp";
		aux=em.createQuery("SELECT i FROM Curso i WHERE i.validado= 'NO'").getResultList();}
		else {
			aux=em.createQuery("SELECT i FROM Curso i WHERE i.destacado= 'SI'").getResultList();
			ruta="admin_destacadocursos.jsp";}
		
		if(aux.size() == 0){
					request.setAttribute("curso","no");    			
				}
				else{
					request.setAttribute("curso","si"); 
					Iterator<Curso> d = aux.iterator();
					while(d.hasNext()){
						Curso aux2=d.next();
						listaCursos.add(aux2);
						System.out.println("AÑADIDO, AHORA HAY " + listaCursos.size() + " ELEMENTOS");								
						}
					}
					System.out.println("NOLLEGO");
					request.setAttribute("listaCursos", listaCursos);
					em.close();
				
			}catch (javax.persistence.NoResultException e){   		
				em.close();
				request.setAttribute("curso", "no");
				System.out.println("Descripcion: " + e.getMessage());    				   				    			   			
			}
		
return ruta;
}


}
