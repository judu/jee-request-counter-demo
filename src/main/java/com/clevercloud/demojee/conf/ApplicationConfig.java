package com.clevercloud.demojee.conf;

import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.ApplicationPath;

/**
 * Created by judu on 25/03/15.
 */
@ApplicationPath("/")
public class ApplicationConfig extends ResourceConfig {
   public ApplicationConfig() {
      packages("com.clevercloud.demojee");
   }
}
