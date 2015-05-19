package com.ssl.jv.gip.web.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.primefaces.util.Constants;

public class MyResponseWrapper extends HttpServletResponseWrapper {

  private final HttpServletRequest request;

  public MyResponseWrapper(HttpServletRequest request, HttpServletResponse response) {
    super(response);
    this.request = request;

  }

  @Override
  public void addCookie(Cookie cookie) {
    if (Constants.DOWNLOAD_COOKIE.equals(cookie.getName())) {
      String refererHeader = request.getHeader("Referer");

      String contextPath = request.getContextPath();
      String requestURI = request.getRequestURI();

      String refererURI = refererHeader.substring(refererHeader.indexOf(contextPath));
      if (!requestURI.equals(refererURI)) {
        String refererPath = refererURI.substring(0, refererURI.lastIndexOf("/"));

        cookie.setPath(refererPath);

      }

    }

    super.addCookie(cookie);
  }
}
