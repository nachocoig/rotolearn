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

public class PerfilRequestHandler
implements RequestHandler {
    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Lifted jumps to return sites
     */
    public String handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	System.out.println("HOLA ENTRO AQUI SI?");
            String ruta = "perfil.jsp";
            HttpSession miSession = request.getSession(false);
           /* RegistroBean user = (RegistroBean)miSession.getAttribute("perfil");
            System.out.println("Vamos a obtener los deseos por DATASOURCE de" + user.getNickName());
            ArrayList<Curso_Alumno> des = new ArrayList<Curso_Alumno>();
            InitialContext miInitialContext;
            DataSource miDS;
            try {
                miInitialContext = new InitialContext();
                miDS = (DataSource)miInitialContext.lookup("RotolearnJNDI");
                Connection conexion = miDS.getConnection();
                Statement myStatement = conexion.createStatement();
                ResultSet listdeseos = myStatement.executeQuery("SELECT * FROM CURSO_ALUMNO WHERE Nickname='" + user.getNickName() + "' AND Estado='lista deseos'");
                if (listdeseos != null) {
                    while (listdeseos.next()) {
                        Curso_Alumno verCurso = new Curso_Alumno();
                        verCurso.setTitulo(listdeseos.getString("Titulo"));
                        des.add(verCurso);
                        System.out.println("tenemos" + verCurso.getTitulo());
                    }
                }
                listdeseos.close();
                myStatement.close();
                conexion.close();
               
            }
            catch (NamingException e) {
        		System.out.println("SALTA EXCEPCION NAMING");
        		e.printStackTrace();
        	

        	} catch (SQLWarning sqlWarning) {
        		while (sqlWarning != null) {
        			System.out.println("SALTA EXCEPCION SQLWARNING");
        			System.out.println("Error: " + sqlWarning.getErrorCode());
        			System.out.println("Descripcion: " + sqlWarning.getMessage());
        			System.out.println("SQLstate: " + sqlWarning.getSQLState());
        			sqlWarning = sqlWarning.getNextWarning();
        		
        		}
        	} catch (SQLException sqlException) {
        		while (sqlException != null) {
        			System.out.println("SALTA EXCEPCION SQLEXCEPTION");
        			System.out.println("Error: " + sqlException.getErrorCode());
        			System.out.println("Descripcion: " + sqlException.getMessage());
        			System.out.println("SQLstate: " + sqlException.getSQLState());
        			sqlException = sqlException.getNextException();
        			
        		}
        	}
        	*/
         //------------------------JPA--------------   
        
        	
    		// 1 Create the factory of Entity Manager
    		EntityManagerFactory factory = Persistence.createEntityManagerFactory("ProyectoJPA");//ESTO ES CLAVE

    		// 2 Create the Entity Manager
    		EntityManager em = factory.createEntityManager();
    		
    		//Creamos el usuario a buscar en la BBDD
    		RegistroBean user = (RegistroBean)miSession.getAttribute("perfil");
    		
    		
    		// 3 Get one EntityTransaction
    		em.getTransaction().begin();
    		//Usuario resultado = em.find(nAux.getClass(), nAux.getNickname());
    		List <CursoAlumno> des = null;
    		ArrayList <Curso> deseo = new ArrayList <Curso> ();
    		try{
    			System.out.println("HAGO LA PRIMERA QUERY de "+ user.getNickName());
    			String nick = user.getNickName();
    			Usuario aux3=(Usuario) em.createQuery("SELECT i FROM Usuario i WHERE i.nickname = ?1").setParameter(1, user.getNickName()).getSingleResult();
    			System.out.println("HE SALIDO DE LA QUERY DE ID= "+ user.getNickName() +" CON " + aux3.getId() +" RESULTADOS");
    				try{
    					System.out.println("HAGO LA SEGUNDA QUERY");
    					des = em.createQuery("SELECT i FROM CursoAlumno i WHERE i.id.ID_u = ?1 AND i.estado='lista deseos'").setParameter(1, aux3.getId()).getResultList();	
    					//des = em.createQuery("SELECT * FROM CURSO_ALUMNO WHERE ID_u='" + user.getID() + "' AND Estado='lista deseos'").getResultList();
    					System.out.println("HE SALIDO DE LA QUERY DE ID= "+ aux3.getId() +" CON " + des.size() +" RESULTADOS");
    					if(des.size() == 0){
    						request.setAttribute("nodeseos","si");    			
    					}
    					else{
    						Iterator<CursoAlumno> d = des.iterator();
    						while(d.hasNext()){
    							CursoAlumno aux=d.next();
    							System.out.println(aux.getId().getID_c());
    							try{
    								System.out.println("HAGO LA TERCERA QUERY para curso " + aux.getId().getID_c());
    								Curso aux2 = (Curso) em.createQuery("SELECT i FROM Curso i WHERE i.id = ?1").setParameter(1, aux.getId().getID_c()).getSingleResult();
    								//Curso aux2 = (Curso) em.createQuery("SELECT i FROM Curso WHERE i.id = ?1").setParameter(1, aux.getId().getID_c()).getSingleResult();
    								System.out.println("Curso añadido:" + aux2.getTitulo());
    								deseo.add(aux2);
    								System.out.println("AÑADIDO, AHORA HAY " + deseo.size() + " ELEMENTOS");
    								
    							}
    							catch (javax.persistence.NoResultException e2){   		
    								em.close();
    								System.out.println("Descripcion: " + e2.getMessage());  
    								request.setAttribute("nodeseos", "si");
    							}
    			
    						}
    						System.out.println("NOLLEGO");
    						request.setAttribute("deseos", deseo);
    						request.setAttribute("nodeseos", "no");
    						System.out.println("Paso al añadir " + ((ArrayList<Curso>) request.getAttribute("deseos")).size() +" elementos");
    						em.close();
    					}
    				}catch (javax.persistence.NoResultException e){   		
    					em.close();
    					request.setAttribute("nodeseos", "si");
    					System.out.println("Descripcion: " + e.getMessage());    				   				    			   			
    				}
    			}catch(javax.persistence.NoResultException e){ 
    				em.close();
    				request.setAttribute("nodeseos", "si");
    				System.out.println("Descripcion: " + e.getMessage());  
    			}	
        return ruta;
    }
}