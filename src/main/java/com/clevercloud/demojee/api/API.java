package com.clevercloud.demojee.api;

import com.clevercloud.demojee.models.dao.APIRequestFacade;
import com.clevercloud.demojee.models.entities.APIRequest;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.logging.Logger;

/**
 * Created by judu on 25/03/15.
 */
@Path("count")
@Produces(MediaType.TEXT_HTML)
@Stateless
public class API {

   @Inject
   APIRequestFacade arf;

   @GET
   public Response getCount(@Context ContainerRequestContext crc, @Context HttpServletRequest request) {
      String remoteAddr = request.getRemoteAddr();
      Logger.getAnonymousLogger().info("Remote addr is " + remoteAddr);

      final String forwardedfor = crc.getHeaderString("X-Forwarded-For");
      if(forwardedfor != null) {
         Logger.getAnonymousLogger().info("Found X-Forwarded-For " + forwardedfor + ". Use it instead of " + remoteAddr);
         remoteAddr = forwardedfor;
      }

      if(remoteAddr != null) {
         APIRequest ar = new APIRequest(remoteAddr);
         arf.create(ar);
      }
      String responseHTML = "<html><head><title>Requests</title></head><body><table><thead><tr><th>ID</th><th>IP</th><th>timestamp</th></thead><tbody>";
      for (APIRequest apiRequest : arf.findAll()) {
         responseHTML += "<tr>";
         responseHTML += "<td>" + apiRequest.getId().toString() + "</td>";
         responseHTML += "<td>" + apiRequest.getId() + "</td>";
         responseHTML += "<td>" + apiRequest.getTimestamp().toString() + "</td>";
         responseHTML += "</tr>";
      }
      responseHTML += "</tbody></table></body></html>";

      return Response.ok(responseHTML).build();
   }
}
