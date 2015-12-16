
package es.rotolearn.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entities.Usuario;
import es.rotolearn.controlImagenes.*;

@MultipartConfig(location="/tmp")
public class RegistroRequestHandler implements RequestHandler {

	public byte []obtenerFicheroBytes(HttpServletRequest request, UploadedFile foto, String nick) throws ServletException, IOException {
		
		ServletContext context = request.getServletContext();
	    final String path = context.getRealPath("/images/im_usuarios");
	    byte ficheroTotal[] = null;
	    
	   

	    //final String fileName = foto.getFileName();

	    OutputStream out = null;
	    InputStream filecontent = null;
	    File file = null;
        FileInputStream fis = null;
        
	    try {
	        
	    	out = new FileOutputStream(new File(path + File.separator + nick + "_perfil.jpg"));
	        out.write(foto.getData());

			file = new File(path + File.separator + nick + "_perfil.jpg");
	        fis = new FileInputStream(file);
			
	        
	        ficheroTotal = new byte[(int)file.length()];
			fis.read(ficheroTotal);
			
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
		String ruta = "login.jsp";
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("ProyectoJPA");//ESTO ES CLAVE
		EntityManager em = factory.createEntityManager();
		Usuario nAux = new Usuario();
		nAux.setNickname(request.getParameter("nick"));
		em.getTransaction().begin();
		
		try{
			Usuario resultado = (Usuario) em.createQuery("SELECT i FROM Usuario i WHERE i.nickname = ?1").setParameter(1, request.getParameter("nick")).getSingleResult();
			request.setAttribute("error","reg");
			ruta = "formulario_registro.jsp";
		}catch (javax.persistence.NoResultException e){
			
			String intereses="";
			MultipartRequest mr = new MultipartRequest(request);
			String [] it = mr.getParameterValues("intereses");
			if(it!=null){
				for(int i=0;i<it.length;i++){
					
					if(it[i]!=null){
						intereses = intereses + it[i] +"/";
					}
				}
			}
			
			
			
			nAux.setNickname(mr.getParameterValues("nick")[0]);
			nAux.setNombre(mr.getParameterValues("nombre")[0]);
			nAux.setTipo(mr.getParameterValues("optradio")[0]);
			nAux.setApellido1(mr.getParameterValues("apellido1")[0]);
			nAux.setApellido2(mr.getParameterValues("apellido2")[0]);
			nAux.setEmail(mr.getParameterValues("email")[0]);
			nAux.setPass(String.valueOf(mr.getParameterValues("pass")[0].hashCode()));
			nAux.setFecha_nac(mr.getParameterValues("date")[0]);
			nAux.setDireccion(mr.getParameterValues("direccion")[0]);
			nAux.setDescripcion(mr.getParameterValues("descripcion")[0]);
			nAux.setIntereses(intereses);
			nAux.setTelefono(Integer.parseInt(mr.getParameterValues("tlf")[0]));
		    
			UploadedFile foto = (UploadedFile) mr.getUploadedFile("file");			
			nAux.setImagen(obtenerFicheroBytes(request, foto, mr.getParameterValues("nick")[0] ));
			
			
			try{
				em.persist(nAux);
				em.getTransaction().commit();
		    	} catch (Exception e2) {
				em.close();
				System.out.println("Descripcion: " + e2.getMessage());
				request.setAttribute("error","reg");
				ruta = "formulario_registro.jsp";
			}
			
		}
		
		em.close();
		return ruta;
	}

}