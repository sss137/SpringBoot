package org.shark.boot13.common.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class Name {
  @Column(name = "first_name")
  private String firstName;
  
  @Column(name = "last_name")
  private String lastName;
  
  protected Name() {}
  
  public static Name createName(String firstName, String lastName) {
    Name name = new Name();
    name.firstName = firstName;
    name.lastName = lastName;
    return name;
  }

  @Override
  public String toString() {
    return "Name [firstName=" + firstName + ", lastName=" + lastName + "]";
  }
  
}


