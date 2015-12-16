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
import es.rotolearn.javaBean.RegistroBean;

public class ProfeDescuentosRequestHandler implements RequestHandler {

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String ruta = "profes_listadodescuentos.jsp";
		
		HttpSession session = ((HttpServletRequest) request).getSession();
		RegistroBean user = (RegistroBean) session.getAttribute("perfil");
		
		/*Consulta a la BBDD con JPA*/
		
		// 1 Create the factory of Entity Manager
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("ProyectoJPA");

		// 2 Create the Entity Manager
		EntityManager em = factory.createEntityManager();
		
		List<Curso> cursosProfe;
		ArrayList<Curso> curso = new ArrayList<Curso>();
		
		List<Descuento> valesProfe;
		ArrayList<Descuento> descuentos = new ArrayList<Descuento>();
		
		// 3 Get one EntityTransaction
		em.getTransaction().begin();
		
		try{
			//Recojo los cursos del profesor logueado
		
			Usuario aux = new Usuario();
			aux.setId(user.getId());			
			cursosProfe = em.createQuery("SELECT i FROM Curso i WHERE i.usuario = ?1 AND i.validado='SI'").setParameter(1, aux).getResultList();	
			
			if(!cursosProfe.isEmpty()){
				
				for(int i=0; i<cursosProfe.size();i++){
					curso.add(cursosProfe.get(i));
				}
				
			}else{
				
			}
		}catch(Exception e){
			System.out.println("¡Error! Descripcion: ");
			e.printStackTrace();
		}
		
		try{
			//Recojo los descuentos del profesor logueado			
			
			valesProfe = em.createQuery("SELECT i FROM Descuento i WHERE i.profesor = ?1").setParameter(1, user.getNickName()).getResultList();	
			
			if(!valesProfe.isEmpty()){
				
				for(int i=0; i<valesProfe.size();i++){
					descuentos.add(valesProfe.get(i));
				}
				
			}else{
				
			}
		}catch(Exception e){
			System.out.println("¡Error! Descripcion:");
			e.printStackTrace();
		}
		
		em.close();
		request.setAttribute("listaCursos", curso);
		request.setAttribute("listaDescuentos", descuentos);
		
		return ruta;
	}

}
