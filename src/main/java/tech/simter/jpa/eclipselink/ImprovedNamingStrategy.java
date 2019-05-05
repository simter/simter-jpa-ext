package tech.simter.jpa.eclipselink;

import org.eclipse.persistence.config.SessionCustomizer;
import org.eclipse.persistence.descriptors.ClassDescriptor;
import org.eclipse.persistence.internal.helper.DatabaseField;
import org.eclipse.persistence.mappings.*;
import org.eclipse.persistence.sessions.Session;
import org.eclipse.persistence.tools.schemaframework.IndexDefinition;

import java.util.Locale;

/**
 * An improved naming strategy that prefers embedded underscores to mixed case names.
 * <p>
 * Copy from <a href="https://gist.github.com/ganeshs/c0deb77ffae33dee4555">CamelCaseSessionCustomizer</a> by
 * <a href="https://stackoverflow.com/questions/19896352#38590411">stackoverflow/19896352#38590411</a>.
 *
 * @author RJ
 * @see org.hibernate.cfg.ImprovedNamingStrategy
 */
public class ImprovedNamingStrategy implements SessionCustomizer {
  @Override
  public void customize(Session session) throws Exception {
    for (ClassDescriptor descriptor : session.getDescriptors().values()) {
      // Only change the table name for non-embeddable entities with no @Table already
      if (!descriptor.getTables().isEmpty() && descriptor.getAlias().equalsIgnoreCase(descriptor.getTableName())) {
        String tableName = addUnderscores(descriptor.getTableName());
        descriptor.setTableName(tableName);
        for (IndexDefinition index : descriptor.getTables().get(0).getIndexes()) {
          index.setTargetTable(tableName);
        }
      }

      for (DatabaseMapping mapping : descriptor.getMappings()) {
        // Only change the column name for non-embeddable entities with no @Column already
        if (mapping instanceof AggregateObjectMapping) {
          for (Association association : ((AggregateObjectMapping) mapping).getAggregateToSourceFieldAssociations()) {
            DatabaseField field = (DatabaseField) association.getValue();
            field.setName(addUnderscores(field.getName()));

            for (DatabaseMapping attrMapping : session.getDescriptor(((AggregateObjectMapping) mapping).getReferenceClass()).getMappings()) {
              if (attrMapping.getAttributeName().equalsIgnoreCase((String) association.getKey())) {
                ((AggregateObjectMapping) mapping).addFieldTranslation(field, addUnderscores(attrMapping.getAttributeName()));
                ((AggregateObjectMapping) mapping).getAggregateToSourceFields().remove(association.getKey());
                break;
              }
            }
          }
        } else if (mapping instanceof ObjectReferenceMapping) {
          for (DatabaseField foreignKey : ((ObjectReferenceMapping) mapping).getForeignKeyFields()) {
            foreignKey.setName(addUnderscores(foreignKey.getName()));
          }
        } else if (mapping instanceof DirectMapMapping) {
          for (DatabaseField referenceKey : ((DirectMapMapping) mapping).getReferenceKeyFields()) {
            referenceKey.setName(addUnderscores(referenceKey.getName()));
          }
          for (DatabaseField sourceKey : ((DirectMapMapping) mapping).getSourceKeyFields()) {
            sourceKey.setName(addUnderscores(sourceKey.getName()));
          }
        } else {
          DatabaseField field = mapping.getField();
          if (field != null && !mapping.getAttributeName().isEmpty() && field.getName().equalsIgnoreCase(mapping.getAttributeName())) {
            field.setName(addUnderscores(mapping.getAttributeName()));
          }
        }
      }
    }
  }

  private static String addUnderscores(String name) {
    StringBuilder buf = new StringBuilder(name.replace('.', '_'));
    for (int i = 1; i < buf.length() - 1; i++) {
      if (Character.isLowerCase(buf.charAt(i - 1)) &&
        Character.isUpperCase(buf.charAt(i)) &&
        Character.isLowerCase(buf.charAt(i + 1))
      ) {
        buf.insert(i++, '_');
      }
    }
    return buf.toString().toLowerCase(Locale.ROOT);
  }
}