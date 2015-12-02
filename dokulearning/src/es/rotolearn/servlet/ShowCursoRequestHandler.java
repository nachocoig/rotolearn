package es.rotolearn.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;
import java.util.ArrayList;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import entities.Curso;
import es.rotolearn.javaBean.RegistroBean;

public class ShowCursoRequestHandler implements RequestHandler {

	public int cargarImagen(byte []img, HttpServletRequest request, int idCurso) throws IOException{
		ServletContext context = request.getServletContext();
	    final String path = context.getRealPath("/images/im_cursos");
	    String rutaCompleta = path + File.separator + idCurso + "_curso.jpg";
		//File fichero = new File(rutaCompleta);
		//if(!fichero.exists()){
			//fichero.delete();
		    try{
			    FileOutputStream fos = new FileOutputStream(rutaCompleta);
			    fos.write(img);
			    fos.close();
			    System.out.println("Pues se supone que la imagen deberia estar cargada...");
			    return 0;
		    }catch (Exception e){
		    	System.out.println("Error al cargar la imagen de usuario");
		    	e.printStackTrace();
		    }
		//}else{
		//	fichero.delete();
		//	System.out.println("Ya existe? WTF?");
		//	System.out.println("Se supone que existe '"+idCurso+"_curso.jpg' en "+rutaCompleta);
		//}
		System.out.println("termino de cargar la imagen, por donde no debo");
		return -1;
	}
	
	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		/*
		String ruta = request.getServletPath();
		
		String titulo = request.getParameter("titulo");
		
		/*OBTENEMOS DE LA BBDD TODOS LOS DATOS DEL CURSO
		/*Consulta a la BBDD con DataSource
		System.out.println("Vamos a obtener los datos del curso por DATASOURCE");
		
		InitialContext miInitialContext;
		DataSource miDS;
		Curso verCurso = new Curso();
		
		try{
			miInitialContext = new InitialContext();

			miDS = (DataSource) miInitialContext.lookup("RotolearnJNDI");

			Connection conexion = miDS.getConnection();

			Statement myStatement = conexion.createStatement();
			
			ResultSet destacados = myStatement.executeQuery("SELECT * FROM CURSO WHERE Titulo='"+titulo+"'");//Hay que cambiar la query

			if(destacados!=null){
				while(destacados.next()){
					verCurso.setTitulo(destacados.getString("Titulo"));
					verCurso.setUsuario((destacados.getString("Profesor")));
					verCurso.setPrecio(destacados.getString("Precio"));
					verCurso.setHoras(destacados.getString("Horas"));
					verCurso.setEmail_paypal(destacados.getString("Email_paypal"));
					verCurso.setDificultad(destacados.getString("Dificultad"));
					verCurso.setDescripcion(destacados.getString("Descripcion"));
					verCurso.setValidado(destacados.getString("Validado"));
					verCurso.setDestacado(destacados.getString("Destacado"));
					verCurso.setCategoria(destacados.getString("Categoria"));
					verCurso.setImagen(destacados.getString("Imagen"));
				}
			}
			System.out.println("SALTA EXCEPCION 1");
			
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
		*/
		
		HttpSession session = ((HttpServletRequest) request).getSession();
		RegistroBean user = (RegistroBean) session.getAttribute("perfil");
		
		String ruta = request.getServletPath();
	 	
	    System.out.println("Procedemos a ver el curso");
	    
	    int id = Integer.parseInt(request.getParameter("id"));
	    
	    System.out.println("Me da a mi que peta aqui, llamame loco");
	    EntityManagerFactory factory = Persistence.createEntityManagerFactory("ProyectoJPA");//ESTO ES CLAVE
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		
		try{
			Curso verCurso = em.find(Curso.class, id);
			cargarImagen(verCurso.getImagen(), request, verCurso.getId());
			request.setAttribute("curso", verCurso);
		}catch(javax.persistence.NoResultException e){ 
			System.out.println("Descripcion: " + e.getMessage());  
		}
		
		if(ruta.equals("/showCurso.form")){
			ruta = "info_curso.jsp";
		}
		else{
			ruta = "mostrarInscritos.form";
		}
		em.close();
		System.out.println("Me piro aparentemente con toda la caca");
		return ruta;
	}

}
