package com.komak.kero.keroapi;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.komak.kero.keroapi.auth.TokenAuthFilter;
import javax.servlet.ServletException;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ContextConfiguration
@Ignore
public class TestBase {

  private MockMvc mockMvc;
  private ObjectMapper objectMapper;

  @Autowired
  private WebApplicationContext context;

  @Autowired
  private TokenAuthFilter filter;

  @Before
  public void setup() throws ServletException {

    mockMvc = MockMvcBuilders
        .webAppContextSetup(context)
        .apply(springSecurity())
        .addFilter(filter)
        .build();

    objectMapper = new ObjectMapper();
  }

  protected MockMvc getMockMvc() {
    return mockMvc;
  }

  protected String toJson(Object object) throws Exception {
    return objectMapper.writer().withDefaultPrettyPrinter().writeValueAsString(object);
  }

  protected <T> T toObject(String json, Class<T> calzz) throws Exception {
    return objectMapper.readValue(json, calzz);
  }

  protected void printResult(MockHttpServletResponse result) throws Exception {
    System.out.println(result.getStatus());
    System.out.println(result.getErrorMessage());
    System.out.println(result.getContentAsString());
  }
}