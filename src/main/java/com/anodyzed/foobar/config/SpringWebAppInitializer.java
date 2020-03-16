package com.anodyzed.foobar.config;

import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

/**
 * SpringWebAppInitializer - Spring Web Application Initializer
 *
 * @author Chris Pratt
 * @since 2020-03-07
 */
public class SpringWebAppInitializer implements WebApplicationInitializer {

  @Override
  public void onStartup (ServletContext container) {
    AnnotationConfigWebApplicationContext appContext = new AnnotationConfigWebApplicationContext();
    appContext.register(AppConfig.class);
    addApacheCxfServlet(container);

    container.addListener(new ContextLoaderListener(appContext));
  } //onStartup

  private void addApacheCxfServlet (ServletContext servletContext) {
    CXFServlet cxfServlet = new CXFServlet();

    ServletRegistration.Dynamic appServlet = servletContext.addServlet("CXFServlet",cxfServlet);
    appServlet.setLoadOnStartup(1);

    Set<String> mappingConflicts = appServlet.addMapping("/api/*");
  } //addApacheCxfServlet

} //*SpringWebAppInitializer
