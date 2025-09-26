package org.shark.boot14.company.entity;

import org.shark.boot14.common.entity.Address;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "employees")

@Getter
@Setter
public class Employee {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "eid")
  private Long id;
  
  private String name;
  
  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "addr_id")
  private Address address;
  
  protected Employee() {}
  
  public static Employee createEmployee(String name) {
    Employee employee = new Employee();
    employee.name = name;
    return employee;
  }

  // 연관관계 설정을 위한 비즈니스 메소드
  public void assignAddress(Address address) {
    this.address = address;
  }
  
  public void removeAddress() {
    if (this.address != null)
      this.address = null;
  }
  
  @Override
  public String toString() {
    return "Employee [id=" + id + ", name=" + name + ", address=" + address + "]";
  }
  
}