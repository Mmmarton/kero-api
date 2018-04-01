package com.komak.kero.keroapi;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ViewController {

  @RequestMapping({"/event", "/user", "/user/list", "/galery", "/event/{id:\\w+}",
      "/upload/{id:\\w+}", "/register/{id:\\w+}/{id:\\w+}", "/home"})
  public String index() {
    return "forward:/index.html";
  }
}