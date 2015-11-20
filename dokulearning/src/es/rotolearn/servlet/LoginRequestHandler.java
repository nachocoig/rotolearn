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

import es.rotolearn.javaBean.RegistroBean;

public class LoginRequestHandler implements RequestHandler {

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session;
		System.out.println("Handler login received the request");
		//HAY QUE AÑADIR/MODIFICAR PARA METERLE LA BBDD
		String ruta = null;
		String nick = request.getParameter("Nickname");
		String pass = String.valueOf(request.getParameter("Password").hashCode());
		System.out.println("Nick introducido: "+nick);
		System.out.println("Pass introducida: "+pass);
		
		
		//Creamos el usuario a buscar en la BBDD
				
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
					ResultSet rs = myStatement.executeQuery("SELECT * FROM USUARIO WHERE Nickname='"+nick+"'");
					System.out.println("Despues de la query");
					
				if(rs == null){ // No existe el usuario
					System.out.println("rs = = NULL");
					request.setAttribute("error", "true");
					ruta = "login.jsp";
				}else{//existe el nick, comparar contraseñas
						rs.next();// PONER ESTO ANTES DE HACER NADA SOBRE UNA RESULSET
						System.out.println("ENTRO A COMPARAR CONTRASEÑAS");
						System.out.println("rs.getstrin " + rs.getString(6) + " pass "+pass);
						if(rs.getString("Pass").equals(pass)){//las contraseñas coinciden login valido
							RegistroBean regbean = new RegistroBean();
							regbean.setNickName(rs.getString("Nickname"));
							regbean.setApellido1(rs.getString("Apellido1"));
							regbean.setApellido2(rs.getString("Apellido2"));
							regbean.setTipo(rs.getString("Tipo"));
							regbean.setEmail(rs.getString("Email"));
							regbean.setDescripcion(rs.getString("Descripcion"));
							regbean.setIntereses("Esto hay que cambiarlo");//por si no te has dado cuenta, esrto hay que cambiarlo.
							regbean.setTelefono(rs.getString("Telefono"));
							regbean.setNacimiento(rs.getString("Fecha_nac"));
						    regbean.setDireccion(rs.getString("Direccion"));
						    regbean.setNombre(rs.getString("Nombre"));	
							//System.out.println("Pass correcta, puede entrar");
							//System.out.println("Paso comparaciond de pass ");
							session = ((HttpServletRequest) request).getSession();
							session.setAttribute("logueado", "true");
							session.setAttribute("usuario",nick);
							session.setAttribute("perfil",regbean);						
							ruta = "index.jsp";
							myStatement.close();
							conexion.close();
							
						}else{ 			//FALLO EN LA PASS			
								request.setAttribute("error", "pass");
								ruta = "login.jsp";
							
							 }				
			
					}	//fin else
				}//fin try
				catch (NamingException e) {
					// TODO Bloque catch generado automaticamente
					e.printStackTrace();
					request.setAttribute("error", "true");
					ruta = "login.jsp";

				} catch (SQLWarning sqlWarning) {
					while (sqlWarning != null) {
						System.out.println("Error: " + sqlWarning.getErrorCode());
						System.out.println("Descripcion: " + sqlWarning.getMessage());
						System.out.println("SQLstate: " + sqlWarning.getSQLState());
						sqlWarning = sqlWarning.getNextWarning();
						request.setAttribute("error", "true");
						ruta = "login.jsp";
					}
				} catch (SQLException sqlException) {
					while (sqlException != null) {
						System.out.println("Error: " + sqlException.getErrorCode());
						System.out.println("Descripcion: " + sqlException.getMessage());
						System.out.println("SQLstate: " + sqlException.getSQLState());
						sqlException = sqlException.getNextException();
						request.setAttribute("error", "true");
						ruta = "login.jsp";
					}
				}
				
		
			
		
		
		
		
		
		
		/*
			HAGO LA CONSULTA A LA BBDD
			// 1 Create the factory of Entity Manager
			EntityManagerFactory factory = Persistence.createEntityManagerFactory("dokulearning");//ESTO ES CLAVE

			// 2 Create the Entity Manager
			EntityManager em = factory.createEntityManager();

			// 3 Get one EntityTransaction
			EntityTransaction tx = em.getTransaction();

			//Creamos el usuario a buscar en la BBDD
			Usuario aux = new Usuario();
			aux.setNickname(nick);
			aux.setPass(pass);
			
			//PARA BUSCAR EL USUARIO QUE HEMOS RECIBIDO POR PARAMETROS, si devuelve null no existe si devuelve algo es que existe
			tx.begin();//Comenzamos la transaccion
			Usuario resultado = em.find(aux.getClass(), aux.getNickname());
			
			// 5 Close the manager
			
			
			if(resultado == null){
				request.setAttribute("error", "true");
				ruta = "login.jsp";
			}else{
				System.out.println("Comparo users " + resultado.getNickname() + " " + nick);
				System.out.println("Comparo pass " + resultado.getPass() + " " + pass);
				if(resultado.getNickname().equals(nick)){
					
					System.out.println("Paso comparaciond e nicks ");
					
					if(resultado.getPass().equals(pass)){
						RegistroBean regbean = new RegistroBean();
						regbean.setNickName(resultado.getNickname());
						regbean.setApellido1(resultado.getApellido1());
						regbean.setApellido2(resultado.getApellido2());
						regbean.setTipo(resultado.getTipo());
						regbean.setEmail(resultado.getEmail());
						regbean.setDescripcion(resultado.getDescripcion());
						regbean.setIntereses("Esto hay que cambiarlo");//por si no te has dado cuenta, esrto hay que cambiarlo.
						regbean.setTelefono(resultado.getTelefono());
						regbean.setNacimiento("05/07/1994");//esto hay que cambiarlo cuando la BBDD tenga varchar en fecha
					    regbean.setDireccion(resultado.getDireccion());
					    regbean.setNombre(resultado.getNombre());	
						//System.out.println("Pass correcta, puede entrar");
						System.out.println("Paso comparaciond de pass ");
						session = ((HttpServletRequest) request).getSession();
						session.setAttribute("logueado", "true");
						session.setAttribute("usuario",nick);
						session.setAttribute("perfil",regbean);						
						ruta = "index.jsp";
						System.out.println("Hago los set atributes ");
						
					}else{
				System.out.println("Pass incorrecta, no puede entrar");
						request.setAttribute("error", "pass");
						ruta = "login.jsp";
					}
					
				}else{
					System.out.println("Login incorrecto usuario no valido");
					request.setAttribute("error", "user");
					ruta = "login.jsp";
				}
				
				em.close();
				System.out.println("C L O S E ");
			}
		
	*/
		
	
return ruta;
	}
}