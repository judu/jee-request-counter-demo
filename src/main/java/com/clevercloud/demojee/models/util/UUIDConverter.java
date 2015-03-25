package com.clevercloud.demojee.models.util;

import org.eclipse.persistence.internal.helper.DatabaseField;
import org.eclipse.persistence.mappings.DatabaseMapping;
import org.eclipse.persistence.mappings.DirectCollectionMapping;
import org.eclipse.persistence.mappings.ManyToOneMapping;
import org.eclipse.persistence.mappings.OneToOneMapping;
import org.eclipse.persistence.mappings.converters.Converter;
import org.eclipse.persistence.sessions.Session;
import org.hibernate.type.PostgresUUIDType;

import java.sql.Types;
import java.util.UUID;

/**
 * Created by judu on 25/03/15.
 */
public class UUIDConverter implements Converter {

   private static final long serialVersionUID = 1L;

   @Override
   public Object convertDataValueToObjectValue(Object dataValue, Session session) {
      return dataValue == null ? null : (UUID) dataValue;
   }

   @Override
   public Object convertObjectValueToDataValue(Object objectValue, Session session) {
      return objectValue;
   }

   @Override
   public void initialize(DatabaseMapping argMapping, Session session)
   {
      final DatabaseField field;
      if (argMapping instanceof DirectCollectionMapping)
      {
         field = ((DirectCollectionMapping) argMapping).getDirectField();
      }
      else
      {
         field = argMapping.getField();
      }
      field.setSqlType(Types.OTHER);
      field.setType(UUID.class);
      field.setTypeName("uuid");
      field.setColumnDefinition("UUID");
   }

   @Override
   public boolean isMutable() {
      return false;
   }
}
