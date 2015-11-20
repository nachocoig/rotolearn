package es.rotolearn.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

public class DenegarRequestHandler implements RequestHandler {

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Handler denegar received the request");
		//HAY QUE AÑADIR/MODIFICAR PARA METERLE LA BBDD
		String ruta = "admin_altacursos.form";
		String denegar = request.getParameter("denegar");
				
				/*Insercion a BBDD con DataSource*/
				InitialContext miInitialContext;
				DataSource miDS;
				try{
					miInitialContext = new InitialContext();

					miDS = (DataSource) miInitialContext.lookup("RotolearnJNDI");

					Connection conexion = miDS.getConnection();

					Statement myStatement = conexion.createStatement();
					System.out.println("Antes de la query:    "+denegar);
					int updateOk = myStatement.executeUpdate("DELETE FROM CURSO WHERE Titulo='"+denegar+"'");
					System.out.println("EL UPDATE SE HA EJECUTADO: "+updateOk);
					
					//existe el nick, comparar contraseñas
					myStatement.close();
					conexion.close();				
						//fin else
				}//fin try
				catch (NamingException e) {
					// TODO Bloque catch generado automaticamente
					e.printStackTrace();
					ruta = "admin_altacursos.jsp";

				} catch (SQLWarning sqlWarning) {
					while (sqlWarning != null) {
						System.out.println("SQL WARNINNNNNNNNNNNNNNNNNNNNNNNNNNNG");
						System.out.println("Error: " + sqlWarning.getErrorCode());
						System.out.println("Descripcion: " + sqlWarning.getMessage());
						System.out.println("SQLstate: " + sqlWarning.getSQLState());
						sqlWarning = sqlWarning.getNextWarning();
						request.setAttribute("error", "true");
						ruta = "admin_altacursos.jsp";
					}
				} catch (SQLException sqlException) {
					while (sqlException != null) {

						System.out.println("SQL ERRORRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRR");
						System.out.println("Error: " + sqlException.getErrorCode());
						System.out.println("Descripcion: " + sqlException.getMessage());
						System.out.println("SQLstate: " + sqlException.getSQLState());
						sqlException = sqlException.getNextException();
						ruta = "admin_altacursos.jsp";
					}
				}

		
	
				return ruta;
	}

}
