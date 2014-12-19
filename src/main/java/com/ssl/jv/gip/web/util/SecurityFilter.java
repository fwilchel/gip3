package com.ssl.jv.gip.web.util;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class SecurityFilter implements Filter{
	
	 private FilterConfig filterConfig = null;

	   public void destroy() {
	      this.filterConfig = null;
	   }

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest req=(HttpServletRequest)request;
        String url=req.getRequestURI();
        if (url.indexOf("javax.faces.resource")!=-1 || url.endsWith("login.jsf")){
        	chain.doFilter(request, response);
        }else{
        	System.out.println(url);
        	chain.doFilter(request, response);
        }
	}
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

		this.filterConfig = filterConfig;
	}

}
