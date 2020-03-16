package com.anodyzed.foobar.config;

import com.anodyzed.foobar.resources.FoobarResource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * ResourceConfig
 *
 * @author Chris Pratt
 * @since 2020-03-08
 */
@Configuration
public class ResourceConfig {

  @Bean
  public FoobarResource foobarResource () {
    return new FoobarResource();
  } //foobarResource

} //*ResourceConfig
