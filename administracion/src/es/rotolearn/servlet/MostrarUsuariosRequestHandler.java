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

import entities.Curso;
import entities.CursoAlumno;
import entities.Usuario;
import es.rotolearn.javabean.Registrobean;


public class MostrarUsuariosRequestHandler implements RequestHandler {

	@Override
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
		ArrayList <Usuario> listaUsuarios = new ArrayList <Usuario> ();
		try{
			List <Usuario> aux = null;
			if(ruta.equals("/veralumn.form")){
				ruta="admin_listadoalumnos.jsp";
			aux=em.createQuery("SELECT i FROM Usuario i WHERE i.tipo='alumn'").getResultList();}
			else{
				aux=em.createQuery("SELECT i FROM Usuario i WHERE i.tipo='profe'").getResultList();
				ruta="admin_listadoprofesores.jsp";}
			if(aux.size() == 0){
						request.setAttribute("usuarios","no");    			
					}
					else{
						request.setAttribute("usuarios","si"); 
						Iterator<Usuario> d = aux.iterator();
						while(d.hasNext()){
							Usuario aux2=d.next();
							listaUsuarios.add(aux2);
							System.out.println("AÑADIDO, AHORA HAY " + listaUsuarios.size() + " ELEMENTOS");								
							}
						}
						System.out.println("NOLLEGO");
						request.setAttribute("listaUsuarios", listaUsuarios);
						em.close();
					
				}catch (javax.persistence.NoResultException e){   		
					em.close();
					request.setAttribute("usuarios", "no");
					System.out.println("Descripcion: " + e.getMessage());    				   				    			   			
				}
			
    return ruta;
	}
}
