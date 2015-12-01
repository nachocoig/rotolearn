package es.rotolearn.servlet;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entities.*;
import es.rotolearn.controlImagenes.MultipartRequest;

public class EditarCursoRequestHandler implements RequestHandler {

	@Override
	public String handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String origen = request.getServletPath();
		String ruta = null;
	 	
	    
	   
	 // 1 Create the factory of Entity Manager
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("ProyectoJPA");//ESTO ES CLAVE

		// 2 Create the Entity Manager
		EntityManager em = factory.createEntityManager();
		
		
		// 3 Get one EntityTransaction
		em.getTransaction().begin();
		try{
			MultipartRequest mr = new MultipartRequest(request);
			if(origen.equals("/editarCurso.form")){
				System.out.print("Entro y la ID es ");
				System.out.println(request.getParameter("ID"));
			 int ID = Integer.parseInt(request.getParameter("ID"));
			System.out.println("Procedemos a actualizar el curso" + ID);
			Curso editar = em.find(Curso.class, ID);
			System.out.println("Hemos encontrado el curso con titulo: " + editar.getTitulo() + " y le vamos a añadir" + mr.getParameterValues("descripcion")[0]);
			editar.setDescripcion(mr.getParameterValues("descripcion")[0]);
			ruta = "administrarCurso.form?ID="+ID;
			request.setAttribute("act", "ok");
			}
			else{
				String datos = request.getParameter("datos");
				 String dat[]=datos.split("-");
				 ProfesorAsociadoPK pk = new ProfesorAsociadoPK();
				 System.out.println("VOY A BORRAR A "+ dat[0] + " del curso " + dat[1]);
				 pk.setID_p(Integer.parseInt(dat[0]));
				 pk.setID_c(Integer.parseInt(dat[1]));
				 
				 if(origen.equals("/eliminarAsociados.form")){
					 ProfesorAsociado editar = em.find(ProfesorAsociado.class,pk);
					 em.remove(editar);
					 request.setAttribute("act", "oke");}
				 else{
					 
					 ProfesorAsociado editar = new ProfesorAsociado();
						 editar.setId(pk);
						 editar.setCurso(em.find(Curso.class,  Integer.parseInt(dat[1])));
						 editar.setUsuario(em.find(Usuario.class, Integer.parseInt(dat[0])));
						 editar.setValidado("NO");
						 em.persist(editar);
						 request.setAttribute("act", "oka");
					 
				 }
				 ruta="administrarCurso.form?ID="+Integer.parseInt(dat[1]);
			}
			
			
				
			
			em.getTransaction().commit();
			//em.close();				   
			}catch(javax.persistence.NoResultException e){ 
				//em.close();
				request.setAttribute("act", "no");
				System.out.println("Descripcion: " + e.getMessage());  
			}	
		em.close();
		System.out.println("VAMOS A" + ruta);
	 return ruta;
	}
		
		
}
