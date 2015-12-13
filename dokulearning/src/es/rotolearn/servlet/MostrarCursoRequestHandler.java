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
import java.util.List;

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

import entities.*;
import es.rotolearn.javaBean.RegistroBean;

public class MostrarCursoRequestHandler implements RequestHandler {

	public int cargarFichero(byte []fichero, HttpServletRequest request, int idMaterial, String tipo) throws IOException{
		ServletContext context = request.getServletContext();
	    final String path = context.getRealPath("/materiales/");
	    
	    String rutaCompleta = path + File.separator + idMaterial + "_mat."+tipo;
		//File fichero = new File(rutaCompleta);
		//if(!fichero.exists()){
			//fichero.delete();
		    try{
			    FileOutputStream fos = new FileOutputStream(rutaCompleta);
			    fos.write(fichero);
			    fos.close();
			    return 0;
		    }catch (Exception e){
		    	System.out.println("Error al cargar el fichero.");
		    	e.printStackTrace();
		    }
		//}else{
		//	fichero.delete();
		//	System.out.println("Ya existe? WTF?");
		//	System.out.println("Se supone que existe '"+idCurso+"_curso.jpg' en "+rutaCompleta);
		//}
		System.out.println("termino de cargar el fichero por donde no debo");
		return -1;
	}
	
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
		System.out.println("Handler MostrarCurso received the request");
		
		String ruta = "info_curso.jsp";
		
		HttpSession session = ((HttpServletRequest) request).getSession();
		RegistroBean user = (RegistroBean) session.getAttribute("perfil");
		int id = Integer.parseInt(request.getParameter("id"));
		
		//Conexion con JPA
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("ProyectoJPA");
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		
		//Obtenemos los datos del curso
		try{
			Curso verCurso = em.find(Curso.class, id);
			cargarImagen(verCurso.getImagen(), request, verCurso.getId());
			request.setAttribute("curso", verCurso);
			
			List<Seccion> secciones =  verCurso.getSeccions();
			List<Leccion> lecciones = null;
			List<Leccion> leccionesAux = null;
			List<Material> materiales = null;
			List<Material> materialesAux = null;
			
			ArrayList<Seccion> lsecciones = new ArrayList<Seccion>();
			ArrayList<Leccion> llecciones = new ArrayList<Leccion>();
			ArrayList<Material> lmateriales = new ArrayList<Material>();
			
			for(int i = 0; i < secciones.size(); i++){
				if(lecciones == null)
					lecciones = secciones.get(i).getLeccions();
				else{
					leccionesAux = secciones.get(i).getLeccions();
					for(int j = 0; j< leccionesAux.size(); j++)
						lecciones.add(leccionesAux.get(j));
				}
				if(leccionesAux != null)
					leccionesAux.clear();
			}
			
			for(int i = 0; i < lecciones.size(); i++){
				if(materiales == null)
					materiales = lecciones.get(i).getMaterials();
				else{
					materialesAux = lecciones.get(i).getMaterials();
					for(int j = 0; j < materialesAux.size(); j++)
						materiales.add(materialesAux.get(j));
				}
				if(materialesAux != null)
					materialesAux.clear();	
			}
			
			for(int i = 0; i < secciones.size(); i++)
				lsecciones.add(secciones.get(i));
			for(int i = 0; i < lecciones.size(); i++)
				llecciones.add(lecciones.get(i));
			for(int i = 0; i < materiales.size();i++){
				lmateriales.add(materiales.get(i));
				if(materiales.get(i).getContenido() == null)
					System.out.println("ES NUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUULLLL");
				else
					cargarFichero(materiales.get(i).getContenido(), request, materiales.get(i).getId(), materiales.get(i).getTipo());
			}
			request.setAttribute("secciones", lsecciones);
			request.setAttribute("lecciones", llecciones);
			request.setAttribute("materiales", lmateriales);
			
		}catch(javax.persistence.NoResultException e){
			System.out.println("Descripcion: " + e.getMessage());  
		}
		
		return ruta;
		/*
		
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
			ruta = "mostrarInscritos.form?ID="+id;
		}
		em.close();
		System.out.println("Me piro aparentemente con toda la caca");
		return ruta;*/
	}

}
