
package es.rotolearn.servlet;

import entities.Curso;
import entities.CursoAlumno;
import entities.Usuario;
import es.rotolearn.javaBean.RegistroBean;
import es.rotolearn.servlet.RequestHandler;
import entities.*;

import java.io.IOException;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

public class AddDeseoRequestHandler
implements RequestHandler {
    
    public String handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String ruta = "showCurso.form";
        HttpSession miSession = request.getSession(false);
        RegistroBean user = (RegistroBean)miSession.getAttribute("perfil");
        int id = Integer.parseInt(request.getParameter("id"));
        
        //------------------------JPA--------------   
        
    	
		// 1 Create the factory of Entity Manager
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("ProyectoJPA");//ESTO ES CLAVE

		// 2 Create the Entity Manager
		EntityManager em = factory.createEntityManager();
				
		
		// 3 Get one EntityTransaction
		em.getTransaction().begin();
		try{
			
				Curso aux = (Curso)em.find(Curso.class, id); 
				try{
						Usuario aux2 = (Usuario) em.createQuery("SELECT i FROM Usuario i WHERE i.nickname = ?1 ").setParameter(1, user.getNickName() ).getSingleResult();	
						CursoAlumno aux3 = new CursoAlumno();
						CursoAlumnoPK aux4 = new CursoAlumnoPK();
						aux4.setID_c(aux.getId());
						aux4.setID_u(aux2.getId());
						aux3.setId(aux4);
						aux3.setCurso(aux);
						aux3.setUsuario(aux2);
						aux3.setEstado("lista deseos");
						
						try {
							
							em.persist(aux3);
							em.getTransaction().commit();
							request.setAttribute("deseo", "ok");
							
					    	} catch (Exception e2) {
							
					    		System.out.println("Descripcion: " + e2.getMessage());
					    		request.setAttribute("deseo","no");
							
						}
					}catch (javax.persistence.NoResultException e){   		
						
						request.setAttribute("deseo", "no");
						System.out.println("Descripcion: " + e.getMessage());    				   				    			   			
					}
				
			
			}catch(javax.persistence.NoResultException e){ 
				
				request.setAttribute("deseo", "no");
				System.out.println("Descripcion: " + e.getMessage());  
			}	
		
		em.close();
     return ruta;
    }
}