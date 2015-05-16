package com.ssl.jv.gip.web.util;

import java.io.IOException;

import javax.faces.application.ViewExpiredException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.ssl.jv.gip.web.mb.AplicacionMB;
import com.ssl.jv.gip.web.mb.MenuMB;

public class SecurityFilter implements Filter {

	private FilterConfig filterConfig = null;

	public static final Logger LOGGER = Logger.getLogger(SecurityFilter.class);

	public void destroy() {
		this.filterConfig = null;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		try {
			MyResponseWrapper responseWrapper = new MyResponseWrapper(req, res);
			AplicacionMB appMB = (AplicacionMB) this.filterConfig
					.getServletContext().getAttribute("aplicacionMB");
			String url = req.getRequestURI();
			if (url.indexOf("javax.faces.resource") != -1
					|| url.endsWith("login.jsf")
					|| url.endsWith("recordarContrasena.jsf")
					|| url.endsWith("cambiarContrasena.jsf")
					|| (appMB != null && appMB.getDebug())
					|| url.endsWith("generadorReportes")
					|| url.endsWith("listaReportes.jsp")
					|| url.endsWith("compilarReporte.jsp")) {

				chain.doFilter(request, responseWrapper);
			} else {
				LOGGER.debug(url);
				HttpSession session = req.getSession(false);
				if (session == null
						|| session.getAttribute("menuMB") == null
						|| ((MenuMB) session.getAttribute("menuMB"))
								.getOpciones() == null) {
					if (session != null) {
						session.removeAttribute("menuMB");
						session.invalidate();
					}
					session = req.getSession(true);
					request.setAttribute(
							"exception",
							new Exception(AplicacionMB.getMessage(
									"sesionCaducado", AplicacionMB.SPANISH)));
					this.filterConfig
							.getServletContext()
							.getRequestDispatcher(
									"/ui/login.jsf;jsessionid="
											+ session.getId())
							.forward(request, response);
				} else {
					// TODO : Validar permiso a la opción de menú
					chain.doFilter(request, responseWrapper);
				}
			}
		} catch (ViewExpiredException g) {
			HttpSession session = req.getSession(false);
			if (session != null) {
				session.invalidate();
			}
			session = req.getSession(true);
			res.sendRedirect("/ui/login.jsf;jsessionid=" + session.getId());
		}
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
	}

}
