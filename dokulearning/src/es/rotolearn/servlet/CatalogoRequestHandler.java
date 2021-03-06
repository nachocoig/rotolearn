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

public class CatalogoRequestHandler implements RequestHandler {

	public int cargarImagen(byte []img, HttpServletRequest request, int idCurso) throws IOException{
		ServletContext context = request.getServletContext();
	    final String path = context.getRealPath("/images/im_cursos");
	    String rutaCompleta = path + File.separator + idCurso + "_curso.jpg";
		
		    try{
			    FileOutputStream fos = new FileOutputStream(rutaCompleta);
			    fos.write(img);
			    fos.close();
			    return 0;
		    }catch (Exception e){
		    	System.out.println("Error: ");
		    	e.printStackTrace();
		    }
		
		return -1;
	}
	
	@Override
	public String handleRequest(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		String ruta="Notificacion.form";
		
		HttpSession session = ((HttpServletRequest) request).getSession();
		RegistroBean user = (RegistroBean) session.getAttribute("perfil");
		
		/*Consulta a la BBDD con JPA*/
		
		// 1 Create the factory of Entity Manager
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("ProyectoJPA");

		// 2 Create the Entity Manager
		EntityManager em = factory.createEntityManager();
		
		List<Curso> destacados;
		List<Curso> recomendados;
		ArrayList<Curso> des = new ArrayList<Curso>();
		ArrayList<Curso> rec = new ArrayList<Curso>();
		
		// 3 Get one EntityTransaction
		em.getTransaction().begin();
		try{
			//Recojo los 10 primeros cursos destacados ES NORMAL QUE NO SAQUE NADA PORQUE LOS CURSOS LOS DESTACA EL ADMIN Y POR DEFECTO AL CREARLOS SON NO
			destacados = em.createQuery("SELECT i FROM Curso i WHERE i.destacado='SI'").setMaxResults(10).getResultList();	
			if(!destacados.isEmpty()){
				for(int i=0; i<destacados.size();i++){
					cargarImagen(destacados.get(i).getImagen(), request, destacados.get(i).getId());
					des.add(destacados.get(i));
				}
			}
		}catch(Exception e){
			System.out.println("Error: " + e.getMessage());
			
		}
		
		if(session.getAttribute("usuario") != null){
			//Vamos a ver los intereses del usuario
			
			Usuario logueado = (Usuario) em.createQuery("SELECT i FROM Usuario i WHERE i.nickname = ?1").setParameter(1, user.getNickName()).getSingleResult();
			if(logueado.getIntereses()!=null){
				String [] resultado = logueado.getIntereses().split("/");
				String query = "";
				for(int i=0;i<resultado.length;i++){
					if(i==(resultado.length-1)){
						query = query +"i.categoria LIKE '%"+resultado[i]+"%'";

					}else{
						query = query +"i.categoria LIKE '%"+resultado[i]+"%' OR ";
					}
				}
				recomendados = em.createQuery("SELECT i FROM Curso i WHERE ("+query+ ") AND i.validado='SI'").setMaxResults(10).getResultList();	
				if(!recomendados.isEmpty()){
					for(int i=0; i<recomendados.size();i++){
						cargarImagen(recomendados.get(i).getImagen(), request, recomendados.get(i).getId());
						rec.add(recomendados.get(i));
					}
				}
			}
		}
		
		request.setAttribute("destacados", des);
		request.setAttribute("recomendados", rec);
		
		em.close();
		
		request.setAttribute("tipo", "catalogo");
		return ruta;
	}

}
