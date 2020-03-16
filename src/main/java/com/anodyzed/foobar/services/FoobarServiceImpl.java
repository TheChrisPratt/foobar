package com.anodyzed.foobar.services;

import com.anodyzed.foobar.model.Foobar;
import com.anodyzed.foobar.repositories.FoobarRepository;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * FoobarServiceImpl - Example Service Implementation
 *
 * @author Chris Pratt
 * @since 2020-03-07
 */
@Service("foobarService")
public class FoobarServiceImpl implements FoobarService {

  @Resource
  private FoobarRepository foobarRepository;

  @Override
  public List<Foobar> getFoobars () {
    return foobarRepository.findAll();
  } //getFoobars

  @Override
  public void save (Foobar foobar) {
    foobarRepository.save(foobar);
  } //save

} //*FoobarServiceImpl
