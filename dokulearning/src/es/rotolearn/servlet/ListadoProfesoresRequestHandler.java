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


public class ListadoProfesoresRequestHandler implements RequestHandler {

	@Override
	public String handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String ruta = "profes_administrarcurso.jsp";
HttpSession miSession = request.getSession(false);
RegistroBean user = (RegistroBean)miSession.getAttribute("perfil");
//1 Create the factory of Entity Manager
EntityManagerFactory factory = Persistence.createEntityManagerFactory("ProyectoJPA");//ESTO ES CLAVE

// 2 Create the Entity Manager
EntityManager em = factory.createEntityManager();

//Cogemos la ID del curso
int ID = Integer.parseInt(request.getParameter("ID"));


// 3 Get one EntityTransaction
em.getTransaction().begin();
//Usuario resultado = em.find(nAux.getClass(), nAux.getNickname());
List <ProfesorAsociado> asociados = null;
List <ProfesorAsociado> asociadosi = null;
List <ProfesorAsociado> asociadono = null;
List <Usuario> profes = null;
ArrayList <Usuario> listaAsociados = new ArrayList <Usuario> ();
ArrayList <Usuario> listaPeticiones = new ArrayList <Usuario> ();
ArrayList <Usuario> listaProfes = new ArrayList <Usuario> ();
		
			System.out.println("HAGO LA PRIMERA QUERY");
			
			asociadosi = em.createQuery("SELECT i FROM ProfesorAsociado i WHERE i.id.ID_c = ?1 AND i.validado='SI'").setParameter(1, ID).getResultList();	
			System.out.println("HAGO LA SEGUNDA QUERY");			
			asociadono = em.createQuery("SELECT i FROM ProfesorAsociado i WHERE i.id.ID_c = ?1 AND i.validado='NO'").setParameter(1, ID).getResultList();	
			System.out.println("HAGO LA TERCERA QUERY");			
			asociados = em.createQuery("SELECT i FROM ProfesorAsociado i WHERE i.id.ID_c = ?1").setParameter(1, ID).getResultList();	
			System.out.println("HAGO LA CUARTA QUERY");	
			profes = em.createQuery("SELECT i FROM Usuario i WHERE i.tipo='profe'").getResultList();	
			System.out.println("SIZES:" + asociadosi.size() +" " + asociadono.size() +" " + asociados.size() +" " + profes.size() +" " );
			//des = em.createQuery("SELECT * FROM CURSO_ALUMNO WHERE ID_u='" + user.getID() + "' AND Estado='lista deseos'").getResultList();
			
			if(asociadosi.size() == 0){
				request.setAttribute("asociados","no");    			
			}
			
			else{
				Iterator<ProfesorAsociado> d = asociadosi.iterator();
				while(d.hasNext()){
					ProfesorAsociado aux=d.next();
					
					try{
						System.out.println("HAGO LA TERCERA QUERY para curso " + aux.getId().getID_c());
						Usuario aux2 = (Usuario) em.createQuery("SELECT i FROM Usuario i WHERE i.id = ?1").setParameter(1, aux.getId().getID_p()).getSingleResult();
						//Curso aux2 = (Curso) em.createQuery("SELECT i FROM Curso WHERE i.id = ?1").setParameter(1, aux.getId().getID_c()).getSingleResult();
						System.out.println("Usuario añadido a asociados:" + aux2.getNickname());
						listaAsociados.add(aux2);
						System.out.println("AÑADIDO, AHORA HAY " + listaAsociados.size() + " ELEMENTOS");
						
					}
					catch (javax.persistence.NoResultException e2){   		
						
						System.out.println("Descripcion: " + e2.getMessage());  
						request.setAttribute("asociados", "no");
					}
	
				}
				System.out.println("NOLLEGO1");
				request.setAttribute("listaAsociados", listaAsociados);
				request.setAttribute("asociados", "si");
				
			}
			if(asociadono.size() == 0){
				request.setAttribute("peticiones","no");    			
			}
			else{
				Iterator<ProfesorAsociado> d = asociadono.iterator();
				while(d.hasNext()){
					ProfesorAsociado aux=d.next();
					
					try{
						System.out.println("HAGO LA TERCERA QUERY para curso " + aux.getId().getID_c());
						Usuario aux2 = (Usuario) em.createQuery("SELECT i FROM Usuario i WHERE i.id = ?1").setParameter(1, aux.getId().getID_p()).getSingleResult();
						//Curso aux2 = (Curso) em.createQuery("SELECT i FROM Curso WHERE i.id = ?1").setParameter(1, aux.getId().getID_c()).getSingleResult();
						System.out.println("Usuario añadido a peticiones:" + aux2.getNickname());
						listaPeticiones.add(aux2);
						System.out.println("AÑADIDO, AHORA HAY " + listaPeticiones.size() + " ELEMENTOS");
						
					}
					catch (javax.persistence.NoResultException e2){   		
						
						System.out.println("Descripcion: " + e2.getMessage());  
						request.setAttribute("peticiones", "no");
					}
	
				}
				System.out.println("NOLLEGO2");
				request.setAttribute("listaPeticiones", listaPeticiones);
				request.setAttribute("peticiones", "si");
				
			}
			if(profes.size() == 0){
				request.setAttribute("profes","no");    			
			}
			else{
				Iterator<Usuario> d = profes.iterator();
				
				while(d.hasNext()){
					Usuario aux=d.next();
					System.out.println("Voy a comparar " + aux.getNickname() + " con " + user.getNickName());
					if(asociados.size() == 0){
						if(aux.getNickname().equals(user.getNickName())){ }
						else{
						listaProfes.add(aux);}
					}
					
					else if(aux.getNickname().equals(user.getNickName())){ }
					else{
						Iterator<ProfesorAsociado> p = asociados.iterator();
						
						while(p.hasNext()){
							ProfesorAsociado aux2=p.next();
							if(aux.getId() != aux2.getId().getID_p() ){
								try{								
									System.out.println("Usuario añadido:" + aux.getNickname());
									listaProfes.add(aux);
									System.out.println("AÑADIDO, AHORA HAY " + listaProfes.size() + " ELEMENTOS");
									
								}
								catch (javax.persistence.NoResultException e2){   		
									
									System.out.println("Descripcion: " + e2.getMessage());  
									request.setAttribute("profes", "no");
								}
							}
							else{}
						}
					}
				}
				System.out.println("NOLLEGO");
				request.setAttribute("listaProfes", listaProfes);
				request.setAttribute("profes", "si");
				
			}
		
em.close();	
return ruta;
}
}