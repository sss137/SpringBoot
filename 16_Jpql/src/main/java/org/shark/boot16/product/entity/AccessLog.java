package org.shark.boot16.product.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "access_logs")

@Getter
@Setter
public class AccessLog {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "log_id")
  private Integer logId;
  
  @Column(name = "log_type", nullable = false, length = 30)
  private String logType;
  
  @Column(name = "log_message", nullable = false, length = 50)
  private String logMessage;
  
  @Column(name = "log_level", nullable = false, length = 20)
  private String logLevel;
  
  @Column(name = "client_ip", length = 45)
  private String clientIp;
  
  @Column(name = "user_agent", length = 200)
  private String userAgent;
  
  @CreationTimestamp
  @Column(name = "create_date", columnDefinition = "datetime")
  private LocalDateTime createDate;
  
  protected AccessLog() {}
  
  public static AccessLog createAccessLog(String logType, String logMessage, String logLevel, String clientIp, String userAgent) {
    AccessLog a = new AccessLog();
    a.logType = logType;
    a.logMessage = logMessage;
    a.logLevel = logLevel;
    a.clientIp = clientIp;
    a.userAgent = userAgent;
    return a;
  }

  @Override
  public String toString() {
    return "AccessLog [logId=" + logId + ", logType=" + logType + ", logMessage=" + logMessage + ", logLevel="
        + logLevel + ", clientIp=" + clientIp + ", userAgent=" + userAgent + ", createDate=" + createDate + "]";
  }
  
}
