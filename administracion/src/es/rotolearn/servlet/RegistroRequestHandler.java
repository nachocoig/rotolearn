package es.rotolearn.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import entities.Usuario;

public class RegistroRequestHandler implements RequestHandler {



	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String 	ruta = "admin_reg.jsp";
		
		
		// 1 Create the factory of Entity Manager
				EntityManagerFactory factory = Persistence.createEntityManagerFactory("ProyectoJPA");//ESTO ES CLAVE

				// 2 Create the Entity Manager
				EntityManager em = factory.createEntityManager();
				
				//Creamos el usuario a buscar en la BBDD
				Usuario nAux = new Usuario();
				nAux.setNickname(request.getParameter("nick"));
				
				// 3 Get one EntityTransaction
				em.getTransaction().begin();
				//Usuario resultado = em.find(nAux.getClass(), nAux.getNickname());
				try{
				Usuario resultado = (Usuario) em.createQuery("SELECT i FROM Usuario i WHERE i.nickname = ?1").setParameter(1, request.getParameter("nick")).getSingleResult();
				request.setAttribute("error","reg");
				ruta = "admin_reg.jsp";
				}catch (javax.persistence.NoResultException e){
					String intereses="";
					for(int i=1;i<11;i++){
						if(request.getParameter("intereses"+i)!=null){
							intereses = intereses+request.getParameter("intereses"+i)+"/";
						}
					}//uu
					nAux.setNombre(request.getParameter("nombre"));
					nAux.setTipo(request.getParameter("optradio"));
					nAux.setApellido1(request.getParameter("apellido1"));
					nAux.setApellido2(request.getParameter("apellido2"));
					nAux.setEmail(request.getParameter("email"));
					nAux.setPass(String.valueOf(request.getParameter("pass").hashCode()));
					nAux.setFecha_nac(request.getParameter("date"));
					nAux.setDireccion(request.getParameter("direccion"));
					nAux.setDescripcion(request.getParameter("descripcion"));
					nAux.setIntereses(intereses);
					nAux.setTelefono(Integer.parseInt(request.getParameter("tlf")));
					//nAux.setImagen(request.getParameter("exampleInputFile"));
					
					try {
						//em = factory.createEntityManager();
						//em.getTransaction().begin();
						em.persist(nAux);
						em.getTransaction().commit();
						request.setAttribute("error","ok");
						//em.close();
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
