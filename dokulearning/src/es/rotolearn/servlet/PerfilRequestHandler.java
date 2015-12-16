package es.rotolearn.servlet;

import java.io.IOException;
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
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import javax.servlet.http.HttpSession;

import entities.*;
import es.rotolearn.javaBean.RegistroBean;

public class PerfilRequestHandler implements RequestHandler {
   
    public String handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
    	///////////////////////////////////////////////////VARIABLES /////////////////////////////////////////////////
    		List <CursoAlumno> des = null;
    		List <CursoAlumno> busq = null;
    		List <Curso> busq2 = null;
    		List <ProfesorAsociado> cursosprofe =null;
    		List <ProfesorAsociado> peticionprofe =null;
    		
    		ArrayList <Curso> deseo = new ArrayList <Curso> ();    	
    		ArrayList <Curso> cursos = new ArrayList <Curso> ();
    		ArrayList <Curso> peticiones = new ArrayList <Curso> ();
    		
            String ruta = "Notificacion.form";
            request.setAttribute("tipo", "perfil");
            HttpSession miSession = request.getSession(false);
           
         //------------------------JPA--------------   
        
        	
    		// 1 Create the factory of Entity Manager
    		EntityManagerFactory factory = Persistence.createEntityManagerFactory("ProyectoJPA");//ESTO ES CLAVE

    		// 2 Create the Entity Manager
    		EntityManager em = factory.createEntityManager();
    		
    		//Creamos el usuario a buscar en la BBDD
    		RegistroBean user = (RegistroBean)miSession.getAttribute("perfil");
    		
    		
    		// 3 Get one EntityTransaction
    		em.getTransaction().begin();
    	  
    		
    	
    		//////////////////////////////////DESEOS//////////////////////////////////
    		
    		    		

			try{
				des = em.createQuery("SELECT i FROM CursoAlumno i WHERE i.id.ID_u = ?1 AND i.estado='lista deseos'").setParameter(1,  user.getId()).getResultList();	
					if(des.size() == 0){
		    			request.setAttribute("nodeseos","si");    			
		    		}
		    		else{
		    			Iterator<CursoAlumno> d = des.iterator();
		    				while(d.hasNext()){
		    					CursoAlumno aux=d.next();
		    					try{
		    						Curso aux2 = (Curso) em.find(Curso.class,aux.getId().getID_c());
		    						deseo.add(aux2);
		    					}
		    					catch (javax.persistence.NoResultException e2){   		
		    						System.out.println("Descripcion: " + e2.getMessage());  
		    						request.setAttribute("nodeseos", "si");
		    					}
		    			
		    				}
		    			request.setAttribute("deseos", deseo);
		    			request.setAttribute("nodeseos", "no"); 						
		    		}
		    	}catch (javax.persistence.NoResultException e){   		
		    					
		    		request.setAttribute("nodeseos", "si");
		    		System.out.println("Descripcion: " + e.getMessage());    				   				    			   			
		    	}
    		
    				
    		
