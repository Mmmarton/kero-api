package com.komak.kero.keroapi.auth;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
class AuthenticationEntryPoint extends BasicAuthenticationEntryPoint {

  private final static Logger LOG = LoggerFactory.getLogger("Auth");

  @Override
  public void commence
      (HttpServletRequest request, HttpServletResponse response, AuthenticationException authEx)
      throws IOException {
    response.addHeader("WWW-Authenticate", "Basic realm='" + getRealmName() + "'");
    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
    PrintWriter writer = response.getWriter();
    writer.print("Authentication failed.");
    response.setStatus(403);
    LOG.warn("failed auth: '" + request.getRequestURI() + "', reason: " + authEx.getMessage());
  }

  @Override
  public void afterPropertiesSet() throws Exception {
    setRealmName("kero");
    super.afterPropertiesSet();
  }
}