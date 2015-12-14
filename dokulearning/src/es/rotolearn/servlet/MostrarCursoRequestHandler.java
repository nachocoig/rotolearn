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
import es.rotolearn.controlImagenes.MultipartRequest;
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
		System.out.println("Handler MostrarCurso received the request" );
		
		String ruta = request.getContextPath();
		if(request.getAttribute("notif")!= null){
			ruta = "info_curso.jsp";
		}
		else{
			ruta = "Notificacion.form";
			}
		request.setAttribute("tipo", "curso");
		
		HttpSession session = ((HttpServletRequest) request).getSession();
		RegistroBean user = (RegistroBean) session.getAttribute("perfil");
		String Id = request.getParameter("id");
		if (Id == null){
			Id=(String)request.getAttribute("id");
		}
		int id = Integer.parseInt(Id);
		//Conexion con JPA
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("ProyectoJPA");
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		
		//Obtenemos los datos del curso
		try{
			Curso verCurso = em.find(Curso.class, id);
			cargarImagen(verCurso.getImagen(), request, verCurso.getId());
			request.setAttribute("curso", verCurso);
			
			List<Seccion> secciones =  em.createQuery("SELECT i FROM Seccion i WHERE i.curso.id = ?1").setParameter(1, verCurso.getId()).getResultList();
			List<Leccion> lecciones = null;
			List<Leccion> leccionesAux = null;
			List<Material> materiales = null;
			List<Material> materialesAux = null;
			
			ArrayList<Seccion> lsecciones = new ArrayList<Seccion>();
			ArrayList<Leccion> llecciones = new ArrayList<Leccion>();
			ArrayList<Material> lmateriales = new ArrayList<Material>();
			
			for(int i = 0; i < secciones.size(); i++){
				lsecciones.add(secciones.get(i));
				lecciones = em.createQuery("SELECT i FROM Leccion i WHERE i.seccion.id = ?1").setParameter(1, secciones.get(i).getId()).getResultList();
				for(int j = 0; j < lecciones.size(); j++){
					llecciones.add(lecciones.get(j));
					materiales = em.createQuery("SELECT i FROM Material i WHERE i.leccion.id = ?1").setParameter(1, lecciones.get(j).getId()).getResultList();
					for(int k = 0; k < materiales.size();k++){
						lmateriales.add(materiales.get(k));
						cargarFichero(materiales.get(k).getContenido(), request, materiales.get(k).getId(), materiales.get(k).getTipo());
					}
				}

			}
			
			request.setAttribute("secciones", lsecciones);
			request.setAttribute("lecciones", llecciones);
			request.setAttribute("materiales", lmateriales);
			
		}catch(javax.persistence.NoResultException e){
			System.out.println("Descripcion: " + e.getMessage());  
		}
		
		return ruta;
	}

}