   ////////////////////////////////////////////////LISTADO CURSOS /////////////////////////////////////////////////////////////////
    				
			
			if(user.getTipo().equals("alumn")){
    			try{
        			busq = em.createQuery("SELECT i FROM CursoAlumno i WHERE i.id.ID_u = ?1 AND i.estado='INCOMPLETO'").setParameter(1,  user.getId()).getResultList();	
        			if(busq.size() == 0){
        				request.setAttribute("cursos","no");    			
        			}
        			else{
        				Iterator<CursoAlumno> d = busq.iterator();
        				while(d.hasNext()){
        					CursoAlumno aux=d.next();
        					try{
        						Curso aux2 = (Curso) em.find(Curso.class,aux.getId().getID_c());
        						cursos.add(aux2);
        			   								
        					}
        					catch (javax.persistence.NoResultException e2){   		
        								
        					System.out.println("Descripcion: " + e2.getMessage());  
        					request.setAttribute("cursos", "no");
        					}
        				}
        	
        				request.setAttribute("listadoCursos", cursos);
        				request.setAttribute("cursos", "si");
        				       						
        			}
        		}catch (javax.persistence.NoResultException e){   		
        					
        			request.setAttribute("cursos", "no");
        			System.out.println("Descripcion: " + e.getMessage());    				   				    			   			
        		}
    					
    		}
    		else{
    			try{
    				busq2=em.createQuery("SELECT i FROM Curso i WHERE i.usuario.id= ?1").setParameter(1,user.getId()).getResultList();
    				cursosprofe=em.createQuery("SELECT i FROM ProfesorAsociado i WHERE i.id.ID_p= ?1 AND i.validado='SI'").setParameter(1,user.getId()).getResultList();
    						
    				if(busq2.size() == 0){
    					request.setAttribute("cursos","no");    			
    				}
    				else{
    					request.setAttribute("cursos","si"); 
    					Iterator<Curso> d = busq2.iterator();
    					while(d.hasNext()){
    						Curso aux2=d.next();
    						cursos.add(aux2);
    					}
    				}
    				if(cursosprofe.size() == 0 && busq2.size() == 0){
						request.setAttribute("cursos","no");    			
    				}
    				else{	
    					Iterator<ProfesorAsociado> d = cursosprofe.iterator();
    					while(d.hasNext()){
    						Curso aux2=(Curso) em.find(Curso.class,d.next().getId().getID_c());
    						cursos.add(aux2);
    					}
    					request.setAttribute("cursos","si"); 
    					request.setAttribute("listaCursos", cursos);
					}
    								
    			}catch (javax.persistence.NoResultException e){   		
    				
    				request.setAttribute("cursos", "no");
    				System.out.println("Descripcion: " + e.getMessage());    				   				    			   			
    			}
    		}
    				
    				
    /////////////////////////////////////////////LISTADO PETICIONES///////////////////////////////////////////////////////////////
    		
    		if(user.getTipo().equals("profe")){
    			try{    						
    				peticionprofe=em.createQuery("SELECT i FROM ProfesorAsociado i WHERE i.id.ID_p= ?1 AND i.validado='NO'").setParameter(1,user.getId()).getResultList();    						  						
    				if(peticionprofe.size() == 0){
    					request.setAttribute("peticiones","no");    			
    				}
    				else{
    					request.setAttribute("peticiones","si"); 
    					Iterator<ProfesorAsociado> d = peticionprofe.iterator();
    					while(d.hasNext()){
    						Curso aux2=(Curso) em.find(Curso.class,d.next().getId().getID_c());
    						peticiones.add(aux2);
    					}
    					request.setAttribute("listaPeticiones", peticiones);    									
    				}
    			}catch (javax.persistence.NoResultException e){   		
    				
    				request.setAttribute("peticiones", "no");
    				System.out.println("Descripcion: " + e.getMessage());    				   				    			   			
    			}
    		}
    ///////////////////////////////////////////////////////// LISTADO CURSOS COMPLETADOS //////////////////////////////////////////////////		
    		try{
    			List<CursoAlumno> cursoAlumnoCompletados = null;
    			ArrayList<CursoAlumno> lcursoAlumnoCompletados = new ArrayList<CursoAlumno>();
    			cursoAlumnoCompletados = em.createQuery("SELECT i FROM CursoAlumno i WHERE i.usuario.id= ?1 AND i.estado = 'COMPLETO'").setParameter(1,user.getId()).getResultList();    						  						
    			for(int i = 0; i < cursoAlumnoCompletados.size(); i++)
    				lcursoAlumnoCompletados.add(cursoAlumnoCompletados.get(i));
    			request.setAttribute("cursosCompletados", lcursoAlumnoCompletados);
    		}catch(Exception e){
    			System.out.println("Error en cursos completados.");
    			e.printStackTrace();
    		}
    		em.close();
    		return ruta;
    }
}