package com.anodyzed.foobar.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * Foobar - Example Model Object
 *
 * @author Chris Pratt
 * @since 2020-03-07
 */
@Table(name="Foobar",catalog="Exerceo",schema="Exerceo")
public class Foobar {
  @Id
  @Column(name="ID")
  private Long id;

  @Column(name="Date")
  @Temporal(TemporalType.DATE)
  private Date date;

  @Column(name="Status",length=20)
  private String status;

  public Long getId () {
    return id;
  } //getId

  public void setId (Long id) {
    this.id = id;
  } //setId

  public Date getDate () {
    return date;
  } //getDate

  public void setDate (Date date) {
    this.date = date;
  } //setDate

  public String getStatus () {
    return status;
  } //getStatus

  public void setStatus (String status) {
    this.status = status;
  } //setStatus

} //*Foobar
