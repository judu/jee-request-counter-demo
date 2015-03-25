package com.clevercloud.demojee.conf;

import org.glassfish.jersey.message.GZipEncoder;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.ApplicationPath;

/**
 * Created by judu on 25/03/15.
 */
@ApplicationPath("/")
public class ApplicationConfig extends ResourceConfig {
   public ApplicationConfig() {
      super(GZipEncoder.class);
      packages("com.clevercloud.demojee.api");
   }
}
