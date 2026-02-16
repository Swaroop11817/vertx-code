package com.swaroop.udemy.vertx_starter.json;

public class Person {

  private Integer id;
  private String name;
  private boolean lovesVertx;

  public Person(final String name,final Integer id,final boolean lovesVertx) {
    this.name = name;
    this.id = id;
    this.lovesVertx = lovesVertx;
  }

  public Person() {
  }

  public Integer getId() {
    return id;
  }

  public void setId(final Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(final String name) {
    this.name = name;
  }

  public boolean isLovesVertx() {
    return lovesVertx;
  }

  public void setLovesVertx(final boolean lovesVertx) {
    this.lovesVertx = lovesVertx;
  }
}
