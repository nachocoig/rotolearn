////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////NO            SIRVE///////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


package es.rotolearn.servlet;

import java.io.IOException;
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
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import es.rotolearn.javabean.Registrobean;


public class RegistroAdminRequestHandler implements RequestHandler {
	@Override
	/****************************************************************************************
	*******************************DEBE BORRARSE PERO DE MOMENTO LO DEJAMOS******************
	*****************************************************************************************/
	public String handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String 	ruta = "admin_reg.jsp";		
		//Creamos el usuario a buscar en la BBDD
		String nick = request.getParameter("nick");		
		HttpSession session = ((HttpServletRequest) request).getSession();
		Registrobean bean = new Registrobean();
		bean = (Registrobean) session.getAttribute("perfil");
		int prior = bean.getPrioridad();
		int priorParaElNuevo = prior + 1;
		/*Insercion a BBDD con DataSource*/
		InitialContext miInitialContext;
		DataSource miDS;
		
		
		try{
			miInitialContext = new InitialContext();

			miDS = (DataSource) miInitialContext.lookup("RotolearnJNDI");

			Connection conexion = miDS.getConnection();

			Statement myStatement = conexion.createStatement();
		
				
			myStatement.executeUpdate("INSERT INTO ADMIN VALUES ('"+request.getParameter("nick")+
					"', '"+String.valueOf(request.getParameter("pass").hashCode())+
					"', '"+priorParaElNuevo+"')");
			
			
			myStatement.close();
			conexion.close();
			request.setAttribute("error","ok");
	
			
		}catch (NamingException e) {
			// TODO Bloque catch generado automaticamente
			e.printStackTrace();
			request.setAttribute("error","reg");
			ruta = "admin_reg.jsp";

		} catch (SQLWarning sqlWarning) {
			while (sqlWarning != null) {
				System.out.println("Error: " + sqlWarning.getErrorCode());
				System.out.println("Descripcion: " + sqlWarning.getMessage());
				System.out.println("SQLstate: " + sqlWarning.getSQLState());
				sqlWarning = sqlWarning.getNextWarning();
				request.setAttribute("error","reg");
				ruta = "admin_reg.jsp";
			}
		} catch (SQLException sqlException) {
			while (sqlException != null) {
				System.out.println("Error: " + sqlException.getErrorCode());
				System.out.println("Descripcion: " + sqlException.getMessage());
				System.out.println("SQLstate: " + sqlException.getSQLState());
				sqlException = sqlException.getNextException();
				request.setAttribute("error","reg");
				ruta = "admin_reg.jsp";
			}
		}
		return ruta;
	}
}
