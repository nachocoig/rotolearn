package es.rotolearn.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;
import java.util.ArrayList;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import es.rotolearn.javabean.Registrobean;
import es.rotolearn.tablas.Curso;

public class AltaCursosRequestHandler implements RequestHandler {

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String ruta="admin_altacursos.jsp";
		
		/*Consulta a la BBDD con DataSource*/
		System.out.println("Vamos a probar a hacer la consulta de cursos por DATASOURCE");
		
		InitialContext miInitialContext;
		DataSource miDS;
		
		ArrayList<Curso> validar = new ArrayList<Curso>();
		
		try{
			miInitialContext = new InitialContext();

			miDS = (DataSource) miInitialContext.lookup("RotolearnJNDI");

			Connection conexion = miDS.getConnection();

			Statement myStatement = conexion.createStatement();
			
			ResultSet validados = myStatement.executeQuery("SELECT * FROM CURSO WHERE Validado = 'NO'");//Hay que cambiar la query

			if(validados!=null){
				while(validados.next()){
					Curso aux = new Curso();
					aux.setTitulo(validados.getString("Titulo"));
					aux.setUsuario((validados.getString("Profesor")));
					aux.setPrecio(validados.getString("Precio"));
					aux.setHoras(validados.getString("Horas"));
					aux.setEmail_paypal(validados.getString("Email_paypal"));
					aux.setDificultad(validados.getString("Dificultad"));
					aux.setDescripcion(validados.getString("Descripcion"));
					aux.setValidado(validados.getString("Validado"));
					aux.setDestacado(validados.getString("Destacado"));
					aux.setCategoria(validados.getString("Categoria"));
					aux.setImagen(validados.getString("Imagen"));
					validar.add(aux);
				}
			}
			System.out.println("SALTA EXCEPCION 1");
			
			validados.close();
			myStatement.close();
			conexion.close();
			
		}catch (NamingException e) {
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
		
		
		request.setAttribute("validados", validar);
		
		return ruta;
	}
}

