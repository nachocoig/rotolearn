package es.rotolearn.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import entities.*;

public class MostrarDescuentoRequestHandler implements RequestHandler {

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String ruta = "admin_listadocupones.jsp";
		
		HttpSession session = ((HttpServletRequest) request).getSession();
		
		/*Consulta a la BBDD con JPA*/
		
		// 1 Create the factory of Entity Manager
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("ProyectoJPA");

		// 2 Create the Entity Manager
		EntityManager em = factory.createEntityManager();
		
		List<Curso> cursosProfe;
		ArrayList<Curso> curso = new ArrayList<Curso>();
		
		List<Promocion> promos;
		ArrayList<Promocion> descuentos = new ArrayList<Promocion>();
		
		// 3 Get one EntityTransaction
		em.getTransaction().begin();
		
		try{
			//Recojo los cursos del profesor logueado			
			cursosProfe = em.createQuery("SELECT i FROM Curso i WHERE i.validado='SI'").getResultList();	
			if(!cursosProfe.isEmpty()){
				
				for(int i=0; i<cursosProfe.size();i++){
					curso.add(cursosProfe.get(i));
				}
			}else{
				System.out.println("Ha encontrao cero cursos ");
			}
		}catch(Exception e){
			System.out.println("excepcion al pillar los cursos");
			e.printStackTrace();
		}
		
		try{
			//Recojo los descuentos existentes			
			promos = em.createQuery("SELECT i FROM Promocion i").getResultList();
			
			if(!promos.isEmpty()){
				
				for(int i=0; i<promos.size();i++){
					descuentos.add(promos.get(i));
				}
			}else{
				System.out.println("pilla cero descuentos");
			}
		}catch(Exception e){
			System.out.println("excepcion al pillar los descuentos");
			e.printStackTrace();
		}
		
		em.close();
		
		return ruta;
	}

}