
package es.rotolearn.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;

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

import entities.Usuario;
import es.rotolearn.javaBean.RegistroBean;

public class RegistroRequestHandler implements RequestHandler {

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String ruta = "login.jsp";

		/*Insercion a BBDD con DataSource
		System.out.println("Vamos a probar a hacer la insercion por DATASOURCE");
		InitialContext miInitialContext;
		DataSource miDS;
		try{
			miInitialContext = new InitialContext();

			miDS = (DataSource) miInitialContext.lookup("RotolearnJNDI");

			Connection conexion = miDS.getConnection();

			Statement myStatement = conexion.createStatement();
		
			String intereses="";
			for(int i=1;i<11;i++){
				if(request.getParameter("intereses"+i)!=null){
					intereses = intereses+request.getParameter("intereses"+i)+"/";
				}
			}
			
			myStatement.executeUpdate("INSERT INTO USUARIO VALUES ('"+request.getParameter("optradio")+"','"+nick+"','"+
			request.getParameter("nombre")+"','"+request.getParameter("apellido1")+"','"+request.getParameter("apellido2")+
			"','"+String.valueOf(request.getParameter("pass").hashCode())+"','"+request.getParameter("date")+"','"+request.getParameter("exampleInputFile")
			+"','"+request.getParameter("email")+"','"+request.getParameter("tlf")+"', '"+
			request.getParameter("direccion")+"', '"+request.getParameter("descripcion")+"', '"+intereses+"')");
			
			
			myStatement.close();
			conexion.close();
			

	
			
		}catch (NamingException e) {
			// TODO Bloque catch generado automaticamente
			e.printStackTrace();
			request.setAttribute("error","reg");
			ruta = "formulario_registro.jsp";

		} catch (SQLWarning sqlWarning) {
			while (sqlWarning != null) {
				System.out.println("Error: " + sqlWarning.getErrorCode());
				System.out.println("Descripcion: " + sqlWarning.getMessage());
				System.out.println("SQLstate: " + sqlWarning.getSQLState());
				sqlWarning = sqlWarning.getNextWarning();
				request.setAttribute("error","reg");
				ruta = "formulario_registro.jsp";
			}
		} catch (SQLException sqlException) {
			while (sqlException != null) {
				System.out.println("Error: " + sqlException.getErrorCode());
				System.out.println("Descripcion: " + sqlException.getMessage());
				System.out.println("SQLstate: " + sqlException.getSQLState());
				sqlException = sqlException.getNextException();
				request.setAttribute("error","reg");
				ruta = "formulario_registro.jsp";
			}
		}

	*/	
		
		/* ESTO NO FUNCIONAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA*/
		
	//////////////////JPA///////////////////////////////////
		
		// 1 Create the factory of Entity Manager
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("ProyectoJPA");//ESTO ES CLAVE

		// 2 Create the Entity Manager
		EntityManager em = factory.createEntityManager();
		
		//Creamos el usuario a buscar en la BBDD
		Usuario nAux = new Usuario();
		nAux.setNickname(request.getParameter("nick"));
		
		// 3 Get one EntityTransaction
		em.getTransaction().begin();
		//Usuario resultado = em.find(nAux.getClass(), nAux.getNickname());
		try{
		Usuario resultado = (Usuario) em.createQuery("SELECT i FROM Usuario i WHERE i.nickname = ?1").setParameter(1, request.getParameter("nick")).getSingleResult();
		request.setAttribute("error","reg");
		ruta = "formulario_registro.jsp";
		}catch (javax.persistence.NoResultException e){
			String intereses="";
			for(int i=1;i<11;i++){
				if(request.getParameter("intereses"+i)!=null){
					intereses = intereses+request.getParameter("intereses"+i)+"/";
				}
			}//uu
			nAux.setNombre(request.getParameter("nombre"));
			nAux.setTipo(request.getParameter("optradio"));
			nAux.setApellido1(request.getParameter("apellido1"));
			nAux.setApellido2(request.getParameter("apellido2"));
			nAux.setEmail(request.getParameter("email"));
			nAux.setPass(String.valueOf(request.getParameter("pass").hashCode()));
			nAux.setFecha_nac(request.getParameter("date"));
			nAux.setDireccion(request.getParameter("direccion"));
			nAux.setDescripcion(request.getParameter("descripcion"));
			nAux.setIntereses(intereses);
			nAux.setTelefono(Integer.parseInt(request.getParameter("tlf")));
			nAux.setImagen(request.getParameter("exampleInputFile"));
			
			try {
				//em = factory.createEntityManager();
				//em.getTransaction().begin();
				em.persist(nAux);
				em.getTransaction().commit();
				//em.close();
		    	} catch (Exception e2) {
				em.close();
				System.out.println("Descripcion: " + e2.getMessage());
				request.setAttribute("error","reg");
				ruta = "formulario_registro.jsp";
			}
			
		}
		
		em.close();
		return ruta;
	}

}