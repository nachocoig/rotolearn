package es.rotolearn.servlet;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import entities.Usuario;
import es.rotolearn.controlImagenes.MultipartRequest;
import es.rotolearn.javaBean.RegistroBean;

public class EditPerfilRequestHandler implements RequestHandler {

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("al menos entramos aqui...");
		String ruta = "perfil.form";
		HttpSession miSession = request.getSession(true);
		String intereses = "";
		MultipartRequest mr = new MultipartRequest(request);
		String [] it = mr.getParameterValues("intereses");
		if(it!=null){
			for(int i=0;i<it.length;i++){
				System.out.println("Elemento "+ i + ": " + it[i] );
				if(it[i]!=null){
					intereses = intereses + it[i] +"/";
				}
			}
		}
		String Nombre = request.getParameter("nombre");
	    String Apellido1 = request.getParameter("apellido1");
	    String Apellido2 = request.getParameter("apellido2");
	    String Email = request.getParameter("email");
	    String Nacimiento = request.getParameter("date");
	    String Direccion = request.getParameter("direccion");
	    String Descripcion = request.getParameter("descripcion");
	  
	    int Telefono = Integer.parseInt(request.getParameter("tlf"));

	    RegistroBean actualizado = (RegistroBean) miSession.getAttribute("perfil");

		System.out.println("PO AQUI");
	    actualizado.setNombre(Nombre);
	    actualizado.setApellido1(Apellido1);
	    actualizado.setApellido2(Apellido2);
	    actualizado.setEmail(Email);
	    actualizado.setNacimiento(Nacimiento);
	    actualizado.setDireccion(Direccion);
	    actualizado.setDescripcion(Descripcion);
	    actualizado.setIntereses(intereses);
	    actualizado.setTelefono(Telefono);

		System.out.println("EL BEAN PARECE QUE FUNSIONA NOSE:" + actualizado.getIntereses());
	    try{
			EntityManagerFactory factory = Persistence.createEntityManagerFactory("ProyectoJPA");
			EntityManager em = factory.createEntityManager();
			EntityTransaction tx = em.getTransaction();
			Usuario aux = null;

			System.out.println("deberia de ir hasta aqui");
			try{
				aux = em.find(Usuario.class, actualizado.getId());
				
				tx.begin();//Comenzamos la transaccion

				System.out.println("al menos entramos aqui...2");
				aux.setNombre(Nombre);
				aux.setApellido1(Apellido1);
				aux.setApellido2(Apellido2);
				aux.setEmail(Email);
				aux.setFecha_nac(Nacimiento);
				aux.setDireccion(Direccion);
				aux.setDescripcion(Descripcion);
				aux.setIntereses(intereses); 
				aux.setTelefono(Telefono);

				System.out.println("al menos entramos aqui...3");
				tx.commit();

				System.out.println("Y hasta aqui llega?");
			}catch(Exception x){
				System.out.println("problema al encontrar el usuario con id: "+actualizado.getId());
				x.printStackTrace();
			}
			
	    }catch(Exception e){
	    	
	    }
	    request.setAttribute("modificado", "si");
		
		return ruta;
	}

}
