package org.shark.boot14.member.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "lockers")
public class Locker {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "locker_id")
  private Long id;
  
  private String location;
 
  //1:1 연관관계 양방향 설정을 위해서 역방향(자식 -> 부모) 설정이 필요합니다.
  @OneToOne(
      mappedBy = "locker",   //Member의 locker 필드 참조
      fetch = FetchType.LAZY
  )
  private Member member;
  
  protected Locker() {}
  
  public static Locker createdLocker(String location) {
    Locker locker = new Locker();
    locker.location = location;
    return locker;
  }

  @Override
  public String toString() {
    return "Locker [id=" + id + ", location=" + location + ", member=" + member + "]";
  }

}


