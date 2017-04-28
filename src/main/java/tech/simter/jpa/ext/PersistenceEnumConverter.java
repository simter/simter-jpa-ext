package tech.simter.jpa.ext;

import tech.simter.persistence.PersistenceEnum;

import javax.persistence.AttributeConverter;

/**
 * Abstract class for convert entity attribute state into database column.
 *
 * @param <E> the type of the entity attribute
 * @param <V> the type of the database column
 * @author dragon 2017-04-28
 */
public abstract class PersistenceEnumConverter<E extends PersistenceEnum<V>, V> implements AttributeConverter<E, V> {
  @Override
  public V convertToDatabaseColumn(E attribute) {
    return attribute == null ? null : attribute.value();
  }
}