package com.komak.kero.keroapi.error;


import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Based on the helpful answer at http://stackoverflow.com/q/25356781/56285,
 * with error details in response body added.
 *
 * @author Joni Karppinen
 * @since 20.2.2015
 */
@RestController
public class NotFoundErrorController implements ErrorController {

  private static final String PATH = "/error";

  @RequestMapping(value = PATH)
  void error(HttpServletRequest request, HttpServletResponse response) {
    try {
      response.sendRedirect("/");
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public String getErrorPath() {
    return PATH;
  }
}