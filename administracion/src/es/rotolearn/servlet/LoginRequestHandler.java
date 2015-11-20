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
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import es.rotolearn.javabean.Registrobean;

public class LoginRequestHandler implements RequestHandler {

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session;
		System.out.println("Handler login received the request");
		//HAY QUE AÑADIR/MODIFICAR PARA METERLE LA BBDD
		String ruta = "admin_index.jsp";
		String nick = request.getParameter("nick");
		String pass = String.valueOf(request.getParameter("pass").hashCode()); //hay que cambiar la contraseña de la base de datos para que se pueda entrar....
		System.out.println("Nick introducido: "+nick);
		System.out.println("Pass introducida: "+pass);
		
				
				/*Insercion a BBDD con DataSource*/
				System.out.println("Vamos a probar a hacer la insercion por DATASOURCE");
				InitialContext miInitialContext;
				DataSource miDS;
				try{
					miInitialContext = new InitialContext();

					miDS = (DataSource) miInitialContext.lookup("RotolearnJNDI");

					Connection conexion = miDS.getConnection();

					Statement myStatement = conexion.createStatement();
					System.out.println("Antes de la query");
					ResultSet rs = myStatement.executeQuery("SELECT * FROM ADMIN WHERE Nickname='"+nick+"'");
					System.out.println("Despues de la query");
					
						//existe el nick, comparar contraseñas
						rs.next();// PONER ESTO ANTES DE HACER NADA SOBRE UNA RESULSET
						System.out.println("ENTRO A COMPARAR CONTRASEÑAS");
						System.out.println("rs.getstrin " + rs.getString(2) + " pass "+pass);
						if(rs.getString("Pass").equals(pass)){//las contraseñas coinciden login valido
							Registrobean regbean = new Registrobean();
							regbean.setNickName(rs.getString("Nickname"));
							regbean.setPass(rs.getString("Pass"));
							regbean.setPrioridad(rs.getInt("Prioridad"));

							//System.out.println("Pass correcta, puede entrar");
							//System.out.println("Paso comparaciond de pass ");
							session = ((HttpServletRequest) request).getSession();
							session.setAttribute("logueado", "true");
							session.setAttribute("usuario",nick);
							session.setAttribute("perfil",regbean);						
							myStatement.close();
							conexion.close();
							
						}else{ 			//FALLO EN LA PASS			
								request.setAttribute("error", "pass");
								ruta = "admin_login.jsp";
							
							 }				
			
						//fin else
				}//fin try
				catch (NamingException e) {
					// TODO Bloque catch generado automaticamente
					e.printStackTrace();
					request.setAttribute("error", "true");
					ruta = "admin_login.jsp";

				} catch (SQLWarning sqlWarning) {
					while (sqlWarning != null) {
						System.out.println("Error: " + sqlWarning.getErrorCode());
						System.out.println("Descripcion: " + sqlWarning.getMessage());
						System.out.println("SQLstate: " + sqlWarning.getSQLState());
						sqlWarning = sqlWarning.getNextWarning();
						request.setAttribute("error", "true");
						ruta = "admin_login.jsp";
					}
				} catch (SQLException sqlException) {
					while (sqlException != null) {
						System.out.println("Error: " + sqlException.getErrorCode());
						System.out.println("Descripcion: " + sqlException.getMessage());
						System.out.println("SQLstate: " + sqlException.getSQLState());
						sqlException = sqlException.getNextException();
						request.setAttribute("error", "true");
						ruta = "admin_login.jsp";
					}
				}

		
	
return ruta;
	}
}
