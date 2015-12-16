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
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import entities.Usuario;
import es.rotolearn.controlImagenes.*;
import es.rotolearn.javaBean.RegistroBean;

@MultipartConfig(location = "/tmp")
public class EditPerfilRequestHandler implements RequestHandler {
	public String cargarImagen(byte []img, HttpServletRequest request, String nick) throws IOException{
		ServletContext context = request.getServletContext();
	    final String path = context.getRealPath("/images/im_usuarios");
	    String rutaCompleta = path + File.separator + nick + "_perfil.jpg";
	    try{
		    FileOutputStream fos = new FileOutputStream(rutaCompleta);
		    fos.write(img);
		    fos.close();
	    }catch (Exception e){
	    	System.out.println("Error: " + e.getMessage());
	    }
		return rutaCompleta;
	}
	public byte[] obtenerFicheroBytes(HttpServletRequest request,
			UploadedFile foto, String nick) throws ServletException,
			IOException {

		ServletContext context = request.getServletContext();
		final String path = context.getRealPath("/images/im_usuarios");
		byte ficheroTotal[] = null;

		OutputStream out = null;
		InputStream filecontent = null;
		File file = null;
		FileInputStream fis = null;

		try {

			out = new FileOutputStream(new File(path + File.separator + nick + "_perfil.jpg"));
			out.write(foto.getData());

			file = new File(path + File.separator + nick + "_perfil.jpg");
			fis = new FileInputStream(file);

			ficheroTotal = new byte[(int) file.length()];
			fis.read(ficheroTotal);

		} catch (Exception e) {
			System.out.println("Error al crear la imagen en el servidor. Motivo: ");
			e.printStackTrace();
		} finally {
			if (out != null)
				out.close();
			if (filecontent != null)
				filecontent.close();
			if (fis != null)
				fis.close();
		}

		return ficheroTotal;
	}

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String ruta = "perfil.form";
		HttpSession miSession = request.getSession(true);
		String intereses = "";
		MultipartRequest mr = new MultipartRequest(request);
		String[] it = mr.getParameterValues("intereses");
		if (it != null) {
			for (int i = 0; i < it.length; i++) {
				if (it[i] != null) {
					intereses = intereses + it[i] + "/";
				}
			}
		}
		String Nombre = mr.getParameter("nombre");
		String Apellido1 = mr.getParameter("apellido1");
		String Apellido2 = mr.getParameter("apellido2");
		String Email = mr.getParameter("email");
		String Nacimiento = mr.getParameter("date");
		String Direccion = mr.getParameter("direccion");
		String Descripcion = mr.getParameter("descripcion");

		int Telefono = Integer.parseInt(mr.getParameter("tlf"));

		RegistroBean actualizado = (RegistroBean) miSession.getAttribute("perfil");

		actualizado.setNombre(Nombre);
		actualizado.setApellido1(Apellido1);
		actualizado.setApellido2(Apellido2);
		actualizado.setEmail(Email);
		actualizado.setNacimiento(Nacimiento);
		actualizado.setDireccion(Direccion);
		actualizado.setDescripcion(Descripcion);
		actualizado.setIntereses(intereses);
		actualizado.setTelefono(Telefono);

		try {
			EntityManagerFactory factory = Persistence
					.createEntityManagerFactory("ProyectoJPA");
			EntityManager em = factory.createEntityManager();
			EntityTransaction tx = em.getTransaction();
			Usuario aux = null;

			try {
				aux = em.find(Usuario.class, actualizado.getId());

				tx.begin();// Comenzamos la transaccion

				aux.setNombre(Nombre);
				aux.setApellido1(Apellido1);
				aux.setApellido2(Apellido2);
				aux.setEmail(Email);
				aux.setFecha_nac(Nacimiento);
				aux.setDireccion(Direccion);
				aux.setDescripcion(Descripcion);
				aux.setIntereses(intereses);
				aux.setTelefono(Telefono);
				UploadedFile foto = (UploadedFile) mr.getUploadedFile("file");
				if (!foto.isValid()) {
					actualizado.setImagen(false);
				} else {
					actualizado.setImagen(true);
					byte[] auxiliar = obtenerFicheroBytes(request, foto,
							Integer.toString(actualizado.getId()));
					aux.setImagen(auxiliar);
					cargarImagen(auxiliar,request, actualizado.getNickName());
					actualizado.setImagen(true);
				}

				
				tx.commit();
				em.close();
			} catch (Exception x) {
				System.out.println("Error: ");
				x.printStackTrace();
			}

		} catch (Exception e) {
				System.out.println("Error: "+e.getMessage());
		}
		request.setAttribute("modificado", "si");
	
		return ruta;
	}

}
