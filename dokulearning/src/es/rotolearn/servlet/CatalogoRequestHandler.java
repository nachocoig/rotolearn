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

import es.rotolearn.tablas.Curso;

public class CatalogoRequestHandler implements RequestHandler {

	@Override
	public String handleRequest(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		String ruta="catalogo.jsp";
		
		HttpSession session = ((HttpServletRequest) request).getSession();
		/*Consulta a la BBDD con DataSource*/
		System.out.println("Vamos a probar a hacer la consulta de cursos por DATASOURCE");
		
		InitialContext miInitialContext;
		DataSource miDS;
		ArrayList<Curso> des = new ArrayList<Curso>();
		ArrayList<Curso> rec = new ArrayList<Curso>();

		String interes="";
		
		try{
			miInitialContext = new InitialContext();

			miDS = (DataSource) miInitialContext.lookup("RotolearnJNDI");

			Connection conexion = miDS.getConnection();

			Statement myStatement = conexion.createStatement();
			
			ResultSet destacados = myStatement.executeQuery("SELECT * FROM CURSO ORDER BY Titulo LIMIT 5");//Hay que cambiar la query

			if(destacados!=null){
				while(destacados.next()){
					Curso aux = new Curso();
					aux.setTitulo(destacados.getString("Titulo"));
					aux.setUsuario((destacados.getString("Profesor")));
					aux.setPrecio(destacados.getString("Precio"));
					aux.setHoras(destacados.getString("Horas"));
					aux.setEmail_paypal(destacados.getString("Email_paypal"));
					aux.setDificultad(destacados.getString("Dificultad"));
					aux.setDescripcion(destacados.getString("Descripcion"));
					aux.setValidado(destacados.getString("Validado"));
					aux.setDestacado(destacados.getString("Destacado"));
					aux.setCategoria(destacados.getString("Categoria"));
					aux.setImagen(destacados.getString("Imagen"));
					des.add(aux);
				}
			}
			System.out.println("VA TODO BIEN DESTACADOS");
			
			destacados.close();
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
		if(session.getAttribute("usuario")!=null){
			try{
				miInitialContext = new InitialContext();
	
				miDS = (DataSource) miInitialContext.lookup("RotolearnJNDI");
	
				Connection conexion = miDS.getConnection();
	
				Statement myStatement = conexion.createStatement();
				
				ResultSet intereses = myStatement.executeQuery("SELECT Intereses FROM USUARIO WHERE Nickname='"+session.getAttribute("usuario")+"'");
	
				if(intereses!=null){
					while(intereses.next()){
						System.out.println("LOS INTERESES SON: "+intereses.getString("Intereses"));
						interes=intereses.getString("Intereses");
					}
				}
				System.out.println("VA TODO BIEN PILLAR INTERESES");
				
				intereses.close();
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
			String query = "";
			if(!interes.equals("")){
				String [] busq = interes.split("/");
				for(int i=0;i<busq.length;i++){
					System.out.println("Elemento "+i+": "+busq[i]);
					if(i!=0)
						query = query+" OR";
					query=query+" Categoria LIKE '%"+busq[i]+"%'";
				}
				
				try{
					miInitialContext = new InitialContext();
		
					miDS = (DataSource) miInitialContext.lookup("RotolearnJNDI");
		
					Connection conexion = miDS.getConnection();
		
					Statement myStatement = conexion.createStatement();
					
					ResultSet recomendados = myStatement.executeQuery("SELECT * FROM CURSO WHERE "+query+"ORDER BY Titulo LIMIT 10");
		
					if(recomendados!=null){
						while(recomendados.next()){
							Curso aux = new Curso();
							aux.setTitulo(recomendados.getString("Titulo"));
							aux.setUsuario((recomendados.getString("Profesor")));
							aux.setPrecio(recomendados.getString("Precio"));
							aux.setHoras(recomendados.getString("Horas"));
							aux.setEmail_paypal(recomendados.getString("Email_paypal"));
							aux.setDificultad(recomendados.getString("Dificultad"));
							aux.setDescripcion(recomendados.getString("Descripcion"));
							aux.setValidado(recomendados.getString("Validado"));
							aux.setDestacado(recomendados.getString("Destacado"));
							aux.setCategoria(recomendados.getString("Categoria"));
							aux.setImagen(recomendados.getString("Imagen"));
							rec.add(aux);
						}
					}
					System.out.println("VA TODO BIEN MOSTRAR RECOMENDADOS");
					
					recomendados.close();
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
			}
			
		}
		
		request.setAttribute("destacados", des);
		request.setAttribute("recomendados", rec);
		
		return ruta;
	}

}
