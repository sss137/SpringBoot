package org.shark.boot13.company.entity;

import org.shark.boot13.common.embeddable.Address;
import org.shark.boot13.common.embeddable.Name;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "employee")
public class Employee {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "eid")
  private Long id;
  
  @Embedded
  private Name name;
  
  @Embedded
  private Address address;
  
  protected Employee() {}
  
  public static Employee createEmployee(Name name, Address address) {
    Employee employee = new Employee();
    employee.name = name;
    employee.address = address;
    return employee;
  }

  @Override
  public String toString() {
    return "Employee [id=" + id + ", name=" + name + ", address=" + address + "]";
  }
  
}


