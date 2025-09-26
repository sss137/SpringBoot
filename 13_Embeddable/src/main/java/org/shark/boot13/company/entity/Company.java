package org.shark.boot13.company.entity;

import org.shark.boot13.common.embeddable.Address;
import org.shark.boot13.common.embeddable.Contact;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
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
@Table(name = "companies")
public class Company {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "cid")
  private Long id;
  
  @Column(name = "company_name")
  private String companyName;
  
  @Embedded
  private Contact contact;
  
  @Embedded
  @AttributeOverrides({
    @AttributeOverride(name = "postcode", column = @Column(name = "company_postcode")),
    @AttributeOverride(name = "city", column = @Column(name = "company_city")),
    @AttributeOverride(name = "streetAddr", column = @Column(name = "company_street_addr"))
  })
  private Address address;
  
  protected Company() {}
  
  public static Company createCompany(String companyName, Contact contact, Address address) {
    Company company = new Company();
    company.companyName = companyName;
    company.contact = contact;
    company.address = address;
    return company;
  }

  @Override
  public String toString() {
    return "Company [id=" + id + ", companyName=" + companyName + ", contact=" + contact + ", address=" + address + "]";
  }
  
}


