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
import es.rotolearn.javaBean.RegistroBean;

public class ListarUsuariosRequestHandler implements RequestHandler {

	public String handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String ruta = "profes_administrarcurso.jsp";
HttpSession miSession = request.getSession(false);

//1 Create the factory of Entity Manager
EntityManagerFactory factory = Persistence.createEntityManagerFactory("ProyectoJPA");//ESTO ES CLAVE

// 2 Create the Entity Manager
EntityManager em = factory.createEntityManager();

//Cogemos la ID del curso
int ID = Integer.parseInt(request.getParameter("ID"));


// 3 Get one EntityTransaction
em.getTransaction().begin();
//Usuario resultado = em.find(nAux.getClass(), nAux.getNickname());
List <CursoAlumno> ins = null;
ArrayList <Usuario> listaInscritos = new ArrayList <Usuario> ();
		try{
			System.out.println("HAGO LA SEGUNDA QUERY");
			ins = em.createQuery("SELECT i FROM CursoAlumno i WHERE i.id.ID_c = ?1 AND i.estado='inscrito'").setParameter(1, ID).getResultList();	
			//des = em.createQuery("SELECT * FROM CURSO_ALUMNO WHERE ID_u='" + user.getID() + "' AND Estado='lista deseos'").getResultList();
			System.out.println("HE SALIDO DE LA QUERY DE ID= "+ ID +" CON " + ins.size() +" RESULTADOS");
			if(ins.size() == 0){
				request.setAttribute("usuario","no");    			
			}
			else{
				Iterator<CursoAlumno> d = ins.iterator();
				while(d.hasNext()){
					CursoAlumno aux=d.next();
					System.out.println(aux.getId().getID_c());
					try{
						System.out.println("HAGO LA TERCERA QUERY para curso " + aux.getId().getID_c());
						Usuario aux2 = (Usuario) em.createQuery("SELECT i FROM Usuario i WHERE i.id = ?1").setParameter(1, aux.getId().getID_c()).getSingleResult();
						//Curso aux2 = (Curso) em.createQuery("SELECT i FROM Curso WHERE i.id = ?1").setParameter(1, aux.getId().getID_c()).getSingleResult();
						System.out.println("Usuario añadido:" + aux2.getNickname());
						listaInscritos.add(aux2);
						System.out.println("AÑADIDO, AHORA HAY " + listaInscritos.size() + " ELEMENTOS");
						
					}
					catch (javax.persistence.NoResultException e2){   		
						em.close();
						System.out.println("Descripcion: " + e2.getMessage());  
						request.setAttribute("usuario", "no");
					}
	
				}
				System.out.println("NOLLEGO");
				request.setAttribute("listaInscritos", listaInscritos);
				request.setAttribute("usuario", "si");
				em.close();
			}
		}catch (javax.persistence.NoResultException e){   		
			em.close();
			request.setAttribute("usuario", "no");
			System.out.println("Descripcion: " + e.getMessage());    				   				    			   			
		}
	
return ruta;
}
}
