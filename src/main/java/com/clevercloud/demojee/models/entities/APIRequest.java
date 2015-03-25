package com.clevercloud.demojee.models.entities;

import org.eclipse.persistence.annotations.Convert;
import org.eclipse.persistence.annotations.Converter;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

/**
 * Created by judu on 25/03/15.
 */
@Entity
@Table(name = "requests")
public class APIRequest {
   @Id
   @Column(columnDefinition = "uuid")
   @Converter(name="uuidConverter", converterClass = com.clevercloud.demojee.models.util.UUIDConverter.class)
   @Convert("uuidConverter")
   private UUID id;
   @Column(name = "source_ip", nullable = false)
   private String ip;
   @Column(nullable = false, columnDefinition = "timestamp with time zone not null")
   @Temporal(TemporalType.TIMESTAMP)
   private Date timestamp;

   public APIRequest() {
      this.id = UUID.randomUUID();
      this.timestamp = new Date();
   }

   public APIRequest(String ip) {
      this();
      this.ip = ip;
   }

   public UUID getId() {
      return id;
   }

   public String getIp() {
      return ip;
   }

   public Date getTimestamp() {
      return timestamp;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (!(o instanceof APIRequest)) return false;

      APIRequest that = (APIRequest) o;

      if (!id.equals(that.id)) return false;
      return ip.equals(that.ip);

   }

   @Override
   public int hashCode() {
      int result = id.hashCode();
      result = 31 * result + ip.hashCode();
      return result;
   }
}
