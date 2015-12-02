//Lista los cursos de un profesor


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
import es.rotolearn.javaBean.RegistroBean;

public class CursosProfeRequestHandler implements RequestHandler {

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			String ruta = "profes_listadocursos.jsp";
    HttpSession miSession = request.getSession(false);
	
	// 1 Create the factory of Entity Manager
	EntityManagerFactory factory = Persistence.createEntityManagerFactory("ProyectoJPA");//ESTO ES CLAVE

	// 2 Create the Entity Manager
	EntityManager em = factory.createEntityManager();
	
	
	// 3 Get one EntityTransaction
	em.getTransaction().begin();
	ArrayList <Curso> listaCursos = new ArrayList <Curso> ();
	try{
		System.out.println("HAGO LA PRIMERA QUERY PARA BUSCAR A TODOS");
		List <Curso> aux = null;
		RegistroBean user = (RegistroBean)miSession.getAttribute("perfil");
		aux=em.createQuery("SELECT i FROM Curso i WHERE i.usuario.id= ?1").setParameter(1,user.getId()).getResultList();
			
		
		System.out.println("HE SALIDO DE LA QUERY CON "+ aux.size() +" RESULTADOS");
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
