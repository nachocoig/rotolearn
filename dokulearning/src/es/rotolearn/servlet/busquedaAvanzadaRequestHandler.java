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

import entities.Curso;
import es.rotolearn.javaBean.RegistroBean;

public class busquedaAvanzadaRequestHandler implements RequestHandler {

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
		
		
		System.out.println("Entro al handler de busquedaAvanzada");
		
		ArrayList<String> categorias = new ArrayList<String>();
		String[] palabrasSeparadas = null;
		String query = "";

		String palabra = request.getParameter("palabra");
		String ruta = "Notificacion.form";
		String destacados = request.getParameter("destacados");
		String recomendados = request.getParameter("recomendados");
		
		List<Curso> cursos;
		ArrayList<Curso> cur = new ArrayList<Curso>();

		
		/*Consulta a la BBDD con JPA*/
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("ProyectoJPA");
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		
		
		if(destacados != null && destacados.equals("SI")){	
			try{
				//Recojo los 10 primeros cursos destacados ES NORMAL QUE NO SAQUE NADA PORQUE LOS CURSOS LOS DESTACA EL ADMIN Y POR DEFECTO AL CREARLOS SON NO
				System.out.println("Voy a recojer los curso destacados que tenemos");
				cursos = em.createQuery("SELECT i FROM Curso i WHERE i.destacado='SI'").setMaxResults(20).getResultList();	
				if(!destacados.isEmpty()){
					for(int i=0; i<cursos.size();i++){
						System.out.println("ENTRA A DESTACADOS");
						cargarImagen(cursos.get(i).getImagen(), request, cursos.get(i).getId());
						System.out.println("SALE DE DESTACADOS");
						cur.add(cursos.get(i));
					}
				}
			}catch(Exception e){
				System.out.println("Pilla excepcion nuse porque");
			}
			
			
		}else{
			
			String categoria1 = request.getParameter("cat1");
			String categoria2 = request.getParameter("cat2");
			String categoria3 = request.getParameter("cat3");
			String categoria4 = request.getParameter("cat4");
			String categoria5 = request.getParameter("cat5");
			String categoria6 = request.getParameter("cat6");
			String categoria7 = request.getParameter("cat7");
			String categoria8 = request.getParameter("cat8");
			String categoria9 = request.getParameter("cat9");
			String categoria10 = request.getParameter("cat10");
			
			query = "";
			
			if((palabra != null && !palabra.equals(""))|| categoria1 != null || categoria2 != null || 
		       categoria3 != null || categoria4 != null || categoria5 != null || 
		       categoria6 != null || categoria7 != null || categoria8 != null || 
		       categoria9 != null || categoria10 != null ){
				//query = query+" WHERE ";
				if(palabra != null && !palabra.equals("")){ //buscamos la palabra
					System.out.println("PARALABRA: "+palabra);
					String delimitadores= "[ .,;?!¡¿\'\"\\[\\]]+";
					palabrasSeparadas = palabra.split(delimitadores);
					query = query+" (i.titulo LIKE '%"+palabrasSeparadas[0]+"%'";
					for(int i = 1; i < palabrasSeparadas.length; i++){
						query = query+" OR i.titulo LIKE '%"+palabrasSeparadas[i]+"%'";
					}
					query = query+")";
				}
				if(categoria1 != null || categoria2 != null || 
			       categoria3 != null || categoria4 != null || categoria5 != null || 
			       categoria6 != null || categoria7 != null || categoria8 != null || 
			       categoria9 != null || categoria10 != null){
					if(palabra != null && !palabra.equals(""))
						query = query + " AND ";
					query = query + " (";
					if(categoria1 != null){
						query = query+" i.categoria LIKE '%"+categoria1+"%'";
						categorias.add(categoria1);
					}
					if(categoria2 != null){
						if(categorias.size()>0)
							query = query+" OR";
						query = query+" i.categoria LIKE '%"+categoria2+"%'";
						categorias.add(categoria2);
					}
					if(categoria3 != null){
						if(categorias.size()>0)
							query = query+" OR";
						query = query+" i.categoria LIKE '%"+categoria3+"%'";
						categorias.add(categoria3);
					}
					if(categoria4 != null){
						if(categorias.size()>0)
							query = query+" OR";
						query = query+" i.categoria LIKE '%"+categoria4+"%'";
						categorias.add(categoria4);
					}
					if(categoria5 != null){
						if(categorias.size()>0)
							query = query+" OR";
						query = query+" i.categoria LIKE '%"+categoria5+"%'";
						categorias.add(categoria5);
					}
					if(categoria6 != null){
						if(categorias.size()>0)
							query = query+" OR";
						query = query+" i.categoria LIKE '%"+categoria6+"%'";
						categorias.add(categoria6);
					}
					if(categoria7 != null){
						if(categorias.size()>0)
							query = query+" OR";
						query = query+" i.categoria LIKE '%"+categoria7+"%'";
						categorias.add(categoria7);
					}
					if(categoria8 != null){
						if(categorias.size()>0)
							query = query+" OR";
						query = query+" i.categoria LIKE '%"+categoria8+"%'";
						categorias.add(categoria8);
					}
					if(categoria9 != null){
						if(categorias.size()>0)
							query = query+" OR";
						query = query+" i.categoria LIKE '%"+categoria9+"%'";
						categorias.add(categoria9);
					}
					if(categoria10 != null){
						if(categorias.size()>0)
							query = query+" OR";
						query = query+" i.categoria LIKE '%"+categoria10+"%'";
						categorias.add(categoria10);
					}
					query = query+")";
				}
			}
			if(categorias.size()==0)
				cursos = em.createQuery("SELECT i from Curso i WHERE i.validado='SI'").setMaxResults(20).getResultList();	
			else
				cursos = em.createQuery("SELECT i from Curso i WHERE i.validado='SI' AND "+ query ).setMaxResults(20).getResultList();		
			if(!cursos.isEmpty()){
				for(int i=0; i<cursos.size();i++){
					System.out.println("ENTRA A TODOS");
					cargarImagen(cursos.get(i).getImagen(), request, cursos.get(i).getId());
					System.out.println("SALE DE TODOS");
					cur.add(cursos.get(i));
				}
			}
		}
		System.out.println("Me piro chaval");
		request.setAttribute("tipo", "busqueda");
		request.setAttribute("categorias", categorias);
		request.setAttribute("palabras", palabrasSeparadas);
		request.setAttribute("buscados", cur);
		
		return ruta;
	}

}
