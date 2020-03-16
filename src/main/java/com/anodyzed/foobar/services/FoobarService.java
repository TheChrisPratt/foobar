package com.anodyzed.foobar.services;

import com.anodyzed.foobar.model.Foobar;

import java.util.List;

/**
 * FoobarService - Example Service Interface
 *
 * @author Chris Pratt
 * @since 2020-03-07
 */
public interface FoobarService {

  List<Foobar> getFoobars();

  void save(Foobar foobar);

} //*FoobarService
