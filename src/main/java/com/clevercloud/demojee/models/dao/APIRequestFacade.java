package com.clevercloud.demojee.models.dao;

import com.clevercloud.demojee.models.entities.APIRequest;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * Created by judu on 25/03/15.
 */
@Stateless
public class APIRequestFacade {
   @PersistenceContext(unitName = "blackpoolPU")
   private EntityManager em;

   public APIRequest create(APIRequest req) {
      em.persist(req);
      return req;
   }

   public List<APIRequest> findAll() {
      final CriteriaBuilder cb = em.getCriteriaBuilder();
      final CriteriaQuery<APIRequest> query = cb.createQuery(APIRequest.class);
      final Root<APIRequest> from = query.from(APIRequest.class);
      query.select(from);
      return em.createQuery(query).getResultList();
   }
}
