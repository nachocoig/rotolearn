package es.rotolearn.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import entities.*;
import es.rotolearn.controlImagenes.MultipartRequest;
import es.rotolearn.controlImagenes.UploadedFile;
import es.rotolearn.javaBean.RegistroBean;

@MultipartConfig(location="/tmp")
public class CrearCursoRequestHandler implements RequestHandler {

	public byte []obtenerFicheroBytes(HttpServletRequest request, UploadedFile foto, int id_creador) throws ServletException, IOException {
		
		ServletContext context = request.getServletContext();
	    final String path = context.getRealPath("/images/im_cursos");
	    byte ficheroTotal[] = null;
	    
	    if(foto == null)
	    	System.out.println("NULLLLLLLLLLLLLLLLLLLLLLL");

	    //final String fileName = foto.getFileName();

	    OutputStream out = null;
	    InputStream filecontent = null;
	    File file = null;
        FileInputStream fis = null;
        
	    try {
	        
	    	out = new FileOutputStream(new File(path + File.separator + id_creador + "_tmp.jpg"));
	        out.write(foto.getData());

			file = new File(path + File.separator + id_creador + "_tmp.jpg");
	        fis = new FileInputStream(file);
			
	        
	        ficheroTotal = new byte[(int)file.length()];
			fis.read(ficheroTotal);
			
			file.delete();
	    } catch (Exception e) {
	        System.out.println("Error al crear la imagen en el servidor. Motivo: ");
	        e.printStackTrace();
	    } finally {
	        if (out != null) 
	            out.close();
	        if (filecontent != null) 
	            filecontent.close();	
			if(fis != null)
				fis.close();
	    }
	    return ficheroTotal;
	}
	
	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String ruta = "profes_crearcurso.jsp";

		HttpSession session;
		session = ((HttpServletRequest) request).getSession();
		RegistroBean user = (RegistroBean) session.getAttribute("perfil");
		
		Usuario uAux = new Usuario();
		Curso aux = new Curso();
		uAux.setId(user.getId());
		
		aux.setUsuario(uAux);

		MultipartRequest mr = new MultipartRequest(request);

		
		aux.setTitulo(mr.getParameterValues("titulo")[0]);
		aux.setPrecio(Integer.parseInt(mr.getParameterValues("precio")[0]));
		aux.setHoras(Integer.parseInt(mr.getParameterValues("horas")[0]));
		aux.setDescripcion(mr.getParameterValues("descripcion")[0]);
		aux.setDificultad(mr.getParameterValues("dificultad")[0]);
		aux.setDestacado("NO");
		aux.setValidado("NO"); 
		aux.setCategoria(mr.getParameterValues("categoria")[0]);
		aux.setEmail_paypal(mr.getParameterValues("paypal")[0]);
		
		UploadedFile foto = (UploadedFile) mr.getUploadedFile("file");
		
		aux.setImagen(obtenerFicheroBytes(request, foto, user.getId()));

		//aux.setImagen(request.getParameter("imagen"));
		
		// 1 Create the factory of Entity Manager
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("ProyectoJPA");

		// 2 Create the Entity Manager
		EntityManager em = factory.createEntityManager();

		// 3 Get one EntityTransaction and start it
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		try{
			em.persist(aux);
			// 4 Commmit the transaction
			tx.commit();
			request.setAttribute("curso", "si");
		}catch(Exception e){
			request.setAttribute("curso", "no");
			e.printStackTrace();
		}

		// 5 Close the manager
		em.close();

		return ruta;
	}

}
