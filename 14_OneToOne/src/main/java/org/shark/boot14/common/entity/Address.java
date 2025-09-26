package org.shark.boot14.common.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "addresses")

@Getter
@Setter
public class Address {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "addr_id")
  private Long id;
  
  private String postcode;
  
  private String city;
  
  @Column(name = "street_addr")
  private String streetAddr;
  
  protected Address() {}
  
  public static Address createAddress(String postcode, String city, String streetAddr) {
    Address address = new Address();
    address.postcode = postcode;
    address.city = city;
    address.streetAddr = streetAddr;
    return address;
  }

  @Override
  public String toString() {
    return "Address [postcode=" + postcode + ", city=" + city + ", streetAddr=" + streetAddr + "]";
  }
  
}