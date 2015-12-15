
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
    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Lifted jumps to return sites
     */
    public String handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String ruta = "showCurso.form";
        HttpSession miSession = request.getSession(false);
        RegistroBean user = (RegistroBean)miSession.getAttribute("perfil");
        System.out.println("Creamos el deseo");
        int id = Integer.parseInt(request.getParameter("id"));
        /*InitialContext miInitialContext;
    	ArrayList<Curso_Alumno> des = new ArrayList<Curso_Alumno>();
		DataSource miDS;
        System.out.println("Vamos a probar a hacer la insercion por DATASOURCE");
        try {
        	
            miInitialContext = new InitialContext();
            miDS = (DataSource)miInitialContext.lookup("RotolearnJNDI");
            Connection conexion = miDS.getConnection();
            Statement myStatement = conexion.createStatement();
            HttpSession miSession = request.getSession(false);
            System.out.println(String.valueOf(titulo) + miSession.getAttribute("usuario"));
            myStatement.executeUpdate("INSERT INTO CURSO_ALUMNO VALUES ('" + miSession.getAttribute("usuario") + "','" + titulo + "','lista deseos')");
			myStatement.close();
			conexion.close();
			request.setAttribute("deseo", "ok");
        }
    catch (NamingException e) {
		System.out.println("SALTA EXCEPCION NAMING");
		e.printStackTrace();
		request.setAttribute("deseo", "no");

	} catch (SQLWarning sqlWarning) {
		while (sqlWarning != null) {
			System.out.println("SALTA EXCEPCION SQLWARNING");
			System.out.println("Error: " + sqlWarning.getErrorCode());
			System.out.println("Descripcion: " + sqlWarning.getMessage());
			System.out.println("SQLstate: " + sqlWarning.getSQLState());
			sqlWarning = sqlWarning.getNextWarning();
			request.setAttribute("deseo", "no");
		}
	} catch (SQLException sqlException) {
		while (sqlException != null) {
			System.out.println("SALTA EXCEPCION SQLEXCEPTION");
			System.out.println("Error: " + sqlException.getErrorCode());
			System.out.println("Descripcion: " + sqlException.getMessage());
			System.out.println("SQLstate: " + sqlException.getSQLState());
			sqlException = sqlException.getNextException();
			request.setAttribute("deseo", "no");
		}
	}
       */
        //------------------------JPA--------------   
        
    	
		// 1 Create the factory of Entity Manager
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("ProyectoJPA");//ESTO ES CLAVE

		// 2 Create the Entity Manager
		EntityManager em = factory.createEntityManager();
				
		
		// 3 Get one EntityTransaction
		em.getTransaction().begin();
		//Usuario resultado = em.find(nAux.getClass(), nAux.getNickname());
		try{
			
				System.out.println("HAGO LA PRIMERA QUERY PARA BUSCAR EL CURSO "+ id);
				Curso aux = (Curso)em.find(Curso.class, id); //creo que al tener el ID ya, no haria falta hacer esta consulta.
				System.out.println("HE SALIDO DE LA QUERY DE titulo= "+ id +" CON ID = " + aux.getId());
					try{
						System.out.println("HAGO LA SEGUNDA QUERY");
						Usuario aux2 = (Usuario) em.createQuery("SELECT i FROM Usuario i WHERE i.nickname = ?1 ").setParameter(1, user.getNickName() ).getSingleResult();	
						//des = em.createQuery("SELECT * FROM CURSO_ALUMNO WHERE ID_u='" + user.getID() + "' AND Estado='lista deseos'").getResultList();
						System.out.println("HE SALIDO DE LA QUERY DE Usuario= "+ user.getNickName() +" CON ID=  " + aux2.getId() );
						System.out.println("HAGO LA TERCERA QUERY para añadir ");
						CursoAlumno aux3 = new CursoAlumno();
						CursoAlumnoPK aux4 = new CursoAlumnoPK();
						aux4.setID_c(aux.getId());
						aux4.setID_u(aux2.getId());
						aux3.setId(aux4);
						aux3.setCurso(aux);
						aux3.setUsuario(aux2);
						aux3.setEstado("lista deseos");
						
						try {
							//em = factory.createEntityManager();
							//em.getTransaction().begin();
							em.persist(aux3);
							em.getTransaction().commit();
							request.setAttribute("deseo", "ok");
							//em.close();
					    	} catch (Exception e2) {
					    		System.out.println("TENGO QUE PASAR AQUI-----------");
							//em.close();
							System.out.println("Descripcion: " + e2.getMessage());
							request.setAttribute("deseo","no");
							
						}
					}catch (javax.persistence.NoResultException e){   		
						//em.close();
						request.setAttribute("deseo", "no");
						System.out.println("Descripcion: " + e.getMessage());    				   				    			   			
					}
				
			
			}catch(javax.persistence.NoResultException e){ 
				//em.close();
				request.setAttribute("deseo", "no");
				System.out.println("Descripcion: " + e.getMessage());  
			}	
		
		em.close();
     return ruta;
    }
}