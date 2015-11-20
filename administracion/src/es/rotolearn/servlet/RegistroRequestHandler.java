package es.rotolearn.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

public class RegistroRequestHandler implements RequestHandler {



	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String 	ruta = "admin_reg.jsp";
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
