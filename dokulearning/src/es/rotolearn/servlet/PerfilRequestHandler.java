package es.rotolearn.servlet;

import es.rotolearn.javaBean.RegistroBean;
import es.rotolearn.servlet.RequestHandler;
import es.rotolearn.tablas.Curso_Alumno;

import java.io.IOException;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;
import java.util.ArrayList;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

public class PerfilRequestHandler
implements RequestHandler {
    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Lifted jumps to return sites
     */
    public String handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       
            String ruta = "perfil.jsp";
            HttpSession miSession = request.getSession(false);
            RegistroBean user = (RegistroBean)miSession.getAttribute("perfil");
            System.out.println("Vamos a obtener los deseos por DATASOURCE de" + user.getNickName());
            ArrayList<Curso_Alumno> des = new ArrayList<Curso_Alumno>();
            InitialContext miInitialContext;
            DataSource miDS;
            try {
                miInitialContext = new InitialContext();
                miDS = (DataSource)miInitialContext.lookup("RotolearnJNDI");
                Connection conexion = miDS.getConnection();
                Statement myStatement = conexion.createStatement();
                ResultSet listdeseos = myStatement.executeQuery("SELECT * FROM CURSO_ALUMNO WHERE Nickname='" + user.getNickName() + "' AND Estado='lista deseos'");
                if (listdeseos != null) {
                    while (listdeseos.next()) {
                        Curso_Alumno verCurso = new Curso_Alumno();
                        verCurso.setTitulo(listdeseos.getString("Titulo"));
                        des.add(verCurso);
                        System.out.println("tenemos" + verCurso.getTitulo());
                    }
                }
                listdeseos.close();
                myStatement.close();
                conexion.close();
               
            }
            catch (NamingException e) {
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
        request.setAttribute("deseos", des);
        return ruta;
    }
}