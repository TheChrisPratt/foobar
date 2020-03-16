package com.anodyzed.foobar.config;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.Path;
import javax.ws.rs.core.Application;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.ext.RuntimeDelegate;

import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.jaxrs.lifecycle.ResourceProvider;
import org.apache.cxf.jaxrs.spring.SpringResourceFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Import;

/**
 * AppConfig - Spring Application Configuration
 *
 * @author Chris Pratt
 * @since 2020-03-07
 */
@Configuration
@ComponentScan(basePackages="com.anodyzed.foobar")//,excludeFilters={@ComponentScan.Filter(type=FilterType.ANNOTATION,value=Repository.class)})
@Import({PersistenceConfig.class,ResourceConfig.class})
public class AppConfig {

  @Bean(destroyMethod="shutdown")
  public SpringBus cxf () {
    return new SpringBus();
  } //cxf

  private Map<Object,Object> extensionMappings () {
    Map<Object,Object> extensionMappings = new HashMap<>();
    extensionMappings.put("json","application/json");
    return extensionMappings;
  } //extensionMappings

  private List<ResourceProvider> resourceProviders (ApplicationContext ctx) {
    SpringResourceFactory factory;
    List<ResourceProvider> resourceProviders = new LinkedList<>();
    for(String beanName : ctx.getBeanDefinitionNames()) {
      if(ctx.findAnnotationOnBean(beanName,Path.class) != null) {
        factory = new SpringResourceFactory(beanName);
        factory.setApplicationContext(ctx);
        resourceProviders.add(factory);
      }
    }
    return resourceProviders;
  } //resourceProviders

  private List<Object> providers (ApplicationContext ctx) {
    List<Object> providers = new LinkedList<>();
    for(String beanName : ctx.getBeanDefinitionNames()) {
      if(ctx.findAnnotationOnBean(beanName,Provider.class) != null) {
        providers.add(ctx.getBean(beanName));
      }
    }
    return providers;
  } //providers

  @Bean
  @DependsOn("cxf")
  public Server jaxRsServer (ApplicationContext ctx) {
    JAXRSServerFactoryBean factory = RuntimeDelegate.getInstance().createEndpoint(jaxRsApiApplication(),JAXRSServerFactoryBean.class);
    factory.setAddress('/' + factory.getAddress());
    factory.setBus(cxf());
    factory.setExtensionMappings(extensionMappings());
    factory.setProviders(providers(ctx));
    factory.setResourceProviders(resourceProviders(ctx));
    return factory.create();
  } //jaxRsServer

  @Bean
  public JaxRsApiApplication jaxRsApiApplication () {
    return new JaxRsApiApplication();
  } //jaxRsApiApplication

  @ApplicationPath("/")
  public static class JaxRsApiApplication extends Application {
  } //*JaxRsApiApplication

  @Bean
  public GsonProvider<?> gsonProvider () {
    return new GsonProvider<>();
  } //gsonProvider

} //*AppConfig
