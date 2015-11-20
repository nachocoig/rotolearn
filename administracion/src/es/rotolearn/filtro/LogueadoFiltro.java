package es.rotolearn.filtro;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@WebFilter("/LogueadoFiltro")
public class LogueadoFiltro implements Filter {

    public LogueadoFiltro() {

    }

	public void destroy() {

	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		System.out.println("Entra al filtro de logueado");

		HttpSession session;
		if(request instanceof HttpServletRequest){
			session = ((HttpServletRequest) request).getSession();
			if(session.getAttribute("logueado")!=null){
				System.out.println("Usuario logueado, puede ver la pagina");
				chain.doFilter(request, response);
			}else{
				System.out.println("Usuario no logueado, no puede ver la pagina");
				RequestDispatcher redireccion = request.getRequestDispatcher("admin_login.jsp");
				redireccion.forward(request, response);
			}
		}else{
			chain.doFilter(request, response);
		}
	}

	
	public void init(FilterConfig fConfig) throws ServletException {

	}

}
