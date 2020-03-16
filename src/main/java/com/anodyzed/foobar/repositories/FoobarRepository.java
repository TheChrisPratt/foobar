package com.anodyzed.foobar.repositories;

import com.anodyzed.foobar.model.Foobar;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * FoobarRepository
 *
 * @author Chris Pratt
 * @since 2020-03-10
 */
public interface FoobarRepository extends JpaRepository<Foobar,Long> {
} //#FoobarRepository
