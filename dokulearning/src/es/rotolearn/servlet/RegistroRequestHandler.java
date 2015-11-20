
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

import es.rotolearn.javaBean.RegistroBean;

public class RegistroRequestHandler implements RequestHandler {

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String ruta = "login.jsp";
		System.out.println("Creamos el usuario");
		
		//Creamos el usuario a buscar en la BBDD
		String nick = request.getParameter("nick");		
		
		/*Insercion a BBDD con DataSource*/
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

		
		
		
		
	/*//////////////////JPA///////////////////////////////////
		/  LA CONSULTA A LA BBDD
		// 1 Create the factory of Entity Manager
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("dokulearning");//ESTO ES CLAVE

		// 2 Create the Entity Manager
		EntityManager em = factory.createEntityManager();

		// 3 Get one EntityTransaction
		em.getTransaction().begin();

		//Creamos el usuario a buscar en la BBDD
		Usuario newUsuario = new Usuario();
		
		newUsuario.setNickname(request.getParameter("nick"));
		Usuario resultado = em.find(newUsuario.getClass(), newUsuario.getNickname());
		if(resultado == null){ // si no existe el usuario, puedo crearlo
		//
		// Modificar el html para quitar lo de la segunda contraseña y cambiar lo de la imagen por  un textbox
		//
		System.out.println("El getparameter " +  request.getParameter("nick") + "fin nombre");
		System.out.println("voy a meter a este usuario " + newUsuario.getNickname());
		newUsuario.setNombre(request.getParameter("nombre"));
		newUsuario.setTipo(request.getParameter("optradio"));
		newUsuario.setApellido1(request.getParameter("apellido1"));
		newUsuario.setApellido2(request.getParameter("apellido2"));
		newUsuario.setEmail(request.getParameter("email"));
		newUsuario.setPass(String.valueOf(request.getParameter("pass").hashCode()));
		//String.valueOf(request.getParameter("Password").hashCode());
		//newUsuario.setFecha_Nac(request.getParameter("date")); es un string y espera un date
		newUsuario.setDireccion(request.getParameter("direccion"));
		newUsuario.setDescripcion(request.getParameter("descripcion"));
		newUsuario.setIntereses(request.getParameter("intereses"));
		newUsuario.setTelefono(request.getParameter("tlf"));
		newUsuario.setImagen(request.getParameter("exampleInputFile"));
		try {
			em.persist(newUsuario);
			
		} catch (Exception e) {
			System.out.println("Descripcion: " + e.getMessage());
		}
		
		}	else{ 		System.out.println("Existe el usuario pues NO lo creo");
					request.setAttribute("error","reg");
						ruta = "formulario_registro.jsp";
				}
		

		
		em.getTransaction().commit();
		em.close();
	*/
		return ruta;
	}

}